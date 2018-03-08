package com.tianyu.jty.caipiao.task;

import com.tianyu.jty.caipiao.entity.CPK;
import com.tianyu.jty.caipiao.entity.GDKL10;
import com.tianyu.jty.caipiao.service.GDKL10Service;
import com.tianyu.jty.common.utils.ContextUtils;
import com.tianyu.jty.common.utils.DateUtils;
import com.tianyu.jty.common.utils.Global;
import com.tianyu.jty.system.entity.ScheduleJob;
import org.apache.commons.lang3.StringUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by hl on 2016/8/9.
 */

@DisallowConcurrentExecution
public class GDKL10Job  implements Job {

    private final static Logger logger = LoggerFactory.getLogger(GDKL10Job.class);
    private static boolean isSucceed = false;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        if (!Global.getLotteryCaiji()) return;
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");

        GDKL10Service gdkl10Service = ContextUtils.getBean(GDKL10Service.class);
        isSucceed = false;

        fromCPK(gdkl10Service,scheduleJob);
        // fromBaiDu(jsk3Service, scheduleJob);

        logger.info("任务名称 = [" + scheduleJob.getName() + "]" + " 在 " + dateFormat.format(new Date()) + " 时运行");

    }

    public void fromCPK(GDKL10Service gdkl10Service, ScheduleJob scheduleJob) {
        try {
            String url = GDKL10.URL_FROM_CPK;
            if (StringUtils.isEmpty(url)) return;
            String resultJson = Global.getResultFromHttp(url);
            List<CPK> list = Global.String2CPK(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                GDKL10 gdkl10 = new GDKL10();
                gdkl10.setKj(cpk.getNumber().replace(",","-"));
                gdkl10.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                gdkl10.setQs(Long.parseLong(cpk.getKey()));
                gdkl10Service.save(gdkl10);
                isSucceed = true;
            }
        } catch (Exception ex) {
            logger.error("任务名称 = [" + scheduleJob.getName() + "]" + ",江苏快3从彩控获取数据执行执行异常", ex);
        }
    }
}
