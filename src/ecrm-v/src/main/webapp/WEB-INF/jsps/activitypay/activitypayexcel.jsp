<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.maven.utils.StringUtil"%>
<%@ page import="java.net.URLEncoder"%>
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
	padding: 5px;
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

.toTabLine {
	border-top-width: 1px;
	border-top-style: solid;
	border-top-color: #003366;
}

.toTab {
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
			<td colspan="16" bgcolor="#FAFAD2" align="center"><h3>${title}</h3></td>
		</tr>
	</table>
	<table width="100%" cellpadding="4" cellspacing="0" class="toTab"
		border="0">
		<thead>
			<th class="toLabelTd">序号</th>

			<th class="toLabelTd">流水号</th>
			<th class="toLabelTd">用户账号</th>
			<th class="toLabelTd">活动名称</th>
			<th class="toLabelTd">支付方式</th>

			<th class="toLabelTd">红利金额</th>
			<th class="toLabelTd">实发金额</th>
			<th class="toLabelTd">汇总时间</th>
			
			<th class="toLabelTd">审核人</th>
			<th class="toLabelTd">审核时间</th>
			<th class="toLabelTd">支付人</th>
			<th class="toLabelTd">支付时间</th>
			
			<th class="toLabelTd">支付状态</th>
			<th class="toLabelTd">审核结果</th>
			<th class="toLabelTd">流水倍数</th>
			<th class="toLabelTd">备注</th>

		</thead>
		<tbody>
			<c:forEach var="item" items="${listData}" varStatus="i">
				<tr>
					<td class="toDataTd">${i.index + 1}</td>

					<td class="toDataTd">${item.lsh}</td>
					<td class="toDataTd">${item.loginaccount}</td>
					<td class="toDataTd">${item.acname}</td>
					<td class="toDataTd">
						<c:if test="${item.paytype == 1}">自动核准</c:if>
						<c:if test="${item.paytype == 2}">人工核准</c:if>
					</td>

					<td class="toDataTd"><fmt:formatNumber value="${item.paymoneyaudit}" pattern="##.##" minFractionDigits="2" /></td>
					<td class="toDataTd"><fmt:formatNumber value="${item.paymoneyreal}" pattern="##.##" minFractionDigits="2" /></td>
					<td class="toDataTd"><fmt:formatDate value="${item.createtime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					
					<td class="toDataTd">${item.auditer == '' ? '系统自动' : item.auditer}</td>
					<td class="toDataTd"><fmt:formatDate value="${item.audittime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td class="toDataTd">${item.payer == '' ? '系统自动' : item.payer}</td>
					<td class="toDataTd"><fmt:formatDate value="${item.paytyime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					
					<td class="toDataTd">
						<c:if test="${item.paystatus == 1}">待审核</c:if>
						<c:if test="${item.paystatus == 2}">已审核</c:if>
						<c:if test="${item.paystatus == 3}"><font color="green">已发放</font></c:if>
						<c:if test="${item.paystatus == 4}"><font color="red">已拒绝</font></c:if>
					
					</td>
					<td class="toDataTd">${item.auditremark}</td>
					<td class="toDataTd">${item.lsbs}</td>
					<td class="toDataTd">${item.descs}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>