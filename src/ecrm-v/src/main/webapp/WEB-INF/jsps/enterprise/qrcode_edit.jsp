<%@page import="com.maven.config.SystemConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body>
<div class="demo-content" style="margin: 27px;">
    <!-- 表单页 ================================================== --> 
    <div class="row">
      <div>
        <h2><a href="javascript:top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">${requestScope.MENUS.parentmenuname}</a><span class="divider"> / </span>${requestScope.MENUS.menuname }</h2>
        <hr/>
        <form aria-pressed="false" aria-disabled="false" id="objForm" class="form-horizontal bui-form bui-form-field-container" method="post" >
            
            <input name="lsh" type="hidden" value="${lsh }">
            
            <div class="control-group">
              <label class="control-label" style="width:100px;text-align:right">账户名称：</label>
                <div class="controls">
                  <input name="qraccountname" value="${enterpriseInformationQrcode.qraccountname}" class="input-small control-text" style="width:167px;height:18px;" type="text" data-rules="{required:true,maxlength:50}" data-tip="{text:'长度不超过50个字符'}"/>
                </div>
            </div>
            
            <div class="control-group">
              <label class="control-label" style="width:100px;text-align:right">账&nbsp;&nbsp;&nbsp;&nbsp;号：</label>
                <div class="controls">
                  <input name="qraccountno" value="${enterpriseInformationQrcode.qraccountno}" class="input-small control-text" style="width:167px;height:18px;" type="text" data-rules="{required:true,maxlength:20}" data-tip="{text:'长度不超过20个字符'}"/>
                </div>
            </div>
            
            <div class="control-group">
              <label class="control-label" style="width:100px;text-align:right">二维码类型：</label>
              <div class="controls">
                <select name="qrtype" data-defualt="${enterpriseInformationQrcode.qrtype}" aria-disabled="false" style="width:192px;height:30px;" class="bui-form-field-select bui-form-field" data-rules="{required:true}">
                   <option value="" >--请选择--</option>
                   <option value="1" ${enterpriseInformationQrcode.qrtype==1?"selected='selected'":"" }>微信二维码</option>
                   <option value="2" ${enterpriseInformationQrcode.qrtype==2?"selected='selected'":"" }>支付宝二维码</option>
                </select>
              </div>
            </div>
            
            <div class="control-group">
              <label class="control-label" style="width:100px;text-align:right">状&nbsp;&nbsp;&nbsp;&nbsp;态：</label>
              <div class="controls">
                <select name="status" data-defualt="${enterpriseInformationQrcode.status}" aria-disabled="false" style="width:192px;height:30px;" class="bui-form-field-select bui-form-field" data-rules="{required:true}">
                   <option value="" >--请选择--</option>
                   <option value="0" ${enterpriseInformationQrcode.status==0?"selected='selected'":"" }>启用</option>
                   <option value="1" ${enterpriseInformationQrcode.status==1?"selected='selected'":"" }>禁用</option>
                </select>
              </div>
            </div>
            
            
            <div class="control-group">
                <label class="control-label" style="width:100px;text-align:right">二维码地址：</label>
                <div class="controls">
                    <input type="file" id="fileId" name="file"  style="width:144px;" dir="rtl"/>
                    <input type="button" value="上传" onclick="ajaxFileUpload('${ctx}/FileUpload','logo');" style="background: none repeat scroll 0 0 transparent;" />
                    <input type="hidden"  name="logopath" value="${enterpriseInformationQrcode.qrurl}" data-rules="{required:true}"/>
                    <span><img id="imgId" src="${enterpriseInformationQrcode.qrurl}" style="width: 80px;height: 80px;border: 1px solid #F6F6F6"></span>
                    <span style="color: red;">请上传614*700像素的图片（微信和支付宝）</span>
                </div>
            </div>
            <hr/>
            <div class="row actions-bar">       
	          <div class="form-actions span13 offset3">
	          	<input type="hidden" id="J_Page_Parent" value="${requestScope.MENUS.parentmenucode}" data-reload="true" >
	            <button type="button" id="J_Form_Submit" class="button button-primary" >保存</button>
	            <button class="button button-danger" type="button" onclick="top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">返回列表</button>
	          </div>
	      </div>
        </form> 
      </div>
    </div>  
    <script type="text/javascript" src="${statics }/js/ajaxfileupload-min.js"></script>
    <script type="text/javascript">
    $(function () {
    	
    	BUI.use('custom/commons');
    	BUI.use('bui/overlay');
    	BUI.use(['bui/mask']);
    	var form = new BUI.Form.HForm({
 		      srcNode : '#objForm',
 	    }).render(); 
   		bindClick("J_Form_Submit","${ctx}/enterpriseqcode/update",form);
    }); 
    </script>
<!-- script end -->
  </div>
  <%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>
</body>
</html>    