<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<!-- 存款申请单审批 -->
    <div style="margin:0px 18px;font-weight: bold;" id="approve-panel">
              
              <div class="control-group">
                  <div class="controls" style="width: 100%">
					当前余额：<font color="red">${inspect.curren_money}</font>
                  </div>
              </div>
              <hr style="margin: 3px 0px;"/>
              
              <div class="control-group">
                  <div class="controls" style="width: 100%">
					<a style="display:inline-block;width: 49%">本次取款时间：<font color="red"><fmt:formatDate value="${inspect.local_take_time}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></font></a>
                    <a style="display:inline-block;width: 49%">本次取款金额：<font color="red">${inspect.local_take_money}</font></a>
                  </div>
              </div>
              <hr style="margin: 3px 0px;"/>
              
              <div class="control-group">
                  <div class="controls" style="width: 100%">
					<a style="display:inline-block;width: 49%">上次存款时间：<font color="red"><fmt:formatDate value="${inspect.last_deposit_time}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></font></a>
					<br />
                    <a style="display:inline-block;width: 49%">上次存款金额：<font color="red">${inspect.last_deposit_money}</font></a>
                    <a style="display:inline-block;width: 49%">上次存款后余额：<font color="red">${inspect.last_deposit_balance}</font></a>
                  </div>
              </div>
              <hr style="margin: 3px 0px;"/>
              
              <div class="control-group">
                  <div class="controls" style="width: 100%">
					<a style="display:inline-block;width: 49%">上次取款时间：<font color="red"><fmt:formatDate value="${inspect.last_take_time}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></font></a>
                    <a style="display:inline-block;width: 49%">上次取款金额：<font color="red">${inspect.last_take_money}</font></a>
                  </div>
              </div>
              <hr style="margin: 3px 0px;"/>
              
              <div class="control-group">
                  <div class="controls" style="width: 100%">
					<a style="display:inline-block;">上次成功取款至本次取款时间范围内所需流水要求：</a><font color="red">${inspect.need_stream_money}</font>
                  </div>
              </div>
              <hr style="margin: 3px 0px;"/>
              
              
              <div class="control-group">
                  <div class="controls">
	                	<table width="100%" border="1">
	                		<tr><td colspan="3" align="center"><a style="display:inline-block;">上次成功取款至本次取款时间范围内游戏信息</a></td></tr>
	                		<tr>
	                			<th width="25%">最近游戏时间</th>
	                			<th width="60%">游戏平台</th>
	                			<th width="15%">累计有效投注</th>
	                		</tr>
                			<c:forEach var="item" items="${listGame }" varStatus="i">
		                		<tr>
			                		<td><fmt:formatDate value="${item.ptime }" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			                		<td>${item.pcontent }</td>
			                		<td>${item.pmoney }</td>
		                		</tr>
		                	</c:forEach>
	                	</table>
                  </div>
              </div>
              <br /><hr style="margin: 3px 0px;"/>
              
              <div class="control-group">
                  <div class="controls">
	                	<table width="100%" border="1">
	                		<tr><td colspan="3" align="center"><a style="display:inline-block;">上次成功取款至本次取款时间范围内领取优惠信息</a></td></tr>
	                		<tr>
	                			<th width="25%">最近优惠时间</th>
	                			<th width="60%">优惠名称</th>
	                			<th width="15%">优惠金额</th>
	                		</tr>
                			<c:forEach var="item" items="${listActivity }" varStatus="i">
                				<tr>
			                		<td><fmt:formatDate value="${item.ptime }" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			                		<td>${item.pcontent }</td>
			                		<td>${item.pmoney }</td>
		                		</tr>
		                	</c:forEach>
	                	</table>
	                	
                  </div>
              </div>
              <br /><hr style="margin: 3px 0px;"/>
              
              <div class="control-group">
                  <div class="controls">
	                	<table width="100%" border="1">
	                		<tr><td colspan="3" align="center"><a style="display:inline-block;">上次成功取款至本次取款时间范围内上下分信息</a></td></tr>
	                		<tr>
	                			<th width="25%">最近上下分时间</th>
	                			<th width="60%">上下分平台</th>
	                			<th width="15%">上下分金额</th>
	                		</tr>	
                			<c:forEach var="item" items="${listMoneyInout }" varStatus="i">
		                		<tr>
			                		<td><fmt:formatDate value="${item.ptime }" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			                		<td>${item.pcontent }</td>
			                		<td>${item.pmoney }</td>
		                		</tr>
		                	</c:forEach>
	                	</table>
                  </div>
              </div>
              <br /><hr style="margin: 3px 0px;"/>
              
              
  </div>
