package cn.qf.release.etl.dw

import cn.qf.enums.{BiddingStatusEnum, BiddingTypeEnum}
import cn.qf.release.constant.ReleaseConstant
import cn.qf.release.util.SparkUtils
import org.apache.spark
import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}
import org.apache.spark.storage.StorageLevel
import org.slf4j.LoggerFactory

class DWReleaseBidding {

}

/**
  * 广告投放——竞价主题处理
  */

object DWReleaseBidding{
    /**
      * 日志
      */

    val logger = LoggerFactory.getLogger(DWReleaseBidding.getClass)

    /**
      * 处理竞价数据
      */

    def handlerBidding(spark:SparkSession,appName:String,bdp_day:String): Unit ={
        try{
            import spark.implicits._
            import org.apache.spark.sql.functions._

            //1.ods->竞价主题相关数据
            //ods
            val odsTable = ReleaseConstant.ODS_RELEASE_SESSION

            //竞价主题表涉及的列（原生列、列表达式）
            val biddingColumns = DWRelaseCustomerColumnHelper.selectDWReleaseCustomerColumns()

            //过滤条件
            val biddingCondition = (col(s"${ReleaseConstant.DEF_PARTITION}") === lit(bdp_day)) and
                (col(s"release_status") === lit("02"))

            //ods -> (竞价)
            val biddingDF:DataFrame = SparkUtils.readTableData(spark,odsTable,biddingColumns)
                .where(biddingCondition)
                //.coalesce()
                .repartition(32)
                .persist(StorageLevel.MEMORY_AND_DISK)
            biddingDF.show(10,false)

            //2.分别提取竞价数据（RTB）+固定价格数据（PMP）
            //PMP
            val pmpBiddingColmns = DWRelaseCustomerColumnHelper.selectDWReleaseCustomerColumns()
            val pmpCondition = (col(s"bidding_type") === lit(BiddingTypeEnum.PMP.getCode))
            val pmpDF = biddingDF.where(pmpCondition)
                .selectExpr(pmpBiddingColmns:_*)
            pmpDF.show(10,false)

            //RTB
            val rtbCondition = (col(s"bidding_type") === lit(BiddingTypeEnum.RTB.getCode))
            val rtbDF = biddingDF.where(rtbCondition)

            //3.竞价过程的数据合并->单条数据
            //出价数据
            val bidPriceCondition = (col(s"bidding_status") === lit(BiddingStatusEnum.BID_PRICE.getCode))
            val bidPriceDF = rtbDF.where(biddingCondition)

            //获胜数据
            val winPriceCondition = col(s"bidding_status") === lit(BiddingStatusEnum.WIN_PRICE.getCode)
            val winPriceDF = rtbDF.where(winPriceCondition)

            //bidPriceDF left join winPriceDF
            val nRtbBiddingColumns = DWRelaseCustomerColumnHelper.selectDWReleaseCustomerColumns()
            val nRtbBiddingDF: DataFrame = bidPriceDF.alias("b").join(winPriceDF.alias("w"),bidPriceDF("realease_session")=== winPriceDF("realease_session"),"left")
                .selectExpr(nRtbBiddingColumns:_*)
            nRtbBiddingDF.show(10,false)

            //合并
            val NbiddingDF = pmpDF.union(nRtbBiddingDF)
            NbiddingDF.show(10,false)

            //先建好竞价主题表
            NbiddingDF.write.mode(SaveMode.Overwrite).insertInto(ReleaseConstant.DW_RELEASE_BIDDING)
        }catch {
            case ex:Exception => {
                logger.error(s"DWReleaseCustomer.handlerCustomer occur exception:", ex)
            }
        }
    }

    /**
      * 处理竞价数据
      */
    def handlerBiddingJob(appName:String,begin:String,end:String): Unit = {
        var spark: SparkSession = null
        try {
            //1.spark连接
            val conf = new SparkConf()
                .set("hive.exec.dynamic.partition", "true")
                .set("hive.exec.dynamic.partition.mode", "nonstrict")
                .set("hive.merge.mapfiles", "true")
                .set("hive.input.format", "org.apache.hadoop.hive.ql.io.CombineHiveInputFormat")

                .set("spark.sql.shuffle.partitions", "32")
                .set("spark.sql.autoBroadcastJoinThreshold", "50485760")
                .set("spark.sql.crossJoin.enabled", "true")
                .setAppName(appName)
                .setMaster("local[4]")

            val spark = SparkUtils.getLocalSparkSessionHiveSconf(conf)

            //时间轮询
            val timeRangs = SparkUtils.getRangeDates(begin, end)
            for (bdp_day <- timeRangs.reverse) {
                //目标竞价数据处理
                handlerBidding(spark, appName, bdp_day)
            }
        } catch {
            case ex: Exception => {
                println(s"xxxx")
                logger.error(s"handlerBiddingJob.handlerBiddingJob occur exception:", ex)
            }
        } finally {
            if (null != spark) {
                spark.stop()
            }
        }
    }

    def main(args: Array[String]): Unit = {

        //任务涉及参数的外部传递
        //val Array(appName, bdp_day_begin, bdp_day_end) = args

        val appName = "DWReleaseBiddingJob"
        val bdp_day_begin = "20190613"
        val bdp_day_end = "20190613"


        //1 ods->竞价主题相关数据
        //2 分别提取实时竞价数据(RTB)+固定价格数据(PMP)
        //3 竞价过程的数据合并->单条数据
        handlerBiddingJob(appName, bdp_day_begin, bdp_day_end)

    }
}
