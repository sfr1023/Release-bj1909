package cn.qf.release.etl.udf

import org.apache.commons.lang.StringUtils

object QFUdf {
    /**
      * 年龄段转换
      */

    def handlerAge(age:String):String={
        var flag =""
        if(!StringUtils.isEmpty(age)){
            flag = String.valueOf(age.toInt %10)
        }
        flag
    }
}
