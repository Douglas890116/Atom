<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>角色管理</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resource/css/pagination.css" type="text/css" media="screen" />
<jsp:include page="../../head.jsp" />
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/resource/vendors/select2/select2-madmin.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/resource/vendors/bootstrap-select/bootstrap-select.min.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/resource/vendors/multi-select/css/multi-select-madmin.css">
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
			<div class="page-title">角色管理</div>
		</div>
		<ol class="breadcrumb page-breadcrumb">
			<li><i class="fa fa-home"></i>&nbsp;<a href="${pageContext.request.contextPath}/manager/system/index.do">首页</a>&nbsp;&nbsp;<i class="fa fa-angle-right"></i>&nbsp;&nbsp;</li>

			<li class="active">角色管理</li>
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
									pagesize="${pageSize}" requestURI="${pageContext.request.contextPath}/manager/sys/toRole.do">
									<display:column title="<input type='checkbox'  class='checkall' />" style="text-align:center" headerClass="ct">
										<input type="checkbox" value="${row.ROLE_ID}" name="answer">
									</display:column>
									<display:column property="ROLE_ID" title="编号" scope="编号" />
									<display:column property="ROLE_NAME" title="名称" scope="名称" />
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
	<!--添加容器开始-->
	<div id="addPopup" tabindex="-1" data-backdrop="static" data-keyboard="false" class="modal fade" style="z-index: 99999">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
					<h4 class="modal-title">添加角色</h4>
				</div>
				<div class="modal-body">
					<form id="addForm" name="addForm">
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label for="ROLE_NAME" class="control-label">名称</label> <input id="ROLE_NAME" name="ROLE_NAME" type="text" placeholder="" class="form-control" emsg="请输入名称" />
								</div>
							</div>
							 <div class="col-md-12">
							 	<label for="ROLE_NAME" class="control-label">权限操作</label>
	                             <select id="ROLE_ACTION" multiple="multiple" name="ROLE_ACTION" emsg="请选择权限操作">
	                             <c:forEach var="m" items="${modular}">
	                                 <optgroup label="${m.MODULAR_NAME}">
	                                 	<c:forEach var="p" items="${m.power}">
	                                     <option value="${p.POWER_ID }">${p.POWER_NAME }</option>
	                                     </c:forEach>
	                                 </optgroup>
	                             </c:forEach>                             
	                             </select>
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
					<h4 class="modal-title">编辑角色</h4>
				</div>
				<div class="modal-body">
					<form id="editForm" name="editForm">
					<input type="hidden" id="editId" name="ROLE_ID" />
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label for="eROLE_NAME" class="control-label">名称</label> <input id="eROLE_NAME" name="ROLE_NAME" type="text" placeholder="" class="form-control" emsg="请输入名称" />
								</div>
							</div>
							 <div class="col-md-12">
							 	<label for="eROLE_NAME" class="control-label">权限操作</label>
	                             <select id="eROLE_ACTION" multiple="multiple" name="ROLE_ACTION" emsg="请选择权限操作">
	                             <c:forEach var="m" items="${modular}">
	                                 <optgroup label="${m.MODULAR_NAME}">
	                                 	<c:forEach var="p" items="${m.power}">
	                                     <option value="${p.POWER_ID }">${p.POWER_NAME }</option>
	                                     </c:forEach>
	                                 </optgroup>
	                             </c:forEach>                             
	                             </select>
	                         </div>
						</div>						
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" data-dismiss="modal" class="btn btn-default">取消</button>
					<button type="button" class="btn btn-primary" onclick="edit()">保存更新</button>
				</div>
			</div>
		</div>
	</div>
	<!--更新容器结束-->
	
	<jsp:include page="../../script.jsp" />
	<!--LOADING SCRIPTS FOR PAGE-->
	<script src="${pageContext.request.contextPath }/resource/vendors/select2/select2.min.js"></script>
	<script src="${pageContext.request.contextPath }/resource/vendors/bootstrap-select/bootstrap-select.min.js"></script>
	<script src="${pageContext.request.contextPath }/resource/vendors/multi-select/js/jquery.multi-select.js"></script>	
	
	<!--更新容器结束-->
	<script type="text/javascript">
		$('#ROLE_ACTION').multiSelect({ selectableOptgroup: true});
		$('td').each(function() {
			$(this).attr("data-title", $(this).attr("scope"));
		});
		var delAction = "${pageContext.request.contextPath}/manager/system/role/delete.do";
		var addAction = "${pageContext.request.contextPath}/manager/system/role/add.do";
		var editAction = "${pageContext.request.contextPath}/manager/system/role/update.do";
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
				var role = getJsonObject("json","ROLE_ID",value);
				var action = role["ROLE_ACTION"];
				var actions = action.split(",");
				var len = actions.length;
				$('#eROLE_ACTION').multiSelect('deselect_all');
				$("#eROLE_ACTION option").each(function(){
					//$(this).removeAttr("selected");
					for(var i=0;i<len;i++){
					   if($(this).val()==actions[i]){
						$(this).attr("selected","selected");
					   }
					}
			   });				
				
				$("#eROLE_NAME").val(role["ROLE_NAME"]);
				$("#editId").val(role["ROLE_ID"]);
				$('#eROLE_ACTION').multiSelect({ selectableOptgroup: true});
				$("#editPopup").modal('show');//toggle,show,hide三种方式
			}
		}

		//添加操作
		function add() {
			if (!empty("ROLE_NAME")) {
				return false;
			}
			if (!empty("ROLE_ACTION")) {
				return false;
			}
			var data = "ROLE_NAME="+$("#ROLE_NAME").val()+"&ROLE_ACTION="+$("#ROLE_ACTION").val();
			upadte("addForm",addAction,data);	
		}
		
		/*
		 * 更新操作(包括添加和更新数据)
		 * @param formName 待更新表单名称
		 * @param url 提交的接口链接
		 * @param data 数据
		 */
		function upadte(formName,url,data) {		
			$.ajax({
				dataType : "json",
				type : "POST",
				url : url,
				data : data,
				success : function(item) {
					alert(item.msg);
					window.location.reload();
				}
			});
		}
		
		//更新操作
		function edit() {
			var data = "ROLE_ID="+$("#editId").val()+"&ROLE_NAME="+$("#eROLE_NAME").val()+"&ROLE_ACTION="+$("#eROLE_ACTION").val();
			upadte("editForm",editAction,data);
		}	
	</script>
</body>
</html>
