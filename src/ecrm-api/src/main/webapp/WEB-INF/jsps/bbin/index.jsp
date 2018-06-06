<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="ctx" value="<%=request.getContextPath()%>" />

<html>
<head>

<meta charset="UTF-8">

<title>正在登录波音BBIN....</title>


</head>

<body>
正在登录波音BBIN....
<form method="post" name="form1" action="${url }">
	<input name="website" value="${website }" type="hidden"/>
	<input name="username" value="${username }" type="hidden"/>
	<input name="password" value="${password }" type="hidden"/>
	<input name="uppername" value="${uppername }" type="hidden"/>
	<input name="key" value="${key }" type="hidden"/>
	
	<!-- 直达视讯 -->
	<input name="page_site" value="${page_site }" type="hidden"/>
	<input name="page_present" value="${page_present }" type="hidden"/>
</form>
  
</body>

</html>
<script>
form1.submit();
</script>