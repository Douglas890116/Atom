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
              <label class="control-label">企业名称：</label>
              <div class="controls">
              	<select name="enterprisecode" aria-disabled="false" style="width:150px;height:30px;" class="bui-form-field-select bui-form-field" data-rules="{required:true}" disabled="disabled">
                  <option value="${enterprise.enterprisecode }">${enterprise.enterprisename}</option>
                </select>
                <input type="hidden" id="enterprisecode" name="enterprisecode" value="${enterprise.enterprisecode }" />
                <input type="hidden" id="sitetype" name="sitetype" value="2" />
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">前台模板：</label>
              <div class="controls">
                <div class="detail-section">
				  <div id="webviews">
				    <input type="hidden" id="hide" value="" name="hide">
				  </div>
				</div>
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
   	//loadWebviewTemplate();
    $(function () {
    	BUI.use('custom/commons');
    	BUI.use('bui/overlay');
    	BUI.use(['bui/mask']);
    	var form = new BUI.Form.HForm({
 		      srcNode : '#objForm',
 	    }).render(); 
   		bindClick("J_Form_Submit","${ctx}/brandTemplate/saveTemplateContact", form);
   		
   		
   		BUI.use('bui/select',function(Select){
   	    	$.post('${ctx}/brandTemplate/contactWebviews?enterprisecode=${enterprise.enterprisecode}&sign=${enterprise.sign}&sitetype=2', function(data){
   	    		debugger;
	   			var show_wvs = new Array();
	   			var allwvs = data.wtList;
   	    		for (var i=0;i<allwvs.length;i++){
   	    			show_wvs.push({text : allwvs[i].templatename, value : allwvs[i].webtemplatecode});
   	    		}
   	    		var exist_wvx = data.ewList;
   	    		var show_exist_wvs='';
   	    		for (var i=0;i<exist_wvx.length;i++){
   	    			show_exist_wvs += exist_wvx[i].webtemplatecode+",";
   	    		}
   	    		var items = show_wvs,
   	   		    select = new Select.Select({
   	   		          render:'#webviews',
   	   		          valueField:'#hide',
   	   		          multipleSelect:true,
   	   		          items:items
   	   		        });
	   		    select.render();
   	    		select.setSelectedValue(show_exist_wvs);
   	    	});
   		    /* $('#J_BtnShow').on('click', function(){
   		      alert('text：' + select.getSelectedText() + '\n' + 'value：' + select.getSelectedValue());
   		    }); */
   		  
		  });
    }); 
    </script>
<!-- script end -->
  </div>
  <%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>
</body>
</html>    