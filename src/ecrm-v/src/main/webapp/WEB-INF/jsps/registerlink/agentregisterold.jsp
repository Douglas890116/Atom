<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<style>
  /* CSS Document */

body,dd,li,dl,dt,h1,h2,h3,h4,h5,h6,li,ol,p,ul{
  margin: 0;
  padding: 0;
  list-style:none;
}
a,a:hover{outline:0;text-decoration:none}
*{font-size: 14px;font-family: 'Microsoft Yahei'}
.clear{clear:both}
body{ background:#B2D3EA}
.admin_reg_body{ width:800px; margin:0 auto; background:#fff; overflow:hidden;    border-radius: 0px 0px 10px 10px;}
.h2{ width:800px; margin:0 auto;height:32px; line-height:32px; color:#fff; background:#357EBD; 
    border-radius:10px 10px 0px 0px; font-size:16px; font-family:"微软雅黑"; text-align:center; margin-top:200px;}
.admin_reg_body ul{ padding:35px 30px 0px 130px}
.admin_reg_body ul li { overflow:hidden; line-height:26px; margin-bottom:16px; font-size:14px}
.admin_reg_body ul li span{ display:block; float:left}
.admin_reg_body ul li p{ float:left; margin-left:6px;}
.admin_reg_body ul li input{ height:26px; border-radius:3px; border:none; line-height:26px; border:1px #ccc solid; width:200px;}
.reg_b_left{ float:left;margin-bottom: 20px}
.reg_b_right{ float:right; width:260px; border-left:1px #ccc solid; height:400px;margin-top:-402px;}
.reg_post_btn a{ display:block; float:left;height:32px; line-height:32px; text-align:center; color:#fff; 
background:#09C; padding:0px 20px 0px 20px; border-radius:5px;}
.reg_post_btn a:hover{ background:#09AAE0}
.reg_post_btn p{ float:left; margin-right:9px;}
.reg_post_btn {  margin:0 auto; margin-left:196px}
.reg_post_btn p button{ height:32px; line-height:32px; text-align:center; color:#fff; background:#357ebd; padding:0px 33px 0px 33px; 
cursor:pointer; border:none;border-radius:5px; cursor:pointer;}
.reg_post_btn p button:hover{ background:#09C}
  
</style>
<body>
<h2 class="h2">创建账户</h2>
<div class="admin_reg_body">
    <div class="reg_b_left">
       <form:form id="objForm" method="post"  class="form-horizontal">
       <ul>
            <li><span>用户昵称:</span><p><input type="text" name="displayalias" data-rules="{required:true,maxlength:8}"  placeholder="请输入用户昵称"/></p></li>
            <li><span>注册账号:</span><p><input type="text" name="loginaccount" data-rules="{required:true,minlength:6,maxlength:12,regexp:/^[0-9a-zA-z]{6,12}$/}" placeholder="请输入登陆账号"  data-messages="{regexp:'账号只能是数字或者字母'}"  data-remote="${ctx}/EEmployee/employeeIsExistForBUI" /></p></li>
            <li><span>设置密码:</span><p><input type="password"  name="loginpassword" id="loginpasswordId" placeholder="请输入登陆密码" data-rules="{required:true,regexp:/^[0-9a-zA-z]{6,20}$/}" data-messages="{regexp:'密码格式不正确,请仔细阅读格式要求'}"/></p></li>
            <li><span>确认密码:</span><p><input type="password" name="confirmloginpassword" placeholder="确保两次输入的登陆密码一致" data-rules="{required:true,equalTo:'#loginpasswordId',regexp:/^[0-9a-zA-z]{6,20}$/}"  data-messages="{regexp:'密码格式不正确'}"/></p></li>
            <li><span>资金密码:</span><p><input type="password" name="fundpassword" placeholder="请输入资金密码" id="fundpasswordId" data-rules="{required:true,regexp:/^[0-9a-zA-z]{6,20}$/}" data-messages="{regexp:'密码格式不正确'}"/></p></li>
            <li><span>确认密码:</span><p><input type="password" name="confirmfundpassword" placeholder="确保两次输入的资金密码一致" data-rules="{required:true,equalTo:'#fundpasswordId',regexp:/^[0-9a-zA-z]{6,20}$/}" data-messages="{regexp:'密码格式不正确'}"/></p></li>
        </ul>
        <div class="reg_post_btn">
        	<p><button type="button" id="J_Form_Submit">提交</button></p>
        </div>
        </form:form>
    </div>
    <div class="reg_b_right"><p><img src="${statics}/img/cmis.png" /></p></div>
</div>
</body>
</html>
<script type="text/javascript">
$(function(){
  BUI.use(['bui/overlay','bui/mask']);
  var form = new BUI.Form.HForm({
       srcNode : '#objForm',
  }).render();
  $("#J_Form_Submit").click(function() {
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
				    	BUI.Message.Show({
				            title : '提示',
				            msg : data.message,
				            icon : 'success',
				            buttons : [
				               {
				                text:'登陆管理后台',
				                elCls : 'button button-primary',
				                handler : function(){
				                  window.location.href=data.login;
				                }
				              }
				            ]
				          });
				    }else if(data.status/1 == 2){
				    	BUI.Message.Show({
				    		title : '提示',
				            msg : data.message,
				            icon : 'success',
				            buttons : [
				              {
				                text:'登陆管理后台',
				                elCls : 'button button-primary',
				                handler : function(){
				                	window.location.href=data.login;
				                }
				              },
				              {
				                text:'查看我的网站',
				                elCls : 'button',
				                handler : function(){
				                	window.location.href="http://"+data.domain;
				                }
				              }
				   
				            ]
				          });
				    }
			    }
			});
		}
		
	});
});
</script>
