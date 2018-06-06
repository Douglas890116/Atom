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
                <div class="control-group span14">
              <label class="control-label">操作时间：</label>
              <div class="bui-form-group" data-rules="{dateRange : true}">
                <input name="dateBegin" type="text" class="calendar calendar-time" placeholder="起始时间"/> 
                <span>&nbsp;-&nbsp;</span>
                <input name="dateEnd" type="text" class="calendar calendar-time" placeholder="结束时间"/>
              </div>
              <div style="margin-right: auto;margin: -30px 425px;">
                 <select onchange="changeFormatDate(this,'dateBegin','dateEnd')" style="width:85px;">
                                <option value="">请选择</option>
                                <option value="1" selected="selected">今天</option>
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
              <label class="control-label">操作人：</label>
                <input name="oprationperson" type="text" data-tip='{"text":"用户账号"}' class="control-text" placeholder="用户账号"/>
          </div>
          <div class="control-group span7">
              <label class="control-label">业务名称：</label>
                <input name="servicename" type="text" data-tip='{"text":"业务名称"}' class="control-text" placeholder="业务名称"/>
          </div>
          <div class="control-group span7">
             <label class="control-label">操作类型：</label>
             <input name="oprationtype" type="text" data-tip='{"text":"操作类型"}' class="control-text" placeholder="操作类型"/>   
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
	   $("input[name='dateBegin']").val(getDate("begintime"));
	   $("input[name='dateEnd']").val(getDate("endtime"));	
        var Grid = BUI.Grid,
          Store = BUI.Data.Store,
          columns = [
            { title: '业务名称',width: '20%',sortable: false,dataIndex: 'servicename'},
            { title: '数据表',width: '20%',sortable: false,dataIndex: 'tablename'},
            { title: '操作类型',width: '10%',sortable: false,dataIndex: 'oprationtype'},
            { title: '操作人',width: '20%',sortable: false,dataIndex: 'oprationperson'},
            { title: '操作时间',width: '30%',sortable: true,dataIndex: 'oprationtime',renderer:BUI.Grid.Format.datetimeRenderer},
            { title: '操作详情',width: 118,sortable: false,renderer:function(value,obj){
            	return '<button class="button button-primary botton-margin" onclick=showOpeartionLogDetail("'+obj.logcode+'")><span class="icon-th-large icon-white"></span>日志详情</button>';
            }}
          ];
        var store = new Store({
            url : '${ctx}/operatingLog/findOperatingLog',
            autoLoad : false,//自动加载数据
            pageSize:15,
            sortInfo : {
                field : 'oprationtime',
                direction : 'desc'
              },
          }),grid = new Grid.Grid({
            render:'#grid',
            autoRender:true,
            loadMask: true,
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
	        if(!sender.hasClass('x-grid-checkbox')){ 
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