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
                  <input name="startDate"  type="text" class="calendar calendar-time" value="${param.startDate }" placeholder="起始时间" />
                  <span>&nbsp;-&nbsp;</span>
                  <input  name="endDate" type="text" class="calendar calendar-time" value="${param.endDate }" placeholder="结束时间" />
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
              <label class="control-label">用户账号：</label>
                <input name="loginaccount"  type="text" data-tip='{"text":"用户账号"}' value="${param.loginaccount }"  class="control-text" placeholder="会员账号"/>
            </div>
            <div class="control-group span7">
              <label class="control-label">上级账号：</label>
                <input name="parentName"  type="text" data-tip='{"text":"上级账号"}' class="control-text"  placeholder="上级账号"/>
            </div>
           <div class="control-group span7">
              <label class="control-label">投注单号：</label>
                <input name="turnNumber"  type="text" data-tip='{"text":"投注单号"}' value="${param.turnNumber }" class="control-text" placeholder="投注单号"/>
            </div>
            
            <div class="control-group span7">
              <label class="control-label">游戏类型：</label>
              <select name="kindId">
                  <option value="">--请选择--</option>
                  <option value="1000">百家乐</option>
                  <option value="2000">斗地主</option>
                  <option value="2005">二人麻将</option>
                  <option value="2001">诈金花</option>
                  <option value="2003">牛牛</option>
                  <option value="2002">德州扑克</option>
                  <option value="2004">港式五张</option>
              </select>
           </div>
            
            <div class="control-group span7">
              <label class="control-label">房间类型：</label>
              <select name="roomName">
                  <option value="">--请选择--</option>
                  <option value="百家乐房间">百家乐房间</option>
                  <option value="斗地主体验房">斗地主体验房</option>
                  <option value="斗地主0.2元">斗地主0.2元</option>
                  <option value="斗地主3元">斗地主3元</option>
                  <option value="二人麻将房间5元">二人麻将房间5元</option>
                  <option value="二人麻将房间10元">二人麻将房间10元</option>
                  
                  <option value="二人麻将房间20元">二人麻将房间20元</option>
                  <option value="诈金花0.2元">诈金花0.2元</option>
                  <option value="诈金花1元">诈金花1元</option>
                  <option value="诈金花3元">诈金花3元</option>
                  <option value="牛牛0.6元">牛牛0.6元</option>
                  <option value="牛牛2元">牛牛2元</option>
                  <option value="牛牛6元">牛牛6元</option>
                  <option value="德州扑克0.6元">德州扑克0.6元</option>
                  <option value="德州扑克4元">德州扑克4元</option>
                  <option value="德州扑克10元">德州扑克10元</option>
                  <option value="港式五张0.6元">港式五张0.6元</option>
                  <option value="港式五张2元">港式五张2元</option>
                  <option value="港式五张6元">港式五张6元</option>
                  <option value="德州扑克2元">德州扑克2元</option>
                  
                  <option value="德州扑克6元">德州扑克6元</option>
                  <option value="二人麻将房间50元">二人麻将房间50元</option>
                  <option value="诈金花5元">诈金花5元</option>
                  <option value="诈金花10元">诈金花10元</option>
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
                searchForm.action = "${ctx}/QwpGame/excel";
                searchForm.submit();
                searchForm.action = "${ctx}/QwpGame/data";
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
			{ title: '投注单号',   width: '11%',  sortable: true,dataIndex:'turnNumber'},
			{ title: '用户账号',width: '12%',  sortable: false,dataIndex:'loginaccount'},
			{ title: '房间名称',   width: '12%',  sortable: false,dataIndex:'roomName'},
			{ title: '底注',   width: '10%',  sortable: false, summary : true, dataIndex:'turnScore', renderer:function(value){
				return parseFloat(value).toFixed(2);
			}},
			{ title: '输赢金额',   width: '10%',  sortable: false, summary : true, dataIndex:'score', renderer:function(value){
				return parseFloat(value).toFixed(2);
			}},
			{ title: '抽水金额',   width: '10%',  sortable: false, summary : true, dataIndex:'revenue', renderer:function(value){
				return parseFloat(value).toFixed(2);
			}},
			{ title: '投注时间',   width: '20%',  sortable: false,dataIndex:'startTime',renderer:BUI.Grid.Format.datetimeRenderer},
			{ title: '结算时间',   width: '20%',  sortable: false,dataIndex:'recordTime',renderer:BUI.Grid.Format.datetimeRenderer}
          ];

        var store = new Store({
            url : '${ctx}/QwpGame/data',
            autoLoad : false,
            pageSize:15,
            remoteSort: true,
            sortInfo : {
              field : 'recordTime',
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
            tbar : { items : botton_arry },
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
    