<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<!DOCTYPE html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>代理消息</title>
	<link href="${statics}/css/bs3/bui-min.css" rel="stylesheet" />
	<link href="${statics}/css/bs3/dpl-min.css" rel="stylesheet" /> 
	<link href="${statics}/css/custom/common.css" rel="stylesheet" />
</head>

<body  style="padding: 10px;">
    <div class="demo-content">
   <form id="searchForm" class="form-horizontal" style="outline: none;" method="post" data-depends="{'#btn:click':['toggle'],'#btn3:click':['clearFields']}">
    <div style="display: none;"><input name="ORDER_FEILD_createtime" value="DESC" type="hidden"/></div>
    <div class="well">
    	<div style="position: relative;display: inline-block;">
          <div class="control-group span11">
            <label class="control-label">发送时间：</label>
            <div class="bui-form-group" data-rules="{dateRange : true}">
              <input name="begintime"  type="text" data-tip='{"text":"起始区间"}' class="calendar calendar-time"/>
              <span>&nbsp;-&nbsp;</span><input  name="endtime" type="text" data-tip='{"text":"结束区间"}'  class="calendar calendar-time"/>
            </div>
          </div>
      	<div style="float: left;margin-right: 96px;">
      	 	<div class="control-group span6">
              <label class="control-label">发送人：</label>
              	<input name="sendemployeeaccount"  type="text"  data-tip='{"text":"发送人"}' class="control-text"/>
            </div>
            <div class="control-group span6">
              <label class="control-label">接收人：</label>
              	<input name="acceptaccount"  type="text"  data-tip='{"text":"接收人"}' class="control-text"/>
            </div>
            <div class="control-group span6">
              <label class="control-label">消息类型：</label>
                <select name="messagetype" data-rules="{required:true}">
                  <option value="">-- 全部 --</option>
                  <option value="1">系统消息</option>
                  <option value="2">代理消息</option>
                </select>
            </div>
<!--             <div class="control-group span7">
              <label class="control-label">消息内容：</label>
                <input name="content"  type="text"  data-tip='{"text":"公告内容"}' class="control-text"/>
            </div> -->

        </div>
        <div style="position: absolute;right: 15px;top: 3px;">
               <button id="btnSearch" type="submit" class="button button-primary"><span class="icon-search icon-white"></span>搜索</button>
        </div>
    </div>
    </div>
    </form>
    <!-- 搜索页 ================================================== -->
    
    
    <div class="search-grid-container well">
      <div id="grid">
    </div>

	<div id="message-detail" style="display: none;width: 400px;">
		<div class="control-group" style="margin-bottom: 10px;">
	      <label class="control-label">收信人：<input class="input-normal control-text" id="see-title"  type="text" disabled="disabled" style="width:200px;" /></label>
	    </div>
	    <div class="control-group">
	      <label class="control-label">消&nbsp;&nbsp;&nbsp;&nbsp;息：<textarea id="see-content" style="width: 380px;height: 200px;margin: 0;"  disabled="disabled"></textarea></label>
	    </div>
	</div>
    <script src="${statics }/js/jquery-1.8.1.min.js"></script>
    <script src="${statics }/js/bui.js"></script> 
    <script src="${statics }/js/config.js"></script>
    <script src="${statics }/js/custom/commons-min.js"></script>
    <script src="${statics }/js/custom/page-quote.js"></script>
    <script type="text/javascript">
    function permissionValidate(){
        var array= new Array();
        array.push({
             btnCls : 'button button-primary',
             text:'<span class="icon-file icon-white"></span>发送消息',
             handler : function(){
            	 openNewWindow('noticadd','${ctx}/Message/ManagerSend','发送消息');
             }
         });
   		return array;
    }
      	
      var Grid = BUI.Grid,Store = BUI.Data.Store,
        columns = [
          { title: '发送人',width:'10%',sortable: false, dataIndex: 'sendemployeeaccount',renderer:function(value,obj){
        	if (obj.sendemployeecode == '${sessionScope.ERCM_USER_SESSION.employeecode}') {
        		return "<span class='text-success'>" + value + "</span>";
        	} else {
        		return value;
        	}
          }},
          { title: '接收人',width:'10%',sortable: false,  dataIndex: 'acceptaccount',renderer:function(value,obj){
            	if (obj.acceptemployeecode == '${sessionScope.ERCM_USER_SESSION.employeecode}') {
            		return "<span class='text-success'>" + value + "</span>";
            	} else {
            		return value;
            	}
          }},
          { title: '消息类型',width:'5%',sortable: false,  dataIndex: 'messagetype', renderer:function(value){
          	switch(value) {
          		case '1' : return "<span class='label label-important'>系统消息</span>";
          		case '2' : return "<span class='label label-info'>代理消息</span>";
          	}
            }},
          { title: '消息内容',width:'50%',sortable: false, renderer:function(value,obj){
          	return obj.text.content;
          }},
          { title: '是否阅读',width:'5%',sortable: false,  dataIndex: 'readstatus',renderer:function(value,obj){
            	return value==1?"<span style='color:red;'>未读</span>":"已读";
          }},
          { title: '发送时间',width:'10%',sortable: true,dataIndex:'sendtime', renderer:function(value,obj){
            	return BUI.Grid.Format.datetimeRenderer(obj.text.sendtime);
            }},
          { title: '操作',width:'10%',sortable: false, renderer:function(value,obj){
          	var  action = "";
          	action += '<button type="button" data-reply="sendemployeecode='+obj.sign+'" data-sign='+obj.sign+' class="button  button-primary button-space see-detail" ><span class="icon-th-large icon-white"></span>查看</button>'
          	if(obj.sendemployeecode != '${sessionScope.ERCM_USER_SESSION.employeecode }'){
          		action += '<button type="button" class="button  button-success button-space" onclick="openNewWindow(\'replymessage\',\'${ctx}/Message/ReplyMessage?sendemployeecode='+obj.sign+'\',\'消息回复\')" ><span class="icon-comment icon-white"></span>回复</button>'
          	}
          	return action;
          }}
        ];
      
       var store = new Store({
           url : '${ctx}/Message/AgentData2',
           autoLoad:false,
           pageSize:15,
           sortInfo : {
               field : 'sendtime',
               direction : 'desc'
             },
           remoteSort: true
         }),grid = new Grid.Grid({
           render:'#grid',
           autoRender:true,
           width:'100%',
           loadMask: true,
           fixedLayout:true,
           columns : columns,
           store: store,
           plugins : [ Grid.Plugins.CheckSelection ],
           emptyDataTpl : '<div class="centered"><img alt="Crying" src="${statics }/img/dataisnull.png"/><font style="color:red;font-size:20px;"><h2>没有找到匹配的数据!</h2></font></div>',
           tbar:{ items:permissionValidate() },
           bbar : { pagingBar:true, }
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
        
        BUI.use('bui/overlay',function(Overlay){
            var dialog = new Overlay.Dialog({
              title:'查看消息',
              width:505,
              contentId:"message-detail",
              buttons:[{
                  text:'回 复',
                  elCls : 'button button-primary',
                  handler : function(){
                	  openNewWindow('replymessage','${ctx}/Message/ReplyMessage?'+$("#see-title").data("data-reply"),'消息回复');
                  }
                },{
                  text:'返 回',
                  elCls : 'button button-danger',
                  handler : function(){
                    this.close();
                  }
                }
              ]
            });     
            $(".see-detail").live("click",function(){
            	var sign = $(this).attr("data-sign");
            	var td = $(this).parents("tr").find("td");
            	$("#see-title").val(td.eq(1).text()).data("data-reply",$(this).attr("data-reply"));
            	$("#see-content").val(td.eq(4).text());
            	dialog.show();
            	$.ajax({
       				type: "POST",
       				url: "${ctx}/Message/UpdataStatus",
       				data:{"sign":sign},
       				dataType: "json",
       			    success: function(data) {
       			    	if(data.status==1){
       			    		td.eq(5).html("<div class='bui-grid-cell-inner'><span class='bui-grid-cell-text ' title=''>已读</span></div>");
       			    	}
       			   }});
            });
        });
        
        
        
    </script>
<!-- script end -->
  </div>
	
	</div>
</body>
</html>