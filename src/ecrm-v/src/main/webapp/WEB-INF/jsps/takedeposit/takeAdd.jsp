<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body style="padding: 27px;">
  <div class="demo-content">
    <div class="row">
      <div>
        <h2><a href="javascript:top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">${requestScope.MENUS.parentmenuname}</a><span class="divider"> / </span>${requestScope.MENUS.menuname }</h2>
        <hr>
            <form  id="objForm" method="post" class="form-horizontal">
               <div class="control-group">
                  <label class="control-label">取款用户账号：</label>
                  <div class="controls">
                      <input class="input-normal control-text" name="loginaccount" type="text" style="width:167px;height:18px;"  data-tip="{text:'请输入用户账号'}"  data-rules="{required:true}" />
                      <input type="hidden" name="employeecode" data-rules="{required:true}"/>
                  </div>
              </div>
              <div class="control-group">
                <label class="control-label">取&nbsp;款&nbsp;银&nbsp;行&nbsp;卡：</label>
                <div class="controls">
                    <select name="employeepaymentaccount" id="informationcode" style="width:192px;height:30px;" data-rules="{required:true}" >
                        <option value="" >请选择</option>
                    </select>
                </div>
              </div>
              <div class="control-group">
                  <label class="control-label">取&nbsp;&nbsp;&nbsp;款&nbsp;&nbsp;&nbsp;金&nbsp;&nbsp;额：</label>
                  <div class="controls">
                      <input class="input-normal control-text" name="orderamount" type="text" style="width:167px;height:18px;" data-tip="{text:'请输入数字'}" data-rules="{required:true,number:true}" />
                  </div>
              </div>
              <div class="control-group">
                <label class="control-label">补&nbsp;&nbsp;&nbsp;单&nbsp;&nbsp;&nbsp;备&nbsp;&nbsp;注：</label>
                <div class="controls">
                  <textarea class="input-normal control-text" name="ordercomment" style="width:167px;height:63px;line-height: 18px;" data-tip="{text:'请输入备注信息'}" data-rules="{required:true}"></textarea>
                </div>
              </div>
              <hr>
              <div class="row actions-bar">     
              <div class="form-actions span5 offset2">
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
<script type="text/javascript">
	$(function(){

		var fullMask = new BUI.Mask.LoadMask({
	        el : 'html',
	        msg : '正在下分中...'
	      });
		
		var form = new BUI.Form.HForm({
		     srcNode : '#objForm',
		}).render();
		
		form.getField('loginaccount').set('remote',{
	         url : '${ctx}/common/validateEmployee',
	         dataType:'json',
	         callback : function(data){
	        	 if(data.status==1){
	        		 $("input[name='employeecode']").val(data.message);
		        		$.ajax({
		    				type: "POST",
		    				url: "${ctx}/common/getEmployeeBanks",
		    				data:{'employeecode':data.message},
		    				dataType: "json",
		    				statusCode:{404:function(){
		    					BUI.Message.Alert("请求未发送成功",'error');
		    				}},
		    			    success: function(data) {
		    				    if(data.message){
		    				    	BUI.Message.Alert(data.message,'warning');
		    				    }else{
		    				    	var banks = data.banks;
		    				    	var html = "<option value='' >请选择</option>";
		    				    	$.each(banks,function(i,bank){
		    				    		html += "<option value='"+bank.informationcode+"' >"+bank.accountname+" ("+bank.paymentaccount+")</option>";
		    				    	});
		    				    	$("#informationcode").html(html);
		    				    }
		    			    }
		    			});
		        		return;
	        	 }else{
	        		 $("#informationcode").html("<option value='' >请选择</option>");
	        		 return data.message;
	        	 }
	         }
	      });
		//bindClick("J_Form_Submit", "${ctx}/takeDepositRecord/SaveTakeOrders", form,fullMask);
		
		$("#J_Form_Submit").click(function() {
			if(form.isValid()){
				fullMask.show();
				$.ajax({
					type: "POST",
					url: "${ctx}/takeDepositRecord/SaveTakeOrders",
					data:$('#objForm').serialize(),
					dataType: "json",
					statusCode:{404:function(){
						BUI.Message.Alert("请求未发送成功",'error');
					}},
				    success: function(data) {
					    if(data.status == "1"){
				    		var mainPage = $("#J_Page_Parent").val();
				    		var reload = $("#J_Page_Parent").attr("data-reload");
				    		if (reload && reload == "true") {
				    			top.topManager.reloadPage(mainPage);
				    		}
				    		top.topManager.openPage({
				    			id : mainPage,
				    			isClose : true
				    		});
					    }else{
					    	BUI.Message.Alert(data.message,function(){
					    		fullMask.hide();
					    	},'warning');
					    }
				    }
				});
			}
			
		});
	});
	</script>
</html>