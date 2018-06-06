<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>企业定制活动列表</title>
<link rel="stylesheet" href="${statics}/css/bs3/bui-min.css"/>
<link rel="stylesheet" href="${statics}/css/bs3/dpl-min.css"/>
<style type="text/css">
  /** 自定义提示样式**/
  .tip1{
    max-width: 600px;
    word-wrap: break-word;
    border:1px solid #7BC3FF;
    box-shadow: 1px 1px 10px #7BC3FF;
  }
    /**内容超出 出现滚动条 **/
    .bui-stdmod-body{
      overflow-x : hidden;
      overflow-y : auto;
    }
</style>
</head>

<body style="padding: 10px;">
	<div class="demo-content">
		<!-- 搜索页 ================================================== -->
		<form id="searchForm" class="form-horizontal" style="outline: none;"
			method="post">
			<div class="row well" style="margin-left: 0px; position: relative;">
				<div style="position: relative; display: inline-block;">
					<div style="float: left; margin-right: 96px;">
						<div class="control-group span7">
							<label class="control-label">企业名称：</label>
							<!-- 
							<input name="enterprisename" type="text" class="control-text" placeholder="企业名称"/>
							 -->
							<select name="enterprisecode">
			                    <option value="">--请选择--</option>
			                    <c:forEach var="game" varStatus="status" items="${listEnterprise}">
			                    	<option value="${game.enterprisecode }">${game.enterprisename }</option>
			                    </c:forEach>
			                </select>
			                
						</div>
						<div class="control-group span7">
							<label class="control-label">活动模板：</label>
							<!-- 
							<input name="activitytemplatename" type="text" class="control-text" placeholder="活动模板"/>
							 -->
							<select name="activitytemplatecode">
			                    <option value="">--请选择--</option>
			                    <c:forEach var="game" varStatus="status" items="${listActivityTemplate}">
			                    	<option value="${game.activitycode }">${game.name }</option>
			                    </c:forEach>
			                </select>
			                
						</div>
						<div class="control-group span7">
							<label class="control-label">活动名称：</label>
							<input name="activityname" type="text" class="control-text" placeholder="活动名称"/>
						</div>
					</div>
					<div style="position: absolute; right: 15px; top: 3px;">
							<button id="btnSearch" type="submit" class="button button-primary">
							<span class="icon-search icon-white"></span>搜索</button>
					</div>
				</div>
			</div>
		</form>
		<div class="search-grid-container well">
			<div id="grid"></div>
			<script src="${statics }/js/jquery-1.8.1.min.js"></script>
			<script src="${statics }/js/bui.js"></script>
			<script src="${statics }/js/custom/commons-min.js "></script>
			<script type="text/javascript">
				(function() {
					var botton_arry = new Array();
					botton_arry.push({
						btnCls : 'button button-primary',
						text : '<span class="icon-file icon-white"></span>定制活动',
						handler : function() {
							top.topManager.openPage({
								id : 'create_enterprise',
								href : '${ctx}/EACustomization/Add',
								title : '定制活动'
							});
						}
					});
					botton_arry.push({
						btnCls : 'button button-danger',
						text : '<span class=" icon-trash icon-white"></span>删除活动	',
						handler : function() {
							var selectItem = grid.getSelection();
							if (selectItem.length == 0) {
								BUI.Message.Alert('请选择需要删除的数据', 'info');
							} else {
								BUI.Message.Confirm('是否确定删除选中的定制模板？',function() {
									var sign = new Array();
									BUI.each(selectItem,function(item) {
												sign.push(item.sign);
									});
									$.ajax({
										type : "POST",
										url : "${ctx}/EACustomization/Delete",
										data : {
											"sign" : sign
										},
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

					var Grid = BUI.Grid, Store = BUI.Data.Store, columns = [
							{title : '企业名称',width : '8%',sortable : false,dataIndex : 'enterprisename'},
							{title : '活动模板',width : '10%',sortable : true,dataIndex : 'activitytemplatename'},
							{title : '活动名称',width : '20%',sortable : true,dataIndex : 'activityname'},
							{title : '活动PC端图片',width : '10%',sortable : true,dataIndex : 'activityimage',renderer :function(value,data){
								if(null != value && value != "")
								    return "<a href='"+value+"' target='_blank'><img src='"+value+"' style='width:100%;height:30px;'/></a>";
							}},
						    {title : '活动H5端图片',width : '10%',sortable : true,dataIndex : 'activityimagehfive',renderer :function(value,data){
						    	if(null != value && value != "")
						    	    return "<a href='"+value+"' target='_blank'><img src='"+value+"' style='width:100%;height:30px;'/></a>";
                            }},
							{title : '活动内容',width : '30%',sortable : true,dataIndex : 'activitycontent',renderer:function(value,data){
								return value?"<a class='activitycontent-tips'  title='"+value+"' >"+value.substring(0,25)+"...</a>":"";
							}},
							{title : '排序',width : '6%',sortable : false,dataIndex : 'ord'},
							{title : '操作',width : 200 ,sortable : false,renderer :function(value,data){
								var oparetion = '';
								oparetion += '<button type="button" onclick="openNewWindow(\'EACustomization_Edit\',\'${ctx}/EACustomization/Edit?ecactivitycode='+data.ecactivitycode+'\',\'编辑定制活动\');" class="button  button-success botton-margin"><span class="icon-edit icon-white"></span>编辑</button>';
								oparetion += '<button type="button" onclick="showSetting('+data.activitytemplatecode+','+data.ecactivitycode+');"  class="button  button-info botton-margin EACustomizationSetting"><span class="icon-qrcode icon-white"></span>参数设置</button>';
								return oparetion;
							}},
							];

					var store = new Store({
						url : '${ctx}/EACustomization/Data',
						autoLoad : false,
						pageSize : 15,
						remoteSort: true,
						sortInfo : {
			                field : 'ecactivitycode',
			                direction : 'desc'
			              }
					}), grid = new Grid.Grid({
						render : '#grid',
						autoRender : true,
						width : '100%',
						loadMask : true,
						columns : columns,
						store : store,
						plugins : [ Grid.Plugins.CheckSelection ],
						emptyDataTpl : '<div class="centered"><img alt="Crying" src="${statics }/img/dataisnull.png"/><font style="color:red;font-size:20px;"><h2>没有找到匹配的数据!</h2></font></div>',
						tbar : {
							items : botton_arry
						},
						bbar : {
							pagingBar : true,
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
					
					//不使用模板的，左侧显示
				      var tips = new BUI.Tooltip.Tips({
				        tip : {
				          trigger : '.activitycontent-tips', //出现此样式的元素显示tip
				          alignType : 'bottom', //默认方向
				          elCls : 'tips tips-no-icon tip1',
				          offset : 10 //距离左边的距离
				        }
				      });
				      tips.render();
				})()
				
				function showSetting(activitytemplatecode,ecactivitycode){
					$.get('${ctx}/EACustomization/Setting',{"activitytemplatecode": activitytemplatecode,"ecactivitycode":ecactivitycode},
			    		  function(html){
			    			$("#setting-panel").remove();
			    			if(html){
			    				var dialog = new BUI.Overlay.Dialog({
			    					id:'setting-dialog',
			    		            title:'参数设置',
			    		            width:560,
			    		            height:400,
			    		            mask:true,
			    		            bodyContent:html,
			    		            success:function () {
			    		            	var _self = this;
			    		            	$.ajax({
			    		    				type: "POST",
			    		    				url: "${ctx}/EACustomization/SaveSetting",
			    		    				data:$('#settingform').serialize(),
			    		    				dataType: "json",
			    		    				statusCode:{404:function(){
			    		    					BUI.Message.Alert("请求未发送成功",'error');
			    		    				}},
			    		    			    success: function(data) {
			    		    				    if(data.status == "1"){
			    		    				    	_self.close();
			    		    				    }else{
			    		    				    	BUI.Message.Alert(data.message,'warning');
			    		    				    }
			    		    			    }
			    		    			});
			    		            }
			    		          });
			    		      dialog.show();
			    			}
			    		 });
				}
			</script>
			<!-- script end -->
		</div>

	</div>
</body>
</html>