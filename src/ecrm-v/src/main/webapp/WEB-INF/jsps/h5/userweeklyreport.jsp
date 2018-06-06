<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsps/h5/layout/header.jsp" %>
<%@include file="/WEB-INF/jsps/h5/layout/sidebar.jsp" %>

  <!-- content start -->
  <div class="admin-content">
    <div class="admin-content-body">
      <div class="am-cf am-padding am-padding-bottom-0">
        <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">打码结算</strong> / <small>会员打码周结算</small></div>
      </div>

      <hr>

    <form class="am-form" action="${ctx}/h5/userWeeklyReport" method="post">
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
                <th class="loginaccount">账号</th>
                <th class="gameplatform">游戏</th>
                <th class="gametype am-hide-sm-only">游戏种类</th>
                <th class="ratio am-hide-sm-only">洗码比例</th>
                <th class="bet">有效投注</th>
                <th class="amount am-hide-sm-only">应发</th>
                <th class="actual am-hide-sm-only">实发</th>
                <th class="subtract">已发</th>
                <th class="amount-subtract">未发</th>
                <th class="starttime-endtime">报表时间</th>
                <th class="reporttime am-hide-sm-only">统计时间</th>
                <th class="status">状态</th>
                <th class="paytype am-hide-sm-only">数据类型</th>
              </tr>
              </thead>
              <tbody>
              <c:if test="${list != null && fn:length(list) > 0}">
              <c:forEach items="${list}" var="data" varStatus="i">
	              <tr>
	                <td class="am-text-middle am-text-primary">${data.loginaccount}</td>
	                <td class="am-text-middle ">${data.gameplatform}</td>
	                <td class="am-text-middle am-hide-sm-only">${data.gametype}</td>
	                <td class="am-text-middle am-hide-sm-only"><fmt:formatNumber value="${data.ratio}" pattern="##.##" minFractionDigits="2"/>%</td>
	                <td class="am-text-middle "><fmt:formatNumber value="${data.bet}" pattern="##.##" minFractionDigits="2"/></td>
	                <td class="am-text-middle am-hide-sm-only"><fmt:formatNumber value="${data.amount}" pattern="##.##" minFractionDigits="2"/></td>
	                <td class="am-text-middle am-hide-sm-only"><fmt:formatNumber value="${data.actual}" pattern="##.##" minFractionDigits="2"/></td>
	                <td class="am-text-middle "><fmt:formatNumber value="${data.subtract}" pattern="##.##" minFractionDigits="2"/></td>
	                <td class="am-text-middle am-text-danger"><fmt:formatNumber value="${data.amount - data.subtract}" pattern="##.##" minFractionDigits="2"/></td>
	                <td class="am-text-middle "><fmt:formatDate value="${data.starttime}" pattern="yyyy-MM-dd"/><br/>至<br/><fmt:formatDate value="${data.endtime}" pattern="yyyy-MM-dd"/></td>
	                <td class="am-text-middle am-hide-sm-only"><fmt:formatDate value="${data.reporttime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	                <td class="am-text-middle ">
	                	<c:if test="${data.status == 1}"><span class="am-badge">未发放</span></c:if>
	                	<c:if test="${data.status == 2}"><span class="am-badge am-badge-success">已发放</span></c:if>
	                	<c:if test="${data.status == 3}"><span class="am-badge am-badge-success">周结汇总</span></c:if>
	                </td>
	                <td class="am-text-middle am-hide-sm-only">
	                	<c:if test="${data.paytype == 1}">正常</c:if>
	                	<c:if test="${data.paytype == 2}"><b>补发</b></c:if>
	                </td>
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