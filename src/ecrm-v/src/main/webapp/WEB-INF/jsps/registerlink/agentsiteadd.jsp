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
						<label class="control-label"
							style="width: 70px; text-align: left;">绑定品牌:</label>
						<div class="controls">
							<select style="width: 248px;" name="brandcode">
								<c:forEach items="${brands}" var="brand">
									<option value="${brand.brandcode}">${brand.brandname}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label"
							style="width: 70px; text-align: left;">域名选项:</label>
						<div class="bui-form-group controls"  data-rules="{checkRange:1}" data-messages="{checkRange:'至少选择一项！'}">
							<label class="control-label" style="width: 90px; text-align: left;">自动绑定<input type="radio" style="vertical-align: middle;" name="domainclass" value="2" checked="checked" data-target="domain-defualt"/></label>
							<label class="control-label" style="width: 90px; text-align: left;">手动定义<input type="radio" style="vertical-align: middle;" name="domainclass" value="1" data-target="domain-customer"/></label>
							<label class="control-label" style="width: 90px; text-align: left;">企业分配<input type="radio" style="vertical-align: middle;" name="domainclass" value="3" data-target="domain-enterprise"/></label> 
						</div>
					</div>
					<div class="control-group domain-defualt domain" >
						<label class="control-label"
							style="width: 70px; text-align: left;">默认域名:</label>
						<div class="controls">
							<input class="input-normal control-text" type="text" readonly="readonly"
								name="defualtdomain" id="defualt_domainlink" style="width: 220px; height: 21px;"/>
						</div>
					</div>
					<div class="control-group domain-customer domain" style="display: none;">
						<label class="control-label"
							style="width: 70px; text-align: left;">自定义域名:</label>
						<div class="controls">
							<input class="input-normal control-text" type="text"  placeholder="请输入您需要注册域名"
								name="customerdomain"  style="width: 220px; height: 21px;"
								data-rules="{regexp:/^\s*$|^([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w \.-]*)*\/?$/}"
								data-messages="{regexp:'请输入正确的域名格式，例如:www.google.com'}"
								data-remote="${ctx}/registerLink/validateExpandDomain" />
						</div>
					</div>
					<div class="control-group domain-enterprise domain" style="display: none;">
						<label class="control-label"
							style="width: 70px; text-align: left;">企业域名:</label>
						<div class="controls">
							<select style="width: 248px;" name="enterprisedomain" id="enterprise_domainlink" class="bui-form-field-select bui-form-field">
							</select>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label"
							style="width: 70px; text-align: left;">站点模板:</label>
						<div class="controls">
							<select style="width: 248px;" name="webtemplatecode">
								<c:forEach items="${templates}" var="template">
									<option value="${template.webtemplatecode}"
										data-defualt="${template.webtemplatecode}">${template.templatename}</option>
								</c:forEach>
							</select>
						</div>
					</div>

					<hr />
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
											<div style="float: left;">
												${category.categoryname}：<input class="input-normal control-text" type="text" name="${key}"
													value="0" style="width: 30px; height: 18px;"
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
							<input type="hidden" id="J_Page_Parent"
								value="${requestScope.MENUS.parentmenucode}" data-reload="true">
							<button type="button" class="button button-primary"
								id="J_Form_Submit">
								<b>注册域名</b>
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
	$(function() {
		BUI.use([ 'bui/overlay', 'bui/mask' ]);
		var form = new BUI.Form.HForm({
			srcNode : '#objForm',
		}).render();
		bindAddClick("J_Form_Submit", "${ctx}/registerLink/addAgentSiteDomain",
				form);
		$("input[name='domainclass']").on("click",function(){
			var target = $(this).attr("data-target");
			$(".domain").hide();
			if(target=="domain-enterprise"){
				if($("#enterprise_domainlink option").size()==0){
					$.ajax({
						type: "POST",
						url: "${ctx}/registerLink/takeASAvailableDomain",
						dataType: "json",
						statusCode:{404:function(){
							BUI.Message.Alert("网络异常,请求未发送成功",'error');
						}},
					    success: function(data) {
					    	var content="";
					    	if(data.length>0){
					    		$.each(data,function(i,domainlink){
						    		content +="<option value="+domainlink+">"+domainlink+"</option>";
					    		});
					    		$("#enterprise_domainlink").html(content);
					    	}else{
					    		content="<option>企业暂无可用域名</option>";
					    		
					    		$("#enterprise_domainlink").css("color","red").html(content);
					    	}
					    }
					});
				}
			}else if(target=="domain-defualt"){
				$.ajax({
					type: "POST",
					url: "${ctx}/registerLink/takeADefualtDomain",
					dataType: "text",
					statusCode:{404:function(){
						BUI.Message.Alert("网络异常,请求未发送成功",'error');
					}},
				    success: function(data) {
				    	if(data){
				    		$("#defualt_domainlink").val(data);
				    	}else{
				    		$("#defualt_domainlink").css("color","red").val("企业暂无默认域名");
				    	}
				    }
				});
			}
			$("."+target).show();
		});
		$("input[name='domainclass']:eq(0)").click();
		
	});
</script>
</html>
<%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>