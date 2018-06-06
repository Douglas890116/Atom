<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="ctx" value="<%=request.getContextPath()%>" />

<html>
<head>

<meta charset="UTF-8">

<title>正在登录....</title>


</head>

<body>
正在登录....
	
  
</body>

</html>
<script>
//必须要写两个cookie用于验证
/* 客户的游戏链接将会创建 " S" Session token 以及  " SelectedLanguage" cookies. 以下是  Opus  所支
	英文 = en-US 
  英文(印尼版) = en-IN 
  中文 = zh- CN 
  泰文 = th-TH 
  越文 = vi-VN 
  日文 = ja- JP 
  印尼文  = id-ID 
  柬埔寨文  = km- KH */

document.cookie="S=${session_token}; path=/; domain=${domain};";
document.cookie="SelectedLanguage=zh-CN; path=/; domain=${domain};";

location.href = "${url }";
</script>