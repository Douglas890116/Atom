<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body style="padding: 10px">
	<div class="demo-content">
		<!-- ==============================搜索页===================================== -->
		<form id="formSearch" class="form-horizontal" style="outline: none;" method="post">
            <input name="end_hidden" type="hidden" />
			<div class="row well" style="margin-left: 0px; position: relative;">
				<div style="position: relative; display: inline-block;">
					<div style="float: left; margin-right: 10px;">
						<div class="control-group span8">
							<label class="control-label">角色名称：</label>
							<input name="rolename" type="text" class="control-text" placeholder="角色名称"/>
						</div>
					</div>
					
					<div style="position: absolute; right: 15px; top: 3px;">
						<button id="btnSearch" type="submit" class="button button-primary">
							<span class="icon-search icon-white"></span>搜索
						</button>
					</div>
				</div>
			</div>
		</form>   
	<!-- ==========list展示层========== -->
	<div class="search-grid-container well">
		<div id="grid"></div>
	</div>
	</div>
</body>
</html>
<!-- script begin -->
<script type="text/javascript">
	var add 		 = ${sessionScope.ERCM_USER_PSERSSION['MN00E6'].menustatus == 1};
	var edit 		 = ${sessionScope.ERCM_USER_PSERSSION['MN00E9'].menustatus == 1};
	var clone 		 = ${sessionScope.ERCM_USER_PSERSSION['MN00EA'].menustatus == 1};
	var one_delete 	 = ${sessionScope.ERCM_USER_PSERSSION['MN00E7'].menustatus == 1};
	var batch_delete = ${sessionScope.ERCM_USER_PSERSSION['MN00E8'].menustatus == 1};
	//权限验证
	function permissionValidate() {
		var botton_arry = new Array();
		if(add){
			botton_arry.push({
				btnCls : 'button button-primary',
				text : '<span class="icon-file icon-white"></span>新建角色',
		        handler : function(){
		        	openNewWindow('add_role','${ctx}/PermissionRole/add','新建角色');
		        }
			});
		}
		if(batch_delete){
			botton_arry.push({
				btnCls : 'button button-danger',
				text : '<span class=" icon-trash icon-white"></span>删除角色',
		        handler : function(){
		        	deleteMutilterm(grid,"${ctx}/PermissionRole/batchDelete","sign");
		        }
			});
		}
		return botton_arry;
	}

	var Grid = BUI.Grid, 
	Store = BUI.Data.Store, 
	columns = [
		{title:'角色名称',width:'15%',sortable:false,dataIndex:'rolename'},
		{title:'企业名称',width:'15%',sortable:false,dataIndex:'enterprisecode'},
		{title:'操作', width:'30%',sortable:false,renderer:function(value,obj){
			var operation = "";
			if (edit) operation += '<button class="button button-success btn-edit" onclick=editRole("'+obj.sign+'")><span class="icon-edit icon-white"></span> 编 辑 </button> ';
			if (clone) operation += '<button class="button button-warning btn-edit" onclick=cloneRole("'+obj.sign+'")><span class="icon-retweet icon-white"></span> 克 隆 </button> ';
			if (one_delete) operation += '<button class="button button-danger" onclick="deleteOne(this)" value="'+obj.sign+'"><span class="icon-trash icon-white"></span> 删 除 </button> ';
			return operation;
		}}
	];

	var store = new Store({
		url : '${ctx}/PermissionRole/data',
		autoLoad : true,
		pageSize:15
		}),
		grid = new Grid.Grid({
			render:'#grid',
			autoRender:true,
			loadMask: true,//加载数据时显示屏蔽层
			columns : columns,
			width:'100%',
			store: store,
			plugins : [Grid.Plugins.CheckSelection],
			emptyDataTpl:'<div class="centered"><img alt="Crying" src="${statics }/img/dataisnull.png"/><font style="color:red;font-size:20px;"><h2>没有找到匹配的数据!</h2></font></div>',
			tbar:{ items:permissionValidate() },
			bbar : { pagingBar:true }
		}),
		datepicker = new BUI.Calendar.DatePicker({
			trigger:'.calendar-time',
			showTime:true,
			autoRender:true
		});
 
 	grid.on('cellclick',function(ev){
 		var sender = $(ev.domTarget);
 		if(!sender.hasClass('x-grid-checkbox')){ //如果不是点中勾选列，不能进行选中操作
 		  return false;
 		}
 	});
	
	//查询数据
	$("#formSearch").submit(function(){
		var obj = BUI.FormHelper.serializeToObject(this);
		obj.start = 0;
		store.load(obj);
		return false;
	}).submit();
	// 克隆数据
	function cloneRole(sign) {
		$.ajax({
			url  : "${ctx}/PermissionRole/clone",
			data : {'rolecode' : sign},
			dataType : "json",
			success  : function(data) {
				if (data.status == 1) {
					$("#formSearch").submit();
				} else {
					BUI.Message.Alert(data.message, "error");
				}
			},
			error : function() {
				BUI.Message.Alert("系统错误，请联系管理员 !", "error");
			}
		});
	}
	// 删除数据
	function deleteOne(obj) {
		BUI.Message.Show({
			title : '删除',
			msg : '确定删除?',
			icon : 'question',
			buttons : [
				{
					text : '确定',
					elCls : 'button button-primary',
					handler : function() {
						$.ajax({
							url  : '${ctx}/PermissionRole/delete',
							data : { 'rolecode' : $(obj).val() },
							dataType : "json",
							success  : function(data) {
								if (data.status == 1) {
									BUI.Message.Alert(data.message, "success");
									$(obj).parent().parent().parent().parent().remove();
								}else {
									BUI.Message.Alert(data.message, "error");
								}
							}
						});
						this.close();
					}
				}, {
					text : '取消',
					elCls : 'button',
					handler : function() {
						this.close();
					}
				}
			]
		});
	}
</script>
<!-- script end -->