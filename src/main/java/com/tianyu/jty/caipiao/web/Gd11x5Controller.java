package com.tianyu.jty.caipiao.web;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.tianyu.jty.caipiao.entity.CPK;
import com.tianyu.jty.caipiao.entity.Gd11x5;
import com.tianyu.jty.caipiao.service.Gd11x5Service;
import com.tianyu.jty.common.persistence.Page;
import com.tianyu.jty.common.persistence.PropertyFilter;
import com.tianyu.jty.common.utils.DateUtils;
import com.tianyu.jty.common.utils.Global;
import com.tianyu.jty.common.utils.StringUtils;
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
@RequestMapping("caipiao/gd11x5")
public class Gd11x5Controller extends BaseController {

    @Autowired
    private Gd11x5Service gd11x5Service;

    @RequestMapping(value = {"163", ""})
    public String from163(Model model) {
        StringBuilder sb = new StringBuilder();
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
                    sb.append(gd11x5.getQs() + "   ");
                    sb.append(gd11x5.getKj() + "<br />");
                }


                if (StringUtils.isNotEmpty(t3) && StringUtils.isNotEmpty(t4)) {
                    gd11x5 = new Gd11x5();
                    gd11x5.setQs(Long.parseLong(currentPeriod + t3));
                    gd11x5.setKj(t4.replace(" ", "-"));
                    gd11x5Service.save(gd11x5);
                    sb.append(gd11x5.getQs() + "   ");
                    sb.append(gd11x5.getKj() + "<br />");
                }

                if (StringUtils.isNotEmpty(t5) && StringUtils.isNotEmpty(t6)) {
                    gd11x5 = new Gd11x5();
                    gd11x5.setQs(Long.parseLong(currentPeriod + t5));
                    gd11x5.setKj(t6.replace(" ", "-"));
                    gd11x5Service.save(gd11x5);
                    sb.append(gd11x5.getQs() + "   ");
                    sb.append(gd11x5.getKj() + "<br />");
                }

                if (StringUtils.isNotEmpty(t7) && StringUtils.isNotEmpty(t8.trim())) {
                    gd11x5 = new Gd11x5();
                    gd11x5.setQs(Long.parseLong(currentPeriod + t7));
                    gd11x5.setKj(t8.replace(" ", "-"));
                    gd11x5Service.save(gd11x5);
                    sb.append(gd11x5.getQs() + "   ");
                    sb.append(gd11x5.getKj() + "<br />");
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sb.append(ex.getMessage());
        }
        model.addAttribute("result", sb.toString());
        return "caipiao/gd11x5";
    }

    @RequestMapping(value = "json", method = RequestMethod.GET)
    @ResponseBody
    public Object getData(HttpServletRequest request) {
        Page<Gd11x5> page = getKjPage();
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        page = gd11x5Service.search(page, filters);
        return page.getResult();
    }

    /**
     * 默认页面
     */
    @RequestMapping(method = RequestMethod.GET)
    public String list(HttpServletRequest request, Model model) {
        StringBuilder result = new StringBuilder();

        try {
            Page<Gd11x5> page = getKjPage();
            List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
            page = gd11x5Service.search(page, filters);
            List<Gd11x5> list = page.getResult();

            String qs = "";
            for (Gd11x5 c : list) {
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

   @RequestMapping(value = {"opencai", ""})
    public String opencai(Model model) {
        StringBuilder sb = new StringBuilder();
        try {
            String url = Gd11x5.URL_FROM_OPENCAI;
            String resultJson = Global.getOpenCai(url);
            List<CPK> list = Global.String2OpenCai(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                Gd11x5 gd11x5 = new Gd11x5();
                gd11x5.setKj(cpk.getNumber().replace(",","-"));
                gd11x5.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                gd11x5.setQs(Global.sub20ForQs(cpk.getKey()));
                gd11x5Service.save(gd11x5);

                sb.append(cpk.getKey() + "   ");
                sb.append(cpk.getNumber() + "    ");
                sb.append(cpk.getDateline() + "<br/>");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sb.append(ex.getMessage());
        }
        model.addAttribute("result", sb.toString());
        return "caipiao/gd11x5";
    }

    @RequestMapping(value = {"haoservice", ""})
    public String haoservice(Model model) {
        StringBuilder sb = new StringBuilder();
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

                sb.append(cpk.getKey() + "   ");
                sb.append(cpk.getNumber() + "    ");
                sb.append(cpk.getDateline() + "<br/>");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sb.append(ex.getMessage());
        }
        model.addAttribute("result", sb.toString());
        return "caipiao/gd11x5";
    }



    @RequestMapping(value = {"cpk", ""})
    public String cpk(Model model) {
        StringBuilder sb = new StringBuilder();
        try {
            String url = Gd11x5.URL_FROM_CPK;
            String resultJson = Global.getResultFromHttp(url);
            List<CPK> list = Global.String2CPK(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                Gd11x5 gd11x5 = new Gd11x5();
                gd11x5.setKj(cpk.getNumber().replace(",", "-"));
                gd11x5.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                gd11x5.setQs(Long.parseLong(cpk.getKey()));
                gd11x5Service.save(gd11x5);

                sb.append(cpk.getKey() + "   ");
                sb.append(cpk.getNumber() + "    ");
                sb.append(cpk.getDateline() + "<br/>");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sb.append(ex.getMessage());
        }

        model.addAttribute("result", sb.toString());
        return "caipiao/gd11x5";
    }

}


