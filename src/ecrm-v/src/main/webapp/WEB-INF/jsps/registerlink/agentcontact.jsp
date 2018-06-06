<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<!-- 代理站点联系方式 -->
    <div id="contact-panel">
        <form method="post" id="contactForm">
        	<input type="hidden" name="sign1" value="${domaincode }">
        	<input type="hidden" name="sign" value="${contact.sign }">
        	<%--  <div class="control-group">
              <label class="control-label">模板名称：</label>
                <div class="controls">
                  <input name="templatename" class="input-small control-text" style="width:167px;height:18px;" type="text" 
                         data-rules="{required:true,maxlength:25}" data-tip="{text:'模板名称'}" value="${template.templatename}"/>
                </div>
            </div> --%>
            <div class="control-group" >
            	<label class="control-label" style="margin-right: 12px;width: 80px;display: inline-block;">手机号码：</label>
		  		<div class="controls">
		  			<input name="phone" class="input-large control-text"  value="${contact.phone}" style="width: 200px;" type="text" placeholder="手机号码"  data-rules="{required:true}"/>
		  		</div>
            </div>
            <div class="control-group" >
            	<label class="control-label" style="margin-right: 12px;width: 80px;display: inline-block;">QQ：</label>
		  		<div class="controls">
		  			<input name="qq" class="input-large control-text"  value="${contact.qq}" style="width: 200px;" type="text" placeholder="QQ"  data-rules="{required:true}" />
		  		</div>
            </div>
            <div class="control-group" >
            	<label class="control-label" style="margin-right: 12px;width: 80px;display: inline-block;">SKYPE：</label>
		  		<div class="controls">
		  			<input name="skype" class="input-large control-text"  value="${contact.skype}" style="width: 200px;" type="text" placeholder="SKYPE"  data-rules="{required:true}"/>
		  		</div>
            </div>
            <div class="control-group" >
            	<label class="control-label" style="margin-right: 12px;width: 80px;display: inline-block;">微信：</label>
		  		<div class="controls">
		  			<input name="vchat" class="input-large control-text"  value="${contact.vchat}" style="width: 200px;" type="text" placeholder="微信"  data-rules="{required:true}"/>
		  		</div>
            </div>
            <div class="control-group" >
            	<label class="control-label" style="margin-right: 12px;width: 80px;display: inline-block;">EMAIL：</label>
            	<div class="controls">
		  			<input name="email" class="input-large control-text"  value="${contact.email}" style="width: 200px;" type="text" placeholder="EMAIL"  data-rules="{email:true,required:true}"/>
		  		</div>
            </div>
        </form>
  </div>
<script type="text/javascript">
var form = new BUI.Form.HForm({
	  srcNode : '#contactForm',
	}).render();
</script>