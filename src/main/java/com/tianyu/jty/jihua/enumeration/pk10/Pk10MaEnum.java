package com.tianyu.jty.jihua.enumeration.pk10;

import com.tianyu.jty.jihua.enumeration.IEnum;

/**
 * Created by hzhonglog on 17/5/15.
 */
public enum Pk10MaEnum implements IEnum {
    M1("1", "1码"),
    M2("2", "2码"),
    M3("3", "3码"),
    M4("4", "4码"),
    M5("5", "5码"),
    M6("6", "6码"),
    M7("7", "7码"),
    M8("8", "8码"),
    M9("9", "9码"),
    M10("10", "10码"),
    OTHER("0", "其他");


    private String code;
    private String description;

    private Pk10MaEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static Pk10MaEnum valueFrom(String code) {
        Pk10MaEnum[] enums = Pk10MaEnum.values();
        for (Pk10MaEnum e : enums) {
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
