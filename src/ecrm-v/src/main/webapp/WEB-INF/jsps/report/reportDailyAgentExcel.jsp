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
  			<td colspan="11" bgcolor="#FAFAD2" align="center"><h3>${title}</h3></td>
  		</tr>
  	</table>
    <table width="100%" cellpadding="4" cellspacing="0" class="toTab" border="0">
		<thead>
			<th class="toLabelTd">序号</th>
    		
    		<th class="toLabelTd">代理用户名</th>
    		<th class="toLabelTd">游戏平台</th>
    		<th class="toLabelTd">游戏种类</th>

			<th class="toLabelTd">洗码比例</th>
			<th class="toLabelTd">有效投注额</th>
			<th class="toLabelTd">洗码金额</th>
    		<th class="toLabelTd">洗码比率</th>
    		
    		<th class="toLabelTd">投注日期</th>
    		<th class="toLabelTd">发放时间</th>
    		<th class="toLabelTd">发放状态</th>
    	</thead>
    	<tbody>
    		<c:forEach var="item" items="${listDailyData}" varStatus="i">
    			<tr>
    				<td class="toDataTd">${i.index + 1}</td>
    				
    				<td class="toDataTd">${item.loginaccount}</td>
    				<td class="toDataTd">${item.gameplatform}</td>
    				<td class="toDataTd">${item.gametype}</td>
    				
    				<td class="toDataTd"><fmt:formatNumber value="${item.ratio * 100}" pattern="##.##" minFractionDigits="2"/>%</td>
    				<td class="toDataTd"><fmt:formatNumber value="${item.bet}" pattern="##.##" minFractionDigits="2"/></td>
    				<td class="toDataTd"><fmt:formatNumber value="${item.amount}" pattern="##.##" minFractionDigits="2"/></td>
    				<td class="toDataTd">${item.ratio}</td>
    				
    				<td class="toDataTd"><fmt:formatDate value="${item.time}" pattern="yyyy.MM.dd" type="date"/></td>
    				<td class="toDataTd"><fmt:formatDate value="${item.reporttime}" pattern="yyyy.MM.dd HH:mm:ss" type="both"/></td>
    				
    				<td class="toDataTd">
    					<c:if test="${item.status eq 1}">未发放</c:if>
    					<c:if test="${item.status eq 2}">已发放</c:if>
    					<c:if test="${item.status eq 3}">周末汇总</c:if>
    				</td>
    			</tr>
    		</c:forEach>
    	</tbody>
    </table>
  </body>
</html>