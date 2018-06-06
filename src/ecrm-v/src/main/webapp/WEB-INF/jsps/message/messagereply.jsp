<%@page import="com.maven.cache.SystemCache"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body>
  <div class="demo-content" style="margin: 27px;">
    <div class="row">
      <div>
        <h2><a href="javascript:top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">${requestScope.MENUS.parentmenuname}</a><span class="divider"> / </span>${requestScope.MENUS.menuname }</h2>
        <hr>
            <form id="objForm" method="post" class="form-horizontal">
                <div class="control-group">
                  <label class="control-label" style="width: 50px;">收信人：</label>
                    <div class="controls">
                    	<input type="hidden" name="sign" value="${acceptemployeecode }">
                        <input class="input-normal control-text" name="title" type="text" value="${acceptaccount}" disabled="disabled" style="width:200px;" data-rules="{required:true,maxlength:15}" data-tip="{text:'收信人'}"/>
                    </div>
                </div>
                <div class="control-group" style="width: 600px;height: 220px;margin-top: 15px;">
                  <label class="control-label" style="width: 50px;">消息：</label>
                  <div class="controls">
                      <textarea style="width: 400px;height: 200px;margin: 0;" name="content" data-rules="{required:true,maxlength:255}" ></textarea>
                  </div>
                </div>
	              <div class="row actions-bar">
		              <div class="form-actions span5 offset3">
		              	<input type="hidden" id="J_Page_Parent" value="${requestScope.MENUS.parentmenucode}" data-reload="true" >
		                <button type="button" class="button button-primary" id="J_Form_Submit" > 发 送 </button>
		                <button class="button button-danger" type="button" onclick="top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">返回列表</button>
		              </div>
	              </div>
        </form>
      </div>
    </div>  
  </div>
</body>
</html>

<script type="text/javascript">
$(function(){
	BUI.use(['bui/overlay','bui/mask']);
	var form = new BUI.Form.HForm({
	     srcNode : '#objForm',
	}).render();
	bindClick("J_Form_Submit","${ctx}/Message/ReplyMessageSave",form);
});
</script>
<%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>