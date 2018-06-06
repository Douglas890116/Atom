<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>系统登录</title>
<style type="text/css">
body,dd,li,dl,dt,h1,h2,h3,h4,h5,h6,li,ol,p,ul,img{
	margin: 0;
	padding: 0;
	list-style:none;   font-family:"微软雅黑";
	overflow: hidden;
}
body{background-color: #F2F2F2;}
.admin_l_bg{ background:url(${statics }/img/body_bg.jpg) center no-repeat; width:100%;  height:960px;}
.a_c_conter{ width:1004px; margin:0 auto}
.c_logo img{ padding-top:25px;}
.c_c_right{  width: 378px;
    float: right;
    margin-top: 236px;}
.c_btn_kt { clear:both;}
.c_btn_kt button { background:#4da807; border:none; height:36px; line-height:36px; text-align:center; color:#fff; font-weight:bold; border-radius:3px; cursor:pointer;width:256px; margin-left:60px;}
.c_btn_kt button:hover { background:#3f9100}
.c_c_right ul { padding-top:40px;}
.c_c_right ul li {  clear:both; font-size:14px; height:32px; line-height:32px;}
.c_c_right ul li p { float:left; width:90px; text-align:right}
.c_c_right ul li span { display:block; float:left;}
.c_c_right ul li input[type=text] { height:28px; width:230px; line-height:28px; border-radius:3px;-webkit-border-radius: 4px;-moz-border-radius: 4px; border:1px #cccccc solid;background-color: #ffffff;vertical-align: middle;color: #555555;padding-left: 10px; }
.c_c_right ul li input[type=password] { height:28px; width:230px; line-height:28px; border-radius:3px;-webkit-border-radius: 4px;-moz-border-radius: 4px; border:1px #cccccc solid;background-color: #ffffff;vertical-align: middle;color: #555555;padding-left: 10px; }
.error {padding-left: 95px;color: red;} 
</style>
</head>

<body>
<form id="objForm" action="${ctx}/EEmployee/login" method="post">
<div class="admin_l_bg">
	<div class="a_c_conter">
    	<div class="c_logo"><img src="${statics}/img/form_logo.png" /></div>
        <div class="c_c_right">
        	<h2><img src="${statics}/img/logo.png" /></h2>
            <ul>
            	<li><p>账号：</p>
            	<span>
            		<input name="loginaccount" placeholder="账号 " type="text"  value="${loginaccount}"/>
            	</span>
            	</li>
            	<li class="error">${message}</li>
            	<li><p>密码：</p>
            	<span>
            		<input name="loginpassword" placeholder="密码 " type="password" value="${loginpassword}"/>
            		<input name="fingerprintcode" id="fingerprintcode" type="hidden" />
            	</span>
            	</li>
            	<li class="error"></li>
            	<li><p>验证码：</p>
            	<span>
            		<input type="text" name="verifyCode"  id="verifyCode" placeholder="验证码 " maxlength="15"  />
            	</span>
            	</li>
            	<li><p>&nbsp;</p>
            	<span>
            		<img id="code" src="${ctx}/code1.jsp?s=<%=System.currentTimeMillis()%>"  title="点击切换图片" alt="点击切换图片" style="width: 170px;height: 30px; cursor:pointer;"/>
            	</span>
            	</li>
            	<li class="error">${msg}</li>
            	
            	<li style="margin: auto; text-align: center;">
            	  <input type="radio" name="language" value="zh" checked="checked"/>中文
            	  &nbsp;&nbsp;&nbsp;
            	  <input type="radio" name="language" value="en"/>English
            	</li>
            </ul>
            <div class="c_btn_kt">
            	<button type="submit">登录</button>
            </div>
            
        </div>
    </div>
</div>
</form>
<script type="text/javascript" src="${statics}/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript" src="${statics}/js/fingerprint2.js"></script>
<script type="text/javascript">
$(function(){
	
	new Fingerprint2().get(function(result, components){
			$("#fingerprintcode").val(result);
		 });
	
	$("#code").click(function(){
		$("#code").attr("src", "${ctx}/code1.jsp?s=" + new Date());
	});
	$("#verifyCode").focus(function(){
		$("#code").attr("src", "${ctx}/code1.jsp?s=" + new Date());
		$("#verifyCode").val("");
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
</body>
</html>
