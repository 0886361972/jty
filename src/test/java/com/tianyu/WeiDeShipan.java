package com.tianyu;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.tianyu.jty.caipiao.utils.CodeUtils;
import org.apache.commons.lang3.StringEscapeUtils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Set;

/**
 * Created by hzhonglog on 16/12/15.
 */
public class WeiDeShipan {

    public static void main(String[] args) throws IOException {
        WebClient WEB_CLIENT = new WebClient(BrowserVersion.CHROME);
        WEB_CLIENT.getOptions().setRedirectEnabled(true);
        WEB_CLIENT.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true
        WEB_CLIENT.getOptions().setCssEnabled(false); //禁用css支持
        WEB_CLIENT.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常
        WEB_CLIENT.getOptions().setTimeout(20000); //设置连接超时时间 ，这里是10S。如果为0，则无限期等待

        HtmlPage page = WEB_CLIENT.getPage("http://112.74.42.50/Admin/index.php/public/login/");
        HtmlForm htmlForm = page.getFormByName("login");
        HtmlInput username = htmlForm.getInputByName("accountalias");
        HtmlInput password = htmlForm.getInputByName("loginpwd");
        HtmlInput valiCode = htmlForm.getInputByName("verify");


        HtmlInput submit = htmlForm.getInputByValue("登录");

        System.out.println(submit.toString());


        HtmlImage valiCodeImg = (HtmlImage) page.getElementById("imgverify");
        ImageReader imageReader = valiCodeImg.getImageReader();
        BufferedImage bufferedImage = imageReader.read(0);

        Long fileName = System.currentTimeMillis();
        File outputfile = new File("/Users/hzhonglong" + File.separator + fileName + ".jpg");
        ImageIO.write(bufferedImage, "jpg", outputfile);

        //验证码识别开始
        CodeUtils.SetAuthor("qq2303297757");//设置作者帐号
        CodeUtils jh = new CodeUtils("");


        String id = jh.SendFile("kOOxf3zMdOYL0ONg", outputfile.getAbsolutePath(), 1005, 30, 1, "");
        String answer = "";
        while (answer == null || answer.length() == 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            jh = new CodeUtils("");
            answer = jh.GetAnswer(id);
        }

        username.click();
        username.type("09ba007");
        password.click();
        password.type("aa820316");
        valiCode.click();
        valiCode.type(answer);

        submit.click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Set<Cookie> cookieSet = WEB_CLIENT.getCookieManager().getCookies();

        WEB_CLIENT.addRequestHeader("Accept", "application/json,text/javascript,*/*;q=0.01");
        WEB_CLIENT.addRequestHeader("Referer", "http://112.74.42.50/Admin/index.php/index/index.html");
        WEB_CLIENT.addRequestHeader("X-Requested-With", "XMLHttpRequest");
        WEB_CLIENT.addRequestHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.95 Safari/537.36");
        WEB_CLIENT.addRequestHeader("Connection", "keep-alive");
        WEB_CLIENT.addRequestHeader("Host", "112.74.42.50");


        while (true){
            try {
                String url = "http://112.74.42.50/Admin/index.php/control/getorder/step/3?t=" + System.currentTimeMillis() + "&chRiskMethods=1&pernum=30&start=0&checktype=0&groups=&allaccountid=";
                //HtmlPage page2= WEB_CLIENT.getPage(url);
                WebRequest webRequest = new WebRequest(new URL(url));
                webRequest.setCharset("utf-8");
                HtmlPage page2 = (HtmlPage) WEB_CLIENT.getPage(webRequest);

                String result = StringEscapeUtils.unescapeJava(page2.asText());
                int start = result.indexOf("\"detail\":\"")+"\"detail\":\"".length();
                int end = result.indexOf("\"}");
                System.out.println(result.substring(start,end).replace("</tr>","\r\n").replace("</td> </td>"," ").replace("</td>"," "));



                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * unicode 转字符串
     */
    public static String unicode2String(String unicode) {
        StringBuffer string = new StringBuffer();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);
            // 追加成string
            string.append((char) data);
        }
        return string.toString();
    }
}
