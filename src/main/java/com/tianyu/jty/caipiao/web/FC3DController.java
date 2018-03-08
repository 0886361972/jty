package com.tianyu.jty.caipiao.web;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.tianyu.jty.caipiao.entity.*;
import com.tianyu.jty.caipiao.service.FC3DService;
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
@RequestMapping("caipiao/fc3d")
public class FC3DController extends BaseController {
    @Autowired
    private FC3DService fc3DService;

    /**
     * 默认页面
     */
    @RequestMapping(method = RequestMethod.GET)
    public String list(HttpServletRequest request,Model model) {
        StringBuilder result = new StringBuilder();
        try{
            Page<FC3D> page = getKjPage();
            List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
            page = fc3DService.search(page, filters);
            List<FC3D> list= page.getResult();

            String qs="";
            for (FC3D c :list){
                qs=String.valueOf(c.getQs());
                result.append(qs);
                result.append(" ");
                result.append(c.getKj().replace(",",".").replace("-","."));
                result.append("<br/>\r\n");
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        model.addAttribute("result", result.toString());
        return "caipiao/show";
    }


    @RequestMapping(value = {"cpk", ""})
    public String cpk(Model model) {
        StringBuilder sb = new StringBuilder();
        try {
            String url = FC3D.URL_FROM_CPK;
            String resultJson = Global.getResultFromHttp(url);
            List<CPK> list = Global.String2CPK(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                FC3D fc3D = new FC3D();
                fc3D.setKj(cpk.getNumber().replace(",","-"));
                fc3D.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                fc3D.setQs(Long.parseLong(cpk.getKey()));
                fc3DService.save(fc3D);

                sb.append(cpk.getKey()+ "   ");
                sb.append(cpk.getNumber()+"    ");
                sb.append(cpk.getDateline()+"<br/>");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sb.append(ex.getMessage());
        }
        model.addAttribute("result", sb.toString());
        return "caipiao/fc3d";
    }

    @RequestMapping(value = {"opencai", ""})
    public String opencai(Model model) {
        StringBuilder sb = new StringBuilder();
        try {
            String url = FC3D.URL_FROM_OPENCAI;
            String resultJson = Global.getOpenCai(url);
            List<CPK> list = Global.String2OpenCai(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                FC3D fc3D = new FC3D();
                fc3D.setKj(cpk.getNumber().replace(",","-"));
                fc3D.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                fc3D.setQs(Long.parseLong(cpk.getKey()));
                fc3DService.save(fc3D);

                sb.append(cpk.getKey() + "   ");
                sb.append(cpk.getNumber() + "    ");
                sb.append(cpk.getDateline() + "<br/>");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sb.append(ex.getMessage());
        }
        model.addAttribute("result", sb.toString());
        return "caipiao/fc3d";
    }


    @RequestMapping(value = {"bd", ""})
    public String fromBd(Model model) {
        StringBuilder sb = new StringBuilder();
        try {
            WebClient wc = new WebClient();
            wc.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true
            wc.getOptions().setCssEnabled(false); //禁用css支持
            wc.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常
            wc.getOptions().setTimeout(10000); //设置连接超时时间 ，这里是10S。如果为0，则无限期等待
            HtmlPage page = wc.getPage(FC3D.URL_FROM_BAIDU);
            String pageXml = page.asXml(); //以xml的形式获取响应文本

            Document doc = Jsoup.parse(pageXml, "http://baidu.lecai.com");
            Elements trs = doc.select("table.historylist").select("tr");
            if (!trs.isEmpty()) {
                for (int i = 1; i < trs.size(); i++) {
                    Elements tds = trs.get(i).select("td");
                    if (!tds.isEmpty()) {
                        Elements aHref = tds.get(0).select("a");
                        if (!aHref.isEmpty()) {
                            String qs = aHref.get(0).text();
                            String kj = "";
                            Elements ems = tds.get(2).select("em");
                            for (int e = 0; e < ems.size(); e++) {
                                if ("".equals(kj)) {
                                    kj = ems.get(e).text();
                                } else {
                                    kj = kj + "-" + ems.get(e).text();
                                }
                            }
                            sb.append(qs + "  ");
                            sb.append(kj);
                            sb.append("</br>");

                            FC3D fc3D = new FC3D();
                            fc3D.setQs(Long.parseLong(qs));
                            fc3D.setKj(kj);
                            fc3DService.save(fc3D);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            sb.append(e.getMessage());
        }
        model.addAttribute("result", sb.toString());
        return "caipiao/fc3d";
    }

    @RequestMapping(value = "json", method = RequestMethod.GET)
    @ResponseBody
    public Object getData(HttpServletRequest request) {
        Page<FC3D> page = getKjPage();
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        page = fc3DService.search(page, filters);
        return  page.getResult();
    }
}
