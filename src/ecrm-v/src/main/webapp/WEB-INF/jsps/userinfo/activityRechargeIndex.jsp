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
            
            <!-- 
            <div class="control-group">
              <label class="control-label">活动类型：</label>
              <div class="controls">
              	<select name="activityRechargeType" data-rules="{required:true}">
                    <option value="">--请选择--</option>
                </select>
              </div>
             </div>
              -->
             <div class="control-group">
              <label class="control-label">活动名称：</label>
              <div class="controls">
              	<select name="activityRechargeType" data-rules="{required:true}" onchange="dochange(this)">
                    <option value="">--请选择--</option>
                    <!-- 
                    <c:forEach var="item" items="${listEnterpriseActivityCustomization }" varStatus="i" >
                    	<option value="${item.ecactivitycode }">${item.activityname }</option>
                    </c:forEach>
                     -->
                     <c:forEach var="item" items="${listEnterpriseBrandActivity }" varStatus="i" >
                    	<option value="${item.ecactivitycode }">${item.activityname }</option>
                    </c:forEach>
                    <input type="hidden" id="activityRechargeTypeName" name="activityRechargeTypeName"/>
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
                  <label class="control-label">存款本金：</label>
                  <div class="controls">
                      <input class="input-normal control-text" name="depositMoney" type="text" style="width:142px;height:18px;"  data-tip="{text:'存款金额'}"  data-rules="{required:true,number:true,min:0}" value="0"/>
                  </div>
              </div>
              
              <div class="control-group">
                  <label class="control-label">充值金额：</label>
                  <div class="controls">
                      <input class="input-normal control-text" name="money" type="text" style="width:142px;height:18px;"  data-tip="{text:'优惠金额'}"  data-rules="{required:true,number:true}" />
                  </div>
              </div>
              
              <div class="control-group">
                  <label class="control-label">流水倍数：</label>
                  <div class="controls">
                      <input class="input-normal control-text" name="lsbs" type="text" style="width:142px;height:18px;"  data-tip="{text:'流水倍数'}" data-rules="{required:true,number:true,min:0,max:100}" />
                       所需流水=（存款本金+充值金额）X 流水倍数
                  </div>
              </div>
              
              <div class="control-group">
                  <label class="control-label">充值说明：</label>
                  <div class="controls">
                      <input class="input-normal control-text" name="desc" type="text" style="width:142px;height:18px;"  data-tip="{text:'描述,最大45个字'}"  data-rules="{required:true,maxlength:45}" />
                  </div>
              </div>
              
              <div class="control-group">
              <label class="control-label" >禁用游戏：</label>
              <div class="controls">
              	<select name="disableSelectEmployee" aria-disabled="false"  class="bui-form-field-select bui-form-field" style="width: 193px;height:200px"  multiple="multiple">
	                  <c:forEach var="game" varStatus="status" items="${sessionScope.ERCM_ENTERPRISE_GAMES}">
	                    	<option value="${game.gametype }">${game.gamename }</option>
	                    </c:forEach>
                </select>
                <div class="common_mark">如需限制玩家不能登录的游戏，请在这里选择</div>
              </div>
            </div>
              
              <p><font color="red">提交后需要人工审核。请为相关审核人员授权【红利信息】的审核及发放权限！</font></p>
              
              
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
    //loadActivityMoneyChangeType();
    
    function dochange(obj) {
    	$("#activityRechargeTypeName").val(  $(obj).find("option:selected").text() );
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
    	plusLessMinuteSubmit("J_Form_Submit","${ctx}/moneyInAndOut/saveActivityRecharge",form);
    }); 
    </script>
<!-- script end -->
  </div>
  <%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>
</body>
</html>    