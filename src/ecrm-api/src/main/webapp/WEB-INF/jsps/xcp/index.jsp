<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="ctx" value="<%=request.getContextPath()%>" />
<c:set var="statics" value="${ctx}/static"></c:set>

<html>
<head>
<script type="text/javascript" src="${statics }/js/jquery-1.8.1.min.js"></script>
<meta charset="UTF-8">

<title>正在登录火星彩票....</title>


</head>

<body>
正在登录火星彩票....
  
</body>

</html>
<script>
$.ajax({
	type:"post",
	url:"${url}",
	dataType:"json", 
	success: function(data) {
		if(date.code == 200){
			window.location.href = data.msg;
		}else{
			alert("登录游戏失败");
		}
	}
});

</script>