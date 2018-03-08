package com.tianyu.jty.caipiao.web;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.tianyu.jty.caipiao.entity.*;
import com.tianyu.jty.caipiao.service.ShSslService;
import com.tianyu.jty.common.persistence.Page;
import com.tianyu.jty.common.persistence.PropertyFilter;
import com.tianyu.jty.common.utils.DateUtils;
import com.tianyu.jty.common.utils.Global;
import com.tianyu.jty.common.web.BaseController;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hl on 2016/8/7.
 */

@Controller
@RequestMapping("caipiao/shssl")
public class ShSslController extends BaseController {

    @Autowired
    private ShSslService shSslService;

    @RequestMapping(value = {"sh", ""})
    public String test_sh(Model model) {
        StringBuilder sb = new StringBuilder();

        try {
            WebClient wc = new WebClient();
            wc.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true
            wc.getOptions().setCssEnabled(false); //禁用css支持
            wc.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常
            wc.getOptions().setTimeout(30000); //设置连接超时时间 ，这里是10S。如果为0，则无限期等待
            HtmlPage page = wc.getPage(ShSsl.URL_FROM_SH);
            String pageXml = page.asXml(); //以xml的形式获取响应文本

            Document doc = Jsoup.parse(pageXml, ShSsl.URL_FROM_SH);
            Elements table = doc.select("div.lm2block1");
            Elements divs = table.select("div.fl");
            List<Element> tmp_list = new LinkedList<>();
            if (!divs.isEmpty()) {
                for (int i = 0; i < divs.size(); i++) {
                    if (tmp_list.size() == 3) {
                        ShSsl shSsl = new ShSsl();
                        shSsl.setAddTime(new Date());
                        shSsl.setQs(Long.parseLong(tmp_list.get(0).text().replace("-", "")));

                        Elements hm = tmp_list.get(2).select("span");
                        shSsl.setKj(hm.get(0).text() + "-" + hm.get(1).text() + "-" + hm.get(2).text());
                        shSslService.save(shSsl);
                        tmp_list.clear();
                        i = i - 1;

                        sb.append(shSsl.getQs() + "  ");
                        sb.append(shSsl.getKj());
                        sb.append("</br>");

                    } else {
                        tmp_list.add(divs.get(i));
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sb.append(ex);
        }
        model.addAttribute("result", sb.toString());
        return "caipiao/shssl";
    }

    @RequestMapping(value = "json", method = RequestMethod.GET)
    @ResponseBody
    public Object getData(HttpServletRequest request) {
        Page<ShSsl> page = getKjPage();
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        page = shSslService.search(page, filters);
        return  page.getResult();
    }


    @RequestMapping(value = {"opencai", ""})
    public String opencai(Model model) {
        StringBuilder sb = new StringBuilder();
        try {
            String url = ShSsl.URL_FROM_OPENCAI;
            String resultJson = Global.getOpenCai(url);
            List<CPK> list = Global.String2OpenCai(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                ShSsl shSsl = new ShSsl();
                shSsl.setKj(cpk.getNumber().replace(",","-"));
                shSsl.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                shSsl.setQs(Long.parseLong(cpk.getKey()));
                shSslService.save(shSsl);

                sb.append(cpk.getKey() + "   ");
                sb.append(cpk.getNumber() + "    ");
                sb.append(cpk.getDateline() + "<br/>");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sb.append(ex.getMessage());
        }
        model.addAttribute("result", sb.toString());
        return "caipiao/shssl";
    }

    @RequestMapping(value = {"haoservice", ""})
    public String haoservice(Model model) {
        StringBuilder sb = new StringBuilder();
        try {
            String url = ShSsl.URL_FROM_HAOSERVICE;
            String resultJson = Global.getHaoService(url);
            List<CPK> list = Global.String2HaoService(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                ShSsl shSsl = new ShSsl();
                shSsl.setKj(cpk.getNumber().replace(",","-"));
                shSsl.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                shSsl.setQs(Long.parseLong(cpk.getKey()));
                shSsl.setKj(shSsl.getKj().substring(0,5));
                shSslService.save(shSsl);

                sb.append(cpk.getKey() + "   ");
                sb.append(cpk.getNumber() + "    ");
                sb.append(cpk.getDateline() + "<br/>");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sb.append(ex.getMessage());
        }
        model.addAttribute("result", sb.toString());
        return "caipiao/shssl";
    }


    @RequestMapping(value = {"cpk", ""})
    public String cpk(Model model) {
        StringBuilder sb = new StringBuilder();
        try {
            String url = ShSsl.URL_FROM_CPK;
            String resultJson = Global.getResultFromHttp(url);
            List<CPK> list = Global.String2CPK(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                ShSsl shSsl = new ShSsl();
                shSsl.setKj(cpk.getNumber().replace(",","-"));
                shSsl.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                shSsl.setQs(Long.parseLong(cpk.getKey()));
                shSslService.save(shSsl);

                sb.append(cpk.getKey()+ "   ");
                sb.append(cpk.getNumber()+"    ");
                sb.append(cpk.getDateline()+"<br/>\r\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sb.append(ex.getMessage());
        }

        model.addAttribute("result", sb.toString());
        return "caipiao/shssl";
    }

    @RequestMapping(value = {"wapus", ""})
    public String wapus(Model model) {
        String re = shSslService.fromWapUs();
        model.addAttribute("result", re);
        return "caipiao/show";
    }

    /**
     * 默认页面
    */
    @RequestMapping(method = RequestMethod.GET)
    public String list(HttpServletRequest request,Model model) {
        StringBuilder result = new StringBuilder();
        try{
            Page<ShSsl> page = getKjPage();
            List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
            page = shSslService.search(page, filters);
            List<ShSsl> list= page.getResult();

            String qs="";
            for (ShSsl c :list){
                qs=String.valueOf(c.getQs());
                result.append(qs.substring(0,8));
                result.append("-");
                result.append(qs.substring(8));
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
