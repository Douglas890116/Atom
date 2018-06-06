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
              <label class="control-label">企&nbsp;业&nbsp;开&nbsp;户&nbsp;银行：</label>
              <div class="controls">
                <select name="bankcode" aria-disabled="false" style="width:190px;height:30px;" class="bui-form-field-select bui-form-field" data-rules="{required:true,maxlength:32}">
                  <option value="">-请选择-</option>
                  <c:forEach  var="bank"  items="${banks}">
                  	<c:if test="${bank.bankcode ==  object.bankcode}">
                  		<option value="${bank.bankcode }" selected="selected">${bank.bankname }</option>
                  	</c:if>
                  	<c:if test="${bank.bankcode != object.bankcode }">
                  		<option value="${bank.bankcode }">${bank.bankname }</option>
                  	</c:if>
                  </c:forEach>
                </select>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">支&nbsp;&nbsp;&nbsp;&nbsp;行&nbsp;&nbsp;&nbsp;&nbsp;名&nbsp;&nbsp;&nbsp;&nbsp;称：</label>
              <div class="controls">
              	<input name="openningbank" class="input-small control-text" style="width:167px;height:18px;" type="text" data-rules="{required:true,maxlength:16}"  value="${requestScope.object.openningbank }"/>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">开&nbsp;&nbsp;&nbsp;&nbsp;户&nbsp;&nbsp;&nbsp;&nbsp;名&nbsp;&nbsp;&nbsp;&nbsp;称：</label>
              <div class="controls">
              	<input name="accountname" class="input-small control-text" style="width:167px;height:18px;" type="text" data-rules="{required:true,maxlength:16}" value="${requestScope.object.accountname }"/>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">银&nbsp;&nbsp;&nbsp;&nbsp;行&nbsp;&nbsp;&nbsp;&nbsp;账&nbsp;&nbsp;&nbsp;&nbsp;号：</label>
              <div class="controls">
              	<input name="openningaccount" class="input-large control-text" style="width:167px;height:18px;" type="text" data-rules="{required:true,maxlength:32}" value="${requestScope.object.openningaccount }"/>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">企&nbsp;&nbsp;业&nbsp;&nbsp;联&nbsp;&nbsp;系&nbsp;&nbsp;人：</label>
              <div class="controls">
              <input name="sign"  value="${requestScope.object.sign }"  type="hidden" />
              	<input name="enterprisecontact" class="input-small control-text" style="width:167px;height:18px;" type="text"  data-rules="{required:true,maxlength:8}" value="${requestScope.object.enterprisecontact }"/>
              </div>
            </div>
<!--             <div class="control-group"> -->
<!--               <label class="control-label">卡&nbsp;&nbsp;&nbsp;&nbsp;号&nbsp;&nbsp;&nbsp;&nbsp;余&nbsp;&nbsp;&nbsp;&nbsp;额：</label> -->
<!--               <div class="controls"> -->
<%--               <input name="sign"  value="${requestScope.object.sign}"  type="hidden" /> --%>
<%--               	<input name="currentbalance" class="input-small control-text" style="width:167px;height:18px;" type="text"  data-rules="{number:true}" value="${requestScope.object.currentbalance}"/> --%>
<!--               </div> -->
<!--             </div> -->
            <div class="control-group">
              <label class="control-label">企业联系人电话：</label>
              <div class="controls">
              	<input name="enterprisephone" class="input-normal control-text" style="width:167px;height:18px;"  type="text"  data-rules="{maxlength:16,number:true}" value="${requestScope.object.enterprisephone }"/>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">企业联系人Q&nbsp;&nbsp;Q：</label>
              <div class="controls">
              	<input name="enterpriseqq" class="input-normal control-text" style="width:167px;height:18px;" type="text" data-rules="{number:true}" value="${requestScope.object.enterpriseqq }"/>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">企业联系人邮箱：</label>
              <div class="controls">
              	<input name="enterpriseemail" class="input-large control-text" style="width:167px;height:18px;" type="text" data-rules="{email:true,maxlength:256}" value="${requestScope.object.enterpriseemail }"/>
              </div>
            </div>
           <div class="control-group">
                <label class="control-label">是否启用:</label>
                <div class="controls">
                      <label class="radio" for="enable1"><input type="radio" id="enable1" name="enable" value="1" style="width:16px;" ${requestScope.object.enable==1?"checked='checked'":""} />启用</label>&nbsp;&nbsp;&nbsp;
                      <label class="radio" for="enable2"><input type="radio" id="enable2" name="enable" value="0" style="width:16px;" ${requestScope.object.enable==0?"checked='checked'":""} />禁用</label>
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
     	bindClick("J_Form_Submit","${ctx}/EInformation/update",form);
     	
    });
    
    
    </script>
<!-- script end -->
  </div>
  <%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>
</body>
</html>    