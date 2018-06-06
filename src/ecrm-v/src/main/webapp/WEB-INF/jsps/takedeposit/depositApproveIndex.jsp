<%@page import="com.maven.config.SystemConstant"%>
<%@page import="com.maven.cache.SystemCache"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body style="padding: 10px;">
<div class="demo-content" >
<!-- 搜索页 ================================================== -->
  <form id="searchForm" class="form-horizontal" style="outline: none;" method="post">
   	<input name="end_hidden" type="hidden" />
    <div class="row well" style="margin-left: 0px;position: relative;">
    <div style="position: relative;display: inline-block;">
      <div style="float: left;margin-right: 96px;">
           <div class="control-group span14 ">
              <label class="control-label">订单时间：</label>
              <div class=" bui-form-group" data-rules="{dateRange : true}">
                <input name="orderdate_begin" type="text" class="calendar calendar-time " data-tip='{"text":"起始时间"}' placeholder="起始时间"/>
                <span>&nbsp;-&nbsp;</span>
                <input name="orderdate_end" type="text"   class="calendar calendar-time " data-tip='{"text":"结束时间"}' placeholder="结束时间"/>
              </div>
               <div style="margin-right: auto;margin: -30px 425px;">
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
            <div class="control-group span7" >
              <label class="control-label">订单号：</label>
                <input name="ordernumber"  type="text" data-tip='{"text":"订单号"}'   class="control-text" placeholder="订单号"/>
            </div>
            <div class="control-group span6" >
              <label class="control-label">账号：</label>
                <input name="loginaccount"  type="text" data-tip='{"text":"账号"}'  class="control-text span3" placeholder="账号"/>
            </div>
            <div class="control-group span7" >
              <label class="control-label">上级账号：</label>
              <input name="parentName"  type="text" data-tip='{"text":"上级账号"}' class="control-text" placeholder="上级账号"/>
            </div>
            
            <div class="control-group span6 toggle-display" >
              <label class="control-label">付款人：</label>
                <input name="employeepaymentname"  type="text" data-tip='{"text":"付款人"}' class="control-text span3" placeholder="付款人"/>
            </div>
            
            <div class="control-group span7 toggle-display" >
              <label class="control-label">支付方式：</label>
                <select name="paymenttypecode" style="width:120px;">
                	<option value="">请选择</option>
                	<c:forEach var="item" items="${listPaymentType }" varStatus="i">
                		<option value="${item.paymenttypecode }">${item.paymenttype }</option>
                	</c:forEach>
                </select>
            </div>
            <div class="control-group span7 toggle-display" >
              <label class="control-label">第三方支付渠道：</label>
                <select name="enterprisepaymentbank" style="width:120px;">
                	<option value="">请选择</option>
                	<c:forEach var="item" items="${listThirdpartyPaymentType }" varStatus="i">
                		<option value="${item.thirdpartypaymenttypecode }">${item.thirdpartypaymenttypename }</option>
                	</c:forEach>
                </select>
            </div>
            
             <div class="control-group span7 toggle-display" >
              <label class="control-label">付款账号：</label>
                <input name="employeepaymentaccount"  type="text" data-tip='{"text":"付款帐号"}' class="control-text " placeholder="付款帐号"/>
            </div>
            <div class="control-group span6 toggle-display" >
              <label class="control-label">收款人：</label>
                <input name="enterprisepaymentname"  type="text" data-tip='{"text":"收款人"}' class="control-text span3" placeholder="收款人"/>
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
                <input name="orderamount_begin"  type="text" data-tip='{"text":"金额下限"}' class="control-text span2" placeholder="金额下限"/>
                -
                <input name="orderamount_end"  type="text" data-tip='{"text":"金额上限"}' class="control-text span2" placeholder="金额上限"/>
            </div>
            
         </div>
      	 <div style="position: absolute;right: 15px;top: 3px;">
         	 <button id="btnSearch" type="submit"  class="button button-primary"><span class="icon-search icon-white"></span>搜 索 </button>
        </div>
        </div>
    </div> 
  </form>
  
  <form id="excelForm" style="outline: none;" method="post" action="${ctx}/takeDepositRecord/excelDepositApproveRecord">
  </form>
  
  <div class="search-grid-container well">
    <div id="grid"></div>
  </div>
</div>
</body>
</html>
<script type="text/javascript">
	(function(){
		var order_audit = ${sessionScope.ERCM_USER_PSERSSION['MN003K'].menustatus == 1 };
		var deposit_add = ${sessionScope.ERCM_USER_PSERSSION['MN0069'].menustatus == 1 };
		
		$("input[name='orderdate_begin']").val(getDate("begintime"));
	    $("input[name='orderdate_end']").val(getDate("endtime"));	
	    $("#quiklychoicetime option:eq(1)").attr("selected","selected");
		
		 function permissionValidate(){
        	var array= new Array();
        	if(deposit_add) {
         		array.push({
                  btnCls : 'button button-primary',
                  text:'<span class="icon-file icon-white"></span>手工补单',
                  handler : function(){
                	 openNewWindow('save_money_order','${ctx}/takeDepositRecord/DepositAdd','存款补单');
                  }
              });	
        	}
       		array.push({
                btnCls : 'button button-primary',
                text:'<span class="icon-file icon-white"></span>导出到EXCEL(当前条件)',
                handler : function(){
                	
                	searchForm.action = "${ctx}/takeDepositRecord/excelDepositApproveRecord";
                	searchForm.submit();
                	searchForm.action = "${ctx}/takeDepositRecord/findDepositApproveRecord";
                }
            });
       		return array;
		 }
		
	    var Grid = BUI.Grid,
	      Store = BUI.Data.Store,
	      columns = [
			{ title: '品牌', width: '5%',  dataIndex: 'brandcode'},
			{ title: '订单号',   width: '15%', dataIndex:'ordernumber'},			
			{ title: '账号/姓名',  width: '6%',  dataIndex: 'loginaccount',renderer:function(value,obj){
				return  "<span style='color: #428BCA;font-size:14px;' title='账户' >"+value+"</span><br/>"+obj.displayalias ;
			}},
			{ title: '订单金额', width: '5%', dataIndex: 'orderamount', summary : true,  renderer:function(value,obj){
	        	return "<span style='color:#5cb85c;font-size:14px'>"+parseFloat(obj.orderamount).toFixed(2)+"</span>";
	        }},
	        { title: '付款人信息',  width: '10%',  dataIndex: 'enterprisepaymentaccount', renderer:function(value,obj){
            	return obj.employeepaymentname + "<br>" + obj.employeepaymentaccount+"<br>"+obj.employeepaymentbank;
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
	        { title: '企业收款信息',  width: '10%',  dataIndex: 'enterprisepaymentaccount', renderer:function(value,obj){
            	return obj.enterprisepaymentname+"<br>"+obj.enterprisepaymentaccount;
        	}},
	        { title: '订单时间',width: '8%',dataIndex: 'orderdate',renderer:BUI.Grid.Format.datetimeRenderer},
	        { title: '交易IP',   width: '6%',    dataIndex: 'traceip'},
	        { title: '当前流程',   width: '6%',    dataIndex: 'flowcode'},
	        { title: '当前处理人',   width: '6%',    dataIndex: 'handleemployee'},
	        { title: '备注',  width: '6%',  dataIndex: 'ordercomment'},
	        { title : '操作', width: '10%',   renderer : function(value,obj){
	        	var html="";
	        	if(order_audit&&obj.sign&&(!obj.handleemployee||obj.handleemployee=="${sessionScope.ERCM_USER_SESSION.loginaccount}")){
	        		html+= '<button onclick="showDepositApprove(\''+obj.sign+'\')" name="存款审批" class="button button-info"><span class="icon-check icon-white"></span>审批</button>';
	        	}
	          	return html;
	        }}
	      ];

	var store = new Store({
	        url : '${ctx}/takeDepositRecord/findDepositApproveRecord',
	        autoLoad : false,
	        pageSize:15,
	        sortInfo : {
              field : 'orderdate',
              direction : 'desc'
            },
	        remoteSort: true
	      }),grid = new Grid.Grid({
	        render:'#grid',
	        autoRender:true,
	        loadMask: true,//加载数据时显示屏蔽层
	        width:'100%',
	        columns : columns,
	        store: store,
	        plugins : [ Grid.Plugins.CheckSelection, Grid.Plugins.Summary  ],
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
	})()
</script>