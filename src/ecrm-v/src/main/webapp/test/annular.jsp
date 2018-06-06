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
 
<!-- <input type="button" value="Change" onclick="changethisreport()"> -->
 
 
 
       <script type="text/javascript">
        var chart = new AChart({
          theme : AChart.Theme.SmoothBase,
          id : 'canvas',
          width : 950,
          height : 500,
          legend : null ,//不显示图例
          seriesOptions : { //设置多个序列共同的属性
            pieCfg : {
              allowPointSelect : false,
              labels : null,
              innerSize : '60%' //内部的圆，留作空白
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
              data: [
                ['Top10',   75.0],
                ['其他',       25.0]
              ]
          }]
        });
 
        chart.render();
      </script>
</body>
</html>