package com.tianyu.jty.caipiao.task;

import com.tianyu.jty.caipiao.entity.CPK;
import com.tianyu.jty.caipiao.entity.CqSSC;
import com.tianyu.jty.caipiao.service.CqSscService;
import com.tianyu.jty.common.utils.ContextUtils;
import com.tianyu.jty.common.utils.DateUtils;
import com.tianyu.jty.common.utils.Global;
import com.tianyu.jty.system.entity.ScheduleJob;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
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
 * Created by hl on 2016/7/21.
 */
@DisallowConcurrentExecution
public class CqSscJob implements Job {
    private final static Logger logger = LoggerFactory.getLogger(CqSscJob.class);
    private static boolean isSucceed = false;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        if (!Global.getLotteryCaiji()) return;
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");

        CqSscService cqSscService = ContextUtils.getBean(CqSscService.class);
        isSucceed = false;

        fromOpenCai(cqSscService, scheduleJob);
        fromCPK(cqSscService, scheduleJob);
        fromHaoService(cqSscService, scheduleJob);
        fromWapus(cqSscService, scheduleJob);


        logger.info("任务名称 = [" + scheduleJob.getName() + "]" + " 在 " + dateFormat.format(new Date()) + " 时运行");
    }

    public void fromWapus(CqSscService cqSscService, ScheduleJob scheduleJob) {
        try {
            cqSscService.fromWapUs();
        } catch (Exception ex) {
            logger.error("任务名称 = [" + scheduleJob.getName() + "]" + ",重庆时时从WAPUS取数据执行执行异常", ex);
        }
    }

    public void fromCPK(CqSscService cqSscService, ScheduleJob scheduleJob) {
        try {
            String url = CqSSC.URL_FROM_CPK;
            if (StringUtils.isEmpty(url)) return;
            String resultJson = Global.getResultFromHttp(url);
            List<CPK> list = Global.String2CPK(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                CqSSC cqSSC = new CqSSC();
                cqSSC.setKj(cpk.getNumber().replace(",", "-"));
                cqSSC.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                cqSSC.setQs(Long.parseLong(cpk.getKey()));
                cqSscService.save(cqSSC);
                isSucceed = true;
            }
        } catch (Exception ex) {
            logger.error("任务名称 = [" + scheduleJob.getName() + "]" + ",重庆时时从彩控获取数据执行执行异常", ex);
        }
    }

    /**
     * 从重庆官网获取数据
     *
     * @param cqSscService
     * @param scheduleJob
     */
    public void fromCq(CqSscService cqSscService, ScheduleJob scheduleJob) {
        try {
            Document doc = Jsoup.connect(CqSSC.URL_FROM_CQ).timeout(5000).get();
            Elements openlist = doc.select("#openlist").select("li");
            if (!openlist.isEmpty()) {
                for (int i = 0; i < openlist.size(); i++) {
                    if ((i + 1) % 9 == 0) {
                        if ((i + 1) < openlist.size()) {
                            CqSSC cqSsc = new CqSSC();
                            cqSsc.setQs(Long.parseLong("20" + openlist.get(i + 1).text()));
                            cqSsc.setKj(openlist.get(i + 2).text());
                            cqSscService.save(cqSsc);
                        }
                    }
                }
                isSucceed = true;//表明执行成功
            }

        } catch (Exception e) {
            logger.error("任务名称 = [" + scheduleJob.getName() + "]" + ",从重庆官方获取数据执行执行异常", e);
        }
    }


    /**
     * 从百度彩票获取数据
     *
     * @param cqSscService
     * @param scheduleJob
     */
    public void fromOpenCai(CqSscService cqSscService, ScheduleJob scheduleJob) {
        try {
            String url = CqSSC.URL_FROM_OPENCAI;
            if (StringUtils.isEmpty(url)) return;
            String resultJson = Global.getOpenCai(url);
            List<CPK> list = Global.String2OpenCai(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                CqSSC cqSSC = new CqSSC();
                cqSSC.setKj(cpk.getNumber().replace(",", "-"));
                cqSSC.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                cqSSC.setQs(Long.parseLong(cpk.getKey()));
                cqSscService.save(cqSSC);
                isSucceed = true;
            }
        } catch (Exception ex) {
            logger.error("任务名称 = [" + scheduleJob.getName() + "]" + ",重庆时时从彩控获取数据执行执行异常", ex);
        }
    }


    public void fromHaoService(CqSscService cqSscService, ScheduleJob scheduleJob) {
        try {
            String url = CqSSC.URL_FROM_HAOSERVICE;
            if (StringUtils.isEmpty(url)) return;
            String resultJson = Global.getHaoService(url);
            List<CPK> list = Global.String2HaoService(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                CqSSC cqSSC = new CqSSC();
                cqSSC.setKj(cpk.getNumber().replace(",", "-"));
                cqSSC.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                cqSSC.setQs(Long.parseLong(cpk.getKey()));
                cqSscService.save(cqSSC);
                isSucceed = true;
            }
        } catch (Exception ex) {
            logger.error("任务名称 = [" + scheduleJob.getName() + "]" + ",重庆时时从haoService获取数据执行执行异常", ex);
        }
    }
}
