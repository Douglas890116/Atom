<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.maven.utils.StringUtil" %>
<%@ page import="java.net.URLEncoder" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
response.setContentType("application/vnd.ms-excel");


String date1 = StringUtil.getCurrentDate();
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
  			<td colspan="18" bgcolor="#FAFAD2" align="center"><h3>${title }</h3></td>
  		</tr>
  		<tr>
  			<td colspan="18" bgcolor="#FAFAD2" align="right">${size }条</td>
  		</tr>
  	</table>
    <table width="100%" cellpadding="4" cellspacing="0" class="toTab" border="0">
    	<thead>
    		<th class="toLabelTd">序号</th>
    		<th class="toLabelTd">品牌</th>
    		<th class="toLabelTd">会员账号</th>
    		<th class="toLabelTd">昵称</th>
    		<th class="toLabelTd">会员等级</th>
    		<th class="toLabelTd">会员类型 </th>
    		<th class="toLabelTd">分红</th>
    		<th class="toLabelTd">占成</th>
    		<th class="toLabelTd">代理数</th>
    		<th class="toLabelTd">员工数</th>
    		<th class="toLabelTd">账户余额</th>
    		<th class="toLabelTd">上级会员</th>
    		<th class="toLabelTd">手机号</th>
    		<th class="toLabelTd">QQ</th>
    		<th class="toLabelTd">邮箱</th>
    		<th class="toLabelTd">状态</th>
    		
    		<th class="toLabelTd">注册时间</th>
    		<th class="toLabelTd">最后登录时间</th>
    		
    	</thead>
    	<tbody>
    		<c:forEach var="item" items="${listEnterpriseEmployee }" varStatus="i">
    			<tr>
    				<td class="toDataTd">${i.index + 1 }</td>
    				<td class="toDataTd">${item.brandname }</td>
    				<td class="toDataTd">&nbsp;${item.loginaccount }</td>
    				<td class="toDataTd">&nbsp;${item.displayalias }</td>
    				
    				<td class="toDataTd">${item.employeetypename }</td>
    				<td class="toDataTd">${item.employeelevelname }</td>
    				
    				<td class="toDataTd">&nbsp;<fmt:formatNumber value="${item.dividend * 100}" pattern="##.##" minFractionDigits="2" />%</td>
    				<td class="toDataTd">&nbsp;<fmt:formatNumber value="${item.share * 100}" pattern="##.##" minFractionDigits="2" />%</td>
    				
    				<td class="toDataTd">&nbsp;${item.agentCount }</td>
    				<td class="toDataTd">&nbsp;${item.memberCount }</td>
    				<td class="toDataTd">&nbsp;<fmt:formatNumber value="${item.balance }" pattern="##.##" minFractionDigits="2" /></td>
    				<td class="toDataTd">&nbsp;${item.parentemployeeaccount }</td>
    				
    				<td class="toDataTd">&nbsp;${item.phoneno }</td>
    				<td class="toDataTd">&nbsp;${item.email }</td>
    				<td class="toDataTd">&nbsp;${item.qq }</td>
    				
    				<td class="toDataTd">
    					<c:if test="${item.employeestatus eq 1}">启用</c:if>
    					<c:if test="${item.employeestatus eq 2}">锁定游戏</c:if>
    					<c:if test="${item.employeestatus eq 3}">禁用</c:if>
    				</td>
    				
    				<fmt:formatDate var="logindatetime" value="${item.logindatetime}" pattern="yyyy-MM-dd HH:mm:ss" />
    				<fmt:formatDate var="lastlogintime" value="${item.lastlogintime}" pattern="yyyy-MM-dd HH:mm:ss" />
    				
    				<td class="toDataTd">&nbsp;${logindatetime }</td>
    				<td class="toDataTd">&nbsp;${lastlogintime }</td>
    				
    				
    			</tr>
    		</c:forEach>
    	</tbody>
    </table>
  </body>
</html>
