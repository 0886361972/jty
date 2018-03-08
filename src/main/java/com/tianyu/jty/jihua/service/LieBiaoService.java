package com.tianyu.jty.jihua.service;

import com.tianyu.jty.common.persistence.HibernateDao;
import com.tianyu.jty.common.service.BaseService;
import com.tianyu.jty.jihua.dao.LieBiaoDao;
import com.tianyu.jty.jihua.entity.LieBiao;
import com.tianyu.jty.jihua.enumeration.LotteryTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by hzhonglog on 17/5/15.
 */
@Service
@Transactional(readOnly = true)
public class LieBiaoService extends BaseService<LieBiao, Integer> {

    @Autowired
    private LieBiaoDao lieBiaoDao;

    @Override
    public HibernateDao<LieBiao, Integer> getEntityDao() {
        return lieBiaoDao;
    }


    @Transactional(readOnly = false)
    public void save(LieBiao lieBiao) {
        if (!(lieBiao.getId() > 0) && !isTitleExists(lieBiao.getTitle())) {
            lieBiao.setCreateDate(new Date());
            lieBiao.setUpdateDate(new Date());
            lieBiaoDao.save(lieBiao);
        } else {
            update(lieBiao);
        }
    }

    @Transactional(readOnly = false)
    public void saveList(List<LieBiao> lieBiaoList) {
        if (null == lieBiaoList || lieBiaoList.size() == 0) return;
        for (LieBiao lieBiao : lieBiaoList) {
            save(lieBiao);
        }
    }


    public boolean isTitleExists(String title) {
        List list = lieBiaoDao.findBy("title", title);
        return (null != list && list.size() > 0);
    }

    @Transactional(readOnly = false)
    public List<LieBiao> findByTopAndStatus(int top, LotteryTypeEnum lotteryTypeEnum, int status) {
        return lieBiaoDao.findByTopAndStatus(top, lotteryTypeEnum, status);
    }
}
