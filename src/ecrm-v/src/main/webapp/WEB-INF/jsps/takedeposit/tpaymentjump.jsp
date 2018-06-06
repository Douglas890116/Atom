<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>即将支付</title>
</head>
<body onload="form.submit()">
    <form class="form-signin" id="form" action="${url }" method="post">
    </form>
</body>
</html>