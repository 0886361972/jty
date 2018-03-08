package com.tianyu.jty.jihua.enumeration.gdkl10;

import com.tianyu.jty.jihua.enumeration.IEnum;

/**
 * Created by hzhonglog on 17/5/15.
 */
public enum Gdkl10TypeEnum implements IEnum {

    N1("N1", "第一球"),
    N2("N2", "第二球"),
    N3("N3", "第三球"),
    N4("N4", "第四球"),
    N5("N5", "第五球"),
    N6("N6", "第六球"),
    N7("N7", "第七球"),
    N8("N8", "第八球"),
    ERZHONG2("N2-2", "二中二"),



    OTHER("0", "其他");


    private String code;
    private String description;

    private Gdkl10TypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static Gdkl10TypeEnum valueFrom(String code) {
        Gdkl10TypeEnum[] enums = Gdkl10TypeEnum.values();
        for (Gdkl10TypeEnum e : enums) {
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
