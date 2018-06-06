<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>我的指纹</title>

</head>

<body>

<h1>您的当前指纹是：</h1>
<h1 id="fingerprintcode"></h1>

<script type="text/javascript" src="${statics}/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript" src="${statics}/js/fingerprint2.js"></script>
<script type="text/javascript">
$(function(){
	
	new Fingerprint2().get(function(result, components){
			$("#fingerprintcode").text(result);
		 });
	
});
</script>
</body>
</html>
