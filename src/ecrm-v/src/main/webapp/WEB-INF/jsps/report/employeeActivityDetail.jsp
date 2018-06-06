<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.maven.entity.EnterpriseEmployeeType.Type" %>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body style="padding: 10px;">
    <div class="demo-content" >
    <ul class="breadcrumb" style="border: none;margin: 0 0 5px;">
      
    </ul>
<!-- 搜索页 ================================================== style=" margin:0 auto;  width:950px;"-->
<form id="searchForm" class="form-horizontal" style="outline: none;" method="post">
    <div class="row well" style="margin-left: 0px;position: relative;">
    <div style="position: relative;display: inline-block;">
      <div style="float: left;margin-right: 96px;">
      		<div class="control-group span7">
              <label class="control-label">登录账号：</label>
              <div class="controls">
                <input name="loginaccount"  type="text" data-tip='{"text":"登录账号","iconCls":"icon icon-user"}' class="control-text"/>
              </div>
            </div>
           <div class="control-group span7">
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
           <div class="control-group span12">
              <label class="control-label">注册时间：</label>
              <div class="controls bui-form-group" data-rules="{dateRange : true}">
                <input type="text" name="startDate" data-tip='{"text":"起始时间"}' class="calendar calendar-time"/>
                <span>&nbsp;-&nbsp;</span>
                <input  type="text" name="endDate" data-tip='{"text":"结束时间"}'  class="calendar calendar-time"/>
              </div>
            </div>
          </div>
          &nbsp;&nbsp;&nbsp;
<!--           toggle-display -->
      	 <div style="position: absolute;right: -80px;top: 3px;">
         	 <button id="btnSearch" type="submit" class="button button-primary"><span class="icon-search icon-white"></span> 搜 索 </button>
           	 <button class="button button-danger" type="button" onclick="top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">返回列表</button>
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
var batch_delete = ${sessionScope.ERCM_USER_PSERSSION['MN000Z'].menustatus==1};
var reset_loginpassword = ${sessionScope.ERCM_USER_PSERSSION['MN0011'].menustatus==1};
var reset_funpassword = ${sessionScope.ERCM_USER_PSERSSION['MN0039'].menustatus==1};
var setting_config = ${sessionScope.ERCM_USER_PSERSSION['MN003A'].menustatus==1};
var batch_add = ${sessionScope.ERCM_USER_PSERSSION['MN0067'].menustatus==1};
var employeetype =  '${sessionScope.ERCM_USER_SESSION.employeetypecode}';

$(function(){
	var Grid = BUI.Grid,
	  Store = BUI.Data.Store,
	  columns = [
		{ title: '用户账号',width: '18%',elCls:'center',sortable: false,renderer:function(value,obj){
	    	return  "<span style='color: #428BCA;font-size:16px;' title='账户' >"+obj.loginaccount+"</span>";
	    	
	    }},
	    { title: '用户名称',width: '10%', sortable: false, elCls:'center', dataIndex: 'displayalias'},
	    { title: '分红', width: '10%', sortable: false, elCls:'center',dataIndex: 'dividend',renderer:function(value,obj){
	    	return "<span style='color:#5cb85c;font-size: 16px;'>"+(value/1*100)+"%</span><button class='button button-primary botton-margin' style='display:none;'></button>";
	    }},
	    { title: '占成', width: '10%', sortable: false,elCls:'center', dataIndex: 'share',renderer:function(value,obj){
	    	return "<span style='color:#5cb85c;font-size: 16px;'>"+(value/1*100)+"%</span>";
	    }},
	    { title: '账户余额', width: '12%',sortable: false, elCls:'center',dataIndex: 'balance',renderer:function(value,obj){
	    	return "<div style='height:40px;'><span style='color:red;font-size:16px;position:relative;top:10px;'>"+value?0:value.toFixed(2)+"</span></div>";
	    }},
	    { title: '上级账号',width: '10%', sortable: false, elCls:'center', dataIndex: 'parentemployeeaccount'},
	    { title: '在线状态', width: '10%',elCls:'center', sortable: false,dataIndex: 'onlinestatus', renderer:function(value,obj){
	    	switch(value){
	    		case 0:
	    			return "下线";
	    		case 1:
	    			return "在线";
	    		default:
	    			return "无";
	    	}
	    }},
	    { title: '员工状态', width: '10%',dataIndex: 'employeestatus',elCls:'center', sortable: false,renderer:function(value,obj){
	    	switch(value){
			case 1:
				return "启用";
			case 2:
				return "锁定游戏";
			case 3:
				return "禁用";
			}
		}},
		{ title: '最后登录时间',width: 200,sortable: false,elCls:'center', dataIndex: 'lastlogintime',renderer:BUI.Grid.Format.datetimeRenderer},
	    { title: '注册日期',width: 200,sortable: false,elCls:'center', dataIndex: 'logindatetime',renderer:BUI.Grid.Format.datetimeRenderer}
	    
	  ];
	
	var store = new Store({
	    url : '${ctx}/report/queryEmployeeActivityDetail',
	    params:{loginDate:'${loginDate}'},
	    autoLoad : false,
	    pageSize:15
	  }),grid = new Grid.Grid({
	    render:'#grid',
	    autoRender:true,
	    loadMask: true,//加载数据时显示屏蔽层
	    width:'100%',
	    columns : columns,
	    store: store,
	    plugins : [Grid.Plugins.CheckSelection],
	    emptyDataTpl:'<div class="centered"><img alt="Crying" src="${statics }/img/dataisnull.png"/><font style="color:red;font-size:20px;"><h2>没有找到匹配的数据!</h2></font></div>',
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
})


</script>