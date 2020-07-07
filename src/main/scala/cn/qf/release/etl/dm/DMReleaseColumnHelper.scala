package cn.qf.release.etl.dm

import scala.collection.mutable.ArrayBuffer

/**
  * 数据集市：列集合
  */
object DMReleaseColumnHelper {
    /**
      * 目标客户
      */

    def selectDWReleaseCustomerColumns():ArrayBuffer[String] ={
        val columns = new ArrayBuffer[String]()
        columns.+=("release_session")
        columns.+=("release_status")
        columns.+=("device_num")
        columns.+=("device_type")
        columns.+=("sources")
        //columns.+=("getNewSource(sources) as sources")
        columns.+=("channels")
        columns.+=("idcard")
        columns.+=("age")
        //columns.+=("handlerAge(age) as age_range")
        columns.+=("gender")
        columns.+=("area_code")
        columns.+=("ct")
        columns.+=("bdp_day")
        columns
    }
    /**
      * 目标客户
      *
      * @return
      */
    def selectDMCustomerSourcesColumns():ArrayBuffer[String] ={
        val columns = new ArrayBuffer[String]()
        columns.+=("sources")
        columns.+=("channels")
        columns.+=("device_type")
        columns.+=("user_count")
        columns.+=("total_count")
        columns.+=("bdp_day")
        columns
    }

}
