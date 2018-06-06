<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext['request'].contextPath}"></c:set>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<title>收款银行信息</title>
	<link href="${ctx }/static/css/public.css" rel="stylesheet" type="text/css">
	
</head>
	
<body >
	<p></p><p></p><p></p><p></p>
	
	<center>
    	
    	
    	<table border="0" cellpadding="4" cellspacing="0" class="toTab" width="50%" align="center">
    		<THEAD>
     			<tr>
     				<td class="toLabelTd tdCenter" colspan="3" align="center"><h3>收款银行信息</font></td>
     			</tr>
  			</THEAD>
  			  <tbody>
  			  	<tr>
      				<td class="toDataTd tdCenter" width="30%" align="right">订单号：</td>
      			    <td class="toDataTd tdCenter" width="30%"><h3>${result["storeOrderId"] }</h3></td>
      			    <td class="toDataTd tdCenter" width="40%">&nbsp;</td>
      			</tr>
      			<tr>
      				<td class="toDataTd tdCenter" width="30%" align="right">付款人姓名：</td>
      			    <td class="toDataTd tdCenter" width="30%"><h3>${result["sName"] }</h3></td>
      			    <td class="toDataTd tdCenter" width="40%">&nbsp;</td>
      			</tr>
      			<tr>
      				<td class="toDataTd tdCenter" width="30%" align="right">玩家识别码：</td>
      			    <td class="toDataTd tdCenter" width="30%"><h3>${result["sPlayersId"] }</h3></td>
      			    <td class="toDataTd tdCenter" width="40%">&nbsp;</td>
      			</tr>
      			<tr>
      				<td class="toDataTd tdCenter" width="30%" align="right">付款人姓名：</td>
      			    <td class="toDataTd tdCenter" width="30%"><h3>${result["sName"] }</h3></td>
      			    <td class="toDataTd tdCenter" width="40%">&nbsp;</td>
      			</tr>
      			
      			<tr>
      				<td class="toDataTd tdCenter" width="30%" align="right">付款金额：</td>
      			    <td class="toDataTd tdCenter" width="30%"><h3>￥${result["ePrice"] }元</h3></td>
      			    <td class="toDataTd tdCenter" width="40%"><font color="red">请以该金额为准，转账时需要转包括小数点在内的金额数</font></td>
      			</tr>
      			
      			<tr>
     				<td class="toLabelTd tdCenter" colspan="3" align="center">
     					<h3><font color="red">请使用網上銀行或是ATM轉帳等方式像以下银行卡转账</font></h3>
     				</td>
     			</tr>
     			
     			<tr>
      				<td class="toDataTd tdCenter" width="30%" align="right">收款银行：</td>
      			    <td class="toDataTd tdCenter" width="30%"><h3>${result["eBank"] }</h3></td>
      			    <td class="toDataTd tdCenter" width="40%">&nbsp;</td>
      			</tr>
      			<tr>
      				<td class="toDataTd tdCenter" width="30%" align="right">收款人姓名：</td>
      			    <td class="toDataTd tdCenter" width="30%"><h3>${result["eName"] }</h3></td>
      			    <td class="toDataTd tdCenter" width="40%">&nbsp;</td>
      			</tr>
      			<tr>
      				<td class="toDataTd tdCenter" width="30%" align="right">收款人卡号：</td>
      			    <td class="toDataTd tdCenter" width="30%"><h3>${result["eBankAccount"] }</h3></td>
      			    <td class="toDataTd tdCenter" width="40%">&nbsp;</td>
      			</tr>
      			
      			
      			<tr>
     				<td class="toLabelTd tdCenter" colspan="3" align="center">
     					<h3>操作完成后，关闭本页。收到款项后平台自动为您账号完成充值</h3>
     				</td>
     			</tr>
     			
  			  </tbody>
    	</table>
    </center>
</body>
</html>
