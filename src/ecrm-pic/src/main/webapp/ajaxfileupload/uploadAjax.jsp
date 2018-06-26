<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String basePath = request.getContextPath(); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
	<title></title>
	<script type="text/javascript" src="ajaxfileupload/jquery-1.8.1.min.js"></script>
	<script type="text/javascript" src="ajaxfileupload/ajaxfileupload.js"></script>
	<script type="text/javascript">
	function ajaxFileUpload() {
	    $.ajaxFileUpload
	    ({
	            url: "<%=basePath %>/FileUpload", //用于文件上传的服务器端请求地址
	            secureuri: false, //是否需要安全协议，一般设置为false
	            fileElementId: 'file', //文件上传域的ID
	            dataType: 'json', //返回值类型 一般设置为json
	            success: function (data, status)  //服务器成功响应处理函数
	            {
	                alert(data.url);
	            },
	            error: function (data, status, e)//服务器响应失败处理函数
	            {
	                alert(e);
	            }
	      });
	}
	</script>
</head>
<body>
	<p><input type="file" id="file" name="file"  dir="rtl" /></p>
    <input type="button" value="上传" onclick="ajaxFileUpload();"/> 
</body>
</html>