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
				<label class="control-label">品牌：</label>
				<div class="controls">
					<select style="width: 248px;" name="brandcode">
						<c:forEach items="${list}" var="brand">
							<option value="${brand.brandcode}">${brand.brandname}</option>
						</c:forEach>
					</select>
				</div>
			</div>
            <div class="control-group">
                <label class="control-label">图片类型：</label>
                <div class="controls">
                    <select style="width: 248px;" name="bannertype">
                            <option value="PC" selected="selected">ＰＣ端图片</option>
                            <option value="H5">移动端图片</option>
                    </select>
                </div>
            </div>
            
            <div class="control-group">
                <label class="control-label"><font color="red">*</font>上传图片：</label>
                <div class="controls">
                    <input type="file" id="fileId" name="file" style="width:144px;" dir="rtl" />
                    <input type="button" value="上传" onclick="ajaxFileUpload('${ctx}/FileUpload','agentBanner');" style="background: none repeat scroll 0 0 transparent;" />
                    <input type="hidden"  name="logopath" data-rules="{required:true}"/>
                    <span><img id="imgId" src="" style="width: 80px;height: 25px;border: 1px solid #F6F6F6;display: none;"></span>
                    <span style="color: red;">PC端，请上传1920*460像素的图片；移动端，请上传1920*460像素的图片</span>
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
    //loadWebviewTemplate('${enterprise.enterprisecode}');
    $(function () {
    	BUI.use('custom/commons');
    	BUI.use('bui/overlay');
    	BUI.use(['bui/mask']);
    	var form = new BUI.Form.HForm({
    	    srcNode : '#objForm',
 	    }).render(); 
   		bindClick("J_Form_Submit","${ctx}/agent/banner/save",form);
    }); 
    </script>
<!-- script end -->
  </div>
  <%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>
</body>
</html>    