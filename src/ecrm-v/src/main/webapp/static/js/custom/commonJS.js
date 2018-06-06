//js获取项目根路径，如： http://localhost:8083/uimcardprj
function getRootPath(){
    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath=window.document.location.href;
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    var localhostPaht=curWwwPath.substring(0,pos);
    //获取带"/"的项目名，如：/uimcardprj
    var projectName= window.top.itempath;
    return(localhostPaht+projectName);
}
//根据部门查询员工
function loadEmployeecode(obj){
	//每次部门改变时,将之前的员工清除掉
	$("#employeecodeId").children().remove();
	$.ajax({
		type:"post",
		url:getRootPath()+"/EEmployee/findEmployee",
		dataType:"json",
		async:false,
		data:{departmentcode:obj.value},
	    success: function(data){
	    	$("#employeecodeId").append("<option value=''>请选择</option>");
	    	for(var i=0;i<data.rows.length;i++){
	    		$("#employeecodeId").append("<option value="+data.rows[i].employeecode.split('_')[1]+">"+data.rows[i].displayalias+"</option>");
	    	}
	    },
	    error:function(){
	    	BUI.Message.Alert('员工部门数据加载失败','error');
	    }
	});
}
//根据企业编码查询品牌
function loadEnterpriseBrand(){
	$.ajax({
		type:"post",
		url:getRootPath()+"/common/loadEnterpriseBrand",
		dataType:"json",
		success:function(data){
			for(var i=0;i<data.obj.length;i++){
	    		$("select[name='brandcode']").append("<option value="+data.obj[i].brandcode+">"+data.obj[i].brandname+"</option>");
	    	}
		},
	    error:function(){
	    	BUI.Message.Alert('品牌数据加载失败','error');
	    }
	});
}
//加载前台UI模板选项
function loadWebviewTemplate(enterprisecode){
	debugger;
	$.ajax({
		type:"post",
		url:getRootPath()+"/common/getWebviewTemplate?enterprisecode="+enterprisecode+"&sitetype=1",
		dataType:"json",
		success:function(data){
			var template = $("select[name='webtemplatecode']");
			var defualt = template.attr("data-defualt");
			for(var i=0;i<data.obj.length;i++){
				if(defualt==data.obj[i].webtemplatecode){
					template.append("<option value="+data.obj[i].webtemplatecode+" selected='selected'>"+data.obj[i].templatename+"</option>");
				}else{
					template.append("<option value="+data.obj[i].webtemplatecode+">"+data.obj[i].templatename+"</option>");
				}
			}
		},
		error:function(){
			BUI.Message.Alert('品牌数据加载失败','error');
		}
	});
}

//加载员工部门数据
function loadBusinessDepartment(){
	$.ajax({
		type:"post",
		url:getRootPath()+"/common/getBusinessDepartment",
		dataType:"json",
		async:false,
	    success: function(data){
	    	for(var i=0;i<data.obj.length;i++){
	    		$("#departmentId").append("<option value="+data.obj[i].departmentcode+">"+data.obj[i].department+"</option>");
	    	}
	    },
	    error:function(){
	    	BUI.Message.Alert('员工部门数据加载失败','error');
	    }
	});
	
}
//加载员工类型数据
function loadEmployeeType(){
	$.ajax({
		url:getRootPath()+"/common/getAllEmployeeType",
		type:"post",
		dataType:"json",
		async:false,
		success:function(data){
			for(var i=0;i<data.obj.length;i++){
				//用户类型为员工的不加载进来显示
				//if(data.obj[i].employeetypecode !="0002"){
					$("#employeetypeId").append("<option value="+data.obj[i].employeetypecode+">"+data.obj[i].employeetype+"</option>");
				//}
	    	}
		},
		error:function(){
			BUI.Message.Alert('员工类型数据加载失败','error');
		}
	});
}
//加载员工级别数据
function loadEmployeeLovel(){
	$.ajax({
		url:getRootPath()+"/common/getAllEmployeeLovel",
		type:"post",
		dataType:"json",
		async:false,
		success:function(data){
			for(var i=0;i<data.obj.length;i++){
	    		$("#employeelevelId").append("<option value="+data.obj[i].employeeLevelCode+">"+data.obj[i].employeeLevelName+"</option>");
	    	}
		},
		error:function(){
			BUI.Message.Alert('员工级别数据加载失败','error');
		}
	});
}

//查询支付方式
function loadPaymentType(){
	 $.ajax({
		  	type:"post",
		  	url:getRootPath()+"/common/findPaymentType",
		  	dataType:"json",
		  	async:false,
		      success: function(data){
		      	for(var i=0;i<data.result.length;i++){
		      		$("#paymenttypecodeId").append("<option value="+data.result[i].paymenttypecode+">"+data.result[i].paymenttype+"</option>");
		      	}
		      },
		      error:function(){
		      	BUI.Message.Alert('支付方式获取失败','error');
		      }
	});
}
//查询快捷支付类型
function loadThirdpartyPaymentType(){
	$.ajax({
		type:"post",
		url:getRootPath()+"/common/loadThirdpartyPaymentType",
		dataType:"json",
		async:false,
		success: function(data){
			for(var i=0;i<data.result.length;i++){
				$("select[name='thirdpartypaymenttypecode']").append("<option value="+data.result[i].thirdpartypaymenttypecode+">"+data.result[i].thirdpartypaymenttypename+"</option>");
			}
		},
		error:function(){
			BUI.Message.Alert('支付方式获取失败','error');
		}
	});
}

//查询快捷支付银行名称
function loadThirdpartyPaymentBankName(){
	$.ajax({
		type:"post",
		url:getRootPath()+"/common/loadThirdpartyPaymentBankName",
		dataType:"json",
		async:false,
		success: function(data){
			for(var i=0;i<data.result.length;i++){
				$("#thirdpartypaymentbankname").append("<option value="+data.result[i].bankcode+">"+data.result[i].bankname+"</option>");
			}
		},
		error:function(){
			BUI.Message.Alert('支付方式获取失败','error');
		}
	});
}


//查询账变类型
function loadEmployeeMoneyChangeType(){
	$.ajax({
		type:"post",
		url:getRootPath()+"/common/findEmployeeMoneyChangeType",
		dataType:"json",
		async:false,
		success: function(data){
			for(var i=0;i<data.result.length;i++){
				$("select[name='moneychangetypecode']").append("<option value="+data.result[i].moneychangetypecode+">"+data.result[i].moneychangetype+"</option>");
			}
		},
		error:function(){
			BUI.Message.Alert('账变类型获取失败','error');
		}
	});
}
//查询对应类别的账变类型
function loadActivityMoneyChangeType(){
	$.ajax({
		type:"post",
		url:getRootPath()+"/common/findEmployeeMoneyChangeType",
		data:{moneychangetypeclassify:2},
		dataType:"json",
		async:false,
		success: function(data){
			for(var i=0;i<data.result.length;i++){
				$("select[name='activityRechargeType']").append("<option value="+data.result[i].moneychangetypecode+">"+data.result[i].moneychangetype+"</option>");
			}
		},
		error:function(){
			BUI.Message.Alert('账变类型获取失败','error');
		}
	});
}
//查询品牌的银行卡信息
function loadEnterpriseBankInformation(){
	 $.ajax({
		  	type:"post",
		  	url:getRootPath()+"/common/findBrandBankInformation",
		  	dataType:"json",
		  	async:false,
		      success: function(data){
		      	for(var i=0;i<data.result.length;i++){
		      		$("#enterprisepaymentaccountId").append("<option value="+data.result[i].openningaccount+">"+data.result[i].bankname+"</option>");
		      	}
		      },
		      error:function(){
		      	BUI.Message.Alert('企业银行卡获取失败','error');
		      }
	});
}
//加载所有银行
function loadAllBank(){
	 $.ajax({
		  	type:"post",
		  	url:getRootPath()+"/common/getAllBankInfo",
		  	dataType:"json",
		  	async:false,
		      success: function(data){
		      	for(var i=0;i<data.obj.length;i++){
		      		$("select[name='bankcode']").append("<option value="+data.obj[i].bankcode+">"+data.obj[i].bankname+"</option>");
		      	}
		      },
		      error:function(){
		      	BUI.Message.Alert('银行资料获取失败','error');
		      }
	});
}
//添加用户,权限选择时调用
function loadPermission(){
	BUI.use(['bui/overlay','bui/grid','bui/data'],function(Overlay,Grid,Data){
	    var Store = Data.Store,
	     columns = [
			{ title: '<input type="checkbox" name="selectAll" class="x-grid-checkbox" onclick="selectAll(this)"/><div style="position:absolute;top:8px;left:24px;">全选</div>',width: 50,sortable: false,
					renderer:function(value,obj){
						var code = $("input[name='permissiongroupcode']").val();
						if(""!=code){
							var array = code.split(",");
							for(var i=0;i<array.length;i++){
								if(array[i]==obj.permissiongroupcode){
									return '<input type="checkbox" checked="true" placeholder="'+obj.permissiongroup+'" name="selectName" value="'+obj.permissiongroupcode+'" class="x-grid-checkbox"/>';
								}
							}
						}
						return '<input type="checkbox" placeholder="'+obj.permissiongroup+'" name="selectName" value="'+obj.permissiongroupcode+'" class="x-grid-checkbox"/>';
					}
			},
	      {title : '权限编码',dataIndex :'permissiongroupcode', width:100},
	      {title : '权限名称',dataIndex : 'permissiongroup',width:100}
	    ];
    	var store = new Store({
		  	url : getRootPath()+'/common/findPermissionGroup',
		  	autoLoad:true
			}),
			grid = new Grid.Grid({
				forceFit: true, // 列宽按百分比自适应
				columns : columns,
				store : store
		});
		var dialog = new Overlay.Dialog({
		      title:'用户权限管理',
		      width:500,
		      height:400,
		      children : [grid],
		      childContainer : '.bui-stdmod-body',
		      success:function () {
		    	var buffvalue=[];
		    	var buffname=[];
		        $("input[name='selectName']:checked").each(function(index){
		        	index++;
		        	buffvalue.push($(this).val());
		        	buffname.push($(this).attr("placeholder"));
					if($("input[name='selectName']:checked").length != index){
						buffvalue.push(",");
						buffname.push(",");
					}
		        });
		        $("input[name='permissiongroupcode']").val(buffvalue.join(""));
		        $("input[name='permissiongroupname']").val(buffname.join(""));
		        this.close();
		      }
		    });
			$("input[name='selectName']:checked").each(function(index){
			    $(this).attr({checked:false});
		    });
			dialog.show();
	});
}

//品牌游戏开放时调用
var dialog_loadgame = null;
var store_loadgame = null;
function loadGame(obj){
	BUI.use(['bui/overlay','bui/grid','bui/data'],function(Overlay,Grid,Data){
	    var Store = Data.Store,
	     columns = [
	      {title : '游戏名称',dataIndex :'gamename', width:300},
	      {title : '开关状态',dataIndex :'flag', width:100, renderer : function(value, obj) {

	    	  var fff = "<font color='green'><b>开启</b></font>";
	    	  if(value == 1) {
	    		  fff = "<font color='red'><b>已禁用</b></font>";
	    	  } else if(value == 2) {
	    		  fff = "<font color='blue'><b>维护中...</b></font>";
	    	  }
	    	  return fff;
          }},
          { title: '操作',width:100,sortable: false,renderer:function(value,obj){
          		var  html = "";
				if(obj.flag == 0){
					html+='<button class="button button-warning bin" onclick=updateBrandGame("'+obj.gamecode+'","'+obj.brandcode+'",1)><span class=" icon-remove icon-white"></span>禁用</button>';
				} else if(obj.flag == 1){
					html+='<button class="button button-success bin" onclick=updateBrandGame("'+obj.gamecode+'","'+obj.brandcode+'",0)><span class=" icon-ok icon-white"></span>开启</button>';
				} else if(obj.flag == 2){
					html+='...';
				}
                return html;
          }}
          
	    ];
	    store_loadgame = new Store({
		  	url : getRootPath()+'/brandGame/loadGame',
		  	params:{"brandcode":$(obj).attr("data-code"),"enterprisecode":$(obj).attr("data-encode")},
		  	autoLoad:true,
		}),grid = new Grid.Grid({
			forceFit: true, // 列宽按百分比自适应
			columns : columns,
			/*itemStatusFields : {
              selected : 'ischoice',
            },*/
            store : store_loadgame,
            /*plugins : [ Grid.Plugins.CheckSelection ],*/
		});
    	dialog_loadgame = new Overlay.Dialog({
		      title:'平台接入的游戏和开关',
		      width:600,
		      height:400,
		      children : [grid],
		      zIndex:'100',
		      childContainer : '.bui-stdmod-body',
		      closeAction : 'destroy', //每次关闭dialog释放
		      success:function () {
		    	  
		    	  /**
		    	var selectItem = grid.getSelection();
		    	var sign = new Array();
				BUI.each(selectItem,function(item) {
					sign.push(item["gamecode"]);
				});
		        $.ajax({
            		url:getRootPath()+'/brandGame/saveBrandGame',
            		type:"post",
            		dataType:"json",
            		data:{"brandcode":$(obj).attr("data-code"),"gamecode":sign.join(",")},
            		success:function(data){
            			if(0!=data.status){
            				BUI.Message.Alert("操作成功","success");
            			}else{
            				BUI.Message.Alert("操作失败","error");
            			}
            		}
            	});
            	***/
		        this.close();
		      }
		    });
    	dialog_loadgame.show();
	});
}

function updateBrandGame(gamecode,brandcode,flag){
	$.ajax({
		url:getRootPath()+'/brandGame/updateBrandGame',
		type:"post",
		dataType:"json",
		data:{"brandcode":brandcode,"gamecode":gamecode, "flag":flag},
		success:function(data){
			if(0!=data.status){
				BUI.Message.Alert("操作成功","success");
				store_loadgame.load();
			}else{
				BUI.Message.Alert("操作失败","error");
			}
		}
	});
}

//存取款审批记录查看
function showApproveRecord(ordernumber){
	BUI.use(['bui/overlay','bui/grid','bui/data'],function(Overlay,Grid,Data){
	    var Store = Data.Store,
	     columns = [
	      {title : '流程名称',dataIndex :'flowname', width:'20%',renderer:function(value,obj){
	    	  debugger;
	    	  if(obj.flowcode=='00000000'){
	    		  return "财务手动出款";
	    	  }else if(obj.flowcode=='00000001'){
	    		  return "系统自动出款";
	    	  }else{
	    		  return value;
	    	  }
	      }},
	      {title : '审批人',dataIndex :'assignedtoaccount', width:'10%'},
	      {title : '开始时间',dataIndex :'begintime', width:'20%',renderer:BUI.Grid.Format.datetimeRenderer},
	      {title : '结束时间',dataIndex :'endtime', width:'20%',renderer:BUI.Grid.Format.datetimeRenderer},
	      //{title : '有效处理时间(S/秒)',dataIndex :'processduration', width:128},
	      {title : '是否超时', width:68,renderer:function(value,obj){
	    	  var start = new Date(obj.begintime);
	    	  var end = new Date(obj.endtime);
	    	  var approveDate = ((end - start)/1000);
	    	  if(approveDate > obj.processduration){
	    		  return "<font style='color:red;'>超时</font>";
	    	  }
	    	  return "正常";
	      }},
	      {title:"处理结果",dataIndex:'auditresult',width:68,renderer:function(value,obj){
	    	  switch(value){
	    	  	case 1:
	    	  		return "通过";
	    		  break;
	    	  	case 2:
	    	  		return "驳回";
	    	  		break;
	    	  	case 3:
	    	  		return "拒绝";
	    	  		break;
	    	  	default:
	    	  		break;
	    	  }
	      }},
	      {title : '图片',dataIndex :'imgname', width:68,renderer:function(value,obj){
	    	  if(""!=value && null!=value && undefined!=value){
	    		  return "<a href=javascript:openImg('"+value+"')>查看图片</a>";
	    	  }else{
	    		  return "无";
	    	  }
	      }},
	      //{title : '超时处理原因',dataIndex : 'overtimereason',width:300},
	      {title : '审核说明',dataIndex : 'auditdesc',width:'20%'}
	    ];
    	var store = new Store({
		  	url : getRootPath()+'/common/queryDepositTakeApproveRecord',
		  	params:{ordernumber:ordernumber},
		  	autoLoad:true
			}),
		grid = new Grid.Grid({
			forceFit: true, // 列宽按百分比自适应
			columns : columns,
			store : store,
			emptyDataTpl:'<div class="centered"><img alt="Crying" src="../static/img/dataisnull.png"/><font style="color:red;font-size:20px;"><h2>没有找到审核记录!</h2></font></div>',
		});
		var dialog = new Overlay.Dialog({
		      title:'<b>审批记录</b>',
		      width:900,
		      height:400,
		      children : [grid],
		      childContainer : '.bui-stdmod-body',
		      success:function () {}
		    });
			dialog.show();
	});
}
//操作日志详情查看
function showOpeartionLogDetail(logcode){
	BUI.use(['bui/overlay','bui/grid','bui/data'],function(Overlay,Grid,Data){
		var Store = Data.Store,
		columns = [
		           //{title : '详细编码',dataIndex :'logdetailcode', width:'10%'},
		           {title : '操作字段',dataIndex :'fieldname', width:'15%'},
		           {title : '操作值',dataIndex :'fieldvalue', width:'60%'},
		           {title : '操作条件 ',dataIndex :'conditions', width:'25%'}
		           ];
		var store = new Store({
			url : getRootPath()+'/operatingLog/findOperatingLogDetail',
			params:{logcode:logcode},
			autoLoad:true
		}),
		grid = new Grid.Grid({
			forceFit: true, // 列宽按百分比自适应
			columns : columns,
			store : store,
			emptyDataTpl:'<div class="centered"><img alt="Crying" src="../static/img/dataisnull.png"/><font style="color:red;font-size:20px;"><h2>没有找到操作详情!</h2></font></div>',
		});
		var dialog = new Overlay.Dialog({
			title:'操作日记详情',
			width:780,
			height:400,
			children : [grid],
			childContainer : '.bui-stdmod-body',
			success:function () {}
		});
		dialog.show();
	});
}
//批量授权,权限选择时调用
/*function loadBatchPermission(parament){
	BUI.use(['bui/overlay','bui/grid','bui/data'],function(Overlay,Grid,Data){
	    var Store = Data.Store,
	     columns = [
			{ title: '<input type="checkbox" name="selectAll" class="x-grid-checkbox" onclick="selectAll(this)"/><div style="position:absolute;top:8px;left:24px;">全选</div>',width: 50,sortable: false,
					renderer:function(value,obj,index){return '<input type="checkbox" name="selectName" value="'+obj.permissiongroupcode+'" class="x-grid-checkbox"/>'}},
	      {title : '权限编码',dataIndex :'permissiongroupcode', width:100},
	      {title : '权限名称',dataIndex : 'permissiongroup',width:100}
	    ];
	  //权限管理,批量授权时调用
	    var store = new Store({
	      	url : getRootPath()+'/PermissionGroup/getAllNotExistPermissionGroup',
	      	params:{permissiongroupcode:parament},
	      	autoLoad:true
	    	}),
	    	grid = new Grid.Grid({
	    		forceFit: true, // 列宽按百分比自适应
	    		columns : columns,
	    		store : store
	    	});
		var dialog = new Overlay.Dialog({
		      title:'用户权限管理',
		      width:500,
		      height:400,
		      children : [grid],
		      childContainer : '.bui-stdmod-body',
		      success:function () {
		    	var buffvalue=[];
		        $("input[name='selectName']:checked").each(function(index){
		        	index++;
		        	buffvalue.push($(this).val());
					if($("input[name='selectName']:checked").length != index){
						buffvalue.push(",");
						
					}
		        });
		        //判断是否选择了权限项
		        if(isEmpty(buffvalue.join(""))){
		        	BUI.Message.Alert("你当前没有选择任何权限项,无法保存！！！","warning");
		        	return false;
		        }
		        //将选择的权限赋予选择的用户
		        $.ajax({
            		url:getRootPath()+'/EmployeeMPG/saveBatchPerssionGroup',
            		type:"post",
            		dataType:"json",
            		data:{employeeCode:$("input[name='employeecode']").val(),permissionGroupCode:buffvalue.join("")},
            		success:function(data){
            			if(data.status == 1){
            				BUI.Message.Alert('授权成功','success');
            			}else{
            				BUI.Message.Alert('授权失败','error');
            			}
            		}
            	});
		        this.close();
		      }
		    });
			$("input[name='selectName']:checked").each(function(index){
			    $(this).attr({checked:false});
		    });
			dialog.show();
	});
}
*/

