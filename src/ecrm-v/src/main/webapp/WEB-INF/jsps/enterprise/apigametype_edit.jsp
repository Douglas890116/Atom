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
              <label class="control-label">游戏种类：</label>
              	<div class="controls">
                
                <select name="biggametype" value="${apiSoltGametype.biggametype}">
                    <option value="">--请选择--</option>
                    <c:forEach var="game" varStatus="status" items="${listGame}">
                    	<option value="${game.gametype }">${game.gamename }</option>
                    </c:forEach>
                </select>
                
                </div>
            </div>
            
            <input name="lsh"  type="hidden" value="${apiSoltGametype.lsh }"/>
            
            
            <div class="control-group">
              <label class="control-label" align="right"><font color="red">*</font>英文名称：</label>
                <div class="controls">
                  <input name="enname" class="input-small control-text" style="width:167px;height:20px;" type="text" data-rules="{required:true}" value="${apiSoltGametype.enname }" data-tip="{text:'长度不超过255个字符'}"/>
                </div>
            </div>
            <div class="control-group">
              <label class="control-label" align="right"><font color="red">*</font>简体名称：</label>
                <div class="controls">
                  <input name="cnname" class="input-small control-text" style="width:167px;height:20px;" type="text" data-rules="{required:true}" value="${apiSoltGametype.cnname }" data-tip="{text:'长度不超过255个字符'}"/>
                </div>
            </div>
            <div class="control-group">
              <label class="control-label"><font color="red">*</font>繁体名称：</label>
                <div class="controls">
                  <input name="trname" class="input-small control-text" style="width:167px;height:20px;" type="text" data-rules="{required:true}" value="${apiSoltGametype.trname }" data-tip="{text:'长度不超过255个字符'}"/>
                </div>
            </div>
            <div class="control-group">
              <label class="control-label">日文名称：</label>
                <div class="controls">
                  <input name="japname" class="input-small control-text" style="width:167px;height:20px;" type="text" value="${apiSoltGametype.japname }" data-tip="{text:'长度不超过255个字符'}"/>
                </div>
            </div>
            <div class="control-group">
              <label class="control-label">韩文名称：</label>
                <div class="controls">
                  <input name="krname" class="input-small control-text" style="width:167px;height:20px;" type="text" value="${apiSoltGametype.krname }" data-tip="{text:'长度不超过255个字符'}"/>
                </div>
            </div>
            
            <div class="control-group">
              <label class="control-label">业务分类：</label>
                <div class="controls">
                  <input name="stype" class="input-small control-text" style="width:167px;height:20px;" type="text"  value="${apiSoltGametype.stype }" data-tip="{text:'长度不超过255个字符'}"/>
                </div>
            </div>
            
            <div class="control-group">
              <label class="control-label">分类名称：</label>
                <div class="controls">
                  <input name="category" class="input-small control-text" style="width:167px;height:20px;" type="text"  value="${apiSoltGametype.category }" data-tip="{text:'长度不超过255个字符'}"/>
                </div>
            </div>
            <div class="control-group">
              <label class="control-label">分类名称2：</label>
                <div class="controls">
                  <input name="category2" class="input-small control-text" style="width:167px;height:20px;" type="text"  value="${apiSoltGametype.category2 }" data-tip="{text:'长度不超过255个字符'}"/>
                </div>
            </div>
            <div class="control-group">
              <label class="control-label">分类名称3：</label>
                <div class="controls">
                  <input name="category3" class="input-small control-text" style="width:167px;height:20px;" type="text"  value="${apiSoltGametype.category3 }" data-tip="{text:'长度不超过255个字符'}"/>
                </div>
            </div>
            
            <div class="control-group">
              <label class="control-label"><font color="red">*</font>游戏代码WEB：</label>
                <div class="controls">
                  <input name="gamecodeweb" class="input-small control-text" style="width:167px;height:20px;" type="text" data-rules="{required:true}" value="${apiSoltGametype.gamecodeweb }" data-tip="{text:'长度不超过255个字符'}"/>
                </div>
            </div>
            <div class="control-group">
              <label class="control-label"><font color="red">*</font>游戏代码H5：</label>
                <div class="controls">
                  <input name="gamecodeh5" class="input-small control-text" style="width:167px;height:20px;" type="text" data-rules="{required:true}" value="${apiSoltGametype.gamecodeh5 }" data-tip="{text:'长度不超过255个字符'}"/>
                </div>
            </div>
            <div class="control-group">
              <label class="control-label"><font color="red">*</font>游戏图片：</label>
                <div class="controls">
                  <input name="imagename" class="input-small control-text" style="width:167px;height:20px;" type="text" data-rules="{required:true}" value="${apiSoltGametype.imagename }" data-tip="{text:'长度不超过255个字符'}"/>
                </div>
            </div>
            
            <div class="control-group">
              <label class="control-label"><font color="red">*</font>是否支持WEB：</label>
                <div class="controls">
                  	<select name="isweb" value="${apiSoltGametype.isweb }" data-rules="{required:true}" >
	                    <option value="">--请选择--</option>
	                    <option value="0">支持</option>
	                    <option value="1">不支持</option>
                	</select>
                </div>
            </div>
            
            <div class="control-group">
              <label class="control-label"><font color="red">*</font>是否支持H5：</label>
                <div class="controls">
                  	<select name="ish5" value="${apiSoltGametype.ish5 }" data-rules="{required:true}" >
	                    <option value="">--请选择--</option>
	                    <option value="0">支持</option>
	                    <option value="1">不支持</option>
                	</select>
                </div>
            </div>
            
            <div class="control-group">
              <label class="control-label"><font color="red">*</font>备注：</label>
                <div class="controls">
                  <input name="remark" class="input-small control-text" style="width:167px;height:20px;" type="text" value="${apiSoltGametype.remark }" data-tip="{text:'长度不超过255个字符'}"/>
                </div>
            </div>
            
            <div class="control-group">
              <label class="control-label"><font color="red">*</font>排序：</label>
                <div class="controls">
                  <input name="ord" class="input-small control-text" style="width:167px;height:20px;" type="text" value="${apiSoltGametype.ord }" data-tip="{text:'长度不超过255个字符'}"/>
                </div>
            </div>
            
            <hr/>
            <div class="row actions-bar">       
	          <div class="form-actions span13 offset3">
	          	<input type="hidden" id="J_Page_Parent" value="${requestScope.MENUS.parentmenucode}" data-reload="true" >
	            <button type="button" id="J_Form_Submit" class="button button-primary" >保存</button>
	            <button class="button button-danger" type="button" onclick="javascript:top.topManager.closePage();">返回列表</button>
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
   		bindClick("J_Form_Submit","${ctx}/apigametype/save",form);
    }); 
    
    function bindClick(buttonId, url,form){
    	jQuery("#" + buttonId).click(function() {	
    		if(form.isValid()){
    			$.ajax({
    				type: "POST",
    				url: url,
    				data:$('#objForm').serialize(),
    				dataType: "json",
    				statusCode:{404:function(){
    					BUI.Message.Alert("请求未发送成功",'error');
    				}},
    			    success: function(data) {
    				    if(data.status == "1"){
    				    	//cancelMainPage();
    				    	
    				    	BUI.Message.Confirm('是否继续添加？',function() {
    							location.href = "${ctx}/apigametype/edit";
    						}, 'question');
    				    	
    				    }else{
    				    	BUI.Message.Alert(data.message,'warning');
    				    }
    			    }
    			});
    		}
    		
    	});
    }
    
    </script>
<!-- script end -->
  </div>
  <%-- <%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%> --%>
</body>
</html>    