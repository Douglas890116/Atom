<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@include file="/WEB-INF/jsps/layout/base-header.jsp"%>

<!doctype html>
<c:set var="title" value="${(sessionScope.ERCM_USER_SESSION.brandcode==''||sessionScope.ERCM_USER_SESSION.brandcode==null)?sessionScope.ERCM_USER_SESSION.enterprisename:sessionScope.ERCM_USER_SESSION.brandname}"/>
<html class="no-js fixed-layout">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>${title}</title>
  <meta name="description" content="${title}">
  <meta name="keywords" content="index">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="renderer" content="webkit">
  <meta http-equiv="Cache-Control" content="no-cashe" />
  <link rel="icon" type="image/png" href="${statics}/assets/i/favicon.png">
  <link rel="apple-touch-icon-precomposed" href="assets/i/app-icon72x72@2x.png">
  <meta name="apple-mobile-web-app-title" content="Amaze UI" />
  <link rel="stylesheet" href="${statics}/assets/css/amazeui.min.css"/>
  <link rel="stylesheet" href="${statics}/assets/css/admin.css">
  
  <!--[if lt IE 9]>
  <script src="http://libs.baidu.com/jquery/1.11.1/jquery.min.js"></script>
  <script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
  <script src="${statics}/assets/js/amazeui.ie8polyfill.min.js"></script>
  <![endif]-->
  
  <!--[if (gte IE 9)|!(IE)]><!-->
  <script type="text/javascript" src="${statics}/js/jquery-1.8.1.min.js"></script>
  <!--<![endif]-->
  
  <script src="${statics}/assets/js/amazeui.min.js"></script>
  <script src="${statics}/assets/js/app.js"></script>
</head>
<body>
<!--[if lte IE 9]>
<p class="browsehappy">你正在使用<strong>过时</strong>的浏览器，Amaze UI 暂不支持。 请 <a href="http://browsehappy.com/" target="_blank">升级浏览器</a>以获得更好的体验！</p>
<![endif]-->

<header class="am-topbar am-topbar-inverse admin-header">
  <div class="am-topbar-brand">
	<strong>${title}</strong>
	<small>管理系统</small>
  </div>

  <button class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only" data-am-collapse="{target: '#topbar-collapse'}"><span class="am-sr-only">导航切换</span> <span class="am-icon-bars"></span></button>

  <div class="am-collapse am-topbar-collapse" id="topbar-collapse">

    <ul class="am-nav am-nav-pills am-topbar-nav am-topbar-right admin-header-list">
      <li class="am-dropdown" data-am-dropdown>
        <a class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">
          <span class="am-icon-users"></span>${sessionScope.ERCM_USER_SESSION.displayalias}<span class="am-icon-caret-down"></span>
        </a>
        <ul class="am-dropdown-content">
          <li><a href="${ctx}/h5/exit"><span class="am-icon-power-off"></span>退出</a></li>
        </ul>
      </li>
      <li class="am-hide-sm-only"><a href="javascript:;" id="admin-fullscreen"><span class="am-icon-arrows-alt"></span><span class="admin-fullText">开启全屏</span></a></li>
    </ul>
  </div>
</header>