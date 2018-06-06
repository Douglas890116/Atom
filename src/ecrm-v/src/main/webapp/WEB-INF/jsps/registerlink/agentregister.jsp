<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>代理注册</title>
<link rel="stylesheet" href="${statics}/css/bs3/bui-min.css" />
<link rel="stylesheet" href="${statics}/css/bs3/dpl-min.css" />
<style>
*{font-family: 'Microsoft Yahei'}
body,dd,div,dl,dt,h1,h2,h3,h4,h5,h6,li,ol,p,ul{margin: 0;padding: 0;border-color: #F81D21; list-style:none;overflow:hidden;font-size: 12px;}
a,a:hover{outline:0;text-decoration:none}
/*注册*/
.reg_bg{ width:100%; background:url(${statics }/img/bg_zc.jpg) center no-repeat fixed; height:980px; background-color:#000000; background-size:100% 100%; overflow:hidden}
.reg_cen{ margin:0 auto; width:500px; position:absolute; top:50%; left:50%; margin-left:25px; margin-top:-250px;}
.reg_top{ width:420px; padding:0 40px; height:54px; border-radius:8px 8px 0 0 ; background:#313131; font-size:24px; color:#fff; line-height:54px;}
.reg_con{ width:420px; padding:0 40px; height:420px; background:#fff; background-color:rgba(255,255,255,0.6);}
.reg_con ul{ padding-top:32px; position:relative}
.reg_con ul li{ height:45px; line-height:45px; margin-bottom:20px; font-size:16px; color:#313131}
.reg_con ul li .span{ width:80px; margin-right:6px; float:left}
.reg_con ul li p{ display:block; float:left;}
.reg_con ul li p input{ width:300px; height:40px; border:1px #e1e4e6 solid; padding:0 10px; outline:none; border-radius:5px;font-size:14px;}
.reg_con ul li b{ float:left; text-align:center; width:138px; margin-left:8px; border:1px #e1e4e6 solid; background:#FFF}
.reg_con ul li button{ width:231px; margin:0 auto; background:#54b014; color:#fff; font-size:26px; border:0; border-radius:4px; display:block; height:42px;}
.reg_yy{ background:url(${statics }/img/reg_yy.png) top no-repeat; width:500px; height:40px; clear:both}
/*注册成功*/
.suc{ width:500px; margin:0 auto; height:425px; position:absolute; top:50%; margin-top:-300px; left:50%;margin-left: 25px;display: none;}
.suc ul{ padding-top:135px; margin:0; height:205px}
.suc ul li{ height:60px; line-height:60px; font-size:24px; text-align:left;padding-left:50px}
.suc_btn{ clear:both;text-align: center;}
.suc_btn button{ display:block; width:250px; text-align:center; font-size:20px; color:#FFF; height:75px; height:75px; border:none}
.suc_btn .btn_l{ float:left; background:#54b014;}
.suc_btn .btn_r{ float:left; background:#2991c5;}
.hiddenDiv { height:100%; overflow:hidden; display:inline-block; width:1px; overflow:hidden; margin-left:-1px; zoom:1; *display:inline; *margin-top:-1px; _margin-top:0; vertical-align:middle;}
</style>
</head>

<body>
<div class="reg_bg">
<!--创建账号star-->

   <div class="reg_cen">
       <div class="reg_top">
          创建账户
       </div>
       <form id="objForm" method="post" >
       <div class="reg_con">
          <ul>
             <li>
                 <span class="span">用户昵称</span><p><input type="text" name="displayalias" maxlength="12"  data-rules="{required:true,maxlength:8}"  placeholder="请输入8位以下任意字符"></p>
             </li>
             <li>
                 <span class="span">注册账号</span><p><input type="text" name="loginaccount"  maxlength="12"  data-rules="{required:true,minlength:6,maxlength:12,regexp:/^[a-zA-Z0-9]{6,12}$/}" placeholder="请输入6-12位字母或数字"  data-messages="{regexp:'账号只能是数字或者字母'}"  data-remote="${ctx}/EEmployee/employeeIsExistForBUI"></p>
             </li>
             <li>
                 <span class="span">设置密码</span><p><input type="password" name="loginpassword" id="password" placeholder="请输入6位以上任意字符" data-rules="{required:true,minlength:6}" ></p>
             </li>
             <li>
                 <span class="span">确认密码</span><p><input type="password" name="confirmloginpassword"   placeholder="请再次输入您的密码" data-rules="{required:true,minlength:6,equalTo:'#password'}" ></p>
             </li>
             <li>
                 <span class="span">验  证  码</span>
                 <p><input style="width:100px" type="text" name="verifycode" id="verifycode" maxlength="12"  placeholder="验证码"></p>
                 <img id="code"  src="${ctx }/code.img"  title="点击切换图片" alt="点击切换图片" style="width: 80px;height: 30px;cursor: pointer;"/>
            		<a href="javascript:void(0)" id="changecode"  style="color: #08B0D5">点击切换</a>
                 
             </li>
             <li>
                 <button id="Register_Submit" type="button">注册</button>
             </li>
          </ul>
       </div>
       </form>
       <div class="reg_yy">
          
       </div>
   </div>
<!--创建账号end-->

	<div class="suc">
       <ul>
          <li style="font-size:36px;">
            <img src="${statics}/img/success.png" height="50" style="vertical-align: sub;">账号已注册成功
          </li>
          <li>
             您的账号：<span style="color:#0CB4D9" id="username"></span>
          </li>
          <li>
            您的运营站点：<a href="" id="domain"></a>
          </li>
       </ul>
       <div class="suc_btn">
          <a href="" id="gologin" style="position: relative;"><button class="btn_l">登陆管理后台</button></a>
          <a href="" id="gomysite" style="position: relative;"><button class="btn_r">我的运营站点</button></a>
       </div>
    </div>
</div>
<script type="text/javascript" src="${statics }/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript" src="${statics }/js/bui.js"></script>
<script type="text/javascript" src="${statics }/js/config-min.js"></script>
<script type="text/javascript">
$(function(){
	$("#code,#changecode").click(function(){
		$("#code").attr("src", "${ctx }/code.img?" + new Date());
	});
	$("#verifycode").focus(function(){
		$("#code").attr("src", "${ctx }/code.img?" + new Date());
	});
	
	  BUI.use(['bui/overlay','bui/mask']);
	  var form = new BUI.Form.HForm({
	       srcNode : '#objForm',
	  }).render();
	  $("#Register_Submit").click(function() {
			if(form.isValid()){
				$.ajax({
					type: "POST",
					url: "${ctx}/res/save",
					data:$('#objForm').serialize(),
					dataType: "json",
					statusCode:{404:function(){
						BUI.Message.Alert("请求未发送成功",'error');
					}},
				    success: function(data) {
					    if(data.status/1 == 0){
					    	BUI.Message.Alert(data.message,'warning');
					    }else if(data.status/1 == 1){
					    	$(".reg_cen").hide();
					    	$(".suc").show();
					    	$(".btn_r").hide();
					    	$("#username").html(data.username);
					    	$("#domain").html(data.domain).attr("href","http://"+data.domain);
					    	$("#gologin").attr("href",data.login);
					    }else if(data.status/1 == 2){
					    	$(".reg_cen").hide();
					    	$(".suc").show();
					    	$("#username").html(data.username);
					    	$("#domain").html(data.domain).attr("href","http://"+data.domain);
					    	$("#gologin").attr("href",data.login);
					    	$("#gomysite").attr("href","http://"+data.domain);
					    }
				    }
				});
			}
			
		});
	});

</script>
</body>
</html>
