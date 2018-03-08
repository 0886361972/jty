package com.tianyu.jty.jihua.enumeration.gdkl10;

import com.tianyu.jty.jihua.enumeration.IEnum;

/**
 * Created by hzhonglog on 17/5/15.
 */
public enum Gdkl10MaEnum implements IEnum {
    M1("1","1码"),
    M2("2","2码"),
    M4("4","4码"),
    M6("6","6码"),
    M8("8","8码"),
    M10("10","10码"),
    M12("12","12码"),

    OTHER("0", "其他");


    private String code;
    private String description;

    private Gdkl10MaEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static Gdkl10MaEnum valueFrom(String code) {
        Gdkl10MaEnum[] enums = Gdkl10MaEnum.values();
        for (Gdkl10MaEnum e : enums) {
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
