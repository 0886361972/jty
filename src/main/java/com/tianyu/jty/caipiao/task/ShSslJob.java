package com.tianyu.jty.caipiao.task;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.tianyu.jty.caipiao.entity.CPK;
import com.tianyu.jty.caipiao.entity.ShSsl;
import com.tianyu.jty.caipiao.entity.SyyDJ;
import com.tianyu.jty.caipiao.service.CqSscService;
import com.tianyu.jty.caipiao.service.ShSslService;
import com.tianyu.jty.common.utils.ContextUtils;
import com.tianyu.jty.common.utils.DateUtils;
import com.tianyu.jty.common.utils.Global;
import com.tianyu.jty.system.entity.ScheduleJob;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 上海时时乐
 * Created by hl on 2016/8/7.
 */
@DisallowConcurrentExecution
public class ShSslJob implements Job {
    private final static Logger logger = LoggerFactory.getLogger(ShSslJob.class);
    private static boolean isSucceed = false;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        if (!Global.getLotteryCaiji()) return;
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");


        ShSslService shSslService = ContextUtils.getBean(ShSslService.class);
        isSucceed = false;

        //fromSH(shSslService, scheduleJob);
        fromCPK(shSslService, scheduleJob);
        fromOpencai(shSslService,scheduleJob);
        fromHaoservice(shSslService,scheduleJob);
        fromWapus(shSslService,scheduleJob);

        logger.info("任务名称 = [" + scheduleJob.getName() + "]" + " 在 " + dateFormat.format(new Date()) + " 时运行");
    }

    public void fromOpencai(ShSslService shSslService, ScheduleJob scheduleJob) {
        try {
            String url = ShSsl.URL_FROM_OPENCAI;
            if (StringUtils.isEmpty(url)) return;
            String resultJson = Global.getOpenCai(url);
            List<CPK> list = Global.String2OpenCai(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                ShSsl shSsl = new ShSsl();
                shSsl.setKj(cpk.getNumber().replace(",","-"));
                shSsl.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                shSsl.setQs(Long.parseLong(cpk.getKey()));

                shSslService.save(shSsl);
                isSucceed=true;

            }
        } catch (Exception ex) {
            logger.error("任务名称 = [" + scheduleJob.getName() + "]" + ",上海时时乐从开采数据执行执行异常", ex);
        }
    }

    public void fromHaoservice(ShSslService shSslService, ScheduleJob scheduleJob) {
        try {
            String url = ShSsl.URL_FROM_HAOSERVICE;
            if (StringUtils.isEmpty(url)) return;
            String resultJson = Global.getHaoService(url);
            List<CPK> list = Global.String2HaoService(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                ShSsl shSsl = new ShSsl();
                shSsl.setKj(cpk.getNumber().replace(",","-"));
                shSsl.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                shSsl.setQs(Long.parseLong(cpk.getKey()));
                shSsl.setKj(shSsl.getKj().substring(0,5));
                shSslService.save(shSsl);
                isSucceed=true;

            }
        } catch (Exception ex) {
            logger.error("任务名称 = [" + scheduleJob.getName() + "]" + ",上海时时乐从haoservice执行执行异常", ex);
        }
    }


    public void fromWapus(ShSslService shSslService, ScheduleJob scheduleJob) {
        try {
            shSslService.fromWapUs();
        } catch (Exception ex) {
            logger.error("任务名称 = [" + scheduleJob.getName() + "]" + ",上海时时乐从WAPUS取数据执行执行异常", ex);
        }
    }


    public void fromCPK(ShSslService shSslService, ScheduleJob scheduleJob) {
        try {
            String url = ShSsl.URL_FROM_CPK;
            if (StringUtils.isEmpty(url)) return;
            String resultJson = Global.getResultFromHttp(url);
            List<CPK> list = Global.String2CPK(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                ShSsl shSsl = new ShSsl();
                shSsl.setKj(cpk.getNumber().replace(",","-"));
                shSsl.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                shSsl.setQs(Long.parseLong(cpk.getKey()));
                shSslService.save(shSsl);
                isSucceed = true;
            }
        } catch (Exception ex) {
            logger.error("任务名称 = [" + scheduleJob.getName() + "]" + ",上海时时乐从彩控获取数据执行执行异常", ex);
        }
    }

    public void fromSH(ShSslService shSslService, ScheduleJob scheduleJob) {
        try {
            WebClient wc = new WebClient();
            wc.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true
            wc.getOptions().setCssEnabled(false); //禁用css支持
            wc.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常
            wc.getOptions().setTimeout(30000); //设置连接超时时间 ，这里是10S。如果为0，则无限期等待
            HtmlPage page = wc.getPage(ShSsl.URL_FROM_SH);
            String pageXml = page.asXml(); //以xml的形式获取响应文本

            Document doc = Jsoup.parse(pageXml, SyyDJ.URL_FROM_SD);
            Elements table = doc.select("div.lm2block1");
            Elements divs = table.select("div.fl");
            List<Element> tmp_list = new LinkedList<>();
            if (!divs.isEmpty()) {
                for (int i = 0; i < divs.size(); i++) {
                    if (tmp_list.size() == 3) {
                        ShSsl shSsl = new ShSsl();
                        shSsl.setAddTime(new Date());
                        shSsl.setQs(Long.parseLong(tmp_list.get(0).text().replace("-", "")));

                        Elements hm = tmp_list.get(2).select("span");
                        shSsl.setKj(hm.get(0).text() + "-" + hm.get(1).text() + "-" + hm.get(2).text());
                        shSslService.save(shSsl);
                        tmp_list.clear();
                        i = i - 1;
                    } else {
                        tmp_list.add(divs.get(i));
                    }
                }
            }
            isSucceed = true;
        } catch (Exception ex) {
            logger.error("任务名称 = [" + scheduleJob.getName() + "]" + ",上海时时乐从官方获取数据执行执行异常", ex);
        }
    }
}
