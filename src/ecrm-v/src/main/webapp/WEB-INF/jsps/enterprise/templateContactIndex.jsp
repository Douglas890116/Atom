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
							<label class="control-label">企业名称：</label>
							<input name="enterprisename" type="text" class="control-text" placeholder="企业名称"/>
							<input name="sitetype" type="hidden" value="1"/>
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
		var template_create = ${sessionScope.ERCM_USER_PSERSSION['MN0090'].menustatus ==1 };
		var template_delete = ${sessionScope.ERCM_USER_PSERSSION['MN0092'].menustatus ==1 };
		var template_editor = ${sessionScope.ERCM_USER_PSERSSION['MN0093'].menustatus ==1 };
		
		(function() {
				var botton_arry = new Array();
	
				var Grid = BUI.Grid, 
					Store = BUI.Data.Store, 
					columns = [
						{title : '企业名称',width : '20%',sortable : false,dataIndex : 'enterprisename'},
						{title : '已有模板',width : '80%',sortable : true,dataIndex : 'webviews',selectable : true},
						{title : '操作',width : 120,renderer : function(value,obj) {
							var temp = ''
							/* if(template_delete){
								temp = temp+'<button type="button" class="button button-danger botton-margin" onclick=deleteTemplate(this,"'+obj.sign1+'")><span class="icon-remove icon-white"></span>删除</button>';
							} */if(template_editor){
	  								temp = temp+'<button type="button" class="button button-info botton-margin" onclick=templateContactAdd("'+obj.enterprisecode+'","'+obj.sign+'")><span class="icon-edit icon-white"></span>分配模板  </button>';
							}
							return temp;
						}}];
				var store = new Store({
					url : '${ctx}/brandTemplate/queryTemplateContactData',
					autoLoad : false,
					pageSize : 15
				}),grid = new Grid.Grid({
					render : '#grid',
					autoRender : true,
					width : '100%',
					loadMask : true,
					columns : columns,
					store : store,
					//plugins : [ Grid.Plugins.CheckSelection ],
					emptyDataTpl : '<div class="centered"><img alt="Crying" src="${statics }/img/dataisnull.png"/><font style="color:red;font-size:20px;"><h2>没有找到匹配的数据!</h2></font></div>',
					//tbar : {items : botton_arry},
					bbar : {pagingBar : true}
				}),datepicker = new BUI.Calendar.DatePicker({
	              trigger:'.calendar-time',
	              showTime:true,
	              autoRender:true
	            });
				
				/* grid.on('cellclick',function(ev){
		          var sender = $(ev.domTarget);
		          if(!sender.hasClass('x-grid-checkbox')){ //如果不是点中勾选列，不能进行选中操作
		            return false;
		          }
			    }); */
	
				 $("#searchForm").submit(function(){
			    	  var obj = BUI.FormHelper.serializeToObject(this);
			          obj.start = 0;
			          store.load(obj);
			    	  return false;
			      }).submit();
			})();
			
			function templateContactAdd(enterprisecode,sign){
				top.topManager.openPage({
					id : 'templateContact_add',
					href : '${ctx}/brandTemplate/templateContactAdd?enterprisecode='+enterprisecode+'&sign='+sign,
					title : '企业分配模板'
				});
			}
		</script>
		<!-- script end -->
		</div>
	</div>
</body>
</html>