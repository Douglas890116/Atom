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
        <form aria-pressed="false" aria-disabled="false" id="objForm" class="form-horizontal bui-form bui-form-field-container" method="post">
            <input type="hidden" name="contactcode" value="${brandContact.contactcode}">
            <input type="hidden" name="sign" value="${brandContact.sign}">
            <div class="control-group">
              <label class="control-label">品牌名称：</label>
                <div class="controls">
	                <select name="brandcode" style="width:192px;">
	                   <option>--请选择--</option>
	                </select>
                </div>
            </div>
            <div class="control-group">
              <label class="control-label">联系Title：</label>
                <div class="controls">
                  <input type="text" name="contacttitle" value="${brandContact.contacttitle}" placeholder="联系方式名称" data-rules="{required:true}" style="width: 170px;"/>
                </div>
            </div>
            <div class="control-group">
              <label class="control-label">联系方式：</label>
                <div class="controls">
                  <select name="contacttype" style="width:192px;height:30px;">
                      <option value="">--请选择--</option>
                      <option value="live800">Live800</option>
                      <option value="qq">QQ</option>
                      <option value="wechat">微信</option>
                      <option value="phone">电话</option>
                      <option value="email">邮箱</option>
                      <option value="skype">Skype</option>
                  </select>
                </div>
            </div>
            <div class="control-group">
              <label class="control-label">联系内容：</label>
                <div class="controls control-row4">
                    <textarea name="content" rows="3" cols="3" data-rules="{required:true}" data-tip="{text:'填写交谈的大概内容'}" style="width:167px;height:75px;">${brandContact.content}</textarea>
                </div>
            </div>
            <div class="control-group">
              <label class="control-label">内容类型：</label>
                <div class="controls">
                <select name="contenttype" data-rules="{required:true}" data-tip="{text:'内容类型'}" style="width:192px;height:30px;">
                	  <option value="" ></option>
                      <option value="value" ${brandContact.contenttype=="value"?"selected=selected":""}>值类型</option>
                      <option value="link"  ${brandContact.contenttype=="link"?"selected=selected":""}>超链接</option>
                      <option value="image" ${brandContact.contenttype=="image"?"selected=selected":""}>图片路径</option>
                  </select>
                </div>
            </div>
            <div class="control-group">
              <label class="control-label radio">是否启用：</label>
                <div class="controls">
                  <span style="display:block;padding:8px;">
                    <input name="enable" type="radio" value="1"/>启用
                    <input name="enable" type="radio" value="2" style="margin-left:25px;"/>禁用
                  </span>
                </div>
            </div>
            <div class="row actions-bar">       
	          <div class="form-actions span13 offset3">
	          	<input type="hidden" id="J_Page_Parent" value="${requestScope.MENUS.parentmenucode}" data-reload="true" >
	            <c:if test="${brandContact==null}">
                    <button type="button" id="J_Form_Submit" class="button button-primary" >保存</button>
                </c:if>
                <c:if test="${brandContact!=null}">
                    <button type="button" id="J_Form_update" class="button button-primary" >保存</button>
                </c:if>
	            <button class="button button-danger" type="button" onclick="top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">返回列表</button>
	          </div>
	      </div>
        </form> 
      </div>
    </div>  
    <script type="text/javascript">
    	$.ajax({
    		type:"post",
    		url:getRootPath()+"/common/loadEnterpriseBrand",
    		dataType:"json",
    		success:function(data){
    			for(var i=0;i<data.obj.length;i++){
    				if(data.obj[i].brandcode=='${brandContact.brandcode}'){
    					$("select[name='brandcode']").append("<option value="+data.obj[i].brandcode+" selected='selected'>"+data.obj[i].brandname+"</option>");
    				}else{
	    	    		$("select[name='brandcode']").append("<option value="+data.obj[i].brandcode+">"+data.obj[i].brandname+"</option>");
    				}
    	    	}
    		},
    	    error:function(){
    	    	BUI.Message.Alert('品牌数据加载失败','error');
    	    }
    	});
   $("select[name='contacttype'] option").each(function(){
	   if($(this).val()=='${brandContact.contacttype}'){
		   $(this).attr("selected","selected");
	   }
   });
   $("input[name=enable][value=${brandContact.enable}]").attr("checked",true);
    $(function () {
    	BUI.use('custom/commons');
    	BUI.use('bui/overlay');
    	BUI.use(['bui/mask']);
    	var form = new BUI.Form.HForm({
 		      srcNode : '#objForm',
 	    }).render(); 
   		bindClick("J_Form_Submit","${ctx}/brandContact/saveBrandContact",form);
   		bindClick("J_Form_update","${ctx}/brandContact/updateBrandContact",form);
    }); 
    </script>
  </div>
  <%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>
</body>
</html>    