<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsps/h5/layout/header.jsp" %>
<%@include file="/WEB-INF/jsps/h5/layout/sidebar.jsp" %>

  <!-- content start -->
  <div class="admin-content">
    <div class="admin-content-body">
      <div class="am-cf am-padding am-padding-bottom-0">
        <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">会员信息</strong> / <small>我的代理信息</small></div>
      </div>

      <hr>

    <form class="am-form" action="${ctx}/h5/agentList" method="post">
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
                <th class="brandname am-hide-sm-only">品牌</th>
                <th class="loginaccount">账号</th>
                <th class="displayalias am-hide-sm-only">姓名</th>
                <th class="dividend">分红</th>
                <th class="share">占城</th>
                <th class="logindatetime">代理/会员</th>
                <th class="lastlogintime am-hide-sm-only">最后登录时间</th>
              </tr>
              </thead>
              <tbody>
              <c:if test="${list != null && fn:length(list) > 0}">
              <c:forEach items="${list}" var="data" varStatus="i">
	              <tr>
	                <td class="am-text-middle am-hide-sm-only">${data.brandname}</td>
	                <td class="am-text-middle am-text-primary">
	                <c:if test="${sessionScope.ERCM_USER_SESSION.employeetypecode == 'T005'}">
	                  <a href="${ctx}/h5/agentPointTransfer?employeecode=${data.employeecode}">
	                </c:if>
	                ${data.loginaccount}
	                <c:if test="${sessionScope.ERCM_USER_SESSION.employeetypecode == 'T005'}">
	                  </a>
	                </c:if>
	                </td>
	                <td class="am-text-middle am-hide-sm-only">${data.displayalias}</td>
	                <td class="am-text-middle "><p class="am-text-success"><fmt:formatNumber value="${data.dividend}" pattern="##.##" minFractionDigits="2"/>%</p></td>
	                <td class="am-text-middle "><p class="am-text-success"><fmt:formatNumber value="${data.share}" pattern="##.##" minFractionDigits="2"/>%</p></td>
	                <td class="am-text-middle "><p class="am-text-warning">${data.agentCount} / ${data.memberCount}</p></td>
	                <td class="am-text-middle am-hide-sm-only"><fmt:formatDate value="${data.lastlogintime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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