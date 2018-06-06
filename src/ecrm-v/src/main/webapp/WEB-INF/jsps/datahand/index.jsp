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
                <label class="control-label">任务平台：</label>
                  <input name="handlecode" type="text" data-tip='{"text":"任务平台"}' class="control-text"/>
              </div>
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


<!-- 初始隐藏 dialog内容 -->
    <div id="content" class="hide">
      <form class="form-horizontal">
        <div class="row">
          <div class="control-group span8">
            <label class="control-label">当前任务名称：</label>
            <div class="controls">
              <input name="handlecode" type="text" readly class="input-normal control-text" style="width:180px;">
            </div>
          </div>
          	
          <div class="control-group span8">
            <label class="control-label">当前任务时间：</label>
            <div class="controls">
              <input name="updatetime" type="text" data-rules="{required:true}" class="input-normal control-text" style="width:180px;">
            </div>
          </div>
          <div class="control-group span8">
            <label class="control-label">间隔时间分钟：</label>
            <div class="controls">
              <input name="intervalnum" type="text" data-rules="{required:true,number : true,min:5}" class="input-normal control-text" style="width:180px;">
            </div>
          </div>
          
          
        </div>
      </form>
    </div>
    

</body>
</html>
   <script type="text/javascript">
   //loadAllBank();
   
  
  var employeetype =  '${sessionScope.ERCM_USER_SESSION.employeetypecode}'; 
  
   //权限验证
     function permissionValidate(){
      var array= new Array();
      
      return array;
     }
        var Grid = BUI.Grid,
          Store = BUI.Data.Store,
          columns = [
			{title : '数据同步平台任务',width : '10%',sortable : true,dataIndex : 'handlecode'},                                                
			{title : '任务描述',width : '15%',sortable : true,dataIndex : 'handledesc',renderer:function(value,obj){
				if(obj.flag == 0 ) {
					return value;
				} else {
					return "<font color='red'>"+value+"</font>";
				}
			}},
			{title : '当前任务时间',width : '30%',sortable : true,dataIndex : 'updatetime', editor:{xtype:"number",rules:{required:true}}},   
			{title : '最后更新时间',width : '10%',sortable : true,dataIndex : 'lasttime',renderer:BUI.Grid.Format.datetimeRenderer},
			
			{title : '最后采集数量',width : '7%',sortable : false,dataIndex : 'lastnum'},
			{title : '当天采集数量',width : '7%',sortable : false,dataIndex : 'daynum'},
			{title : '总计采集数量',width : '7%',sortable : false,dataIndex : 'allnum'},
			{title : '间隔（分钟）',width : '7%',sortable : false,dataIndex : 'intervalnum', editor:{xtype:"number",rules:{required:true,min:5}}},
			{title : '备注',width : '13%',sortable : false,dataIndex : 'remark'},
            { title : '操作',width: '10%',sortable: false,renderer : function(value,obj){
            	 var temp = '';
            	 temp = temp+'<button type="button" class="button  button-success btn-edit" style="margin-right:9px;" onclick="updateEmployeeLevel()"><span class="icon-edit icon-white"></span>编辑  </button>';
				return temp;
            }}
          ];
		
        var isAddRemote = false;
        var editing = new Grid.Plugins.DialogEditing({
          contentId : 'content', //设置隐藏的Dialog内容
          triggerCls : 'btn-edit', //触发显示Dialog的样式
          editor : {
            title : '任务定时器',
            width : 600
          }
        });
        
        var store = new Store({
        	url : '${ctx}/datahand/indexdata',
            autoLoad : true,
            pageSize: 30
          }),grid = new Grid.Grid({
            render:'#grid',
            autoRender:true,
            loadMask: true,//加载数据时显示屏蔽层
            columns : columns,
            width:'100%',
            store: store,
            plugins : [editing,Grid.Plugins.CheckSelection],
            emptyDataTpl:'<div class="centered"><img alt="Crying" src="${statics }/img/dataisnull.png"/><font style="color:red;font-size:20px;"><h2>没有找到匹配的数据!</h2></font></div>',
            
            bbar : {
              pagingBar:true
            }
          }),datepicker = new BUI.Calendar.DatePicker({
              trigger:'.calendar-time',
              showTime:true,
              autoRender:true
           });
    
        grid.on('cellclick',function(ev){
	          var sender = $(ev.domTarget);
	          if(!sender.hasClass('x-grid-checkbox')){ //如果不是点中勾选列，不能进行选中操作
	            return false;
	          }
	        });
        
        editing.on('accept',function(ev){
            if(isAddRemote)	{
            	var data = editing.get('record');
            	$.post(getRootPath()+"/datahand/updatedata",{
            		handlecode: data.handlecode,
            		updatetime: data.updatetime,
            		intervalnum : data.intervalnum
            		},
    					function(data){
    						if(data.status=="failure"){
    							BUI.Message.Alert("任务保存失败："+data.message,"error");
    							return;
    						}
    			},'json')  
            }else{
            	//
            	alert("更新");
            }
        });
        
      //添加记录
        function addFunction(){
          isAddRemote = false;
          var newData = {};
          editing.add(newData,''); //添加记录后，直接编辑
        }
      //修改记录
       function updateEmployeeLevel(){
    	   isAddRemote = true;
       }
        //删除选中的记录
        function delFunction(){
        	//deleteMutilterm(grid,"${ctx}/employeeLevel/batchDelete","sign");
        }          
      
        $("#searchForm").submit(function(){
      	  var obj = BUI.FormHelper.serializeToObject(this);
            obj.start = 0;
            store.load(obj);
      	  return false;
        }).submit();
    </script>
    <style>
</style>