<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body style="padding: 10px;">
<div class="demo-content">
<!-- 搜索页 ================================================== -->
  <form id="searchForm" class="form-horizontal" style="outline: none;" method="post">
    <input name="end_hidden" type="hidden" />
    <div class="row well" style="margin-left: 0px;position: relative;">
    <div style="position: relative;display: inline-block;">
      <div style="float: left;margin-right: 96px;">
      
           <div class="control-group span14 ">
              <label class="control-label">订单时间：</label>
              <div class="bui-form-group" data-rules="{dateRange : true}">
                <input name="orderdate_begin" type="text" class="calendar calendar-time " data-tip='{"text":"起始时间"}' placeholder="起始时间" />
                <span>&nbsp;-&nbsp;</span>
                <input name="orderdate_end" type="text"   class="calendar calendar-time " data-tip='{"text":"结束时间"}' placeholder="结束时间"/>
              </div>
              <div style="margin-right: auto;margin: -30px 425px;">
                             <select onchange="changeFormatDate(this,'orderdate_begin','orderdate_end')" id="quiklychoicetime"   style="width:85px;">
                                <option value="" selected="selected">请选择</option>
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
            <div class="control-group span7" >
              <label class="control-label">订单号：</label>
                <input name="ordernumber"  type="text" data-tip='{"text":"订单号"}'  class="control-text" placeholder="订单号"/>
            </div>
            <div class="control-group span6" >
              <label class="control-label">账号：</label>
                <input name="loginaccount"  type="text" data-tip='{"text":"账号"}' class="control-text span3" placeholder="账号"/>
            </div>
            <div class="control-group span7" >
              <label class="control-label">上级账号：</label>
              <input name="parentName"  type="text" data-tip='{"text":"上级账号"}' class="control-text" placeholder="上级账号"/>
            </div>
            
            <div class="control-group span6 toggle-display"   >
              <label class="control-label">付款人：</label>
                <input name="employeepaymentname"  type="text" data-tip='{"text":"付款人"}' class="control-text span3" placeholder="付款人"/>
            </div>
             <div class="control-group span7 toggle-display" >
              <label class="control-label">付款账号：</label>
                <input name="employeepaymentaccount"  type="text" data-tip='{"text":"付款账号"}' class="control-text " placeholder="付款账号"/>
            </div>
            <div class="control-group span6 toggle-display" >
              <label class="control-label">收款人：</label>
                <input name="enterprisepaymentname"  type="text" data-tip='{"text":"收款人"}'  class="control-text span3" placeholder="收款人"/>
            </div>
             <div class="control-group span7 toggle-display" >
              <label class="control-label">收款账号：</label>
                <input name="enterprisepaymentaccount"  type="text" data-tip='{"text":"收款账号"}'  class="control-text " placeholder="收款账号"/>
            </div>
             <div class="control-group span6 toggle-display" >
              <label class="control-label">交易IP：</label>
                <input name="traceip"  type="text" data-tip='{"text":"交易IP"}' class="control-text span3" placeholder="交易IP"/>
            </div>
            <div class="control-group span7 toggle-display" >
              <label class="control-label">订单金额：</label>
                <input name="orderamount_begin"  type="text" data-tip='{"text":"金额下限"}'  class="control-text span2"  placeholder="金额下限"/>
                -
                <input name="orderamount_end"  type="text"  data-tip='{"text":"金额上限"}' class="control-text span2" placeholder="金额上限"/>
            </div>
         </div>
      	 <div style="position: absolute;right: 15px;top: 3px;">
	         	 <button id="btnSearch" type="submit"  class="button button-primary"><span class="icon-search icon-white"></span>搜 索 </button>
<!--              <a id="testId" href="">进入接口返回指定路径</a> -->
        </div>
        </div>
    </div> 
 </form>
  <div class="search-grid-container well">
    <div id="grid"></div>
  </div>
</div>
</body>
</html>
   <script type="text/javascript">
   		//修改按钮的权限
   		var update_operation = ${sessionScope.ERCM_USER_PSERSSION['MN0042'].menustatus==1};
	    
   		$("input[name='orderdate_begin']").val(getDate("begintime"));
   	    $("input[name='orderdate_end']").val(getDate("endtime"));	
   	    $("#quiklychoicetime option:eq(1)").attr("selected","selected");
   		
   		//权限验证
        function permissionValidate(){
         var array= new Array();
         //if(add_bank){
           array.push({
               btnCls : 'button button-primary payoutsign',
               text : '<span class="icon-align-justify icon-white"></span>批量出款',
               listeners : {
                 'click' : batchAutoPayment
               }
             });
           
           array.push({
               btnCls : 'button button-primary',
               text:'<span class="icon-file icon-white"></span>导出到EXCEL(当前条件)',
               handler : function(){
               	
               	searchForm.action = "${ctx}/takeDepositRecord/excelWaitPayment";
               	searchForm.submit();
               	searchForm.action = "${ctx}/takeDepositRecord/findWaitPayment";
               }
           });
           
         //}
         return array;
        }
   		var Grid = BUI.Grid,
          Store = BUI.Data.Store,
          columns = [
			{ title: '品牌',width: '5%',  dataIndex: 'brandcode'},
			{ title: '订单号',width: '15%',dataIndex:'ordernumber'},		
			{ title: '账号/姓名',width: '6%',dataIndex: 'loginaccount',renderer:function(value,obj){
				return  "<span style='color: #428BCA;font-size:16px;' title='账户' >"+value+"</span><br />"+obj.displayalias;
			}},
			{ title: '订单金额', width: '5%', sortable: false, dataIndex: 'orderamount', summary : true,  renderer:function(value,obj){
            	if(obj.ordertype == 1){
            		return "<span style='color:#5cb85c;font-size: 16px;'>"+parseFloat(value).toFixed(2)+"</span>";
            	}else{
            		return "<span style='color:#FF0000;font-size: 16px;'>"+parseFloat(value).toFixed(2)+"</span>";
            	}
            }},
	        { title: '收款人信息', width: '10%',  dataIndex: 'employeepaymentaccount', renderer:function(value,obj){
	        	return obj.employeepaymentname+"<br>"+obj.employeepaymentaccount+"<br>"+obj.employeepaymentbank;
	        }},
        	{title: '信用评级',width: '6%',  dataIndex: 'creditrating',renderer:function(value,obj){
            	switch(value) {
	    	    	case 0 : return '<div class="star-0"></div>';
	    	    	case 1 : return '<div class="star-1"></div>';
	    	    	case 2 : return '<div class="star-2"></div>';
	    	    	case 3 : return '<div class="star-3"></div>';
	    	    	case 4 : return '<div class="star-4"></div>';
	    	    	case 5 : return '<div class="star-5"></div>';
        		}
        	}},
	        { title: '订单时间',width: '8%',dataIndex: 'orderdate',sortable: true,renderer:BUI.Grid.Format.datetimeRenderer},
	        { title: '类型',width: '3%',sortable: false,renderer:function(value,obj){
            	switch(obj.ordertype){
                	case 1:
                		return "存款";
                	case 2:
                		return "取款";
                	default:
                		return "未知类型";
            	}
            }},
	        { title: '状态',width: '4%',sortable: false,renderer:function(value,obj){
	            	return "待出款";
	        }},
	        { title: '交易IP',width: '6%',dataIndex: 'traceip'},
	        { title: '备注',width: '6%',dataIndex: 'ordercomment'},
	        { title: '当前处理人',   width: '6%',    dataIndex: 'handleemployee'},
            { title: '操作',width: '20%',sortable: false,renderer:function(value,obj){
            	return '<button class="button botton-margin button-green1" onclick="batchAutoPayment(this)" ><span class="icon-screenshot icon-white"></span>快速出款</button>'+
            		   '<button class="button button-info botton-margin"   onclick="accountingManuallyPayment(\''+obj.sign+'\',\'<font style=color:#428BCA>财务手动确认出款</font>\',\'5\')" ><i class="icon-user icon-white"></i>手动出款</button>'+
            		   '<button class="button button-danger botton-margin" onclick="accountingManuallyPayment(\''+obj.sign+'\',\'<font style=color:#D50303>拒绝出款订单</font>\',\'4\')"><span class="icon-ban-circle icon-white"></span>拒绝</button>';
            }}
          ];
        var store = new Store({
            url : '${ctx}/takeDepositRecord/findWaitPayment',
            autoLoad : false,
            pageSize:15,
            sortInfo : { field : 'orderdate', direction : 'desc' },
          }),
          grid = new Grid.Grid({
            render:'#grid',
            autoRender:true,
            loadMask: true,//加载数据时显示屏蔽层
            width:'100%',
            columns : columns,
            store: store,
            plugins : [Grid.Plugins.CheckSelection, Grid.Plugins.Summary],
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
           }),fullMask = new BUI.Mask.LoadMask({
             el : '.demo-content',
             msg : '正在出款中...'
           });
        
        grid.on('cellclick',function(ev){
	          var sender = $(ev.domTarget);
	          if(!sender.hasClass('x-grid-checkbox')){ //如果不是点中勾选列，不能进行选中操作
	            return false;
	          }
	    });
        
        
        function batchAutoPayment(obj){
        	if(obj){
        		$(obj).parents("tr").addClass("bui-grid-row-selected");
        		$(obj).prop("disabled", true);
        		$(obj).html("出款中...");
        	}
        	$(".payoutsign").prop("disabled", true);
        	$(".payoutsign").html("出款中...");
        	var selectItem = grid.getSelection();
        	if(selectItem.length<=0){
        		 BUI.Message.Alert('请先选择需要出款的订单!!!','info');
     			// 处理按钮
	             	$(".payoutsign").prop("disabled", false);
	             	$(".payoutsign").html('<span class="icon-align-justify icon-white"></span>批量出款');
	             	if (obj) {
	            		$(obj).prop("disabled", false);
	            		$(obj).html('<span class="icon-screenshot icon-white"></span>快速出款</button>');
	             	}
        		return false;
        	}
    		BUI.Message.Show({
    			title : '自动出款',
    			msg : '是否确定自动出款?',
    			icon : 'question',
    			buttons : [ {
    				text : '确定',
    				elCls : 'button button-primary',
    				handler : function() {
            	var sign = new Array();
            	BUI.each(selectItem,function (item) {
            		sign.push(item.sign);
            	});
            	this.close();
            	fullMask.show();
            	$.ajax({
            		url:'${ctx}/takeDepositRecord/systemAutoPayment',
            		type:'get',
            		data:{"orders":sign.join(",")},
            		dataType:'json',
            		contentType : 'application/json;charset=utf-8',
            		success:function(data){
            			fullMask.hide();
            			if(data.message&&data.status/1==0){
            				BUI.Message.Alert(data.message,'warning');
            			}else if(!isEmptyObject(data)){
            				var buff=[];
                			for(var i=0;i<data.length;i++){
                				if(data[i].prompt){
                					buff.push("<h2 style='font-weight:700;font-size:20px;'>出款未执行:<h2><h4><div style='margin:10px 0 0 50px;'><span style='font-weight:700;font-size:19px;'>"+data[i].prompt+"</span</div><h4>")
                				}else{
                					store.load();
                					if(buff.length<=0){
                						buff.push("<h2 style='font-weight:700'>以下订单出款失败:<h2>");
                					}
    	            				buff.push("<h4><div style='margin:10px;'><span style='font-weight:700'>订单编号:</span><span>"+data[i].orderNumber+"</span></br><span style='font-weight:700'>失败原因:</span><span>"+data[i].errorMsg+"</span></div><h4>")
                				}
                			}
                			var Overlay = BUI.Overlay;
                			var dialog = new Overlay.Dialog({
                	            width:600,
                	            height:450,
                	            elCls : 'custom-dialog',
                	            bodyContent:buff.join(""),
                	            buttons : []
                	          });
                	        dialog.show();
            			} else {
            				BUI.Message.Alert('出款成功!','success');
            			}
             			// 处理按钮
    	             	$(".payoutsign").prop("disabled", false);
    	             	$(".payoutsign").html('<span class="icon-align-justify icon-white"></span>批量出款');
    	             	if (obj) {
    	            		$(obj).prop("disabled", false);
    	            		$(obj).html('<span class="icon-screenshot icon-white"></span>快速出款</button>');
    	             	}
            		}
            	});
    				}
    			}, {
    				text : '取消',
    				elCls : 'button',
    				handler : function() {
    	     			// 处理按钮
    	             	$(".payoutsign").prop("disabled", false);
    	             	$(".payoutsign").html('<span class="icon-align-justify icon-white"></span>批量出款');
    	             	if (obj) {
    	            		$(obj).prop("disabled", false);
    	            		$(obj).html('<span class="icon-screenshot icon-white"></span>快速出款</button>');
    	             	}
    					this.close();
    				}
    			} ]
    		});
        }
        
        $("#searchForm").submit(function(){
      	  var obj = BUI.FormHelper.serializeToObject(this);
            obj.start = 0;
            store.load(obj);
      	  return false;
        }).submit();
        
    </script>