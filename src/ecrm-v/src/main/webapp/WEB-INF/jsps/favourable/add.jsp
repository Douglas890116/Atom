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
            
            <div class="control-group">
              <label class="control-label">优惠名称：</label>
                <div class="controls">
                  <input name="favourablename" class="input-small control-text" style="width:167px;height:18px;" type="text" data-rules="{required:true}" />
                </div>
            </div>
            
            <div class="control-group">
              <label class="control-label">流水倍数：</label>
                <div class="controls">
                  <input name="lsbs" class="input-small control-text" style="width:167px;height:18px;" type="text" data-rules="{required:true,number:true}" />
                </div>
            </div>
            
            <div class="control-group">
              <label class="control-label" >开始时间：</label>
              <div class="controls">
                  <input name="starttime" class="calendar calendar-time" style="width:167px;height:18px;" type="text" data-rules="{required:true}" data-tip="{text:'请输入活动开始时间'}"/>
                </div>
            </div>
            <div class="control-group">
              <label class="control-label" >结束时间：</label>
              <div class="controls">
                  <input name="endtime" class="calendar calendar-time" style="width:167px;height:18px;" type="text" data-rules="{required:true}" data-tip="{text:'请输入活动结束时间'}"/>
                </div>
            </div>
            <div class="control-group">
              <label class="control-label" >优惠类型：</label>
              <div class="controls">
              	<select name="isonce" aria-disabled="false" data-rules="{required:true}" class="bui-form-field-select bui-form-field" style="width: 193px;">
                  <option value="">--请选择--</option>
	                  <option value="0">一次性优惠</option>
	                  <option value="1">多次优惠</option>
                </select>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label" >默认优惠：</label>
              <div class="controls">
              	<select name="isdeault" aria-disabled="false" data-rules="{required:true}" class="bui-form-field-select bui-form-field" style="width: 193px;">
                  <option value="">--请选择--</option>
	                  <option value="0">不是默认</option>
	                  <option value="1">默认</option>
                </select>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label" >优惠状态：</label>
              <div class="controls">
              	<select name="status" aria-disabled="false" data-rules="{required:true}" class="bui-form-field-select bui-form-field" style="width: 193px;">
	                  <option value="0">启用</option>
	                  <option value="1">禁用</option>
                </select>
              </div>
            </div>
            
            <div class="control-group">
              <label class="control-label" >加入用户（可选）：</label>
              <div class="controls">
              	<select name="vips" aria-disabled="false"  class="bui-form-field-select bui-form-field" style="width: 193px;height:200px"  multiple="multiple">
	                  <c:forEach var="item" items="${listEnterpriseEmployeeLevel }" varStatus="i" >
                    	<option value="${item.employeeLevelCode }">VIP等级 [${item.employeeLevelName }]</option>
                    </c:forEach>
                </select>
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
    	BUI.use(['bui/tooltip']);
    	var form = new BUI.Form.HForm({
 		      srcNode : '#objForm',
 	    }).render(); 

	      
   		bindClick("J_Form_Submit","${ctx}/favourable/Save",form);
    }); 
    </script>
<!-- script end -->
  </div>
  <%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>
</body>
</html>    
