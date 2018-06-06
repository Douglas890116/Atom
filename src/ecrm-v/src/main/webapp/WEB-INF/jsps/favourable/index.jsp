<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优惠列表</title>
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
    /**内容超出 出现滚动条 **/
    .bui-stdmod-body{
      overflow-x : hidden;
      overflow-y : auto;
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
					
					<div class="control-group span7">
		              <label class="control-label">优惠类型 ：</label>
		              <div class="controls">
		              	<select name="isonce" data-rules="{required:true}" >
		                    <option value="">--请选择--</option>
		                    <option value="0">一次性优惠</option>
		                    <option value="1">多次优惠</option>
		                </select>
		              </div>
		             </div>
		            
		            <div class="control-group span7">
		              <label class="control-label">状态 ：</label>
		              <div class="controls">
		              	<select name="status" data-rules="{required:true}" >
		                    <option value="">--请选择--</option>
		                    <option value="0">启用</option>
		                    <option value="1">禁用</option>
		                </select>
		              </div>
		             </div> 
		             
		             <div class="control-group span7">
		              <label class="control-label">默认优惠 ：</label>
		              <div class="controls">
		              	<select name="isdeault" data-rules="{required:true}" >
		                    <option value="">--请选择--</option>
		                    <option value="0">非默认</option>
		                    <option value="1">默认</option>
		                </select>
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
			var store ;
			
				(function() {
					var botton_arry = new Array();
					botton_arry.push({
				        btnCls : 'button button-primary',
				        text:"<span class='icon-file icon-white'></span>创建优惠",
				        handler : function(){
				        	openNewWindow('create_favourable','${ctx}/favourable/add','创建优惠');
				        }
				  	});
					
					

					var Grid = BUI.Grid, Store = BUI.Data.Store, columns = [
							{title : '名称',width : '20%',sortable : true,dataIndex : 'favourablename'},
							{title : '流水倍数',width : '5%',sortable : true,dataIndex : 'lsbs'},
							{title : '开始时间',width : '10%',sortable : true,dataIndex : 'starttime',renderer:BUI.Grid.Format.datetimeRenderer},
							{title : '结束时间',width : '10%',sortable : true,dataIndex : 'endtime',renderer:BUI.Grid.Format.datetimeRenderer},
							{title : '创建时间',width : '10%',sortable : true,dataIndex : 'createtime',renderer:BUI.Grid.Format.datetimeRenderer},
							
							{title : '优惠类型',width : '10%',sortable : true, dataIndex : 'isonce',renderer:function(value,obj){
								if(value == "0" ) {
									return "一次性优惠";
								} else {
									return "多次优惠";
								}
							}},
							{title : '状态',width : '10%',sortable : true, dataIndex : 'status',renderer:function(value,obj){
								if(value == "0" ) {
									return "启用";
								} else {
									return "禁用";
								}
							}},
							{title : '默认优惠',width : '10%',sortable : true, dataIndex : 'isdeault',renderer:function(value,obj){
								if(value == "0" ) {
									return "--";
								} else {
									return "默认";
								}
							}},
							{title : '操作',width : '15%',sortable : true, dataIndex : 'isdeault',renderer:function(value,obj){
								
								var  html = "";
								
								html += '<button type="button" onclick="showUpdate(\''+ obj.lsh+'\');" class="button  button-inverse botton-margin"><span class="icon-edit icon-white"></span>编辑</button>';
								
				            	if(obj.status == "1") {
				            		html += '<button type="button" onclick="showAudit(\''+ obj.lsh+'\', 0);" class="button  button-success botton-margin">启用</button>';
				            	} else if(obj.status == "0") {
				            		html += '<button type="button" onclick="showAudit(\''+ obj.lsh+'\', 1);" class="button  button-danger botton-margin">禁用</button>';
				            	} 
				            	
				                return html;
								
							}}
							
							
							];

					store = new Store({
						url : '${ctx}/favourable/data',
						autoLoad : false,
						pageSize : 15,
						remoteSort: true,
						sortInfo : {
			                field : 'createtime',
			                direction : 'desc'
			              }
					}), grid = new Grid.Grid({
						render : '#grid',
						autoRender : true,
						width : '100%',
						loadMask : true,
						columns : columns,
						store : store,
						plugins : [Grid.Plugins.CheckSelection],
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
				          trigger : '.activitycontent-tips', //出现此样式的元素显示tip
				          alignType : 'bottom', //默认方向
				          elCls : 'tips tips-no-icon tip1',
				          offset : 10 //距离左边的距离
				        }
				      });
				      tips.render();
				})()
				
				function showUpdate(lsh){
					openNewWindow('update_favourable','${ctx}/favourable/edit?lsh='+lsh,'编辑优惠');
			    }
				function showAudit(lsh,statu){
			    	BUI.Message.Confirm('是否确定操作？',function() {
						$.ajax({
							type : "POST",
							url : "${ctx}/favourable/UpdateStatus",
							data : {"lsh" : lsh, "status": statu},
							dataType : "json",
							success : function(data) {
								if (data.status == 1) {
									BUI.Message.Alert(data.message,'success');
									store.load();
								} else {
									BUI.Message.Alert(data.message,'warning');
								}
							}
						});
					}, 'question');
			    }
				
			</script>
			<!-- script end -->
		</div>

	</div>
</body>
</html>