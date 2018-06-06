<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.maven.entity.EnterpriseEmployeeType.Type" %>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body style="padding: 10px;">
    <div class="demo-content" >
    
<!-- 搜索页 ================================================== style=" margin:0 auto;  width:950px;"-->
<form id="searchForm" class="form-horizontal" style="outline: none;" method="post">
    <input name="end_hidden" type="hidden" />
    <input name="employeecode" type="hidden" value="${employeecode}"/>
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
                  <select onchange="changeFormatDate(this,'startDate','endDate')" style="width:85px;">
                    <option value="">请选择</option>
                    <option value="1" >今天</option>
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
function showMemberGameRecord(employeecode,gametype){
	debugger;
	var url_params ="";
	switch(gametype.trim()){
		case "NHQGame"://HY-好赢
			url_params="/NHQGame/index?employeecode="+employeecode;
		break;
		case "BBINGame"://BBIN-波音
			url_params="/BBINGame/list?employeecode="+employeecode;
		break;
		case "TAGGame"://AG-亚游
			url_params="/TAGGame/index?employeecode="+employeecode;
		break;
		case "OGGame"://OG-东方
			url_params="/OGGame/list?employeecode="+employeecode;
		break;
		case "PTGame"://PT-小游戏
			url_params="/PTGame/list?employeecode="+employeecode;
		break;
		case "XCPGame"://XR-彩票
			url_params="/XCPGame/list?employeecode="+employeecode;
		break;
		case "IBCGame"://SB-沙巴体育
			url_params="/IBCGame/list?employeecode="+employeecode;
		break;
		case "AGGame"://OG-AG (东方-AG)
			url_params="/IBCGame/list?employeecode="+employeecode;
		break;
		default:
			break;
	}
	top.topManager.openPage({
	  	id : 'member_gameRecord_detail',
	  	href : getRootPath()+url_params,
	 	title : '游戏记录'
	});
}

var Grid = BUI.Grid,
  Store = BUI.Data.Store,
  columns = [
	//{ title: '用户编码',width: '15%',elCls:'center',  sortable: false,  dataIndex: 'employeecode'},
	{ title: '用户名',width: '15%', sortable: false,  dataIndex: 'loginaccount'},
	{ title: '用户类型',width: '10%', sortable: false,  dataIndex: 'employeetypename',renderer:function(value){
		return value.replace("_nodown","");
	}},
	{ title: '游戏类型',width: '10%', sortable: false,  dataIndex: 'gametype'},
	{ title: '游戏名称',width: '15%', sortable: false,  dataIndex: 'gamename'},
	{ title: '投注总额',width: '15%',  sortable: false, summary : true,  dataIndex: 'game_betting_amount',renderer:function(value,obj){
		return "<span style='color:#29B529;font-size:medium;'>"+(value==null?"0.00":value.toFixed(2))+"</span>";
	}},
	{ title: '输赢金额',width: '15%',sortable: false, summary : true,  dataIndex: 'lose_win_amount',renderer:function(value){
		if((value+"").indexOf("-")<0){
			return "<span style='color:#29B529;font-size:medium;'>"+(value==null?"0.00":value.toFixed(2))+"</span>";
		}else{
			return "<span style='color:red;font-size:medium;'>"+(value==null?"0.00":value.toFixed(2))+"</span>";			
		}
	}},
	{ title: '洗码金额',width: '15%',sortable: false,summary : true, dataIndex: 'bonus',renderer:function(value,obj){
		return "<span style='color:#29B529;font-size:medium;'>"+(value==null?"0.00":value.toFixed(2))+"</span>";
	}},
	{ title: '操作',width: '10%',sortable: false,renderer:function(value,obj){
		return '<button class="button button-primary botton-margin" onclick=showMemberGameRecord("'+obj.employeecode+'","'+obj.gametype+'")><span class="icon-th-large icon-white"></span>详细记录</button>';
	}}
  ];

var store = new Store({
    url : '${ctx}/report/queryUserGameRecordNew',
    autoLoad:false, 
    pageSize:15
  }),grid = new Grid.Grid({
    render:'#grid',
    autoRender:true,
    loadMask: true,//加载数据时显示屏蔽层
    width:'100%',
    columns : columns,
    store: store,
    plugins : [Grid.Plugins.CheckSelection, Grid.Plugins.Summary],
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