<%@page import="com.maven.cache.SystemCache"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<style type="text/css">
.breadcrumb {
	padding: 5px 10px;
	margin-right: 5px;
	list-style: none;
	border: 1px solid #ddd;
	-webkit-border-radius: 4px;
	-moz-border-radius: 4px;
	border-radius: 4px;
	-webkit-box-shadow: inset 0 1px 0 #ffffff;
	box-shadow: inset 0 1px 0 #ffffff;
}

.breadcrumb li {
	display: inline-block;
	*display: inline;
	/* IE7 inline-block hack */
	*zoom: 1;
	text-shadow: 0 1px 0 #ffffff;
}
</style>

<body>
  <div class="demo-content" style="margin: 27px;">
    <div class="row">
      <div>
        <h2><a href="javascript:top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">${requestScope.MENUS.parentmenuname}</a><span class="divider"> / </span>${requestScope.MENUS.menuname }</h2>
        <hr>
            <form:form modelAttribute="enterpriseEmployee" method="post" id="objForm" class="form-horizontal">
              <div class="control-group">
                <label class="control-label">品牌名称:</label>
                <div class="controls">
                      <select name="brandcode" style="width:192px;height:30px;">
                          <option value="">请选择品牌</option>
                      </select>
                  </div>
              </div>
              <div class="control-group">
               <label class="control-label">用户类型:</label>
               <div class="controls">
                    <select name="employeetypecode" id="employeetypeId" style="width:192px;height:30px;" data-rules="{required:true}">
                        <option value="">--请选择--</option>
                    </select>
                    <div class="common_mark"></div>
                </div>
              </div>
              <div class="control-group">
               <label class="control-label">注册账号:</label>
                <div class="controls">
                  <input class="input-normal control-text" name="loginaccount"  type="text"  data-rules="{required:true,minlength:6,maxlength:12,regexp:/^[0-9a-zA-z]{6,12}$/}" data-messages="{regexp:'账号只能是数字或者字母'}" data-tip="{text:'长度为6-12个字符'}" data-remote="${ctx}/EEmployee/employeeIsExistForBUI" style="width:167px;height:18px;"/>
                  <div class="common_mark"></div>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">登陆密码:</label>
                <div class="controls">
				<input class="input-normal control-text" name="loginpassword" id="passwordId" type="password"
					data-rules="{
								required:true,
								minlength:6,
								maxlength:12,
								regexp:[/^[a-z0-9]{6,12}$/,'密码不能包含特殊字符和大写字母 !'],
								pwd:null
								}"
					data-tip="{text:'长度为6-12个字符'}"
					style="width: 167px; height: 18px;"/>
				<div class="common_mark"></div>
                </div>
               
              </div>
               <div class="control-group">
               <label class="control-label">密码确认:</label>
                <div class="controls">
                    <input class="input-normal control-text" name="confirmLoginpassword" data-rules="{required:true,equalTo:'#passwordId'}" data-tip="{text:'与登陆密码一致'}" type="password" style="width:167px;height:18px;"/>
                    <div class="common_mark"></div>
                </div>
                </div>
               <div class="control-group">
               <label class="control-label">权限角色:</label>
                <div class="controls">
				  <div id="webviews">
				    <input type="hidden" id="rolecodes" value="" name="rolecodes">
				  </div>
                </div>
                </div>
              <hr/>
<%-- 				<div class="control-group">
					<c:if test="${rolelist != null && fn:length(rolelist) > 0}">
					<div class="row show-grid">
					<h3> 权 限 角 色 : </h3>
					<c:forEach items="${rolelist}" var="data" varStatus="i">
						下面这个span5的样式不要随意修改，如果要修改，记得修改下面的js控制
						<div class="span4">
						<a href="#" style="color: #000000;">
							<ul class="breadcrumb" ${data.rolestatus == 1 ? 'style="background-color: #FFFFE0"' : ''}>
							<li class="active"><input type="checkbox" name="rolecodes" value="${data.rolecode}" " ${data.rolestatus == 1 ? 'checked="checked"' : ''}/> ${data.rolename}</li>
							</ul>
						</a>
						</div>
					</c:forEach>
					</div>
					</c:if>
				</div>
              <br/><hr/> --%>
              <div class="control-group">
               <label class="control-label">分红/占成模式:</label>
               <div class="controls">
                    <select name="modle" id="modle" style="width:192px;height:30px;" data-rules="{required:true}" onchange="dochange(this)">
                    	<option value="">--请选择--</option>
                        <option value="1">分红模式</option>
                        <option value="2">占成模式</option>
                    </select>
                    <div class="common_mark"><font color="red">(注：设置后不能转换，并且分红、占成 设置之后只升不降)</font></div>
                </div>
              </div>
              
                  <div class="control-group" id="modle1" style="display: none">
                    <label class="control-label">分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;红:</label>
                      <div class="controls">
                        <input class="input-normal control-text" type="text" value="0" name="dividend" style="width:35px;height:18px;" data-rules="{required:true,number:true,min:0,max:${sessionScope.ERCM_USER_SESSION.dividend*100 }}"/> % （最高<fmt:formatNumber type="number" value="${sessionScope.ERCM_USER_SESSION.dividend*100 }" pattern="0.00" maxFractionDigits="2"/>%）
                      </div>
                  </div>
                  <div class="control-group" id="modle2" style="display: none">
                  	<label class="control-label" >占&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;成:</label>
                      <div class="controls">
                        <input class="input-normal control-text" type="text" value="0" name="share" style="width:35px;height:18px;" data-rules="{required:true,number:true,min:0,max:${sessionScope.ERCM_USER_SESSION.share*100 }}"/> % （最高<fmt:formatNumber type="number" value="${sessionScope.ERCM_USER_SESSION.share*100 }" pattern="0.00" maxFractionDigits="2"/>%）
                      </div>
                  </div>
                  
                  <div class="control-group" >
                    <label class="control-label">洗&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码: </label>
                    <div style="clear: both;">
                      <c:forEach var="game" varStatus="status" items="${games}">
                        <div class="control-group" >
                          <label class="control-label" style="color:red;width: 50px;"><b>${game.gametype.replace("Game","") }:</b></label>
                          <div class="controls">
                            <c:forEach items="${SystemCache.getInstance().getGameCategory(game.gametype)}" var="category">
                            <c:set var="key" scope="page" value="${category.gametype}_${category.categorytype}"/>
                            <c:set var="maxbonus" scope="page" value="${bonus.get(key)==null?0:bonus.get(key)*100}"/>
                             <div style="float: left;">
                             ${category.categoryname}：<input  class="input-normal control-text" type="text" name="${key}"  value="0"  style="width:30px;height:18px;" data-rules="{required:true,number:true,min:${category.minbonus*100 },max:${maxbonus}}" /> % （最高<fmt:formatNumber type="number" value="${maxbonus}" pattern="0.00" maxFractionDigits="2"/> %）
                             </div>
                         </c:forEach>
                          </div>
                        </div>
                    </c:forEach>
                      <div style="clear: both;"></div>
                    </div>
                  </div>
                  <hr/>
                   <div class="row actions-bar">
 						<div class="form-actions span5 offset3">
			                <input type="hidden" id="J_Page_Parent" value="${requestScope.MENUS.parentmenucode}" data-reload="true" >
				            <button type="button" id="J_Form_Submit" class="button button-primary"> 提 交 </button>
				            <button class="button button-danger" type="button" onclick="top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">返回列表</button>
			              </div>
 				</div>
              
        </form:form>
      </div>
    </div>  
  </div>
</body>
</html>
<script type="text/javascript">
	// 加载用户类型
	loadEmployeeType();
	// 加载企业品牌
	loadEnterpriseBrand();
	//密码验证规则
	BUI.use('bui/form', function(Form) {
		Form.Rules.add({
			name : 'pwd', //规则名称
			msg : '密码必须由数字和小写字母组成',//默认显示的错误信息
			validator : function(value, baseValue, formatMsg) {// 验证函数，验证值、基准值、格式化后的错误信息
				var reg2 = new RegExp("[a-z]+");// 验证必须包含小写字母
				var reg3 = new RegExp("[0-9]+");// 验证必须包含数字
				if (!reg2.test(value) || !reg3.test(value)) {
					return formatMsg;// 返回默认错误信息
				}
			}
		});
	});

	$(function() {
		BUI.use([ 'bui/overlay', 'bui/mask' ]);
		var form = new BUI.Form.HForm({
			srcNode : '#objForm',
		}).render();
		bindAddClick("J_Form_Submit", "${ctx}/EEmployee/saveEnterpriseEmployee", form);
		
		BUI.use('bui/select', function(Select) {
			$.post("${ctx}/EmployeeRole/loadRoleList", function(data) {
				var items = new Array();
				var er_list = data.data.enterpriseRoleList;
				for (var i = 0; i < er_list.length; i++) {
					items.push({
						'text' : er_list[i].rolename,
						'value' : er_list[i].rolecode
					});
				}
				select = new Select.Select({
					render : '#webviews',
					valueField : '#rolecodes',
					multipleSelect : true,
					items : items
				});
				select.render();
			});
		});
	});

/* 	$("div.span4").click(function() {
		var obj = $(this).find(":checkbox");
		if ($(obj).is(':checked')) {
			$(obj).prop("checked", false);
			$(this).find("ul").css('background-color', '#FFFFFF');
		} else {
			$(obj).prop("checked", true);
			$(this).find("ul").css('background-color', '#FFFFE0');
		}
	});
	$("input[type='checkbox'][name='rolecodes']").click(function(event) {
		event.stopPropagation();
		if ($(this).is(':checked')) {
			$(this).parent().parent().css('background-color', '#FFFFE0');
		} else {
			$(this).parent().parent().css('background-color', '#FFFFFF');
		}
	}); */

	function dochange(obj) {
		if (obj.value == "1") {
			$("#modle1").show();
			$("#modle2").hide();
		} else if (obj.value == "2") {
			$("#modle2").show();
			$("#modle1").hide();
		} else {
			$("#modle1").hide();
			$("#modle2").hide();
		}
	}
</script>
<%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>