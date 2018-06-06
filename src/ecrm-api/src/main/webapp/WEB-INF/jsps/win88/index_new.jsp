<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ page import="com.maven.utils.AESUtil"%>

<c:set var="ctx" value="<%=request.getContextPath()%>" />
<c:set var="statics" value="${ctx}/static/pt2/"></c:set>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<title>PT老虎机</title>
		<link rel="stylesheet" type="text/css" href="${statics }/css/bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="${statics }/css/common.css"/>
		<link rel="stylesheet" type="text/css" media="screen and (min-width: 1200px)" href="${statics }/css/index.css"/>
		<link rel="stylesheet" type="text/css" media="screen and (min-width: 970px) and (max-width: 1200px)" href="${statics }/css/index2.css"/>
		<link rel="stylesheet" type="text/css" media="screen and (min-width: 769px) and (max-width: 970px)" href="${statics }/css/index3.css"/>	
		<link rel="stylesheet" type="text/css" media="screen and (max-width: 768px)" href="${statics }/css/index4.css"/>
		
		<script src="${statics }/js/jquery-2.4.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="${statics }/js/jquery.lazyload.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="http://cache.download.banner.greenjade88.com/integrationjs.php"></script>
<script type="text/javascript">
iapiSetCallout('Login', calloutLogin);
function login(realMode) {
   iapiLogin('${sessionScope.username}'.toUpperCase(), '${sessionScope.password}', realMode, "zh-cn");
   }
function calloutLogin(response) {
    // 以下错误在登录时会发生，但游戏可以玩
    // errorCode=6,errorText=Request time out
    var code = response.errorCode;
    if (code && code != 6) {
       //alert('登录失败,错误码:' + code + ',' + response.errorText).show();
        return;
    }
    // 等待登录，解决有时单点登录失败问题
    
    window.location('http://cache.download.banner.greenjade88.com/casinoclient.html?language=zh-cn&game='+id);
};
function popup(idx)
 {
	window.open('http://cache.download.banner.greenjade88.com/casinoclient.html?language=zh-cn&game='+idx,'Games','width=800,height=600,menubar=no,scrollbars=no,toolbar=no,location=no,directories=no,resizable=no');
	login(1);
 	
 	
 }
function testGame(idx)
 {
 	window.open('http://cache.download.banner.greenjade88.com/casinoclient.html?language=zh-cn&game='+idx+'&mode=offline&currency=cny','Games','width=800,height=600,menubar=no,scrollbars=no,toolbar=no,location=no,directories=no,resizable=no') ;
 }

 </script>
 		
	</head>
	<body>
		<div class="top">
	        <ul class="top_left hidden-xs">
	           <li class="one"><a href="javascript:;">中国-简体中文</a></li>
	           <li class="times"><a href="javascript:;"></a></li>
	           <!-- 
	           <li><a href="##">联系我们</a></li>
	           <li><a href="##">牌照展示</a></li>
	           <li><a class="last" href="##">线路中心</a></li> -->
	        </ul>
	        
	        <div class="dropdown visible-xs">
				<button type="button" class="btn dropdown-toggle" id="dropdownMenu1"  data-toggle="dropdown">
					 联系我们
					<span class="caret"></span>
				</button>
				<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
					<li role="presentation">
						<a role="menuitem" tabindex="-1" href="##">联系我们</a>
					</li>
					<li role="presentation">
						<a role="menuitem" tabindex="-1" href="##">牌照展示</a>
					</li>
					<li role="presentation">
						<a role="menuitem" tabindex="-1" href="##">线路中心</a>
					</li>
	
				</ul>
			</div>
	        
	        <div class="top_right">
	        	<form name="frm1" id="frm1" method="post" action="${ctx}/win88game/ptnew">
	       		<input type="text" name="gamename" value="${gamename }"/>
	       		<p onclick="frm1.submit()">搜索</p>
	       		</form>
	        </div>  	
		</div>
		
		<div class="games">
			<h5>PT老虎机</h5>
			<ol class="gameN hidden-xs">
				<c:if test="${stype == '最新游戏' }"><li class="on" onclick="javascript:location='${ctx}/win88game/ptnew?stype=<%=AESUtil.encrypt("最新游戏") %>'">最新游戏</li></c:if>
                <c:if test="${stype != '最新游戏' }"><li            onclick="javascript:location='${ctx}/win88game/ptnew?stype=<%=AESUtil.encrypt("最新游戏") %>'">最新游戏</li></c:if>
                
                <c:if test="${stype == '热门游戏' }"><li class="on" onclick="javascript:location='${ctx}/win88game/ptnew?stype=<%=AESUtil.encrypt("热门游戏") %>'">热门游戏</li></c:if>
                <c:if test="${stype != '热门游戏' }"><li            onclick="javascript:location='${ctx}/win88game/ptnew?stype=<%=AESUtil.encrypt("热门游戏") %>'">热门游戏</li></c:if>
                
                <c:if test="${stype == '全部游戏' }"><li class="on" onclick="javascript:location='${ctx}/win88game/ptnew?stype=<%=AESUtil.encrypt("全部游戏") %>'">全部游戏</li></c:if>
                <c:if test="${stype != '全部游戏' }"><li            onclick="javascript:location='${ctx}/win88game/ptnew?stype=<%=AESUtil.encrypt("全部游戏") %>'">全部游戏</li></c:if>
				
			</ol>
			
			 <div class="dropdown visible-xs rightb">
				<button type="button" class="btn dropdown-toggle" id="dropdownMenu2" 
						data-toggle="dropdown">
					 游戏分类
					<span class="caret"></span>
				</button>
				<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu2">
					<li role="presentation">
						<a role="menuitem" tabindex="-1" href="##">老虎机</a>
					</li>
					<li role="presentation">
						<a role="menuitem" tabindex="-1" href="##">老虎机</a>
					</li>
					<li role="presentation">
						<a role="menuitem" tabindex="-1" href="##">老虎机</a>
					</li>
	
				</ul>
			</div>
		</div>
		
		<div class="game_list">
			<div class="container">
				<div class="row">
					<c:if test="${data.size() > 0 }">
					<c:forEach var="item" items="${data }" varStatus="i">
					<div class="col-xs-5 col-sm-3 col-md-2 col-lg-2 tbl" >
						<img data-original="${statics }/game/${item.imagename }.jpg" alt="" src="${statics }/pt.jpg" class="gameimg"/><h5>${item.cnname}</h5>
						<ul>
						<li onclick="popup('${item.gamecodeweb }')">开始游戏</li>
						<li onclick="testGame('${item.gamecodeweb }')">立即试玩</li>
						</ul>
					</div>
					</c:forEach>
					</c:if>
					
					<c:if test="${data.size() <= 0 }">
					<h2><font color="white">很抱歉，没有找到该游戏</font></h2>
					</c:if>
				</div>
			</div>
		</div>
		
		<div class="footer hidden-xs">
			<div class="cont">
				<div><img src="${statics }/img/pt.png"/></div>
				<div><img src="${statics }/img/ttp.png"/></div>
				<div><img src="${statics }/img/mg.png"/></div>
				<div><img src="${statics }/img/xin.png"/></div>
				<div><img src="${statics }/img/int.png"/></div>
				<div><img src="${statics }/img/ptgo.png"/></div>
				
				<div><img src="${statics }/img/idn.png"/></div>
				<div><img src="${statics }/img/gnet.png"/></div>
				<div><img src="${statics }/img/bet.png"/></div>
				<div><img src="${statics }/img/ag.png"/></div>
				<div><img src="${statics }/img/bbinss.png"/></div>
				<div><img src="${statics }/img/onew.png"/></div>
			</div>		
		</div>
		
		<div class="bottom">
			<!-- 
			<ul>
				<li><a href="##">关于我们</a></li>
				<li><a href="##">条款与协议</a></li>
				<li><a href="##">常见问题</a></li>
				<li><a href="##">加盟合作</a></li>
			</ul>
			 -->
			<p>Copyright@2017 娱乐城</p>
		</div>
		
		<div class="bottom2">
			<p>Copyright@2017 娱乐城</p>
		</div>
	</body>
	<script src="${statics }/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="${statics }/js/index.js" type="text/javascript" charset="utf-8"></script>
</html>
	<script type="text/javascript" charset="utf-8">
		$(function() {
			$(".gameimg").lazyload({
				effect : "fadeIn"
			});
		});
	</script>

