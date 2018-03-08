package com.tianyu.jty.jihua.enumeration;

/**
 * Created by hzhonglog on 17/5/15.
 */
public enum LotteryTypeEnum implements IEnum {
    GD11X5("GD11X5", "广东11选5"),
    GDKL10("GDKL10", "广东快乐10分"),
    JSK3("JSK3", "江苏快3"),
    PK10("PK10", "北京赛车"),
    SSC("SSC", "重庆时时彩"),

    OTHER("0", "其他");


    private String code;
    private String description;

    private LotteryTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static LotteryTypeEnum valueFrom(String code) {
        LotteryTypeEnum[] enums = LotteryTypeEnum.values();
        for (LotteryTypeEnum e : enums) {
            if (e.getCode().equals(code) || e.getDescription().equals(code)) {
                return e;
            }
        }
        return OTHER;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public IEnum getOther() {
        return OTHER;
    }
}
