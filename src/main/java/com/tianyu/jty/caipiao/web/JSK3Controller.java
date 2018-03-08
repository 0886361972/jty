package com.tianyu.jty.caipiao.web;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.tianyu.jty.caipiao.entity.*;
import com.tianyu.jty.caipiao.service.JSK3Service;
import com.tianyu.jty.common.persistence.Page;
import com.tianyu.jty.common.persistence.PropertyFilter;
import com.tianyu.jty.common.utils.DateUtils;
import com.tianyu.jty.common.utils.Global;
import com.tianyu.jty.common.web.BaseController;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hl on 2016/7/25.
 */
@Controller
@RequestMapping("caipiao/jsk3")
public class JSK3Controller extends BaseController {
    @Autowired
    private JSK3Service jsk3Service;


    @RequestMapping(value = {"cpk", ""})
    public String cpk(Model model) {
        StringBuilder sb = new StringBuilder();
        try {
            String url = JSK3.URL_FROM_CPK;
            String resultJson = Global.getResultFromHttp(url);
            List<CPK> list = Global.String2CPK(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                JSK3 jsk3 = new JSK3();
                jsk3.setKj(cpk.getNumber().replace(",", "-"));
                jsk3.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                jsk3.setQs(Long.parseLong(cpk.getKey()));
                jsk3Service.save(jsk3);

                sb.append(cpk.getKey() + "   ");
                sb.append(cpk.getNumber() + "    ");
                sb.append(cpk.getDateline() + "<br/>");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sb.append(ex.getMessage());
        }

        model.addAttribute("result", sb.toString());
        return "caipiao/jsk3";
    }

    @RequestMapping(value = {"opencai", ""})
    public String opencai(Model model) {
        StringBuilder sb = new StringBuilder();
        try {
            String url = JSK3.URL_FROM_OPENCAI;
            String resultJson = Global.getOpenCai(url);
            List<CPK> list = Global.String2OpenCai(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                JSK3 jsk3 = new JSK3();
                jsk3.setKj(cpk.getNumber().replace(",","-"));
                jsk3.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                jsk3.setQs(Global.sub20ForQs(cpk.getKey()));
                jsk3Service.save(jsk3);

                sb.append(cpk.getKey() + "   ");
                sb.append(cpk.getNumber() + "    ");
                sb.append(cpk.getDateline() + "<br/>");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sb.append(ex.getMessage());
        }
        model.addAttribute("result", sb.toString());
        return "caipiao/jsk3";
    }

    @RequestMapping(value = {"haoservice", ""})
    public String haoservice(Model model) {
        StringBuilder sb = new StringBuilder();
        try {
            String url = JSK3.URL_FROM_HAOSERVICE;
            String resultJson = Global.getHaoService(url);
            List<CPK> list = Global.String2HaoService(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                JSK3 jsk3 = new JSK3();
                jsk3.setKj(cpk.getNumber().replace(",","-"));
                jsk3.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                jsk3.setQs(Global.sub20ForQs(cpk.getKey()));
                jsk3.setKj(jsk3.getKj().substring(0,5));
                jsk3Service.save(jsk3);

                sb.append(cpk.getKey() + "   ");
                sb.append(cpk.getNumber() + "    ");
                sb.append(cpk.getDateline() + "<br/>");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sb.append(ex.getMessage());
        }
        model.addAttribute("result", sb.toString());
        return "caipiao/jsk3";
    }


    @RequestMapping(value = {"bd", ""})
    public String fromBaiDu(Model model) {
        StringBuilder sb = new StringBuilder();
        try {
            WebClient wc = new WebClient();
            wc.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true
            wc.getOptions().setCssEnabled(false); //禁用css支持
            wc.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常
            wc.getOptions().setTimeout(10000); //设置连接超时时间 ，这里是10S。如果为0，则无限期等待
            HtmlPage page = wc.getPage(JSK3.URL_FROM_BAIDU);
            String pageXml = page.asXml(); //以xml的形式获取响应文本

            Document doc = Jsoup.parse(pageXml, "http://baidu.lecai.com");
            Elements trs = doc.select("#jq_draw_list").select("tr");
            if (!trs.isEmpty()) {
                for (int i = trs.size() - 1; i > 0; i--) {
                    Elements tds = trs.get(i).select("td");
                    if (!tds.isEmpty()) {
                        sb.append(tds.get(0).text() + "  ");
                        sb.append(tds.get(2).text().replace(",", "-"));
                        sb.append("</br>");

                        JSK3 jsk3 = new JSK3();
                        jsk3.setQs(Long.parseLong(tds.get(0).text()));
                        jsk3.setKj(tds.get(2).text().replace(",", "-"));
                        jsk3Service.save(jsk3);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            sb.append(e.getMessage());
        }
        model.addAttribute("result", sb.toString());
        return "caipiao/jsk3";
    }

    @RequestMapping(value = "json", method = RequestMethod.GET)
    @ResponseBody
    public Object getData(HttpServletRequest request) {
        Page<JSK3> page = getKjPage();
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        page = jsk3Service.search(page, filters);
        return page.getResult();
    }

    /**
     * 默认页面
     */
    @RequestMapping(method = RequestMethod.GET)
    public String list(HttpServletRequest request, Model model) {
        StringBuilder result = new StringBuilder();

        try {
            Page<JSK3> page = getKjPage();
            List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
            page = jsk3Service.search(page, filters);
            List<JSK3> list = page.getResult();

            String qs = "";
            for (JSK3 c : list) {
                qs = String.valueOf(c.getQs());
                if (!qs.startsWith("20")) {
                    qs = "20" + qs;
                }
                result.append(qs.substring(0, 8));
                result.append("-");
                result.append(qs.substring(8));
                result.append(" ");
                result.append(c.getKj().replace(",", ".").replace("-", "."));
                result.append("<br/>\r\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        model.addAttribute("result", result.toString());
        return "caipiao/show";
    }
}
