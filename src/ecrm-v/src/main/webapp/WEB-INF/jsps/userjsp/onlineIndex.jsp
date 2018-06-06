<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>在线用户列表</title>
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
					
		             
		             <div class="control-group span14">
		             	<label class="control-label">用户账号：</label>
						<div class="controls">
							<input name="loginaccount" type="text" class="control-text" value="${param.loginaccount }" placeholder="用户账号"/>
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
			
			function showUserData(employeecode,loginaccount) {
				openNewWindow('showshowuserdatadetail','${ctx}/EEmployee/userJsp/allinfo?employeecode='+employeecode+'&loginaccount='+loginaccount,'会员详细信息');
			}
			
				(function() {
					var botton_arry = new Array();

					var Grid = BUI.Grid, Store = BUI.Data.Store, columns = [
							{title : '会员编码',width : '10%',sortable : true,dataIndex : 'employeecode'},                                                
							{ title: '会员账号 ',width: '10%',dataIndex:'loginaccount',elCls:'center',sortable: false,renderer:function(value,obj){
								return "<a href=javascript:showUserData('"+obj.employeecode+"','"+obj.loginaccount+"') style='font-size:16px;' title='查看汇总信息'>"+value+"</a>";
						    }},
							{title : '会员姓名',width : '10%',sortable : true,dataIndex : 'displayalias'},
							{title : '上级代理',width : '10%',sortable : true,dataIndex : 'parentemployeeaccount'},
							{title : '登录设备',width : '10%',sortable : true,dataIndex : 'enterprisename'},
							{title : '最后操作时间',width : '15%',sortable : true,dataIndex : 'lastlogintime',renderer:BUI.Grid.Format.datetimeRenderer},
							];

					var store = new Store({
						url : '${ctx}/EEmployee/onlineData',
						autoLoad : false,
						pageSize : 50,
						remoteSort: true
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
				
				
			</script>
			<!-- script end -->
		</div>

	</div>
</body>
</html>