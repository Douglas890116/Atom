<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body style="padding: 10px;">
<div class="demo-content">
<!-- 搜索页 ================================================== -->
  <form id="searchForm" class="form-horizontal" style="outline: none;" method="post">
    <div class="row well" style="margin-left: 0px;position: relative;">
    <div style="position: relative;display: inline-block;">
      <div style="float: left;margin-right: 96px;">
            <div class="control-group span7">
              <label class="control-label">订单类型：</label>
                <select name="ordertype" aria-pressed="false" aria-disabled="false"   class="bui-form-field-select bui-form-field">
					<option value="">请选择</option>
                    <option value="1">存款单据</option>
                    <option value="2">取款单据</option>
              	</select>
	       </div>
	       <div class="control-group span7">
              <label class="control-label">开始时间：</label>
              	<input name="startDate" type="text" data-tip='{"text":"起始时间"}' class="calendar calendar-time" value=""/>
           </div>
           <div class="control-group span7">
              <label class="control-label">结束时间：</label>
                <input name="endDate" type="text" data-tip='{"text":"结束时间"}' class="calendar calendar-time" value=""/>
           </div>
          </div>
      	 <div style="position: absolute;right: 15px;top: 3px;">
         	 <button id="btnSearch" type="submit"  class="button button-primary"> 搜 索 </button>
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
        var Grid = BUI.Grid,
          Store = BUI.Data.Store,
          columns = [
			{ title: '订单号',   width: 218,  sortable: false,dataIndex:'ordernumber'},
			{ title: '系统账号',  width: 100, sortable: false,  dataIndex: 'loginaccount'},
			{ title: '用户名',   width: 50,  sortable: false,dataIndex:'enterprisepaymentname'},
			{ title: '员工收付款账号', width: 120, sortable: false,  dataIndex: 'employeepaymentaccount'},
			{ title: '企业收款人',   width: 80,  sortable: false,dataIndex:'enterprisepaymentname'},
			{ title: '企业收付款账号',  width: 128, sortable: false,  dataIndex: 'enterprisepaymentaccount'},
            { title: '游戏平台',width: 70,  sortable: false,  dataIndex: 'brandcode'},
            { title: '订单日期',width: 110,  sortable: false,  dataIndex: 'orderdate',renderer:BUI.Grid.Format.datetimeRenderer},
            { title: '订单金额', width: 70, sortable: false,  renderer:function(value,obj){
            	if(obj.ordertype == 1){
            		return "<span style='color:#5cb85c;font-size: 18px;'>"+obj.orderamount+"</span>";
            	}else{
            		return "<span style='color:#FF0000;font-size: 18px;'>"+obj.orderamount+"</span>";
            	}
            }},
            
            { title: '订单类型',   width: 60,  sortable: false,  renderer:function(value,obj){
            	switch(obj.ordertype){
                	case 1:
                		return "存款";
                	case 2:
                		return "取款";
                	default:
                		return "未知类型";
            	}
            }},
            { title: '订单状态',   width: 60,  sortable: false, renderer:function(value,obj){
            	switch(obj.orderstatus){
                	case 1:
                		return "处理中";
                	case 2:
                		return "已处理";
                	default:
                		return "未知状态";
            	}
            }},
            { title: '订单创建人',   width: 100,  sortable: false,   dataIndex: 'ordercreater'},
            { title: '订单备注',   width: 100,  sortable: false,   dataIndex: 'ordercomment' }
          ];
        //var editing = new Grid.Plugins.RowEditing();
        /**
         * 自动发送的数据格式： 
         *  1. start: 开始记录的起始数，如第 20 条,从0开始
         *  2. limit : 单页多少条记录
         *  3. pageIndex : 第几页，同start参数重复，可以选择其中一个使用
         *
         * 返回的数据格式：
         *  {
         *     "rows" : [{},{}], //数据集合
         *     "results" : 100, //记录总数
         *     "hasError" : false, //是否存在错误
         *     "error" : "" // 仅在 hasError : true 时使用
         *   }
         * pencil,bin,plus
         */
        var store = new Store({
            url : '${ctx}/takeDepositRecord/queryDepositTakeRecodeDatail',
            autoLoad : false,
            pageSize:15
          }),grid = new Grid.Grid({
            render:'#grid',
            autoRender:true,
            loadMask: true,//加载数据时显示屏蔽层
            forceFit:true,
            columns : columns,
            store: store,
            emptyDataTpl:'<div class="centered"><img alt="Crying" src="${statics }/img/dataisnull.png"/><font style="color:red;font-size:20px;"><h2>没有找到匹配的数据!</h2></font></div>',
            bbar : {
              pagingBar:true
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