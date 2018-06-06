<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<!DOCTYPE html>
<html>
   <head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   <title>创建企业</title>
   <!-- 此文件为了显示Demo样式，项目中不需要引入 -->
<link href="${statics}/css/bs3/bui-min.css" rel="stylesheet" />
<link href="${statics}/css/bs3/dpl-min.css" rel="stylesheet" /> 
</head>
<body style="padding: 27px;">
<div class="demo-content">
    <jsp:useBean id="propertis" class="com.maven.config.SystemConstant"></jsp:useBean>
    <!-- 表单页 ================================================== --> 
    <div class="row">
      <div>
        <h2><a href="javascript:top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">${requestScope.MENUS.parentmenuname}</a><span class="divider"> / </span>${requestScope.MENUS.menuname }</h2>
        <hr/>
        <form aria-pressed="false" aria-disabled="false" id="objForm" class="form-horizontal bui-form bui-form-field-container" method="post" >
            <div class="control-group">
              <label class="control-label">企业名称：</label>
              <div class="controls">
              	<input name="enterprisename" class="input-normal control-text"   type="text" data-rules="{required:true,maxlength:16}"  data-tip="{text:'长度不超过16个字符'}"/>
              </div>
            </div>
            <br/><hr/>
            <h3>创建企业账号</h3>
            <div class="control-group">
              <label class="control-label">用户昵称：</label>
              <div class="controls">
              	<input name="displayalias" class="input-normal control-text "   type="text" data-rules="{required:true,maxlength:8}"  data-tip="{text:'长度不超过8个字符'}"/>
              </div>
            </div>
             <div class="control-group">
              <label class="control-label">登录账号：</label>
              <div class="controls">
              	<input name="loginaccount" class="input-normal control-text"   type="text"  data-rules="{required:true,minlength:6,maxlength:12,regexp:/^[0-9a-zA-z]{6,12}$/}" data-remote="${ctx}/EEmployee/employeeIsExistForBUI" data-tip="{text:'长度为6-12个字符'}" />
              </div>
            </div>
             <div class="control-group">
              <label class="control-label">登录密码：</label>
              <div class="controls">
              	<input name="loginpassword" id="loginpassword" class="input-normal control-text"   type="password" data-rules="{required:true,minlength:6,regexp:/^[a-z0-9A-Z]{6,20}$/}" data-tip="{text:'长度为6-18个字符'}" data-messages="{regexp:'密码只能是数字或者字母'}"  />
              </div>
            </div>
             <div class="control-group">
              <label class="control-label">确认密码：</label>
              <div class="controls">
              	<input class="input-normal control-text"   type="password" data-rules="{required:true,minlength:6,equalTo:'#loginpassword'}" data-tip="{text:'与登陆密码一致'}"/>
              </div>
            </div>
            <hr/>
            <div class="row actions-bar">       
	          <div class="form-actions span13 offset2">
	          	<input type="hidden" id="J_Page_Parent" value="${requestScope.MENUS.parentmenucode}" data-reload="true" >
	            <button type="button" id="J_Form_Submit" class="button button-primary">保存</button>
	            <button class="button button-danger" type="button" onclick="top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">返回列表</button>
	          </div>
	      </div> 
        </form> 
      </div>
    </div>  
    <script src="${statics }/js/jquery-1.8.1.min.js"></script>
    <script src="${statics }/js/bui.js"></script> 
    <script src="${statics }/js/config.js"></script>
    <script type="text/javascript">
    $(function () {
    	BUI.use('custom/common');
    	BUI.use('bui/overlay');
    	BUI.use("ajaxfileupload");
    	BUI.use(['bui/mask']);
    	var form = new BUI.Form.HForm({
 		      srcNode : '#objForm',
 	    }).render(); 
    	bindAddClick("J_Form_Submit","${ctx}/Enterprise/Save",form);
     	
    });
    
    </script>
<!-- script end -->
  </div>
  <%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>
</body>
</html>    