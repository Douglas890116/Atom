<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>管理员管理</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resource/css/pagination.css" type="text/css" media="screen" />
<link href="${pageContext.request.contextPath }/resource/css/mobiscroll.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath }/resource/css/mobiscroll_date.css" rel="stylesheet" />
<jsp:include page="../../head.jsp" />
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
</head>
<body class="sidebar-default header-fixed">
	<input type="hidden" id="json" name="json" value='${entity.json }' />

	<!--面包削导航开始-->
	<div id="title-breadcrumb-option-demo" class="page-title-breadcrumb">
		<div class="page-header pull-left">
			<div class="page-title">管理员管理</div>
		</div>
		<ol class="breadcrumb page-breadcrumb">
			<li><i class="fa fa-home"></i>&nbsp;<a href="${pageContext.request.contextPath}/manager/system/index.do">首页</a>&nbsp;&nbsp;<i class="fa fa-angle-right"></i>&nbsp;&nbsp;</li>

			<li class="active">管理员管理</li>
		</ol>
		<div class="clearfix"></div>

	</div>
	<!--面包削导航结束-->

	<!--主体内容开始-->
	<div class="page-content">
		<div id="table-action" class="row">
			<div class="col-lg-12">
				<div id="tableactionTabContent" class="tab-content">
					<div id="table-table-tab" class="tab-pane fade in active">
						<div class="panel-body">
							<!--条件检索开始-->
							<form id="sf" action="${pageContext.request.contextPath}/manager/system/admin/list.do" method="post">

								<div class="row">
									<div class="col-md-2">
										<select data-style="btn-grey" class="selectpicker form-control" id="ROLE_ID" name="ROLE_ID">
											<option value="">所属角色</option>
											<c:forEach var="me" items="${role }">
												<option value="${me.ROLE_ID }" ${me.ROLE_ID==entity.ROLE_ID ? "selected" : "" }>${me.ROLE_NAME}</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-md-2">
										<input type="text" id="sADMIN_NAME" name="ADMIN_NAME" placeholder="名称" class="form-control" value="${entity.ADMIN_NAME }" />

									</div>

									<div class="col-md-2">
										<input type="text" id="BEGIN_DATE" name="BEGIN_DATE" placeholder="开始日期" class="form-control" value="${entity.BEGIN_DATE }" />

									</div>
									<div class="col-md-2">
										<input type="text" id="END_DATE" name="END_DATE" placeholder="结束日期" class="form-control" value="${entity.END_DATE }" />

									</div>
									<div class="col-md-1">
										<a href="javascript:search();" class="btn btn-success"> <i class="fa fa-search"></i> &nbsp; 搜索
										</a>
									</div>
								</div>
							</form>
							<br />
							<!-- 操作列表开始 -->
							<a href="javascript:showAdd();" class="btn btn-grey"><i class="fa fa-plus"></i>&nbsp;&nbsp;添加</a> <a href="javascript:showEdit();" class="btn btn-grey"><i class="fa fa-edit"></i>&nbsp;&nbsp;编辑</a>
							<a href="javascript:del()" class="btn btn-primary"><i class="fa fa-trash-o"></i>&nbsp;&nbsp;删除</a><br />
							<!-- 操作列表结束 -->
							<div id="no-more-tables" style="margin-top: 5px;">
								<display:table id="row" name="list" class="table table-bordered table-striped table-condensed table-advanced cf" partialList="true" size="resultSize" pagesize="${pageSize}"
									requestURI="${pageContext.request.contextPath}/manager/system/admin/list.do">
									<display:column title="<input type='checkbox'  class='checkall' />" style="text-align:center" headerClass="ct">
										<input type="checkbox" value="${row.ADMIN_ID}" name="answer">
									</display:column>
									<display:column property="ADMIN_ID" title="编号" scope="编号" />
									<display:column property="ADMIN_NAME" title="名称" scope="名称" />
									<display:column property="ROLE_NAME" title="所属角色" scope="所属角色" />
									<display:column property="CREATE_DATE" title="创建日期" scope="创建日期" />
								</display:table>
							</div>
						</div>
						<!--响应式表格容器结束-->
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--主体内容结束-->

	<!--添加容器开始-->
	<div id="addPopup" tabindex="-1" data-backdrop="static" data-keyboard="false" class="modal fade" style="z-index: 99999">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
					<h4 class="modal-title">添加管理员</h4>
				</div>
				<div class="modal-body">
					<form id="addForm" name="addForm">
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label for="ADMIN_NAME" class="control-label">名称</label> <input id="ADMIN_NAME" name="ADMIN_NAME" type="text" placeholder="" class="form-control" emsg="请输入名称" />
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label for="ADMIN_PWD" class="control-label">密码</label> <input id="ADMIN_PWD" name="ADMIN_PWD" type="text" placeholder="" class="form-control" emsg="请输入密码" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label for="ADMIN_PWD1" class="control-label">再次密码</label> <input id="ADMIN_PWD1" name="ADMIN_PWD1" type="text" placeholder="" class="form-control" emsg="请输入再次密码" />
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label for="ROLE_ID" class="control-label">所属角色</label> <select id="ROLE_ID" class="form-control" name="ROLE_ID">
										<c:forEach var="m" items="${role }">
											<option value="${m.ROLE_ID }">${m.ROLE_NAME}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" data-dismiss="modal" class="btn btn-default">取消</button>
					<button type="button" class="btn btn-primary" onclick="add()">保存添加</button>
				</div>
			</div>
		</div>
	</div>
	<!--添加容器结束-->

	<!--更新容器开始-->
	<div id="editPopup" tabindex="-1" data-backdrop="static" data-keyboard="false" class="modal fade" style="z-index: 99999">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
					<h4 class="modal-title">编辑管理员</h4>
				</div>
				<div class="modal-body">
					<form id="editForm" name="editForm">
						<input type="hidden" id="editId" name="ADMIN_ID" />
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label for="ADMIN_NAME" class="control-label">名称</label> <input id="editName" name="ADMIN_NAME" type="text" placeholder="" class="form-control" />
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label for="ROLE_ID" class="control-label">所属角色</label> <select id="editRoleId" class="form-control" name="ROLE_ID">
										<c:forEach var="me" items="${role }">
											<option value="${me.ROLE_ID }">${me.ROLE_NAME}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" data-dismiss="modal" class="btn btn-default">取消</button>
					<button type="button" class="btn btn-primary" onclick="edit()">保存修改</button>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="../../script.jsp" />	
	  <script src="${pageContext.request.contextPath}/resource/js/mobiscroll_date.js" charset="gb2312"></script>
	  <script src="${pageContext.request.contextPath}/resource/js/mobiscroll.js"></script>
	<!--更新容器结束-->
	<script type="text/javascript">
		$(function () {
		  var currYear = (new Date()).getFullYear();  
		  var opt={};
		  opt.date = {preset : 'date'};
		  opt.datetime = {preset : 'datetime'};
		  opt.time = {preset : 'time'};
		  opt.defaults = {
		    theme: 'android-ics light', //皮肤样式
		    display: 'modal', //显示方式 
		    mode: 'scroller', //日期选择模式
		    dateFormat: 'yyyy-mm-dd',
		    lang: 'zh',
		    showNow: true,
		    nowText: "今天",
		    startYear: currYear - 50, //开始年份
		    endYear: currYear + 10 //结束年份
		  };

		  $("#END_DATE").mobiscroll($.extend(opt['date'], opt['defaults']));
		  
		   $("#BEGIN_DATE").mobiscroll($.extend(opt['date'], opt['defaults']));

		});
		$('td').each(function() {
			$(this).attr("data-title", $(this).attr("scope"));
		});
		var delAction = "${pageContext.request.contextPath}/manager/system/admin/delete.do";
		var addAction = "${pageContext.request.contextPath}/manager/system/admin/add.do";
		var editAction = "${pageContext.request.contextPath}/manager/system/admin/update.do";
		//删除全部选中项
		function del() {
			delAll(delAction);
		}
		//弹出编辑框
		function showAdd() {
			$("#addForm")[0].reset();
			$("#addPopup").modal('show');//toggle,show,hide三种方式
		}
		//弹出编辑框
		function showEdit() {
			$("#editForm")[0].reset();
			var len = getCheckCount("answer");
			if (len == 0) {
				alert("请选择要编辑的数据!");
			} else if (len > 1) {
				alert("请选择单条数据!");
			} else {
				var value = getCheckVlaue("answer");
				var admin = getJsonObject("json", "ADMIN_ID", value);
				$("#editName").val(admin["ADMIN_NAME"]);
				$("#editId").val(admin["ADMIN_ID"]);
				$("#editRoleId").attr("value", admin["ROLE_ID"]);
				$("#editPopup").modal('show');//toggle,show,hide三种方式
			}
		}

		//添加操作
		function add() {
			if (!empty("ADMIN_NAME")) {
				return false;
			}
			if (!empty("ADMIN_PWD")) {
				return false;
			}
			if (!empty("ADMIN_PWD1")) {
				return false;
			}
			insertAndUpadte("addForm", addAction);
		}
		
		//更新操作
		function edit() {
			insertAndUpadte("editForm", editAction);
		}

		function search() {
			$("#sf").submit();
		}
	</script>
</body>
</html>
