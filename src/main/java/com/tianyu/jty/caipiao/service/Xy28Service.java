package com.tianyu.jty.caipiao.service;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.tianyu.jty.caipiao.dao.SyyDJDao;
import com.tianyu.jty.caipiao.dao.Xy28Dao;
import com.tianyu.jty.caipiao.entity.SyyDJ;
import com.tianyu.jty.caipiao.entity.Xy28;
import com.tianyu.jty.caipiao.utils.CodeUtils;
import com.tianyu.jty.common.persistence.HibernateDao;
import com.tianyu.jty.common.service.BaseService;
import com.tianyu.jty.common.utils.DateUtils;
import com.tianyu.jty.common.utils.Global;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hzhonglong on 2016/9/14.
 */

@Service
@Transactional(readOnly = true)
public class Xy28Service extends BaseService<Xy28, Integer> {
    private final static Logger logger = LoggerFactory.getLogger(Xy28Service.class);

    private  WebClient WEB_CLIENT =null;
    @Autowired
    private Xy28Dao xy28Dao;


    @Override
    public HibernateDao<Xy28, Integer> getEntityDao() {
        return xy28Dao;
    }

    @Transactional(readOnly = false)
    public void save(Xy28 xy28) {
        if (xy28Dao.findQs(xy28.getQs()).isEmpty()) {
            xy28.setAddTime(new Date());
            xy28Dao.save(xy28);
        }
    }

    public List<Xy28> login(){
        WEB_CLIENT = new WebClient(BrowserVersion.INTERNET_EXPLORER);
        WEB_CLIENT.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true
        WEB_CLIENT.getOptions().setCssEnabled(false); //禁用css支持
        WEB_CLIENT.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常
        WEB_CLIENT.getOptions().setTimeout(20000); //设置连接超时时间 ，这里是10S。如果为0，则无限期等待


        List<Xy28> xy28s = null;
        try{
            HtmlPage page = WEB_CLIENT.getPage("http://www.pceggs.com/nologin.aspx");
            HtmlInput username = (HtmlInput)page.getElementById("txt_UserName");
            HtmlInput password =(HtmlInput)page.getElementById("txt_PWD");
            HtmlInput valiCode = (HtmlInput)page.getElementById("txt_VerifyCode");

            HtmlInput login_Submit = (HtmlInput)page.getElementById("Login_Submit");

            HtmlImage valiCodeImg = (HtmlImage) page.getElementById("valiCode");
            ImageReader imageReader = valiCodeImg.getImageReader();
            BufferedImage bufferedImage = imageReader.read(0);

            Long fileName = System.currentTimeMillis();
            File outputfile = new File(Xy28.LOCAL_IMAGE_DIR+File.separator+fileName+".jpg");
            ImageIO.write(bufferedImage, "jpg", outputfile);

            //验证码识别开始
            CodeUtils.SetAuthor(Global.getConfig("91yzm.author"));//设置作者帐号
            CodeUtils jh = new CodeUtils("");

            String id=jh.SendFile(Global.getConfig("91yzm.ac"),outputfile.getAbsolutePath(),1000,30,1,"");
            String answer="";
            while(answer==null||answer.length()==0){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                jh =new CodeUtils("");
                answer=jh.GetAnswer(id);
            }

            username.click();
            username.type(Global.getConfig("pcegg.account"));
            password.click();
            password.type(Global.getConfig("pcegg.pwd"));
            valiCode.click();
            valiCode.type(answer);
            login_Submit.click();
            return getXy28List();

        }catch (Exception ex){
            logger.error("尝试登陆pcegg失败",ex);
            return xy28s;
        }
    }

    public List<Xy28> getXy28List(){
        List<Xy28> xy28s = null;

        if (WEB_CLIENT==null){
            return null;
        }
        try{
            HtmlPage CustomerFund= WEB_CLIENT.getPage("http://www.pceggs.com/play/pxya.aspx");
            String pageXml = CustomerFund.asXml(); //以xml的形式获取响应文本
            // System.out.println(pageXml);
            Document doc = Jsoup.parse(pageXml, "http://www.pceggs.com");
            Elements trs = doc.select("#panel").select("tr");
            if(trs==null || trs.size()<=0){
                return xy28s;
            }
            xy28s = new LinkedList<>();
            for (int i=0;i<trs.size();i++){
                Element element=trs.get(i);
                Xy28 xy28=new Xy28();
                Elements tds=element.select("td");
                String qs=tds.get(0).text();
                String dline= DateUtils.getYear()+"-"+tds.get(1).text()+":00";
                String kj=tds.get(2).text();
                if (kj.contains("+") && kj.contains("=")){
                    xy28.setAddTime(new Date());
                    xy28.setDateLine(DateUtils.parseDate(dline));
                    xy28.setKj(kj.replace(" + ",".").replace(" =",""));
                    xy28.setQs(NumberUtils.toLong(qs));
                    xy28s.add(xy28);
                }
            }
        }catch (Exception ex){
            logger.error("尝试获取xy28开奖失败",ex);
            return xy28s;
        }
        return  xy28s;
    }

}
