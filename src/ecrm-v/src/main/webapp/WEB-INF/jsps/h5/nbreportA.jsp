<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsps/h5/layout/header.jsp" %>
<%@include file="/WEB-INF/jsps/h5/layout/sidebar.jsp" %>

  <!-- content start -->
  <div class="admin-content">
    <div class="admin-content-body">
      <div class="am-cf am-padding am-padding-bottom-0">
        <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">占成待结算</strong> / <small>占成待结算Ａ</small></div>
      </div>

      <hr>

    <form class="am-form" action="${ctx}/h5/nbReportA" method="post">
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
                <th class="remark am-hide-sm-only">用户类型</th>
                <th class="countMember am-hide-sm-only">团队用户数</th>
                <th class="game_betting_amount am-hide-sm-only">投注总额</th>
                <th class="lose_win_amount">游戏输赢</th>
                <th class="depositMoney">总存款</th>
                <th class="dividend am-hide-sm-only">存款手续费</th>
                <th class="withdrawMoney">总取款</th>
                <th class="share am-hide-sm-only">取款手续费</th>
                <th class="activity_money">优惠金额</th>
                <th class="bonus">洗码金额</th>
                <th class="profit_amount">结算额</th>
              </tr>
              </thead>
              <tbody>
              <c:if test="${list != null && fn:length(list) > 0}">
              <c:forEach items="${list}" var="data" varStatus="i">
                  <tr class="${i.count == 1 ? 'am-active' : ''}">
                    <td class="am-text-middle am-text-primary">${data.loginaccount}</td>
                    <td class="am-text-middle am-hide-sm-only">${data.remark}</td>
                    <td class="am-text-middle am-hide-sm-only">会员：${data.countMember}<br/>代理：${data.countAgent}</td>
                    <td class="am-text-middle am-hide-sm-only"><fmt:formatNumber value="${data.game_betting_amount}" pattern="##.##" minFractionDigits="2"/></td>
                    <td class="am-text-middle "><fmt:formatNumber value="${data.lose_win_amount}" pattern="##.##" minFractionDigits="2"/></td>
                    <td class="am-text-middle "><fmt:formatNumber value="${data.depositMoney}" pattern="##.##" minFractionDigits="2"/></td>
                    <td class="am-text-middle am-hide-sm-only"><fmt:formatNumber value="${data.dividend}" pattern="##.##" minFractionDigits="2"/></td>
                    <td class="am-text-middle "><fmt:formatNumber value="${data.withdrawMoney}" pattern="##.##" minFractionDigits="2"/></td>
                    <td class="am-text-middle am-hide-sm-only"><fmt:formatNumber value="${data.share}" pattern="##.##" minFractionDigits="2"/></td>
                    <td class="am-text-middle "><fmt:formatNumber value="${data.activity_money}" pattern="##.##" minFractionDigits="2"/></td>
                    <td class="am-text-middle "><fmt:formatNumber value="${data.bonus}" pattern="##.##" minFractionDigits="2"/></td>
                    <td class="am-text-middle "><fmt:formatNumber value="${data.profit_amount}" pattern="##.##" minFractionDigits="2"/></td>
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