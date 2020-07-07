package cn.qf.release.util

import cn.qf.release.etl.udf.QFUdf
import org.slf4j.{Logger, LoggerFactory}
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object SparkUtils {

    /**
      * 日志
      */
    val logger: Logger = LoggerFactory.getLogger(SparkUtils.getClass)

    /**
     * 获取到SparkContext的入口
     */
    def getSparkContext(appName:String, master:String) : SparkContext = {
        new SparkContext(new SparkConf().setAppName(appName).setMaster(master))
    }

    def getLocalSparkContext(appName:String): SparkContext = {
        getSparkContext(appName, "local[*]")
    }

    /**
     * 获取SparkSql的入口
     */
    def getSparkSession(appName:String, master:String) : SparkSession = {
        SparkSession.builder().appName(appName).master(master).getOrCreate()
    }

    /**
     * 支持hive的sparkSession
     */
    def getSparkSessionSupportHive(appName:String, master:String) : SparkSession = {
        SparkSession.builder().appName(appName).master(master).enableHiveSupport().getOrCreate()
    }

    def getLocalSparkSession(appName:String): SparkSession = {
        getSparkSession(appName, "local[*]")
    }

    def getLocalSparkSession(appName:String, supportHive:Boolean): SparkSession = {
        if (supportHive) getSparkSessionSupportHive(appName, "local[*]")
        else getSparkSession(appName, "local[*]")
    }
    def getLocalSparkSessionHiveSconf(sconf:SparkConf):SparkSession = {
        SparkSession.builder
            .config(sconf)
            .enableHiveSupport()
            .getOrCreate();
    }

    /**
      * udf函数注册
      */
    def registerSelFun(spark:SparkSession): Unit ={
        //年龄处理 ->年龄段
        spark.udf.register("handlerAge",QFUdf.handlerAge _)
    }

    /**
      * 读取hive表数据
      */
    def readTableData(spark:SparkSession,tableName:String,colNames:mutable.Seq[String]): DataFrame ={
        val df = spark.read.table(tableName)
            .selectExpr(colNames:_*)
        df
    }

    /**
      * 日志集合
      */

    def getRangeDates(begin:String,end:String):Seq[String] ={
        val dates = new ArrayBuffer[String]()

        try{
            val beginDate = DateUtils.dateFormat4String(begin,"yyyyMMdd")
            val endDate = DateUtils.dateFormat4String(end,"yyyyMMdd")

            if(begin.equals(end)){
                dates.+=(beginDate)
            }else{
                var cday = beginDate
                while(cday != endDate){
                    dates.+=(cday)
                    val pday = DateUtils.dateFormat4StringDiff(cday,1)
                    cday = pday
                }
            }
        }catch {
            case ex:Exception =>{
                println(s"$ex")
                logger.error(ex.getMessage, ex)
            }
        }
        dates
    }






    /**
     * 释放资源
     */
    def stop(sc:SparkContext) : Unit = {
        if (sc != null) sc.stop()
    }
    def stop(ss:SparkSession) : Unit = {
        if (ss != null) ss.stop()
    }
    def stop(sc:SparkContext, ss:SparkSession) : Unit = {
        if (sc != null) sc.stop()
        if (ss != null) ss.stop()
    }
}
