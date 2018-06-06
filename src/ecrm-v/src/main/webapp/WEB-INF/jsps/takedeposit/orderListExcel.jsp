<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.maven.utils.StringUtil" %>
<%@ page import="java.net.URLEncoder" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
response.setContentType("application/vnd.ms-excel");


String date1 = StringUtil.getCurrentTime();
String title = (String)request.getAttribute("title");
title = URLEncoder.encode(title, "UTF-8");
response.setHeader("Content-Disposition", "attachment; filename=report-" + date1 + "-" + title +".xls");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">

.toLabelTd {
	background-color: #e0f0ff;
	border-bottom-width: 1px;
	border-bottom-style: solid;
	border-bottom-color: #003366;
	border-right-width: 1px;
	border-right-style: solid;
	border-right-color: #003366;
}

.toDataTd {
	border-right-width: 1px;
	border-bottom-width: 1px;
	border-right-style: solid;
	border-bottom-style: solid;
	border-right-color: #003366;
	border-bottom-color: #003366;
}

.toDataTd_N123 {
	border-bottom-width: 1px;
	border-bottom-style: solid;
	border-bottom-color: #003366;
}

.toDataTdC {
	border-right-width: 1px;
	border-bottom-width: 1px;
	border-right-style: solid;
	border-bottom-style: solid;
	border-right-color: #003366;
	border-bottom-color: #003366;
	text-align: center;
}

.toDataTdL {
	border-right-width: 1px;
	border-bottom-width: 1px;
	border-right-style: solid;
	border-bottom-style: solid;
	border-right-color: #003366;
	border-bottom-color: #003366;
	text-align: left;
}

.toDataTdL_P5 {
	border-right-width: 1px;
	border-right-style: solid;
	border-right-color: #003366;
	text-align: left;
	padding:5px;
}

.toDataTdR {
	border-right-width: 1px;
	border-bottom-width: 1px;
	border-right-style: solid;
	border-bottom-style: solid;
	border-right-color: #003366;
	border-bottom-color: #003366;
	text-align: right;
}

.toTabLine{
	border-top-width: 1px;
	border-top-style: solid;
	border-top-color: #003366;
}

.toTab{
	border-top-width: 1px;
	border-left-width: 1px;
	border-top-style: solid;
	border-left-style: solid;
	border-top-color: #003366;
	border-left-color: #003366;
}
.toTab2 {
	border-left-width: 1px;
	border-left-style: solid;
	border-left-color: #000000;
}
.toFormTitle {
	background-image: url(btnbar.jpg);
	height: 24px;
	border-right: 1px solid #003366;
	border-bottom: 1px solid #003366;
	font-weight: bold;


}
</style>	
  </head>
  
  <body>
  	<table width="100%" border="1">
  		<tr>
  			<td colspan="17" bgcolor="#FAFAD2" align="center"><h3>${title }</h3></td>
  		</tr>
  		<tr>
  			<td colspan="17" bgcolor="#FAFAD2">说明：P001=通汇支付 P002=易宝支付 P003=通汇-微信 P004=乐盈支付 P005=优付支付 P006=国采支付</td>
  		</tr>
  	</table>
    <table width="100%" cellpadding="4" cellspacing="0" class="toTab" border="0">
    	<thead>
    		<th class="toLabelTd">序号</th>
    		<th class="toLabelTd">品牌</th>
    		<th class="toLabelTd">订单号</th>
    		<th class="toLabelTd">账号</th>
    		<th class="toLabelTd">订单金额</th>
    		<th class="toLabelTd">订单时间</th>
    		
    		<th class="toLabelTd">付款人</th>
    		<th class="toLabelTd">付款账号</th>
    		<th class="toLabelTd">企业收款人</th>
    		<th class="toLabelTd">会员付款银行</th>
    		<th class="toLabelTd">企业收款账号</th>
    		
    		<th class="toLabelTd">状态</th>
    		<th class="toLabelTd">交易IP</th>
    		
    		<th class="toLabelTd">支付方式</th>
    		<th class="toLabelTd">企业收款银行</th>
    		
    		<th class="toLabelTd">订单类型</th>
    		<th class="toLabelTd">附言</th>
    	</thead>
    	<tbody>
    		<c:forEach var="item" items="${listTakeDepositRecord }" varStatus="i">
    			<tr>
    				<td class="toDataTd">${i.index + 1 }</td>
    				<td class="toDataTd">${item.brandcode }</td>
    				<td class="toDataTd">${item.ordernumber }</td>
    				<td class="toDataTd">${item.loginaccount }</td>
    				
    				
    				
    				<td class="toDataTd"><fmt:formatNumber value="${item.orderamount }" pattern="##.##" minFractionDigits="2" /></td>
    				
    				<fmt:formatDate var="orderdate" value="${item.orderdate}" type="both" pattern="yyyy.MM.dd HH:mm:ss" />
    				
    				<td class="toDataTd">${orderdate }</td>
    				
    				<td class="toDataTd">${item.employeepaymentname }</td>
    				<td class="toDataTd">&nbsp;${item.employeepaymentaccount }</td>
    				<td class="toDataTd">${item.enterprisepaymentname }</td>
    				<td class="toDataTd">${item.employeepaymentbank }</td>
    				<td class="toDataTd">&nbsp;${item.enterprisepaymentaccount }</td>
    				
    				<td class="toDataTd">
    					<c:if test="${item.orderstatus eq 1}">审核中</c:if>
    					<c:if test="${item.orderstatus eq 2}">审核通过</c:if>
    					<c:if test="${item.orderstatus eq 3}">驳回</c:if>
    					<c:if test="${item.orderstatus eq 4}">拒绝</c:if>
    					<c:if test="${item.orderstatus eq 5}">待出款</c:if>
    				</td>
    				<td class="toDataTd">${item.traceip }</td>
    				
    				<td class="toDataTd">${item.paymenttypecode }</td>
    				<td class="toDataTd">${item.enterprisepaymentbank }</td>
    				
    				
    				<td class="toDataTd">
    					<c:if test="${item.ordertype eq 1}">存款</c:if>
    					<c:if test="${item.ordertype eq 2}">取款</c:if>
    				</td>
    				<td class="toDataTd">${item.ordercomment }</td>
    				
    				
    			</tr>
    		</c:forEach>
    	</tbody>
    </table>
  </body>
</html>
