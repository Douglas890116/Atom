<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ page import="com.maven.utils.AESUtil"%>
<c:set var="ctx" value="<%=request.getContextPath()%>" />
<c:set var="statics" value="${ctx}/static/avh5/"></c:set>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>梦幻时空</title>
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
          <div class="s-logo"><a href="index.html"><img src="${ctx }/static/sa/images/logo.png" width="80"></a></div>
          
		  
			<button class="am-btn am-btn-primary am-icon-bars category" data-am-offcanvas="{target: '#doc-oc-demo2', effect: 'push'}"></button>
            
			<!-- 侧边栏内容 -->
			<div id="doc-oc-demo2" class="am-offcanvas">
			  <div class="am-offcanvas-bar">
			    <div class="am-offcanvas-content">
                    <p><a href="${ctx}/sagame/mainh5?stype=<%=AESUtil.encrypt("最新游戏") %>">最新游戏</a></p>
			    </div>
			  </div>
			</div>
		  </div><!--header结束-->
		  
		  <ul class="menu">
		  	<c:forEach var="item" items="${data }" varStatus="i">
			     
			     <li> <a href="${ctx }/sagame/loginh5?gamecode=${item.gamecodeh5}"><img src="${ctx}/static/sa/images/${item.gamecodeh5}_h5.jpg" /> 
			     <p style="white-space:nowrap;  text-overflow:ellipsis;overflow:hidden;">${item.cnname }</p> 
			     <p style="white-space:nowrap;  text-overflow:ellipsis;overflow:hidden;">${item.enname }</p> 
			     </a> 
			     </li>
			     
	        </c:forEach>
		    
		  </ul>
		  
		</div>
	</body>
</html>

