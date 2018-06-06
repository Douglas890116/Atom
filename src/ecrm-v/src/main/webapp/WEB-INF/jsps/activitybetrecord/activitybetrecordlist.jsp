<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优惠流水列表</title>
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
					
					<div class="control-group span12">
						<label class="control-label">时间：</label>
						<div class="bui-form-group" data-rules="{dateRange : true}">
							<input name="createtime_begin" type="text" class="calendar calendar-time" value="${param.createtime_begin }" placeholder="起始时间" /> 
							<span>&nbsp;-&nbsp;</span>
							<input name="createtime_end" type="text"  class="calendar calendar-time" value="${param.createtime_end }" placeholder="结束时间"/>
						</div>
					</div>
					
					<div class="control-group span7">
		              <label class="control-label">活动名称：</label>
		              <div class="controls">
		              	<select name="ecactivitycode" data-rules="{required:true}" >
		                    <option value="">--请选择--</option>
		                     <c:forEach var="item" items="${listEnterpriseBrandActivity }" varStatus="i" >
		                    	<option value="${item.ecactivitycode }">${item.activityname }</option>
		                    </c:forEach>
		                </select>
		              </div>
		             </div>
		             
		             <div class="control-group span7">
		             	<label class="control-label">用户账号：</label>
						<div class="controls">
							<input name="loginaccount" type="text" class="control-text" value="${param.loginaccount }" placeholder="用户账号"/>
						</div>
					</div>
		             <div class="control-group span8">
		             	<label class="control-label">上级账号：</label>
						<div class="controls">
							<input name="parentName" type="text" class="control-text" value="${param.loginaccount }" placeholder="上级账号"/>
						</div>
					</div>
	                <!-- <div style="margin-right: auto;margin: -30px 425px;">
	                  <select onchange="changeFormatDate(this,'brandfoundedtime_begin','brandfoundedtime_end')" id="selectDateId" style="width:85px;">
	                                <option value="">请选择</option>
	                                <option value="1">今天</option>
	                                <option value="2">昨天</option>
	                                <option value="3">三天</option>
	                                <option value="4">本周</option>
	                                <option value="5">上周</option>
	                                <option value="6">半月</option>
	                                <option value="7">本月</option>
	                                <option value="8">上月</option>
	                                <option value="9">半年</option>
	                                <option value="10">本年</option>
	                   </select>
	                </div> -->
	                
					<div style="position: absolute; right: 5px; top: 3px;">
						<button id="btnSearch" type="submit" class="button button-primary">
						<span class="icon-search icon-white"></span>搜索</button>
					</div>
				</div>
			</div>
		</form>
		<div class="search-grid-container well">
			<div id="grid"></div>
			<script src="${statics}/js/jquery-1.8.1.min.js"></script>
			<script src="${statics}/js/bui.js"></script>
			<script src="${statics}/js/custom/commons-min.js "></script>
			<script type="text/javascript">
			
		    var export_excel = (1 == 1); 


		    
				(function() {
					var botton_arry = new Array();
				    if(export_excel){
				    	botton_arry.push({
				    	          btnCls:'button button-primary',
				    	          text:'<span class="icon-file icon-white"></span>导出到EXCEL(当前条件)',
				    	          handler:function(){
				    	          	searchForm.action = "${ctx}/ActivityBetRecord/exportExcel";
				    	          	searchForm.submit();
				    	          	searchForm.action = "${ctx}/ActivityBetRecord/data";
				    	          }
				    	});
				    }

					var Grid = BUI.Grid, Store = BUI.Data.Store, columns = [
							{title : '流水号',width : '6%',sortable : true,dataIndex : 'betrecordcode'},                                                
							{title : '用户账户',width : '10%',sortable : true,dataIndex : 'loginaccount'},
							{title : '用户编码',width : '10%',sortable : true,dataIndex : 'employeecode'},
							{title : '参与活动',width : '30%',sortable : false,dataIndex : 'activityname'},
							{title : '充值/活动金额',width : '13%',sortable : true, dataIndex : 'recharge',renderer:function(value,obj){
								if(value == "" || value == null) {
									return "0.0";
								} else {
									return parseFloat(value).toFixed(2);
								}
							}},
							{title : '所需流水',width : '10%',sortable : true,summary : true, dataIndex : 'mustbet',renderer:function(value,obj){
								if(value == "" || value == null) {
									return "0.0";
								} else {
									return parseFloat(value).toFixed(2);
								}
							}},
							/***
							{title : '已完成打码',width : '13%',sortable : true,summary : true, dataIndex : 'alreadybet',renderer:function(value,obj){
								if(value == "" || value == null) {
									return "0.0";
								} else {
									return parseFloat(value).toFixed(2);
								}
							}},
							***/
							{title : '参与时间',width : '15%',sortable : true,dataIndex : 'createtime',renderer:BUI.Grid.Format.datetimeRenderer},
							/* {title : '操作',width : 200 ,sortable : false,renderer :function(value,data){
								var oparetion = '';
								oparetion += '<button type="button" onclick="openNewWindow(\'EACustomization_Edit\',\'${ctx}/EACustomization/Edit?ecactivitycode='+data.ecactivitycode+'\',\'编辑定制活动\');" class="button  button-success botton-margin"><span class="icon-edit icon-white"></span>编辑</button>';
								oparetion += '<button type="button" onclick="showSetting('+data.activitytemplatecode+','+data.ecactivitycode+');"  class="button  button-info botton-margin EACustomizationSetting"><span class="icon-qrcode icon-white"></span>参数设置</button>';
								return oparetion;
							}}, */
							];

					var store = new Store({
						url : '${ctx}/ActivityBetRecord/data',
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
						plugins : [Grid.Plugins.CheckSelection, Grid.Plugins.Summary],
						emptyDataTpl : '<div class="centered"><img alt="Crying" src="${statics }/img/dataisnull.png"/><font style="color:red;font-size:20px;"><h2>没有找到匹配的数据!</h2></font></div>',
						tbar : { items : botton_arry },
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
				
				function showSetting(activitytemplatecode,ecactivitycode){
					$.get('${ctx}/EACustomization/Setting',{"activitytemplatecode": activitytemplatecode,"ecactivitycode":ecactivitycode},
			    		  function(html){
			    			$("#setting-panel").remove();
			    			if(html){
			    				var dialog = new BUI.Overlay.Dialog({
			    					id:'setting-dialog',
			    		            title:'参数设置',
			    		            width:420,
			    		            height:400,
			    		            mask:true,
			    		            bodyContent:html,
			    		            success:function () {
			    		            	var _self = this;
			    		            	$.ajax({
			    		    				type: "POST",
			    		    				url: "${ctx}/EACustomization/SaveSetting",
			    		    				data:$('#settingform').serialize(),
			    		    				dataType: "json",
			    		    				statusCode:{404:function(){
			    		    					BUI.Message.Alert("请求未发送成功",'error');
			    		    				}},
			    		    			    success: function(data) {
			    		    				    if(data.status == "1"){
			    		    				    	_self.close();
			    		    				    }else{
			    		    				    	BUI.Message.Alert(data.message,'warning');
			    		    				    }
			    		    			    }
			    		    			});
			    		            }
			    		          });
			    		      dialog.show();
			    			}
			    		 });
				}
			</script>
			<!-- script end -->
		</div>

	</div>
</body>
</html>