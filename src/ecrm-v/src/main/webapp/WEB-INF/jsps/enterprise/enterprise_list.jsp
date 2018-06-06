<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body style="padding: 10px">
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
						</div>
						<div class="control-group span14">
							<label class="control-label">创建时间：</label>
							<div class="bui-form-group" data-rules="{dateRange : true}">
								<input name="registrationdate_begin" type="text" class="calendar calendar-time" placeholder="起始时间"/> 
								<span>&nbsp;-&nbsp;</span>
								<input name="registrationdate_end" type="text" class="calendar calendar-time"  placeholder="结束时间"/>
							</div>
                            <div style="margin-right: auto;margin: -30px 425px;">
                              <select onchange="changeFormatDate(this,'registrationdate_begin','registrationdate_end') "  style="width:85px;" >
                                <option value="">请选择</option>
                                <option value="1">今天</option>
                                <option value="2">昨天</option>
                                <option value="3">三天</option>
                                <option value="4">本周</option>
                                <option value="5">上周</option>
                                <option value="6">半月</option>
                                <option value="7">本月</option>
                                <option value="8">上月</option>
                                <option value="9">半年</option>
                                <option value="10">本年</option>
                              </select>
                            </div>
						</div>
					</div>
					
					<div style="position: absolute; right: 15px; top: 3px;">
							<button class="button button-primary"><span class="icon-search icon-white"></span>搜索</button>
<!-- 							<button onclick="top.topManager.reloadPage();" style="display: none;">重新加载</button> -->
					</div>
				</div>
			</div>
		</form>
<!--     公司出款方式隐藏域 -->
     <div id="content" style="display: none;">
      <form id="form" class="form-horizontal">
          <div class="control-group span10">
              <label class="control-label">出款方式：</label>
              <select name="withdralway" onchange="checkDefaultPaymentCard(this)">
                  <option value="">--请选择--</option>
                  <option value="1">系统自动出款</option>
                  <option value="2">财务手动出款</option>
              </select>
              <span style="color:red;"></span>
            </div>
           <div class="control-group span10 hide" id="upperLimitId">
              <label class="control-label">出款上限：</label>
              <input name="upperlimit" type="text" class="input-normal control-text" placeholder="最大出款金额"/>
              <span style="color:red;"></span>
            </div>
      </form>
    </div>
    
<!--     企业登录IP白名单管理 -->
     <div id="content2" style="display: none;">
      <form id="form" class="form-horizontal">
      	<p style="color: red">操作说明：<br/>设置前，请联系企业获取常用IP。打开此链接<a href="http://tool.lu/ip" target="_blank">http://tool.lu/ip</a>即可获取本人IP地址</p>
          	<div class="control-group">
                <label class="control-label" style="width:100px;">白名单IP地址：</label>
                <input class="input-normal control-text" name="ip" type="text" value="" style="width:167px;height:18px;"  data-rules="{required:true,maxlength:18}" data-tip="{text:'长度不超过18个字符'}">
              </div>
              <div class="control-group">
                <label class="control-label" style="width:100px;">IP地址描述：</label>
                <input class="input-normal control-text" name="remark" type="text" value="" style="width:167px;height:18px;" data-rules="{required:true}" data-tip="{text:'请描述该IP'}">
              </div>
      </form>
    </div>    
  
	<div class="search-grid-container well">
	<div id="grid"></div>
			<script type="text/javascript">
			var enterprise_create = ${sessionScope.ERCM_USER_PSERSSION['MN000I'].menustatus ==1 };
			var enterprise_delete = ${sessionScope.ERCM_USER_PSERSSION['MN007T'].menustatus ==1 };
			var enterprise_editor = ${sessionScope.ERCM_USER_PSERSSION['MN005V'].menustatus ==1 };
			var enterprise_apiBox = ${sessionScope.ERCM_USER_PSERSSION['MN005W'].menustatus ==1 };
			var postmoney_way = ${sessionScope.ERCM_USER_PSERSSION['MN009F'].menustatus ==1 };
			var whitelist = ${sessionScope.ERCM_USER_PSERSSION['MN00B4'].menustatus ==1 };
			var enterprise_fast = ${sessionScope.ERCM_USER_PSERSSION['MN00B5'].menustatus ==1 };
			var game_pre_fix = ${sessionScope.ERCM_USER_PSERSSION['MN00B9'].menustatus ==1 };
			var enterprise_domain = ${sessionScope.ERCM_USER_PSERSSION['MN00E3'].menustatus ==1 };
			 
			var enterprisecode =  '${sessionScope.ERCM_USER_SESSION.enterprisecode}';
	
			(function() {
					var botton_arry = new Array();
					if(enterprise_create){
    					botton_arry.push({
    						btnCls : 'button button-primary',
    						text : '<span class="icon-file icon-white"></span>创建企业',
    						handler : function() {
    							top.topManager.openPage({
    								id : 'create_enterprise',
    								href : '${ctx}/Enterprise/Add',
    								title : '创建企业'
    							});
    						}
    					});
					}
					if(enterprise_delete){
    					botton_arry.push({
    						btnCls : 'button button-danger',
    						text : '<span class=" icon-trash icon-white"></span>删除企业',
    						handler : function() {
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
    										url : "${ctx}/Enterprise/Delete",
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
					}
					
					if(enterprise_fast){
    					botton_arry.push({
    						btnCls : 'button button-primary',
    						text : '<span class="icon-file icon-white"></span>快速创建企业帐号',
    						handler : function() {
    							top.topManager.openPage({
    								id : 'create_enterprise',
    								href : '${ctx}/enterprise/toFastCreate',
    								title : '快速创建企业帐号'
    							});
    						}
    					});
					}

					var Grid = BUI.Grid, Store = BUI.Data.Store, columns = [
							{title : '企业编码',width : '6%',sortable : false,dataIndex : 'enterprisecode'},
							{title : '企业名称',width : '12%',sortable : true,dataIndex : 'enterprisename',selectable : true},
							/* {title : '企业上级编码',width : '20%',sortable : true,dataIndex : 'parententerprisecode',selectable : true
							}, */
							{title : '创建时间',width : '12%',sortable : true,dataIndex : 'registrationdate',showTip : true,renderer : BUI.Grid.Format.datetimeRenderer},
							{title : '操作',width : '70%' ,dataIndex : 'g',renderer : function(value, obj) {
									var oparetion = '';
									if(enterprise_editor){
    									oparetion += '<button type="button" onclick="openNewWindow(\'Edit_Enterprise\',\'${ctx}/Enterprise/Edit?sign='+ obj.sign
    											+ '&enterprisename='
    											+ obj.enterprisename
    											+ '\',\'编辑企业\');" class="button  button-success botton-margin"><span class="icon-edit icon-white"></span>编辑</button>';
									}
									if(postmoney_way){
										oparetion += '<button type="button" onclick="companyPaymentMethodConfig()" class="button botton-margin button-info"><span class="icon-cog icon-white"></span>出款方式</button>'
									}
									if(enterprise_apiBox){
										oparetion += '<button type="button" onclick="openNewWindow(\'API_Setting\',\'${ctx}/InputAPI/SettingAPI?sign='+ obj.sign
										+ '\',\'API证书\');"  class="button  button-warning botton-margin settingApi"><span class="icon-qrcode icon-white"></span>API证书</button>';
									}
									if(enterprise_domain) {
										oparetion += '<button type="button" onclick="openNewWindow(\'Setting_MianDomain\',\'${ctx}/registerLink/mianDomainSetting?enterprisecode='
											+ obj.enterprisecode
											+ '\',\'企业域名\');"  class="button  button-blue1 botton-margin settingApi"><span class="icon-globe icon-white"></span>企业域名</button>';	
									}
									
									if(whitelist) {
										oparetion += '<button type="button" onclick=showWhiteListWin("'+obj.enterprisecode+'")  class="button  button-blue1 botton-margin button-info"><span class="icon-edit icon-white"></span>IP白名单</button>';
									}	
									//游戏账号前缀配置
									if(game_pre_fix) {
										oparetion += '<button type="button" onclick="openNewWindow(\'API_Setting\',\'${ctx}/GamePlatformPrefix/data?sign='+ obj.sign
										+ '\',\'游戏账号前缀配置\');"  class="button  button-blue1 botton-margin "><span class="icon-edit icon-white"></span>游戏账号前缀配置</button>';
									}	
									//积分配置
									if(game_pre_fix) {
										oparetion += '<button type="button" onclick="openNewWindow(\'integral_Setting\',\'${ctx}/IntegralSetting/setting?enterprisecode='+ obj.enterprisecode
										+ '\',\'积分设置\');"  class="button  button-blue1 botton-margin "><span class="icon-edit icon-white"></span>积分设置</button>';
									}	
									if(enterprise_apiBox){
										oparetion += '<button type="button" onclick="openNewWindow(\'brandpay_Setting\',\'${ctx}/EnterpriseOperatingBrandPay/index?sign='+ obj.sign
										+ '\',\'支付回调域名\');"  class="button  button-warning botton-margin settingApi"><span class="icon-qrcode icon-white"></span>支付回调域名</button>';
									}
									
									return oparetion;
								}
							} ];

					var store = new Store({
						url : '${ctx}/Enterprise/data',
						autoLoad : false,
						pageSize : 15,
			      	    sortInfo : {
		    	          field : 'registrationdate',
		    	          direction : 'desc'
			    	    }, 
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
				})()
			</script>
			<!-- script end -->
			<script type="text/javascript">
			function showWhiteListWin(enterprisecodeVal){
				
				BUI.use('bui/overlay',function(Overlay){
			        var dialog = new BUI.Overlay.Dialog({
			          title:'企业登录IP白名单设置',
			          width:460,
			          height:238,
			          childContainer : '.bui-stdmod-body',
				      closeAction : 'destroy',
			          contentId:'content2',
			          success:function () {
			        	var ipVal = $("input[name='ip']").val();
			        	var remarkVal = $("input[name='remark']").val();
			            if(isEmpty(ipVal)){
			            	$("input[name='ip']").next().text("登录IP不能为空");
			            	return ;
			            }
			            if(isEmpty(remarkVal)){
			            	$("input[name='remark']").next().text("请输入备注，如家里/公司");
		                	return ;
			            }
			            $.ajax({
			        		url:getRootPath()+"/LoginWhiteList/savesa",
			        		type:"post",
			        		data:{"ip": ipVal, "remark": remarkVal, "enterprisecode": enterprisecodeVal},
			        		dataType:"json",
			        		success:function(data){
			        			if(1 == data.status){
			        				BUI.Message.Alert('设置成功!','success');
			        				//清空
			        				$("input[name='ip']").val("");
						        	$("input[name='remark']").val("");
			        			}else {
			        				BUI.Message.Alert('设置失败!'+data.message,'error');
			        			}
			        		}
			        	});
			        	this.close();
			          }
				 	})
			        dialog.show();
			    });	
			}
			</script>
		</div>
	</div>
	<p>提示：创建企业后，请先配置该企业的登录IP白名单，否则企业无法登录。</p>
</body>
</html>