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
            <div class="control-group span7" >
              <label class="control-label">订单号：</label>
                <input name="ordernumber"  type="text" data-tip='{"text":"订单号"}'  class="control-text"/>
            </div>
            <div class="control-group span6" >
              <label class="control-label">账号：</label>
                <input name="loginaccount"  type="text" data-tip='{"text":"账号"}' class="control-text span3"/>
            </div>
            <div class="control-group span6" >
              <label class="control-label">付款人：</label>
                <input name="employeepaymentname"  type="text" data-tip='{"text":"付款人"}' class="control-text span3"/>
            </div>
             <div class="control-group span7 toggle-display" >
              <label class="control-label">付款账号：</label>
                <input name="employeepaymentaccount"  type="text" data-tip='{"text":"付款账号"}' class="control-text "/>
            </div>
            <div class="control-group span6 toggle-display" >
              <label class="control-label">收款人：</label>
                <input name="enterprisepaymentname"  type="text" data-tip='{"text":"收款人"}' class="control-text span3"/>
            </div>
             <div class="control-group span7 toggle-display" >
              <label class="control-label">收款账号：</label>
                <input name="enterprisepaymentaccount"  type="text" data-tip='{"text":"收款账号"}' class="control-text "/>
            </div>
            <div class="control-group span7 toggle-display" >
              <label class="control-label">订单金额：</label>
                <input name="orderamount_begin"  type="text" data-tip='{"text":"金额下限"}' class="control-text span2"/>
                -
                <input name="orderamount_end"  type="text" data-tip='{"text":"金额上限"}' class="control-text span2"/>
            </div>
            <div class="control-group span12  toggle-display">
              <label class="control-label">订单时间：</label>
              <div class="bui-form-group" data-rules="{dateRange : true}">
                <input name="orderdate_begin" type="text" class="calendar calendar-time" placeholder="起始时间"  style="width: 125px;"/>
                -
                <input name="orderdate_end" type="text"   class="calendar calendar-time" placeholder="结束时间" style="width: 125px;"/>
              </div>
              <div style="margin-right: auto;margin: -30px 376px;">
                <select onchange="changeDate(this,'orderdate_begin','orderdate_end')" style="width:85px;">
                  <option value="">请选择</option>
                  <option value="1">一天</option>
                  <option value="2">三天</option>
                  <option value="3">一周</option>
                  <option value="4">一个月</option>
                </select>
              </div>
            </div>
            <div style="position: absolute;right:10px;top: 3px;">
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
<input name="startDate" type="hidden" value="${startDate}">
<input name="endDate" type="hidden" value="${endDate}">
<input name="startHandleTime" type="hidden" value="${startHandleTime}">
<input name="endHandleTime" type="hidden" value="${endHandleTime}">
</body>
</html>
   <script type="text/javascript">
   	var select_audit = ${sessionScope.ERCM_USER_PSERSSION['MN003P'].menustatus == 1 || sessionScope.ERCM_USER_PSERSSION['MN003R'].menustatus == 1 };
   
        var Grid = BUI.Grid,
          Store = BUI.Data.Store,
          columns = [
			{ title: '订单号',   width: 258,  sortable: false,dataIndex:'ordernumber'},
			{ title: '账号',  width: '8%', sortable: false,  dataIndex: 'loginaccount',renderer:function(value,obj){
		    	return  "<span style='color: #428BCA;font-size:16px;' title='账户' >"+value+"</span>";
		    }},
		    { title: '订单金额', width: '8%', sortable: false,  renderer:function(value,obj){
            	if(obj.ordertype == 1){
            		return "<span style='color:#5cb85c;font-size: 18px;'>"+parseFloat(obj.orderamount).toFixed(2)+"</span>";
            	}else{
            		return "<span style='color:#FF0000;font-size: 18px;'>"+parseFloat(obj.orderamount).toFixed(2)+"</span>";
            	}
            }},
			{ title: '存取款用户',   width: 78,  sortable: false,dataIndex:'employeepaymentname'},
			//{ title: '员工收付款账号', width: '12%', sortable: false,  dataIndex: 'employeepaymentaccount'},
			//{ title: '企业收款人',   width: 78,  sortable: false,dataIndex:'enterprisepaymentname'},
			//{ title: '企业收付款账号',  width: '12%', sortable: false,  dataIndex: 'enterprisepaymentaccount'},
            { title: '游戏平台',width: '6%',  sortable: false,  dataIndex: 'brandcode'},
            { title: '订单日期',width: '10%',  sortable: false,  dataIndex: 'orderdate',renderer:BUI.Grid.Format.datetimeRenderer},
            { title: '处理时长',width: '12%',  sortable: false,renderer:function(value,obj){
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
            	
				return days+"天 "+hours+"小时 "+minutes+" 分钟"+seconds+" 秒";
            }},
            { title: '类型',   width: '5%',  sortable: false,  renderer:function(value,obj){
            	switch(obj.ordertype){
                	case 1:
                		return "存款";
                	case 2:
                		return "取款";
                	default:
                		return "未知类型";
            	}
            }},
            { title: '状态',   width: '5%',  sortable: false, renderer:function(value,obj){
            	return "已处理";
            }},
            //{ title: '订单创建人',   width: 78,  sortable: false,   dataIndex: 'ordercreater'},
            { title: '交易IP',   width: '8%',  sortable: false,   dataIndex: 'traceip'},
            { title: '订单备注',   width: '8%',  sortable: false,   dataIndex: 'ordercomment' },
            { title: '操作',   width: 108,  sortable: false, renderer:function(value,obj){
            	var html="";
            	if(select_audit){
            		html +=  "<button class='button button-primary botton-margin' onclick=showApproveRecord('"+obj.ordernumber+"')><span class='icon-th-large icon-white'></span>审批详情</button>";
            	}
            	return html;
            }}
          ];
      
        var store = new Store({
            url : '${ctx}/report/queryBusinessHandleReportDetail',
            params:{
            		startDate:$("input[name='startDate']").val(),
            		endDate:$("input[name='endDate']").val(),
            		startHandleTime:$("input[name='startHandleTime']").val(),
            		endHandleTime:$("input[name='endHandleTime']").val()
            },
            autoLoad : false,
            pageSize:15
          }),grid = new Grid.Grid({
            render:'#grid',
            autoRender:true,
            loadMask: true,
            width:'100%',
            columns : columns,
            store: store,
            plugins : [Grid.Plugins.CheckSelection],
            emptyDataTpl:'<div class="centered"><img alt="Crying" src="${statics }/img/dataisnull.png"/><font style="color:red;font-size:20px;"><h2>没有找到匹配的数据!</h2></font></div>',
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