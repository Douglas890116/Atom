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
                <label class="control-label">时间范围：</label>
                <div class="bui-form-group" data-rules="{dateRange : true}">
                  <input name="startDate" type="text" class="calendar calendar-time" placeholder="起始时间"/>
                  <span>&nbsp;-&nbsp;</span>
                  <input name="endDate" type="text" class="calendar calendar-time" placeholder="结束时间"/>
                </div>
                <div style="margin-right: auto;margin: -30px 420px;">
                  <select onchange="changeFormatDate(this,'startDate','endDate')" id="selectDateId" style="width:85px;">
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
            <div class="control-group span6" >
              <label class="control-label">会员账号：</label>
              <input name="loginaccount"  type="text" class="control-text" placeholder="会员账号"/>
            </div>
            <div class="control-group span6" >
              <label class="control-label">上级账号：</label>
              <input name="parentName"  type="text" class="control-text" placeholder="上级账号"/>
            </div>
            <div class="control-group span6 ">
                <label class="control-label">游戏平台：</label>
                <select id="gamePlatform" name="gamePlatform" >
                         <option value="" selected="selected">请选择</option>
                         <c:forEach var="game" varStatus="status" items="${listGame}">
                            <option value="${game.gametype}">${game.gamename}</option>
                        </c:forEach>
                 </select>
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
var export_excel = (1 == 1); 
var botton_arry = new Array();

if(export_excel){
	botton_arry.push({
	          btnCls:'button button-primary',
	          text:'<span class="icon-file icon-white"></span>导出到EXCEL(当前条件)',
	          handler:function(){
	          	searchForm.action = "${ctx}/bettingallday/export";
	          	searchForm.submit();
	          	searchForm.action = "${ctx}/bettingallday/report";
	          }
	});
}
$("input[name='startDate']").val(getDate("begintime"));
$("input[name='endDate']").val(getDate("endtime"));
$('#selectDateId option:eq(1)').attr('selected','selected');

var Grid = BUI.Grid,
  Store = BUI.Data.Store,
  columns = [
    { title: '流水号',width: '30%',elCls:'center',  sortable: false,  dataIndex: 'platformid'},
    { title: '会员账号',width: '10%',elCls:'center',  sortable: false,  dataIndex: 'loginaccount'},
    { title: '游戏平台',width: '10%',elCls:'center',  sortable: false,  dataIndex: 'platformtype'},
    /* { title: '游戏种类',width: '6%',elCls:'center',  sortable: false,  dataIndex: 'gamebigtype'}, */
    { title: '投注额',width: '10%',elCls:'center',  sortable: true, summary : true, dataIndex: 'betmoney', renderer:function(value){
        return value.toFixed(2);
    }},
    { title: '有效投注额',width: '10%',elCls:'center',  sortable: true, summary : true, dataIndex: 'validbet', renderer:function(value){
        return value.toFixed(2);
    }},
    { title: '输赢值',width: '10%',elCls:'center',  sortable: true, summary : true, dataIndex: 'netmoney', renderer:function(value){
        return value.toFixed(2);
    }},
    { title: '投注日期',width: '15%',elCls:'center',  sortable: false,  dataIndex: 'bettime', renderer : BUI.Grid.Format.datetimeRenderer}
    
  ];

var store = new Store({
    url : '${ctx}/bettingallday/report',
    autoLoad:false, 
    pageSize:15,
    remoteSort: true,
    sortInfo : {
        field : 'bettime',
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