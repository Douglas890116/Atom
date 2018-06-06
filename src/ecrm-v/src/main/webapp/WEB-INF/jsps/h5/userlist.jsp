<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsps/h5/layout/header.jsp" %>
<%@include file="/WEB-INF/jsps/h5/layout/sidebar.jsp" %>

  <!-- content start -->
  <div class="admin-content">
    <div class="admin-content-body">
      <div class="am-cf am-padding am-padding-bottom-0">
        <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">会员信息</strong> / <small>我的会员信息</small></div>
      </div>

      <hr>

    <form class="am-form" action="${ctx}/h5/userList" method="post">
      <div class="am-g">

        <div class="am-u-sm-12 am-u-md-3">
          <div class="am-btn-toolbar">
            <div class="am-btn-group am-btn-group-xs">
              <a type="button" class="am-btn am-btn-default" href="${ctx}/h5/userAdd"><span class="am-icon-plus"></span> 创建会员</a>
            </div>
          </div>
        </div>

        <hr/>

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
                <th class="displayalias">姓名</th>
                <th class="balance">余额</th>
                <th class="parentemployeeaccount am-hide-sm-only">上级</th>
                <th class="logindatetime am-hide-sm-only">注册时间</th>
                <th class="lastlogintime">最后登录时间</th>
              </tr>
              </thead>
              <tbody>
              <c:if test="${list != null && fn:length(list) > 0}">
              <c:forEach items="${list}" var="data" varStatus="i">
	              <tr>
	                <td class="am-hide-sm-only">${data.brandname}</td>
	                <td class="am-text-primary"><a href="${ctx}/h5/showUser?employeecode=${data.employeecode}">${data.loginaccount}</a></td>
	                <td class="">${data.displayalias}</td>
	                <td class="am-text-warning"><fmt:formatNumber value="${data.balance}" pattern="##.##" minFractionDigits="2"/></td>
	                <td class="am-hide-sm-only">${data.parentemployeeaccount}</td>
	                <td class="am-hide-sm-only"><fmt:formatDate value="${data.logindatetime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	                <td class=""><fmt:formatDate value="${data.lastlogintime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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