package com.tianyu;

import com.tianyu.jty.common.mapper.JsonMapper;
import com.tianyu.jty.common.utils.StringUtils;
import com.tianyu.jty.jihua.entity.LieBiao;
import com.tianyu.jty.system.utils.HttpUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by hzhonglog on 17/5/15.
 */
public class Test implements PageProcessor {

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(1).setSleepTime(1000);

    @Override
    public void process(Page page) {
        String text = page.getRawText();
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {


        JsonMapper jsonMapper = new JsonMapper();
        String result = HttpUtils.sendGet("http://15876.com/ajaxMore.html?type=N1&qi=&number=&page=5");
        if (StringUtils.isNotEmpty(result)) {
            Map map = jsonMapper.fromJson(result, Map.class);
            if (null != map && map.size() > 0) {
                String data = (String) map.get("data");
                Document doc = Jsoup.parse(data);
                Elements list = doc.select("li");
                for (int i = 0; i < list.size(); i++) {
                    Elements as = list.get(i).select("a");
                    LieBiao lieBiao = new LieBiao();

                }
            }
        }

    }

    public static String toGBK(String unicodeStr) {
        try {
            String gbkStr = new String(unicodeStr.getBytes("ISO8859-1"), "GBK");
            return gbkStr;
        } catch (UnsupportedEncodingException e) {
            return unicodeStr;
        }
    }

    public static String ascii2native(String ascii) {
        int n = ascii.length() / 6;
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0, j = 2; i < n; i++, j += 6) {
            String code = ascii.substring(j, j + 4);
            char ch = (char) Integer.parseInt(code, 16);
            sb.append(ch);
        }
        return sb.toString();
    }


}
