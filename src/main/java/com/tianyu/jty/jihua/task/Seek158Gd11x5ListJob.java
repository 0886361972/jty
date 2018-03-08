package com.tianyu.jty.jihua.task;

import com.tianyu.jty.common.utils.ContextUtils;
import com.tianyu.jty.jihua.enumeration.LotteryTypeEnum;
import com.tianyu.jty.jihua.service.LieBiaoService;
import com.tianyu.jty.jihua.utils.Seeker158Utils;
import com.tianyu.jty.system.entity.ScheduleJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by hzhonglog on 17/5/15.
 */
@DisallowConcurrentExecution
public class Seek158Gd11x5ListJob implements Job {

    private final static Logger logger = LoggerFactory.getLogger(Seek158Gd11x5ListJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
        LieBiaoService service = ContextUtils.getBean(LieBiaoService.class);

        logger.debug("任务名称 = [" + scheduleJob.getName() + "]" + " 在 " + "[开始]--抓取 广东11选5 最新一页,时间-->" + Seek158AllListJob.dateFormat.format(new Date()));
        // Seeker158Utils.getAndSaveLastList(service, LotteryTypeEnum.GD11X5, Gd11x5TypeEnum.values(), Gd11x5MaEnum.values(), Gd11x5QiEnum.values());
        Seeker158Utils.getAndSaveLastList(service, LotteryTypeEnum.GD11X5);
        logger.debug("任务名称 = [" + scheduleJob.getName() + "]" + " 在 " + "[结束]--抓取 广东11选5 最新一页,时间-->" + Seek158AllListJob.dateFormat.format(new Date()));

    }
}
