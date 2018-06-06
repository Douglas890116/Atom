<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<!-- 活动参数设置-->
    <div style="margin: 18px;" id="approve-panel">
        <form method="post" action="" id="approveform">
              <div class="control-group">
                  <div class="controls" >
                                                         订单号：${order.ordernumber}
                  </div>
              </div>
              <div class="control-group">
                  <div class="controls">
                                                         取款人：${order.employeepaymentname}
                  </div>
              </div>
              <div class="control-group">
                  <div class="controls">
                                                         订单金额：<span style="color: red;font-size: 16px;">${order.orderamount}</span>
                  </div>
              </div>
               <div class="control-group">
                  <div class="controls">
                                                         付款账号：${order.employeepaymentaccount}
					<hr/>
                  </div>
              </div>
              <div class="control-group">
                  <div class="controls">
                                                         订单时间： <fmt:formatDate value="${order.orderdate}" pattern="yyyy-MM-dd hh:mm:ss"/>
                                                         
                  </div>
              </div>
              <div class="control-group">
                  <div class="controls">
                                                         补单备注： ${order.ordercomment}
                                                         
                  </div>
              </div>
              <div class="control-group">
                  <div class="controls">
                                                          审核人：${sessionScope.ERCM_USER_SESSION.loginaccount}
                  </div>
              </div>
              <br />
              <div class="control-group">
                  <div class="controls">
                                        审&nbsp;核&nbsp;说&nbsp;明：
					<textarea  name="auditdesc"  style="width:172px;height:68px;" ></textarea>&nbsp;&nbsp;<span style="color: red">*</span>
					<select multiple="multiple" style="height: 78px;" id="quick_instructions">
                  		<option>风控稽核通过</option>
                  	</select>
                  </div>
              </div>
              <br />
              <div class="control-group">
                  <div class="controls">
                                                        图&nbsp;片&nbsp;上&nbsp;传：<input type="file" id="fileId" name="file" style="width:152px;" dir="rtl" />
                                                <input type="button" value="上传" onclick="approveFileUpload('${ctx}/FileUpload','take','approve-imgname');" style="background: none repeat scroll 0 0 transparent;"/>
                                                <input type="hidden"  name="imgname" id="approve-imgname"/> 
                  </div>
              </div>
              <br />
              <div class="control-group">
                  <div class="controls" id="imgId"></div>
              </div>
              <hr>
              <div class="control-group">
                <input type="hidden" name="ordernumber" value="${order.sign }">
                <button type="button" class="button button-primary" onclick="pass();">通过</button>
                <button type="button" class="button button-danger"  style="margin-left: 320px" onclick="refusal();">拒绝</button>
                <button type="button" class="button button-warning" onclick="dismiss();">驳回</button>
                <br>
                <span style="color:red;">通过:订单进入下一流程;&nbsp;&nbsp;&nbsp;&nbsp;拒绝:订单作废;&nbsp;&nbsp;&nbsp;&nbsp;驳回:订单返回用户进行修改</span>
            </div>
        </form>
  </div>
<script type="text/javascript">
$(function(){
	$("#quick_instructions option").dblclick(function(){
		$(this).parent().prevAll("textarea[name='auditdesc']").val($(this).val());
	});
})
</script>
