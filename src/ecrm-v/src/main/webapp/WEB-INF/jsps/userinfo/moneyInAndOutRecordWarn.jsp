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
                <input name="moneyinoutdate_begin" type="text" class="calendar calendar-time" value="${param.moneyinoutdate_begin }" placeholder="起始时间"/> 
                <span>&nbsp;-&nbsp;</span>
                <input name="moneyinoutdate_end" type="text" class="calendar calendar-time" value="${param.moneyinoutdate_end }" placeholder="结束时间"/>
                <select onchange="changeFormatDate(this,'moneyinoutdate_begin','moneyinoutdate_end')"  id="quiklychoicetime"  style="width:85px;">
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
                <input name="loginaccount" type="text" placeholder='用户账号' value="${param.loginaccount }"  class="control-text"/>
          </div>
          <!-- 
          <div class="control-group span7">
              <label class="control-label">处理状态：</label>
                <select id="updatecapital" name="updatecapital" style="width:85px;">
                         <option value="" selected="selected">请选择</option>
                         <option value="0">请求中</option>
                         <option value="1">成功</option>
                         <option value="2">失败</option>
                 </select>
          </div>
           -->
          <div class="control-group span7">
              <label class="control-label">操作类型：</label>
                <select id="opreatetype" name="opreatetype" style="width:85px;">
                         <option value="" selected="selected">请选择</option>
                         <option value="1">上分操作</option>
                         <option value="2">下分操作</option>
                 </select>
          </div>
          
          <div class="control-group span7">
              <label class="control-label">游戏平台：</label>
                <select id="gametype" name="gametype" >
                         <option value="" selected="selected">请选择</option>
                         <c:forEach var="game" varStatus="status" items="${sessionScope.ERCM_ENTERPRISE_GAMES}">
	                    	<option value="${game.gametype }">${game.gamename }</option>
	                    </c:forEach>
                 </select>
          </div>
          
          <div class="control-group span7">
              <label class="control-label">企业：</label>
                <select id="enterprisecode" name="enterprisecode" >
                         <option value="" selected="selected">请选择</option>
                         <c:forEach var="item" varStatus="status" items="${listEnterprise}">
	                    	<option value="${item.enterprisecode }">${item.enterprisename }</option>
	                    </c:forEach>
                 </select>
          </div>
          
          <div class="control-group span8 ">
              <label class="control-label">上下分风险金额：</label>
                <input type="text" name="money" placeholder='上下分风险金额' class="control-text" value="5000" style="color:red">
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
	   var begintime = $("input[name='moneyinoutdate_begin']");
	   var endtime = $("input[name='moneyinoutdate_end']");
	   if(!begintime.val()&&!endtime.val()){
		   begintime.val(getDate("begintime"));
		   endtime.val(getDate("endtime"));
		   $("#quiklychoicetime option:eq(1)").attr("selected","selected");
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
            { title: '用户账号',width: '8%',sortable: false,dataIndex: 'loginaccount',renderer:function(value,obj){
            	return "<a href=\"javascript:openTableUser('"+value+"')\">"+value+"</a>";
            }},
            { title: '用户别名',width: '10%',sortable: false,dataIndex: 'employeename'},
            { title: '游戏类型',width: '6%',sortable: false,dataIndex: 'gametype'},
            { title: '操作类型',width: '6%',sortable: false,dataIndex: 'opreatetype',renderer:function(value,obj){
            	switch(value){
            	case 1:
            		return "<span style='display:block;width:80%;text-align:right;'><b>上分</b></span>";
            		break;
            	case 2:
            		return "<b>下分</b>";
            		break;
            	}
            }},
            { title: '操作前余额',width: '8%',sortable: false,dataIndex: 'beforebalance', summary : true,renderer:function(value,data){
            	return parseFloat(value).toFixed(2);
            }},
            { title: '金额',width: '8%',sortable: false,dataIndex: 'moneyinoutamount', summary : true,renderer:function(value,data){
            	return '<font color=red>'+parseFloat(value).toFixed(2)+'</font>';
            }},
            { title: '操作后余额',width: '8%',sortable: false,dataIndex: 'afterbalance', summary : true,renderer:function(value,data){
            	return parseFloat(value).toFixed(2);
            }},
            { title: '状态',width: '5%',sortable: false,dataIndex: 'updatecapital',renderer:function(value,obj){
            	switch(value){
            	case 0:
            		return "请求中";
            		break;
            	case 1:
            		return "成功";
            		break;
            	case 2:
            		return "失败";
            		break;
            	}
            }},
            { title: '上下分时间',width: '10%',sortable: true,dataIndex: 'moneyinoutdate',renderer:BUI.Grid.Format.datetimeRenderer},
            { title: '备注',width: '30%',sortable: false,dataIndex: 'moneyinoutcomment', renderer:function(value,data){
            	if(value == "success") {
            		return value;
            	} else {
            		if(data.updatecapital == 0) {
                		return "<font color='red'><b>请求中</b></font>";
                	} else {
                		return "<font color='red'><b>"+value+"</b></font>";
                	}
            	}
            	
            }},
            { title: '单号',width: '12%',sortable: false,dataIndex: 'orderno'}
            
          ];
        var store = new Store({
            url : '${ctx}/moneyInAndOut/warnData',
            autoLoad : false,
            pageSize: 50,
            sortInfo : {
                    field : 'moneyinoutcode',
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
            plugins : [ Grid.Plugins.CheckSelection , Grid.Plugins.Summary],
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