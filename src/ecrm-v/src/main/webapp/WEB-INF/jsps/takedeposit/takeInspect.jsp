<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<!-- 存款申请单审批 -->
    <div style="margin:0px 18px;font-weight: bold;" id="approve-panel">
              <div class="control-group">
                  <div class="controls" style="width: 100%">
					上次存款时间：${inspect.lastsavedate}
					<br/>
					<a style="display:inline-block;width: 49%">上次存款金额：<font color="red">${inspect.lastsaveamount}</font></a>
                    <a style="display:inline-block;width: 49%">上次存款后余额：<font color="red">
                    <c:set var="aftersubalance" value="${inspect.afterupiamount+inspect.afterupibalance}"></c:set>
                    ${inspect.aftersaveupidate==''?"----":aftersubalance}
                    </font></a>
                  </div>
              </div>
              <hr style="margin: 3px 0px;"/>
              <div class="control-group">
                  <div class="controls" style="width: 100%">
					存款后上分时间：${inspect.aftersaveupidate==''?"----":inspect.aftersaveupidate}
					<br/>
					<a style="display:inline-block;width: 49%">存款后上分金额：<font color="red">${inspect.aftersaveupidate==''?"未上分":inspect.afterupiamount}</font></a>
                    <a style="display:inline-block;width: 49%">上分后账户余额：<font color="red">${inspect.aftersaveupidate==''?"未上分":inspect.afterupibalance}</font></a>
                  </div>
              </div>
              <hr style="margin: 3px 0px;"/>
              <div class="control-group">
                  <div class="controls" style="width: 100%">
                  			本次取款时间：${inspect.takemoneydate}
					<br/>
						<a style="display:inline-block;width: 49%">本次取款金额：<font color="red">${inspect.takeamount}</font></a>
						<a style="display:inline-block;width: 49%">本次取款后余额：<font color="red">${inspect.aftertakebalance}</font></a>
                  </div>
              </div>
              <hr style="margin: 3px 0px;"/>
              <div class="control-group">
                  <div class="controls">
                  		帐变时间周期： (${inspect.lastsavedate} -- ${inspect.takemoneydate})
                  		<br/>
						<a style="display:inline-block;width: 49%">帐变金额：<font color="red">${inspect.moneychangeamout}</font></a>
                  </div>
              </div>
              <hr style="margin: 3px 0px;"/>
              <div class="control-group">
                  <div class="controls">
                  		游戏时间周期：
                  		<c:choose>
                  			<c:when test="${inspect.aftersaveupidate==''}">
                  				----
                  			</c:when>
                  			<c:otherwise>
                  				(${inspect.aftersaveupidate} -- ${inspect.takemoneydate})
                  			</c:otherwise>
                  		</c:choose>
                  		<br/>
						<a style="display:inline-block;width: 49%">游戏输赢：<font color="red">${inspect.gamelosewin}</font></a>
                  </div>
              </div>
              <hr/>
              	<div class="control-group">
                  <div class="controls" style="font-size: 14px;">
                  		稽查: 
                  		<c:set var="inspect_result" value="${aftersubalance + inspect.takeamount + inspect.gamelosewin == inspect.aftertakebalance}"></c:set>
                  		<c:choose>
                  			<c:when test="${inspect_result }">
                  				<font style="color: rgb(16, 135, 43);">上次存款后余额(${aftersubalance}) + 游戏盈亏(${inspect.gamelosewin}) - 取款金额(${inspect.takeamount.abs()}) = 账户余额(${inspect.aftertakebalance })</font>
                  			</c:when>
                  			<c:otherwise>
                  				<font style="font-size: 14px;color: red;">上次存款后余额(${aftersubalance}) + 游戏盈亏(${inspect.gamelosewin}) - 取款金额(${inspect.takeamount.abs()}) ≠ 账户余额(${inspect.aftertakebalance })</font>
                  			</c:otherwise>
                  		</c:choose>
                  		
                  </div>
              </div>
              <hr/>
              <div class="control-group">
                  <div class="controls">
                  	<a style="display:inline-block;width: 24%;margin-bottom: 5px;" href="javascript:openNewWindow('account_money_change', '${ctx}/employeeMoneyChange/index?loginaccount=${inspect.loginaccount}&moneyChangeDate_begin=${inspect.lastsavedate}&moneyChangeDate_end=${ inspect.takemoneydate}', '账户帐变');">账变记录</a>
                  	<a style="display:inline-block;width: 24%;margin-bottom: 5px;" href="javascript:openNewWindow('updown_intergral', '${ctx}/moneyInAndOut/index?loginaccount=${inspect.loginaccount}&moneyinoutdate_begin=${inspect.lastsavedate}&moneyinoutdate_end=${ inspect.takemoneydate}', '转分信息');">上下分记录</a>
                  	<a style="display:inline-block;width: 24%;margin-bottom: 5px;" href="javascript:openNewWindow('needbet_record', '${ctx}/ActivityBetRecord/list?loginaccount=${inspect.loginaccount}&createtime_begin=${inspect.takemoneydate}&createtime_end=', '优惠流水信息');">优惠流水信息</a>
                  	<c:if test="${inspect.taglosewin!=0}">
	                  	<a style="display:inline-block;width: 24%;margin-bottom: 5px;" href="javascript:openNewWindow('hy_grecords', '${ctx}/NHQGame/index?loginaccount=${inspect.loginaccount}&startDate=${inspect.aftersaveupidate}&endDate=${ inspect.takemoneydate}', 'HY-好赢');">HY-好赢</a>
                  	</c:if>
                  	<c:if test="${inspect.nhqlosewin!=0}">
                  		<a style="display:inline-block;width: 24%;margin-bottom: 5px;" href="javascript:openNewWindow('bbin_grecords', '${ctx}/BBINGame/list?loginaccount=${inspect.loginaccount}&startDate=${inspect.aftersaveupidate}&endDate=${ inspect.takemoneydate}', 'BBIN-波音');">BBIN-波音</a>
                  	</c:if>
                  	<c:if test="${inspect.taglosewin!=0}">
                  		<a style="display:inline-block;width: 24%;margin-bottom: 5px;" href="javascript:openNewWindow('tag_grecords', '${ctx}/TAGGame/index?loginaccount=${inspect.loginaccount}&startDate=${inspect.aftersaveupidate}&endDate=${ inspect.takemoneydate}', 'AG-亚游');">AG-亚游</a>
                  	</c:if>
                  	<c:if test="${inspect.oglosewin!=0}">
                  		<a style="display:inline-block;width: 24%;margin-bottom: 5px;" href="javascript:openNewWindow('oag_grecords', '${ctx}/OGGame/list?loginaccount=${inspect.loginaccount}&startDate=${inspect.aftersaveupidate}&endDate=${ inspect.takemoneydate}', 'OG-东方');">OG-东方</a>
                  	</c:if>
                  	<c:if test="${inspect.ptlosewin!=0}">
                  		<a style="display:inline-block;width: 24%;margin-bottom: 5px;" href="javascript:openNewWindow('pt_grecords', '${ctx}/PTGame/list?loginaccount=${inspect.loginaccount}&startDate=${inspect.aftersaveupidate}&endDate=${ inspect.takemoneydate}', 'PT-小游戏');">PT-小游戏</a>
                  	</c:if>
                  	<c:if test="${inspect.xcplosewin!=0}">
                  		<a style="display:inline-block;width: 24%;margin-bottom: 5px;" href="javascript:openNewWindow('xr_grecords', '${ctx}/XCPGame/list?loginaccount=${inspect.loginaccount}&startDate=${inspect.aftersaveupidate}&endDate=${ inspect.takemoneydate}', 'XR-彩票');">XR-彩票</a>
                  	</c:if>
                  	<c:if test="${inspect.ibclosewin!=0}">
                  		<a style="display:inline-block;width: 24%;margin-bottom: 5px;" href="javascript:openNewWindow('sb_grecords', '${ctx}/IBCGame/list?loginaccount=${inspect.loginaccount}&startDate=${inspect.aftersaveupidate}&endDate=${ inspect.takemoneydate}', 'SB-沙巴体育');">SB-沙巴体育</a>
                  	</c:if>
                  </div>
              </div>
  </div>
