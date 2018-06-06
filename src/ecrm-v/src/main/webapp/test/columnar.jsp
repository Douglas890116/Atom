<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>图形展示</title>
    <script type="text/javascript" src="${statics }/js/charts/acharts-min.js"></script>
</head>
<body>
 
<div class="detail-section">
    <div id="canvas">
 
    </div>
</div>

 
 
  <script type="text/javascript">
    var chart = new AChart({
      theme : AChart.Theme.Base,
      id : 'canvas',
      width : 950,
      height : 400,
      xAxis : {
        type : 'category'
      },
      seriesOptions : {
          columnCfg : {
            allowPointSelect : true,
            xField : 'x',
            item : {
              cursor : 'pointer',
              stroke : 'none'
            }
          }
      },
      yAxis : {
        min : 0
      },
      series: [ {
        name: '浏览器占比',
        events : {
          itemclick : function (ev) {
            var point = ev.point,
              //item = ev.item, //点击的项
              obj = point.obj; //point.obj是点击的配置项的信息
            alert(obj.x); //执行一系列操作
          }/*,
          itemselected : function(ev){ //选中事件
 
          },
          itemunselected : function(ev){ //取消选中事件
 
          }
          */
        },
        data: [
          {x : 'ie',y : 50,attrs : {fill : '#7179cb'}},
          {x : 'chrome',y : 30,attrs : {fill : '#6ed7ff'}},
          {x : 'firfox',y : 10,attrs : {fill : '#79c850'}},
          {x : 'other',y : 10,attrs : {fill : '#ffb65d'}},
        ]
      }]
 
    });
 
    chart.render();
  </script>
 
</body>
</html>