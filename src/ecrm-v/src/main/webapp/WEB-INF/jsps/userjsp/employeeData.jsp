<%@page import="com.maven.config.SystemConstant"%>
<%@page import="com.maven.cache.SystemCache"%>
<%@page import="com.maven.entity.EnterpriseEmployee"%>
<%@page import="com.maven.constant.Constant"%>
<%@page import="org.springframework.web.context.request.SessionScope"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.maven.entity.EnterpriseEmployeeType.Type" %>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<c:set var="user" value="${sessionScope.ERCM_USER_SESSION.employeecode}"></c:set>
<style>
body,dd,div,dl,dt,h1,h2,h3,h4,h5,h6,li,ol,p,ul{
	margin: 0;
	padding: 0;
	border-color: #F81D21; list-style:none;
}
.btn{ width:80px; height:28px; line-height:28px; text-align:center; border-radius:4px; color:#fff; background-color:#449bc9; margin:5px 10px; cursor:pointer; list-style-type:none; outline:none; border:0;}
.btn:hover{ background-color:#0f8fce}
div#OperatingButton{
    background:rgba(0,0,0,0.5);
    width:100px;
    height:150px;
    display:none; 
    padding:10px 0; 
    border-radius:6px
}
</style>
<body style="padding: 10px;">
    <div class="demo-content" >
<!-- 搜索页 ================================================== style=" margin:0 auto;  width:950px;"-->
<form id="searchForm" class="form-horizontal" style="outline: none;" method="post">
    <input name="parentemployeecode" type="hidden" />
    <input name="end_hidden" type="hidden" />
    <input name="last_hidden" type="hidden" />
    <input name="createDate" type="hidden" value="${createDate}"/>
    <div class="row well" style="margin-left: 0px;position: relative;margin-bottom: 0px;">
    <div style="position: relative;display: inline-block;">
      <div style="float: left;margin-right: 96px;">
      		 <div class="control-group span6 ">
	              <label class="control-label">用户类型：</label>
	                <select name="employeetypecode" id="employeetypeId" aria-pressed="false" aria-disabled="false"   class="bui-form-field-select bui-form-field" style="width: 117px;">
						<option value="">请选择</option>
						<option value="T001" >企业号</option>
						<option value="T002" >员工</option>
						<option value="T003" >会员</option>
						<option value="T004" >代理</option>
						<option value="T005" >信用代理</option>
	              	</select>
	         </div>
         	 <div class="control-group span7">
              <label class="control-label">登录账户：</label>
                <input name="loginaccount"  type="text"  data-tip='{"text":"登录账户"}'  class="control-text" placeholder="登录账户"/>
            </div>
             <div class="control-group span7">
              <label class="control-label">上级账户：</label>
                <input name="parentemployeeaccount"  type="text" data-tip='{"text":"上级账户"}' class="control-text" placeholder="上级账户"/>
            </div>
            <!-- <div class="control-group span7">
              <label class="control-label">品牌名称：</label>
                <input name="brandname"  type="text" data-tip='{"text":"品牌名称"}'  class="control-text" placeholder="品牌名称"/>
            </div> -->
            <div class="control-group span6 ">
            	<label class="control-label">姓名：</label>
            	<input name="displayalias"  type="text" data-tip='{"text":"姓名"}' class="control-text" placeholder="姓名"/>
            </div>
            <div class="control-group span6 ">
            	<label class="control-label">手机号码：</label>
            	<input name="phoneno"  type="text" data-tip='{"text":"手机号码"}' class="control-text" placeholder="手机号码"/>
            </div>
            <div class="control-group span6 ">
            	<label class="control-label">QQ号码：</label>
            	<input name="qq"  type="text" data-tip='{"text":"QQ号码"}' class="control-text" placeholder="QQ号码"/>
            </div>
            <div class="control-group span6 ">
            	<label class="control-label">邮箱：</label>
            	<input name="email"  type="text" data-tip='{"text":"邮箱"}' class="control-text" placeholder="邮箱"/>
            </div>
            <div class="control-group span6 ">
            	<label class="control-label">微信号：</label>
            	<input name="wechat"  type="text" data-tip='{"text":"微信号"}' class="control-text" placeholder="微信号"/>
            </div>
            
            <div class="control-group span6">
              <label class="control-label">VIP等级：</label>
              	<select name="employeelevelcode" aria-pressed="false" aria-disabled="false"   class="bui-form-field-select bui-form-field" style="width: 117px;">
                	<option value="">请选择</option>
                	<c:forEach var="item" items="${listEnterpriseEmployeeLevel }" varStatus="i" >
                    	<option value="${item.employeeLevelCode }">${item.employeeLevelName }</option>
                    </c:forEach>
                </select>    
            </div>
            
            <div class="control-group span6 ">
	              <label class="control-label">会员状态：</label>
	                <select name="employeestatus" aria-pressed="false" aria-disabled="false"   class="bui-form-field-select bui-form-field" style="width: 117px;">
						<option value="">请选择</option>
	                    <option value="1">启用</option>
	                    <!-- <option value="2">锁定游戏</option> -->
	                    <option value="3">禁用</option>
	              	</select>
	         </div>
	         <div class="control-group span6">
	              <label class="control-label">手机验证状态：</label>
	                <select name="phonestatus" aria-pressed="false" aria-disabled="false"   class="bui-form-field-select bui-form-field" style="width: 117px;">
						<option value="">请选择</option>
	                    <option value="0">未验证</option>
	                    <option value="1">已验证</option>
	              	</select>
	         </div>
            
           <div class="control-group span7 toggle-display">
              <label class="control-label">账户余额：</label>
                <input name="balance_begin"  type="text" data-tip='{"text":"余额下限"}'  class="control-text span2" placeholder="余额下限"/>
                -
                <input name="balance_end"  type="text" data-tip='{"text":"余额上限"}'  class="control-text span2" placeholder="余额上限"/>
            </div>
             
            <div class="control-group span15 toggle-display">
              <label class="control-label">最后登录时间：</label>
              <div class="bui-form-group" data-rules="{dateRange : true}">
                <input name="lastStartDate"  type="text"  class="calendar calendar-time" placeholder="起始时间"/>
                <span>&nbsp;-&nbsp;</span>
               	<input  name="lastEndDate" type="text" class="calendar calendar-time" placeholder="结束时间"/>
              </div>
              <div style="margin-right: auto;margin: -30px 446px;">
                     <select onchange="changeFormatDate(this,'lastStartDate','lastEndDate')"  id="selectDateId"  style="width:85px;">
                                <option value="">请选择</option>
                                <option value="1">今天</option>
                                <option value="2">昨天</option>
                                <option value="3">三天</option>
                                <option value="4">本周</option>
                                <option value="5">上周</option>
                                <option value="6">半月</option>
                                <option value="7">本月</option>
                                <option value="8">上月</option>
                                <option value="9">半年</option>
                                <option value="10">本年</option>
                      </select>
                </div>
            </div>
            <div class="control-group span13 toggle-display">
                <div class="control-group span13 toggle-display">
                <label class="control-label">注册时间：</label>
                <div class="bui-form-group" data-rules="{dateRange : true}">
                  <input name="startDate"  type="text"   class="calendar calendar-time" placeholder="起始时间" />
                  <span>&nbsp;-&nbsp;</span>
                  <input  name="endDate" type="text"    class="calendar calendar-time" placeholder="结束时间" />
                </div>
                <div style="margin-right: auto;margin: -30px 425px;">
                     <select onchange="changeFormatDate(this,'startDate','endDate')"  id="selectDateId"  style="width:85px;">
                                <option value="">请选择</option>
                                <option value="1">今天</option>
                                <option value="2">昨天</option>
                                <option value="3">三天</option>
                                <option value="4">本周</option>
                                <option value="5">上周</option>
                                <option value="6">半月</option>
                                <option value="7">本月</option>
                                <option value="8">上月</option>
                                <option value="9">半年</option>
                                <option value="10">本年</option>
                              </select>
                </div>
            	</div>
           </div>
           <div class="control-group span7 toggle-display">
               	<button type="reset" class="button button-warning">
            	<span class="icon-repeat icon-white"></span>重置条件
            	</button>
           </div>
          	 <div style="position: absolute;right: 0px;top: 3px; ">
             	<button id="btnSearch" type="submit"  class="button button-primary">
             	<span class="icon-search icon-white"></span> 搜 索
				</button>
            </div>
        </div>
    </div> 
    </div>
     </form>
     <!-- 此节点内部的内容会在弹出框内显示,默认隐藏此节点-->
    <div id="content" class="hidden" style="display: none;">
      <form id="validtimeform" class="form-horizontal">
        <div class="row">
          <div class="control-group span15">
            <label class="control-label">有效截止日期：</label>
            <div class="controls">
              <input class="calendar calendar-time" type="text" id="validtime" data-rules="{required : true}"><!-- <label>&nbsp;-&nbsp;</label><input class="input-small control-text" type="text"> -->
            </div>
          </div>
        </div>
      </form>
    </div>
    
    <!-- 设置会员等级内容-->
    <div id="content2" class="hidden" style="display: none;">
      <form id="userLevel" class="form-horizontal">
        <div class="row">
          <div class="control-group span15">
            <label class="control-label">选择等级：</label>
            <div class="controls">
            	<select name="selectLevel" id="selectLevel" data-rules="{required : true}">
                  <option value="">--请选择--</option>
              		<c:forEach var="item" items="${listEnterpriseEmployeeLevel }" varStatus="i">
                  		<option value="${item.employeeLevelCode }">${item.employeeLevelName }-每日取款次数：${item.takeTimeOfDay }次-每日取款金额：${item.takeMoneyOfDay }元</option>
                  	</c:forEach>
                  </select>
            </div>
          </div>
        </div>
      </form>
    </div>
    
    <!-- 此节点内部的内容会在弹出框内显示,默认隐藏此节点-->
    <div id="content3" class="hidden" style="display: none;">
      <form id="userPwd1" class="form-horizontal">
        <div class="row">
          <div class="control-group span15">
          
          	<input class="input control-text" type="hidden" id="employeecode1" >
          	
          	<div class="controls" style="">
          		<p style="text-align:center">请输入新的密码：<br /><br /></p>
              	<p style="text-align:center"><input class="input control-text" type="text" id="newpassword1" data-rules="{required : true}"/><br/></p>
              	<p><font color="red">注：密码由【6~12位】的【小写字母】和【数字】组成</font></p><br/>
              	<p style="text-align:center">
              	<input type="button" class="button button-primary  botton-margin" onclick="getRandomPassword(this,1)" value="随机密码"></input>
              	<input type="button" class='button button-success botton-margin'  onclick="saveNewpassword(this,1)" value="确定修改"></input>
              	</p><p></p>
   			</div>
   			
          </div>
        </div>
      </form>
    </div>
    
    <!-- 此节点内部的内容会在弹出框内显示,默认隐藏此节点-->
    <div id="content4" class="hidden" style="display: none;">
      <form id="userPwd2" class="form-horizontal">
        <div class="row">
          <div class="control-group span15">
          
          	<input class="input control-text" type="hidden" id="employeecode2" >
          	
          	<div class="controls" style="">
          		<p style="text-align:center">请输入新的密码：<br /><br /></p>
              	<p style="text-align:center"><input class="input control-text" type="text" id="newpassword2" data-rules="{required : true}"/><br/></p>
              	<p><font color="red">注：密码由【6~12位】的【小写字母】和【数字】组成</font></p><br/>
              	<p style="text-align:center">
              	<input type="button" class="button button-primary  botton-margin" onclick="getRandomPassword(this,2)" value="随机密码"></input>
              	<input type="button" class='button button-success botton-margin'  onclick="saveNewpassword(this,2)" value="确定修改"></input>
              	</p><p></p>
   			</div>
   			
          </div>
        </div>
      </form>
    </div>
    
    <!-- 此节点内部的内容会在弹出框内显示,默认隐藏此节点-->
    <div id="content5" class="hidden" style="display: none;">
      <form id="userInfo" class="form-horizontal">
        <div class="row">
          <div class="control-group span17">
          	<div class="controls" style="">
          	    <input type="hidden" id="employeeCodeC"/>
          		<div style="text-align:right">用户昵称：<input class="input control-text" type="text" id="displayalias"/></div><br/>
          		<div style="text-align:right">手机号码：<input class="input control-text" type="text" id="phoneno"/></div><br/>
              	<div style="text-align:right">QQ号码：<input class="input control-text" type="text" id="qqno"/></div><br/>
              	<div style="text-align:right">邮箱地址：<input class="input control-text" type="text" id="email" data-rules="{email:true}"/></div><br/>
              	<div style="text-align:right">微信号：<input class="input control-text" type="text" id="wechat" /></div><br/>
              	<div style="text-align:right">注册时间：<input class="input control-text" type="text" id="logindatetime" readonly="readonly"/></div><br/>
              	<div style="text-align:right">最后登录时间：<input class="input control-text" type="text" id="lastlogintime" readonly="readonly"/></div><br/>
   			</div>
          </div>
        </div>
      </form>
    </div>

    <!-- 此节点内部的内容会在弹出框内显示,默认隐藏此节点-->
    <div id="content6" class="hidden" style="display: none;">
      <form id="creditratingForm" class="form-horizontal">
        <div class="row">
          <div class="control-group span17">
          	<div class="controls" style="">
          	    <input type="hidden" id="employeeCode"/>
          		<div style="text-align:right">
          		<label class="control-label">信用评级：</label>
          		<input id="employeecodes" type="hidden"/>
          		<select name="creditrating">
          			<option value="1">★</option>
          			<option value="2">★★</option>
          			<option value="3">★★★</option>
          			<option value="4">★★★★</option>
          			<option value="5">★★★★★</option>
          		</select>
          		</div>
   			</div>
          </div>
        </div>
      </form>
    </div>

<ul style="border: none;height: 20px;line-height: 20px;color:#428bca;font-weight: bold;visibility: hidden;" id="visiteScent" class="breadcrumb">
        <li>
			访问轨迹：
        </li>
        <li>
        	<span style="padding: 0 5px;border: 1px solid #E8E9EE;cursor:pointer;" onclick = "clearScent(this)">清除</span>
        </li>
</ul>
<ul style="border:5px; height:20px; line-height:20px; color:red; font-weight:bold;" class="breadcrumb">
	<li>
	<span style="font-size: 12px">
	用户信息属于隐私数据，若需要查看、编辑，可向相关人员申请权限，[配置管理]-[隐私数据授权]
	</span>
	</li>
</ul>
<div class="search-grid-container well">
  <div id="grid"></div>
</div>
</div>
</body>
</html>
<script type="text/javascript">
function showButton(){
	$("#OperatingButton").show();
}


var dialogGlobal11 = null;
var dialogGlobal22 = null;
var dialogGlobal33 = null;
var dialogGlobal55 = null;
var dialogGlobal66 = null;

function setUserCrditRating(employeecodes) {
	$("#employeecodes").val(employeecodes);
	if (dialogGlobal66 == null) {
		BUI.use('bui/overlay',function(Overlay){
			dialogGlobal66 = new Overlay.Dialog({
			    title:'用户信用评级',
			    width:350, height:150,
			    //配置DOM容器的编号
			    contentId:'content6',
			    success:function () {
			    	sendUserCrditRating(employeecodes);
					this.close();
			    }
			});
		});
	}
	dialogGlobal66.show();
}

function sendUserCrditRating(employeecodes) {
	$.ajax({
		url:"${ctx}/EEmployee/setCrditrating",
		type:"post",
		data:{"creditrating" : $("select[name='creditrating']").val(), "employeecodes" : $("#employeecodes").val()},
		dataType:"json",
		success:function(data){
			if(data.status == 1){
				BUI.Message.Alert('设置会员信用评级成功!','success');
				$("#searchForm").submit();
			} else {
				BUI.Message.Alert('设置会员信用评级失败: ' + data.message,'error');
			}
		},
		error:function(){
			BUI.Message.Alert('系统错误，请联系管理员!','error');
		}
	});
}

function updatePassword(obj){
	
	$("#employeecode1").val("");
	$("#newpassword1").val("");
	
	$("#employeecode2").val("");
	$("#newpassword2").val("");
	
	if(dialogGlobal11 == null) {
		BUI.use('bui/overlay',function(Overlay){
			dialogGlobal11 = new Overlay.Dialog({
				width:240,
				height:140,
				title:"密码重置",
				elCls : 'custom-dialog',
				bodyContent:"<button class='button button-inverse botton-margin'  onclick='showLoginPassword(this)' value='"+$(obj).val()+"'>登录密码重置</button>"
				+"<button class='button button-danger botton-margin'  onclick='showCapitalPassword(this)' value='"+$(obj).val()+"'>资金密码重置</button>",
				buttons : [{
					text:'取消',
					elCls : 'button',
					handler : function(){
						this.close();
					}
				}]
			});
			//dialogGlobal11.show();
		});
	}
	
	dialogGlobal11.show();
	
}

function showLoginPassword(obj){
	
	//$("#newpasswordtype").val("loginpassword");//登录密码
	$("#employeecode1").val(obj.value);
	
	
	if(dialogGlobal22 == null) {
		BUI.use('bui/overlay',function(Overlay){
			dialogGlobal22 = new Overlay.Dialog({
				width:340,
				height:260,
				title:"登录密码重置",
				elCls : 'custom-dialog',
				contentId:'content3',
				buttons : [{
					text:'取消',
					elCls : 'button',
					handler : function(){
						this.close();
					}
				}]
			});
			//dialogGlobal22.show();
		});
	}
	dialogGlobal22.show();
}

function showCapitalPassword(obj){
	
	//$("#newpasswordtype").val("capitalpassword");//资金密码
	$("#employeecode2").val(obj.value);
	
	if(dialogGlobal33 == null) {
		BUI.use('bui/overlay',function(Overlay){
			dialogGlobal33 = new Overlay.Dialog({
				width:340,
				height:260,
				title:"资金密码重置",
				elCls : 'custom-dialog',
				contentId:'content4',
				buttons : [{
					text:'取消',
					elCls : 'button',
					handler : function(){
						this.close();
					}
				}]
			});
			//dialogGlobal33.show();
		});
	}
	
	dialogGlobal33.show();
}
//确定修改密码
function saveNewpassword(obj,stype) {
	var value = $("#newpassword"+stype).val();
	var reg1 = new RegExp("^[a-z0-9]+$");// 验证只包含小写字母与数字
	var reg2 = new RegExp("[a-z]+");// 验证必须包含小写字母
	var reg3 = new RegExp("[0-9]+");// 验证必须包含数字
	if(value == null || value == "") {
		BUI.Message.Alert('请输入密码 !','error');
		$("#newpassword"+stype)[0].focus();
		return ;
	}
	if(value.length < 6 || value.length > 12) {
		BUI.Message.Alert('密码长度必须在6~12位之间 !','error');
		return ;
	}
 	if(!reg1.test(value)) {
		BUI.Message.Alert('密码不能包含特殊字符和大写字母 !','error');
		return ;
	}
 	if(!reg2.test(value) || !reg3.test(value)) {
		BUI.Message.Alert('密码必须包含小写字母符和数字 !','error');
		return ;
 	}
	if(stype == 2 ) {
		resetCapitalPassword($("#employeecode"+stype).val(), $("#newpassword"+stype).val());
	} else {
		resetLoginPassword($("#employeecode"+stype).val(), $("#newpassword"+stype).val());
	}
}

//获取随机密码
function getRandomPassword(obj,stype){
	$.ajax({
		url:"${ctx}/common/getRandomPassword",
		type:"post",
		data:{"employeecode": $("#employeecode"+stype).val()},
		dataType:"json",
		success:function(data){
			
			if("success" == data.status){
				$("#newpassword"+stype).attr("value", data.resetPassword);
				
			} else if("failure" == data.status){
				BUI.Message.Alert('获取随机密码失败!','error');
			}
			
		}
	});
}

//登录密码重置
function resetLoginPassword(employeecode, newpassword){
	//alert("调试：employeecode"+employeecode+ ",newpassword="+newpassword);
	$.ajax({
		url:"${ctx}/common/resetLoginPassword",
		type:"post",
		data:{"employeecode":employeecode, "newpassword": newpassword },
		dataType:"json",
		success:function(data){
			if("success" == data.status){
				//$(obj).parent().parent().remove();
				//$("div.bui-ext-mask").remove();
				//BUI.Message.Alert('初始密码为：<span style="font-weight:bolder;">'+data.resetPassword+'</span>','success');
				alert("初始密码为："+data.resetPassword);
				$("#searchForm").submit();
			}else if("failure" == data.status){
				//BUI.Message.Alert('密码重置失败!','error');
				alert("密码重置失败!");
			} else {
				alert("密码重置失败，status="+data.status);
			}
		}
	});
}
//资金密码重置
function resetCapitalPassword(employeecode, newpassword){
	$.ajax({
		url:"${ctx}/common/resetCapitalPassword",
		type:"post",
		data:{"employeecode":employeecode, "newpassword": newpassword },
		dataType:"json",
		success:function(data){
			if("success" == data.status){
				//$(obj).parent().parent().remove();
				//$("div.bui-ext-mask").remove();
				//BUI.Message.Alert('初始密码为：<span style="font-weight:bolder;">'+data.resetPassword+'</span>','success');
				alert("初始密码为："+data.resetPassword);
				$("#searchForm").submit();
			}else if("failure" == data.status){
				//BUI.Message.Alert('密码重置失败!','error');
				alert("密码重置失败!");
			}
		}
	});
}
function fmtdate(t) {
	var d = new Date(t/1);
	return (d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate()+" "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds());
}
function showLink(v1,v2,v3,v4,v5,v6,v7,v8) {
	$("#userInfo")[0].reset();
	
	if(v1!=null&&v1!="null"&&v1!="")$("#displayalias").val(v1);
	if(v2!=null&&v2!="null"&&v2!="")$("#phoneno").val(v2);
	if(v3!=null&&v3!="null"&&v3!="")$("#qqno").val(v3);
	if(v4!=null&&v4!="null"&&v4!="")$("#email").val(v4);
	if(v5!=null&&v5!="null"&&v5!="")$("#logindatetime").val(fmtdate(v5));
	if(v6!=null&&v6!="null"&&v6!="")$("#lastlogintime").val(fmtdate(v6));
	if(v7!=null&&v7!="null"&&v7!="")$("#employeeCodeC").val(v7);
	if(v8!=null&&v8!="null"&&v8!="")$("#wechat").val(v8);
	// 编辑权限限制
	//var user_edit = ${sessionScope.ERCM_USER_PSERSSION['MN00DU'].menustatus==1};
	var arr= new Array();
	//if (user_edit) {
		arr.push({
            text:'保存',
            elCls : 'button button-success btn-save',
            handler : function(){
        	   $.ajax({
        	        url:"${ctx}/EEmployee/userJsp/updateEmployeeInfo",
        	        type:"post",
        	        data:{
        	        	"employeeCode" : $("#employeeCodeC").val(), 
        	        	"displayalias" : $("#displayalias").val(),
        	        	"phoneno" : $("#phoneno").val(),
        	        	"qq" : $("#qqno").val(),
        	        	"wechat" : $("#wechat").val(),
        	        	"email" : $("#email").val()
        	        	},
        	        dataType:"json",
        	        success:function(data){
        	            if("success" == data.status){
        	            	BUI.Message.Alert("信息更新成功!","success");
        	            	dialogGlobal55.close();
        	            	$("#searchForm").submit();
        	            }else{
        	                BUI.Message.Alert('信息更新失败!','error');
        	            }
        	        }
        	    });
            }
        });
	//}
	arr.push({
        text:'取消',
        elCls : 'button button-primary',
        handler : function(){
            this.close();
        }
    });
	
	if(dialogGlobal55 == null) {
		BUI.use('bui/overlay',function(Overlay){
			dialogGlobal55 = new Overlay.Dialog({
				width:480,
				height:350,
				title:"更多信息",
				elCls : 'custom-dialog',
				contentId:'content5',
				buttons : arr
			});
		});
	}
	dialogGlobal55.show();
}

function showUserData(employeecode,loginaccount) {
	openNewWindow('showshowuserdatadetail','${ctx}/EEmployee/userJsp/allinfo?employeecode='+employeecode+'&loginaccount='+loginaccount,'会员详细信息');
}

(function(){
//加载员工类型	
//loadEmployeeType();

var add_employee 		= ${sessionScope.ERCM_USER_PSERSSION['MN005X'].menustatus==1};
var reset_loginpassword	= ${sessionScope.ERCM_USER_PSERSSION['MN008Q'].menustatus==1};
var reset_funpassword	= ${sessionScope.ERCM_USER_PSERSSION['MN008R'].menustatus==1};
var batch_delete		= ${sessionScope.ERCM_USER_PSERSSION['MN005Y'].menustatus==1};
var permission_admin	= ${sessionScope.ERCM_USER_PSERSSION['MN008S'].menustatus==1};
var setting_config		= ${sessionScope.ERCM_USER_PSERSSION['MN009M'].menustatus==1};
var batch_disable		= ${sessionScope.ERCM_USER_PSERSSION['MN00A7'].menustatus==1};
var batch_activate		= ${sessionScope.ERCM_USER_PSERSSION['MN00A8'].menustatus==1};
var batch_validtime		= ${sessionScope.ERCM_USER_PSERSSION['MN00AE'].menustatus==1};
var batch_export		= ${sessionScope.ERCM_USER_PSERSSION['MN00CT'].menustatus==1};

var user_edit 	 = ${sessionScope.PRIVATE_DATA_PSERSSION != null};
var employeetype = '${sessionScope.ERCM_USER_SESSION.employeetypecode}';
//权限验证
function permissionValidate(){
	var array= new Array();
	 if(add_employee){
		array.push({
	        btnCls : 'button button-primary',
	        text:"<span class='icon-file icon-white'></span>创建用户",
	        handler : function(){
	        	openNewWindow('create_employee','${ctx}/employeeOperating/userJsp/employeeAdd','创建用户');
	        }
	  	});
	}
	if(batch_delete){
		array.push({
	        btnCls : 'button button-danger',
	        text:'<span class=" icon-trash icon-white"></span>删除用户',
	        handler : function(){
	        	deleteMutilterm(grid,"${ctx}/EEmployee/deleteSelectEmployee","employeecode");
	     }});
	}
	if(batch_disable){
		array.push({
	        btnCls : 'button button-warning',
	        text:'<span class=" icon-remove icon-white"></span>禁用用户',
	        handler : function(){
	        	disableMutilterm(grid,store,"${ctx}/EEmployee/disableSelectEmployee","employeecode");
	     }});
	}
	if(batch_activate){
		array.push({
	        btnCls : 'button button-success',
	        text:'<span class=" icon-ok icon-white"></span>启用用户',
	        handler : function(){
	        	activateMutilterm(grid,store,"${ctx}/EEmployee/activateSelectEmployee","employeecode");
	     }});
	}
	if(batch_validtime){
		array.push({
	        id: 'validtime_btn',
	        btnCls : 'button button-info',
	        text:'<span class=" icon-minus-sign icon-white"></span>有效时间',
	        /* handler : function(){
	        	
	     } */});
	}
	if(batch_activate){
		array.push({
			id: 'level_btn',
	        btnCls : 'button button-success',
	        text:'<span class=" icon-signal icon-white"></span>会员等级'
	        });
	}
	if(batch_activate){
		array.push({
			id: 'credit_btn',
	        btnCls : 'button button-warning',
	        text:'<span class=" icon-star icon-white"></span>信用评级'
	        });
	}
	if(batch_export){
		array.push({
			id: 'export_btn',
	        btnCls : 'button button-primary',
	        text:'<span class=" icon-download-alt icon-white"></span>导出当前条件用户',
	        handler : function(){
            	
            	searchForm.action = "${ctx}/EEmployee/exportEmployee";
            	searchForm.submit();
            	searchForm.action = "${ctx}/EEmployee/findEmployeeData";
            }
	    });
	}
	return array;
}



var Grid = BUI.Grid,
  Store = BUI.Data.Store,
  columns = [
    {title:'企业',width:'7%',dataIndex:'enterprisename',elCls:'center',sortable:false},
   	{title:'品牌',width:'6%', elCls:'center', dataIndex:'brandname',sortable:false},
	{ title: '账号 ',width: '7%',dataIndex:'loginaccount',elCls:'center',sortable: false,renderer:function(value,obj){
		if(obj.employeetypecode !='<%=Type.会员.value%>'){
			return "<a href=javascript:showDownData('"+obj.employeecode.split('_')[1]+"','"+obj.loginaccount+"') style='font-size:16px;' title='查看下级信息'>"+value+"</a>";
		}else{
			return "<a href=javascript:showUserData('"+obj.employeecode.split('_')[1]+"','"+obj.loginaccount+"') style='font-size:16px;' title='查看汇总信息'>"+value+"</a>";
		}
    }},
    { title: '昵称',width: '9%',  sortable: false, elCls:'center',dataIndex: 'displayalias',renderer:function(value,obj){
    	return value+"<p>VIP等级："+(obj.employeelevelname)+"</p>";
    }},
    { title: '类型',width: '5%',sortable: false,elCls:'center',dataIndex: 'employeetypename'},
    { title: '分红',width: '5%',csortable: false, elCls:'center',dataIndex: 'dividend',renderer:function(value,obj){
    	return "<span style='color:#5cb85c;font-size: 16px;'>"+(value/1*100).toFixed(2) +"%</span>";
    }},
    { title: '占成',width: '5%', elCls:'center', sortable: false,elCls:'center', dataIndex: 'share',renderer:function(value,obj){
    	return "<span style='color:#5cb85c;font-size: 16px;'>"+(value/1*100).toFixed(2)+"%</span>";
    }},
    {title:'信用评级',width:'6%',dataIndex:'creditrating',elCls:'center',sortable:false,renderer:function(value,obj){
    	switch(value) {
	    	case 0 : return '<a title="点击进行编辑" onclick=setUserCrditRating("' + obj.employeecode + '") href="#"><div class="star-0"></div></a>';
	    	case 1 : return '<a title="点击进行编辑" onclick=setUserCrditRating("' + obj.employeecode + '") href="#"><div class="star-1"></div></a>';
	    	case 2 : return '<a title="点击进行编辑" onclick=setUserCrditRating("' + obj.employeecode + '") href="#"><div class="star-2"></div></a>';
	    	case 3 : return '<a title="点击进行编辑" onclick=setUserCrditRating("' + obj.employeecode + '") href="#"><div class="star-3"></div></a>';
	    	case 4 : return '<a title="点击进行编辑" onclick=setUserCrditRating("' + obj.employeecode + '") href="#"><div class="star-4"></div></a>';
	    	case 5 : return '<a title="点击进行编辑" onclick=setUserCrditRating("' + obj.employeecode + '") href="#"><div class="star-5"></div></a>';
    	}
    }},
    { title: '代理数',width: '5%', elCls:'center', sortable: false,elCls:'center', dataIndex: 'agentCount',renderer:function(value,obj){
    	return "<span style='color:red;font-size: 16px;'>"+value+"</span>";
    }},
    { title: '会员数',width: '5%', elCls:'center', sortable: false,elCls:'center', dataIndex: 'memberCount',renderer:function(value,obj){
    	return "<span style='color:red;font-size: 16px;'>"+value+"</span>";
    }},
    { title: '账户余额',width: '6%',dataIndex: 'balance',elCls:'center',renderer:function(value,obj){
    	return "<span style='color:red;font-size:16px;'>"+($.isNumeric(value)?value.toFixed(2):value)+"</span>";
    }},
    {title: '上级账号/站点 ',width: '8%',sortable: false, elCls:'center', dataIndex: 'parentemployeeaccount',renderer:function(value,obj){
    	var str = obj.registercode;
    	if(str == null || str == "null") {
    		str = "";
    	}
    	return value + "<br/>"+str;
	}},
	
    /*  
	{ title: '最后登录', width: '10%',dataIndex: 'lastlogintime',elCls:'center',renderer:BUI.Grid.Format.datetimeRenderer},
    { title: '注册日期', width: '10%',sortable: false,   dataIndex: 'logindatetime',elCls:'center',renderer:BUI.Grid.Format.datetimeRenderer},	
    */
    
    {title: '编辑信息 ',width: '5%',sortable: false, elCls:'left', dataIndex: 'phoneno',renderer:function(value,obj){
    	if(user_edit) {
     		return "<button class='button button-success' onclick=showLink('"+obj.displayalias+"','"+obj.phoneno+"','"+obj.qq+"','"+obj.email+"','"+obj.logindatetime+"','"+obj.lastlogintime+"','"+obj.employeecode+"','"+obj.wechat+"') title='查看并编辑信息'><i class='icon-th icon-white'></i>编辑</button>"; 
    	}
	}},
     
    { title: '状态',width: '3%',sortable: false,elCls:'center',renderer:function(value,obj){
    	switch(obj.employeestatus){
		case 1:
			return "启用";
		case 2:
			return "锁定游戏";
		case 3:
			return "<span style='color:red'>禁用</span>";
		default:
			return "无";
		}
	}},
    { title : '操作',width: '17%', sortable: false, renderer : function(value,obj){
    	var temp = '';
    	var employeecode = obj.employeecode.split('_')[1];
    	if(obj.memberCount){
    		temp+="<button class='button button-warning' onclick=openMember('"+employeecode+"','"+obj.loginaccount+"') title='查看会员'><i class='icon-th icon-white'></i>详</button>"; 
    	}
    	if(obj.employeetypecode=='<%=Type.会员.value%>'){
			temp += "<button class='button button-inverse'  onclick='getGameAccount(\""+obj.employeecode+"\")' title='游戏信息'><i class='icon-align-justify icon-white'></i>游</button>";
    	}
   		if(employeetype!='<%=Type.企业号.value%>' &&(obj.employeetypecode=='<%=Type.代理.value%>'||obj.employeetypecode=='<%=Type.会员.value%>')){
   			if(reset_funpassword && reset_loginpassword ){
   				temp += "<button class='button button-info   botton-margin'  onclick='updatePassword(this)' value='"+obj.employeecode+"' title='密码重置'><i class=' icon-info-sign icon-white'></i>密</button>";
   			}
   			if(permission_admin&&employeecode!='${sessionScope.ERCM_USER_SESSION.employeecode}'){
   	    		temp += '<button class="button button-primary  botton-margin" onclick="permission(\'employeecode='+obj.employeecode+'&employeename='+obj.loginaccount+'\')" title="权限管理"><span class="icon-globe icon-white"></span>权</button>';
   	    	}
   			if(employeecode!='${sessionScope.ERCM_USER_SESSION.employeecode}'&&setting_config){
   				if(obj.employeetypecode=='<%=Type.会员.value%>') {
   					temp += "<button class='button button-success botton-margin'  onclick=\"openNewWindow('agentsettlement','${ctx}/GCBonus/userSetting?employeecode="+obj.employeecode+"','结算配置')\"  title='结算配置'><span class='icon-cog icon-white'/>结</button>";
   				} else {
	   				temp += "<button class='button button-success botton-margin'  onclick=\"openNewWindow('agentsettlement','${ctx}/GCBonus/agentSetting?employeecode="+obj.employeecode+"','结算配置')\"  title='结算配置'><span class='icon-cog icon-white'/>结</button>";
   				}
   			}
   		}else if(employeetype=='<%=Type.企业号.value%>'){
   			if(reset_funpassword && reset_loginpassword ){
		    	temp += "<button class='button button-info   botton-margin'  onclick='updatePassword(this)' value='"+obj.employeecode+"' title='密码重置'><i class=' icon-info-sign icon-white'></i>密</button>";
   			}
   			if(permission_admin&&employeecode!='${sessionScope.ERCM_USER_SESSION.employeecode}'||employeecode=='<%=SystemConstant.getProperties("admin.employeecode")%>'){
   	    		temp += '<button class="button button-primary  botton-margin" onclick="permission(\'employeecode='+obj.employeecode+'&employeename='+obj.loginaccount+'\')" title="权限管理"><span class="icon-globe icon-white"></span>权</button>';
   	    	}
   			if(employeecode!='${sessionScope.ERCM_USER_SESSION.employeecode}'&&setting_config){
   				if(obj.employeetypecode=='<%=Type.代理.value%>') {
   					temp += "<button class='button button-success botton-margin'  onclick=\"openNewWindow('agentsettlement','${ctx}/GCBonus/agentSetting?employeecode="+obj.employeecode+"','结算配置')\"  title='结算配置'><span class='icon-cog icon-white'/>结</button>";
   				} else {
   					temp += "<button class='button button-success botton-margin'  onclick=\"openNewWindow('agentsettlement','${ctx}/GCBonus/userSetting?employeecode="+obj.employeecode+"','结算配置')\"  title='结算配置'><span class='icon-cog icon-white'/>结</button>";	
   				}
	   			
   			}
   			
   			
   			/* if('sa'=='${sessionScope.ERCM_USER_SESSION.loginaccount}'){
	   			temp += "<button class='button button-success botton-margin'  onclick=\"openNewWindow('agentsettlement','${ctx}/GCBonus/userSetting?employeecode="+obj.employeecode+"','结算配置')\"  title='结算配置'><span class='icon-cog icon-white'/>结</button>";
   			}  */
   		}
    	return temp;
    }}
  ];

	var store  = new Store({
      url : '${ctx}/EEmployee/findEmployeeData',
      autoLoad:false,
      pageSize:15,
      remoteSort: true,
      sortInfo : {
          field : 'lastlogintime',
          direction : 'desc'
        },
      sortInfo : {
          field : 'logindatetime',
          direction : 'desc'
        }, 
  	}),
  	grid = new Grid.Grid({
      render:'#grid',
      loadMask: true,
      autoRender:true,
      width:'100%',
      columns : columns,
      store: store,
      plugins : [ Grid.Plugins.CheckSelection ],
      emptyDataTpl:'<div class="centered"><img alt="Crying" src="${statics }/img/dataisnull.png"/><font style="color:red;font-size:20px;"><h2>没有找到匹配的数据!</h2></font></div>',
      tbar:{
       items:permissionValidate()
      },
      bbar : {
        pagingBar:true
      }
  	}),datepicker = new BUI.Calendar.DatePicker({
        trigger:'.calendar-time',
        showTime:true,
        autoRender:true
     });

	grid.on('cellclick',function(ev){
        var sender = $(ev.domTarget);
        if(!sender.hasClass('x-grid-checkbox')){ //如果不是点中勾选列，不能进行选中操作
          return false;
        }
      });
	
	$("#searchForm").submit(function(){
  	  var obj = BUI.FormHelper.serializeToObject(this);
        obj.start = 0;
        store.load(obj);
  	  return false;
    }).submit();
	
	/* BUI.use('bui/calendar',function(Calendar){
	    var datepicker = new Calendar.DatePicker({
	      trigger:'.calendar-time',
	      showTime:true,
	      autoRender : true
	    });
	}); */
	
	BUI.use(['bui/overlay','bui/form'],function(Overlay,Form){
		/* var form = new Form.Form({
			srcNode : '#validtimeform'
		}).render(); */
		var dialog = new Overlay.Dialog({
		    title:'设置用户有效时间',
		    width:370,
		    height:150,
		    //配置DOM容器的编号
		    contentId:'content',
		    success:function () {
		    	var data = {};
		    	data.validtime = $("#validtime").val();
		    	doOtherServiceMutilterm(grid,data,"${ctx}/EEmployee/validTimeEmployee","employeecode");
		      	this.close();
		    }
		  });
		$('#validtime_btn').on('click',function () {
			debugger;
			var selectItem = grid.getSelection();
        	if (selectItem.length == 0) {
        		BUI.Message.Alert('请选择需要設置的数据', 'info');
        	} else {
        		var sender = $(this),
    		    type = sender.text(),
    		    effect = {
    		      effect : type,
    		      duration : 400
    		      //callback : function(){} ,callback 回调函数
    		    };
   			  	dialog.set('effect',effect);
   			  	dialog.show();
        	}
		});
		

		
		
		
		var dialog2 = new Overlay.Dialog({
		    title:'设置用户等级',
		    width:470,
		    height:150,
		    //配置DOM容器的编号
		    contentId:'content2',
		    success:function () {
		    	var data = {};
		    	data.selectLevel = $("#selectLevel").val();
		    	if($("#selectLevel").val() == "") {
		    		BUI.Message.Alert('请选择等级', 'error');
		    		return ;
		    	}
		    	doOtherServiceMutilterm(grid,data,"${ctx}/EEmployee/setLevel","employeecode");
		      	this.close();
		    }
		  });
		$('#level_btn').on('click',function () {
			var selectItem = grid.getSelection();
        	if (selectItem.length == 0) {
        		BUI.Message.Alert('请选择需要設置的数据', 'info');
        	} else {
        		var sender = $(this),
    		    type = sender.text(),
    		    effect = {
    		      effect : type,
    		      duration : 500
    		      //callback : function(){} ,callback 回调函数
    		    };
   			  	dialog2.set('effect',effect);
   			  	dialog2.show();
        	}
		});
		
		$('#credit_btn').on('click', function(){
			var selectItem = grid.getSelection();
			if (selectItem.length == 0) {
				BUI.Message.Alert('请选择需要設置的数据', 'info');
			} else {
				var employeecodes = '';
				selectItem.forEach(function(e){
					employeecodes += e.employeecode + ',';
				});
				setUserCrditRating(employeecodes);
			}
		});
	});
	
})();

</script>