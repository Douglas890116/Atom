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
        <h2><a href="javascript:top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">${requestScope.MENUS.parentmenuname}</a><span class="divider"> / </span>快速创建企业帐号</h2>
        <hr/>
        <form aria-pressed="false" aria-disabled="false" id="objForm" class="form-horizontal bui-form bui-form-field-container" method="post" >
          
            <br/>
            <h3>公司信息</h3>
              <div class="control-group">
              <label class="control-label">企业名称：</label>
              <div class="controls">
              	<input name="enterprisename" class="input-normal control-text"   type="text" data-rules="{required:true,maxlength:16}"  data-tip="{text:'长度不超过16个字符'}"/>
              </div>
            </div>
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
            <h3>API证书</h3>
            <div class="control-group">
	                <label class="control-label">接入API编码：</label>
	                <div class="controls">
	                  	<input class="input-normal control-text" name="platformcode" type="text" data-rules="{required:true}" data-tip="{text:'API平台编码'}"  style="width:250px;height:18px;" />
	                </div>
              	</div>
              	 <div class="control-group">
              	 <label class="control-label">接入API地址：</label>
	                <div class="controls">
	                  	<input class="input-normal control-text" name="apiurl" type="text" data-rules="{required:true}" data-tip="{text:'API访问地址'}"  style="width:250px;height:18px;" />
	                </div>
              	 </div>
              	<div class="control-group">
	                <label class="control-label">AES &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  KEY：</label>
	                <div class="controls">
	                  	<input class="input-normal control-text" name="apikey1"  type="text" data-rules="{required:true}" data-tip="{text:'AES KEY'}"  style="width:250px;height:18px;" />
	                </div>
	                
              	</div>
              	<div class="control-group">
              	<label class="control-label">MD5 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;KEY：</label>
	                <div class="controls">
	                  	<input class="input-normal control-text" name="apikey2" type="text" data-rules="{required:true}" data-tip="{text:'MD5 KEY'}"  style="width:250px;height:18px;" />
	                </div>
              	
              	</div>
              	  
               <div class="control-group">
                <label class="control-label">是&nbsp;&nbsp;否&nbsp;&nbsp;启&nbsp;&nbsp;用：</label>
                <div class="controls">
                      <label class="radio" for="enable1"><input type="radio" id="enable1" name="outputapistatus" value="1" style="width:16px;"  checked="checked" />启用</label>&nbsp;&nbsp;&nbsp;
                      <label class="radio" for="enable2"><input type="radio" id="enable2" name="outputapistatus" value="0" style="width:16px;"  />禁用</label>
                </div>
              </div>
               <div class="control-group">
               	<label class="control-label">API开放游戏：</label>
               	<div class="controls">
             	 <c:forEach var="game" varStatus="status" items="${games}">
                  	<label class="control-label checkbox" style="color: #3071A9;font-weight: bold;width:120px;text-align: left;">
		              <input type="checkbox" name="enterprisegames"  value="${game.gamecode}"  ${game.ischoice ? "checked='checked'" : ""} /> ${game.gamename}
		            </label>
		           <c:if test="${status.count%3==0 }"> <br> </c:if>
                 </c:forEach>
                 </div>
               </div>
              
	      
	        <h3>绑定企业域名</h3>
            <div class="control-group">
	                <label class="control-label">会员站点：</label>
	                <div class="controls">
	                  	<input class="input-normal control-text" name="memberUrl" type="text" data-tip="{text:'会员站点域名'}" data-rules="{required:true,regexp:/^(?=^.{3,255}$)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+$/}" data-messages="{regexp:'请输入正确的域名格式，例如:www.google.com'}" data-remote="${ctx}/registerLink/validateDomain"  style="width:250px;height:18px;" />
	                </div>
              	</div>
              	 <div class="control-group">
              	 <label class="control-label">代理站点：</label>
	                <div class="controls">
	                  	<input class="input-normal control-text" name="proxyUrl" type="text" data-tip="{text:'代理站点域名'}" data-rules="{required:true,regexp:/^(?=^.{3,255}$)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+$/}" data-messages="{regexp:'请输入正确的域名格式，例如:www.google.com'}" data-remote="${ctx}/registerLink/validateDomain"  style="width:250px;height:18px;" />
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
    	BUI.use(['bui/mask']);
    	var form = new BUI.Form.HForm({
 		      srcNode : '#objForm',
 	    }).render(); 
    	bindAddClick("J_Form_Submit","${ctx}/enterprise/saveFastCreate",form);
     	
    });
    
    </script>
<!-- script end -->
  </div>
  <%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>
</body>
</html>    