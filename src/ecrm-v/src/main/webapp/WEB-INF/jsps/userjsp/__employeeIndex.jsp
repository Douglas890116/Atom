<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.maven.entity.EnterpriseEmployeeType.Type" %>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body style="padding: 10px;">
    <div class="demo-content" >
<!-- 搜索页 ================================================== style=" margin:0 auto;  width:950px;"-->
<form id="searchForm" class="form-horizontal" style="outline: none;" method="post">
    <div class="row well" style="margin-left: 0px;position: relative;">
    <div style="position: relative;display: inline-block;">
      <div style="float: left;margin-right: 96px;">
      		<div class="control-group span7">
              <label class="control-label">登录账户：</label>
              <div class="controls">
                <input name="loginaccount"  type="text"  class="control-text"/>
              </div>
            </div>
            <div class="control-group span7">
	              <label class="control-label">在线状态：</label>
	              <div class="controls">
	                <select name="onlinestatus" aria-pressed="false" aria-disabled="false"    class="bui-form-field-select bui-form-field">
						<option value="">请选择</option>
	                    <option value="0">下线</option>
	                    <option value="1">在线</option>
	              	</select>
	              </div>
	         </div>
	         <div class="control-group span7 toggle-display">
	              <label class="control-label">员工状态：</label>
	              <div class="controls">
	                <select name="employeestatus" aria-pressed="false" aria-disabled="false"   class="bui-form-field-select bui-form-field">
						<option value="">请选择</option>
	                    <option value="1">启用</option>
	                    <option value="2">锁定游戏</option>
	                    <option value="3">禁用</option>
	              	</select>
	              </div>
	         </div>
             <div class="control-group span12 toggle-display">
                <label class="control-label">注册时间：</label>
                <div class="controls bui-form-group" data-rules="{dateRange : true}">
                  <input name="startDate"  type="text"  class="calendar calendar-time"/>
                  <span>&nbsp;-&nbsp;</span><input  name="endDate" type="text"   class="calendar calendar-time"/>
                </div>
            </div>
          </div>
<!--           toggle-display -->
          	 <div style="position: absolute;right: 15px;top: 3px;">
             	 <button id="btnSearch" type="submit"  class="button button-primary"> 搜 索 </button>
            </div>
        </div>
    </div> 
     </form>


<div class="search-grid-container well">
  <div id="grid"></div>
</div>
</div>
</body>
</html>
<script type="text/javascript">

var add_employee =        ${sessionScope.ERCM_USER_PSERSSION['MN0030'].menustatus==1};
var reset_loginpassword = ${sessionScope.ERCM_USER_PSERSSION['MN0031'].menustatus==1};
var reset_funpassword =   ${sessionScope.ERCM_USER_PSERSSION['MN0033'].menustatus==1};
var batch_delete =        ${sessionScope.ERCM_USER_PSERSSION['MN0032'].menustatus==1};
var permission_admin =    ${sessionScope.ERCM_USER_PSERSSION['MN0034'].menustatus==1};
var employeetype =  '${sessionScope.ERCM_USER_SESSION.employeetypecode}';
//权限验证
function permissionValidate(){
	var array= new Array();
	 if(add_employee){
		array.push({
	        btnCls : 'button button-primary',
	        text:"创建员工",
	        handler : function(){
	        	openNewWindow('create_employee','${ctx}/employeeOperating/userJsp/employeeAdd','创建员工');
	        }
	  	});
	}
	if(batch_delete){
		array.push({
	        btnCls : 'button button-danger bin',
	        text:'删除选中',
	        handler : function(){
  	        	deleteAllSelect("/employeeOperating/deleteSelectEmployee");
	     }});
	}
	return array;
}

var Grid = BUI.Grid,
  Store = BUI.Data.Store,
  columns = [
	{ title: '<input type="checkbox" name="selectAll" class="x-grid-checkbox" onclick="selectAll(this)"/><div style="position:absolute;top:8px;left:24px;">全选</div>',width: 60,sortable: false,
				renderer:function(value,obj){return '<input type="checkbox" name="selectName" value="'+obj.employeecode+'" class="x-grid-checkbox"/>'}},
				{ title: '账户 <b>/</b> 别名',   width: '20%',  sortable: false, renderer:function(value,obj){
			    	return  "<span style='color: #428BCA;font-size:16px;' title='账户' >"+obj.loginaccount.split("_")[1]+"</span> / <span class='auxiliary-text' title='别名'>"+obj.displayalias+"</span>";
			    	
			    }},
			    { title: '在线状态',   width: '10%',  sortable: false, renderer:function(value,obj){
			    	switch(obj.onlinestatus){
			    		case 0:
			    			return "下线";
			    		case 1:
			    			return "在线";
			    		default:
			    			return "无";
			    	}
			    }},
			    { title: '员工状态',   width: '10%',  sortable: false,renderer:function(value,obj){
			    	switch(obj.employeestatus){
					case 1:
						return "启用";
					case 2:
						return "锁定游戏";
					case 3:
						return "禁用";
					default:
						return "无";
					}
				}},
				{ title: '最后登录时间',   width: '12%',  sortable: false, dataIndex: 'lastlogintime',renderer:BUI.Grid.Format.datetimeRenderer},
			    { title: '注册日期',   width: '12%',  sortable: false,   dataIndex: 'logindatetime',renderer:BUI.Grid.Format.datetimeRenderer},
			    { title : '操作',		width: '21%',  sortable: false,  renderer : function(value,obj){
			    	var temp = '';
			    	if(reset_loginpassword && employeetype=='<%=Type.企业号.value%>'){
				    	temp += "<button class='button button-primary botton-margin'  onclick='resetLoginPassword(this)' value='"+obj.employeecode+"'>登录密码重置</button>";
			    	}
			    	if(reset_funpassword && employeetype=='<%=Type.企业号.value%>'){
				    	temp += "<button class='button button-primary botton-margin'  onclick='resetCapitalPassword(this)' value='"+obj.employeecode+"'>资金密码重置</button>";
			    	}
			    	if(permission_admin){
			    		temp += '<button class="button button-info botton-margin" onclick="permission(\'employeecode='+obj.employeecode+'&employeename='+obj.loginaccount.split("_")[1]+'\')"><span class="icon-globe icon-white"></span>权限管理</button>';
			    	}
			    	return temp;
			    }}
  ];

var store = new Store({
    url : '${ctx}/employeeOperating/findEmployee',
    autoLoad : false,
    pageSize:15
  }),
  grid = new Grid.Grid({
    render:'#grid',
    loadMask: true,
    autoRender:true,
    width:'100%',
    columns : columns,
    store: store,
    emptyDataTpl:'<div class="centered"><img alt="Crying" src="${statics }/img/dataisnull.png"/><font style="color:red;font-size:20px;"><h2>没有找到匹配的数据!</h2></font></div>',
    tbar:{
     items:permissionValidate()
    },
    bbar : {
      pagingBar:true
    }
  }),datepicker = new BUI.Calendar.DatePicker({
      trigger:'.calendar-time',
      showTime:true,
      autoRender:true
   });

$("#searchForm").submit(function(){
	  var obj = BUI.FormHelper.serializeToObject(this);
    obj.start = 0;
    store.load(obj);
	  return false;
}).submit();

</script>