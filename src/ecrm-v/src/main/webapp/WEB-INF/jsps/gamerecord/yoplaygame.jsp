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
           <div style="float: left;margin-right: 96px;">
          <div class="control-group span14 ">
                <label class="control-label">投注时间：</label>
                <div class="bui-form-group" data-rules="{dateRange : true}">
                  <input name="startDate"  type="text"   class="calendar calendar-time" value="${param.startDate }"  placeholder="起始时间" />
                  <span>&nbsp;-&nbsp;</span>
                  <input  name="endDate" type="text"    class="calendar calendar-time" value="${param.endDate }"   placeholder="结束时间" />
                </div>
                <div style="margin-right: auto;margin: -30px 425px;">
                    <select onchange="changeFormatDate(this,'startDate','endDate')" id="quiklychoicetime"  style="width:85px;">
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
                <input name="loginaccount"  type="text" data-tip='{"text":"会员账号"}' value="${param.loginaccount }"  class="control-text" placeholder="会员账号" />
            </div>
            <div class="control-group span7">
              <label class="control-label">上级账号：</label>
                <input name="parentName"  type="text" data-tip='{"text":"上级账号"}' class="control-text" placeholder="上级账号"/>
           </div>
           <div class="control-group span7">
              <label class="control-label">单号：</label>
                <input name="billNo"  type="text" data-tip='{"text":"单号"}' class="control-text" placeholder="单号" />
            </div>
            
            <div class="control-group span7 ">
              <label class="control-label">游戏类型：</label>
              <select name="gameType">
                 <option value="">--请选择--</option>
                 <option value="YFP">水果派对</option>
                 <option value="YDZ">德州牛仔</option>
                 <option value="YBIR">飞禽走兽</option>
                 <option value="YMFD">森林舞会多人版</option>
                 <option value="YFD">森林舞会</option>
                 <option value="YBEN">奔驰宝马</option>
                 <option value="YHR">极速赛马</option>
                 <option value="YMFR">水果拉霸多人版</option>
                 <option value="YGS">猜猜乐</option>
                 <option value="YFR">水果拉霸</option>
                 <option value="YMGS">猜猜乐多人版</option>
                 <option value="YMBN">百人牛牛</option>
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
            <div class="control-group span7 ">
              <label class="control-label">设备类型：</label>
              <select name="deviceType">
                 <option value="">--请选择--</option>
                 <option value="0">电脑</option>
                 <option value="1">手机</option>
              </select>
            </div>
            
          </div>  
      </div>
      	 <div style="position: absolute;right: 128px;top: 3px;">
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
                searchForm.action = "${ctx}/yoplaygame/exportExcel";
                searchForm.submit();
                searchForm.action = "${ctx}/yoplaygame/data";
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
			{ title: '单号', width: '10%', sortable: false,  dataIndex: 'billNo'},
			{ title: '会员账号',width: '10%',  sortable: false,dataIndex:'playerName'},
            { title: '游戏类型',width: '13%',  sortable: false,  dataIndex: 'gameCode'},
			{ title: '游戏结果',   width: '25%',  sortable: false,dataIndex:'result'},
			{ title: '设备类型',  width: '5%', sortable: false,  dataIndex: 'deviceType', renderer:function(value){
				switch (value) {
				case '0':
					return '电脑';
				case '1':
					return '手机';
				default:
					return value;
				}
			}},
			{ title: '玩家IP',  width: '8%', sortable: false,  dataIndex: 'loginIp'},
            { title: '投注金额',   width: '6%',  sortable: false, summary : true,  dataIndex: 'betmoney', renderer:function(value){
        		return value.toFixed(2);
        	}},
            { title: '有效投注额',   width: '6%',  sortable: false, summary : true,   dataIndex: 'validmoney', renderer:function(value){
        		return value.toFixed(2);
        	}},
            { title: '输赢金额',   width: '6%',  sortable: false,  summary : true,  dataIndex: 'netmoney' , renderer:function(value){
        		return value.toFixed(2);
        	}},
           // { title: '余额',   width: '10%',  sortable: false,   dataIndex: ''},
            { title: '游戏时间',   width: '11%',  sortable: true,   dataIndex: 'betTime',renderer:BUI.Grid.Format.datetimeRenderer},
            //{ title: '供应商Id',   width: 100,  sortable: false,   dataIndex: 'aoiVendorId'},
          ];

        var store = new Store({
            url : '${ctx}/yoplaygame/data',
            autoLoad : false,
            pageSize:15,
            remoteSort: true,
            sortInfo : {
                field : 'bet_Time',
                direction : 'desc'
              },
          }),grid = new Grid.Grid({
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