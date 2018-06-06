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
                <label class="control-label">级别名称：</label>
                  <input name="employeeLevelName" type="text" data-tip='{"text":"级别名称"}' class="control-text"/>
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
            <label class="control-label">级&nbsp;&nbsp;别&nbsp;&nbsp;&nbsp;&nbsp;名&nbsp;&nbsp;称:</label>
            <div class="controls">
              <input name="employeeLevelName" type="text" data-rules="{required:true}" class="input-normal control-text">
            </div>
          </div>
          <div class="control-group span8">
            <label class="control-label">等级次序:</label>
            <!-- 
            <div class="controls">
              <input name="ord" type="text" data-rules="{required:true,number : true, min: 1, max:10 }" class="input-normal control-text">
              	低到高
            </div>
             -->
            <div class="controls">
              <select name="ord" data-rules="{required:true,number : true}"  class="bui-form-field-select bui-form-field" style="width: 193px;">
                  <option value="">--请选择--</option>
	                  <option value="1">第一级</option>
	                  <option value="2">第二级</option>
	                  <option value="3">第三级</option>
	                  <option value="4">第四级</option>
	                  <option value="5">第五级</option>
	                  <option value="6">第六级</option>
	                  <option value="7">第七级</option>
	                  <option value="8">第八级</option>
	                  <option value="9">第九级</option>
	                  <option value="10">第十级</option>
                </select>
            </div>
            
          </div>
          
          <div class="control-group span8">
            <label class="control-label">升级所需投注流水:</label>
            <div class="controls">
              <input name="conditionlevel" type="text" data-rules="{required:true}" class="input-normal control-text">
            </div>
          </div>
          
          <div class="control-group span8">
            <label class="control-label">每日取款次数:</label>
            <div class="controls">
              <input name="takeTimeOfDay" type="text" data-rules="{required:true,number : true}" class="input-normal control-text">
            </div>
          </div>
          <div class="control-group span8">
            <label class="control-label">每日取款金额 :</label>
            <div class="controls">
              <input name="takeMoneyOfDay" type="text" data-rules="{required:true,number : true}" class="input-normal control-text">
            </div>
          </div>
          <div class="control-group span8">
            <label class="control-label">默认 :</label>
            <div class="controls">
              <select name="isdefault" data-rules="{required:true,number : true}"  class="bui-form-field-select bui-form-field" style="width: 193px;">
                  <option value="">--请选择--</option>
	                  <option value="0">不是默认</option>
	                  <option value="1">默认</option>
                </select>
            </div>
          </div>
          
        </div>
      </form>
    </div>
    <p><font color="red">提示：每周一凌晨04:01分，对上周的流水进行计算。</font></p>
</body>
</html>
   <script type="text/javascript">
   loadAllBank();
   
  var add  =    ${sessionScope.ERCM_USER_PSERSSION['MN008E'].menustatus == 1 };
  var update  = ${sessionScope.ERCM_USER_PSERSSION['MN008B'].menustatus == 1 };
  var batch_delete = ${sessionScope.ERCM_USER_PSERSSION['MN008C'].menustatus == 1 };
  var one_delete = ${sessionScope.ERCM_USER_PSERSSION['MN008D'].menustatus == 1 };
  var setting_bouns = ${sessionScope.ERCM_USER_PSERSSION['MN00DR'].menustatus == 1 };
  
  var employeetype =  '${sessionScope.ERCM_USER_SESSION.employeetypecode}'; 
   //权限验证
     function permissionValidate(){
      var array= new Array();
      if(add){
        array.push({
              btnCls : 'button button-primary',
              text:'<span class="icon-file icon-white"></span>添加',
              listeners : {
                  'click' : addFunction
              }
          });
      }
      if(batch_delete){
    	  array.push({
  	        btnCls : 'button button-danger bin',
  	        text:'<span class="icon-trash icon-white"></span>批量删除',
  	      	listeners : {
              'click' : delFunction
            }
  	      });
      }
      return array;
     }
        var Grid = BUI.Grid,
          Store = BUI.Data.Store,
          columns = [
			{ title: '企业编码',width: '6%',sortable: false,dataIndex: 'enterpriseCode',editor:{xtype:"text",disabled:true}},
      		{ title: '级别编码',width: '6%',sortable: false,dataIndex: 'employeeLevelCode'},
            { title: '级别名称',width: '12%',sortable: false,dataIndex: 'employeeLevelName',editor:{xtype:"text"}},
            { title: '标准等级次序',width: '6%',sortable: false,dataIndex: 'ord',editor:{xtype:"number",rules:{required:true,min:1,max:10}}},
            { title: '升级投注流水',width: '12%',sortable: false,dataIndex: 'conditionlevel',editor:{xtype:"text"}},
            {title : '是否默认',width : '6%',sortable : true, dataIndex : 'isdefault',renderer:function(value,obj){
				if(value == "0" ) {
					return "--";
				} else {
					return "默认";
				}
			}},
            
            { title: '每日最多取款次数',width: '10%',sortable: false,dataIndex: 'takeTimeOfDay',editor:{xtype:"number",rules:{required:true}}},
            { title: '每日最多取款金额',width: '10%',sortable: false,dataIndex: 'takeMoneyOfDay',editor:{xtype:"number",rules:{required:true}}},
            /* 
            { title: '升级周投注额',width: '15%',sortable: false,dataIndex: 'takeMoneyOfDay',editor:{xtype:"number",rules:{required:true}}},
             */
            { title : '操作',width: '20%',sortable: false,renderer : function(value,obj){
            	 var temp = '';
                 if(update){
                	 temp = temp+'<button type="button" class="button  button-success btn-edit" style="margin-right:9px;" onclick="updateEmployeeLevel()"><span class="icon-edit icon-white"></span>编辑  </button>';
                 }
				if(one_delete){
					temp = temp+'<button type="button" class="button button-danger" style="margin-right:9px;" onclick="deleteRow(this)" name="/employeeLevel/delete" alt="'+obj.sign+'"><span class="icon-remove icon-white"></span>删除 </button>';
				}           
				
				if(setting_bouns){
               	 	temp = temp+"<button type='button' class='button  button-success ' style='margin-right:9px;' onclick=openNewWindow('bounssettlement','${ctx}/employeeLevelBonus/setting?employeelevelcode="+obj.employeeLevelCode+"','结算配置')><span class=icon-edit icon-white></span>设置洗码  </button>";
                }
				
				return temp;
            }}
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
            url : '${ctx}/employeeLevel/list',
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
                	$.post(getRootPath()+"/employeeLevel/update",
        					{
                		employeeLevelCode:data.employeeLevelCode,
                		employeeLevelName:data.employeeLevelName,
                		takeTimeOfDay:data.takeTimeOfDay,
                		takeMoneyOfDay:data.takeMoneyOfDay,
                		isdefault:data.isdefault,
                		conditionlevel : data.conditionlevel,
                		ord:data.ord},
        					function(data){
        						if(data.status=="failure"){
        							BUI.Message.Alert("用户级别保存失败!"+data.message,"error");
        							return;
        						}
        			},'json')  
            }else{
            	var data = editing.get('record');
              	$.post(getRootPath()+"/employeeLevel/save",
      					{
              		employeeLevelName:data.employeeLevelName,
              		takeTimeOfDay:data.takeTimeOfDay,
              		takeMoneyOfDay:data.takeMoneyOfDay,
              		isdefault:data.isdefault ,
              		conditionlevel : data.conditionlevel,
              		ord:data.ord},
      					function(data){
      						if(data.status=="failure"){
      							BUI.Message.Alert("用户级别保存失败!"+data.message,"error");
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