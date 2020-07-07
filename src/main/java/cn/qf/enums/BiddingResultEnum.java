package cn.qf.enums;

import java.util.Arrays;
import java.util.List;

/**
 * 竞价结果
 */
public enum BiddingResultEnum {

    SUC("1", "竞价成功"),
    FAIL("0", "竞价失败");

    private String code;
    private String desc;

    private BiddingResultEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static List<String> getReleaseStatus(){
        List<String> status = Arrays.asList(
                SUC.code,
                FAIL.code
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
