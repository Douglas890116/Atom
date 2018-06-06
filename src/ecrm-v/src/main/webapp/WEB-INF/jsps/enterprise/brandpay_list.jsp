<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<style type="text/css">
   .bui-stdmod-body{
        overflow-x : hidden;
        overflow-y : auto;
      }
 </style>
<body  style="padding: 10px;">
    <div class="demo-content">
   <form id="searchForm" class="form-horizontal" style="outline: none;" method="post" >
    <input name="end_hidden" type="hidden" />
    <input name="sign"  type="hidden" value="${sign }"/>
    
    
            
    <div class="well">
    	<div style="position: relative;display: inline-block;">
    	<div class="control-group">
              <label class="control-label">企业名称：</label>
              <div class="controls">
              	${enterprise.enterprisename}
              </div>
            </div>
            
    </div>
    </div>
    </form>
    <!-- 搜索页 ================================================== -->
    
    
    <div class="search-grid-container well">
      <div id="grid">
    </div>
<!-- 用户新增按钮是否有操作权限 -->
	<script type="text/javascript" src="${statics }/js/custom/page-quote.js"></script>
    <script type="text/javascript">
    (function(){
    	//权限判断
    	var add_brand = ${sessionScope.ERCM_USER_PSERSSION['MN001Z'].menustatus==1};
  		
  		
        function permissionValidate(){
        	var array= new Array();
        	if(add_brand){
        		array.push({
                  btnCls : 'button button-primary',
                  text:'<span class="icon-file icon-white"></span>新增域名',
                  handler : function(){
                	 openNewWindow('create_brand','${ctx}/EnterpriseOperatingBrandPay/add?sign=${sign}','新增域名');
                  }
              });
        	}
        	
        	return array;
        }
    	
        var Grid = BUI.Grid,
          Store = BUI.Data.Store,
          columns = [
            { title: '品牌名称',width:'15%', dataIndex: 'brandname'},
            { title: '支付回调域名',width:'25%', sortable: false,  dataIndex: 'paycallbackurl' },
            { title: '后台管理域名',width:'25%', sortable: false,  dataIndex: 'adminurl'},
            { title: '操作',width:360,sortable: false,renderer:function(value,obj){
            	var  html = "";
            	html += '<button type="button" onclick="openNewWindow(\'brandpay_edit\',\'${ctx}/EnterpriseOperatingBrandPay/update?sign=${sign}&lsh='
						+ obj.lsh
						+ '\',\'编辑域名\');" class="button  button-success botton-margin"><span class="icon-edit icon-white"></span>编辑</button>';
				html +='<button type="button" class="button button-danger button-space"  onclick=delMessage(this,"'+obj.sign+'")><span class="icon-remove icon-white"></span> 删 除 </button></li>';
                return html;
            }}
          ];
      
        var store = new Store({
            url : '${ctx}/EnterpriseOperatingBrandPay/data',
            autoLoad : false,
            pageSize: 15,
            remoteSort: true,
            sortInfo : {
                field : 'lsh',
                direction : 'desc'
              }
          }),grid = new Grid.Grid({
            render:'#grid',
            autoRender:true,
            width:'100%',
            loadMask: true,
            fixedLayout:true,
            columns : columns,
            store: store,
            emptyDataTpl : '<div class="centered"><img alt="Crying" src="${statics }/img/dataisnull.png"/><font style="color:red;font-size:20px;"><h2>没有找到匹配的数据!</h2></font></div>',
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
        
    })();
    
    function delMessage(obj,val){
		BUI.Message.Confirm('是否确定删除？',function() {
			var array = new Array();
				array.push(val);
			$.ajax({
				type : "POST",
				url : "${ctx}/EnterpriseOperatingBrandPay/deleteIp",
				data : {"array" : array,"sign" : val},
				dataType : "json",
				success : function(data) {
					if (data.status == 1) {
						BUI.Message.Alert(data.message,'success');
						$(obj).parent().parent().parent().parent().remove();
					} else {
						BUI.Message.Alert(data.message,'warning');
					}
				}
			});
		}, 'question');
	}
    
    </script>
<!-- script end -->
  </div>
	
	</div>
	
</body>
</html>