package cn.qf.release.constant

import org.apache.spark.sql.SaveMode
import org.apache.spark.storage.StorageLevel

/**
  * 广告投放常量类
  */
object ReleaseConstant {
    //同步数据（mysql->hive）状态值
    val SYNCDATA_USED:Int =1
    val SYNCDATA_NOT_USED:Int =0
    val SYNCDATA_MYSQL_HIVE:String = "release.sync_mysql_hive"

    //range
    val RANGE_DAYS =2

    //jdbc config
    val JDBC_CONFIG_PATH ="jdbc.properties"
    val JDBC_CONFIG_USER="user"
    val JDBC_CONFIG_PASSWD="password"
    val JDBC_CONFIG_URL="url"

    //partititon
    val DEF_PARTITIONS_FACTOR=4
    val DEF_FILEPARTITION_FACTOR=10
    val DEF_SOURCE_PARTITIONS=1
    val DEF_OTHER_PARTITIONS=8
    val DEF_STORAGE_LEVEL:StorageLevel= StorageLevel.MEMORY_AND_DISK
    val DEF_SAVEMODE:SaveMode = SaveMode.Overwrite
    val DEF_PARTITION:String ="bdp_day"

    //维度列
    val COL_RELEASE_REQ_ID:String = "release_req_id"
    val COL_RELEASE_SESSION_ID:String = "release_session"
    val COL_RELEASE_SESSION_STATUS:String = "release_status"
    val COL_RELEASE_DEVICE_NUM:String = "device_num"
    val COL_RELEASE_DEVICE_TYPE:String = "device_type"
    val COL_RELEASE_SOURCES:String = "sources"
    val COL_RELEASE_AGE_RANGE:String = "age_range"
    val COL_RELEASE_CHANNELS:String = "channels"
    val COL_RELEASE_GENDER:String = "gender"
    val COL_RELEASE_AREA_CODE:String = "area_code"
    val COL_RELEASE_EXTS:String = "exts"
    val COL_RELEASE_CT:String = "ct"

    val COL_RELEASE_BIDDING_TYPE:String = "bidding_type"
    val COL_RELEASE_BIDDING_STATUS:String = "bidding_status"

    //量度列
    val COL_MEASURE_USER_COUNT = "user_count"
    val COL_MEASURE_TOTAL_COUNT = "total_count"

    val COL_MEASURE_EXPOSURE_COUNT = "exposure_count"
    val COL_MEASURE_EXPOSURE_RATES = "exposure_rates"

    //ods======================================================
    val ODS_RELEASE_SESSION = "ods_release.ods_01_release_session"

    //dw======================================================
    val DW_RELEASE_CUSTOMER = "dw_release.dw_release_customer"
    val DW_RELEASE_SHOW = "dw_release.dw_release_show"
    val DW_RELEASE_BIDDING = "dw_release.dw_release_bidding"
    val DW_RELEASE_NOTCUSTOMER = "dw_release.dw_release_notcustomer"
    val DW_RELEASE_EXPOSURE = "dw_release.dw_release_exposure"
    val DW_RELEASE_CLICK = "dw_release.dw_release_click"
    val DW_RELEASE_ARRIVE = "dw_release.dw_release_arrive"



    //mid======================================================



    //dm======================================================
    val DM_RELEASE_CUSTOMER_SOURCES = "dm_release.dm_customer_sources"

    val DM_RELEASE_CUSTOMER_CUBE = "dm_release.dm_customer_cube"

    val DM_RELEASE_EXPOSURE_SOURCES = "dm_release.dm_exposure_sources"

    val DM_RELEASE_EXPOSURE_CUBE = "dm_release.dm_exposure_cube"

    //dm-MYSQL======================================================


}
