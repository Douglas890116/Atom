<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body style="padding: 10px;">
  <div class="demo-content">
		<!-- 搜索页 ================================================== -->
		<form id="searchForm" class="form-horizontal" style="outline: none;" method="post">
            <input name="end_hidden" type="hidden" />
			<div class="row well" style="margin-left: 0px; position: relative;">
				<div style="position: relative; display: inline-block;">
					<div style="float: left; margin-right: 96px;">
						<div class="control-group span7">
							<label class="control-label">模板名称：</label>
							<input name="templatename" type="text" class="control-text" placeholder="模板名称"/>
							<input name="sitetype" type="hidden" class="control-text" value="2"/>
						</div>
					</div>
					<div style="position: absolute; right: 15px; top: 3px;">
						<button class="button button-primary"><span class="icon-search icon-white"></span>搜索</button>
					</div>
				</div>
			</div>
		</form>
	<div class="search-grid-container well">
	<div id="grid"></div>
			<script type="text/javascript">
			var template_create = ${sessionScope.ERCM_USER_PSERSSION['MN00A1'].menustatus ==1 };
			var template_delete = ${sessionScope.ERCM_USER_PSERSSION['MN00A2'].menustatus ==1 };
			var template_editor = ${sessionScope.ERCM_USER_PSERSSION['MN00A3'].menustatus ==1 };
			
			(function() {
					var botton_arry = new Array();
					if(template_create){
    					botton_arry.push({
    						btnCls : 'button button-primary',
    						text : '<span class="icon-file icon-white"></span>创建代理模板',
    						handler : function() {
    							top.topManager.openPage({
    								id : 'create_template',
    								href : '${ctx}/brandTemplate/addAgentTemplate',
    								title : '创建代理模板'
    							});
    						}
    					});
					}
					if(template_delete){
    					botton_arry.push({
					  	        btnCls : 'button button-danger bin',
					  	        text:'<span class=" icon-trash icon-white"></span>批量删除',
					  	        handler : function(){
					  	        	deleteMutilterm(grid,"${ctx}/brandTemplate/deleteBatchTemplate","sign1");
					  	     }});
					}

					var Grid = BUI.Grid, 
						Store = BUI.Data.Store, 
						columns = [
							{title : '模板编号',width : '10%',sortable : false,dataIndex : 'webtemplatecode'},              
							{title : '模板名称',width : '30%',sortable : false,dataIndex : 'templatename'},
							{title : '模板标识',width : '30%',sortable : true,dataIndex : 'sign',selectable : true},
							{title : '模板类型',width : '30%',sortable : true,dataIndex : 'templatetype'},
							{title : '操作',width : 180,renderer : function(value,obj) {
								var temp = ''
								if(template_delete){
									temp = temp+'<button type="button" class="button button-danger botton-margin" onclick=deleteTemplate(this,"'+obj.sign1+'")><span class="icon-remove icon-white"></span>删除</button>';
								}if(template_editor){
    								temp = temp+'<button type="button" class="button button-success botton-margin" onclick=editorTemplate("'+obj.webtemplatecode+'","'+obj.sign1+'")><span class="icon-edit icon-white"></span>编辑  </button>';
								}
								return temp;
							}}];
					var store = new Store({
						url : '${ctx}/brandTemplate/queryTemplateData',
						autoLoad : false,
						pageSize : 10
					}),grid = new Grid.Grid({
						render : '#grid',
						autoRender : true,
						width : '100%',
						loadMask : true,
						columns : columns,
						store : store,
						plugins : [ Grid.Plugins.CheckSelection ],
						emptyDataTpl : '<div class="centered"><img alt="Crying" src="${statics }/img/dataisnull.png"/><font style="color:red;font-size:20px;"><h2>没有找到匹配的数据!</h2></font></div>',
						tbar : {items : botton_arry},
						bbar : {pagingBar : true}
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
				function deleteTemplate(obj,webtemplatecode){
    				BUI.Message.Confirm('是否确定删除？',function() {
    					var array = new Array();
    						array.push(webtemplatecode);
    					$.ajax({
    						type : "POST",
    						url : "${ctx}/brandTemplate/deleteTemplate",
    						data : {"array" : array,"sign" : webtemplatecode},
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
				function editorTemplate(webtemplatecode,sign){
					top.topManager.openPage({
						id : 'update_template',
						href : '${ctx}/brandTemplate/updAgentTemplate?webtemplatecode='+webtemplatecode+'&sign='+sign,
						title : '修改代理模板'
					});
				}
			</script>
			<!-- script end -->
		</div>
	</div>
</body>
</html>