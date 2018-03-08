package com.tianyu.jty.jihua.enumeration.gd11x5;

import com.tianyu.jty.jihua.enumeration.IEnum;

/**
 * Created by hzhonglog on 17/5/15.
 */
public enum  Gd11x5MaEnum implements IEnum {
    M1("1", "1码"),
    M2("2", "2码"),
    M3("3", "3码"),
    M4("4", "4码"),
    M5("5", "5码"),
    M6("6", "6码"),
    M7("7", "7码"),

    OTHER("0", "其他");


    private String code;
    private String description;

    private Gd11x5MaEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static Gd11x5MaEnum valueFrom(String code) {
        Gd11x5MaEnum[] enums = Gd11x5MaEnum.values();
        for (Gd11x5MaEnum e : enums) {
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
