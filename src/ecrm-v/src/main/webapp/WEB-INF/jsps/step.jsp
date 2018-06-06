<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>  
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>基础流程</title>
	<style type="text/css">
		body,ul,li,ol,p,h1,h2,h3{ list-style:none; padding:0; margin:0;font-family:'microsoft yahei';}
		.clear{ clear:both}
		.c_b_step{background:#e8e9ee;  border-radius:6px;width:98%; margin:0 auto;   margin-top:12px; padding:10px;}
		.st_center{ border:1px #a6a6a6 solid; margin:0 auto; background:#fff; overflow:hidden;}
		.st_center h2{ 
		    height: 42px; color:#1b2352;
			line-height: 42px;
			background: #e8e9ee;
			font-size: 20px;
			margin: 10px;
			text-indent: 1em;}
			#triangle-left { 
			width: 0;
			height: 0;
			border-left: 50px solid transparent;
			border-top: 95px solid #e1e2e5;
			float: left;
			border-bottom: 81px solid #e1e2e5;
			} 
			#left{
				height: 176px;
				background: #e1e2e5;
				margin-left: 50px; color:#333;
			}
			#right{ position:absolute; right:0;
			width: 0; 
			height: 0; 
			border-top: 95px solid transparent;
			border-left: 50px solid #e1e2e5;
			border-bottom: 80px solid transparent;
			float: left;   
			top: 31px;
			right: -50px;
			}
			.st_dyb{ float:left; width:100%}
			.st_buzou li{ width:18%; float:left; position:relative; margin-right:15px;}
			.st_buzou.st_buzou2{}
			.st_buzou.st_buzou2 li{ width:15%}
			.st_buzou.st_buzou2 li:hover{ width:15%}
			.ad_btn{ width:40%; margin:0 auto}
			.ad_btn button{ width:124px; height:32px; margin:0 auto; margin-top:12px; border:none; background:url(${statics }/img/bt223.png) left no-repeat; font-weight:bold; color:#333; cursor:pointer}
			.ad_btn button:hover{ width:124px; height:32px; margin:0 auto; margin-top:12px; border:none; background:url(${statics }/img/btn.png) left no-repeat; color:#fff}
			.st_buzou li:hover{ color:#47a9ce; width:18%}
			 #left:hover{ color:#fff;overflow:hidden }
			 #left span{ display:block;     padding-top: 50px;
    padding-left: 20px; overflow:hidden
    }
			 .st_buzou{ width:100%; padding:10px; margin:0 auto; overflow:hidden}
			 .st_dyb:hover #triangle-left{border-top: 95px solid #1183b4;border-bottom: 81px solid #1183b4;}
			 .st_dyb:hover #left{background:#1183b4;}
			 .st_dyb:hover #right{border-left:50px solid #1183b4;}
			 .st_buzou p{ font-size:20px;}
			 #triangle-left.cr{border-top: 95px solid #1183b4;border-bottom: 81px solid #1183b4;}
			 #left.cr{    
			background-color: #1183b4; color:#fff; font-size:16px;}
			 #right.cr{border-top: 95px solid transparent;
				border-left: 50px solid #1183b4;
				border-bottom: 80px solid transparent;}
			 .olst{ background:url(${statics }/img/ace1.jpg) left no-repeat; background-size:100% auto;background-repeat: no-repeat;height:219px; width:95%; margin:0 auto;overflow:hidden;}
			 .olst li{ float:left;  }
			 .olst:before{content: ""; display: block; padding-top: 100%;}
			 .olst img{ width:100%}
    </style>
</head>

<body>
    
		<div class="c_b_step">
      	<div class="st_center">
        	<h2>组织架构搭建流程</h2>
            <div class="st_buzou">
            	<ul>
                	<li>
                    	<p>第1步</p>
                        <div class="st_dyb">
                            <div id="triangle-left" class="cr"></div>
                            <div id="left" class="cr"><span>进入【用户信息-创建用户】创建企业运营coo（员工类型）账号</span></div>
                            <div id="right" class="cr"></div>
                        </div>
                        <div class="ad_btn"><button onclick="javascript:top.topManager.openPage({id : 'create_employee',title:'创建用户',href:'${ctx}/employeeOperating/userJsp/employeeAdd'});">前往设置</button></div>
                    </li>
                	<li>
                    	<p>第2步</p>
                        <div class="st_dyb">
                            <div id="triangle-left"></div>
                            <div id="left"><span>进入【用户信息-权限管理】分配coo账号管理权限</span></div>
                            <div id="right"></div>
                        </div>
                        <div class="ad_btn"><button onclick="javascript:top.topManager.openPage({id : 'MN004W',href:'${ctx}/EEmployee/userJsp/employeeData'});">前往设置</button></div>
                    </li>
                	<li>
                    	<p>第3步</p>
                        <div class="st_dyb">
                            <div id="triangle-left"></div>
                            <div id="left"><span>登陆coo账号，创建客服主管、财务主管、风控主管（员工类型）、市场总代理（代理类型）四个账号，分配相应管理权限</span></div>
                            <div id="right"></div>
                        </div>
                    </li>
                	<li>
                    	<p>第4步</p>
                        <div class="st_dyb">
                            <div id="triangle-left"></div>
                            <div id="left"><span>登陆主管账号创建相应的下级账号，权限自动继承上级权限</span></div>
                            <div id="right"></div>
                        </div>
                    </li>
                	<li>
                    	<p>第5步</p>
                        <div class="st_dyb">
                            <div id="triangle-left"></div>
                            <div id="left"><span>登陆市场总代理账号，注册会员站点，进行推广运营</span></div>
                            <div id="right"></div>
                        </div>
                    </li>
                </ul>
         </div>
        </div>
        <!--公司基础流程设定-->
        <div class="st_center">
        	<h2>公司基础信息配置流程</h2>
            <div class="st_buzou st_buzou2">
            	<ul>
                	<li>
                    	<p>第1步</p>
                        <div class="st_dyb">
                            <div id="triangle-left" class="cr"></div>
                            <div id="left" class="cr"><span >进入【公司信息-企业域名】，绑定至少一个会员站点域名，一个代理站点域名，并设为默认域名</span></div>
                            <div id="right" class="cr"></div>
                        </div>
                        <div class="ad_btn"><button onclick="javascript:top.topManager.openPage({id : 'MN004Q',href:'${ctx}/Enterprise/List'});">前往设置</button></div>
                    </li>
                	<li>
                    	<p>第2步</p>
                        <div class="st_dyb">
                            <div id="triangle-left"></div>
                            <div id="left"><span>进入【品牌信息-创建品牌】，创建一个运营的品牌，并设置默认打码，开启游戏，客服专线</span></div>
                            <div id="right"></div>
                        </div>
                        <div class="ad_btn"><button onclick="javascript:top.topManager.openPage({id : 'create_brand',title:'创建品牌',href:'${ctx}/EOBrand/add'});">前往设置</button></div>
                    </li>
                	<li>
                    	<p>第3步</p>
                        <div class="st_dyb">
                            <div id="triangle-left"></div>
                            <div id="left"><span >进入【公司银行卡-添加银行卡】，添加企业收款银行卡</span></div>
                            <div id="right"></div>
                        </div>
                        <div class="ad_btn"><button onclick="javascript:top.topManager.openPage({id : 'create_enterpriseInformation',title:'添加收款卡',href:'${ctx}/EInformation/add'});">前往设置</button></div>
                    </li>
                	<li>
                    	<p>第4步</p>
                        <div class="st_dyb">
                            <div id="triangle-left"></div>
                            <div id="left"><span>进入【公司快捷支付-添加快捷支付】，添加企业快捷支付</span></div>
                            <div id="right"></div>
                        </div>
                        <div class="ad_btn"><button onclick="javascript:top.topManager.openPage({id : 'create_thenterpriseInformation',title:'添加快捷支付',href:'${ctx}/thirdpartyPayment/add'});">前往设置</button></div>
                    </li>
                	<li>
                    	<p>第5步</p>
                        <div class="st_dyb">
                            <div id="triangle-left"></div>
                            <div id="left"><span>进入【流程管理-存款流程管理-新增存款流程】，添加存款审核流程，设置该流程的审批人</span></div>
                            <div id="right"></div>
                        </div>
                        <div class="ad_btn"><button onclick="javascript:top.topManager.openPage({id : 'create_thenterpriseInformation',title:'新增存款流程',href:'${ctx}/workingFlow/depositWorkingFlowConfigurationAdd'});">前往设置</button></div>
                    </li>
                	<li>
                    	<p>第6步</p>
                        <div class="st_dyb">
                            <div id="triangle-left"></div>
                            <div id="left"><span>进入【流程管理-取款流程管理-新增取款流程】，添加取款审核流程，设置该流程的审批人</span></div>
                            <div id="right"></div>
                        </div>
                        <div class="ad_btn"><button onclick="javascript:top.topManager.openPage({id : 'create_thenterpriseInformation',title:'新增取款流程',href:'${ctx}/workingFlow/takeWorkingFlowConfigurationAdd'});">前往设置</button></div>
                    </li>
                </ul>
         </div>
        </div>
        <!--公司基础流程设定End-->
        </div>
               

</body>
</html>
