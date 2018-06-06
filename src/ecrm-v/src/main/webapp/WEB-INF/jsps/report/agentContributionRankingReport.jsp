<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body>
<div class="demo-content">
<div class="detail-section" style="text-align: center;">
    
    <div class="sdateinfo">
      <h3 style="display: inline;">日期选择:&nbsp;</h3>
      <input name="begin_date" type="text" onclick="laydate()" class="calendar" placeholder="起始时间"  style="width: 125px;"/>-
      <input name="end_date" type="text"  onclick="laydate()" class="calendar" placeholder="结束时间" style="width: 125px;"/>
      <button id="btnSearch" type="submit"  class="button button-primary" onclick="queryAgentContributionRankingReport()"><span class="icon-search icon-white"></span>搜 索 </button>
    </div>
    <div class="srepinfo">
		<span class="label label-info">报表介绍:</span><br>
	    <div style="width: 300px; text-align: left; margin-top:10px;" class="tips tips-small tips-notice">
	        <span class="x-icon x-icon-small x-icon-warning"><i class="icon icon-white icon-volume-up"></i></span>
	        <div class="tips-content">统计某一段时间内，<font color='red'>代理团队下前10个的直系代理的盈利</font>，进行排名；<br>
		   		计算方式： 盈利 = 游戏输赢金额+洗码+分红-占成<br>
		   		---------------------------------------------------------<br>
		        	洗码 = 有效投注额 * 各个平台各个不同类型游戏的洗码值<br>
		               	分红 = (游戏输赢金额 + 洗码 ) * 分红百分比<br>
		             	占成 = (游戏输赢金额 + 洗码 + 分红) * 占成百分比<br>
		   		---------------------------------------------------------<br><br>
		   可选操作条件【起始时间，结束时间】</div>
	    </div>
    </div>
    
    <div id="canvas" style="margin:0px auto"></div>
</div>
  <script type="text/javascript">
   var chart;
   queryAgentContributionRankingReport();
   
    function queryAgentContributionRankingReport(){
    	if(null != chart || undefined != chart){
    		chart.clear();
    	}
    	var begin_date = $("input[name='begin_date']").val();
    	var end_date = $("input[name='end_date']").val();
    	$.ajax({
    		url:"${ctx}/report/queryAgentContributionRankingReport",
    		type:"post",
    		data:{startDate:begin_date,endDate:end_date},
    		dataType:"json",
    		success:function(data){
    			debugger
    			var array = new Array();
    			var employeecode = new Map();
    			for(var i=0;i<data.length;i++){
    				if (i == 0){
	  					array.push({name: data[i].loginaccount, y: parseInt(Math.abs(data[i].profit_amount)), sliced: true, selected: true});
	  				} else {
  	  					array.push([data[i].loginaccount, parseInt(Math.abs(data[i].profit_amount))]);  	  					
	  				}
	  				employeecode.set(data[i].loginaccount,data[i].employeecode);
    			}
    			array.join(",");
    			chart = new AChart({
    		      theme : AChart.Theme.SmoothBase,
    		      id : 'canvas',
    		      forceFit : true, //自适应宽度
    	          fitRatio : 0.4, // 高度是宽度的 0.4
    	          plotCfg : {
    	            margin : [50,120 ] //画板的边距
    	          },
    	          seriesOptions : {pieCfg : {allowPointSelect : true,labels:{distance: 40, label: { }, renderer: function(value,item){ //格式化文本
	                  return value + ' ' + (item.point.percent * 100).toFixed(2)  + '%';
	              }}}},
		          tooltip : {
		            pointRenderer : function(point){
		            	//return (point.percent * 100).toFixed(2)+ '%';
		            	return point.value;
		            }
		          },
    		      series: [ {
    		    	type: 'pie',
    		        name: '代理贡献',
    		        events : {
    		          itemclick : function (ev) {
    		        	  debugger;
    		            var  point = ev.point;
    		            top.topManager.openPage({
    		      		  id : 'agentContributionRankingDetail',
    		      		  href : getRootPath()+'/report/agentContributionRankingDetail?employeecode='+employeecode.get(point.xValue),
    		      		  title : '代理贡献明细'
    		      		});
    		          }
    		        },
    		        data:array
    		       }]
    		    });
    		    chart.render();
    		}
    	});
  }
</script>
</div>
</body>
</html>