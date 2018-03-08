package com.tianyu.jty.caipiao.service;

import com.tianyu.jty.caipiao.dao.GDKL10Dao;
import com.tianyu.jty.caipiao.dao.Gd11x5Dao;
import com.tianyu.jty.caipiao.entity.GDKL10;
import com.tianyu.jty.caipiao.entity.Gd11x5;
import com.tianyu.jty.common.persistence.HibernateDao;
import com.tianyu.jty.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by hl on 2016/8/7.
 */
@Service
@Transactional(readOnly = true)
public class GDKL10Service extends BaseService<GDKL10, Integer> {

    @Autowired
    private GDKL10Dao gdkl10Dao;

    @Override
    public HibernateDao<GDKL10, Integer> getEntityDao() {
        return gdkl10Dao;
    }

    @Transactional(readOnly = false)
    public void save(GDKL10 gdkl10) {
        if (gdkl10Dao.findQs(gdkl10.getQs()).isEmpty()) {
            gdkl10.setAddTime(new Date());
            gdkl10Dao.save(gdkl10);
        }
    }
}
