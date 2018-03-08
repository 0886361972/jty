package com.tianyu.jty.caipiao.web;

import com.tianyu.jty.caipiao.entity.BJK8;
import com.tianyu.jty.caipiao.entity.CPK;
import com.tianyu.jty.caipiao.service.BJK8Service;
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
                qs =String.valueOf(c.getQs());
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
                bjk8.setKj(cpk.getNumber().replace(",", "-").replace("+","-"));
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
                bjk8.setKj(cpk.getNumber().replace(",", "-").replace("+","-"));
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
}
