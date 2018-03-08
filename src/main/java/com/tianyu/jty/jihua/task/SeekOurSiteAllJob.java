package com.tianyu.jty.jihua.task;

import com.tianyu.jty.common.mapper.JsonMapper;
import com.tianyu.jty.common.utils.Global;
import com.tianyu.jty.common.utils.StringUtils;
import com.tianyu.jty.jihua.service.LieBiaoService;
import com.tianyu.jty.jihua.service.WenZhangService;
import com.tianyu.jty.system.entity.ScheduleJob;
import com.tianyu.jty.system.utils.HttpUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by hzhonglog on 17/5/17.
 */
@DisallowConcurrentExecution
public class SeekOurSiteAllJob implements Job {
    private final static Logger logger = LoggerFactory.getLogger(SeekOurSiteAllJob.class);


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
        String listUrl = Global.getConfig("oursite.list.url");
        String detailUrl = Global.getConfig("oursite.detail.url");
        if (StringUtils.isNotEmpty(listUrl) && StringUtils.isNotEmpty(detailUrl)) {

        }
    }

    private void getList(LieBiaoService service, String url) {
        String result = HttpUtils.sendGet(url);
    }

    private void getDetail(WenZhangService service, String url) {
        String result = HttpUtils.sendGet(url);
        if (StringUtils.isNotEmpty(result)) {
            try {
                JsonMapper jsonMapper = new JsonMapper();
                Map map = jsonMapper.fromJson(result, Map.class);
                if (null != map && map.size() > 0) {

                }
            } catch (Exception ex) {
                logger.error("[从自己的站点读取文章详细数据失败]->", ex);
            }
        }
    }


}
