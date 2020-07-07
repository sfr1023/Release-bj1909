package cn.qf.enums;

import java.util.Arrays;
import java.util.List;

/**
 * 竞价状态
 */
public enum BiddingStatusEnum {

    BID_PRICE("01", "出价"),
    WIN_PRICE("02", "获胜价格");

    private String code;
    private String desc;

    private BiddingStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static List<String> getReleaseStatus(){
        List<String> status = Arrays.asList(
                BID_PRICE.code,
                WIN_PRICE.code
        );
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
