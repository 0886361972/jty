package com.tianyu.jty.caipiao.web;

import com.tianyu.jty.caipiao.entity.CPK;
import com.tianyu.jty.caipiao.entity.PK10;
import com.tianyu.jty.caipiao.service.PK10Service;
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
 * Created by hl on 2016/7/21.
 */
@Controller
@RequestMapping("caipiao/pk10")
public class Pk10Controller extends BaseController {

    @Autowired
   private PK10Service pk10Service;

    /**
     * 默认页面
     */
    @RequestMapping(method = RequestMethod.GET)
    public String list(HttpServletRequest request,Model model) {
        StringBuilder result = new StringBuilder();
       try{
           Page<PK10> page = getKjPage();
           List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
           page = pk10Service.search(page, filters);
           List<PK10> list= page.getResult();

           for (PK10 p :list){
               if (null==p.getDateLine()){
                   result.append(DateUtils.formatDate(p.getAddTime(),"yyyyMMdd"));
               }else {
                   result.append(DateUtils.formatDate(p.getDateLine(),"yyyyMMdd"));
               }
               result.append("-");
               result.append(p.getQs());
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

    @RequestMapping(value = {"wapus", ""})
    public String wapus(Model model) {
        String re = pk10Service.fromWapUs();
        model.addAttribute("result", re);
        return "caipiao/show";
    }


    @RequestMapping(value = {"opencai", ""})
    public String opencai(Model model) {
        StringBuilder sb = new StringBuilder();
        try {
            String url = PK10.URL_FROM_OPENCAI;
            String resultJson = Global.getOpenCai(url);
            List<CPK> list = Global.String2OpenCai(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                PK10 pk10 = new PK10();
                pk10.setKj(cpk.getNumber().replace(",","-"));
                pk10.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                pk10.setQs(Long.parseLong(cpk.getKey()));
                pk10Service.save(pk10);

                sb.append(cpk.getKey() + "   ");
                sb.append(cpk.getNumber() + "    ");
                sb.append(cpk.getDateline() + "<br/>");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sb.append(ex.getMessage());
        }
        model.addAttribute("result", sb.toString());
        return "caipiao/pk10";
    }


    @RequestMapping(value = {"haoservice", ""})
    public String haoservice(Model model) {
        StringBuilder sb = new StringBuilder();
        try {
            String url = PK10.URL_FROM_HAOSERVICE;
            String resultJson = Global.getHaoService(url);
            List<CPK> list = Global.String2HaoService(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);

                if (!cpk.getKey().startsWith("61") && !cpk.getKey().startsWith("62")){
                    continue;
                }

                PK10 pk10 = new PK10();
                pk10.setKj(cpk.getNumber().replace(",","-"));
                pk10.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                pk10.setQs(Long.parseLong(cpk.getKey()));
                pk10Service.save(pk10);

                sb.append(cpk.getKey() + "   ");
                sb.append(cpk.getNumber() + "    ");
                sb.append(cpk.getDateline() + "<br/>");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sb.append(ex.getMessage());
        }
        model.addAttribute("result", sb.toString());
        return "caipiao/pk10";
    }

    @RequestMapping(value = {"cpk", ""})
    public String cpk(Model model) {
        StringBuilder sb = new StringBuilder();
        try {
            String url = PK10.URL_FROM_CPK;
            String resultJson = Global.getResultFromHttp(url);
            List<CPK> list = Global.String2CPK(resultJson);
            for (int i = 0; i < list.size(); i++) {
                CPK cpk = list.get(i);
                PK10 pk10 = new PK10();
                pk10.setKj(cpk.getNumber().replace(",","-"));
                pk10.setDateLine(DateUtils.parseDate(cpk.getDateline()));
                pk10.setQs(Long.parseLong(cpk.getKey()));
                pk10Service.save(pk10);

                sb.append(cpk.getKey()+ "   ");
                sb.append(cpk.getNumber()+"    ");
                sb.append(cpk.getDateline()+"<br/>");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sb.append(ex.getMessage());
        }

        model.addAttribute("result", sb.toString());
        return "caipiao/pk10";
    }

    @RequestMapping(value = {"bj", ""})
    public String test(Model model) {
        StringBuilder sb =new StringBuilder();
        try {
            Document doc = Jsoup.connect(PK10.URL_FROM_BJ).timeout(5000).get();
            Elements trs = doc.select("table.tb").select("tr");
            if (!trs.isEmpty()) {
                for (int i = 0; i < trs.size(); i++) {
                    Elements tds= trs.get(i).select("td");
                    if (!tds.isEmpty()){
                        sb.append(tds.get(0) +"  ");
                        sb.append(tds.get(1));
                        sb.append("</br>");

                        PK10 pk10 = new PK10();
                        pk10.setQs(Long.parseLong(tds.get(0).text()));
                        pk10.setKj(tds.get(1).text().replace(",","-"));
                        pk10Service.save(pk10);
                    }
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
            sb.append(ex.getMessage());
        }

        model.addAttribute("result", sb.toString());
        return "caipiao/pk10";
    }


    @RequestMapping(value = "json", method = RequestMethod.GET)
    @ResponseBody
    public Object getData(HttpServletRequest request) {
        Page<PK10> page = getKjPage();
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        page = pk10Service.search(page, filters);
        return  page.getResult();
    }
}
