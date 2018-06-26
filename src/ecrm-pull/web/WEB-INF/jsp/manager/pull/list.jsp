<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>游戏数据拉取中心</title>
<jsp:include page="../head.jsp" />
<style>
td {
	word-wrap: break-word;
	word-break: normal;
}

td, th {
	text-align: center;
}

.ct {
	width: 50px;
}
</style>

<link href="${pageContext.request.contextPath }/resource/css/mobiscroll.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath }/resource/css/mobiscroll_date.css" rel="stylesheet" />
</head>
<body class="sidebar-default header-fixed">
	<input type="hidden" id="json" name="json" value='${entity.json }' />

	<!--面包削导航开始-->
	<div id="title-breadcrumb-option-demo" class="page-title-breadcrumb">
		<div class="page-header pull-left">
			<div class="page-title">游戏数据拉取中心</div>
		</div>
		<ol class="breadcrumb page-breadcrumb">
			<li><i class="fa fa-home"></i>&nbsp;<a href="#">首页</a>&nbsp;&nbsp;<i class="fa fa-angle-right"></i>&nbsp;&nbsp;</li>

			<li class="active">游戏数据拉取中心</li>
		</ol>
		<div class="clearfix"></div>

	</div>
	<!--面包削导航结束-->

	<!--主体内容开始-->
	<div class="page-content">
		<div id="table-action" class="row">
			<div class="col-lg-12">

				<div class="panel panel-yellow">
					<div class="panel-heading">东方游戏数据</div>
					<div class="panel-body pan">
						<div class="form-body pal">
							<form id="aoiForm" name="aoiForm">
								<input type="hidden" name="GAME_ID" value="1" /> <input type="hidden" name="type" value="aoi" />
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<label for="PROXY_ID" class="control-label">所属代理</label> <select id="PROXY_ID" class="form-control" name="PROXY_ID">
												<option value="">请选择代理</option>
												<c:forEach var="m" items="${aoi }">
													<option value="${m.PROXY_ID }">${m.PROXY_NAME}(${m.PROXY_SITE})</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<label for="loginaccount" class="control-label">最后拉取最大vendorid(格式如：1422939011770)</label> <input id="MAX_VALUE" name="MAX_VALUE" type="text" placeholder="" class="form-control"
												maxlength="15" />
										</div>
									</div>


									<div class="col-md-12">
										<div class="form-group">
											<button type="button" class="btn btn-primary" onclick="pullData('aoiForm')">马上拉取</button>
										</div>
									</div>

								</div>
							</form>
						</div>
					</div>
				</div>

				<div class="panel panel-yellow">
					<div class="panel-heading">沙巴体育游戏数据</div>
					<div class="panel-body pan">
						<div class="form-body pal">
							<form id="ibcForm" name="ibcForm">
								<input type="hidden" name="GAME_ID" value="2" /> <input type="hidden" name="type" value="ibc" />
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<label for="PROXY_ID" class="control-label">所属代理</label> <select id="PROXY_ID" class="form-control" name="PROXY_ID">
												<option value="">请选择代理</option>
												<c:forEach var="m" items="${ibc }">
													<option value="${m.PROXY_ID }">${m.PROXY_NAME}(${m.PROXY_SITE})</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<label for="loginaccount" class="control-label">最后拉取最大vendorid(格式如：1422939011770)</label> <input id="MAX_VALUE" name="MAX_VALUE" type="text" placeholder="" class="form-control"
												maxlength="15" />
										</div>
									</div>


									<div class="col-md-12">
										<div class="form-group">
											<button type="button" class="btn btn-primary" onclick="pullData('ibcForm')">马上拉取</button>
										</div>
									</div>

								</div>
							</form>
						</div>
					</div>
				</div>

				<div class="panel panel-yellow">
					<div class="panel-heading">波音游戏数据</div>
					<div class="panel-body pan">
						<div class="form-body pal">
							<form id="bbinForm" name="bbinForm">
								<input type="hidden" name="GAME_ID" value="3" /> <input type="hidden" name="type" value="bbin" />
								<div class="row">
									<div class="col-md-3">
										<div class="form-group">
											<label for="PROXY_ID" class="control-label">上层帐号</label> <select id="PROXY_ID" class="form-control" name="PROXY_ID">
												<option value="">请选择上层帐号</option>
												<c:forEach var="m" items="${bbin }">
													<option value="${m.PROXY_ID }">${m.PROXY_NAME}(${m.PROXY_SITE})</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="col-md-3">
										<div class="form-group">
											<label for="GAME_KIND_ID" class="control-label">游戏种类</label> <select id="GAME_KIND_ID" class="form-control" name="GAME_KIND_ID">
												<option value="">请选择游戏种类</option>
												<c:forEach var="m" items="${bbinKind }">
													<option value="${m.GAME_KIND_ID }">${m.GAME_KIND_NAME}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="col-md-3">
										<div class="form-group">
											<label for="GAME_TYPE_ID" class="control-label">游戏类型</label> <select id="GAME_TYPE_ID" class="form-control" name="GAME_TYPE_ID">
												<option value="">请选择游戏类型</option>
												<c:forEach var="m" items="${gameType }">
													<option value="${m.GAME_TYPE_ID }">${m.GAME_TYPE_NAME}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="col-md-3">
										<div class="form-group">
											<label for="USRE_NAME" class="control-label">会员帐号</label> <input id="USRE_NAME" name="USRE_NAME" type="text" placeholder="" class="form-control" />
										</div>
									</div>
									<div class="col-md-3">
										<div class="form-group">
											<label for="ROUND_DATE" class="control-label">拉取日期</label> <input id="ROUND_DATE" name="ROUND_DATE" type="text" placeholder="" class="form-control" maxlength="10" />
										</div>
									</div>
									<div class="col-md-3">
										<div class="form-group">
											<label for="START_TIME" class="control-label">开始时间</label> <input id="START_TIME" name="START_TIME" type="text" placeholder="" class="form-control" maxlength="8" value="00:00:00" />
										</div>
									</div>
									<div class="col-md-3">
										<div class="form-group">
											<label for="END_TIME" class="control-label">结束时间</label> <input id="END_TIME" name="END_TIME" type="text" placeholder="" class="form-control" maxlength="8" value="23:59:59" />
										</div>
									</div>



									<div class="col-md-12">
										<div class="form-group">
											<button type="button" class="btn btn-primary" onclick="pullData('bbinForm')">马上拉取</button>
										</div>
									</div>

								</div>
							</form>
						</div>
					</div>
				</div>




				<div class="panel panel-yellow">
					<div class="panel-heading">新环球游戏数据</div>
					<div class="panel-body pan">
						<div class="form-body pal">
							<form id="nhqForm" name="nhqForm">
								<input type="hidden" name="GAME_ID" value="4" /> <input type="hidden" name="type" value="nhq" />
								<div class="row">
									<div class="col-md-4">
										<div class="form-group">
											<label for="PROXY_ID" class="control-label">所属代理</label> <select id="PROXY_ID" class="form-control" name="PROXY_ID">
												<option value="">请选择代理</option>
												<c:forEach var="m" items="${nhq }">
													<option value="${m.PROXY_ID }">${m.PROXY_NAME}(${m.PROXY_SITE})</option>
												</c:forEach>
											</select>
										</div>
									</div>

									<div class="col-md-4">
										<div class="form-group">
											<label for="NHQBEGIN_DATE" class="control-label">开始日期</label> <input id="NHQBEGIN_DATE" name="BEGIN_DATE" type="text" placeholder="" class="form-control" maxlength="14" />
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label for="NHQEND_DATE" class="control-label">结束日期</label> <input id="NHQEND_DATE" name="END_DATE" type="text" placeholder="" class="form-control" maxlength="14" />
										</div>
									</div>


									<div class="col-md-12">
										<div class="form-group">
											<button type="button" class="btn btn-primary" onclick="pullData('nhqForm')">马上拉取</button>
										</div>
									</div>

								</div>
							</form>
						</div>
					</div>
				</div>



				<div class="panel panel-yellow">
					<div class="panel-heading">沙龙游戏数据</div>
					<div class="panel-body pan">
						<div class="form-body pal">
							<form id="saForm" name="saForm">
								<input type="hidden" name="GAME_ID" value="5" /> <input type="hidden" name="type" value="sa" />
								<div class="row">
									<div class="col-md-4">
										<div class="form-group">
											<label for="PROXY_ID" class="control-label">所属代理</label> <select id="PROXY_ID" class="form-control" name="PROXY_ID">
												<option value="">请选择代理</option>
												<c:forEach var="m" items="${sa }">
													<option value="${m.PROXY_ID }">${m.PROXY_NAME}(${m.PROXY_SITE})</option>
												</c:forEach>
											</select>
										</div>
									</div>

									<div class="col-md-4">
										<div class="form-group">
											<label for="SABEGIN_DATE" class="control-label">开始日期</label> <input id="SABEGIN_DATE" name="BEGIN_DATE" type="text" placeholder="" class="form-control" maxlength="14" />
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label for="SAEND_DATE" class="control-label">结束日期</label> <input id="SAEND_DATE" name="END_DATE" type="text" placeholder="" class="form-control" maxlength="14" />
										</div>
									</div>

									<div class="col-md-12">
										<div class="form-group">
											<button type="button" class="btn btn-primary" onclick="pullData('saForm')">马上拉取</button>
										</div>
									</div>

								</div>
							</form>
						</div>
					</div>
				</div>




				<div class="panel panel-yellow">
					<div class="panel-heading">TAG游戏数据</div>
					<div class="panel-body pan">
						<div class="form-body pal">
							<form id="tagForm" name="tagForm">
								<input type="hidden" name="GAME_ID" value="6" /> <input type="hidden" name="type" value="tag" />
								<div class="row">
									<div class="col-md-4">
										<div class="form-group">
											<label for="PROXY_ID" class="control-label">所属代理</label> <select id="PROXY_ID" class="form-control" name="PROXY_ID">
												<option value="">请选择代理</option>
												<c:forEach var="m" items="${tag }">
													<option value="${m.PROXY_ID }">${m.PROXY_NAME}(${m.PROXY_SITE})</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label for="GAME_KIND_ID" class="control-label">游戏种类</label> <select id="GAME_KIND_ID" class="form-control" name="GAME_KIND_ID">
												<option value="">请选择游戏种类</option>
												<c:forEach var="m" items="${tagKind }">
													<option value="${m.GAME_KIND_ID }">${m.GAME_KIND_NAME}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label for="loginaccount" class="control-label">最后拉取日期(日期格式如：yyyyMMddHHmm)</label> <input id="MAX_VALUE" name="MAX_VALUE" type="text" placeholder="" class="form-control" maxlength="12" />
										</div>
									</div>


									<div class="col-md-12">
										<div class="form-group">
											<button type="button" class="btn btn-primary" onclick="pullData('tagForm')">马上拉取</button>
										</div>
									</div>

								</div>
							</form>
						</div>
					</div>
				</div>


				<div class="panel panel-yellow">
					<div class="panel-heading">AV游戏数据</div>
					<div class="panel-body pan">
						<div class="form-body pal">
							<form id="avForm" name="avForm">
								<input type="hidden" name="GAME_ID" value="7" /> <input type="hidden" name="type" value="av" />
								<div class="row">
									<div class="col-md-4">
										<div class="form-group">
											<label for="PROXY_ID" class="control-label">所属代理</label> <select id="PROXY_ID" class="form-control" name="PROXY_ID">
												<option value="">请选择代理</option>
												<c:forEach var="m" items="${av }">
													<option value="${m.PROXY_ID }">${m.PROXY_NAME}(${m.PROXY_SITE})</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label for="AVBEGIN_DATE" class="control-label">开始日期</label> <input id="AVBEGIN_DATE" name="BEGIN_DATE" type="text" placeholder="" class="form-control" maxlength="14" />
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label for="AVEND_DATE" class="control-label">结束日期</label> <input id="AVEND_DATE" name="END_DATE" type="text" placeholder="" class="form-control" maxlength="14" />
										</div>
									</div>

									<div class="col-md-12">
										<div class="form-group">
											<button type="button" class="btn btn-primary" onclick="pullData('avForm')">马上拉取</button>
										</div>
									</div>

								</div>
							</form>
						</div>
					</div>
				</div>


				<div class="panel panel-yellow">
					<div class="panel-heading">PT游戏数据</div>
					<div class="panel-body pan">
						<div class="form-body pal">
							<form id="ptForm" name="ptForm">
								<input type="hidden" name="GAME_ID" value="8" /> <input type="hidden" name="type" value="pt" />
								<div class="row">
									<div class="col-md-4">
										<div class="form-group">
											<label for="PROXY_KEY_ID" class="control-label">所属代理</label> <select id="PROXY_KEY_ID" class="form-control" name="PROXY_KEY_ID">
												<option value="">请选择代理</option>
												<c:forEach var="m" items="${pt }">
													<option value="${m.PROXY_KEY_ID }">${m.PROXY_NAME}(${m.PROXY_SITE})</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label for="PTBEGIN_DATE" class="control-label">开始日期</label> <input id="PTBEGIN_DATE" name="BEGIN_DATE" type="text" placeholder="" class="form-control" maxlength="14" />
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label for="PTEND_DATE" class="control-label">结束日期</label> <input id="PTEND_DATE" name="END_DATE" type="text" placeholder="" class="form-control" maxlength="14" />
										</div>
									</div>


									<div class="col-md-12">
										<div class="form-group">
											<button type="button" class="btn btn-primary" onclick="pullData('ptForm')">马上拉取</button>
										</div>
									</div>

								</div>
							</form>
						</div>
					</div>
				</div>


				<div class="panel panel-yellow">
					<div class="panel-heading">zj游戏数据</div>
					<div class="panel-body pan">
						<div class="form-body pal">
							<form id="zjForm" name="zjForm">
								<input type="hidden" name="GAME_ID" value="9" /> <input type="hidden" name="type" value="zj" />
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<label for="PROXY_ID" class="control-label">所属代理</label> <select id="PROXY_ID" class="form-control" name="PROXY_ID">
												<option value="">请选择代理</option>
												<c:forEach var="m" items="${zj }">
													<option value="${m.PROXY_ID }">${m.PROXY_NAME}(${m.PROXY_SITE})</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<label for="ZJMAX_VALUE" class="control-label">最后拉取最大ID</label> <input id="ZJMAX_VALUE" name="MAX_VALUE" type="text" placeholder="" class="form-control" maxlength="14" />
										</div>
									</div>


									<div class="col-md-12">
										<div class="form-group">
											<button type="button" class="btn btn-primary" onclick="pullData('zjForm')">马上拉取</button>
										</div>
									</div>

								</div>
							</form>
						</div>
					</div>
				</div>




				<div class="panel panel-yellow">
					<div class="panel-heading">MG游戏数据</div>
					<div class="panel-body pan">
						<div class="form-body pal">
							<form id="mgForm" name="mgForm">
								<input type="hidden" name="GAME_ID" value="10" /> <input type="hidden" name="type" value="mg" />
								<div class="row">
									<div class="col-md-4">
										<div class="form-group">
											<label for="PROXY_ID" class="control-label">所属代理</label> <select id="PROXY_ID" class="form-control" name="PROXY_ID">
												<option value="">请选择代理</option>
												<c:forEach var="m" items="${mg }">
													<option value="${m.PROXY_ID }">${m.PROXY_NAME}(${m.PROXY_SITE})</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label for="MGBEGIN_DATE" class="control-label">开始日期</label> <input id="MGBEGIN_DATE" name="BEGIN_DATE" type="text" placeholder="" class="form-control" maxlength="14" />
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label for="MGEND_DATE" class="control-label">结束日期</label> <input id="MGEND_DATE" name="END_DATE" type="text" placeholder="" class="form-control" maxlength="14" />
										</div>
									</div>


									<div class="col-md-12">
										<div class="form-group">
											<button type="button" class="btn btn-primary" onclick="pullData('mgForm')">马上拉取</button>
										</div>
									</div>

								</div>
							</form>
						</div>
					</div>
				</div>


				<div class="panel panel-yellow">
					<div class="panel-heading">XCP游戏数据</div>
					<div class="panel-body pan">
						<div class="form-body pal">
							<form id="xcpForm" name="xcpForm">
								<input type="hidden" name="GAME_ID" value="11" /> <input type="hidden" name="type" value="xcp" />
								<div class="row">
									<div class="col-md-4">
										<div class="form-group">
											<label for="PROXY_KEY_ID" class="control-label">所属代理</label> <select id="PROXY_KEY_ID" class="form-control" name="PROXY_KEY_ID">
												<option value="">请选择代理</option>
												<c:forEach var="m" items="${xcp }">
													<option value="${m.PROXY_KEY_ID }">${m.PROXY_NAME}(${m.PROXY_SITE})</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label for="XCPBEGIN_DATE" class="control-label">开始日期</label> <input id="XCPBEGIN_DATE" name="BEGIN_DATE" type="text" placeholder="" class="form-control" maxlength="14" />
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label for="XCPEND_DATE" class="control-label">结束日期</label> <input id="XCPEND_DATE" name="END_DATE" type="text" placeholder="" class="form-control" maxlength="14" />
										</div>
									</div>


									<div class="col-md-12">
										<div class="form-group">
											<button type="button" class="btn btn-primary" onclick="pullData('xcpForm')">马上拉取</button>
										</div>
									</div>

								</div>
							</form>
						</div>
					</div>
				</div>



				<div class="panel panel-yellow">
					<div class="panel-heading">TTG游戏数据</div>
					<div class="panel-body pan">
						<div class="form-body pal">
							<form id="ttgForm" name="ttgForm">
								<input type="hidden" name="GAME_ID" value="12" /> <input type="hidden" name="type" value="ttg" />
								<div class="row">
									<div class="col-md-4">
										<div class="form-group">
											<label for="PROXY_ID" class="control-label">代理帐号</label> <select id="PROXY_ID" class="form-control" name="PROXY_ID">
												<option value="">请选择上层帐号</option>
												<c:forEach var="m" items="${ttg }">
													<option value="${m.PROXY_ID }">${m.PROXY_NAME}(${m.PROXY_SITE})</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label for="transactionType" class="control-label">交易类型</label> <select id="transactionType" class="form-control" name="transactionType">										
												<option value="Game">Game</option>
												<option value="MoneyTransfer">MoneyTransfer</option>
												<option value="ManualAdjustment">ManualAdjustment</option>
												<option value="Bonus">Bonus</option>
											</select>
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label for="currency" class="control-label">货币类型</label> <select id="currency" class="form-control" name="currency">
												<option value="CNY">CNY</option>
												<option value="USD">USD</option>
											</select>
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label for="includeSubPartner" class="control-label">是否包含子合作商</label> <select id="includeSubPartner" class="form-control" name="includeSubPartner">
												<option value="Y">是</option>
												<option value="N">否</option>
											</select>
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label for="TTGBEGIN_DATE" class="control-label">开始日期</label> <input id="TTGBEGIN_DATE" name="BEGIN_DATE" type="text" placeholder="" class="form-control" maxlength="14" />
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label for="TTGEND_DATE" class="control-label">结束日期</label> <input id="TTGEND_DATE" name="END_DATE" type="text" placeholder="" class="form-control" maxlength="14" />
										</div>
									</div>



									<div class="col-md-12">
										<div class="form-group">
											<button type="button" class="btn btn-primary" onclick="pullData('ttgForm')">马上拉取</button>
										</div>
									</div>

								</div>
							</form>
						</div>
					</div>
				</div>
				
				


				<div class="panel panel-yellow">
					<div class="panel-heading">棋牌游戏数据</div>
					<div class="panel-body pan">
						<div class="form-body pal">
							<form id="qpForm" name="qpForm">
								<input type="hidden" name="GAME_ID" value="13" /> <input type="hidden" name="type" value="qp" />
								<div class="row">
									<div class="col-md-4">
										<div class="form-group">
											<label for="PROXY_KEY_ID" class="control-label">所属代理</label> <select id="PROXY_KEY_ID" class="form-control" name="PROXY_KEY_ID">
												<option value="">请选择代理</option>
												<c:forEach var="m" items="${qp }">
													<option value="${m.PROXY_KEY_ID }">${m.PROXY_NAME}(${m.PROXY_SITE})</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label for="QPBEGIN_DATE" class="control-label">开始日期</label> <input id="QPBEGIN_DATE" name="BEGIN_DATE" type="text" placeholder="" class="form-control" maxlength="14" />
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label for="QPEND_DATE" class="control-label">结束日期</label> <input id="QPEND_DATE" name="END_DATE" type="text" placeholder="" class="form-control" maxlength="14" />
										</div>
									</div>


									<div class="col-md-12">
										<div class="form-group">
											<button type="button" class="btn btn-primary" onclick="pullData('qpForm')">马上拉取</button>
										</div>
									</div>

								</div>
							</form>
						</div>
					</div>
				</div>
				<div class="panel panel-yellow">
					<div class="panel-heading">波音EVEB游戏数据</div>
					<div class="panel-body pan">
						<div class="form-body pal">
							<form id="bbin5Form" name="bbin5Form">
								<input type="hidden" name="GAME_ID" value="14" /> <input type="hidden" name="type" value="bbin5" />
								<div class="row">
									<div class="col-md-3">
										<div class="form-group">
											<label for="PROXY_ID" class="control-label">上层帐号</label> <select id="PROXY_ID" class="form-control" name="PROXY_ID">
												<option value="">请选择上层帐号</option>
												<c:forEach var="m" items="${bbin5 }">
													<option value="${m.PROXY_ID }">${m.PROXY_NAME}(${m.PROXY_SITE})</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="col-md-3">
										<div class="form-group">
											<label for="GAME_KIND_ID" class="control-label">游戏种类</label> <select id="GAME_KIND_ID" class="form-control" name="GAME_KIND_ID">
												<option value="">请选择游戏种类</option>
												<c:forEach var="m" items="${bbinKind }">
													<option value="${m.GAME_KIND_ID }">${m.GAME_KIND_NAME}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="col-md-3">
										<div class="form-group">
											<label for="GAME_TYPE_ID" class="control-label">游戏类型</label> <select id="GAME_TYPE_ID" class="form-control" name="GAME_TYPE_ID">
												<option value="">请选择游戏类型</option>
												<c:forEach var="m" items="${gameType }">
													<option value="${m.GAME_TYPE_ID }">${m.GAME_TYPE_NAME}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="col-md-3">
										<div class="form-group">
											<label for="USRE_NAME" class="control-label">会员帐号</label> <input id="USRE_NAME" name="USRE_NAME" type="text" placeholder="" class="form-control" />
										</div>
									</div>
									<div class="col-md-3">
										<div class="form-group">
											<label for="ROUND_DATE5" class="control-label">拉取日期</label> <input id="ROUND_DATE5" name="ROUND_DATE" type="text" placeholder="" class="form-control" maxlength="10" />
										</div>
									</div>
									<div class="col-md-3">
										<div class="form-group">
											<label for="START_TIME5" class="control-label">开始时间</label> <input id="START_TIME5" name="START_TIME" type="text" placeholder="" class="form-control" maxlength="8" value="00:00:00" />
										</div>
									</div>
									<div class="col-md-3">
										<div class="form-group">
											<label for="END_TIME5" class="control-label">结束时间</label> <input id="END_TIME5" name="END_TIME" type="text" placeholder="" class="form-control" maxlength="8" value="23:59:59" />
										</div>
									</div>



									<div class="col-md-12">
										<div class="form-group">
											<button type="button" class="btn btn-primary" onclick="pullData('bbin5Form')">马上拉取</button>
										</div>
									</div>

								</div>
							</form>
						</div>
					</div>
				</div>
				
				


				<div class="panel panel-yellow">
					<div class="panel-heading">德州扑克游戏数据</div>
					<div class="panel-body pan">
						<div class="form-body pal">
							<form id="dzpkForm" name="dzpkForm">
								<input type="hidden" name="GAME_ID" value="15" /> <input type="hidden" name="type" value="dzpk" />
								<div class="row">
									<div class="col-md-4">
										<div class="form-group">
											<label for="PROXY_KEY_ID" class="control-label">所属代理</label> <select id="PROXY_KEY_ID" class="form-control" name="PROXY_KEY_ID">
												<option value="">请选择代理</option>
												<c:forEach var="m" items="${dzpk }">
													<option value="${m.PROXY_KEY_ID }">${m.PROXY_NAME}(${m.PROXY_SITE})</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label for="DZPKBEGIN_DATE" class="control-label">开始日期</label> <input id="DZPKBEGIN_DATE" name="BEGIN_DATE" type="text" placeholder="" class="form-control" maxlength="14" />
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label for="DZPKEND_DATE" class="control-label">结束日期</label> <input id="DZPKEND_DATE" name="END_DATE" type="text" placeholder="" class="form-control" maxlength="14" />
										</div>
									</div>


									<div class="col-md-12">
										<div class="form-group">
											<button type="button" class="btn btn-primary" onclick="pullData('dzpkForm')">马上拉取</button>
										</div>
									</div>

								</div>
							</form>
						</div>
					</div>
				</div>
				


			</div>
		</div>
	</div>
	<!--主体内容结束-->

	<!--添加容器开始-->
	<div id="tipPopup" tabindex="-1" data-backdrop="static" data-keyboard="false" class="modal fade" style="z-index: 99999">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">提示框</h4>
				</div>
				<div class="modal-body" id="dataState">
					<div id="loadData" style="color: red; font-weight: bold;">
						<img src="${pageContext.request.contextPath}/resource/images/loading_bar.gif" style="width: 100%;" />
					</div>
					<div id="loadResult" style="color: green; font-weight: bold; font-size: 18px;"></div>
				</div>
				<div class="modal-footer" id="closeBT">
					<button type="button" data-dismiss="modal" class="btn btn-default">关闭</button>
				</div>
			</div>
		</div>
	</div>
	<!--添加容器结束-->


	<jsp:include page="../script.jsp" />
	<script src="${pageContext.request.contextPath}/resource/js/mobiscroll_date.js" charset="gb2312"></script>
	<script src="${pageContext.request.contextPath}/resource/js/mobiscroll.js"></script>
	<script type="text/javascript">
		$(function() {
			var currYear = (new Date()).getFullYear();
			var opt = {};
			opt.date = {
				preset : 'date'
			};
			opt.datetime = {
				preset : 'datetime'
			};
			opt.time = {
				preset : 'time'
			};
			opt.default1 = {
				theme : 'android-ics light', //皮肤样式
				display : 'modal', //显示方式 
				mode : 'scroller', //日期选择模式
				dateFormat : 'yyyy-mm-dd',
				lang : 'zh',
				showNow : true,
				nowText : "今天",
				startYear : currYear - 50, //开始年份
				endYear : currYear + 10
			//结束年份
			};

			opt.default2 = {
				theme : 'android-ics light', //皮肤样式
				display : 'modal', //显示方式 
				mode : 'scroller', //日期选择模式
				dateFormat : 'yyyy-mm-dd',
				timeFormat : 'HH:ii:ss',
				timeOrder : 'hhiiss',
				lang : 'zh',
				showNow : true,
				nowText : "今天",
				startYear : currYear - 50, //开始年份
				endYear : currYear + 10
			//结束年份
			};
			var optDateTime = $.extend(opt['datetime'], opt['default2']);
			$("#ROUND_DATE").mobiscroll($.extend(opt['date'], opt['default1']));
			$("#ROUND_DATE5").mobiscroll($.extend(opt['date'], opt['default1']));
			$("#PTBEGIN_DATE").mobiscroll(optDateTime).datetime(optDateTime);
			$("#PTEND_DATE").mobiscroll(optDateTime).datetime(optDateTime);
			$("#AVBEGIN_DATE").mobiscroll(optDateTime).datetime(optDateTime);
			$("#AVEND_DATE").mobiscroll(optDateTime).datetime(optDateTime);
			$("#NHQBEGIN_DATE").mobiscroll(optDateTime).datetime(optDateTime);
			$("#NHQEND_DATE").mobiscroll(optDateTime).datetime(optDateTime);
			$("#SABEGIN_DATE").mobiscroll(optDateTime).datetime(optDateTime);
			$("#SAEND_DATE").mobiscroll(optDateTime).datetime(optDateTime);
			$("#MGBEGIN_DATE").mobiscroll(optDateTime).datetime(optDateTime);
			$("#MGEND_DATE").mobiscroll(optDateTime).datetime(optDateTime);
			$("#XCPBEGIN_DATE").mobiscroll(optDateTime).datetime(optDateTime);
			$("#XCPEND_DATE").mobiscroll(optDateTime).datetime(optDateTime);
			$("#TTGBEGIN_DATE").mobiscroll(optDateTime).datetime(optDateTime);
			$("#TTGEND_DATE").mobiscroll(optDateTime).datetime(optDateTime);
			$("#QPBEGIN_DATE").mobiscroll(optDateTime).datetime(optDateTime);
			$("#QPEND_DATE").mobiscroll(optDateTime).datetime(optDateTime);
			$("#DZPKBEGIN_DATE").mobiscroll(optDateTime).datetime(optDateTime);
			$("#DZPKEND_DATE").mobiscroll(optDateTime).datetime(optDateTime);
		});

		var action = "${pageContext.request.contextPath}/manager/pull/pullData.do";
		//删除全部选中项
		function pullData(formName) {
			$("#loadData").show();
			var title = "东方游戏";
			if (formName == "tagForm") {
				title = "TAG游戏";
			} else if (formName == "ibcForm") {
				title = "沙巴体育游戏";
			} else if (formName == "bbinForm") {
				title = "波音OG游戏";
			} else if (formName == "saForm") {
				title = "沙龙游戏";
			} else if (formName == "nhqForm") {
				title = "新环球游戏";
			} else if (formName == "zjForm") {
				title = "ZJ游戏";
			} else if (formName == "avForm") {
				title = "AV游戏";
			} else if (formName == "ptForm") {
				title = "PT游戏";
			} else if (formName == "mgForm") {
				title = "MG游戏";
			} else if (formName == "xcpForm") {
				title = "XCP游戏";
			} else if (formName == "ttgForm") {
				title = "TTG游戏";
			} else if (formName == "qpForm") {
				title = "棋牌游戏";
			} else if (formName == "dzpkForm") {
				title = "德州扑克游戏";
			} else if (formName == "bbin5Form") {
				title = "波音EVEB游戏";
			} 
			var data = $("form[name='" + formName + "']").serialize();
			$.ajax({
						dataType : "json",
						type : "POST",
						url : action,
						data : data,
						success : function(item) {
							$("#loadResult").html(
									"数据拉取结束，本次拉取了(" + item.count + ")条" + title
											+ "数据");
							$("#loadResult").show();
							$("#closeBT").show();
						},
						beforeSend : function() {
							$("#closeBT").hide();
							$("#loadData").show();
							$("#loadResult").hide();
							$("#tipPopup").modal('show');
						},
						complete : function() {
							$("#loadData").hide();
						}
					});
		}
	</script>
</body>
</html>