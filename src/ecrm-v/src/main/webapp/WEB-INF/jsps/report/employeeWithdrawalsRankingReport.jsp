<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body>
<div class="demo-content">
<div class="detail-section" style="text-align: center;">
    
    <div class="sdateinfo">
      <h3 style="display: inline;">日期选择:&nbsp;</h3>
      <input name="begin_date" type="text" onclick="laydate()" class="calendar" placeholder="起始时间"  style="width: 125px;"/>-
      <input name="end_date" type="text"  onclick="laydate()" class="calendar" placeholder="结束时间" style="width: 125px;"/>
      <button id="btnSearch" type="submit"  class="button button-primary" onclick="withdrawalsRankingQuery()"><span class="icon-search icon-white"></span>搜 索 </button>
    </div>
    <div class="srepinfo">
		<span class="label label-info">报表介绍:</span><br>
	    <div style="width: 240px; text-align: left; margin-top:10px;" class="tips tips-small tips-notice">
	        <span class="x-icon x-icon-small x-icon-warning"><i class="icon icon-white icon-volume-up"></i></span>
	        <div class="tips-content">该报表将会统计某一段时间内，<br>
	        	代理直线取款次数最多的前10个用户,根据取款次数排倒序；<br><br>
    			可选操作条件【起始时间，结束时间】。</div>
	    </div>
    </div>
    
    <div id="canvas" style="margin:0px auto"></div>
</div>
  <script type="text/javascript">
    var chart;
    withdrawalsRankingQuery();
    
    function withdrawalsRankingQuery(){
    	if(null != chart || undefined != chart){
    		chart.clear();
    	}
    	var begin_date = $("input[name='begin_date']").val();
    	var end_date = $("input[name='end_date']").val();
    	$.ajax({
    		url:"${ctx}/report/queryEmployeeWithdrawalsRankingReport",
    		type:"post",
    		data:{startDate:begin_date,endDate:end_date},
    		dataType:"json",
    		success:function(data){
    			debugger
    			var array = new Array();
    			var employeecode = new Map();
    			
    			for(var i=0;i<data.length;i++){
  	  				if (i == 0){
	  					array.push({name: data[i].loginaccount, y: data[i].orderamount, sliced: true, selected: true});
	  				} else {
  	  					array.push([data[i].loginaccount, data[i].orderamount]);  	  					
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
		            	return point.value + '元';
		            }
		          },
    		      series: [ {
    		    	type: 'pie',
    		        name: '取款金额',
    		        events : {
    		          itemclick : function (ev) {
    		        	  debugger;
    		            var  point = ev.point;
    		            top.topManager.openPage({
    		      		  id : 'queryEmployeeWithdrawalsRankingDeatil',
    		      		  href : getRootPath()+'/report/employeeWithdrawalsRankingReportDetail?employeecode='+employeecode.get(point.xValue)+"&orderStartDate="+begin_date+"&orderEndDate="+end_date,
    		      		  title : '取款明细'
    		      		});
    		          }/*,
    		          itemselected : function(ev){ //选中事件
    		 
    		          },
    		          itemunselected : function(ev){ //取消选中事件
    		 
    		          }
    		          */
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