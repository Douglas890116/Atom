<%@page import="com.maven.entity.EnterpriseEmployeeType.Type"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<c:set var="usertype" value="<%=Type.代理.value%>"/>
<c:set var="sa_user" value="sa"/>
<c:set var="title" value="${(sessionScope.ERCM_USER_SESSION.brandcode==''||sessionScope.ERCM_USER_SESSION.brandcode==null)?sessionScope.ERCM_USER_SESSION.enterprisename:sessionScope.ERCM_USER_SESSION.brandname}"/>
<c:set var="isacvity" value="${sessionScope.ERCM_USER_PSERSSION['MN00C7'].menustatus==1 || sessionScope.ERCM_USER_PSERSSION['MN00C8'].menustatus==1 || sessionScope.ERCM_USER_PSERSSION['MN00C9'].menustatus==1}"></c:set>
<c:set var="iswaitPayment" value="${sessionScope.ERCM_USER_PSERSSION['MN0082'].menustatus==1 }"></c:set>
<c:set var="isDeposit" value="${sessionScope.USER_WORKING_FLOW_DEPOSIT }"></c:set>
<c:set var="isTake" value="${sessionScope.USER_WORKING_FLOW_TAKE }"></c:set>
<c:set var="isOnline" value="${sessionScope.ERCM_USER_PSERSSION['MN00E4'].menustatus==1 }"></c:set>
<c:set var="isEnglish" value="${sessionScope.LANGUAGE == 'en'}"/>

<%
response.setHeader("Pragma", "No-cache");  
response.setHeader("Cache-Control", "no-cache");  
response.setDateHeader("Expires", 0);  
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>${title }</title>
	<link rel="stylesheet" href="${statics}/css/bs3/bui-min.css" />
    <link rel="stylesheet" href="${statics}/css/bs3/dpl-min.css" />
	<link href="${statics }/css/main-min.css" rel="stylesheet" type="text/css" />

	<style type="text/css">
	.searchTool{position: absolute;right: 10px;top:3px;}
	.searchTool .txtSearch{float:left;width:232px;padding:2px 2px 0 2px;height:25px;line-height:25px;vertical-align:bottom;border-bottom:1px solid #fff;color:#fff;border-left:0; border-right:0;  border-top:0;background:none;}
	.searchTool .btnSearch{float:left;margin-left:10px;width:30px;height:30px;line-height:22px;border:none;overflow:hidden;}
	.searchTool .btnSearch a{background:url(${statics }/img/search.png) repeat-x center ;display:block;text-decoration:none;height:30px;line-height:30px;overflow:hidden;}
	.searchTool .btnSearch a:hover{background:url(${statics }/img/search1.png) repeat-x center center;border:1px solid #fff;height:30px;line-height:20px; border:none;}
	.searchTool .btnSearch .lbl{cursor:pointer;display:block;width:30px;height:30px;padding-top:1px;}
	.searchTool .btnSearch a:hover .lbl{padding-top:0px;}
	.header a{color: #c1d5ec}
	.header a:hover{color: white;text-decoration: none;} 
	#headq::-moz-placeholder { color: white; }
	#headq::-webkit-input-placeholder { color:white; }
	#headq:-ms-input-placeholder { color:white; }
	</style>
</head>
<body>
<%-- <iframe src="${ctx}/EEmployee/push"  width="0" height="0" style="display: none;"></iframe> --%>
  <div class="header" style="min-width: 1342px;">
      <div class="dl-title">
      	
          <span class="dl-title-text" style="font-size: 20px; color: white">${title}</span>
      </div>
	<div style="padding-left: 250px;">
	   <div style=" height:20px; height:20px; margin-top:10px; color:#fff;" align="right">
             <c:if test="${!sessionScope.ERCM_USER_SESSION.employeetypecode.equals(usertype)}">  
	             <i class="icon-white" id="audioImgId" style="background-position:-265px -168px;cursor: pointer; display:none"></i>
		         
		         <c:if test="${isDeposit }">
		         <span style=" margin-right:5px;" > 
		         	<a href="javascript:onclick1();" class="dl-log-quit">存款待审核(<span id="depositId" class="badge badge-success">0</span>)</a>
		         </span>
		         </c:if>
		         
		         <c:if test="${isTake }">
		         <span style=" margin-right:5px;" >
		         	<a href="javascript:onclick2();" class="dl-log-quit">取款待审核(<span id="takeId" class="badge badge-warning">0</span>)</a>
		         </span>
		         </c:if>
		         
		         <c:if test="${iswaitPayment }">
		         <span style=" margin-right:5px;" >
		         	<a href="javascript:onclick3();" class="dl-log-quit">待出款(<span id="waitPaymentId" class="badge badge-error">0</span>)</a>
		         </span>
		         </c:if>
		         
		         <c:if test="${isacvity }">
		         <span style=" margin-right:5px;" >
		         	<a href="javascript:onclick5();" class="dl-log-quit">红利待审核(<span id="waitacvityId" class="badge">0</span>)</a>
		         </span>
		         </c:if>
		         
	          </c:if>
	         
	         <c:if test="${isOnline }">
	         <span style=" margin-right:5px;" >
		         <a href="javascript:onclick6();" class="dl-log-quit">在线(<span id="onlineId" class="badge badge-inverse">0</span>)</a>
	         </span>
	         </c:if>
	          
	         <span style=" margin-right:5px;" >
		         <a href="javascript:onclick4();" class="dl-log-quit">站内消息(<span id="siteId" class="badge badge-info">0</span>)</a>
	         </span>
	         
	         
	         <span style=" margin-right:18px;" > |</span>
	         
	         
	         <span style=" margin-right:18px;">
	           <a href="#" onclick="changeLanguage('zh')">简体中文</a>
	           &nbsp;
	           <a href="#" onclick="changeLanguage('en')">English</a>
	         </span>
	         
	         <span style=" margin-right:18px;" > |</span>
	         
	         <span style=" margin-right:18px;" > 
	         	${isEnglish?'Welcome':'欢迎您'}，<span class="dl-log-user">${sessionScope.ERCM_USER_SESSION.displayalias}</span><a href="${ctx }/EEmployee/exit" title="退出系统" class="dl-log-quit">[${isEnglish?'Exit':'退出'}]</a>
	         </span>
	   </div>
	   <div style="  height:20px; margin-left:50px; margin-top:10px; color:#fff; "> 
	          <marquee scrollamount="8" scrolldelay="150" direction="left" id="msgNews" onmouseover="this.stop();" onmouseout="this.start();" style="cursor:pointer; white-space:nowrap; margin-top:5px;width: 95%">
	   			<a href="#" id="noticeId"></a>
	   		</marquee>
	   </div>       
	</div>
  </div>
   <div class="content">
    <div class="dl-main-nav">
      <div class="dl-inform"><div class="dl-inform-title">贴心小秘书<span class="dl-inform-icon dl-up"></span></div></div>
      <div>
	      <ul id="J_Nav"  class="nav-list ks-clear">
	      </ul>
      </div>
      <div class="searchTool">
      
	  <input class="txtSearch" id="headq" value="" name="loginaccount" placeholder="输入会员或者代理账号" type="text"/>
	  <div class="btnSearch">
		<a href="javascript:topNameSearch();"><span class="lbl">&nbsp;&nbsp;&nbsp;&nbsp;</span></a>
	  </div>		
	</div>
    </div>
    <ul id="J_NavContent" class="dl-tab-conten">

    </ul>
   </div>
   
   <!-- 
   	  var count1 = 0;//存款记录
	  var count2 = 0;//取款记录
	  var count3 = 0;//待取款记录
	  var count4 = 0;//站内消息记录
    -->
   <div id="notiContent1" style="position:absolute; bottom:0; right:10px; width:300px; height:60px; background-color:#468847; color: #000000; padding:20px; display:none">
   	<div style="position:absolute; top:5px; right:20px; width:0px; height:0px;">
   		<a style="color:#ffffff; text-decoration:none;" href="#">Ｘ</a>
   	</div>
   	<div id="msg1" style="position:absolute;"></div>
   </div>
   
   <div id="notiContent2" style="position:absolute; bottom:0; right:10px; width:300px; height:60px; background-color:#f89406; color: #000000; padding:20px; display:none">
	<div style="position:absolute; top:5px; right:20px; width:0px; height:0px;">
   		<a style="color:#000000; text-decoration:none;" href="#">Ｘ</a>
   	</div>
    <div id="msg2" style="position:absolute;"></div>
   </div>
   
   <div id="notiContent3" style="position:absolute; bottom:0; right:10px; width:300px; height:60px; background-color:#da4f49; color: #000000; padding:20px; display:none">
   	<div style="position:absolute; top:5px; right:20px; width:0px; height:0px;">
   		<a style="color:#ffffff; text-decoration:none;" href="#">Ｘ</a>
   	</div>
    <div id="msg3" style="position:absolute;"></div>
   </div>
   
   <div id="notiContent4" style="position:absolute; bottom:0; right:10px; width:300px; height:60px; background-color:#3a87ad; color: #fff; padding:20px; display:none">
   	<div style="position:absolute; top:5px; right:20px; width:0px; height:0px;">
   		<a style="color:#ffffff; text-decoration:none;" href="#">Ｘ</a>
   	</div>
    <div id="msg4" style="position:absolute;"></div>
   </div>
   
   <div id="notiContent5" style="position:absolute; bottom:0; right:10px; width:300px; height:60px; background-color:#999; color: #fff; padding:20px; display:none">
   	<div style="position:absolute; top:5px; right:20px; width:0px; height:0px;">
   		<a style="color:#ffffff; text-decoration:none;" href="#">Ｘ</a>
   	</div>
    <div id="msg5" style="position:absolute;"></div>
   </div>
   
  <script type="text/javascript" src="${statics }/js/jquery-1.8.1.min.js"></script>
  <script type="text/javascript" src="${statics }/js/bui.js"></script>
  <script type="text/javascript" src="${statics }/js/config.js"></script>
  <script type="text/javascript">
  	  window.top.itempath = '${ctx}';
	  BUI.use('common/main',function(){
		    var firstMenu = ${firstMenu};
		    var fistMenuHtml = "";
		    $.each(firstMenu,function(i,n){
		    	if(i==0){
			    	fistMenuHtml = "<li class='nav-item dl-selected'><div class='nav-item-inner nav-home'>"+n.menuname+"</div><div class='nav-item-mask'></div></li>";
		    	}else{
			    	fistMenuHtml += "<li class='nav-item'><div class='nav-item-inner nav-order'>"+n.menuname+"</div><div class='nav-item-mask'></div></li>";
		    	}
		    });
		   	$("#J_Nav").html(fistMenuHtml);
	
		   	var config = ${mainMenu};
		      new PageUtil.MainPage({
		        modulesConfig : config
		    });
	 });
	  
	  function updatePasswordPage(){
			top.topManager.openPage({
			    id : 'updatepassword',
			    href : '${ctx}/EEmployee/userJsp/modifyPassword',
			    title : '修改密码'
		  	});
		}
	  
	  //加载当前登录用户的公告信息
	  $.ajax({
    	  type:"post",
    	  url:'${ctx}/common/getNoticeMessage',
    	  dataType:"json", 
    	  success:function(data){
    		  $("#noticeId").text(data.notic);
    	  }
      });
	 
	  var audio = document.createElement("audio");
	  var audio2 = document.createElement("audio");
	  audio.src = "${statics}/wav/new_order.mp3";
	  audio.autoplay = false;
      audio.loop = true;
      audio2.src = "${statics}/wav/new_notice.mp3";
      audio2.autoplay = false;
      audio2.loop = true;
      var ismuted = false;//是否全静音
	  
    	//绑定播放暂停控制  
      $('#audioImgId').bind('click', function() {  
    	  toggleAudio();
      });
      function toggleAudio() {
    	  if(ismuted) {
    		  ismuted = false;
    	  } else {
    		  ismuted = true;
    		  stopAudio(1);//
    	  }
    	  mutedAudio1(ismuted);
    	  mutedAudio2(ismuted);
      }
      function mutedAudio1(result) {   
    	  ismuted = result;
    	  if(ismuted) {
    		  $("#audioImgId").css("background-position","-240px -168px");
    	  } else {
    		  $("#audioImgId").css("background-position","-265px -168px");
    	  }
    	  audio.muted = ismuted;
      }
      function mutedAudio2(result) {   
    	  ismuted = result;
    	  if(ismuted) {
    		  $("#audioImgId").css("background-position","-240px -168px");
    	  } else {
    		  $("#audioImgId").css("background-position","-265px -168px");
    	  }
		  audio2.muted = ismuted;
      }
      /****
  		$("#audioImgId").on("click",function(){
		  if(audio.autoplay){
			$("#audioImgId").css("background-position","-240px -168px");
			audio.autoplay = false;
			audio.pause();
		  }else{
			  if("0"!= $("#depositId").text() ||"0" !=$("#takeId").text()){
	  				audio.autoplay = true;
	  				$("#audioImgId").css("background-position","-265px -168px");
	  				audio.play();  
			  }
		  }
  		});
	  ****/
	  $("#headq").keydown(function(eve){
		  if (eve.keyCode == 13){
			  topNameSearch();
		  }
	  });
	  function topNameSearch(){
		  var loginaccount = $("#headq").val();
		  if (loginaccount == ''){
			  BUI.Message.Alert("请先输入账号",'info');
			  return;
		  } else {
			  $.post('${ctx}/EEmployee/topNameSearch',{loginaccount:loginaccount},function(page){
				  var id;
				  var title;
				  if (page.indexOf("Agent")>1){
					  id = "MN0029"
					  title = "代理信息";
				  } else {
					  id = "MN000F";
					  title = "会员信息";
				  }
				  top.topManager.openPage({id:"#MN0001"+id,href:'${ctx}'+page+"?search="+loginaccount,title:title});
			  });
		  }
	  }
	  $('#notiContent1').bind('dblclick click', function() {  
    	  $(this).slideUp("fast");
    	  stopAudio(1);
      });
	  $('#notiContent2').bind('dblclick click', function() {  
    	  $(this).slideUp("fast");
    	  stopAudio(2);
      });
	  $('#notiContent3').bind('dblclick click', function() {  
    	  $(this).slideUp("fast");
    	  stopAudio(3);
      });
	  $('#notiContent4').bind('dblclick click', function() {  
    	  $(this).slideUp("fast");
    	  stopAudio(4);
      });
	  $('#notiContent5').bind('dblclick click', function() {  
    	  $(this).slideUp("fast");
    	  stopAudio(5);
      });
	  
	  var count1 = 0;//存款记录
	  var count2 = 0;//取款记录
	  var count3 = 0;//待取款记录
	  var count4 = 0;//站内消息记录
	  var count5 = 0;//红利待处理记录
	  var count6 = 0;//在线人数
	  
	  // 定时刷新
	  function getDepositTakeCount(){
		  var allDepositCount = 0;//存款记录
		  var allTakeCount = 0;//取款记录
		  var allPaymentCount = 0;//待取款记录
		  var allSystemCount = 0;//站内消息记录
		  var allAcvityCount = 0;//红利待处理记录
		  
		  // 统计存/取款记录
		if("${isDeposit }" == "true" || "${isTake }" == "true") {
			  
		  $.ajax({
	    	  type:"post",
	    	  url:'${ctx}/takeDepositRecord/countRecord',
	    	  dataType:"json",
	    	  success:function(data){
	    		  $("#depositId").text(data.allDepositCount);
    			  $("#takeId").text(data.allTakeCount);
    			  
    			  allDepositCount = data.allDepositCount;
    			  allTakeCount = data.allTakeCount;
    			  
	    		  if((allDepositCount > count1 || allTakeCount > count2) ){
	    			  
	    			  mutedAudio1(false);//取消静音
	    			  if(audio.paused){
						  audio.play();
	    			  } 
	    		  }
	    		  
	    		  if((allDepositCount > count1 ) ){
	    			  	var href = "javascript: onclick1() ";
	     			 	$("#msg1").html("你有"+(allDepositCount-count1)+"个新的存款待审需处理！<a href='"+href+"' style='color: red'>点击这里</a>查看");
	     			 	$("#notiContent1").show();
	     		  }  else {
					  //$("#notiContent1").hide(300);
				  }
	     		  if((allTakeCount > count2 ) ){
	     			  	var href = "javascript: onclick2() ";
	     			 	$("#msg2").html("你有"+(allTakeCount-count2)+"个新的取款待审核需处理！<a href='"+href+"' style='color: red'>点击这里</a>查看");
	     			 	$("#notiContent2").show();
	     		  }  else {
					  //$("#notiContent2").hide(300);
				  }
	     		  count1 = allDepositCount;//记录上一次
	     		  count2 = allTakeCount;//记录上一次
	    	  }
	      }); 
		  
		}  
		  
		//统计待出款记录
		if("${iswaitPayment }" == "true"){
			
		  $.ajax({
			  type:"post",
			  url:'${ctx}/takeDepositRecord/findWaitPayment',
			  dataType:"json",
			  success:function(data){
				  $("#waitPaymentId").text(data.results);
				  allPaymentCount = data.results;
				  
				  if(allPaymentCount > count3) {
					  mutedAudio1(false);//取消静音
					  if(audio.paused){
						  audio.play();
	    			  } 
				  } 
				  
				  if((allPaymentCount > count3 ) ){
					  var href = "javascript: onclick3() ";	
					  $("#msg3").html("你有"+(allPaymentCount-count3)+"个新的待出款需处理！<a href='"+href+"' style='color: white'>点击这里</a>查看");
					  $("#notiContent3").show();
				  }  else {
					  //$("#notiContent3").hide(300);
				  }
				  count3 = allPaymentCount;//记录上一次
			  }
		  });
		  
		}
		
		//统计红利待审核记录
		if("${isacvity }"=="true") {
			
		  $.ajax({
			  type:"post",
			  url:'${ctx}/activitypay/findWaitPayment',
			  dataType:"json",
			  success:function(data){
				  $("#waitacvityId").text(data.results);
				  allAcvityCount = data.results;
				  
				  if(allAcvityCount > count5) {
					  mutedAudio1(false);//取消静音
					  if(audio.paused){
						  audio.play();
	    			  } 
				  } 
				  
				  if((allAcvityCount > count5 ) ){
					  var href = "javascript: onclick5() ";	
					  $("#msg5").html("你有"+(allAcvityCount-count5)+"个新的红利待处理！<a href='"+href+"' style='color: white'>点击这里</a>查看");
					  $("#notiContent5").show();
				  }  else {
					  //$("#notiContent5").hide(300);
				  }
				  count5 = allAcvityCount;//记录上一次
			  }
		  });
		  
		}
		
		//站内信息
		  $.ajax({
			  type:"post",
			  url:'${ctx}/Message/AgentData',
			  dataType:"json",
			  success:function(data){
				  $("#siteId").text(data.results);
				  allSystemCount = data.results;
				  
					// 处理声音业务
					if(allSystemCount > count4) {
						mutedAudio2(false);//取消静音
					    if(audio2.paused){
						   audio2.play();
	    			    } 
					} 
				  
				  if((allSystemCount > count4 ) ){
					  var href = "javascript: onclick4() ";	
					  $("#msg4").html("你有"+(allSystemCount-count4)+"个新的站内消息！<a href='"+href+"' style='color: red'>点击这里</a>查看");
					  $("#notiContent4").show();
				  } else {
					  //$("#notiContent4").hide(300);
				  }
				  count4 = allSystemCount;//记录上一次
			  }
		  }); 
 		
		  
		//在线人数
		if("${isOnline }"=="true") {
		  $.ajax({
			  type:"post",
			  url:'${ctx}/EEmployee/onlineAll',
			  dataType:"json",
			  success:function(data){
				  if(data.results > 0) {
					  $("#onlineId").text(data.results);
					  $("#onlineId").css("color","red");
				  } else {
					  $("#onlineId").text(data.results);
					  $("#onlineId").css("color","white");
				  }
				  
			  }
		  });
		}
		  
	  }
	  
	// 5秒触发一次
  	function onclick1() {
  		top.topManager.reloadPage("MN0022");
  		top.topManager.openPage({id : 'MN0022',href:'${ctx}/takeDepositRecord/depositApprove?menucode=MN0022'});
  		stopAudio(1);
  	}
	function onclick2() {
		top.topManager.reloadPage("MN0023");
		top.topManager.openPage({id : 'MN0023',href:'${ctx}/takeDepositRecord/takeApprove?menucode=MN0023'});
		stopAudio(2);
	}
	function onclick3() {
		top.topManager.reloadPage("MN0082");
		top.topManager.openPage({id : 'MN0082',href:'${ctx}/takeDepositRecord/waitPayment?menucode=MN0082'});
		stopAudio(3);
	}
	function onclick4() {
		top.topManager.reloadPage("MN004O");
		top.topManager.openPage({id : 'MN004O',href:'${ctx}/Message/AgentMessage?menucode=MN004O',title:'站内消息'});
		stopAudio(4);
	}
	function onclick5() {
		top.topManager.reloadPage("MN0052");
		top.topManager.openPage({id : 'MN0052',href:'${ctx}/activitypay/index?menucode=MN0052',title:'红利待审核消息'});
		stopAudio(5);
	}
	function onclick6() {
		top.topManager.reloadPage("MN00E4");
		top.topManager.openPage({id : 'MN00E4',href:'${ctx}/EEmployee/onlineAll',title:'当前在线用户'});
	}
	function stopAudio(flag) {
		if(flag == 1) {
			audio.pause();$("#notiContent1").slideUp("fast");//隐藏
		} else if(flag == 2) {
			audio.pause();$("#notiContent2").slideUp("fast");//隐藏
		} else if(flag == 3) {
			audio.pause();$("#notiContent3").slideUp("fast");//隐藏
		} else if(flag == 4) {
			audio2.pause();$("#notiContent4").slideUp("fast");//隐藏
		} else if(flag == 5) {
			audio.pause();$("#notiContent5").slideUp("fast");//隐藏
		}
		
		showFlag = false;
	}
	function changeLanguage(language) {
		$.ajax({
			url : '${ctx}/EEmployee/language',
			data: {'language' : language},
			dataType : 'json',
			success : function (data) {
				location.reload();
			}
		});
	}
  </script>
  <c:choose>
  	<c:when test="${!sessionScope.ERCM_USER_SESSION.employeetypecode.equals(usertype) && !sessionScope.ERCM_USER_SESSION.loginaccount.equals(sa_user)}">
  <script type="text/javascript">
	setInterval(getDepositTakeCount, 2000);//2秒一次
  </script>
  	</c:when>
  	<c:otherwise>

  	</c:otherwise>
  </c:choose>

 </body>
</html>