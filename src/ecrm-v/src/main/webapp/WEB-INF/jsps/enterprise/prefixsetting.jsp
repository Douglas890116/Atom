<%@page import="com.maven.config.SystemConstant"%>
<%@page import="com.maven.entity.*"%>
<%@page import="java.util.*"%>
<%@page import="java.lang.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body>
<div class="demo-content" style="margin: 27px;">
    <!-- 表单页 ================================================== --> 
    <div class="row">
      <div>
        <h2><a href="javascript:top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">${requestScope.MENUS.parentmenuname}</a><span class="divider"> / </span>${requestScope.MENUS.menuname }</h2>
        <hr/>
        <input type="hidden" id="enterprisecode"  value="${enterprisecode }" />
        
        <table cellpadding="10" cellspacing="10" width="35%" border="0">
        <%
        	List<Game> games = (List)request.getAttribute("listGames");
        	Map mapPrefix = (Map)request.getAttribute("mapGamePlatformPrefix"); 
        	int count = 0;
        	for (Game _m : games) {
        		if(_m.isIschoice()) {
        			count ++;	
        			GamePlatformPrefix item = (GamePlatformPrefix)mapPrefix.get(_m.getGametype());
        %>
        <tr >
        <form aria-pressed="false" aria-disabled="false" id="objForm" class="form-horizontal bui-form bui-form-field-container" method="post" >
        	
        	<%
        		if(item != null) {
        	%>
        	<input type="hidden" id="lsh"  value="<%=item.getLsh()  %>" />
        	<input type="hidden" id="gamePlatform"  value="<%=item.getGamePlatform() %>" />
        	<%}else{ %>
        	<input type="hidden" id="gamePlatform"  value="<%=_m.getGametype()%>" />
        	<%} %>
        	
        <td align="right"><label class="control-label" ><%=_m.getGamename() %>：</label></td>
        <td><input class="input-normal control-text" id="prefixcode"  value='<%=item == null ? "" : item.getPrefixcode() %>'  type="text" data-rules="{required:true}" data-tip="{text:'未设置'}" placeholder="未设置"  style="width:250px;height:18px;" /></td>
        <td style="padding:4px">&nbsp;<button type="button" id="J_Form_Submit" onclick="dosave(this)" class="button button-primary"> 保存 </button></td>
        <td style="padding:4px">&nbsp;请务必区分大小写</td>
        
        </form>  
        </tr>  	
        <%}} %>
        
        <%
        	if(count == 0) {
        		
        %>
        该企业暂无授权的游戏API。
        <%} %>
        </table>
        
            <div class="row actions-bar">       
	          <div class="form-actions span13 offset2">
	          	
	          	<input type="hidden" id="J_Page_Parent" value="${requestScope.MENUS.parentmenucode}" >
	            <button class="button button-danger" type="button" onclick="top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">返回列表</button>
	          </div>
	      </div>
	      
        
      </div>
    </div>  
    <script type="text/javascript" src="${statics }/js/ajaxfileupload-min.js"></script>
    <%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>
    <script type="text/javascript">
    
    function dosave(obj) {
    	var enterprisecode = $("#enterprisecode").val();
		var prefixcode = $(obj).parent().parent().find("#prefixcode").val();
		var gamePlatform = $(obj).parent().parent().find("#gamePlatform").val();
		var lsh = 0;
		var stype = "add";
		if($(obj).parent().parent().find("#lsh").size() <= 0 ){
			//新增
		} else {
			//修改
			lsh = $(obj).parent().parent().find("#lsh").val();
			stype = "edit";
		}
		if(prefixcode == "") {
			BUI.Message.Alert('请输入前缀代码', 'info');
			$(obj).parent().parent().find("#prefixcode")[0].focus();
			return ;
		}
		
		$.ajax({
			type : "POST",
			url : "${ctx}/GamePlatformPrefix/Save",
			data : {
				"enterprisecode" : enterprisecode,
				"lsh" : lsh,
				"stype" : stype,
				"gamePlatform" : gamePlatform,
				"prefixcode" : prefixcode
			},
			dataType : "json",
			success : function(data) {
				if (data.status == 1) {
					BUI.Message.Alert(data.message,'success');
					//grid.removeItems(selectItem);
				} else {
					BUI.Message.Alert(data.message,'warning');
				}
			}
		});
		
    }
    
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