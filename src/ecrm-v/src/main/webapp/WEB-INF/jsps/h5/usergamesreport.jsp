<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsps/h5/layout/header.jsp" %>
<%@include file="/WEB-INF/jsps/h5/layout/sidebar.jsp" %>

  <!-- content start -->
  <div class="admin-content">
    <div class="admin-content-body">
      <div class="am-cf am-padding am-padding-bottom-0">
        <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">游戏报表</strong> / <small>会员游戏汇总</small></div>
      </div>
      <div class="am-cf am-padding am-padding-bottom-0">
        <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">Game Report</strong> / <small>Member Game Report</small></div>
      </div>

      <hr>

    <form class="am-form" action="${ctx}/h5/userGamesReport" method="post">
      <div class="am-g">
        <div class="am-u-sm-12 am-u-md-3">
          <div class="am-input-group am-input-group-sm">
            <input type="text" name="loginaccount" placeholder="登录账户" value="${params.loginaccount}" class="am-form-field"/>
            <%-- <input type="text" name="parentemployeeaccount" placeholder="上级账户" value="${params.parentemployeeaccount}" class="am-form-field"/> --%>
            <span class="am-input-group-btn">
            <button class="am-btn am-btn-default" type="submit">搜索</button>
            </span>
          </div>
        </div>
      </div>

      <div class="am-g">
        <div class="am-u-sm-12">
        
            <table class="am-table am-table-striped am-table-hover table-main am-table-centered">
              <thead>
              <tr>
                <th class="platformid am-hide-sm-only">流水号</th>
                <th class="loginaccount">账号<br/>account</th>
                <th class="platformtype">游戏<br/>game</th>
                <th class="gamebigtype am-hide-sm-only">游戏类型</th>
                <th class="betmoney am-hide-sm-only">投注额</th>
                <th class="validbet am-hide-sm-only">有效投注</th>
                <th class="netmoney">输赢(CNY)<br/>win/lose(CNY)</th>
                <th class="bettime">投注时间<br/>bet time</th>
              </tr>
              </thead>
              <tbody>
              <c:if test="${list != null && fn:length(list) > 0}">
              <c:forEach items="${list}" var="data" varStatus="i">
	              <tr>
	                <td class="am-text-middle am-hide-sm-only">${data.platformid}</td>
	                <td class="am-text-middle am-text-primary">${data.loginaccount}</td>
	                <td class="am-text-middle ">${data.platformtype}</td>
	                <td class="am-text-middle am-hide-sm-only">${data.gamebigtype}</td>
	                <td class="am-text-middle am-hide-sm-only"><fmt:formatNumber value="${data.betmoney}" pattern="##.##" minFractionDigits="2"/></td>
	                <td class="am-text-middle am-hide-sm-only"><fmt:formatNumber value="${data.validbet}" pattern="##.##" minFractionDigits="2"/></td>
	                <td class="am-text-middle "><fmt:formatNumber value="${data.netmoney}" pattern="##.##" minFractionDigits="2"/></td>
	                <td class="am-text-middle "><fmt:formatDate value="${data.bettime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	                <td></td>
	              </tr>
              </c:forEach>
              </c:if>
              </tbody>
            </table>
			<%@include file="/WEB-INF/jsps/h5/layout/pagination.jsp" %>
        </div>
      </form>

      </div>
    </div>
  </div>
<!-- 下面这个div结尾要保留，开始在sidebar里面 -->
</div>

<%@include file="/WEB-INF/jsps/h5/layout/footer.jsp" %>