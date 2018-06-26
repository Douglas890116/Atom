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
	<title>运行日志开关</title>
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
    	<th>标识</th>
    	<th>状态</th>
    	<th style="width: 220px;">操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${fields }" var="field">
    	<tr>
    		<td width="60%"><b>${field[0] }</b></td>
    		<td width="35%">
    			<c:choose>
    			<c:when test="${field[1] }">
    				开启
				</c:when>				
    			<c:otherwise>
					关闭    
    			</c:otherwise>
    		</c:choose>
    		</td>
    		<td style="width: 220px;">
    		<c:choose>
    			<c:when test="${field[1] }">
					<a href="${ctx}/RunLogger/Swith?field=${field[0] }&swith=off" class="logger-off" ></a>    
				</c:when>				
    			<c:otherwise>
    				<a href="${ctx}/RunLogger/Swith?field=${field[0] }&swith=on" class="logger-on" ></a>
    			</c:otherwise>
    		</c:choose>
    		</td>
    	</tr>
    </c:forEach>
    </tbody>
    </table>
    <!-- 搜索页 ================================================== -->
    

    <script src="${statics }/js/jquery-1.8.1.min.js"></script>
    <script src="${statics }/js/bui.js"></script> 
    <script src="${statics }/js/config.js"></script>
    <script type="text/javascript">
    	$(function () {
    		
    	})
    </script>
    
  </div>
</body>
</html>