<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body>
  <div class="demo-content" style="margin: 27px;">
    <div class="row">
      <div>
        <h2>编辑用户银行信息</h2>
        <hr>
            <form:form modelAttribute="enterpriseEmployeeInformation" method="post" id="objForm" class="form-horizontal">
              <input type="hidden" name="informationcode" value="${bankInfo.informationcode}">
              <input type="hidden" name="employeecode" value="${bankInfo.employeecode}">
              <%-- <div class="control-group">
                <label class="control-label">账&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号:</label>
                <div class="controls">
                    <select name="employeecode" aria-disabled="false" style="width:192px;height:30px;" class="bui-form-field-select bui-form-field" data-rules="{required:true}" disabled="disabled">
	                  <option value="${employee.employeecode }">${employee.loginaccount }</option>
	                </select>
                </div>
              </div> --%>
              <div class="control-group">
                <label class="control-label">银&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;行:</label>
                <div class="controls">
                    <select name="bankcode" id="bankcodeId" data-rules="{required:true}" style="width:192px;height:30px;">
                        <option value="">请选择</option>
                    </select>
                    <div class="common_mark"></div>
                </div>
              </div>
              <div class="control-group">
                  <label class="control-label">银行账号:</label>
                  <div class="controls">
                      <input class="input-normal control-text" name="paymentaccount" type="text" value="${bankInfo.paymentaccount}" style="width:167px;height:18px;"/>
                      <div class="common_mark"></div>
                  </div>
              </div>
              <div class="control-group ">
                <label class="control-label">开&nbsp;&nbsp;户&nbsp;&nbsp;名:</label>
                <div class="controls">
                  <input class="input-normal control-text" name="accountname" type="text" value="${bankInfo.accountname}" data-rules="{required:true}" placeholder="请保持与银行卡开户名一致" style="width:167px;height:18px;"/>
                  <div class="common_mark"><span class="xing">*&nbsp;&nbsp;</span></div>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">开&nbsp;&nbsp;户&nbsp;&nbsp;行:</label>
                <div class="controls">
                    <input class="input-normal control-text" name="openningbank" type="text" value="${bankInfo.openningbank}" data-rules="{required:true}" placeholder="开户行信息" style="width:167px;height:18px;"/>
                    <div class="common_mark"><span class="xing">*&nbsp;&nbsp;</span></div>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">电话号码:</label>
                <div class="controls">
                  <input class="input-normal control-text" type="text" name="phonenumber" value="${bankInfo.phonenumber}" data-rules="{maxlength:11,regexp:/^1(3|4|5|7|8)[0-9]\d{8}$/}" data-messages="{regexp:'无效的手机号码'}" placeholder="作为以后修改银行信息的凭证" style="width:167px;height:18px;" />
                  <div class="common_mark"></div>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">Q&nbsp;Q号&nbsp;码:</label>
                <div class="controls">
                  <input class="input-normal control-text" type="text" name="qq" value="${bankInfo.qq}" data-rules="{maxlength:13,regexp:/^[0-9]*$/}" data-messages="{regexp:'无效的QQ号'}" placeholder="作为以后修改银行信息的凭证" style="width:167px;height:18px;" />
                   <div class="common_mark"></div>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">E&nbsp;m&nbsp;a&nbsp;i&nbsp;l&nbsp;:</label>
                <div class="controls">
                  <input class="input-normal control-text" type="text" name="email" value="${bankInfo.email}" data-rules="{regexp:/^\s*$|^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/}" data-messages="{regexp:'邮箱格式错误'}" style="width:167px;height:18px;" />
                  <div class="common_mark"></div>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">S&nbsp;k&nbsp;y&nbsp;p&nbsp;e&nbsp;:</label>
                <div class="controls">
                  <input class="input-normal control-text" type="text" name="skype" value="${bankInfo.skype}" style="width:167px;height:18px;" />
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label>
                <div class="controls">
                  <input class="input-normal control-text" type="text" name="infomationcomment" value="${bankInfo.infomationcomment}" style="width:167px;height:18px;" />
                </div>
              </div>
              <hr>
               <div class="row actions-bar">
 				<div class="form-actions span5 offset3">
                <button type="button" class="button button-primary" id="J_Form_Submit">提 交 </button>
 				<c:if test="${fromTag != null}">
					<input type="hidden" id="J_Page_Parent" value="MN00CL" data-reload="true" > 				
	                <button class="button" type="button" onclick="top.topManager.openPage({id:'MN00CL',isClose : true});">返回列表</button>
 				</c:if>
 				<c:if test="${fromTag == null}">
					<input type="hidden" id="J_Page_Parent" value="${requestScope.MENUS.parentmenucode}" data-reload="true" >
	                <button class="button" type="button" onclick="top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">返回列表</button>
 				</c:if>
                <!-- <a href="javascript:top.topManager.closePage();">关闭</a> -->
              </div>
 			   </div>
              
        </form:form>
      </div>
    </div>  
  </div>
</body>
</html>
<script type="text/javascript">
//加载所有银行,默认选择上次保存的
$.ajax({
	  	type:"post",
	  	url:getRootPath()+"/common/getAllBankInfo",
	  	dataType:"json",
	  	async:false,
	    success: function(data){
	      	for(var i=0;i<data.obj.length;i++){
	      		if(data.obj[i].bankcode == '${bankInfo.bankcode}'){
	      			$("select[name='bankcode']").append("<option value="+data.obj[i].bankcode+" selected='seleced'>"+data.obj[i].bankname+"</option>");
	      		}
	      		$("select[name='bankcode']").append("<option value="+data.obj[i].bankcode+">"+data.obj[i].bankname+"</option>");
	      	}
	    }
	     
});
$(function(){
	BUI.use(['bui/overlay','bui/mask']);
	var form = new BUI.Form.HForm({
	     srcNode : '#objForm',
	}).render();
	bindClick("J_Form_Submit","${ctx}/EmployeeInformation/updateEnterpriseEmployeeInformation",form);
});
</script>
<%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>