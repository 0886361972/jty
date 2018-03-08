package com.tianyu.jty.jihua.task;

import com.tianyu.jty.common.utils.ContextUtils;
import com.tianyu.jty.jihua.enumeration.IEnum;
import com.tianyu.jty.jihua.enumeration.LotteryTypeEnum;
import com.tianyu.jty.jihua.enumeration.gd11x5.Gd11x5MaEnum;
import com.tianyu.jty.jihua.enumeration.gd11x5.Gd11x5QiEnum;
import com.tianyu.jty.jihua.enumeration.gd11x5.Gd11x5TypeEnum;
import com.tianyu.jty.jihua.enumeration.gdkl10.Gdkl10MaEnum;
import com.tianyu.jty.jihua.enumeration.gdkl10.Gdkl10QiEnum;
import com.tianyu.jty.jihua.enumeration.gdkl10.Gdkl10TypeEnum;
import com.tianyu.jty.jihua.enumeration.jsk3.Jsk3MaEnum;
import com.tianyu.jty.jihua.enumeration.jsk3.Jsk3QiEnum;
import com.tianyu.jty.jihua.enumeration.jsk3.Jsk3TypeEnum;
import com.tianyu.jty.jihua.enumeration.pk10.Pk10MaEnum;
import com.tianyu.jty.jihua.enumeration.pk10.Pk10QiEnum;
import com.tianyu.jty.jihua.enumeration.pk10.Pk10TypeEnum;
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
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 抓起所有页的列表
 * Created by hzhonglog on 17/5/15.
 */
@DisallowConcurrentExecution
public class Seek158AllListJob implements Job {
    private final static Logger logger = LoggerFactory.getLogger(Seek158AllListJob.class);
    private final static int MAX_PAGE = 5;
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");

        LieBiaoService service = ContextUtils.getBean(LieBiaoService.class);

        logger.debug("任务名称 = [" + scheduleJob.getName() + "]" + " 在 " + "[开始]--抓取 江苏快三 的最近" + MAX_PAGE + "页的列表,时间-->" + dateFormat.format(new Date()));
        get(service, LotteryTypeEnum.JSK3, Jsk3TypeEnum.values(), Jsk3MaEnum.values(), Jsk3QiEnum.values());
        logger.debug("任务名称 = [" + scheduleJob.getName() + "]" + " 在 " + "[结束]--抓取 江苏快三 的最近" + MAX_PAGE + "页的列表,时间-->" + dateFormat.format(new Date()));

        logger.debug("任务名称 = [" + scheduleJob.getName() + "]" + " 在 " + "[开始]--抓取 广东十一选五 的最近" + MAX_PAGE + "页的列表,时间-->" + dateFormat.format(new Date()));
        get(service, LotteryTypeEnum.GD11X5, Gd11x5TypeEnum.values(), Gd11x5MaEnum.values(), Gd11x5QiEnum.values());
        logger.debug("任务名称 = [" + scheduleJob.getName() + "]" + " 在 " + "[结束]--抓取 广东十一选五 的最近" + MAX_PAGE + "页的列表,时间-->" + dateFormat.format(new Date()));




        logger.debug("任务名称 = [" + scheduleJob.getName() + "]" + " 在 " + "[开始]--抓取 重庆时时彩 的最近" + MAX_PAGE + "页的列表,时间-->" + dateFormat.format(new Date()));
        get(service, LotteryTypeEnum.SSC, SscTypeEnum.values(), SscMaEnum.values(), SscQiEnum.values());
        logger.debug("任务名称 = [" + scheduleJob.getName() + "]" + " 在 " + "[结束]--抓取 重庆时时彩 的最近" + MAX_PAGE + "页的列表,时间-->" + dateFormat.format(new Date()));

        logger.debug("任务名称 = [" + scheduleJob.getName() + "]" + " 在 " + "[开始]--抓取 北京赛车 的最近" + MAX_PAGE + "页的列表,时间-->" + dateFormat.format(new Date()));
        get(service, LotteryTypeEnum.PK10, Pk10TypeEnum.values(), Pk10MaEnum.values(), Pk10QiEnum.values());
        logger.debug("任务名称 = [" + scheduleJob.getName() + "]" + " 在 " + "[结束]--抓取 北京赛车 的最近" + MAX_PAGE + "页的列表,时间-->" + dateFormat.format(new Date()));

        logger.debug("任务名称 = [" + scheduleJob.getName() + "]" + " 在 " + "[开始]--抓取 广东快乐十分 的最近" + MAX_PAGE + "页的列表,时间-->" + dateFormat.format(new Date()));
        get(service, LotteryTypeEnum.GDKL10, Gdkl10TypeEnum.values(), Gdkl10MaEnum.values(), Gdkl10QiEnum.values());
        logger.debug("任务名称 = [" + scheduleJob.getName() + "]" + " 在 " + "[结束]--抓取 广东快乐十分 的最近" + MAX_PAGE + "页的列表,时间-->" + dateFormat.format(new Date()));
    }

    private void get(LieBiaoService service, LotteryTypeEnum lotteryType, IEnum types[], IEnum mas[], IEnum qis[]) {

        for (int i = MAX_PAGE; i >= 0; i--) {
            Seeker158Utils.getAndSaveListByPage(service, lotteryType, types, mas, qis, i);
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
