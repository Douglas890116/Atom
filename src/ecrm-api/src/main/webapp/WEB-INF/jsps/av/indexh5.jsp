<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="ctx" value="<%=request.getContextPath()%>" />
<c:set var="statics" value="${ctx}/static/avh5/"></c:set>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>AV少女时代</title>
		<meta name="description" content="">
        <meta name="keywords" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<link rel="stylesheet" href="${statics }/css/amazeui.min.css" type="text/css"/>
		<link rel="stylesheet" href="${statics }/css/app.css" type="text/css"  />
		<script src="${statics }/js/jquery.min.js"></script>
		<script src="${statics }/js/amazeui.min.js"></script>
	</head>
	<body>
		<div class="box">
		  <div class="header"><!--header开始-->
          <div class="s-logo"><a href="index.html"><img src="images/s-logo.png" width="80"></a></div>
          
		  <!-- 按钮触发器， 需要指定 target -->
			<button class="am-btn am-btn-primary am-icon-bars category" data-am-offcanvas="{target: '#doc-oc-demo2', effect: 'push'}"></button>
            <div class="login-icon"><a href="${ctx }/avgame/changeh5?type=SLOT">SLOT</a><a href="${ctx }/avgame/changeh5?type=CASCADING" style="margin-right:5px;">CASCADING</a></div>
            
			<!-- 侧边栏内容 -->
			<div id="doc-oc-demo2" class="am-offcanvas">
			  <div class="am-offcanvas-bar">
			    <div class="am-offcanvas-content">
                    <p><a href="${ctx }/avgame/changeh5?type=SLOT">SLOT</a></p>
                    <p><a href="${ctx }/avgame/changeh5?type=CASCADING">CASCADING</a></p>
			    </div>
			  </div>
			</div>
		  </div><!--header结束-->
		  
		  <ul class="menu">
		  	<c:forEach var="item" items="${listGame1 }" varStatus="i">
			     
			     <li> <a href="${item.get('gameURL') }"><img src="${item.get('thumbnail') }" /> 
			     <p>${item.get('gameName-ch') }</p> 
			     <p>${item.get('gameName-en') }</p>
			     <p>${item.get('gameName-jp') }</p> </a> </li>
			     
	        </c:forEach>
		    
		  </ul>
		  
		</div>
	</body>
</html>

