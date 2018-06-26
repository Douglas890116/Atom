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
	<title>API接口访问日志</title>
	<link href="${statics}/css/bs3/bui-min.css" rel="stylesheet" />
	<link href="${statics}/css/bs3/dpl-min.css" rel="stylesheet" /> 
	<link href="${statics}/css/custom/common.css" rel="stylesheet" />
	<style type="text/css">
	.logger-on{
		display:inline-block;
		width:81px;
		height:32px;
		background: url("${statics}/img/on-off.png") no-repeat -2px -5px;
		vertical-align: middle;
	}
	.logger-off{
		display:inline-block;
		width:81px;
		height:32px;
		background: url("${statics}/img/on-off.png") no-repeat -2px -40px;
		vertical-align: middle;
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
    <div>
    	<c:choose>
   			<c:when test="${APILOGSWITH}">
				<a href="${ctx }/SystemVisite/SysSwith?swith=off" class="logger-off" ></a> -- ${requestScope.MENUS.menuname}开关    
			</c:when>				
   			<c:otherwise>
   				<a href="${ctx }/SystemVisite/SysSwith?swith=on" class="logger-on" ></a> -- ${requestScope.MENUS.menuname}开关 
   			</c:otherwise>
   		</c:choose>
    </div>
    <table style="width: 100%">
    <thead>
    <tr>
    	<th>访问URL</th>
    	<th>第一次访问时间</th>
    	<th>最后一次访问时间</th>
    	<th>访问次数</th>
    	<th>是否开启周期统计</th>
    	<th>统计周期(秒)</th>
    	<th style="width: 220px;text-align: center;">操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${APILOG}" var="LOG">
    	<tr>
    		<td><b>${LOG.visiteURL }</b></td>
    		<td><b>
    			<fmt:formatDate value="${LOG.firstVisitDate }" pattern="yyyy-MM-dd hh:mm:ss"/>
    		</b></td>
    		<td><b>
    			<fmt:formatDate value="${LOG.lastVisiteDate }" pattern="yyyy-MM-dd hh:mm:ss"/>
    		</b></td>
    		<td><b>${LOG.visiteTime }</b></td>
    		<td><b>
    			<c:choose>
    			<c:when test="${LOG.opencyclestat }">
    				开启
				</c:when>				
    			<c:otherwise>
					关闭    
    			</c:otherwise>
    		</c:choose>
    		</b></td>
    		<td><b>
    			<span  class="statcycleshow" style="cursor: pointer;width: 80px;">${LOG.statcycle }</span>
    			<input class="statcyclesetting" value="${LOG.statcycle }" data-url="${LOG.visiteURL }"  style="display: none;width: 80px;"/>
    		</b></td>
    		<td>
    			<c:choose>
    			<c:when test="${LOG.opencyclestat }">
	    			<a href="${ctx }/SystemVisite/SysCycleSwith?url=${LOG.visiteURL }&swith=off">
	    				<button type="button" class="button botton-margin" style="background-color:#8B8E8B;color:white;">
	    				关闭分时统计
	    				</button>
	    			</a>
				</c:when>				
    			<c:otherwise>
	    			<a href="${ctx }/SystemVisite/SysCycleSwith?url=${LOG.visiteURL }&swith=on">
						<button type="button"  class="button button-success">
						开启分时统计
						</button>
					</a>    
    			</c:otherwise>
    		</c:choose>
    			<button type="submit"  class="button button-inverse viewSystemLog" data-url="${LOG.visiteURL }" >
					查看分时统计
				</button>
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
    		$(".statcycleshow").dblclick(function(){
    			$(this).hide().next().show();
    		});
    		$(".statcyclesetting").blur(function(){
    			$(this).hide().prev().show();
    			var url = $(this).attr("data-url");
    			window.location.href="${ctx }/SystemVisite/UpdateSysStatCycle?url="+url+"&cycle="+$(this).val();
    		});
    		$(".viewSystemLog").click(function(){
    			var url = $(this).attr("data-url");
    			if(top.topManager){
        			top.topManager.openPage({
        			    id :"ApiCycleVisiteLog",
        			    href :"${ctx }/SystemVisite/SysCycleVisiteLog?url="+url,
        			    title : "API分时统计",
        			  }); 
        		}
    		});
    	})
    </script>
    
  </div>
</body>
</html>