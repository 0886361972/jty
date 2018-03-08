package com.tianyu.jty.caipiao.service;

import com.tianyu.jty.caipiao.dao.BJK8Dao;
import com.tianyu.jty.caipiao.entity.BJK8;
import com.tianyu.jty.caipiao.entity.CQKL10;
import com.tianyu.jty.common.persistence.HibernateDao;
import com.tianyu.jty.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by hl on 2016/7/25.
 */
@Service
@Transactional(readOnly = true)
public class BJK8Service extends BaseService<BJK8, Integer> {

    @Autowired
    private BJK8Dao bjk8Dao;

    @Override
    public HibernateDao<BJK8, Integer> getEntityDao() {
        return bjk8Dao;
    }

    @Transactional(readOnly = false)
    public void save(BJK8 bjk8) {
        if (bjk8Dao.findQs(bjk8.getQs()).isEmpty()) {
            bjk8.setAddTime(new Date());
            bjk8Dao.save(bjk8);
        }
    }
}
