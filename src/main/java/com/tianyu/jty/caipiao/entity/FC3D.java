package com.tianyu.jty.caipiao.entity;

import com.tianyu.jty.common.utils.Global;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 福彩3D
 * Created by hl on 2016/7/25.
 */
@Entity
@Table(name = "cp_fc3d")
public class FC3D  implements java.io.Serializable {

    public static final String URL_FROM_BAIDU="http://baidu.lecai.com/lottery/draw/list/52";
    public static final  String URL_FROM_CPK= Global.getCpkUrl("sd");
    /**
     * 开采网采集http://opencai.net
     */
    public static final String URL_FROM_OPENCAI = Global.getOpencaiUrl("fc3d");



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

    public FC3D() {
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
