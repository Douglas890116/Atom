<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body>
<div class="demo-content">
<div class="detail-section" style="text-align: center;">
    <div id="t2" class="srinfo" onclick="">
		查看报表介绍
	</div>
    <div style="margin:15px 0 0 200px; float: left;">
     日期选择:
      <input name="begin_date" type="text" onclick="laydate()" class="calendar" placeholder="起始时间"  style="width: 125px;"/>-
      <input name="end_date" type="text"  onclick="laydate()" class="calendar" placeholder="结束时间" style="width: 125px;"/>
      <button id="btnSearch" type="submit"  class="button button-primary" onclick="queryUserLoseWinRanking()"><span class="icon-search icon-white"></span>搜 索 </button>
    </div>
    <div id="canvas" style="margin:0px auto"></div>
    <input name="mark" value="${mark}" type="hidden" >
</div>
<script type="text/javascript">
	var chart;
	queryUserLoseWinRanking();
	function queryUserLoseWinRanking(){
		if(null != chart || undefined != chart)chart.clear();
		var result = new Array();
		var colums = new Array();
		var startDate = $("input[name='begin_date']").val();
		var endDate = $("input[name='end_date']").val();
		var mark = $("input[name='mark']").val();
						
		$.ajax({
			url:"${ctx}/report/queryUserLoseWinRanking",
			  type:"POST",
			  data:{mark:mark,startDate:startDate,endDate:endDate},
			  dataType:"json",
			  success:function(data){
				  debugger;
				  if(null==data || data.result.length==0)return false;
				  $("input[name='begin_date']").val(getYearMonthDate(data.result[0].start_date));
				  $("input[name='end_date']").val(getYearMonthDate(data.result[0].end_date));
				  for (var i = 0; i < data.result.length; i++) {
					  result.push((data.result[i].netMoney+"").replace("-",""));
					  colums.push(data.result[i].userName);
				  }
				  chart = new AChart({
			          theme : AChart.Theme.SmoothBase,
			          id : 'canvas',
			          forceFit : true, //自适应宽度
	    	          fitRatio : 0.4, // 高度是宽度的 0.4
	    	          plotCfg : {
	    	            margin : [50,50,80] //画板的边距
	    	          },
			          xAxis : {
			            categories: colums,
			            labels : {
      		              label : {rotate : -35,'text-anchor' : 'end'}
      		          	}
			          },
			          yAxis : {
			            min : 0
			          },
			          tooltip : {
			            shared : true
			          },
			          seriesOptions : {
			              columnCfg : {
			              }
			          },
			          series: [{
			                  name: (mark=='0'?'输(lose)':'赢(win)'),
			                  data: result.map(function(data){return +data}),
			                  events : {
      	  		          		itemclick : function (ev) {
      	  		        	  		debugger;
      	  		            		var point = ev.point;
      	  		            		var startDate = $("input[name='begin_date']").val();
      	  		    				var endDate = $("input[name='end_date']").val();
      	  		            		top.topManager.openPage({
      		    		      		  	id : 'userWinGameRecordDetailId',
      		    		      		  	href : getRootPath()+'/report/userLoseWinGameRecordDetail?mark='+mark+'&loginaccount='+point.xValue+'&startDate='+startDate+'&endDate='+endDate,
      		    		      		 	title : '游戏投注记录'
      		    		      		}); 
      	  		          		}
      	  		        	  }}]
			        });
			        chart.render();
			  }
		});
	}
	
	if ($("input[name='mark']").val()=='0'){
		srightBuiTip("#t2", "该报表将会统计某一段时间内，代理团队下前10个输钱最多的会员；<br>"+
				"可选操作条件【起始时间，结束时间】");
	} else if ($("input[name='mark']").val()=='1'){
		srightBuiTip("#t2", "该报表将会统计某一段时间内，代理团队下前10个赢钱最多的会员；<br>"+
				"可选操作条件【起始时间，结束时间】");
	}
</script>
</div>
</body>
</html>