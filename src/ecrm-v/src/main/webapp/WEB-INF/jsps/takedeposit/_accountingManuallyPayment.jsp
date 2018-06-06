<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<!-- 财务手动出款显示信息 -->
    <div style="margin: 18px;" id="approve-panel">
        <form method="post" action="" id="confirmPaymentForm">
              <div class="control-group">
                  <div class="controls" >
                                                         订单编号：${takeDepositRecord.ordernumber}
                  </div>
              </div>
              <div class="control-group">
                  <div class="controls">
                                                         收款用户：${takeDepositRecord.employeepaymentname}
                  </div>
              </div>
              <div class="control-group">
                  <div class="controls">
                                                         订单金额：<span style="color: red;font-size: 16px;">${takeDepositRecord.orderamount}</span>
                  </div>
              </div>
               <div class="control-group">
                  <div class="controls">
                                                         收款账号：${takeDepositRecord.employeepaymentaccount}
                  </div>
              </div>
              <div class="control-group">
                  <div class="controls">
                                                         订单时间： <fmt:formatDate value="${takeDepositRecord.orderdate}" pattern="yyyy-MM-dd hh:mm:ss"/>
                  </div>
              </div>
              <div class="control-group">
                  <div class="controls">
                                                         订单备注： ${takeDepositRecord.ordercomment}
                  </div>
              </div>
              <div class="control-group">
                  <div class="controls">
                                                          出款用户：${sessionScope.ERCM_USER_SESSION.loginaccount}
                  </div>
              </div>
              <div class="control-group">
                  <div class="controls">
                                                        处理详情：<textarea name="auditdesc" style="width:172px;height:68px;"></textarea>&nbsp;&nbsp;<span style="color: red">*</span>
                  </div>
              </div>
              <hr>
              <div class="control-group">
                <input type="hidden" name="ordernumber" value="${takeDepositRecord.sign}">
                <c:if test="${orderMark==5}">
                  <button type="button" class="button button-primary" onclick="confirmPayment('/takeDepositRecord/confirmPayment')">确认出款</button>
                </c:if>
                <c:if test="${orderMark==4}">
                  <button type="button" class="button button-primary" onclick="confirmPayment('/takeDepositRecord/refusal')">确认拒绝</button>
                </c:if>
            </div>
        </form>
  </div>
