<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<!DOCTYPE html>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>${title }</title>
		<link rel="stylesheet" href="${statics}/css/bs3/bui-min.css" />
		<link rel="stylesheet" href="${statics}/css/bs3/dpl-min.css" />
		<link rel="stylesheet" href="${statics}/css/custom/itemContent.css" />
		<style type="text/css">
			/** 自定义提示样式**/
			
			.tip1 {
				max-width: 600px;
				word-wrap: break-word;
				border: 1px solid #7BC3FF;
				box-shadow: 1px 1px 10px #7BC3FF;
			}
			/**内容超出 出现滚动条 **/
			
			.bui-stdmod-body {
				overflow-x: hidden;
				overflow-y: auto;
			}
		</style>
	</head>

	<body style="padding: 10px;">
		<div class="demo-content">
			<!-- 搜索页 ================================================== -->
			<form id="searchForm" class="form-horizontal" style="outline: none;" method="post">
				<div class="row well" style="margin-left: 0px; position: relative;">
					<div style="position: relative; display: inline-block;">
						<div style="float: left; margin-right: 96px;">
							<div class="control-group span7">
								<label class="control-label">品牌名称：</label>
								<select name="brandcode">
									<option value="">--请选择--</option>
									<c:forEach var="brand" varStatus="status" items="${list }">
										<option value="${brand.brandcode}">${brand.brandname}</option>
									</c:forEach>
								</select>

							</div>
							<div style="position: absolute; right: 15px; top: 3px;">
								<button id="btnSearch" type="submit" class="button button-primary">
								<span class="icon-search icon-white"></span>搜索
							</button>
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
							btnCls: 'button button-primary',
							text: '<span class="icon-file icon-white"></span>新增栏目',
							handler: function() {
								top.topManager.openPage({
									/* id : 'create_enterprise', */
									id: 'add_item',
									href: '${ctx}/agent/item/add',
									title: '新增栏目'
								});
							}
						});
						botton_arry.push({
							btnCls: 'button button-danger',
							text: '<span class=" icon-trash icon-white"></span>删除栏目	',
							handler: function() {
								var selectItem = grid.getSelection();
								if(selectItem.length == 0) {
									BUI.Message.Alert('请选择需要删除的数据', 'info');
								} else {
									BUI.Message.Confirm('是否确定删除选中的栏目？', function() {
										var sign = new Array();
										BUI.each(selectItem, function(item) {
											sign.push(item.lsh);
										});
										$.ajax({
											type: "POST",
											url: "${ctx}/agent/item/delete",
											data: {
												"sign": sign
											},
											dataType: "json",
											success: function(data) {
												if(data.status == 1) {
													BUI.Message.Alert(data.message, 'success');
													grid.removeItems(selectItem);
												} else {
													BUI.Message.Alert(data.message, 'warning');
												}
											}
										});
									}, 'question');
								}
							}
						});

						var Grid = BUI.Grid,
							Store = BUI.Data.Store,
							columns = [{
								title: '品牌名称',
								width: '8%',
								sortable: false,
								dataIndex: 'brandname'
							}, /* {
								title: '栏目图标',
								width: '10%',
								sortable: true,
								dataIndex: 'iconpath',
								renderer: function(value, data) {
									if(null != value && value != "")
										return "<a href='" + value + "' target='_blank'><img src='" +
											value +
											" 'style='width:30%;height:30%;'/></a>";
								}
							}, */ {
								title: '栏目标题',
								width: '8%',
								sortable: false,
								dataIndex: 'title'
							}, {
								title: '栏目内容',
								width: 200,
								sortable: false,
								renderer: function(value, data) {
									var oparetion = '';
									oparetion += '<button type="button" onclick="window.open(\'${ctx}/agent/item/content?lsh=' +
										data.lsh +
										'\');" class="button  button-success botton-margin"><span class="icon-edit icon-white"></span>查看</button>';
									return oparetion;
								}
							}, {
								title: '操作',
								width: 200,
								sortable: false,
								renderer: function(value, data) {
									var oparetion = '';
									oparetion += '<button type="button" onclick="openNewWindow(\'item_edit\',\'${ctx}/agent/item/edit?lsh=' +
										data.lsh +
										'\',\'编辑栏目\');" class="button  button-success botton-margin"><span class="icon-edit icon-white"></span>编辑</button>';
									return oparetion;
								}
							}, ];

						var store = new Store({
								url: '${ctx}/agent/item/data',
								autoLoad: false,
								pageSize: 15,
								remoteSort: true,
								sortInfo: {
									field: 'lsh',
									direction: 'desc'
								}
							}),
							grid = new Grid.Grid({
								render: '#grid',
								autoRender: true,
								width: '100%',
								loadMask: true,
								columns: columns,
								store: store,
								plugins: [Grid.Plugins.CheckSelection],
								emptyDataTpl: '<div class="centered"><img alt="Crying" src="${statics }/img/dataisnull.png"/><font style="color:red;font-size:20px;"><h2>没有找到匹配的数据!</h2></font></div>',
								tbar: {
									items: botton_arry
								},
								bbar: {
									pagingBar: true,
								}
							}),
							datepicker = new BUI.Calendar.DatePicker({
								trigger: '.calendar-time',
								showTime: true,
								autoRender: true
							});

						grid.on('cellclick', function(ev) {
							var sender = $(ev.domTarget);
							if(!sender.hasClass('x-grid-checkbox')) { //如果不是点中勾选列，不能进行选中操作
								return false;
							}
						});

						$("#searchForm").submit(function() {
							var obj = BUI.FormHelper.serializeToObject(this);
							obj.start = 0;
							store.load(obj);
							return false;
						}).submit();

						//不使用模板的，左侧显示
						var tips = new BUI.Tooltip.Tips({
							tip: {
								trigger: '.activitycontent-tips', //出现此样式的元素显示tip
								alignType: 'bottom', //默认方向
								elCls: 'tips tips-no-icon tip1',
								offset: 10
									//距离左边的距离
							}
						});
						tips.render();
					})()
				</script>
			</div>

			</div>
	</body>

</html>