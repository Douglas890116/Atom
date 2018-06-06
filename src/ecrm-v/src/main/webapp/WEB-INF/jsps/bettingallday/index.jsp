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
      		<div class="control-group span14">
              <label class="control-label">上下分时间：</label>
              <div class="bui-form-group" data-rules="{dateRange : true}">
                <input name="startBetDay" type="text" class="calendar calendar-time" value="${param.moneyinoutdate_begin }" placeholder="起始时间"/> 
                <span>&nbsp;-&nbsp;</span>
                <input name="endBetDay" type="text" class="calendar calendar-time" value="${param.moneyinoutdate_end }" placeholder="结束时间"/>
                <select onchange="changeFormatDate(this,'startBetDay','endBetDay')"  id="quiklychoicetime"  style="width:85px;">
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
          <div class="control-group span7">
              <label class="control-label">用户账号：</label>
                <input name="userName" type="text" data-tip='{"text":"用户账号"}' value="${param.loginaccount }"  class="control-text"/>
          </div>
          
          <div class="control-group span7">
              <label class="control-label">游戏平台：</label>
                <select id="gamePlatform" name="gamePlatform" >
                         <option value="" selected="selected">请选择</option>
                         <c:forEach var="game" varStatus="status" items="${listGame}">
	                    	<option value="${game.gametype }">${game.gamename }</option>
	                    </c:forEach>
                 </select>
          </div>
          
           <div class="control-group span8 ">
              <label class="control-label">投注金额：</label>
                <input type="text" name="betMoneyStart" style="width:60px;">-
                <input type="text" name="betMoneyEnd" style="width:60px;">
            </div>
            
      </div>  
      <div style="position: absolute;right: 15px;top: 3px;">
<!--        toggle-display -->
             <button id="btnSearch" type="submit"  class="button button-primary"> <span class="icon-search icon-white"></span>搜 索 </button>
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
   <script type="text/javascript" src="${statics }/js/custom/page-quote.js"></script>
   <script type="text/javascript">
	   var begintime = $("input[name='startBetDay']");
	   var endtime = $("input[name='endBetDay']");
	   if(!begintime.val()&&!endtime.val()){
		   begintime.val(getDate("begintime"));
		   endtime.val(getDate("endtime"));
		   $("#quiklychoicetime option:eq(2)").attr("selected","selected");
	   }
	   
	   function openTableUser(data){
		   $("[name='loginaccount']").val(data);
		   var obj = BUI.FormHelper.serializeToObject($("#searchForm"));
	       obj.start = 0;
		   store.load(obj);
	   }
		   
        var Grid = BUI.Grid,
          Store = BUI.Data.Store,
          columns = [
            { title: '用户账号',width: '10%',sortable: false,dataIndex: 'userName',renderer:function(value,obj){
            	return value;
            }},
            { title: '平台',width: '10%',sortable: false,dataIndex: 'gamePlatform'},
            { title: '投注时间',width: '15%',sortable: true,dataIndex: 'betDay',renderer:BUI.Grid.Format.datetimeRenderer},
            
            { title: '投注额',width: '12%',sortable: false,dataIndex: 'betMoney', summary : true,renderer:function(value,data){
            	return parseFloat(value).toFixed(2);
            }},
            { title: '输赢金额',width: '8%',sortable: false,dataIndex: 'netMoney', summary : true,renderer:function(value,data){
            	return parseFloat(value).toFixed(2);
            }}
            
          ];
        var store = new Store({
            url : '${ctx}/bettingallday/data',
            autoLoad : false,
            pageSize:15,
            sortInfo : {
                    field : 'bet_Money',
                    direction : 'desc'
                  },
            remoteSort: true
          }),grid = new Grid.Grid({
            render:'#grid',
            autoRender:true,
            loadMask: true,//加载数据时显示屏蔽层
            columns : columns,
            width:'100%',
            store: store,
            plugins : [ Grid.Plugins.CheckSelection ],
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