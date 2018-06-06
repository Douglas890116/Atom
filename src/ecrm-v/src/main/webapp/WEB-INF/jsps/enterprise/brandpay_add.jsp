<%@page import="com.maven.config.SystemConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body style="padding: 27px;">
<div class="demo-content" >
    <!-- 表单页 ================================================== --> 
    <div class="row">
      <div>
        <h2><a href="javascript:top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">${requestScope.MENUS.parentmenuname}</a><span class="divider"> / </span>${requestScope.MENUS.menuname }</h2>
        <hr/>
        <form aria-pressed="false" aria-disabled="false" id="objForm" class="form-horizontal bui-form bui-form-field-container" method="post" >
            <div class="control-group">
              <label class="control-label">企业名称：</label>
              <div class="controls">
              	${enterprise.enterprisename}
              </div>
            </div>
            
            <input name="sign"  type="hidden" value="${sign }"/>
            
            <div class="control-group">
              <label class="control-label"><font color="red">*</font>品牌名称：</label>
              <div class="controls">
                <select name="brandcode" aria-disabled="false" style="width:192px;height:30px;" class="bui-form-field-select bui-form-field" >
                  <option value="">--请选择--</option>
                  	<c:forEach var="item" items="${listEnterpriseOperatingBrand }" varStatus="i">
                  		<option value="${item.brandcode }">${item.brandname }</option>
                  	</c:forEach>
                </select>
              </div>
            </div>
            
            <div class="control-group">
              <label class="control-label"><font color="red">*</font>支付回调域名：</label>
                <div class="controls">
                  <input name="paycallbackurl" class="input-small control-text" style="width:167px;height:20px;" type="text" data-rules="{required:true,url:true}" data-tip="{text:'长度不超过255个字符'}"/>
                  http或hppts开头的顶级、二级、三级等域名
                </div>
            </div>
            <div class="control-group">
              <label class="control-label"><font color="red">*</font>后台管理域名：</label>
                <div class="controls">
                  <input name="adminurl" class="input-small control-text" style="width:167px;height:20px;" type="text" data-rules="{required:true,url:true}" data-tip="{text:'长度不超过255个字符'}"/>
                  http或hppts开头的顶级、二级、三级等域名
                </div>
            </div>
            
            
            
            <hr/>
            <div class="row actions-bar">       
	          <div class="form-actions span13 offset3">
	          	<input type="hidden" id="J_Page_Parent" value="${requestScope.MENUS.parentmenucode}" data-reload="true" >
	            <button type="button" id="J_Form_Submit" class="button button-primary" >保存</button>
	            <button class="button button-danger" type="button" onclick="javascript:top.topManager.closePage();">返回列表</button>
	          </div>
	      </div>
        </form> 
      </div>
    </div>  
    <script type="text/javascript" src="${statics }/js/ajaxfileupload-min.js"></script>
    <script type="text/javascript">
    //loadWebviewTemplate('${enterprise.enterprisecode}');
    $(function () {
    	BUI.use('custom/commons');
    	BUI.use('bui/overlay');
    	BUI.use(['bui/mask']);
    	var form = new BUI.Form.HForm({
 		      srcNode : '#objForm',
 	    }).render(); 
   		bindClick("J_Form_Submit","${ctx}/EnterpriseOperatingBrandPay/save",form);
    }); 
    </script>
<!-- script end -->
  </div>
  <%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>
</body>
</html>    