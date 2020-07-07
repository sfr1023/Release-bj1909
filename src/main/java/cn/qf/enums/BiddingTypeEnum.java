package cn.qf.enums;

import java.util.Arrays;
import java.util.List;

/**
 * 竞价类型
 */
public enum BiddingTypeEnum {

    RTB("RTB", "实时竞价"),
    PMP("PMP", "固定价格");

    private String code;
    private String desc;

    private BiddingTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static List<String> getReleaseStatus(){
        List<String> status = Arrays.asList(
                RTB.code,
                PMP.code
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
