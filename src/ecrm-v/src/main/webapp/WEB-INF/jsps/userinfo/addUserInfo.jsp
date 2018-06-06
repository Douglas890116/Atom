<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body>
  <div class="demo-content" style="margin: 27px;">
    <div class="row">
      <div>
        <h2><a href="javascript:top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">${isEnglish ? requestScope.MENUS.parentenglishname : requestScope.MENUS.parentmenuname}</a><span class="divider"> / </span>${isEnglish ? requestScope.MENUS.englishname : requestScope.MENUS.menuname }</h2>
        <hr>
            <form:form modelAttribute="enterpriseEmployeeInformation" method="post" id="objForm" class="form-horizontal">
              <div class="control-group">
                <label class="control-label">${isEnglish ? 'Account:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' : '账&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号:'}</label>
                <div class="controls">
					<input name="loginaccount" type="text" data-rules="{required:true}" data-tip="{text:'请输入用户账号'}" style="width: 165px;"/>
					<input name="employeecode" type="hidden" data-rules="{required:true}"/>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">${isEnglish ? 'Bank Name:&nbsp;&nbsp;&nbsp;&nbsp;' : '银&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;行:'}</label>
                <div class="controls">
                    <select name="bankcode" id="bankcodeId" style="width:192px;height:30px;" data-rules="{required:true}">
                        <option value="">${isEnglish ? '---select---' : '请选择'}</option>
                    </select>
                    <div class="common_mark"></div>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">${isEnglish ? 'Bank Branch:&nbsp;&nbsp;' : '开户支行:'}</label>
                <div class="controls">
                    <input class="input-normal control-text" name="openningbank" type="text" data-rules="{required:true}" data-tip="{text:'请输入支行名称'}" style="width:167px;height:18px;"/>
                    <div class="common_mark"></div>
                </div>
              </div>
              <div class="control-group">
                  <label class="control-label">${isEnglish ? 'Card Num:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' : '银行卡号:'}</label>
                  <div class="controls">
                      <input class="input-normal control-text" name="paymentaccount" type="text" data-remote="${ctx}/EmployeeInformation/validateBankCardNumber" data-rules="{required:true,regexp:/^\d{16,19}$/}" data-messages="{regexp:'银行卡格式有误'}" data-tip="{text:'请输入银行卡号'}" style="width:167px;height:18px;"/>
                      <div class="common_mark"></div>
                  </div>
              </div>
              <div class="control-group ">
                <label class="control-label">${isEnglish ? 'Bank Account:' : '开&nbsp;&nbsp;户&nbsp;&nbsp;名:'}</label>
                <div class="controls">
                  <input class="input-normal control-text" name="accountname" type="text" data-rules="{required:true,maxlength:10}" data-tip="{text:'请输入开户名'}"  style="width:167px;height:18px;"/>
                  <div class="common_mark"></div>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">${isEnglish ? 'Phone No:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' : '电话号码:'}</label>
                <div class="controls">
                  <input class="input-normal control-text" type="text" name="phonenumber" data-rules="{maxlength:11,regexp:/^\s*$|^0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}$/}" data-messages="{regexp:'无效的手机号码'}" data-tip="{text:'请输入手机号码'}" style="width:167px;height:18px;" />
                  <div class="common_mark"></div>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">${isEnglish ? 'Q&nbsp;&nbsp;Q:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' : 'Q&nbsp;Q号码:'}</label>
                <div class="controls">
                  <input class="input-normal control-text" type="text" name="qq" data-rules="{maxlength:13,regexp:/^[0-9]*$/}" data-messages="{regexp:'无效的QQ号'}" data-tip="{text:'请输入QQ号码'}" style="width:167px;height:18px;" />
                   <div class="common_mark"></div>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">${isEnglish ? 'Email:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' : 'E&nbsp;m&nbsp;a&nbsp;i&nbsp;l&nbsp;:'}</label>
                <div class="controls">
                  <input class="input-normal control-text" type="text" name="email" data-rules="{regexp:/^\s*$|^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/}" data-messages="{regexp:'邮箱格式错误'}" data-tip="{text:'请输入邮箱地址'}" style="width:167px;height:18px;" />
                  <div class="common_mark"></div>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">${isEnglish ? 'Skype:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' : 'S&nbsp;k&nbsp;y&nbsp;p&nbsp;e:'}</label>
                <div class="controls">
                  <input class="input-normal control-text" type="text" name="skype" data-tip="{text:'请输入skype账号'}" style="width:167px;height:18px;" />
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">${isEnglish ? 'Remarks:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' : '备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:'}</label>
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
	form.getField('loginaccount').set('remote', {
		url : '${ctx}/common/employeeIsExist',
		dataType : 'json',
		callback : function(data) {
			if (data.dont_exist) {
				return data.dont_exist;
			}
			if (data.exist) {
				$("input[name='employeecode']").val(data.exist);
				return '';
			}
		}
	});
	bindClick("J_Form_Submit","${ctx}/EmployeeInformation/addEnterpriseEmployeeInformation",form);
});
</script>
<%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>