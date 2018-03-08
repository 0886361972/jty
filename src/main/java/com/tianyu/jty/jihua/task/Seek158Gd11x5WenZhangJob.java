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
 * Created by hzhonglog on 17/5/19.
 */

public class Seek158Gd11x5WenZhangJob implements Job {

    private final static Logger logger = LoggerFactory.getLogger(Seek158SScWenZhangJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
        logger.debug("任务名称 = [" + scheduleJob.getName() + "]" + " 广东11选5在 " + "[开始]--进行文章抓取");
        WenZhangService wenZhangService = ContextUtils.getBean(WenZhangService.class);
        LieBiaoService lieBiaoService = ContextUtils.getBean(LieBiaoService.class);
        //get(lieBiaoService, wenZhangService);
        wenZhangService.seek158(LotteryTypeEnum.GD11X5);
        logger.debug("任务名称 = [" + scheduleJob.getName() + "]" + "广东11选5在 " + "[结束]--进行文章抓取");
    }
}
