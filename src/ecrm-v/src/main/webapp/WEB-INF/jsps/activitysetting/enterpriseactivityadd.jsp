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
              <label class="control-label" style="margin-right: 24px;">企业：</label>
                <div class="controls">
                  <input value="${enterprisename }" class="input-small control-text" style="width:167px;height:18px;" type="text" data-rules="{required:true}" disabled="disabled"/>
                </div>
            </div>
            <div class="control-group">
              <label class="control-label" style="margin-right: 24px;">品牌：</label>
                <div class="controls">
                <select name="brandcode" aria-disabled="false"  class="bui-form-field-select bui-form-field" style="width: 193px;">
                  <option value="">--请选择--</option>
                  <c:forEach items="${brands }" var="m">
	                  <option value="${m.brandcode }">${m.brandname}</option>
                  </c:forEach>
                </select>
                (选填)
                </div>
            </div>
            <div class="control-group">
              <label class="control-label" style="margin-right: 24px;">团队：</label>
              <div class="controls">
                  <input name="loginaccount" class="input-small control-text" style="width:167px;height:18px;" type="text"data-rules="{required:false}"  data-tip="{text:'请输入团队所有者账号'}"/>
                  <input type="hidden" name="employeecode">
                  (选填，单线活动)
                </div>
            </div>
            <div class="control-group">
              <label class="control-label">活动模板：</label>
              <div class="controls">
              	<select name="sign" aria-disabled="false"  class="bui-form-field-select bui-form-field" style="width: 193px;">
                  <option value="">--请选择--</option>
                  <c:forEach items="${templates }" var="m">
                  	  <c:set var="title" value="<a>活动的名称</a>--${m.activityname }<br/><a>活动的描述</a>--${m.activitycontent }"></c:set>
	                  <option class="activitydesc-tips"  value="${m.sign }" data-title="${m.activitycontent }">${m.activityname}</option>
                  </c:forEach>
                </select>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label" >开始时间：</label>
              <div class="controls">
                  <input name="begintime" class="calendar calendar-time" style="width:167px;height:18px;" type="text" data-rules="{required:true}" data-tip="{text:'请输入活动开始时间'}"/>
                </div>
            </div>
            <div class="control-group">
              <label class="control-label" >结束时间：</label>
              <div class="controls">
                  <input name="endtime" class="calendar calendar-time" style="width:167px;height:18px;" type="text" data-rules="{required:true}" data-tip="{text:'请输入活动结束时间'}"/>
                </div>
            </div>
            <div class="control-group">
              <label class="control-label" style="margin-right: 24px;">状态：</label>
              <div class="controls">
              	<select name="status" aria-disabled="false"  class="bui-form-field-select bui-form-field" style="width: 193px;">
                  <option value="">--请选择--</option>
	                  <option value="1">启用</option>
	                  <option value="2">禁用</option>
                </select>
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
    $(function () {
    	BUI.use('custom/commons');
    	BUI.use('bui/overlay');
    	BUI.use(['bui/mask']);
    	BUI.use(['bui/tooltip']);
    	var form = new BUI.Form.HForm({
 		      srcNode : '#objForm',
 	    }).render(); 

		form.getField('loginaccount').set('remote',{
	         url : '${ctx}/common/employeeIsExist',
	         dataType:'json',
	         callback : function(data){
	        	 if(!$("input[name='loginaccount']").val()){
	        		 return '';
	        	 }else{
	        		 if(data.dont_exist){
		        		 $("input[name='employeecode']").val("");
		        		 return data.dont_exist;
		        	 }
		        	 if(data.exist){
		        		$("input[name='employeecode']").val(data.exist);
		        		return '';
		        	 }
	        	 }
	         }
	      });
	      var tips = new BUI.Tooltip.Tips({
	        tip : {
	          trigger : '.activitydesc-tips', //出现此样式的元素显示tip
	          alignType : 'right', //默认方向
	          elCls : 'tips tips-no-icon tip2',
	          offset : 10 //距离左边的距离
	        }
	      });
	      tips.render();
	      
   		bindClick("J_Form_Submit","${ctx}/Activity/Save",form);
    }); 
    </script>
<!-- script end -->
  </div>
  <%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>
</body>
</html>    
