<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body  style="padding: 10px;">
    <div class="demo-content">
<!-- 搜索页 ================================================== -->
   <form id="searchForm" class="form-horizontal" style="outline: none;" method="post">
    <div class="row well" style="margin-left: 0px;">
    	<div style="position: relative;display: inline-block;">
      	<div style="float: left;margin-right: 126px;">
            <div class="control-group span7">
              <label class="control-label">银行账号：</label>
                <input name="openningaccount"  type="text" data-tip='{"text":"银行账号"}' class="control-text" placeholder="银行账号"/>
            </div>
            <div class="control-group span7">
              <label class="control-label">开户名：</label>
                <input name="accountname"  type="text" data-tip='{"text":"开户名"}' class="control-text" placeholder="开户名"/>
            </div>
	        <div class="control-group span7">
	              <label class="control-label">开户银行：</label>
	                <select name="bankcode" aria-pressed="false"  aria-disabled="false" class="bui-form-field-select bui-form-field">
						<option value="" >请选择</option>
	              	</select>
	         </div>
            <div class="control-group span7  toggle-display">
              <label class="control-label">开户支行：</label>
                <input name="openningbank"  type="text" data-tip='{"text":"开户支行"}' class="control-text" placeholder="开户支行"/>
            </div>
            <div class="control-group span7  toggle-display">
              <label class="control-label">联系人：</label>
                <input name="enterprisecontact"  type="text" data-tip='{"text":"联系人"}' class="control-text" placeholder="联系人"/>
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
    <!--  隐藏域 -->
     <div id="content" style="display: none;">
      <form id="form" class="form-horizontal">
        <div class="row">
           <div class="control-group span10">
              <label class="control-label">账户余额：</label>
              <input name="currentbalance" type="text" class="input-normal control-text"/>
           </div>
        </div>
      </form>
    </div>
    <script type="text/javascript">
      loadAllBank();
      
      //新增按钮有没有权限
      var add_isoperate = ${sessionScope.ERCM_USER_PSERSSION['MN000L'].menustatus==1};
      //删除 按钮有没有权限
      var delete_isoperate = ${sessionScope.ERCM_USER_PSERSSION['MN001T'].menustatus==1};
      //编辑按钮有没有权限
      var editor_isoperate = ${sessionScope.ERCM_USER_PSERSSION['MN001S'].menustatus==1};
    	(function(){
    		//权限验证
    	      function permissionValidate(){
    	      	var array= new Array();
    	      	if(add_isoperate){
    	      		array.push({
    	                btnCls : 'button button-primary',
    	                text:'<span class="icon-file icon-white"></span>添加收款卡',
    	                handler : function(){
    	                	openNewWindow('create_enterpriseInformation','${ctx}/EInformation/add','添加收款卡');
    	                }
    	            });
    	      		array.push({
    					btnCls : 'button button-danger',
    					text : '<span class=" icon-trash icon-white"></span>删除收款卡',
    					handler : function() {
    						var selectItem = grid.getSelection();
    						if (selectItem.length == 0) {
    							BUI.Message.Alert('请选择需要删除的数据', 'info');
    						} else {
    							BUI.Message.Confirm('是否确定删除？',function() {
    								var sign = new Array();
    								BUI.each(selectItem,function(item) {
    											sign.push(item.enterpriseinformationcode);
    								});
    								$.ajax({
    									type : "POST",
    									url : "${ctx}/EInformation/BatchDelete",
    									data : {"sign" : sign},
    									dataType : "json",
    									success : function(data) {
    										if (data.status == 1) {
    											BUI.Message.Alert(data.message,'success');
    											grid.removeItems(selectItem);
    										} else {
    											BUI.Message.Alert(data.message,'warning');
    										}
    									}
    								});
    							}, 'question');
    						}
    					}
    				});
    	      	}
    	      	return array;
    	      }
    		
    		var Grid = BUI.Grid,
            Store = BUI.Data.Store,
            columns = [
			  { title: '银行账号',width:'15%',  dataIndex: 'openningaccount', selectable: true},
              { title: '开户名',width:'10%',  dataIndex: 'accountname', selectable: true},
              { title: '开户银行',width:'10%',   dataIndex: 'bankname',  showTip: true },
              { title: '开户支行',width:'10%',   dataIndex: 'openningbank',  showTip: true },
              { title: '联系人姓名',width:'10%',  dataIndex: 'enterprisecontact', selectable: true },
              { title: '卡上余额',width:'10%',   dataIndex: 'currentbalance',  showTip: true,renderer:function(value,obj){
            	  return parseInt(value).toFixed(2);
              } },
              { title: '联系人电话',width:'15%',  dataIndex: 'enterprisephone', selectable: true},
              { title: '联系人QQ',width:'10%',  dataIndex: 'enterpriseqq', selectable: true},
              { title: '联系人邮箱',width:'15%',  dataIndex: 'enterpriseemail', selectable: true},
              { title: '是否启用',width:'10%',  dataIndex: 'enable', renderer:function(value,obj){
            	  return value/1== 1?"启用":"禁用";
              }},
              { title: '操作',width:258, dataIndex: 'g',renderer:function(value,obj){
                    var temp = '';
                	//编辑按钮判断有没有权限
                	if(editor_isoperate){
                		temp = '<button type="button" class="button  button-success " style="margin-right:9px;" onclick="edit(this)" sign="'+obj.enterpriseinformationcode+'" title="编辑"><span class="icon-edit icon-white"></span> </button>';	
                	}
                	//删除按钮判断有没有权限
                	if(delete_isoperate){
                		temp += '<button type="button" class="button button-danger" style="margin-right:9px;" onclick="del(this)" sign="'+obj.enterpriseinformationcode+'" title="删除"><span class=" icon-remove icon-white"></span> </button>';
                	}
                	temp+= '<button type="button" class="button button-warning"  onclick=AdjustmentCompanyAmount("'+obj.enterpriseinformationcode+'","'+obj.currentbalance+'")>调整余额</button>';
                	return temp;
              }}
            ];
        
          var store = new Store({
              url : '${ctx}/EInformation/data',
              autoLoad : false,
              pageSize:15
            }),grid = new Grid.Grid({
              render:'#grid',
              autoRender:true,
              width:'100%',
              loadMask: true,
              columns : columns,
              store: store,
              plugins : [Grid.Plugins.CheckSelection],
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
         
    	})()
    	
	   	function edit(obj){
         	var sign = $(obj).attr("sign");
         	openNewWindow('editor_enterpriseInformation','${ctx}/EInformation/edit?sign='+sign,'编辑收款卡');
        }
    	
    	function del(obj){
    		BUI.Message.Confirm('确认要删除吗？',function(){
        		var sign = $(obj).attr("sign");
              	var tr = $(obj).parents("tr");
               	$.ajax({
       				type: "POST",
       				url: "${ctx}/EInformation/delete",
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
        }
        
    </script>
<!-- script end -->
  </div>
</div>
</body>
</html>