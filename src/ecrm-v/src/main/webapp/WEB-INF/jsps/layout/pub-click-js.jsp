<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
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
				    	cancelMainPage();
				    }else{
				    	BUI.Message.Alert(data.message,'warning');
				    }
			    }
			});
		}
		
	});
}
function plusLessMinuteSubmit(buttonId, url,form){
		
	jQuery("#" + buttonId).click(function() {
		if(form.isValid()){
			
			BUI.Message.Confirm('是否确定操作吗？',function() {
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
					    	 BUI.Message.Alert(data.message,function() {top.topManager.reloadPage();},'success');
					    }else{
					    	BUI.Message.Alert(data.message,'warning');
					    }
				    }
				});
			}, 'question');
			
		}
		
	});
	
}
function bindAddClick(buttonId, url,form){
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
			    	debugger;
				    if(data.status == "1"){
				    	cancelMainPage();
				    }else{
				    	BUI.Message.Alert(data.message,'warning');
				    }
			    }
			});
		}
		
	});
}

function bindFormClick(buttonId,form){
	jQuery("#" + buttonId).click(function() {
		if(form.isValid()){
			document.forms[0].submit();
		}
	});
}
function bindSubmitClick(buttonId, url,form){
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
				    	BUI.Message.Alert(data.message,'info');
				    }else{
				    	BUI.Message.Alert(data.message,'warning');
				    }
			    }
			});
		}
		
	});
}

function cancelMainPage(){
	debugger;
	var mainPage = $("#J_Page_Parent").val();
	var reload = $("#J_Page_Parent").attr("data-reload");
	var menutitle = $("#J_Page_Parent").attr("data-title");
	var menuurl = $("#J_Page_Parent").attr("data-url");
	if(reload&&reload=="true"){
		top.topManager.reloadPage(mainPage);
	}
	top.topManager.openPage({id:mainPage,isClose:true,href:menuurl,title:menutitle});
}

</script>