<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<!DOCTYPE html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>登陆日志</title>
	<link href="${statics}/css/bs3/bui-min.css" rel="stylesheet" />
	<link href="${statics}/css/bs3/dpl-min.css" rel="stylesheet" /> 
	<link href="${statics}/css/custom/common.css" rel="stylesheet" />
	<style type="text/css">
		html, body,.notic-content{
			height: 100%;
			width: 100%;
		}
		.notic-title{
			border:1px solid #E8E9EE;
			width: 250px;
			float: left;
			height: 100%;
			overflow-y:auto; 
		}
		.notic-title ul{
			margin-top: 15px;
		}
		.notic-title ul li{
			line-height: 35px;
			border-bottom: 1px solid #E8E9EE;
		}
		.notic-title ul li a{
			font-size:14px;
			padding-left: 35px;
			padding-right:10px;
			display: block;
			overflow:hidden;
			text-overflow:ellipsis;
			white-space:nowrap;-o-text-overflow:ellipsis;
		}
		.notic-title ul li a:hover{
			background-color: #E8E9EE;
		}
		.notic-text-vessel {
			height:100%;
			min-height: 100%;
			margin-left: 250px;
			text-align: left;
			border: 1px solid #E8E9EE;
		}
		.notic-maintitle{
			font-size: 16px;
			line-height: 36px;
			height:36px; 
			width: 500px;
			display: block;
			overflow:hidden;
			text-align: center;
			margin: auto;
		}
		.notic-text{
			background:#FFFFFF;
			border:none;
			border-left: 1px solid #7BC3FF;
			border-right: 1px solid #7BC3FF;
			box-shadow: 1px 1px 10px #7BC3FF;
			width: 600px;
			height:100%;
			overflow-y:auto;
			padding:36px 15px 15px;
			margin-top:0;
			font-size: 14px;
			line-height: 28px;
			box-sizing: border-box;
		   -o-box-sizing: border-box;
		   -ms-box-sizing: border-box;
		   -moz-box-sizing: border-box;
		   -webkit-box-sizing: border-box;
		}
		
	</style>
</head>

<body>
    <div class="demo-content notic-content">
   		<div class="notic-title">
   			<ul>
   			<c:forEach items="${notics }" var="notic" >
   				<li><a href="##" title="${notic.title }" data-content="${notic.content }">${notic.title }</a></li>
   			</c:forEach>
   			</ul>
   			
   		</div>
    	<div class="notic-text-vessel">
    		 <div style="position: absolute;z-index: 2;text-align: center;width: 630px;"><a id="notic-maintitle" class="notic-maintitle"></a></div>
    		 <textarea class="notic-text"  name="content" id="notic-text" readonly="readonly" onfocus="return false" oncontextmenu="return false" onclick="return false"></textarea>
    	</div>
	</div>
	<script src="${statics }/js/jquery-1.8.1.min.js"></script>
    <script src="${statics }/js/bui.js"></script> 
    <script src="${statics }/js/config.js"></script>
    <script type="text/javascript" src="${statics }/js/custom/commons-min.js"></script>
    <script type="text/javascript">
    $(function(){
    	var object = $(".notic-title ul li a");
    	if(object.size()){
    		object.bind("click",function(){
    			$("#notic-maintitle").html($(this).attr("title"));
    			$("#notic-text").val($(this).attr("data-content"));    		
        	});
    		object.eq(0).trigger("click");
    	}
    });
    </script>
</body>
</html>