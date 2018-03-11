package com.tianyu.jty.caipiao.dao;

import com.tianyu.jty.caipiao.entity.BJK8;
import com.tianyu.jty.common.persistence.HibernateDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hl on 2016/7/25.
 */
@Repository
public class BJK8Dao extends HibernateDao<BJK8, Integer> {
    /**
     * 查找期数
     * @param qs
     * @return
     */
    public List<Integer> findQs(Long qs){
        String hql="select qs from BJK8 where qs=?0";
        Query query= createQuery(hql, qs);
        return query.list();
    }

    /**
     * 查找最大的期数号
     * @return
     */
    public Long findMaxQS(){
        String hql="select qs from BJK8 order by qs desc ";
        Query query= createQuery(hql);
        query.setMaxResults(1);
        return (Long) query.uniqueResult();
    }
}
