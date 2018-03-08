package com.tianyu.jty.caipiao.web;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.tianyu.jty.caipiao.entity.CPK;
import com.tianyu.jty.caipiao.entity.CQKL10;
import com.tianyu.jty.caipiao.service.CqKL10Service;
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
 * Created by hl on 2016/7/24.
 */
@Controller
@RequestMapping("caipiao/cqkl10")
public class CqKL10Controller extends BaseController {

    @Autowired
    private CqKL10Service cqKL10Service;


    /**
     * 默认页面
     */
    @RequestMapping(method = RequestMethod.GET)
    public String list(HttpServletRequest request,Model model) {
        StringBuilder result = new StringBuilder();
        try{
            Page<CQKL10> page = getKjPage();
            List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
            page = cqKL10Service.search(page, filters);
            List<CQKL10> list= page.getResult();

            for (CQKL10 p :list){
                String qs="20"+String.valueOf(p.getQs());
                result.append(qs.substring(0,8)+"-"+qs.substring(9));
                result.append(" ");
                result.append(p.getKj().replace(",",".").replace("-","."));
                result.append("<br/>\r\n");
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        model.addAttribute("result", result.toString());
        return "caipiao/show";
    }

    @RequestMapping(value = {"opencai", ""})
    public String opencai(Model model) {
        StringBuilder sb = new StringBuilder();
        try {
            String url = CQKL10.URL_FROM_OPENCAI;
            String resultJson = Global.getOpenCai(url);
            List<CPK> list = Global.String2OpenCai(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                CQKL10 cqkl10 = new CQKL10();
                cqkl10.setKj(cpk.getNumber().replace(",","-"));
                cqkl10.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                cqkl10.setQs(Global.sub20ForQs(cpk.getKey()));
                cqKL10Service.save(cqkl10);

                sb.append(cpk.getKey() + "   ");
                sb.append(cpk.getNumber() + "    ");
                sb.append(cpk.getDateline() + "<br/>");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sb.append(ex.getMessage());
        }
        model.addAttribute("result", sb.toString());
        return "caipiao/cqkl10";
    }

    @RequestMapping(value = {"haoservice", ""})
    public String haoservice(Model model) {
        StringBuilder sb = new StringBuilder();
        try {
            String url = CQKL10.URL_FROM_HAOSERVICE;
            String resultJson = Global.getHaoService(url);
            List<CPK> list = Global.String2HaoService(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                CQKL10 cqkl10 = new CQKL10();
                cqkl10.setKj(cpk.getNumber().replace(",","-"));
                cqkl10.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                cqkl10.setQs(Global.sub20ForQs(cpk.getKey()));
                cqKL10Service.save(cqkl10);

                sb.append(cpk.getKey() + "   ");
                sb.append(cpk.getNumber() + "    ");
                sb.append(cpk.getDateline() + "<br/>");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sb.append(ex.getMessage());
        }
        model.addAttribute("result", sb.toString());
        return "caipiao/cqkl10";
    }




    @RequestMapping(value = {"cpk", ""})
    public String cpk(Model model) {
        StringBuilder sb = new StringBuilder();
        try {
            String url = CQKL10.URL_FROM_CPK;
            String resultJson = Global.getResultFromHttp(url);
            List<CPK> list = Global.String2CPK(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                CQKL10 cqkl10 = new CQKL10();
                cqkl10.setKj(cpk.getNumber().replace(",","-"));
                cqkl10.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                cqkl10.setQs(Long.parseLong(cpk.getKey()));
                cqKL10Service.save(cqkl10);

                sb.append(cpk.getKey()+ "   ");
                sb.append(cpk.getNumber()+"    ");
                sb.append(cpk.getDateline()+"<br/>");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sb.append(ex.getMessage());
        }

        model.addAttribute("result", sb.toString());
        return "caipiao/cqkl10";
    }

    @RequestMapping(value = {"bd", ""})
    public String test(Model model) {
        StringBuilder sb =new StringBuilder();
        try {
            WebClient wc = new WebClient();
            wc.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true
            wc.getOptions().setCssEnabled(false); //禁用css支持
            wc.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常
            wc.getOptions().setTimeout(10000); //设置连接超时时间 ，这里是10S。如果为0，则无限期等待
            HtmlPage page = wc.getPage(CQKL10.URL_FROM_BAIDU);
            String pageXml = page.asXml(); //以xml的形式获取响应文本

            Document doc = Jsoup.parse(pageXml, "http://baidu.lecai.com");
            Elements trs = doc.select("#jq_draw_list").select("tr");
            if (!trs.isEmpty()){
                for (int i=trs.size()-1;i>0;i--){
                    Elements tds = trs.get(i).select("td");
                    if (!tds.isEmpty()){
                        sb.append(tds.get(0).text() + "  ");
                        sb.append(tds.get(2).text().replace(",","-"));
                        sb.append("</br>");

                        CQKL10 cqkl10 = new CQKL10();
                        cqkl10.setQs(Long.parseLong(tds.get(0).text()));
                        cqkl10.setKj(tds.get(2).text().replace(",","-"));
                        cqKL10Service.save(cqkl10);
                    }
                }
            }

        }catch (Exception ex){
            ex.printStackTrace();
            sb.append(ex.getMessage());
        }

        model.addAttribute("result", sb.toString());
        return "caipiao/cqkl10";
    }

    @RequestMapping(value = "json", method = RequestMethod.GET)
    @ResponseBody
    public Object getData(HttpServletRequest request) {
        Page<CQKL10> page = getKjPage();
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        page = cqKL10Service.search(page, filters);
        return  page.getResult();
    }
}