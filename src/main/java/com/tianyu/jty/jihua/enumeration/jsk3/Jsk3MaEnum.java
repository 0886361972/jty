package com.tianyu.jty.jihua.enumeration.jsk3;

import com.tianyu.jty.jihua.enumeration.IEnum;

/**
 * Created by hzhonglog on 17/5/15.
 */
public enum  Jsk3MaEnum implements IEnum {
    Z1("1","1组"),
    Z2("2","2组"),
    Z3("3","3组"),
    Z4("4","4组"),
    Z6("6","6组"),

    M1("1", "1码"),
    M2("2", "2码"),
    M3("3", "3码"),
    M4("4", "4码"),
    M5("5", "5码"),
    M6("6", "6码"),

    OTHER("0", "其他");


    private String code;
    private String description;

    private Jsk3MaEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static Jsk3MaEnum valueFrom(String code) {
        Jsk3MaEnum[] enums = Jsk3MaEnum.values();
        for (Jsk3MaEnum e : enums) {
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
