package com.tianyu.jty.caipiao.web;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.tianyu.jty.caipiao.entity.*;
import com.tianyu.jty.caipiao.service.GDKL10Service;
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
import java.util.Date;
import java.util.List;

/**
 * Created by hl on 2016/8/7.
 */
@Controller
@RequestMapping("caipiao/gdkl10")
public class GDKL10Controller extends BaseController {
    @Autowired
    private GDKL10Service gdkl10Service;

    @RequestMapping(value = {"cpk", ""})
    public String cpk(Model model) {
        StringBuilder sb = new StringBuilder();
        try {
            String url = GDKL10.URL_FROM_CPK;
            String resultJson = Global.getResultFromHttp(url);
            List<CPK> list = Global.String2CPK(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                GDKL10 gdkl10 = new GDKL10();
                gdkl10.setKj(cpk.getNumber().replace(",","-"));
                gdkl10.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                gdkl10.setQs(Long.parseLong(cpk.getKey()));
                gdkl10Service.save(gdkl10);

                sb.append(cpk.getKey()+ "   ");
                sb.append(cpk.getNumber()+"    ");
                sb.append(cpk.getDateline()+"<br/>");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sb.append(ex.getMessage());
        }

        model.addAttribute("result", sb.toString());
        return "caipiao/gdkl10";
    }

    @RequestMapping(value = {"gd", ""})
    public String test(Model model) {
        StringBuilder sb =new StringBuilder();
        try {
            WebClient wc = new WebClient();
            wc.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true
            wc.getOptions().setCssEnabled(false); //禁用css支持
            wc.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常
            wc.getOptions().setTimeout(10000); //设置连接超时时间 ，这里是10S。如果为0，则无限期等待
            HtmlPage page = wc.getPage(GDKL10.URL_FROM_GD);
            String pageXml = page.asXml(); //以xml的形式获取响应文本

            Document doc = Jsoup.parse(pageXml, "http://www.gdfc.org.cn/");
            Elements trs = doc.select("table").select("tr");
            if (!trs.isEmpty()){
               for (int i=1;i<trs.size();i++){
                   Elements tds = trs.get(i).select("td");
                   GDKL10 gdkl10 = new GDKL10();
                   gdkl10.setAddTime(new Date());
                   gdkl10.setQs(Long.parseLong(tds.get(0).text()));
                   gdkl10.setKj(tds.get(1).html());
                   gdkl10.setKj(gdkl10.getKj().replace(" ","-").replace("&nbsp;","-").replace("--","-"));
                   gdkl10Service.save(gdkl10);
                   sb.append(trs.get(i)+"<br/>");
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
            sb.append(ex.getMessage());
        }

        model.addAttribute("result", sb.toString());
        return "caipiao/gdkl10";
    }

    @RequestMapping(value = "json", method = RequestMethod.GET)
    @ResponseBody
    public Object getData(HttpServletRequest request) {
        Page<GDKL10> page = getKjPage();
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        page = gdkl10Service.search(page, filters);
        return page.getResult();
    }

    /**
     * 默认页面
     */
    @RequestMapping(method = RequestMethod.GET)
    public String list(HttpServletRequest request,Model model) {
        StringBuilder result = new StringBuilder();

        try{
            Page<GDKL10> page = getKjPage();
            List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
            page = gdkl10Service.search(page, filters);
            List<GDKL10> list= page.getResult();

            String qs="";
            for (GDKL10 c :list){
                qs=String.valueOf(c.getQs());
                result.append(qs.substring(0,8));
                result.append("-");
                result.append(qs.substring(8));
                result.append(" ");
                result.append(c.getKj().replace(",",".").replace("-","."));
                result.append("<br/>\r\n");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        model.addAttribute("result", result.toString());
        return "caipiao/show";
    }
}
