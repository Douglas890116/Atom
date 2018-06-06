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
                   <select onchange="changeFormatDate(this,'startDate','endDate')"  id="selectDateId" style="width:85px;">
                                <option value="">请选择</option>
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
            
          </div>
          
          <div class="control-group span7" >
              <label class="control-label">代理账号：</label>
              <input name="loginaccount"  type="text" data-tip='{"text":"代理账号"}' class="control-text" placeholder="代理账号"/>
            </div>
            
          	 <div style="position: absolute;right: -25px;top: 3px;">
             	 <button id="btnSearch" type="submit"  class="button button-primary"> <span class="icon-search icon-white"></span>搜 索 </button>
                 <input type="reset" style="display:none;" id="resetId" />
            </div>
        </div>
    </div>
    <h3>公式=【 【游戏输赢-优惠-洗码】 * 代理分红比例 】-手续费；</h3>
</form>
<ul style="border:5px; height:20px; line-height:20px; color:red; font-weight:bold;" class="breadcrumb">
	<li>
	<span style="font-size: 12px">
	最新数据截止到昨日。
	</span>
	</li>
</ul>
<ul style="border: none;height: 20px;line-height: 20px;color:#428bca;font-weight: bold;visibility: hidden;" id="visiteScent" class="breadcrumb">
        <li>
			访问轨迹：
        </li>
        <li>
        	<span style="padding: 0 5px;border: 1px solid #E8E9EE;cursor:pointer;" onclick = "clear(this)">清除</span>
        </li>
    </ul>
<div class="search-grid-container well">
  <div id="grid"></div>
</div>
</div>

</body>
</html>
<script type="text/javascript">
$("input[name='startDate']").val(getDate("begintime"));
$("input[name='endDate']").val(getDate("endtime"));	
$('#selectDateId option:eq(1)').attr('selected','selected');
function showMember(employeecode){
	top.topManager.openPage({
	  	id : 'member_detail',
	  	href : getRootPath()+'/nbreport/b?employeecode='+employeecode+"&employeeType=T003",
	 	title : '直线会员'
	});
}
function showAgent(employeecode,loginaccount){
	/* top.topManager.openPage({
	  	id : 'agent_detail',
	  	href : getRootPath()+'/report/userGameReport?employeecode='+employeecode+"&employeeType=T004",
	 	title : '代理团队'
	}); */
	$("#visiteScent").css("visibility","visible");
	$("#visiteScent li:nth-child(2)").append("<span>~><span onclick=userGame(this,'"+employeecode+"') style='padding: 0 5px;border: 1px solid #E8E9EE;cursor:pointer;'>"+loginaccount+"</span></span>");
	$("input[name='employeecode']").val(employeecode);
	$("#btnSearch").click();
}
//面包屑导航处理
function userGame(obj,employeecode){
	$(obj).parent().nextAll().remove();	
	$("input[name='employeecode']").val(employeecode);
	$("#btnSearch").click();
}
//清楚轨迹
function clear(obj){
	$(obj).nextAll().remove();
	$("#visiteScent").css("visibility","hidden");
	$("input[name='employeecode']").val("");
	$("#btnSearch").trigger("click");
}
var Grid = BUI.Grid,
  Store = BUI.Data.Store,
  columns = [
	//{ title: '用户编码',width: '15%',elCls:'center',  sortable: false,  dataIndex: 'employeecode'},
	{ title: '用户名',width: '15%',elCls:'center',  sortable: false,  dataIndex: 'loginaccount',renderer:function(value,obj){
		if("会员"==obj.employeetypename){
			return "<a style='color: #428BCA;font-size:16px;cursor:pointer' title='点击查看' onclick=showMember('"+obj.employeecode+"')>"+value+"</a>";
		}if("代理"==obj.employeetypename || "员工"==obj.employeetypename){
			return "<a style='color: #428BCA;font-size:16px;cursor:pointer' title='点击查看' onclick=showAgent('"+obj.employeecode+"','"+obj.loginaccount+"')>"+value+"</a>";
		}else{
			return value;
		}
	}},
	{ title: '用户类型',width: '15%',elCls:'center',  sortable: false,  dataIndex: 'remark',renderer:function(value,obj){
		if("会员"==obj.employeetypename){
			return "<font color='red'>"+value+"</font>";
		} else {
			return value;
		}
	}},
	{ title: '团队用户数',width: '15%',elCls:'center',  sortable: false,  dataIndex: 'countMember',renderer:function(value,obj){
		if(obj.employeetypename == "个人会员") {
			return "-";
		} else {
			return "会员："+value+" 代理："+obj.countAgent + " 员工："+obj.countEmployee;
		}
	}},
	
	
	
	{ title: '投注总额',width: '13%',elCls:'center',  sortable: false, summary : true,  dataIndex: 'game_betting_amount',renderer:function(value,obj){
		var aa= ""+(value==null?"0.00":value.toFixed(2))+"";
		
		if( aa != 0) {
			return "<b>"+aa+"</b>";
		} else {
			return 0;
		}
	}},
	{ title: '游戏输赢',width: '13%',elCls:'center',  sortable: false, summary : true,  dataIndex: 'lose_win_amount',renderer:function(value){
		var aa=0;
		if((value+"").indexOf("-")<0){
			aa= ""+(value==null?"0.00":value.toFixed(2))+"";
		}else{
			aa= ""+(value==null?"0.00":value.toFixed(2))+"";			
		}
		
		if( aa != 0) {
			return "<b>"+aa+"</b>";
		} else {
			return 0;
		}
	}},
	
	{ title: '总存款',width: '10%',elCls:'center',  sortable: false, summary : true,  dataIndex: 'depositMoney',renderer:function(value,obj){
		var aa= ""+(value==null?"0.00":value.toFixed(2))+"";
		
		if( aa != 0) {
			return "<b>"+aa+"</b>";
		} else {
			return 0;
		}
	}},
	{ title: '存款手续费',   width: '10%', elCls:'center', sortable: false, summary : true,dataIndex: 'dividend',renderer:function(value,obj){
		var aa= value==null?"0.00":value.toFixed(2);
		
		if( aa != 0) {
			return "<b>"+aa+"</b>";
		} else {
			return 0;
		}
	}},
	
	{ title: '总取款',width: '10%',elCls:'center',  sortable: false, summary : true,  dataIndex: 'withdrawMoney',renderer:function(value,obj){
		var aa= ""+(value==null?"0.00":value.toFixed(2))+"";
		
		if( aa != 0) {
			return "<b>"+aa+"</b>";
		} else {
			return 0;
		}
	}},
	{ title: '取款手续费',   width: '10%', elCls:'center', sortable: false, summary : true, dataIndex: 'share',renderer:function(value,obj){
		var aa= value==null?"0.00":value.toFixed(2);
		
		if( aa != 0) {
			return "<b>"+aa+"</b>";
		} else {
			return 0;
		}
	}},
	
	{ title: '优惠金额',width: '15%',elCls:'center',  sortable: false, summary : true,  dataIndex: 'activity_money',renderer:function(value,obj){
		var aa= value==null?"0.00":value.toFixed(2);
		
		if( aa != 0) {
			return "<b>"+aa+"</b>";
		} else {
			return 0;
		}
	}},
	{ title: '洗码金额',width: '15%',elCls:'center',  sortable: false, summary : true,  dataIndex: 'bonus',renderer:function(value,obj){
		var aa= ""+(value==null?"0.00":value.toFixed(2))+"";
		
		if( aa != 0) {
			return "<b>"+aa+"</b>";
		} else {
			return 0;
		}
	}},
	
	{ title: '结算额',   width: '10%', elCls:'center', sortable: false, dataIndex: 'profit_amount',summary : true, renderer:function(value,obj){
		return "<font color=red>"+(value==null?"0.00":parseFloat(value).toFixed(2))+"</font>";
		
	}}
	
	
  ];

var store = new Store({
    url : '${ctx}/nbreport/queryUserProfitNew3',
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
    // 顶部工具栏
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