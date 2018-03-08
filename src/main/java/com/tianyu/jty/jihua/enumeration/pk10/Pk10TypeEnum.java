package com.tianyu.jty.jihua.enumeration.pk10;

import com.tianyu.jty.jihua.enumeration.IEnum;

/**
 * Created by hzhonglog on 17/5/15.
 */
public enum Pk10TypeEnum implements IEnum {
    N1("N1", "冠军"),
    N2("N2", "亚军"),
    N3("N3", "第三名"),
    N4("N4", "第四名"),
    N5("N5", "第五名"),
    N6("N6", "第六名"),
    N7("N7", "第七名"),
    N8("N8", "第八名"),
    N9("N9", "第九名"),
    N10("N10", "第十名"),
    OTHER("O", "其他");


    private String code;
    private String description;

    private Pk10TypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static Pk10TypeEnum valueFrom(String code) {
        Pk10TypeEnum[] enums = Pk10TypeEnum.values();
        for (Pk10TypeEnum e : enums) {
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
