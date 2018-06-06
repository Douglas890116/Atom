<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="ctx" value="<%=request.getContextPath()%>" />
<c:set var="statics" value="${ctx}/static"></c:set>

<title>【${sessionScope.username}】PT老虎机游戏
 <c:if test="${sessionScope.gametype=='0'}" >
-试玩
 </c:if>
</title>
<link rel="stylesheet" type="text/css" href="${statics}/pt/css/style.css">
<script src="${statics}/pt/js/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="${statics}/pt/js/index.js" type="text/javascript"></script>
<script type="text/javascript" src="http://cache.download.banner.greatfortune88.com/integrationjs.php"></script>
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
    
    window.location('http://cache.download.banner.greatfortune88.com/casinoclient.html?language=zh-cn&game='+id);
};
function popup(idx)
 {
	window.open('http://cache.download.banner.greatfortune88.com/casinoclient.html?language=zh-cn&game='+idx,'Games','width=800,height=600,menubar=no,scrollbars=no,toolbar=no,location=no,directories=no,resizable=no');
	login(1);
 	
 	
 }
function testGame(idx)
 {
 	window.open('http://cache.download.banner.greatfortune88.com/casinoclient.html?language=zh-cn&game='+idx+'&mode=offline&currency=cny','Games','width=800,height=600,menubar=no,scrollbars=no,toolbar=no,location=no,directories=no,resizable=no') ;
 }

 </script>

