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
              <div class="control-group span13">
              <label class="control-label">订单时间：</label>
              <div class="bui-form-group" data-rules="{dateRange : true}">
                <input name="orderdate_begin" type="text" class="calendar calendar-time" placeholder="起始时间" />
                -
                <input name="orderdate_end" type="text"   class="calendar calendar-time" placeholder="结束时间" />
              </div>
              <div style="margin-right: auto;margin: -30px 415px;">
                <select onchange="changeFormatDate(this,'orderdate_begin','orderdate_end')"  id="quiklychoicetime"  style="width:85px;">
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
            <div class="control-group span6" >
              <label class="control-label">订单号：</label>
                <input name="ordernumber"  type="text" data-tip='{"text":"订单号"}'  class="control-text" placeholder="订单号"/>
            </div>
            <div class="control-group span5" >
              <label class="control-label">账号：</label>
                <input name="loginaccount"  type="text" data-tip='{"text":"账号"}' class="control-text span3" placeholder="账号"/>
            </div>
            <div class="control-group span5" >
              <label class="control-label">付款人：</label>
                <input name="employeepaymentname"  type="text" data-tip='{"text":"付款人"}' class="control-text span3" placeholder="付款人"/>
            </div>
            <div class="control-group span5" >
              <label class="control-label">审核结果：</label>
                <select name="orderstatus" style="width:120px;">
                  <option value="">请选择</option>
                  <option value="2">审核通过</option>
                  <option value="4">审核拒绝</option>
                </select>
            </div>
            
            <div class="control-group span6" >
              <label class="control-label">支付方式：</label>
                <select name="paymenttypecode" style="width:130px;">
                	<option value="">请选择</option>
                	<c:forEach var="item" items="${listPaymentType }" varStatus="i">
                		<option value="${item.paymenttypecode }">${item.paymenttype }</option>
                	</c:forEach>
                </select>
            </div>
            <div class="control-group span7" >
              <label class="control-label">第三方支付渠道：</label>
                <select name="enterprisepaymentbank" style="width:145px;">
                	<option value="">请选择</option>
                	<c:forEach var="item" items="${listThirdpartyPaymentType }" varStatus="i">
                		<option value="${item.thirdpartypaymenttypecode }">${item.thirdpartypaymenttypename }</option>
                	</c:forEach>
                </select>
            </div>
            <div class="control-group span6" >
              <label class="control-label">交易银行：</label>
                <select id="thirdpartypaymentbankname" name="employeepaymentbank" style="width:145px;">
                	<option value="">请选择</option>
                </select>
            </div>
            <div class="control-group span6" >
              <label class="control-label">上级账号：</label>
              <input name="parentName"  type="text" data-tip='{"text":"上级账号"}' class="control-text" placeholder="上级账号"/>
            </div>
            
             <div class="control-group span7 toggle-display" >
              <label class="control-label">付款账号：</label>
                <input name="employeepaymentaccount"  type="text" data-tip='{"text":"付款账号"}' class="control-text " placeholder="付款账号"/>
            </div>
            <div class="control-group span6 toggle-display" >
              <label class="control-label">收款人：</label>
                <input name="enterprisepaymentname"  type="text" data-tip='{"text":"收款人"}' class="control-text span3" placeholder="收款人"/>
            </div>
             <div class="control-group span7 toggle-display" >
              <label class="control-label">收款账号：</label>
                <input name="enterprisepaymentaccount"  type="text" data-tip='{"text":"收款账号"}' class="control-text " placeholder="收款账号"/>
            </div>
            <div class="control-group span7 toggle-display" >
              <label class="control-label">订单金额：</label>
                <input name="orderamount_begin"  type="text" data-tip='{"text":"金额下限"}' class="control-text span2" placeholder="金额下限"/>
                -
                <input name="orderamount_end"  type="text" data-tip='{"text":"金额上限"}' class="control-text span2" placeholder="金额上限"/>
            </div>
            <div style="position: absolute;right:15px;top: 3px;">
           <button id="btnSearch" type="submit"  class="button button-primary"> <span class="icon-search icon-white"></span>搜 索 </button>
        </div> 
         </div>
       </div>
    </div> 
  </form>
  <div class="search-grid-container well">
    <div id="grid"></div>
  </div>
</div>
<!-- 存取款类型  {1:存款,2:取款}-->
<input type="hidden" value="${ordertype}" id="ordertype_id" />
</body>
</html>
   <script type="text/javascript">
   // 加载银行列表
   loadThirdpartyPaymentBankName();
   
	function takeDepositFail(ordernumber) {
		$.get(getRootPath()+'/takeDepositRecord/takeDepositFail',
			{"ordernumber": ordernumber},
			function(html){
				$("#approve-panel").remove();
				if(html){
					var dialog = new BUI.Overlay.Dialog({
						id:'approve-dialog',
						title:'<h2><b>出款失败</b></h2>',
						width:600,
						height:450,
						mask:true,
						buttons:[],
						zIndex:10,
						bodyContent:html
					});
					dialog.show();
					$("#confirmPaymentForm").data("data-dialog",dialog);
					dialog.on("closeclick",function(e){
						$.ajax({
							url:getRootPath()+'/takeDepositRecord/closeAuidit',
							type:"POST",
							dataType:"JSON",
							data:{"ordernumber": ordernumber},
							success:function(data){
								
							}
						});
					});
				}
			}
		);
	}
   	var select_audit = ${sessionScope.ERCM_USER_PSERSSION['MN003P'].menustatus == 1 || sessionScope.ERCM_USER_PSERSSION['MN003R'].menustatus == 1 };
   	var order_audit = ${sessionScope.ERCM_USER_PSERSSION['MN009J'].menustatus == 1};
   		$("input[name='orderdate_begin']").val(getDate("begintime"));
	   	$("input[name='orderdate_end']").val(getDate("endtime"));
	   	$("#quiklychoicetime option:eq(1)").attr("selected","selected");
	   	
	   	function permissionValidate(){
        	var array= new Array();
       		array.push({
                btnCls : 'button button-primary',
                text:'<span class="icon-file icon-white"></span>导出到EXCEL(当前条件)',
                handler : function(){
                	
                	searchForm.action = "${ctx}/takeDepositRecord/excelTakeDepositRecord?ordertype="+$("#ordertype_id").val();
                	searchForm.submit();
                	searchForm.action = "${ctx}/takeDepositRecord/findTakeDepositRecord";
                }
            });
       		return array;
		 }
	   	
	   	
        var Grid = BUI.Grid,
          Store = BUI.Data.Store,
          columns = [
              { title: '品牌',width: '5%',  sortable: false,  dataIndex: 'brandcode',renderer:function(value,obj){
  				if(value&&obj.orderstatus/1==4){
  					return "<span style='color:red;text-decoration:line-through;'>"+value+"</span>" 
  				}else{
  					return value;
  				}
  			}},
			{ title: '订单号',   width: '16%',  sortable: false,dataIndex:'ordernumber',renderer:function(value,obj){
				if(value&&obj.orderstatus/1==4){
					return "<span style='color:red;text-decoration:line-through;'>"+value+"</span>" 
				}else{
					return value;
				}
			}},
			{ title: '账号/姓名',  width: '6%', sortable: false,  dataIndex: 'loginaccount',renderer:function(value,obj){
				if(value&&obj.orderstatus/1==4){
					return "<span style='color:red;text-decoration:line-through;font-size:14px'>"+value+"</span><br/>"+obj.displayalias ;
				}else{
					return  "<span style='color: #428BCA;' title='账户' font-size:14px>"+value+"</span><br/>"+obj.displayalias ;
				}
		    }},
		    { title: '订单金额', width: '5%', sortable: false,  dataIndex: 'orderamount', summary : true, renderer:function(value,obj){
		    	if(value&&obj.orderstatus/1==4){
					return "<span style='color:red;text-decoration:line-through;font-size:14px'>"+parseFloat(value).toFixed(2)+"</span>" 
				}else{
					if(obj.ordertype == 1){
	            		return "<span style='color:#5cb85c;font-size:14px'>"+parseFloat(value).toFixed(2)+"</span>";
	            	}else{
	            		return "<span style='color:#FF0000;font-size:14px'>"+parseFloat(value).toFixed(2)+"</span>";
	            	}
				}
            }},
			{ title: '会员收付款信息', width: '10%', sortable: false,  dataIndex: 'employeepaymentaccount',renderer:function(value,obj){
				var str = obj.employeepaymentname + "<br>" + obj.employeepaymentaccount+"<br>"+obj.employeepaymentbank;
				if(value&&obj.orderstatus/1==4){
					return "<span style='color:red;text-decoration:line-through;'>"+str+"</span>" 
				}else{
					return str;
				}
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
			{ title: '企业收付款信息', width: '10%', sortable: false,  dataIndex: 'employeepaymentaccount',renderer:function(value,obj){
				var str = "";
				if(obj.enterprisepaymentname != null) {
					str = obj.enterprisepaymentname + " - " + obj.employeepaymentbank
						+ "<br>" + obj.enterprisepaymentaccount;
				}
				if(value&&obj.orderstatus/1==4){
					return "<span style='color:red;text-decoration:line-through;'>"+str+"</span>" 
				}else{
					return str;
				}
			}},
            { title: '状态',   width: '3%',  sortable: false,  dataIndex: 'orderstatus', renderer:function(value,obj){
            	if(value&&obj.orderstatus/1==2){
					return "审核通过";
				}else{
					return "<span style='color:red;text-decoration:line-through;'>审核拒绝</span>"					
				}
            }},
            { title: '订单时间',width: '9%',  sortable: false,  dataIndex: 'orderdate',renderer:function(value,obj){
				if(value&&obj.orderstatus/1==4){
					return "<span style='color:red;text-decoration:line-through;'>"+BUI.Grid.Format.datetimeRenderer(value)+"</span>" 
				}else{
					return BUI.Grid.Format.datetimeRenderer(value);
				}
			}},
            { title: '处理时长',width: '6%',  sortable: false,renderer:function(value,obj){
            	if(obj.handleovertime == null || obj.handleovertime == "") {
            		return "--";
            	}
            	var create_date = new Date(obj.orderdate);
            	var close_date = new Date(obj.handleovertime);
            	var dealWith_date = (close_date.getTime() - create_date.getTime());
            	//计算出相差天数
            	var days=Math.floor(dealWith_date/(24*3600*1000));  
            	//计算出小时数
            	var leave1=dealWith_date%(24*3600*1000);    
            	//计算小时数后剩余的毫秒数
            	var hours=Math.floor(leave1/(3600*1000));  
            	//计算相差分钟数
            	var leave2=leave1%(3600*1000);        
            	//计算小时数后剩余的毫秒数
            	var minutes=Math.floor(leave2/(60*1000));  
            	//计算相差秒数
            	var leave3=leave2%(60*1000); 
            	//计算分钟数后剩余的毫秒数
            	var seconds=Math.round(leave3/1000);
            	var time ="";
            	if(days) time += days+"天";
            	if(hours) time += hours+"时";
            	if(minutes) time += minutes+"分";
            	if(seconds) time += seconds+"秒"
            	if(time&&obj.orderstatus/1==4){
					return "<span style='color:red;text-decoration:line-through;'>"+time+"</span>" 
				}else{
					return time;
				}
            }},
            { title: '交易IP',   width: '6%',  sortable: false,   dataIndex: 'traceip',renderer:function(value,obj){
				if(value&&obj.orderstatus/1==4){
					return "<span style='color:red;text-decoration:line-through;'>"+value+"</span>" 
				}else{
					return value;
				}
			}},
            { title: '备注',   width: '7%',  sortable: false,   dataIndex: 'ordercomment',renderer:function(value,obj){
				if(value&&obj.orderstatus/1==4){
					return "<span style='color:red;text-decoration:line-through;'>"+value+"</span>" 
				}else{
					return value;
				}
			}},
            { title: '操作',   width: '13%',  sortable: false, renderer:function(value,obj){
            	var html="";
            	if(select_audit){
            		html +=  "<button class='button button-primary botton-margin' onclick=showApproveRecord('"+obj.ordernumber+"')><span class='icon-th-large icon-white'></span>详情</button>";
            	}
            	if(obj.ordertype == 2 && obj.orderstatus == 2 && order_audit){
            		html += "<button class='button button-danger botton-margin' onclick=takeDepositFail('"+obj.ordernumber+"')><span class='icon-th-large icon-white'></span>失败</button>";
            	}
            	return html;
            }}
          ];
      
        var store = new Store({
            url : '${ctx}/takeDepositRecord/findTakeDepositRecord',
            params:{ordertype:$("#ordertype_id").val()},
            autoLoad : false,
            pageSize:15,
            sortInfo : {
                field : 'orderdate',
                direction : 'desc'
              },
  	        remoteSort: true
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
            tbar : {
				items : permissionValidate()
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
        
    </script>