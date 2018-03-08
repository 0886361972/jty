package com.tianyu.jty.caipiao.task;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.tianyu.jty.caipiao.entity.CPK;
import com.tianyu.jty.caipiao.entity.CQKL10;
import com.tianyu.jty.caipiao.service.CqKL10Service;
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
 * Created by hl on 2016/7/24.
 */
@DisallowConcurrentExecution
public class CqKL10Job implements Job {

    private final static Logger logger = LoggerFactory.getLogger(CqKL10Job.class);
    private static boolean isSucceed = false;


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        if (!Global.getLotteryCaiji()) return;
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");

        CqKL10Service cqKL10Service = ContextUtils.getBean(CqKL10Service.class);
        isSucceed = false;
        fromCPK(cqKL10Service, scheduleJob);
        fromOpencai(cqKL10Service, scheduleJob);
        fromHaoservice(cqKL10Service,scheduleJob);
        logger.info("任务名称 = [" + scheduleJob.getName() + "]" + " 在 " + dateFormat.format(new Date()) + " 时运行");
    }

    public void fromCPK(CqKL10Service cqKL10Service, ScheduleJob scheduleJob) {
        try {
            String url = CQKL10.URL_FROM_CPK;
            if (StringUtils.isEmpty(url)) return;
            String resultJson = Global.getResultFromHttp(url);
            List<CPK> list = Global.String2CPK(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                CQKL10 cqkl10 = new CQKL10();
                cqkl10.setKj(cpk.getNumber().replace(",", "-"));
                cqkl10.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                cqkl10.setQs(Long.parseLong(cpk.getKey()));
                cqKL10Service.save(cqkl10);
                isSucceed = true;
            }
        } catch (Exception ex) {
            logger.error("任务名称 = [" + scheduleJob.getName() + "]" + ",重庆快乐十分从彩票控获取数据执行执行异常", ex);
        }
    }

    public void fromOpencai(CqKL10Service cqKL10Service, ScheduleJob scheduleJob) {
        try {
            String url = CQKL10.URL_FROM_OPENCAI;
            if (StringUtils.isEmpty(url)) return;
            String resultJson = Global.getOpenCai(url);
            List<CPK> list = Global.String2OpenCai(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                CQKL10 cqkl10 = new CQKL10();
                cqkl10.setKj(cpk.getNumber().replace(",", "-"));
                cqkl10.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                cqkl10.setQs(Global.sub20ForQs(cpk.getKey()));
                cqKL10Service.save(cqkl10);
                isSucceed = true;
            }
        } catch (Exception ex) {
            logger.error("任务名称 = [" + scheduleJob.getName() + "]" + ",重庆快乐十分从开采网获取数据执行执行异常", ex);

        }
    }

    public void fromHaoservice(CqKL10Service cqKL10Service, ScheduleJob scheduleJob) {
        try {
            String url = CQKL10.URL_FROM_HAOSERVICE;
            if (StringUtils.isEmpty(url)) return;
            String resultJson = Global.getHaoService(url);
            List<CPK> list = Global.String2HaoService(resultJson);;
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                CQKL10 cqkl10 = new CQKL10();
                cqkl10.setKj(cpk.getNumber().replace(",", "-"));
                cqkl10.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                cqkl10.setQs(Global.sub20ForQs(cpk.getKey()));
                cqKL10Service.save(cqkl10);
                isSucceed = true;
            }
        } catch (Exception ex) {
            logger.error("任务名称 = [" + scheduleJob.getName() + "]" + ",重庆快乐十分从haoservice获取数据执行执行异常", ex);

        }
    }

    private void fromBaidu(CqKL10Service cqKL10Service, ScheduleJob scheduleJob) {
        try {
            WebClient wc = new WebClient();
            wc.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true
            wc.getOptions().setCssEnabled(false); //禁用css支持
            wc.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常
            wc.getOptions().setTimeout(10000); //设置连接超时时间 ，这里是10S。如果为0，则无限期等待
            HtmlPage page = wc.getPage(CQKL10.URL_FROM_BAIDU);
            String pageXml = page.asXml(); //以xml的形式获取响应文本

            Document doc = Jsoup.parse(pageXml, "http://baidu.lecai.com");
            Elements trs = doc.select("#jq_draw_list").select("tr");
            if (!trs.isEmpty()) {
                for (int i = trs.size() - 1; i > 0; i--) {
                    Elements tds = trs.get(i).select("td");
                    if (!tds.isEmpty()) {
                        CQKL10 cqkl10 = new CQKL10();
                        cqkl10.setQs(Long.parseLong(tds.get(0).text()));
                        cqkl10.setKj(tds.get(2).text().replace(",", "-"));
                        cqKL10Service.save(cqkl10);
                    }
                }
                isSucceed = true;
            }

        } catch (Exception ex) {
            logger.error("任务名称 = [" + scheduleJob.getName() + "]" + ",从百度彩票获取数据执行执行异常", ex);
        }
    }
}
