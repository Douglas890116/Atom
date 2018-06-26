<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>代理管理</title>
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
			<div class="page-title">代理管理</div>
		</div>
		<ol class="breadcrumb page-breadcrumb">
			<li><i class="fa fa-home"></i>&nbsp;<a href="#">首页</a>&nbsp;&nbsp;<i class="fa fa-angle-right"></i>&nbsp;&nbsp;</li>

			<li class="active">代理管理</li>
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
									pagesize="${pageSize}" requestURI="${pageContext.request.contextPath}/manager/proxy/list.do">
									<display:column title="<input type='checkbox'  class='checkall' />" style="text-align:center" headerClass="ct">
										<input type="checkbox" value="${row.PROXY_ID}" name="answer">
									</display:column>
									<display:column property="PROXY_ID" title="编号" scope="编号" />
									<display:column property="PROXY_NAME" title="名称" scope="名称" />
									<display:column title="网站" scope="网站">
									${empty row.PROXY_SITE ? "无" : row.PROXY_SITE}
									</display:column>
									<display:column title="KEY1" scope="KEY1">
									${empty row.PROXY_KEY1 ? "无" : row.PROXY_KEY1}
									</display:column>
									<display:column title="KEY2" scope="KEY2">
									${empty row.PROXY_KEY2 ? "无" : row.PROXY_KEY2}
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
					<h4 class="modal-title">添加代理</h4>
				</div>
				<div class="modal-body">
					<form id="addForm" name="addForm">
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label for="PROXY_NAME" class="control-label">名称</label> <input id="PROXY_NAME" name="PROXY_NAME" type="text" placeholder="" class="form-control" emsg="请输入名称" />
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<label for="PROXY_SITE" class="control-label">网站（选 填）</label> <input id="PROXY_SITE" name="PROXY_SITE" type="text" placeholder="" class="form-control" />
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<label for="PROXY_KEY1" class="control-label">KEY1（选 填）</label> <input id="PROXY_KEY1" name="PROXY_KEY1" type="text" placeholder="" class="form-control" />
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<label for="PROXY_KEY2" class="control-label">KEY2（选 填）</label> <input id="PROXY_KEY2" name="PROXY_KEY2" type="text" placeholder="" class="form-control" />
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<label for="PROXY_CODE" class="control-label">代理编码（不填写则系统自动生成6位的编码）</label> <input id="PROXY_CODE" name="PROXY_CODE" type="text" placeholder="" class="form-control" />
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
					<h4 class="modal-title">编辑代理</h4>
				</div>
				<div class="modal-body">
					<form id="editForm" name="editForm">
						<input type="hidden" id="editId" name="PROXY_ID" />
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label for="ePROXY_NAME" class="control-label">名称</label> <input id="ePROXY_NAME" name="PROXY_NAME" type="text" placeholder="" class="form-control" />
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<label for="ePROXY_SITE" class="control-label">网站</label> <input id="ePROXY_SITE" name="PROXY_SITE" type="text" placeholder="" class="form-control" />
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<label for="ePROXY_KEY1" class="control-label">KEY1（选 填）</label> <input id="ePROXY_KEY1" name="PROXY_KEY1" type="text" placeholder="" class="form-control" />
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<label for="ePROXY_KEY2" class="control-label">KEY2（选 填）</label> <input id="ePROXY_KEY2" name="PROXY_KEY2" type="text" placeholder="" class="form-control" />
								</div>
							</div>
							<div class="col-md-12">
								<div class="form-group">
									<label for="ePROXY_CODE" class="control-label">代理编码（不填写则系统自动生成6位的编码）</label> <input id="ePROXY_CODE" name="PROXY_CODE" type="text" placeholder="" class="form-control" />
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
		var delAction = "${pageContext.request.contextPath}/manager/proxy/delete.do";
		var addAction = "${pageContext.request.contextPath}/manager/proxy/add.do";
		var editAction = "${pageContext.request.contextPath}/manager/proxy/update.do";
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
				var proxy = getJsonObject("json","PROXY_ID",value);
				$("#ePROXY_NAME").val(proxy["PROXY_NAME"]);
				$("#ePROXY_SITE").val(proxy["PROXY_SITE"]);
				$("#ePROXY_KEY1").val(proxy["PROXY_KEY1"]);
				$("#ePROXY_KEY2").val(proxy["PROXY_KEY2"]);
				$("#ePROXY_CODE").val(proxy["PROXY_CODE"]);
				$("#editId").val(proxy["PROXY_ID"]);
				$("#editPopup").modal('show');//toggle,show,hide三种方式
			}
		}

		//添加操作
		function add() {
			if (!empty("PROXY_NAME")) {
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
