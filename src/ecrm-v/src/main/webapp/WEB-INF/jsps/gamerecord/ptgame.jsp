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
             <div class="control-group span13">
                <label class="control-label">投注时间：</label>
                <div class="bui-form-group" data-rules="{dateRange : true}">
                  <input name="startDate"  type="text" class="calendar calendar-time" value="${param.startDate }"  placeholder="起始时间" />
                  <span>&nbsp;-&nbsp;</span>
                  <input  name="endDate" type="text" class="calendar calendar-time" value="${param.endDate }"   placeholder="结束时间" />
                </div>
                <div style="margin-right: auto;margin: -30px 423px;">
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
            
            
           <div class="control-group span6">
              <label class="control-label">用户账号：</label>
                <input name="loginaccount"  type="text" data-tip='{"text":"用户账号"}' value="${param.loginaccount }"  class="control-text" placeholder="用户账号" />
           </div>
           <div class="control-group span6">
              <label class="control-label">上级账号：</label>
                <input name="parentName"  type="text" data-tip='{"text":"上级账号"}' class="control-text" placeholder="上级账号" />
           </div>
           <div class="control-group span6">
              <label class="control-label">单号：</label>
                <input name="ptGamecode"  type="text" data-tip='{"text":"单号"}' value="${param.ptGamecode }"  class="control-text" placeholder="单号" />
           </div>
           <div class="control-group span6">
              <label class="control-label">游戏名称：</label>
                <select name="ptGamename">
                    <option value="">--请选择--</option>
                    <c:forEach var="item" items="${list }">
                    	<option value="${item.gamecodeweb}">${item.cnname }（${item.enname }）</option>
                    </c:forEach>
                </select>
                
           </div>
           <div class="control-group span6">
              <label class="control-label">游戏类型：</label>
                <select name="ptGametype">
                    <option value="">--请选择--</option>
                    <option value="Card Games">卡牌游戏</option>
                    <option value="Progressive Slot Machines">奖池老虎机游戏</option>
                    <option value="Scratchcards">刮刮乐游戏</option>
                    <!-- <option value="Scratch Cards">刮刮乐游戏</option> -->
                    <option value="Fixed Odds">固定赔率游戏</option>
                    <!-- <option value="Fixed-Odds Games">固定赔率游戏</option> -->
                    <option value="Table Games">桌面游戏</option>
                    <option value="Table & Card Games">桌面和卡牌游戏</option>
                    <option value="Video Slots">视频老虎机</option>
                    <option value="Live Games">真人视讯游戏</option>
                    <option value="Mini games">迷你游戏</option>
                    <option value="Sidegames">小游戏</option>
                    <option value="Slot Machines">老虎机</option>
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
      </div>
      	 <div style="position: absolute;right: 118px;top: 3px;">
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
                searchForm.action = "${ctx}/PTGame/exportExcel";
                searchForm.submit();
                searchForm.action = "${ctx}/PTGame/data";
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
			//{ title: '游戏编号',width: 150,sortable: false,dataIndex:'ptGamecode'},
			//{ title: '品牌编码',width: 150,sortable: false,dataIndex:'brandcode'},
			{ title: '单号',width: '10%',  sortable: false,dataIndex:'ptGamecode'},
			{ title: '用户账号',width: '15%',  sortable: false,dataIndex:'loginaccount'},
			{ title: '游戏类型',width: '15%',sortable: false,dataIndex:'ptGametype'},
			{ title: '游戏id',width: 150,sortable: false,dataIndex:'ptGameid'},
			{ title: '游戏名称',width: '15%',sortable: false,dataIndex:'ptGamename'},
			//{ title: '窗口代码',width: 150,sortable: false,dataIndex:'ptWindowcode'},
			{ title: '投注金额',width: '15%',sortable: false,summary : true, dataIndex:'ptBet', renderer:function(value){
				return value.toFixed(2);
			}},
			{ title: '输赢金额',width: '15%',sortable: false,summary : true, dataIndex:'ptWin', renderer:function(value){
				return value.toFixed(2);
			}},
			{ title: '余额',width: '15%',sortable: false, summary : true, dataIndex:'ptBalance', renderer:function(value){
				return value.toFixed(2);
			}},
			{ title: '游戏时间',width: '20%',sortable: true,dataIndex:'ptGamedate',renderer:BUI.Grid.Format.datetimeRenderer},
			//{ title: '游戏信息',width: 150,sortable: false,dataIndex:'ptInfo'},
			//{ title: 'sessionID',width: 150,sortable: false,dataIndex:'ptSessionid'},
			//{ title: '逐步下注',width: 150,sortable: false,dataIndex:'ptProgressivebet'},
			//{ title: '进步的胜利',width: 150,sortable: false,dataIndex:'ptProgressivewin'},
			//{ title: '最近赌注',width: 150,sortable: false,dataIndex:'ptCurrentbet'},
			//{ title: '活网工作',width: 150,sortable: false,dataIndex:'ptLivenetwork'},
			//{ title: '序号',width: 150,sortable: false,dataIndex:'ptRnum'}
          ];

        var store = new Store({
            url : '${ctx}/PTGame/data',
            autoLoad : false, //自动加载数据
            pageSize:15,
            remoteSort: true,
            sortInfo : {
                field : 'pt_gamedate',
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