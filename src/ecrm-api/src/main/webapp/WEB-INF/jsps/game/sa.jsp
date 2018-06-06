<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="ctx" value="<%=request.getContextPath()%>" />
<c:set var="statics" value="${ctx}/static/game"></c:set>
<c:set var="statics2" value="${ctx}/static"></c:set>
<%@ page import="com.maven.utils.AESUtil"%>

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>游戏列表页</title>
<script src="${statics}/js/jquery-1.10.1.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="${statics}/css/tab.css">
</head>

<body>
<div class="slot-game-box w">
				<!-- slot-game-title_begin -->
				<div class="slot-game-title fl">
					<div class="top clearfix fl">
                        <!-- tabs_begin -->
						<ul class="tabs nav clearfix" id="shiyan">
                              <c:forEach var="item" items="${listType }" varStatus="i">
                              	<li class="item ${item.biggametype}  ${item.biggametype == 'PNGGame' ? 'active' : '' }"><a href="${ctx}/apigame/index?enterprisecode=${enterprisecode}&biggametype=${item.biggametype}">${item.biggametype}</a></li>
                              </c:forEach>
                        </ul>
                        <!-- tabs_end -->

                        <!-- search-box_begin -->
						<div class="search-box">
                            <input type="text" data-i18n="[placeholder]请输入游戏查询..." placeholder="请输入游戏查询...">
                            <span></span>
						</div>
                        <!-- search-box_end -->
					</div>
                    <!-- btm_begin -->
                    
                        	<div class="btm">
                                <!-- 二級菜單_begin -->
                                <ul class="list clearfix" style="display: block;">
                                    
                                    <li ><a class="item <c:if test="${category == '最新游戏' }">active</c:if>" href="${ctx}/apigame/index?enterprisecode=${enterprisecode}&biggametype=PNGGame&category=<%=AESUtil.encrypt("最新游戏") %>" >最新游戏</a></li>
									<li ><a class="item <c:if test="${category == '桌面游戏' }">active</c:if>" href="${ctx}/apigame/index?enterprisecode=${enterprisecode}&biggametype=PNGGame&category=<%=AESUtil.encrypt("桌面游戏") %>" >桌面游戏</a></li>
									<li ><a class="item <c:if test="${category == '视频扑克' }">active</c:if>" href="${ctx}/apigame/index?enterprisecode=${enterprisecode}&biggametype=PNGGame&category=<%=AESUtil.encrypt("视频扑克") %>" >视频扑克</a></li>
									<li ><a class="item <c:if test="${category == '热门游戏' }">active</c:if>" href="${ctx}/apigame/index?enterprisecode=${enterprisecode}&biggametype=PNGGame&category=<%=AESUtil.encrypt("热门游戏") %>" >热门游戏</a></li>
									<li ><a class="item <c:if test="${category == '多线游戏' }">active</c:if>" href="${ctx}/apigame/index?enterprisecode=${enterprisecode}&biggametype=PNGGame&category=<%=AESUtil.encrypt("多线游戏") %>" >多线游戏</a></li>
					
                                </ul>
                                <!-- 
                                <ul class="list clearfix">
                                    <li class="item">2</li>
                                    <li class="item">2</li>
                                    <li class="item">2</li>
                                    <li class="item">2</li>
                                    <li class="item">2</li>
                                    <li class="item">2</li>
                                    <li class="item">2</li>
                                    <li class="item">2</li>
                                    <li class="item">2</li>
                                </ul>
                                <ul class="list bbinlist clearfix">
                                    <li class="item">3</li>
                                    <li class="item">3</li>
                                    <li class="item">3</li>
                                    <li class="item">3</li>
                                    <li class="item">3</li>
                                    <li class="item">3</li>
                                </ul>
                                <ul class="list clearfix">
                                    <li class="item">4</li>
                                    <li class="item">4</li>
                                </ul>
                                <ul class="list clearfix">
                                    <li class="item">5</li>
                                    <li class="item">5</li>
                                    <li class="item">5</li>
                                    <li class="item">5</li>
                                    <li class="item">5</li>
                                </ul>
                                 -->    
                                <!-- 二級菜單_end -->
                            </div>
					
                    <!-- btm_end -->
				</div>
				<!-- slot-game-title_end -->

                <!-- slot-game-cont_begin -->
                <div id="slot-game-cont">
					<div class="slot-game-cont fl">
                        <div class="con_cen fl">
                            <ul>
                            <c:forEach var="item" items="${data }" varStatus="i">
                               <li>
                                    <div class="pt_img">
                                        <img src="${statics2 }/png/game/${item.gamecodeweb}" />
                                        <div class="rsp"></div>
                                        <div class="text">
                                        <a href="#" onclick="popup('avng')"><h2>进入游戏</h2></a>
                                        <a href="#" onclick="testGame('avng')"><h3>立即试玩</h3></a>
                                        </div>
                                    </div>
								<p>${item.cnname}<br>${item.enname}</p>
                               </li>
                            </c:forEach>   
                            </ul>

                    </div>
                </div>
			</div>
</div>
	<script src="${statics}/js/ptgame.js" type="text/javascript"></script>
	<script type="text/javascript">
	$(function(){
		$(".user_agreement").click(function(){
			$(".agreement_alert").show();
			$(".alpha").show();
		})
		$('.agreement_alert .btn_close').click(function(){
			$(".agreement_alert").hide();
			$(".alpha").hide();
		});
		$(".alpha").css("height",$("body").outerHeight());

		/*****
		 $("#shiyan>li").click(function(){
			 $("#shiyan>li").removeClass("active");
			 $(this).addClass("active");
			$("div.btm ul").hide();
			$("div.btm ul").eq($(this).index()).stop().show();
		});
		******/
		
		 $("div.btm ul li").click(function(){
			//$(".con_cen ul").hide();
			//$(".con_cen ul").eq($(this).index()).stop().show();
		});
	})
	
</script>
</body>
</html>
