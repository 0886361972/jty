package com.tianyu.jty.jihua.web;

import com.tianyu.jty.common.persistence.Page;
import com.tianyu.jty.common.persistence.PropertyFilter;
import com.tianyu.jty.common.utils.Global;
import com.tianyu.jty.common.utils.ServletUtils;
import com.tianyu.jty.common.utils.StringUtils;
import com.tianyu.jty.common.web.BaseController;
import com.tianyu.jty.jihua.entity.LieBiao;
import com.tianyu.jty.jihua.enumeration.LotteryTypeEnum;
import com.tianyu.jty.jihua.service.LieBiaoService;
import com.tianyu.jty.system.utils.IPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by hzhonglog on 17/5/16.
 */
@Controller
@RequestMapping("plan/list")
public class PlanListController extends BaseController {
    @Autowired
    private LieBiaoService lieBiaoService;

    @RequestMapping(value = "/json.our")
    @ResponseBody
    public Map<String, Object> our(HttpServletRequest request, HttpServletResponse response) {
        String whiteIp = Global.getConfig("whiteIP");
        String ip = IPUtil.getIpAddress(request);
        if (StringUtils.isNotEmpty(whiteIp) && !whiteIp.contains(ip)) {
            return null;
        }
        Page<LieBiao> page = getPage(request);
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        page = lieBiaoService.search(page, filters);
        return getEasyUIData(page);
    }


    @RequestMapping(value = "/pk10/json.front", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> pk10List(HttpServletRequest request, HttpServletResponse response) {
        ServletUtils.setAccessControlAllow(response);
        Page<LieBiao> page = getPage(request);
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        filters.add(new PropertyFilter("EQS_lotteryType", LotteryTypeEnum.PK10.getDescription()));
        page = lieBiaoService.search(page, filters);
        return getEasyUIData(page);
    }


    @RequestMapping(value = "/jsk3/json.front", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> jsk3List(HttpServletRequest request, HttpServletResponse response) {
        ServletUtils.setAccessControlAllow(response);
        Page<LieBiao> page = getPage(request);
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        filters.add(new PropertyFilter("EQS_lotteryType", LotteryTypeEnum.JSK3.getDescription()));
        page = lieBiaoService.search(page, filters);
        return getEasyUIData(page);
    }


    @RequestMapping(value = "/gd11x5/json.front", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> gd11x5List(HttpServletRequest request, HttpServletResponse response) {
        ServletUtils.setAccessControlAllow(response);
        Page<LieBiao> page = getPage(request);
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        filters.add(new PropertyFilter("EQS_lotteryType", LotteryTypeEnum.GD11X5.getDescription()));
        page = lieBiaoService.search(page, filters);
        return getEasyUIData(page);
    }


    @RequestMapping(value = "/gdkl10/json.front", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> gdkl10List(HttpServletRequest request, HttpServletResponse response) {
        ServletUtils.setAccessControlAllow(response);
        Page<LieBiao> page = getPage(request);
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        filters.add(new PropertyFilter("EQS_lotteryType", LotteryTypeEnum.GDKL10.getDescription()));
        page = lieBiaoService.search(page, filters);
        return getEasyUIData(page);
    }


    @RequestMapping(value = "/ssc/json.front", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> sscList(HttpServletRequest request, HttpServletResponse response) {
        ServletUtils.setAccessControlAllow(response);
        Page<LieBiao> page = getPage(request);
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        filters.add(new PropertyFilter("EQS_lotteryType", LotteryTypeEnum.SSC.getDescription()));
        page = lieBiaoService.search(page, filters);
        return getEasyUIData(page);
    }


}
