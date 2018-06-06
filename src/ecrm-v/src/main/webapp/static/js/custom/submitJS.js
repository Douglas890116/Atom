//清除查看轨迹
function clearScent(obj){
	$(obj).nextAll().remove();
	$("#visiteScent").css("visibility","hidden");
	$("input[name='parentemployeecode']").val("");
	$("#btnSearch").trigger("click");
}
//代理查看下级函数
function showDownData(employeecode,agentAccount){
	$("#visiteScent").css("visibility","visible");
	$("#visiteScent li:nth-child(2)").append("<span>~><span onclick=selectQuery(this,'"+employeecode+"') style='padding: 0 5px;border: 1px solid #E8E9EE;cursor:pointer;'>"+agentAccount+"</span></span>");
	$("input[name='parentemployeecode']").val(employeecode);
	$("#btnSearch").trigger("click");
}
//面包屑导航处理
function selectQuery(obj,employeecode){
	$(obj).parent().nextAll().remove();
	$("input[name='parentemployeecode']").val(employeecode);
	$("#btnSearch").trigger("click");
}
//用户信息
function employeeFindAgent(parentEmployeeCode){
	$("#resetId").click();
	$("input[name='parentemployeecode']").val(parentEmployeeCode);
	$("#btnSearch").click();
}
//锁定银行卡
function lockingBank(bankcode){
	$.ajax({
		type:"post",
		url:getRootPath()+"/EmployeeInformation/lockingBank",
		data:{informationcode:bankcode},
		dataType:"json",
		success:function(data){
			if(data.status == "success"){
				 BUI.Message.Alert('银行卡锁定成功','success');
				 reloadPage();
			}
			if(data.status == "failure"){
				BUI.Message.Alert('银行卡锁定失败','error');
			}
		}
	});
}
//解锁银行卡
function unlockingBank(bankcode){
	$.ajax({
		type:"post",
		url:getRootPath()+"/EmployeeInformation/unlockingBank",
		data:{informationcode:bankcode},
		dataType:"json",
		success:function(data){
			if(data.status == "success"){
				 BUI.Message.Alert('银行卡解锁成功','success');
				 reloadPage();
			}
			if(data.status == "failure"){
				BUI.Message.Alert('银行卡解锁失败','error');
			}
		}
	});
}
//点击登录账号显示用户详细信息
function openEmployeeMessage(){
	BUI.use('bui/overlay',function(Overlay){
        var dialog = new Overlay.Dialog({
            title:'<span style="color:white;font-weight:700;font-size:16px;">用户详情<span>',
            width:500,
            height:440,
            elCls :'employee-cls',
            buttons:[{
                text:'取消',
                elCls : 'button button-primary',
                handler : function(){
                  this.close();
                }
              }],
            bodyContent:$("#content").html(),
            success:function () {
              this.close();
            }
          });
        dialog.show();
   });
}
//只有当前公司有设置默认出款卡是才能选择系统自动出款项
function checkDefaultPaymentCard(obj){
	var paymentMethod = $(obj).val();
	if("1" == paymentMethod){
		$.ajax({
			url:getRootPath()+'/thirdpartyPayment/queryCompanyWhetherSetDefaultPaymentCard',
			type:"POST",
			dataType:"JSON",
			success:function(data){
				if(isEmptyObject(data)){
					$(obj).find("option[value='']").attr("selected",true);
					$("#upperLimitId").css({"display":'none'});
					BUI.Message.Show({
				          msg : '<span style="color:red;font-weight:bolder;">请设置默认的快捷出款支付出款卡!</span>',
				          icon : 'warning',
				          zIndex:1071,
				          buttons : [
				            {
				              text:'去设置',
				              elCls : 'button button-primary',
				              handler : function(){
				            	  var url = getRootPath()+'/thirdpartyPayment/index?menucode=MN005';
									top.topManager.openPage({id : 'MN0051',href:url,title:'公司快捷支付'});
				              }
				            }
				          ]
				        });
				}else{
					$("#upperLimitId").css({"display":'block'});
				}
			}
		});		
	}else{
		$("#upperLimitId").css({"display":'none'});
	}
}
//加载公司出款方式
function loadCompanyPaymentMethodConfig(){
	 $.ajax({
 		url:getRootPath()+"/Enterprise/loadCompanyPaymentMethodConfig",
 		type:"post",
 		dataType:"json",
 		async:false,
 		success:function(data){
 			if(null !=data){
 				$("input[name='upperlimit']").val(data.upperlimit);
 	 			switch (data.withdralway) {
 				case "1":
 					$("select[name='withdralway']").find("option[value='1']").attr("selected",true);
 					break;
 				case "2":
 					$("select[name='withdralway']").find("option[value='2']").attr("selected",true);
 					$("#upperLimitId").css({"display":'none'});
 					break;
 				default:
 					break;
 				}
 			}
 		}
 	});
}
//公司出款方式配置函数
function companyPaymentMethodConfig(){
	loadCompanyPaymentMethodConfig();
	BUI.use('bui/overlay',function(Overlay){
        var dialog = new BUI.Overlay.Dialog({
          title:'公司出款方式设置',
          width:280,
          height:188,
          childContainer : '.bui-stdmod-body',
	      closeAction : 'destroy',
          contentId:'content',
          success:function () {
        	var paymentMethod = $("select[name='withdralway']").val();
        	var maxMoney = $("input[name='upperlimit']").val();
            if(isEmpty(paymentMethod)){
            	$("select[name='withdralway']").next().text("出款方式不能为空");
            	return ;
            }
            $("select[name='withdralway']").next().empty();
            if(!isEmpty(maxMoney)){
            	if(!checkMoney(maxMoney)){
            		$("input[name='upperlimit']").next().text("不是有效的数字");
                	return ;
            	}
            }
            $("select[name='withdralway']").next().empty();
            $.ajax({
        		url:getRootPath()+"/Enterprise/savePaymentMethod",
        		type:"post",
        		data:{withdralway:paymentMethod,upperlimit:maxMoney},
        		dataType:"json",
        		success:function(data){
        			if("success" == data.status){
        				BUI.Message.Alert('出款方式设置成功!','success');
        			}if("failure" == data.statu){
        				BUI.Message.Alert('出款方式设置失败!','error');
        			}
        		}
        	});
        	this.close();
          }
	 	})
        dialog.show();
    });	
}
//打开代理直线会员页面
function openAllDownMember(employeecode,loginaccount){
	 top.topManager.openPage({
		  id : 'openAllDownMember'+employeecode,
		  href : getRootPath()+'/employeeAgent/agentMember?parentemployeecode='+employeecode,
		  title : '['+loginaccount+'] 直线会员'
	});
}
//打开用户直线会员页面
function openMember(employeecode,loginaccount){
	 top.topManager.openPage({
		  id : 'openAllDownMember'+employeecode,
		  href : getRootPath()+'/employeeAgent/member?parentemployeecode='+employeecode,
		  title : '['+loginaccount+'] 直线会员'
	});
}
//点击ip地址,显示所有登录过的IP信息href='http://www.ip138.com/ips138.asp?ip="+value+"&action=2'
function openIpMessage(employeecode){
	BUI.use(['bui/overlay','bui/grid','bui/data'],function(Overlay,Grid,Data){
	    var Store = Data.Store,
	     columns = [
	      {title : '登录账号',sortable: false,dataIndex :'loginaccount', width:"15%"},
	      {title : '登录IP',sortable: false,dataIndex : 'loginip',width:"20%",renderer:function(value,obj){
	    	  return "<a href=javascript:openWindow('"+value+"')>"+value+"</a>";
	      }},
	      {title : '操作系统',sortable: false,dataIndex :'opratesystem', width:"15%"},
	      {title : '浏览器版本',sortable: false,dataIndex :'browserversion', width:"25%"},
	      {title : '登录时间',sortable: false,dataIndex :'logintime', width:"25%",renderer:BUI.Grid.Format.datetimeRenderer}
	    ];
    	var store = new Store({
		  	url : getRootPath()+'/common/findEmployeeLoginLog',
		  	autoLoad:true,
		  	params:{employeecode:employeecode},
			}),
			grid = new Grid.Grid({
				forceFit: true, // 列宽按百分比自适应
				columns : columns,
				loadMask: true,//加载数据时显示屏蔽层
				store : store
			});
		var dialog = new Overlay.Dialog({
		      title:'<span style="font-size:14px;font-weight:700;"><b>用户登录信息查看</b></span>',
		      width:700,
		      height:280,
		      children : [grid],
		      buttons:[{
	                text:'取消',
	                elCls : 'button button-primary',
	                handler : function(){
	                  this.close();
	                }
	              }],
		      childContainer : '.bui-stdmod-body',
		      success:function () {
		        this.close();
		      }
		    });
			dialog.show();
	});
}
//打开查询IP地址网址
function openWindow(ip){
	window.open("http://www.ip138.com/ips138.asp?ip="+ip+"&action=2");
}
//上传LOGO
function ajaxFileUpload(url,params) {
    $.ajaxFileUpload({
          url: url+"?type="+params, //用于文件上传的服务器端请求地址
          secureuri: false, //是否需要安全协议，一般设置为false
          fileElementId: 'fileId', //文件上传域的ID
          dataType: 'json', //返回值类型 一般设置为json
          success: function (data, status){//服务器成功响应处理函数 
              if(data && "success" == data.status){
            	  $("input[name='logopath']").val(data.url);
            	  $("#imgId").attr("src",data.url).show();
            	  BUI.Message.Alert("图片上传成功",'success');
              }else{
            	  var msg = '<h2>'+data.status+'</h2>'+
                  '<p class="auxiliary-text">如连续上传失败，请及时联系客服</p>'+
                  '<p><a href="#">返回list页面</a> <a href="#">查看详情</a></p>';
            	  BUI.Message.Alert(msg,'error');
              }
          },
          error: function (data, status, e)//服务器响应失败处理函数
          {
              BUI.Message.Alert(e,'error');
          }
      });
}
//上传审核图片
function approveFileUpload(url,params,target) {
    $.ajaxFileUpload({
          url: url+"?type="+params,
          secureuri: false,
          fileElementId: 'fileId', 
          dataType: 'json',
          success: function (data, status){
              if(data && "success" == data.status){
            	  $("#"+target).val(data.url);
            	  var last = data.url.lastIndexOf("/");
            	  var imgName = data.url.substring(last+1,data.url.length);
            	  $("#imgId").html("<a href=javascript:showImg('"+data.url+"') title='点击查看图片'>"+imgName+"</a>");
            	  BUI.Message.Alert("图片上传成功",'success');
              }else{
            	  var msg = '<h2>'+data.status+'</h2>'+
                  '<p class="auxiliary-text">如连续上传失败，请及时联系客服</p>'+
                  '<p><a href="#">返回list页面</a> <a href="#">查看详情</a></p>';
            	  BUI.Message.Alert(msg,'error');
              }
          },
          error: function (data, status, e)//服务器响应失败处理函数
          {
              BUI.Message.Alert(e,'error');
          }
      });
}

//生成一个随机数
function getSystemCreateValue(maindomain){
	var random = (new Date()-0).toString(36);
	$("input[name='domainlink']").val(maindomain+"/p-"+random+".html").trigger("focus").trigger("change");
}



//跳转到修改页面
function isUpdateOperate(obj,title,urls){
	openNewWindow('update_page',getRootPath()+urls+$(obj).attr("name"),title);
}
//银行卡编辑
function bankInfoEditor(informationcode){
	openNewWindow('bankInfo_update_page',getRootPath()+"/EmployeeInformation/updateBankInfo?informationcode="+informationcode,'编辑银行卡信息');
}
//编辑存款订单
function editDepositOrders(ordernumber){
	openNewWindow('deposit_ordernumber_update_page',getRootPath()+"/takeDepositRecord/editDepositOrders?orderNumber="+ordernumber,'修改存款订单');
}
//编辑取款订单
function editTakeOrders(ordernumber){
	openNewWindow('take_ordernumber_update_page',getRootPath()+"/takeDepositRecord/editTakeOrders?orderNumber="+ordernumber,'修改取款订单');
}
//用户类型修改
function employeeTypeEditor(obj){
	openNewWindow("employee_type_editor",getRootPath()+"/employeeType/update?employeetypecode="+$(obj).attr("alt"),"用户类型修改");
}
//游戏类型
function editGameType(gamecode){
	openNewWindow("game_type_editor",getRootPath()+"/gametype/update?gamecode="+gamecode,"游戏类型修改");
}
//支付类型
function editPaymentType(paymenttypecode){
	openNewWindow("payment_type_editor",getRootPath()+"/paymenttype/update?paymenttypecode="+paymenttypecode,"支付类型修改");
}
//资金类型
function editMoneyChangeType(moneyChangeTypeCode){
	openNewWindow("money_change_type_editor",getRootPath()+"/moneyChangeType/update?moneychangetypecode="+moneyChangeTypeCode,"帐变类型修改");
}
//登录日志详情
function showLoginLogDatail(loginip){
	openNewWindow("show_login_log_datail",getRootPath()+"/LoginLog/list?loginip="+loginip,"日志详情");
}
//快捷支付银行
function editorThirdpartyBank(thirdpartyBank){
	openNewWindow("thirdparty_bank",getRootPath()+"/thirdpartyPaymentBank/update?thirdpartybank="+thirdpartyBank,"编辑支付银行");
}
function editRole(rolecode){
	openNewWindow("permission_role",getRootPath()+"/PermissionRole/edit?rolecode="+rolecode,"编辑角色");
}
//快捷支付资金调整方法
function AdjustmentAmount(code,amount){
	$("input[name='currentbalance']").val(amount);
	BUI.use('bui/overlay',function(Overlay){
        var dialog = new BUI.Overlay.Dialog({
          title:'账户资金调整',
          width:500,
          height:188,
          childContainer : '.bui-stdmod-body',
	      closeAction : 'destroy',
          contentId:'content',
          success:function () {
            $.ajax({
        		url:getRootPath()+"/thirdpartyPayment/saveCurrentbalance",
        		type:"post",
        		data:{enterprisethirdpartycode:code,currentbalance:$("input[name='currentbalance']").val()},
        		dataType:"json",
        		success:function(data){
        			if("success" == data.status){
        				BUI.Message.Alert('操作成功!',function(){
        					top.topManager.reloadPage("MN0051");
        				},'success');
        			}if("failure" == data.statu){
        				BUI.Message.Alert('操作失败!','error');
        			}
        		}
        	});
        	this.close();
          }
	 	})
        dialog.show();
    });	
}
//公司银行卡资金调整
function AdjustmentCompanyAmount(code,amount){
	$("input[name='currentbalance']").val(amount);
	BUI.use('bui/overlay',function(Overlay){
        var dialog = new BUI.Overlay.Dialog({
          title:'账户资金调整',
          width:500,
          height:188,
          childContainer : '.bui-stdmod-body',
	      closeAction : 'destroy',
          contentId:'content',
          success:function () {
            $.ajax({
        		url:getRootPath()+"/EInformation/update",
        		type:"post",
        		data:{sign:code,currentbalance:$("input[name='currentbalance']").val()},
        		dataType:"json",
        		success:function(data){
        			if(1 == data.status){
        				BUI.Message.Alert('操作成功!',function(){
        					top.topManager.reloadPage("MN000C");
        				},'success');
        			}if(0 == data.status){
        				BUI.Message.Alert('操作失败!','error');
        			}
        		}
        	});
        	this.close();
          }
	 	})
        dialog.show();
    });	
}
//快捷支付方式
function editorThirdparty(enterprisethirdpartycode){
	BUI.use(['bui/overlay','bui/grid','bui/data'],function(Overlay,Grid,Data){
	    var Store = Data.Store,
	    columns = [
	               {title : '参数ID',dataIndex :'paymentagumentcode',width:"8%"},
//	               {title : '企业第三方支付编码', dataIndex :'enterprisethirdpartycode',width:"25%"},
	               {title : '第三方支付名称',dataIndex :'dscription',width:"30%"},
	               {title : '第三方支付参数名称',dataIndex :'argumentdesc',width:"20%"},
	               {title : '参数值',dataIndex : 'agumentvalue',width:"30%",editor:{xtype:'text',rules:{required:true}}},
	               {title : '操作',width:50,renderer : function(){
	                 return '<span class="grid-command btn-edit">编辑</span>'
	               }}
	             ];
	    var editing = new Grid.Plugins.RowEditing({
	          triggerCls : 'btn-edit',
	          triggerSelected : true
	        });
    	var store = new Store({
		  	url : getRootPath()+'/thirdpartyPayment/findEnterpriseThirdpartyPaymentAgument',
		  	autoLoad:true,
		  	params:{enterprisethirdpartycode:enterprisethirdpartycode},
			}),
			grid = new Grid.Grid({
				forceFit: true, // 列宽按百分比自适应
				columns : columns,
				loadMask: true,//加载数据时显示屏蔽层Grid.Plugins.CheckSelection
				plugins : [editing],
				store : store
			});
		var dialog = new Overlay.Dialog({
		      title:'<span style="color:#2AB951;font-weight:1000;"><b>快捷支付参数列表</b></span>',
		      width:700,
		      height:280,
		      children : [grid],
		      buttons:[{
	                text:'取消',
	                elCls : 'button button-primary',
	                handler : function(){
	                  this.close();
	                  editing.cancel();
	                }
	              }],
		      childContainer : '.bui-stdmod-body',
		      success:function () {
		        this.close();
		      }
		    });
			dialog.show();
			editing.on('editorshow',function(ev){
		        var editor = editing.get('curEditor');
		        editor.set('errorAlign',{
		           points :['br','tr'] ,
		           offset: [0, 5]
		        });
	        });
			editing.on('accept',function(ev){
				var data = editing.get('record');
				$.post(getRootPath()+"/thirdpartyPayment/updateEnterpriseThirdpartyPaymentAgument",
						{paymentagumentcode:data.paymentagumentcode,agumentvalue:data.agumentvalue},
						function(data){
							if(data.status=="failure"){
								BUI.Message.Alert("参数修改失败","error");
								return;
							}
				},'json')
			});
	});
}
//设置默认出款卡
function setDefaultWCard(enterprisethirdpartycode,fc){
	debugger;
	BUI.Message.Confirm('确认要设置为默认出款卡么？',function(){
		$.ajax({
			url:getRootPath()+'/thirdpartyPayment/configDefaultPaymentCard',
			type:"POST",
			data:{'enterprisethirdpartycode':enterprisethirdpartycode},
			dataType:"JSON",
			success:function(data){
				if(status.status = "success"){
					BUI.Message.Alert("默认出款信息设置成功...","success");
					fc();
				}else{
					BUI.Message.Alert("默认出款信息设置失败!!!","error");
				}
			}
		});
	},'question');
}
function isEquivalent(a, b) {
    
	var aProps = Object.getOwnPropertyNames(a);
    var bProps = Object.getOwnPropertyNames(b);
    
    if (aProps.length != bProps.length) {
        return false;
    }
    
    for (var i = 0, len = aProps.length; i < len; i++) {
        var propName = aProps[i];
        // 如果对应的值不同，那么对象内容也不同
        if (a[propName] !== b[propName]) {
            return false;
        }
    }
    return true;
}
//单行删除函数
function deleteRow(obj){
	//获取请求的url
	var urls = $(obj).attr("name");
	BUI.Message.Show({
        title : '删除',
        msg : '确定删除?',
        icon : 'question',
        buttons : [
          {
            text:'确定',
            elCls : 'button button-primary',
            handler : function(){
            	$.ajax({
            		url:getRootPath()+urls,
            		type:"post",
            		dataType:"json",
            		data:{deleteCode:$(obj).attr("alt")},
            		success:function(data){
            			if(data.status =="success"){
            				BUI.Message.Alert("删除成功","success");
            				$(obj).parent().parent().parent().parent().remove();
            			}
            			if(data.status =="failure"){
            				BUI.Message.Alert("加密验证未通过,禁止操作","error");
            			}
            		}
            	});
            	this.close();
            }
          },
          {
            text:'取消',
            elCls : 'button',
            handler : function(){
              this.close();
            }
          }
        ]
      });
}
//单行删除函数
function deletewconfig(obj){
	//获取请求的url
	var sign = $(obj).attr("data-sign");
	var urls = $(obj).attr("data-url");
	BUI.Message.Show({
        title : '删除',
        msg : '确定删除?',
        icon : 'question',
        buttons : [
          {
            text:'确定',
            elCls : 'button button-primary',
            handler : function(){
            	$.ajax({
            		url:getRootPath()+urls,
            		type:"post",
            		dataType:"json",
            		data:{"sign":sign},
            		success:function(data){
            			if(data.status/1==1){
            				BUI.Message.Alert("删除成功","success");
            				$(obj).parent().parent().parent().parent().remove();
            			}else{
            				BUI.Message.Alert(data.message,"error");
            			}
            		}
            	});
            	this.close();
            }
          },
          {
            text:'取消',
            elCls : 'button',
            handler : function(){
              this.close();
            }
          }
        ]
      });
}
function deleteMutilterm(grid,url,filed){
	var selectItem = grid.getSelection();
	if (selectItem.length == 0) {
		BUI.Message.Alert('请选择需要删除的数据', 'info');
	} else {
		BUI.Message.Confirm('是否确定删除？',function() {
			var reason = prompt("请输入禁用原因","");
			if (reason == null || reason.trim() == "") return false;
			var sign = new Array();
			BUI.each(selectItem,function(item) {
						sign.push(item[filed]);
			});
			$.ajax({
				type : "POST",
				url : url,
				data : { "sign" : sign.join(","), "reason" : reason },
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
function disableMutilterm(grid,store,url,filed){
	var selectItem = grid.getSelection();
	if (selectItem.length == 0) {
		BUI.Message.Alert('请选择需要禁用的数据', 'info');
	} else {
		BUI.Message.Confirm('是否确定禁用 ?',function() {
			var reason = prompt("请输入禁用原因","");
			if (reason == null || reason.trim() == "") return false;
			var sign = new Array();
			BUI.each(selectItem,function(item) {
				sign.push(item[filed]);
			});
			$.ajax({
				type : "POST",
				url : url,
				data : { "sign" : sign.join(","), "reason" : reason },
				dataType : "json",
				success : function(data) {
					if (data.status == 1) {
						BUI.Message.Alert(data.message,'success');
						store.load();
					} else {
						BUI.Message.Alert(data.message,'warning');
					}
				}
			});
		}, 'question');
	}
}
function activateMutilterm(grid,store,url,filed){
	var selectItem = grid.getSelection();
	if (selectItem.length == 0) {
		BUI.Message.Alert('请选择需要启用的数据', 'info');
	} else {
		BUI.Message.Confirm('是否确定启用？',function() {
			var reason = prompt("请输入启用原因","");
			if (reason == null || reason.trim() == "") return false;
			var sign = new Array();
			BUI.each(selectItem,function(item) {
				sign.push(item[filed]);
			});
			$.ajax({
				type : "POST",
				url : url,
				data : { "sign" : sign.join(","), "reason" : reason },
				dataType : "json",
				success : function(data) {
					if (data.status == 1) {
						BUI.Message.Alert(data.message,'success');
						//grid.removeItems(selectItem);
						store.load();
					} else {
						BUI.Message.Alert(data.message,'warning');
					}
				}
			});
		}, 'question');
	}
}

function doOtherServiceMutilterm(grid,data,url,filed){
	var selectItem = grid.getSelection();
	if (selectItem.length == 0) {
		BUI.Message.Alert('请选择需要的数据', 'info');
	} else {
		BUI.Message.Confirm('是否确定？',function() {
			var reason = prompt("请输入原因","");
			if (reason == null || reason.trim() == "") return false;
			var sign = new Array();
			BUI.each(selectItem,function(item) {
				sign.push(item[filed]);
			});
			data.sign = sign.join(",");
			data.reason = reason;
			$.ajax({
				type : "POST",
				url : url,
				data : data,
				dataType : "json",
				success : function(data) {
					if (data.status == 1) {
						BUI.Message.Alert(data.message,'success');
					} else {
						BUI.Message.Alert(data.message,'warning');
					}
				}
			});
		}, 'question');
	}
}

//批量设置分红、占成、洗码
function batchSetDividendShareBonus(){
	//如果没有任何一条数据被选中,则给出提示
	if($("input[name='selectName']:checked").length == 0){
		BUI.Message.Alert("请先选择需要设置洗码的用户","info"); 
		return false;
	}
	var employeeCodeArray = new Array();
	$("input[name='selectName']:checked").each(function(index){
		employeeCodeArray.push($(this).val().split("_")[1]);
	});
	 BUI.use(['bui/overlay','bui/mask'],function(Overlay){
	        var dialog = new Overlay.Dialog({
	            title:'批量设置用户洗码',
	            width:800,
	            height:600,
	            buttons : [],
	            loader : {
	              url : getRootPath()+'/employeeAgent/bonusPage',
	              autoLoad : true, //不自动加载
	              params : {employeeCodeArray : employeeCodeArray},//附加的参数
	              lazyLoad : false, //不延迟加载
	            },
	            mask:false
	          });
	      dialog.show();
	    });
}

//批量设置洗码
function batchSetBonus(grid){
	var selectItem = grid.getSelection();
	if(selectItem.length == 0){
		BUI.Message.Alert("请先选择需要设置的用户","info"); 
		return false;
	}
	var sign = new Array();
	BUI.each(selectItem,function(item) {
		sign.push(item['employeecode']);
	});
	var form1;
	var dialog = new BUI.Overlay.Dialog({
		title:'批量设置会员洗码',
        width:630,
        height:500,
        loader : {
          url : getRootPath()+'/GCBonus/bonus',
          autoLoad : true, //不自动加载
          lazyLoad : false, //不延迟加载
          callback : function(){
              var node = dialog.get('el').find('form');//查找内部的表单元素
              form1 = new BUI.Form.HForm({
                srcNode : node,
                autoRender : true
              });
            }
        },
        success:function () {
     	   if(form1.isValid()){
     		   var params = BUI.FormHelper.serializeToObject(dialog.get('el').find('form'));
     		   params.sign=sign.join(",");
     		   $.ajax({
	       				type: "POST",
	       				url: getRootPath()+'/GCBonus/batchSaveBonus',
	       				dataType: "json",
	       				data:params,
	       				statusCode:{404:function(){BUI.Message.Alert("请求未发送成功",'error');}},
	       			    success: function(data) {
	       				    if(data.status == 1){
	       				    	BUI.Message.Alert(data.message,function(){
	       				    		grid.clearSelection()
	       				    	},'success');
	       				    }else{
	       				    	 BUI.Message.Alert(data.message,'error');
	       				    }
	       				 dialog.close();
	       			    }
	       			});
    			}
          }
      });
	  dialog.show();
}

/**
 * 存款审批
 * @param ordernumber
 */
function showDepositApprove(ordernumber){
	$.get(getRootPath()+'/takeDepositRecord/ReadyDepositApprove', {"ordernumber": ordernumber},
	  function(html){
		$("#approve-panel").remove();
		if(html){
			var dialog = new BUI.Overlay.Dialog({
				id:'approve-dialog',
	            title:'<h2>存款审核</h2>',
	            width:600,
	            height:600,
	            mask:true,
	            buttons:[],
	            zIndex:10,
	            bodyContent:html
	          });
	      dialog.show();
	      $("#approveform").data("data-dialog",dialog);
	      dialog.on("closeclick",function(e){
	    	  cacelAudit(ordernumber);
	      });
		}
	 });
}
/** 
 * 撤销审批
 */
function cacelAudit(ordernumber){
	$.ajax({
		url:getRootPath()+'/takeDepositRecord/closeAuidit',
		type:"POST",
		dataType:"JSON",
		data:{"ordernumber": ordernumber},
		success:function(data){
			//成功
			if(data.status/1==1){
				
			}
		}
	});
}
/**
 * 取款审批
 * @param ordernumber
 */
function showTakeApprove(ordernumber){
	$.get(getRootPath()+'/takeDepositRecord/ReadyTakeApprove', {"ordernumber": ordernumber},
	  function(html){
		$("#approve-panel").remove();
		if(html){
			var dialog = new BUI.Overlay.Dialog({
				id:'approve-dialog',
	            title:'<h2>取款审核</h2>',
	            width:600,
	            height:600,
	            mask:true,
	            buttons:[],
	            zIndex:10,
	            bodyContent:html
	          });
	      dialog.show();
	      $("#approveform").data("data-dialog",dialog);
	      dialog.on("closeclick",function(e){
	    	  cacelAudit(ordernumber);
	      });
		}
	 });
}

/**
 * 取款审批
 * @param ordernumber
 */
function showTakeInspect(ordernumber,employeecode,brandcode){
	$.get(getRootPath()+'/takeDepositRecord/inspect', {"ordernumber": ordernumber, "employeecode": employeecode, "brandcode": brandcode},
	  function(html){
		$("#approve-panel").remove();
		if(html){
			var dialog = new BUI.Overlay.Dialog({
				id:'approve-dialog',
	            title:'<h2>取款稽查</h2>',
	            width:700,
	            height:400,
	            mask:true,
	            buttons:[],
	            zIndex:10,
	            bodyContent:html
	          });
	      dialog.show();
		}
	 });
}
/**
 * 财务手动确认出款弹出框
 * @param ordernumber
 */
function accountingManuallyPayment(ordernumber,titles,orderMark){
	$.get(getRootPath()+'/takeDepositRecord/accountingManuallyPayment', {"ordernumber": ordernumber,"orderMark":orderMark},
		  function(html){
			$("#approve-panel").remove();
			if(html){
				var dialog = new BUI.Overlay.Dialog({
					id:'approve-dialog',
		            title:'<h2><b>'+titles+'</b></h2>',
		            width:600,
		            height:610,
		            mask:true,
		            buttons:[],
		            zIndex:10,
		            bodyContent:html
		          });
		      dialog.show();
		      $("#confirmPaymentForm").data("data-dialog",dialog);
		      dialog.on("closeclick",function(e){
		    	  cacelAudit(ordernumber);
		      });
			}
	 });
}


/**
 * 财务手动出款提交
 */
function confirmPayment(urls){
	if(isEmpty($("textarea[name='auditdesc']").val())){
		BUI.Message.Alert('请填写处理详情!','warning');
		return false;
	}
	$.ajax({
		url:getRootPath()+urls,
		type:"post",
		dataType:"json",
		data:$("#confirmPaymentForm").serialize(),
		statusCode:{404:function(){
			BUI.Message.Alert("请求未发送成功",'error');
		}},
		success:function(data){
			var dialog = $("#confirmPaymentForm").data("data-dialog");
			if(data.status=="1"){
				$("#btnSearch").trigger("click");
				dialog.close();
				BUI.Message.Alert("订单处理成功","success");
			}else{
				dialog.close();
				BUI.Message.Alert(data.message,"error");
			}
			$("#confirmPaymentForm").removeData("data-dialog");
			dialog.remove();
		}
	});
}

/**
 * 出款时拒绝
 * @param ordernumber
 */
function accountingManuallyRefusal(ordernumber){
	$.ajax({
		url:getRootPath()+"/takeDepositRecord/refusal",
		type:"POST",
		data:{ordernumber:ordernumber},
		dataType:"JSON",
		success:function(data){
			debugger;
		}
	});
}

//审批通过
function pass(){
	approveSubmit(getRootPath()+"/takeDepositRecord/approve");
}
//审批拒绝
function refusal(){
	approveSubmit(getRootPath()+"/takeDepositRecord/refusal");
}
//审批驳回
function dismiss(){
	approveSubmit(getRootPath()+"/takeDepositRecord/dismiss");
}
function approveSubmit(url){
	if(isEmpty($("textarea[name='auditdesc']").val())){
		BUI.Message.Alert('带   * 符号为必填项!','warning');
		return false;
	}
	$.ajax({
		url:url,
		type:"post",
		dataType:"json",
		data:$("#approveform").serialize(),
		statusCode:{404:function(){
			BUI.Message.Alert("请求未发送成功",'error');
		}},
		success:function(data){
			var dialog = $("#approveform").data("data-dialog");
			if(data.status/1==1){
				$("#btnSearch").trigger("click");
				dialog.hide();
				BUI.Message.Alert(data.message,"success");
			}
			if(data.status/1 == 0){
				dialog.hide();
				BUI.Message.Alert(data.message,"error");
			}
			$("#approveform").removeData("data-dialog");
			dialog.remove();
		}
	});
}

//快捷支付禁用
function thirdpartyDisable(enterprisethirdpartycode, _type){
	$.post(getRootPath()+"/thirdpartyPayment/enableDisable",{id:enterprisethirdpartycode,status:2,type:_type},function(data){
		if(data.status = "success"){
			reloadPage();
		}else{
			BUI.Message.Alert("操作失败","error");
		}
	},'json');
}
//快捷支付启用
function thirdpartyEnable(enterprisethirdpartycode, _type){
	$.post(getRootPath()+"/thirdpartyPayment/enableDisable",{id:enterprisethirdpartycode,status:1,type:_type},function(data){
		if(data.status = "success"){
			reloadPage();
		}else{
			BUI.Message.Alert("操作失败","error");
		}
	},'json');
}

//加载快捷支付类型所对应的值
function thirdpartyPaymentTypeChange(obj){
	$("#propertyValueId").empty();
	
	$("#displayname").val(  $(obj).find("option:selected").text()  );//给默认值
	
	var buff=[];
	$.post(getRootPath()+"/common/loadThirdpartyPaymentTypeSetting",{thirdpartyPaymentTypeCode:obj.value},function(data){
        for (var i = 0; i < data.result.length; i++) {
        	buff.push("<div class='control-group'>");
        	buff.push("<label class='control-label'>");
        	switch(data.result[i].argumentdesc.length){
        		case 2 :
        			buff.push(data.result[i].argumentdesc.slice(0,1)+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+data.result[i].argumentdesc.slice(1,2));
        			break;
        		case 3 :
        			buff.push(data.result[i].argumentdesc.slice(0,1)+"&nbsp;&nbsp;"+data.result[i].argumentdesc.slice(1,2)+"&nbsp;&nbsp;"+data.result[i].argumentdesc.slice(2,3));
        			break;
        		default:
        			buff.push(data.result[i].argumentdesc);
        			break;
        	}
			buff.push("</label>");
			buff.push("<div class='controls'>");
			buff.push("<input class='input-normal control-text' onblur='my_Blur_Focus(this)' onchange='my_Blur_Focus(this)' onkeydown='my_Blur_Focus(this)'  onfocus='my_Blur_Focus(this)' name='"+data.result[i].argumentfiled+"' type='text' style='width:142px;height:18px;'/>");
        	buff.push("</div>");
        	buff.push("</div>");
		}
        $("#propertyValueId").append(buff.join(""));
	},'json');
}
function my_Blur_Focus(obj){
	if(isEmpty($(obj).val())){
		$(obj).parent().append('<span class="valid-text"><span class="estate error"><span class="x-icon x-icon-mini x-icon-error">!</span><em>不能为空！</em></span></span>');
	}else{
		$(obj).siblings().remove();
	}
}

function balance(object,aguments){
	var __self = $(object).parent();
	$.ajax({
		url:getRootPath()+'/EmployeeAccout/balance',
		type:"post",
		dataType:"json",
		data:{"sign":aguments},
		success:function(data){
			__self.html(data.message);
		}
	});
}
function userShimobun(object,aguments){
	var __tr = $(object).parentsUntil("tr");
	//alert(__tr.html());
	$.ajax({
		url:getRootPath()+'/EmployeeAccout/userShimobun',
		type:"post",
		dataType:"json",
		data:{"sign":aguments},
		success:function(data){
			debugger;
			if(data.status/1==1){
				__tr.eq(3).html(data.message);
				BUI.Message.Alert("下分成功",'success');
			}else{
				BUI.Message.Alert(data.message,'warning');
			}
		}
	});
}
function getGameAccount(employeecode){
	BUI.use(['bui/overlay','bui/grid','bui/data'],function(Overlay,Grid,Data){
	    var Store = Data.Store,
	     columns = [
	      {title : '游戏名称',dataIndex :'gamename',elCls:'center', width:'30%'},
	      {title : '游戏账号',dataIndex :'gameaccount',elCls:'center', width:'30%'},
	      {title : '游戏余额',dataIndex :'balance',width:'30%',elCls:'center',renderer:function(value,obj){
	    	  //return "<a href='##' name='gamebalance' onclick='balance(this,\""+obj.sign+"\")' >点击查看</a>";
	    	  var ac = ($.isNumeric(value)?value.toFixed(2):value);
	    	  return ac > 0 ? ("<font color='red'>"+ac+"</font>" ) : ac;
	      }},
	      {title : '操作', width:120,elCls:'center',renderer:function(value,obj){
	    	  return "<button class='button button-success botton-margin' onclick='userShimobun(this,\""+obj.sign+"\")' ><i class='icon-download-alt icon-white'></i>手动下分</button>";
	      }},
	    ];
    	var store = new Store({
		  	url : getRootPath()+'/EmployeeAccout/userAccout',
		  	params:{"employeecode":employeecode},
		  	autoLoad:true,
		}),grid = new Grid.Grid({
			forceFit: true, // 列宽按百分比自适应
			columns : columns,
            store : store,
            plugins : [ Grid.Plugins.CheckSelection ],
            emptyDataTpl:'<div class="centered"><font style="color:#3071a9;"><h3>该用户未生成游戏账号</h3></font></div>',
		});
    	grid.on('cellclick',function(ev){
            var sender = $(ev.domTarget);
            if(!sender.hasClass('x-grid-checkbox')){ //如果不是点中勾选列，不能进行选中操作
              return false;
            }
          });
		var dialog = new Overlay.Dialog({
		      title:'<font style="color:#428bca;font-weight:700;">游戏账号信息（请耐心等待页面加载完成！）</font>',
		      width:600,
		      height:460,
		      children : [grid],
		      zIndex:'100',
		      childContainer : '.bui-stdmod-body',
		      closeAction : 'destroy', //每次关闭dialog释放
		      buttons:[],
		    });
			dialog.show();
			
	});
}
//打开新窗口显示上传的图片
function showImg(url){
	openNewWindow('showImg',url,'图片显示');
}
function reloadPage(){
	top.topManager.reloadPage();
}
//打开添加流程审批人操作页面
function openAddDepositApprove(obj){
	openNewWindow('addapprovepeople',getRootPath()+'/workingFlow/addDepositWorkingFlowApproveEmployee?flowcode='+obj.name,'审批人设置');
}
//打开添加流程审批人操作页面
function openAddTakeApprove(obj){
	openNewWindow('addapprovepeople',getRootPath()+'/workingFlow/addTakeWorkingFlowApproveEmployee?flowcode='+obj.name,'审批人设置');
}

//根据部门与流程flowcode查询有审批权限的与没有审批权限的
function loadData(){
	$.ajax({
		type:"post",
		url:getRootPath()+"/workingFlow/getData",
		data:{flowcode:$("input[name='flowcode']").val()},
		dataType:"json",
		async:false,
	    success: function(data){
	    	if(null !=data){
	    		$("#countGrant").text(data.NoGrant.length);
		    	$("#countNoGrant").text(data.Grant.length);
		    	$("#addElement").empty();
		    	$("#removeElement").empty();
		    	//循环已添加的
		    	for (var i = 0; i < data.NoGrant.length; i++) {
					$("#addElement").append("<li id='"+data.NoGrant[i].employeecode+"'>"+data.NoGrant[i].loginaccount+"("+data.NoGrant[i].displayalias+")</li>");
				}
		    	//循环未添加的
		    	for (var i = 0; i < data.Grant.length; i++) {
					$("#removeElement").append("<li id='"+data.Grant[i].employeecode+"'>"+data.Grant[i].loginaccount+"("+data.Grant[i].displayalias+")</li>");
				}
		    	//事件绑定
		    	//移入
		        $("#addElement").children().each(function(){
		          $(this).bind("click",function(){
		            	$(this).toggleClass("hover");
		            	$("#countSelect").text($("#addElement").children().filter(".hover").size());
		          });
		        });
		    	//移除
		        $("#removeElement").children().each(function(){
		            $(this).bind("click",function(){
		              	$(this).toggleClass("hover");
		            });
		        });
	    	}
	    },
	    error:function(){
	    	BUI.Message.Alert('员工信息获取失败','error');
	    }
	});
}
//添加元素
function addElement(parames){
	var add_element = $("#addElement").children().filter(".hover");
	var del_element = $("#removeElement").children().filter(".hover");
	switch (parames) {
		//选中加入
		case 1:
			  if(add_element.length == 0){
				  BUI.Message.Alert("当前没有选择任何项,不能添加","warning");
				  return false;
			  }
			  $("#removeElement").append(add_element);
			  //移入成功之后,清除原来的选中数量
			  $("#countSelect").text("0");
			  //移入之后,修改总数
			  $("#countGrant").text($("#countGrant").text()-add_element.length);
			  //启用保存按钮
			  $("#saveId").removeAttr("disabled");
			  break;
		//全部加入
		case 2:
			if($("#addElement").children().length == 0){
				  BUI.Message.Alert("没有需要加入的用户","warning");
				  return false;
			}
			 $("#removeElement").append($("#addElement").children());
			 //启用保存按钮
			 $("#saveId").removeAttr("disabled");
			 break;
		//选中移除
		case 3:
			if(del_element.length == 0){
				  BUI.Message.Alert("当前没有选择任何项,不能移除","warning");
				  return false;
			}
			$("#addElement").append($("#removeElement").children().filter(".hover"));
			//启用保存按钮
			 $("#saveId").removeAttr("disabled");
			break;
		//全部移除
		case 4:
			if($("#removeElement").children().length == 0){
				  BUI.Message.Alert("没有需要移除的用户","warning");
				  return false;
			}
			$("#addElement").append($("#removeElement").children()); 
			//启用保存按钮
			 $("#saveId").removeAttr("disabled");
			break;	
		default:
			break;
	}
}
//保存添加的审批人员
function saveElement(){
	//获取对应的流程节点编码
	var flowcode = $("input[name='flowcode']").val();
	var buff = [];
	$("#removeElement").children().each(function(index){
		if($("#removeElement").children().size() == (index+1)){
			buff.push($(this).attr("id"));
		}else{
			buff.push($(this).attr("id")+",");
		}
    });
	$.ajax({
		type:"post",
		url:getRootPath()+"/workingFlow/saveWorkFlowApproveEmployee",
		data:{flowcode:flowcode,employee:buff.join("")},
		dataType:"json",
		beforeSend:function(){
			
		},
		complete:function(){
			
		},
		success:function(data){
			if(null != data && "0" == data.status){
				BUI.Message.Alert('操作成功',function(){top.topManager.reloadPage();},'success');
			}else{
				BUI.Message.Alert('操作失败','error');
			}
		}
	});
}

var settingStatus = function(message , sign , status){
	BUI.Message.Confirm(message,function(){
		$.ajax({
			type: "POST",
			url: getRootPath()+"/registerLink/settingDomainStatus",
			data:{"sign":sign,"linkstatus":status},
			dataType: "json",
		    success: function(data) {
			    if(data.status == "1"){
		    		BUI.Message.Alert(data.message,function(){
		    			store.load(null);
		    		},'success');
			    }else{
			    	BUI.Message.Alert(data.message,'error');
			    }
		    }
		});
	},'question');
}

var deleteUserDBODomain = function (sign,domaincode){
	BUI.Message.Confirm("确定删除吗?",function(){
		$.ajax({
    		url:getRootPath()+'/registerLink/deleteUserDBODomain',
    		type:"post",
    		dataType:"json",
    		data:{"domaincode":domaincode, "sign":sign},
    		success:function(data){
    			 if(data.status == "1"){
 		    		BUI.Message.Alert(data.message,function(){
 		    			store.load(null);
 		    		},'success');
 			    }else{
 			    	BUI.Message.Alert(data.message,'error');
 			    }
    	}});
	},'question');
}

/*function changeDate(obj,begin_Date,end_Date){
	var value = $(obj).val();
	var myDate = new Date();
	var date = new Date(myDate.getTime());
	Y = date.getFullYear() + '-';
	M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
	D = (date.getDate() < 10 ? '0'+date.getDate() : date.getDate()) +' ';
	h = (date.getHours() < 10 ? '0'+date.getHours() : date.getHours()) + ':';
	m = (date.getMinutes() < 10 ? '0'+date.getMinutes() : date.getMinutes()) + ':';
	s = (date.getSeconds() < 10 ? '0'+date.getSeconds() : date.getSeconds());
	switch(value){
		case "1":
			$("input[name="+begin_Date+"]").val(Y+M+D+"00:00:00");
			$("input[name="+end_Date+"]").val(Y+M+D+"23:59:59");
			$("input[name='end_hidden']").val(Y+M+D+"23:59:59");
			$("input[name='last_hidden']").val(Y+M+D+"23:59:59");
			break;
		case "2":
			var datetime = getComputeDate(86400000*3);
			$("input[name="+begin_Date+"]").val(datetime);
			$("input[name="+end_Date+"]").val(Y+M+D+h+m+s);
			$("input[name='end_hidden']").val(Y+M+D+h+m+s);
			$("input[name='last_hidden']").val(Y+M+D+h+m+s);
			break;
		case "3":
			var datetime = getComputeDate(86400000*7);
			$("input[name="+begin_Date+"]").val(datetime);
			$("input[name="+end_Date+"]").val(Y+M+D+h+m+s);
			$("input[name='end_hidden']").val(Y+M+D+h+m+s);
			$("input[name='last_hidden']").val(Y+M+D+h+m+s);
			break;
		case "4":
			var datetime = getComputeDate(86400000*30);
			$("input[name="+begin_Date+"]").val(datetime);
			$("input[name="+end_Date+"]").val(Y+M+D+h+m+s);
			$("input[name='end_hidden']").val(Y+M+D+h+m+s);
			$("input[name='last_hidden']").val(Y+M+D+h+m+s);
			break;
		default:
			break;
	}
}*/



 var now = new Date();                    //当前日期
 var nowDayOfWeek = now.getDay()-1;         //今天本周的第几天
 var nowDay = now.getDate();              //当前日
 var nowMonth = now.getMonth();           //当前月
 var nowYear = now.getYear();             //当前年
 nowYear += (nowYear < 2000) ? 1900 : 0;  //

 var lastMonthDate = new Date();  //上月日期
 lastMonthDate.setDate(1);
 lastMonthDate.setMonth(lastMonthDate.getMonth()-1);
 var lastYear = lastMonthDate.getYear();
 var lastMonth = lastMonthDate.getMonth();
 
 var nowYear = now.getYear();             //当前年
 nowYear += (nowYear < 2000) ? 1900 : 0;  //
 
 function getYMD(date){
	 
	    var myyear = date.getFullYear();
	    var mymonth = date.getMonth()+1;
	    var myweekday = date.getDate();
	    if(mymonth < 10){
	        mymonth = "0" + mymonth;
	    }
	    if(myweekday < 10){
	        myweekday = "0" + myweekday;
	    }
	    
	    return myyear+"-"+mymonth + "-" + myweekday;
	    
 }

 //获得某月的天数
 function getMonthDays(myMonth){
     var monthStartDate = new Date(nowYear, myMonth, 1);
     var monthEndDate = new Date(nowYear, myMonth + 1, 1);
     var   days   =   (monthEndDate   -   monthStartDate)/(1000   *   60   *   60   *   24);
     return   days;
 }

//格式化日期：yyyy-MM-dd
function changeFormatDate(obj,begin_Date,end_Date) {
	var getNowDate = new Date(nowYear, nowMonth, nowDay);//现在时间
	var getNowDate =  getYMD(getNowDate);
	var value = $(obj).val();
	switch(value){
	case "":
		$("input[name="+begin_Date+"]").val("");
		$("input[name="+end_Date+"]").val("");
		break;
	case "1":
		var getYesterdayDate = new Date(nowYear, nowMonth, nowDay);
		var getYesterdayDate =  getYMD(getYesterdayDate);
		$("input[name="+begin_Date+"]").val(getYesterdayDate+" 00:00:00");
		$("input[name="+end_Date+"]").val(getYesterdayDate+" 23:59:59");

		break;
		
	case "2":
		var getYesterdayDate = new Date(nowYear, nowMonth, nowDay-1);
		var getYesterdayDate =  getYMD(getYesterdayDate);
		$("input[name="+begin_Date+"]").val(getYesterdayDate+" 00:00:00");
		$("input[name="+end_Date+"]").val(getYesterdayDate+" 23:59:59");
		break;
	case "3":
		var getYesterdayDate = new Date(nowYear, nowMonth, nowDay-3);
		var getYesterdayDate =  getYMD(getYesterdayDate);
		$("input[name="+begin_Date+"]").val(getYesterdayDate+" 00:00:00");
		$("input[name="+end_Date+"]").val(getNowDate+" 23:59:59");
		break;
	case "4":
		 //获得本周的开始日期
		 var getWeekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek);
		 var getWeekStartDate =  getYMD(getWeekStartDate);
		 //获得本周的结束日期
		 var getWeekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek));
		 var getWeekEndDate =  getYMD(getWeekEndDate);
		 $("input[name="+begin_Date+"]").val(getWeekStartDate+" 00:00:00");
		 $("input[name="+end_Date+"]").val(getWeekEndDate+" 23:59:59");
		break;
	case "5":
	    //获得上周的开始日期
	    var getUpWeekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek -7);
	    var getUpWeekStartDate =  getYMD(getUpWeekStartDate);
	    //获得上周的结束日期
	    var getUpWeekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek - 7));
	    var getUpWeekEndDate =  getYMD(getUpWeekEndDate);
		$("input[name="+begin_Date+"]").val(getUpWeekStartDate+" 00:00:00");
		$("input[name="+end_Date+"]").val(getUpWeekEndDate+" 23:59:59");
		break;
	case "6":
	    //获得半月的开始日期
	    var getMonthStartDate = new Date(nowYear, nowMonth,1);
	    var getMonthStartDate =  getYMD(getMonthStartDate);
	    var getMonthEndDate = new Date(nowYear, nowMonth,15);
	    var getMonthEndDate =  getYMD(getMonthEndDate);
		$("input[name="+begin_Date+"]").val(getMonthStartDate+" 00:00:00");
		$("input[name="+end_Date+"]").val(getMonthEndDate+" 23:59:59");
		break;
	case "7":
	    //获得本月的开始日期
	    var getMonthStartDate = new Date(nowYear, nowMonth, 1);
	    var getMonthStartDate =  getYMD(getMonthStartDate);
	    //获得本月的结束日期
	    var getMonthEndDate = new Date(nowYear, nowMonth, getMonthDays(nowMonth));
	    var getMonthEndDate =  getYMD(getMonthEndDate);
		$("input[name="+begin_Date+"]").val(getMonthStartDate+" 00:00:00");
		$("input[name="+end_Date+"]").val(getMonthEndDate+" 23:59:59");
		break;
	case "8":
	    //获得上月开始时间
	    var getLastMonthStartDate = new Date(nowYear, lastMonth, 1);
	    var getLastMonthStartDate = getYMD(getLastMonthStartDate);
	    //获得上月结束时间
	    var getLastMonthEndDate = new Date(nowYear, lastMonth, getMonthDays(lastMonth));
	    var getLastMonthEndDate = getYMD(getLastMonthEndDate);
		$("input[name="+begin_Date+"]").val(getLastMonthStartDate+" 00:00:00");
		$("input[name="+end_Date+"]").val(getLastMonthEndDate+" 23:59:59");
		break;
	case "9":
		var getLastMonthStartDate = new Date(nowYear, 0);
		var getLastMonthStartDate = getYMD(getLastMonthStartDate);
		var getLastMonthEndDate = new Date(nowYear, 5, 30);//上半年，6余额30
		var getLastMonthEndDate = getYMD(getLastMonthEndDate);
		$("input[name="+begin_Date+"]").val(getLastMonthStartDate+" 00:00:00");
		$("input[name="+end_Date+"]").val(getLastMonthEndDate+" 23:59:59");
		break;
	case "10":
		var currentYearFirstDate = new Date(nowYear, 0, 1);
		var currentYearFirstDate = getYMD(currentYearFirstDate);
		var priorYearLastDay = new Date(nowYear, 11, 31);//年底
		var priorYearLastDay = getYMD(priorYearLastDay);
		$("input[name="+begin_Date+"]").val(currentYearFirstDate+" 00:00:00");
		$("input[name="+end_Date+"]").val(priorYearLastDay+" 23:59:59");
		break;
	default:
		break;

}
    
    
  //  return (myyear+"-"+mymonth + "-" + myweekday+"00:00:00");
}

function getComputeDate(times){
	var myDates = new Date();
	var thisTime = myDates.getTime();
	var afterTime = (thisTime-(times))
	var dates = new Date(afterTime);
	Y1 = dates.getFullYear() + '-';
	M1 = (dates.getMonth()+1 < 10 ? '0'+(dates.getMonth()+1) : dates.getMonth()+1) + '-';
	D1 = (dates.getDate() < 10 ? '0'+dates.getDate() : dates.getDate()) +' ';
	//h1 = (dates.getHours() < 10 ? '0'+dates.getHours() : dates.getHours()) + ':';
	//m1 = (dates.getMinutes() < 10 ? '0'+dates.getMinutes() : dates.getMinutes()) + ':';
	//s1 = (dates.getSeconds() < 10 ? '0'+dates.getSeconds() : dates.getSeconds());	
	h1 = ("23")+":"+("59")+":"+("59");
	return Y1+M1+D1+h1;
}
//获取年月日
function getYearMonthDate(myDate){
	var date = new Date(myDate);
	Y = date.getFullYear() + '-';
	M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
	D = (date.getDate() < 10 ? '0'+date.getDate() : date.getDate());
	return (Y+M+D);
}
//默认起始时间，结束时间
function getDate(obj){
	var d = new Date();
	var vYear = d.getFullYear();
	var vMon = d.getMonth() + 1;
	var vDay = d.getDate();
	var vDay1 = d.getDate();
	var h = d.getHours(); 
	var m = d.getMinutes(); 
	var se = d.getSeconds(); 
	if(obj=="begintime"){
		begintime = vYear+"-"+(vMon<10 ? "0" + vMon : vMon)+"-"+(vDay<10 ? "0"+ vDay : vDay)+" "+("00")+":"+("00")+":"+("00");
		return begintime;
	}else if(obj="endtime"){
		endtime = vYear+"-"+(vMon<10 ? "0" + vMon : vMon)+"-"+(vDay<10 ? "0"+ vDay : vDay)+" "+("23")+":"+("59")+":"+("59");
		return endtime;
	}
	
}

//查询条件重置
function resetCondition(){
	/*$("#searchForm").find("input:not(:hidden)").val("");
	  $("#searchForm").find("select").val("");*/
	form.clearFields();
}
//审批详情图片查看
function openImg(url){
	window.open(url);
}
//校验注册账号格式
function checkAccount(params){
	var reg = /^[0-9a-zA-z]{8,20}$/;
	return reg.test(params);
}
//校验登录密码格式
function checkPassword(params){
	var reg = /^[0-9a-zA-Z]{8,20}$/;
	return reg.test(params);
}
//校验手机号码格式
function checkPhone(params){
	var reg = /^0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}$/;
	return reg.test(params);
}
//qq验证
function checkQQ(params){
	var reg =/^[0-9]*$/;
	return reg.test(params);
}
//校验邮件格式
function checkEmail(params){
	var reg =/^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/;
	return reg.test(params);
}
//银行卡号格式
function checkBankCard(params){
	var reg =/^\d{16,19}$/;
	return reg.test(params);
}
//金额(包含金额)
function checkMoney(params){
	var reg = /^(\d+)(.\d+)?$/;
	return reg.test(params);
}
function buiMessageShow(msg,time){
	BUI.Message.Show({
        msg : msg,
        icon : 'question',
        buttons : [],
        autoHide : true,
        autoHideDelay : time
      });
}
//判断是否为空值
function isEmpty(parame){
	if(!parame ||'undefined' == parame){
		return true;
	}
	return false;
}
//判断是否为空数组[]
function isEmptyObject(obj){
  for (var code in obj){
    return false;
  }
  return true;
}