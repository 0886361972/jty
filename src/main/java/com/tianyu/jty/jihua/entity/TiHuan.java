package com.tianyu.jty.jihua.entity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by hzhonglog on 17/5/15.
 */

@Entity
@Table(name = "jh_tihuan")
public class TiHuan  implements java.io.Serializable {
    private int id;
    private String keyword;
    private String replaceWord;


    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "KEYWORD")
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Column(name = "REPLACE_WORD")
    public String getReplaceWord() {
        return replaceWord;
    }

    public void setReplaceWord(String replaceWord) {
        this.replaceWord = replaceWord;
    }

    @Override
    public String toString() {
        return "TiHuan{" +
                "id=" + id +
                ", keyword='" + keyword + '\'' +
                ", replaceWord='" + replaceWord + '\'' +
                '}';
    }
}
