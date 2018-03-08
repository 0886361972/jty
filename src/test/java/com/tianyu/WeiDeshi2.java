package com.tianyu;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.tianyu.jty.common.utils.DateUtils;

import java.io.IOException;

/**
 * Created by hzhonglog on 16/12/16.
 */
public class WeiDeshi2 {
    public static void main(String[] args) throws IOException {
        WebClient WEB_CLIENT = new WebClient(BrowserVersion.CHROME);
        WEB_CLIENT.getOptions().setRedirectEnabled(true);
        WEB_CLIENT.getOptions().setJavaScriptEnabled(false); //启用JS解释器，默认为true
        WEB_CLIENT.getOptions().setCssEnabled(false); //禁用css支持
        WEB_CLIENT.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常
        WEB_CLIENT.getOptions().setTimeout(20000); //设置连接超时时间 ，这里是10S。如果为0，则无限期等待

        Cookie cookie =new Cookie("112.74.42.50","PHPSESSID","a43psfdl60knrea8ue2qre9t13");
        Cookie cookie2 =new Cookie("112.74.42.50","think_language","zh-CN");
        WEB_CLIENT.getCookieManager().addCookie(cookie);
        WEB_CLIENT.getCookieManager().addCookie(cookie2);

        WEB_CLIENT.addRequestHeader("Accept","application/json,text/javascript,*/*;q=0.01");
        WEB_CLIENT.addRequestHeader("Referer","http://112.74.42.50/Admin/index.php/index/index.html");
        WEB_CLIENT.addRequestHeader("X-Requested-With","XMLHttpRequest");
        WEB_CLIENT.addRequestHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.95 Safari/537.36");
        WEB_CLIENT.addRequestHeader("Connection","keep-alive");
        WEB_CLIENT.addRequestHeader("Host","112.74.42.50");

        HtmlPage page= WEB_CLIENT.getPage("http://112.74.42.50/Admin/index.php/control/getorder/step/3?t="+ System.currentTimeMillis()+"&chRiskMethods=1&pernum=30&start=0&checktype=0&groups=&allaccountid=");
        System.out.println(page.asText());

    }
}
