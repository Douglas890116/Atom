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
              <label class="control-label">帐变编码：</label>
                <input name="moneychangetypecode" type="text"  data-tip='{"text":"帐变编码"}' class="control-text"/>
            </div>
            <div class="control-group span7">
              <label class="control-label">帐变类型：</label>
                  <input name="moneychangetype" type="text"  data-tip='{"text":"资金类型"}' class="control-text"/>
            </div>
            <div class="control-group span7">
              <label class="control-label">帐变类型类别 ：</label>
           		<select name="moneychangetypeclassify"  class="bui-form-field-select bui-form-field span3">
					<option value="">请选择</option>
                    <option value="1">常规</option>
                    <option value="2">活动</option>
              	</select>
            </div>
            <div class="control-group span5">
              <label class="control-label">进出帐类型：</label>
           		<select name="moneyinouttype"  class="bui-form-field-select bui-form-field span3">
					<option value="">请选择</option>
                    <option value="1">进账</option>
                    <option value="2">出账</option>
              	</select>
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
  var add = ${sessionScope.ERCM_USER_PSERSSION['MN0073'].menustatus == 1 };
  var update = ${sessionScope.ERCM_USER_PSERSSION['MN0076'].menustatus == 1 };
  var batch_delete = ${sessionScope.ERCM_USER_PSERSSION['MN0075'].menustatus == 1 };
  var one_delete = ${sessionScope.ERCM_USER_PSERSSION['MN0074'].menustatus == 1 }; 
   //权限验证
     function permissionValidate(){
      var array= new Array();
      if(add){
        array.push({
              btnCls : 'button button-primary',
              text:'<span class="icon-file icon-white"></span>创建类型',
              handler : function(){
                openNewWindow('create_employeeMoneyChangeType','${ctx}/moneyChangeType/add','创建帐变类型');
              }
          });
      }
      if(batch_delete){
    	  array.push({
  	        btnCls : 'button button-danger bin',
  	        text:'<span class=" icon-trash icon-white"></span>批量删除',
  	        handler : function(){
   	        	deleteMutilterm(grid,"${ctx}/moneyChangeType/deleteSelectEmployeeMoneyChangeType","moneychangetypecode");
  	     }});
      }
      return array;
     }
        var Grid = BUI.Grid,
          Store = BUI.Data.Store,
          columns = [
			{ title: '帐变编码',width: '40%',sortable: false,dataIndex: 'moneychangetypecode',renderer:function(value,obj){
				return value.split("_")[1];
			}},
            { title: '帐变类型',width: '20%',sortable: false,dataIndex: 'moneychangetype'},
            { title: '帐变类型类别 ',width: '20%',sortable: false,dataIndex: 'moneychangetypeclassify',renderer:function(value,obj){
            	switch(value){
            		case "1":
                		return "常规";
                		break;
            		case "2":
                		return "活动";
                		break;
            	}
            }},
            { title: '进出帐类型',width: '20%',sortable: false,dataIndex: 'moneyinouttype',renderer:function(value,obj){
            	if(value/1==1) return "进账";
            	if(value/1==2) return "出账";
            }},
            { title : '操作',width: 180,sortable: false,renderer : function(value,obj){
              var temp = '';
              	 if(update){
                  	temp+= '<button type="button" class="button  button-success botton-margin" onclick=editMoneyChangeType("'+obj.moneychangetypecode+'")><span class="icon-edit icon-white"></span>编辑  </button>';
              	 }  
            	 if(one_delete){
                	  temp += '<button type="button" class="button button-danger" style="margin-right:9px;" onclick="deleteRow(this)" name="/moneyChangeType/deleteEmployeeMoneyChangeType" alt="'+obj.moneychangetypecode+'"><span class="icon-remove icon-white"></span>删除 </button>';
            	 }
            	  return temp;
            }}
          ];

        var store = new Store({
            url : '${ctx}/moneyChangeType/data',
            autoLoad : false,
            pageSize:15
          }),grid = new Grid.Grid({
            render:'#grid',
            autoRender:true,
            loadMask: true,
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
        
       	   grid.on('cellclick',function(ev){
	         var sender = $(ev.domTarget);
	         if(!sender.hasClass('x-grid-checkbox')){ //如果不是点中勾选列，不能进行选中操作
	           return false;
	         }
	       });
        
       	$("#searchForm").submit(function(){
      	  var obj = BUI.FormHelper.serializeToObject(this);
            obj.start = 0;
            store.load(obj);
      	  return false;
        }).submit();
       	
    </script>