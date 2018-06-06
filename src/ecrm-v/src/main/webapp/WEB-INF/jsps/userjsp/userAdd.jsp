<%@page import="com.maven.cache.SystemCache"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<c:set var="isEnglish" value="${sessionScope.LANGUAGE == 'en'}"/>
<body>
  <div class="demo-content" style="margin: 27px;">
    <div class="row">
      <div>
        <h2><a href="javascript:top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">${requestScope.MENUS.parentmenuname}</a><span class="divider"> / </span>${requestScope.MENUS.menuname }</h2>
        <hr>
            <form:form modelAttribute="enterpriseEmployee" id="objForm" method="post" class="form-horizontal">
	           <div class="control-group">
                  <label class="control-label">${isEnglish?'Brand:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;':'所&nbsp;&nbsp;属&nbsp;&nbsp;&nbsp;品&nbsp;&nbsp;牌&nbsp;:'}</label>
                  <div class="controls">
                      <select name="brandcode" style="width:192px;height:30px;" data-rules="{required:true}" >
                          <option value="">${isEnglish?'===Choose Brand===':'请选择品牌'}</option>
                      </select>
                  </div>
               </div> 
               <div class="control-group">
	                <label class="control-label">${isEnglish?'User Type:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;':'员&nbsp;&nbsp;工&nbsp;&nbsp;&nbsp;类&nbsp;&nbsp;别&nbsp;:'}</label>
	                <div class="controls">
	                    <select name="employeetypecode" data-rules="{required:true}" style="width:192px;height:30px;">
	                        <option value="T003" >${isEnglish?'Member':'会员'}</option>
	                    </select>
                        <div class="common_mark"></div>
	                </div>
	            </div>
                <div class="control-group">
                  <label class="control-label">${isEnglish?'Member Name:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;':'用&nbsp;&nbsp;户&nbsp;&nbsp;&nbsp;昵&nbsp;&nbsp;称&nbsp;:'}</label>
                    <div class="controls">
                        <input class="input-normal control-text" name="displayalias" type="text" style="width:167px;height:18px;" data-rules="{required:true,maxlength:8}" data-tip="{text:'长度不超过8个字符'}"/>
                        <div class="common_mark"></div>
                   </div>
                </div>
                <div class="control-group">
                  <label class="control-label">${isEnglish?'Account:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;':'注&nbsp;&nbsp;册&nbsp;&nbsp;&nbsp;账&nbsp;&nbsp;号&nbsp;:'}</label>
                  <div class="controls">
                    <input class="input-normal control-text" name="loginaccount"  type="text"  data-remote="${ctx}/EEmployee/employeeIsExistForBUI"  data-rules="{required:true,minlength:6,maxlength:12,regexp:/^[0-9a-zA-z]{6,12}$/}" data-messages="{regexp:'账号只能是数字或者字母'}" data-tip="{text:'长度为6-12个字符'}" style="width:167px;height:18px;"/>
                    <div class="common_mark"></div>
                  </div>
                </div>
                <div class="control-group">
                  <label class="control-label">${isEnglish?'Login Password:&nbsp;&nbsp;&nbsp;&nbsp;':'登&nbsp;&nbsp;陆&nbsp;&nbsp;&nbsp;密&nbsp;&nbsp;码&nbsp;:'}</label>
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
                  <label class="control-label">${isEnglish?'Confirm Password:':'确认登陆密码:'}</label>
                  <div class="controls">
                      <input class="input-normal control-text" name="confirmLoginpassword"  type="password" style="width:167px;height:18px;" maxlength="20" data-rules="{required:true,equalTo:'#passwordId'}" data-tip="{text:'与登陆密码一致'}"/>
                      <div class="common_mark"></div>
                  </div>
                </div>
	              	<hr/>
	              	<div style="margin-bottom: 15px;color: red;">(注：洗码值设置以后只升不降)</div>
	              	<div class="control-group" >
		                <label class="control-label">${isEnglish?'Washing Code':'洗&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码'}:</label>
		                <div style="clear: both;">
		               		<c:forEach var="game" varStatus="status" items="${games}">
			                	<div class="control-group" >
					                <label class="control-label" style="color:red;width: 50px;"><b>${game.gametype.replace("Game","") }:</b></label>
					                <div class="controls">
					                	<c:forEach items="${SystemCache.getInstance().getGameCategory(game.gametype)}" var="category">
						                <c:set var="key" scope="page" value="${category.gametype}_${category.categorytype}"/>
						                <c:set var="maxbonus" scope="page" value="${bonus.get(key)==null?0:bonus.get(key)*100}"/>
								             <div style="float: left;">
								             ${category.categoryname}：<input  class="input-normal control-text" type="text" name="${key}"  value="0"  style="width:30px;height:18px;" data-rules="{required:true,number:true,min:${category.minbonus*100 },max:${maxbonus}}" /> % （${isEnglish?'Max':'最高'}<fmt:formatNumber type="number" value="${maxbonus}" pattern="0.00" maxFractionDigits="2"/> %）
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
		                <button type="button" class="button button-primary" id="J_Form_Submit" > ${isEnglish?'Submit':'提 交'} </button>
		                <button class="button" type="button" onclick="top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});"> ${isEnglish?'Back To List':'返回列表'} </button>
		              </div>
	              </div>
        </form:form>
      </div>
    </div>  
  </div>
</body>
</html>

<script type="text/javascript">
//密码验证规则
BUI.use('bui/form',function(Form){
    Form.Rules.add({
        name : 'pwd',  //规则名称
        msg : '密码必须由数字和小写字母组成',//默认显示的错误信息
        validator : function(value,baseValue,formatMsg){// 验证函数，验证值、基准值、格式化后的错误信息
        	var reg2 = new RegExp("[a-z]+");// 验证必须包含小写字母
        	var reg3 = new RegExp("[0-9]+");// 验证必须包含数字
        	if(!reg2.test(value) || !reg3.test(value)){
            return formatMsg;// 返回默认错误信息
          }
        }
      }); 
});

if(''=='${employee.brandcode}' || null=='${employee.brandcode}'){
	loadEnterpriseBrand();
}else{
	$("select[name='brandcode']").append("<option value='${employee.brandcode}'>"+'${employee.brandname}'+"</option>");
}
$(function(){
	BUI.use(['bui/overlay','bui/mask']);
	var form = new BUI.Form.HForm({
	     srcNode : '#objForm',
	}).render();
	bindClick("J_Form_Submit","${ctx}/EEmployee/saveEnterpriseEmployee",form);
});
</script>
<%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>