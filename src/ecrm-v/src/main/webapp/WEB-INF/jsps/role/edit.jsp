<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<!DOCTYPE html>
<html>
   <head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   <title>用户权限管理</title>
   <!-- 此文件为了显示Demo样式，项目中不需要引入 -->
<link href="${statics}/css/bs3/bui-min.css" rel="stylesheet" />
<link href="${statics}/css/bs3/dpl-min.css" rel="stylesheet" /> 
<link href="${statics}/css/custom/common.css" rel="stylesheet" /> 
<style type="text/css">
.bui-grid-table tr:hover{
	background-color: #EBEFF2;
}
</style>
</head>
<body>
<div class="demo-content" style="margin: 27px;">
	
	<!-- 表单页 ================================================== --> 
	<div class="row">
	<div class="span18">
		<form action="${ctx}/PermissionRole/update" aria-pressed="false" aria-disabled="false" id="objForm" class="form-horizontal bui-form bui-form-field-container" method="post" >
			<div class="control-group">
				<label class="control-label">角色名称：</label>
				<div class="controls">
					<input type="text" id="rolename" name="rolename" value="${rolename}" data-rules="{required:true}" placeholder="角色名称" class="input-normal control-text"/>
					<input type="hidden" id="rolecode" name="rolecode" value="${rolecode}"/>
				</div>
				<div class="controls span9">
					<div class="pull-right">
					<button type="button" id="J_Form_Submit" class="button button-primary">保存</button>
					<button type="button" id="cloneRole" class="button button-warning">克隆</button>
					<button class="button button-danger" type="button" onclick="top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">返回列表</button>
					</div>
				</div>
			</div>
			<hr/>
			<div id="t1" class="row"></div>
			<div class="row actions-bar">	   
			  <div class="form-actions span13 offset3">
				<button type="button" id="J_Form_Submit2" class="button button-primary">保存</button>
				<button class="button button-danger" type="button" onclick="top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">返回列表</button>
			  </div>
		   </div> 
		</form> 
	</div>
	</div> 
	<script src="${statics }/js/jquery-1.8.1.min.js"></script>
	<script src="${statics }/js/bui.js"></script> 
	<script src="${statics }/js/config.js"></script>
	<script type="text/javascript">
	$(function () {
		BUI.use('custom/commons');
		BUI.use('bui/overlay');
		BUI.use(['bui/mask']);
		var form = new BUI.Form.HForm({
			srcNode : '#objForm',
		}).render(); 
		
		$("#J_Form_Submit,#J_Form_Submit2").click(function(){
			if($("#rolename").val().trim().length <= 0) {
				$("#rolename").focus();
				return false;
			}
			var submit = "";
			$.each($(".bui-grid-body .bui-grid-table tr"),function(){
				var sign = $(this).find("#sign").val();
				var checked = $(this).find("input[name='menustatus']").attr("checked");
				if(checked){
					submit += ","+sign;
				}
			});
			submit = submit.length>1?submit.substr(1):"";
			$.ajax({
				type: "POST",
				url: $("form").attr("action"),
				data:{'menucode' : submit, 'rolename' : $("#rolename").val(), 'rolecode' : $("#rolecode").val()},
				async: false,
				dataType: "json",
				success: function(data) {
					if(data.status == 1){
						BUI.Message.Alert(data.message,'success');
						top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});
					}else{
						BUI.Message.Alert(data.message,'warning');
					}
				   
				}
			});
			
		});
		
		BUI.use(['bui/extensions/treegrid'],function (TreeGrid) {
			var data = ${menus};
			var tree = new TreeGrid({
					render : '#t1',
					nodes : data,
					showLine : true ,//显示连接线
					disabled : true,
					columns : [
						{title : '菜单名称',dataIndex :'menuname',width:'50%',renderer:function(value,obj){
							return value+"<input type='hidden' id='sign' value='"+obj.sign+"'>";
						}},
						{title : '菜单层级',dataIndex :'menulevel',width:'25%',renderer:function(value,obj){
							return value+" 级菜单";
						}},
						{title : '是否显示',dataIndex :'menustatus',width:'25%',renderer:function(value,obj){
							return "<label class='checkbox lable-box'><input type='checkbox' name='menustatus' "+(value==1?"checked='checked'":"")+" level='"+obj.menulevel+"' /></label>";
						}}]
			});
			tree.render();
		});

		$("input[name='menustatus']").live("click",function(){
			var level  = $(this).attr("level")/1;
			var chekcked = $(this).attr("checked");
			$.each($(this).parents("tr").nextAll(),function(){
				var inputafter = $(this).find("input[name='menustatus']");
				var leveln = inputafter.attr("level")/1;
				if(leveln>level){
					if(chekcked){ inputafter.attr("checked","checked"); }
					else{ inputafter.removeAttr("checked"); }
					
				}else{ return false; }
			});
	
			if(chekcked){
				var l = level;
				$.each($(this).parents("tr").prevAll(),function(){
					var inputbefore = $(this).find("input[name='menustatus']");
					var le = inputbefore.attr("level")/1;
					if(l!=le&&le<level){
						inputbefore.attr("checked","checked");
	 					l=le;
					}
					if(le==1) return false;
				});
			}
		});
	});
	
	$("#cloneRole").click(function(){
		var rolename = $("#rolename").val();
		$("#rolename").val("[克隆]" + rolename);
		$("#rolecode").remove();
		$("form").attr("action", "${ctx}/PermissionRole/save");
		$(this).remove();
	});
	</script>
<!-- script end -->
</div>
<%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>
</body>
</html>