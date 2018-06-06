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
        
        
        <table cellpadding="10" cellspacing="10" width="80%" border="0">
        
        
        <c:if test="${keydata != null }">
        	<c:forEach var="item" items="${keydata }"> 
		        <tr >
		        <form aria-pressed="false" aria-disabled="false" id="objForm" class="form-horizontal bui-form bui-form-field-container" method="post" >
		        <td align="right">
		        	<input class="input-normal control-text" id="enterprisecode"  value='${enterprisecode }'  type="text" readonly style="width:250px;height:18px;" />
		        </td>
		        
		        <td align="right">
		        	<input class="input-normal control-text" id="gamecode"  value='${gametype }'  type="text" readonly style="width:250px;height:18px;" />
		        </td>
		        
		        <td><label class="control-label" >参数名称：</label><input class="input-normal control-text" id="key"  value='${item.key }'  type="text" data-rules="{required:true}"  style="width:250px;height:18px;" /></td>
		        <td><label class="control-label" >参数值：</label><input class="input-normal control-text" id="val"  value='${item.value }'  type="text" data-rules="{required:true}"  style="width:250px;height:18px;" /></td>
		        <td style="padding:4px">&nbsp;<button type="button" id="J_Form_Submit" onclick="doupdate(this)" class="button button-primary"> 修改 </button></td>
		        <td style="padding:4px">&nbsp;请务必区分大小写</td>
		        
		        </form>  
		        </tr>  	
	        </c:forEach>
	        
        </c:if>
        
        
        
        <tr >
        <form aria-pressed="false" aria-disabled="false" id="objForm" class="form-horizontal bui-form bui-form-field-container" method="post" >
        	
        <td align="right"><label class="control-label" >
        		
                <c:if test="${enterprisecode != null }">
                	<input class="input-normal control-text" id="enterprisecode"  value='${enterprisecode }'  type="text" readonly style="width:250px;height:18px;" />
                </c:if>
                <c:if test="${enterprisecode == null }">
                	<select name="enterprisecode" id="enterprisecode">
	                    <option value="">--企业名称--</option>
	                    <c:forEach var="game" varStatus="status" items="${listEnterprise}">
	                    	<option value="${game.enterprisecode }">${game.enterprisename }</option>
	                    </c:forEach>
	                </select>
                </c:if>
        </label></td>
        
        <td align="right"><label class="control-label" >
        		<c:if test="${gametype != null }">
        			<input class="input-normal control-text" id="gamecode"  value='${gametype }'  type="text" readonly style="width:250px;height:18px;" />
        		</c:if>
        		<c:if test="${gametype == null }">
        			<select name="gamecode" id="gamecode">
	                    <option value="">--游戏类型--</option>
	                    <c:forEach var="game" varStatus="status" items="${listGame}">
	                    	<option value="${game.gamecode }">${game.gamename }</option>
	                    </c:forEach>
	                </select>
        		</c:if>
        		
        </label></td>
        
        <td><label class="control-label" >参数名称：</label><input class="input-normal control-text" id="key"  value=''  type="text" data-rules="{required:true}"  style="width:250px;height:18px;" /></td>
        <td><label class="control-label" >参数值：</label><input class="input-normal control-text" id="val"  value=''  type="text" data-rules="{required:true}"  style="width:250px;height:18px;" /></td>
        <td style="padding:4px">&nbsp;<button type="button" id="J_Form_Submit" onclick="dosave(this)" class="button button-primary"> 新增 </button></td>
        <td style="padding:4px">&nbsp;请务必区分大小写</td>
        
        </form>  
        </tr>  	
        
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
		var gametype = $("#gamecode").val();
		var key = $(obj).parent().parent().find("#key").val();
		var val = $(obj).parent().parent().find("#val").val();
		var stype = "add";
		
		if(enterprisecode == "") {
			$("#enterprisecode").focus();
			return ;
		}
		if(gametype == "") {
			$("#gamecode").focus();
			return ;
		}
		
		if(key == "") {
			//BUI.Message.Alert('请输入参数名称', 'info');
			$(obj).parent().parent().find("#key")[0].focus();
			return ;
		}
		if(val == "") {
			//BUI.Message.Alert('请输入参数值', 'info');
			$(obj).parent().parent().find("#val")[0].focus();
			return ;
		}
		
		$.ajax({
			type : "POST",
			url : "${ctx}/apigame/save",
			data : {
				"enterprisecode" : enterprisecode,
				"gametype" : gametype,
				"stype" : stype,
				"key" : key,
				"val" : val
			},
			dataType : "json",
			success : function(data) {
				if (data.status == 1) {
					//BUI.Message.Alert(data.message,'success');
					//grid.removeItems(selectItem);
					
					//刷新
					var url = "${ctx}/apigame/edit?enterprisecode=${enterprisecode}&gametype=${gametype}";
					location.href = url;
				} else {
					BUI.Message.Alert(data.message,'warning');
				}
			}
		});
		
    }
    
    function doupdate(obj) {
    	
		var enterprisecode = $(obj).parent().parent().find("#enterprisecode").val();
		var gametype = $(obj).parent().parent().find("#gamecode").val();
		var key = $(obj).parent().parent().find("#key").val();
		var val = $(obj).parent().parent().find("#val").val();
		var stype = "update";
		
		if(key == "") {
			//BUI.Message.Alert('请输入参数名称', 'info');
			$(obj).parent().parent().find("#key")[0].focus();
			return ;
		}
		if(val == "") {
			//BUI.Message.Alert('请输入参数值', 'info');
			$(obj).parent().parent().find("#val")[0].focus();
			return ;
		}
		
		
		$.ajax({
			type : "POST",
			url : "${ctx}/apigame/save",
			data : {
				"enterprisecode" : enterprisecode,
				"gametype" : gametype,
				"stype" : stype,
				"key" : key,
				"val" : val
			},
			dataType : "json",
			success : function(data) {
				if (data.status == 1) {
					//BUI.Message.Alert(data.message,'success');
					//grid.removeItems(selectItem);
					//刷新
					var url = "${ctx}/apigame/edit?enterprisecode=${enterprisecode}&gametype=${gametype}";
					location.href = url;
				} else {
					BUI.Message.Alert(data.message,'warning');
				}
			}
		});
		
    }
    
    /**
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
    ***/
    </script>
    
<!-- script end -->
  </div>
</body>
</html>    