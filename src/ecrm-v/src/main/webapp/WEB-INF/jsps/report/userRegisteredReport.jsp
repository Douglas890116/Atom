<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body>
<div class="demo-content">
<div class="detail-section" style="text-align: center;">
	<div id="t2" class="srinfo" onclick="">
		查看报表介绍
	</div>
	
    <div style="margin:15px 0 0 300px; float:left;">
     <div class="control-group">
      <input type="radio" name="queryDate" value="1" onclick="userRegisteredQuery()" /> <span style="cursor: pointer;" class="label label-info" onclick="radioClick(this);">一周</span>
      <input type="radio" name="queryDate" value="2" onclick="userRegisteredQuery()" /> <span style="cursor: pointer;" class="label label-info" onclick="radioClick(this);">半个月</span>
      <input type="radio" name="queryDate" value="3" onclick="userRegisteredQuery()" /> <span style="cursor: pointer;" class="label label-info" onclick="radioClick(this);">一个月</span>
      <label class="control-label radio">
      </label>
     </div>
    </div>
    <div id="canvas" style="margin:0px auto"></div>
</div>
<script type="text/javascript">
	var chart;
	userRegisteredQuery();
	srightBuiTip("#t2", "该报表统计某一段时间内，代理团队的注册用户数、代理直线的注册用户数。将传入时间分成10个区间(最小的单位为天)进行汇总统计；<br>"+
			"可选操作条件【时间周期】。");
	function userRegisteredQuery(){
  		if(null != chart || undefined != chart){
    		chart.clear();
    	}
	  var member= new Array();
	  var user= new Array();
	  var showDate= new Array();
	  $.ajax({
		  url:"${ctx}/report/queryUserRegisteredReport",
		  type:"POST",
		  data:{queryDate:$("input[name='queryDate']:checked").val()},
		  dataType:"json",
		  success:function(data){
			  if(null!=data){
        		  debugger;
        		  for(var i=0;i<data.length;i++){
        			  if((data.length/2)>i){
        				  showDate.push(getYearMonthDate(data[i].startDate));
        				  member.push(data[i].num);
        			  }else{
        				  user.push(data[i].num); 
        			  }
        		  }
        		  chart = new AChart({
        		        theme : AChart.Theme.SmoothBase,
        		        id : 'canvas',
        		        forceFit : true, //自适应宽度
          	          	fitRatio : 0.4, // 高度是宽度的 0.4
        		        plotCfg : {
        		            margin : [80,120] //画板的边距
        		        },
        		        xAxis : {
        		            categories : showDate,
        		            labels : {
          		              label : {rotate : -35,'text-anchor' : 'end'}
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
        		            //valueSuffix : '°C',
        		            shared : true, //是否多个数据序列共同显示信息
        		            crosshairs : true, //是否出现基准线
        		        },
        		        series : [{
        	                markers : {
        	                    events : {
        	                      markerclick : function  (ev) {
        	                    	  debugger
        	                    	  var  point = ev.point;
        	                    	  if(point.value > 0){
         	  		            		 top.topManager.openPage({
         		    		      		  	id : 'queryRegisteredAllUserDetail',
         		    		      		  	href : getRootPath()+'/EEmployee/userJsp/employeeData?createDate='+point.xValue,
         		    		      		 	title : '团队用户'
         		    		      		});
        	                    	  }
        	                      }
        	                    },
        	                },
        		            type: 'line',
        		            name: '团队用户数',
        		            data: user.map(function(member_data){
            	            	  return +member_data
            	            })
        		        }, {
          	                markers : {
        	                    events : {
        	                      markerclick : function  (ev) {
        	                    	var  point = ev.point;
        	                    	 if(point.value > 0){
        	                    		 top.topManager.openPage({
          		     		      		  	id : 'queryRegisteredAllMemberDetail',
          		     		      		  	href : getRootPath()+'/EEmployee/userJsp/index?createDate='+point.xValue,
          		     		      		  	title : '直线用户'
          		     		      		});
        	                    	 }
        	                      }
        	                    },
        	                },
        		            type: 'line',
        		            name: '直线用户数',
        		            data: member.map(function(member_data){
	          	            	  return +member_data
	          	            }),
        		        }],
        		        
        		    });
        		    chart.render();
        	  }
		  }
	  });
	}
	
	function radioClick(eve){
		$(eve).prev().attr("checked", "checked");
		userRegisteredQuery();
	}
</script>
</div>
</body>
</html>