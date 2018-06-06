<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<script type="text/javascript" src="${statics }/js/custom/commonJS.js"></script>
<script type="text/javascript" src="${statics }/js/custom/submitJS.js"></script>
<body>
  <div class="demo-content" style="margin: 27px;">
    <div class="row">
      <div>
        <h2>员工密码修改</h2>
        <hr>
          <form:form modelAttribute="enterpriseEmployee" method="post" action="" class="form-horizontal">
              <div class="control-group">
                <label class="control-label">账&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号:</label>
                <label style="position:absolute;top:100px;left:148px;">${loginaccount}</label>
                <input class="input-normal control-text" name="loginaccount" type="hidden" value="${loginaccount}" style="width:167px;height:18px;">
              </div>
              <div class="control-group">
                <label class="control-label">旧&nbsp;&nbsp;密&nbsp;&nbsp;码:</label>
                <div class="controls">
                    <input class="input-normal control-text" name="loginpassword" type="password" style="width:167px;height:18px;" />
                    <div class="common_mark"></div>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">新&nbsp;&nbsp;密&nbsp;&nbsp;码:</label>
                <div class="controls">
                    <input class="input-normal control-text" name="newLoginpassword" type="password"  style="width:167px;height:18px;"/>
                    <div class="common_mark">&nbsp;&nbsp;<span>密码只能是数字、字母与!@#$%^*组合,长度8-20位</span></div>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">确认密码:</label>
                <div class="controls">
                    <input class="input-normal control-text" name="confirmNewLoginpassword" type="password" style="width:167px;height:18px;"/>
                    <div class="common_mark">&nbsp;&nbsp;<span>两次密码请保持一致</span></div>
                </div>
              </div>
              <hr>
               <div class="row actions-bar">
 					<div class="form-actions span5 offset3">
                <button type="button" class="button button-primary" onclick="modifyPassword(1);"> 确定 </button>
                <a href="javascript:top.topManager.closePage();"><button class="button" type="button">返回列表</button></a>
              </div>
 			  </div>
              
        </form:form>
      </div>
    </div>  
  </div>
</body>
</html>
