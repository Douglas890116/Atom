<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsps/h5/layout/header.jsp" %>
<%@include file="/WEB-INF/jsps/h5/layout/sidebar.jsp" %>

  <!-- content start -->
  <div class="admin-content">
    <div class="admin-content-body">
      <div class="am-cf am-padding am-padding-bottom-0">
        <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">统计管理</strong> / <small>会员输赢分析</small></div>
      </div>

      <hr>

    <form class="am-form" action="${ctx}/h5/loseWinAnalysis">
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
                <th class="table-parentemployeeaccount am-hide-sm-only">上级账号</th>
                <th class="table-accumulateddeposit">总存</th>
                <th class="table-accumulatedwithdraw">总取</th>
                <th class="table-summoney">总输赢</th>
              </tr>
              </thead>
              <tbody>
              <c:if test="${list != null && fn:length(list) > 0}">
              <c:forEach items="${list}" var="data" varStatus="i">
	              <tr>
	                <td class="am-text-primary">${data.loginaccount}</td>
	                <td class="am-hide-sm-only">${data.parentemployeeaccount}</td>
	                <td class=""><p class="am-text-success"><fmt:formatNumber value="${data.accumulateddeposit}" pattern="##.##" minFractionDigits="2"/></p></td>
	                <td class=""><p class="am-text-danger"><fmt:formatNumber value="${data.accumulatedwithdraw}" pattern="##.##" minFractionDigits="2"/></p></td>
	                <td class=""><p class="am-text-primary"><fmt:formatNumber value="${data.summoney}" pattern="##.##" minFractionDigits="2"/></p></td>
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