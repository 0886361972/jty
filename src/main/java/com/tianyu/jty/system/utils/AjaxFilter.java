package com.tianyu.jty.system.utils;

import com.tianyu.jty.common.utils.StringUtils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AjaxFilter implements Filter{

	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	public void doFilter(ServletRequest servletRequestt, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
		 HttpServletRequest request=(HttpServletRequest) servletRequestt;
		 HttpServletResponse response=(HttpServletResponse) servletResponse;
		 
		 String currentURL = request.getRequestURI();//取得根目录所对应的绝对路径:
		 if (StringUtils.isNotEmpty(currentURL)){
			 //String targetURL = currentURL.substring(currentURL.indexOf("/", 1), currentURL.length());  //截取到当前文件名用于比较
			 if (currentURL.endsWith(".front")){
				 response.setHeader("Access-Control-Allow-Origin", "*");
				 response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
				 response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
				 response.setHeader("Access-Control-Expose-Headers", "x-requested-with");
				 response.addHeader("Access-Control-Allow-Headers", "Content-Type, authorization,token");
			 }

		 }


		 String ajaxSubmit = request.getHeader("X-Requested-With");
		 if(ajaxSubmit != null && ajaxSubmit.equals("XMLHttpRequest")){
			 if (request.getSession(false) == null) {
				 response.setHeader("sessionstatus", "timeout");
				 response.getWriter().print("sessionstatus");
				 return;
			 }
		 }


		 chain.doFilter(servletRequestt, servletResponse);
	}

	public void destroy() {
		
	}
}
