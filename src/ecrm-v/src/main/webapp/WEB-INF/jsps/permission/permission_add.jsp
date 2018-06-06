<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
   <head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   <title>添加菜单</title>
   <!-- 此文件为了显示Demo样式，项目中不需要引入 -->
<link href="${statics}/css/bs3/bui-min.css" rel="stylesheet" />
<link href="${statics}/css/bs3/dpl-min.css" rel="stylesheet" /> 
 
</head>
<body>
<div class="demo-content" style="margin: 27px;">
    
    <!-- 表单页 ================================================== --> 
    <div class="row">
      <div>
        <h2><a href="javascript:top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">${requestScope.MENUS.parentmenuname}</a><span class="divider"> / </span>${requestScope.MENUS.menuname }</h2>
        <hr/>
        <form aria-pressed="false" aria-disabled="false" id="objForm" class="form-horizontal bui-form bui-form-field-container" method="post" >
            <div class="control-group">
              <label class="control-label">上级菜单：</label>
              <div class="controls">
              	<select aria-pressed="false" name="parentmenucode" aria-disabled="false" class="bui-form-field-select bui-form-field bui-form-field-error" data-rules="{required:true}">
                  <option value="${parentMenue.menucode}">${parentMenue.menuname}&nbsp;(${parentMenue.menulevel}级)</option>
                </select>
                <input name="menulevel" class="input-small control-text"  type="hidden"  value="${parentMenue.menulevel+1}"/>
                <input name="sign" class="input-small control-text"  type="hidden"  value="${parentMenue.sign}"/>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">菜单标题：</label>
              <div class="controls">
              	<input name="menuname" class="input-normal control-text"  type="text" data-rules="{required:true}"/>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">排序：</label>
              <div class="controls">
              	<input name="menusort" class="input-small control-text"  type="text"  data-rules="{required:true,number:true}"/>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">状态：</label>
              <div class="controls">
                <label class="control-label radio" style="width: 50px;">
              		<input type="radio" name="menustatus" value="1" checked="checked"/>正常 
              	</label>
              	<label class="control-label radio" style="width: 50px;">
              		<input type="radio" name="menustatus" value="2"/> 禁用
          		</label>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">访问URL：</label>
              <div class="controls">
              	<input name="menuurl" class="input-large control-text"  type="text"  data-rules="{required:true,maxlength:150}"/>
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
    	bindAddClick("J_Form_Submit","${ctx}/PermissionMenu/save",form);
    });
    
    
    </script>
<!-- script end -->
  </div>
  <%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>
</body>
</html>    