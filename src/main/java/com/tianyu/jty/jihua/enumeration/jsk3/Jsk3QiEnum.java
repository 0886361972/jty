package com.tianyu.jty.jihua.enumeration.jsk3;

import com.tianyu.jty.jihua.enumeration.IEnum;

/**
 * Created by hzhonglog on 17/5/15.
 */
public enum Jsk3QiEnum implements IEnum {
    Q1("1", "1期"),
    Q2("2", "2期"),
    Q3("3", "3期"),
    OTHER("O", "其他");


    private String code;
    private String description;

    private Jsk3QiEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static Jsk3QiEnum valueFrom(String code) {
        Jsk3QiEnum[] enums = Jsk3QiEnum.values();
        for (Jsk3QiEnum e : enums) {
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
