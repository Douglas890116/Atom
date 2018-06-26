<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>后台首页</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="Thu, 19 Nov 1900 08:52:00 GMT">
<jsp:include page="../head.jsp" />
<style type="text/css">
td {
	word-wrap: break-word;
	word-break: normal;
}
</style>
</head>
<body class="sidebar-default header-fixed">
	<!--面包削导航开始-->
	<div id="title-breadcrumb-option-demo" class="page-title-breadcrumb">
		<div class="page-header pull-left">
			<div class="page-title">后台首页</div>
		</div>
		<ol class="breadcrumb page-breadcrumb">
			<li><i class="fa fa-home"></i> &nbsp; <a href="index.html">首页</a> &nbsp;&nbsp; <i class="fa fa-angle-right"></i> &nbsp;&nbsp;</li>
			<li class="active">后台首页</li>
		</ol>
		<div class="clearfix"></div>
	</div>
	<!--面包削导航结束-->
	<!--主体内容开始-->
	<div class="page-content">
		<div id="sum_box" class="row mbl">
			<div class="col-sm-6 col-md-3">
				<div class="panel profit db mbm">
					<div class="panel-body">
						<p class="icon">
							<i class="icon fa fa-dollar"></i>
						</p>
						<h4 class="value">
							<span></span> <span>$</span>
						</h4>
						<p class="description">输赢</p>
						<div class="progress progress-sm mbn">
							<div role="progressbar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100" style="width: 100%;" class="progress-bar progress-bar-success">
								<span class="sr-only">80% Complete (success)</span>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-6 col-md-3">
				<div class="panel income db mbm">
					<div class="panel-body">
						<p class="icon">
							<i class="icon fa fa-sun-o"></i>
						</p>
						<h4 class="value">
							<span></span> <span>$</span>
						</h4>
						<p class="description">充值总额</p>
						<div class="progress progress-sm mbn">
							<div role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 100%;" class="progress-bar progress-bar-info">
								<span class="sr-only">60% Complete (success)</span>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-6 col-md-3">
				<div class="panel task db mbm">
					<div class="panel-body">
						<p class="icon">
							<i class="icon fa fa-yen"></i>
						</p>
						<h4 class="value">
							<span></span> <span>$</span>
						</h4>
						<p class="description">取款总额</p>
						<div class="progress progress-sm mbn">
							<div role="progressbar" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100" style="width: 100%;" class="progress-bar progress-bar-danger">
								<span class="sr-only">50% Complete (success)</span>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-6 col-md-3">
				<div class="panel visit db mbm">
					<div class="panel-body">
						<p class="icon">
							<i class="icon fa fa-group"></i>
						</p>
						<h4 class="value">
							<span></span>
						</h4>
						<p class="description">团队余额</p>
						<div class="progress progress-sm mbn">
							<div role="progressbar" aria-valuenow="70" aria-valuemin="0" aria-valuemax="100" style="width: 100%;" class="progress-bar progress-bar-warning">
								<span class="sr-only">70% Complete (success)</span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="table-action" class="row">
			<div class="col-lg-12">
				<div id="tableactionTabContent" class="tab-content">
					<div id="table-table-tab" class="tab-pane fade in active">
						<div class="portlet box">
							<div class="portlet-header">
								<div class="caption">数据统计分析</div>
								<div class="tools">
									<i class="fa fa-chevron-up"></i>
								</div>
							</div>
							<div class="portlet-body">
								<div id="with-data-labels"></div>
							</div>
						</div>
						<div class="portlet box">
							<div class="portlet-header">
								<div class="caption">最近30天输赢数据统计分析</div>
								<div class="tools">
									<i class="fa fa-chevron-up"></i>
								</div>
							</div>
							<div class="portlet-body">
								<div id="with-data-paltform"></div>
							</div>
						</div>
						<a href="http://v1.hcharts.cn/demo/index.php" style="display: block; text-align: center; font-weight: bold; font-size: 18px;">点击查看更多的统计图表</a>
					</div>
				</div>
			</div>
		</div>
		<!--主体内容结束-->
	</div>
	
<jsp:include page="../script.jsp" />
<script src="${pageContext.request.contextPath }/resource/vendors/jquery-highcharts/exporting.js" type="text/javascript"></script>
<script type="text/javascript">
	//BEGIN LINE CHART
	var d1_1 = [ 1000, 950, 860, 500, 400, 100 ];
	var d1_2 = [ 10000, 9950, 5860, 8500, 4400, 7100 ];
	var d1_3 = [ 5000, 3950, 2860, 4500, 4000, 500 ];
	$('#with-data-labels').highcharts(
			{
				chart : {
					type : 'line'
				},
				title : {
					text : '<b>数据统计分析</b>'
				},
				subtitle : {
					text : ''
				},
				xAxis : {
					categories : [ '汇总', '本年', '本月', '前7天', '昨天', '今天' ]
				},
				yAxis : {
					title : {
						text : '数据统计分析'
					}
				},
				tooltip : {
					enabled : true,
					formatter : function() {
						return '<b>' + this.series.name + '</b><br/>' + this.x
								+ ': ' + this.y;
					}
				},
				plotOptions : {
					line : {
						dataLabels : {
							enabled : true
						},
						enableMouseTracking : true
					}
				},
				series : [ {
					name : '输',
					data : d1_1
				}, {
					name : '赢',
					data : d1_2
				}, {
					name : '充值',
					data : d1_3
				} ]
			});

	var all = 10000;
	var avg = 300;
	//BEGIN LINE CHART
	var d1_1 = [ 15, 20, 3, 4, 5, 6, 7, 80, 9, 100, 11, 102, 13, 104, 15, 160,
			17, 108, 19, 200, 21, 202, 230, 240, 205, 26, 27, 28, 209, 300 ];
	$('#with-data-paltform')
			.highcharts(
					{
						chart : {
							type : 'column'
						},
						title : {
							text : '<b>最近30天输赢报表</b>'
						},
						subtitle : {
							text : '总输赢：' + all + ' 平均：' + avg
						},
						xAxis : {
							categories : [ '1', '2', '3', '4', '5', '6', '7',
									'8', '9', '10', '11', '12', '13', '14',
									'15', '16', '17', '18', '19', '20', '21',
									'22', '23', '24', '25', '26', '27', '28',
									'29', '30' ]
						},
						yAxis : {
							min : 0,
							title : {
								text : '最近30天输赢统计'
							}
						},
						tooltip : {
							headerFormat : '<span style="font-size:10px">近{point.key}天</span><table>',
							pointFormat : '<tr><td style="color:{series.color};padding:0">{series.name}: </td>'
									+ '<td style="padding:0"><b>{point.y:.2f}</b></td></tr>',
							footerFormat : '</table>',
							shared : true,
							useHTML : true
						},
						plotOptions : {
							column : {
								pointPadding : 0.2,
								borderWidth : 0
							}
						},
						series : [ {
							name : '输赢',
							data : d1_1
						} ]
					});

	var profitObj = $(".profit h4 span:first-child");
	var incomeObj = $(".income h4 span:first-child");
	var taskObj = $(".task h4 span:first-child");
	var visitObj = $(".visit h4 span:first-child");

	//每隔几秒获
	function stat() {
		var trade = Math.floor(Math.random() * 10000);
		var pool = Math.floor(Math.random() * 10000);
		var recycle = Math.floor(Math.random() * 10000);
		var user = Math.floor(Math.random() * 10000);
		$(profitObj).html(trade);
		$(incomeObj).html(pool);
		$(taskObj).html(recycle);
		$(visitObj).html(user);

	}
	stat();
	var timerStat = window.setInterval(stat, 2000);//每隔2秒执行一次
</script>
</body>
</html>