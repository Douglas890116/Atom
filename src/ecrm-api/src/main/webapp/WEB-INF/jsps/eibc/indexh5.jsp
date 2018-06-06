<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="ctx" value="<%=request.getContextPath()%>" />

<html>
<head>

<meta charset="UTF-8">

<title>正在登录....</title>


</head>

<body>
正在登录....
	
  
</body>

</html>
<script>
//http://ismart.ib.{您的网域}/Deposit_ProcessLogin.aspx?lang=en&st=f96b6c7f-e737-456f-9f01-bbd504525578
	
//H5不需要写token，直接在URL里面		
//document.cookie="g=${sessionToken}; path=/; domain=${domain};";

location.href = "${url }";
</script>