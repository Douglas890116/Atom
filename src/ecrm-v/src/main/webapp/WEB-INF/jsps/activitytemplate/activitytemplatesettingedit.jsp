<%@page import="com.maven.config.SystemConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body>
<div class="demo-content" style="margin: 27px;">
    <!-- 表单页 ================================================== --> 
    <div class="row">
      <div>
        <h2><a href="javascript:top.topManager.openPage({id:'ActivityTemplateSetting_Show',isClose : true});">${requestScope.MENUS.parentmenuname}</a><span class="divider"> / </span>${requestScope.MENUS.menuname }</h2>
        <hr/>
        <form aria-pressed="false" aria-disabled="false" id="objForm" class="form-horizontal bui-form bui-form-field-container" method="post" >
            <input type="hidden" value="${setting.sign }" name="sign" >
            
            <div class="control-group">
              <label class="control-label" style="margin-right: 12px;">参数字段：</label>
                <div class="controls">
                  <input name="agementname"  value="${setting.agementname }"  class="input-small control-text" style="width:167px;height:18px;" type="text" data-rules="{required:true}" data-tip="{text:'请输入参数字段'}" />
                </div>
            </div>
            
            <div class="control-group">
              <label class="control-label" style="margin-right: 12px;">参数描述：</label>
                <div class="controls">
                  <input name="agementdesc" value="${setting.agementdesc }" class="input-small control-text" style="width:167px;height:18px;" type="text" data-rules="{required:true}" data-tip="{text:'请输入参数描述'}"/>
                </div>
            </div>
            <hr/>
            <div class="row actions-bar">       
	          <div class="form-actions span13 offset3">
	          	<input type="hidden" id="J_Page_Parent" value="ActivityTemplateSetting_Show" data-reload="true" >
	            <button type="button" id="J_Form_Submit" class="button button-primary" >保存</button>
	            <button class="button button-danger" type="button" onclick="top.topManager.openPage({id:'ActivityTemplateSetting_Show',isClose : true});">返回列表</button>
	          </div>
	      </div>
        </form> 
      </div>
    </div>  
    <script type="text/javascript" src="${statics }/js/ajaxfileupload-min.js"></script>
    <script type="text/javascript">
    $(function () {
    	BUI.use('custom/commons');
    	BUI.use('bui/overlay');
    	BUI.use(['bui/mask']);
    	var form = new BUI.Form.HForm({
 		      srcNode : '#objForm',
 	    }).render(); 
   		bindClick("J_Form_Submit","${ctx}/ATemplateSetting/Update",form);
    }); 
    </script>
<!-- script end -->
  </div>
  <%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>
</body>
</html>    