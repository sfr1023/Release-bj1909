package cn.qf.release.etl.dm

import cn.qf.release.constant.ReleaseConstant
import cn.qf.release.util.SparkUtils
import org.apache.spark.SparkConf
import org.apache.spark.sql.{Column, DataFrame, SaveMode, SparkSession}
import org.slf4j.{Logger, LoggerFactory}

class DMReleaseCustomer{

}

/**
  * 广告投放目标客户数据集市
  */
object DMReleaseCustomer {
    /**
      * 日志
      */

    val logger: Logger = LoggerFactory.getLogger(DMReleaseCustomer.getClass)


    /**
      * 处理目标客户数据
      */
    def handlerCustomer(spark:SparkSession, appName:String, bdp_day:String): Unit ={
        try{
            import spark.implicits._
            import org.apache.spark.sql.functions._

            //dw目标客户主题
            val dwTable = ReleaseConstant.DW_RELEASE_CUSTOMER

            //目标表涉及到的列（原生列、表达式）
            val customerColumns = DMReleaseColumnHelper.selectDMCustomerSourcesColumns()

            //过滤条件
            val customerCondition = (col(s"${ReleaseConstant.DEF_PARTITION}") === lit(bdp_day))

            //dw -> dm目标客户
            val dwDF:DataFrame = SparkUtils.readTableData(spark,dwTable,customerColumns)
                .where(customerCondition)
            dwDF.show(10,false)

            //2 统计计算
            val customerSourceGroupColumns:Seq[Column] = Seq[Column]($"${ReleaseConstant.COL_RELEASE_SOURCES}",$"${ReleaseConstant.COL_RELEASE_CHANNELS}",
                $"${ReleaseConstant.COL_RELEASE_DEVICE_TYPE}")

            //集市表数据列
            val dmColumns = DMReleaseColumnHelper.selectDMCustomerSourcesColumns

            //集市数据集
            val dmDF = dwDF.groupBy(customerSourceGroupColumns:_*)
                .pivot(s"${ReleaseConstant.COL_RELEASE_SOURCES}")
                .agg(
                    countDistinct(s"${ReleaseConstant.COL_RELEASE_DEVICE_NUM}").alias("user_count"),
                    count(s"${ReleaseConstant.COL_RELEASE_SESSION_ID}").alias("total_count")
                )
                .withColumn(s"${ReleaseConstant.DEF_PARTITION}", lit(bdp_day))//如何增加分区列
            dmDF.show(30, false)

            //Cube
            val customerCubeSourceGroupColumns : Seq[Column] = Seq[Column]($"${ReleaseConstant.COL_RELEASE_SOURCES}",
                $"${ReleaseConstant.COL_RELEASE_DEVICE_TYPE}")

            val dmCubeDF = dwDF.cube(customerCubeSourceGroupColumns:_*)
                .agg(
                    countDistinct(s"${ReleaseConstant.COL_RELEASE_DEVICE_NUM}").alias("user_count"),
                    count(s"${ReleaseConstant.COL_RELEASE_SESSION_ID}").alias("total_count")
                )
                .withColumn(s"${ReleaseConstant.DEF_PARTITION}", lit(bdp_day))//如何增加分区列

            dmCubeDF.sort($"${ReleaseConstant.COL_RELEASE_SOURCES}".asc, $"${ReleaseConstant.COL_RELEASE_DEVICE_TYPE}".asc).show(30, false)

        }catch {
            case ex: Exception => {
                logger.error(s"DMReleaseCustomer.handlerCustomer occur exception:", ex)
            }
        }
    }
    /**
      * 处理目标客户数据
      */
    def handlerCustomersJob(appName:String, begin:String, end:String):Unit = {
        var spark:SparkSession = null
        try{
            //1 spark连接-> sparkhelper
            val sconf = new SparkConf()
                .set("hive.exec.dynamic.partition", "true")
                .set("hive.exec.dynamic.partition.mode", "nonstrict")
                .set("hive.merge.mapfiles", "true")
                .set("hive.input.format", "org.apache.hadoop.hive.ql.io.CombineHiveInputFormat")

                .set("spark.sql.shuffle.partitions", "32")
                .set("spark.sql.autoBroadcastJoinThreshold", "50485760")
                .set("spark.sql.crossJoin.enabled", "true")
                .setAppName(appName)
                .setMaster("local[4]")

            val spark:SparkSession = SparkUtils.getLocalSparkSessionHiveSconf(sconf)

            //时间轮询
            val timeRanges = SparkUtils.getRangeDates(begin:String, end:String)
            for(bdp_day <- timeRanges.reverse){

                //目标客户数据处理
                handlerCustomer(spark, appName, bdp_day)
            }

        }catch {
            case ex:Exception => {
                println(s"xxxx")
                logger.error(s"DWReleaseCustomer.handlerCustomerJob occur exception:", ex)
            }
        }finally {
            if(null != spark){
                spark.stop()
            }
        }
    }


    def main(args: Array[String]): Unit = {
        //任务涉及参数的外部传递
        //val Array(appName, bdp_day_begin, bdp_day_end) = args

        val appName = "DMReleaseCustomerJob"
        val bdp_day_begin = "20190613"
        val bdp_day_end = "20190613"


        //1 spark连接-> sparkhelper
        //2 dw读取->etl->dm写入
        //3 时间范围
        handlerCustomersJob(appName, bdp_day_begin, bdp_day_end)
    }
}
