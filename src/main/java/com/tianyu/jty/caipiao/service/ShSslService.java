package com.tianyu.jty.caipiao.service;

import com.tianyu.jty.caipiao.dao.ShSslDao;
import com.tianyu.jty.caipiao.dao.SyyDJDao;
import com.tianyu.jty.caipiao.entity.CqSSC;
import com.tianyu.jty.caipiao.entity.Gd11x5;
import com.tianyu.jty.caipiao.entity.ShSsl;
import com.tianyu.jty.caipiao.entity.SyyDJ;
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
 * Created by hl on 2016/8/7.
 */
@Service
@Transactional(readOnly = true)
public class ShSslService extends BaseService<ShSsl, Integer> {
    @Autowired
    private ShSslDao shSslDaoDao;


    @Override
    public HibernateDao<ShSsl, Integer> getEntityDao() {
        return shSslDaoDao;
    }

    @Transactional(readOnly = false)
    public void save(ShSsl shSsl) {
        if (shSslDaoDao.findQs(shSsl.getQs()).isEmpty()) {
            shSsl.setAddTime(new Date());
            shSslDaoDao.save(shSsl);
        }
    }

    @Transactional(readOnly = false)
    public String fromWapUs() {
        String result = Global.getResultFromHttp(ShSsl.URL_FROM_WAPUS).replace("\r\n","");
        if (StringUtils.isNotEmpty(result) && result.contains("<br/>")) {
            String[] list = result.split("<br/>");
            for (String line : list) {
                String[] en = line.split(" ");
                if (null != en && en.length == 2) {
                    ShSsl shSsl = new ShSsl();
                    shSsl.setQs(NumberUtils.toLong(en[0].replace("-", ""), 0));
                    shSsl.setKj(en[1].replace(".", "-"));
                    if (shSsl.getQs() <= 0) {
                        continue;
                    } else {
                        shSsl.setDateLine(new Date());
                        shSsl.setAddTime(new Date());
                        save(shSsl);
                    }
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String result = Global.getResultFromHttp(ShSsl.URL_FROM_WAPUS).replace("\r\n","");
        if (StringUtils.isNotEmpty(result) && result.contains("<br/>")) {
            String[] list = result.split("<br/>");
            for (String line : list) {
                String[] en = line.split(" ");
                if (null != en && en.length == 2) {
                    ShSsl shSsl = new ShSsl();
                    shSsl.setQs(NumberUtils.toLong(en[0].replace("-", ""), 0));
                    shSsl.setKj(en[1].replace(".", "-"));
                    if (shSsl.getQs() <= 0) {
                        continue;
                    } else {
                        shSsl.setDateLine(new Date());
                        shSsl.setAddTime(new Date());

                    }
                }
            }
        }
    }


}
