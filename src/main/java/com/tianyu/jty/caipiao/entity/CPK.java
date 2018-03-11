package com.tianyu.jty.caipiao.entity;

import javax.persistence.Column;

/**
 * 彩票控开奖实体类
 * Created by hl on 2016/8/8.
 */
public class CPK {
    private  String key;
    private  String number;
    private  String dateline;
    //操作员
    private String operator;
    //操作员ip
    private String operatorIp;
    //
    private Boolean flag;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    //@Column(name = "OPERATOR")
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    //@Column(name = "OPERATOR_IP")
    public String getOperatorIp() {
        return operatorIp;
    }

    public void setOperatorIp(String operatorIp) {
        this.operatorIp = operatorIp;
    }

    //@Column(name = "FLAG")
    public Boolean isFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
}
