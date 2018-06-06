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
                  <label class="control-label" style="width: 50px;">类型：</label>
                  <div class="controls">
                    <select name="messagetype" data-rules="{required:true}">
                      <option value="1">系统消息</option>
                      <option value="2">代理消息</option>
                    </select>
                  </div>
                </div>
                <div class="control-group">
                  <label class="control-label" style="width: 50px;">收信人：</label>
                    <div class="controls">
                    	<div id="accept-user" style="background: white;width:400px;min-height: 30px;border: 1px solid #CCCCCC;border-radius:4px;-webkit-border-radius:4px;-moz-border-radius:4px;padding: 1px;max-height: 100px;overflow-y:auto; ">
                    		
                    	</div>
                    </div>
                    <div id="search-accepter"  style="position: relative;display: inline-block;margin-left: 5px;height: 30px;margin-top: 6px;cursor: pointer;" title="收信人">
                        <i class="icon-user icon-black" style="position: absolute;left: 0px;top:3px;"></i>
                        <i class="icon-user icon-black" style="position: absolute;left: 5px;top:3px;"></i>
                    </div>
                </div>
                <div class="control-group" style="width: 600px;height: 220px;margin-top: 15px;">
                  <label class="control-label" style="width: 50px;">消息：</label>
                  <div class="controls">
                      <textarea style="width: 410px;height: 200px;margin: 0;padding: 5px;font-size: 14px;line-height: 28px;box-sizing: border-box;-o-box-sizing: border-box;-ms-box-sizing: border-box; -moz-box-sizing: border-box;-webkit-box-sizing: border-box;" name="content"></textarea>
                  </div>
                </div>
              <div class="row actions-bar">
              <div class="form-actions span5 offset3">
                <input type="hidden" id="J_Page_Parent" value="${requestScope.MENUS.parentmenucode}" data-reload="true" >
           		<button type="button" id="J_Form_Submit" class="button button-primary"> 发 送 </button>
            		<button class="button button-danger" type="button" onclick="top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">取消发送</button>
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
	bindClick("J_Form_Submit","${ctx}/Message/SendMessage",form);
	
	BUI.use(['bui/overlay','bui/mask'],function(Overlay){
        var dialog = new Overlay.Dialog({
            title:'选择接收人',
            height:600,
            width:730,
            loader : {
              url : '${ctx}/Message/ManagerChoiceUser',
              autoLoad : true,
              lazyLoad : false, //不延迟加载
            },
            mask:true
          });
      //dialog.show();
      $('#search-accepter').on('click',function () {
        dialog.show();
      });
    });
});
</script>
<%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>