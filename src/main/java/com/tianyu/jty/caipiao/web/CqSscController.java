package com.tianyu.jty.caipiao.web;

import com.tianyu.jty.caipiao.entity.CPK;
import com.tianyu.jty.caipiao.entity.CqSSC;
import com.tianyu.jty.caipiao.service.CqSscService;
import com.tianyu.jty.common.persistence.Page;
import com.tianyu.jty.common.persistence.PropertyFilter;
import com.tianyu.jty.common.utils.DateUtils;
import com.tianyu.jty.common.utils.Global;
import com.tianyu.jty.common.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Created by hl on 2016/7/21.
 */
@Controller
@RequestMapping("caipiao/cqssc")
public class CqSscController extends BaseController {

    @Autowired
    CqSscService cqSSCService;

    /**
     * 默认页面
     */
    @RequestMapping(method = RequestMethod.GET)
    public String list(HttpServletRequest request, Model model) {
        StringBuilder result = new StringBuilder();
        try {
            Page<CqSSC> page = getKjPage();
            List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
            page = cqSSCService.search(page, filters);
            List<CqSSC> list = page.getResult();

            String qs = "";
            for (CqSSC c : list) {
                qs = String.valueOf(c.getQs());
                String kj = c.getKj().replace(",", ".").replace("-", ".");
                if (qs.startsWith("20")) {
                    qs = qs.substring(2);
                }

                String num = qs.substring(6);
                if (num.length() == 2) {
                    num = "0" + num;
                } else if (num.length() == 1) {
                    num = "00" + num;
                }

                result.append("20" + qs.substring(0, 6) + "-" + num + " " + kj + "<br/>\r\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String re = result.toString();
        model.addAttribute("result", re);
        return "caipiao/show";
    }

    @RequestMapping(value = {"wapus", ""})
    public String wapus(Model model) {
        String re = cqSSCService.fromWapUs();
        model.addAttribute("result", re);
        return "caipiao/show";
    }

    @RequestMapping(value = {"opencai", ""})
    public String opencai(Model model) {
        StringBuilder sb = new StringBuilder();
        try {
            String url = CqSSC.URL_FROM_OPENCAI;
            String resultJson = Global.getOpenCai(url);
            List<CPK> list = Global.String2OpenCai(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                CqSSC cqSSC = new CqSSC();
                cqSSC.setKj(cpk.getNumber().replace(",", "-"));
                cqSSC.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                cqSSC.setQs(Long.parseLong(cpk.getKey()));
                cqSSCService.save(cqSSC);

                sb.append(cpk.getKey() + "   ");
                sb.append(cpk.getNumber() + "    ");
                sb.append(cpk.getDateline() + "<br/>");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sb.append(ex.getMessage());
        }
        model.addAttribute("result", sb.toString());
        return "caipiao/cqssc";
    }

    @RequestMapping(value = {"haoservice", ""})
    public String haoservice(Model model) {
        StringBuilder sb = new StringBuilder();
        try {
            String url = CqSSC.URL_FROM_HAOSERVICE;
            String resultJson = Global.getHaoService(url);
            List<CPK> list = Global.String2HaoService(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                CqSSC cqSSC = new CqSSC();
                cqSSC.setKj(cpk.getNumber().replace(",", "-"));
                cqSSC.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                cqSSC.setQs(Long.parseLong(cpk.getKey()));
                cqSSCService.save(cqSSC);

                sb.append(cpk.getKey() + "   ");
                sb.append(cpk.getNumber() + "    ");
                sb.append(cpk.getDateline() + "<br/>");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sb.append(ex.getMessage());
        }
        model.addAttribute("result", sb.toString());
        return "caipiao/cqssc";
    }


    @RequestMapping(value = {"cpk", ""})
    public String cpk(Model model) {
        StringBuilder sb = new StringBuilder();
        try {
            String url = CqSSC.URL_FROM_CPK;
            String resultJson = Global.getResultFromHttp(url);
            List<CPK> list = Global.String2CPK(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                CqSSC cqSSC = new CqSSC();
                cqSSC.setKj(cpk.getNumber().replace(",", "-"));
                cqSSC.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                cqSSC.setQs(Long.parseLong(cpk.getKey()));
                cqSSCService.save(cqSSC);

                sb.append(cpk.getKey() + "   ");
                sb.append(cpk.getNumber() + "    ");
                sb.append(cpk.getDateline() + "<br/>");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sb.append(ex.getMessage());
        }

        model.addAttribute("result", sb.toString());
        return "caipiao/cqssc";
    }


    @RequestMapping(value = {"cq", ""})
    public String test(Model model) {
        String result = "开奖错误";

        try {
            Document doc = Jsoup.connect(CqSSC.URL_FROM_CQ).timeout(50000).get();
            Elements openlist = doc.select("#openlist").select("li");
            if (!openlist.isEmpty()) {
                result = "";
                for (int i = 0; i < openlist.size(); i++) {
                    result = result + openlist.get(i).text() + ",";
                    if ((i + 1) % 9 == 0) {
                        result += "<br/>";
                        if ((i + 1) < openlist.size()) {
                            CqSSC cqSsc = new CqSSC();
                            cqSsc.setQs(Long.parseLong("20" + openlist.get(i + 1).text()));
                            cqSsc.setKj(openlist.get(i + 2).text());
                            cqSSCService.save(cqSsc);
                            System.out.println(openlist.get(i + 1).text() + "  " + openlist.get(i + 2).text());
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            result = e.getMessage();
        }
        model.addAttribute("result", result);
        return "caipiao/cqssc";
    }

    @RequestMapping(value = "json", method = RequestMethod.GET)
    @ResponseBody
    public Object getData(HttpServletRequest request) {
        Page<CqSSC> page = getKjPage();
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        page = cqSSCService.search(page, filters);
        return page.getResult();
    }
}
