package com.tianyu.jty.caipiao.entity;

import com.tianyu.jty.common.utils.Global;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 重庆时时彩
 */
@Entity
@Table(name = "cp_cqssc")
public class CqSSC implements java.io.Serializable {

    public final static String URL_FROM_CQ = "http://www.cqcp.net/game/ssc/";
    public final static String URL_FROM_BAIDU = "http://baidu.lecai.com/lottery/draw/view/200";

    /**
     * 彩票控采集
     */
    public static final String URL_FROM_CPK = Global.getCpkUrl("cqssc");

    /**
     * 开采网采集http://opencai.net
     */
    public static final String URL_FROM_OPENCAI = Global.getOpencaiUrl("cqssc");


    /**
     * haoservice网络采集
     */
    public static final String URL_FROM_HAOSERVICE = Global.getHaoserviceUrl("cqssc");


    /**
     * http://caipiao.wap.us.com网开奖地址
     */
    public static final String URL_FROM_WAPUS = "http://caipiao.wap.us.com/cqssc/kaijiang";


    private static final long serialVersionUID = 1L;
    private Integer id;
    private Long qs;
    private String kj;
    private Date addTime;


    public CqSSC() {
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


