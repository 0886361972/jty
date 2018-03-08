package com.tianyu.jty.jihua.enumeration.ssc;

import com.tianyu.jty.jihua.enumeration.IEnum;

/**
 * Created by hzhonglog on 17/5/15.
 */
public enum  SscTypeEnum implements IEnum {
    WUXING("A","五星"),
    N1("N1","个位"),
    N2("N2","十位"),
    N3("N3","百位"),
    N4("N4","千位"),
    N5("N5","万位"),


    OTHER("O", "其他");


    private String code;
    private String description;

    private SscTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static SscTypeEnum valueFrom(String code) {
        SscTypeEnum[] enums = SscTypeEnum.values();
        for (SscTypeEnum e : enums) {
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
