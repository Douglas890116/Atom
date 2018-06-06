<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="ctx" value="<%=request.getContextPath()%>" />
<c:set var="statics" value="${ctx}/static"></c:set>
<html>
<head>
<meta charset="utf-8">
<%@ include file="top.jsp"%>

<script type="text/javascript">
function popup(idx)
{
	window.open('${ctx}/sgsgame/login.do?gamecode='+idx,'Games','width=800,height=600,menubar=no,scrollbars=no,toolbar=no,location=no,directories=no,resizable=no');
	
}
</script>
</head>
<body>
<%@ include file="menu.jsp"%>
<div class="con_cen">
  <ul>
  		<c:forEach var="item" items="${data }" varStatus="i">
  			<c:if test="${item.isactive == 'true'}">
  			<li>
		         <div class="pt_img">
		                    <img src="${item.iconurl }">
		                    <div class="rsp"></div>
		                    <div class="text">
		                      <c:if test="${sessionScope.gametype!='0'}" ><a href="javascript:;" onclick="popup('${item.providercode};${item.code }')"><h2>进入游戏</h2></a></c:if>
		                      <!-- <a href="javascript:;" onclick="testGame('fm')"><h3>立即试玩</h3></a> -->
		                    </div>
		        </div>
		        <p style="white-space:nowrap;  text-overflow:ellipsis;overflow:hidden;">${item.name}</p>
		     </li>
		     </c:if>
  		</c:forEach>

	 </ul>
  <div class="clear"></div>
</div>

<div class="footer">
   <div class="f_cen">Copyright &copy; 2016 娱乐城 All Rights Reserved</div>
</div>
</body>
</html>
