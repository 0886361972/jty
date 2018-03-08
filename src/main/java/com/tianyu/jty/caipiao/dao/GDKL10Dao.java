package com.tianyu.jty.caipiao.dao;

import com.tianyu.jty.caipiao.entity.GDKL10;
import com.tianyu.jty.common.persistence.HibernateDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hl on 2016/8/7.
 */
@Repository
public class GDKL10Dao extends HibernateDao<GDKL10, Integer> {


    public List<Integer> findQs(Long qs){
        String hql="select qs from GDKL10 where qs=?0";
        Query query= createQuery(hql, qs);
        return query.list();
    }
}
