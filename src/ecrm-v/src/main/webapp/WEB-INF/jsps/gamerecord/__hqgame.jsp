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
              <label class="control-label">订单号：</label>
                <input name="hqId"  type="text" data-tip='{"text":"订单号"}' class="control-text"/>
            </div>
	         <div class="control-group span7">
              <label class="control-label">投注时间：</label>
              <div class="bui-form-group" data-rules="{dateRange : true}">
              	<input name="hqDt" type="text" data-tip='{"text":"投注时间"}' class="calendar calendar-time" value=""/>
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
         			{ title: '订单号',   width: 150,  sortable: false,dataIndex:'hqId'},
         			{ title: '投注编号',   width: 100,  sortable: false,   dataIndex: 'hqRec'},
         			{ title: '厅号',width: 70,  sortable: false,  dataIndex: 'hqHl'},
         			//{ title: '游戏帐号',   width: 100,  sortable: false,dataIndex:'hqNm'},
         			{ title: '游戏名称', width: 120, sortable: false, dataIndex: 'hqGm',renderer:function(value,obj){
         				switch(value){
         				case '1':
         					return "百家乐";
         					break;
         				case '2':
         					return "龙虎";
         					break;
         				case '3':
         					return "轮盘";
         					break;
         				case '4':
         					return "骰宝";
         					break;
         				case '5':
         					return "番摊";
         					break;
         				}
         			}},
         			{ title: '桌号',width: 100,  sortable: false,  dataIndex: 'hqTbl'},
         			{ title: '局次',  width: 120, sortable: false,  dataIndex: 'hqRn'},
         			{ title: '靴号',   width: 80,  sortable: false,dataIndex:'hqBt'},
                     { title: '投注点', width: 70, sortable: false, dataIndex: 'hqPt'},
                     { title: '下注金额.',   width: 60,  sortable: false, dataIndex: 'hqBet'},
                     { title: '输赢金额',   width: 60,  sortable: false,  dataIndex: 'hqWin'},
                     { title: '洗码量',   width: 100,  sortable: false,   dataIndex: 'hqTm'},
                     { title: '投注时间',  width: 100, sortable: false,  dataIndex: 'hqDt',renderer:BUI.Grid.Format.datetimeRenderer},
                     //{ title: '结果',   width: 100,  sortable: false,   dataIndex: 'hqWer' },
                     //{ title: '牌面',   width: 100,  sortable: false,   dataIndex: 'hqCrd'},
                   ];


        var store = new Store({
            url : '${ctx}/HQGame/data',
            autoLoad : false, //自动加载数据
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