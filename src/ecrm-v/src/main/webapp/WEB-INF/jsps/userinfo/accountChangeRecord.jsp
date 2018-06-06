<%@page import="java.util.Map"%>
<%@page import="com.maven.entity.EnterpriseEmployeeType.Type"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body style="padding: 10px;">
    <div class="demo-content" >
<!-- 搜索页 ================================================== -->
<form id="searchForm" class="form-horizontal" style="outline: none;" method="post">
 <input name="end_hidden" type="hidden" />
<div class="row well" style="margin-left: 0px;position: relative;">
    <div style="position: relative;display: inline-block;">
      <div style="float: left;margin-right: 126px;">
            <div class="control-group span13">
              <label class="control-label">账变日期：</label>
              <div class="bui-form-group" data-rules="{dateRange : true}">
                <input name="moneyChangeDate_begin" type="text" class="calendar calendar-time" value="${param.moneyChangeDate_begin }" placeholder="起始时间"/> 
                <span>&nbsp;-&nbsp;</span>
                <input name="moneyChangeDate_end" type="text" class="calendar calendar-time" value="${param.moneyChangeDate_end }" placeholder="结束时间"/>
              </div>
              <div style="margin-right: auto;margin: -30px 425px;">
                  <select onchange="changeFormatDate(this,'moneyChangeDate_begin','moneyChangeDate_end')"  id="quiklychoicetime" style="width:85px;">
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
          <div class="control-group span6">
              <label class="control-label">用户账号：</label>
                <input name="loginaccount" type="text" data-tip='{"text":"用户账号"}' class="control-text"  value="${param.loginaccount }" placeholder="用户账号"/>
            </div>
            <div class="control-group span6" >
              <label class="control-label">上级账号：</label>
              <input name="parentName" type="text" data-tip='{"text":"上级账号"}' class="control-text" placeholder="上级账号"/>
            </div>
            <div class="control-group span6">
                <label class="control-label">单号：</label>
                <input name="ordernumber" type="text" data-tip='{"text":"单号"}' class="control-text"  value="${param.ordernumber }" placeholder="订单号"/>
            </div>
            <div class="control-group span6">
              <label class="control-label">账变类型：</label>
                <select name="moneychangetypecode">
                  <option value="">--请选择--</option>
                </select>
            </div>
            <div class="control-group span3" style="margin-top: 10px;">
            	<input type="hidden" name="notMoneryInOut" id="notMoneryInOut" value="on"/>
                <input type="checkbox" name="checkbox1"  checked="checked" onclick="dochange(this)"/>不含转分信息
            </div>

            <div class="control-group span7 toggle-display">
              <label class="control-label">账变金额：</label>
                <input type="text" name="moneychangeamountstart" data-tip="{'text':'起始值'}"  class="control-text span2" placeholder="金额上限">-
                <input type="text" name="moneychangeamountend"   data-tip="{'text':'结束值'}"  class="control-text span2" placeholder="金额下限">
            </div>
            <div class="control-group span6 toggle-display">
              <label class="control-label">冲正原因：</label>
                <select name="moneyaddtype">
                  <option value="">--请选择--</option>
                  <c:forEach var="item" items="${moneyaddtypes }" varStatus="i">
                    	<option value="${item.value }">${item.desc }</option>
                    </c:forEach>
                </select>
            </div>
        </div>  
         <div style="position: absolute;right: 15px;top: 3px;">
<!--          toggle-display -->
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
   <script type="text/javascript">
   var begintime = $("input[name='moneyChangeDate_begin']");
   var endtime = $("input[name='moneyChangeDate_end']");
   if(!begintime.val()&&!endtime.val()){
	   begintime.val(getDate("begintime"))
	   endtime.val(getDate("endtime"));
	   $("#quiklychoicetime option:eq(1)").attr("selected","selected");
   }
   
   function dochange(obj) {
	   if(obj.checked) {
		   $("#notMoneryInOut").val("on");
	   } else {
		   $("#notMoneryInOut").val("off");
	   }
   }
   
   function permissionValidate(){
   	var array= new Array();
  		array.push({
           btnCls : 'button button-primary',
           text:'<span class="icon-file icon-white"></span>导出到EXCEL(当前条件)',
           handler : function(){
           	
           	searchForm.action = "${ctx}/employeeMoneyChange/excelAccountChange";
           	searchForm.submit();
           	searchForm.action = "${ctx}/employeeMoneyChange/findAccountChange";
           }
       });
  		return array;
	 }
   
   function openTableUser(data){
	   $("[name='loginaccount']").val(data);
	   var obj = BUI.FormHelper.serializeToObject($("#searchForm"));
       obj.start = 0;
	   store.load(obj);
   }
   
   loadEmployeeMoneyChangeType();
        var Grid = BUI.Grid,
          Store = BUI.Data.Store,
          columns = [
            { title: '用户账号',width: '8%',sortable: false,dataIndex: 'loginaccount', renderer:function(value,data){
            	//var openparame = {id:'#MN004W',href:'/EEmployee/userJsp/index?search='+data,title:'用户信息'};
            	return "<a href=\"javascript:openTableUser('"+value+"')\">"+value+"</a>";
            }},
            { title: '用户别名',width: '6%',sortable: false,dataIndex: 'employeename'},
            { title: '账变类型',width: '8%',sortable: false,dataIndex: 'moneychangetypename'},
            { title: '备注',width: '28%',sortable: false,dataIndex: 'moneyinoutcomment',renderer:function(value,data){
            	return value+'<button type="button" class="button  button-success " style="visibility:hidden;"><span class="icon-repeat icon-white"></span></button>';
            }},
            { title: '帐变前余额',width: '7%',sortable: false,dataIndex: 'settlementamount', summary : true,renderer:function(value,data){
            	return parseFloat(value).toFixed(2);
            }},
            { title: '账变金额',width: '7%',sortable: false,dataIndex: 'moneychangeamount', summary : true,renderer:function(value,data){
            	return '<font color=red><b>'+parseFloat(value).toFixed(2)+'</b></font>';
            }},
            { title: '账变后金额',width: '7%',sortable: false,dataIndex: 'afteramount', summary : true,renderer:function(value,data){
            	return parseFloat(value).toFixed(2);
            }},
            { title: '账变时间',width: '10%',sortable: true,dataIndex: 'moneychangedate',renderer:BUI.Grid.Format.datetimeRenderer},
            { title: '单号',width: '16%',sortable: false,dataIndex: 'ordernumber'}
            
          ];
        var store = new Store({
            url : '${ctx}/employeeMoneyChange/findAccountChange',
            autoLoad : false,
            pageSize:15,
            sortInfo : {
                          field : 'timesort',
                          direction : 'desc'
                        },
            remoteSort: true
          }),
          grid = new Grid.Grid({
            render:'#grid',
            autoRender:true,
            loadMask: true,//加载数据时显示屏蔽层
            columns : columns,
            width:'100%',
            store: store,
            plugins : [ Grid.Plugins.CheckSelection , Grid.Plugins.Summary],
            emptyDataTpl:'<div class="centered"><img alt="Crying" src="${statics }/img/dataisnull.png"/><font style="color:red;font-size:20px;"><h2>没有找到匹配的数据!</h2></font></div>',
            tbar : {
				items : permissionValidate()
			},
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