<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.maven.entity.EnterpriseEmployeeType.Type" %>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body style="padding: 10px;">
    <div class="demo-content" >
<!-- 搜索页 ================================================== style=" margin:0 auto;  width:950px;"-->
<form id="searchForm" class="form-horizontal" style="outline: none;" method="post">
    <input name="end_hidden" type="hidden" />
    <input name="employeecode" type="hidden" value="${employeecode}"/>
    <input name="employeeType" type="hidden" value="${employeeType}"/>
    <div class="row well" style="margin-left: 0px;position: relative;">
    <div style="position: relative;display: inline-block;">
      <div style="float: left;margin-right: 96px;">
             <div class="control-group span12">
                <label class="control-label">时间范围：</label>
                <div class="bui-form-group" data-rules="{dateRange : true}">
                  <input name="startDate"  type="text"   class="calendar calendar-time" placeholder="起始时间"/>
                  <span>&nbsp;-&nbsp;</span>
                  <input  name="endDate" type="text"    class="calendar calendar-time" placeholder="结束时间"/>
                </div>
                <div style="margin-right: auto;margin: -30px 425px;">
                  <select onchange="changeDate(this,'startDate','endDate')" style="width:85px;">
                    <option value="">请选择</option>
                    <option value="1">一天</option>
                    <option value="2">三天</option>
                    <option value="3">一周</option>
                    <option value="4">一个月</option>
                  </select>
                </div>
            </div>
          </div>
          	 <div style="position: absolute;right: -25px;top: 3px;">
             	 <button id="btnSearch" type="submit"  class="button button-primary"> <span class="icon-search icon-white"></span>搜 索 </button>
                 <input type="reset" style="display:none;" id="resetId" />
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
function showMember(employeecode){
	top.topManager.openPage({
	  	id : 'member_detail',
	  	href : getRootPath()+'/report/userLoseWinCount?employeecode='+employeecode+"&employeeType=T003",
	 	title : '会员'
	});
}
function showAgent(employeecode){
	top.topManager.openPage({
	  	id : 'agent_detail',
	  	href : getRootPath()+'/report/userLoseWinCount?employeecode='+employeecode+"&employeeType=T004",
	 	title : '代理'
	});
}
var Grid = BUI.Grid,
  Store = BUI.Data.Store,
  columns = [
	{ title: '用户编码',width: '10%',elCls:'center',  sortable: false,  dataIndex: 'employeecode'},
	{ title: '用户名',width: '10%',elCls:'center',  sortable: false,  dataIndex: 'loginaccount',renderer:function(value,obj){
		if("会员"==obj.employeetypename){
			return "<a style='color: #428BCA;font-size:16px;cursor:pointer' title='点击查看' onclick=showMember('"+obj.employeecode+"')>"+value+"</a>";
		}if("代理"==obj.employeetypename){
    		return "<a style='color: #428BCA;font-size:16px;cursor:pointer' title='点击查看' onclick=showAgent('"+obj.employeecode+"')>"+value+"</a>";
		}else{
			return value;
		}
	}},
	{ title: '用户类型',width: '10%',elCls:'center',  sortable: false,  dataIndex: 'employeetypename',renderer:function(value){
		return value;
	}},
	{ title: '用户数量',width: '10%',elCls:'center',  sortable: false,  dataIndex: 'num'},
	{ title: '投注总额',width: '10%',elCls:'center',  sortable: false,  dataIndex: 'game_betting_amount',renderer:function(value){
		return "<span style='color:#29B529;font-size:medium;'>"+(null==value?"0.00":value)+"</span>";
	}},
	{ title: '输赢金额',width: '10%',elCls:'center',  sortable: false,  dataIndex: 'lose_win_amount',renderer:function(value){
		if((value+"").indexOf("-")<0){
			return "<span style='color:#29B529;font-size:medium;'>"+(null==value?"0.00":value)+"</span>";
		}else{
			return "<span style='color:red;font-size:medium;'>"+(null==value?"0.00":value)+"</span>";			
		}
	}},
	{ title: '洗码金额',width: '10%',elCls:'center',  sortable: false,  dataIndex: 'bonus',renderer:function(value,obj){
		return "<span style='color:#29B529;font-size:medium;'>"+(null==value?"0.00":value)+"</span>";
	}},
	{ title: '分红金额',   width: '10%', elCls:'center', sortable: false, dataIndex: 'dividend',renderer:function(value,obj){
		return "<span style='color:#29B529;font-size:medium;'>"+(null==value?"0.00":value)+"</span>";
	}},
    { title: '占成金额',   width: '10%', elCls:'center', sortable: false, dataIndex: 'share',renderer:function(value,obj){
    	return "<span style='color:#29B529;font-size:medium;'>"+(null==value?"0.00":value)+"</span>";
    }},
    { title: '用户输赢金额',   width: '10%', elCls:'center', sortable: false, dataIndex: 'user_lose_win_amount',renderer:function(value){
		if((value+"").indexOf("-")<0){
			return "<span style='color:#29B529;font-size:medium;'>"+(null==value?"0.00":value)+"</span>";
		}else{
			return "<span style='color:red;font-size:medium;'>"+(null==value?"0.00":value)+"</span>";			
		}
	}}
  ];

var store = new Store({
    url : '${ctx}/report/queryAgentContributionDetail',
    autoLoad:false, 
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
   
	$("#searchForm").submit(function(){
		var obj = BUI.FormHelper.serializeToObject(this);
	    obj.start = 0;
	    store.load(obj);
		  return false;
	}).submit();

</script>