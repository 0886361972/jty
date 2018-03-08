package com.tianyu.jty.caipiao.service;

import com.tianyu.jty.caipiao.dao.JSK3Dao;
import com.tianyu.jty.caipiao.entity.JSK3;
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
public class JSK3Service extends BaseService<JSK3, Integer> {

    @Autowired
    private JSK3Dao jsk3Dao;
    @Override
    public HibernateDao<JSK3, Integer> getEntityDao() {
        return jsk3Dao;
    }

    @Transactional(readOnly = false)
    public void save(JSK3 jsk3) {
        if (jsk3Dao.findQs(jsk3.getQs()).isEmpty()) {
            jsk3.setAddTime(new Date());
            jsk3Dao.save(jsk3);
        }
    }
}
