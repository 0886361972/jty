package com.tianyu.jty.caipiao.entity;

import com.tianyu.jty.common.utils.Global;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * PK10,北京赛车
 * Created by hl on 2016/7/21.
 */
@Entity
@Table(name = "cp_pk10")
public class PK10 implements java.io.Serializable {

    public final static String URL_FROM_BJ = "http://www.bwlc.net/bulletin/prevtrax.html";
    public static final String URL_FROM_CPK = Global.getCpkUrl("bjpks");

    /**
     * 开采网采集http://opencai.net
     */
    public static final String URL_FROM_OPENCAI = Global.getOpencaiUrl("bjpk10");

    /**
     * haoservice网络采集
     */
    public static final String URL_FROM_HAOSERVICE = Global.getHaoserviceUrl("bjpks");

    /**
     * http://caipiao.wap.us.com/pk10/kaijiang
     */
    public static final String URL_FROM_WAPUS = "http://caipiao.wap.us.com/pk10/kaijiang";


    private Integer id;
    private Long qs;
    private String kj;
    private Date addTime;


    public PK10() {
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
