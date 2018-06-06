<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body>
  <div class="demo-content" style="margin: 27px;">
    <div class="row">
      <div>
        <h2><a href="javascript:top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">${requestScope.MENUS.parentmenuname}</a><span class="divider"> / </span>${requestScope.MENUS.menuname }</h2>
        <hr>
            <form:form modelAttribute="enterpriseEmployeeInformation" method="post" id="objForm" class="form-horizontal">
              <div class="control-group">
                <label class="control-label">${isEnglish ? '账&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号:' : 'Account : '}</label>
                <div class="controls">
                    <select name="employeecode" aria-disabled="false" style="width:192px;height:30px;" class="bui-form-field-select bui-form-field" data-rules="{required:true}" disabled="disabled">
	                  <option value="${employee.employeecode }">${employee.loginaccount }</option>
	                </select>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">银&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;行:</label>
                <div class="controls">
                    <select name="bankcode" id="bankcodeId" style="width:192px;height:30px;" data-rules="{required:true}">
                        <option value="">请选择</option>
                    </select>
                    <div class="common_mark"></div>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">开户支行:</label>
                <div class="controls">
                    <input class="input-normal control-text" name="openningbank" type="text" data-rules="{required:true}" data-tip="{text:'请输入支行名称'}" style="width:167px;height:18px;"/>
                    <div class="common_mark"></div>
                </div>
              </div>
              <div class="control-group">
                  <label class="control-label">银行卡号:</label>
                  <div class="controls">
                      <input class="input-normal control-text" name="paymentaccount" type="text" data-remote="${ctx}/EmployeeInformation/validateBankCardNumber" data-rules="{required:true,regexp:/^\d{16,19}$/}" data-messages="{regexp:'银行卡格式有误'}" data-tip="{text:'请输入银行卡号'}" style="width:167px;height:18px;"/>
                      <div class="common_mark"></div>
                  </div>
              </div>
              <div class="control-group ">
                <label class="control-label">开&nbsp;&nbsp;户&nbsp;&nbsp;名:</label>
                <div class="controls">
                  <input class="input-normal control-text" name="accountname" type="text" data-rules="{required:true,maxlength:8}" data-tip="{text:'请输入开户名'}"  style="width:167px;height:18px;"/>
                  <div class="common_mark"></div>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">电话号码:</label>
                <div class="controls">
                  <input class="input-normal control-text" type="text" name="phonenumber" data-rules="{maxlength:11,regexp:/^\s*$|^0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}$/}" data-messages="{regexp:'无效的手机号码'}" data-tip="{text:'请输入手机号码'}" style="width:167px;height:18px;" />
                  <div class="common_mark"></div>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">Q&nbsp;Q号&nbsp;码:</label>
                <div class="controls">
                  <input class="input-normal control-text" type="text" name="qq" data-rules="{maxlength:13,regexp:/^[0-9]*$/}" data-messages="{regexp:'无效的QQ号'}" data-tip="{text:'请输入QQ号码'}" style="width:167px;height:18px;" />
                   <div class="common_mark"></div>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">E&nbsp;m&nbsp;a&nbsp;i&nbsp;l&nbsp;:</label>
                <div class="controls">
                  <input class="input-normal control-text" type="text" name="email" data-rules="{regexp:/^\s*$|^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/}" data-messages="{regexp:'邮箱格式错误'}" data-tip="{text:'请输入邮箱地址'}" style="width:167px;height:18px;" />
                  <div class="common_mark"></div>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">S&nbsp;k&nbsp;y&nbsp;p&nbsp;e:</label>
                <div class="controls">
                  <input class="input-normal control-text" type="text" name="skype" data-tip="{text:'请输入skype账号'}" style="width:167px;height:18px;" />
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label>
                <div class="controls">
                  <input class="input-normal control-text" type="text" name="infomationcomment" data-tip="{text:'备注信息'}" style="width:167px;height:18px;" />
                </div>
              </div>
              <hr>
               <div class="row actions-bar">
 					<div class="form-actions span5 offset3">
                <input type="hidden" id="J_Page_Parent" value="${requestScope.MENUS.parentmenucode}" data-reload="true" >
	            <button type="button" id="J_Form_Submit" class="button button-primary" >保存</button>
	            <button class="button button-danger" type="button" onclick="top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">返回列表</button>
              </div>
 				</div>
              
        </form:form>
      </div>
    </div>  
  </div>
</body>
</html>
<script type="text/javascript">
loadAllBank();
$(function(){
	BUI.use(['bui/overlay','bui/mask']);
	var form = new BUI.Form.HForm({
	     srcNode : '#objForm',
	}).render();
	$("input[name='paymentaccount']").on('remotestart',function(ev){
        var data = ev.data;
        data.paymentaccount = this.value;
      });
	bindClick("J_Form_Submit","${ctx}/EmployeeInformation/saveEnterpriseEmployeeInformation",form);
});
</script>
<%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>