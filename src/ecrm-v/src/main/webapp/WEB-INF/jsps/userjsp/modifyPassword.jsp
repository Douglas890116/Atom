<%@page import="com.maven.entity.EnterpriseEmployee"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body>
  <c:if test="${sessionScope.ERCM_USER_SESSION.employeecode == 'E0000000'}">
    <h1 style="margin-left: 20px; margin-top: 20px">超级管理员不可直接修改密码，请联系开发人员 ! ! !</h1>
  </c:if>
  <c:if test="${sessionScope.ERCM_USER_SESSION.employeecode != 'E0000000'}">
  <div class="demo-content" style="margin: 27px;">
      <div class="doc-content">
          <ul class="link-tabs">
          <c:choose>
            <c:when test="${sessionScope.LANGUAGE == 'en'}">
              <li class="active"><a href="javascript:loginpwd();">Login Password</a></li>
              <li ><a href="javascript:capitalpwd();">Funds Password</a></li>
            </c:when>
            <c:otherwise>
              <li class="active"><a href="javascript:loginpwd();">登录密码</a></li>
              <li ><a href="javascript:capitalpwd();">资金密码</a></li>
            </c:otherwise>
          </c:choose>

          </ul>
      </div>
<!--       登录密码 -->
      <div class="row">
        <div>
            <!--       登录密码 -->
            <form method="post" id="oneForm" class="form-horizontal" >
                <div class="control-group">
                  <c:choose>
                    <c:when test="${sessionScope.LANGUAGE == 'en'}"><label class="control-label">Account：</label></c:when>
                    <c:otherwise><label class="control-label">账&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号：</label></c:otherwise>
                  </c:choose>
                  <label style="position:absolute;top:76px;left:148px;font-size:15px;">${sessionScope.ERCM_USER_SESSION.loginaccount}</label>
                  <input class="input-normal control-text" name="loginaccount" type="hidden" value="${sessionScope.ERCM_USER_SESSION.loginaccount}" style="width:167px;height:28px;">
                </div>
                <div class="control-group">
                  <c:choose>
                    <c:when test="${sessionScope.LANGUAGE == 'en'}"><label class="control-label">Old Password：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label></c:when>
                    <c:otherwise><label class="control-label">旧&nbsp;&nbsp;密&nbsp;&nbsp;码：</label></c:otherwise>
                  </c:choose>
                  <div class="controls">
                      <input class="input-normal control-text" name="loginpassword" type="password" data-rules="{required:true}" data-tip="{text:'现在使用的登录密码'}" style="width:167px;height:18px;" />
                      <div class="common_mark"></div>
                  </div>
                </div>
                <div class="control-group">
                  <c:choose>
                    <c:when test="${sessionScope.LANGUAGE == 'en'}"><label class="control-label">New Password：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label></c:when>
                    <c:otherwise><label class="control-label">新&nbsp;&nbsp;密&nbsp;&nbsp;码：</label></c:otherwise>
                  </c:choose>
                  <div class="controls">
                      <input class="input-normal control-text" name="newLoginpassword" type="password" id="newLoginpasswordId" data-rules="{required:true,minlength:6,maxlength:20,regexp:/^[a-z0-9A-Z]{6,20}$/}" data-tip="{text:'请填写一个6-20位的密码'}" data-messages="{regexp:'密码只能是数字或者字母'}" style="width:167px;height:18px;"/>
                      <div class="common_mark"></div>
                  </div>
                </div>
                <div class="control-group">
                  <c:choose>
                    <c:when test="${sessionScope.LANGUAGE == 'en'}"><label class="control-label">Confirm Password：</label></c:when>
                    <c:otherwise><label class="control-label">确认密码：</label></c:otherwise>
                  </c:choose>
                  <div class="controls">
                      <input class="input-normal control-text" name="confirmNewLoginpassword" type="password" data-rules="{required:true,equalTo:'#newLoginpasswordId'}" data-tip="{text:'确保两次输入的密码一致'}" style="width:167px;height:18px;"/>
                      <div class="common_mark"></div>
                  </div>
                </div>
                <hr>
                <div class="row actions-bar">
                <div class="form-actions span5 offset1">
                  <c:choose>
                    <c:when test="${sessionScope.LANGUAGE == 'en'}">
                      <button type="button" class="button button-primary" id="J_Form_Submit"> OK </button>
                      <a href="javascript:top.topManager.closePage();"><button class="button button-danger" type="button">Back To List</button></a>
                    </c:when>
                    <c:otherwise>
                      <button type="button" class="button button-primary" id="J_Form_Submit"> 确定 </button>
                      <a href="javascript:top.topManager.closePage();"><button class="button button-danger" type="button">返回列表</button></a>
                    </c:otherwise>
                  </c:choose>
                </div>
 			  </div>
                
            </form>
             <!--       资金密码 -->
            <form method="post" id="twoForm" class="form-horizontal" style="display:none;">
                <div class="control-group">
                  <c:choose>
                    <c:when test="${sessionScope.LANGUAGE == 'en'}"><label class="control-label">Account： </label></c:when>
                    <c:otherwise><label class="control-label">账&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号：</label></c:otherwise>
                  </c:choose>
                  <label style="position:absolute;top:76px;left:148px;font-size:15px;">${sessionScope.ERCM_USER_SESSION.loginaccount}</label>
                  <input class="input-normal control-text" name="loginaccount" type="hidden" value="${sessionScope.ERCM_USER_SESSION.loginaccount}" style="width:167px;height:28px;">
                </div>
                <% if(StringUtils.isNotEmpty(((EnterpriseEmployee)session.getAttribute("ERCM_USER_SESSION")).getFundpassword())){ %>
                <div class="control-group">
                  <c:choose>
                    <c:when test="${sessionScope.LANGUAGE == 'en'}"><label class="control-label">Old Password：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label></c:when>
                    <c:otherwise><label class="control-label">旧&nbsp;&nbsp;密&nbsp;&nbsp;码：</label></c:otherwise>
                  </c:choose>
                  <div class="controls">
                      <input class="input-normal control-text" name="fundpassword" type="password" data-rules="{required:true}" placeholder="现在使用的取款密码" style="width:167px;height:18px;" />
                      <div class="common_mark"></div>
                  </div>
                </div>
                <% } %> 
                <div class="control-group">
                  <c:choose>
                    <c:when test="${sessionScope.LANGUAGE == 'en'}"><label class="control-label">New Password：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label></c:when>
                    <c:otherwise><label class="control-label">新&nbsp;&nbsp;密&nbsp;&nbsp;码：</label></c:otherwise>
                  </c:choose>
                  <div class="controls">
                      <input class="input-normal control-text" name="newCapitalpwd" type="password" id="newCapitalpwdId" data-rules="{required:true,minlength:6,maxlength:20,regexp:/^[a-z0-9A-Z]{6,20}$/}" placeholder="请填写一个6-20位的密码" data-messages="{regexp:'密码只能是数字或者字母'}" style="width:167px;height:18px;"/>
                      <div class="common_mark"></div>
                  </div>
                </div>
                <div class="control-group">
                  <c:choose>
                    <c:when test="${sessionScope.LANGUAGE == 'en'}"><label class="control-label">Confirm Password：</label></c:when>
                    <c:otherwise><label class="control-label">确认密码：</label></c:otherwise>
                  </c:choose>
                  <div class="controls">
                      <input class="input-normal control-text" name="confirmNewCapitalpwd" type="password" data-rules="{required:true,equalTo:'#newCapitalpwdId'}" placeholder="确保两次输入的密码一致" style="width:167px;height:18px;"/>
                      <div class="common_mark"></div>
                  </div>
                </div>
                <hr>
                <div class="row actions-bar">
                  <div class="form-actions span5 offset1">
                  <c:choose>
                    <c:when test="${sessionScope.LANGUAGE == 'en'}">
                      <button type="button" class="button button-primary" id="J_Form_Submit_capital"> OK </button>
                      <a href="javascript:top.topManager.closePage();"><button class="button button-danger" type="button">Back To List</button></a>
                    </c:when>
                    <c:otherwise>
                      <button type="button" class="button button-primary" id="J_Form_Submit_capital"> 确定 </button>
                      <a href="javascript:top.topManager.closePage();"><button class="button button-danger" type="button">返回列表</button></a>
                    </c:otherwise>
                  </c:choose>
                  </div>
                </div>
               
            </form>
        </div>
    </div>
  </div>
  </c:if>
</body>
</html>
<script type="text/javascript">
function loginpwd(){
	$('#oneForm').show();
	$('#twoForm').hide();
}
function capitalpwd(){
	$('#oneForm').hide();
	$('#twoForm').show();
}
$("ul.link-tabs").children().each(function(){
	$(this).bind('click',function(){
		$(this).addClass("active");
		$(this).siblings().removeClass("active");
	});	
});

$(function(){
  BUI.use(['bui/overlay','bui/mask']);
  //登录密码
  var form = new BUI.Form.HForm({
       srcNode : '#oneForm',
  }).render();
  bindpwdSubmit("J_Form_Submit","${ctx}/common/updatePassword",form,'oneForm');
  //资金密码
   var formTwo = new BUI.Form.HForm({
      srcNode : '#twoForm',
 }).render();
   bindpwdSubmit("J_Form_Submit_capital","${ctx}/common/updateCapital",formTwo,'twoForm'); 
   
   function bindpwdSubmit(buttonId, url,form,formId){
		jQuery("#" + buttonId).click(function() {
			if(form.isValid()){
				$.ajax({
					type: "POST",
					url: url,
					data:$('#'+formId).serialize(),
					dataType: "json",
					statusCode:{404:function(){
						BUI.Message.Alert("请求未发送成功",'error');
					}},
				    success: function(data) {
					    if(data.status == "1"){
				    		BUI.Message.Alert(data.message,'success');
					    }else{
					    	BUI.Message.Alert(data.message,'warning');
					    }
				    }
				});
			}
			
		});
	}
});
</script>

