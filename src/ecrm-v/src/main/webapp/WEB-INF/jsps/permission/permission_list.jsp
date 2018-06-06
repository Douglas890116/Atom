<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>企业列表</title>
	<link href="${statics}/css/bs3/bui-min.css" rel="stylesheet" />
	<link href="${statics}/css/bs3/dpl-min.css" rel="stylesheet" /> 
	<link href="${statics}/css/custom/common.css" rel="stylesheet" />
</head>

<body style="padding: 10px;">
   <div class="demo-content">
      <div>
        <div class="panel panel-head-borded panel-small">
          <div class="panel-header"><button class="button button-primary" onclick="addFirst()"><span class="icon-file icon-white"></span>添加一级菜单</button></div>
          <div id="t1">
            
          </div>
        </div>
        
      </div>
    
 
	<script src="${statics }/js/jquery-1.8.1.min.js"></script>
    <script src="${statics }/js/bui.js"></script> 
    <script src="${statics }/js/config.js"></script>
    <script type="text/javascript" src="${statics }/js/custom/commons-min.js"></script>
 
<!-- script start --> 
    <script type="text/javascript">
      BUI.use(['bui/extensions/treegrid'],function (TreeGrid) {
    	  
      var data = ${menus};
      //由于这个树，不显示根节点，所以可以不指定根节点
      var tree = new TreeGrid({
        render : '#t1',
        nodes : data,
        width:'100%',
        columns : [
          {title : '菜单名称',dataIndex :'menuname',width:'20%'}, 
          {title : '菜单层级',dataIndex :'menulevel',width:'10%',renderer:function(value,obj){
        	  return value+" 级菜单";
          }}, 
          {title : '访问URL',dataIndex :'menuurl',width:'25%'},
          {title : '状态',dataIndex :'menustatus',width:'10%',renderer:function(value,obj){
        	  return value==1?"正常":"禁用";
          }},
          {title : '排序',dataIndex : 'menusort',width:'10%'},
          {title : '操作',width:'24%',renderer:function(value,obj){
        	  var opreate =  '<ul aria-pressed="false" aria-disabled="false" id="bar7" role="toolbar" class="bui-bar bui-grid-button-bar">'+
        	  '<li id="bar-item-button0" aria-disabled="false" class="bui-bar-item-button bui-bar-item bui-inline-block"><button type="button" class="button button-small button-success" sign='+obj.sign+' menucode='+obj.menucode+' onclick="edit(this)"><span class="icon-edit icon-white"></span> 编 辑 </button></li>'+
        	  '<li id="bar-item-button2" aria-disabled="false" class="bui-bar-item-button bui-bar-item bui-inline-block"><button type="button" class="button button-small button-primary" sign='+obj.sign+' menucode='+obj.menucode+' onclick="add(this)"><span class="icon-plus icon-white"></span>添加子菜单</button></li>'+
        	  '<li id="bar-item-button2" aria-disabled="false" class="bui-bar-item-button bui-bar-item bui-inline-block"><button type="button" class="button button-small button-danger" sign='+obj.sign+' menucode='+obj.menucode+' onclick="del(this)"><span class="icon-remove icon-white"></span> 删 除 </button></li>'+
        	  '</ul>';
        	   return opreate;
          }}
        ],
      });
      tree.render();
    });
      
    function addFirst(){
    	openNewWindow('add_first_menu','${ctx}/PermissionMenu/addFistMenu?&menucode=MN0000&menulevel=0','创建一级菜单');
    }
    function add(obj){
      	var sign = $(obj).attr("sign");
      	var menucode = $(obj).attr("menucode");
      	openNewWindow('add_second_menu','${ctx}/PermissionMenu/add?sign='+sign+'&menucode='+menucode,'创建子菜单');
    }
    function edit(obj){
      	var sign = $(obj).attr("sign");
      	var menucode = $(obj).attr("menucode");
      	openNewWindow('edit_first_menu','${ctx}/PermissionMenu/edit?sign='+sign+'&menucode='+menucode,'编辑菜单');
    }
    function del(obj){
    	BUI.Message.Confirm('确认要删除吗？',function(){
    		var sign = $(obj).attr("sign");
          	var menucode = $(obj).attr("menucode");
          	var tr = $(obj).parents("tr");
           	$.ajax({
   				type: "POST",
   				url: "${ctx}/PermissionMenu/delete",
   				data:{"sign":sign,"menucode":menucode},
   				dataType: "json",
   			    success: function(data) {
   			    	if(data.status==1){
	   			    	BUI.Message.Alert("删除成功",'success');
	   			    	tr.remove();
   			    	}else{
   			    		BUI.Message.Alert("删除失败",'warning');
   			    	}
   			   }});
         },'question');
    }
    </script>
<!-- script end -->
  </div>
</body>
</html>