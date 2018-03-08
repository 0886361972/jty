package com.tianyu.jty.caipiao.task;

import com.tianyu.jty.caipiao.entity.BJK8;
import com.tianyu.jty.caipiao.entity.CPK;
import com.tianyu.jty.caipiao.service.BJK8Service;
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
 * 北京快8
 * Created by hl on 2016/7/25.
 */
@DisallowConcurrentExecution
public class BJK8Job implements Job {

    private final static Logger logger = LoggerFactory.getLogger(BJK8Job.class);
    private static boolean isSucceed = false;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        if (!Global.getLotteryCaiji()) return;

        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");

        BJK8Service bjk8Service = ContextUtils.getBean(BJK8Service.class);
        isSucceed = false;

        fromOpencai(bjk8Service, scheduleJob);
        fromCPK(bjk8Service, scheduleJob);
        fromHaoservice(bjk8Service, scheduleJob);

        logger.info("任务名称 = [" + scheduleJob.getName() + "]" + " 在 " + dateFormat.format(new Date()) + " 时运行");
    }

    /**
     * 从彩票控读取数据
     *
     * @param bjk8Service
     * @param scheduleJob
     */
    public void fromCPK(BJK8Service bjk8Service, ScheduleJob scheduleJob) {
        try {
            String url = BJK8.URL_FROM_CPK;
            if (StringUtils.isEmpty(url)) return;
            String resultJson = Global.getResultFromHttp(url);
            List<CPK> list = Global.String2CPK(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                BJK8 bjk8 = new BJK8();
                bjk8.setKj(cpk.getNumber().replace(",", "-"));
                bjk8.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                bjk8.setQs(Long.parseLong(cpk.getKey()));
                bjk8Service.save(bjk8);
                isSucceed = true;
            }
        } catch (Exception ex) {
            logger.error("任务名称 = [" + scheduleJob.getName() + "]" + ",北京快8从彩票控获取数据执行执行异常", ex);

        }
    }

    public void fromOpencai(BJK8Service bjk8Service, ScheduleJob scheduleJob) {
        try {
            String url = BJK8.URL_FROM_OPENCAI;
            if (StringUtils.isEmpty(url)) return;
            String resultJson = Global.getOpenCai(url);
            List<CPK> list = Global.String2OpenCai(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                BJK8 bjk8 = new BJK8();
                bjk8.setKj(cpk.getNumber().replace(",", "-").replace("+", "-"));
                bjk8.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                bjk8.setQs(Long.parseLong(cpk.getKey()));
                bjk8Service.save(bjk8);
                isSucceed = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("任务名称 = [" + scheduleJob.getName() + "]" + ",北京快8从开采网获取数据执行执行异常", ex);

        }
    }

    public void fromHaoservice(BJK8Service bjk8Service, ScheduleJob scheduleJob) {
        try {
            String url = BJK8.URL_FROM_HAOSERVICE;
            if (StringUtils.isEmpty(url)) return;
            String resultJson = Global.getHaoService(url);
            List<CPK> list = Global.String2HaoService(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                BJK8 bjk8 = new BJK8();
                bjk8.setKj(cpk.getNumber().replace(",", "-").replace("+", "-"));
                bjk8.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                bjk8.setQs(Long.parseLong(cpk.getKey()));
                bjk8Service.save(bjk8);
                isSucceed = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("任务名称 = [" + scheduleJob.getName() + "]" + ",北京快8从haoService获取数据执行执行异常", ex);

        }
    }

    public void fromBj(BJK8Service bjk8Service, ScheduleJob scheduleJob) {
        try {
            Document doc = Jsoup.connect(BJK8.URL_FROM_BJ).timeout(5000).get();
            Elements trs = doc.select("table.tb").select("tr");
            if (!trs.isEmpty()) {
                for (int i = 0; i < trs.size(); i++) {
                    Elements tds = trs.get(i).select("td");
                    if (!tds.isEmpty()) {
                        BJK8 bjk8 = new BJK8();
                        bjk8.setQs(Long.parseLong(tds.get(0).text()));
                        bjk8.setKj(tds.get(1).text().replace(",", "-"));
                        bjk8Service.save(bjk8);
                    }
                }
                isSucceed = true;
            }
        } catch (Exception ex) {
            logger.error("任务名称 = [" + scheduleJob.getName() + "]" + ",北京快8从官网获取数据执行执行异常", ex);
        }
    }
}
