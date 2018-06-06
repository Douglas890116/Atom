<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsps/h5/layout/header.jsp" %>
<%@include file="/WEB-INF/jsps/h5/layout/sidebar.jsp" %>

  <!-- content start -->
  <div class="admin-content">
    <div class="admin-content-body">
      <div class="am-cf am-padding am-padding-bottom-0">
        <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">统计管理</strong> / <small>会员存取统计</small></div>
      </div>

      <hr>

    <form class="am-form" action="${ctx}/h5/depositAndTakeReport">
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
                <th class="table-loginaccount">账号</th>
                <th class="table-depositNumber">存款次数</th>
                <th class="table-allDepositMoney">存款总额</th>
                <th class="table-quantity">取款次数</th>
                <th class="table-allTakeMoney">取款总额</th>
              </tr>
              </thead>
              <tbody>
              <c:if test="${list != null && fn:length(list) > 0}">
              <c:forEach items="${list}" var="user" varStatus="i">
	              <tr>
	                <td class="am-text-primary">${user.loginaccount}</td>
	                <td class="">${user.depositNumber}</td>
	                <td class="am-text-success"><fmt:formatNumber value="${user.allDepositMoney + 0}" pattern="##.##" minFractionDigits="2"/></td>
	                <td class="">${user.quantity}</td>
	                <td class="am-text-danger"><fmt:formatNumber value="${user.allTakeMoney + 0}" pattern="##.##" minFractionDigits="2"/></td>
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