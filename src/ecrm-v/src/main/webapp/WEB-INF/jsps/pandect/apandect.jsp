<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<!DOCTYPE>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>总览</title>
	<style type="text/css">
		body,ul,li,ol,p,h1,h2,h3{ list-style:none; padding:0; margin:0;}
    	.center_body{ width:100%; }
		.clear{ clear:both}
		.c_b_top{background:#e8e9ee; height:103px; border-radius:6px;width:99%; margin:0 auto; overflow:hidden; margin-top:12px;}
		.c_b_center{background:#e8e9ee;  border-radius:6px;width:99%; margin:0 auto; overflow:hidden; margin-top:12px; padding-bottom:20px;}
		.c_b_center_tab{width:98%; margin:0 auto; margin-top:12px;}
		.c_b_center_tab ul li{ float:left; font-family:"微软雅黑"; text-align:center; font-size:14px; color:#333; width:111px; height:29px; line-height:29px; background:url(${statics}/img/repost_tab_bg.png) left no-repeat; cursor:pointer}
		.c_b_center_tab ul li.hover{ background:url(${statics}/img/repost_tab_bg_hover.png) left no-repeat;}
		.c_b_center_souy{width:98%; background:#fff; margin:0 auto; border:1px #d3d3db solid; overflow:hidden}
		.licst{width: 99%;
    margin: 0 auto;
    overflow: hidden;
    background: #e8e9ee; margin-bottom: 10px;
    margin-top: 10px;}
	
		.licst ol li{ float:left; width:33.2%; text-align:center; font-family:"微软雅黑"; font-size:28px; color:#333; line-height:50px; color:#1b2352}
		.cb_tcent{ height:76px; width:98%; background:#fff; margin:0 auto; margin-top:12px;}
		.cb_tcent ul { border:1px #a0a0a0 solid;overflow:hidden}
		.cb_tcent ul li.foeu{ border:none}
		.cb_tcent ul li{ border-right:1px #a0a0a0 solid; width:24.9%; float:left; height:76px; line-height:76px; color:#666; text-indent:2em;font-family:"微软雅黑"; position:relative}
		.cb_tcent ul li font{ color:red; font-weight:bold; font-size:22px; }
		.cb_tcent ul li span{ display:block; float:left; background:#f65c5c; width:15%; color:#fff;text-align: center;text-indent: 0;}
		.cb_tcent ul li b{ float:left; display:block; background:#3993ff; width:10%; color:#fff; font-weight:normal;    text-align: center;text-indent: 0;}
		.cb_tcent ul li p{ float:left; z-index:1000}
		.c_b_center_souy ul li{ width:33.2%; border-right:1px #ddd solid; height:468px; float:left}
		.c_b_center_souy ul li.bnoe{ border:none;}
		.c_b_center_souy ul li p{ float:left; padding-top:15px;box-sizing: border-box;-webkit-box-sizing: border-box;-moz-box-sizing: border-box;}
		.c_b_center_souy ul li p span{ display:block; font-family:"微软雅黑"; font-size:16px; line-height:30px;}
		.c_b_center_souy ul li p span font{ color:red;}
		.wu_wan{ width: 100%;margin:0 auto;margin-top:-55px;text-align: center;}
		.wu_wan span{ line-height:30px; font-size:12px; display:block; float:left; padding-left:10px; color:#333}
    </style>
    <script type="text/javascript">
    	 function setTab(name,cursel,n){
			 for(i=1;i<=n;i++){
			  var menu=document.getElementById(name+i);
			  menu.className=i==cursel?"hover":"";
			 }
		}
    </script>
</head>

<body>
	<div class="center_body">
    
    	<div class="c_b_top">
        	<div class="cb_tcent">
            	<ul>
                	<li><span>赢</span><b>输</b><p><font>￥</font><font id="teamLoseWin">---- ----</font></p></li>
                	<li>充值总额：<font>￥</font><font id="teamSaveMoney">---- ----</font></li>
                	<li>取款总额：<font>￥</font><font id="teamTakeMoney">---- ----</font></li>
                	<li class="foeu">团队余额：<font>￥</font><font id="teamBalance">---- ----</font></li>
                </ul>
                
            	<ul>
                	<li>冲正统计：<font>￥</font><font id="integralPlus">---- ----</font></p></li>
                	<li>冲负统计：<font>￥</font><font id="integralMinus">---- ----</font></li>
                	<li>优惠额：   <font>￥</font><font id="discountAmount">---- ----</font></li>
                	<li class="foeu">洗码额：<font>￥</font><font id="washingAmount">---- ----</font></li>
                </ul>
            </div>
        </div>
        
        <!--综合报表菜单-->
        <div class="c_b_center">
        	<div class="c_b_center_tab">
            	<ul>
                	<li id="repost1" onclick="setTab('repost',1,6)"  data-date="today" class="hover reportswithcard">今日</li>
                	<li id="repost2" onclick="setTab('repost',2,6)"  data-date="yesterday" class="reportswithcard" >昨日</li>
                	<li id="repost3" onclick="setTab('repost',3,6)"  data-date="thisweek" class="reportswithcard">本周</li>
                	<li id="repost4" onclick="setTab('repost',4,6)"  data-date="lastweek" class="reportswithcard">上周</li>
                	<li id="repost5" onclick="setTab('repost',5,6)"  data-date="thismonth" class="reportswithcard">本月</li>
                	<li id="repost6" onclick="setTab('repost',6,6)"  data-date="lastmonth" class="reportswithcard">上个月</li>
                </ul>
            </div>
            <div class="clear"></div>
        	<div class="c_b_center_souy" id="con_repost_1">
            	<ul>
                	<li>
                    	<p id="teamPerformanceCanvas" style="width: 100%;">
                    	</p>
                        <!-- <p id="teamPerformanceTexts" style="width: 40%; padding-top: 85px;">
                        	<span>--：<font>---- ---- ----</font></span>
                        	<span>--：<font>---- ---- ----</font></span>
                        	<span>--：<font>---- ---- ----</font></span>
                        	<span>--：<font>---- ---- ----</font></span>
                        	<span>--：<font>---- ---- ----</font></span>
                        	<span>--：<font>---- ---- ----</font></span>
                        	<span>--：<font>---- ---- ----</font></span>
                        	<span>--：<font>---- ---- ----</font></span>
                        	<span>--：<font>---- ---- ----</font></span>
                        	<span>--：<font>---- ---- ----</font></span>
                        </p> -->
                    </li>
                	<li>
                    	<p id="teamPerformanceBrandCanvas" style="width: 100%;">
                    	</p>
                       <!--  <p id="teamPerformanceBrandTexts" style="width: 40%;padding-top: 85px;">
                        	<span>--：<font>---- ---- ----</font></span>
                        	<span>--：<font>---- ---- ----</font></span>
                        	<span>--：<font>---- ---- ----</font></span>
                        	<span>--：<font>---- ---- ----</font></span>
                        	<span>--：<font>---- ---- ----</font></span>
                        	<span>--：<font>---- ---- ----</font></span>
                        	<span>--：<font>---- ---- ----</font></span>
                        	<span>--：<font>---- ---- ----</font></span>
                        	<span>--：<font>---- ---- ----</font></span>
                        	<span>--：<font>---- ---- ----</font></span>
                        </p> -->
                    </li>
                    
                	<li class="bnoe">
                    	<p id="teamPerUseractivityCanvas" style="width: 100%;padding-top:45px;"></p>
                       	<div class="clear"></div>
                        <div class="wu_wan">
                        	<a href="##" style="display: block;width: 80%;margin: 0 auto;text-decoration: none;color:#0E8BB9;font-size: 14px;">
							报表统计了三天以上，一周以上，半个月以上，一个月以上，三个月以上未登录系统的用户，各个周期之间不重复统计
							</a>
                        </div>
                    </li>
                </ul>
                <div class="clear"></div>
                  <div class="licst">
                		<ol>
                            <li>代理贡献</li>
                            <li>品牌贡献</li>
                            <li>客户活跃度</li>
               		 </ol>
      			  </div>
            </div>
        </div>
        <!--综合报表菜单End-->
       
    </div>
<script type="text/javascript" src="${statics }/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript" src="${statics }/js/charts/acharts-min.js"></script>
<script type="text/javascript" src="${statics }/js/custom/commons-min.js"></script>
<script type="text/javascript">
	var teamreport = function(){
		this.stat = function(idselector,data){
		    if(data.status == "1"){
		    	$("#"+idselector).html(data.message);
		    }else{
		    	$("#"+idselector).html("---- ----");
		    }
		}
		this.toprl = function(picidselector,textidselector,url,param){
			$("#"+picidselector).html("");
	    	var chart = new AChart({
	            theme : AChart.Theme.SmoothBase,
	            id : picidselector,
	            forceFit : true, //自适应宽度
	            fitRatio : 1, // 高度是宽度的 0.4
	            plotCfg : {
	                margin : [10] //画板的边距
	              },
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
	          var d = '75%';
	          var canvas = chart._attrs.canvas;
	          var text =  canvas.addShape('text',{
		            text : d,
		            x : '50%',
		            y : '56%',
		            fill : 'black',
		            'font-size':60,
		            'font-weight' : 'bold'
		          }); 
		}
		this.circle = function(picidselector,resultdata){
			$("#"+picidselector).html("");
			
			var ddata = eval("["+resultdata.message.data+"]");
			
			if(resultdata.status/1==1){
				var chart = new AChart({
					theme : AChart.Theme.SmoothBase,
			          id : picidselector,
			          forceFit : true, //自适应宽度
			          height : 400,
			          plotCfg : {
			            margin : [50,50,80], //画板的边距
			            border: {
			                  fill: '#F5F5F5'
			              }
			          },
			          xAxis : {
			            categories: 
			            	resultdata.message.title.split(",")
			            	//['sssssss','百乐门','梦溪游']
			            ,
			            labels : {
			              label : {
			                rotate : -45,
			                'text-anchor' : 'end'
			              }
			            }
			          },
			          yAxis : {
			        	//min : 0  可以没有最小值
			          },
			          seriesOptions : { //设置多个序列共同的属性
			            /*columnCfg : { //公共的样式在此配置
			 
			            }*/
			          },
			          tooltip : {
			            valueSuffix : '元'
			          },
			          series : [ {
			            name: '',
			            type : 'column',
			            data: ddata,
			            //data: [0.01,0.01,0.01],
			            labels : { //显示的文本信息
			              label : {
			            	  rotate : -90,
				                y : 10,
				                'fill' : '#000000',
				                'text-anchor' : 'end',
				                textShadow: '0 0 3px black',
				                'font-size' : '14px'
			              }
			            }
			 
			          }]
			 
			        });		
		    	chart.render();
			}else{
				$("#"+picidselector).html("<div style='position: relative;font-size:63px;margin-top:50%;margin-left:50%;left:-98px;top:-100px;color:rgb(242, 242, 242);'>无数据<div>");
			}
		}
		this.polyline = function(picidselector,resultdata){
				$("#"+picidselector).html("");
				if(resultdata.status/1==1){
		    		var chart = new AChart({
				        theme : AChart.Theme.SmoothBase,
				        id : picidselector,
				        forceFit : true, //自适应宽度
			            fitRatio : 0.6, // 高度是宽度的 0.4
			            legend : null ,//不显示图例
				        plotCfg : {
				          margin : [50,50,80] //画板的边距
				        },
				        xAxis : {
				          categories : ['0-3天','3-7天','7-15天','15-30天','30-90天','90天以上']
				        },
				        tooltip : {
				          valueSuffix : '个',
				          shared : true, //是否多个数据序列共同显示信息
				          crosshairs : true //是否出现基准线
				        },
				        series : [{
				        	 name: '登录用户数',
				            data: resultdata.message
				        }]
				    });
				    chart.render();
				}else{
					$("#"+picidselector).html("<div style='position: relative;font-size:63px;margin-top:50%;margin-left:50%;left:-98px;top:-135px;color:rgb(242, 242, 242);'>无数据<div>");
				}
		}
		this.getData = function(pictype,picidselector,url,param,bindelement){
			var __self = this;
			$.ajax({
				type: "POST",
				url: url,
				data:param,
				dataType: "json",
			    success: function(ajaxdata) {
			    	var binddata = $("body").data(bindelement);
			    	if(!binddata) binddata = {};
		    		binddata[picidselector]=ajaxdata;
			    	$("body").data(bindelement,binddata);
			    	if(pictype=="polyline"){
			    		__self.polyline(picidselector,ajaxdata);
			    	}else if(pictype=="circle"){
			    		__self.circle(picidselector,ajaxdata)
			    	}else if(pictype=="stat"){
			    		__self.stat(picidselector,ajaxdata);
			    	}
			}});
		}
		this.render = function(date,data,bindelement){
			if(data&&data.teamLoseWin){
				this.stat("teamLoseWin",data.teamLoseWin);
			}else{
				this.getData("stat","teamLoseWin","${ctx}/Pandect/ALoseWin",date,bindelement);
			}
			if(data&&data.teamSaveMoney){
				this.stat("teamSaveMoney",data.teamSaveMoney);
			}else{
				this.getData("stat","teamSaveMoney","${ctx}/Pandect/ASaveMoney",date,bindelement);
			}
			if(data&&data.teamTakeMoney){
				this.stat("teamTakeMoney",data.teamTakeMoney);
			}else{
				this.getData("stat","teamTakeMoney","${ctx}/Pandect/ATakeMoney",date,bindelement);
			}
			if(data&&data.teamBalance){
				this.stat("teamBalance",data.teamBalance);
			}else{
				this.getData("stat","teamBalance","${ctx}/Pandect/ABalance",{},bindelement);
			}
			
			// 冲正统计
			if(data&&data.integralPlus){
				this.stat("integralPlus",data.integralPlus);
			}else{
				this.getData("stat","integralPlus","${ctx}/Pandect/AIntegralPlus",date,bindelement);
			}
			// 冲负统计
			if(data&&data.integralMinus){
				this.stat("integralMinus",data.integralMinus);
			}else{
				this.getData("stat","integralMinus","${ctx}/Pandect/AIntegralMinus",date,bindelement);
			}
			// 优惠额
			if(data&&data.discountAmount){
				this.stat("discountAmount",data.discountAmount);
			}else{
				this.getData("stat","discountAmount","${ctx}/Pandect/ADiscountAmount",date,bindelement);
			}
			// 洗码额
			if(data&&data.washingAmount){
				this.stat("washingAmount",data.washingAmount);
			}else{
				this.getData("stat","washingAmount","${ctx}/Pandect/AWashingAmount",date,bindelement);
			}
			
			if(data&&data.teamPerformanceCanvas){
				this.circle("teamPerformanceCanvas",data.teamPerformanceCanvas)
			}else{
				this.getData("circle","teamPerformanceCanvas","${ctx}/Pandect/APerformance",date,bindelement);
			}
			if(data&&data.teamPerformanceBrandCanvas){
				this.circle("teamPerformanceBrandCanvas",data.teamPerformanceBrandCanvas)
			}else{
				this.getData("circle","teamPerformanceBrandCanvas","${ctx}/Pandect/ABrandPerformance",date,bindelement);
			}
			if(data&&data.teamPerUseractivityCanvas){
				this.polyline("teamPerUseractivityCanvas",data.teamPerUseractivityCanvas);
			}else{
				this.getData("polyline","teamPerUseractivityCanvas","${ctx}/Pandect/AUseractivity",{},bindelement);
			}
		}
	}
	
	$(function(){
		var report = new teamreport();
		$(".reportswithcard").on("click",function(){
			var data = $("body").data($(this).attr("id"));
			var swithtype = $(this).attr("data-date");
			var date = {};
			if(swithtype=="today"){
				date.begintime = getDate(0)+" 00:00:00";
				date.endtime = getDate(1)+" 00:00:00";
			}else if(swithtype=="yesterday"){
				date.begintime = getDate(-1)+" 00:00:00";
				date.endtime = getDate(0)+" 00:00:00";
			}else if(swithtype=="thisweek"){
				date.begintime = getDate(-getMonday(0))+" 00:00:00";
				date.endtime = getDate(-getMonday(0)+7)+" 00:00:00";
			}else if(swithtype=="lastweek"){
				date.begintime = getDate(-getMonday(0)-7)+" 00:00:00";
				date.endtime = getDate(-getMonday(0))+" 00:00:00";
			}else if(swithtype=="thismonth"){
				date.begintime = getMonth('s',0)+" 00:00:00";
				date.endtime = getMonth('s',1)+" 00:00:00";
			}else if(swithtype=="lastmonth"){
				date.begintime = getMonth('s',-1)+" 00:00:00";
				date.endtime = getMonth('s',0)+" 00:00:00";
			}
			report.render(date,data,$(this).attr("id"));
		});
		$(".reportswithcard:eq(0)").trigger("click");	
	})
	
</script>
</body>
</html>