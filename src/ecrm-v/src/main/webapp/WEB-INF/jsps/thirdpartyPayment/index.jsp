<%@page import="com.maven.entity.EnterpriseEmployeeType.Type"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body style="padding: 10px;">
    <div class="demo-content" >
<!-- 搜索页 ================================================== -->
<form id="searchForm" class="form-horizontal" style="outline: none;" method="post">
<div class="row well" style="margin-left: 0px;position: relative;">
    <div style="position: relative;display: inline-block;">
      <div style="float: left;margin-right: 126px;">
          <div class="control-group span7">
              <label class="control-label">企业名称：</label>
                <input name="enterprisename" type="text" data-tip='{"text":"企业名称"}'  class="control-text"/>
            </div>
            <div class="control-group span7">
              <label class="control-label">支付类型：</label>
                  <select name="thirdpartypaymenttypecode">
                    <option value="">--请选择--</option>
                  </select>
            </div>
            <div class="control-group span7">
              <label class="control-label">支付类型：</label>
                  <select name="status">
                    <option value="">--请选择--</option>
                    <option value="1">启用</option>
                    <option value="2">禁用</option>
                  </select>
            </div>
        </div>  
         <div style="position: absolute;right: 15px;top: 3px;">
           <button id="btnSearch" type="submit"  class="button button-primary"> <span class="icon-search icon-white"></span>搜 索 </button>
        </div>
        </div>
    </div> 
</form>
<div class="search-grid-container well">
    <div id="grid"></div>
</div>
</div>
<!--  隐藏域 -->
     <div id="content" style="display: none;">
      <form id="form" class="form-horizontal">
        <div class="row">
           <div class="control-group span10">
              <label class="control-label">账户余额：</label>
              <input name="currentbalance" type="text" class="input-normal control-text"/>
           </div>
        </div>
      </form>
    </div>
</body>
</html>
<script type="text/javascript">
  loadThirdpartyPaymentType();
  var add  =    ${sessionScope.ERCM_USER_PSERSSION['MN007E'].menustatus == 1 };
  var update  = ${sessionScope.ERCM_USER_PSERSSION['MN00BP'].menustatus == 1 };//MN00BP 
  var batch_delete = ${sessionScope.ERCM_USER_PSERSSION['MN007G'].menustatus == 1 };
  var one_delete = ${sessionScope.ERCM_USER_PSERSSION['MN007F'].menustatus == 1 };
  var enable_disable = ${sessionScope.ERCM_USER_PSERSSION['MN007H'].menustatus == 1 };
  var _store;
  //默认设置以及回调
  function setDefaultWCardCallback(ecode){
    setDefaultWCard(ecode, function(){
      _store.load();
    });
  }
  
  function updateName(enterprisethirdpartycode) {
	  openNewWindow('update_thirdpartyPayment','${ctx}/thirdpartyPayment/update?enterprisethirdpartycode='+enterprisethirdpartycode,'编辑');
  }
 (function(){
	 
	//权限验证
     function permissionValidate(){
      var array= new Array();
      if(add){
        array.push({
              btnCls : 'button button-primary',
              text:'<span class="icon-file icon-white"></span>添加快捷支付',
              handler : function(){
                openNewWindow('create_thirdpartyPayment','${ctx}/thirdpartyPayment/add','添加快捷支付');
              }
          });
      }
      if(batch_delete){
    	  array.push({
  	        btnCls : 'button button-danger bin',
  	        text:'<span class=" icon-trash icon-white"></span>批量删除',
  	        handler : function(){
    	        	deleteMutilterm(grid,"${ctx}/thirdpartyPayment/deleteSelect","enterprisethirdpartycode");
  	     }});
      }
      /* array.push({
          btnCls : 'button button-inverse',
          text:'<span class="icon-cog icon-white"></span>设置默认出款卡',
          handler : function(){
        	  var selectItem = grid.getSelection();
        	  var lengths = selectItem.length;
        		//如果没有任何一条数据被选中,则给出提示
        		if(lengths == 0){
        			BUI.Message.Alert("请选择需要设置的项","info");
        			return false;
        		}
        		if(lengths >1){
        			BUI.Message.Alert("只允许设置一个默认出款卡","info");
        			return false;
        		}
        		$.ajax({
        			url:'${ctx}/thirdpartyPayment/configDefaultPaymentCard',
        			type:"POST",
        			data:{enterprisethirdpartycode:selectItem[0]["enterprisethirdpartycode"]},
        			dataType:"JSON",
        			success:function(data){
        				if(status.status = "success"){
        					BUI.Message.Alert("默认出款信息设置成功...","success");
        					store.load();
        				}else{
        					BUI.Message.Alert("默认出款信息设置失败!!!","error");
        				}
        			}
        		});
          }
      }); */
      return array;
     }
		
        var Grid = BUI.Grid,
          Store = BUI.Data.Store,
          columns = [
            //{ title: '企业名称',width: '20%',sortable: false,dataIndex: 'enterprisename'},
			//{ title: '支付编码',width: '15%',sortable: false,dataIndex: 'enterprisethirdpartycode'},
			{ title: '显示名称',width: '6%',sortable: false,dataIndex: 'displayname',renderer:function(value,obj){
				var html = "<font color='red'>"+value+"</font>"+"<br/>"+obj.enterprisethirdpartycode.split('_')[1];
				return html;
			}},
			{ title: '支付类型',width: '7%',sortable: false,dataIndex: 'thirdpartypaymenttypename'},
			{ title: '描述',width: '9%',sortable: false,dataIndex: 'dscription'},
			{ title: '银行卡',width: '5%',sortable: false,dataIndex: 'isbanks',renderer:function(value,obj){
				if( value) return "启用";
				else return "禁用";
			}},
			{ title: '微信',width: '5%',sortable: false,dataIndex: 'isweixin',renderer:function(value,obj){
				if( value) return "启用";
				else return "禁用";
			}},
			{ title: '支付宝',width: '5%',sortable: false,dataIndex: 'iszhifubao',renderer:function(value,obj){
				if( value) return "启用";
				else return "禁用";
			}},
			{ title: '支付限额',width: '5%',sortable: false,dataIndex: 'isweixin',renderer:function(value,obj){
				return obj.minmoney+"-"+(obj.maxmoney > 0 ? obj.maxmoney : "无限制");
			}},
			/**
			{ title: '账户余额',width: '5%',sortable: false,dataIndex: 'currentbalance',renderer:function(value){
				if(value == null || value == "") {
					return "0.0";
				} else {
					return parseFloat(value).toFixed(2);
				}
			}},
			***/
			{ title: '排序',width: '3%',sortable: false,dataIndex: 'ord'},
			{ title: '支付域名',width: '5%',sortable: false,dataIndex: 'callbackurl'},
			{ title: 'PC状态',width: '4%',sortable: false,dataIndex: 'status',renderer:function(value,obj){
				switch(value){
					case "1":
    					return "<font style='color:green;'>启用</font>";
    					break;
					case "2":
    					return "<font style='color:grey;'>禁用</font>";
    					break;
    				default:
    					break;
				}
			}},
			{ title: 'H5状态',width: '4%',sortable: false,dataIndex: 'h5status',renderer:function(value,obj){
				switch(value){
					case "1":
						return "<font style='color:green;'>启用</font>";
						break;
					case "2":
						return "<font style='color:grey;'>禁用</font>";
						break;
					default:
						break;
				}
			}},
            { title : '操作',width: '40%',sortable: false,renderer : function(value,obj){
              	 var temp = '';
              	 
              	if(update){
                  	temp+= '<button type="button" class="button  button-success botton-margin" onclick=updateName("'+obj.enterprisethirdpartycode+'")><span class="icon-edit icon-white"></span>编辑</button>';
              	 }
              	 if(update){
                  	temp+= '<button type="button" class="button  button-success botton-margin" onclick=editorThirdparty("'+obj.enterprisethirdpartycode+'")><span class="icon-edit icon-white"></span>编辑参数</button>';
              	 }
            	 if(one_delete){
                	  temp += '<button type="button" class="button button-danger" onclick="deleteRow(this)" name="/thirdpartyPayment/delete" alt="'+obj.enterprisethirdpartycode+'"><span class="icon-remove icon-white"></span>删除</button>';
            	 }
            	 if (enable_disable) {
            		if("1" == obj.status) {
            			temp+= '<button type="button" class="button botton-margin" style="background-color:#DEDBDB;" onclick=thirdpartyDisable("'+obj.enterprisethirdpartycode+'","PC")><i class="icon-remove-circle icon-white"></i>PC禁用</button>';
            		} else {
            			temp+= '<button type="button" class="button button-success botton-margin" style="background-color:#DEDBDB;" onclick=thirdpartyEnable("'+obj.enterprisethirdpartycode+'","PC")><i class="icon-ok-circle icon-white"></i><b style="color:green;">PC启用</b></button>';
            		}
            		
             		if("1" == obj.h5status) {
             			temp+= '<button type="button" class="button botton-margin" style="background-color:#DEDBDB;" onclick=thirdpartyDisable("'+obj.enterprisethirdpartycode+'","H5")><i class="icon-remove-circle icon-white"></i>H5禁用</button>';
             		} else {
             			temp+= '<button type="button" class="button button-success botton-margin" style="background-color:#DEDBDB;" onclick=thirdpartyEnable("'+obj.enterprisethirdpartycode+'","H5")><i class="icon-ok-circle icon-white"></i><b style="color:green;">H5启用</b></button>';
             		}
            	 }
            	 temp+= '<button type="button" class="button button-warning"  onclick=AdjustmentAmount("'+obj.enterprisethirdpartycode+'","'+obj.currentbalance+'")>调整余额</button>';
            	 if (obj.isdefualttakecard == 0){
               	 	temp += '<button type="button" class="button button-inverse" onclick=setDefaultWCardCallback("'+obj.enterprisethirdpartycode+'")><span class="icon-cog icon-white"></span>设为默认出款</button>';
               	 } else {
               		temp += '<button type="button" class="button" disabled="disabled" style="color:red;"><b>&nbsp;默 &nbsp;&nbsp;认 &nbsp;&nbsp;出 &nbsp;&nbsp;款&nbsp;</b></button>'
               	 }
            	 return temp;
            }}
          ];

        var store = new Store({
            url : '${ctx}/thirdpartyPayment/data',
            autoLoad : false,
            pageSize:15
          }),grid = new Grid.Grid({
            render:'#grid',
            autoRender:true,
            loadMask: true,//加载数据时显示屏蔽层
            columns : columns,
            width:'100%',
            store: store,
            plugins : [ Grid.Plugins.CheckSelection ],
            emptyDataTpl:'<div class="centered"><img alt="Crying" src="${statics }/img/dataisnull.png"/><font style="color:red;font-size:20px;"><h2>没有找到匹配的数据!</h2></font></div>',
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
        
        _store = store;
 })()
</script>