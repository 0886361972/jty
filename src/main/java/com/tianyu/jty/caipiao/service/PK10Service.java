package com.tianyu.jty.caipiao.service;

import com.tianyu.jty.caipiao.dao.PK10Dao;
import com.tianyu.jty.caipiao.entity.CqSSC;
import com.tianyu.jty.caipiao.entity.PK10;
import com.tianyu.jty.common.persistence.HibernateDao;
import com.tianyu.jty.common.service.BaseService;
import com.tianyu.jty.common.utils.Global;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by hl on 2016/7/21.
 */
@Service
@Transactional(readOnly = true)
public class PK10Service extends BaseService<PK10, Integer> {

    @Autowired
    private PK10Dao pk10Dao;

    @Override
    public HibernateDao<PK10, Integer> getEntityDao() {
        return pk10Dao;
    }

    @Transactional(readOnly = false)
    public void save(PK10 pk10) {
        if (pk10Dao.findQs(pk10.getQs()).isEmpty()) {
            pk10.setAddTime(new Date());
            pk10Dao.save(pk10);
        }
    }

    @Transactional(readOnly = false)
    public String fromWapUs() {
        String result = Global.getResultFromHttp(PK10.URL_FROM_WAPUS);
        if (StringUtils.isNotEmpty(result) && result.contains("<br/>")) {
            String[] list = result.split("<br/>");
            for (String line : list) {
                String[] en = line.split(" ");
                if (null != en && en.length == 2) {
                    PK10 pk10 = new PK10();
                    String qs = en[0].substring(en[0].indexOf("-") + 1);
                    pk10.setQs(NumberUtils.toLong(qs, 0));
                    pk10.setKj(en[1].replace(".", "-"));
                    if (pk10.getQs() <= 0) {
                        continue;
                    } else {
                        pk10.setDateLine(new Date());
                        pk10.setAddTime(new Date());
                        save(pk10);
                    }
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String result = Global.getResultFromHttp(PK10.URL_FROM_WAPUS);
        if (StringUtils.isNotEmpty(result) && result.contains("<br/>")) {
            String[] list = result.split("<br/>");
            for (String line : list) {
                String[] en = line.split(" ");
                if (null != en && en.length == 2) {
                    PK10 pk10 = new PK10();
                    String qs = en[0].substring(en[0].indexOf("-") + 1);
                    pk10.setQs(NumberUtils.toLong(qs, 0));
                    pk10.setKj(en[1].replace(".", "-"));
                    if (pk10.getQs() <= 0) {
                        continue;
                    } else {
                        pk10.setDateLine(new Date());
                        pk10.setAddTime(new Date());

                    }
                }
            }
        }
    }

}
