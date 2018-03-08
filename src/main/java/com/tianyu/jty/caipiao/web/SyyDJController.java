package com.tianyu.jty.caipiao.web;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.tianyu.jty.caipiao.entity.*;
import com.tianyu.jty.caipiao.service.SyyDJService;
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
 * 山东十一运夺金
 */
@Controller
@RequestMapping("caipiao/syydj")
public class SyyDJController extends BaseController {

    @Autowired
    private SyyDJService syyDJService;

    @RequestMapping(value = {"sd", ""})
    public String test_sd(Model model) {
        StringBuilder sb = new StringBuilder();
        try {
            WebClient wc = new WebClient();
            wc.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true
            wc.getOptions().setCssEnabled(false); //禁用css支持
            wc.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常
            wc.getOptions().setTimeout(10000); //设置连接超时时间 ，这里是10S。如果为0，则无限期等待
            HtmlPage page = wc.getPage(SyyDJ.URL_FROM_SD);
            String pageXml = page.asXml(); //以xml的形式获取响应文本

            Document doc = Jsoup.parse(pageXml, SyyDJ.URL_FROM_SD);
            Elements table = doc.select("table.sbk").select("table.js").select("table.js");
            Elements trs = table.select("tr");
            if (!table.isEmpty() && !trs.isEmpty()) {
                for (int i=1;i<trs.size();i++){
                    Elements tds = trs.get(i).select("td");
                    sb.append(tds.get(0).text() + "  ");
                    sb.append(tds.get(1).text()+"-"+tds.get(2).text()+"-"+tds.get(3).text()+"-"+tds.get(4).text()+"-"+tds.get(5).text());
                    sb.append("</br>");

                    SyyDJ syyDJ = new SyyDJ();
                    syyDJ.setQs(Long.parseLong(tds.get(0).text()));
                    syyDJ.setKj(tds.get(1).text()+"-"+tds.get(2).text()+"-"+tds.get(3).text()+"-"+tds.get(4).text()+"-"+tds.get(5).text());
                    syyDJService.save(syyDJ);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sb.append(ex);
        }
        model.addAttribute("result", sb.toString());
        return "caipiao/syydj";
    }


    @RequestMapping(value = {"opencai", ""})
    public String opencai(Model model) {
        StringBuilder sb = new StringBuilder();
        try {
            String url = SyyDJ.URL_FROM_OPENCAI;
            String resultJson = Global.getOpenCai(url);
            List<CPK> list = Global.String2OpenCai(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                SyyDJ syyDJ = new SyyDJ();
                syyDJ.setKj(cpk.getNumber().replace(",","-"));
                syyDJ.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                syyDJ.setQs(Long.parseLong(cpk.getKey()));
                syyDJService.save(syyDJ);

                sb.append(cpk.getKey() + "   ");
                sb.append(cpk.getNumber() + "    ");
                sb.append(cpk.getDateline() + "<br/>");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sb.append(ex.getMessage());
        }
        model.addAttribute("result", sb.toString());
        return "caipiao/syydj";
    }


    @RequestMapping(value = {"haoservice", ""})
    public String haoservice(Model model) {
        StringBuilder sb = new StringBuilder();
        try {
            String url = SyyDJ.URL_FROM_HAOSERVICE;
            String resultJson = Global.getHaoService(url);
            List<CPK> list = Global.String2HaoService(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                SyyDJ syyDJ = new SyyDJ();
                syyDJ.setKj(cpk.getNumber().replace(",","-"));
                syyDJ.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                syyDJ.setQs(Long.parseLong(cpk.getKey()));
                syyDJService.save(syyDJ);

                sb.append(cpk.getKey() + "   ");
                sb.append(cpk.getNumber() + "    ");
                sb.append(cpk.getDateline() + "<br/>");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sb.append(ex.getMessage());
        }
        model.addAttribute("result", sb.toString());
        return "caipiao/syydj";
    }

    @RequestMapping(value = {"cpk", ""})
    public String cpk(Model model) {
        StringBuilder sb = new StringBuilder();
        try {
            String url = SyyDJ.URL_FROM_CPK;
            String resultJson = Global.getResultFromHttp(url);
            List<CPK> list = Global.String2CPK(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                SyyDJ syyDJ = new SyyDJ();
                syyDJ.setKj(cpk.getNumber().replace(",","-"));
                syyDJ.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                syyDJ.setQs(Long.parseLong(cpk.getKey()));
                syyDJService.save(syyDJ);

                sb.append(cpk.getKey()+ "   ");
                sb.append(cpk.getNumber()+"    ");
                sb.append(cpk.getDateline()+"<br/>");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sb.append(ex.getMessage());
        }

        model.addAttribute("result", sb.toString());
        return "caipiao/syydj";
    }

    @RequestMapping(value = "json", method = RequestMethod.GET)
    @ResponseBody
    public Object getData(HttpServletRequest request) {
        Page<SyyDJ> page = getKjPage();
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        page = syyDJService.search(page, filters);
        return  page.getResult();
    }

    /**
     * 默认页面
     */
    @RequestMapping(method = RequestMethod.GET)
    public String list(HttpServletRequest request,Model model) {
        StringBuilder result = new StringBuilder();
        try{
            Page<SyyDJ> page = getKjPage();
            List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
            page = syyDJService.search(page, filters);
            List<SyyDJ> list= page.getResult();

            String qs="";
            for (SyyDJ c :list){
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
}
