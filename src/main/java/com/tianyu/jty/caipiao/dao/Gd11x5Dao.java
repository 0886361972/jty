package com.tianyu.jty.caipiao.dao;

import com.tianyu.jty.caipiao.entity.Gd11x5;
import com.tianyu.jty.common.persistence.HibernateDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hl on 2016/7/25.
 */
@Repository
public class Gd11x5Dao extends HibernateDao<Gd11x5, Integer> {
    /**
     * 查找期数
     * @param qs
     * @return
     */
    public List<Integer> findQs(Long qs){
        String hql="select qs from Gd11x5 where qs=?0";
        Query query= createQuery(hql, qs);
        return query.list();
    }
}
