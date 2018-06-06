<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="ctx" value="<%=request.getContextPath()%>" />

<html>
<head>

<meta charset="UTF-8">

<title>正在登录沙龙视讯....</title>


</head>

<body>
正在登录沙龙视讯....
<form method="post" name="form1" action="${url }">
	<input name="username" value="${username }" type="hidden"/>
	<input name="token" value="${token }" type="hidden"/>
	<input name="lobby" value="${lobby }" type="hidden"/>
	<input name="lang" value="${lang }" type="hidden"/>
	<input name="mobile" value="${mobile }" type="hidden"/>
</form>
  
</body>

</html>
<script>
form1.submit();
</script>