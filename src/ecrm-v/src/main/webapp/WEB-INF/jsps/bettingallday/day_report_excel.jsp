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
    
    <meta charset="UTF-8">
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
  			<td colspan="7" bgcolor="#FAFAD2" align="center"><h3>${title}</h3></td>
  		</tr>
  	</table>
    <table width="100%" cellpadding="4" cellspacing="0" class="toTab" border="0">
		<thead>
			<th class="toLabelTd">游戏平台</th> 
			<th class="toLabelTd">会员账号</th>
			<th class="toLabelTd">玩家账号</th>
			
			<th class="toLabelTd">投注金额</th>
			<th class="toLabelTd">有效投注金额</th>
			<th class="toLabelTd">输赢金额</th>
			<th class="toLabelTd">局数</th>
    	</thead>
    	<tbody>
    		<c:forEach var="item" items="${list}" varStatus="i">
    			<tr>
    				<td class="toDataTd">${item.platformtype}</td>
    				<td class="toDataTd">${item.loginaccount}</td>
    				<td class="toDataTd">${item.brandcode}</td>
    				
    				<td class="toDataTd"><fmt:formatNumber value="${item.betmoney}" pattern="##.##" minFractionDigits="2"/></td>
    				<td class="toDataTd"><fmt:formatNumber value="${item.validbet}" pattern="##.##" minFractionDigits="2"/></td>
    				<td class="toDataTd"><fmt:formatNumber value="${item.netmoney}" pattern="##.##" minFractionDigits="2"/></td>
    				<td class="toDataTd">${item.cnt} 局</td>
    			</tr>
    		</c:forEach>
    			<tr>
    				<td class="toDataTd" align="right" colspan="3">合计：</td>
    				
    				<td class="toDataTd"><fmt:formatNumber value="${total_betmoney }" pattern="##.##" minFractionDigits="2"/></td>
    				<td class="toDataTd"><fmt:formatNumber value="${total_validbet }" pattern="##.##" minFractionDigits="2"/></td>
    				<td class="toDataTd"><fmt:formatNumber value="${total_netmoney }" pattern="##.##" minFractionDigits="2"/></td>
    				<td class="toDataTd">${total_count } 局</td>
    			</tr>
    	</tbody>
			
    </table>
  </body>
</html>