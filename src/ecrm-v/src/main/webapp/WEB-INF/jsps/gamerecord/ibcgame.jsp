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
           <div class="control-group span7">
              <label class="control-label">订单号：</label>
                <input name="ibcRowguid"  type="text" data-tip='{"text":"订单号"}' class="control-text" placeholder="订单号"/>
            </div>
           <div class="control-group span7">
              <label class="control-label">投注号：</label>
                <input name="ibcOrderid"  type="text" data-tip='{"text":"投注号"}' class="control-text" placeholder="投注号"/>
            </div>
           <div class="control-group span7 toggle-display">
              <label class="control-label">下注类型：</label>
              <select name="ibcTztype">
                  <option value="">--请选择--</option>
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
              <label class="control-label">下注金额：</label>
                <input name="ibcTzmoney"  type="text" data-tip='{"text":"下注金额"}' class="control-text" placeholder="下注金额"/>
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
   	          	searchForm.action = "${ctx}/IBCGame/exportExcel";
   	          	searchForm.submit();
   	          	searchForm.action = "${ctx}/IBCGame/data";
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
			{ title: '订单号',   width: '20%',  sortable: false,dataIndex:'ibcRowguid'},
			//{ title: 'brandcode',   width: 100,  sortable: false,dataIndex:'brandcode'},
			//{ title: '投注编码',   width: '8%',  sortable: false,dataIndex:'ibcBallid'},
			{ title: '投注号',   width: '8%',  sortable: false,dataIndex:'ibcOrderid'},
			{ title: '用户账号',width: '10%',  sortable: false,dataIndex:'loginaccount'},
			//{ title: '游戏类别',   width: '5%',  sortable: false,dataIndex:'ibcIsbk'},
			/* { title: '下注内容',   width: 100,  sortable: false,dataIndex:'ibcContent'}, */
			{ title: '赔率',   width: '5%',  sortable: false,summary : true, dataIndex:'ibcCurpl',renderer:function(value,obj){
				return parseFloat(value).toFixed(2);
			}},
			//{ title: '单双',   width: 100,  sortable: false,dataIndex:'ibcDs'},
			//{ title: '大小个数',   width: 100,  sortable: false,dataIndex:'ibcDxc'},
			//{ title: '是否取消',   width: 100,  sortable: false,dataIndex:'ibcIscancel'},
			//{ title: '是否危险球',   width: 100,  sortable: false,dataIndex:'ibcIsdanger'},
			//{ title: '是否结算',   width: 100,  sortable: false,dataIndex:'ibcIsjs'},
			{ title: '输掉的金额',   width: '5%',  sortable: false,summary : true, dataIndex:'ibcLose', renderer:function(value){
				return value.toFixed(2);
			}},
			//{ title: '联赛ID',   width: 100,  sortable: false,dataIndex:'ibcMatchid'},
			//{ title: '货币比率',   width: 100,  sortable: false,dataIndex:'ibcMoneyrate'},
			//{ title: '投注单号',   width: '8%',  sortable: false,dataIndex:'ibcOrderid'},
			//{ title: '红牌',   width: 100,  sortable: false,dataIndex:'ibcRecard'},
			{ title: '赛事结果',   width: '5%',  sortable: false,dataIndex:'ibcResult'},
			//{ title: '让球个数',   width: 100,  sortable: false,dataIndex:'ibcRqc'},
			//{ title: '让球队伍',   width: 100,  sortable: false,dataIndex:'ibcRqteam'},
			//{ title: '下注球类ID号',   width: '6%',  sortable: false,dataIndex:'ibcSportid'},
			//{ title: '走地时间',   width: 100,  sortable: false,dataIndex:'ibcTballtime'},
			//{ title: '结算日期',   width: '8%',  sortable: false,dataIndex:'ibcThisdate'},
			//{ title: '赢半，赢，输半，输，和',   width: 100,  sortable: false,dataIndex:'ibcTruewin'},
			//{ title: '投注IP，ibc 不提供，为空就行',   width: 100,  sortable: false,dataIndex:'ibcTzip'},
			{ title: '下注金额',   width: '5%',  sortable: false,summary : true, dataIndex:'ibcTzmoney', renderer:function(value){
				return value.toFixed(2);
			}},
			//{ title: '下注队伍',   width: 100,  sortable: false,dataIndex:'ibcTzteam'},
			{ title: '下注类别',   width: '5%',  sortable: false,dataIndex:'ibcTztype'},
			{ title: '输赢金额',   width: '8%',  sortable: false,summary : true, dataIndex:'ibcWin', renderer:function(value){
				return value.toFixed(2);
			}},
			//{ title: '走地比分',   width: 100,  sortable: false,dataIndex:'ibcZdbf'},
			{ title: '开赛时间',   width: '8%',  sortable: false,dataIndex:'ibcBalltime',renderer:BUI.Grid.Format.datetimeRenderer},
			{ title: '下注时间',   width: '12%',  sortable: true,dataIndex:'ibcUpdatetime',renderer:BUI.Grid.Format.datetimeRenderer}
			//{ title: '注单更新顺序号',   width: 100,  sortable: false,dataIndex:'ibcVendorid'},
			//{ title: '平台更新时间',   width: 100,  sortable: false,dataIndex:'ibcCreatetime'}
          ];

        var store = new Store({
            url : '${ctx}/IBCGame/data',
            autoLoad : false,
            pageSize:15,
            remoteSort: true,
            sortInfo : {
              field : 'ibc_updatetime',
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
    