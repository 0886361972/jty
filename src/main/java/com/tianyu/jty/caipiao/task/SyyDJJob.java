package com.tianyu.jty.caipiao.task;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.tianyu.jty.caipiao.entity.CPK;
import com.tianyu.jty.caipiao.entity.SyyDJ;
import com.tianyu.jty.caipiao.service.SyyDJService;
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
 * Created by hl on 2016/7/25.
 */
@DisallowConcurrentExecution
public class SyyDJJob implements Job {
    private final static Logger logger = LoggerFactory.getLogger(SyyDJJob.class);
    private static boolean isSucceed = false;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        if (!Global.getLotteryCaiji()) return;
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");

        SyyDJService syyDJService = ContextUtils.getBean(SyyDJService.class);
        isSucceed = false;

        //fromSD(syyDJService, scheduleJob);
        fromCPK(syyDJService, scheduleJob);
        fromOpencai(syyDJService, scheduleJob);
        fromHaoservice(syyDJService,scheduleJob);

        logger.info("任务名称 = [" + scheduleJob.getName() + "]" + " 在 " + dateFormat.format(new Date()) + " 时运行");
    }

    public void fromOpencai(SyyDJService syyDJService, ScheduleJob scheduleJob) {
        try {
            String url = SyyDJ.URL_FROM_OPENCAI;
            if (StringUtils.isEmpty(url)) return;
            String resultJson = Global.getOpenCai(url);
            List<CPK> list = Global.String2OpenCai(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                SyyDJ syyDJ = new SyyDJ();
                syyDJ.setKj(cpk.getNumber().replace(",", "-"));
                syyDJ.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                syyDJ.setQs(Long.parseLong(cpk.getKey()));

                syyDJService.save(syyDJ);

                isSucceed = true;
            }
        } catch (Exception ex) {
            logger.error("任务名称 = [" + scheduleJob.getName() + "]" + "山东十一运从开采网获取数据执行执行异常", ex);
        }
    }

    public void fromHaoservice(SyyDJService syyDJService, ScheduleJob scheduleJob) {
        try {
            String url = SyyDJ.URL_FROM_HAOSERVICE;
            if (StringUtils.isEmpty(url)) return;
            String resultJson = Global.getHaoService(url);
            List<CPK> list = Global.String2HaoService(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                SyyDJ syyDJ = new SyyDJ();
                syyDJ.setKj(cpk.getNumber().replace(",", "-"));
                syyDJ.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                syyDJ.setQs(Long.parseLong(cpk.getKey()));
                syyDJService.save(syyDJ);

                isSucceed = true;
            }
        } catch (Exception ex) {
            logger.error("任务名称 = [" + scheduleJob.getName() + "]" + "山东十一运从haoservice获取数据执行执行异常", ex);
        }
    }


    public void fromCPK(SyyDJService syyDJService, ScheduleJob scheduleJob) {
        try {
            String url = SyyDJ.URL_FROM_CPK;
            if (StringUtils.isEmpty(url)) return;
            String resultJson = Global.getResultFromHttp(url);
            List<CPK> list = Global.String2CPK(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                SyyDJ syyDJ = new SyyDJ();
                syyDJ.setKj(cpk.getNumber().replace(",", "-"));
                syyDJ.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                syyDJ.setQs(Long.parseLong(cpk.getKey()));
                syyDJService.save(syyDJ);
                isSucceed = true;
            }
        } catch (Exception ex) {
            logger.error("任务名称 = [" + scheduleJob.getName() + "]" + "山东十一运从彩控获取数据执行执行异常", ex);
        }
    }


    public void fromSD(SyyDJService syyDJService, ScheduleJob scheduleJob) {
        try {
            WebClient wc = new WebClient();
            wc.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true
            wc.getOptions().setCssEnabled(false); //禁用css支持
            wc.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常
            wc.getOptions().setTimeout(10000); //设置连接超时时间 ，这里是10S。如果为0，则无限期等待
            HtmlPage page = wc.getPage(SyyDJ.URL_FROM_SD);
            String pageXml = page.asXml(); //以xml的形式获取响应文本

            Document doc = Jsoup.parse(pageXml, SyyDJ.URL_FROM_SD);
            Elements table = doc.select("table.sbk").select("table.js").select("table.js");
            Elements trs = table.select("tr");
            if (!table.isEmpty() && !trs.isEmpty()) {
                for (int i = 1; i < trs.size(); i++) {
                    Elements tds = trs.get(i).select("td");

                    SyyDJ syyDJ = new SyyDJ();
                    syyDJ.setQs(Long.parseLong(tds.get(0).text()));
                    syyDJ.setKj(tds.get(1).text() + "-" + tds.get(2).text() + "-" + tds.get(3).text() + "-" + tds.get(4).text() + "-" + tds.get(5).text());
                    syyDJService.save(syyDJ);
                }
                isSucceed = true;
            }
        } catch (Exception ex) {
            logger.error("任务名称 = [" + scheduleJob.getName() + "]" + ",十一运夺金从山东官方获取数据执行执行异常", ex);
        }
    }
}
