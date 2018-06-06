<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsps/h5/layout/header.jsp" %>
<%@include file="/WEB-INF/jsps/h5/layout/sidebar.jsp" %>

  <!-- content start -->
  <div class="admin-content">
    <div class="admin-content-body">
      <div class="am-cf am-padding am-padding-bottom-0">
        <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">会员信息</strong> / <small>会员${ordername}信息</small></div>
      </div>

      <hr>

    <form class="am-form" action="${ctx}/h5/takeDepositRecords?ordertype=${ordertype}" method="post">
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
                <th class="table-ordernumber am-hide-sm-only">订单号</th>
                <th class="table-loginaccount">账号</th>
                <th class="table-orderamount">订单金额</th>
                <th class="table-employeepaymentaccount am-hide-sm-only">会员收付款信息</th>
                <th class="table-employeepaymentaccount am-hide-sm-only">企业收付款信息</th>
                <th class="table-orderdate">订单时间</th>
                <th class="table-orderstatus">状态</th>
              </tr>
              </thead>
              <tbody>
              <c:if test="${list != null && fn:length(list) > 0}">
              <c:forEach items="${list}" var="data" varStatus="i">
	              <tr>
	                <td class="am-hide-sm-only">${data.ordernumber}</td>
	                <td class="am-text-primary">${data.loginaccount}</td>
	                <td class="">
	                <c:if test="${ordertype == 1}"><p class="am-text-success"></c:if>
	                <c:if test="${ordertype == 2}"><p class="am-text-danger"></c:if>
	                	<fmt:formatNumber value="${data.orderamount}" pattern="##.##" minFractionDigits="2"/>
	                </p>
	                </td>
	                <td class="am-hide-sm-only">${data.employeepaymentaccount}</td>
	                <td class="am-hide-sm-only">${data.enterprisepaymentaccount}</td>
	                <td class=""><fmt:formatDate value="${data.orderdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	                <td class="">
	                	<c:if test="${data.orderstatus == 2}">
	                	<p class="am-text-success">通过</p>
	                	</c:if>
	                	<c:if test="${data.orderstatus == 4}">
	                	<p class="am-text-danger">拒绝</p>
	                	</c:if>
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