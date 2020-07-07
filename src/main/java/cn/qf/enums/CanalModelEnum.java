package cn.qf.enums;

/**
 * Created by finup on 2018/5/14.
 */
public enum CanalModelEnum {

    CONSUMER_SINGLE(1, "单点模式"),
    CONSUMER_CLUSTER(9, "集群模式");


    CanalModelEnum(Integer model, String desc) {
        this.model = model;
        this.desc = desc;
    }

    private Integer model;

    private String desc;

    public Integer getType() {
        return model;
    }

}
