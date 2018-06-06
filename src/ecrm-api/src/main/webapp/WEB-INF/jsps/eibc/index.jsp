<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="ctx" value="<%=request.getContextPath()%>" />

<html>
<head>

<meta charset="UTF-8">

<title>正在登录....</title>

<%
   // 创建一个Cookie,包括(key,value).
   Cookie cookie = new Cookie("g", request.getAttribute("sessionToken").toString());
   
   // 设置Cookie的生命周期,如果设置为负值的话,关闭浏览器就失效.
   //cookie.setMaxAge(60*60*24*365);
   
   // 设置Cookie路径,不设置的话为当前路径(对于Servlet来说为request.getContextPath() + web.xml里配置的该Servlet的url-pattern路径部分)
   cookie.setPath("/"); 
   
   cookie.setDomain(request.getAttribute("domain").toString());
   // 输出Cookie
   response.addCookie(cookie);
   
   
   
%>

</head>

<body>
正在登录....
	
  
</body>

</html>
<script>
//http://mkt.ib.{您的网域}/Deposit_ProcessLogin.aspx?lang=cs
		
//document.cookie="g=${sessionToken}; path=/; domain=${domain};";



location.href = "${url }";
</script>