<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext['request'].contextPath}"></c:set>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<title>平台维护状态</title>
	<link href="${ctx }/resource/css/public.css" rel="stylesheet" type="text/css">
	<script src="${ctx }/resource/js/jquery-1.10.2.min.js" type="text/javascript" charset="utf-8"></script>
	
</head>
	
<body >
	<br /><br />
	
	<center>
    	
    	<table border="0" cellpadding="4" cellspacing="0" class="toTab" width="70%" align="center">
    		<THEAD>
    			<tr>
     				<td class="toLabelTd tdCenter" colspan="4" align="center"><h2>平台维护情况</h2></td>
     			</tr>
     			<tr>
     				<td class="toLabelTd tdCenter" colspan="4" align="center"><h3 class="times"></h3></td>
     			</tr>
     			<tr>
     				<td class="toLabelTd tdCenter" colspan="" align="center">平台</td>
     				<td class="toLabelTd tdCenter" colspan="" align="center">状态</td>
     				<td class="toLabelTd tdCenter" colspan="" align="center">最后更新时间</td>
     				<td class="toLabelTd tdCenter" colspan="" align="center">备注</td>
     			</tr>
  			</THEAD>
  			  <tbody>
  			  	<c:forEach var="item" items="${list }">
  			  	<tr>
      				<td class="toDataTd tdCenter" width="20%" align="center"><h3>${item["gametype"] }</h3></td>
      			    <td class="toDataTd tdCenter" width="20%" align="center">
      			    	${item["flag"] }
      			    </td>
      			    <td class="toDataTd tdCenter" width="30%" align="center"><h3>${item["lasttime"] }</h3></td>
      			    <td class="toDataTd tdCenter" width="30%" align="center">&nbsp;<h3 style="color:red">${item["remark"] }</h3></td>
      			</tr>
     			</c:forEach>
     			
  			  </tbody>
    	</table>
    	
    	<br /><br />
    	<table border="0" cellpadding="4" cellspacing="0" class="toTab" width="70%" align="center">
    		<THEAD>
     			<tr>
     				<td class="toLabelTd tdCenter" colspan="" align="center"><input type="button" value="平台维护情况" onclick="location='${ctx}/datahand/list.do'"/></td>
     				<td class="toLabelTd tdCenter" colspan="" align="center"><input type="button" value="采集数据定时任务" onclick="location='${ctx}/datahand/listTask.do'"/></td>
     				<td class="toLabelTd tdCenter" colspan="" align="center">
     					<input type="button" value="AG采集数据定时任务" onclick="location='${ctx}/datahand/listTag.do'"/>
     					<input type="button" value="批量修改采集时间" onclick="location='${ctx}/datahand/gosetTag.do'"/>
     				</td>
     			</tr>
  			</THEAD>
    	</table>
    	
    	
    </center>
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
