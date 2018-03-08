package com.tianyu.jty.caipiao.dao;

import com.tianyu.jty.caipiao.entity.CQKL10;
import com.tianyu.jty.caipiao.entity.CqSSC;
import com.tianyu.jty.common.persistence.HibernateDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 重庆快乐十分
 */
@Repository
public class CqKL10Dao  extends HibernateDao<CQKL10, Integer> {
    /**
     * 查找期数
     * @param qs
     * @return
     */
    public List<Integer> findQs(Long qs){
        String hql="select qs from CQKL10 where qs=?0";
        Query query= createQuery(hql, qs);
        return query.list();
    }
}
