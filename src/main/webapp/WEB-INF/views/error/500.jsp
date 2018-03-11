<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>
<%@ page import="com.tianyu.jty.exception.CaipiaoException" %>
<%response.setStatus(200);%>

<%
	Throwable ex = null;
	if (exception != null)
		ex = exception;
	if (request.getAttribute("javax.servlet.error.exception") != null)
		ex = (Throwable) request.getAttribute("javax.servlet.error.exception");

	//记录日志
	Logger logger = LoggerFactory.getLogger("500.jsp");
	logger.error(ex.getMessage(), ex);

	boolean isBizException = false;

	String bizExceptionMessage = null;
	if(ex instanceof CaipiaoException){
		isBizException = true;
		bizExceptionMessage = ex.getMessage();
	}
%>
<c:set var="isBizException" value="<%= isBizException%>"/>
<c:set var="bizExceptionMessage" value="<%= bizExceptionMessage%>"/>
<!DOCTYPE html>
<html>
<head>
	<title>
		<c:if test="${!isBizException}">500 - 系统内部错误</c:if>
		<c:if test="${isBizException}">提示</c:if>
	</title>
</head>

<body>
    <c:if test="${!isBizException}"><h2>500 - 系统发生内部错误.</h2></c:if>
	<c:if test="${isBizException}"><h2>${bizExceptionMessage}</h2></c:if>
</body>
</html>
