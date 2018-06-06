<%@page import="com.maven.cache.SystemCache"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body>
  <div class="demo-content" style="margin: 27px;">
    <div class="row">
      <div>
        <h2><a href="javascript:top.topManager.reloadPage('${requestScope.MENUS.parentmenucode}');top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">${requestScope.MENUS.parentmenuname}</a><span class="divider"> / </span>${requestScope.MENUS.menuname }</h2>
        <hr>
            <form:form id="objForm" method="post" class="form-horizontal">
            	<div class="control-group">
                    <label class="control-label">运营品牌:</label>
                    <div class="controls">
			           <select  style="width:200px;" name="brandcode">
							<c:forEach items="${brands}" var="brand">
								<option value="${brand.brandcode }">${brand.brandname }</option>
							</c:forEach>
                        </select>            
                    </div>
                  </div>  
                  <hr/>
	              	<div class="control-group">
		                <label class="control-label">分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;红:</label>
		                	<div class="controls">
			                  <input class="input-normal control-text" type="text" name="dividend" value="0" style="width:99px;height:18px;" data-rules="{required:true,number:true,min:0,max:${sessionScope.ERCM_USER_SESSION.dividend*100 }}"/> % （最高<fmt:formatNumber type="number" value="${sessionScope.ERCM_USER_SESSION.dividend*100 }" pattern="0.00" maxFractionDigits="2"/>%）
			                </div>
			                <label class="control-label" style="margin-left: 50px">占&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;成:</label>
			                <div class="controls">
			                  <input class="input-normal control-text" type="text" name="share" value="0" style="width:99px;height:18px;" data-rules="{required:true,number:true,min:0,max:${sessionScope.ERCM_USER_SESSION.share*100 }}"/> % （最高<fmt:formatNumber type="number" value="${sessionScope.ERCM_USER_SESSION.share*100 }" pattern="0.00" maxFractionDigits="2"/>%）
			                </div>
	              	</div>
	              	<div class="control-group" >
		                <label class="control-label">洗&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码:</label>
		                <div style="clear: both;">
		               		<c:forEach var="game" varStatus="status" items="${games}">
			                	<div class="control-group" >
					                <label class="control-label" style="color:red;width: 50px;"><b>${game.gametype.replace("Game","") }:</b></label>
					                <div class="controls">
					                	<c:forEach items="${SystemCache.getInstance().getGameCategory(game.gametype)}" var="category">
						                <c:set var="key" scope="page" value="${category.gametype}_${category.categorytype}"/>
						                <c:set var="maxbonus" scope="page" value="${bonus.get(key)==null?0:bonus.get(key)*100}"/>
								             <div style="float: left;">
								             ${category.categoryname}：<input  class="input-normal control-text" type="text" name="${key}" value="0"   style="width:30px;height:18px;" data-rules="{required:true,number:true,min:${category.minbonus*100 },max:${maxbonus}}" /> % （最高<fmt:formatNumber type="number" value="${maxbonus}" pattern="0.00" maxFractionDigits="2"/> %）
								             </div>
								         </c:forEach>
					                </div>
				              	</div>
				            </c:forEach>
			              	<div style="clear: both;"></div>
		                </div>
	              	</div>
	              	<hr/>
	              	<div class="control-group">
	                <label class="control-label">注册链接:</label>
	                <div class="controls">
	                    <input class="input-normal control-text" type="text" name="domainlink" id="domainlink"   style="width:480px;height:24px;border: none;" disabled="disabled"/>
	                </div>
	              </div>
	              	<hr/>
	              <div class="row actions-bar">
		              <div class="form-actions span8 offset3">
		                <button type="button" class="button button-primary" id="J_Form_Submit" ><b> 生成链接</b> </button>
		                <button class="button button-danger" type="button" onclick="top.topManager.reloadPage('${requestScope.MENUS.parentmenucode}');top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">返回列表</button>
		              </div>
	              </div>
        </form:form>
      </div>
    </div>  
  </div>
</body>
</html>
<script type="text/javascript">
$(function(){
	BUI.use(['bui/overlay','bui/mask']);
	var form = new BUI.Form.HForm({
	     srcNode : '#objForm',
	}).render();
	$("#J_Form_Submit").click(function() {
		if(form.isValid()){
			$.ajax({
				type: "POST",
				url: "${ctx}/registerLink/addRegisterLink",
				data:$('#objForm').serialize(),
				async: false,
				dataType: "json",
			    success: function(data) {
				    if(data.status == "1"){
				    	$("#domainlink").val(data.message).removeAttr("disabled").attr("readonly","readonly").css({"border":"1px solid #5bc0de"});
				    	BUI.Message.Alert("操作成功",function(){
				    		top.topManager.reloadPage('MN0044');
				    	},'success');
				    }else{
				    	BUI.Message.Alert(data.message,'warning');
				    }
			    }
			});
		}
		
	});
});
</script>
</html>
