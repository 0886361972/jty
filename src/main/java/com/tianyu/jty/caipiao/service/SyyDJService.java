package com.tianyu.jty.caipiao.service;

import com.tianyu.jty.caipiao.dao.SyyDJDao;
import com.tianyu.jty.caipiao.entity.PK10;
import com.tianyu.jty.caipiao.entity.SyyDJ;
import com.tianyu.jty.common.persistence.HibernateDao;
import com.tianyu.jty.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by hl on 2016/7/25.
 * 山东十一运夺金
 */
@Service
@Transactional(readOnly = true)
public class SyyDJService extends BaseService<SyyDJ, Integer> {

    @Autowired
    private SyyDJDao syyDJDao;


    @Override
    public HibernateDao<SyyDJ, Integer> getEntityDao() {
        return syyDJDao;
    }

    @Transactional(readOnly = false)
    public void save(SyyDJ syyDJ) {
        if (syyDJDao.findQs(syyDJ.getQs()).isEmpty()) {
            syyDJ.setAddTime(new Date());
            syyDJDao.save(syyDJ);
        }
    }
}
