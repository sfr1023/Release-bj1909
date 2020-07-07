package cn.qf.release.util

import java.sql.Date
import java.time.{LocalDate, LocalDateTime, ZoneId}
import java.time.format.DateTimeFormatter

import org.apache.commons.httpclient.util.DateUtil
import parquet.org.slf4j.LoggerFactory

object DateUtils {
    private val logger = LoggerFactory.getLogger(classOf[DateUtil])
    private val localUrl = "common/holiday.json"
    val PATTERN_YYYYMMDD =  DateTimeFormatter.ofPattern("yyyyMMdd")

    val PATTERN_YYYYMMDD_MID =  DateTimeFormatter.ofPattern("yyyy-MM-dd")

    val PATTERN_HOUR =  DateTimeFormatter.ofPattern("HH")

    def main(args: Array[String]): Unit = {
        val t = 1525164706943l
        val d = new Date(t)
        val diff = -2

        val ds = "20180527"
        val df = dateFormat4StringDiff(ds,diff)
        println(s"${df},${diff}")
    }

    def dateFormat4Long2Pattern(time:Long,pattern:DateTimeFormatter):String={
        if(time <=0 || null ==pattern){
            return null
        }
        val datetime = LocalDateTime.ofInstant(new Date(time).toInstant,ZoneId.systemDefault())
        datetime.format(pattern)
    }

    def dateFormat4Long(time:Long,pattern:String):String={
        if(time <= 0 || null == pattern){
            return null
        }
        val datetime: LocalDateTime = LocalDateTime.ofInstant(new Date(time).toInstant,ZoneId.systemDefault())
        datetime.format(DateTimeFormatter.ofPattern(pattern))
    }

    def dateFormat(date:Date):String={
        if(null == date){
            return null
        }
        val datetime = LocalDateTime.ofInstant(date.toInstant,ZoneId.systemDefault())
        datetime.format(PATTERN_YYYYMMDD)
    }
    def dateformat(date:Date,formater:String):String={
        if(null == date){
            return null
        }
        val datetime = LocalDateTime.ofInstant(date.toInstant,ZoneId.systemDefault())
        datetime.format(DateTimeFormatter.ofPattern(formater))
    }

    def dateFormat4StringDiff(date:String,diff:Long,formater:String="yyyyMMdd"):String={
        if(null == date){
            return null
        }
        val formatter = DateTimeFormatter.ofPattern(formater)
        val datetime =LocalDate.parse(date,formatter)

        val resultDatetime = datetime.plusDays(diff)

        resultDatetime.format(DateTimeFormatter.ofPattern(formater))

    }

    def dateFormat4String(date:String,formater:String="yyyyMMdd"):String={
        if(null == date){
            return null
        }
        val formatter = DateTimeFormatter.ofPattern(formater)
        val datetime = LocalDate.parse(date,formatter)

        datetime.format(DateTimeFormatter.ofPattern(formater))
    }

}
