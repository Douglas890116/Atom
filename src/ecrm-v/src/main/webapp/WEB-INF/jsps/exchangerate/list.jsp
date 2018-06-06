<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body style="padding: 10px">
	<div class="demo-content">
		<!-- ==============================搜索页===================================== -->
		<form id="formSearch" class="form-horizontal" style="outline: none;" method="post">
            <input name="end_hidden" type="hidden" />
			<div class="row well" style="margin-left: 0px; position: relative;">
				<div style="position: relative; display: inline-block;">
					<div style="float: left; margin-right: 10px;">
						<div class="control-group span7">
							<label class="control-label">货币名称：</label>
							<input name="currencyname" type="text" class="control-text" placeholder="货币名称"/>
						</div>
						<div class="control-group span14">
							<label class="control-label">货币代码：</label>
							<input name="currencycode" type="text" class="control-text" placeholder="货币代码"/>
						</div>
					</div>
					
					<div style="position: absolute; right: 15px; top: 3px;">
							<button id="btnSearch" type="submit" class="button button-primary">
								<span class="icon-search icon-white"></span>搜索
							</button>
					</div>
				</div>
			</div>
		</form>   
	<!-- ==========list展示层========== -->
	<div class="search-grid-container well">
		<div id="grid"></div>
	</div>
	</div>
	<!-- 初始隐藏 dialog内容 -->
    <div id="content" class="hide">
      <table style="text-align: center; vertical-align: middle;">
      <tr>
      
      	<td>
      	<form class="form-horizontal">
      	<div class="row">
			<div class="control-group-span8">
				<input type="hidden" name="id">
				<label class="control-label">货&nbsp;&nbsp;币&nbsp;&nbsp;名&nbsp;&nbsp;称&nbsp;：</label>
				<div class="controls">
				<input name="currencyname" type="text" data-rules="{required:true}" class="input-normal control-text" placeholder="货币名称"/>
				</div>
				<br/><br/><br/>
				<label class="control-label">货&nbsp;&nbsp;币&nbsp;&nbsp;代&nbsp;&nbsp;码&nbsp;：</label>
				<div class="controls">
				<input name="currencycode" type="text" data-rules="{required:true, regexp:[/^[a-zA-Z]+$/,'不是有效的字母！']}" class="input-normal control-text" placeholder="字母代码"/>
				</div><br/><br/>
				<br/>
				<label class="control-label">对人民币汇率：</label>
				<div class="controls">
				<input name="exchangerate" type="text" data-rules="{required:true, number:true}" class="input-normal control-text" placeholder="对人民币汇率"/>
				</div>
			</div>
		</div>
		</form>
      	</td>
      	<td bordercolor="5px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
      	<td style="text-align: center; vertical-align: middle;">
			<iframe id="tmccc" src="https://themoneyconverter.com/CurrencyConverter.aspx?tab=0&amp;from=CNY&amp;to=USD&amp;amount=1&amp;bg=ffffff&amp;logo=http://a2.att.hudong.com/20/96/01300000432684142370960734311.jpg" style="width:251px; height:350px; border: none;" scrolling="no" marginwidth="0" marginheight="0"></iframe>
      	</td>
      	
      </tr>
      </table>
    </div>
</body>
</html>
<!-- script begin -->
<script type="text/javascript">
	var add 		 = ${sessionScope.ERCM_USER_PSERSSION['MN00DH'].menustatus == 1};
	var edit 		 = ${sessionScope.ERCM_USER_PSERSSION['MN00DI'].menustatus == 1};
	var one_delete 	 = ${sessionScope.ERCM_USER_PSERSSION['MN00DJ'].menustatus == 1};
	var batch_delete = ${sessionScope.ERCM_USER_PSERSSION['MN00DK'].menustatus == 1};
	//权限验证
	function permissionValidate() {
		var botton_arry = new Array();
		if(add){
			botton_arry.push({
				btnCls : 'button button-primary',
				text : '<span class="icon-file icon-white"></span>新建汇率',
				listeners : {
					'click' : addFunction
				}
			});
		}
		if(batch_delete){
			botton_arry.push({
				btnCls : 'button button-danger',
				text : '<span class=" icon-trash icon-white"></span>删除汇率',
				listeners : {
	                'click' : delFunction
	              }
			});
		}
		return botton_arry;
	}

	var Grid = BUI.Grid, 
	Store = BUI.Data.Store, 
	columns = [
		{title:'货币编码',width:'15%',sortable:false,dataIndex:'currencycode'},
		{title:'货币名称',width:'15%',sortable:false,dataIndex:'currencyname'},
		{title:'货币汇率',width:'15%',sortable:false,dataIndex: 'exchangerate'},
		{title:'更新时间',width:'15%',sortable:false,dataIndex: 'oprationtime',renderer:BUI.Grid.Format.datetimeRenderer},
		{title:'操作者',width:'10%',sortable:false,dataIndex: 'oprationperson'},
		{title:'操作', width:'30%',sortable:false,renderer:function(value,obj){
			var operation = "";
			if (edit) operation += '<button class="button button-success btn-edit" onclick="editExchangeRate()"><span class="icon-edit icon-white"></span> 编 辑 </button> ';
			if (one_delete) operation += '<button class="button button-danger" onclick="deleteRow(this)"name="/exchangerate/delete" alt="'+obj.sign+'"><span class="icon-remove icon-white"></span> 删 除 </button> ';
			return operation;
		}}
	];
		
	var isAddRemote = false;
	var editing = new Grid.Plugins.DialogEditing({
		contentId	: 'content', //设置隐藏的Dialog内容
		triggerCls	: 'btn-edit', //触发显示Dialog的样式
		editor		: {
			title	: '货币汇率',
			width	: 600
	  	}
	});
        
    var store = new Store({
        url : '${ctx}/exchangerate/data',
        autoLoad : true,
        pageSize:15
      	}),
      	grid = new Grid.Grid({
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
		}),
		datepicker = new BUI.Calendar.DatePicker({
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
		$.post(getRootPath()+"/exchangerate/update",
			{
				id:data.id,
				currencyname:data.currencyname,
				currencycode:data.currencycode,
				exchangerate:data.exchangerate
			},
			function(data){
				if(data.status=="success"){
					BUI.Message.Alert("汇率数据更新成功!","success");
					return;
				} else {
					BUI.Message.Alert("汇率数据更新失败!","error");
				}
				$("#btnSearch").click();
			},
			'json')
		}else{
     		var data = editing.get('record');
			$.post(getRootPath()+"/exchangerate/save",
			{
				currencyname:data.currencyname,
				currencycode:data.currencycode,
				exchangerate:data.exchangerate
       		},
			function(data){
                if(data.status=="success"){
                    BUI.Message.Alert("汇率数据保存成功!","success");
                    return;
                } else {
                	BUI.Message.Alert("汇率数据保存失败!","error");
                }
				$("#btnSearch").click();
			},
			'json')
	     }
	});
        
	//添加数据
	function addFunction() {
		isAddRemote = false;
		var newData = {};
		editing.add(newData,''); //添加记录后，直接编辑
	}
	
	//删除选中数据
	function delFunction() {
		deleteMutilterm(grid,"${ctx}/exchangerate/batchDelete","sign");
	}
	
	//编辑数据
	function editExchangeRate() {
		isAddRemote = true;
	}
	
	//查询数据
	$("#formSearch").submit(function(){
		var obj = BUI.FormHelper.serializeToObject(this);
		obj.start = 0;
		store.load(obj);
		return false;
	}).submit();
</script>
<!-- script end -->