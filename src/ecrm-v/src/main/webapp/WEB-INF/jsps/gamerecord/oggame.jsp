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
                  <input name="startDate"  type="text"   class="calendar calendar-time" value="${param.startDate }"  placeholder="起始时间" />
                  <span>&nbsp;-&nbsp;</span>
                  <input  name="endDate" type="text"    class="calendar calendar-time" value="${param.endDate }"  placeholder="结束时间" />
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
                <input name="loginaccount"  type="text" data-tip='{"text":"用户账号"}' value="${param.loginaccount }"  class="control-text" placeholder="用户名"/>
           </div>
           <div class="control-group span7">
              <label class="control-label">上级账号：</label>
                <input name="parentName"  type="text" data-tip='{"text":"上级账号"}' class="control-text" placeholder="上级账号"/>
           </div>
           <div class="control-group span7">
              <label class="control-label">投注编号：</label>
                <input name="aoiOrderNumber"  type="text" data-tip='{"text":"投注编号"}' class="control-text" placeholder="投注编号"/>
           </div>
           <div class="control-group span7">
              <label class="control-label">游戏类型：</label>
              <select name="aoiGameNameId">
                  <option value="">--请选择--</option>
                  <option value="11">百家乐</option>
                  <option value="12">龙虎</option>
                  <option value="13">轮盘</option>
                  <option value="14">骰宝</option>
                  <option value="15">德州扑克</option>
                  <option value="16">番摊</option>
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
      	 <div style="position: absolute;right: 32px;top: 3px;">
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
                searchForm.action = "${ctx}/OGGame/exportExcel";
                searchForm.submit();
                searchForm.action = "${ctx}/OGGame/data";
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
			/* { title: '订单号',   width: '8%',  sortable: false,dataIndex:'aoiProductId'}, */
			{ title: '投注编号',   width: '8%',  sortable: false,dataIndex:'aoiOrderNumber'},
			//{ title: 'brandcode',   width: 150,  sortable: false,dataIndex:'brandcode'},
			{ title: '会员账号',width: '8%',  sortable: false,dataIndex:'loginaccount'},
			//{ title: '游戏结果',   width: '8%',  sortable: false,dataIndex:'aoiGameRecordId'},
			{ title: '游戏类型',   width: '6%',  sortable: false,dataIndex:'aoiGameNameId',renderer:function(value,obj){
 				switch(value){
 				case '11':
 					return "百家乐";
 					break;
 				case '12':
 					return "龙虎";
 					break;
 				case '13':
 					return "轮盘";
 					break;
 				case '14':
 					return "骰宝";
 					break;
 				case '15':
 					return "德州扑克";
 					break;
 				case '16':
 					return "番摊";
 					break;
 				default:
 					return value;
 				}
 			}},
			{ title: '桌号',   width: '4%',  sortable: false,dataIndex:'aoiTableId'},
			{ title: '局号',   width: '4%',  sortable: false,dataIndex:'aoiStage'},
			{ title: '靴号',   width: '4%',  sortable: false,dataIndex:'aoiInning'},
			{ title: '赔率',   width: '4%',  sortable: false,dataIndex:'aoiCompensateRate' , renderer:function(value,obj){
	        	return parseFloat(value).toFixed(2);
	        }},
			//{ title: '投注类型',   width: '8%',  sortable: false,dataIndex:'aoiGameBettingKind'},
			{ title: '投注内容',   width: '20%',  sortable: false,dataIndex:'aoiGameBettingContent'},
			{ title: '游戏结果',   width: '6%',  sortable: false,dataIndex:'aoiResultType',renderer:function(value,obj){
 				switch(value){
 				case '1':
 					return "<font color='red'>输</font>";
 					break;
 				case '2':
 					return "<font color='green'>赢</font>";
 					break;
 				case '3':
 					return "<font color='blue'>和</font>";
 					break;
 				default:
 					return "<font color='red'>无效</font>";
 				}
 			}},
			{ title: '投注金额',   width: '8%', summary : true,  sortable: false,dataIndex:'aoiBettingAmount', renderer:function(value){
				return value.toFixed(2);
			}},
			
			{ title: '有效投注额',   width: '8%', summary : true, sortable: false,dataIndex:'aoiValidAmount', renderer:function(value){
				return value.toFixed(2);
			}},
			{ title: '输赢金额',   width: '8%', summary : true, sortable: false,dataIndex:'aoiWinLoseAmount', renderer:function(value){
				return value.toFixed(2);
			}},
			//{ title: '余额',   width: 150,  sortable: false,dataIndex:'aoiBalance'},
			{ title: '投注时间',   width: '12%',  sortable: true,dataIndex:'aoiAddTime',renderer:BUI.Grid.Format.datetimeRenderer},
			//{ title: '供应商Id',   width: 150,  sortable: false,dataIndex:'aoiVendorId'},
			
			//{ title: 'aoi_Platform_ID',   width: 150,  sortable: false,dataIndex:'aoiPlatformId'}
          ];

        var store = new Store({
            url : '${ctx}/OGGame/data',
            autoLoad : false,//自动加载数据
            pageSize:15,
            remoteSort: true,
            sortInfo : {
                field : 'aoi_Add_Time',
                direction : 'desc'
              }
          }),grid = new Grid.Grid({
            render:'#grid',
            autoRender:true,
            loadMask: true,
            width:'100%',
            columns : columns,
            store: store,
            plugins : [Grid.Plugins.CheckSelection , Grid.Plugins.Summary],
            emptyDataTpl : '<div class="centered"><img alt="Crying" src="${statics }/img/dataisnull.png"/><font style="color:red;font-size:20px;"><h2>没有找到匹配的数据!</h2></font></div>',
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