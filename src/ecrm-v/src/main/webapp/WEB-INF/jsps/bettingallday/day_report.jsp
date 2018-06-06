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
             <div class="control-group span14">
                <label class="control-label">日期范围：</label>
                <div class="bui-form-group" data-rules="{dateRange : true}">
                  <input name="startDate" type="text" class="calendar1" placeholder="起始日期" />
                  <span>&nbsp;-&nbsp;</span>
                  <input name="endDate" type="text" class="calendar1" placeholder="结束日期" />
                </div>
                <div style="margin-right: auto;margin: -30px 425px;">
                  <select onchange="changeFormatDateOf(this,'startDate','endDate')" id="selectDateId" style="width:85px;">
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
            <div class="control-group span7" >
              <label class="control-label">会员账号：</label>
              <input name="loginaccount"  type="text" data-tip='{"text":"会员账号"}' class="control-text" placeholder="会员账号"/>
            </div>
            <div class="control-group span7 ">
                <label class="control-label">游戏平台：</label>
                <select id="gametype" name="gametype" >
                         <option value="" selected="selected">请选择</option>
                         <c:forEach var="game" varStatus="status" items="${listGame}">
                            <option value="${game.gametype}">${game.gamename}</option>
                        </c:forEach>
                 </select>
            </div>
            
            <c:if test="${listEnterprise != null}">
            <div class="control-group span7">
              <label class="control-label">企业：</label>
                <select id="enterprisecode" name="enterprisecode" >
                         <option value="" selected="selected">请选择</option>
                         <c:forEach var="item" varStatus="status" items="${listEnterprise}">
	                    	<option value="${item.enterprisecode }">${item.enterprisename }</option>
	                    </c:forEach>
                 </select>
            </div>
          	</c:if>
          	
          </div>
               <div style="position: absolute;right: -25px;top: 3px;">
                  <button id="btnSearch" type="submit"  class="button button-primary"> <span class="icon-search icon-white"></span>搜 索 </button>
                 <input type="reset" style="display:none;" id="resetId" />
            </div>
        </div>
    </div>
</form>
<ul style="border:5px; height:20px; line-height:20px; color:red; font-weight:bold;" class="breadcrumb">
	<li>
	<span style="font-size: 12px">
	数据截止到昨日。每日11：00汇总昨日的数据。
	</span>
	</li>
</ul>
<div class="search-grid-container well">
  <div id="grid"></div>
</div>
</div>

</body>
</html>
<script type="text/javascript">
var export_excel = (1 == 1); 
var botton_arry = new Array();

if(export_excel){
	botton_arry.push({
	          btnCls:'button button-primary',
	          text:'<span class="icon-file icon-white"></span>导出到EXCEL(当前条件)',
	          handler:function(){
	          	searchForm.action = "${ctx}/bettingallday/dayreportExcel";
	          	searchForm.submit();
	          	searchForm.action = "${ctx}/bettingallday/dayreportData";
	          }
	});
}

//默认起始时间，结束时间
function getDateOf(){
	var d = new Date();
	var vYear = d.getFullYear();
	var vMon = d.getMonth() + 1;
	var vDay = d.getDate();
	var vDay1 = d.getDate();
	var h = d.getHours(); 
	var m = d.getMinutes(); 
	var se = d.getSeconds(); 
	var endtime = vYear+""+(vMon<10 ? "0" + vMon : vMon)+""+(vDay<10 ? "0"+ vDay : vDay);
	return endtime;
}
function getYMDof(date){
	 
    var myyear = date.getFullYear();
    var mymonth = date.getMonth()+1;
    var myweekday = date.getDate();
    if(mymonth < 10){
        mymonth = "0" + mymonth;
    }
    if(myweekday < 10){
        myweekday = "0" + myweekday;
    }
    
    return myyear+""+mymonth + "" + myweekday;
    
}
//格式化日期：yyyy-MM-dd
function changeFormatDateOf(obj,begin_Date,end_Date) {
	var getNowDate = new Date(nowYear, nowMonth, nowDay);//现在时间
	var getNowDate =  getYMDof(getNowDate);
	var value = $(obj).val();
	switch(value){
	case "":
		$("input[name="+begin_Date+"]").val("");
		$("input[name="+end_Date+"]").val("");
		break;
	case "1":
		var getYesterdayDate = new Date(nowYear, nowMonth, nowDay);
		var getYesterdayDate =  getYMDof(getYesterdayDate);
		$("input[name="+begin_Date+"]").val(getYesterdayDate);
		$("input[name="+end_Date+"]").val(getYesterdayDate);

		break;
		
	case "2":
		var getYesterdayDate = new Date(nowYear, nowMonth, nowDay-1);
		var getYesterdayDate =  getYMDof(getYesterdayDate);
		$("input[name="+begin_Date+"]").val(getYesterdayDate);
		$("input[name="+end_Date+"]").val(getYesterdayDate);
		break;
	case "3":
		var getYesterdayDate = new Date(nowYear, nowMonth, nowDay-3);
		var getYesterdayDate =  getYMDof(getYesterdayDate);
		$("input[name="+begin_Date+"]").val(getYesterdayDate);
		$("input[name="+end_Date+"]").val(getNowDate);
		break;
	case "4":
		 //获得本周的开始日期
		 var getWeekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek);
		 var getWeekStartDate =  getYMDof(getWeekStartDate);
		 //获得本周的结束日期
		 var getWeekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek));
		 var getWeekEndDate =  getYMDof(getWeekEndDate);
		 $("input[name="+begin_Date+"]").val(getWeekStartDate);
		 $("input[name="+end_Date+"]").val(getWeekEndDate);
		break;
	case "5":
	    //获得上周的开始日期
	    var getUpWeekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek -7);
	    var getUpWeekStartDate =  getYMDof(getUpWeekStartDate);
	    //获得上周的结束日期
	    var getUpWeekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek - 7));
	    var getUpWeekEndDate =  getYMDof(getUpWeekEndDate);
		$("input[name="+begin_Date+"]").val(getUpWeekStartDate);
		$("input[name="+end_Date+"]").val(getUpWeekEndDate);
		break;
	case "6":
	    //获得半月的开始日期
	    var getMonthStartDate = new Date(nowYear, nowMonth,1);
	    var getMonthStartDate =  getYMDof(getMonthStartDate);
	    var getMonthEndDate = new Date(nowYear, nowMonth,15);
	    var getMonthEndDate =  getYMDof(getMonthEndDate);
		$("input[name="+begin_Date+"]").val(getMonthStartDate);
		$("input[name="+end_Date+"]").val(getMonthEndDate);
		break;
	case "7":
	    //获得本月的开始日期
	    var getMonthStartDate = new Date(nowYear, nowMonth, 1);
	    var getMonthStartDate =  getYMDof(getMonthStartDate);
	    //获得本月的结束日期
	    var getMonthEndDate = new Date(nowYear, nowMonth, getMonthDays(nowMonth));
	    var getMonthEndDate =  getYMDof(getMonthEndDate);
		$("input[name="+begin_Date+"]").val(getMonthStartDate);
		$("input[name="+end_Date+"]").val(getMonthEndDate);
		break;
	case "8":
	    //获得上月开始时间
	    var getLastMonthStartDate = new Date(nowYear, lastMonth, 1);
	    var getLastMonthStartDate = getYMDof(getLastMonthStartDate);
	    //获得上月结束时间
	    var getLastMonthEndDate = new Date(nowYear, lastMonth, getMonthDays(lastMonth));
	    var getLastMonthEndDate = getYMDof(getLastMonthEndDate);
		$("input[name="+begin_Date+"]").val(getLastMonthStartDate);
		$("input[name="+end_Date+"]").val(getLastMonthEndDate);
		break;
	case "9":
		var getLastMonthStartDate = new Date(nowYear, 0);
		var getLastMonthStartDate = getYMDof(getLastMonthStartDate);
		var getLastMonthEndDate = new Date(nowYear, 5, 30);//上半年，6余额30
		var getLastMonthEndDate = getYMDof(getLastMonthEndDate);
		$("input[name="+begin_Date+"]").val(getLastMonthStartDate);
		$("input[name="+end_Date+"]").val(getLastMonthEndDate);
		break;
	case "10":
		var currentYearFirstDate = new Date(nowYear, 0, 1);
		var currentYearFirstDate = getYMDof(currentYearFirstDate);
		var priorYearLastDay = new Date(nowYear, 11, 31);//年底
		var priorYearLastDay = getYMDof(priorYearLastDay);
		$("input[name="+begin_Date+"]").val(currentYearFirstDate);
		$("input[name="+end_Date+"]").val(priorYearLastDay);
		break;
	default:
		break;

}
}

BUI.use('bui/calendar',function(Calendar){
    var datepicker = new Calendar.DatePicker({
      trigger:'.calendar1',
      dateMask : 'yyyymmdd',
      autoRender : true
    });
  });
  
  
$("input[name='startDate']").val(getDateOf());
$("input[name='endDate']").val(getDateOf());
$('#selectDateId option:eq(1)').attr('selected','selected');

var Grid = BUI.Grid,
  Store = BUI.Data.Store,
  columns = [
    { title: '游戏平台',width: '10%',elCls:'center',  sortable: false,  dataIndex: 'platformtype'},
    { title: '会员账号',width: '15%',elCls:'center',  sortable: false,  dataIndex: 'loginaccount'},
    { title: '玩家账号',width: '15%',elCls:'center',  sortable: false,  dataIndex: 'brandcode', renderer:function(value){
        return "<b>"+value+"</b>";
    }},
    { title: '投注',width: '10%',elCls:'center',  sortable: true, summary : true, dataIndex: 'betmoney', renderer:function(value){
        return value.toFixed(2);
    }},
    { title: '有效投注',width: '10%',elCls:'center',  sortable: true, summary : true, dataIndex: 'validbet', renderer:function(value){
        return value.toFixed(2);
    }},
    { title: '输赢',width: '10%',elCls:'center',  sortable: true, summary : true, dataIndex: 'netmoney', renderer:function(value){
        return value.toFixed(2);
    }},
    { title: '投注数（局）',width: '10%',elCls:'center',  sortable: true,  dataIndex: 'cnt', renderer:function(value){
        return "- 局";
    }},
    
  ];

var store = new Store({
    url : '${ctx}/bettingallday/dayreportData',
    autoLoad:false, 
    pageSize: 50, 
    remoteSort: true,
    sortInfo : {
        field : 'betmoney',
        direction : 'desc'
      },
  }),
  grid = new Grid.Grid({
    render:'#grid',
    autoRender:true,
    loadMask: true,//加载数据时显示屏蔽层
    width:'100%',
    columns : columns,
    store: store,
    plugins : [ Grid.Plugins.CheckSelection, Grid.Plugins.Summary  ],
    emptyDataTpl:'<div class="centered"><img alt="Crying" src="${statics }/img/dataisnull.png"/><font style="color:red;font-size:20px;"><h2>没有找到匹配的数据!</h2></font></div>',
    tbar : { items:botton_arry },
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