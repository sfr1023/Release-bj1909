package cn.qf.enums;

public enum DBColumnTypeEnum {

    TIMESTAMP("timestamp", "时间戳"),
    DATETIME("datetime", "日期"),
    INT("int", "整数");


    private String code;
    private String desc;

    private DBColumnTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
