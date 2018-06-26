<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>修改密码</title>
<jsp:include page="../head.jsp" />
</head>
<body class="sidebar-default header-fixed">
	<input type="hidden" id="json" name="json" value='${entity.json }' />

	<!--面包削导航开始-->
	<div id="title-breadcrumb-option-demo" class="page-title-breadcrumb">
		<div class="page-header pull-left">
			<div class="page-title">修改密码</div>
		</div>
		<ol class="breadcrumb page-breadcrumb">
			<li><i class="fa fa-home"></i>&nbsp;<a href="${pageContext.request.contextPath}/manager/system/index.do">首页</a>&nbsp;&nbsp;<i class="fa fa-angle-right"></i>&nbsp;&nbsp;</li>

			<li class="active">修改密码</li>
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
						<jsp:include page="../script.jsp" />
						<form>
							<input type="hidden" name="ADMIN_ID" id="ADMIN_ID" value="${sessionAdmin.ADMIN_ID}" />
							<div class="form-body pal">

								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<label for="ADMIN_PWD" class="control-label">旧密码</label> <input id="ADMIN_PWD" name="ADMIN_PWD" type="text" placeholder="" class="form-control" maxlength="30" emsg="请输入旧密码！" />
										</div>
									</div>

								</div>
								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<label for="ADMIN_PWD1" class="control-label">新密码</label> <input id="ADMIN_PWD1" name="ADMIN_PWD1" type="text" placeholder="" class="form-control" maxlength="30" emsg="请输入新密码！" />
										</div>
									</div>

								</div>
								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<label for="ADMIN_PWD2" class="control-label">再一次密码</label> <input id="ADMIN_PWD2" name="ADMIN_PWD2" type="text" placeholder="" class="form-control" maxlength="30" emsg="请输入再一次密码！" />
										</div>
									</div>

								</div>
							</div>
							<div class="form-actions text-left pal">
								<button type="button" class="btn btn-primary" onclick="tj()">提交</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--主体内容结束-->

	<jsp:include page="../script.jsp" />
	<script type="text/javascript">	
		function tj(){
			if(!empty("ADMIN_PWD")){
				return false;
			}
			if(!empty("ADMIN_PWD1")){
				return false;
			}		
			if(!empty("ADMIN_PWD2")){
				return false;
			}
			
			var data = $("form").serialize();
			$.ajax({
				dataType : "json",
				type : "POST",
				url : "${pageContext.request.contextPath}/manager/system/updatePwd.do",
				data : data,
				success : function(item) {
					alert(item.msg);	
					if(item.result_code=="0"){
						window.location.href = "${pageContext.request.contextPath}/manager/system/logout.do";
					}else{
						window.location.reload();
					}
				}
			});		
		}
	</script>
</body>
</html>
