<%@page import="com.maven.config.SystemConstant"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>${title }</title>
	<link rel="stylesheet" href="${statics}/css/bs3/bui-min.css" />
    <link rel="stylesheet" href="${statics}/css/bs3/dpl-min.css" />
    <link rel="stylesheet" href="${ctx }/kindeditor/themes/default/default.css"/>
	<link rel="stylesheet" href="${ctx }/kindeditor/plugins/code/prettify.css" />
	<style type="text/css">
		.ke-input-number {
		    width: 50px !important;
		 }
		.ke-input-text {
		    background-color: #FFFFFF;
		    font-family: "sans serif",tahoma,verdana,helvetica;
		    font-size: 12px;
		    line-height: 17px;
		    height: 17px;
		    padding: 2px 4px;
		    border-color: #848484 #E0E0E0 #E0E0E0 #848484;
		    border-style: solid;
		    border-width: 1px;
		    display: -moz-inline-stack;
		    display: inline-block;
		    vertical-align: middle;
		    zoom: 1;
		}
	</style>
</head>
<body>
<div class="demo-content" style="margin: 27px;">
    <!-- 表单页 ================================================== --> 
    <div class="row">
      <div>
        <h2><a href="javascript:top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">${requestScope.MENUS.parentmenuname}</a><span class="divider"> / </span>${requestScope.MENUS.menuname }</h2>
        <hr/>
        <form aria-pressed="false" aria-disabled="false"  name="example" id="objForm" action="${ctx}/ActivityTemplate/Save" class="form-horizontal bui-form bui-form-field-container" method="post" >
            <div class="control-group">
              <label class="control-label" style="margin-right: 12px;">定制企业：</label>
              <div class="controls">
              	<select name="enterprisecode" id="s_enterprise"  aria-disabled="false" class="bui-form-field-select bui-form-field" data-rules="{required:true}" style="width: 398px;">
              	  <option value="">--请选择--</option>
                  <c:forEach items="${enterprise }" var="ent">
	                  <option value="${ent.enterprisecode }">${ent.enterprisename }</option>
                  </c:forEach>
                </select>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label" style="margin-right: 12px;">活动名称：</label>
                <div class="controls">
                  <input name="activityname" class="input-small control-text" style="width:370px;height:18px;" type="text" data-rules="{required:true}" data-tip="{text:'请输入模板名称'}"/>
                </div>
                <label class="control-label" style="margin-right: 12px;margin-left: 120px;">活动模板：</label>
              <div class="controls">
              	<select name="activitytemplatecode" aria-disabled="false" class="bui-form-field-select bui-form-field" data-rules="{required:true}" style="width: 200px;">
                  <option value="">--请选择--</option>
                  <c:forEach items="${template }" var="tm">
	                  <option value="${tm.activitycode }">${tm.name }</option>
                  </c:forEach>
                </select>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">不共享活动：</label>
              <div class="controls">
              	<select name="unshare" id="unshareactivity" aria-disabled="false"  class="bui-form-field-select bui-form-field"  multiple="multiple" style="height: 150px;width: 400px;">
                  <option value="">--请选择--</option>
                </select>
				&nbsp;(按住Ctrl可进行多选)                
              </div>
            </div>
             <div class="control-group">
              <label class="control-label" style="margin-right: 12px;">活动PC端图片：</label>
                <div class="controls">
                	<input type="file" id="fileId" name="file" style="width:360px;" dir="rtl" data-rules="{required:true}"/>
                    <input type="button" value="上传" onclick="ajaxFileUpload('${ctx}/FileUpload','activity');" style="background: none repeat scroll 0 0 transparent;" />
                    <input type="hidden"  name="activityimage" data-rules="{required:true}"/>
                    <span><img id="imgId" src="" style="width: 220px;height: 45px;border: 1px solid #F6F6F6;display: none;margin-left: 150px;"></span>格式：812*144
                </div>
            </div>、
             <div class="control-group">
              <label class="control-label" style="margin-right: 12px;">活动移动端图片：</label>
                <div class="controls">
                    <input type="file" id="fileH5" name="file" style="width:360px;" dir="rtl" data-rules="{required:true}"/>
                    <input type="button" value="上传" onclick="ajaxFileUploadH5('${ctx}/FileUpload','activity');" style="background: none repeat scroll 0 0 transparent;" />
                    <input type="hidden"  name="activityimagehfive" data-rules="{required:true}"/>
                    <span><img id="h5iImgId" src="" style="width: 220px;height: 45px;border: 1px solid #F6F6F6;display: none;margin-left: 150px;"></span>格式：812*144
                </div>
            </div>
            <div class="control-group">
              <label class="control-label" style="margin-right: 12px;">PC活动描述（依据背景）：</label>
                <div class="controls">
                	<textarea name="activitycontent" style="width: 800px;height:400px; " class="bui-form-field"></textarea>
                </div>
            </div>
            
            <div class="control-group">
              <label class="control-label" style="margin-right: 12px;">H5活动描述（依据背景）：</label>
                <div class="controls">
                	<textarea name="activitycontenth5" style="width: 800px;height:400px; " class="bui-form-field"></textarea>
                </div>
            </div>
            
            <div class="control-group">
            <label class="control-label" style="margin-right: 12px;">排序：</label>
                <div class="controls">
                  <input name="ord" class="input-small control-text" style="width:370px;height:18px;" type="text" data-rules="{required:true}" data-tip="{text:'请输入排序'}" value="99"/>
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
    <script type="text/javascript" src="${statics }/js/jquery-1.8.1.min.js"></script>
    <script type="text/javascript" src="${statics }/js/bui.js"></script>
    <script type="text/javascript" src="${statics }/js/ajaxfileupload-min.js"></script>
    <script src="${ctx }/kindeditor/kindeditor-all.js" charset="utf-8"></script>
	<script src="${ctx }/kindeditor/lang/zh-CN.js" charset="utf-8"></script>
	<script src="${ctx }/kindeditor/plugins/code/prettify.js" charset="utf-8"></script>
	
    <script type="text/javascript">
    $(function () {
    	BUI.use('custom/commons');
    	BUI.use('bui/overlay');
    	BUI.use(['bui/mask']);
    	var form = new BUI.Form.HForm({
 		      srcNode : '#objForm',
 	    }).render(); 
   		bindClick("J_Form_Submit","${ctx}/EACustomization/Save",form);
   		
   		$("#s_enterprise").bind("change",function(){
   			var enterprisecode = $(this).val();
   			$.ajax({
				type: "POST",
				url: "${ctx}/EACustomization/Data",
				data:{"enterprisecode":enterprisecode},
				dataType: "json",
				statusCode:{404:function(){
					BUI.Message.Alert("请求未发送成功",'error');
				}},
			    success: function(data) {
		    		var html = "<option selected='selected' value=''>--请选择--</option>";
			    	if(data&&data.rows.length>0){
			    		$.each(data.rows,function(i,n){
			    			html += "<option value='"+n.ecactivitycode+"'>"+n.activityname+"</option>";
			    		});
			    	}
		    		$("#unshareactivity").html(html);
			    }
			});
   		});
    }); 
    function ajaxFileUpload(url,params) {
        $.ajaxFileUpload({
              url: url+"?type="+params, 
              secureuri: false, 
              fileElementId: 'fileId', 
              dataType: 'json', 
              success: function (data, status){ 
                  if(data && "success" == data.status){
                	  $("input[name='activityimage']").val(data.url);
                	  $("#imgId").attr("src",data.url).show();
                	  BUI.Message.Alert("图片上传成功",'success');
                  }else{
                	  BUI.Message.Alert("图片上传失败",'error');
                  }
              },
              error: function (data, status, e)//服务器响应失败处理函数
              {
                  BUI.Message.Alert(e,'error');
              }
          });
    }
    function ajaxFileUploadH5(url,params) {
        $.ajaxFileUpload({
              url: url+"?type="+params, //用于文件上传的服务器端请求地址
              secureuri: false, //是否需要安全协议，一般设置为false
              fileElementId: 'fileH5', //文件上传域的ID
              dataType: 'json', //返回值类型 一般设置为json
              success: function (data, status){//服务器成功响应处理函数 
                  if(data && "success" == data.status){
                      $("input[name='activityimagehfive']").val(data.url);
                      $("#h5iImgId").attr("src",data.url).show();
                      BUI.Message.Alert("图片上传成功",'success');
                  }else{
                      var msg = '<h2>'+data.status+'</h2>'+
                      '<p class="auxiliary-text">如连续上传失败，请及时联系客服</p>'+
                      '<p><a href="#">返回list页面</a> <a href="#">查看详情</a></p>';
                      BUI.Message.Alert(msg,'error');
                  }
              },
              error: function (data, status, e)//服务器响应失败处理函数
              {
                  BUI.Message.Alert(e,'error');
              }
          });
    }
    </script>
    <script>
		KindEditor.ready(function(K) {
			var editor1 = K.create('textarea[name="activitycontent"]', {
				cssPath : '${ctx }/kindeditor/plugins/code/prettify.css',
				uploadJson : '${ctx }/KEFileUpload',
				allowFileManager : false,
				afterBlur: function(){
					this.sync();
				},
				afterCreate : function() {
					var self = this;
					K.ctrl(document, 13, function() {
						self.sync();
						document.forms['example'].submit();
					});
					K.ctrl(self.edit.doc, 13, function() {
						self.sync();
						document.forms['example'].submit();
					});
				}
			});
			
			var editor2 = K.create('textarea[name="activitycontenth5"]', {
				cssPath : '${ctx }/kindeditor/plugins/code/prettify.css',
				uploadJson : '${ctx }/KEFileUpload',
				allowFileManager : false,
				afterBlur: function(){
					this.sync();
				},
				afterCreate : function() {
					var self = this;
					K.ctrl(document, 13, function() {
						self.sync();
						document.forms['example'].submit();
					});
					K.ctrl(self.edit.doc, 13, function() {
						self.sync();
						document.forms['example'].submit();
					});
				}
			});
			prettyPrint();
		});
	</script>
<!-- script end -->
  </div>
  <%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>
</body>
</html>    