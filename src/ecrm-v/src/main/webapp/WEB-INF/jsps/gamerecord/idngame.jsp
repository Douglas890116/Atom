<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<c:set var="isEnglish" value="${sessionScope.LANGUAGE == 'en'}"/>
<body style="padding: 10px;">
<div class="demo-content">
<!-- 搜索页 ================================================== -->
  <form id="searchForm" class="form-horizontal" style="outline: none;" method="post">
  <input name="end_hidden" type="hidden" />
  <input name="employeecode" type="hidden" value="${employeecode}"/>
    <div class="row well" style="margin-left: 0px;position: relative;">
    <div style="position: relative;display: inline-block;">
      <div style="float: left;margin-right: 96px;">
           <div class="control-group span14 ">
                <label class="control-label">${isEnglish?'Bet Time':'投注时间'}：</label>
                <div class="bui-form-group" data-rules="{dateRange : true}">
                  <input name="startDate"  type="text" class="calendar calendar-time" value="${param.startDate }"  placeholder="起始时间" />
                  <span>&nbsp;-&nbsp;</span>
                  <input  name="endDate" type="text" class="calendar calendar-time" value="${param.endDate }"   placeholder="结束时间" />
                </div>
                <div style="margin-right: auto;margin: -30px 425px;">
                  <select onchange="changeFormatDate(this,'startDate','endDate')"  id="quiklychoicetime"   style="width:85px;">
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
            <div class="control-group span7">
              <label class="control-label">${isEnglish?'Account':'用户账号'}：</label>
                <input name="loginaccount"  type="text" data-tip='{"text":"用户账号"}' value="${param.loginaccount }"  class="control-text" placeholder="会员账号"/>
            </div>
            <div class="control-group span7">
              <label class="control-label">上级账号：</label>
                <input name="parentName"  type="text" data-tip='{"text":"上级账号"}' class="control-text" placeholder="上级账号"/>
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
            <%-- <div class="control-group span7">
              <label class="control-label">游戏名称：</label>
              <input name="gamename"  type="text" data-tip='{"text":"游戏名称"}' value="${param.gamename }"  class="control-text" placeholder="游戏名称"/>
            </div> --%>
      </div>
  	  <div style="position: absolute;right: 15px;top: 3px;">
     	 <button id="btnSearch" type="submit"  class="button button-primary"> ${isEnglish?'Search':'搜 索'} </button>
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
              text:'<span class="icon-file icon-white"></span> ${isEnglish?"Export Excel":"导出到EXCEL(当前条件)"}',
              handler:function(){
                searchForm.action = "${ctx}/idngame/exportExcel";
                searchForm.submit();
                searchForm.action = "${ctx}/idngame/data";
              }
    });
   }
   
   $(function(){
	   var begintime = $("input[name='startDate']");
	   var endtime = $("input[name='endDate']");
	   if(!begintime.val()&&!endtime.val()){
		   begintime.val(getDate("begintime"));
		   endtime.val(getDate("endtime"));
		   $("#quiklychoicetime option:eq(1)").attr("selected","selected");
	   }
	   
        var Grid = BUI.Grid,
          Store = BUI.Data.Store,
          columns = [
			/* 
			{ title: '投注单号',   width: '11%',  sortable: true,dataIndex:'transactionno'},
			{ title: '游戏代号',   width: '12%',  sortable: false,dataIndex:'game'},
			{ title: '桌号',   width: '15%',  sortable: false,dataIndex:'bettable'},
			{ title: '结果',   width: '15%',  sortable: false,dataIndex:'card'},
			
			{ title: '下注金额',   width: '10%',  sortable: false,summary : true, dataIndex:'betmoney', renderer:function(value){
				return parseFloat(value).toFixed(2);
			}},
			{ title: '输赢金额',   width: '10%',  sortable: false,summary : true, dataIndex:'netmoney', renderer:function(value){
				return parseFloat(value).toFixed(2);
			}},
			{ title: '下注后余额',   width: '10%',  sortable: false,summary : false, dataIndex:'total', renderer:function(value){
				return parseFloat(value).toFixed(2);
			}},
			 */
			 { title: '${isEnglish?"Account":"会员账号"}',   width: '10%',  sortable: false,dataIndex:'loginaccount'},
/* 			 { title: '全部投注额',   width: '10%',  sortable: false,summary : false, dataIndex:'totalturnover', renderer:function(value){
					return parseFloat(value).toFixed(2);
				}}, */
			{ title: '${isEnglish?"Rebate(RMB)":"抽水(人民币)"}',   width: '10%',  sortable: false,summary : true, dataIndex:'agentcommission', renderer:function(value){
				return parseFloat(value).toFixed(2);
			}},
			{ title: '${isEnglish?"Rebate(USD)":"抽水(美元)"}',   width: '10%',  sortable: false,summary : true, dataIndex:'livepoker', renderer:function(value){
				return parseFloat(value).toFixed(2);
			}},
			/* { title: '代理费',   width: '10%',  sortable: false,summary : false, dataIndex:'agentbill', renderer:function(value){
				return parseFloat(value).toFixed(2);
			}}, */
			
			{ title: '${isEnglish?"Record Time":"记录时间"}',   width: '20%',  sortable: true,dataIndex:'turnoverdate',renderer:BUI.Grid.Format.datetimeRenderer}
          ];

        var store = new Store({
            url : '${ctx}/idngame/data',
            autoLoad : false,
            pageSize:15,
            remoteSort: true,
            sortInfo : {
              field : 'turnoverdate',
              direction : 'desc'
            }
          }),
          grid = new Grid.Grid({
            render:'#grid',
            autoRender:true,
            loadMask: true,
            width:'100%',
            columns : columns,
            store: store,
            plugins : [Grid.Plugins.CheckSelection, Grid.Plugins.Summary],
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
   })
</script>