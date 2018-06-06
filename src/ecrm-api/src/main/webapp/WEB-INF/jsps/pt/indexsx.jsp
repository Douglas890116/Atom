<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="ctx" value="<%=request.getContextPath()%>" />
<c:set var="statics" value="${ctx}/static"></c:set>

<html>
<head>
<meta charset="utf-8">

<title>正在进入....【${sessionScope.username}】PT真人
 <c:if test="${sessionScope.gametype=='0'}" >
-试玩
 </c:if>
</title>
<script src="${statics}/pt/js/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="${statics}/pt/js/index.js" type="text/javascript"></script>
<script type="text/javascript" src="https://login.luckydragon88.com/jswrapper/integration.js.php?casino=greatfortune88"></script>
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
    
    window.location = ('http://cache.download.banner.greatfortune88.com/casinoclient.html?language=zh-cn&game=${gametype}');
};
function popup(id)
 {
 	window.open('http://cache.download.banner.greatfortune88.com/casinoclient.html?language=zh-cn&game='+id,'Games','width=800,height=600,menubar=no,scrollbars=no,toolbar=no,location=no,directories=no,resizable=no');
 	login(1);
 }
function testGame(id)
 {
 	window.open('http://cache.download.banner.greatfortune88.com/casinoclient.html?language=zh-cn&game='+id+'&mode=offline&currency=cny','Games','width=800,height=600,menubar=no,scrollbars=no,toolbar=no,location=no,directories=no,resizable=no') ;
 }

login(1);

 </script>

</head>



<body>
正在跳转...
</body>
</html>
