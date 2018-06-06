<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body>
<div class="demo-content">
<div class="detail-section" style="text-align: center;">
	<div id="t2" class="srinfo" onclick="">
		查看报表介绍
	</div>
	
    <div id="canvas" style="margin:0px auto"></div>
</div>
  <script type="text/javascript">
   var chart;
    businessHandleQuery();
    srightBuiTip("#t2", "该报表统计用户直线下级三天以上，一周以上，半个月以上，一个月以上，三个月以上未登录系统的用户，<br>"+
		       "各个级别之间不重复统计，例如一周未登陆的用户不能出现在3天未登陆的用户里面；<br>"+
		       "点击具体时间段图形，可进入列表页查看详细数据。");
    function businessHandleQuery(){
    	if(null != chart || undefined != chart){
    		chart.clear();
    	}
    	$.ajax({
    		url:"${ctx}/report/queryEmployeeActivityReport",
    		type:"post",
    		dataType:"json",
    		success:function(data){
    			debugger
    			var array = new Array();
    			var showDays = new Array();
    			//var color = ['#7179cb','#F60','#FF0','#0C0','#699','#06C',':#909'];
    			for(var i=0;i<data.length;i++){
  	  				//array.push({x : data[i].days,y : data[i].num/1,attrs : {fill : color[i], style : "width:25px;"}});
  	  				showDays.push(data[i].days);
  	  				array.push(data[i].num);
    			}
    			//array.join(",");
    			chart = new AChart({
    		      theme : AChart.Theme.SmoothBase,
    		      id : 'canvas',
    		      forceFit : true, //自适应宽度
    	          fitRatio : 0.4, // 高度是宽度的 0.4
    	          plotCfg : {
    	            margin : [90,130 ] //画板的边距
    	          },
    	          xAxis : {
  		            categories : showDays,
  		            labels : {
    		              /* label : {rotate : -35,'text-anchor' : 'end'} */
    		          }
  		          },
	        	  legend : {
 		              dy : 10, //跟绘图区域的偏移y
 		              dx : 10,
 		              align : 'top',//top,left,right,bottom(默认)
 		              layout : 'horizontal',//默认 horizontal
 		              back : { //背景的配置信息，等同于shape的attrs
 		                fill : '#efefef'//stroke : null 清除边框
 		              },
 		              spacingX : 30
 		          }, 
 		          tooltip : {
 		            shared : true, 
 		            crosshairs : true,
 		          },
    		      //seriesOptions : {columnCfg : {allowPointSelect : true,xField : 'x',item : {cursor : 'pointer',stroke : 'none'}}},
    		      //yAxis : {min : 0},
    		      series : [{
   	                markers : {
	                    events : {
	                      markerclick : function  (ev) {
	                    	  debugger
	                    	  var  point = ev.point;
	                    	  if(point.value > 0){
 	  		            		 top.topManager.openPage({
 		    		      		  	id : 'queryActivityReportDetail',
 		    		      		  	href : getRootPath()+'/EEmployee/userJsp/employeeData?createDate='+point.xValue,
 		    		      		 	title : '未登陆的用户'
 		    		      		});
	                    	  }
	                      }
	                    },
   	                },
   		            type: 'line',
   		            name: '未登陆的用户数',
   		            data: array.map(function(member_data){
       	            	  return +member_data
       	            })
		          }]
    		      /* series: [ {
    		        name: '客户活跃统计报表',
    		        events : {
    		          itemclick : function (ev) {
    		        	  debugger;
    		            var  point = ev.point,startTime = point.xValue,loginDate;
    		            if("三天内" == point.xValue){
    		            	loginDate = 3;
    		            }else if("七天内" == point.xValue){
    		            	loginDate = 7;
    		            }else if("十五天内" == point.xValue){
    		            	loginDate = 15;
    		            }else if("一个月内" == point.xValue){
    		            	loginDate = 30;
    		            }else if("三个月内" == point.xValue){
    		            	loginDate = 90;
    		            }else if("三个月以上" == point.xValue){
    		            	loginDate = 91;
    		            }
    		            top.topManager.openPage({
      		      		  id : 'queryActivityReportDetail',
      		      	  	  href : getRootPath()+'/report/employeeActivityDetail?loginDate='+loginDate,
      		      		  title : '未登陆的用户'
      		      		});  
    		          }
    		        }, 
    		        data:array
    		       }]*/
    		    });
    		    chart.render();
    		    $("#canvas").children().css({marign:'100px auto'});
    		}
    	});
  }
    
</script>
</div>
</body>
</html>