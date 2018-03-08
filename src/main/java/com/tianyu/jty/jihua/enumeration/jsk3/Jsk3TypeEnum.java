package com.tianyu.jty.jihua.enumeration.jsk3;

import com.tianyu.jty.jihua.enumeration.IEnum;

/**
 * Created by hzhonglog on 17/5/15.
 */
public enum Jsk3TypeEnum implements IEnum {
    DuDan("N1", "独胆"),
    LianMa("N2", "两连"),
    OTHER("0", "其他");


    private String code;
    private String description;

    private Jsk3TypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static Jsk3TypeEnum valueFrom(String code) {
        Jsk3TypeEnum[] enums = Jsk3TypeEnum.values();
        for (Jsk3TypeEnum e : enums) {
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
