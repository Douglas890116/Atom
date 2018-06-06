<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body style="padding: 10px;">
  <div class="demo-content">
		<!-- 搜索页 ================================================== -->
		<form id="searchForm" class="form-horizontal" style="outline: none;" method="post">
            <input name="end_hidden" type="hidden" />
            <input name="brandcode" type="hidden" value="${brandcode}"/>
			<div class="row well" style="margin-left: 0px; position: relative;">
				<div style="position: relative; display: inline-block;">
					<div style="float: left;">
						<div class="control-group span7">
							<label class="control-label">品牌名称：</label>
							<input name="brandname" type="text" value="${brandname}" class="control-text" placeholder="品牌名称"/>
						</div>
					</div>
					<div style="float: left;">
						<div class="control-group span7">
							<label class="control-label">联系类型：</label>
							<select name="contacttype"  class="bui-form-field-select bui-form-field">
                                <option value="">--请选择--</option>
                                <option value="QQ">QQ</option>
                                <option value="skype">skype</option>
                                <option value="wechat">微信</option>
                                <option value="Phone">电话</option>
                            </select>
						</div>
					</div>
					<div style="float: left; margin-right: 96px;">
						<div class="control-group span7">
							<label class="control-label">是否启用：</label>
							<select name="enable" class="bui-form-field-select bui-form-field">
                                <option value="">--请选择--</option>
                                <option value="1">启用</option>
                                <option value="2">禁用</option>
                            </select>
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
			var brandContact_create_editor = ${sessionScope.ERCM_USER_PSERSSION['MN0098'].menustatus ==1 };
			var brandContact_delete = ${sessionScope.ERCM_USER_PSERSSION['MN0099'].menustatus ==1 };
			(function() {
					var botton_arry = new Array();
					if(brandContact_create_editor){
    					botton_arry.push({
    						btnCls : 'button button-primary',
    						text : '<span class="icon-file icon-white"></span>添加',
    						handler : function() {
    							top.topManager.openPage({
    								id : 'create_brandContact',
    								href : '${ctx}/brandContact/addBrandContact?brandcode='+$("input[name='brandcode']").val()+"&brandname="+$("input[name='brandname']").val(),
    								title : '添加'
    							});
    						}
    					});
					}
					if(brandContact_delete){
    					/*botton_arry.push({
    						btnCls : 'button button-danger',
    						text : '<span class=" icon-trash icon-white"></span>批量删除',
    						handler : function() {
    							var selectItem = grid.getSelection();
    							if (selectItem.length == 0) {
    								BUI.Message.Alert('请选择需要删除的数据', 'info');
    							} else {
    								BUI.Message.Confirm('是否确定删除？',function() {
    									var array = new Array();
    									BUI.each(selectItem,function(item) {
    										array.push(item.sign);
    									});
    									$.ajax({
    										type : "POST",
    										url : "${ctx}/brandContact/deleteBatchBrandContact",
    										data : {"array" : array},
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
    					});*/
    					
    					botton_arry.push({
					  	        btnCls : 'button button-danger bin',
					  	        text:'<span class=" icon-trash icon-white"></span>批量删除',
					  	        handler : function(){
					  	        	deleteMutilterm(grid,"${ctx}/brandContact/deleteBatchBrandContact","sign");
					  	     }});
    					
					}

					var Grid = BUI.Grid, 
						Store = BUI.Data.Store, 
						columns = [
							{title : '品牌名称',width : '15%',sortable : false,dataIndex : 'brandname'},
							{title : '联系方式Title',width : '15%',sortable : true,dataIndex : 'contacttitle'},
							{title : '联系方式类型',width : '15%',sortable : true,dataIndex : 'contacttype'},
							{title : '联系方式内容',width : '30%',sortable : true,dataIndex : 'content'},
							{title : '内容类型',width : '15%',sortable : true,dataIndex : 'contenttype'},
							{title : '是否启用',width : '15%',sortable : true,dataIndex : 'enable',renderer:function(value,obj){
								switch (value) {
    								case "1":
    									return "启用";
    									break;
    								case "2":
    									return "禁用";
    									break;
    								default:
    									break;
								}
							}},
							{title : '操作',width : 180,renderer : function(value,obj) {
								var temp = ''
								if(brandContact_create_editor){
    								temp = temp+'<button type="button" class="button button-success botton-margin" onclick=editorBrandContact("'+obj.contactcode+'","'+obj.sign+'")><span class="icon-edit icon-white"></span>编辑  </button>';
								}
								if(brandContact_delete){
									temp = temp+'<button type="button" class="button button-danger botton-margin" onclick=deleteBrandContact(this,"'+obj.contactcode+'")  name="brandContact/deleteBrandContact" alt="'+obj.sign+'"><span class="icon-remove icon-white"></span>删除</button>';
								}
								return temp;
							}}];
					var store = new Store({
						url : '${ctx}/brandContact/queryBrandContact',
						autoLoad : false,
						pageSize : 10
					}), grid = new Grid.Grid({
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
				function deleteBrandContact(obj,webtemplatecode,sign){
    				BUI.Message.Confirm('是否确定删除？',function() {
    					var array = new Array();
    						array.push(webtemplatecode);
    					$.ajax({
    						type : "POST",
    						url : "${ctx}/brandContact/deleteBrandContact",
    						data : {"array" : array,deleteCode:$(obj).attr("alt")},
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
				function editorBrandContact(contactcode,sign){
					top.topManager.openPage({
						id : 'update_brandContact',
						href : '${ctx}/brandContact/updBrandContact?contactcode='+contactcode+'&sign='+sign,
						title : '修改'
					});
				}
			</script>
			<!-- script end -->
		</div>
	</div>
</body>
</html>