package com.tianyu.jty.caipiao.service;

import com.tianyu.jty.caipiao.dao.CqKL10Dao;
import com.tianyu.jty.caipiao.entity.CQKL10;
import com.tianyu.jty.caipiao.entity.CqSSC;
import com.tianyu.jty.common.persistence.HibernateDao;
import com.tianyu.jty.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by hl on 2016/7/24.
 */
@Service
@Transactional(readOnly = true)
public class CqKL10Service extends BaseService<CQKL10, Integer> {
    @Autowired
   private CqKL10Dao cqKL10Dao;

    @Override
    public HibernateDao<CQKL10, Integer> getEntityDao() {
        return cqKL10Dao;
    }

    @Transactional(readOnly = false)
    public void save(CQKL10 cqkl10) {
        if (cqKL10Dao.findQs(cqkl10.getQs()).isEmpty()) {
            cqkl10.setAddTime(new Date());
            cqKL10Dao.save(cqkl10);
        }
    }
}
