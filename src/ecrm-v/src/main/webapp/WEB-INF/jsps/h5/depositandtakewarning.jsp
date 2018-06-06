<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsps/h5/layout/header.jsp" %>
<%@include file="/WEB-INF/jsps/h5/layout/sidebar.jsp" %>

  <!-- content start -->
  <div class="admin-content">
    <div class="admin-content-body">
      <div class="am-cf am-padding am-padding-bottom-0">
        <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">风险管理</strong> / <small>会员提存预警</small></div>
      </div>

      <hr>

    <form class="am-form" action="${ctx}/h5/userlist">
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
                <th class="table-savemoney am-hide-sm-only">总存</th>
                <th class="table-takemoney am-hide-sm-only">总取</th>
                <th class="table-">提存比(金额)</th>
                <th class="table-savecount am-hide-sm-only">存款次数</th>
                <th class="table-takecount am-hide-sm-only">取款次数</th>
                <th class="table-">提存比(次数)</th>
              </tr>
              </thead>
              <tbody>
              <c:if test="${list != null && fn:length(list) > 0}">
              <c:forEach items="${list}" var="data" varStatus="i">
	              <tr>
	                <td class="am-text-primary">${data.loginaccount}</td>
	                <td class="am-hide-sm-only"><fmt:formatNumber value="${data.savemoney}" pattern="##.##" minFractionDigits="2"/></td>
	                <td class="am-hide-sm-only"><fmt:formatNumber value="${data.takemoney}" pattern="##.##" minFractionDigits="2"/></td>
	                <td class="">
	                <c:set var="rate" value="${(data.takemoney / (data.savemoney + 1)) * 100}"/>
	                <c:if test="${rate < 1}">
	                	<span style="color:green;font-size: 18px;"><fmt:formatNumber value="${rate}" pattern="##.##" minFractionDigits="2"/>%</span>
	                </c:if>
	                <c:if test="${rate >= 1 && rate < 2}">
	                	<span style="color:lightgreen;font-size: 18px;"><fmt:formatNumber value="${rate}" pattern="##.##" minFractionDigits="2"/>%（风险）</span>
	                </c:if>
	                <c:if test="${rate >= 2 && rate < 3}">
	                	<span style="color:lightgreen;font-size: 18px;"><fmt:formatNumber value="${rate}" pattern="##.##" minFractionDigits="2"/>%（1级风险）</span>
	                </c:if>
	                <c:if test="${rate >= 3 && rate < 4}">
	                	<span style="color:greenyellow;font-size: 18px;"><fmt:formatNumber value="${rate}" pattern="##.##" minFractionDigits="2"/>%（2级风险）</span>
	                </c:if>
	                <c:if test="${rate >= 4 && rate < 5}">
	                	<span style="color:greenyellow;font-size: 18px;"><fmt:formatNumber value="${rate}" pattern="##.##" minFractionDigits="2"/>%（3级风险）</span>
	                </c:if>
	                <c:if test="${rate >= 5 && rate < 6}">
	                	<span style="color:yellow;font-size: 18px;"><fmt:formatNumber value="${rate}" pattern="##.##" minFractionDigits="2"/>%（4级风险）</span>
	                </c:if>
	                <c:if test="${rate >= 6 && rate < 7}">
	                	<span style="color:yellow;font-size: 18px;"><fmt:formatNumber value="${rate}" pattern="##.##" minFractionDigits="2"/>%（5级风险）</span>
	                </c:if>
	                <c:if test="${rate >= 7 && rate < 8}">
	                	<span style="color:orange;font-size: 18px;"><fmt:formatNumber value="${rate}" pattern="##.##" minFractionDigits="2"/>%（6级风险）</span>
	                </c:if>
	                <c:if test="${rate >= 8 && rate < 9}">
	                	<span style="color:orangered;font-size: 18px;"><fmt:formatNumber value="${rate}" pattern="##.##" minFractionDigits="2"/>%（7级风险）</span>
	                </c:if>
	                <c:if test="${rate >= 9 && rate < 10}">
	                	<span style="color:orangered;font-size: 18px;"><fmt:formatNumber value="${rate}" pattern="##.##" minFractionDigits="2"/>%（8级风险）</span>
	                </c:if>
	                <c:if test="${rate >= 10}">
	                	<span style="color:red;font-size: 18px;"><fmt:formatNumber value="${rate}" pattern="##.##" minFractionDigits="2"/>%（9级风险）</span>
	                </c:if>
	                </td>
	                <td class="am-hide-sm-only">${data.savecount}</td>
	                <td class="am-hide-sm-only">${data.takecount}</td>
	                <td class="">
	                <c:set var="rate" value="${(data.takecount / (data.savecount + 1)) * 100}"/>
	                <c:if test="${rate <= 1}">
	                	<span style="color:green;font-size:18px;"><fmt:formatNumber value="${rate}" pattern="##.##" minFractionDigits="2"/>%</span>
	                </c:if>
	                <c:if test="${rate > 1}">
	                	<span style="color:red;font-size:18px;"><fmt:formatNumber value="${rate}" pattern="##.##" minFractionDigits="2"/>%（风险）</span>
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