<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>图形展示</title>
    <script type="text/javascript" src="${statics }/js/jquery-1.8.1.min.js"></script>
    <script type="text/javascript" src="${statics }/js/charts/acharts-min.js"></script>
</head>
<body>
 
<div class="detail-section">
    <div id="canvas">
 
    </div>
</div>
 
<input type="button" value="Change" onclick="changethisreport()">
 
 
 
      <script type="text/javascript">
        var chart = new AChart({
          theme : AChart.Theme.Base,
          id : 'canvas',
          width : 950,
          height : 500,
          title : {
            text : '浏览器分布图'
          },
          legend : null ,//不显示图例
          seriesOptions : { //设置多个序列共同的属性
            pieCfg : {
              allowPointSelect : true,
              labels : {
                distance : 40,
                label : {
                  //文本信息可以在此配置
                },
                renderer : function(value,item){ //格式化文本
                  return value + ' ' + (item.point.percent * 100).toFixed(2)  + '%';
                }
              }
            }
          },
          tooltip : {
            pointRenderer : function(point){
              return (point.percent * 100).toFixed(2)+ '%';
            }
          },
          series : [{
              type: 'pie',
              name: 'Browser share',
              legendType : 'circle', //显示在legend上的图形
              legend : {
                position : 'bottom', //位置
                back : null, //背景清空
                spacingX : 15, //增加x方向间距
                itemCfg : { //子项的配置信息
                  label : {
                    fill : '#999',
                    'text-anchor' : 'start',
                    cursor : 'pointer'
                  }
                }
              },
              events : {
                itemclick : function (ev) {
                  var point = ev.point
                    //item = ev.item, //点击的项
                  console.log(point); //执行一系列操作
                },
                //选中事件
                itemselected : function(ev){
                  console.log(ev.point.xValue + ' selected');
                },
                //取消选中
                itemunselected : function(ev){
                  console.log(ev.point.xValue + ' unselected');
                }
              },
              data: [
                ['Firefox',   45.0],
                ['IE',       -26.8],
                ['Chrome',  12.8],
                /* {
                    name: 'Chrome',
                    y: 12.8,
                    sliced: true,
                    selected: true
                }, */
                ['Safari',    8.5],
                ['Opera',     6.2],
                ['Others',   0.7]
              ]
          }]
        });
 
        chart.render();
        
        function changethisreport(){
        	var data2 = [
        	                ['Firefox',   45.0],
        	                ['IE',       26.8],
        	                ['Safari',    8.5],
        	                ['Opera',     6.2],
        	                ['Others',   13.5]
        	              ];
        	$("#canvas").html("");
        	chart._attrs.series[0].data=data2;
        	chart.render();
        }
      </script>
</body>
</html>