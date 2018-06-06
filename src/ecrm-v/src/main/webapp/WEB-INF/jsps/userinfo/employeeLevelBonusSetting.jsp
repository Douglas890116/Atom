<%@page import="com.maven.entity.EnterpriseEmployeeType.Type"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.maven.cache.SystemCache"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<c:set var="etype" value="<%=Type.会员.value %>"></c:set>
<!DOCTYPE html>
<html>
   <head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   <title>结算设置</title>
   <!-- 此文件为了显示Demo样式，项目中不需要引入 -->
	<link href="${statics}/css/bs3/bui-min.css" rel="stylesheet" />
	<link href="${statics}/css/bs3/dpl-min.css" rel="stylesheet" /> 
<body>
<div class="demo-content" style="margin: 27px;">
    <div class="row" style="margin: 5px;">
    	<h2><a href="javascript:top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">${requestScope.MENUS.parentmenuname}</a><span class="divider"> / </span>${requestScope.MENUS.menuname }</h2>
        <hr>
            <form:form modelAttribute="enterpriseEmployee" id="objForm"  method="post" class="form-horizontal">
	              	<div class="control-group" >
		                <label class="control-label">等级名称:&nbsp;&nbsp; <span style="font-size: 18px;color: #428bca;">${employeeLevel.employeeLevelName }</span></label>
		                <input type="hidden" name="employeelevelcode"  value="${employeeLevel.employeeLevelCode }"  data-rules="{required:true}"  />
	              	</div>
	              	
	              	<hr/>	
	              	<div class="control-group" >
		                <label class="control-label">洗&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码:</label>
		                <div style="clear: both;">
		               		<c:forEach var="game" varStatus="status" items="${games}">
			                	<div class="control-group" >
					                <label class="control-label" style="color:red;width: 50px;"><b>${game.gametype.replace("Game","") }:</b></label>
					                <div class="controls">
					                <c:forEach items="${SystemCache.getInstance().getGameCategory(game.gametype)}" var="category">
							             <div style="float: left;">
							             <c:set var="key" scope="page" value='${category.gametype}_${category.categorytype}'/>
							             <c:set var="maxbonus" scope="page" value="${supbonus.get(key)==null?0:supbonus.get(key)*100}"/>
							             ${category.categoryname}：<input  class="input-normal control-text" type="text" name="type_${key}" value="<fmt:formatNumber type="number" value="${bonus.get(key).doubleValue()*100 }" pattern="0.00" maxFractionDigits="2"/>"   style="width:30px;height:18px;" data-rules="{required:true,number:true,min:${submaxbonus.get(key)*100},max:${maxbonus}}" /> % （最高<fmt:formatNumber type="number" value="${maxbonus}" pattern="0.00" maxFractionDigits="2"/> %）
							             </div>
							         </c:forEach>
					                </div>
				              	</div>
				            </c:forEach>
			              	<div style="clear: both;"></div>
		                </div>
	              	</div>
	              	<hr/>
	              	<div style="color: green;font-size: 14px">*每周进行统计和等级用户自动升级，升级完成后按当前配置进行洗码结算</div>
	              <div class="row actions-bar">
		              <div class="form-actions span5 offset3">
		                <input type="hidden" id="J_Page_Parent" value="${requestScope.MENUS.parentmenucode}" data-reload="false" >
		                <button type="button" class="button button-primary" id="J_Form_Submit" > 提 交 </button>
		                <button class="button button-danger" type="button" onclick="top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">返回列表</button>
		              </div>
	              </div>
        </form:form>
      </div>
    </div>
    <script src="${statics }/js/jquery-1.8.1.min.js"></script>
	<script src="${statics }/js/bui.js"></script> 
	<script src="${statics }/js/config.js"></script>
	<script type="text/javascript">
	$(function(){
		BUI.use(['bui/overlay','bui/mask']);
		var form = new BUI.Form.HForm({
		     srcNode : '#objForm',
		}).render();
		bindClick("J_Form_Submit","${ctx}/employeeLevelBonus/saveSetting",form);
	});
	</script>
	<%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>
</body>
</html>


