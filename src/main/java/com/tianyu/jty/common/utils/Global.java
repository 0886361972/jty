/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.tianyu.jty.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.Maps;
import com.tianyu.jty.caipiao.entity.CPK;
import com.tianyu.jty.common.mapper.JsonMapper;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * 全局配置类
 *
 * @author ty
 * @date 2014年12月31日
 */
public class Global {


    public static int getSeekSleep() {
        return NumberUtils.toInt(getConfig("SeekSleep"), 500);
    }

    /**
     * 保存全局属性值
     */
    private static Map<String, String> map = Maps.newHashMap();

    /**
     * 属性文件加载对象
     */
    private static PropertiesLoader propertiesLoader = new PropertiesLoader("application.properties");

    /**
     * 获取配置
     */
    public static String getConfig(String key) {
        String value = map.get(key);
        if (value == null) {
            value = propertiesLoader.getProperty(key);
            map.put(key, value);
        }
        return value;
    }

    /**
     * 获取管理端根路径
     */
    public static String getAdminPath() {
        return getConfig("adminPath");
    }

    /**
     * 获取前端根路径
     */
    public static String getFrontPath() {
        return getConfig("frontPath");
    }

    /**
     * 获取URL后缀
     */
    public static String getUrlSuffix() {
        return getConfig("urlSuffix");
    }

    public static String getOpenCai(String urlAll) {
        return getResultFromHttp(urlAll);
    }

    public static String getHaoService(String urlAll) {
        return getResultFromHttp(urlAll);
    }


    /**
     * 是否开启彩票开奖采集
     *
     * @return
     */
    public static boolean getLotteryCaiji() {
        return "1".equals(getConfig("caipiao.caiji"));
    }


    public static String get158Url() {
        return getConfig("seeker.go15876.url");
    }

    /**
     * @param urlAll:请求接口
     * @return 返回json结果
     */
    public static String getResultFromHttp(String urlAll) {
        if (StringUtils.isEmpty(urlAll) || !urlAll.startsWith("http")) {
            return null;
        }

        String charset = "UTF-8";
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";// 模拟浏览器
        try {
            URL url = new URL(urlAll);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            connection.setRequestProperty("User-agent", userAgent);
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, charset));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<CPK> String2OpenCai(String jsonResult) {
        final JsonMapper jsonMapper = new JsonMapper(JsonInclude.Include.NON_NULL);
        List<CPK> result = new LinkedList();
        try {
            Map map = jsonMapper.fromJson(jsonResult, Map.class);
            if (!map.isEmpty()) {
                if (map.containsKey("data")) {
                    List<Map> datas = (List<Map>) map.get("data");
                    for (int i = 0; i < datas.size(); i++) {
                        Map<String, String> tmp = datas.get(i);
                        CPK cpk = new CPK();
                        cpk.setKey(tmp.get("expect"));
                        cpk.setNumber(tmp.get("opencode"));
                        cpk.setDateline(tmp.get("opentime"));
                        result.add(cpk);
                    }
                }
            }
        } catch (NullPointerException ex) {

        }
        return result;
    }

    public static List<CPK> String2HaoService(String jsonResult) {
        final JsonMapper jsonMapper = new JsonMapper(JsonInclude.Include.NON_NULL);
        List<CPK> result = new LinkedList();
        try {
            Map map = jsonMapper.fromJson(jsonResult, Map.class);
            if (!map.isEmpty()) {
                if (map.containsKey("result")) {
                    List<Map> datas = (List<Map>) map.get("result");
                    for (int i = 0; i < datas.size(); i++) {
                        Map<String, String> tmp = datas.get(i);
                        CPK cpk = new CPK();
                        cpk.setKey(tmp.get("lottery_no"));
                        cpk.setNumber(tmp.get("lottery_nums"));
                        cpk.setDateline(tmp.get("lottery_time"));
                        result.add(cpk);
                    }
                }
            }
        } catch (NullPointerException ex) {
        }
        return result;
    }


    public static List<CPK> String2CPK(String jsonResult) {
        final JsonMapper jsonMapper = new JsonMapper(JsonInclude.Include.NON_NULL);

        List<CPK> result = new LinkedList();

        try {
            Map map = jsonMapper.fromJson(jsonResult, Map.class);
            if (!map.isEmpty()) {
                Iterator iterator = map.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry tmp = (Map.Entry) iterator.next();

                    CPK cpk = new CPK();
                    cpk.setKey(String.valueOf(tmp.getKey()));
                    Map<String, String> value = (Map<String, String>) tmp.getValue();
                    cpk.setNumber(value.get("number"));
                    cpk.setDateline(value.get("dateline"));
                    result.add(cpk);
                }
            }
        } catch (NullPointerException ex) {

        }

        return result;
    }

    public static String getCpkUrl(String name) {
        String url = "http://api.caipiaokong.com/lottery/";
        String uid = getConfig("cpk_uid");
        String token = getConfig("cpk_token");
        if (StringUtils.isEmpty(uid) || StringUtils.isEmpty(token)) {
            return null;
        }

        url += "?name=" + name;
        url += "&format=json";// 数据格式，此文件仅支持json
        url += "&uid=" + uid;
        url += "&token=" + token;
        url += "&num=" + getConfig("cpk_num");
        return url;
    }


    public static long sub20ForQs(String qs) {
        if (StringUtils.isNotEmpty(qs) && qs.startsWith(DateUtils.getYear())) {
            String tmp = qs.substring(2);
            return Long.parseLong(tmp);
        }
        return Long.parseLong(qs);
    }

    /**
     * 开采网的API地址
     *
     * @param name
     * @return
     */
    public static String getOpencaiUrl(String name) {
        String url = getConfig("open.apius.url");
        if (StringUtils.isEmpty(url)) {
            return null;
        }

        url += getConfig("open.apius.token") + "/";
        url += name + "-20.json";
        return url;
    }

    public static String getHaoserviceUrl(String name) {
        String url = getConfig("haoservice.url");
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        String day = DateUtils.formatDate(new Date());

        if ((DateUtils.getTime().startsWith("00") || DateUtils.getTime().startsWith("01")) && !"cqssc".equals(name) && !"cqxync".equals(name)) {
            day = DateUtils.formatDate(DateUtils.addDays(new Date(), -1), "yyyy-MM-dd");
        }

        sb.append(url).append("?")
                .append("spell=").append(name).append("&date=")
                .append(day).append("&key=").append(getConfig("haoservice.token"));
        return sb.toString();
    }
}
