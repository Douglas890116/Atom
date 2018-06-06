<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>用户身份不匹配</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  </head>
  
  <body> 
    <div class="errorContainer">
        <h2>Identity does not match! ${message} </h2>
    </div>
  </body>
</html>