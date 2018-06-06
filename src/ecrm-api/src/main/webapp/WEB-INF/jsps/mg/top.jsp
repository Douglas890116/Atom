<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="ctx" value="<%=request.getContextPath()%>" />
<c:set var="statics" value="${ctx}/static"></c:set>

<title>MG电子游艺
 <c:if test="${sessionScope.gametype=='0'}" >
-试玩
 </c:if>
</title>
<link rel="stylesheet" type="text/css" href="${statics}/mg/style.css">
<script src="${statics}/pt/js/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="${statics}/pt/js/index.js" type="text/javascript"></script>

