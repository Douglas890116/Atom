<%@page import="com.maven.entity.EnterpriseEmployeeType.Type"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body style="padding: 10px;">
    <div class="demo-content" >
<!-- 搜索页 ================================================== -->
<form id="searchForm" class="form-horizontal" style="outline: none;" method="post">
<div class="row well" style="margin-left: 0px;position: relative;">
    <div style="position: relative;display: inline-block;">
      <div style="float: left;margin-right: 126px;">
          <div class="control-group span7">
              <label class="control-label">游戏类型：</label>
                <input name="gametype" type="text"  data-tip='{"text":"游戏类型"}' class="control-text"/>
            </div>
            <div class="control-group span7">
              <label class="control-label">游戏名称：</label>
                  <input name="gamename" type="text"  data-tip='{"text":"游戏名称"}' class="control-text"/>
            </div>
<!-- toggle-display -->
        </div>  
         <div style="position: absolute;right: 15px;top: 3px;">
           <button id="btnSearch" type="submit"  class="button button-primary"> <span class="icon-search icon-white"></span>搜 索 </button>
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
   loadAllBank();
   
  var add_gameType  =    ${sessionScope.ERCM_USER_PSERSSION['MN006R'].menustatus == 1 };
  var update_gameType  = ${sessionScope.ERCM_USER_PSERSSION['MN006V'].menustatus == 1 };
  var batch_delete = ${sessionScope.ERCM_USER_PSERSSION['MN006U'].menustatus == 1 };
  var one_delete = ${sessionScope.ERCM_USER_PSERSSION['MN006T'].menustatus == 1 };
  var employeetype =  '${sessionScope.ERCM_USER_SESSION.employeetypecode}'; 
   //权限验证
     function permissionValidate(){
      var array= new Array();
      if(add_gameType){
        array.push({
              btnCls : 'button button-primary',
              text:'<span class="icon-file icon-white"></span>添加类型',
              handler : function(){
                openNewWindow('create_gameType','${ctx}/gametype/add','添加游戏类型');
              }
          });
      }
      if(batch_delete){
    	  array.push({
  	        btnCls : 'button button-danger bin',
  	        text:'<span class=" icon-trash icon-white"></span>批量删除',
  	        handler : function(){
   	        	deleteMutilterm(grid,"${ctx}/gametype/deleteSelectGameType","gamecode");
  	     }});
      }
      return array;
     }
        var Grid = BUI.Grid,
          Store = BUI.Data.Store,
          columns = [
			{ title: '游戏类型',width: '33%',sortable: false,dataIndex: 'gametype'},
            { title: '游戏名称',width: '33%',sortable: false,dataIndex: 'gamename'},
            { title: '排序顺序',width: '33%',sortable: false,dataIndex: 'sort'},
            { title : '操作',width: 180,sortable: false,renderer : function(value,obj){
              var temp = '';
              	 if(update_gameType){
                  	temp+= '<button type="button" class="button  button-success botton-margin" onclick=editGameType("'+obj.gamecode+'")><span class="icon-edit icon-white"></span>编辑  </button>';
              	 }  
            	 if(one_delete){
                	  temp += '<button type="button" class="button button-danger" style="margin-right:9px;" onclick="deleteRow(this)" name="/gametype/deleteGameType" alt="'+obj.gamecode+'"><span class="icon-remove icon-white"></span>删除 </button>';
            	 }
            	  return temp;
            }}
          ];

        var store = new Store({
            url : '${ctx}/gametype/data',
            autoLoad : false, //自动加载数据
            pageSize:15
          }),grid = new Grid.Grid({
            render:'#grid',
            autoRender:true,
            loadMask: true,//加载数据时显示屏蔽层
            columns : columns,
            width:'100%',
            store: store,
            plugins : [ Grid.Plugins.CheckSelection ],
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