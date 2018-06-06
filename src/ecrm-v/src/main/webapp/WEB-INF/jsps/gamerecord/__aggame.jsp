<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body style="padding: 10px;">
<div class="demo-content">
<!-- 搜索页 ================================================== -->
  <form id="searchForm" class="form-horizontal" style="outline: none;" method="post">
      <input name="employeecode" type="hidden" value="${employeecode}"/>
    <div class="row well" style="margin-left: 0px;position: relative;">
    <div style="position: relative;display: inline-block;">
      <div style="float: left;margin-right: 96px;">
           <div style="float: left;margin-right: 96px;">
           <div class="control-group span7">
              <label class="control-label">订单号：</label>
                <input name="aoiOrderNumber"  type="text" data-tip='{"text":"订单号"}' class="control-text"/>
            </div>
            <div class="control-group span7">
              <label class="control-label">用户名称：</label>
                <input name="aoiUserName"  type="text" data-tip='{"text":"用户名称"}' class="control-text"/>
            </div>
           <div class="control-group span7">
              <label class="control-label">游戏时间：</label>
              <div class="bui-form-group" data-rules="{dateRange : true}">
                <input name="aoiAddTime" type="text" data-tip='{"text":"游戏时间"}' class="calendar calendar-time" value=""/>
              </div>
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
        var Grid = BUI.Grid,
          Store = BUI.Data.Store,
          columns = [
			//{ title: '唯一ID',   width: 150,  sortable: false,dataIndex:'aoiProductId'},
			{ title: '定单号', width: '10%', sortable: false,  dataIndex: 'aoiOrderNumber'},
			{ title: '用户名',   width: '10%',  sortable: false,dataIndex:'aoiUserName'},
			//{ title: '游戏结果',   width: 150,  sortable: false,dataIndex:'aoiGameRecordId'},
            { title: '游戏类型',width: '8%',  sortable: false,  dataIndex: 'aoiGameNameId',renderer:function(value,obj){
            	switch(value){
 				case '11':
 					return "百家乐";
 					break;
 				case '12':
 					return "龙虎";
 					break;
 				case '13':
 					return "轮盘";
 					break;
 				case '14':
 					return "骰宝";
 					break;
 				case '15':
 					return "德州扑克";
 					break;
 				case '16':
 					return "番摊";
 				case '17':
 					return "未知";
 					break;
 				}
            }},
			{ title: '桌号',  width: '8%', sortable: false,  dataIndex: 'aoiTableId'},
			{ title: '局号',  width: '8%', sortable: false,  dataIndex: 'aoiStage'},
			{ title: '靴号',   width: '8%',  sortable: false,dataIndex:'aoiInning'},
            //{ title: '投注类型',width: '5%',  sortable: false,  dataIndex: 'aoiGameBettingKind'},
            //{ title: '投注区', width: 70, sortable: false, dataIndex: 'aoiGameBettingContent'},
            //{ title: '游戏结果类型',   width: '8%',  sortable: false, dataIndex: 'aoiResultType'},
            { title: '投注金额',   width: '8%',  sortable: false,  dataIndex: 'aoiBettingAmount'},
            { title: '赔率',   width: '8%',  sortable: false,   dataIndex: 'aoiCompensateRate'},
            { title: '有效投注额',   width: 100,  sortable: false,   dataIndex: 'aoiValidAmount'},
            { title: '输赢金额',   width: '8%',  sortable: false,   dataIndex: 'aoiWinLoseAmount' },
            { title: '余额',   width: '10%',  sortable: false,   dataIndex: 'aoiBalance'},
            { title: '游戏时间',   width: '10%',  sortable: false,   dataIndex: 'aoiAddTime',renderer:BUI.Grid.Format.datetimeRenderer},
            //{ title: '供应商Id',   width: 100,  sortable: false,   dataIndex: 'aoiVendorId'},
          ];

        var store = new Store({
            url : '${ctx}/AGGame/data',
            autoLoad : false,
            pageSize:15
          }),grid = new Grid.Grid({
            render:'#grid',
            autoRender:true,
            loadMask: true,
            width:'100%',
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