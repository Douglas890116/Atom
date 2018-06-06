<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<!DOCTYPE html>
<html>
   <head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   <title>创建企业</title>
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
              <label class="control-label">开户银行：</label>
              <div class="controls">
                 <select name="bankcode" aria-disabled="false" style="width:190px;height:30px;" class="bui-form-field-select bui-form-field" data-rules="{required:true,maxlength:32}">
                  <option value="">-请选择-</option>
                  <c:forEach  var="bank"  items="${banks}">
                  	<option value="${bank.bankcode }">${bank.bankname }</option>
                  </c:forEach>
                </select>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">支行名称：</label>
              <div class="controls">
              	<input name="openningbank" class="input-small control-text" style="width:167px;height:18px;" type="text" data-rules="{required:true,maxlength:16}" data-tip="{text:'请输入支行名称'}"/>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">开户名称：</label>
              <div class="controls">
              	<input name="accountname" class="input-small control-text" style="width:167px;height:18px;" type="text" data-rules="{required:true,maxlength:16}" data-tip="{text:'请输入开户名'}"/>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">银行账号：</label>
              <div class="controls">
              	<input name="openningaccount" class="input-large control-text" style="width:167px;height:18px;" type="text" data-rules="{required:true,maxlength:32}" data-tip="{text:'请输入银行卡号'}"/>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">联&nbsp;&nbsp;系&nbsp;&nbsp;人：</label>
              <div class="controls">
              	<input name="enterprisecontact" class="input-small control-text" style="width:167px;height:18px;" type="text"  data-rules="{maxlength:8}" data-tip="{text:'请输入联系人姓名'}"/>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">联系电话：</label>
              <div class="controls">
              	<input name="enterprisephone" class="input-normal control-text" style="width:167px;height:18px;" type="text"  data-rules="{maxlength:16,number:true}" data-tip="{text:'请输入联系电话'}"/>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">联系Q&nbsp;&nbsp;Q：</label>
              <div class="controls">
              	<input name="enterpriseqq" class="input-normal control-text" style="width:167px;height:18px;" type="text" data-rules="{number:true}" data-tip="{text:'请输入QQ号码'}"/>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">联系邮箱：</label>
              <div class="controls">
              	<input name="enterpriseemail" class="input-large control-text" style="width:167px;height:18px;" type="text" data-rules="{email:true,maxlength:256}" data-tip="{text:'请输入正确的邮箱'}"/>
              </div>
            </div>
             <div class="control-group">
                <label class="control-label">是否启用:</label>
                <div class="controls">
                      <label class="radio" for="enable1"><input type="radio" id="enable1" name="enable" value="1" style="width:16px;" checked="checked"/>启用</label>&nbsp;&nbsp;&nbsp;
                      <label class="radio" for="enable2"><input type="radio" id="enable2" name="enable" value="0" style="width:16px;"/>禁用</label>
                </div>
              </div>
            <hr/>
            <div class="row actions-bar">       
	          <div class="form-actions span13 offset3">
	          	<input type="hidden" id="J_Page_Parent" value="${requestScope.MENUS.parentmenucode}" data-reload="true" >
	            <button type="button" id="J_Form_Submit" class="button button-primary"> 提 交 </button>
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
     	bindClick("J_Form_Submit","${ctx}/EInformation/save",form);
     	
    });
    
    
    </script>
<!-- script end -->
  </div>
  <%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>
</body>
</html>    