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
              <label class="control-label"><font color="red">*</font>图片名称：</label>
                <div class="controls">
                  <input name="bannername" class="input-small control-text" style="width:167px;height:20px;" type="text" data-rules="{required:true,maxlength:50}" data-tip="{text:'长度不超过50个字符'}"/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">图片类型：</label>
                <div class="controls">
                    <select style="width: 248px;" name="bannertype">
                            <option value="PC" selected="selected">ＰＣ端图片</option>
                            <option value="H5">移动端图片</option>
                            <option value="new1">新版PC端图片</option>
                    </select>
                </div>
            </div>
            <div class="control-group">
              <label class="control-label"><font color="red">*</font>图片路径：</label>
                <div class="controls">
                  <input name="linkpath" class="input-small control-text" style="width:167px;height:20px;" type="text" data-rules="{required:true,maxlength:500}" data-tip="{text:'长度不超过500个字符'}"/>
                </div>
            </div>
            <div class="control-group">
              <label class="control-label"><font color="red">*</font>显示排序：</label>
                <div class="controls">
                  <input name="ord" class="input-small control-text" style="width:167px;height:20px;" type="text" data-rules="{required:true,maxlength:2}" data-tip="{text:'长度不超过2个字符'}"/>
                </div>
            </div>
            
            <div class="control-group">
                <label class="control-label"><font color="red">*</font>上传图片：</label>
                <div class="controls">
                    <input type="file" id="fileId" name="file" style="width:144px;" dir="rtl" />
                    <input type="button" value="上传" onclick="ajaxFileUpload('${ctx}/FileUpload','img');" style="background: none repeat scroll 0 0 transparent;" />
                    <input type="hidden"  name="logopath" data-rules="{required:true}"/>
                    <span><img id="imgId" src="" style="width: 80px;height: 25px;border: 1px solid #F6F6F6;display: none;"></span>
                    <span style="color: red;">PC端，请上传1920*427像素的图片；移动端，请上传414*180像素的图片</span>
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
   		bindClick("J_Form_Submit","${ctx}/banner/save",form);
    }); 
    </script>
<!-- script end -->
  </div>
  <%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>
</body>
</html>    