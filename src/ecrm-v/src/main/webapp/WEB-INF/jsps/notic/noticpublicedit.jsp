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
	              <label class="control-label">发布品牌：</label>
	           		<div class="controls">
	           			<select name="brandcode" aria-pressed="false" aria-disabled="false"   class="bui-form-field-select bui-form-field" style="width:295px;">
							<option value="ALL">${sessionScope.ERCM_USER_SESSION.enterprisename }</option>
							<c:forEach items="${brands}" var="brand">
								<option value="${brand.brandcode }" ${brand.brandcode==notic.brandcode?"selected='selected'":"" }>${brand.enterprisename } - ${brand.brandname }</option>
							</c:forEach>
	              		</select>
	           		</div>
	            </div>
                <div class="control-group">
                  <label class="control-label">公告标题：</label>
                    <div class="controls">
                    	<input name="noticcode" value="${notic.noticcode }" type="hidden">
                        <input class="input-normal control-text" name="title" type="text" value="${notic.title }" style="width:270px;" data-rules="{required:true,maxlength:50}" data-tip="{text:'标题'}"/>
                    </div>
                </div>
                <div class="control-group" style="width: 800px;height: 320px;margin-top: 15px;">
                  <label class="control-label">公告内容：</label>
                  <div class="controls">
                      <textarea style="width: 600px;height: 300px;margin: 0;padding: 15px;font-size: 14px;line-height: 28px;box-sizing: border-box;-o-box-sizing: border-box;-ms-box-sizing: border-box; -moz-box-sizing: border-box;-webkit-box-sizing: border-box;" name="content">${notic.content}</textarea>
                  </div>
                </div>
               <div class="control-group">
	              <label class="control-label">有效时间：</label>
	              <div class="controls bui-form-group" data-rules="{dateRange : true}">
	                <input name="begintime"  type="text" data-tip='{"text":"起始时间"}' value="<fmt:formatDate value="${notic.begintime}" pattern="yyyy-MM-dd HH:mm:ss"/>" class="calendar calendar-time"/>
	                <span>&nbsp;-&nbsp;</span><input  name="endtime" type="text" data-tip='{"text":"结束时间"}' value="<fmt:formatDate value="${notic.endtime}" pattern="yyyy-MM-dd HH:mm:ss"/>"  class="calendar calendar-time"/>
	              </div>
	           </div>
	           <hr style="margin-top: 20px"/>
	              <div class="row actions-bar">
		              <div class="form-actions span5 offset3">
		                <input type="hidden" id="J_Page_Parent" value="${requestScope.MENUS.parentmenucode}" data-reload="true" >
			            <button type="button" id="J_Form_Submit" class="button button-primary"> 提 交 </button>
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
	bindAddClick("J_Form_Submit","${ctx}/BrandNotic/update",form);
});
</script>
<%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>