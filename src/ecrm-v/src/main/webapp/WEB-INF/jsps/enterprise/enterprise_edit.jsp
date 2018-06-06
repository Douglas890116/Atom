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
<body>
<div class="demo-content" style="margin: 27px;">
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
              	<input name="sign" type="hidden" value="${enterprise.sign}">
              	<input name="enterprisename" class="input-normal control-text"  value="${enterprise.enterprisename}"  type="text" data-rules="{required:true,maxlength:16}"/>
              </div>
            </div>
            
            <div class="control-group">
                <label class="control-label">是否启用IP白名单:</label>
                <div class="controls">
                      <label class="radio" for="enable1"><input type="radio" id="enable1" name="ipenable" value="1" style="width:16px;" ${enterprise.ipenable=="1"?"checked='checked'":""} />启用</label>&nbsp;&nbsp;&nbsp;
                      <label class="radio" for="enable2"><input type="radio" id="enable2" name="ipenable" value="2" style="width:16px;" ${enterprise.ipenable=="2"?"checked='checked'":""} />禁用</label>
                </div>
              </div>
              
            <div class="control-group">
              <label class="control-label">存款手续费比例：</label>
              <div class="controls">
              	<input name="depositrate" class="input-normal control-text"  value="${enterprise.depositrate * 100}"  type="text" data-rules="{required:true,min:0,max:5,number:true}"/>&nbsp;%
              </div>
            </div>
            
            <div class="control-group">
              <label class="control-label">取款手续费比例：</label>
              <div class="controls">
              	<input name="takerate" class="input-normal control-text"  value="${enterprise.takerate  * 100}"  type="text" data-rules="{required:true,min:0,max:5,number:true}"/>&nbsp;%
              </div>
            </div>  
            
            <div class="control-group">
                <label class="control-label">平分转分模式:</label>
                <div class="controls">
                      <label class="radio" for="transfertype1"><input type="radio" id="transfertype1" name="transfertype" value="1" style="width:16px;" ${enterprise.transfertype=="1"?"checked='checked'":""} />自动模式</label>&nbsp;&nbsp;&nbsp;
                      <label class="radio" for="transfertype2"><input type="radio" id="transfertype2" name="transfertype" value="2" style="width:16px;" ${enterprise.transfertype=="2"?"checked='checked'":""} />手动模式</label>
                </div>
              </div>
            
            <br/><hr/>
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
    	bindClick("J_Form_Submit","${ctx}/Enterprise/Update",form);
    });
    
    </script>
<!-- script end -->
  </div>
  <%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>
</body>
</html>    