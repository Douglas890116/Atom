<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="ctx" value="${pageContext['request'].contextPath}"></c:set>
<c:set var="statics" value="${ctx}/static"></c:set>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>AstroPay Cards</title>
		<link rel="stylesheet" type="text/css" href="${statics}/css/paycounter/index.css"/>
		<%-- <link rel="stylesheet" type="text/css" href="${statics}/css/paycounter/radio-banks.css"/> --%>
	</head>
	<body>
		<form action="${ctx}/TPayment/Astropay" method="post">
			<input type="hidden" name="enterprisethirdpartycode" value="${enterprisethirdpartycode}"/>
			<input type="hidden" name="enterprisecode" value="${enterprisecode}"/>
			<input type="hidden" name="employeecode" value="${employeecode}"/>
			<input type="hidden" name="orderamount" value="${orderamount}"/>
			<input type="hidden" name="brandcode" value="${brandcode}"/>
			<input type="hidden" name="traceip" value="${traceip}"/>
			<div class="top">Payment Info</div>
			<div class="payM">

				<div class="payMess">
					<label>Account : &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${username}</label>
				</div>
				

				<div class="payMess">
					<label>Amount : &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:formatNumber value="${orderamount}" pattern="##.##" minFractionDigits="2"/></label>
				</div>
				
				<div class="payMess">
					<label>Card Num : </label>
					<input type="number" name="cardNum" placeholder="AstroPay Card Number" required/>
					<p id="cardNum">Required</p>
				</div>

				<div class="payMess">
					<label>CVV : &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
					<input type="number" name="cardCode" placeholder="AstroPay CVV" required/>
					<p id="cardCode">Required</p>
				</div>

				<div class="payMess">
					<label>EXP Date : &nbsp;&nbsp;&nbsp;</label>
					<div class="exp">
						<input type="number" name="expireMonth" placeholder="Month" min="1" max="12" required/> / <input type="number" name="expireYear" placeholder="Year" min="2017" required/>
					<p id="expDate">Required</p>
					</div>
				</div>

				<div class="payMess">
					<label>Currency : &nbsp;&nbsp;</label>
					<input type="text" name="currency" placeholder="USD/ERU/RMB..." value="RMB" required/>
					<p id="currency">Required</p>
				</div>

			</div>
			<div class="tab_pay">
				<div class="btn">
					<button type="submit" class="paySure" id="BtnSubmit">Submit</button>
				</div>
			</div>
		</form>
	</body>
<script src="${statics}/js/jquery-1.8.1.min.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
	$("button").click(function() {
		var flag = true;
		$("p").hide();
		if ($("input[name='cardNum']").val() == '') {
			$("#cardNum").show();
			flag = false;
		}
		if ($("input[name='cardCode']").val() == '') {
			$("#cardCode").show();
			flag =  false;
		}
		if ($("input[name='expireMonth']").val() == ''
				|| $("input[name='expireYear']").val() == '') {
			$("#expDate").show();
			flag =  false;
		}
		if ($("input[name='currency']").val() == '') {
			$("#currency").show();
			flag = false;
		}
		return flag;
	});
</script>
</html>