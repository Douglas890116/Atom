<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<script type="text/javascript" src="${statics }/js/custom/commonJS.js"></script>
<script type="text/javascript" src="${statics }/js/custom/submitJS.js"></script>
<body>
  <div class="demo-content" style="margin: 27px;">
    <div class="row">
      <div>
      	<h2><a href="javascript:top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">${requestScope.MENUS.parentmenuname}</a><span class="divider"> / </span>${requestScope.MENUS.menuname }</h2>
        <%-- <h2>
        <c:if test="${whiteList != null }">编辑白名单</c:if>
        <c:if test="${whiteList == null }">添加白名单</c:if>
        </h2> --%>
        <hr>
          <form aria-pressed="false" aria-disabled="false" id="objForm" class="form-horizontal bui-form bui-form-field-container" method="post" >
          	<input type="hidden" name="lsh" id="lsh" value="${whiteList.lsh}"/>
              <div class="control-group">
                <label class="control-label" style="width:100px;">白名单IP地址：</label>
                <input class="input-normal control-text" name="ip" type="text" value="${whiteList.ip}" style="width:167px;height:18px;"  data-rules="{required:true,maxlength:18}" data-tip="{text:'长度不超过18个字符'}">
              </div>
              <div class="control-group">
                <label class="control-label" style="width:100px;">IP地址描述：</label>
                <input class="input-normal control-text" name="remark" type="text" value="${whiteList.remark}" style="width:167px;height:18px;" data-rules="{required:true}" data-tip="{text:'请描述该IP'}">
              </div>
              <hr>
               <div class="row actions-bar">
 					<div class="form-actions span5 offset3">
 					<input type="hidden" id="J_Page_Parent" value="${requestScope.MENUS.parentmenucode}" data-reload="true" >
                <button type="button" class="button button-primary" id="J_Form_Submit"> 保存 </button>
                <a href="javascript:top.topManager.closePage();"><button class="button" type="button">返回列表</button></a>
              </div>
 			  </div>
              
        </form>
      </div>
    </div>  
  </div>
</body>
</html>
<script type="text/javascript" src="${statics }/js/ajaxfileupload-min.js"></script>
    <script type="text/javascript">
    $(function () {
    	BUI.use('custom/commons');
    	BUI.use('bui/overlay');
    	BUI.use(['bui/mask']);
    	var form = new BUI.Form.HForm({
 		      srcNode : '#objForm',
 	    }).render(); 
    	bindClick("J_Form_Submit","${ctx}/LoginWhiteList/save",form);
    }); 
    
    
    </script>
  <%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>