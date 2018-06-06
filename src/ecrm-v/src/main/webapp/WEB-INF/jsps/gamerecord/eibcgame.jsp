<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
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
                <label class="control-label">投注时间：</label>
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
              <label class="control-label">会员账号：</label>
                <input name="loginaccount"  type="text" data-tip='{"text":"会员账号"}' value="${param.loginaccount }"  class="control-text" placeholder="会员账号"/>
            </div>
            <div class="control-group span7">
              <label class="control-label">上级账号：</label>
                <input name="parentName"  type="text" data-tip='{"text":"上级账号"}' class="control-text" placeholder="上级账号"/>
           </div>
            <div class="control-group span7">
              <label class="control-label">投注单号：</label>
                <input name="transid"  type="text" data-tip='{"text":"投注单号"}' value="${param.transid }"  class="control-text" placeholder="投注单号"/>
            </div>
            
            <div class="control-group span7">
              <label class="control-label">状态：</label>
              <select name="ticketstatus">
                  <option value="">--请选择--</option>
                  <option value="waiting">等待</option>
                  <option value="running">进行中</option>
                  <option value="won">赢</option>
                  <option value="lose">输</option>
                  <option value="draw">平 局</option>
                  <option value="reject">拒 绝</option>
                  <option value="refund">退 钱</option>
                  <option value="void">取 消</option>
                  <option value="half won">上 半 场赢</option>
                  <option value="half lose">上 半 场输</option>
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
            
      </div>
  	  <div style="position: absolute;right: 15px;top: 3px;">
     	 <button id="btnSearch" type="submit"  class="button button-primary"> 搜 索 </button>
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
   	          	searchForm.action = "${ctx}/eibcgame/exportExcel";
   	          	searchForm.submit();
   	          	searchForm.action = "${ctx}/eibcgame/data";
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
			{ title: '投注单号',   width: '10%',  sortable: true,dataIndex:'transid'},
			{ title: '登录账号',   width: '8%',  sortable: true,dataIndex:'loginaccount'},
			{ title: '联赛名称',   width: '15%',  sortable: false,dataIndex:'leaguename'},
			{ title: '客队名称',   width: '12%',  sortable: false,dataIndex:'awayidname'},
			{ title: '主场球队名称',   width: '12%',  sortable: false,dataIndex:'homeidname'},
			
			{ title: '运动类型',   width: '6%',  sortable: false,dataIndex:'sporttype'},
			{ title: '投注类型',   width: '6%',  sortable: false,dataIndex:'bettype'},
			{ title: '投注站团队',   width: '6%',  sortable: false,dataIndex:'betteam'},
			{ title: '赔率',   width: '6%',  sortable: false,dataIndex:'odds'},
			
			{ title: '客队得分',   width: '6%',  sortable: false,dataIndex:'awayscore', renderer:function(value){
				return parseFloat(value).toFixed(2);
			}},
			{ title: '主场得分',   width: '6%',  sortable: false,dataIndex:'homescore', renderer:function(value){
				return parseFloat(value).toFixed(2);
			}},
			{ title: '状态',   width: '6%',  sortable: false,dataIndex:'ticketstatus', renderer:function(value){
				var ticketstatus = "";
				value = value.toLowerCase();
				if(value == "waiting") {
					ticketstatus = "等待";
				} else if(value == "running") {
					ticketstatus = "进行中";
				} else if(value == "won") {
					ticketstatus = "赢";
				} else if(value == "lose") {
					ticketstatus = "输";
				} else if(value == "draw") {
					ticketstatus = "平 局";
				} else if(value == "reject") {
					ticketstatus = "拒 绝";
				} else if(value == "refund") {
					ticketstatus = "退 钱";
				} else if(value == "void") {
					ticketstatus = "取 消";
				} else if(value == "half won") {
					ticketstatus = "上 半 场赢";
				} else if(value == "half lose") {
					ticketstatus = "上 半 场输";
				}
				
				if(ticketstatus == "等待" || ticketstatus == "进行中") {
					return "<font color='red'><b>"+ticketstatus+"</b></font>";
				} else {
					return "<font color='green'><b>"+ticketstatus+"</b></font>";
				}
			}},
			
			{ title: '投注金额',   width: '6%',  sortable: false,summary : true, dataIndex:'betmoney', renderer:function(value,obj){
				if(obj.ticketstatus == "waiting" || obj.ticketstatus == "running") {
					return parseFloat(obj.stake).toFixed(2);
				} else {
					return parseFloat(value).toFixed(2);
				}
			}},
			{ title: '输赢金额',   width: '6%',  sortable: false,summary : true, dataIndex:'netmoney', renderer:function(value){
				return parseFloat(value).toFixed(2);
			}},
			{ title: '投注时间',   width: '10%',  sortable: false,dataIndex:'bettime',renderer:BUI.Grid.Format.datetimeRenderer},
			{ title: '结算时间',   width: '10%',  sortable: false,dataIndex:'nettime',renderer:BUI.Grid.Format.datetimeRenderer}
          ];

        var store = new Store({
            url : '${ctx}/eibcgame/data',
            autoLoad : false,
            pageSize:15,
            remoteSort: true,
            sortInfo : {
              field : 'bettime',
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
            bbar : { pagingBar:true    }
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
    