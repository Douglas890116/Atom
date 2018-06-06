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
  			<td colspan="33" bgcolor="#FAFAD2" align="center"><h3>${title}</h3></td>
  		</tr>
  	</table>
    <table width="100%" cellpadding="4" cellspacing="0" class="toTab" border="0">
		<thead>
			<th class="toLabelTd">序号</th>
			
			<th class="toLabelTd">订单号</th>
			<th class="toLabelTd">用户账号</th>
			<th class="toLabelTd">会员名</th>
			<th class="toLabelTd">游戏类别</th>
						
			<th class="toLabelTd">投注号</th>
			<!-- <th class="toLabelTd">下注内容</th> -->
			<th class="toLabelTd">赔率</th>
			<th class="toLabelTd">单双</th>
			<th class="toLabelTd">大小个数</th>
			<th class="toLabelTd">是否取消</th>
			<th class="toLabelTd">是否危险球</th>
			<th class="toLabelTd">是否结算</th>
			<th class="toLabelTd">输掉的金额</th>
			
			<th class="toLabelTd">联赛ID</th>
			<th class="toLabelTd">货币比率</th>
			<th class="toLabelTd">红牌</th>
			<th class="toLabelTd">赛事结果</th>
			<th class="toLabelTd">让球个数</th>
			<th class="toLabelTd">让球队伍</th>
			<th class="toLabelTd">下注球类ID号</th>
			<th class="toLabelTd">走地时间</th>
			<th class="toLabelTd">结算日期</th>
			<th class="toLabelTd">赢半，赢，输半，输，和</th>
			
			<th class="toLabelTd">下注金额</th>
			<th class="toLabelTd">下注队伍</th>
			<th class="toLabelTd">下注类别</th>
			<th class="toLabelTd">输赢金额</th>
			<th class="toLabelTd">走地比分</th>
			<th class="toLabelTd">开赛时间</th>
			<th class="toLabelTd">下注时间</th>
			<th class="toLabelTd">注单更新顺序号</th>
			<th class="toLabelTd">平台更新时间</th>    		
    	</thead>
    	<tbody>
    		<c:forEach var="item" items="${listData}" varStatus="i">
    			<tr>
    				<td class="toDataTd">${i.index + 1}</td>
    				
    				<td class="toDataTd">${item.ibcRowguid}</td>
    				<td class="toDataTd">${item.loginaccount}</td>
    				<td class="toDataTd">${item.ibcUsername}</td>
    				<td class="toDataTd">${item.ibcIsbk}</td>
    				
    				<td class="toDataTd">${item.ibcOrderid}</td>
    				<%-- <td class="toDataTd">${item.ibcContent}</td> --%>
    				<td class="toDataTd"><fmt:formatNumber value="${item.ibcCurpl}" pattern="##.##" minFractionDigits="2"/></td>
    				<td class="toDataTd">${item.ibcDs}</td>
    				<td class="toDataTd">${item.ibcDxc}</td>
    				<td class="toDataTd">${item.ibcIscancel}</td>
    				<td class="toDataTd">${item.ibcIsdanger}</td>
    				<td class="toDataTd">${item.ibcIsjs}</td>
    				<td class="toDataTd"><fmt:formatNumber value="${item.ibcLose}" pattern="##.##" minFractionDigits="2"/></td>
    				    				
    				<td class="toDataTd">${item.ibcMatchid}</td>
    				<td class="toDataTd">${item.ibcMoneyrate}</td>
    				<td class="toDataTd">${item.ibcRecard}</td>
    				<td class="toDataTd">${item.ibcResult}</td>
    				<td class="toDataTd">${item.ibcRqc}</td>
    				<td class="toDataTd">${item.ibcRqteam}</td>
    				<td class="toDataTd">${item.ibcSportid}</td>
    				<td class="toDataTd"><fmt:formatDate value="${item.ibcTballtime}" var="ibcTballtime" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
    				<td class="toDataTd">${item.ibcThisdate}</td>
    				<td class="toDataTd">${item.ibcTruewin}</td>

    				<td class="toDataTd"><fmt:formatNumber value="${item.ibcTzmoney}" pattern="##.##" minFractionDigits="2"/></td>
    				<td class="toDataTd">${item.ibcTzteam}</td>
    				<td class="toDataTd">${item.ibcTztype}</td>
    				<td class="toDataTd"><fmt:formatNumber value="${item.ibcWin}" pattern="##.##" minFractionDigits="2"/></td>
    				<td class="toDataTd">${item.ibcZdbf}</td>
    				<td class="toDataTd"><fmt:formatDate value="${item.ibcBalltime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>    			
    				<td class="toDataTd"><fmt:formatDate value="${item.ibcUpdatetime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
    				<td class="toDataTd">${item.ibcVendorid}</td>
    				<td class="toDataTd"><fmt:formatDate value="${item.ibcCreatetime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
    			</tr>
    		</c:forEach>
    	</tbody>
    </table>
  </body>
</html>