package com.tianyu.jty.jihua.dao;

import com.tianyu.jty.common.persistence.HibernateDao;
import com.tianyu.jty.jihua.entity.LieBiao;
import com.tianyu.jty.jihua.enumeration.LotteryTypeEnum;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hzhonglog on 17/5/15.
 */
@Repository
public class LieBiaoDao extends HibernateDao<LieBiao, Integer> {
    /**
     * 查找文章类别是否已经存在
     *
     * @param wenZhangUrl
     * @return
     */
    public List<Integer> findByWenZhangUrl(String wenZhangUrl) {
        String hql = "select id from LieBiao where wenZhangUrl=?0";
        Query query = createQuery(hql, wenZhangUrl);
        return query.list();
    }

    public List<LieBiao> findByStatus(int status) {
        return this.findBy("status", 0);
    }

    public List<LieBiao> findByTopAndStatus(int top, LotteryTypeEnum lotteryTypeEnum, int status) {
        Criteria criteria = createCriteria();
        criteria.add(Restrictions.eq("status", status))
                .add(Restrictions.eq("lotteryType", lotteryTypeEnum.getDescription())).addOrder(Order.desc("id"));
        criteria.setMaxResults(top);
        List result = null;
        try {
            result = criteria.list();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
}
