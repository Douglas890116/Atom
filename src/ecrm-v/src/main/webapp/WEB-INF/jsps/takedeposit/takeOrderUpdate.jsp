<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body>
  <div class="demo-content" style="margin: 27px;">
    <div class="row">
      <div>
        <h2><a href="javascript:top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">${requestScope.MENUS.parentmenuname}</a><span class="divider"> / </span>${requestScope.MENUS.menuname }</h2>
        <hr>
            <form:form modelAttribute="takeDepositRecord" id="objForm" method="post" class="form-horizontal" >
               <div class="control-group">
                  <label class="control-label">取款订单号码：</label>
                  <div class="controls"><a href="##" style="line-height: 30px;">${order.ordernumber }</a>
                  </div>
              </div>
               <div class="control-group">
                  <label class="control-label">取款用户账号：</label>
                  <div class="controls">
                      <input class="input-normal control-text" name="loginaccount" type="text" style="width:210px;height:18px;"  data-tip="{text:'请输入用户账号'}"  data-rules="{required:true}" value="${order.loginaccount }" disabled="disabled"/>
                      <input type="hidden" name="ordernumber" data-rules="{required:true}" value="${order.sign }" />
                  </div>
              </div>
              <div class="control-group">
                <label class="control-label">取&nbsp;款&nbsp;银&nbsp;行&nbsp;卡：</label>
                <div class="controls">
                    <select name="employeepaymentaccount" id="informationcode" style="width:235px;height:30px;" data-rules="{required:true}" >
                        <option value="" >请选择</option>
                        <c:forEach items="${banks }" var="bank">
                        	<c:choose>
                        		<c:when test="${bank.paymentaccount == order.employeepaymentaccount}">
                        			<option value="${bank.informationcode }" selected="selected" >${bank.accountname} (${bank.paymentaccount }) </option>
                        		</c:when>
                        		<c:otherwise>
                        			<option value="${bank.informationcode }" >${bank.accountname} (${bank.paymentaccount }) </option>	
                        		</c:otherwise>
                        	</c:choose>
                        </c:forEach>
                    </select>
                </div>
              </div>
              <div class="control-group">
                  <label class="control-label">取&nbsp;&nbsp;&nbsp;款&nbsp;&nbsp;&nbsp;金&nbsp;&nbsp;额：</label>
                  <div class="controls">
                      <input class="input-normal control-text" name="orderamount" type="text" style="width:210px;height:18px;" data-tip="{text:'请输入数字'}" data-rules="{required:true,number:true}" value="${order.orderamount }"/>
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
		//加载支付方式
		loadPaymentType();
		//加载企业所有银行卡信息
		loadEnterpriseBankInformation();
		
		//存款单显示企业银行账号
		$("#ordertype").change(function(){
			if($(this).val() !="1"){
				$("#enterprisepaymentaccountId").val("").attr("disabled","disabled");
			}else{
				$("#enterprisepaymentaccountId").removeAttr("disabled");
			}
		});
		
		//订单类型
		$("#ordertype").children().each(function(){
			if('${obj.ordertype}'==this.value){
				$(this).attr("selected","selected");
      		}
		});
		//企业收款银行
		$("#enterprisepaymentaccountId").children().each(function(){
			if('${obj.enterprisepaymentaccount}'==this.value){
				$(this).attr("selected","selected");
      		}
		});
		//支付方式
		$("#paymenttypecodeId").children().each(function(){
			if('${obj.paymenttypecode}'==this.value){ 
				$(this).attr("selected","selected");
      		}
		});
		
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
		bindClick("J_Form_Submit","${ctx}/takeDepositRecord/updateTakeOrders",form);
	});
	</script>
</html>
<%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>
