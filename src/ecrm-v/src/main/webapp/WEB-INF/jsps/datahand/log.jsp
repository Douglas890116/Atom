<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>数据同步日志</title>
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
					
					<div style="float: left; margin-right: 96px;">
						<div class="control-group span7">
							<label class="control-label">平台类型：</label>
							<select name=gametype>
			                    <option value="">--请选择--</option>
			                    <option value="begin.time.eibc">沙巴体育</option>
			                    <option value="begin.time.hq.bbin">BBIN</option>
			                    <option value="begin.time.hq.ebet">eBET真人</option>
			                    <option value="begin.time.hq.gb">GB彩票</option>
			                    <option value="begin.time.hq.gg">GG捕鱼</option>
			                    <option value="begin.time.hq.ggpoker">GG扑克</option>
			                    <option value="begin.time.hq.hab">哈巴电子</option>
			                    <option value="begin.time.hq.idn">IDN扑克</option>
			                    <option value="begin.time.hq.nhq">HY真人</option>
			                    <option value="begin.time.hq.og.og">OG东方真人</option>
			                    <option value="begin.time.hq.pt">PT游戏</option>
			                    <option value="begin.time.hq.qt">QT电子</option>
			                    <option value="begin.time.hq.sgs">申博</option>
			                    <option value="begin.time.hq.tgp">TGP申博</option>
			                    <option value="begin.time.mg">MG游戏</option>
			                    <option value="begin.time.qp">帝王棋牌</option>
			                    <option value="begin.time.sa">沙龙</option>
			                    <option value="begin.time.ttg">TTG电子</option>
			                    <option value="begin.time.zj">洲际</option>
			                    <option value="begin.time.hq.win88">WIN88 PT</option>
			                    <option value="begin.time.hq.m88">M88明升</option>
			                </select>
						</div>
						
						<div class="control-group span7">
							<label class="control-label">采集标志：</label>
							<select name=flag>
			                    <option value="">--请选择--</option>
			                    <option value="0">正常</option>
			                    <option value="1">数据异常</option>
			                </select>
						</div>
						
						
						<div class="control-group span7">
							<label class="control-label">活动名称：</label>
							<input name="activityname" type="text" class="control-text" placeholder="活动名称"/>
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
				
					var botton_arry = new Array();

					var Grid = BUI.Grid, Store = BUI.Data.Store, columns = [
					        {title : '流水号',width : '10%',sortable : true,dataIndex : 'lsh'},         
							{title : '数据同步平台任务',width : '10%',sortable : true,dataIndex : 'gametype'},                                                
							{title : '代理标识',width : '10%',sortable : true,dataIndex : 'agentaccount'},   
							{title : '最后更新时间',width : '15%',sortable : true,dataIndex : 'lasttime',renderer:BUI.Grid.Format.datetimeRenderer},
							{title : '状态',width : '10%',sortable : true, dataIndex : 'flag',renderer:function(value,obj){
								if(value == "0" ) {
									return "正常";
								} else {
									return "<font color='red'>数据异常</font>";
								}
							}},
							{title : '请求参数',width : '20%',sortable : true,dataIndex : 'dataparams'},
							{title : '结果信息',width : '30%',sortable : true,dataIndex : 'datalog',renderer:function(value,data){
								return value.length > 50 ?"<a class='activitycontent-tips'  title='"+value+"' >"+value.substring(0,25)+"...</a>":value;
							}},
							
							{title : '备注',width : '5%',sortable : false,dataIndex : 'remark'}
							];

					var store = new Store({
						url : '${ctx}/datahand/logdata',
						autoLoad : false,
						pageSize : 12,
						remoteSort: true,
						sortInfo : {
			                field : 'lasttime',
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
				
			</script>
			<!-- script end -->
		</div>

	</div>
</body>
</html>