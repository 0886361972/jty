package com.tianyu.jty.caipiao.entity;

import com.tianyu.jty.common.utils.Global;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 江苏快3
 * Created by hl on 2016/7/25.
 */

@Entity
@Table(name = "cp_jsk3")
public class JSK3 implements java.io.Serializable {
    public static final String URL_FROM_BAIDU = "http://baidu.lecai.com/lottery/draw/view/564";
    public static final  String URL_FROM_CPK= Global.getCpkUrl("jsks");
    /**
     * 开采网采集http://opencai.net
     */
    public static final String URL_FROM_OPENCAI = Global.getOpencaiUrl("jsk3");


    /**
     * haoservice网络采集
     */
    public static final String URL_FROM_HAOSERVICE = Global.getHaoserviceUrl("jsks");


    private Integer id;
    private Long qs;
    private String kj;
    private Date addTime;
    //操作员
    private String operator;
    //操作员ip
    private String operatorIp;
    //
    private Boolean flag;

    public JSK3() {

    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "QS")
    public Long getQs() {
        return qs;
    }

    public void setQs(Long qs) {
        this.qs = qs;
    }

    @Column(name = "KJ")
    public String getKj() {
        return kj;
    }

    public void setKj(String kj) {
        this.kj = kj;
    }

    @Column(name = "ADDTIME")
    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    private Date dateLine;
    @Column(name = "DATELINE")
    public Date getDateLine() {
        return dateLine;
    }

    public void setDateLine(Date dateLine) {
        this.dateLine = dateLine;
    }

    @Column(name = "OPERATOR")
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Column(name = "OPERATOR_IP")
    public String getOperatorIp() {
        return operatorIp;
    }

    public void setOperatorIp(String operatorIp) {
        this.operatorIp = operatorIp;
    }

    @Column(name = "FLAG")
    public Boolean isFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
}
