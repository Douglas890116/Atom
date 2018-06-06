<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>平台维护状态</title>
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
				
					var botton_arry = new Array();

					var Grid = BUI.Grid, Store = BUI.Data.Store, columns = [
							{title : '游戏平台',width : '8%',sortable : true,dataIndex : 'gametype'},                                                
							{title : '游戏平台名称',width : '13%',sortable : true,dataIndex : 'gamename'}, 
							{title : '状态',width : '13%',sortable : true, dataIndex : 'flag',renderer:function(value,obj){
								if(value == "1" ) {
									return "正常";
								} else if(value == "2" ) {
									return "<font color='red'>维护中</font>";
								} else if(value == "3" ) {
									return "<font color='red'>禁用</font>";
								}
							}},
							
							{title : '最后更新时间',width : '18%',sortable : true,dataIndex : 'lasttime',renderer:BUI.Grid.Format.datetimeRenderer},
							{title : '启用/禁用',width: '7%',sortable: false,dataIndex: 'flag',renderer:function(value,obj){
								//console.log("value--->"+value+"****obj--->"+obj.gametype);
								if(value == "1" ) {
									//console.log("value == 1");
									return '<button type="button" class="button botton-margin" style="background-color:#DEDBDB;" onclick=updateState("'+obj.gametype+'","'+value+'")><i class="icon-remove-circle icon-white"></i>禁用</button>';;
								}else if(value == "2"){
									/* return "<font color='red'>维护中</font>"; */
								} else if(value == "3" ) {
									//console.log("value == 3");
									return '<button type="button" class="button button-success botton-margin" style="background-color:#DEDBDB;" onclick=updateState("'+obj.gametype+'","'+value+'")><i class="icon-ok-circle icon-white"></i><b style="color:green;">启用</b></button>';;
								}
							}},
							{title : '备注说明',width : '30%',sortable : false,dataIndex : 'remark'}
							];

					var store = new Store({
						url : '${ctx}/datahand/statusdata',
						autoLoad : false,
						pageSize : 30,
						remoteSort: true,
						sortInfo : {
			                field : 'flag',
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
						/* tbar : {
							items : botton_arry
						}, */
						bbar : {
							pagingBar : true,
						}
					}),datepicker = new BUI.Calendar.DatePicker({
		              trigger:'.calendar-time',
		              showTime:true,
		              autoRender:true
		           });
					

				    $("#searchForm").submit(function(){
				    	  var obj = BUI.FormHelper.serializeToObject(this);
				          obj.start = 0;
				          store.load(obj);
				    	  return false;
				     }).submit();
					
				function updateState(gametype,flag){
					if(flag==1){
						flag=3;
					}else if(flag==3){
						flag=1;
					}
					var data={
							"gametype":gametype,
							"flag":flag
					};
					$.post("${ctx}/datahand/updateState",data,function(result){
						if(result){
							//window.location.href="${ctx}/datahand/status";
							location.reload(true);
						}else{
							alert("修改错误,请稍后再试!");
						}
					});
				}
				    
			</script>
			<!-- script end -->
		</div>

	</div>
</body>
</html>