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
  			<td colspan="9" bgcolor="#FAFAD2" align="center"><h3>${title }</h3></td>
  		</tr>
  	</table>
    <table width="100%" cellpadding="4" cellspacing="0" class="toTab" border="0">
    	<thead>
    		<th class="toLabelTd">序号</th>
    		
    		<th class="toLabelTd">用户账号</th>
    		<th class="toLabelTd">用户别名</th>
    		<th class="toLabelTd">账变类型</th>
    		<th class="toLabelTd">帐变前余额</th>
    		<th class="toLabelTd">账变金额</th>
    		<th class="toLabelTd">账变后金额</th>
    		<th class="toLabelTd">账变时间</th>
    		
    		<th class="toLabelTd">备注</th>
    	</thead>
    	<tbody>
    		<c:forEach var="item" items="${listEmployeeMoneyChange }" varStatus="i">
    			<tr>
    				<td class="toDataTd">${i.index + 1 }</td>
    				<td class="toDataTd">${item.loginaccount }</td>
    				<td class="toDataTd">${item.employeename }</td>
    				<td class="toDataTd">${item.moneychangetypename }</td>
    				
    				<fmt:parseNumber value="${item.settlementamount}" var="settlementamount" />
    				<fmt:parseNumber value="${item.moneychangeamount}" var="moneychangeamount" />
    				<fmt:parseNumber value="${item.afteramount}" var="afteramount" />
    				
    				<td class="toDataTd"><fmt:formatNumber value="${settlementamount }" pattern="##.##" minFractionDigits="2" /></td>
    				<td class="toDataTd"><fmt:formatNumber value="${moneychangeamount }" pattern="##.##" minFractionDigits="2" /></td>
    				<td class="toDataTd"><fmt:formatNumber value="${afteramount }" pattern="##.##" minFractionDigits="2" /></td>
    				
    				<td class="toDataTd"><fmt:formatDate var="moneychangedate" value="${item.moneychangedate}" type="both" pattern="yyyy.MM.dd HH:mm:ss" />${moneychangedate }</td>
    				
    				<td class="toDataTd">&nbsp;${item.moneyinoutcomment }</td>
    				
    			</tr>
    		</c:forEach>
    	</tbody>
    </table>
  </body>
</html>
