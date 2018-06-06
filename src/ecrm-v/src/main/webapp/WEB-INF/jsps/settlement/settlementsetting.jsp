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
		                <label class="control-label">用&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;户:&nbsp;&nbsp; <span style="font-size: 18px;color: #428bca;">${user.loginaccount }</span></label>
		                <input type="hidden" name="employeecode"  value="${user.employeecode }"  data-rules="{required:true}"  />
	              	</div>
	              	<!-- 
	              	<div style="color: red;line-height: 35px;font-size: 14px">注：---- 分红、占成、洗码 设置之后只升不降 ----</div>
	              	 -->
	              	<div style="color: green;line-height: 35px;font-size: 14px">注：分红、占成、洗码不能低于下线的最高分红、占成、洗码值</div>
	              	 
	              	<c:if test="${user.employeetypecode!=etype}">
	              	<div class="control-group">
		                <label class="control-label">分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;红:</label>
		                <div class="controls">
		                	<!-- 
		                	<input class="input-normal control-text" type="text" name="dividend" value="${user.dividend.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()*100 }" style="width:35px;height:18px;" data-rules="{required:true,number:true,min:${user.dividend*100 },max:${superior.dividend*100 }}"/> %
		                	 -->
		                	 
		                	 <c:set var="max_dividend" scope="page" value='max_dividend'/>
		                	 <c:set var="max_share" scope="page" value='max_share'/>
		                	 <!-- 
		                	 max_dividend=${submaxbonus.get(max_dividend)*100 }
		                	 max_share=${submaxbonus.get(max_share)*100 }
		                	  -->
		                	 <input class="input-normal control-text" type="text" name="dividend" value="<fmt:formatNumber type="number" value="${user.dividend * 100 }" pattern="0.00" maxFractionDigits="2"/>" style="width:35px;height:18px;" data-rules="{required:true,number:true,min:${submaxbonus.get(max_dividend)*100 },max:${superior.dividend*100 }}"/> % （最高<fmt:formatNumber type="number" value="${superior.dividend*100 }" pattern="0.00" maxFractionDigits="2"/> %）
		                </div>
		                <label class="control-label" style="margin-left: 50px">占&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;成:</label>
		                <div class="controls">
		                  <input class="input-normal control-text" type="text" name="share" value="<fmt:formatNumber type="number" value="${user.share * 100 }" pattern="0.00" maxFractionDigits="2"/>" style="width:35px;height:18px;" data-rules="{required:true,number:true,min:${submaxbonus.get(max_share)*100 },max:${superior.share*100 }}"/> % （最高<fmt:formatNumber type="number" value="${superior.share*100 }" pattern="0.00" maxFractionDigits="2"/> %）
		                </div>
	              	</div>
	              	</c:if>
	              	<hr/>	
	              	<div class="control-group" >
		                <label class="control-label">洗&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码:</label>
		                <div style="clear: both;">
		               		<c:forEach var="game" varStatus="status" items="${games}">
			                	<div class="control-group" >
					                <%-- <label class="control-label" style="color:red;width: 50px;"><b>${game.gametype.replace("Game","") }:</b></label> --%>
					                <label class="control-label" style="color:red;width: 100px;text-align:right"><b>${game.gamename }:</b></label>
					                <div class="controls">
					                <c:forEach items="${SystemCache.getInstance().getGameCategory(game.gametype)}" var="category">
							             <div style="float: left;">
							             <c:set var="key" scope="page" value='${category.gametype}_${category.categorytype}'/>
							             <c:set var="maxbonus" scope="page" value="${supbonus.get(key)==null?0:supbonus.get(key)*100}"/>
							             ${category.categoryname}：<input  class="input-normal control-text" type="text" name="${key}" value="<fmt:formatNumber type="number" value="${bonus.get(key).doubleValue()*100 }" pattern="0.00" maxFractionDigits="2"/>"   style="width:30px;height:18px;" data-rules="{required:true,number:true,min:${submaxbonus.get(key)*100},max:${maxbonus}}" /> % （最高<fmt:formatNumber type="number" value="${maxbonus}" pattern="0.00" maxFractionDigits="2"/> %）
							             </div>
							         </c:forEach>
					                </div>
				              	</div>
				            </c:forEach>
			              	<div style="clear: both;"></div>
		                </div>
	              	</div>
	              	<hr/>
	              	<div style="color: green;font-size: 14px">*每日结算时按照最后一次修改的值计算</div>
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
		bindClick("J_Form_Submit","${ctx}/GCBonus/saveSetting",form);
	});
	</script>
	<%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>
</body>
</html>


