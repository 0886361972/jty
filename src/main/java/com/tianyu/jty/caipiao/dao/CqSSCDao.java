package com.tianyu.jty.caipiao.dao;

import com.tianyu.jty.caipiao.entity.CqSSC;
import com.tianyu.jty.common.persistence.HibernateDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hl on 2016/7/21.
 */
@Repository
public class CqSSCDao extends HibernateDao<CqSSC, Integer> {

    /**
     * 查找期数
     * @param qs
     * @return
     */
    public List<Integer> findQs(Long qs){
        String hql="select qs from CqSSC where qs=?0";
        Query query= createQuery(hql, qs);
        return query.list();
    }
}

