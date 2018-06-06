<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:set var="ctx" value="${pageContext['request'].contextPath}"></c:set>
<c:set var="statics" value="${ctx}/static"></c:set>
<c:set var="isEnglish" value="${sessionScope.LANGUAGE == 'en'}"/>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title></title>
    <link rel="stylesheet" href="${statics}/css/bs3/bui-min.css" />
    <link rel="stylesheet" href="${statics}/css/bs3/dpl-min.css" />
    <!-- 自定义的公共样式文件 -->
    <link rel="stylesheet" href="${statics}/css/custom/common.css"  />
    <link rel="stylesheet" href="${statics}/css/custom/commonStyle.css" />
    
    <script type="text/javascript" src="${statics}/js/jquery-1.8.1.min.js"></script>
    <script type="text/javascript" src="${statics}/js/ajaxfileupload-min.js"></script>
    <script type="text/javascript" src="${statics}/js/bui.js"></script>
    <script type="text/javascript" src="${statics}/js/sea.js"></script>
    <script type="text/javascript" src="${statics}/js/slider-min.js"></script>
    <script type="text/javascript" src="${statics}/js/config-min.js"></script>
    <script type="text/javascript" src="${statics}/js/laydate/laydate.js"></script>
    <script type="text/javascript" src="${statics}/js/charts/acharts-min.js"></script>
    <!-- 通用JS -->
   <script type="text/javascript" src="${statics}/js/custom/commons-min.js?date=201709121754"></script>
	<!-- 自定义JS，修改过自定义JS后在对应JS映入地址后面加一个时间参数，一遍更新浏览器上的JS -->
    <script type="text/javascript" src="${statics}/js/custom/submitJS.js?date=201709121754"></script>
    <script type="text/javascript" src="${statics}/js/custom/commonJS.js?date=201709121754"></script>
  	
</head>