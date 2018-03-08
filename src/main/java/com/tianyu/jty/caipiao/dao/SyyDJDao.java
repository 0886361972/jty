package com.tianyu.jty.caipiao.dao;

import com.tianyu.jty.caipiao.entity.SyyDJ;
import com.tianyu.jty.common.persistence.HibernateDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 山东十一运夺金
 * Created by hl on 2016/7/25.
 */
@Repository
public class SyyDJDao  extends HibernateDao<SyyDJ, Integer> {
    /**
     * 查找期数
     * @param qs
     * @return
     */
    public List<Integer> findQs(Long qs){
        String hql="select qs from SyyDJ where qs=?0";
        Query query= createQuery(hql, qs);
        return query.list();
    }
}
