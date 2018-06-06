<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body>
  <div class="demo-content" style="margin: 27px;">
    <div class="row">
      <div>
        <h2><a href="javascript:top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">${requestScope.MENUS.parentmenuname}</a><span class="divider"> / </span>${requestScope.MENUS.menuname }</h2>
        <hr>
            <form:form method="post" id="objForm" class="form-horizontal">
            
            	<div class="control-group">
                <label class="control-label">支付类型:</label>
                <div class="controls">
                    <select name="thirdpartypaymenttypecode" data-rules="{required:true}">
                        <option value="">--请选择--</option>
                    </select>
                </div>
              </div>
              
              <div class="control-group">
                <label class="control-label">银行名称:</label>
                <div class="controls">
                    <select id="thirdpartypaymentbankname" name="thirdpartypaymentbankname" data-rules="{required:true}">
                        <option value="">--请选择--</option>
                    </select>
                </div>
              </div>
              
              <div class="control-group">
                <label class="control-label">银行编码:</label>
                <div class="controls">
                    <input class="input-normal control-text" name="paymenttypebankcode" type="text" data-rules="{required:true}" style="width:142px;height:18px;"/>
                </div>
              </div>
              
              <div class="control-group">
                <label class="control-label">状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态:</label>
                <div class="controls">
                      <label class="radio" for="enable1"><input type="radio" id="enable1" name="enable" value="1" style="width:16px;" checked="checked"/>启用</label>&nbsp;&nbsp;&nbsp;
                      <label class="radio" for="enable2"><input type="radio" id="enable2" name="enable" value="2" style="width:16px;"  />禁用</label>
                      <div class="common_mark"></div>
                </div>
              </div>
              <div id="propertyValueId">
                  
              </div>
              <hr>
              <div class="form-actions span5 offset3" id="inserBefor">
                  <input type="hidden" id="J_Page_Parent" value="${requestScope.MENUS.parentmenucode}" data-reload="true" >
                  <button type="button" id="J_Form_Submit" class="button button-primary" >保存</button>
                  <button class="button button-danger" type="button" onclick="top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">返回列表</button>
              </div>
        </form:form>
      </div>
    </div>  
  </div>
</body>
</html>
<script type="text/javascript">
loadThirdpartyPaymentBankName();
loadThirdpartyPaymentType();

$(function(){
  BUI.use(['bui/overlay','bui/mask']);
  var form = new BUI.Form.HForm({
       srcNode : '#objForm',
  }).render();
  validateInput(form);
});
function validateInput(form){
	var isOk = true;
	$("#propertyValueId").find("input").each(function(){
		if(isEmpty($(this).val())){
			isOk=false;
			$(this).parent().append('<span class="valid-text"><span class="estate error"><span class="x-icon x-icon-mini x-icon-error">!</span><em>不能为空！</em></span></span>');
		}
	});
	if(isOk){
		BUI.use(['bui/overlay','bui/mask']);
		var form = new BUI.Form.HForm({
		       srcNode : '#objForm',
		  }).render();
		bindClick("J_Form_Submit","${ctx}/thirdpartyPaymentBank/save",form);
	}
}
</script>
<%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>