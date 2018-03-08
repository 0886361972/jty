package com.tianyu.jty.caipiao.service;

import com.tianyu.jty.caipiao.dao.CqSSCDao;
import com.tianyu.jty.caipiao.entity.CqSSC;
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
public class CqSscService extends BaseService<CqSSC, Integer> {

    @Autowired
    private CqSSCDao cqSSCDaoDao;

    @Override
    public HibernateDao<CqSSC, Integer> getEntityDao() {
        return cqSSCDaoDao;
    }

    @Transactional(readOnly = false)
    public void save(CqSSC ssc) {
        if (cqSSCDaoDao.findQs(ssc.getQs()).isEmpty()) {
            ssc.setAddTime(new Date());
            cqSSCDaoDao.save(ssc);
        }
    }

    @Transactional(readOnly = false)
    public String fromWapUs() {
        String result = Global.getResultFromHttp(CqSSC.URL_FROM_WAPUS);
        if (StringUtils.isNotEmpty(result) && result.contains("<br/>")) {
            String[] list = result.split("<br/>");
            for (String line : list) {
                String[] en = line.split(" ");
                if (null != en && en.length == 2) {
                    CqSSC cqSSC = new CqSSC();
                    cqSSC.setQs(NumberUtils.toLong(en[0].replace("-", ""), 0));
                    cqSSC.setKj(en[1].replace(".", "-"));
                    if (cqSSC.getQs() <= 0) {
                        continue;
                    } else {
                        cqSSC.setDateLine(new Date());
                        cqSSC.setAddTime(new Date());
                        save(cqSSC);
                    }
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String result = Global.getResultFromHttp(CqSSC.URL_FROM_WAPUS);
        if (StringUtils.isNotEmpty(result) && result.contains("<br/>")) {
            String[] list = result.split("<br/>");
            for (String line : list) {
                String[] en = line.split(" ");
                if (null != en && en.length == 2) {
                    CqSSC cqSSC = new CqSSC();
                    cqSSC.setQs(NumberUtils.toLong(en[0].replace("-", ""), 0));
                    cqSSC.setKj(en[1].replace(".", "-"));
                    cqSSC.setDateLine(new Date());
                    cqSSC.setAddTime(new Date());
                    if (cqSSC.getQs() <= 0 || cqSSC.getKj().length()>10) {
                        continue;
                    } else {

                    }
                }
            }
        }
    }
}
