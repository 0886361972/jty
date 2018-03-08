package com.tianyu.jty.caipiao.task;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.tianyu.jty.caipiao.entity.CPK;
import com.tianyu.jty.caipiao.entity.FC3D;
import com.tianyu.jty.caipiao.service.FC3DService;
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
 * 福彩3D
 */
@DisallowConcurrentExecution
public class FC3DJob implements Job {

    private final static Logger logger = LoggerFactory.getLogger(FC3DJob.class);
    private static boolean isSucceed = false;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        if (!Global.getLotteryCaiji()) return;
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");

        FC3DService fc3DService = ContextUtils.getBean(FC3DService.class);
        isSucceed = false;

        fromCPK(fc3DService,scheduleJob);
        fromOpencai(fc3DService,scheduleJob);

        logger.info("任务名称 = [" + scheduleJob.getName() + "]" + " 在 " + dateFormat.format(new Date()) + " 时运行");
    }

    public void fromCPK(FC3DService fc3DService, ScheduleJob scheduleJob) {
        try {
            String url = FC3D.URL_FROM_CPK;
            if (StringUtils.isEmpty(url)) return;
            String resultJson = Global.getResultFromHttp(url);
            List<CPK> list = Global.String2CPK(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                FC3D fc3D = new FC3D();
                fc3D.setKj(cpk.getNumber().replace(",","-"));
                fc3D.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                fc3D.setQs(Long.parseLong(cpk.getKey()));
                fc3DService.save(fc3D);
                isSucceed = true;
            }
        } catch (Exception ex) {
            logger.error("任务名称 = [" + scheduleJob.getName() + "]" + ",福彩3D从彩控获取数据执行执行异常", ex);
        }
    }
    public void fromOpencai(FC3DService fc3DService, ScheduleJob scheduleJob) {
        try {
            String url = FC3D.URL_FROM_OPENCAI;
            if (StringUtils.isEmpty(url)) return;
            String resultJson = Global.getOpenCai(url);
            List<CPK> list = Global.String2OpenCai(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                FC3D fc3D = new FC3D();
                fc3D.setKj(cpk.getNumber().replace(",","-"));
                fc3D.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                fc3D.setQs(Long.parseLong(cpk.getKey()));
                fc3DService.save(fc3D);
                isSucceed = true;
            }
        } catch (Exception ex) {
            logger.error("任务名称 = [" + scheduleJob.getName() + "]" + ",福彩3D从开采网获取数据执行执行异常", ex);

        }
    }

    private void fromBaidu(FC3DService fc3DService, ScheduleJob scheduleJob) {
        try {
            WebClient wc = new WebClient();
            wc.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true
            wc.getOptions().setCssEnabled(false); //禁用css支持
            wc.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常
            wc.getOptions().setTimeout(10000); //设置连接超时时间 ，这里是10S。如果为0，则无限期等待
            HtmlPage page = wc.getPage(FC3D.URL_FROM_BAIDU);
            String pageXml = page.asXml(); //以xml的形式获取响应文本

            Document doc = Jsoup.parse(pageXml, "http://baidu.lecai.com");
            Elements trs = doc.select("table.historylist").select("tr");
            if (!trs.isEmpty()) {
                for (int i = 1; i < trs.size(); i++) {
                    Elements tds = trs.get(i).select("td");
                    if (!tds.isEmpty()) {
                        Elements aHref = tds.get(0).select("a");
                        if (!aHref.isEmpty()) {
                            String qs = aHref.get(0).text();
                            String kj = "";
                            Elements ems = tds.get(2).select("em");
                            for (int e = 0; e < ems.size(); e++) {
                                if ("".equals(kj)) {
                                    kj = ems.get(e).text();
                                } else {
                                    kj = kj + "-" + ems.get(e).text();
                                }
                            }
                            FC3D fc3D = new FC3D();
                            fc3D.setQs(Long.parseLong(qs));
                            fc3D.setKj(kj);
                            fc3DService.save(fc3D);
                        }
                        isSucceed = true;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("任务名称 = [" + scheduleJob.getName() + "]" + ",福彩3D从百度彩票获取数据执行执行异常", e);
        }
    }
}
