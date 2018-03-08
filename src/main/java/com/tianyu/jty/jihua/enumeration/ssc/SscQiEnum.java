package com.tianyu.jty.jihua.enumeration.ssc;

import com.tianyu.jty.jihua.enumeration.IEnum;

/**
 * Created by hzhonglog on 17/5/15.
 */
public enum  SscQiEnum implements IEnum {
    Q1("1", "1期"),
    Q2("2", "2期"),
    Q3("3", "3期"),
    OTHER("0", "其他");


    private String code;
    private String description;

    private SscQiEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static SscQiEnum valueFrom(String code) {
        SscQiEnum[] enums = SscQiEnum.values();
        for (SscQiEnum e : enums) {
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
