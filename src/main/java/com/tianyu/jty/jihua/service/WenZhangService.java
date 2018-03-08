package com.tianyu.jty.jihua.service;

import com.tianyu.jty.common.persistence.HibernateDao;
import com.tianyu.jty.common.service.BaseService;
import com.tianyu.jty.jihua.dao.LieBiaoDao;
import com.tianyu.jty.jihua.dao.WenZhangDao;
import com.tianyu.jty.jihua.entity.LieBiao;
import com.tianyu.jty.jihua.entity.WenZhang;
import com.tianyu.jty.jihua.enumeration.LotteryTypeEnum;
import com.tianyu.jty.jihua.utils.Seeker158Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by hzhonglog on 17/5/15.
 */
@Service
@Transactional(readOnly = true)
public class WenZhangService extends BaseService<WenZhang, Integer> {
    private final static Logger logger = LoggerFactory.getLogger(WenZhangService.class);

    private final static Map<String,ArrayBlockingQueue> QueueMap = new HashMap();

    static {
        LotteryTypeEnum[] lotterys = LotteryTypeEnum.values();
        for (LotteryTypeEnum lottery : lotterys) {
            if (lottery == LotteryTypeEnum.OTHER) continue;
            QueueMap.put(lottery.getCode(), new ArrayBlockingQueue<LieBiao>(250));
        }
    }

    @Autowired
    private WenZhangDao wenZhangDao;

    @Autowired
    private LieBiaoDao lieBiaoDao;

    @Override
    public HibernateDao<WenZhang, Integer> getEntityDao() {
        return wenZhangDao;
    }

    @Override
    public void save(WenZhang entity) {
       /* if (!(entity.getId() > 0) && findByLeiId(entity.getLieId()) == null) {
            entity.setUpdateDate(new Date());
            entity.setCreateDate(new Date());
            wenZhangDao.save(entity);
        }*/
        wenZhangDao.save(entity);
    }

    public List<WenZhang> findBySourceUrl(String sourceUrl) {
        return wenZhangDao.findBy("sourceUrl", sourceUrl);

    }

    public WenZhang findByLeiId(int leiId) {
        List<WenZhang> list = wenZhangDao.findBy("lieId", leiId);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public boolean isTitleExists(String title) {
        List list = wenZhangDao.findBy("titile", title);
        return (null != list && list.size() > 0);
    }

    @Transactional(readOnly = false)
    public void seek158(LotteryTypeEnum lotteryTypeEnum) {
        ArrayBlockingQueue<LieBiao> tempQueue = QueueMap.get(lotteryTypeEnum.getCode());

        synchronized (tempQueue) {
            if (tempQueue.size() <= 2) {
                List<LieBiao> list = lieBiaoDao.findByTopAndStatus(10, lotteryTypeEnum, 0);
                tempQueue.addAll(list);
            }
        }

        LieBiao lieBiao = tempQueue.poll();

        if (null != lieBiao) {
            WenZhang wenZhang = Seeker158Utils.getWenZhang(lieBiao);
            if (null != wenZhang) {
                save(wenZhang);
                lieBiao.setStatus(1);
                lieBiaoDao.save(lieBiao);
            }
        }
    }

    @Transactional(readOnly = false)
    public void seek158(int count, LotteryTypeEnum lotteryTypeEnum) {
        List<LieBiao> list = lieBiaoDao.findByTopAndStatus(count, lotteryTypeEnum, 0);
        if (null != list && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                try {
                    LieBiao lieBiao = (LieBiao) list.get(i);
                    WenZhang wenZhang = Seeker158Utils.getWenZhang(lieBiao);
                    if (null != wenZhang) {
                        save(wenZhang);
                        lieBiao.setStatus(1);
                        lieBiaoDao.save(lieBiao);
                    }
                    if (count > 1) {
                        Thread.sleep(10);
                    }
                } catch (Exception e) {
                    logger.error("抓取文章保存失败", e);
                }
            }
        }
    }
}
