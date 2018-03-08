package com.tianyu.jty.jihua.web;

import com.google.common.collect.Maps;
import com.tianyu.jty.common.persistence.Page;
import com.tianyu.jty.common.persistence.PropertyFilter;
import com.tianyu.jty.common.utils.DateUtils;
import com.tianyu.jty.common.utils.Global;
import com.tianyu.jty.common.utils.ServletUtils;
import com.tianyu.jty.common.utils.StringUtils;
import com.tianyu.jty.common.web.BaseController;
import com.tianyu.jty.jihua.entity.WenZhang;
import com.tianyu.jty.jihua.enumeration.LotteryTypeEnum;
import com.tianyu.jty.jihua.service.WenZhangService;
import com.tianyu.jty.system.utils.IPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("plan/article")
public class ArticleController extends BaseController {
    @Autowired
    private WenZhangService wenZhangService;

    @RequestMapping(value = "/json.our", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> our(HttpServletRequest request, HttpServletResponse response) {
        String whiteIp = Global.getConfig("whiteIP");
        String ip = IPUtil.getIpAddress(request);
        if (StringUtils.isNotEmpty(whiteIp) && !whiteIp.contains(ip)) {
            return null;
        }

        Page<WenZhang> page = getPage(request);
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);

        page = wenZhangService.search(page, filters);
        return getEasyUIData(page);
    }


    @RequestMapping(value = "/qun.our", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> qun(HttpServletRequest request, HttpServletResponse response) {
        Map result = Maps.newHashMap();
        result.put("qunUrl", Global.getConfig("qunUrl"));
        result.put("qunHao", Global.getConfig("qunHao"));
        return result;
    }

    private void cleanWenZhang(List<WenZhang> list) {
        if (null != list && list.size() > 0) {
            for (WenZhang wenZhang : list) {
                wenZhang.setContent("");
            }
        }
    }

    @RequestMapping(value = "/pk10/json.front", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> pk10List(HttpServletRequest request, HttpServletResponse response) {
        ServletUtils.setAccessControlAllow(response);
        Page<WenZhang> page = getPage(request);
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        filters.add(new PropertyFilter("EQS_lotteryType", LotteryTypeEnum.PK10.getDescription()));
        page = wenZhangService.search(page, filters);
        cleanWenZhang(page.getResult());
        return getEasyUIData(page);
    }


    @RequestMapping(value = "/jsk3/json.front", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> jsk3List(HttpServletRequest request, HttpServletResponse response) {
        ServletUtils.setAccessControlAllow(response);
        Page<WenZhang> page = getPage(request);
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        filters.add(new PropertyFilter("EQS_lotteryType", LotteryTypeEnum.JSK3.getDescription()));
        page = wenZhangService.search(page, filters);
        return getEasyUIData(page);
    }


    @RequestMapping(value = "/gd11x5/json.front", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> gd11x5List(HttpServletRequest request, HttpServletResponse response) {
        ServletUtils.setAccessControlAllow(response);
        Page<WenZhang> page = getPage(request);
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        filters.add(new PropertyFilter("EQS_lotteryType", LotteryTypeEnum.GD11X5.getDescription()));
        page = wenZhangService.search(page, filters);
        return getEasyUIData(page);
    }


    @RequestMapping(value = "/gdkl10/json.front", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> gdkl10List(HttpServletRequest request, HttpServletResponse response) {
        ServletUtils.setAccessControlAllow(response);
        Page<WenZhang> page = getPage(request);
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        filters.add(new PropertyFilter("EQS_lotteryType", LotteryTypeEnum.GDKL10.getDescription()));
        page = wenZhangService.search(page, filters);
        return getEasyUIData(page);
    }


    @RequestMapping(value = "/ssc/json.front", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> sscList(HttpServletRequest request, HttpServletResponse response) {
        ServletUtils.setAccessControlAllow(response);
        Page<WenZhang> page = getPage(request);
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        filters.add(new PropertyFilter("EQS_lotteryType", LotteryTypeEnum.SSC.getDescription()));
        page = wenZhangService.search(page, filters);
        return getEasyUIData(page);
    }

    @RequestMapping(value = "/show/{id}.front", method = RequestMethod.POST)
    @ResponseBody
    public Object show(@PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response) {
        ServletUtils.setAccessControlAllow(response);
        WenZhang wenZhang = wenZhangService.get(id);
        return pack(wenZhang);
    }

    @RequestMapping(value = "/lei/{id}.front", method = RequestMethod.POST)
    @ResponseBody
    public Object lei(@PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response) {
        ServletUtils.setAccessControlAllow(response);
        WenZhang wenZhang = wenZhangService.findByLeiId(id);
        return pack(wenZhang);
    }

    private Map pack(WenZhang wenZhang) {
        Map<String, String> result = Maps.newHashMap();
        result.put("title", "");
        result.put("content", "");
        result.put("createtime", "");


        if (null != wenZhang) {
            result.put("title", wenZhang.getTitle());
            result.put("content", wenZhang.getContent());
            if (null != wenZhang.getCreateDate()) {
                result.put("createtime", DateUtils.formatDateTime(DateUtils.addSeconds(wenZhang.getCreateDate(), -30)));
            }
            String planDesc = "";
            switch (LotteryTypeEnum.valueFrom(wenZhang.getLotteryType())) {
                case SSC: {
                    planDesc = "<p>\n" +
                            "\t第五球（<span style=\"color:#ff0000;\">个位</span>）：下注的一个或者多个号码与开奖结果的倒数第一个球出现一致的即视为中奖；</p>\n" +
                            "<p>\n" +
                            "\t第四球（<span style=\"color:#ff0000;\">十位</span>）：下注的一个或者多个号码与开奖结果的倒数第二个球出现一致的即视为中奖；</p>\n" +
                            "<p>\n" +
                            "\t第三球（<span style=\"color:#ff0000;\">百位</span>）：下注的一个或者多个号码与开奖结果的倒数第三个球出现一致的即视为中奖；</p>\n" +
                            "<p>\n" +
                            "\t第二球（<span style=\"color:#ff0000;\">千位</span>）：下注的一个或者多个号码与开奖结果的前面第二个球出现一致的即视为中奖；</p>\n" +
                            "<p>\n" +
                            "\t第一球（<span style=\"color:#ff0000;\">万位</span>）：下注的一个或者多个号码与开奖结果的前面第一个球出现一致的即视为中奖；</p>\n" +
                            "<p>\n" +
                            "\t重庆时时彩，<span style=\"color:#ff0000;\">五星定位</span>：下注的一个或者多个号码有出现在开奖结果的任何一个位置即视为中奖，同一个号码开出多个位置，即奖金翻倍；</p>\n" +
                            "</p>";
                    break;
                }
                case PK10: {
                    planDesc = "<p>\n" +
                            "\t北京赛车(PK10)：<span style=\"color:#ff0000;\">第一名</span>到<span style=\"color:#ff0000;\">第十名</span>。根据计划下注到指定名次，每次下注一个或多个车号，开奖结果有开出指定名次和投注车号相对应即视为中奖；</p>\n" +
                            "</p>";
                    break;
                }
                case GD11X5:
                    planDesc = "<p>\n" +
                            "\t广东11选5：<span style=\"color:#ff0000;\">1球</span>-<span style=\"color:#ff0000;\">5球</span>，第一球到第五球。根据计划下注到指定位置，每次下注一个或多个球号，开奖结果有开出指定位置和投注球号即视为中奖；</p>\n" +
                            "<p>\n" +
                            "\t广东11选5：任选<span style=\"color:#ff0000;\">一中一</span>。根据计划球号下注到&ldquo;一中一&rdquo;位置，所选下注号码与开奖结果的5个号码中任意1个相同，即视为中奖；</p>\n" +
                            "<p>\n" +
                            "\t广东11选5：任选<span style=\"color:#ff0000;\">二中二</span>。根据计划球号下注到&ldquo;二中二&rdquo;位置，所选下注号码与开奖结果的5个号码中任意2个相同，即视为中奖；</p>\n" +
                            "</p>";
                    break;
                case JSK3:
                    planDesc = "<p>\n" +
                            "\t江苏快三：<span style=\"color:#ff0000;\">独胆 &nbsp;</span>根据计划下注，每次下注一个或者多个号码。开奖结果有开出下注的指定号码，即视为中奖；</p>\n" +
                            "<p>\n" +
                            "\t江苏快三：<font color=\"#ff0000\">两连&nbsp;</font>&nbsp;根据计划下注，每次下注一组或多组组合号码（两个号码为一个组合）。开奖结果有开出下注组合的任意一个，即视为中奖；</p>\n" +
                            "</p>";
                    break;
                case GDKL10:
                    planDesc = "<div>\n" +
                            "\t广东快乐十分，<span style=\"color:#ff0000;\">任选二中二</span>，根据计划球号下注到&ldquo;任选二&rdquo;位置，所选下注号码与开奖结果8个球中任意出现2个相同，即视为中奖；</div>\n" +
                            "</p>\n" +
                            "\t  </div>";
                    break;
            }
            result.put("planDesc", planDesc);
        }
        result.put("qunUrl", Global.getConfig("qunUrl"));
        result.put("qunHao", Global.getConfig("qunHao"));
        return result;
    }

}
