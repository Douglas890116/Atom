<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body>
  <div class="demo-content" style="margin: 27px;">
    <div class="row">
      <div>
        <h2><a href="javascript:top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">${requestScope.MENUS.parentmenuname}</a><span class="divider"> / </span>${requestScope.MENUS.menuname }</h2>
        <hr>
            <form:form modelAttribute="takeDepositRecord" id="objForm" method="post" class="form-horizontal">
              <div class="control-group">
                  <label class="control-label">取款订单号码：</label>
                  <div class="controls"><a href="##" style="line-height: 30px;">${order.ordernumber }</a>
                  </div>
              </div>
              <div class="control-group">
                  <label class="control-label">存款用户账号：</label>
                  <div class="controls">
                      <input class="input-normal control-text" name="loginaccount" type="text" style="width:210px;height:18px;"  data-tip="{text:'请输入用户账号'}"  data-rules="{required:true}" disabled="disabled" value="${order.loginaccount }"/>
                      <input type="hidden" name="ordernumber"  value="${order.sign }"/>
                  </div>
              </div>
              <div class="control-group" id="enterpriseaccount_column">
                <label class="control-label">公司收款账号：</label>
                <div class="controls">
                    <select name="enterprisepaymentaccount" id="enterprisepaymentaccountId" style="width:235px;height:30px;" data-rules="{required:true}" >
                        <option value="">请选择</option>
                        <c:forEach items="${enterprisebanks}" var="enterprisebank">
                        	<c:choose>
                        		<c:when test="${enterprisebank.openningaccount == order.enterprisepaymentaccount }">
                        			<option value="${enterprisebank.enterpriseinformationcode }" selected="selected">${enterprisebank.accountname} (${enterprisebank.openningaccount})</option>
                        		</c:when>
                        		<c:otherwise>
                        			<option value="${enterprisebank.enterpriseinformationcode }">${enterprisebank.accountname} (${enterprisebank.openningaccount})</option>
                        		</c:otherwise>
                        	</c:choose>
                        </c:forEach>
                    </select>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">存&nbsp;&nbsp;&nbsp;款&nbsp;&nbsp;&nbsp;方&nbsp;&nbsp;式：</label>
                <div class="controls">
                    <select name="paymenttypecode" id="paymenttypecodeId" style="width:235px;height:30px;" data-rules="{required:true}" >
                        <option value="" >请选择</option>
                        <c:forEach items="${paytypes}" var="paytype">
                        	<c:choose>
                        		<c:when test="${paytype.paymenttypecode == order.paymenttypecode }">
                        			<option value="${paytype.paymenttypecode }" selected="selected">${paytype.paymenttype}</option>
                        		</c:when>
                        		<c:otherwise>
                        			<option value="${paytype.paymenttypecode }">${paytype.paymenttype}</option>
                        		</c:otherwise>
                        	</c:choose>
                        </c:forEach>
                    </select>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">存&nbsp;&nbsp;&nbsp;款&nbsp;&nbsp;&nbsp;银&nbsp;&nbsp;行：</label>
                <div class="controls">
                    <select name="employeepaymentbank" style="width:235px;height:30px;" data-rules="{required:true}" >
                        <option value="">请选择</option>
                        <c:forEach items="${banks}" var="bank">
                        	<c:choose>
                        		<c:when test="${bank.bankcode  == order.employeepaymentbank }">
                        			<option value="${bank.bankcode }" selected="selected">${bank.bankname}</option>
                        		</c:when>
                        		<c:otherwise>
                        			<option value="${bank.bankcode }">${bank.bankname}</option>
                        		</c:otherwise>
                        	</c:choose>
                        </c:forEach>
                    </select>
                </div>
              </div>
              <div class="control-group">
                  <label class="control-label">存&nbsp;&nbsp;&nbsp;款&nbsp;&nbsp;&nbsp;金&nbsp;&nbsp;额：</label>
                  <div class="controls">
                      <input class="input-normal control-text" name="orderamount" type="text" style="width:210px;height:18px;" data-tip="{text:'请输入数字'}" data-rules="{required:true,number:true}"  value="${order.orderamount }"/>
                      <div class="common_mark">
                      <span id="moneykMsgId"></span>
                  </div>
                  </div>
              </div>
               <div class="control-group">
                <label class="control-label">存&nbsp;款&nbsp;人&nbsp;姓&nbsp;名：</label>
                <div class="controls">
                    <input class="input-normal control-text" name="employeepaymentname" type="text" style="width:210px;height:18px;" data-tip="{text:'请输入存款姓名'}" data-rules="{required:true}" value="${order.employeepaymentname }"/>
                    <div class="common_mark">
                    <span id="employeeBankMsgId"></span>
                </div>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">存款银行卡号：</label>
                <div class="controls">
                    <input class="input-normal control-text" name="employeepaymentaccount" type="text" style="width:210px;height:18px;" data-tip="{text:'请输入银行卡号码'}" data-rules="{required:true,regexp:/^\d{16,19}$/}" maxlength="19" data-messages="{regexp:'银行卡格式有误'}" value="${order.employeepaymentaccount }"/>
                    <div class="common_mark">
                    <span id="employeeBankMsgId"></span>
                </div>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">补&nbsp;&nbsp;&nbsp;单&nbsp;&nbsp;&nbsp;备&nbsp;&nbsp;注：</label>
                <div class="controls">
                  <textarea class="input-normal control-text" name="ordercomment" style="width:210px;height:63px;line-height: 18px;" data-tip="{text:'请输入备注信息'}" data-rules="{required:true}" >${order.ordercomment }</textarea>
                </div>
              </div>
              <hr>
               <div class="row actions-bar">
 				<div class="form-actions span5 offset3">
	                <input type="hidden" id="J_Page_Parent" value="${requestScope.MENUS.parentmenucode}" data-reload="true" >
		            <button type="button" id="J_Form_Submit" class="button button-primary"> 提 交 </button>
		            <button class="button button-danger" type="button" onclick="top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">返回列表</button>
	              </div>
 				</div>
        </form:form>
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
	         callback : function(data){
	        	 if(data.status==1){
	        		 $("input[name='employeecode']").val(data.message);
	        		 return;
	        	 }else{
	        		 return data.message;
	        	 }
	         }
	      });
		bindClick("J_Form_Submit","${ctx}/takeDepositRecord/updateDepositOrders",form);
	});
	</script>
</html>
<%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>
