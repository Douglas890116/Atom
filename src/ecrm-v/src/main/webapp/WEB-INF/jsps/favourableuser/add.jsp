<%@page import="com.maven.config.SystemConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="ctx" value="${pageContext['request'].contextPath}"></c:set>
<c:set var="statics" value="${ctx}/static"></c:set>
<script type="text/javascript" src="${statics }/js/jquery-1.8.1.min.js"></script>
<link rel="stylesheet" href="${statics}/css/bs3/bui-min.css"/>
<link rel="stylesheet" href="${statics}/css/bs3/dpl-min.css"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优惠组管理</title>
<link rel="stylesheet" type="text/css" href="${statics }/multiselect2side/jquery.multiselect2side.css" />
<style type="text/css">

</style>
<script type="text/javascript" src="${statics }/multiselect2side/jquery.multiselect2side.js"></script>
<script type="text/javascript">
$(function(){
   $("#searchable").multiselect2side({
	    selectedPosition: 'right',
	    moveOptions: false,
		labelsx: '可选列表：',
		labeldx: '已选列表：'
   });
});
</script>
</head>

<body>

  	<form id="searchForm" class="form-horizontal" style="outline: none;"
			method="post">
			<div class="row well" style="margin-left: 0px; position: relative;">
				<div style="position: relative; display: inline-block;">
					
					<div class="control-group span7">
		              <label class="control-label">优惠名称 ：</label>
		              <div class="controls">
		              	<select name="favourableid" id="favourableid" data-rules="{required:true}" >
		                    <option value="">--请选择--</option>
		                    <c:forEach var="item" items="${listFavourable }" varStatus="i" >
		                    	<option value="${item.lsh }">${item.favourablename }</option>
		                    </c:forEach>
		                </select>
		              </div>
		             </div>
		             
		             <div class="control-group span7">
		              <button id="btnSearch" onclick="doshow()" class="button button-primary">
						<span class="icon-search icon-white"></span>搜索</button>
		            </div>
		            
		            <div class="control-group span7">
		              <button type="button" onclick="dosave()" class="button  button-success botton-margin">保存设置</button>
		            </div>
	                
				</div>
			</div>
		</form>
		
		<h3>提示：用户可同时存在于多个优惠组</h3>		
      <div class="ms2side__div">
      
	      <div class="ms2side__select">
		      <select title="" name="searchable[]" id="searchable" size="8" multiple="multiple" >
			      <c:forEach var="item" items="${list }" varStatus="i" >
		        	<option value="${item.employeecode }" ${item.selected ? "selected":"" }>${item.loginaccount }</option>
		        </c:forEach>
		      </select>
	      </div>
		  
	   </div>
      
     	

<script src="${statics }/js/bui.js"></script>
<script src="${statics }/js/custom/commons-min.js "></script>  
<script >
$("#favourableid").val("${favourableid }");

function dosave(lsh){
	if($("#favourableid").val() == "") {
		BUI.Message.Alert("请选择需要加入的优惠组",'error');
		return ;
	}
	BUI.Message.Confirm('是否确定操作？',function() {
		$.ajax({
			type : "POST",
			url : "${ctx}/favourableuser/Save",
			data : {"favourableid" : $("#favourableid").val(), "searchable": $("#searchable").val()},
			dataType : "json",
			success : function(data) {
				if (data.status == 1) {
					BUI.Message.Alert(data.message,'success');
					store.load();
				} else {
					BUI.Message.Alert(data.message,'warning');
				}
			}
		});
	}, 'question');
}
function doshow(){
	location.href = "${ctx}/favourableuser/add?favourableid="+$("#favourableid").val();
}
</script>  

</body>
</html>

