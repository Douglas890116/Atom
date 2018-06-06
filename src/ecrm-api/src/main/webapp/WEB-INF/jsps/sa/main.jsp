<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="ctx" value="<%=request.getContextPath()%>" />

<!DOCTYPE HTML>
<html lang="en-US">
<head>
<title>梦幻时空</title>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx }/static/sa/css/sastyle.css">
</head>

<body >
<div class="top">
    <div class="top_cen">
       <div class="nav_r">
          <ul>
             <li class="l_china">
                <a href="">中文版</a>
             </li>
             <li class="l_english"><a href="">英文版</a></li>
             
          </ul>
       </div>
       <div class="logo"></div>
    </div>
</div>
<div class="con_cen">

  <ul>
  		
  		<c:forEach var="item" items="${data }" varStatus="i">
  			<li>
		        <a href="${ctx }/sagame/login?gamecode=${item.gamecodeweb }"><img src="${ctx }/static/sa/images/${item.gamecodeweb }.jpg"></a>
		        <p>${item.cnname }<br>
		           ${item.enname }
		        </p>
		     </li>
  		</c:forEach>
     

  </ul> 
</div>

</body>
</html>