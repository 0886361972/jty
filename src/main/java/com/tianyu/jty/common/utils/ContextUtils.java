package com.tianyu.jty.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hl on 2016/7/21.
 */
public abstract class ContextUtils {

    private final static Logger logger = LoggerFactory.getLogger(ContextUtils.class);

    public static ApplicationContext applicationContext;

    public static ServletContext servletcontext;

    private static String webRealPath;


    public static synchronized void init(ServletContext context) {
        logger.info("ApplicationContext 初始化");
        servletcontext = context;
        applicationContext = WebApplicationContextUtils.getWebApplicationContext(context);
    }

    public static void destory(){
        applicationContext = null;
        servletcontext = null;
    }


    /**
     * 通过beanName获取spring对象
     *
     * @param name bean名称
     * @return springBean实体
     */

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) getApplicationContext().getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过对象类型获取spring对象
     *
     * @param clazz 对象clazz
     * @return bean对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBeanForType(Class<T> clazz) {
        String[] beanNames = getApplicationContext().getBeanNamesForType(clazz);
        if (beanNames != null && beanNames.length > 0) {
            return (T) getApplicationContext().getBean(beanNames[0]);
        } else {
            throw new NoSuchBeanDefinitionException(clazz, "没有定义" + clazz.getName() + "类型的bean");
        }
    }

    /**
     * 通过对象类型获取spring对象集合
     *
     * @param clazz 对象clazz
     * @param <T>  对象类型
     * @return  对象实体
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> getBeansForType(Class<T> clazz) {
        String[] beanNames = getApplicationContext().getBeanNamesForType(clazz);
        if (beanNames != null && beanNames.length > 0) {
            List<T> beans = new ArrayList<T>();
            for (String beanName : beanNames) {
                beans.add((T) getApplicationContext().getBean(beanName));
            }
            return beans;
        } else {
            throw new NoSuchBeanDefinitionException(clazz, "没有定义" + clazz.getName() + "类型的bean");
        }
    }


    /**
     * 获得绝对路径信息
     *
     * @param subPath 相对路径
     * @return 绝对路径信息
     */
    public static String getRealPath(String subPath) {
        return ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath(subPath);

    }


    /**
     * 获取工程名称
     * @return  工程名称
     */
    public static String getContextPath() {
        return ContextLoader.getCurrentWebApplicationContext().getServletContext().getServletContextName();
    }


    /**
     * 获取系统上下文
     *
     * @return 系统的上下文信息
     */
    public static ApplicationContext getApplicationContext() {
        if (applicationContext == null) {
            int count = 1;
            while (count <= 3) {
                applicationContext = ContextLoader.getCurrentWebApplicationContext();
                if (applicationContext == null) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count++;
                } else {
                    break;
                }
            }
            if (applicationContext == null) {
                throw new IllegalArgumentException("spring上下文初始化错误！");
            }
        }
        return applicationContext;
    }

    /**
     * 获取根路径
     *
     * @return web应用根目录
     */
    public static String getWebRealPath() {
        if (webRealPath == null) {
            String result = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("");
            int index = result.indexOf("WEB-INF");
            if (index == -1) {
                webRealPath = result;
                return webRealPath;
            }
            result = result.substring(0, index);
            if (result.startsWith("jar")) {
                // 当class文件在jar文件中时，返回"jar:file:/F:/ ..."样的路径
                result = result.substring(10);
            } else if (result.startsWith("file")) {
                // 当class文件在class文件中时，返回"file:/F:/ ..."样的路径
                result = result.substring(6);
            }
            if (result.endsWith("/"))
                result = result.substring(0, result.length() - 1);// 不包含最后的"/"
            if (result.startsWith("/")) {
                result = result.replaceFirst("/", "");
            }

            webRealPath = result;
        }

        return webRealPath;

    }

}