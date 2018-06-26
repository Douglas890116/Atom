<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<c:set var="ctx" value="${pageContext['request'].contextPath}"></c:set>
<c:set var="statics" value="${ctx}/static"></c:set>

<!DOCTYPE html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>API接口访问日志-周期</title>
	<link href="${statics}/css/bs3/bui-min.css" rel="stylesheet" />
	<link href="${statics}/css/bs3/dpl-min.css" rel="stylesheet" /> 
	<link href="${statics}/css/custom/common.css" rel="stylesheet" />
	<style type="text/css">
	.logger-on{
		display:block;
		width:81px;
		height:32px;
		background: url("${statics}/img/on-off.png") no-repeat -2px -5px;
	}
	.logger-off{
		display:block;
		width:81px;
		height:32px;
		background: url("${statics}/img/on-off.png") no-repeat -2px -40px;
	}
	th{
		text-align: left;
		height: 38px;
		line-height: 38px;
		font-size: 14px;
		border-bottom: 1px solid black;
	}
	td{
		text-align: left;
		height:35px;
		line-height: 35px; 
	}
	tbody tr:hover{
		background-color: #f5f5f5;
	}
	</style>
</head>

<body  style="padding: 27px 27px;">
    <div class="demo-content">
    <table style="width: 100%">
    <thead>
    <tr>
    	<th>访问URL</th>
    	<th>开始时间</th>
    	<th>结束时间</th>
    	<th>访问次数</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${APILOG}" var="LOG">
    	<tr>
    		<td><b>${URL }</b></td>
    		<td><b>
    			<fmt:formatDate value="${LOG.startDate }" pattern="yyyy-MM-dd hh:mm:ss"/>
    		</b></td>
    		<td><b>
    			<fmt:formatDate value="${LOG.endDate }" pattern="yyyy-MM-dd hh:mm:ss"/>
    		</b></td>
    		<td><b>${LOG.visiteTime }</b></td>
    	</tr>
    </c:forEach>
    </tbody>
    </table>
    <!-- 搜索页 ================================================== -->
    

    <script src="${statics }/js/jquery-1.8.1.min.js"></script>
    <script src="${statics }/js/bui.js"></script> 
    <script src="${statics }/js/config.js"></script>
    <script type="text/javascript">
    </script>
    
  </div>
</body>
</html>