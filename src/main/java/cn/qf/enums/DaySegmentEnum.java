package cn.qf.enums;

/**
 * 天：时间段划分
 */
public enum DaySegmentEnum {

    MORNING_EARLY("0", "凌晨", 0,6),
    MORNING("1", "早晨", 7,10),
    MIDDAY("2", "中午", 11, 13),
    AFTERNOON("3", "下午", 14, 18),
    NIGHT("4", "晚上",19, 23);


    private String code;
    private String desc;
    private int begin;
    private int end;

    private DaySegmentEnum(String code, String desc, int begin, int end) {
        this.code = code;
        this.desc = desc;
        this.begin = begin;
        this.end = end;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public int getBegin() {
        return begin;
    }

    public int getEnd() {
        return end;
    }
}
