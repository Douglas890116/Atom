<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ page import="com.maven.utils.AESUtil"%>
<c:set var="ctx" value="<%=request.getContextPath()%>" />
<c:set var="statics" value="${ctx}/static/hab2_h5/"></c:set>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
		<title>哈巴电子</title>
		<link rel="stylesheet" type="text/css" href="${statics}/css/mui.css"/>
		<link rel="stylesheet" type="text/css" href="${statics}/css/common.css"/>
		<link rel="stylesheet" type="text/css" href="${statics}/css/tiger_game.css"/>
		<link rel="stylesheet" type="text/css" href="${statics}/fonts/iconfont.css"/>
		
		

<script type="text/javascript">

</script>

		
	</head>
	<body>
		<header class="mui-bar mui-bar-nav">
		    <h1 class="mui-title">哈巴电子</h1>
		</header>
		
		<div id="query_hint" class="query_hint" style="display:none">
		</div>
		    
		<div class="mui-content">
			
			<div class="search">
	    		<form name="frm1" id="frm1" method="post" action="${ctx}/habgame/indexh5">
	       		<input type="text" name="gamename" value="${gamename }"/>
	       		<button onclick="frm1.submit()">搜索</button>
	       		</form>
	    	</div>
	    	
		    <div class="mui-scroll-wrapper mui-slider-indicator mui-segmented-control mui-segmented-control-inverted scroll_row">	
		        <div class="mui-scroll">
		        	<!-- 
		            <div class="mui-control-item mui-active">最新游戏</div>
		            <div class="mui-control-item">热门游戏</div>
		            <div class="mui-control-item">全部游戏</div>
		            <div class="mui-control-item last">全部游戏</div> -->
		            
		            <c:if test="${stype == '最新游戏' }"><div class="mui-control-item mui-active" onclick="javascript:location='${ctx}/habgame/indexh5?stype=<%=AESUtil.encrypt("最新游戏") %>'">最新游戏</div></c:if>
                	<c:if test="${stype != '最新游戏' }"><div class="mui-control-item"            onclick="javascript:location='${ctx}/habgame/indexh5?stype=<%=AESUtil.encrypt("最新游戏") %>'">最新游戏</div></c:if>
                	
                	<c:if test="${stype == '热门游戏' }"><div class="mui-control-item mui-active" onclick="javascript:location='${ctx}/habgame/indexh5?stype=<%=AESUtil.encrypt("热门游戏") %>'">热门游戏</div></c:if>
                	<c:if test="${stype != '热门游戏' }"><div class="mui-control-item"            onclick="javascript:location='${ctx}/habgame/indexh5?stype=<%=AESUtil.encrypt("热门游戏") %>'">热门游戏</div></c:if>
                	
                	<c:if test="${stype == '澳门游戏' }"><div class="mui-control-item mui-active" onclick="javascript:location='${ctx}/habgame/indexh5?stype=<%=AESUtil.encrypt("澳门游戏") %>'">澳门游戏</div></c:if>
                	<c:if test="${stype != '澳门游戏' }"><div class="mui-control-item"            onclick="javascript:location='${ctx}/habgame/indexh5?stype=<%=AESUtil.encrypt("澳门游戏") %>'">澳门游戏</div></c:if>
                	
                	<c:if test="${stype == '桌面纸牌' }"><div class="mui-control-item mui-active" onclick="javascript:location='${ctx}/habgame/indexh5?stype=<%=AESUtil.encrypt("桌面纸牌") %>'">桌面纸牌</div></c:if>
                	<c:if test="${stype != '桌面纸牌' }"><div class="mui-control-item"            onclick="javascript:location='${ctx}/habgame/indexh5?stype=<%=AESUtil.encrypt("桌面纸牌") %>'">桌面纸牌</div></c:if>
                	
                	<c:if test="${stype == '视频扑克' }"><div class="mui-control-item mui-active" onclick="javascript:location='${ctx}/habgame/indexh5?stype=<%=AESUtil.encrypt("视频扑克") %>'">视频扑克</div></c:if>
                	<c:if test="${stype != '视频扑克' }"><div class="mui-control-item"            onclick="javascript:location='${ctx}/habgame/indexh5?stype=<%=AESUtil.encrypt("视频扑克") %>'">视频扑克</div></c:if>
                
                	<c:if test="${stype == '全部游戏' }"><div class="mui-control-item mui-active" onclick="javascript:location='${ctx}/habgame/indexh5?stype=<%=AESUtil.encrypt("全部游戏") %>'">全部游戏</div></c:if>
                	<c:if test="${stype != '全部游戏' }"><div class="mui-control-item"            onclick="javascript:location='${ctx}/habgame/indexh5?stype=<%=AESUtil.encrypt("全部游戏") %>'">全部游戏</div></c:if>
                
		        </div>
		    </div>
		    
		    <div class="addGame">
		    	<div class="mui-scroll-wrapper">
		    	    <div class="mui-scroll">
		    	        <div class="close"><span>关闭</span></div>		    	      
		    	    </div>
		    	</div>	    	
		    </div>
		    
		    <div class="game_list">
		    	<div class="mui-row">
		    		<c:if test="${data.size() > 0 }">
		    		<c:forEach var="item" items="${data }" varStatus="i">
	            	<div class="mui-col-sm-4 mui-col-xs-4 lists">
			       		<div>
			            	<%-- <img src="${statics }/pt.jpg" alt="" data-original="${ctx }/static/pt2/game/${item.gamecodeh5 }.jpg" class="gameimg" /> --%>
			            	<img src="${ctx}/static/hab2/game/${item.gamecodeweb}.png" alt="" class="gameimg" />
			            	<ul class="try_play">
			            		<!-- <li>免费试玩</li> -->
			            		<li onClick="location='${ctx }/habgame/loginh5?gamecode=${item.gamecodeh5 }'">立即游戏</li>
			            	</ul>	
			            	<h5>${item.cnname}</h5>
			            </div>
			        </div>
			        </c:forEach>
			        </c:if>
			        
			        <c:if test="${data.size() <= 0 }">
					<h3><font color="white">很抱歉，没有找到该游戏</font></h3>
					</c:if>
	            </div>
		    </div>
		    
		</div>
		<nav>
			Copyright@2017 娱乐城
		</nav>
	</body>
	<script src="${statics }/js/mui.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="${statics }/js/getRem.js" type="text/javascript" charset="utf-8"></script>
	<script src="${statics }/js/zepto.min.js" type="text/javascript" charset="utf-8"></script>
	<%-- <script src="${statics }/js/zepto.picLazyLoad.min.js" type="text/javascript" charset="utf-8"></script> --%>
	<script src="${statics }/js/common.js" type="text/javascript" charset="utf-8"></script>
</html>
	<script type="text/javascript" charset="utf-8">
		//找不到图片时显示默认的
		$('.gameimg').error(function(){
	        $(this).attr('src', "${ctx}/static/hab2/haba.jpg");
	    });
		
		var widths = $(".lists>div").width();
		$(".lists>div").height(widths);
		window.onresize = function(){
		 	var widths = $(".lists>div").width();
			$(".lists>div").height(widths);
		};
		
		$(".lists>div").on('tap',function(){
			 $(".try_play").removeClass("show");
			 $(this).find(".try_play").addClass("show");
			})
		
	</script>