<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.maven.util.WebInfoHandle"%>
<c:set var="ctx" value="${pageContext['request'].contextPath}"></c:set>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<title>即将支付</title>
</head>
	
<body bgcolor="#333333">
	<br /><br /><br /><br /><br />
	<center>
		<p><h1 style="color:white">正在支付</h1></p>
    	<img alt="" src="${ctx}/TPayment/QRCode?url=${url}">
    	<p><h3 style="color:white"></h3></p>
    	
    	<%if(WebInfoHandle.checkAgentIsMobile(request) == false) {%>
    	<c:if test="${takeDepositRecord.employeepaymentbank == 'B019'}"><p><h3 style="color:white">请打开[微信]“扫一扫”进行支付</h3></p></c:if>
    	<c:if test="${takeDepositRecord.employeepaymentbank == 'B020'}"><p><h3 style="color:white">请打开[支付宝]“扫一扫”进行支付</h3></p></c:if>
    	<c:if test="${takeDepositRecord.employeepaymentbank == 'B034'}"><p><h3 style="color:white">请打开[QQ]“扫一扫”进行支付</h3></p></c:if>
    	<%} else { %>
    	<p><h3 style="color:white">温馨提示：</h3></p>
    	<p><h4 style="color:white">1、用手机截图保存到相册；</h4></p>
    	<c:if test="${takeDepositRecord.employeepaymentbank == 'B019'}"><p><h4 style="color:white">2、打开[微信]扫一扫，点击右上角的相册按钮；</h3></p></c:if>
    	<c:if test="${takeDepositRecord.employeepaymentbank == 'B020'}"><p><h4 style="color:white">2、打开[支付宝]扫一扫，点击右上角的相册按钮；</h3></p></c:if>
    	<c:if test="${takeDepositRecord.employeepaymentbank == 'B034'}"><p><h4 style="color:white">2、打开[QQ]扫一扫，点击右上角的相册按钮；</h3></p></c:if>
    	<p><h4 style="color:white">3、选择刚才截图的二维码图片即可支付；</h4></p>
    	
        <c:if test="${takeDepositRecord.employeepaymentbank == 'B020' }">
        <script type="text/javascript">
        location.href = "${url}";
        </script>
        </c:if>
    	<%} %>
    </center>
</body>
</html>