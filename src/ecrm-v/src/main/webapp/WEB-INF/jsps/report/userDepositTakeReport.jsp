<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body style="padding: 10px;">
    <div class="demo-content" >
<!-- 搜索页 ================================================== style=" margin:0 auto;  width:950px;"-->
<form id="searchForm" class="form-horizontal" style="outline: none;" method="post">
 <input name="end_hidden" type="hidden" />
    <div class="row well" style="margin-left: 0px;position: relative;">
      <div style="position: relative;display: inline-block;">
          <div style="float: left;margin-right: 96px;">
             <div class="control-group span13">
                <label class="control-label">时间范围：</label>
                <div class="bui-form-group" data-rules="{dateRange : true}">
                  <input name="startDate"  type="text"   class="calendar calendar-time" placeholder="起始时间"/>
                  <span>&nbsp;-&nbsp;</span>
                  <input  name="endDate" type="text"    class="calendar calendar-time" placeholder="结束时间"/>
                </div>
                <div style="margin-right: auto;margin: -30px 425px;">
							 <select onchange="changeFormatDate(this,'startDate','endDate')"  style="width:85px;">
                                <option value="">请选择</option>
                                <option value="1" selected="selected">今天</option>
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
   $(function(){
	   $("input[name='startDate']").val(getDate("begintime"));
	   $("input[name='endDate']").val(getDate("endtime"));	
   		var ranking = 0; 
        var Grid = BUI.Grid,
          Store = BUI.Data.Store,
          columns = [
			{ title: '用户账号',  width: 100, sortable: false,  dataIndex: 'loginaccount'},
			{ title: '存款总额',  width: 50,  sortable: false,dataIndex:'allDepositMoney',renderer:function(value,obj){
          		return "<span style='color:#5cb85c;font-size: 18px;'>"+(value==null?"0.00":parseFloat(value).toFixed(2))+"</span>";
            }},
            { title: '存款次数',  width: 100, sortable: false,  dataIndex: 'depositNumber'},
			{ title: '取款总额',   width: 50,  sortable: false,dataIndex:'allTakeMoney',renderer:function(value,obj){
				return "<span style='color:#FF0000;font-size: 18px;'>"+(value==null?"0.00":parseFloat(value).toFixed(2))+"</span>";
			}},
            { title: '取款次数',  width: 100, sortable: false,  dataIndex: 'quantity'}
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
            url : '${ctx}/report/queryUserDepositTake',
            autoLoad : false,
            pageSize:15
          }),grid = new Grid.Grid({
            render:'#grid',
            autoRender:true,
            loadMask: true,//加载数据时显示屏蔽层
            forceFit:true,
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
 		
        $("#searchForm").submit(function(){
      	  var obj = BUI.FormHelper.serializeToObject(this);
            obj.start = 0;
            store.load(obj);
      	  return false;
        }).submit();
        
   })
    </script>