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
              <label class="control-label">代理账号：</label>
              <input name="loginaccount"  type="text" data-tip='{"text":"代理账号"}' class="control-text" placeholder="代理账号"/>
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
		    	</select>
		 	</div>
		 	<div class="control-group span7 ">
                <label class="control-label">游戏平台：</label>
                <select id="gameplatform" name="gameplatform" >
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
	每周一18：40分汇总上周一至上周日的未发放的代理洗码数据。（请在这个时间之前尽可能发放周日的洗码）
	</span>
	</li>
</ul>
<div class="search-grid-container well">
  <div id="grid"></div>
</div>
</div>

	<input class="input control-text" type="hidden" id="lsh" >
    
    <!-- 此节点内部的内容会在弹出框内显示,默认隐藏此节点-->
    <div id="content1" class="hidden" style="display: none;">
      <form id="validtimeform" class="form-horizontal">
        <div class="row">
          <div class="control-group span15">
          
          	<label class="control-label">实发金额：</label>
          	<div class="controls" >
              	<p style="text-align:center"><input class="input control-text" type="text" id="paymoneyreal" style="width:167px;height:20px;" data-rules="{required : true}"/><br /><br /></p>
              	<p style="text-align:right">
              	<input type="button" class='button button-success botton-margin'  onclick="savePayMoney()" value="确定"></input>
              	</p>
   			</div>
   			
          </div>
        </div>
      </form>
    </div>

</body>
</html>
<script type="text/javascript">
//$("input[name='startDate']").val(getDate("begintime"));
//$("input[name='endDate']").val(getDate("endtime"));
$('#selectDateId option:eq(1)').attr('selected','selected');

var process_select = ${sessionScope.ERCM_USER_PSERSSION['MN00AU'].menustatus ==1 };
var process_all = ${sessionScope.ERCM_USER_PSERSSION['MN00AT'].menustatus ==1 };
var cancel_plan = ${sessionScope.ERCM_USER_PSERSSION['MN00EZ'].menustatus ==1 };

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
						items.push(item.reportcode);
					});
					$.ajax({
						type : "POST",
						url : "${ctx}/reportAgent/processWeekSelect",
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
					url : "${ctx}/reportAgent/processWeekAll",
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
if(cancel_plan){
	botton_arry.push({
        btnCls : 'button button-danger',
        text:'<span class="icon-remove icon-white"></span>取消支付计划',
        handler : function(){
      	 openNewWindow('cancel_weekly_agent','${ctx}/reportAgent/cancelWeekly','取消支付计划');
        }
    });	
}

var dialogGlobal = null;
function showUpdate(lsh, paymoneyreal){
	$("#lsh").val(lsh);
	$("#paymoneyreal").val(paymoneyreal);
	
	if(dialogGlobal == null) {
		BUI.use('bui/overlay',function(Overlay){
			dialogGlobal = new Overlay.Dialog({
				width:340,
				height:230,
				title:"修改实发金额",
				elCls : 'custom-dialog',
				contentId:'content1',
				buttons : [{
					text:'取消',
					elCls : 'button',
					handler : function(){
						this.close();
					}
				}]
			});
			//dialogGlobal.show();
		});
	}
	dialogGlobal.show();
}
//保存新金额
function savePayMoney() {
	
	if($("#paymoneyreal").val() == "" ) {
		$("#paymoneyreal")[0].focus();
		return ;
	}
	
	BUI.Message.Confirm('是否确定操作吗？',function() {
		$.ajax({
			type : "POST",
			url : "${ctx}/reportAgent/updateMoney",
			data : {"lsh" : $("#lsh").val(), "paymoneyreal": $("#paymoneyreal").val()},
			dataType : "json",
			success : function(data) {
				if (data.status == 1) {
					BUI.Message.Alert(data.message,'success');
					dialogGlobal.close();
					store.load();
				} else {
					BUI.Message.Alert(data.message,'warning');
				}
			}
		});
	}, 'question');
}

function FormatDate (strTime) {
    var date = new Date(strTime);
    return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
}

function processWeekCheck(reportcode,objx) {
	BUI.Message.Confirm('是否确定进行校验？可能会需要一点时间',function() {
		$.ajax({
			type : "POST",
			url : "${ctx}/reportAgent/processWeekCheck",
			data : {"reportcode": reportcode},
			dataType : "json",
			success : function(data) {
				
				if (data.status == 1) {
					BUI.Message.Alert(data.message,'info');
					
					$(objx).hide();
					$(objx).parent().html(data.message);
					//刷新
					store.load();
					
				} else {
					BUI.Message.Alert(data.message,'warning');
					
					if( (data.isNeed) ) {//需要补发
						$("#btnbf"+reportcode).show(300);
					}
				}
			}
		});
	}, 'question');
}

function processWeekSupplement(reportcode,objx) {
	BUI.Message.Confirm('是否确定现在进行补发？可能会需要一点时间，补发前将会做二次校验',function() {
		$.ajax({
			type : "POST",
			url : "${ctx}/reportAgent/processWeekSupplement",
			data : {"reportcode": reportcode},
			dataType : "json",
			success : function(data) {
				
				if (data.status == 1) {
					BUI.Message.Alert(data.message,'info');
					
					$(objx).hide();
					//刷新
					store.load();
					
				} else {
					BUI.Message.Alert(data.message,'warning');
					
				}
			}
		});
	}, 'question');
}


var Grid = BUI.Grid,
  Store = BUI.Data.Store,
  columns = [
	{ title: '代理用户名',width: '10%',elCls:'center',  sortable: false,  dataIndex: 'loginaccount'},
	{ title: '游戏平台',width: '10%',elCls:'center',  sortable: false,  dataIndex: 'gameplatform'},
	{ title: '游戏种类',width: '6%',elCls:'center',  sortable: false,  dataIndex: 'gametype'},
	{ title: '洗码比例',width: '6%',elCls:'center',  sortable: false,  dataIndex: 'ratio', renderer:function(value){
		return (value*100).toFixed(2) + "%";
	}},
	{ title: '投注总额',width: '6%',elCls:'center',  sortable: true, summary : true, dataIndex: 'bet', renderer:function(value){
		return value.toFixed(2);
	}},
	{ title: '应发放洗码',width: '6%',elCls:'center',  sortable: true, summary : true, dataIndex: 'amount', renderer:function(value){
		return value.toFixed(2);
	}},
	{ title: '实际应发额',width: '6%',elCls:'center',  sortable: true, summary : true, dataIndex: 'actual', renderer:function(value){
		if( value==null || value == "" || value == 0) {
			return "<font color='red'>"+parseFloat(0).toFixed(2)+"</font>";
		} else {
			return "<font color='red'>"+parseFloat(value).toFixed(2)+"</font>";
		}
	}},
	{ title: '已发放',width: '6%',elCls:'center',  sortable: true, summary : true, dataIndex: 'subtract', renderer:function(value){
		if( value==null || value == "" || value == 0) {
			return "<font color='green'>"+parseFloat(0).toFixed(2)+"</font>";;
		} else {
			return "<font color='green'>"+parseFloat(value).toFixed(2)+"</font>";
		}
	}},
	{ title: '未发放',width: '6%',elCls:'center',  sortable: true, summary : true, renderer: function(value,obj){
		if(obj.status == 1) {
			var num = (obj.amount - obj.subtract).toFixed(2);
			if(num > 0) {
				return "<font color='red'>"+(num)+"</font>";
			} else {
				if(num == 0) {
					return 0;
				}
				return num;
			}
		} else {
			return 0; 
		}
		
	}},
	
	{ title: '状态',width: '6%',elCls:'center',  sortable: false,  dataIndex: 'status', renderer:function(value,obj){
		return value=="1"?"<span class='label'>未发放</span>":"<span class='label label-success'>已发放</span>";
	}},
	{ title: '数据类型',width: '6%',elCls:'center',  sortable: false,  dataIndex: 'paytype', renderer:function(value,obj){
		if(value == "1") {
			return "正常";
		} else if(value == "2") {
			return "<b>补发</b>";
		}
	}},
	
	{ title: '报表统计区间',width: '16%',elCls:'center',  sortable: false, renderer: function(value,obj){
		var str1=FormatDate(obj.starttime);
		var str2=FormatDate(obj.endtime);
		return str1+"至"+str2;
	}},
	{ title: '汇总时间',width: '10%',elCls:'center',  sortable: true,  dataIndex: 'reporttime', renderer : BUI.Grid.Format.datetimeRenderer},
	{ title: '支付/取消单号',width: '10%',elCls:'center',  sortable: false,  dataIndex: 'payno', renderer:function(value,obj){
		return value;
	}},
	
    { title: '操作',   width: '10%', elCls:'center', sortable: false, renderer:function(value,obj){
    	if(obj.paytype == "1") {
    		var b = '<button type="button" onclick="processWeekCheck('+obj.reportcode+',this)" class="button  button-success botton-margin"><span class="icon-edit icon-white"></span>洗码校验</button>';
    		b += '<button type="button" id="btnbf'+obj.reportcode+'" onclick="processWeekSupplement('+obj.reportcode+',this)" class="button  button-warn botton-margin" style="display:none"><span class="icon-edit icon-blue"></span>补发</button>';
    		return b;
    	} else if(obj.paytype == "2" && obj.status == "1") {//补发记录而还没发放的，可以修改金额.jason20171005取消修改功能
			//return '<button type="button" onclick="showUpdate('+obj.reportcode+','+obj.actual+')" class="button  button-success botton-margin"><span class="icon-edit icon-white"></span>修改金额</button>';
			return "";
		}
		
	}} 
  ];

var store = new Store({
    url : '${ctx}/reportAgent/reportWeekData',
    autoLoad:false, 
    pageSize:15,
    remoteSort: true,
    sortInfo : {
       field : 'patchno',
       direction : 'asc'
    }
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