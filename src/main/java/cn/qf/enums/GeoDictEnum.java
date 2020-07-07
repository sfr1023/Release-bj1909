package cn.qf.enums;

import java.util.Arrays;
import java.util.List;

/**
 * 经纬度方向
 */
public enum GeoDictEnum {

    ORIGIN("0", "原点"),
    EAST("1", "正东"),
    EAST_SOUTH("2", "东南"),
    SOUTH("3", "正南"),
    WEST_SOUTH("4","西南"),
    WEST("5","正西"),
    WEST_NORTH("6", "西北"),
    NORTH("7", "正北"),
    EAST_NORTH("8", "东北");


    private String code;
    private String desc;

    private GeoDictEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public static List<String> getGeoDicts(){
        List<String> dicts = Arrays.asList(
                ORIGIN.code,
                EAST.code,
                EAST_SOUTH.code,
                SOUTH.code,
                WEST_SOUTH.code,
                WEST.code,
                WEST_NORTH.code,
                NORTH.code,
                EAST_NORTH.code
        );
        return dicts;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
