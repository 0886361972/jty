package com.tianyu.jty.caipiao.web;

import com.tianyu.jty.caipiao.entity.SyyDJ;
import com.tianyu.jty.caipiao.entity.Xy28;
import com.tianyu.jty.caipiao.service.Xy28Service;
import com.tianyu.jty.common.persistence.Page;
import com.tianyu.jty.common.persistence.PropertyFilter;
import com.tianyu.jty.common.utils.DateUtils;
import com.tianyu.jty.common.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hzhonglong on 2016/9/14.
 */

@Controller
@RequestMapping("caipiao/xy28")
public class Xy28Controller extends BaseController {
    @Autowired
    private Xy28Service xy28Service;


    @RequestMapping(value = {"gf", ""})
    public String kj(HttpServletRequest request, Model model) {
        try {
            List<Xy28> retList = xy28Service.getXy28List();
            if (retList == null) {
                retList = xy28Service.login();
            }

            if (retList != null) {
                for (Xy28 xy28 : retList) {
                    xy28Service.save(xy28);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        String result=queryFromDB(request);
        model.addAttribute("result", result.toString());
        return "caipiao/show";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String list(HttpServletRequest request,Model model) {
        String result = queryFromDB(request);
        model.addAttribute("result", result.toString());
        return "caipiao/show";
    }

    @RequestMapping(value = "json", method = RequestMethod.GET)
    @ResponseBody
    public Object getData(HttpServletRequest request) {
        Page<Xy28> page = getKjPage();
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        page = xy28Service.search(page, filters);
        return  page.getResult();
    }



    public String queryFromDB(HttpServletRequest request) {
        Page<Xy28> page = getKjPage();
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        page = xy28Service.search(page, filters);
        List<Xy28> list = page.getResult();

        StringBuilder result = new StringBuilder();
        result.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        result.append("<CaiPiao>");
        for (Xy28 xy28 : list) {
            result.append("<Node Date=\"" + DateUtils.formatDate(xy28.getDateLine(), "yyyyMMdd") + "\" ID=\"" + xy28.getQs() + "\" >" + xy28.getKj() + "</Node>");
        }
        result.append("</CaiPiao>");
        return result.toString();
    }

}
