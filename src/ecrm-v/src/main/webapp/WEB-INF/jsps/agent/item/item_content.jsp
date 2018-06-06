<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${title }</title>
<link rel="stylesheet" href="${statics}/css/custom/itemContent.css" />
</head>
<body>
	<div id="content">
		${item.content }
	</div>
</body>
</html>