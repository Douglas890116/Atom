<%@page import="com.maven.config.SystemConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>${title }</title>
	<link rel="stylesheet" href="${statics}/css/bs3/bui-min.css" />
    <link rel="stylesheet" href="${statics}/css/bs3/dpl-min.css" />
    <link rel="stylesheet" href="${ctx }/kindeditor/themes/default/default.css"/>
	<link rel="stylesheet" href="${ctx }/kindeditor/plugins/code/prettify.css" />
	<style type="text/css">
		.ke-input-number {
		    width: 50px !important;
		 }
		.ke-input-text {
		    background-color: #FFFFFF;
		    font-family: "sans serif",tahoma,verdana,helvetica;
		    font-size: 12px;
		    line-height: 17px;
		    height: 17px;
		    padding: 2px 4px;
		    border-color: #848484 #E0E0E0 #E0E0E0 #848484;
		    border-style: solid;
		    border-width: 1px;
		    display: -moz-inline-stack;
		    display: inline-block;
		    vertical-align: middle;
		    zoom: 1;
		}
	</style>
</head>
<body>
<div class="demo-content" style="margin: 27px;">
    <!-- 表单页 ================================================== --> 
    <div class="row">
      <div>
        <h2><a href="javascript:top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">${requestScope.MENUS.parentmenuname}</a><span class="divider"> / </span>${requestScope.MENUS.menuname }</h2>
        <hr/>
        <form aria-pressed="false" aria-disabled="false"  name="example" id="objForm" action="${ctx}/ActivityTemplate/Save" class="form-horizontal bui-form bui-form-field-container" method="post" >
            <div class="control-group">
              <label class="control-label" style="margin-right: 12px;">模板名称：</label>
                <div class="controls">
                  <input name="name" class="input-small control-text" style="width:370px;height:18px;" type="text" data-rules="{required:true}" data-tip="{text:'请输入模板名称'}"/>
                </div>
                <label class="control-label" style="margin-right: 12px;margin-left: 120px;">模板状态：</label>
              <div class="controls">
              	<select name="status" aria-disabled="false" class="bui-form-field-select bui-form-field" data-rules="{required:true}" style="width: 200px;">
                  <option value="1">启用</option>
                  <option value="2">禁用</option>
                </select>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">不共享活动：</label>
              <div class="controls">
              	<select name="unshare" aria-disabled="false"  class="bui-form-field-select bui-form-field"  multiple="multiple" style="height: 150px;width: 400px;">
                  <option value="">--请选择--</option>
                  <c:forEach items="${template }" var="m">
	                  <option value="${m.activitycode }" >${m.name}</option>
                  </c:forEach>
                </select>
				&nbsp;(按住Ctrl可进行多选)                
              </div>
            </div>
            <div class="control-group">
              <label class="control-label" style="margin-right: 12px;">活动描述：</label>
                <div class="controls">
                	<textarea name="activitydesc" style="width: 800px;height:400px; " class="bui-form-field"></textarea>
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
    <script type="text/javascript" src="${statics }/js/jquery-1.8.1.min.js"></script>
    <script type="text/javascript" src="${statics }/js/bui.js"></script>
    <script type="text/javascript" src="${statics }/js/ajaxfileupload-min.js"></script>
    <script src="${ctx }/kindeditor/kindeditor-all.js" charset="utf-8"></script>
	<script src="${ctx }/kindeditor/lang/zh-CN.js" charset="utf-8"></script>
	<script src="${ctx }/kindeditor/plugins/code/prettify.js" charset="utf-8"></script>
    <script type="text/javascript">
    $(function () {
    	BUI.use('custom/commons');
    	BUI.use('bui/overlay');
    	BUI.use(['bui/mask']);
    	var form = new BUI.Form.HForm({
 		      srcNode : '#objForm',
 	    }).render(); 
   		bindClick("J_Form_Submit","${ctx}/ActivityTemplate/Save",form);
    }); 
    </script>
    <script>
		KindEditor.ready(function(K) {
			var editor1 = K.create('textarea[name="activitydesc"]', {
				cssPath : '${ctx }/kindeditor/plugins/code/prettify.css',
				uploadJson : '${ctx }/KEFileUpload',
				allowFileManager : false,
				afterBlur: function(){
					this.sync();
					},
				afterCreate : function() {
					var self = this;
					K.ctrl(document, 13, function() {
						self.sync();
						document.forms['example'].submit();
					});
					K.ctrl(self.edit.doc, 13, function() {
						self.sync();
						document.forms['example'].submit();
					});
				}
			});
			prettyPrint();
		});
	</script>
<!-- script end -->
  </div>
  <%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>
</body>
</html>    