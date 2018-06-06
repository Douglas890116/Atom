<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.maven.util.WebInfoHandle"%>
<c:set var="ctx" value="${pageContext['request'].contextPath}"></c:set>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<title>测试</title>
	<script src="${ctx }/static/pt2/js/jquery-2.4.min.js" type="text/javascript" charset="utf-8"></script>
</head>
	
<body >
	<center>
		<p>代理号：<input name="agentno" id="agentno" ></p>
    	<p>会员数：<input name="usernumber" id="usernumber" ></p>
    	<p>流水号：<input name="startpoke" id="startpoke" ></p>
    	<input value="提交" id="showTooltips" type="button">
    </center>
</body>
</html>
<script type="text/javascript">

function bindClick(url,form,gourl){
	$.ajax({
		type: "POST",
		url: url,
		data: form.serialize(),
		dataType: "json",
		statusCode:{404:function(){
			showErrTips("请求未发送成功");
		}},
	    success: function(data) {
	    	alert(data.status);
	    	alert(data.info);
	    }
	});
}

$(function () {
	
	
	
	$("#showTooltips").click(function(){
		if($("#agentno").val() == "") {
			alert("请输入2位数的代理号");
			return false;
		}	
		if($("#usernumber").val() == "") {
			alert("请输入会员数");
			return false;
		}
		if($("#startpoke").val() == "") {
			alert("请输入起始流水号");
			return false;
		}
		$("#showTooltips").attr('disabled',"true");//添加disabled属性 
		$.ajax({
			type: "POST",
			url: "${ctx}/User/patch_dw_users",
			data: {"agentno": $("#agentno").val(), "usernumber": $("#usernumber").val(), "startpoke": $("#startpoke").val() },
			dataType: "json",
			statusCode:{404:function(){
				alert("请求未发送成功");
			}},
		    success: function(data) {
		    	alert(data.code+"="+data.info);
		    	$('#showTooltips').removeAttr("disabled"); //移除disabled属性 
		    }
		});
		
	});
});

</script>