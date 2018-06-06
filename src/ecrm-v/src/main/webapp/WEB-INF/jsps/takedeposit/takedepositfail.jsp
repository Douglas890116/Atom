<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<!-- 财务手动出款显示信息 -->
<div style="margin: 18px;" id="approve-panel">
	<form method="post" action="" id="confirmPaymentForm">
		<div class="control-group">
			<div class="controls">订单编号：${order.ordernumber}</div>
		</div>
		<div class="control-group">
			<div class="controls">
				收款用户：${order.employeepaymentname}</div>
		</div>
		<div class="control-group">
			<div class="controls">
				订单金额：<span style="color: red; font-size: 16px;">${order.orderamount}</span>
			</div>
		</div>
		<div class="control-group">
			<div class="controls">
				收款账号：${order.employeepaymentaccount}</div>
		</div>
		<div class="control-group">
			<div class="controls">
				订单时间：
				<fmt:formatDate value="${order.orderdate}"
					pattern="yyyy-MM-dd hh:mm:ss" />
			</div>
		</div>
		<div class="control-group">
			<div class="controls">订单备注： ${order.ordercomment}</div>
		</div>
		<div class="control-group">
			<div class="controls">
				处理详情：
				<textarea name="auditdesc" style="width: 172px; height: 68px;"></textarea>
				&nbsp;&nbsp;<span style="color: red">*</span>
			</div>
		</div>
		<hr>
		<div class="control-group">
			<h4 class="text-danger">注：出款失败后，订单状态会变为【拒绝】，同时将分数返还到玩家账户。</h4>
			<input type="hidden" name="ordernumber" value="${order.sign}">
			<button type="button" class="button button-danger" onclick="confirmPayment('/takeDepositRecord/fail')">出款失败</button>
		</div>
	</form>
</div>