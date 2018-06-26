<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>权限管理</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resource/css/pagination.css" type="text/css" media="screen" />
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
			<div class="page-title">权限管理</div>
		</div>
		<ol class="breadcrumb page-breadcrumb">
			<li><i class="fa fa-home"></i>&nbsp;<a href="${pageContext.request.contextPath}/manager/system/index.do">首页</a>&nbsp;&nbsp;<i class="fa fa-angle-right"></i>&nbsp;&nbsp;</li>

			<li class="active">权限管理</li>
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
									pagesize="${pageSize}" requestURI="${pageContext.request.contextPath}/manager/system/power/list.do">
									<display:column title="<input type='checkbox'  class='checkall' />" style="text-align:center" headerClass="ct">
										<input type="checkbox" value="${row.POWER_ID}" name="answer">
									</display:column>
									<display:column property="POWER_ID" title="编号" scope="编号" />
									<display:column property="POWER_NAME" title="名称" scope="名称" />
									<display:column property="MODULAR_NAME" title="所属模块" scope="所属模块" />
									<display:column title="所属父权限" scope="所属父权限">
									${row.PARENT_NAME==null ? "顶级权限" : row.PARENT_NAME }
									</display:column>
									<display:column property="POWER_ACTION" title="操作路径" scope="操作路径"/>
									<display:column title="是否显示" scope="是否显示">
								    	${row.POWER_DISPLAY=="0" ? "显示" : "隐藏"}
								    </display:column>
									<display:column property="POWER_CLASSNAME" title="样式名称" scope="样式名称" />
									<display:column title="图标" scope="图标">
									<i class="fa ${row.POWER_CLASSNAME }"></i>
									</display:column>
									
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
					<h4 class="modal-title">添加权限</h4>
				</div>
				<div class="modal-body">
					<form id="addForm" name="addForm">
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label for="POWER_NAME" class="control-label">名称</label> <input id="POWER_NAME" name="POWER_NAME" type="text" placeholder="" class="form-control" class="form-control" emsg="请输入名称" />
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<label for="MODULAR_ID" class="control-label">所属模块</label> <select id="MODULAR_ID" class="form-control" name="MODULAR_ID">
										<c:forEach var="m" items="${modular }">
											<option value="${m.MODULAR_ID }">${m.MODULAR_NAME}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<label for="POWER_PARENT" class="control-label">所属父权限</label> <select id="POWER_PARENT" class="form-control" name="POWER_PARENT">
										<option value="0">顶级权限</option>
										<c:forEach var="m" items="${powers }">									
											<option value="${m.POWER_ID }">${m.POWER_NAME}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<label for="POWER_DISPLAY" class="control-label">是否可见</label> <select id="POWER_DISPLAY" class="form-control" name="POWER_DISPLAY">
										<option value="1">隐藏</option>
										<option value="0">显示</option>
									</select>
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<label for="POWER_ACTION" class="control-label">操作路径</label><input id="POWER_ACTION" name="POWER_ACTION" type="text" placeholder="" class="form-control" class="form-control" emsg="请输入操作路径" />
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<label for="POWER_CLASSNAME" class="control-label">图标样式</label><input id="POWER_CLASSNAME" name="POWER_CLASSNAME" type="text" placeholder="" class="form-control" emsg="请输入图标样式" />
								</div>
							</div>
							<div class="col-md-12">
								<a href="${pageContext.request.contextPath}/icons.jsp" target="_blank">点击此链接打开ICONS列表页面，然后复制名称即可</a>
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
					<h4 class="modal-title">编辑权限</h4>
				</div>
				<div class="modal-body">
					<form id="editForm" name="editForm">
						<input type="hidden" id="editId" name="POWER_ID" />
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label for="ePOWER_NAME" class="control-label">名称</label> <input id="ePOWER_NAME" name="POWER_NAME" type="text" placeholder="" class="form-control" />
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<label for="eMODULAR_ID" class="control-label">所属模块</label> <select id="eMODULAR_ID" class="form-control" name="MODULAR_ID">
										<c:forEach var="m" items="${modular }">
											<option value="${m.MODULAR_ID }">${m.MODULAR_NAME}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<label for="ePOWER_PARENT" class="control-label">所属父权限</label> <select id="ePOWER_PARENT" class="form-control" name="POWER_PARENT">
										<option value="0">顶级权限</option>
										<c:forEach var="m" items="${powers }">									
											<option value="${m.POWER_ID }">${m.POWER_NAME}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<label for="ePOWER_DISPLAY" class="control-label">是否可见</label> <select id="ePOWER_DISPLAY" class="form-control" name="POWER_DISPLAY">
										<option value="1">隐藏</option>
										<option value="0">显示</option>
									</select>
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<label for="ePOWER_ACTION" class="control-label">操作路径</label><input id="ePOWER_ACTION" name="POWER_ACTION" type="text" placeholder="" class="form-control" />
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<label for="ePOWER_CLASSNAME" class="control-label">图标样式</label><input id="ePOWER_CLASSNAME" name="POWER_CLASSNAME" type="text" placeholder="" class="form-control" />
								</div>
							</div>
							<div class="col-md-12">
								<a href="${pageContext.request.contextPath}/icons.jsp" target="_blank">点击此链接打开ICONS列表页面，然后复制名称即可</a>
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

	<jsp:include page="../../script.jsp" />
	<!--更新容器结束-->
	<script type="text/javascript">
		
		$('td').each(function() {
			$(this).attr("data-title", $(this).attr("scope"));
		});
		var delAction = "${pageContext.request.contextPath}/manager/system/power/delete.do";
		var addAction = "${pageContext.request.contextPath}/manager/system/power/add.do";
		var editAction = "${pageContext.request.contextPath}/manager/system/power/update.do";
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
				var power = getJsonObject("json","POWER_ID",value);			
				$("#editId").val(power["POWER_ID"]);
				$("#ePOWER_ACTION").val(power["POWER_ACTION"]);
				$("#ePOWER_NAME").val(power["POWER_NAME"]);
				$("#ePOWER_CLASSNAME").val(power["POWER_CLASSNAME"]);
				$("#eMODULAR_ID").attr("value",power["MODULAR_ID"]);
				$("#ePOWER_DISPLAY").attr("value",power["POWER_DISPLAY"]);
				$("#ePOWER_PARENT").attr("value",power["POWER_PARENT"]);
				$("#editPopup").modal('show');//toggle,show,hide三种方式
			}
		}

		//添加操作
		function add() {
			if (!empty("POWER_NAME")) {
				return false;
			}
			if (!empty("POWER_NAME")) {
				return false;
			}
			if (!empty("POWER_CLASSNAME")) {
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
