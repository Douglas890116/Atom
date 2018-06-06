<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body>
  <div class="demo-content" style="margin: 27px;">
    <div class="row">
      <div>
        <h2><a href="javascript:top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">${requestScope.MENUS.parentmenuname}</a><span class="divider"> / </span>${requestScope.MENUS.menuname }</h2>
        <hr>
            <form  id="objForm" method="post" class="form-horizontal">
              <div class="control-group" id="enterpriseaccount_column">
                <label class="control-label">公司收款账号：</label>
                <div class="controls">
                    <select name="enterprisepaymentaccount" id="enterprisepaymentaccountId" style="width:192px;height:30px;" data-rules="{required:true}" >
                        <option value="">请选择</option>
                        <c:forEach items="${enterprisebanks}" var="enterprisebank">
                        	<option value="${enterprisebank.enterpriseinformationcode }">${enterprisebank.accountname} (${enterprisebank.openningaccount})</option>
                        </c:forEach>
                    </select>
                </div>
              </div>
               <div class="control-group">
                  <label class="control-label">存款用户账号：</label>
                  <div class="controls">
                      <input class="input-normal control-text" name="loginaccount" type="text" style="width:167px;height:18px;"  data-tip="{text:'请输入用户账号'}"  data-rules="{required:true}" />
                      <input type="hidden" name="employeecode" data-rules="{required:true}"/>
                  </div>
              </div>
              <div class="control-group">
                <label class="control-label">存&nbsp;&nbsp;&nbsp;款&nbsp;&nbsp;&nbsp;方&nbsp;&nbsp;式：</label>
                <div class="controls">
                    <select name="paymenttypecode" id="paymenttypecodeId" style="width:192px;height:30px;" data-rules="{required:true}" >
                        <option value="" >请选择</option>
                        <c:forEach items="${paytypes}" var="paytype">
                        	<option value="${paytype.paymenttypecode }">${paytype.paymenttype}</option>
                        </c:forEach>
                    </select>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">存&nbsp;&nbsp;&nbsp;款&nbsp;&nbsp;&nbsp;银&nbsp;&nbsp;行：</label>
                <div class="controls">
                    <select name="employeepaymentbank" style="width:192px;height:30px;" data-rules="{required:true}" >
                        <option value="">请选择</option>
                        <c:forEach items="${banks}" var="bank">
                        	<option value="${bank.bankcode }">${bank.bankname}</option>
                        </c:forEach>
                    </select>
                </div>
              </div>
              <div class="control-group">
                  <label class="control-label">存&nbsp;&nbsp;&nbsp;款&nbsp;&nbsp;&nbsp;金&nbsp;&nbsp;额：</label>
                  <div class="controls">
                      <input class="input-normal control-text" name="orderamount" type="text" style="width:167px;height:18px;" data-tip="{text:'请输入数字'}" data-rules="{required:true,number:true}" />
                      <div class="common_mark">
                      <span id="moneykMsgId"></span>
                  </div>
                  </div>
              </div>
               <div class="control-group">
                <label class="control-label">存&nbsp;款&nbsp;人&nbsp;姓&nbsp;名：</label>
                <div class="controls">
                    <input class="input-normal control-text" name="employeepaymentname" type="text" style="width:167px;height:18px;" data-tip="{text:'请输入存款姓名'}" data-rules="{required:true}" data-messages="{regexp:'格式有误'}"/>
                    <div class="common_mark">
                    <span id="employeeBankMsgId"></span>
                </div>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">存款银行卡号：</label>
                <div class="controls">
                    <input class="input-normal control-text" name="employeepaymentaccount" type="text" style="width:167px;height:18px;" data-tip="{text:'请输入银行卡号码后四位'}" data-rules="{required:true,number:true}" minlength="4" maxlength="19" data-messages="{regexp:'银行卡格式有误'}"/>
                    <div class="common_mark">
                    <span id="employeeBankMsgId"></span>
                </div>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">补&nbsp;&nbsp;&nbsp;单&nbsp;&nbsp;&nbsp;备&nbsp;&nbsp;注：</label>
                <div class="controls">
                  <textarea class="input-normal control-text" name="ordercomment" style="width:167px;height:63px;line-height: 18px;" data-tip="{text:'请输入备注信息'}" data-rules="{required:true}"></textarea>
                </div>
              </div>
              <hr>
              <div class="row actions-bar">    
              <div class="form-actions span5 offset2">
                <input type="hidden" id="J_Page_Parent" value="${requestScope.MENUS.parentmenucode}" data-reload="true" >
	            <button type="button" id="J_Form_Submit" class="button button-primary"> 提 交 </button>
	            <button class="button button-danger" type="button" onclick="top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">返回列表</button>
              </div>
              </div>
        </form>
       
      </div>
    </div>  
  </div>
</body>
<script type="text/javascript">
	$(function(){
		
		BUI.use(['bui/overlay','bui/mask']);
		var form = new BUI.Form.HForm({
		     srcNode : '#objForm',
		}).render();
		
		
		form.getField('loginaccount').set('remote',{
	         url : '${ctx}/common/validateEmployee',
	         dataType:'json',
	         remoteDaly : 800,
	         callback : function(data){
	        	 debugger;
	        	 if(data.status==1){
	        		 $("input[name='employeecode']").val(data.message);
	        		 return;
	        	 }else{
	        		 return data.message;
	        	 }
	         }
	      });
		bindAddClick("J_Form_Submit","${ctx}/takeDepositRecord/SaveDepositOrders",form);
	});
	</script>
</html>
<%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>
