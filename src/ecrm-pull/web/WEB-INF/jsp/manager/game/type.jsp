<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>游戏类型管理</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resource/css/pagination.css" type="text/css" media="screen" />
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
</head>
<body class="sidebar-default header-fixed">
	<input type="hidden" id="json" name="json" value='${entity.json }' />

	<!--面包削导航开始-->
	<div id="title-breadcrumb-option-demo" class="page-title-breadcrumb">
		<div class="page-header pull-left">
			<div class="page-title">游戏类型管理</div>
		</div>
		<ol class="breadcrumb page-breadcrumb">
			<li><i class="fa fa-home"></i>&nbsp;<a href="#">首页</a>&nbsp;&nbsp;<i class="fa fa-angle-right"></i>&nbsp;&nbsp;</li>

			<li class="active">游戏类型管理</li>
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
							
							<br />
							<!-- 操作列表开始 -->
							<a href="javascript:showAdd();" class="btn btn-grey"><i class="fa fa-plus"></i>&nbsp;&nbsp;添加</a>
							<a href="javascript:showEdit();" class="btn btn-grey"><i class="fa fa-edit"></i>&nbsp;&nbsp;编辑</a>
							<a href="javascript:del()" class="btn btn-primary"><i class="fa fa-trash-o"></i>&nbsp;&nbsp;删除</a><br />
							<!-- 操作列表结束 -->
							<div id="no-more-tables" style="margin-top:5px;">
								<display:table id="row" name="list" class="table table-bordered table-striped table-condensed table-advanced cf" partialList="true" size="resultSize"
									pagesize="${pageSize}" requestURI="${pageContext.request.contextPath}/manager/type/list.do">
									<display:column title="<input type='checkbox'  class='checkall' />" style="text-align:center" headerClass="ct">
										<input type="checkbox" value="${row.GAME_TYPE_ID}" name="answer">
									</display:column>
									<display:column property="GAME_TYPE_ID" title="编号" scope="编号" />
									<display:column property="GAME_TYPE_NAME" title="名称" scope="名称" />
									<display:column property="GAME_NAME" title="所属游戏" scope="所属游戏" />
									<display:column property="GAME_TYPE_DESC" title="描述" scope="描述"/>									
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
					<h4 class="modal-title">添加游戏类型</h4>
				</div>
				<div class="modal-body">
					<form id="addForm" name="addForm">
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label for="GAME_TYPE_NAME" class="control-label">名称</label> <input id="GAME_TYPE_NAME" name="GAME_TYPE_NAME" type="text" placeholder="" class="form-control" class="form-control" emsg="请输入名称" />
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<label for="GAME_ID" class="control-label">所属游戏</label> <select id="GAME_ID" class="form-control" name="GAME_ID">
										<c:forEach var="m" items="${game }">
											<option value="${m.GAME_ID }">${m.GAME_NAME}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<label for="GAME_TYPE_DESC" class="control-label">描述</label><input id="GAME_TYPE_DESC" name="GAME_TYPE_DESC" type="text" placeholder="" class="form-control" class="form-control" emsg="请输入描述" />
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
					<h4 class="modal-title">编辑游戏类型</h4>
				</div>
				<div class="modal-body">
					<form id="editForm" name="editForm">
						<input type="hidden" id="editId" name="GAME_TYPE_ID" />
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label for="eGAME_TYPE_NAME" class="control-label">名称</label> <input id="eGAME_TYPE_NAME" name="GAME_TYPE_NAME" type="text" placeholder="" class="form-control" />
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<label for="eGAME_ID" class="control-label">所属游戏</label> <select id="eGAME_ID" class="form-control" name="GAME_ID">
										<c:forEach var="m" items="${game }">
											<option value="${m.GAME_ID }">${m.GAME_NAME}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<label for="eGAME_TYPE_DESC" class="control-label">描述</label><input id="eGAME_TYPE_DESC" name="GAME_TYPE_DESC" type="text" placeholder="" class="form-control" />
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
	<!--更新容器结束-->

	<jsp:include page="../script.jsp" />
	<!--更新容器结束-->
	<script type="text/javascript">
		
		$('td').each(function() {
			$(this).attr("data-title", $(this).attr("scope"));
		});
		var delAction = "${pageContext.request.contextPath}/manager/type/delete.do";
		var addAction = "${pageContext.request.contextPath}/manager/type/add.do";
		var editAction = "${pageContext.request.contextPath}/manager/type/update.do";
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
				var power = getJsonObject("json","GAME_TYPE_ID",value);			
				$("#editId").val(power["GAME_TYPE_ID"]);
				$("#eGAME_TYPE_DESC").val(power["GAME_TYPE_DESC"]);
				$("#eGAME_TYPE_NAME").val(power["GAME_TYPE_NAME"]);
				$("#eGAME_ID").attr("value",power["GAME_ID"]);
				$("#editPopup").modal('show');//toggle,show,hide三种方式
			}
		}

		//添加操作
		function add() {
			if (!empty("GAME_TYPE_NAME")) {
				return false;
			}
			insertAndUpadte("addForm",addAction);	
		}
		//更新操作
		function edit() {
			insertAndUpadte("editForm",editAction);
		}
	</script>
</body>
</html>