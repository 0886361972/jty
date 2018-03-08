package com.tianyu.jty.jihua.utils;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptJobManager;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.google.common.collect.Maps;
import com.tianyu.jty.common.mapper.JsonMapper;
import com.tianyu.jty.common.utils.Global;
import com.tianyu.jty.common.utils.StringUtils;
import com.tianyu.jty.jihua.entity.LieBiao;
import com.tianyu.jty.jihua.entity.WenZhang;
import com.tianyu.jty.jihua.enumeration.IEnum;
import com.tianyu.jty.jihua.enumeration.LotteryTypeEnum;
import com.tianyu.jty.jihua.enumeration.gd11x5.Gd11x5MaEnum;
import com.tianyu.jty.jihua.enumeration.gd11x5.Gd11x5QiEnum;
import com.tianyu.jty.jihua.enumeration.gd11x5.Gd11x5TypeEnum;
import com.tianyu.jty.jihua.enumeration.gdkl10.Gdkl10MaEnum;
import com.tianyu.jty.jihua.enumeration.gdkl10.Gdkl10QiEnum;
import com.tianyu.jty.jihua.enumeration.gdkl10.Gdkl10TypeEnum;
import com.tianyu.jty.jihua.enumeration.jsk3.Jsk3MaEnum;
import com.tianyu.jty.jihua.enumeration.jsk3.Jsk3QiEnum;
import com.tianyu.jty.jihua.enumeration.jsk3.Jsk3TypeEnum;
import com.tianyu.jty.jihua.enumeration.pk10.Pk10MaEnum;
import com.tianyu.jty.jihua.enumeration.pk10.Pk10QiEnum;
import com.tianyu.jty.jihua.enumeration.pk10.Pk10TypeEnum;
import com.tianyu.jty.jihua.enumeration.ssc.SscMaEnum;
import com.tianyu.jty.jihua.enumeration.ssc.SscQiEnum;
import com.tianyu.jty.jihua.enumeration.ssc.SscTypeEnum;
import com.tianyu.jty.jihua.service.LieBiaoService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by hzhonglog on 17/5/15.
 */
public class Seeker158Utils {
    private final static Logger logger = LoggerFactory.getLogger(Seeker158Utils.class);
    private static final String ROOT_URL = "http://15876.com";
    private static WebClient WEB_CLIENT = null;

    private static ArrayBlockingQueue<WebClient> webClientsQueue = null;

    private static WebClient getWebClient() {
        if (WEB_CLIENT == null) {
            WEB_CLIENT = new WebClient(BrowserVersion.CHROME);
            WEB_CLIENT.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true
            WEB_CLIENT.getOptions().setCssEnabled(false); //禁用css支持
            WEB_CLIENT.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常
            WEB_CLIENT.getOptions().setTimeout(3000); //设置连接超时时间 ，这里是10S。如果为0，则无限期等待
            WEB_CLIENT.setJavaScriptTimeout(3000);
        } else {
            if (null == webClientsQueue) {
                webClientsQueue = new ArrayBlockingQueue(158);
            }
            if (webClientsQueue.size() <= 1) {
                logger.debug("－－－－－－－－》初始化连接池webclient《－－－－－－－－－－");
                for (int i = 0; i < 150; i++) {
                    WebClient webClient = new WebClient(BrowserVersion.CHROME);
                    webClient.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true
                    webClient.getOptions().setCssEnabled(false); //禁用css支持
                    webClient.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常
                    webClient.getOptions().setTimeout(3000); //设置连接超时时间 ，这里是10S。如果为0，则无限期等待
                    webClient.setJavaScriptTimeout(3000);
                    Set<Cookie> cookieSet = WEB_CLIENT.getCookieManager().getCookies();
                    for (Cookie cookie : cookieSet) {
                        webClient.getCookieManager().addCookie(cookie);
                    }
                    webClientsQueue.offer(webClient);
                }
            }
            WebClient webClient = null;
            try {
                logger.debug("正在获取webClient连接。当前剩余:" + webClientsQueue.size());
                webClient = webClientsQueue.take();
            } catch (InterruptedException e) {
                logger.error("【获取webClient对象失败】", e);
                webClient = webClientsQueue.remove();
            } finally {
                return webClient;
            }
        }
        return WEB_CLIENT;
    }


    public static synchronized void closeJavaScript(Page page, WebClient webClient) {
        String url = "";
        try {
            if (null != page) {
                JavaScriptJobManager manager = page.getEnclosingWindow().getJobManager();
                manager.shutdown();
                page.cleanUp();
                url = page.getUrl().toString();

            }
        } catch (Exception ex) {
            logger.error("closeJavaScript错误", ex);
        }
        try {
            if (null != webClient) {

                webClientsQueue.offer(webClient);
                webClient.close();
                logger.debug("归还webClient-,return url->>" + url);
            }
        } catch (Exception ex) {
            logger.error("closeJavaScript错误", ex);
        }
    }

    /**
     * 读取并解析文章
     *
     * @param lieBiao
     * @return
     */

    public static WenZhang getWenZhang(LieBiao lieBiao) {
        WebClient webClient = getWebClient();
        Page page = null;
        String result = null;

        try {
            page = webClient.getPage(lieBiao.getWenZhangUrl());
        } catch (IOException e) {
            logger.error("读取文章错误url->" + lieBiao.getWenZhangUrl(), e);
        }
        if (null != page) {
            result = page.getWebResponse().getContentAsString();

        }
        closeJavaScript(page, webClient);
        if (StringUtils.isNotEmpty(result)) {
            Document doc = Jsoup.parse(result);
            if (null != doc) {
                Elements elements = doc.select("div.detail");
                if (null != elements && !elements.isEmpty()) {
                    Element contentElement = elements.first();
                    if (null != contentElement && StringUtils.isNotEmpty(contentElement.text())) {
                        Elements h2s = contentElement.select("h2");
                        Elements h6s = contentElement.select("h6");
                        h6s.remove();
                        h2s.remove();

                        WenZhang wenZhang = new WenZhang();
                        wenZhang.setUpdateDate(new Date());
                        wenZhang.setCreateDate(new Date());
                        wenZhang.setContent(contentElement.html());
                        wenZhang.setCategoryDesc(lieBiao.getCategoryDesc());
                        wenZhang.setSourceUrl(lieBiao.getWenZhangUrl());
                        wenZhang.setCategoryType(lieBiao.getCategoryType());
                        wenZhang.setLieId(lieBiao.getId());
                        wenZhang.setLotteryType(lieBiao.getLotteryType());
                        wenZhang.setMa(lieBiao.getMa());
                        wenZhang.setQi(lieBiao.getQi());
                        wenZhang.setTitle(lieBiao.getTitle());
                        return wenZhang;
                    }
                }
            }
        }
        return null;
    }


    public static void getAndSaveListByPage(LieBiaoService service, LotteryTypeEnum lotteryType, IEnum types[], IEnum mas[], IEnum qis[], int page) {
        for (IEnum type : types) {
            if (type == type.getOther()) continue;
            for (IEnum ma : mas) {
                if (ma == ma.getOther()) continue;
                for (IEnum qi : qis) {
                    if (qi == qi.getOther()) continue;
                    try {
                        List<LieBiao> lieBiaos = Seeker158Utils.getLieBiao(lotteryType, type, qi, ma, page);
                        service.saveList(lieBiaos);
                        Thread.sleep(Global.getSeekSleep());
                    } catch (Exception e) {
                        logger.error("--->>>读取列表" + lotteryType.getDescription() + "失败!<<<-------");
                    }
                }
            }
        }
    }


    /**
     * 读取最新的列表
     *
     * @param service
     * @param lotteryType
     * @param types
     * @param mas
     * @param qis
     */
    public static void getAndSaveLastList(LieBiaoService service, LotteryTypeEnum lotteryType, IEnum types[], IEnum mas[], IEnum qis[]) {
        getAndSaveListByPage(service, lotteryType, types, mas, qis, 0);
    }

    /**
     * 读取最新的列表
     *
     * @param service
     * @param lotteryType
     */
    public static void getAndSaveLastList(LieBiaoService service, LotteryTypeEnum lotteryType) {
        List<LieBiao> lieBiaos = getLieBiao(lotteryType, 0);
        service.saveList(lieBiaos);
    }

    protected static List<LieBiao> getLieBiao(LotteryTypeEnum lotteryType, Integer page) {
        return getLieBiao(lotteryType, null, null, null, page);
    }

    /**
     * 读取第几页的列表
     *
     * @param lotteryType
     * @param type
     * @param qi
     * @param ma
     * @param page
     * @return
     */
    protected static List<LieBiao> getLieBiao(LotteryTypeEnum lotteryType, IEnum type, IEnum qi, IEnum ma, Integer page) {
        if (!StringUtils.isEmpty(ROOT_URL)) {
            StringBuilder url = packUrl(lotteryType);
            if (null != type) {
                url.append("type=").append(type.getCode());
            }
            if (null != qi) {
                url.append("&qi=").append(qi.getCode());
            }
            if (null != ma) {
                url.append("&number=").append(ma.getCode());
            }
            if (null != page && page > 0) {
                url.append("&page=").append(String.valueOf(page));
            }

            boolean parseEnumFromTitle = false;
            if (null == type || null == qi || ma == ma) {
                parseEnumFromTitle = true;
            }

            JsonMapper jsonMapper = new JsonMapper();
            List<LieBiao> lieBiaos = new ArrayList<>();
            String result = getLieBiaoString(url.toString());

            //重试读取一次
            if (StringUtils.isEmpty(result) || result.contains("yunsuo_session_verify")) {
                try {
                    Thread.sleep(Global.getSeekSleep());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                result = getLieBiaoString(url.toString());
            }

            if (StringUtils.isNotEmpty(result)) {
                Map map = jsonMapper.fromJson(result, Map.class);
                if (null != map && map.size() > 0) {
                    String data = (String) map.get("data");
                    Document doc = Jsoup.parse(data);
                    Elements list = doc.select("li");
                    for (int i = 0; i < list.size(); i++) {
                        try {
                            Elements as = list.get(i).select("a");
                            if (null == as || as.size() == 0) continue;
                            Element a = as.get(0);
                            String title = a.text();
                            if (StringUtils.isNotEmpty(title)) {
                                if (parseEnumFromTitle) {
                                    Map<String, IEnum> tqm = parseFromTitle(lotteryType, title);
                                    type = tqm.get(KEY_TYPE);
                                    qi = tqm.get(KEY_QI);
                                    ma = tqm.get(KEY_MA);
                                }


                                LieBiao lieBiao = new LieBiao();
                                lieBiao.setCreateDate(new Date());
                                lieBiao.setUpdateDate(new Date());
                                if (null != type) {
                                    lieBiao.setCategoryDesc(type.getDescription());
                                    lieBiao.setCategoryType(type.getCode());
                                    title = title.replace(type.getDescription(), "").trim();
                                } else {
                                    continue;
                                }
                                lieBiao.setLotteryType(lotteryType.getDescription());
                                if (null != ma) {
                                    lieBiao.setMa(ma.getDescription());
                                }
                                if (qi != null) {
                                    lieBiao.setQi(qi.getDescription());
                                }


                                lieBiao.setWenZhangUrl(a.attr("href"));
                                lieBiao.setSourceUrl(url.toString());
                                lieBiao.setStatus(0);
                                lieBiao.setPage(page);

                                lieBiao.setTitle(title);
                                lieBiaos.add(lieBiao);
                            } else {
                                logger.error("[<<无法读取列表的文章标题]");
                            }
                        } catch (Exception ex) {
                            logger.error("解析文章列表失败-->" + lotteryType.getDescription(), ex);
                        }
                    }
                }
            }
            return lieBiaos;
        }
        return null;
    }


    private final static String KEY_TYPE = "TYPE";
    private final static String KEY_QI = "QI";
    private final static String KEY_MA = "MA";

    private static Map<String, IEnum> parseFromTitle(LotteryTypeEnum lotteryType, String title) {
        Map<String, IEnum> map = Maps.newHashMap();

        if (StringUtils.isEmpty(title)) {
            return map;
        }

        IEnum types[] = null;
        IEnum qis[] = null;
        IEnum mas[] = null;
        switch (lotteryType) {
            case SSC: {
                types = SscTypeEnum.values();
                qis = SscQiEnum.values();
                mas = SscMaEnum.values();
                break;
            }
            case JSK3: {
                types = Jsk3TypeEnum.values();
                qis = Jsk3QiEnum.values();
                mas = Jsk3MaEnum.values();
                break;
            }
            case PK10: {
                types = Pk10TypeEnum.values();
                qis = Pk10QiEnum.values();
                mas = Pk10MaEnum.values();
                break;
            }
            case GD11X5: {
                types = Gd11x5TypeEnum.values();
                qis = Gd11x5QiEnum.values();
                mas = Gd11x5MaEnum.values();
                break;
            }
            case GDKL10: {
                types = Gdkl10TypeEnum.values();
                qis = Gdkl10QiEnum.values();
                mas = Gdkl10MaEnum.values();
                break;
            }
            default:
                return null;
        }
        for (IEnum type : types) {
            if (title.contains(type.getDescription())) {
                map.put(KEY_TYPE, type);
                break;
            }
        }

        for (IEnum qi : qis) {
            if (title.contains(qi.getDescription())) {
                map.put(KEY_QI, qi);
                break;
            }
        }
        for (IEnum ma : mas) {
            if (title.contains(ma.getDescription())) {
                map.put(KEY_MA, ma);
                break;
            }
        }
        return map;
    }

    private static String getLieBiaoString(String url) {
        WebClient webClient = getWebClient();
        Page jsonPage = null;
        String result = null;
        try {
            jsonPage = webClient.getPage(url);
            WebResponse response = jsonPage.getWebResponse();
            result = response.getContentAsString();
        } catch (Exception ex) {
            logger.error("读取文章列表失败,url->" + url, ex);
        } finally {
            closeJavaScript(jsonPage, webClient);
        }

        return result;
    }

    private static StringBuilder packUrl(LotteryTypeEnum lotteryType) {
        StringBuilder url = new StringBuilder();
        url.append(ROOT_URL);
        if (!ROOT_URL.endsWith("/")) {
            url.append("/");
        }
        switch (lotteryType) {
            case SSC:
                url.append("ajaxMore.html?");
                break;
            case PK10:
                url.append("pk10/ajaxMore.html?");
                break;
            case GD11X5:
                url.append("gd11x5/ajaxMore.html?");
                break;
            case JSK3:
                url.append("jsk3/ajaxMore.html?");
                break;
            case GDKL10:
                url.append("gdklsf/ajaxMore.html?");
                break;
            default:
                return null;
        }
        return url;
    }

    public static void main(String[] args) {
        //ssc();
        //pk10();
        //gd11x5();
        //jsk3();
        //gdkl10();
        wenTest();
    }


    private static void wenTest() {
        LieBiao lieBiao = new LieBiao();
        lieBiao.setLotteryType("广东快乐10分");
        lieBiao.setCategoryType("N1");
        lieBiao.setQi("一期");
        lieBiao.setMa("6码");
        lieBiao.setSourceUrl("http://15876.com/gdklsf/ajaxMore.html?type=N1&qi=1&number=6");
        lieBiao.setStatus(0);
        lieBiao.setWenZhangUrl("http://15876.com/gdklsf/list-1493.html");
        lieBiao.setCategoryDesc("第一球");
        lieBiao.setTitle("广东快乐十分 043-043解除1期6码计划");


    }

    private static void ssc() throws IOException {
        List<LieBiao> list = getLieBiao(LotteryTypeEnum.SSC, SscTypeEnum.N1, SscQiEnum.Q1, SscMaEnum.M5, 1);
        for (LieBiao lieBiao : list) {
            System.out.println(lieBiao.toString());
        }
    }

    private static void pk10() throws IOException {
        List<LieBiao> list = getLieBiao(LotteryTypeEnum.PK10, Pk10TypeEnum.N1, Pk10QiEnum.Q1, Pk10MaEnum.M5, 1);
        for (LieBiao lieBiao : list) {
            System.out.println(lieBiao.toString());
        }
    }

    private static void gd11x5() throws IOException {
        List<LieBiao> list = getLieBiao(LotteryTypeEnum.GD11X5, Gd11x5TypeEnum.N1, Gd11x5QiEnum.Q1, Gd11x5MaEnum.M5, 1);
        for (LieBiao lieBiao : list) {
            System.out.println(lieBiao.toString());
        }
    }

    private static void jsk3() throws IOException {
        List<LieBiao> list = getLieBiao(LotteryTypeEnum.JSK3, Jsk3TypeEnum.DuDan, Jsk3QiEnum.Q1, Jsk3MaEnum.Z1, 1);
        for (LieBiao lieBiao : list) {
            System.out.println(lieBiao.toString());
        }
    }

    private static void gdkl10() throws IOException {
        List<LieBiao> list = getLieBiao(LotteryTypeEnum.GDKL10, Gdkl10TypeEnum.N1, Gdkl10QiEnum.Q1, Gdkl10MaEnum.M6, 0);
        for (LieBiao lieBiao : list) {
            System.out.println(lieBiao.toString());
        }
    }

}
