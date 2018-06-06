<%@page import="org.apache.ibatis.scripting.xmltags.ForEachSqlNode"%>
<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:set var="ctx" value="${pageContext['request'].contextPath}"></c:set>
<c:set var="statics" value="${ctx}/static"></c:set>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<title>即将支付</title>
</head>

	<%
		String url_for_get = (String)request.getAttribute("url");
		String url_for_post = url_for_get.substring(0,url_for_get.indexOf("?"));
		String url_params = url_for_get.substring(url_for_get.indexOf("?")+1);
		String[] url_params_array = url_params.split("&");
		
		//	默认是post方式，银宝是get方式
		String methodName = url_for_get.indexOf("YbPayCallback") > -1 ? "get" : "post";
		
		//华仁支付也是get
		if(url_for_get.indexOf("http://api.hr-pay.com/PayInterface.aspx") > -1  ) {
			methodName = "get";
		}
		if(url_for_get.startsWith("https://pay.dinpay.com/gateway") ) {
			url_for_post = "https://pay.dinpay.com/gateway?input_charset=UTF-8";
		}
		//System.err.println("methodName==="+methodName+"===url_for_get="+url_for_get);
	%>

<body onload="form.submit()">
	
    <form class="form-signin" id="form" action="<%=url_for_post %>" method="<%=methodName %>">
    <%
    	for(String s : url_params_array){
    		String[] keyvalue = s.split("=");
    		
    		if(keyvalue.length > 0){
    			int index = s.indexOf("=");
    			String name = s.substring(0, index);
    			String value = s.substring(index + 1);
		%>
			<input type="hidden" name="<%=name%>" value="<%=value%>" /> 
		<%
    		}else{
    	%>
    		<input type="hidden" name="<%=keyvalue[0] %>" value="" />
    	<%
    		}
    		
    	}
    %>
    </form>
</body>
</html>