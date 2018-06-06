<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.maven.entity.EnterpriseEmployeeType.Type" %>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body style="padding: 10px;">
    <div class="demo-content" >
<!-- 搜索页 ================================================== style=" margin:0 auto;  width:950px;"-->
<form id="searchForm" class="form-horizontal" style="outline: none;" method="post">
    <input name="end_hidden" type="hidden" />
    <input name="employeecode" type="hidden" value="${employeecode}"/>
    <input name="employeeType" type="hidden" value="${employeeType}"/>
    <div class="row well" style="margin-left: 0px;position: relative;">
    <div style="position: relative;display: inline-block;">
      <div style="float: left;margin-right: 96px;">
             <div class="control-group span14">
                <label class="control-label">时间范围：</label>
                <div class="bui-form-group" data-rules="{dateRange : true}">
                  <input name="startDate" type="text" class="calendar calendar-time" placeholder="起始时间"/>
                  <span>&nbsp;-&nbsp;</span>
                  <input name="endDate" type="text" class="calendar calendar-time" placeholder="结束时间"/>
                </div>
                <div style="margin-right: auto;margin: -30px 425px;">
                  <select onchange="changeFormatDate(this,'startDate','endDate')" id="selectDateId" style="width:85px;">
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
            <div class="control-group span7" >
              <label class="control-label">会员账号：</label>
              <input name="userName"  type="text" data-tip='{"text":"会员账号"}' class="control-text" placeholder="会员账号"/>
            </div>
            <div class="control-group span7" >
              <label class="control-label">上级账号：</label>
              <input name="parentName"  type="text" data-tip='{"text":"上级账号"}' class="control-text" placeholder="上级账号"/>
            </div>
            <div class="control-group span7" >
              <label class="control-label">支付单号：</label>
              <input name="payno"  type="text" data-tip='{"text":"支付单号"}' class="control-text" placeholder="支付单号"/>
            </div>
            <div class="control-group span7 ">
			  	<label class="control-label">发放状态：</label>
			  	<select name="status" class="span3">
			    	<option value="">--请选择--</option>
			    	<option value="1">未发放</option>
		    		<option value="2">已发放</option>
		    		<option value="3">周结汇总</option>
		    	</select>
		 	</div>
		 	<div class="control-group span7 ">
			  	<label class="control-label">存款选项：</label>
			  	<select name="depositflag" name="depositflag">
			    	<option value="">--请选择--</option>
			    	<option value="1">没有存款</option>
		    		<option value="2">有存款</option>
		    	</select>
		 	</div>
		 	<div class="control-group span7 ">
                <label class="control-label">游戏平台：</label>
                <select id="gamePlatform" name="gamePlatform" >
                         <option value="" selected="selected">请选择</option>
                         <c:forEach var="game" varStatus="status" items="${listGame}">
                            <option value="${game.gametype}">${game.gamename}</option>
                        </c:forEach>
                 </select>
            </div>
          </div>
          	 <div style="position: absolute;right: -25px;top: 3px;">
             	 <button id="btnSearch" type="submit"  class="button button-primary"> <span class="icon-search icon-white"></span>搜 索 </button>
                 <input type="reset" style="display:none;" id="resetId" />
            </div>
        </div>
    </div>
</form>
<ul style="border:5px; height:20px; line-height:20px; color:red; font-weight:bold;" class="breadcrumb">
	<li>
	<span style="font-size: 12px">
	每日12：10分汇总昨日的投注数据。
	</span>
	</li>
</ul>
<div class="search-grid-container well">
  <div id="grid"></div>
</div>
</div>

</body>
</html>
<script type="text/javascript">
$("input[name='startDate']").val(getDate("begintime"));
$("input[name='endDate']").val(getDate("endtime"));
$('#selectDateId option:eq(1)').attr('selected','selected');

var process_select = ${sessionScope.ERCM_USER_PSERSSION['MN00AU'].menustatus ==1 };
var process_all = ${sessionScope.ERCM_USER_PSERSSION['MN00AT'].menustatus ==1 };
var cancel_plan = ${sessionScope.ERCM_USER_PSERSSION['MN00EW'].menustatus ==1 };
var export_excel = (1 == 1);

var botton_arry = new Array();

if(process_select){
	botton_arry.push({
		btnCls : 'button button-primary',
		text : '<span class="icon-ok icon-white"></span>发放本页选择的',
		handler : function() {
			var selectItem = grid.getSelection();
			if (selectItem.length == 0) {
				BUI.Message.Alert('请选择需要发放的数据,(已经发放过的不会再次发放)', 'info');
			} else {
				BUI.Message.Confirm('是否确定发放？(当前已选择'+selectItem.length+'条)',function() {
					var items = new Array();
					BUI.each(selectItem,function(item) {
						items.push(item.id);
					});
					$.ajax({
						type : "POST",
						url : "${ctx}/reportMember/processDailySelect",
						data : {"iditems":items.toString()},
						dataType : "json",
						success : function(data) {
							if (data.status == 1) {
								BUI.Message.Alert(data.message,function(){
					    			store.load(null);
					    		},'success');
							} else {
								BUI.Message.Alert(data.message,'warning');
							}
						}
					});
				}, 'question');
			}
		}
	});
}

if(process_all){
	botton_arry.push({
		btnCls : 'button button-success',
		text : '<span class="icon-forward icon-white"></span>发放所有当前未发放的',
		handler : function() {
			var selectItem = grid.getSelection();
			BUI.Message.Confirm('注意：：：：是否确定"全部"发放？(当前没有未发放的将会全部发放，请注意)',function() {
				var sign = new Array();
				/* BUI.each(selectItem,function(item) {
							sign.push(item.sign);
				}); */
				$.ajax({
					type : "POST",
					url : "${ctx}/reportMember/processDailyAll",
					/* data : {
						"sign" : sign
					}, */
					dataType : "json",
					success : function(data) {
						if (data.status == 1) {
							BUI.Message.Alert(data.message,function(){
				    			store.load(null);
				    		},'success');
						} else {
							BUI.Message.Alert(data.message,'warning');
						}
					}
				});
			}, 'question');
		}
	});
}

if(export_excel){
	botton_arry.push({
	          btnCls:'button button-primary',
	          text:'<span class="icon-file icon-white"></span>导出到EXCEL(当前条件)',
	          handler:function(){
	          	searchForm.action = "${ctx}/reportMember/excelDailyData";
	          	searchForm.submit();
	          	searchForm.action = "${ctx}/reportMember/reportDailyData";
	          }
	});
}
if(cancel_plan){
	botton_arry.push({
        btnCls : 'button button-danger',
        text:'<span class="icon-remove icon-white"></span>取消支付计划',
        handler : function(){
      	 openNewWindow('cancel_daily_member','${ctx}/reportMember/cancelDaily','取消支付计划');
        }
    });	
}

var Grid = BUI.Grid,
  Store = BUI.Data.Store,
  columns = [
	{ title: '用户名',width: '10%',elCls:'center',  sortable: false,  dataIndex: 'userName'},
	{ title: '游戏平台',width: '10%',elCls:'center',  sortable: false,  dataIndex: 'gamePlatform'},
	{ title: '游戏种类',width: '6%',elCls:'center',  sortable: false,  dataIndex: 'gameBigType'},
	{ title: '洗码比例',width: '6%',elCls:'center',  sortable: false,  dataIndex: 'ratio', renderer:function(value,obj){
		return (obj.ratio * 100.00).toFixed(2) + "%";
	}},
	{ title: '有效投注额',width: '8%',elCls:'center',  sortable: true, summary : true, dataIndex: 'validMoney', renderer:function(value){
		return value.toFixed(2);
	}},
	{ title: '输赢值',width: '8%',elCls:'center',  sortable: true, summary : true, dataIndex: 'netMoney', renderer:function(value){
		return value.toFixed(2);
	}},
	{ title: '洗码金额',width: '8%',elCls:'center',  sortable: true, summary : true, dataIndex: 'rebatesCash', renderer:function(value){
		if( value==null || value == "") {
			return 0;
		} else {
			return "<font color='red'>"+parseFloat(value).toFixed(2)+"</font>";
		}
	}},
	{ title: '投注日期',width: '8%',elCls:'center',  sortable: false,  dataIndex: 'betDay', renderer : BUI.Grid.Format.dateRenderer},
	{ title: '汇总时间',width: '12%',elCls:'center',  sortable: false,  dataIndex: 'addTime', renderer : BUI.Grid.Format.datetimeRenderer},
	{ title: '支付/取消单号',width: '10%',elCls:'center',  sortable: false,  dataIndex: 'payno', renderer:function(value,obj){
		return value;
	}},
	{ title: '发放状态',width: '6%',elCls:'center',  sortable: false,  dataIndex: 'status', renderer:function(value){
		if (value==1) {
			return "<span class='label'>未发放</span>";
		} else if (value==2) {
			return "<span class='label label-success'>已发放</span>";
		} else if (value==3) {
			return "<span class='label label-success'>周结汇总</span>";
		} 
	}}
    /* { title: '操作',   width: '20%', elCls:'center', sortable: false, dataIndex: '',renderer:function(value){
		var b = '<button type="button" onclick="openNewWindow(\'Edit_Enterprise\',\'${ctx}/Enterprise/Edit?sign='+ obj.sign
		+ '&enterprisename='
		+ obj.enterprisename
		+ '\',\'编辑企业\');" class="button  button-success botton-margin"><span class="icon-edit icon-white"></span>编辑</button>';
		
		b += '<button type="button" onclick="openNewWindow(\'Edit_Enterprise\',\'${ctx}/Enterprise/Edit?sign='+ obj.sign
		+ '&enterprisename='
		+ obj.enterprisename
		+ '\',\'编辑企业\');" class="button  button-success botton-margin"><span class="icon-edit icon-white"></span>编辑</button>';
		
		return b;
	}} */
  ];

var store = new Store({
    url : '${ctx}/reportMember/reportDailyData',
    autoLoad:false, 
    pageSize:15,
    sortInfo : {
        field : 'addTime',
        direction : 'desc'
      },
  }),
  grid = new Grid.Grid({
    render:'#grid',
    autoRender:true,
    loadMask: true,//加载数据时显示屏蔽层
    width:'100%',
    columns : columns,
    store: store,
    plugins : [ Grid.Plugins.CheckSelection, Grid.Plugins.Summary  ],
    emptyDataTpl:'<div class="centered"><img alt="Crying" src="${statics }/img/dataisnull.png"/><font style="color:red;font-size:20px;"><h2>没有找到匹配的数据!</h2></font></div>',
    tbar:{ items:botton_arry },//暂时注释，等测试稳定后再开放 jason20161028
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

</script>