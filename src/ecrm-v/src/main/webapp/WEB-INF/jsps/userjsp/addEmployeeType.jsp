<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
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
    
    <!-- 表单页 ================================================== --> 
    <div class="row">
      <div>
        <h2><a href="${ctx}/EInformation/list">用户类型管理</a><span class="divider"> / </span>添加用户类型</h2>
        <hr/>
        <form aria-pressed="false" aria-disabled="false" id="objForm" class="form-horizontal bui-form bui-form-field-container" method="post" >
             
            <div class="control-group">
              <label class="control-label">用户类型：</label>
              <div class="controls">
              	<input name="employeetype" class="input-small control-text" style="width:167px;height:18px;" type="text" data-rules="{required:true,maxlength:16}"/>
              </div>
            </div>
            
            <hr/>
            <div class="row actions-bar">       
	          <div class="form-actions span13 offset3">
                <c:if test="${sessionScope.ERCM_USER_PSERSSION['MN0065'].menustatus==1}">
	               <button type="button" id="J_Form_Submit" class="button button-primary">保存</button>
                </c:if>
	             <button class="button" type="button" onclick="top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">返回列表</button>
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
    	BUI.use('custom/commons');
    	BUI.use('bui/overlay');
    	BUI.use(['bui/mask']);
    	var form = new BUI.Form.HForm({
 		      srcNode : '#objForm',
 	    }).render(); 
    	bindAddClick("J_Form_Submit","${ctx}/employeeType/save",form);
     	
    });
    </script>
<!-- script end -->
  </div>
  <%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>
</body>
</html>    