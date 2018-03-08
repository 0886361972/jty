package com.tianyu.jty.jihua.enumeration.gd11x5;

import com.tianyu.jty.jihua.enumeration.IEnum;

/**
 * Created by hzhonglog on 17/5/15.
 */
public enum Gd11x5TypeEnum implements IEnum {
    N1("N1", "第一球"),
    N2("N2", "第二球"),
    N3("N3", "第三球"),
    N4("N4", "第四球"),
    N5("N5", "第五球"),
    YiZhong1("N1-1", "一中一"),
    ErZhong2("N2-2", "二中二"),
    OTHER("0", "其他");


    private String code;
    private String description;

    private Gd11x5TypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static Gd11x5TypeEnum valueFrom(String code) {
        Gd11x5TypeEnum[] enums = Gd11x5TypeEnum.values();
        for (Gd11x5TypeEnum e : enums) {
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
