package com.tianyu.jty.caipiao.entity;

import com.tianyu.jty.common.utils.Global;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 上海时时乐
 * Created by hl on 2016/8/7.
 */
@Entity
@Table(name = "cp_shssl")
public class ShSsl {
    public final static String URL_FROM_SH = "http://fucai.eastday.com/LotteryNew/app_SSL.aspx";

    public static final  String URL_FROM_CPK= Global.getCpkUrl("shssl");

    /**
     * 开采网采集http://opencai.net
     */
    public static final String URL_FROM_OPENCAI = Global.getOpencaiUrl("shssl");


    /**
     * haoservice网络采集
     */
    public static final String URL_FROM_HAOSERVICE = Global.getHaoserviceUrl("shssl");


    /**
     * http://caipiao.wap.us.com网开奖地址
     */
    public static final String URL_FROM_WAPUS = "http://caipiao.wap.us.com/shssl/kaijiang";


    private Integer id;
    private Long qs;
    private String kj;
    private Date addTime;

    public ShSsl() {
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
