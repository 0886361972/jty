package com.tianyu.jty.caipiao.dao;

import com.tianyu.jty.caipiao.entity.SyyDJ;
import com.tianyu.jty.caipiao.entity.Xy28;
import com.tianyu.jty.common.persistence.HibernateDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hzhonglong on 2016/9/14.
 */

@Repository
public class Xy28Dao extends HibernateDao<Xy28, Integer> {
    /**
     * 查找期数
     * @param qs
     * @return
     */
    public List<Integer> findQs(Long qs){
        String hql="select qs from Xy28 where qs=?0";
        Query query= createQuery(hql, qs);
        return query.list();
    }
}
