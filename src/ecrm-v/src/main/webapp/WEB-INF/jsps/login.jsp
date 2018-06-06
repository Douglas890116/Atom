<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html lang="en"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="UTF-8">
	<title>登录</title>
	<link rel="stylesheet" href="${statics }/admin/css/component-min.css">
	<link rel="stylesheet" href="${statics }/admin/css/jbox-min.css">
	<link rel="stylesheet" href="${statics }/admin/css/common_login.css">
<script language="javascript"> 
function loadimage(){ 
document.getElementById("randImage").src = "${ctx}/code.img?s="+new Date(); 
} 
</script>
</head>
<body>
	<div class="login">
	
		<form id="objForm" action="${ctx}/EEmployee/login" method="post">
		<!-- <a href="#" class="logo"></a> -->
        <div class="login-inner">
        
			<h1 class="login-title">客户关系管理系统</h1>
			<input name="fingerprintcode" id="fingerprintcode" type="hidden" />
            <div style="text-align: center; vertical-align:middle; margin-bottom: 10px">
				  <input type="radio" name="language" value="zh" checked="checked"/>中文&nbsp;&nbsp;&nbsp;
            	  <input type="radio" name="language" value="en"/>English
			</div>	  
			<div class="login-item mgb20">
				<input type="text" class="clearError" name="loginaccount" id="loginaccount" placeholder="账号" tabindex="1" value="${loginaccount }">
			</div>
			<div class="login-item mgb20">
				<input type="password" class="clearError" name="loginpassword" id="loginpassword" placeholder="密码" tabindex="2" value="${loginpassword }">
			</div>
			<div class="clearfix mgb20">
				<div class="code-img fl">
					<img src="${ctx}/code.img?s=<%=System.currentTimeMillis()%>" class="j-codeReresh" id="randImage" onclick="loadimage();" >
				</div>
				<div class="login-item code fl">
					<input type="text" name="verifyCode" class="clearError" id="verifyCode" placeholder="验证码" data-autohide="1" tabindex="3" >
				</div>
			</div>
			<div id="checkerror" style="color: red;padding: 10px">
				${msg}
			</div>
			<!--  
			<div class="checkbox-group clearfix mgb20">
				<label class="fl"><input type="checkbox" id="rd_remember">记住密码</label>
				<a href="#" class="fr login-forgetlink">忘记密码？</a>
			</div> -->
			
			<div>
				<input type="submit" class="login-btn" tabindex="4" value="登录" id="dl"/>
			</div>
		</div>
		</form>
		
	</div>
	<!-- end login -->


	<script type="text/javascript" src="${statics}/js/jquery-1.8.1.min.js"></script>
	<script type="text/javascript" src="${statics}/js/fingerprint2.js"></script>
    
	<script>
	$(function(){
		
		new Fingerprint2().get(function(result, components){
			$("#fingerprintcode").val(result);
		 });
		
		$("#randImage").click(function(){
			$("#randImage").attr("src", "${ctx}/code.img?s=" + new Date());
		});
		$("#verifyCode").focus(function(){
			$("#randImage").attr("src", "${ctx}/code.img?s=" + new Date());
			$("#verifyCode").val("");
		});
		
		$("#objForm").submit(function(){
			
			if( $.trim($("#loginaccount").val())=="" ) {
				$("#checkerror").html("请输入账号");return false;
			}
			if( $.trim($("#loginpassword").val())=="" ) {
				$("#checkerror").html("请输入密码");return false;
			}
			if( $.trim($("#verifyCode").val())=="" ) {
				$("#checkerror").html("请输入验证码");return false;
			}
			return true;
		});
		
		document.onkeydown = function(e){ 
		    var ev = document.all ? window.event : e;
		    if(ev.keyCode==13) {
		    	$("#dl").click();
		    }
		};
    });
	</script>
	
	<!-- end session hint -->

</body></html>
