<%@page import="com.maven.config.SystemConstant"%>
<%@page import="com.maven.cache.SystemCache"%>
<%@page import="com.maven.entity.EnterpriseEmployee"%>
<%@page import="com.maven.constant.Constant"%>
<%@page import="org.springframework.web.context.request.SessionScope"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.maven.entity.EnterpriseEmployeeType.Type" %>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<c:set var="user" value="${sessionScope.ERCM_USER_SESSION.employeecode}"></c:set>

<link href="${ctx }/static/css/public.css" rel="stylesheet" type="text/css">

<body>
			
			<table border="0" cellpadding="4" cellspacing="10" class="toTab" width="70%" align="center">
     			<tr>
     				<td class="toLabelTd tdCenter" colspan="8" align="center"><b>会员基本信息</b></td>
     			</tr>
  			  	<tr>
      				<td class="toDataTd tdCenter" width="10%" align="right">会员账号：</td>
      			    <td class="toDataTd tdCenter" width="15%">${__ee.loginaccount }</td>
      			    <td class="toDataTd tdCenter" width="10%" align="right">会员姓名：</td>
      			    <td class="toDataTd tdCenter" width="15%">${__ee.displayalias }</td>
      			    <td class="toDataTd tdCenter" width="10%" align="right">上级账号：</td>
      			    <td class="toDataTd tdCenter" width="15%">${__ee.parentemployeeaccount }</td>
      			    <td class="toDataTd tdCenter" width="10%" align="right">上级编码：</td>
      			    <td class="toDataTd tdCenter" width="15%">${__ee.parentemployeecode }</td>
      			</tr>
      			<tr>
      			    <td class="toDataTd tdCenter" width="10%" align="right">等级名称：</td>
      			    <td class="toDataTd tdCenter" width="15%"><font color="green"><b>${__ee.employeelevelcode }-${__ee.employeelevelname }</b></font></td>
      			    <td class="toDataTd tdCenter" width="10%" align="right">会员状态：</td>
      			    <td class="toDataTd tdCenter" width="15%">
      			    	<c:choose>
      			    		<c:when test="${__ee.employeestatus == 1}"><font color="green"><b>启用</b></font></c:when>
      			    		<c:when test="${__ee.employeestatus == 2}"><font color="red"><b>锁定游戏</b></font></c:when>
      			    		<c:when test="${__ee.employeestatus == 3}"><font color="red"><b>禁用</b></font></c:when>
      			    	</c:choose>
      			    </td>
      			    <td class="toDataTd tdCenter" width="10%" align="right">注册时间：</td>
      			    <td class="toDataTd tdCenter" width="15%"><fmt:formatDate value="${__ee.logindatetime }" pattern="yyyy/MM/dd HH:mm:ss" /></td>
      			    <td class="toDataTd tdCenter" width="10%" align="right">最后登录时间：</td>
      			    <td class="toDataTd tdCenter" width="15%"><fmt:formatDate value="${__ee.lastlogintime }" pattern="yyyy/MM/dd HH:mm:ss" /></td>
      			</tr>
      			<tr>
      				<td class="toDataTd tdCenter" width="10%" align="right">手机号码：</td>
      			    <td class="toDataTd tdCenter" width="15%">${__ee.phoneno }</td>
      			    <td class="toDataTd tdCenter" width="10%" align="right">邮箱：</td>
      			    <td class="toDataTd tdCenter" width="15%">${__ee.email }</td>
      			    <td class="toDataTd tdCenter" width="10%" align="right">qq号码：</td>
      			    <td class="toDataTd tdCenter" width="15%">${__ee.qq }</td>
      			    <td class="toDataTd tdCenter" width="10%" align="right">微信号：</td>
      			    <td class="toDataTd tdCenter" width="15%">${__ee.wechat }</td>
      			</tr>
      			<tr>
      				<td class="toDataTd tdCenter" width="10%" align="right">注册来源：</td>
      			    <td class="toDataTd tdCenter" width="15%" colspan="7">${__ee.domainlink }</td>
      			</tr>
  			</table>
  			
  			<br />
  			<table border="0" cellpadding="4" cellspacing="10" class="toTab" width="70%" align="center">
     			<tr>
     				<td class="toLabelTd tdCenter" colspan="6" align="center"><b>存款信息</b></td>
     			</tr>
      			<tr>
      				<td class="toDataTd tdCenter" width="10%" align="right">首次存款时间：</td>
      			    <td class="toDataTd tdCenter" width="20%">${result["first_deposit_time"] }</td>
      			    <td class="toDataTd tdCenter" width="10%" align="right">首次存款金额：</td>
      			    <td class="toDataTd tdCenter" width="20%">${result["first_deposit_money"] }元</td>
      			    <td class="toDataTd tdCenter" width="10%" align="right">首存存款单号：</td>
      			    <td class="toDataTd tdCenter" width="30%">${result["first_deposit_ordernumber"] }</td>
      			</tr>
      			<tr>
      				<td class="toDataTd tdCenter" width="10%" align="right">最近存款时间：</td>
      			    <td class="toDataTd tdCenter" width="20%">${result["last_deposit_time"] }</td>
      			    <td class="toDataTd tdCenter" width="10%" align="right">最近存款金额：</td>
      			    <td class="toDataTd tdCenter" width="20%">${result["last_deposit_money"] }元</td>
      			    <td class="toDataTd tdCenter" width="10%" align="right">最近存款单号：</td>
      			    <td class="toDataTd tdCenter" width="30%">${result["last_deposit_ordernumber"] }</td>
      			</tr>
      			<tr>
      				<td class="toDataTd tdCenter" width="10%" align="right">累计存款总额：</td>
      			    <td class="toDataTd tdCenter" width="20%">${result["all_deposit_money"] }元</td>
      			    <td class="toDataTd tdCenter" width="10%" align="right">累计存款笔数：</td>
      			    <td class="toDataTd tdCenter" width="" colspan="3">${result["all_deposit_count"] }笔</td>
      			</tr>
  			</table>
  			
  			<br />
  			<table border="0" cellpadding="4" cellspacing="10" class="toTab" width="70%" align="center">
     			<tr>
     				<td class="toLabelTd tdCenter" colspan="6" align="center"><b>取款信息</b></td>
     			</tr>
      			<tr>
      				<td class="toDataTd tdCenter" width="10%" align="right">首次取款时间：</td>
      			    <td class="toDataTd tdCenter" width="20%">${result["first_take_time"] }</td>
      			    <td class="toDataTd tdCenter" width="10%" align="right">首次取款金额：</td>
      			    <td class="toDataTd tdCenter" width="20%">${result["first_take_money"] }元</td>
      			    <td class="toDataTd tdCenter" width="10%" align="right">首存取款单号：</td>
      			    <td class="toDataTd tdCenter" width="30%">${result["first_take_ordernumber"] }</td>
      			</tr>
      			<tr>
      				<td class="toDataTd tdCenter" width="10%" align="right">最近取款时间：</td>
      			    <td class="toDataTd tdCenter" width="20%">${result["last_take_time"] }</td>
      			    <td class="toDataTd tdCenter" width="10%" align="right">最近取款金额：</td>
      			    <td class="toDataTd tdCenter" width="20%">${result["last_take_money"] }元</td>
      			    <td class="toDataTd tdCenter" width="10%" align="right">最近取款单号：</td>
      			    <td class="toDataTd tdCenter" width="30%">${result["last_take_ordernumber"] }</td>
      			</tr>
      			<tr>
      				<td class="toDataTd tdCenter" width="10%" align="right">累计取款总额：</td>
      			    <td class="toDataTd tdCenter" width="20%">${result["all_take_money"] }元</td>
      			    <td class="toDataTd tdCenter" width="10%" align="right">累计取款笔数：</td>
      			    <td class="toDataTd tdCenter" width="" colspan="3">${result["all_take_count"] }笔</td>
      			</tr>
  			</table>
  			
  			<br />
  			<table border="0" cellpadding="4" cellspacing="10" class="toTab" width="70%" align="center">
     			<tr>
     				<td class="toLabelTd tdCenter" colspan="10" align="center"><b>其他汇总信息</b></td>
     			</tr>
     			<tr>
      				<td class="toDataTd tdCenter" width="10%" align="right">累计优惠总额：</td>
      			    <td class="toDataTd tdCenter" width="10%">${result2["pmoney_acvity"] }元</td>
      			    <td class="toDataTd tdCenter" width="10%" align="right">累计洗码总额：</td>
      			    <td class="toDataTd tdCenter" width="10%">${result2["pmoney_daily"] }元</td>
      			    <td class="toDataTd tdCenter" width="10%" align="right">累计上分返回金额：</td>
      			    <td class="toDataTd tdCenter" width="10%">${result2["pmoney_up_fail"] }元</td>
      			    <td class="toDataTd tdCenter" width="10%" align="right">累计冲正总额：</td>
      			    <td class="toDataTd tdCenter" width="10%">${result2["pmoney_add"] }元</td>
      			    <td class="toDataTd tdCenter" width="10%" align="right">累计冲负总额：</td>
      			    <td class="toDataTd tdCenter" width="10%">${result2["pmoney_sub"] }元</td>
      			</tr>
  			</table>
  			
  			<br />
  			<table border="0" cellpadding="4" cellspacing="10" class="toTab" width="70%" align="center">
     			<tr>
     				<td class="toLabelTd tdCenter" colspan="3" align="center"><b>游戏投注情况</b></td>
     			</tr>
     			<tr>
     				<td class="toLabelTd" align="center" width="40%">最近投注时间</td>
     				<td class="toLabelTd" align="center" width="30%">游戏平台</td>
     				<td class="toLabelTd" align="center" width="30%">有效投注总额</td>
     			</tr>
    			<c:forEach var="data" varStatus="status" items="${list}">
                  	<tr>
	      				<td class="toDataTd" align="center"><fmt:formatDate value="${data.ptime }" pattern="yyyy/MM/dd HH:mm:ss" /></td>
	      			    <td class="toDataTd" align="center">${data.pcontent }</td>
	      			    <td class="toDataTd" align="center">${data.pmoney }元</td>
	      			</tr>
                </c:forEach>
  			</table>
  			
  			<br />
  			<table border="0" cellpadding="4" cellspacing="10" class="toTab" width="70%" align="center">
     			<tr>
     				<td class="toLabelTd tdCenter" colspan="3" align="center"><b>账户余额情况</b></td>
     			</tr>
     			<tr>
     				<td class="toLabelTd" width="40%" align="center">钱包名称</td>
     				<td class="toLabelTd" width="30%" align="center">余额</td>
     			</tr>
    			<c:forEach var="data" varStatus="status" items="${listBalance}">
                  	<tr>
	      			    <td class="toDataTd" align="center"><font color="red"><b>${data["gamename"] }</b></font></td>
	      			    <td class="toDataTd" align="center"><font color="red"><b>${data["gamebalance"] }</b></font>元</td>
	      			</tr>
                </c:forEach>
  			</table>
  			  
  			  
  			  <div class="row actions-bar">
		              <div class="form-actions span5 offset3">
                        <input type="hidden" id="J_Page_Parent" value="${requestScope.MENUS.parentmenucode}" data-reload="true" >
		                <button class="button" type="button" onclick="top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">返回列表</button>
		              </div>
	              </div>
	              
</body>
</html>
  
<script type="text/javascript">

    
</script>