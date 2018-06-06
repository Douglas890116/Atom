<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>企业列表</title>
<link href="${statics}/css/bs3/bui-min.css" rel="stylesheet" />
<link href="${statics}/css/bs3/dpl-min.css" rel="stylesheet" />
<link href="${statics}/css/custom/common.css" rel="stylesheet" />
</head>

<body style="padding: 10px;">
	<div class="demo-content">
		<!-- 搜索页 ================================================== -->
		<form id="searchForm" class="form-horizontal" style="outline: none;" method="post">
		<input type="hidden" value="${activitycode }" name="activitycode">
			<div class="row well" style="margin-left: 0px; position: relative;">
				<div style="position: relative; display: inline-block;">
					<div style="float: left; margin-right: 96px;">
						<div class="control-group span6">
							<label class="control-label">参数：</label>
							<input name="agementname" type="text" class="control-text" placeholder="参数"/>
						</div>
						<div class="control-group span7">
							<label class="control-label">参数描述：</label>
							<input name="agementdesc" type="text" class="control-text" placeholder="参数描述"/>
						</div>
						<div class="control-group span">
							<label class="control-label">参数值：</label>
							<input name="agementvalue" type="text" class="control-text" placeholder="参数值"/>
						</div>
					</div>
					<div style="position: absolute; right: -95px; top: 3px;">
							<button id="btnSearch" type="submit" class="button button-primary"><span class="icon-search icon-white"></span>搜索</button>
							<button class="button button-danger" type="button" onclick="top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">返回列表</button>
					</div>
				</div>
			</div>
		</form>
		<div class="search-grid-container well">
			<div id="grid"></div>
			<script src="${statics }/js/jquery-1.8.1.min.js"></script>
			<script src="${statics }/js/bui.js"></script>
			<script src="${statics }/js/custom/commons-min.js "></script>
			<script type="text/javascript">
				(function() {
					var botton_arry = new Array();
					botton_arry.push({
						btnCls : 'button button-primary',
						text : '<span class="icon-file icon-white"></span>添加参数',
						handler : function() {
							top.topManager.openPage({
								id : 'ATemplateSetting_Add',
								href : '${ctx}/ATemplateSetting/Add?sign=${activitycode }',
								title : '添加参数'
							});
						}
					});
					botton_arry.push({
						btnCls : 'button button-danger',
						text : '<span class=" icon-trash icon-white"></span>删除参数	',
						handler : function() {
							var selectItem = grid.getSelection();
							if (selectItem.length == 0) {
								BUI.Message.Alert('请选择需要删除的数据', 'info');
							} else {
								BUI.Message.Confirm('是否确定删除选中的活动参数？',function() {
									var sign = new Array();
									BUI.each(selectItem,function(item) {
												sign.push(item.sign);
									});
									$.ajax({
										type : "POST",
										url : "${ctx}/ATemplateSetting/Delete",
										data : {"sign" : sign},
										dataType : "json",
										success : function(data) {
											if (data.status == 1) {
												BUI.Message.Alert(data.message,'success');
												grid.removeItems(selectItem);
											} else {
												BUI.Message.Alert(data.message,'warning');
											}
										}
									});
								}, 'question');
							}
						}
					});

					var Grid = BUI.Grid, Store = BUI.Data.Store, columns = [
							{title : '活动名称',width : '30%',sortable : false,dataIndex : 'activityname'},
							{title : '参数',width : '30%',sortable : true,dataIndex : 'agementname'},
							{title : '参数描述',width : '40%',sortable : true,dataIndex : 'agementdesc'},
							{title : '操作', width : 180, dataIndex : 'g',renderer : function(value, obj) {
									var oparetion = '';
									oparetion += '<button type="button" onclick="openNewWindow(\'ATemplateSetting_Edit\',\'${ctx}/ATemplateSetting/Edit?sign='+ obj.sign+ '\',\'编辑活动模板\');" class="button  button-success botton-margin"><span class="icon-edit icon-white"></span>编辑</button>';
									return oparetion;
								}
							} ];

					var store = new Store({
						url : '${ctx}/ATemplateSetting/Data',
						autoLoad : false,
						pageSize : 15
					}), grid = new Grid.Grid({
						render : '#grid',
						autoRender : true,
						width : '100%',
						loadMask : true,
						columns : columns,
						store : store,
						plugins : [ Grid.Plugins.CheckSelection ],
						emptyDataTpl : '<div class="centered"><img alt="Crying" src="${statics }/img/dataisnull.png"/><font style="color:red;font-size:20px;"><h2>没有找到匹配的数据!</h2></font></div>',
						tbar : {
							items : botton_arry
						},
						bbar : {
							pagingBar : true,
						}
					}),datepicker = new BUI.Calendar.DatePicker({
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

					$("#searchForm").submit(function(){
				    	  var obj = BUI.FormHelper.serializeToObject(this);
				          obj.start = 0;
				          store.load(obj);
				    	  return false;
				      }).submit();

				})()
			</script>
			<!-- script end -->
		</div>

	</div>
</body>
</html>