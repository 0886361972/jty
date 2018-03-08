package com.tianyu.jty.caipiao.service;

import com.tianyu.jty.caipiao.dao.Gd11x5Dao;
import com.tianyu.jty.caipiao.entity.FC3D;
import com.tianyu.jty.caipiao.entity.Gd11x5;
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
public class Gd11x5Service  extends BaseService<Gd11x5, Integer> {

    @Autowired
    private Gd11x5Dao gd11x5Dao;
    @Override
    public HibernateDao<Gd11x5, Integer> getEntityDao() {
        return gd11x5Dao;
    }

    @Transactional(readOnly = false)
    public void save(Gd11x5 gd11x5) {
        if (gd11x5Dao.findQs(gd11x5.getQs()).isEmpty()) {
            gd11x5.setAddTime(new Date());
            gd11x5Dao.save(gd11x5);
        }
    }
}
