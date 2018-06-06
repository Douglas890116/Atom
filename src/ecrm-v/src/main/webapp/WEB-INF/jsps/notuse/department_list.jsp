<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<!DOCTYPE html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>企业列表</title>
	<link href="${statics}/css/bs3/bui-min.css" rel="stylesheet" />
	<link href="${statics}/css/bs3/dpl-min.css" rel="stylesheet" /> 
	<link href="${statics}/css/custom/common.css" rel="stylesheet" />
</head>

<body  style="padding: 5px 27px;">
    <div class="demo-content">
    <ul class="breadcrumb" style="border: none;margin: 0 0 5px;">
      <li><a href="#">Home</a> <span class="divider">>></span></li>
      <li><a href="#">Library</a> <span class="divider">>></span></li>
      <li class="active">Data</li>
    </ul>
<!-- 搜索页 ================================================== -->
  <form id="searchForm" class="form-horizontal" style="outline: none;" method="post">
    <div class="row well" style="margin-left: 0px;">
      <div style="position: relative;display: inline-block;">
      <div style="float: left;margin-right: 96px;">
          <div class="control-group span7">
              <label class="control-label">企业名称：</label>
              <div class="controls">
                <input name="enterprisename"  type="text" data-tip='{"text":"企业名称","iconCls":"icon icon-barcode"}' class="control-text"/>
              </div>
            </div>
            <div class="control-group span7">
              <label class="control-label">部门编码：</label>
              <div class="controls">
                <input name="departmentcode"  type="text" data-tip='{"text":"部门编码","iconCls":"icon icon-barcode"}' class="control-text"/>
              </div>
            </div>
            <div class="control-group span7">
              <label class="control-label">部门名称：</label>
              <div class="controls">
                <input name="department"  type="text" data-tip='{"text":"部门名称","iconCls":"icon icon-user"}' class="control-text"/>
              </div>
            </div>
          </div>
      	 <div style="position: absolute;right: 15px;top: 3px;">
                    <button id="btnSearch" type="submit" class="button button-primary">搜索</button>
        </div>
        </div>
        </div>
    </form>
    <div class="search-grid-container well">
        <div id="grid">
    </div>
      
<!-- 用户新增按钮是否有操作权限 -->
<input type="hidden" value="${sessionScope.ERCM_USER_PSERSSION['MN001W'].menustatus}" id="add_isoperate_Id" />
<!-- 用户修改 按钮是否有操作权限-->
<input type="hidden" value="${sessionScope.ERCM_USER_PSERSSION['MN001X'].menustatus}" id="editor_isoperate_Id" />
  
     <script src="${statics }/js/jquery-1.8.1.min.js"></script>
    <script src="${statics }/js/bui.js"></script> 
    <script type="text/javascript">
    //新增按钮有没有权限
    var add_isoperate_Id = $("#add_isoperate_Id").val();
    //编辑按钮有没有权限
    var editor_isoperate_Id = $("#delete_isoperate_Id").val();
  //权限验证
    function permissionValidate(){
    	var array= new Array();
    	if("1" == add_isoperate_Id){
    		array.push({
              btnCls : 'button button-primary',
              text:'创建部门',
              handler : function(){
              	 top.topManager.openPage({
        		    id : 'create_department',
        		    href : '${ctx}/Department/add',
        		    title : '创建部门'
	        	}); 
              }
          });
    	}
    	return array;
    }
    (function(){
    	var Grid = BUI.Grid,
        Store = BUI.Data.Store,
        columns = [
          { title: '企业名称', width:'20%', sortable: false, dataIndex: 'enterprisename'},
          { title: '部门编码',width:'20%', sortable: true, dataIndex: 'departmentcode',renderer:function(value,obj){
          	return value.split("_")[1];
          }},
          { title: '部门名称',width:'20%', sortable: true, dataIndex: 'department'},
          { title: '操作',width:'40%', dataIndex: 'g',renderer:function(value,obj){
            /* return '<ul aria-pressed="false" aria-disabled="false" id="bar7" role="toolbar" class="bui-bar bui-grid-button-bar">'+
            '<li id="bar-item-button1" aria-disabled="false" class="bui-bar-item-button bui-bar-item bui-inline-block"><button type="button" class="button button-small" onclick="edit(this)" sign="'+obj.departmentcode+'"><span class="icon-pencil"></span>编辑部门</button></li>'+
            '</ul>'; */
        	 var temp = '<ul aria-pressed="false" aria-disabled="false" id="bar7" role="toolbar" class="bui-bar bui-grid-button-bar">';
          	//编辑按钮判断有没有权限
          	if("1" == editor_isoperate_Id){
          		temp = '<li id="bar-item-button1" aria-disabled="false" class="bui-bar-item-button bui-bar-item bui-inline-block"><button type="button" class="button button-small" onclick="edit(this)" sign="'+obj.departmentcode+'"><span class="icon-pencil"></span>编辑部门</button></li>';	
          	}
          	temp = temp+'</ul>';
          	return temp;
          }}
        ];
    
      var store = new Store({
          url : '${ctx}/Department/data',
          pageSize:15
        }),
        grid = new Grid.Grid({
          render:'#grid',
          autoRender:true,
          width:'100%',
          loadMask: true,
          forceFit:true,
          fixedLayout:true,
          columns : columns,
          store: store,
          plugins : [Grid.Plugins.CheckSelection],
          tbar:{
           items:permissionValidate()
          },
          bbar : {
            pagingBar:true,
          }
        });

      //创建表单，表单中的日历，不需要单独初始化
 	    var form = new BUI.Form.HForm({
 		      srcNode : '#searchForm',
 	    }).render(); 
      form.on('beforesubmit',function(ev) {
        var obj = form.serializeToObject();
        obj.start = 0;
        store.load(obj);
        return false;
      }); 
      
    })()
        
    function edit(obj){
    	var sign = $(obj).attr("sign");
     	//window.location.href='${ctx}/Department/edit?departmentcode='+sign;
     	top.topManager.openPage({
		    id : 'editor_department',
		    href : '${ctx}/Department/edit?departmentcode='+sign,
		    title : '修改部门信息'
    	}); 
    }
    </script>
<!-- script end -->
  </div>
	
	</div>
</body>
</html>