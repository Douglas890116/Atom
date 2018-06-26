<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext['request'].contextPath}"></c:set>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<title>平台维护状态</title>
	<link rel="stylesheet"  href="${ctx }/jquerymobile/jquery.mobile-1.3.2.min.css">
	<link rel="stylesheet" href="${ctx }/jquerymobile/jqm-demos.css">
	<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,700">
	<script src="${ctx }/jquerymobile/jquery.js"></script>
	<script src="${ctx }/jquerymobile/index.js"></script>
	<script src="${ctx }/jquerymobile/jquery.mobile-1.3.2.min.js"></script>
	
</head>
	
<body >

<div data-role="header" style="overflow:hidden;">
    <h1>采集数据定时任务</h1>
    <a href="${ctx }/datahand/listTask.do" data-icon="gear" class="ui-btn-right">刷新</a>
    <h3 class="times"></h3>
    <p><font color="red">${messages }</font></p>
    
    <!-- <div data-role="navbar">
        <ul>
            <li><a href="#">One</a></li>
            <li><a href="#">Two</a></li>
            <li><a href="#">Three</a></li>
        </ul>
    </div> --><!-- /navbar -->
</div><!-- /header -->

<div data-role="content" class="jqm-content">
	<!-- <h2 id="list-filter">当前任务定时器</h2> -->
	<div>
	<ul data-role="listview" data-inset="true">
		
		<c:forEach var="item" items="${list }">
			
			<li data-role="list-divider">${item["handlecode"] }<span class="ui-li-count">${item["lastnum"] }</span></li>
			    <li><a href="${ctx}/datahand/gosetOther.do?handlecode=${item["handlecode"] }" data-ajax="false">
			        <h2>${item["handledesc"] }</h2>
			        <h2><strong>定时器：${item["updatetime"] }</strong></h2>
			        <p>更新时间：${item["lasttime"] }</p>
			    </a>
			    </li>
		</c:forEach>
	    
	</ul>
	</div><!--/demo-html -->
</div><!--/content -->

<div data-role="footer" style="overflow:hidden;">
    <div data-role="navbar">
        <ul>
            <li><a href="${ctx }/datahand/listTask.do" data-ajax="false">定时任务</a></li>
            <li><a href="${ctx }/datahand/list.do" data-ajax="false">平台维护情况</a></li>
            <li><a href="${ctx }/datahand/listTag.do" data-ajax="false">AG定时任务</a></li>
            <li><a href="${ctx }/datahand/gosetTag.do" data-ajax="false">AG批量修改</a></li>
        </ul>
    </div><!-- /navbar -->
    <h4 style="text-align:center;">监控管理窗口</h4>
</div><!-- /footer -->

     
</body>

<script language="JavaScript"> 
function myrefresh() 
{ 
window.location.reload(); 
} 
setTimeout('myrefresh()',1000 * 120); //指定2分钟刷新一次 

function showtimes() {
	var Datas = new Date();
	var	nowData = Datas.getFullYear()+"-"+(Datas.getMonth()+1)+"-"+Datas.getDate()+" "+Datas.getHours()+":"+Datas.getMinutes()+":"+Datas.getSeconds()+"(GMT+8)"
	$(".times").html(nowData);
}
setInterval(showtimes,1000);// 注意函数名没有引号和括弧！ 
</script> 

</html>
