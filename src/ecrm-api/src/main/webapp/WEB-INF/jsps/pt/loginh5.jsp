<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="ctx" value="<%=request.getContextPath()%>" />
<html>
<head>

<meta charset="UTF-8">

<title>正在登录PT...</title>


<script type="text/javascript" src="https://login.luckydragon88.com/jswrapper/integration.js.php?casino=greatfortune88"></script>

<script type="text/javascript">

iapiSetCallout('Login', calloutLogin);

function login() {
	iapiSetClientPlatform("mobile&deliveryPlatform=HTML5");
	var realMode = 1;
	iapiLogin('${sessionScope.username}'.toUpperCase(), '${sessionScope.password}', realMode, "zh-cn");
}


function calloutLogin(response) {
	if (response.errorCode && response.errorCode != 6) {
		alert("Login failed. " + response.playerMessage + " Error code: " + response.errorCode);
		return;
	}
	else {
		//window.location = "lobby.html?username=" + document.getElementById("loginform").username.value;
		window.location = "${ctx}/ptgame/indexh5";
	}
}

login();

</script>



</head>

<body>
正在登录PT...



</body></html>