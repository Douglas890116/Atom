<%@page import="com.maven.config.SystemConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body>
<div class="demo-content" style="margin: 27px;">
    <!-- 表单页 ================================================== --> 
    <div class="row">
      <div>
        <h2><a href="javascript:top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">${requestScope.MENUS.parentmenuname}</a><span class="divider"> / </span>${requestScope.MENUS.menuname }</h2>
        <hr/>
        <form aria-pressed="false" aria-disabled="false" id="objForm" class="form-horizontal bui-form bui-form-field-container" method="post">
            <input type="hidden" name="webtemplatecode" value="${template.webtemplatecode}">
             <input type="hidden" name="sign1" value="${template.sign1}" />
            <div class="control-group">
              <label class="control-label">模板名称：</label>
                <div class="controls">
                  <input name="templatename" class="input-small control-text" style="width:167px;height:18px;" type="text" 
                         data-rules="{required:true,maxlength:25}" data-tip="{text:'模板名称'}" value="${template.templatename}"/>
                </div>
            </div>
            <div class="control-group">
              <label class="control-label">模板类型：</label>
                <div class="controls">
                  <input name="templatetype" class="input-small control-text" style="width:167px;height:18px;" type="text" 
                         data-rules="{required:true,maxlength:10}" data-tip="{text:'模板类型'}" value="${template.templatetype}"/>
                </div>
            </div>
            <div class="control-group">
              <label class="control-label">模板标识：</label>
                <div class="controls">
                  <input name="sign" class="input-small control-text" style="width:167px;height:18px;" type="text" 
                         data-rules="{required:true,maxlength:20}" data-tip="{text:'模板标识'}" value="${template.sign}"/>
                  <input name="sitetype" type="hidden" value="1"/>
                </div>
            </div>
            <div class="row actions-bar">       
	          <div class="form-actions span13 offset3">
	          	<input type="hidden" id="J_Page_Parent" value="${requestScope.MENUS.parentmenucode}" data-reload="true" >
	            <c:if test="${template==null}">
                    <button type="button" id="J_Form_Submit" class="button button-primary" >保存</button>
                </c:if>
                <c:if test="${template!=null}">
                    <button type="button" id="J_Form_update" class="button button-primary" >保存</button>
                </c:if>
	            <button class="button button-danger" type="button" onclick="top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">返回列表</button>
	          </div>
	      </div>
        </form> 
      </div>
    </div>  
    <script type="text/javascript">
    $(function () {
    	BUI.use('custom/commons');
    	BUI.use('bui/overlay');
    	BUI.use(['bui/mask']);
    	var form = new BUI.Form.HForm({
 		      srcNode : '#objForm',
 	    }).render(); 
   		bindClick("J_Form_Submit","${ctx}/brandTemplate/saveTemplate",form);
   		bindClick("J_Form_update","${ctx}/brandTemplate/updateTemplate",form);
    }); 
    </script>
  </div>
  <%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>
</body>
</html>    