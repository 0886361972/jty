package com.tianyu.jty.caipiao.dao;

import com.tianyu.jty.caipiao.entity.BJK8;
import com.tianyu.jty.caipiao.entity.FC3D;
import com.tianyu.jty.common.persistence.HibernateDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hl on 2016/7/25.
 */
@Repository
public class FC3DDao  extends HibernateDao<FC3D, Integer> {
    /**
     * 查找期数
     * @param qs
     * @return
     */
    public List<Integer> findQs(Long qs){
        String hql="select qs from FC3D where qs=?0";
        Query query= createQuery(hql, qs);
        return query.list();
    }
}
