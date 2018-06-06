<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body style="padding: 10px;">
<div class="demo-content">
<!-- 搜索页 ================================================== -->
<form id="searchForm" class="form-horizontal" style="outline: none;" method="post">
  <input name="end_hidden" type="hidden" />
  <input name="end_hidden" type="hidden" />
   <input name="employeecode" type="hidden" value="${employeecode}"/>
  <div class="row well" style="margin-left: 0px;position: relative;">
    <div style="position: relative;display: inline-block;">
      <div style="float: left;margin-right: 96px;">
           <div style="float: left;margin-right: 96px;">
            <div class="control-group span14 ">
                <label class="control-label">下注时间：</label>
                <div class="bui-form-group" data-rules="{dateRange : true}">
                  <input name="startDate"  type="text"   class="calendar calendar-time" value="${param.startDate }"  placeholder="起始时间" />
                  <span>&nbsp;-&nbsp;</span>
                  <input  name="endDate" type="text"    class="calendar calendar-time" value="${param.endDate }"  placeholder="结束时间" />
                </div>
                <div style="margin-right: auto;margin: -30px 425px;">
 							<select onchange="changeFormatDate(this,'startDate','endDate')" id="quiklychoicetime" style="width:85px;">
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
                <input name="loginaccount"  type="text" data-tip='{"text":"用户账号"}' class="control-text" value="${param.loginaccount }"  placeholder="用户名称"/>
            </div>
            <div class="control-group span7">
              <label class="control-label">上级账号：</label>
                <input name="parentName"  type="text" data-tip='{"text":"上级账号"}' class="control-text" placeholder="上级账号"/>
           </div>
           <div class="control-group span7">
              <label class="control-label">订单号：</label>
                <input name="bbinWagersId"  type="text" data-tip='{"text":"订单号"}' class="control-text" placeholder="订单号" />
            </div>
            
            <div class="control-group span7">
              <label class="control-label">客户端：</label>
                <select name="bbinOrigin">
                    <option value="">--请选择--</option>
                    <option value="M">行动装置下单</option>
                    <option value="MI1">ios手机</option>
                    <option value="MI2">ios平板</option>
                    <option value="MA1">Android手机</option>
                    <option value="MA2">Android平板</option>
                    <option value="P">计算机下单</option>
                    
                </select>
            </div>
            
            <div class="control-group span7 toggle-display">
              <label class="control-label">派彩结果：</label>
                <select name="bbinIsPaid">
                    <option value="">--请选择--</option>
                    <option value="Y">已派彩</option>
                    <option value="N">未派彩</option>
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
	          	searchForm.action = "${ctx}/BBINGame/exportExcel";
	          	searchForm.submit();
	          	searchForm.action = "${ctx}/BBINGame/data";
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
			{ title: '订单号',width: '8%',  sortable: false,dataIndex:'bbinWagersId'},
			//{ title: 'brandcode',width: 100,  sortable: false,dataIndex:'brandcode'},
			{ title: '用户账号',width: '6%',  sortable: false,dataIndex:'loginaccount'},
			//{ title: '局号',width: '8%',  sortable: false,dataIndex:'bbinSerialId'},
			//{ title: '场次',width: 100,  sortable: false,dataIndex:'bbinRoundNo'},
			{ title: '游戏种类',width: '6%',  sortable: false,dataIndex:'bbinGameType'},
			//{ title: '桌号',width: '8%',  sortable: false,dataIndex:'bbinGameCode'},
			{ title: '下注',width: '18%',  sortable: false, dataIndex:'bbinWagerDetail', renderer:function(value,obj){
				return value;
			}},
			{ title: '开牌结果(庄/闲)',width: '8%',  sortable: false,dataIndex:'bbinResult'},
			//{ title: '注单结果-1：注销、0：未结算',width: 100,  sortable: false,dataIndex:'bbinResultType'},
			{ title: '结果牌',width: '18%',  sortable: false,dataIndex:'bbinCard',renderer:function(value,obj){
				debugger
				var tableHtml ="" ;
				try{
					var result = obj.bbinCard;
					var before;
					var after;
					var center;
					var center2;
					var center3;
					var count = 0;
					if(result){
						var result = obj.bbinCard.split(",");
						if(result[1].indexOf("*")>=0){
							var arr = result[1].split("*");
							before = arr[0];
							after = arr[1];
							if(result[3]){
								center3 = result[3];
							}
							count++;
						}
						if(result[2].indexOf("*")>=0){
							var arr = result[2].split("*");
							before = arr[0];
							after = arr[1];
							center  = result[1];
							center2  = result[3];
							center3  = result[4];
						}
						tableHtml +="<span style=color:red>庄：</span>";
						if(result[0]){
							tableHtml += "<img src='${statics}/img/poker/"+ result[0].replace(".","")+".png" + "' />";	
						}
						if(center){
							tableHtml += "<img src='${statics}/img/poker/"+ center.replace(".","")+".png" + "' />";	
						}
						if(before){
							tableHtml += "<img src='${statics}/img/poker/"+ before.replace(".","")+".png" + "' />";	
						}
						if(count !=0 ){
							tableHtml += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp&nbsp&nbsp&nbsp;&nbsp";
						}
						tableHtml +="&nbsp;&nbsp;&nbsp&nbsp;<span style=color:green>闲：</span>";
						if(after){
							tableHtml += "<img src='${statics}/img/poker/"+ after.replace(".","")+".png" + "' />";
						}
						if(count !=0 ){
							if(result[2]){
								tableHtml += "<img src='${statics}/img/poker/"+ result[2].replace(".","")+".png" + "' />";
							}
						}
						if(center2){
							tableHtml += "<img src='${statics}/img/poker/"+ center2.replace(".","")+".png" + "' />";	
						}if(center3){
							tableHtml += "<img src='${statics}/img/poker/"+ center3.replace(".","")+".png" + "' />";	
						}
						
						
					}
				} catch(e){
					tableHtml = value;
				}
				return tableHtml;
			}},
			{ title: '下注金额',width: '6%',  sortable: false, summary : true, dataIndex:'bbinBetAmount', renderer:function(value){
				return value.toFixed(2);
			}},
			{ title: '输赢',width: '6%',  sortable: false, summary : true, dataIndex:'bbinPayoff', renderer:function(value,obj){
				return (obj.bbinPayoff ).toFixed(2);
			}},
			//{ title: '币别',width: 100,  sortable: false,dataIndex:'bbinCurrency'},
			//{ title: '与人民币的汇率',width: 100,  sortable: false,dataIndex:'bbinExchangeRate'},
			{ title: '有效投注额',width: '6%',  sortable: false, summary : true, dataIndex:'bbinCommissionable', renderer:function(value){
				return value.toFixed(2);
			}},
			//{ title: '退水',width: '8%',  sortable: false,dataIndex:'bbinCommission'},
			//{ title: '1.行动装置下单：M 1-1.ios手机：MI1 1-2.ios平板：MI2 1-3.Android手机：MA1 1-4.Android平板：MA2 2.计算机下单：P',width: 100,  sortable: false,dataIndex:'bbinOrigin'},
			//{ title: '注单变更时间',width: '8%',  sortable: false,dataIndex:'bbinModifiedDate'},
			{ title: '派彩结果',width: '6%', sortable: false,dataIndex:'bbinIsPaid',renderer:function(value,obj){
				switch(value){
    				case 'Y':
    					return "已派彩";
    					break;
    				case 'N':
    					return "未派彩";
    					break;
				}
			}},
			{ title: '下注时间',width: '12%',  sortable: true,dataIndex:'bbinWagersDate',renderer:BUI.Grid.Format.datetimeRenderer}
			//{ title: '帐务时间',width: 100,  sortable: false,dataIndex:'bbinOrderDate'}
          ];

        var store = new Store({
            url : '${ctx}/BBINGame/data',
            autoLoad : false, //自动加载数据
            pageSize:15,
            remoteSort: true,
            sortInfo : {
                field : 'bbin_Wagers_Date',
                direction : 'desc'
              },
          }),grid = new Grid.Grid({
            render:'#grid',
            autoRender:true,
            loadMask: true,//加载数据时显示屏蔽层
            width:'100%',
            columns : columns,
            store: store,
            plugins : [Grid.Plugins.CheckSelection , Grid.Plugins.Summary ],
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