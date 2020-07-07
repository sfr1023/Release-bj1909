package cn.qf.enums;

import java.util.Arrays;
import java.util.List;

/**
 * 经纬度geohash级别
 */
public enum GeoLevelEnum {

    GEOLEVEL6(6, 609.4d, 1200d, "geohash长度及范围"),
    GEOLEVEL7(7, 152.4d, 152.9d, "geohash长度及范围"),
    GEOLEVEL8(8, 19.0d, 38.2d, "geohash长度及范围"),
    GEOLEVEL9(9, 4.8d, 4.8d, "geohash长度及范围");

    private int length;
    private double latMax;
    private double lngMax;
    private String desc;

    private GeoLevelEnum(int length, double latMax, double lngMax, String desc) {
        this.length = length;
        this.latMax = latMax;
        this.lngMax = lngMax;
        this.desc = desc;
    }

    public int getLength() {
        return length;
    }

    public double getLatMax() {
        return latMax;
    }

    public double getLngMax() {
        return lngMax;
    }

    public String getDesc() {
        return desc;
    }}
