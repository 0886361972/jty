package com.tianyu.jty.jihua.enumeration.gd11x5;

import com.tianyu.jty.jihua.enumeration.IEnum;

/**
 * Created by hzhonglog on 17/5/15.
 */
public enum  Gd11x5QiEnum implements IEnum {
    Q1("1", "1期"),
    Q2("2", "2期"),
    Q3("3", "3期"),
    OTHER("0", "其他");


    private String code;
    private String description;

    private Gd11x5QiEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static Gd11x5QiEnum valueFrom(String code) {
        Gd11x5QiEnum[] enums = Gd11x5QiEnum.values();
        for (Gd11x5QiEnum e : enums) {
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
