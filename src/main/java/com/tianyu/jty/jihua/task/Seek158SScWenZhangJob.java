package com.tianyu.jty.jihua.task;

import com.tianyu.jty.common.utils.ContextUtils;
import com.tianyu.jty.jihua.enumeration.LotteryTypeEnum;
import com.tianyu.jty.jihua.service.LieBiaoService;
import com.tianyu.jty.jihua.service.WenZhangService;
import com.tianyu.jty.system.entity.ScheduleJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hzhonglog on 17/5/16.
 */

public class Seek158SScWenZhangJob implements Job {

    private final static Logger logger = LoggerFactory.getLogger(Seek158SScWenZhangJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
        logger.debug("任务名称 = [" + scheduleJob.getName() + "]" + " 时时彩在 " + "[开始]--进行文章抓取");
        WenZhangService wenZhangService = ContextUtils.getBean(WenZhangService.class);
        LieBiaoService lieBiaoService = ContextUtils.getBean(LieBiaoService.class);
        //get(lieBiaoService, wenZhangService);
        wenZhangService.seek158(LotteryTypeEnum.SSC);
        logger.debug("任务名称 = [" + scheduleJob.getName() + "]" + " 时时彩在 " + "[结束]--进行文章抓取");
    }

/*    private void get(LieBiaoService lService, WenZhangService wService) {
        List<LieBiao> lieBiaoList = lService.findByTopAndStatus(100, 0);
        if (null != lieBiaoList && lieBiaoList.size() > 0) {
            for (int i = 0; i < lieBiaoList.size(); i++) {
                LieBiao lieBiao = lieBiaoList.get(i);
                WenZhang wenZhang = Seeker158Utils.getWenZhang(lieBiao);
                if (null != wenZhang) {
                    wService.save(wenZhang);
                    lieBiao.setStatus(1);
                    lService.update(lieBiao);
                }
            }
        }
    }*/
}
