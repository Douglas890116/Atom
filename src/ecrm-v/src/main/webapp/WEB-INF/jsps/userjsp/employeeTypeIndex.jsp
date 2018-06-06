<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body  style="padding: 10px;">
    <div class="demo-content">
<!-- 搜索页 ================================================== -->
   <form id="searchForm" class="form-horizontal" style="outline: none;" method="post">
    <div class="row well" style="margin-left: 0px;">
    	<div style="position: relative;display: inline-block;">
      	<div style="float: left;margin-right: 96px;">
            <div class="control-group span7">
              <label class="control-label">员工类型：</label>
                <input name="employeetype"  type="text" data-tip='{"text":"员工类型"}'  class="control-text"/>
            </div>
          </div>
          <div style="position: absolute;right: 15px;top: 3px;">
                <button id="btnSearch" type="submit" class="button button-primary"><span class="icon-search icon-white"></span>搜索</button>
          </div>
       </div>
    </div>
    </form>
    <div class="search-grid-container well">
        <div id="grid">
    </div>
    <script type="text/javascript">
      //新增按钮有没有权限
      var add_isoperate = ${sessionScope.ERCM_USER_PSERSSION['MN0062'].menustatus==1};
      //删除 按钮有没有权限
      var delete_isoperate = ${sessionScope.ERCM_USER_PSERSSION['MN0064'].menustatus==1};
      //批量删除
      var batch_delete = ${sessionScope.ERCM_USER_PSERSSION['MN006B'].menustatus==1};
      //编辑按钮有没有权限
      var editor_isoperate = ${sessionScope.ERCM_USER_PSERSSION['MN0063'].menustatus==1};
    	(function(){
    		 //权限验证
    	      function permissionValidate(){
    	      	var array= new Array();
    	      	if(add_isoperate){
    	      		array.push({
    	                btnCls : 'button button-primary',
    	                text:'<span class="icon-file icon-white"></span>添加类型',
    	                handler : function(){
    	                	openNewWindow('create_employeeType','${ctx}/employeeType/add','添加类型');
    	                }
    	            });
    	      	}
    	      	if(batch_delete){
    	      		array.push({
    	    	        btnCls : 'button button-danger bin',
    	    	        text:'<span class=" icon-trash icon-white"></span>批量删除',
    	    	        handler : function(){
    	      	        	deleteMutilterm(grid,"${ctx}/employeeType/deleteSelect","employeetypecode");
    	    	     }});
    	      	}
    	      	return array;
    	      }
    		 
    		var Grid = BUI.Grid,
            Store = BUI.Data.Store,
            columns = [
			  { title: '员工编码',width:'35%', sortable: true, dataIndex: 'employeetypecode', selectable: true,renderer:function(value,obj){
				  return value.split("_")[1];
			  }},
              { title: '员工类型',width:'35%', sortable: true, dataIndex: 'employeetype', selectable: true},
              { title: '操作',width:180, dataIndex: 'g',renderer:function(value,obj){
                    var temp = '';
                	//编辑按钮判断有没有权限
                	if(editor_isoperate){
                		temp = '<button type="button" class="button  button-success " style="margin-right:9px;" onclick="employeeTypeEditor(this)" alt="'+obj.employeetypecode+'"><span class="icon-edit icon-white"></span>编辑  </button>';	
                	}
                	//删除按钮判断有没有权限
                	if(delete_isoperate){
                		temp += '<button type="button" class="button button-danger" style="margin-right:9px;" onclick="deleteRow(this)" name="/employeeType/delete" alt="'+obj.employeetypecode+'"><span class="icon-remove icon-white"></span>删除  </button>';
                	}
                	return temp;
              }}
            ];
        
          var store = new Store({
              url : '${ctx}/employeeType/data',
              autoLoad : false,
              pageSize:15
            }),
            grid = new Grid.Grid({
              render:'#grid',
              autoRender:true,
              width:'100%',
              loadMask: true,
              columns : columns,
              store: store,
              plugins : [ Grid.Plugins.CheckSelection],
              tbar:{
               items:permissionValidate()
              },
              bbar : {
                pagingBar:true,
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
         
    	})()
    	
	   	/* function edit(obj){
         	var sign = $(obj).attr("sign");
         	openNewWindow('editor_enterpriseInformation','${ctx}/EInformation/edit?sign='+sign,'编辑收款卡');
        }
    	 */
    	/* function del(obj){
    		BUI.Message.Confirm('确认要删除吗？',function(){
        		var sign = $(obj).attr("sign");
              	var tr = $(obj).parents("tr");
               	$.ajax({
       				type: "POST",
       				url: "${ctx}/employeeType/delete",
       				data:{"sign":sign},
       				dataType: "json",
       			    success: function(data) {
       			    	if(data.status==1){
    	   			    	BUI.Message.Alert(data.message,'success');
    	   			    	tr.remove();
       			    	}else{
       			    		BUI.Message.Alert(data.message,'warning');
       			    	}
       			   }});
             },'question');
        } */
    </script>
<!-- script end -->
  </div>
</div>
</body>
</html>