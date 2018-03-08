package com.tianyu.jty.jihua.enumeration.pk10;

import com.tianyu.jty.jihua.enumeration.IEnum;

/**
 * Created by hzhonglog on 17/5/15.
 */
public enum Pk10QiEnum implements IEnum {
    Q1("1", "1期"),
    Q2("2", "2期"),
    Q3("3", "3期"),
    OTHER("0", "其他");


    private String code;
    private String description;

    private Pk10QiEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static Pk10QiEnum valueFrom(String code) {
        Pk10QiEnum[] enums = Pk10QiEnum.values();
        for (Pk10QiEnum e : enums) {
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
