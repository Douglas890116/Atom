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
                <label class="control-label">卡号前缀：</label>
                  <input name="bankprefix" type="text" data-tip='{"text":"卡号前缀"}' class="control-text"/>
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
            <label class="control-label">卡号前缀:</label>
            <div class="controls">
              <input name="bankprefix" type="text" data-rules="{required:true,number : true}" class="input-normal control-text">
            </div>
          </div>
          
          <div class="control-group span8">
            <label class="control-label">银行名称:</label>
            <div class="controls">
              <input name="bankname" type="text" data-rules="{required:true}" class="input-normal control-text">
            </div>
          </div>
          
          <div class="control-group span8">
            <label class="control-label">银行编码:</label>
            <div class="controls">
              <input name="bankcode" type="text" data-rules="{required:true,minlength:1,maxlength:4}" class="input-normal control-text">
            </div>
          </div>
          <div class="control-group span8">
            <label class="control-label">卡号长度:</label>
            <div class="controls">
              <input name="banklen" type="text" data-rules="{required:true}" class="input-normal control-text">
            </div>
          </div>
          <div class="control-group span8">
            <label class="control-label">描述 :</label>
            <div class="controls">
              <input name="remark" type="text" data-rules="{required:true}" class="input-normal control-text">
            </div>
          </div>
          
        </div>
      </form>
    </div>
</body>
</html>
   <script type="text/javascript">
   
  
  var employeetype =  '${sessionScope.ERCM_USER_SESSION.employeetypecode}'; 
   //权限验证
     function permissionValidate(){
      var array= new Array();
        array.push({
              btnCls : 'button button-primary',
              text:'<span class="icon-file icon-white"></span>添加',
              listeners : {
                  'click' : addFunction
              }
          });
      return array;
     }
        var Grid = BUI.Grid,
          Store = BUI.Data.Store,
          columns = [
			{ title: '编号',width: '6%',sortable: false,dataIndex: 'lsh',editor:{xtype:"text",disabled:true}},
      		{ title: '银行前缀',width: '6%',sortable: false,dataIndex: 'bankprefix'},
            { title: '银行编码',width: '6%',sortable: false,dataIndex: 'bankcode',editor:{xtype:"text",rules:{required:true,minlength:1,maxlength:4}}},
            { title: '银行名称',width: '20%',sortable: false,dataIndex: 'bankname',editor:{xtype:"text",rules:{required:true}}},
            { title: '卡号长度',width: '6%',sortable: false,dataIndex: 'banklen'},
            {title : '描述',width : '10%',sortable : true, dataIndex : 'remark',editor:{xtype:"text"}}
            
          ];
		
        var isAddRemote = false;
        var editing = new Grid.Plugins.DialogEditing({
          contentId : 'content', //设置隐藏的Dialog内容
          triggerCls : 'btn-edit', //触发显示Dialog的样式
          editor : {
            title : '用户等级',
            width : 600
          }
        });
        
        var store = new Store({
            url : '${ctx}/cardbin/list',
            autoLoad : true,
            pageSize:15
          }),grid = new Grid.Grid({
            render:'#grid',
            autoRender:true,
            loadMask: true,//加载数据时显示屏蔽层
            columns : columns,
            width:'100%',
            store: store,
            plugins : [editing,Grid.Plugins.CheckSelection],
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
    
        grid.on('cellclick',function(ev){
	          var sender = $(ev.domTarget);
	          if(!sender.hasClass('x-grid-checkbox')){ //如果不是点中勾选列，不能进行选中操作
	            return false;
	          }
	        });
        
        editing.on('accept',function(ev){
            if(isAddRemote)	{
            	  var data = editing.get('record');
                	$.post(getRootPath()+"/cardbin/update",
        					{
                		bankprefix: data.bankprefix,
                  		bankcode: data.bankcode,
                  		bankname: data.bankname,
                  		banklen: data.banklen ,
                  		remark : data.remark
                  		},
        					function(data){
        						if(data.status=="failure"){
        							BUI.Message.Alert("用户级别保存失败!"+data.message,"error");
        							return;
        						}
        			},'json')  
            }else{
            	var data = editing.get('record');
              	$.post(getRootPath()+"/cardbin/save",
      					{
              		bankprefix: data.bankprefix,
              		bankcode: data.bankcode,
              		bankname: data.bankname,
              		banklen: data.banklen ,
              		remark : data.remark
              		},
      					function(data){
      						if(data.status=="failure"){
      							BUI.Message.Alert("数据保存失败!"+data.message,"error");
      							return;
      						}
      						$("#btnSearch").click();
      			},'json')
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
        	deleteMutilterm(grid,"${ctx}/employeeLevel/batchDelete","sign");
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