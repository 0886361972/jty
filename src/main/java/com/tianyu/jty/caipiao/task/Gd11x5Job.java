package com.tianyu.jty.caipiao.task;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.tianyu.jty.caipiao.entity.CPK;
import com.tianyu.jty.caipiao.entity.Gd11x5;
import com.tianyu.jty.caipiao.service.Gd11x5Service;
import com.tianyu.jty.common.utils.ContextUtils;
import com.tianyu.jty.common.utils.DateUtils;
import com.tianyu.jty.common.utils.Global;
import com.tianyu.jty.common.utils.StringUtils;
import com.tianyu.jty.system.entity.ScheduleJob;
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
 * Created by hl on 2016/8/7.
 */
@DisallowConcurrentExecution
public class Gd11x5Job implements Job {
    private final static Logger logger = LoggerFactory.getLogger(Gd11x5Job.class);
    private static boolean isSucceed = false;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        if (!Global.getLotteryCaiji()) return;
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");

        Gd11x5Service gd11x5Service = ContextUtils.getBean(Gd11x5Service.class);
        isSucceed = false;


        fromOpencai(gd11x5Service,scheduleJob);
        fromCPK(gd11x5Service,scheduleJob);
       //from163(gd11x5Service, scheduleJob);

        fromHaoService(gd11x5Service,scheduleJob);

        logger.info("任务名称 = [" + scheduleJob.getName() + "]" + " 在 " + dateFormat.format(new Date()) + " 时运行");
    }

    public void fromCPK(Gd11x5Service gd11x5Service, ScheduleJob scheduleJob) {
        try {
            String url = Gd11x5.URL_FROM_CPK;
            if (org.apache.commons.lang3.StringUtils.isEmpty(url)) return;
            String resultJson = Global.getResultFromHttp(url);
            List<CPK> list = Global.String2CPK(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                Gd11x5 gd11x5 = new Gd11x5();
                gd11x5.setKj(cpk.getNumber().replace(",","-"));
                gd11x5.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                gd11x5.setQs(Long.parseLong(cpk.getKey()));
                gd11x5Service.save(gd11x5);
                isSucceed = true;
            }
        } catch (Exception ex) {
            logger.error("任务名称 = [" + scheduleJob.getName() + "]" + ",广东快乐10分从彩控获取数据执行执行异常", ex);
        }
    }

    public void fromOpencai(Gd11x5Service gd11x5Service, ScheduleJob scheduleJob) {
        try {
            String url = Gd11x5.URL_FROM_OPENCAI;
            if (org.apache.commons.lang3.StringUtils.isEmpty(url)) return;
            String resultJson = Global.getOpenCai(url);
            List<CPK> list = Global.String2OpenCai(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                Gd11x5 gd11x5 = new Gd11x5();
                gd11x5.setKj(cpk.getNumber().replace(",","-"));
                gd11x5.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                gd11x5.setQs(Global.sub20ForQs(cpk.getKey()));
                gd11x5Service.save(gd11x5);

                isSucceed=true;
            }
        } catch (Exception ex) {
            logger.error("任务名称 = [" + scheduleJob.getName() + "]" + ",广东快乐10分从开采网获取数据执行执行异常", ex);
        }
    }

    public void fromHaoService(Gd11x5Service gd11x5Service, ScheduleJob scheduleJob) {
        try {
            String url = Gd11x5.URL_FROM_HAOSERVICE;
            String resultJson = Global.getHaoService(url);
            List<CPK> list = Global.String2HaoService(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                Gd11x5 gd11x5 = new Gd11x5();
                gd11x5.setKj(cpk.getNumber().replace(",","-"));
                gd11x5.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                gd11x5.setQs(Global.sub20ForQs(cpk.getKey()));
                gd11x5Service.save(gd11x5);

                isSucceed=true;
            }
        } catch (Exception ex) {
            logger.error("任务名称 = [" + scheduleJob.getName() + "]" + ",广东快乐10分从haoservice获取数据执行执行异常", ex);
        }
    }

    private void from163(Gd11x5Service gd11x5Service, ScheduleJob scheduleJob) {
        try {
            WebClient wc = new WebClient();
            wc.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true
            wc.getOptions().setCssEnabled(false); //禁用css支持
            wc.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常
            wc.getOptions().setTimeout(10000); //设置连接超时时间 ，这里是10S。如果为0，则无限期等待
            HtmlPage page = wc.getPage(Gd11x5.URL_FROM_163);
            String pageXml = page.asXml(); //以xml的形式获取响应文本
            Document doc = Jsoup.parse(pageXml, "http://caipiao.163.com/");
            Elements trs = doc.select("#j-todayNumTable").select("tr");

            String currentPeriod = "20" + doc.select("#currentPeriod").text();
            currentPeriod = currentPeriod.substring(0, 8);

            for (int i = 1; i < trs.size(); i++) {
                Elements tds = trs.get(i).select("td");

                String t1 = tds.get(0).text().trim().replace("-", "");
                String t2 = tds.get(1).text().trim().replace("-", "");
                String t3 = tds.get(2).text().trim().replace("-", "");
                String t4 = tds.get(3).text().trim().replace("-", "");
                String t5 = tds.get(4).text().trim().replace("-", "");
                String t6 = tds.get(5).text().trim().replace("-", "");
                String t7 = tds.get(6).text().trim().replace("-", "");
                String t8 = tds.get(7).text().trim().replace("-", "");

                Gd11x5 gd11x5 = null;
                if (StringUtils.isNotEmpty(t2) && StringUtils.isNotEmpty(t1)) {
                    gd11x5 = new Gd11x5();
                    gd11x5.setQs(Long.parseLong(currentPeriod + t1));
                    gd11x5.setKj(t2.replace(" ", "-"));
                    gd11x5Service.save(gd11x5);
                }

                if (StringUtils.isNotEmpty(t3) && StringUtils.isNotEmpty(t4)) {
                    gd11x5 = new Gd11x5();
                    gd11x5.setQs(Long.parseLong(currentPeriod + t3));
                    gd11x5.setKj(t4.replace(" ", "-"));
                    gd11x5Service.save(gd11x5);
                }

                if (StringUtils.isNotEmpty(t5) && StringUtils.isNotEmpty(t6)) {
                    gd11x5 = new Gd11x5();
                    gd11x5.setQs(Long.parseLong(currentPeriod + t5));
                    gd11x5.setKj(t6.replace(" ", "-"));
                    gd11x5Service.save(gd11x5);
                }

                if (StringUtils.isNotEmpty(t7) && StringUtils.isNotEmpty(t8.trim())) {
                    gd11x5 = new Gd11x5();
                    gd11x5.setQs(Long.parseLong(currentPeriod + t7));
                    gd11x5.setKj(t8.replace(" ", "-"));
                    gd11x5Service.save(gd11x5);
                }
            }
            isSucceed = true;
        } catch (Exception ex) {
            logger.error("任务名称 = [" + scheduleJob.getName() + "]" + ",上广东11选5从163彩票获取数据执行执行异常", ex);
        }
    }

}
