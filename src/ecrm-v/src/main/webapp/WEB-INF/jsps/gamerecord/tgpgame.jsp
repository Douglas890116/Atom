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
              <label class="control-label">用户账号：</label>
                <input name="loginaccount"  type="text" data-tip='{"text":"用户账号"}' value="${param.loginaccount }"  class="control-text" placeholder="会员账号"/>
            </div>
            <div class="control-group span7">
              <label class="control-label">上级账号：</label>
                <input name="parentName"  type="text" data-tip='{"text":"上级账号"}' class="control-text" placeholder="上级账号"/>
           </div>
            <%-- <div class="control-group span7">
              <label class="control-label">游戏名称：</label>
              <input name="gamename"  type="text" data-tip='{"text":"游戏名称"}' value="${param.gamename }"  class="control-text" placeholder="游戏名称"/>
            </div> --%>
           <div class="control-group span7">
              <label class="control-label">投注单号：</label>
                <input name="betid"  type="text" data-tip='{"text":"投注单号"}' value="${param.betid }" class="control-text" placeholder="投注单号"/>
            </div>
            <div class="control-group span7">
              <label class="control-label">局号：</label>
                <input name="roundid"  type="text" data-tip='{"text":"局号"}' value="${param.roundid }" class="control-text" placeholder="局号"/>
            </div>
           <div class="control-group span7">
              <label class="control-label">平台类型：</label>
              <select name="platformtype">
                  <option value="">--请选择--</option>
                  <option value="Desktop">桌面版</option>
                  <option value="Mobile">移动版</option>
                  <option value="Mini Game">Mini</option>
              </select>
            </div>
            <div class="control-group span7">
              <label class="control-label">游戏类型：</label>
              <select name="gameprovider">
                  <option value="">--请选择--</option>
                  <option value="Red Tiger">Red Tiger</option>
                  <option value="Sunbet">Sunbet</option>
                  <option value="Globalbet">Globalbet</option>
                  <option value="Gold Deluxe">Gold Deluxe</option>
                  <option value="Laxino">Laxino</option>
                  <option value="Fly Cow">Fly Cow</option>
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
                searchForm.action = "${ctx}/tgpgame/exportExcel";
                searchForm.submit();
                searchForm.action = "${ctx}/tgpgame/data";
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
			{ title: '流水号',   width: '13%',  sortable: true,dataIndex:'ugsbetid'},
			{ title: '投注单号',   width: '8%',  sortable: true,dataIndex:'betid'},
			{ title: '局号',   width: '6%',  sortable: true,dataIndex:'roundid'},
			{ title: '结算时间',width: '12%',  sortable: false,dataIndex:'timestamp',renderer:BUI.Grid.Format.datetimeRenderer},
			{ title: '游戏账号',   width: '10%',  sortable: false,dataIndex:'userid'},
			{ title: '游戏类型',   width: '10%',  sortable: false,dataIndex:'gameprovider'},
			{ title: '游戏代码',   width: '6%',  sortable: false,dataIndex:'gameid'},
			{ title: '游戏名称',   width: '10%',  sortable: false,dataIndex:'gamename'},
			{ title: '平台类型',   width: '5%',  sortable: false,dataIndex:'platformtype'},
			
			{ title: '下注金额',   width: '6%',  sortable: false,summary : true, dataIndex:'betmoney', renderer:function(value){
				return parseFloat(value).toFixed(2);
			}},
			{ title: '输赢金额',   width: '6%',  sortable: false,summary : true, dataIndex:'netmoney', renderer:function(value){
				return parseFloat(value).toFixed(2);
			}},
			{ title: '下注后余额',   width: '6%',  sortable: false,summary : true, dataIndex:'postbal', renderer:function(value){
				return parseFloat(value).toFixed(2);
			}},
			{ title: '有效投注额',   width: '6%',  sortable: false,summary : true, dataIndex:'validmoney', renderer:function(value){
				return parseFloat(value).toFixed(2);
			}},
			{ title: '投注时间',   width: '10%',  sortable: false,dataIndex:'bettime',renderer:BUI.Grid.Format.datetimeRenderer}
          ];

        var store = new Store({
            url : '${ctx}/tgpgame/data',
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
    