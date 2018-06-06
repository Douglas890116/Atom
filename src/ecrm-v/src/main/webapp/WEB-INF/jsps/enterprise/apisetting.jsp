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
              	<div class="control-group">
	                <label class="control-label">接入API编码：</label>
	                <div class="controls">
	                  	<input class="input-normal control-text" name="platformcode"  value="${api.platformcode }" ${api!=null?"disabled='disabled'":"" }  type="text" data-rules="{required:true}" data-tip="{text:'API平台编码'}"  style="width:250px;height:18px;" />
	                </div>
              	</div>
              	 <div class="control-group">
              	 <label class="control-label">接入API地址：</label>
	                <div class="controls">
	                  	<input class="input-normal control-text" name="apiurl" value="${api.apiurl }"  ${api!=null?"disabled='disabled'":"" }  type="text" data-rules="{required:true}" data-tip="{text:'API访问地址'}"  style="width:250px;height:18px;" />
	                </div>
              	 </div>
              	<div class="control-group">
	                <label class="control-label">AES &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  KEY：</label>
	                <div class="controls">
	                	<input type="hidden" name="apicode" value="${api.apicode }">
	                	<input type="hidden" name="enterprisecode" value="${enterprisecode}">
	                  	<input class="input-normal control-text" name="apikey1" value="${api.apikey1 }" ${api!=null?"disabled='disabled'":"" }  type="text" data-rules="{required:true}" data-tip="{text:'AES KEY'}"  style="width:250px;height:18px;" />
	                </div>
	                
              	</div>
              	<div class="control-group">
              	<label class="control-label">MD5 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;KEY：</label>
	                <div class="controls">
	                  	<input class="input-normal control-text" name="apikey2" value="${api.apikey2 }" ${api!=null?"disabled='disabled'":"" }  type="text" data-rules="{required:true}" data-tip="{text:'MD5 KEY'}"  style="width:250px;height:18px;" />
	                </div>
              	
              	</div>
              	  
               <div class="control-group">
                <label class="control-label">是&nbsp;&nbsp;否&nbsp;&nbsp;启&nbsp;&nbsp;用：</label>
                <div class="controls">
                      <label class="radio" for="enable1"><input type="radio" id="enable1" name="outputapistatus" value="1" style="width:16px;"  checked="checked" ${api!=null?"disabled='disabled'":""}/>启用</label>&nbsp;&nbsp;&nbsp;
                      <label class="radio" for="enable2"><input type="radio" id="enable2" name="outputapistatus" value="0" style="width:16px;" ${api!=null?"disabled='disabled'":""} ${api.outputapistatus==0?"checked='checked'":""} />禁用</label>
                </div>
              </div>
               <div class="control-group">
               	<label class="control-label">API开放游戏：</label>
               	<div class="controls">
               	
             	 <c:forEach var="game" varStatus="status" items="${games}">
                  	<label class="control-label checkbox" style="color: #3071A9;font-weight: bold;width:120px;text-align: left;">
		              <input type="checkbox" name="enterprisegames"  value="${game.gamecode}"  ${game.ischoice?"checked='checked'":""}  ${api!=null?"disabled='disabled'":""}/> ${game.gamename}
		            </label>
		           <c:if test="${status.count%3==0 }"> <br> </c:if>
                 </c:forEach>
                 
                 </div>
               </div>
              	<hr/>
            <div class="row actions-bar">       
	          <div class="form-actions span13 offset2">
	          <c:choose>
	          	<%-- <c:when test="${api!=null && isadmin == true}"> --%>
	          	<c:when test="${api!=null}">
		          	<button type="button" id="J_Form_Edit" class="button button-success " > 启用编辑 </button>
		          	<button type="button" id="J_Form_Submit" class="button button-primary"  style="display: none;"> 保存 </button>
	          	</c:when>
	          	<c:otherwise>
	          		<button type="button" id="J_Form_Submit" class="button button-primary"> 保存 </button>
	          	</c:otherwise>
	          </c:choose>
	          	<input type="hidden" id="J_Page_Parent" value="${requestScope.MENUS.parentmenucode}" >
	            <button class="button button-danger" type="button" onclick="top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">返回列表</button>
	          </div>
	      </div>
        </form> 
      </div>
    </div>  
    <script type="text/javascript" src="${statics }/js/ajaxfileupload-min.js"></script>
    <%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>
    <script type="text/javascript">
    (function () {
    	BUI.use('custom/commons');
    	BUI.use('bui/overlay');
    	BUI.use(['bui/mask']);
    	var form = new BUI.Form.HForm({
 		      srcNode : '#objForm',
 	    }).render();
    	
    	$("#J_Form_Edit").click(function(){
    		$("input[type='text'],input[type='checkbox'],input[type='radio']").removeAttr("disabled");
    		$("#J_Form_Submit").show();
    		$(this).remove();
    	});
    	
    	bindClick("J_Form_Submit","${ctx}/InputAPI/Save",form);
    	
    })()
    </script>
    
<!-- script end -->
  </div>
</body>
</html>    