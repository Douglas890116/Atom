<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>企业列表</title>
<link rel="stylesheet" href="${statics}/css/bs3/bui-min.css"/>
<link rel="stylesheet" href="${statics}/css/bs3/dpl-min.css"/>
<style type="text/css">
  /** 自定义提示样式**/
  .tip1{
    max-width: 600px;
    word-wrap: break-word;
    border:1px solid #7BC3FF;
    box-shadow: 1px 1px 10px #7BC3FF;
  }
</style>
</head>

<body style="padding: 10px;">
	<div class="demo-content">
		<!-- 搜索页 ================================================== -->
		<form id="searchForm" class="form-horizontal" style="outline: none;"
			method="post">
			<div class="row well" style="margin-left: 0px; position: relative;">
				<div style="position: relative; display: inline-block;">
					<div style="float: left; margin-right: 96px;">
						<div class="control-group span7">
							<label class="control-label">活动名称：</label>
							<input name="name" type="text" class="control-text" placeholder="活动名称"/>
						</div>
						<div class="control-group span5">
							<label class="control-label">状态：</label>
							<div class="bui-form-group" data-rules="{dateRange : true}">
								<select aria-pressed="false" name="status" aria-disabled="false" class="bui-form-field-select bui-form-field input-small">
									<option value="">请选择</option>
									<option value="1">启用</option>
									<option value="2">禁用</option>
								</select>
							</div>
						</div>
					</div>
					<div style="position: absolute; right: 15px; top: 3px;">
							<button id="btnSearch" type="submit" class="button button-primary">
							<span class="icon-search icon-white"></span>搜索</button>
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
						text : '<span class="icon-file icon-white"></span>创建模板',
						handler : function() {
							top.topManager.openPage({
								id : 'create_enterprise',
								href : '${ctx}/ActivityTemplate/Add',
								title : '创建活动模板'
							});
						}
					});
					botton_arry.push({
						btnCls : 'button button-danger',
						text : '<span class=" icon-trash icon-white"></span>删除模板	',
						handler : function() {
							var selectItem = grid.getSelection();
							if (selectItem.length == 0) {
								BUI.Message.Alert('请选择需要删除的数据', 'info');
							} else {
								BUI.Message.Confirm('是否确定删除选中的活动模板？',function() {
									var sign = new Array();
									BUI.each(selectItem,function(item) {
												sign.push(item.sign);
									});
									$.ajax({
										type : "POST",
										url : "${ctx}/ActivityTemplate/Delete",
										data : {
											"sign" : sign
										},
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
							{title : '活动名称',width : '20%',sortable : false,dataIndex : 'name'},
							{title : '活动状态',width : '20%',sortable : true,dataIndex : 'status', renderer : function(value, obj) {
								return value/1==1?"启用":"禁用";
							}},
							{title : '活动描述',width : '60%',sortable : true,dataIndex : 'activitydesc',renderer : function(value, obj) {
								return value?"<a class='activitydesc-tips'  title='"+value+"' >"+value.substring(0,25)+"...</a>":"";
							}},
							{title : '操作', width : 180, dataIndex : 'g',renderer : function(value, obj) {
									var oparetion = '';
									oparetion += '<button type="button" onclick="openNewWindow(\'ActivityTemplate_Edit\',\'${ctx}/ActivityTemplate/Edit?sign='+ obj.sign+ '\',\'编辑活动模板\');" class="button  button-success botton-margin"><span class="icon-edit icon-white"></span>编辑</button>';
									oparetion += '<button type="button" onclick="openNewWindow(\'ActivityTemplateSetting_Show\',\'${ctx}/ATemplateSetting/Show?sign='+ obj.sign+ '\',\'活动模板参数设置\');"  class="button  button-info botton-margin"><span class="icon-qrcode icon-white"></span>参数设置</button>';
									return oparetion;
								}
							} ];

					var store = new Store({
						url : '${ctx}/ActivityTemplate/Data',
						autoLoad : false,
						pageSize : 15,
						remoteSort: true,
						sortInfo : {
			                field : 'activitycode',
			                direction : 'desc'
			              }
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
					
					//不使用模板的，左侧显示
				      var tips = new BUI.Tooltip.Tips({
				        tip : {
				          trigger : '.activitydesc-tips', //出现此样式的元素显示tip
				          alignType : 'bottom', //默认方向
				          elCls : 'tips tips-no-icon tip1',
				          offset : 10 //距离左边的距离
				        }
				      });
				      tips.render();

				})()
			</script>
			<!-- script end -->
		</div>

	</div>
</body>
</html>