<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>管理系统 | 手机版</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="format-detection" content="telephone=no">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="alternate icon" type="image/png" href="${statics}/assets/i/favicon.png">
<link rel="stylesheet" href="${statics}/assets/css/amazeui.min.css" />
<style>
.header {
	text-align: center;
}

.header h1 {
	font-size: 200%;
	color: #333;
	margin-top: 30px;
}

.header p {
	font-size: 14px;
}

.error {
	color: red;
}

div.all {
	position: relative;
}

#code {
	position: absolute;
	right: 1px;
	top: 0;
	bottom: 0;
	margin: auto;
	width: 120px;
	height: 36px;
}
</style>
</head>
<body>
	<div class="header">
		<div class="am-g">
			<h1>客户关系管理系统</h1>
			<p>欢迎进入管理系统</p>
		</div>
		<hr />
	</div>
	<div class="am-g">
		<div class="am-u-lg-6 am-u-md-8 am-u-sm-centered">
			<form id="objForm" action="${ctx}/h5/login" method="post" class="am-form">
				<label for="loginaccount">账号:</label>
				<input type="text" name="loginaccount" placeholder="账号" value="${loginaccount}"/>
				<span class="error">${message}</span>
				<br />

				<label for="loginpassword">密码:</label>
				<input type="password" name="loginpassword" placeholder="密码"/>
				<span class="error">${message}</span>
				<br />

				<label for="verifyCode">验证码:</label>
				<div class="all">
					<input type="text" name="verifyCode" placeholder="验证码" id="verifyCode" />
					<span><img id="code" src="${ctx}/code.img" title="点击切换图片" alt="点击切换图片"/></span>
				</div>
				<span class="error">${msg}</span>
				<br />
				
				<div class="am-cf">
					<input type="submit" value=" 登 录 " 	class="am-btn am-btn-primary am-btn-block"/>
				</div>
			</form>
			<hr>
		</div>
	</div>
</body>
<script type="text/javascript" src="${statics}/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript">
$(function(){
	$("#code,#changecode").click(function(){
		$("#code").attr("src", "${ctx}/code.img?" + new Date());
	});
	
	$("#objForm").submit(function(){
		var issubmit = true;
		$("form input").each(function(i){
			if($.trim($(this).val())==""){
				issubmit=false;
				$(".error").eq(i).html("请输入"+$(this).attr("placeholder"));
			}else{
				$(".error").eq(i).html("");
			}
		});
		return issubmit;
	});
});
</script>
</html>