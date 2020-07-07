package cn.qf.constant;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;

public class CommonConstant implements Serializable {
    public static final int DEF_CODE_COUNT =4; //代码位数
    public static final int DEF_RANGER =10; //范围

    //时间格式
    public static final DateTimeFormatter PATTERN_YYYYMMDD = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter PATTERN_YYYYMMDD_MID = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter PATTERN_HOUR = DateTimeFormatter.ofPattern("HH");

    public static final String FORMATTER_YYYYMMDD="yyyyMMdd";
    public static final String FORMATTER_YYYYMMDD_MID = "yyyy-MM-dd";
    public static final String FORMATTER_YYYYMMDDHHMMDD ="yyyyMMddHHmmss";

    //===charset====================================================================
    public static final String CHARSET_UTF8="utf-8";//测试通道

    //===kafka-topic====================================================================
    public static final String TOPIC_TEST = "t-test"; //测试通道
    public static final String TOPIC_EXAMPLE = "t-example"; //测试2通道
    public static final String TOPIC_PERSON = "t-person"; //用户通道
    public static final String TOPIC_BINLOG = "t-binlog"; //mysql binlog通道
    public static final String TOPIC_SHARE_CAR = "topic-sharecar"; //电车消息通道
    public static final String TOPIC_SHARE_CAR_TEST= "ts-sharecar"; //电车消息通道
    public static final String TOPIC_SHARE_CAR_AGG = "t-sharecar-agg"; //电车消息通道

    public static final String TOPIC_KAFKA_CONSUMER = "__consumer_offsets";

    public static final String TOPIC_BINLOG1 = "t-binlog"; //mysql binlog消息

    public static final String KAFKA_PRODUCER_JSON_PATH = "kafka/json/kafka-producer.properties";
    public static final String KAFKA_CONSUMER_JSON_PATH = "kafka/json/kafka-consumer.properties";
    public static final String KAFKA_CONFIG_PATH = "kafka/kafka-config.properties";

    //===zk====================================================================
    public static final String ZK_CONNECT = "zk.connect";
    public static final String ZK_CONNECT_KAFKA = "zk.kafka.connect";
    public static final String ZK_SESSION_TIMEOUT = "zk.session.timeout";
    public static final String ZK_CONN_TIMEOUT = "zk.connection.timeout";
    public static final String ZK_BEE_ROOT = "zk.dw.root";

    //===常用符号====================================================================

    public static final String Encoding_UTF8 ="utf-8";

    public static final String Encoding_GBK = "GBK";

    public static final String MIDDLE_LINE = "-";
    public static final String BOTTOM_LINE = "_";
    public static final String COMMA = ",";
    public static final String SEMICOLON = ";";
    public static final String PLINE = "|";
    public static final String COLON = ":";
    public static final String PATH_W = "\\";
    public static final String PATH_L = "/";
    public static final String POINT = ".";
    public static final String BLANK = " ";

    public static final String LEFT_ARROWS = "<-";
    public static final String RIGHT_ARROWS = "->";

    public static final String LEFT_BRACKET = "[";
    public static final String RIGHT_BRACKET = "]";

    public static final String TAB = "\t";

    //=====================================================
    public static final String KAFKA_DATA_KEY_TOPIC = "topic";
    public static final String KEY_TIMESTAMP = "timestamp";
    public static final String KEY_KEY = "key";
    public static final String KEY_VALUE = "value";
    public static final String KEY_OFFSET = "offset";
    public static final String KEY_PARTITION = "partition";

    public static final String KEY_CTTIME_BEGIN = "ctTimeBegin";
    public static final String KEY_CTTIME_END = "ctTimeEnd";
    public static final String KEY_CTTIME = "ctTime";
    public static final String KEY_USER_CODE = "userCode";

    public static final String KEY_ORDER_CODE = "orderCode";
    public static final String KEY_VEHICLE_CODE = "vehicleCode";
    public static final String KEY_VEHICLE_TYPE = "vehicleType";
    public static final String  KEY_STATUS = "status";
    public static final String  KEY_LNG = "longitude";
    public static final String  KEY_LAT = "latitude";
    public static final String  KEY_GEOHASH = "geoHash";
    public static final String  KEY_ADCODE = "adcode";
    public static final String  KEY_PROVINCE = "province";
    public static final String  KEY_DISTRICT = "district";
    public static final String  KEY_TOWNCODE = "towncode";
    public static final String  KEY_TOWNSHIP = "township";
    public static final String  KEY_FORMATTED_ADDRESS = "formatted_address";
    public static final String  KEY_ADDRESS = "address";
    public static final String  KEY_G_SIGNAL = "gSignal";

    //GIS
    public final static Double LONGITUDE_CHINA_MAX = 135.05; //经度
    public final static Double LONGITUDE_CHINA_MIN = 73.66; //经度

    public final static Double LATITUDE_CHINA_MAX = 53.55; //纬度
    public final static Double LATITUDE_CHINA_MIN = 3.86; //纬度


//    public final static Double LONGITUDE_CHINA_MAX = 135.05; //经度
//    public final static Double LONGITUDE_CHINA_MIN = 73.66; //经度
//
//    public final static Double LATITUDE_CHINA_MAX = 53.55; //纬度
//    public final static Double LATITUDE_CHINA_MIN = 3.86; //纬度

    //北京行政中心的纬度为39.92，经度为116.46
    //北京 北纬39”26'至41”03'，东经115”25'至117”30'
    public final static Double LONGITUDE_BJ_MAX = 117.30; //经度
    public final static Double LONGITUDE_BJ_MIN = 115.25; //经度

    public final static Double LATITUDE_BJ_MAX = 41.03; //纬度
    public final static Double LATITUDE_BJ_MIN = 39.26; //纬度

    //学校附近
    public final static Double LONGITUDE_QF_MIN = 116.300000; //经度
    public final static Double LONGITUDE_QF_MAX = 116.399999; //经度

    public final static Double LATITUDE_QF_MIN = 40.060000; //纬度
    public final static Double LATITUDE_QF_MAX = 40.069999; //纬度


}

