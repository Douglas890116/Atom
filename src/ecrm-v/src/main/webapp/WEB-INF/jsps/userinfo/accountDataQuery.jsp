<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<%@page import="com.maven.cache.SystemCache"%>
<c:set var="isEnglish" value="${sessionScope.LANGUAGE == 'en'}"/>
<style>
body,dd,div,dl,dt,h1,h2,h3,h4,h5,h6,li,ol,p,ul{margin:0;padding: 0; color: #000; list-style:none;font-family: "ff-tisa-web-pro-1","ff-tisa-web-pro-2","Lucida Grande","Helvetica Neue",Helvetica,Arial,"Hiragino Sans GB","Hiragino Sans GB W3","WenQuanYi Micro Hei",sans-serif;font-size: 14px;}
ul{ line-height:28px;}
strong{color: #575757; font-size:14px;font-family: 'Microsoft Yahei';}
.center{ width:830px; height:200px; background-color:ccc;}
.center_l{background:url(${statics}/img/hfdgfdgfdgdfgfdg.jpg) no-repeat; width:180px; height:170px; margin-top:15px; margin-left:15px; float:left}
.center_r{ width:600px;height:160px; margin-top:10px;float:left; margin-left:20px}

.center_r .butt{color:#428bca}
.center_r .butt:hover{ cursor:pointer;color:#3071a9}
span.location{ position:absolute;margin-left:50px}
.table{margin-bottom:0px;}
.table tr td{border: none;}
.table tr td{ height:30px; line-height:30px; text-align:center}
.table tr td a{ width:50px; align-content:center;}
.conter{width:800px; margin-left:15px;}
.conter a{ color:#000;}
.center_r ul li{ clear:both; width:800px;line-height: 30px;}
.center_r ul li b{ display:block; float:left; width:265px; padding-right:0px; font-weight:normal}
.center_r ul li span{ display:block; float:left}
.clear{ clear:both}
.employee-cls{background-image: url("${statics}/img/empl.png");}
#bg{background:black; filter:alpha(opacity:30);opacity:0.3; height:100%;position:absolute;width:100%;top:0; }
div.row{margin-bottom:6px;}
body{border:1px #ccc solid; }
.nocss{padding: 0px 0px;border: 0px; width: 55px;}
</style>
<body>
 <div class="center">
   <div class="center_l">
   </div>
   <div class="center_r">
    <ul>
      <li>
        <b>
          <c:choose>
            <c:when test="${sessionScope.LANGUAGE == 'en'}">Account：</c:when>
            <c:otherwise>登录账号：</c:otherwise>
          </c:choose>
          <a href="javascript:openEmployeeMessage();" style="font-size:18px;">${employeeInformation.loginaccount}</a>
        </b>
        <span id="balanceDiv">
          <c:choose>
            <c:when test="${sessionScope.LANGUAGE == 'en'}">Balance：</c:when>
            <c:otherwise>账户余额：</c:otherwise>
          </c:choose>
          <fmt:formatNumber value="${employeeInformation.balance}" pattern="##.##" minFractionDigits="2"/>
        </span>
        &nbsp;&nbsp;
        <select id="currency" class="nocss">
          <option value="CNY">CNY</option>
          <option value="USD">USD</option>
          <option value="EUR">EUR</option>
          <option value="IDR">IDR</option>
          <option value="KRW">KRW</option>
          <option value="HKD">HKD</option>
          <option value="TWD">TWD</option>
        </select>
        <input id="balance" type="hidden" value="${employeeInformation.balance}"/>
      </li>
      <li>
        <b id="totalDepositB">
          <c:choose>
            <c:when test="${sessionScope.LANGUAGE == 'en'}">Total Deposits：</c:when>
            <c:otherwise>存款总额：</c:otherwise>
          </c:choose>
          ${employeeInformation.depositTotal}
        </b>
        <input id="totalDeposit" type="hidden" value="${employeeInformation.depositTotal}" />
        <span id="totalTakeSpan">
          <c:choose>
            <c:when test="${sessionScope.LANGUAGE == 'en'}">Total Withdrawals：</c:when>
            <c:otherwise>取款总额：</c:otherwise>
          </c:choose>
          ${employeeInformation.takeTotal}
        </span>
        <input id="totalTake" type="hidden" value="${employeeInformation.takeTotal}" />
      </li>
      <li>
        <b>
          <c:choose>
            <c:when test="${sessionScope.LANGUAGE == 'en'}">Member Dividends：</c:when>
            <c:otherwise>用户分红：</c:otherwise>
          </c:choose>
          <fmt:parseNumber value="${employeeInformation.dividend*100}"/>%
        </b>
        <span>
          <c:choose>
            <c:when test="${sessionScope.LANGUAGE == 'en'}">Member Commission：</c:when>
            <c:otherwise>用户占成：</c:otherwise>
          </c:choose>
          <fmt:parseNumber value="${employeeInformation.share*100}"/>%
        </span>
      </li>
      <li>
        <b>
          <c:choose>
            <c:when test="${sessionScope.LANGUAGE == 'en'}">Registration Time：</c:when>
            <c:otherwise>用户注册时间：</c:otherwise>
          </c:choose>
          <fmt:formatDate value="${employeeInformation.registerDate}" type="both" />
        </b>
      </li>
      <li>
        <b><span>
          <c:choose>
            <c:when test="${sessionScope.LANGUAGE == 'en'}">Last Login Time：</c:when>
            <c:otherwise>最近登录时间：</c:otherwise>
          </c:choose>
          <fmt:formatDate value="${employeeInformation.lastLoginDate}" type="both" /></span>
        </b>
      </li>
      <li>
        <b>
          <c:choose>
            <c:when test="${sessionScope.LANGUAGE == 'en'}">Last Login IP：</c:when>
            <c:otherwise>最&nbsp;近&nbsp;登&nbsp;录&nbsp;IP：</c:otherwise>
          </c:choose>
          <a style="font-size:18px;" href="javascript:openIpMessage('${employeeInformation.employeecode}');">${employeeInformation.ip}</a>
        </b>
      </li>
    </ul>
   </div>
 </div>
 <hr/>
 <div class="conter">
 <a>洗码：</a>
        <div class="borders">
          <table style="width:100%" class="table">
              <c:forEach items="${listBonus}" var="game">
                <tr>
                  <td align="center">${game.gamename}：</td>
                  <c:forEach items="${SystemCache.getInstance().getGameCategory(game.gametype)}" var="category">
                    <td align="center">${category.categoryname}:</td>
                    <td align="center"><a style="color:#c9302c;align-content:center;width:50px;">
                    <fmt:parseNumber value="${game.bonus*100}"/></a>%</td>
                  </c:forEach>
                </tr>
              </c:forEach>
          </table>
        </div>
</div>
 <div class="demo-content">
 <!-- 此节点内部的内容会在弹出框内显示,默认隐藏此节点-->
    <div id="content" style="display: none">
    	<div style="padding-left: 25px;">
	        <div class="row">
	          <div class="control-group span8">
	            <label class="control-label">登录账号：${enterpriseEmployee.loginaccount}</label>
	          </div>
	        </div>
	        <div class="row">
	          <div class="control-group span8">
	            <label class="control-label">上级账号：${enterpriseEmployee.parentemployeeaccount}</label>
	          </div>
	        </div>
	        <div class="row">
	          <div class="control-group span8">
	            <label class="control-label">账号别名：${enterpriseEmployee.displayalias}</label>
	          </div>
	        </div>
	        <div class="row">
	          <div class="control-group span8">
	            <label class="control-label">用户类型：${enterpriseEmployee.employeetypename}</label>
	          </div>
	        </div>
	        <div class="row">
	          <div class="control-group span8">
	            <label class="control-label">用户级别：${enterpriseEmployee.employeelevelname}</label>
	          </div>
	        </div>
	        <div class="row">
	          <div class="control-group span8">
	            <label class="control-label">所属企业：${enterpriseEmployee.enterprisename}</label>
	          </div>
	        </div>
	        <div class="row">
	          <div class="control-group span8">
	            <label class="control-label">所属品牌：${enterpriseEmployee.brandname}</label>
	          </div>
	        </div>
	        <div class="row">
	          <div class="control-group span8">
	            <label class="control-label">在线状态：
	              <c:if test="${enterpriseEmployee.onlinestatus==0}">下线 </c:if>
	              <c:if test="${enterpriseEmployee.onlinestatus==1}">在线 </c:if>
	            </label>
	          </div>
	        </div>
	        <div class="row">
	          <div class="control-group span8">
	            <label class="control-label">用户状态：
	              <c:if test="${enterpriseEmployee.employeestatus==1}">启用 </c:if>
	              <c:if test="${enterpriseEmployee.employeestatus==2}">锁定游戏</c:if>
	              <c:if test="${enterpriseEmployee.employeestatus==3}">禁用</c:if>
	            </label>
	          </div>
	        </div>
	        <div class="row">
	          <div class="control-group span8">
	            <label class="control-label">用户分红：<fmt:parseNumber value="${enterpriseEmployee.dividend*100}"/>%</label>
	          </div>
	        </div>
	        <div class="row">
	          <div class="control-group span8">
	            <label class="control-label">用户占成：<fmt:parseNumber value="${enterpriseEmployee.share*100}"/>%</label>
	          </div>
	        </div>
	        <div class="row">
	          <div class="control-group span8">
	            <label class="control-label">注册时间：<fmt:formatDate value="${enterpriseEmployee.logindatetime}" type="both"/></label>
	          </div>
	        </div>
	        <div class="row">
	          <div class="control-group span8">
	            <label class="control-label">最后登录时间：<fmt:formatDate value="${enterpriseEmployee.lastlogintime}" type="both"/></label>
	          </div>
	    	</div>
        </div>
    </div>
</div>
<script type="text/javascript" >
$("#currency").change(function(){
	var currency = $(this).val();
	var balance = $("#balanceDiv").text();
	var deposit = $("#totalDepositB").text();
	var take = $("#totalTakeSpan").text();
	
	var balance_text = balance.split('：')[0].trim();
	var deposit_text = deposit.split('：')[0].trim();
	var take_text = take.split('：')[0].trim();
	
	var balance_amount = $("#balance").val();
	var deposit_amount = $("#totalDeposit").val();
	var take_amount = $("#totalTake").val();
	
	if(currency == 'CNY') {
		$("#balanceDiv").html(balance_text + "： " + balance_amount);
		$("#totalDepositB").html(deposit_text + "： " + deposit_amount);
		$("#totalTakeSpan").html(take_text + "： " + take_amount);
		return;
	}
	$.ajax({
		url  : '${ctx}/exchangerate/getExchangeRate',
		data : {
				'currency' : currency,
				'balance' : balance_amount,
				'deposit' : deposit_amount,
				'take' : take_amount
				},
		dataType : 'json',
		success  : function(result) {
			if (result.status == 1) {
				var map = result.data;
				$("#balanceDiv").html(balance_text + "： " + map.balance);
				$("#totalDepositB").html(deposit_text + "： " + map.deposit);
				$("#totalTakeSpan").html(take_text + "： " + map.take);
			}else {
				BUI.Message.Alert(result.message,"error");
			}
		},
		error : function(){
			BUI.Message.Alert("系统错误，请联系管理员!","error");
		}
	});
});
</script>
</body>
</html>