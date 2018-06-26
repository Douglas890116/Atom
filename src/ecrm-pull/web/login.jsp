<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>后台管理登录</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="Thu, 19 Nov 1900 08:52:00 GMT">
<script type="text/javascript">
if (top.location != self.location)top.location=self.location;
</script>
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
<!--Loading style vendors-->
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/resource/vendors/animate.css/animate.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/resource/vendors/iCheck/skins/all.css">
<!--Loading style-->
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/resource/css/themes/style1/pink-blue.css" class="default-style">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/resource/css/themes/style1/pink-blue.css" id="theme-change" class="style-change color-change">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/resource/css/style-responsive.css">

</head>

<body id="signin-page">
	<div class="page-form">
		<form class="form" method="post">
			<div class="header-content">
				<h1>后台管理登录</h1>
			</div>
			<div class="body-content">
				<div class="form-group">
					<div class="input-icon right">
						<i class="fa fa-user"></i> <input type="text" placeholder="帐号" name="ADMIN_NAME" id="ADMIN_NAME" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<div class="input-icon right">
						<i class="fa fa-key"></i> <input type="password" placeholder="密码" name="ADMIN_PWD" id="ADMIN_PWD" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<div class="input-icon right">
						<input type="text" placeholder="验证码" name="CODE" id="CODE" class="form-control" style="width: 70%; float: left;"><img id="validate"
							src="${pageContext.request.contextPath}/client/validate.do" style="width: 30%; height: 40px; cursor: pointer; border: none;" />
					</div>
				</div>
				<div class="clearfix"></div>
				<div class="clearfix">&nbsp;</div>
				<div class="form-group pull-left"></div>
				<div class="form-group pull-right">
					<button type="button" id="login" class="btn btn-success">
						登 录 &nbsp; <i class="fa fa-chevron-circle-right"></i>
					</button>
				</div>
				<div class="clearfix"></div>
			</div>
		</form>
	</div>
	<script src="${pageContext.request.contextPath }/resource/js/jquery-1.10.2.min.js"></script>
	<script src="${pageContext.request.contextPath }/resource/js/html5shiv.js"></script>
	<script src="${pageContext.request.contextPath }/resource/js/respond.min.js"></script>
	<script type="text/javascript">
		$(function() {
			$("#validate").click(
					function() {
						$(this).attr(
								"src",
								"${pageContext.request.contextPath}/client/validate.do?d="
										+ Math.random());
					});
			$("#refresh").click(
					function() {
						$("#validate").attr(
								"src",
								"${pageContext.request.contextPath}/client/validate.do?d="
										+ Math.random());
					});

			$("#login").click(function() {
				$(this).attr({
					"disabled" : "disabled"
				});
				var data = $("form").serialize();
				$.ajax({
					type : "POST",
					url : "${pageContext.request.contextPath}/client/login.do",
					dataType : "json",
					data : data,
					success : function(data) {
						if (data.result_code == "0") {
							window.location.href = data.url;
						} else if (data.result_code == "2") {
							alert("请填写完整帐号、密码或验证码信息！");
							$('#login').removeAttr("disabled");
						} else if (data.result_code == "3") {
							alert("验证码错误！");
							$('#login').removeAttr("disabled");
						} else {
							alert("帐号或密码错误！");
							$('#login').removeAttr("disabled");
						}
					}
				});
			});
		});
	</script>
</body>

</html>