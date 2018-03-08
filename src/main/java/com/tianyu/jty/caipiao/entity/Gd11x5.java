package com.tianyu.jty.caipiao.entity;

import com.tianyu.jty.common.utils.Global;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 广东11X5
 * Created by hl on 2016/7/25.
 */
@Entity
@Table(name = "cp_gd11x5")
public class Gd11x5 implements java.io.Serializable {
    public static final String URL_FROM_163="http://caipiao.163.com/order/gd11xuan5/";
    public static final  String URL_FROM_CPK= Global.getCpkUrl("gdsyxw");

    /**
     * 开采网采集http://opencai.net
     */
    public static final String URL_FROM_OPENCAI = Global.getOpencaiUrl("gd11x5");


    /**
     * haoservice网络采集
     */
    public static final String URL_FROM_HAOSERVICE = Global.getHaoserviceUrl("gdsyxw");




    private Integer id;
    private Long qs;
    private String kj;
    private Date addTime;

    public Gd11x5(){

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
}
