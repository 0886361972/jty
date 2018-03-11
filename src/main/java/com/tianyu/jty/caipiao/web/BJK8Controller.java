package com.tianyu.jty.caipiao.web;

import com.tianyu.jty.caipiao.entity.BJK8;
import com.tianyu.jty.caipiao.entity.CPK;
import com.tianyu.jty.caipiao.service.BJK8Service;
import com.tianyu.jty.common.persistence.Page;
import com.tianyu.jty.common.persistence.PropertyFilter;
import com.tianyu.jty.common.utils.DateUtils;
import com.tianyu.jty.common.utils.Global;

import com.tianyu.jty.common.utils.StringUtils;
import com.tianyu.jty.common.web.BaseController;
import com.tianyu.jty.exception.CaipiaoException;
import com.tianyu.jty.system.entity.User;
import com.tianyu.jty.system.utils.IPUtil;
import com.tianyu.jty.system.utils.UserUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.ObjectNotFoundException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.*;

/**
 * Created by hl on 2016/7/25.
 */
@Controller
@RequestMapping("caipiao/bjk8")
public class BJK8Controller extends BaseController {

    @Autowired
    private BJK8Service bjk8Service;

    /**
     * 默认页面
     */
    @RequestMapping(method = RequestMethod.GET)
    public String list(HttpServletRequest request, Model model) {
        StringBuilder result = new StringBuilder();

        try {
            Page<BJK8> page = getKjPage();
            List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
            page = bjk8Service.search(page, filters);
            List<BJK8> list = page.getResult();

            String qs = "";
            String kj = "";
            for (BJK8 c : list) {
                kj = c.getKj().replace(",", ".").replace("-", ".");
                kj = kj.substring(0, kj.lastIndexOf("."));
                qs = String.valueOf(c.getQs());
                result.append(qs);
                result.append(" ");
                result.append(kj);
                result.append("<br/>\r\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        model.addAttribute("result", result.toString());
        return "caipiao/show";
    }


    @RequestMapping(value = {"cpk", ""})
    public String cpk(Model model) {
        StringBuilder sb = new StringBuilder();
        try {
            String url = BJK8.URL_FROM_CPK;
            String resultJson = Global.getResultFromHttp(url);
            List<CPK> list = Global.String2CPK(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                BJK8 bjk8 = new BJK8();
                bjk8.setKj(cpk.getNumber().replace(",", "-"));
                bjk8.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                bjk8.setQs(Long.parseLong(cpk.getKey()));
                bjk8Service.save(bjk8);


                sb.append(cpk.getKey() + "   ");
                sb.append(cpk.getNumber() + "    ");
                sb.append(cpk.getDateline() + "<br/>");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sb.append(ex.getMessage());
        }

        model.addAttribute("result", sb.toString());
        return "caipiao/bjk8";
    }

    @RequestMapping(value = {"opencai", ""})
    public String opencai(Model model) {
        StringBuilder sb = new StringBuilder();
        try {
            String url = BJK8.URL_FROM_OPENCAI;
            String resultJson = Global.getOpenCai(url);
            List<CPK> list = Global.String2OpenCai(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                BJK8 bjk8 = new BJK8();
                bjk8.setKj(cpk.getNumber().replace(",", "-").replace("+", "-"));
                bjk8.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                bjk8.setQs(Long.parseLong(cpk.getKey()));
                bjk8Service.save(bjk8);

                sb.append(cpk.getKey() + "   ");
                sb.append(cpk.getNumber() + "    ");
                sb.append(cpk.getDateline() + "<br/>");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sb.append(ex.getMessage());
        }
        model.addAttribute("result", sb.toString());
        return "caipiao/bjk8";
    }

    @RequestMapping(value = {"haoservice", ""})
    public String haoservice(Model model) {
        StringBuilder sb = new StringBuilder();
        try {
            String url = BJK8.URL_FROM_HAOSERVICE;
            String resultJson = Global.getHaoService(url);
            List<CPK> list = Global.String2HaoService(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                BJK8 bjk8 = new BJK8();
                bjk8.setKj(cpk.getNumber().replace(",", "-").replace("+", "-"));
                bjk8.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                bjk8.setQs(Long.parseLong(cpk.getKey()));
                bjk8Service.save(bjk8);

                sb.append(cpk.getKey() + "   ");
                sb.append(cpk.getNumber() + "    ");
                sb.append(cpk.getDateline() + "<br/>");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sb.append(ex.getMessage());
        }
        model.addAttribute("result", sb.toString());
        return "caipiao/bjk8";
    }


    @RequestMapping(value = {"bj", ""})
    public String test(Model model) {
        StringBuilder sb = new StringBuilder();
        try {
            Document doc = Jsoup.connect(BJK8.URL_FROM_BJ).timeout(5000).get();
            Elements trs = doc.select("table.tb").select("tr");
            if (!trs.isEmpty()) {
                for (int i = 0; i < trs.size(); i++) {
                    Elements tds = trs.get(i).select("td");
                    if (!tds.isEmpty()) {
                        sb.append(tds.get(0) + "  ");
                        sb.append(tds.get(1));
                        sb.append("</br>");

                        BJK8 bjk8 = new BJK8();
                        bjk8.setQs(Long.parseLong(tds.get(0).text()));
                        bjk8.setKj(tds.get(1).text().replace(",", "-"));
                        bjk8Service.save(bjk8);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sb.append(ex.getMessage());
        }

        model.addAttribute("result", sb.toString());
        return "caipiao/bjk8";
    }


    @RequestMapping(value = "json", method = RequestMethod.GET)
    @ResponseBody
    public Object getData(HttpServletRequest request) {
        Page<BJK8> page = getKjPage();
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        page = bjk8Service.search(page, filters);
        return page.getResult();
    }

    @RequiresPermissions("bjk8:view")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("requestUrl", "caipiao/bjk8/list/json");
        model.addAttribute("openUrl", "caipiao/bjk8/drawNum");
        model.addAttribute("detailUrl", "caipiao/bjk8/detail");
        return "caipiao/list";
    }


    @RequiresPermissions("bjk8:view")
    @RequestMapping(value = "list/json", method = RequestMethod.GET)
    @ResponseBody
    public Object listJson(HttpServletRequest request) {
        Page<BJK8> page = getPage(request);
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        page = bjk8Service.search(page, filters);
        return getEasyUIData(page);
    }

    //bjk8:drawnumber

    @RequiresPermissions("bjk8:drawnumber")
    @RequestMapping(value = "drawNum", method = RequestMethod.GET)
    public String opennumber(Model model) {

        Long maxQS = bjk8Service.findMaxQS();
        model.addAttribute("next", maxQS + 1);
        return "caipiao/bjk8Form";
    }

    @RequiresPermissions("bjk8:drawnumber")
    @ResponseBody
    @RequestMapping(value = "drawNum", method = RequestMethod.POST)
    public String opennumbersave(HttpServletRequest request) {
        //期数
        long qs = NumberUtils.toLong(request.getParameter("qs"), -1);
        //List<Integer> qius = new LinkedList<>();
        Set<Integer> qius = new HashSet();
        for (int i = 1; i <= 20; i++) {
            int qiu = NumberUtils.toInt(request.getParameter("qiu" + i), -1);
            if (qiu == -1) throw new CaipiaoException("第" + i + "球不能为空");
            if (!(qiu >= 1 && qiu <= 80)) throw new CaipiaoException("北京快8开奖号码必须是1到80之间");
            boolean isRepeat = qius.add(qiu);
            if (!isRepeat) throw new CaipiaoException("第" + i + "球已存在 请重新输入");
        }


        int flagInt = NumberUtils.toInt(request.getParameter("flag"));

        Boolean flag = BooleanUtils.toBoolean(flagInt);

        Integer[] qiusArray = qius.toArray(new Integer[0]);

        List<Integer> qiusList = Arrays.asList(qiusArray);
        Collections.sort(qiusList);

        String kj = StringUtils.join(qiusList, '-');

        String operator = UserUtil.getCurrentUser().getLoginName();
        String operatorIp = IPUtil.getIpAddress(request);
        BJK8 bjk8 = new BJK8();
        int id = NumberUtils.toInt(request.getParameter("id"), -1);
        if (id != -1) {
            bjk8.setId(id);
        }

        bjk8.setQs(qs);
        bjk8.setOperator(operator);
        bjk8.setOperatorIp(operatorIp);
        bjk8.setKj(kj);
        bjk8.setFlag(flag);
        try {
            bjk8Service.save(bjk8);
        } catch (DataIntegrityViolationException e) {
            throw new CaipiaoException("期数已存在 请重新输入");
        }

        return "success";
    }

    @RequiresPermissions("bjk8:drawnumber")
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public String detail(HttpServletRequest request, Model model) {

        Integer id = NumberUtils.toInt(request.getParameter("id"), -1);
        if (id == -1) throw new CaipiaoException("请选择一条数据");
        BJK8 bjk8 = bjk8Service.get(id);

        Map<String, Object> result = new HashMap<>();
        result.put("id", bjk8.getId());
        result.put("flag", bjk8.isFlag());
        result.put("qs", bjk8.getQs());
        String[] qius = bjk8.getKj().split("-");
        int i = 0;
        for (String qiu : qius) {
            result.put("qiu" + (++i), qiu);
        }
        model.addAttribute("bjk8", result);
        return "caipiao/bjk8Form";
    }

    @RequiresPermissions("bjk8:drawnumber")
    @RequestMapping(value = "delOne", method = RequestMethod.POST)
    @ResponseBody
    public String delOne(HttpServletRequest request) {

        Long id = NumberUtils.toLong(request.getParameter("id"), -1);
        if (id == -1) throw new CaipiaoException("请选择一条数据");
        try {
            bjk8Service.delete(id.intValue());
        }catch (ObjectNotFoundException e){
            throw new CaipiaoException("该期数不存在");
        }

        return "success";
    }


}
