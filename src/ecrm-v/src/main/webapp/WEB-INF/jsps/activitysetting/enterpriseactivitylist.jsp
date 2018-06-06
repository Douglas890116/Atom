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
		<form id="searchForm" class="form-horizontal" style="outline: none;"
			method="post">
			<div class="row well" style="margin-left: 0px; position: relative;">
				<div style="position: relative; display: inline-block;">
					<div style="float: left; margin-right: 96px;">
						<div class="control-group span7">
							<label class="control-label">活动名称：</label>
							<input name="activityname" type="text" class="control-text" data-tip='{"text":"活动名称"}'/>
						</div>
						<div class="control-group span7">
							<label class="control-label">品牌：</label>
							<input name="brandname" type="text" class="control-text" data-tip='{"text":"活动所属品牌"}'/>
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
		<div id="activity-detail" style="display: none;width: 600px;">
			<div id="activity-title"></div>
		    <div id="activity-content"></div>
		</div>
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
						text : '<span class="icon-file icon-white"></span>添加活动',
						handler : function() {
							top.topManager.openPage({
								id : 'Activity_Add',
								href : '${ctx}/Activity/Add',
								title : '添加活动'
							});
						}
					});
					botton_arry.push({
						btnCls : 'button button-danger',
						text : '<span class=" icon-trash icon-white"></span>删除活动	',
						handler : function() {
							var selectItem = grid.getSelection();
							if (selectItem.length == 0) {
								BUI.Message.Alert('请选择需要删除的数据', 'info');
							} else {
								BUI.Message.Confirm('是否确定删除选中的活动？',function() {
									var sign = new Array();
									BUI.each(selectItem,function(item) {
												sign.push(item.sign);
									});
									$.ajax({
										type : "POST",
										url : "${ctx}/Activity/Delete",
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
							{title : '编号',width : '5%',dataIndex : 'enterprisebrandactivitycode'},      
							{title : '定制编号',width : '5%',dataIndex : 'ecactivitycode'},     
							{title : '企业',width : '10%',dataIndex : 'enterprisename'},
							{title : '品牌',width : '10%',dataIndex : 'brandname'},
							{title : '团队',width : '10%',dataIndex : 'loginaccount'},
							{title : '活动名称',width : '30%',dataIndex : 'activityname'},
							{title : '开始时间',width : '20%',dataIndex : 'begintime',renderer : BUI.Grid.Format.datetimeRenderer},
							{title : '结束时间',width : '20%',dataIndex : 'endtime',renderer : BUI.Grid.Format.datetimeRenderer},
							{title : '活动状态',width : '10%',dataIndex : 'status',renderer :function(value,data){
								return value/1==1?"启用":"禁用";
							}},
							{title : '操作', width : 180, dataIndex : 'g',renderer : function(value, obj) {
									var oparetion = '';
									oparetion += '<button type="button" onclick="openNewWindow(\'Activity_Edit\',\'${ctx}/Activity/Edit?sign='+ obj.sign+ '\',\'编辑活动\');" class="button  button-success botton-margin"><span class="icon-edit icon-white"></span>编辑</button>';
									oparetion += '<button type="button" data-sign='+obj.ecactivitycode+ ' class="button  button-info botton-margin see-activitydetail"><span class="icon-th-large icon-white"></span>活动详情</button>';
									return oparetion;
								}
							} ];

					var store = new Store({
						url : '${ctx}/Activity/Data',
						autoLoad : false,
						pageSize : 10
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
					
					
					BUI.use('bui/overlay',function(Overlay){
			            var dialog = new Overlay.Dialog({
			              title:'活动详情',
			              contentId:"activity-detail",
			              buttons:[{
			                  text:' 确 定 ',
			                  elCls : 'button button-primary',
			                  handler : function(){
			                    this.close();
			                  }
			                }
			              ]
			            });     
			            $(".see-activitydetail").live("click",function(){
			            	$.ajax({
			    				type: "POST",
			    				url: "${ctx}/EACustomization/View",
			    				data:{"ecactivitycode":$(this).attr("data-sign")},
			    				dataType: "json",
			    				statusCode:{404:function(){
			    					BUI.Message.Alert("请求未发送成功",'error');
			    				}},
			    			    success: function(data) {
			    				    if(data.status == "1"){
		    				    		$("#activity-title").html("<img src="+data.message.activityimage+">");
			    				    	$("#activity-content").html(data.message.activitycontent);
			    				    	dialog.show();
			    				    }else{
			    				    	BUI.Message.Alert(data.message,'warning');
			    				    }
			    			    }
			    			});
			            });
			        });

				})()
			</script>
			<!-- script end -->
		</div>

	</div>
</body>
</html>