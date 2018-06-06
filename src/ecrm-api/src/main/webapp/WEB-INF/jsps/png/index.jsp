<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="ctx" value="<%=request.getContextPath()%>" />
<c:set var="statics" value="${ctx}/static"></c:set>
<html>
<head>
<meta charset="utf-8">
<%@ include file="top.jsp"%>
</head>
<body>
<%@ include file="menu.jsp"%>
<div class="con_cen">
  <ul>
  	
  		<c:forEach var="item" items="${data }" varStatus="i">
  			<li>
		         <div class="pt_img">
		                    <img src="${statics }/png/game/${item.gamecodeweb}">
		                    <div class="rsp"></div>
		                    <div class="text">
		                      <a href="${ctx }/pnggame/login?gamecode=${item.gamecodeweb}" ><h2>进入游戏</h2></a>
		                    </div>
		        </div>
		        <p style="white-space:nowrap;  text-overflow:ellipsis;overflow:hidden;">${item.cnname}</p>
		        <p style="white-space:nowrap;  text-overflow:ellipsis;overflow:hidden;">${item.enname}</p>
		     </li>
  		</c:forEach>	
     

	 </ul>
  <div class="clear"></div>
</div>

<div class="footer">
   <div class="f_cen">Copyright &copy; 2016 娱乐城 All Rights Reserved</div>
</div>
</body>
</html>
