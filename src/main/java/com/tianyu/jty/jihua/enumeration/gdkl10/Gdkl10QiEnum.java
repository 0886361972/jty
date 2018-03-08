package com.tianyu.jty.jihua.enumeration.gdkl10;

import com.tianyu.jty.jihua.enumeration.IEnum;

/**
 * Created by hzhonglog on 17/5/15.
 */
public enum  Gdkl10QiEnum implements IEnum {
    Q1("1", "1期"),
    Q2("2", "2期"),
    Q3("3", "3期"),

    OTHER("0", "其他");


    private String code;
    private String description;

    private Gdkl10QiEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static Gdkl10QiEnum valueFrom(String code) {
        Gdkl10QiEnum[] enums = Gdkl10QiEnum.values();
        for (Gdkl10QiEnum e : enums) {
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
