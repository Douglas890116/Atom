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
              <label class="control-label">品牌名称：</label>
                <input name="brandname" type="text"  data-tip='{"text":"品牌名称"}' class="control-text"/>
            </div>
            <div class="control-group span12">
                <label class="control-label">记录时间：</label>
                <div class="bui-form-group" data-rules="{dateRange : true}">
                  <input name="startDate"  type="text"   class="calendar calendar-time" placeholder="起始时间"/>
                  <span>&nbsp;-&nbsp;</span>
                  <input  name="endDate" type="text"    class="calendar calendar-time" placeholder="结束时间"/>
                </div>
            </div>
<!-- toggle-display -->
        </div>  
         <div style="position: absolute;right: 15px;top: 3px;">
           <button id="btnSearch" type="submit"  class="button button-primary"> <span class="icon-search icon-white"></span>搜 索 </button>
        </div>
        </div>
    </div>
    <input type="hidden" name="mark" value="${mark}">
    <input type="hidden" name="startDate" value="${startDate}">
    <input type="hidden" name="endDate" value="${endDate}">
    <input type="hidden" name="loginaccount" value="${loginaccount}">
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
			{ title: '企业名称',width: '8%',sortable: false,dataIndex: 'enterprisename'},
            { title: '品牌名称',width: '8%',sortable: false,dataIndex: 'brandname'},
            //{ title: '用户编码',width: '8%',sortable: false,dataIndex: 'employeecode'},
            { title: '游戏账号',width: '8%',sortable: false,dataIndex: 'userName'},
            { title: '游戏平台',width: '8%',sortable: false,dataIndex: 'gamePlatform'},
            { title: '游戏类型',width: '8%',sortable: false,dataIndex: 'gameType'},
            { title: '投注日期',width: '8%',sortable: false,dataIndex: 'betDay',renderer:BUI.Grid.Format.dateRenderer},
            { title: '投注金额',width: '8%',sortable: false,dataIndex: 'betMoney'},
            { title: '输赢金额',width: '8%',sortable: false,dataIndex: 'netMoney'},
            { title: '有效投注额',width: '8%',sortable: false,dataIndex: 'validMoney'},
            { title: '是否返水',width: '8%',sortable: false,dataIndex: 'rebates'},
            { title: '记录时间',width: '8%',sortable: false,dataIndex: 'addTime',renderer:BUI.Grid.Format.datetimeRenderer}
          ];

        var store = new Store({
            url : '${ctx}/report/queryUserLoseWinGameRecordDetail',
            autoLoad : false, //自动加载数据
            pageSize:15
          }),grid = new Grid.Grid({
            render:'#grid',
            autoRender:true,
            loadMask: true,//加载数据时显示屏蔽层
            columns : columns,
            width:'100%',
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
        
    </script>