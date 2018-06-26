<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext['request'].contextPath}"></c:set>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<title>修改定时任务器</title>
	<link href="${ctx }/resource/css/public.css" rel="stylesheet" type="text/css">
	<script src="${ctx }/resource/js/jquery-1.10.2.min.js" type="text/javascript" charset="utf-8"></script>
	
</head>
	
<body >
	<br /><br />
	
	<center>
    	
    	
    	<table border="0" cellpadding="4" cellspacing="0" class="toTab" width="70%" align="center">
    		<THEAD>
    			<tr>
     				<td class="toLabelTd tdCenter" colspan="3" align="center"><h2>修改定时任务器（只限HY和BBIN）</h2></td>
     			</tr>
  			</THEAD>
  			  <tbody>
  			  	<tr><input type="hidden" name="handlecode" id="handlecode" value="${handlecode }"/>
  			  		<td class="toDataTd tdCenter" width="40%" align="center">${handlecode }</td>
      				<td class="toDataTd tdCenter" width="30%" align="center"><input type="number" name="updatetime" id="updatetime"/></td>
      			    <td class="toDataTd tdCenter" width="30%" align="center"><input type="button" value="保存设置" onclick="dosubmit()"/></td>
      			</tr>
  			  </tbody>
    	</table>
    	
    	
    	
    </center>
</body>

<script language="JavaScript"> 

function dosubmit() {
	$.ajax({
		url: "${ctx}/datahand/setOther.do",
		type:"post",
		data:{"updatetime": $("#updatetime").val(), "handlecode":$("#handlecode").val() },
		dataType:"json",
		success:function(data){
			alert(data.msg);
		}
	});
}

</script> 

</html>
