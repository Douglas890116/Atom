<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ page import="com.maven.utils.AESUtil"%>
<c:set var="ctx" value="<%=request.getContextPath()%>" />
<c:set var="statics" value="${ctx}/static/pt2_h5/"></c:set>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
		<title>PT老虎机</title>
		<link rel="stylesheet" type="text/css" href="${statics}/css/mui.css"/>
		<link rel="stylesheet" type="text/css" href="${statics}/css/common.css"/>
		<link rel="stylesheet" type="text/css" href="${statics}/css/tiger_game.css"/>
		<link rel="stylesheet" type="text/css" href="${statics}/fonts/iconfont.css"/>
		
		<script src="${ctx}/static/pt2/js/jquery-2.4.min.js" type="text/javascript" charset="utf-8"></script>
		
		
<script type="text/javascript" src="https://login.luckydragon88.com/jswrapper/integration.js.php?casino=greatfortune88"></script>

<style type="text/css">
.query_hint{
 border:1px solid #939393;
 width:250px;
 height:80px;
 line-height:85px;
 padding:0 20px;
 position:absolute;
 left:50%;
 margin-left:-140px;
 top:40%;
 margin-top:-40px;
 font-size:15px;
 color:#333;
 font-weight:bold;
 text-align:center;
 background-color:#f9f9f9;
 z-index: 5000;
}
</style>

<script type="text/javascript">

var showtimes_title_num = 0;
var showtimes_title_inteval = null ;
function showtimes_title() {
	//document.title = "正在登录PT游戏，请耐心等待..."+showtimes_title_num;
	$("#query_hint").html("正在登录，请耐心等待..." + showtimes_title_num);
	showtimes_title_num ++;
}

function getUrlVars() {
	var vars = {};
	var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
		vars[key] = value;
	});
	return vars;

}

iapiSetCallout('GetTemporaryAuthenticationToken', calloutGetTemporaryAuthenticationToken);


//'ngm', 'ano'
var currentgame = "";
var gametype = "";
function askTempandLaunchGame(type, game) {
	currentgame = game;
	gametype = type;
	var realMode = 1;
	
	//操作提示
	if(showtimes_title_inteval != null) {
		clearInterval(showtimes_title_inteval);
	}
	showtimes_title_num = 1;
	$("#query_hint").html("正在登录，请耐心等待..." + showtimes_title_num);
	$("#query_hint").show();
	showtimes_title_inteval = setInterval(showtimes_title,1000);// 注意函数名没有引号和括弧！ 
	
	iapiRequestTemporaryToken(realMode, '424', 'GamePlay');	
}

var clientUrl = "";
function launchMobileClient(temptoken) {
	if (gametype == "mps") {
		clientUrl=''+'?username=' + getUrlVars()["username"] + '&temptoken=' + temptoken + '&game=' + currentgame + '&real=1';
	} else if (gametype = "ngm") {
		//clientUrl = 'http://hub.ld176888.com/igaming/' + '?gameId=' + currentgame + '&real=1' + '&username=${sessionScope.username}&lang=' + document.getElementById("languageform").language.value + '&tempToken=' + temptoken + '&lobby=' + location.href.substring(0,location.href.lastIndexOf('/')+1) + '/indexh5' + '&support=' + location.href.substring(0,location.href.lastIndexOf('/')+1) + '/supporth5' + '&logout=' + location.href.substring(0,location.href.lastIndexOf('/')+1) + '/logouth5';
		
		var uuusername = '${sessionScope.username}'.toUpperCase();
		clientUrl = 'http://hub.ld176888.com/igaming/' + '?gameId=' + currentgame + '&real=1' + '&username='+uuusername+'&lang=zh-cn&tempToken=' + temptoken + 
				'&lobby=${sessionScope.homeurl}' + 
				'&support=${sessionScope.homeurl}' + 
				'&logout=${sessionScope.homeurl}';
		
	}
	document.location = clientUrl;
}


//CALLOUT----------------------------------------------



function calloutGetTemporaryAuthenticationToken(response) {
	
	if (response.errorCode && response.errorCode != 6) {
	//if (response.errorCode) {
		alert("Token failed. " + response.playerMessage + " Error code: " + response.errorCode);
	}
	else {
		launchMobileClient(response.sessionToken.sessionToken);		
	}
}

//如果有默认游戏代码值，则直接跳转
var gametype = "${gametype}" ;
if(gametype != "") {
	askTempandLaunchGame('ngm', gametype);
}
</script>

		
	</head>
	<body>
		<header class="mui-bar mui-bar-nav">
		    <h1 class="mui-title">PT老虎机</h1>
		</header>
		
		<div id="query_hint" class="query_hint" style="display:none">
		</div>
		    
		<div class="mui-content">
			
			<div class="search">
	    		<form name="frm1" id="frm1" method="post" action="${ctx}/ptgame/indexh5">
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
		            
		            <c:if test="${stype == '最新游戏' }"><div class="mui-control-item mui-active" onclick="javascript:location='${ctx}/ptgame/indexh5?stype=<%=AESUtil.encrypt("最新游戏") %>'">最新游戏</div></c:if>
                	<c:if test="${stype != '最新游戏' }"><div class="mui-control-item"            onclick="javascript:location='${ctx}/ptgame/indexh5?stype=<%=AESUtil.encrypt("最新游戏") %>'">最新游戏</div></c:if>
                
                	<c:if test="${stype == '热门游戏' }"><div class="mui-control-item mui-active" onclick="javascript:location='${ctx}/ptgame/indexh5?stype=<%=AESUtil.encrypt("热门游戏") %>'">热门游戏</div></c:if>
                	<c:if test="${stype != '热门游戏' }"><div class="mui-control-item"            onclick="javascript:location='${ctx}/ptgame/indexh5?stype=<%=AESUtil.encrypt("热门游戏") %>'">热门游戏</div></c:if>
                
                	<c:if test="${stype == '全部游戏' }"><div class="mui-control-item mui-active" onclick="javascript:location='${ctx}/ptgame/indexh5?stype=<%=AESUtil.encrypt("全部游戏") %>'">全部游戏</div></c:if>
                	<c:if test="${stype != '全部游戏' }"><div class="mui-control-item"            onclick="javascript:location='${ctx}/ptgame/indexh5?stype=<%=AESUtil.encrypt("全部游戏") %>'">全部游戏</div></c:if>
                
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
			            	<img src="${ctx }/static/pt2/game/${item.gamecodeh5 }.jpg" alt="" class="gameimg" />
			            	<ul class="try_play">
			            		<!-- <li>免费试玩</li> -->
			            		<li onClick="askTempandLaunchGame('ngm', '${item.gamecodeh5}')">立即游戏</li>
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
		$(function() {
			/* $('.gameimg').picLazyLoad(); */
			//找不到图片时显示默认的
			$('.gameimg').error(function(){
		        $(this).attr('src', "${statics}/pt.jpg");
		    });
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