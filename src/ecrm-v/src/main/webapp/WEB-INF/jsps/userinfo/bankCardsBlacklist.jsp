<%@page import="com.maven.entity.EnterpriseEmployeeType.Type"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body style="padding: 10px;">
	<div class="demo-content">
		<!-- 搜索页 ================================================== -->
		<form id="searchForm" class="form-horizontal" style="outline: none;"
			method="post">
			<div class="row well" style="margin-left: 0px; position: relative;">
				<div style="position: relative; display: inline-block;">
					<div style="float: left; margin-right: 126px;">
						<div class="control-group span6">
							<label class="control-label">姓名：</label>
							<input name="username" type="text" data-tip='{"text":"姓名"}' class="control-text" />
						</div>
						
						<div class="control-group span6" >
							<label class="control-label">手机号码：</label>
							<input name="phoneno" type="text" data-tip='{"text":"手机号码"}' class="control-text" />
						</div>

						<div class="control-group span6">
							<label class="control-label">银行卡号：</label>
							<input name="bankcard" type="text" data-tip='{"text":"银行卡号"}' class="control-text" />
						</div>
					</div>
					<div style="position: absolute; right: 15px; top: 3px;">
						<button id="btnSearch" type="submit" class="button button-primary">
							<span class="icon-search icon-white"></span>搜 索
						</button>
					</div>
				</div>
			</div>
		</form>

		<div class="search-grid-container well">
			<div id="grid"></div>
		</div>

	</div>


	<!-- 初始隐藏 dialog内容 -->
	<div id="content" class="hide">
		<form class="form-horizontal">
			<div class="row">
				<input id="lsh" name="lsh" type="hidden" value=""/>
				<div class="control-group span7">
					<label class="control-label">&nbsp;&nbsp;姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</label>
					<div class="controls">
						<input id="username" name="username" type="text" value="test" data-rules="{required:true}" class="input-normal control-text"/>
					</div>
				</div>
				<div class="control-group span7">
					<label class="control-label">&nbsp;&nbsp;手机号码：</label>
					<div class="controls">
						<input id="phoneno" name="phoneno" type="text" data-rules="{number:true}" class="input-normal control-text"/>
					</div>
				</div>
				<div class="control-group span7">
					<label class="control-label">&nbsp;&nbsp;银行卡号：</label>
					<div class="controls">
						<input id="bankcard" name="bankcard" type="text" data-rules="{number:true}" class="input-normal control-text"/>
					</div>
				</div>
				<div class="control-group span7">
					<label class="control-label">&nbsp;&nbsp;描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述：</label>
					<div class="controls">
						<textarea id="remark" name="remark" rows="20" cols="200"></textarea>
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
</html>
<script type="text/javascript">
	var employeetype = '${sessionScope.ERCM_USER_SESSION.employeetypecode}';
	//权限验证
	function permissionValidate() {
		var array = new Array();
		array.push({
			btnCls : 'button button-primary',
			text : '<span class="icon-plus icon-white"></span> 新增黑名单',
			listeners : {
				'click' : addFunction
			}
		});
		return array;
	}
	var Grid = BUI.Grid, Store = BUI.Data.Store, columns = [
		{title : '编号', width : '3%', sortable : false, dataIndex : 'lsh', elCls:'center'},
		{title : '姓名', width : '5%', sortable : false, dataIndex : 'username'},
		{title : '手机号码', width : '10%', sortable : false, dataIndex : 'phoneno'},
		{title : '银行卡号', width : '15%', sortable : false, dataIndex : 'bankcard'},
		{title : '描述', width : '60%', 	sortable : true, dataIndex : 'remark'},
		{title : '操作', width:'15%',sortable:false,renderer:function(value,obj){
			var operation = '';
			operation += '<button class="button button-success btn-edit" onclick=editOne()><span class="icon-edit icon-white"></span> 编 辑 </button>';
			// operation += '<button class="button button-danger" onclick=deleteOne(this) value="'+obj.lsh+'"><span class="icon-trash icon-white"></span> 删 除 </button>';
			return operation;
		}}
	];

	var isAddRemote = false;
	var editing = new Grid.Plugins.DialogEditing({
		contentId : 'content', //设置隐藏的Dialog内容
		triggerCls : 'btn-edit', //触发显示Dialog的样式
		editor : {
			title : '黑名单',
			width : 300,
			hight : 300
		}
	});

	var store = new Store({
		url : '${ctx}/cardsblacklist/list',
		autoLoad : true,
		pageSize : 15
	}), grid = new Grid.Grid(
			{
				render : '#grid',
				autoRender : true,
				loadMask : true,//加载数据时显示屏蔽层
				columns : columns,
				width : '100%',
				store : store,
				plugins : [ editing, Grid.Plugins.CheckSelection ],
				emptyDataTpl : '<div class="centered"><img alt="Crying" src="${statics}/img/dataisnull.png"/><font style="color:red;font-size:20px;"><h2>没有找到匹配的数据!</h2></font></div>',
				tbar : { items : permissionValidate() },
				bbar : { pagingBar : true }
			}), datepicker = new BUI.Calendar.DatePicker({
		trigger : '.calendar-time',
		showTime : true,
		autoRender : true
	});

	grid.on('cellclick', function(ev) {
		var sender = $(ev.domTarget);
		if (!sender.hasClass('x-grid-checkbox')) { //如果不是点中勾选列，不能进行选中操作
			return false;
		}
	});

	editing.on('accept', function(ev) {
		if (isAddRemote) {
			var data = editing.get('record');
			$.post(getRootPath() + "/cardsblacklist/update", {
				'lsh' : data.lsh,
				'username' : $.trim(data.username),
				'bankcard' : $.trim(data.bankcard),
				'phoneno' : $.trim(data.phoneno),
				'remark' : $.trim(data.remark)
			}, function(data) {
				if (data.status == 0) {
					BUI.Message.Alert(data.message, "error");
					return;
				}
			}, 'json')
		} else {
			var data = editing.get('record');
			$.post(getRootPath() + "/cardsblacklist/save", {
				'username' : $.trim(data.username),
				'bankcard' : $.trim(data.bankcard),
				'phoneno' : $.trim(data.phoneno),
				'remark' : $.trim(data.remark)
			}, function(data) {
				if (data.status == 0) {
					BUI.Message.Alert(data.message, "error");
					return;
				}
				$("#btnSearch").click();
			}, 'json')
		}
	});
	$("#searchForm").submit(function() {
		var obj = BUI.FormHelper.serializeToObject(this);
		obj.start = 0;
		store.load(obj);
		return false;
	}).submit();
	//添加记录
	function addFunction() {
		isAddRemote = false;
		var newData = {};
		editing.add(newData, ''); //添加记录后，直接编辑
	}
	//修改记录
	function editOne(grid) {
		isAddRemote = true;
	}
	// 删除数据
	function deleteOne(obj) {
		BUI.Message.Show({
			//title : '警告',
			msg : '确定删除?',
			icon : 'question',
			buttons : [
				{
					text : '确定',
					elCls : 'button button-primary',
					handler : function() {
						$.ajax({
							url  : '${ctx}/cardsblacklist/delete',
							data : { 'lsh' : $(obj).val() },
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
<style>
</style>