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
        <form aria-pressed="false" aria-disabled="false" id="objForm" class="form-horizontal bui-form bui-form-field-container" method="post" >
            <input type="hidden" name="moneychangetypecode" value="${employeeMoneyChangeType.moneychangetypecode}" />
            <div class="control-group">
              <label class="control-label">进&nbsp;出&nbsp;帐&nbsp;类&nbsp;型：</label>
           		<div class="controls">
           			<select name="moneyinouttype" aria-pressed="false" aria-disabled="false" data-rules="{required:true}" class="bui-form-field-select bui-form-field span5">
						<option value="">请选择</option>
	                    <option value="1" ${employeeMoneyChangeType.moneyinouttype==1?"selected='selected'":""}>进账</option>
	                    <option value="2" ${employeeMoneyChangeType.moneyinouttype==2?"selected='selected'":""}>出账</option>
              		</select>
           		</div>
            </div>
            <div class="control-group">
              <label class="control-label">帐变类型类别：</label>
           		<div class="controls">
           			<select name="moneychangetypeclassify" aria-pressed="false" aria-disabled="false"   class="bui-form-field-select bui-form-field span5">
						<option value="">请选择</option>
	                    <option value="1" ${employeeMoneyChangeType.moneychangetypeclassify==1?"selected='selected'":""}>常规</option>
	                    <option value="2" ${employeeMoneyChangeType.moneychangetypeclassify==2?"selected='selected'":""}>活动</option>
              		</select>
           		</div>
            </div>
            <div class="control-group">
              <label class="control-label">帐变类型名称：</label>
              <div class="controls">
              	<input type="text" name="moneychangetype" data-rules="{required:true}"  value="${employeeMoneyChangeType.moneychangetype}" class="input-small control-text" style="width:167px;height:18px;" />
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
   		bindClick("J_Form_Submit","${ctx}/moneyChangeType/updateSave",form);
    }); 
    </script>
<!-- script end -->
  </div>
  <%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>
</body>
</html>    