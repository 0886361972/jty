package com.tianyu.jty.caipiao.task;

import com.tianyu.jty.caipiao.entity.CPK;
import com.tianyu.jty.caipiao.entity.PK10;
import com.tianyu.jty.caipiao.service.CqSscService;
import com.tianyu.jty.caipiao.service.PK10Service;
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
public class PK10Job implements Job {
    private final static Logger logger = LoggerFactory.getLogger(PK10Job.class);
    private static boolean isSucceed = false;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        if (!Global.getLotteryCaiji()) return;
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");

        PK10Service pk10Service = ContextUtils.getBean(PK10Service.class);
        isSucceed = false;

        fromOpenCai(pk10Service, scheduleJob);
        fromCPK(pk10Service, scheduleJob);
        //fromHaoservice(pk10Service, scheduleJob);
        fromWapus(pk10Service, scheduleJob);

        logger.info("任务名称 = [" + scheduleJob.getName() + "]" + " 在 " + dateFormat.format(new Date()) + " 时运行");
    }

    public void fromCPK(PK10Service pk10Service, ScheduleJob scheduleJob) {
        try {
            String url = PK10.URL_FROM_CPK;
            if (StringUtils.isEmpty(url)) return;
            String resultJson = Global.getResultFromHttp(url);
            List<CPK> list = Global.String2CPK(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                PK10 pk10 = new PK10();
                pk10.setKj(cpk.getNumber().replace(",", "-"));
                pk10.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                pk10.setQs(Long.parseLong(cpk.getKey()));
                if (null == pk10.getDateLine()) {
                    pk10.setDateLine(new Date());
                }
                pk10Service.save(pk10);
                isSucceed = true;
            }
        } catch (Exception ex) {
            logger.error("任务名称 = [" + scheduleJob.getName() + "]" + ",北京PK10从彩控获取数据执行执行异常", ex);
        }
    }

    public void fromOpenCai(PK10Service pk10Service, ScheduleJob scheduleJob) {
        try {
            String url = PK10.URL_FROM_OPENCAI;
            if (StringUtils.isEmpty(url)) return;
            String resultJson = Global.getOpenCai(url);
            List<CPK> list = Global.String2OpenCai(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);

                PK10 pk10 = new PK10();
                pk10.setKj(cpk.getNumber().replace(",", "-"));
                pk10.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                pk10.setQs(Long.parseLong(cpk.getKey()));
                if (null == pk10.getDateLine()) {
                    pk10.setDateLine(new Date());
                }
                pk10Service.save(pk10);
                isSucceed = true;

            }
        } catch (Exception ex) {
            logger.error("任务名称 = [" + scheduleJob.getName() + "]" + ",北京PK10从开采网执行执行异常", ex);
        }
    }


    public void fromWapus(PK10Service pk10Service, ScheduleJob scheduleJob) {
        try {
            pk10Service.fromWapUs();
        } catch (Exception ex) {
            logger.error("任务名称 = [" + scheduleJob.getName() + "]" + ",北京PK10从WAPUS取数据执行执行异常", ex);
        }
    }

    public void fromHaoservice(PK10Service pk10Service, ScheduleJob scheduleJob) {
        try {
            String url = PK10.URL_FROM_HAOSERVICE;
            if (StringUtils.isEmpty(url)) return;
            String resultJson = Global.getHaoService(url);
            List<CPK> list = Global.String2HaoService(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                if (cpk.getKey().startsWith("61") || cpk.getKey().startsWith("62") || cpk.getKey().startsWith("63")) {
                    PK10 pk10 = new PK10();
                    pk10.setKj(cpk.getNumber().replace(",", "-"));
                    pk10.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                    pk10.setQs(Long.parseLong(cpk.getKey()));
                    if (null == pk10.getDateLine()) {
                        pk10.setDateLine(new Date());
                    }
                    pk10Service.save(pk10);
                    isSucceed = true;
                }
            }
        } catch (Exception ex) {
            logger.error("任务名称 = [" + scheduleJob.getName() + "]" + ",北京PK10从haoservice执行执行异常", ex);
        }
    }

    public void fromBj(PK10Service pk10Service, ScheduleJob scheduleJob) {
        try {
            Document doc = Jsoup.connect(PK10.URL_FROM_BJ).timeout(6000).get();
            Elements trs = doc.select("table.tb").select("tr");
            if (!trs.isEmpty()) {
                for (int i = 0; i < trs.size(); i++) {
                    Elements tds = trs.get(i).select("td");
                    if (!tds.isEmpty()) {
                        PK10 pk10 = new PK10();

                        pk10.setQs(Long.parseLong(tds.get(0).text()));
                        pk10.setKj(tds.get(1).text().replace(",", "-"));
                        pk10Service.save(pk10);
                    }
                }
                isSucceed = true;//表明执行成功
            }
        } catch (Exception ex) {
            logger.error("任务名称 = [" + scheduleJob.getName() + "]" + ",从北京官方获取数据执行执行异常", ex);
        }
    }
}
