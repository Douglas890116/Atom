<%@page import="com.maven.util.JSONUnit"%>
<%@page import="com.maven.game.enums.NHQGameType"%>
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
                <div class="control-group span14">
                <label class="control-label">游戏时间：</label>
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
              <label class="control-label">用户账号：</label>
                <input name="loginaccount"  type="text" data-tip='{"text":"用户账号"}' value="${param.loginaccount }" class="control-text"  placeholder="用户名称"/>
            </div>
            <div class="control-group span7">
              <label class="control-label">上级账号：</label>
                <input name="parentName"  type="text" data-tip='{"text":"上级账号"}' class="control-text"  placeholder="上级账号"/>
            </div>
           <div class="control-group span7">
              <label class="control-label">投注单号：</label>
                <input name="bettingId"  type="text" data-tip='{"text":"投注单号"}' class="control-text" placeholder="投注单号"/>
            </div>
            <div class="control-group span7">
              <label class="control-label">局号：</label>
                <input name="gamblingCode"  type="text" data-tip='{"text":"局号"}' class="control-text" placeholder="局号"/>
            </div>
            
            <div class="control-group span7">
              <label class="control-label">游戏类型：</label>
                <select name="gameType">
                    <option value="">--请选择--</option>
                    <option value="1">百家乐</option>
                    <option value="2">龙虎</option>
                    <option value="3">番摊</option>
                    <option value="4">轮盘</option>
                    <option value="5">骰宝</option>
                    <option value="6">极速百家乐</option>
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
            <div class="control-group span7 toggle-display">
              <label class="control-label">投注金额：</label>
              <input name="bettingCredits"  type="text" data-tip='{"text":"投注金额"}' class="control-text" placeholder="投注金额"/>
            </div>
          </div>  
      </div>
      ${getJKeyType}
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
                searchForm.action = "${ctx}/NHQGame/exportExcel";
                searchForm.submit();
                searchForm.action = "${ctx}/NHQGame/data";
              }
    });
   }
   
//   var  gameType = ${JSONUnit.getJSONString(NHQGameType.getGameType())};
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
			{ title: '投注单号', width: '8%', sortable: false,  dataIndex: 'bettingId'},
			{ title: '局号', width: '10%', sortable: false,  dataIndex: 'gamblingCode'},
			{ title: '用户账号',width: '6%',  sortable: false,dataIndex:'loginaccount'},
			{ title: '游戏类型',width: '8%',  sortable: false,  dataIndex: 'gameType',renderer:function(value,obj){
            	switch(value){
 				case '1':
 					return "百家乐";
 					break;
 				case '2':
 					return "龙虎";
 					break;
 				case '3':
 					return "番摊";
 				case '4':
 					return "轮盘";
 					break;
 				case '5':
 					return "骰宝";
 					break;
 				case '6':
 					return "极速百家乐";
 					break;
 				}
            }},
            
			{ title: '下注',width: '4%',  sortable: false,dataIndex:'bettingPoint', renderer:function(value){
        		return "<font color='#2A1475'><b>"+value+"</b></font>";
        	}},
			{ title: '游戏结果',   width: '15%',  sortable: false,dataIndex:'gameResult',renderer:function(value,obj){
				var gameType = obj.gameType;
				if(gameType =="1" || gameType =="6"){
					var tableHtml ="" ;
					var result = obj.gameResult.split(',');//HK,S4,H9,H4,DA,#
					/** 
					info.put("player1", pokers[0]);
					info.put("player2", pokers[1]);
					info.put("player3", pokers[2]);
					info.put("banker1", pokers[3]);
					info.put("banker2", pokers[4]);
					info.put("banker3", pokers[5]);
					 */
					 
					 tableHtml +="<span style=color:green>闲：</span>";
					tableHtml += "<img src='${statics}/img/poker/"+ result[0].toUpperCase()+".png" + "' />";	
					tableHtml += "<img src='${statics}/img/poker/"+ result[1].toUpperCase()+".png" + "' />";	
					if(result[2] !="" && result[2] !="#"){
						tableHtml += "<img src='${statics}/img/poker/"+ result[2].toUpperCase()+".png" + "' />";	
					}
					
					
					tableHtml +="&nbsp;&nbsp;&nbsp&nbsp;<span style=color:red>庄：</span>";
					tableHtml += "<img src='${statics}/img/poker/"+ result[3].toUpperCase()+".png" + "' />";
					tableHtml += "<img src='${statics}/img/poker/"+ result[4].toUpperCase()+".png" + "' />";
					if(result[5] !="" && result[5] !="#"){
						tableHtml += "<img src='${statics}/img/poker/"+ result[5].toUpperCase()+".png" + "' />";
					}
					
					return tableHtml;
				}else if(gameType =="2"){
					var tableHtml ="" ;
					var result = obj.gameResult.split(',');
					tableHtml +="<span style=color:red>龙：</span>";
					if(result[0] !="" && result[0] !="#"){
						tableHtml += "<img src='${statics}/img/poker/"+ result[0].toUpperCase()+".png" + "' />";	
					}
					tableHtml +="&nbsp;&nbsp;&nbsp&nbsp;<span style=color:green>虎：</span>";
					if(result[1] !="" && result[1] !="#"){
						tableHtml += "<img src='${statics}/img/poker/"+ result[1].toUpperCase()+".png" + "' />";
					}else{
						return obj.gameResult;
					}
					return tableHtml;
				}else if(gameType =="3"){
					return obj.gameResult;
				}else if(gameType =="4"){
					return obj.gameResult;
				}else if(gameType =="5"){
					return obj.gameResult;
				}
            }},
            
			{ title: '桌号',  width: '8%', sortable: false,  dataIndex: 'tableName'},
			//{ title: '局号',  width: '8%', sortable: false,  dataIndex: 'setGameNo'},
			//{ title: '靴号',   width: '8%',  sortable: false,dataIndex:''},
            //{ title: '投注类型',width: '5%',  sortable: false,  dataIndex: 'aoiGameBettingKind'},
            //{ title: '投注区', width: 70, sortable: false, dataIndex: 'aoiGameBettingContent'},
            //{ title: '游戏结果类型',   width: '8%',  sortable: false, dataIndex: 'aoiResultType'},
            { title: '投注金额',   width: '8%',  sortable: false,  summary : true, dataIndex: 'bettingCredits', renderer:function(value){
        		return value.toFixed(2);
        	}},
            //{ title: '赔率',   width: '8%',  sortable: false,   dataIndex: ''},
            //{ title: '有效投注额',   width: 100,  sortable: false,   dataIndex: ''},
            { title: '输赢金额',   width: '8%',  sortable: false, summary : true,  dataIndex: 'winningCredits' , renderer:function(value){
        		return value.toFixed(2);
        	}},
            { title: '有效投注额',   width: '10%',  sortable: false, summary : true,  dataIndex: 'washCodeCredits', renderer:function(value){
        		return value.toFixed(2);
        	}},
            { title: '游戏时间',   width: '10%',  sortable: true,   dataIndex: 'bettingDate',renderer:BUI.Grid.Format.datetimeRenderer},
            //{ title: '供应商Id',   width: 100,  sortable: false,   dataIndex: 'aoiVendorId'},
          ];

        var store = new Store({
            url : '${ctx}/NHQGame/data',
            autoLoad : false, //自动加载数据
            pageSize:15,
            remoteSort: true,
            sortInfo : {
                field : 'Betting_Date',
                direction : 'desc'
              }
          }),grid = new Grid.Grid({
            render:'#grid',
            autoRender:true,
            loadMask: true,
            width:'100%',
            columns : columns,
            store: store,
            plugins : [Grid.Plugins.CheckSelection, Grid.Plugins.Summary ],
            emptyDataTpl:'<div class="centered"><img alt="Crying" src="${statics }/img/dataisnull.png"/><font style="color:red;font-size:20px;"><h2>没有找到匹配的数据!</h2></font></div>',
            tbar : { items:botton_arry },
            // 顶部工具栏
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