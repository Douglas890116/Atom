<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="am-cf admin-main">
<!-- sidebar start -->
  <div class="admin-sidebar am-offcanvas" id="admin-offcanvas">
    <div class="am-offcanvas-bar admin-offcanvas-bar">
	<!-- 左侧菜单 start -->
	<ul id="collapase-top" class="am-list admin-sidebar-list">
		<li class="am-panel"><a href="${ctx}/h5/index"><span class="am-icon-home"></span> 首 页 </a></li>
		<li class="am-panel">
		<a data-am-collapse="{parent: '#collapase-top', target: '#collapse-operation'}">
			<span class="am-icon-users"></span> 运 营 管 理 <span class="am-icon-angle-right am-fr am-margin-right"></span>
		</a>
		<ul class="am-list am-collapse admin-sidebar-sub ${menuNo == 2 ? 'am-in' : ''}" id="collapse-operation">
			<li><a href="${ctx}/h5/userList"><span class="am-icon-table"></span> 会员信息 </a></li>
			<li><a href="${ctx}/h5/agentList"><span class="am-icon-table"></span> 代理信息 </a></li>
			<li><a href="${ctx}/h5/takeDepositRecords?ordertype=1"><span class="am-icon-table"></span> 存款信息 </a></li>
			<li><a href="${ctx}/h5/takeDepositRecords?ordertype=2"><span class="am-icon-table"></span> 取款信息 </a></li>
			<li><a href="${ctx}/h5/depositAndTakeReport"><span class="am-icon-table"></span> 用户存取统计 </a></li>
			<li><a href="${ctx}/h5/depositAndTakeWarning"><span class="am-icon-table"></span> 提存比预警 </a></li>
			<li><a href="${ctx}/h5/loseWinAnalysis"><span class="am-icon-table"></span> 用户输赢分析 </a></li>
			<li><a href="${ctx}/h5/loseWinWarning"><span class="am-icon-table"></span> 输赢概率预警 </a></li>
		</ul>
		</li>
		<li class="am-panel">
		<a data-am-collapse="{parent: '#collapase-top', target: '#collapse-business'}">
			<span class="am-icon-tasks"></span> 业 务 管 理 <span class="am-icon-angle-right am-fr am-margin-right"></span>
		</a>
		<ul class="am-list am-collapse admin-sidebar-sub ${menuNo == 3 ? 'am-in' : ''}" id="collapse-business">
			<li><a href="${ctx}/h5/userGamesReport"><span class="am-icon-table"></span> 会员游戏汇总 </a></li>
			<li><a href="${ctx}/h5/userDailyReport"><span class="am-icon-table"></span> 会员打码日结算 </a></li>
			<li><a href="${ctx}/h5/userWeeklyReport"><span class="am-icon-table"></span> 会员打码周结算 </a></li>
			<li><a href="${ctx}/h5/agentDailyReport"><span class="am-icon-table"></span> 代理打码日结算 </a></li>
			<li><a href="${ctx}/h5/agentWeeklyReport"><span class="am-icon-table"></span> 代理打码周结算 </a></li>
			<li><a href="${ctx}/h5/nbReportA"><span class="am-icon-table"></span> 代理占城结算 A </a></li>
			<li><a href="${ctx}/h5/nbReportB"><span class="am-icon-table"></span> 代理占城结算 B </a></li>
			<%--<li><a href="${ctx}/h5/plusLessMinute"><span class="am-icon-table"></span> 冲正冲负 </a></li> --%>
			<li><a href="${ctx}/h5/userBalance"><span class="am-icon-table"></span> 额度转换 </a></li>
		</ul>
		</li>
		<!-- li class="am-panel"><a href="${ctx}/h5/creatAgent"><span class="am-icon-pencil-square-o"></span> 创 建 代 理 </a></li-->
		<li class="am-panel"><a href="${ctx}/h5/exit"><span class="am-icon-sign-out"></span> 注销</a></li>
	</ul>
	<!-- 左侧菜单 end -->
      <div class="am-panel am-panel-default admin-sidebar-panel">
        <div class="am-panel-bd">
          <p><span class="am-icon-tag"></span> wiki</p>
          <p>欢迎使用H5版后台管理系统，系统还处于完善阶段，若有问题请向管理员询问，祝您使用愉快 !</p>
        </div>
      </div>
    </div>
  </div>
<!-- sidebar end -->