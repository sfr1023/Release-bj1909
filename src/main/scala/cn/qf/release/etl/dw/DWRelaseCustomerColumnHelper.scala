package cn.qf.release.etl.dw

import scala.collection.mutable.ArrayBuffer

object DWRelaseCustomerColumnHelper {

    /**
      * 列集合
      * @return
      */
    def selectDWReleaseCustomerColumns():ArrayBuffer[String] ={
        val columns = new ArrayBuffer[String]()
        columns.+=("release_session")
        columns.+=("release_status")
        columns.+=("device_num")
        columns.+=("device_type")
        columns.+=("sources")
        columns.+=("channels")
        columns.+=("get_json_object(exts,'$.idcard') as idcard")
        columns.+=("( cast(date_format(now(),'yyyy') as int) - cast( regexp_extract(get_json_object(exts,'$.idcard'), '(\\\\d{6})(\\\\d{4})(\\\\d{4})', 2) as int) ) as age")
        columns.+=("cast(regexp_extract(get_json_object(exts,'$.idcard'), '(\\\\d{6})(\\\\d{8})(\\\\d{4})', 3) as int) % 2 as gender")
        columns.+=("get_json_object(exts,'$.area_code') as area_code")
        columns.+=("get_json_object(exts,'$.longitude') as longitude")
        columns.+=("get_json_object(exts,'$.latitude') as latitude")
        columns.+=("get_json_object(exts,'$.matter_id') as matter_id")
        columns.+=("get_json_object(exts,'$.model_code') as model_code")
        columns.+=("get_json_object(exts,'$.model_version') as model_version")
        columns.+=("get_json_object(exts,'$.aid') as aid")
        columns.+=("ct")
        //columns.+=("from_unixtime(ct) as nbdp_day")
        //columns.+=("getTimeSegment(ct) as ts")
        columns.+=("bdp_day")
        columns
    }
}
