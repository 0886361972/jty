package com.tianyu.jty.jihua.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by hzhonglog on 17/5/15.
 */
@Entity
@Table(name = "jh_wenzhang")
public class WenZhang implements java.io.Serializable {
    private int id;
    private String lotteryType;
    private String categoryType;
    private String qi;
    private String ma;
    private transient String sourceUrl;
    private String content;
    private int lieId;
    private Date createDate;
    private Date updateDate;
    private String categoryDesc;
    private String title;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Column(name = "LOTTERY_TYPE")
    public String getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(String lotteryType) {
        this.lotteryType = lotteryType;
    }

    @Column(name = "CATEGORY_TYPE")
    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    @Column(name = "QI")
    public String getQi() {
        return qi;
    }

    public void setQi(String qi) {
        this.qi = qi;
    }


    @Column(name = "MA")
    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }


    @JsonIgnore
    @Column(name = "SOURCE_URL")
    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    @Column(name = "CONTENT")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "LIE_ID")
    public int getLieId() {
        return lieId;
    }

    public void setLieId(int lieId) {
        this.lieId = lieId;
    }

    @Column(name = "CREATE_DATE")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


    @Column(name = "UPDATE_DATE")
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Column(name = "CATEGORY_DESC")
    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }


    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "WenZhang{" +
                "id=" + id +
                ", lotteryType='" + lotteryType + '\'' +
                ", categoryType='" + categoryType + '\'' +
                ", qi='" + qi + '\'' +
                ", ma='" + ma + '\'' +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", content='" + content + '\'' +
                ", lieId=" + lieId +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", categoryDesc='" + categoryDesc + '\'' +
                ", titile='" + title + '\'' +
                '}';
    }
}
