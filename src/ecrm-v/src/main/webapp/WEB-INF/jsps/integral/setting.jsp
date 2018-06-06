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
        
        <form aria-pressed="false" aria-disabled="false" id="objForm" class="form-horizontal bui-form bui-form-field-container" method="post">
        <table cellpadding="10" cellspacing="10" width="65%" border="0">
        
        <input type="hidden" id="enterprisecode"  value="${enterprisecode }" />
        	<c:forEach var="item" items="${list }" varStatus="i" >
        	<tr style="height:40px">
        		<input type="hidden" name="gametype"  value="${item.gametype }" />
        		<input type="hidden" name="gamebigtype"  value="${item.gamebigtype }" />
        		<c:if test="${item.lsh != null }">
        			<input type="hidden" name="lsh"  value="${item.lsh }" />
        		</c:if>
		        <td align="right"><label class="control-label" >${item.keyname }：</label></td>
		        
		        <td><input class="input-normal control-text" name="m2iRate"  value='${item.m2iRate }'  type="text" data-rules="{required:true,min:0,number:true}" data-tip="{text:'未设置'}"  />金额换积分的比例（获得积分时）</td>
		        <td><input class="input-normal control-text" name="i2mRate"  value='${item.i2mRate }'  type="text" data-rules="{required:true,min:0,number:true}" data-tip="{text:'未设置'}"   />积分换成现金的比例（使用积分时）</td>
		        <td><input class="input-normal control-text" name="timeoutday"  value='${item.timeoutday }'  type="text" data-rules="{required:true,min:1,number:true}" data-tip="{text:'未设置'}" />过期天数（单位：日）</td>
        	</tr>  	
        	</c:forEach>
        
        
        </table>
        
            <div class="row actions-bar">       
	          <div class="form-actions span13 offset2">
	          	<button type="button" id="J_Form_Submit" class="button button-primary" >保存</button>
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
    
    $(function () {
    	BUI.use('custom/commons');
    	BUI.use('bui/overlay');
    	BUI.use(['bui/mask']);
    	var form = new BUI.Form.HForm({
 		      srcNode : '#objForm',
 	    }).render(); 
    	bindSubmitClick("J_Form_Submit","${ctx}/IntegralSetting/save",form);
    }); 
    </script>
    
<!-- script end -->
  </div>
</body>
</html>    