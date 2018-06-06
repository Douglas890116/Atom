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
  			<td colspan="19" bgcolor="#FAFAD2" align="center"><h3>${title}</h3></td>
  		</tr>
  		<tr>
  			<td colspan="19" bgcolor="#FAFAD2">下单设备说明 : 1.行动装置下单：M 1-1.ios手机：MI1 1-2.ios平板：MI2 1-3.Android手机：MA1 1-4.Android平板：MA2 2.计算机下单：P</td>
  		</tr>
  	</table>
    <table width="100%" cellpadding="4" cellspacing="0" class="toTab" border="0">
		<thead>
			<th class="toLabelTd">序号</th>
			
    		<th class="toLabelTd">用户账号</th>
    		<th class="toLabelTd">游戏账号</th>
    		
    		<th class="toLabelTd">订单号</th>
    		<th class="toLabelTd">局号</th>
    		<th class="toLabelTd">场次</th>
    		<th class="toLabelTd">桌号</th>
    		<th class="toLabelTd">游戏种类</th>

			<th class="toLabelTd">开牌结果</th>
			<th class="toLabelTd">结果牌</th>
			<th class="toLabelTd">下注金额</th>
    		<th class="toLabelTd">会员有效投注额</th>
    		<th class="toLabelTd">注单结果</th>
    		<th class="toLabelTd">派彩金额</th>
    		<th class="toLabelTd">派彩结果</th>
    		<th class="toLabelTd">下注时间</th>

    		<th class="toLabelTd">货币</th>
    		<th class="toLabelTd">与人民币汇率</th>
    		<th class="toLabelTd">下单设备</th>
    		
    	</thead>
    	<tbody>
    		<c:forEach var="item" items="${listData}" varStatus="i">
    			<tr>
    				<td class="toDataTd">${i.index + 1}</td>
    				
    				<td class="toDataTd">${item.loginaccount}</td>
    				<td class="toDataTd">${item.bbinUserName}</td>
    				
    				<td class="toDataTd">${item.bbinWagersId}</td>
    				<td class="toDataTd">${item.bbinSerialId}</td>
    				<td class="toDataTd">${item.bbinRoundNo}</td>
    				<td class="toDataTd">${item.bbinGameCode}</td>
    				<td class="toDataTd">${item.bbinGameType}</td>
    				
    				<td class="toDataTd">${item.bbinResult}</td>
    				<!-- bbinCard 数据处理还需要修改 -->
    				<td class="toDataTd">${item.bbinCard}</td>
    				<td class="toDataTd"><fmt:formatNumber value="${item.bbinBetAmount}"  pattern="##.##" minFractionDigits="2"/></td>
    				<td class="toDataTd"><fmt:formatNumber value="${item.bbinCommissionable}"  pattern="##.##" minFractionDigits="2"/></td>
    				<td class="toDataTd">${item.bbinResultType}</td>
    				<td class="toDataTd"><fmt:formatNumber value="${item.bbinPayoff}"  pattern="##.##" minFractionDigits="2"/></td>
    				<td class="toDataTd">
    					<c:if test="${item.bbinIsPaid != null && item.bbinIsPaid eq Y}">已派彩</c:if>
    					<c:if test="${item.bbinIsPaid != null && item.bbinIsPaid eq N}">未派彩</c:if>
    				</td>
    				<td class="toDataTd"><fmt:formatDate value="${item.bbinWagersDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
    				
    				<td class="toDataTd">${item.bbinCurrency}</td>
    				<td class="toDataTd">${item.bbinExchangeRate}</td>
    				<td class="toDataTd">${item.bbinOrigin}</td>
    			</tr>
    		</c:forEach>
    	</tbody>
    </table>
  </body>
</html>