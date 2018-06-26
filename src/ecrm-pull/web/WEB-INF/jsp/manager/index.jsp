<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>后台框架</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="Thu, 19 Nov 1900 08:52:00 GMT">
<link rel="shortcut icon" href="${pageContext.request.contextPath }/resource/images/icons/favicon.ico">
<link rel="apple-touch-icon" href="${pageContext.request.contextPath }/resource/images/icons/favicon.png">
<link rel="apple-touch-icon" sizes="72x72" href="${pageContext.request.contextPath }/resource/images/icons/favicon-72x72.png">
<link rel="apple-touch-icon" sizes="114x114" href="${pageContext.request.contextPath }/resource/images/icons/favicon-114x114.png">
<!--Loading bootstrap css-->
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/resource/css/family-OpenSans.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/resource/css/family-Oswald.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/resource/vendors/jquery-ui-1.10.4.custom/css/ui-lightness/jquery-ui-1.10.4.custom.min.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/resource/vendors/font-awesome/css/font-awesome.min.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/resource/vendors/bootstrap/css/bootstrap.min.css">
<!--LOADING STYLESHEET FOR PAGE-->
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/resource/vendors/intro.js/introjs.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/resource/vendors/calendar/zabuto_calendar.min.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/resource/vendors/sco.message/sco.message.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/resource/vendors/intro.js/introjs.css">
<!--Loading style vendors-->
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/resource/vendors/animate.css/animate.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/resource/vendors/jquery-pace/pace.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/resource/vendors/iCheck/skins/all.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/resource/vendors/jquery-notific8/jquery.notific8.min.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/resource/vendors/bootstrap-daterangepicker/daterangepicker-bs3.css">
<!--Loading style-->
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/resource/css/themes/style1/orange-blue.css" class="default-style">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/resource/css/themes/style1/orange-blue.css" id="theme-change" class="style-change color-change">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/resource/css/style-responsive.css">

<style type="text/css">
td {
	word-wrap: break-word;
	word-break: normal;
}
</style>
</head>
<body class="sidebar-default header-fixed">
	<!--BEGIN BACK TO TOP-->
	<a id="totop" href="#"> <i class="fa fa-angle-up"></i>
	</a>
	<!--END BACK TO TOP-->
	<!--BEGIN TOPBAR-->
	<div id="header-topbar-option-demo" class="page-header-topbar" style="z-index: 100">
		<nav id="topbar" role="navigation" style="margin-bottom: 0; z-index: 2;" class="navbar navbar-default navbar-static-top">
			<div class="navbar-header">
				<button type="button" data-toggle="collapse" data-target=".sidebar-collapse" class="navbar-toggle">
					<span class="sr-only"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
				</button>
				<a id="logo" href="${pageContext.request.contextPath}/manager/proxy/list.do" class="navbar-brand" target="mainFrame"> <span class="fa fa-rocket"></span> <span class="logo-text">管理后台</span> <span style="display: none"
					class="logo-text-icon">M</span>
				</a>
			</div>
			<div class="topbar-main">
				<a id="menu-toggle" href="#" class="hidden-xs"> <i class="fa fa-bars"></i>
				</a>
				<ul class="nav navbar navbar-top-links navbar-right mbn" style="display:none">
				
					<li class="dropdown topbar-user"><a data-hover="dropdown" href="#" class="dropdown-toggle"> <img src="${pageContext.request.contextPath }/resource/images/pic.png" width="48" height="48" alt=""
							class="img-responsive img-circle" /> &nbsp; <span class="hidden-xs"></span> &nbsp; <span class="caret"></span>
					</a>
						<ul class="dropdown-menu dropdown-user pull-left">
							<li><a href="${pageContext.request.contextPath }/manager/system/stat.do" target="mainFrame"> <i class="fa fa-home"></i> 首页
							</a></li>
							<li><a href="${pageContext.request.contextPath }/manager/system/editPwd.do" target="mainFrame"> <i class="fa fa-lock"></i> 修改密码
							</a></li>
							<li><a href="${pageContext.request.contextPath }/manager/system/logout.do"> <i class="fa fa-sign-out"></i> 安全退出
							</a></li>
						</ul></li>
				</ul>
			</div>
		</nav>
	</div>
	<!--END TOPBAR-->

	<div id="wrapper">
		<!--左侧导航菜单开始-->
		<nav id="sidebar" role="navigation" class="navbar-default navbar-static-side">
		  <div class="sidebar-collapse menu-scroll">
		    <ul id="side-menu" class="nav">
		      <li style="display:none"></li>

			      <li class="active"><a href="#"><i class="fa fa-pagelines fa-fw"><div class="icon-bg bg-pink"></div></i><span class="menu-title">代理模块</span><span class="fa arrow"></span></a>
			        <ul class="nav nav-second-level">				      
						<li><a href="${pageContext.request.contextPath}/manager/proxy/list.do" target="mainFrame"><i class="fa fa-group"></i><span class="submenu-title">代理管理</span></a></li>
						<li><a href="${pageContext.request.contextPath}/manager/key/list.do" target="mainFrame"><i class="fa fa-puzzle-piece"></i><span class="submenu-title">密钥管理</span></a></li>
			        </ul>
			      </li>
			      <li><a href="#"><i class="fa fa-gamepad fa-fw"><div class="icon-bg bg-pink"></div></i><span class="menu-title">游戏模块</span><span class="fa arrow"></span></a>
			        <ul class="nav nav-second-level">				      
						<li><a href="${pageContext.request.contextPath}/manager/game/list.do" target="mainFrame"><i class="fa fa-headphones"></i><span class="submenu-title">游戏管理</span></a></li>
						<li><a href="${pageContext.request.contextPath}/manager/kind/list.do" target="mainFrame"><i class="fa fa-sitemap"></i><span class="submenu-title">种类管理</span></a></li>
						<li><a href="${pageContext.request.contextPath}/manager/type/list.do" target="mainFrame"><i class="fa fa-asterisk"></i><span class="submenu-title">类型管理</span></a></li>
			        </ul>
			      </li>
			      
			      
			      <li><a href="#"><i class="fa fa-table fa-fw"><div class="icon-bg bg-pink"></div></i><span class="menu-title">游戏数据拉取</span><span class="fa arrow"></span></a>
			        <ul class="nav nav-second-level">				      
						<li><a href="${pageContext.request.contextPath}/manager/pull/toPull.do" target="mainFrame"><i class="fa fa-gears"></i><span class="submenu-title">游戏数据拉取中心</span></a></li>
			        </ul>
			      </li>
		    </ul>
		  </div>
		</nav>
		<!--左侧导航菜单结束-->
		<div id="page-wrapper">
			<!--------------------------------------------------主休内容-------------------------------------------------->
			<iframe id="mainFrame" name="mainFrame" src="${pageContext.request.contextPath }/manager/proxy/list.do" frameborder="0"></iframe>
		</div>
	</div>
	<script src="${pageContext.request.contextPath }/resource/js/jquery-1.10.2.min.js"></script> 
	<script src="${pageContext.request.contextPath }/resource/js/jquery-migrate-1.2.1.min.js"></script> 
	<script src="${pageContext.request.contextPath }/resource/js/jquery-ui.js"></script> 
	<!--loading bootstrap js--> 
	<script src="${pageContext.request.contextPath }/resource/vendors/bootstrap/js/bootstrap.min.js"></script> 
	<script src="${pageContext.request.contextPath }/resource/vendors/bootstrap-hover-dropdown/bootstrap-hover-dropdown.js"></script> 
	<script src="${pageContext.request.contextPath }/resource/js/html5shiv.js"></script> 
	<script src="${pageContext.request.contextPath }/resource/js/respond.min.js"></script> 
	<script src="${pageContext.request.contextPath }/resource/vendors/metisMenu/jquery.metisMenu.js"></script> 
	<script src="${pageContext.request.contextPath }/resource/vendors/slimScroll/jquery.slimscroll.js"></script> 
	<script src="${pageContext.request.contextPath }/resource/vendors/jquery-cookie/jquery.cookie.js"></script> 
	<script src="${pageContext.request.contextPath }/resource/vendors/iCheck/icheck.min.js"></script> 
	<script src="${pageContext.request.contextPath }/resource/vendors/iCheck/custom.min.js"></script> 
	<script src="${pageContext.request.contextPath }/resource/vendors/jquery-notific8/jquery.notific8.min.js"></script> 
	<script src="${pageContext.request.contextPath }/resource/vendors/jquery-highcharts/highcharts.js"></script> 
	<script src="${pageContext.request.contextPath }/resource/js/jquery.menu.js"></script> 
	<script src="${pageContext.request.contextPath }/resource/vendors/jquery-pace/pace.min.js"></script> 
	<script src="${pageContext.request.contextPath }/resource/vendors/holder/holder.js"></script> 
	<script src="${pageContext.request.contextPath }/resource/vendors/responsive-tabs/responsive-tabs.js"></script>
	<!--CORE JAVASCRIPT--> 
	<script src="${pageContext.request.contextPath }/resource/js/main.js"></script> 
	<!--LOADING SCRIPTS FOR PAGE-->
	<script type="text/javascript">
		if (document.body.clientWidth < 500) {
			$('#mainFrame').css('height', document.body.clientHeight - 50);
		}
		
	</script>
</body>
</html>