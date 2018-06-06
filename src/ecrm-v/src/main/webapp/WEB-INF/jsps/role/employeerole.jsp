<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<link href="http://g.alicdn.com/bui/bui/1.1.21/css/bs3/dpl.css" rel="stylesheet" />
<body>
<div class="demo-content" style="margin: 27px;">
	
	<!-- 表单页 ================================================== --> 
	<div class="row">
		<form id="objForm">
			<div class="control-group">
			<label class="control-label">用户名称：</label>
			<div class="controls">
				<input type="text" name="loginaccount" value="${loginaccount}" data-rules="{required:true}" disabled="disabled" class="input-normal control-text"/>
				<input type="hidden" id="employeecode" value="${employeecode}"/>
			</div>
			</div>
			<hr/>
			<div class="control-group">
				<c:if test="${rolelist != null && fn:length(rolelist) > 0}">
				<div class="row show-grid">
				<h2> 角 色 列 表 </h2>
				<c:forEach items="${rolelist}" var="data" varStatus="i">
					<%-- 下面这个span5的样式不要随意修改，如果要修改，记得修改下面的JS --%>
					<div class="span5">
					<a href="#" style="color: #000000;">
						<ul class="breadcrumb" ${data.rolestatus == 1 ? 'style="background-color: #FFFFE0"' : ''}>
						<li class="active"><input type="checkbox" name="rolecodes" value="${data.rolecode}" " ${data.rolestatus == 1 ? 'checked="checked"' : ''}/> ${data.rolename}</li>
						</ul>
					</a>
					</div>
				</c:forEach>
				</div>
				</c:if>
			</div>
			<div class="row actions-bar">	   
				<div class="form-actions span13 offset3">
					<button type="button" id="J_Form_Submit" class="button button-primary">保存</button>
					<button class="button button-danger" type="button" onclick="top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">返回列表</button>
				</div>
			</div> 
		</form>
	</div>
	<script src="${statics }/js/jquery-1.8.1.min.js"></script>
	<script src="${statics }/js/bui.js"></script> 
	<script src="${statics }/js/config.js"></script>
	<script type="text/javascript">
	
		$("div.span5").click(function() {
			var obj = $(this).find(":checkbox");
			if($(obj).is(':checked')){
				$(obj).prop("checked", false);
				$(this).find("ul").css('background-color', '#FFFFFF');
			} else {
				$(obj).prop("checked", true);
				$(this).find("ul").css('background-color', '#FFFFE0');
			}
		});
		
		$("#J_Form_Submit").click(function(){
			var rolecodes = "";
			$.each($("div.span5"), function(){
				var rolecode = $(this).find(":checkbox").val();
				var checked  = $(this).find(":checkbox").attr("checked");
				if (checked) rolecodes += "," + rolecode;
			});
			rolecodes = rolecodes.length > 1 ? rolecodes.substr(1) : "";
			$.ajax({
				url  : "${ctx}/EmployeeRole/auth",
				type : "post",
				data : {'rolecodes' : rolecodes, 'employeecode' : $("#employeecode").val()},
				dataType : "json",
				async: false,
				success : function(data) {
					if(data.status == 1){
						BUI.Message.Show({
							title : '提示框',
							msg : data.message,
							icon : 'success',
							buttons : [
								{text:'继续修改',elCls : 'button button-wran',handler : function(){ this.close();}},
								{text:'返回列表',elCls : 'button button-success',handler : function(){ top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});}}
							]
						});
					}else{
						BUI.Message.Alert(data.message,'warning');
					}
				},
				error : function() {
					BUI.Message.Alert("系统出现错误，请联系管理员 !",'error');
				}
			});
		});
	</script>
<!-- script end -->
</div>
</body>
</html>