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
              <label class="control-label">冲值类型：</label>
              <div class="controls">
              	<select name="plusLess" data-rules="{required:true}" >
                    <option value="">--请选择--</option>
                    <option value="1">冲正</option>
                    <option value="2">冲负</option>
                </select>
              </div>
             </div>
             <div class="control-group">
              <label class="control-label">类型描述：</label>
              <div class="controls">
              	<select name="moneyaddtype" data-rules="{required:true}" onchange="dochange(this)">
                    <option value="">--请选择--</option>
                    <c:forEach var="item" items="${moneyaddtypes }" varStatus="i">
                    	<option value="${item.value }">${item.desc }</option>
                    </c:forEach>
                    <input type="hidden" id="moneyaddtypeName" name="moneyaddtypeName"/>
                </select>
              </div>
             </div>
             
             <div class="control-group">
                  <label class="control-label">用户账号：</label>
                  <div class="controls">
                      <input class="input-normal control-text" name="loginaccount" type="text" style="width:142px;height:18px;"  data-tip="{text:'请输入用户账号'}"  data-rules="{required:true}" />
                      <input type="hidden" name="employeecode" data-rules="{required:true}"/>
                  </div>
              </div>
              
              <div class="control-group">
                  <label class="control-label">冲值金额：</label>
                  <div class="controls">
                      <input class="input-normal control-text" name="money" type="text" style="width:142px;height:18px;"  data-tip="{text:'金额'}"  data-rules="{required:true}" />
                  </div>
              </div>
              
              <div class="control-group">
                  <label class="control-label"><font color="red">流水倍数</font>：</label>
                  <div class="controls">
                      <input class="input-normal control-text" name="lsbs" type="text" style="width:142px;height:18px;"  data-tip="{text:'流水倍数'}"  data-rules="{required:true,number:true,min:0,max:50}" />
                      <div class="common_mark"><font color="red">(注：0-50倍。如不需要流水可输入0)</font></div>
                  </div>
              </div>
              
              <div class="control-group" >
                  <label class="control-label">输入备注：</label>
                  <div class="controls">
                      <input class="input-normal control-text" name="remark" type="text" style="width:142px;height:18px;"  data-tip="{text:'请输入说明'}"  data-rules="{required:true}" />
                  </div>
              </div>
              
            <hr/>
            <div class="row actions-bar">       
	          <div class="form-actions span13 offset3">
	          	<input type="hidden" id="J_Page_Parent" value="${requestScope.MENUS.parentmenucode}" data-reload="true" >
	            <button type="button" id="J_Form_Submit" class="button button-primary" >提交</button>
<%-- 	            <button class="button button-danger" type="button" onclick="top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">返回列表</button> --%>
	          </div>
	      </div>
        </form> 
      </div>
    </div>  
    <script type="text/javascript" src="${statics }/js/ajaxfileupload-min.js"></script>
    <script type="text/javascript">
    
    function dochange(obj) {
    	$("#moneyaddtypeName").val(  $(obj).find("option:selected").text() );
    }
    
    $(function () {
    	BUI.use('custom/commons');
    	BUI.use('bui/overlay');
    	BUI.use(['bui/mask']);
    	var form = new BUI.Form.HForm({
 		      srcNode : '#objForm',
 	    }).render(); 
    	
    	form.getField('loginaccount').set('remote',{
            url : '${ctx}/common/employeeIsExist',
            dataType:'json',
            callback : function(data){
           	 if(data.dont_exist){
           		 return data.dont_exist;
           	 }
           	 if(data.exist){
           		$("input[name='employeecode']").val(data.exist);
           		return '';
           	 }
            }
         });
    	plusLessMinuteSubmit("J_Form_Submit","${ctx}/moneyInAndOut/savePlusLessMinute",form);
    }); 
    </script>
<!-- script end -->
  </div>
  <%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>
</body>
</html>    