package com.tianyu.jty.jihua.task;

import com.tianyu.jty.common.utils.ContextUtils;
import com.tianyu.jty.jihua.enumeration.LotteryTypeEnum;
import com.tianyu.jty.jihua.enumeration.ssc.SscMaEnum;
import com.tianyu.jty.jihua.enumeration.ssc.SscQiEnum;
import com.tianyu.jty.jihua.enumeration.ssc.SscTypeEnum;
import com.tianyu.jty.jihua.service.LieBiaoService;
import com.tianyu.jty.jihua.utils.Seeker158Utils;
import com.tianyu.jty.system.entity.ScheduleJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;

/**
 * Created by hzhonglog on 17/5/15.
 */
@DisallowConcurrentExecution
public class Seek158SscListJob implements Job {
    private final static Logger logger = LoggerFactory.getLogger(Seek158SscListJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
        LieBiaoService service = ContextUtils.getBean(LieBiaoService.class);

        logger.debug("任务名称 = [" + scheduleJob.getName() + "]" + " 在 " + "[开始]--抓取 重庆时时彩 最新一页,时间-->" + Seek158AllListJob.dateFormat.format(new Date()));
        //Seeker158Utils.getAndSaveLastList(service, LotteryTypeEnum.SSC, SscTypeEnum.values(), SscMaEnum.values(), SscQiEnum.values());
        Seeker158Utils.getAndSaveLastList(service, LotteryTypeEnum.SSC);
        logger.debug("任务名称 = [" + scheduleJob.getName() + "]" + " 在 " + "[结束]--抓取 重庆时时彩 最新一页,时间-->" + Seek158AllListJob.dateFormat.format(new Date()));

    }
}
