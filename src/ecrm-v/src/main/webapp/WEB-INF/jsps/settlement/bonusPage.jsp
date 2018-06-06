<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.maven.cache.SystemCache"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body>
  <div class="demo-content" style="margin: 27px;">
    <div class="row">
      <div>
            <form:form modelAttribute="enterpriseEmployee" method="post" id="objForm" class="form-horizontal" style="width:558px;">
                  <div style="margin-bottom: 15px;color: red;"> (注：分红、占成、洗码 设置之后只升不降)</div>
                  <div class="control-group">
                    <label class="control-label">分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;红:</label>
                      <div class="controls">
                        <input class="input-normal control-text" type="text" value="0" name="dividend" style="width:99px;height:18px;" data-rules="{required:true,number:true,min:0,max:${sessionScope.ERCM_USER_SESSION.dividend*100 }}"/> % 
                      </div>
                      <label class="control-label" style="margin-left: 50px">占&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;成:</label>
                      <div class="controls">
                        <input class="input-normal control-text" type="text" value="0" name="share" style="width:99px;height:18px;" data-rules="{required:true,number:true,min:0,max:${sessionScope.ERCM_USER_SESSION.share*100 }}"/> % 
                      </div>
                  </div>
                  <div class="control-group" >
                    <label class="control-label">洗&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码: </label>
                    <div style="clear: both;">
                      <c:forEach var="game" varStatus="status" items="${games}">
                        <div class="control-group" >
                          <label class="control-label" style="color:red;width: 50px;"><b>${game.gametype.replace("Game","") }:</b></label>
                          <div class="controls">
                            <c:forEach items="${SystemCache.getInstance().getGameCategory(game.gametype)}" var="category">
                            <c:set var="key" scope="page" value="${category.gametype}_${category.categorytype}"/>
                            <c:set var="maxbonus" scope="page" value="${bonus.get(key)==null?0:bonus.get(key)*100}"/>
                             <div style="float: left;">
                             ${category.categoryname}：<input  class="input-normal control-text" type="text" name="${key}"  value="0"  style="width:30px;height:18px;" data-rules="{required:true,number:true,min:${category.minbonus*100 },max:${maxbonus}}" /> %&nbsp;&nbsp;&nbsp;&nbsp;
                             </div>
                         </c:forEach>
                          </div>
                        </div>
                    </c:forEach>
                      <div style="clear: both;"></div>
                    </div>
                  </div>
                  <hr/>
                   <button type="button" id="J_Form_Submit" class="button button-primary" onclick="save()"> 提 交 </button>
        </form:form>
      </div>
    </div>  
  </div>
</body>
</html>
<script type="text/javascript">
var form;
$(function(){
  BUI.use(['bui/overlay','bui/mask']);
  form = new BUI.Form.HForm({
       srcNode : '#objForm',
  }).render();
});
function save(){
	if(form.isValid()){
		$.ajax({
			type: "POST",
			url: '${ctx}/employeeAgent/batchSaveDividendShareBonus',
			data:$("#objForm").serialize(),
			dataType: "json",
			statusCode:{404:function(){BUI.Message.Alert("请求未发送成功",'error');}},
		    success: function(data) {
			    debugger;
			    if(data.status == 1){
			    	BUI.Message.Alert(data.message,function(){
			    		top.topManager.reloadPage('MN0029')
			    	},'success');
			    }else{
			    	 BUI.Message.Alert(data.message,'error');
			    }
		    }
		});
	}
}
</script>
