<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext['request'].contextPath}"></c:set>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<title>批量重置AG数据采集任务</title>
	<link href="${ctx }/resource/css/public.css" rel="stylesheet" type="text/css">
	<script src="${ctx }/resource/js/jquery-1.10.2.min.js" type="text/javascript" charset="utf-8"></script>
	
</head>
	
<body >
	<br /><br />
	
	<center>
    	
    	
    	<table border="0" cellpadding="4" cellspacing="0" class="toTab" width="70%" align="center">
    		<THEAD>
    			<tr>
     				<td class="toLabelTd tdCenter" colspan="2" align="center"><h2>批量重置AG数据采集任务（时间是美东时间GM-4，精确到8位数的分钟）</h2></td>
     			</tr>
  			</THEAD>
  			  <tbody>
  			  	<tr>
      				<td class="toDataTd tdCenter" width="70%" align="center"><input type="number" name="updatetime" id="updatetime"/></td>
      			    <td class="toDataTd tdCenter" width="30%" align="center"><input type="button" value="保存设置" onclick="dosubmit()"/></td>
      			</tr>
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

function dosubmit() {
	$.ajax({
		url: "${ctx}/datahand/setTag.do",
		type:"post",
		data:{"updatetime": $("#updatetime").val() },
		dataType:"json",
		success:function(data){
			alert(data.msg);
		}
	});
}

</script> 

</html>
