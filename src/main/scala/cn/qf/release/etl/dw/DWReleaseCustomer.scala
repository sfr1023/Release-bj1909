package cn.qf.release.etl.dw

import cn.qf.release.constant.ReleaseConstant
import cn.qf.release.util.SparkUtils
import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.mutable.ArrayBuffer

/**
  * 广告投放_目标客户主题处理
  */
class DWReleaseCustomer {

}

//input+逻辑处理+output

object DWReleaseCustomer {
    /**
      * 日志
      */
    val logger: Logger = LoggerFactory.getLogger(DWReleaseCustomer.getClass)

    /**
      * 处理目标客户数据
      */
    def handlerCustomer(spark: SparkSession, appName: String, bdp_day: String): Unit = {
        try {
            import spark.implicits._
            import org.apache.spark.sql.functions._

            //ods
            val odsTable: String = ReleaseConstant.ODS_RELEASE_SESSION

            //目标表涉及到的列（原生列、列表达式）
            val customerColumns: ArrayBuffer[String] = DWRelaseCustomerColumnHelper.selectDWReleaseCustomerColumns()

            //过滤条件
            val customerCondition = (col(s"${ReleaseConstant.DEF_PARTITION}") === lit(bdp_day)) and (col(s"release_status") === lit("01"))

            //ods ->dw （目标客户）
            val df: DataFrame = SparkUtils.readTableData(spark, odsTable, customerColumns)
                .where(customerCondition)
            df.show(10, false)

            //写入目标主题表
            df.write.mode(SaveMode.Overwrite).insertInto(ReleaseConstant.DW_RELEASE_CUSTOMER)
//            df.write.mode(SaveMode.Overwrite).saveAsTable(ReleaseConstant.DW_RELEASE_CUSTOMER)

        } catch {
            case ex: Exception => {
                logger.error(s"DWReleaseCustomer.handlerCustomer occur exception:", ex)
            }
        }
    }

    /**
      * 处理目标客户数据
      */
    def handlerCustomersJob(appName:String,begin:String,end:String): Unit ={
        var spark:SparkSession = null
        try{
            //1.spark连接
            val conf: SparkConf = new SparkConf()
                .set("hive.exec.dynamic.partition", "true")
                .set("hive.exec.dynamic.partition.mode", "nonstrict")
                .set("hive.merge.mapfiles", "true")
                .set("hive.input.format", "org.apache.hadoop.hive.ql.io.CombineHiveInputFormat")

                .set("spark.sql.shuffle.partitions", "32")
                .set("spark.sql.autoBroadcastJoinThreshold", "50485760")
                .set("spark.sql.crossJoin.enabled", "true")
                .setAppName(appName)
                .setMaster("local[4]")

            val spark:SparkSession = SparkUtils.getLocalSparkSessionHiveSconf(conf)

            //时间轮询
            val timeRanges: Seq[String] = SparkUtils.getRangeDates(begin:String,end:String)
            for(bdp_day <- timeRanges.reverse){
                //目标客户数据处理
                handlerCustomer(spark,appName,bdp_day)
            }
        }catch {
            case ex:Exception => {
                println(s"xxxx")
                logger.error(s"DWReleaseCustomer.handlerCustomerJob occur exception:", ex)
            }
        }finally {
            if (null != spark) {
                spark.stop()
            }
        }
    }
    def main(args: Array[String]): Unit = {
        //任务涉及参数的外部传递
        //val Array(appName, bdp_day_begin, bdp_day_end) = args

        val appName = "DWReleaseCustomerJob"
        val bdp_day_begin = "20190613"
        val bdp_day_end = "20190613"


        //1 spark连接-> sparkhelper
        //2 ods读取->etl->dw写入
        //3 时间范围
        handlerCustomersJob(appName, bdp_day_begin, bdp_day_end)
    }
}


