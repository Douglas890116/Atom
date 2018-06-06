<%@page import="com.maven.cache.SystemCache"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body>
	<div class="demo-content" style="margin: 27px;">
		<div class="row">
			<div>
				<h2>
					<a href="javascript:top.topManager.reloadPage('${requestScope.MENUS.parentmenucode}');top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">${requestScope.MENUS.parentmenuname}</a><span
						class="divider"> / </span>${requestScope.MENUS.menuname }</h2>
				<hr>
				<form:form id="objForm" method="post" class="form-horizontal">
					<div class="control-group">
						<label class="control-label">代理站点域名:</label>
						<div class="controls">
							<input type="text" name="domainlink" value="${registerDomain.domainlink}" data-rules="{required:true}" data-tip="{text:'会员站点域名'}"/>
							<input type="hidden" name="code" value="${registerDomain.domaincode}"/>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">代理站点模板:</label>
						<div class="controls">
							<select name="webtemplatecode">
								<c:forEach items="${templates}" var="template">
									<option value="${template.webtemplatecode}" ${registerDomain.webtemplatecode == template.webtemplatecode ? 'selected="selected"' : ''}>${template.templatename}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<hr/>
					<div class="control-group">
						<fmt:parseNumber value="${registerDomain.dividend * 100}" var="dividend" />
						<fmt:formatNumber value="${dividend}" pattern="0.00" var="dividend"/>
						
						<fmt:parseNumber value="${registerDomain.share * 100}" var="share" />
						<fmt:formatNumber value="${share}" pattern="0.00" var="share"/>

		                <label class="control-label">分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;红:</label>
		                	<div class="controls">
			                  <input class="input-normal control-text" type="text" name="dividend" value="${dividend }" style="width:99px;height:18px;" data-rules="{required:true,number:true,min:0,max:${sessionScope.ERCM_USER_SESSION.dividend*100 }}"/> % （最高<fmt:formatNumber type="number" value="${sessionScope.ERCM_USER_SESSION.dividend*100 }" pattern="0.00" maxFractionDigits="2"/>%）
			                </div>
			                <label class="control-label" style="margin-left: 50px">占&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;成:</label>
			                <div class="controls">
			                  <input class="input-normal control-text" type="text" name="share" value="${share }" style="width:99px;height:18px;" data-rules="{required:true,number:true,min:0,max:${sessionScope.ERCM_USER_SESSION.share*100 }}"/> % （最高<fmt:formatNumber type="number" value="${sessionScope.ERCM_USER_SESSION.share*100 }" pattern="0.00" maxFractionDigits="2"/>%）
			                </div>
	              	</div>
					
					<div class="control-group">
						<label class="control-label">洗&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码:</label>
						<div style="clear: both;">
							<c:forEach var="game" varStatus="status" items="${games}">
								<div class="control-group">
									<label class="control-label" style="color: red; width: 50px;"><b>${game.gametype.replace("Game","") }:</b></label>
									<div class="controls">
										<c:forEach
											items="${SystemCache.getInstance().getGameCategory(game.gametype)}"
											var="category">
											<c:set var="key" scope="page" value="${category.gametype}_${category.categorytype}" />
											<c:set var="maxbonus" scope="page" value="${bonus.get(key)==null?0:bonus.get(key)*100}" />
											
											<fmt:formatNumber value="${bonusItem.get(key)*100}" pattern="0.0" var="vvv"/>
											
											<div style="float: left;">
												${category.categoryname}：<input class="input-normal control-text" type="text" name="${key}"
													value="${vvv }" style="width: 30px; height: 18px;"
													data-rules="{required:true,number:true,min:${category.minbonus*100 },max:${maxbonus}}" />
												% （最高<fmt:formatNumber type="number" value="${maxbonus}" pattern="0.00" maxFractionDigits="2"/> %）
											</div>
										</c:forEach>
									</div>
								</div>
							</c:forEach>
							<div style="clear: both;"></div>
						</div>
					</div>
					<hr />
					<div class="row actions-bar">
						<div class="form-actions span5 offset3">
							<input type="hidden" id="J_Page_Parent" value="${requestScope.MENUS.parentmenucode}" data-reload="true">
							<button type="submit" class="button button-primary">
								<b>保存</b>
							</button>
							<button class="button button-danger" type="button"
								onclick="top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">返回列表</button>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
	var domain = 0;
	function formSubmit(obj) {
		$.ajax({
			type : 'post',
			url : '${ctx}/registerLink/agentSiteSave',
			dataType : "json",
			data : obj,
			success : function(data) {
				if (data.status == 1)
					top.topManager.openPage({id : '${requestScope.MENUS.parentmenucode}',isClose : true});
				else
					BUI.Message.Alert(data.message, 'error');
			},
			error : function() {
				BUI.Message.Alert('系统错误，请联系管理员!', 'error');
			}
		});
	}
	
	$(function() {
		BUI.use('custom/commons');
		BUI.use('bui/overlay');
		BUI.use([ 'bui/mask' ]);
		var form = new BUI.Form.HForm({
			srcNode : '#objForm',
		}).render();

		form.getField('domainlink').set('remote', {
			url : '${ctx}/common/isDomainResolution',
			dataType : 'json',
			callback : function(data){
				if (data.status != 0) {
					if (data.status < 0){
						domain = -1;
						return data.msg;
					}
					if (data.status == 1) domain = 1;	
				}
			}
		});
	});
	
	$("#objForm").submit(function() {
		if (domain < 0) return false;
		var obj = BUI.FormHelper.serializeToObject(this);
		if (domain == 1) {
			BUI.Message.Confirm("域名未解析，是否提交 ?", function() {formSubmit(obj);}, 'question');
		} else {
			formSubmit(obj);
		}
		return false;
	});
</script>
</html>
<%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>