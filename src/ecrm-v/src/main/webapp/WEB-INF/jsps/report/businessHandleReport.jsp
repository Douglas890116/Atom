<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body>
<div class="demo-content" style="overflow: hidde;">
<div class="detail-section" style="text-align: center;">
    <!-- <div style="margin:15px 300px 0px 0px; float: right;"> -->	
    <div class="sdateinfo">
      <h3 style="display: inline;">日期选择:&nbsp;</h3>
      <input name="begin_date" type="text" onclick="laydate()" class="calendar" placeholder="起始时间"  style="width: 125px;"/>-
      <input name="end_date" type="text"  onclick="laydate()" class="calendar" placeholder="结束时间" style="width: 125px;"/>
      <button id="btnSearch" type="submit"  class="button button-primary" onclick="businessHandleQuery()"><span class="icon-search icon-white"></span>搜 索 </button>
    </div>
    
	<!-- <div class="tips tips-small tips-notice">
      <span class="x-icon x-icon-small x-icon-warning"><i class="icon icon-white icon-volume-up"></i></span>
      <div class="tips-content">
      	<font>报表介绍:</font><br>
      	该报表统计一段时间内，订单审核时间在 3分钟、5分钟、10分钟、30分钟、60分钟及以上区间的订单数；可选操作条件【起始时间，结束时间】。
      </div>
    </div> -->
	<!-- <div style="text-align:left; margin:15px 0 0 15px; z-index: 20;"> -->
	<div class="srepinfo">
	<span class="label label-info">报表介绍:</span><br>
	    <div style="width: 240px; text-align: left; margin-top:10px;" class="tips tips-small tips-notice">
	        <span class="x-icon x-icon-small x-icon-warning"><i class="icon icon-white icon-volume-up"></i></span>
	        <div class="tips-content">该报表将会统计一段时间内，<br>订单审核时间在:<br>
	        	3分钟、5分钟、10分钟、30分钟、60分钟及以上区间的订单数；<br><br>
				可选操作条件【起始时间，结束时间】。</div>
	    </div>
    </div>
    <div id="canvas"></div>
</div>
  <script type="text/javascript">
   var chart;
    businessHandleQuery();
    
    function businessHandleQuery(){
    	if(null != chart || undefined != chart){
    		chart.clear();
    	}
    	var begin_date = $("input[name='begin_date']").val();
    	var end_date = $("input[name='end_date']").val();
    	$.ajax({
    		url:"${ctx}/report/businessHandleCount",
    		type:"post",
    		data:{startDate:begin_date,endDate:end_date},
    		dataType:"json",
    		success:function(data){
    			debugger
    			var array = new Array();
    			var color = ['#7179cb','#F60','#FF0','#0C0','#699','#06C'];
    			for(var i=0;i<data.length;i++){
  	  				//array.push({x : data[i].time,y : parseInt(data[i].mum),attrs : {fill : color[i]}});
  	  				if (i == 0){
  	  					array.push({name: data[i].time, y: parseInt(data[i].mum), sliced: true, selected: true});
  	  				} else {
	  	  				array.push([data[i].time, parseInt(data[i].mum)]);  	  					
  	  				}
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
    		      //xAxis : {type : 'category'},
    		      //seriesOptions : {columnCfg : {allowPointSelect : true,xField : 'x',item : {cursor : 'pointer',stroke : 'none'}}},
    		      seriesOptions : {pieCfg : {allowPointSelect : true,labels:{distance: 40, label: { }, renderer: function(value,item){ //格式化文本
                      return value + ' ' + (item.point.percent * 100).toFixed(2)  + '%';
                  }}}},
    		      //yAxis : {min : 0},
    		      tooltip : {
		            pointRenderer : function(point){
		             	return point.value;
		            }
		          },
    		      series: [ {
    		      	type: 'pie',
    		        name: '订单数量',
    		        visible: true,
    		        events : {
    		          itemclick : function (ev) {
    		        	  debugger;
    		            var  point = ev.point,startTime = point.xValue.split("-")[0]*100,entTime;
    		            if("5-分钟" == point.xValue){
    		            	entTime = 300;
    		            }else if("10-分钟" == point.xValue){
    		            	entTime = 500;
    		            }else if("30-分钟" == point.xValue){
    		            	entTime = 1000;
    		            }else if("60-分钟" == point.xValue){
    		            	entTime = 3000;
    		            }else if("60-分钟以上" == point.xValue){
    		            	entTime = 6000;
    		            	startTime = "";
    		            }
    		            top.topManager.openPage({
    		      		  id : 'queryBusinessHandleReportDetail',
    		      		  href : getRootPath()+'/report/businessHandleReportDetail?startDate='+begin_date+'&endDate='+end_date+'&startHandleTime='+startTime+"&endHandleTime="+entTime,
    		      		  title : '查看处理详情'
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