<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>AG数据采集状态</title>
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
					
					<div style="float: left;margin-right: 126px;">
			            <div class="control-group span7">
			                <label class="control-label">代理号：</label>
			                  <input name="agentcode" type="text" data-tip='{"text":"代理号"}' class="control-text"/>
			              </div>
			            <div class="control-group span7">
			                <label class="control-label">游戏类型：</label>
			                  <input name="platformtype" type="text" data-tip='{"text":"游戏类型"}' class="control-text"/>
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
				
					var botton_arry = new Array();
					
					botton_arry.push({
			              btnCls : 'button button-primary',
			              text:'<span class="icon-file icon-white"></span>批量设置更新时间',
			              listeners : {
			                  'click' : showWhiteListWin
			              }
			          });
					
				     function showWhiteListWin(){
							
							BUI.use('bui/overlay',function(Overlay){
						        var dialog = new BUI.Overlay.Dialog({
						          title:'批量设置开始时间',
						          width:460,
						          height:238,
						          childContainer : '.bui-stdmod-body',
							      closeAction : 'destroy',
						          contentId:'content2',
						          success:function () {
						        	var ipVal = $("input[name='updatetime']").val();
						            if(ipVal == ""){
						            	alert("不能为空");
						            	return ;
						            }
						            $.ajax({
						        		url:getRootPath()+"/datahand/settag",
						        		type:"post",
						        		data:{"updatetime": ipVal },
						        		dataType:"json",
						        		success:function(data){
						        			if(1 == data.status){
						        				BUI.Message.Alert('设置成功!','success');
						        				//清空
						        				$("input[name='updatetime']").val("");
						        				store.load();
						        			}else {
						        				BUI.Message.Alert('设置失败！'+data.message,'error');
						        			}
						        		}
						        	});
						        	this.close();
						          }
							 	})
						        dialog.show();
						    });	
						}

					var Grid = BUI.Grid, Store = BUI.Data.Store, columns = [
							{title : '代理号',width : '10%',sortable : true,dataIndex : 'agentcode'},
							{title : '游戏类型',width : '10%',sortable : true,dataIndex : 'platformtype'},
							{title : '数据类型',width : '10%',sortable : true, dataIndex : 'xmltype',renderer:function(value,obj){
								if(value == "normal" ) {
									return "正常";
								} else {
									return "补单数据";
								}
							}},
							
							{title : '最后更新时间',width : '10%',sortable : true,dataIndex : 'updatetime'},
							{title : '备注',width : '60%',sortable : true,dataIndex : 'remark'}
							];

					var store = new Store({
						url : '${ctx}/datahand/tagdata',
						autoLoad : false,
						pageSize : 30,
						remoteSort: true,
						sortInfo : {
			                field : 'updatetime',
			                direction : 'asc'
			              }
					}), grid = new Grid.Grid({
						render : '#grid',
						autoRender : true,
						width : '100%',
						loadMask : true,
						columns : columns,
						store : store,
						plugins : [Grid.Plugins.CheckSelection],
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
					

				    $("#searchForm").submit(function(){
				    	  var obj = BUI.FormHelper.serializeToObject(this);
				          obj.start = 0;
				          store.load(obj);
				    	  return false;
				     }).submit();
					
				
			</script>
			<!-- script end -->
		</div>

	</div>
	
<!--     设置开始时间 -->
     <div id="content2" style="display: none;">
      <form id="form" class="form-horizontal">
      	<p style="color: red">操作说明：<br/>时区为美东时间（GMT-4）。请精确到分钟。</p>
          	<div class="control-group">
                <label class="control-label" style="width:100px;">任务开始时间：</label>
                <input class="input-normal control-text" name="updatetime" type="text" value="" style="width:167px;height:18px;"  data-rules="{required:true,maxlength:8,minlength:8}" data-tip="{text:'8位数的时间'}">
              </div>
      </form>
    </div>    
    	
</body>
</html>