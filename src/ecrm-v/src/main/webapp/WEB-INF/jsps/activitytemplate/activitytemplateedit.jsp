<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body>
<div class="demo-content" style="margin: 27px;">
    <!-- 表单页 ================================================== --> 
    <div class="row">
      <div>
        <h2><a href="javascript:top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">${requestScope.MENUS.parentmenuname}</a><span class="divider"> / </span>${requestScope.MENUS.menuname }</h2>
        <hr/>
        <form aria-pressed="false" aria-disabled="false" id="objForm" class="form-horizontal bui-form bui-form-field-container" method="post" >
            <div class="control-group">
              <label class="control-label" style="margin-right: 12px;">模板名称：</label>
                <div class="controls">
                  <input type="hidden" name="sign" value="${template.sign }">
                  <input name="name" value="${template.name }" class="input-small control-text" style="width:360px;height:18px;" type="text" data-rules="{required:true}" data-tip="{text:'请输入模板名称'}"/>
                </div>
            </div>
            <div class="control-group">
            	<label class="control-label" style="margin-right: 12px;">模板状态：</label>
              <div class="controls">
              	<select name="status" aria-disabled="false" class="bui-form-field-select bui-form-field" data-rules="{required:true}" style="width: 385px;">
                  <option value="1" ${template.status=="1"?"selected='selected'":"" } >启用</option>
                  <option value="2" ${template.status=="2"?"selected='selected'":"" }>禁用</option>
                </select>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label" style="margin-right: 12px;">活动描述：</label>
                <div class="controls">
                	<textarea name="activitydesc" style="width: 360px;height:180px; " class="bui-form-field">${template.activitydesc }</textarea>
                </div>
            </div>
            <hr/>
            <div class="row actions-bar">       
	          <div class="form-actions span13 offset3">
	          	<input type="hidden" id="J_Page_Parent" value="${requestScope.MENUS.parentmenucode}" data-reload="true" >
	            <button type="button" id="J_Form_Submit" class="button button-primary" >保存</button>
	            <button class="button button-danger" type="button" onclick="top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">返回列表</button>
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
   		bindClick("J_Form_Submit","${ctx}/ActivityTemplate//Update",form);
    }); 
    </script>
<!-- script end -->
  </div>
  <%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>
</body>
</html>    