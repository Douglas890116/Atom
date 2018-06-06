<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>图形展示</title>
</head>
<body>
 
<div class="detail-section">
    <div id="canvas">
 
    </div>
</div>
 
<script src="http://g.tbcdn.cn/bui/acharts/1.0.32/acharts-min.js"></script>
<!-- https://g.alicdn.com/bui/acharts/1.0.29/acharts-min.js -->
 
 
<script type="text/javascript">
    var chart = new AChart({
        theme : AChart.Theme.Base,
        id : 'canvas',
        width : 950,
        height : 500,
        plotCfg : {
          margin : [50,50,80] //画板的边距
        },
        xAxis : {
          categories : ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月']
        },
        tooltip : {
          valueSuffix : '°C',
          shared : true, //是否多个数据序列共同显示信息
          crosshairs : true //是否出现基准线
        },
        series : [{
            name: 'Tokyo',
            data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
        }, {
            name: 'London',
            data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
        }, {
            name: 'Sahnghai',
            data: [17.0, 16.9, 19.5, 24.5, 28.2, 31.5, 35.2, 36.5, 33.3, 28.3, 23.9, 19.6]
        }]
    });
 
    chart.render();
</script>
 
</body>
</html>