package com.tianyu.jty.caipiao.task;

import com.tianyu.jty.caipiao.entity.Xy28;
import com.tianyu.jty.caipiao.service.Xy28Service;
import com.tianyu.jty.common.utils.ContextUtils;
import com.tianyu.jty.common.utils.Global;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by hzhonglong on 2016/9/14.
 */
@DisallowConcurrentExecution
public class Xy28Job implements Job {

    private final static Logger logger = LoggerFactory.getLogger(Xy28Job.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        if (!Global.getLotteryCaiji()) return;
        Xy28Service xy28Service = ContextUtils.getBean(Xy28Service.class);
        List<Xy28> result = xy28Service.getXy28List();
        if (result==null){
            result=xy28Service.login();
        }

        if (result!=null){
            for(Xy28 xy28:result){
                xy28Service.save(xy28);
            }
        }
    }
}
