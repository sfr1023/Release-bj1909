package cn.qf.release.syncdata

import java.util.Properties

import cn.qf.release.constant.ReleaseConstant
import cn.qf.release.util.SparkUtils
import cn.qf.utils.PropertyUtil
import org.apache.spark.SparkConf
import org.apache.spark.sql.{Row, SparkSession}
import org.slf4j.LoggerFactory

/**
  * 数据源数据同步
  */
class SyncDataJob {

}
object SyncDataJob {
    val logger = LoggerFactory.getLogger(SyncDataJob.getClass)

    /**
      * 数据同步
      */

    def handleSyncData(spark: SparkSession, appName: String): Unit = {
        val begin = System.currentTimeMillis()
        try {
            //1.获取调度表
            val jdbcPro: Properties = PropertyUtil.readProperties(ReleaseConstant.JDBC_CONFIG_PATH)
            if (jdbcPro != null) {
                val url = jdbcPro.getProperty(ReleaseConstant.JDBC_CONFIG_URL)
                val user = jdbcPro.getProperty(ReleaseConstant.JDBC_CONFIG_USER)
                val password = jdbcPro.getProperty(ReleaseConstant.JDBC_CONFIG_PASSWD)

                //需要处理数据对应的数据库连接地址
                val mysqlConnUrlEnd = "?serverTimezone=UTC&characterEncoding=utf-8"
                val serverUrl = url.substring(0, url.lastIndexOf("/") + 1)

                //2.读取数据处理信息
                val prop = new Properties()
                prop.put("user", user)
                prop.put("password", password)
                val tablename = ReleaseConstant.SYNCDATA_MYSQL_HIVE

                //需要同步的数据信息
                val syncTable: Array[Row] = spark.read.jdbc(url, tablename, prop).collect()
                if (!syncTable.isEmpty) {
                    for (dbTable <- syncTable if dbTable.getAs[Int]("status") == ReleaseConstant.SYNCDATA_NOT_USED)
                        try {
                            //读取的mysql数据源信息
                            val dbMysql = dbTable.getAs[String]("db_mysql")
                            val tableMysql = dbTable.getAs[String]("table_mysql")
                            val syncSourceUrl = serverUrl + dbMysql + mysqlConnUrlEnd

                            //写入的hive库表信息
                            val dbHive = dbTable.getAs[String]("db_hive")
                            val tableHive = dbTable.getAs[String]("table_hive")
                            val hiveTable = s"${dbHive}.${tableHive}"

                            //写入模式
                            val saveMode = dbTable.getAs[String]("save_mode")
                            val fieldMap = dbTable.getAs[String]("fieldMap")

                            //3.数据处理
                            val syncSourceDF = spark.read.jdbc(syncSourceUrl, tableMysql, prop)
                            syncSourceDF.write.mode(saveMode).saveAsTable(hiveTable)
                        } catch {
                            case ex: Exception => {
                                logger.error(ex.getMessage, ex)
                            }
                        }
                }
            }
        } catch{
        case ex: Exception => {
            println(s"SyncDataJob.handleSyncData occur exception：app=[$appName], msg=$ex")
            logger.error(ex.getMessage, ex)
        }
        } finally{
            println(s"SyncDataJob.handleSyncData End：appName=[${appName}], use=[${System.currentTimeMillis() - begin}]")
        }
    }

    /**
      * 同步数据
      */
    def handleJobs(appName:String): Unit ={
        var spark:SparkSession = null
        try{
            //spark配置参数
            try {
                //spark配置参数
                val sconf = new SparkConf()
                    .set("hive.exec.dynamic.partition", "true")
                    .set("hive.exec.dynamic.partition.mode", "nonstrict")
                    .set("spark.sql.shuffle.partitions", "32")
                    .set("hive.merge.mapfiles", "true")
                    .set("hive.input.format", "org.apache.hadoop.hive.ql.io.CombineHiveInputFormat")
                    .set("spark.sql.autoBroadcastJoinThreshold", "50485760")
                    .set("spark.sql.crossJoin.enabled", "true")
                    .setAppName(appName)
                    .setMaster("local[4]")

                //spark上下文会话
                spark = SparkUtils.getLocalSparkSessionHiveSconf(sconf)

                handleSyncData(spark, appName)

            } catch {
                case ex: Exception => {
                    println(s"SyncDataJob.handleJobs occur exception：app=[$appName]], msg=$ex")
                    logger.error(ex.getMessage, ex)
                }
            } finally {
                if (spark != null) {
                    spark.stop()
                }
            }
        }
        }
}
