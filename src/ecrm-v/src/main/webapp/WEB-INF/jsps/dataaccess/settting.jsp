<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.maven.entity.EnterpriseEmployeeType.Type" %>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body style="padding: 10px;">
    <div class="demo-content" >
<!-- 搜索页 ================================================== style=" margin:0 auto;  width:950px;"-->
<form id="searchForm" class="form-horizontal" style="outline: none;" method="post">
    <input type="hidden" name="parentemployeecode" />
    <input name="end_hidden" type="hidden" />
    <input name="last_hidden" type="hidden" />
    <div class="row well" style="margin-left: 0px;position: relative;" >
    <div style="position: relative;display: inline-block;">
      <div style="float: left;margin-right: 96px;">
          <div class="control-group span7">
              <label class="control-label">登录账户：</label>
                <input name="loginaccount"  type="text"  data-tip='{"text":"登录账户"}'  class="control-text"/>
            </div>
          </div>
          	 <div style="position: absolute;right: 0px;top: 3px;">
             	 <button id="btnSearch" type="submit"  class="button button-primary"><span class="icon-search icon-white"></span> 搜 索 </button>
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
(function(){
	//权限验证
	function permissionValidate(){
		var array= new Array();
			array.push({
		        btnCls : 'button button-primary',
		        text:"<span class='icon-file icon-white'></span>添加授权",
		        handler : function(){
		        	var dialog = new BUI.Overlay.Dialog({
		        	    title:'选择授权用户',
		        	    height:600,
		        	    width:730,
		        	    loader : {
		        	      url : '${ctx}/PrivateData/ChoiceUser',
		        	      autoLoad : true,
		        	      lazyLoad : false, 
		        	    },
		        	    closeAction : 'destroy',
		        	    buttons:[
		        	             {
		        	               text:'确定',
		        	               elCls : 'button button-primary',
		        	               handler : function(){
		        	            	 var objects = $("#accredit-user input[name='employee']");
		        	            	 if(objects.size()==0){
		        	            		 BUI.Message.Alert("请选择需要授权的用户",'warning');
		        	            	 }else{
		        	            		 var employees = new Array();
			        	            	 $.each(objects,function(i,n){
			        	            		 employees.push($(n).val());
			        	            	 });
			        	            	 var self = this;
			        					 $.ajax({
			        						type : "POST",
			        						url : "${ctx}/PrivateData/Add",
			        						data : {"employees" : employees},
			        						dataType : "json",
			        						success : function(data) {
			        							if (data.status == 1) {
			        								self.close();
			        								$("#accredit-user").html("");
			        								$("#searchForm").submit();
			        							} else {
			        								BUI.Message.Alert(data.message,'warning');
			        							}
			        						}
			        					 });
		        	            	 }
		        	               }
		        	             },{
		        	               text:'取消',
		        	               elCls : 'button button-primary',
		        	               handler : function(){
		        	                 this.close();
		        	               }
		        	             }
		        	           ],
		        	    mask:true
		        	  });
		        	dialog.show();
		        }
		  	});
			array.push({
		        btnCls : 'button button-danger bin',
		        text:'<span class=" icon-trash icon-white"></span>删除授权',
		        handler : function(){
		        	var selectItem = grid.getSelection();
					if (selectItem.length == 0) {
						BUI.Message.Alert('请选择需要删除的数据', 'info');
					} else {
						BUI.Message.Confirm('是否确定删除？',function() {
							var sign = new Array();
							BUI.each(selectItem,function(item) {
										sign.push(item.sign);
							});
							$.ajax({
								type : "POST",
								url : "${ctx}/PrivateData/Delete",
								data : {"sign" : sign},
								dataType : "json",
								success : function(data) {
									if (data.status == 1) {
										BUI.Message.Alert(data.message,'success');
										$("#searchForm").submit();
									} else {
										BUI.Message.Alert(data.message,'warning');
									}
								}
							});
						}, 'question');
					}
		     }});
			array.push({
		        btnCls : 'button button-primary',
		        text:"<span class='icon-th-large icon-white'></span>查看隐私数据",
		        handler : function(){
		        	var dialog2 = new BUI.Overlay.Dialog({
		        	    title:'隐私数据',
		        	    height:300,
		        	    width:510,
		        	    loader : {
		        	      url : '${ctx}/PrivateData/ViewPrivateData',
		        	      autoLoad : true,
		        	      lazyLoad : false, 
		        	    },
		        	    mask:true
		        	  });
		        	dialog2.show();
		        }
		  	});
		return array;
	}
	
	var Grid = BUI.Grid,
	  Store = BUI.Data.Store,
	  columns = [
	    { title: '用户账号',   width: '15%',elCls:'center',  sortable: false, dataIndex: 'loginaccount'},
	    { title: '类型',   width: '15%',elCls:'center',  sortable: false, dataIndex: 'employeetypename'},
	    { title: '在线状态',   width: '15%',elCls:'center',dataIndex:'onlinestatus',sortable: false, renderer:function(value,obj){
	    	switch(value){
	    		case 0:
	    			return "<span class='label'>下线</span>";
	    		case 1:
	    			return "<span class='label label-success'>在线</span>";
	    		default:
	    			return "无";
	    	}
	    }},
	    { title: '用户状态',   width: '15%',elCls:'center',  sortable: false,renderer:function(value,obj){
	    	switch(obj.employeestatus){
			case 1:
				return "启用";
			case 2:
				return "锁定游戏";
			case 3:
				return "禁用";
			default:
				return "无";
			}
		}},
		{ title: '最后登录时间',   width: '15%',elCls:'center', dataIndex: 'lastlogintime',renderer:BUI.Grid.Format.datetimeRenderer},
	    { title: '注册日期',   width: '15%',elCls:'center',  sortable: false,   dataIndex: 'logindatetime',renderer:BUI.Grid.Format.datetimeRenderer},
	    { title : '操作',width: 110, sortable: false,  renderer : function(value,obj){
	    	var temp = '<button class="button button-danger botton-margin deleteAccess" data-id="'+obj.sign+'" ><span class="icon-remove icon-white"></span>删除授权</button>';
	    	return temp;
	    }}
	  ];

		var store = new Store({
	      url : '${ctx}/PrivateData/Data',
	      autoLoad:false,
	      pageSize:15,
	      remoteSort: true,
	      sortInfo : {
	          field : 'lastlogintime',
	          direction : 'desc'
	        }, 
	  	}),grid = new Grid.Grid({
	      render:'#grid',
	      autoRender:true,
	      loadMask: true,
	      width:'100%',
	      columns : columns,
	      store: store,
	      emptyDataTpl:'<div class="centered"><img alt="Crying" src="${statics }/img/dataisnull.png"/><font style="color:red;font-size:20px;"><h2>没有找到匹配的数据!</h2></font></div>',
	      plugins : [ Grid.Plugins.CheckSelection ],
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
		
		$(".deleteAccess").live("click",function(){
			var id = $(this).attr("data-id");
			BUI.Message.Confirm('是否确定删除？',function() {
				var sign = new Array();
				sign.push(id);
				$.ajax({
					type : "POST",
					url : "${ctx}/PrivateData/Delete",
					data : {"sign" : sign},
					dataType : "json",
					success : function(data) {
						if (data.status == 1) {
							BUI.Message.Alert(data.message,'success');
							$("#searchForm").submit();
						} else {
							BUI.Message.Alert(data.message,'warning');
						}
					}
				});
			}, 'question');
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

</script>