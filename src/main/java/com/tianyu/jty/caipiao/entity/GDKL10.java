package com.tianyu.jty.caipiao.entity;

import com.tianyu.jty.common.utils.Global;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 广东快乐10分
 * Created by hl on 2016/8/7.
 */
@Entity
@Table(name = "cp_gdkl10")
public class GDKL10 implements java.io.Serializable {

    /**
     *广东官网
     */
    public static final String URL_FROM_GD="http://www.gdfc.org.cn/play_list_game_9.html";

    /**
     * 彩票控
     */
    public static final  String URL_FROM_CPK= Global.getCpkUrl("gdklsf");

    /**
     * haoservice网络采集
     */
    public static final String URL_FROM_HAOSERVICE = Global.getHaoserviceUrl("gdklsf");




    private Integer id;
    private Long qs;
    private String kj;
    private Date addTime;

    public GDKL10(){

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
