package com.tianyu.jty.caipiao.service;

import com.tianyu.jty.caipiao.dao.FC3DDao;
import com.tianyu.jty.caipiao.entity.BJK8;
import com.tianyu.jty.caipiao.entity.FC3D;
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
public class FC3DService extends BaseService<FC3D, Integer> {

    @Autowired
    private FC3DDao fc3DDao;

    @Override
    public HibernateDao<FC3D, Integer> getEntityDao() {
        return fc3DDao;
    }

    @Transactional(readOnly = false)
    public void save(FC3D fc3D) {
        if (fc3DDao.findQs(fc3D.getQs()).isEmpty()) {
            fc3D.setAddTime(new Date());
            fc3DDao.save(fc3D);
        }
    }
}
