<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<!DOCTYPE html>
<html>
   <head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   <title>创建部门</title>
   <!-- 此文件为了显示Demo样式，项目中不需要引入 -->
<link href="${statics}/css/bs3/bui-min.css" rel="stylesheet" />
<link href="${statics}/css/bs3/dpl-min.css" rel="stylesheet" /> 
 
</head>
<body>
<div class="demo-content" style="margin: 27px;">
    
    <!-- 表单页 ================================================== --> 
    <div class="row">
      <div>
        <h2><a href="${ctx}/Department/list">部门管理</a><span class="divider"> / </span>创建部门</h2>
        <hr/>
        <form aria-pressed="false" aria-disabled="false" id="objForm" class="form-horizontal bui-form bui-form-field-container" method="post" >
            <div class="control-group">
              <label class="control-label">企业名称：</label>
              <div class="controls">
                <input aria-pressed="false" aria-disabled="false" disabled="disabled" style="width:167px;height:18px;"  class="control-text bui-form-field" type="text" value="${edpart.enterprisename}" />
                <input name="departmentcode" class="input-normal control-text"  type="hidden"  value="${edpart.departmentcode }"/>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">部门名称：</label>
              <div class="controls">
              	<input name="department" class="input-small control-text" style="width:167px;height:18px;" type="text" data-rules="{required:true,maxlength:8}" value="${edpart.department }"/>
              </div>
            </div>
            <hr/>
            <div class="row actions-bar">       
	          <div class="form-actions span13 offset3">
	            <button type="button" id="J_Form_Submit" class="button button-primary">保存</button>
	             <button class="button button-danger" type="button" onclick="top.topManager.closePage();">返回列表</button>
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
     	bindClick("J_Form_Submit","${ctx}/Department/update",form);
    });
    
    
    </script>
<!-- script end -->
  </div>
  <%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>
</body>
</html>    