package com.tianyu.jty.caipiao.entity;


import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.tianyu.jty.common.utils.Global;
import org.apache.commons.io.FileUtils;
import org.springframework.util.FileSystemUtils;

import javax.persistence.*;
import java.io.File;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "cp_xy28")
public class Xy28  implements java.io.Serializable {


    public static final String LOCAL_IMAGE_DIR= System.getProperty("user.home")+ File.separator + "xy28_login_image";
    static {
        File theDir=new File(LOCAL_IMAGE_DIR);
        if (!theDir.exists()) {
            theDir.mkdir();
        }


    }

    private Integer id;
    private Long qs;
    private String kj;
    private Date addTime;


    public Xy28() {
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
