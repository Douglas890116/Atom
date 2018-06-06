<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="ctx" value="${pageContext['request'].contextPath}"></c:set>
<c:set var="statics" value="${ctx}/static"></c:set>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title></title> 
    <link rel="stylesheet" type="text/css" href="${statics}/css/paycounter/message.css"/>
    <style type="text/css">
    	*{padding:0;margin:0;}ul,ol,li{list-style:none;}img{display:block;}a{text-decoration:none;}
    	.payM{width:96%;height:4.5rem;margin:10px auto;border-radius:5px 5px 0 0;font-size:0.19rem;border:1px solid #0088cc;background:#F7F8F9;}
    	.top{background:#0088cc;border-radius:4px 4px 0 0;height:0.32rem;line-height:0.32rem;color:white;padding-left:0.2rem;}
    	.list{width:90%;min-height:0.3rem;margin:0.25rem auto;overflow:hidden;}
    	.list>p{float:left;font-weight:600;font-size:0.2rem;}
    	.list>input{border:1px solid #CCC;float:left;height:0.3rem;width:2.0rem;margin-left:10px;}
    	.list1{width:90%;min-height:0.3rem;margin:0.25rem auto;overflow:hidden;}
    	.list1>p{float:left;font-weight:600;font-size:0.2rem;}
    	.list1>input{border:1px solid #CCC;height:0.25rem;width:2.0rem;}
    	.btn{margin: auto; text-align: center;width:100%;}
    	.btn>button{width:80%;height:0.45rem;background:#006dcc;border:0;border-radius:10px;font-size:0.35rem;color:white;cursor:pointer;outline:none;}
    </style>
</head>
<body>
	<form action="${ctx}/TPayment/Astropay" method="post">
		<input type="hidden" name="enterprisethirdpartycode" value="${enterprisethirdpartycode}"/>
		<input type="hidden" name="enterprisecode" value="${enterprisecode}"/>
		<input type="hidden" name="employeecode" value="${employeecode}"/>
		<input type="hidden" name="orderamount" value="${orderamount}"/>
		<input type="hidden" name="brandcode" value="${brandcode}"/>
		<input type="hidden" name="traceip" value="${traceip}"/>
	<div class="payM">
		<div class="top">Payment Info</div>
		
		<div class="list">
			<p>Account : &nbsp;&nbsp;&nbsp;${username}</p>
		</div>
		
		<div class="list">
			<p>Amount : &nbsp;&nbsp;&nbsp;<fmt:formatNumber value="${orderamount}" pattern="##.##" minFractionDigits="2"/></p>
		</div>

		<div class="list">
			<p>Card Num : </p>
			<input type="number" name="cardNum" placeholder="   AstroPay Card Number" required/>
		</div>

		<div class="list">
			<p>CVV : &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
			<input type="number" name="cardCode" placeholder="   AstroPay CVV" required/>
		</div>

		<div class="list1">
			<p>EXP Date : &nbsp;&nbsp;</p>
			<input type="number" name="expireMonth" placeholder="   Month" min="1" max="12" required style="width: 0.5rem;"/> / <input type="number" name="expireYear" placeholder="   Year" min="2017" required style="width: 0.5rem;"/>
		</div>

		<div class="list">
			<p>Currency : &nbsp;</p>
			<input type="text" name="currency" placeholder="   USD/ERU/RMB..." value="RMB" required/>
		</div>

		<div class="btn">
			<button type="submit" class="paySure" id="BtnSubmit">Submit</button>
		</div>

	</div>
	
	</form>
</body>
<script src="${statics}/js/jquery-1.8.1.min.js" type="text/javascript" charset="utf-8"></script>
<%-- <script src="${statics}/js/message.js" type="text/javascript" charset="utf-8"></script> --%>
<script type="text/javascript">
	function getRem(pwidth,prem){
		var html = document.getElementsByTagName("html")[0];
		var oWidth = document.body.clientWidth || document.documentElement.clientWidth;
		html.style.fontSize = oWidth/pwidth*prem+"px";
	}
	window.onload = function(){getRem(414,100);}
	window.onresize = function(){getRem(414,100);}

</script>
</html>