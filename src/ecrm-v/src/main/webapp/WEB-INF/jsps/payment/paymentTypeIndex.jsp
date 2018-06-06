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
              <label class="control-label">支付编码：</label>
                <input name="paymenttypecode" type="text" data-tip='{"text":"支付编码"}'  class="control-text"/>
            </div>
            <div class="control-group span7">
              <label class="control-label">支付类型：</label>
                  <input name="paymenttype" type="text" data-tip='{"text":"支付类型"}'  class="control-text"/>
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

</body>
</html>
   <script type="text/javascript">
   loadAllBank();
   
  var add_paymentType  =    ${sessionScope.ERCM_USER_PSERSSION['MN006X'].menustatus == 1 };
  var update_paymentType  = ${sessionScope.ERCM_USER_PSERSSION['MN0070'].menustatus == 1 };
  var batch_delete = ${sessionScope.ERCM_USER_PSERSSION['MN006Z'].menustatus == 1 };
  var one_delete = ${sessionScope.ERCM_USER_PSERSSION['MN006Y'].menustatus == 1 };
  var employeetype =  '${sessionScope.ERCM_USER_SESSION.employeetypecode}'; 
   //权限验证
     function permissionValidate(){
      var array= new Array();
      if(add_paymentType){
        array.push({
              btnCls : 'button button-primary',
              text:'<span class="icon-file icon-white"></span>添加类型',
              handler : function(){
                openNewWindow('create_paymentType','${ctx}/paymenttype/add','添加支付类型');
              }
          });
      }
      if(batch_delete){
    	  array.push({
  	        btnCls : 'button button-danger bin',
  	        text:'<span class=" icon-trash icon-white"></span>批量删除',
  	        handler : function(){
    	       deleteMutilterm(grid,"${ctx}/paymenttype/deleteSelectPaymentType","paymenttypecode");
  	     }});
      }
      return array;
     }
        var Grid = BUI.Grid,
          Store = BUI.Data.Store,
          columns = [
			{ title: '支付编码',width: '35%',sortable: false,dataIndex: 'paymenttypecode',renderer:function(value,obj){
				return value.split("_")[1];
			}},
            { title: '支付类型',width: '35%',sortable: false,dataIndex: 'paymenttype'},
            { title : '操作',width:200,sortable: false,renderer : function(value,obj){
              var temp = '';
              	 if(update_paymentType){
                  	temp+= '<button type="button" class="button  button-success botton-margin" onclick=editPaymentType("'+obj.paymenttypecode+'")><span class="icon-edit icon-white"></span>编辑  </button>';
              	 }  
            	 if(one_delete){
                	  temp += '<button type="button" class="button button-danger" style="margin-right:9px;" onclick="deleteRow(this)" name="/paymenttype/deletePaymentType" alt="'+obj.paymenttypecode+'"><span class="icon-remove icon-white"></span>删除 </button>';
            	 }
            	  return temp;
            }}
          ];

        var store = new Store({
            url : '${ctx}/paymenttype/data',
            autoLoad : false,
            pageSize:15
          }),
          grid = new Grid.Grid({
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