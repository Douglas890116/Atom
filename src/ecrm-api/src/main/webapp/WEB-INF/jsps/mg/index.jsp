<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="ctx" value="<%=request.getContextPath()%>" />
<c:set var="statics" value="${ctx}/static"></c:set>
<%@ page import="com.maven.utils.AESUtil"%>

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>MG老虎机</title>
<link rel="stylesheet" type="text/css" href="${statics }/mg/css/style.css">
<script src="${statics}/pt/js/jquery-1.8.3.min.js" type="text/javascript"></script>
<script>
	$(document).ready(function() {
		$(".mg_img").bind({
			mouseover : function() {
				console.log(0);
				$(this).find("div.gamebutton").show();
			},
			mouseout : function() {
				console.log(1);
				$(this).find("div.gamebutton").hide();
			}
		});
	});
</script>
</head>
<body>
	<div class="top">
		<div class="top_cen">
			<div class="nav_r">
				<ul>
					<li class="l_china"><a href="#">中文版</a></li>
					<li class="l_english"><a href="#">英文版</a></li>

				</ul>
			</div>
			<div class="logo">
				<img src="${ctx }/static/mg/images/logo.png">
			</div>
			<div class="nav_l">
				<ul>
					<li <c:if test="${stype == '最新游戏' }">class="hover"</c:if>><a href="${ctx}/mggame/index?stype=<%=AESUtil.encrypt("最新游戏") %>" >最新游戏</li>
					<li <c:if test="${stype == '老虎机' }">class="hover"</c:if>><a href="${ctx}/mggame/index?stype=<%=AESUtil.encrypt("老虎机") %>"   >老虎机</li>
					<li <c:if test="${stype == '桌面游戏' }">class="hover"</c:if>><a href="${ctx}/mggame/index?stype=<%=AESUtil.encrypt("桌面游戏") %>" >桌面游戏</li>
					<li <c:if test="${stype == '视频扑克' }">class="hover"</c:if>><a href="${ctx}/mggame/index?stype=<%=AESUtil.encrypt("视频扑克") %>" >视频扑克</li>
					
				</ul>
			</div>
		</div>
	</div>
	<div class="con_cen">
		<ul>
			<c:forEach var="item" items="${data }" varStatus="i">
			<li>
				<div class="mg_img">
					<span
						style="background-image: url(${statics }/mg/game/${item.imagename}); background-repeat: no-repeat"></span>
					<div class="gamebutton">
						<!--<a   onClick="mgpop('28680" class="mgtrybtn">游戏试玩</a>-->
						<a href="${ctx }/mggame/login?gamecode=${item.gamecodeweb}" class="mgplaybtn">立即游戏</a>
					</div>
				</div>
				<div class="content">
					<p style="white-space:nowrap;  text-overflow:ellipsis;overflow:hidden;">
					<a href="${ctx }/mggame/login?gamecode=${item.gamecodeweb}" >${item.cnname}</a>
					</p>
					<p style="white-space:nowrap;  text-overflow:ellipsis;overflow:hidden;">
					<a href="${ctx }/mggame/login?gamecode=${item.gamecodeweb}" >${item.enname}</a>
					</p>
				</div>
			</li>
			</c:forEach>
			
		</ul>
		<div class="clear"></div>
	</div>
	<div class="footer">
		<div class="f_cen">©2016帝王国际 版权所有 DiWang88.COM</div>
	</div>
</body>
</html>
