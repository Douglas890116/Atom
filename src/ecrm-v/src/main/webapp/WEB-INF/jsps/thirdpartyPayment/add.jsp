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
                    <select name="thirdpartypaymenttypecode" data-rules="{required:true}" onchange="thirdpartyPaymentTypeChange(this)">
                        <option value="">--请选择--</option>
                    </select>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">显示名称:</label>
                <div class="controls">
                    <input class="input-normal control-text" name="displayname" id="displayname" type="text" data-rules="{required:true,maxlength:10}" style="width:142px;height:18px;"/>
                    重要：在前端网页显示的名字，最长5个汉字
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">最小充值额:</label>
                <div class="controls">
                    <input class="input-normal control-text" name="minmoney" id="minmoney" type="text" value="1" data-rules="{required:true,min:1}" style="width:142px;height:18px;"/>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">最大充值额:</label>
                <div class="controls">
                    <input class="input-normal control-text" name="maxmoney" id="maxmoney" type="text" value="999999" data-rules="{required:true,min:500}" style="width:142px;height:18px;"/>
                    0表示不设上限
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">排序:</label>
                <div class="controls">
                    <input class="input-normal control-text" name="ord" id="ord" type="text" value="99" data-rules="{required:true,min:0}" style="width:142px;height:18px;"/>
                    	越小越靠前
                </div>
              </div>
              
              <div class="control-group">
                <label class="control-label">描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述:</label>
                <div class="controls">
                    <input class="input-normal control-text" name="dscription" type="text" data-rules="{required:true}" style="width:142px;height:18px;"/>
                </div>
              </div>
              
              <div class="control-group">
                <label class="control-label">支付域名:</label>
                <div class="controls">
                    <input class="input-normal control-text" name="callbackurl" type="text" data-rules="{required:true}" value="http://api.hyzonghe.net/" style="width:142px;height:18px;"/>
                </div>
              </div>
              
              <div id="propertyValueId">
                  
              </div>
              
              <div class="control-group">
              		<input type="hidden" name="isbanks" id="isbanks" value="on"/>
                	<input type="checkbox" name="checkbox1"  checked="checked" onclick="dochange(this,'isbanks')"/>启用银行卡快捷支付
              </div>
              <div class="control-group">
              		<input type="hidden" name="isweixin" id="isweixin" value="on"/>
                	<input type="checkbox" name="checkbox2"  checked="checked" onclick="dochange(this,'isweixin')"/>启用微信支付
              </div>
              <div class="control-group">
              		<input type="hidden" name="iszhifubao" id="iszhifubao" value="on"/>
                	<input type="checkbox" name="checkbox3"  checked="checked" onclick="dochange(this,'iszhifubao')"/>启用支付宝支付
              </div>
              
              <hr>
               <div class="row actions-bar">
 					<div class="form-actions span5 offset3" id="inserBefor">
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
loadThirdpartyPaymentType();

function dochange(obj,idname) {
	   if(obj.checked) {
		   $("#"+idname).val("on");
	   } else {
		   $("#"+idname).val("off");
	   }
}


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
		bindClick("J_Form_Submit","${ctx}/thirdpartyPayment/save",form);
	}
}
</script>
<%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>