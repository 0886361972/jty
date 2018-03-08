package com.tianyu.jty.jihua.service;

import com.tianyu.jty.common.persistence.HibernateDao;
import com.tianyu.jty.common.service.BaseService;
import com.tianyu.jty.common.utils.StringUtils;
import com.tianyu.jty.jihua.dao.TiHuanDao;
import com.tianyu.jty.jihua.entity.TiHuan;
import com.tianyu.jty.jihua.entity.WenZhang;
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
public class TiHuanService extends BaseService<TiHuan, Integer> {

    @Autowired
    private TiHuanDao tiHuanDao;

    @Override
    public HibernateDao<TiHuan, Integer> getEntityDao() {
        return tiHuanDao;
    }

    public WenZhang relaceWord(WenZhang wenZhang) {
        if (StringUtils.isEmpty(wenZhang.getContent())) {
            return wenZhang;
        }
        List<TiHuan> list = tiHuanDao.findAll();
        if (null != list && list.size() > 0) {

            for (TiHuan t : list) {
                String content = wenZhang.getContent();
                content = content.replace(t.getKeyword(), t.getReplaceWord());
                wenZhang.setContent(content);
            }
        }
        wenZhang.setUpdateDate(new Date());
        return wenZhang;
    }
}
