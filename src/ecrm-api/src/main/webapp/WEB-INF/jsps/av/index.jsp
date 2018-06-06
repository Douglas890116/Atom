<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="ctx" value="<%=request.getContextPath()%>" />
<c:set var="statics" value="${ctx}/static/av/"></c:set>

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>正在进入少女时代...</title>
<link rel="stylesheet" type="text/css" href="${statics }/css/style.css">
</head>
<script type="text/javascript">
function setTab(name,cursel,n){
 for(i=1;i<=n;i++){
  var menu=document.getElementById(name+i);
  var con=document.getElementById("con_"+name+"_"+i);
  menu.className=i==cursel?"hover":"";
  con.style.display=i==cursel?"block":"none";
 }
}
</script>
<body>
<div class="top">
    <div class="top_cen">
       <div class="nav_l">
          <ul>
             <li id="zt1"  onclick="setTab('zt',1,2)" class="l1 hover"></li>
             <li id="zt2"  onclick="setTab('zt',2,2)" class="l2"></li>
          </ul>
          <ol class="zt1" id="con_zt_1">
             <li id="list1" onclick="setTab('list',1,4)" class="hover"></li>
             <li id="list2" onclick="setTab('list',2,4)"></li>
          </ol>
          <ol class="zt2" id="con_zt_2" style="display:none">
             <li id="list3" onclick="setTab('list',3,4)"></li>
             <li id="list4" onclick="setTab('list',4,4)"></li>
          </ol>
       </div>
       <div class="nav_r">
          <ul>
             <li class="l_china">
                <a href="#">中文版</a>
             </li>
             <li class="l_english"><a href="#">英文版</a></li>
             <li class="l_japan"><a href="#">日文版</a></li>
          </ul>
       </div>
       <div class="logo"></div>
    </div>
</div>
<div class="con_cen">
 
  <ul id="con_list_1">
  		
  		<c:forEach var="item" items="${listGame1 }" varStatus="i">
        	<li>
		        <a href="${item.get('gameURL') }"><img src="${item.get('thumbnail') }"></a>
		        <p>${item.get('gameName-ch') }(${item.get('gameName-en') })<br/>
		           ${item.get('gameName-jp') }
		        </p>
		     </li>
        </c:forEach>
  		
  </ul> 
  
  <ul style="display:none" id="con_list_2">
     	<c:forEach var="item" items="${listGame2 }" varStatus="i">
        	<li>
		        <a href="${item.get('gameURL') }"><img src="${item.get('thumbnail') }"></a>
		        <p>${item.get('gameName-ch') }(${item.get('gameName-en') })<br/>
		           ${item.get('gameName-jp') }
		        </p>
		     </li>
        </c:forEach>
  </ul> 
  
  
</div>
</body>
</html>
