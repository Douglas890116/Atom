<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<body style="margin: 5px 27px;">
	<div  style="min-width:700px;width: 700px;">
		<form id="searchForm" class="form-horizontal" style="outline: none;" method="post">
			<div class="row well" style="margin-left: 0px; position: relative;">
				<div style="position: relative; display: inline-block;">
					<div style="float: left; margin-right: 96px;">
						<div class="control-group span7">
							<label class="control-label">登录账号：</label>
								<input name="loginaccount" type="text"
									data-tip='{"text":"登录账号","iconCls":"icon icon-user"}'
									class="control-text" />
						</div>
						<div class="control-group span7">
							<label class="control-label">账户类型：</label>
								<select name="employeetypecode" aria-pressed="false"
									aria-disabled="false"
									class="bui-form-field-select bui-form-field">
									<option value="">请选择</option>
									<option value="0001">股东</option>
									<option value="0002">员工</option>
									<option value="0003">会员</option>
									<option value="0004">代理</option>
								</select>
						</div>
						<div class="control-group span12 toggle-display">
							<label class="control-label">注册时间：</label>
							<div class="bui-form-group" data-rules="{dateRange : true}">
								<input name="startDate" type="text" data-tip='{"text":"起始时间"}'
									class="calendar calendar-time" /> <span>&nbsp;-&nbsp;</span><input
									name="endDate" type="text" data-tip='{"text":"结束时间"}'
									class="calendar calendar-time" />
							</div>
						</div>
					</div>
					<div style="position: absolute; right: 15px; top: 3px;">
						<button id="btnSearch" type="submit" class="button button-primary">
							搜 索</button>
					</div>
				</div>
			</div>
			<div class="search-grid-container well">
			<div id="grid"></div>
		</div>
		</form>


		
	</div>


</body>
</html>
<script type="text/javascript">

$(function(){
	var Grid = BUI.Grid,
	  Store = BUI.Data.Store,
	  columns = [
		{ title: '账户 <b>/</b> ',   width: 150,  sortable: false,dataIndex: 'loginaccount', renderer:function(value,obj){
	    	return  "<span style='color: #428BCA;font-size:16px;' title='账户' >"+value+"</span>";
	    	
	    }},
	    { title: '别名',   width: 150,  sortable: false,dataIndex: 'displayalias'},
	    { title: '账户类型',   width: 150,  sortable: false,dataIndex: 'employeetypename'},
		{ title: '最后登录时间',   width: 150,  sortable: false, dataIndex: 'lastlogintime',renderer:BUI.Grid.Format.datetimeRenderer},
	  ];

	var store = new Store({
	    url : '${ctx}/Message/ManagerSearchAccount',
	    autoLoad : false,
	    pageSize:10
	  }),grid = new Grid.Grid({
	    render:'#grid',
	    autoRender:true,
	    loadMask: true,
	    width:'100%',
	    columns : columns,
	    itemStatusFields : { //设置数据跟状态的对应关系
            selected : 'selected',
            disabled : 'disabled'
          },
	    store: store,
	    plugins : [Grid.Plugins.CheckSelection],
	    emptyDataTpl:'<div class="centered"><img alt="Crying" src="${statics }/img/dataisnull.png"/><font style="color:red;font-size:20px;"><h2>没有找到匹配的数据!</h2></font></div>',
	    bbar : {
	      pagingBar:true
	    }
	  }),datepicker = new BUI.Calendar.DatePicker({
         trigger:'.calendar-time',
         showTime:true,
         autoRender:true
      });
	
	grid.on('cellclick',function(ev){
        var sender = $(ev.domTarget);
        var user = $("#accept-user");
        if($(ev.row).hasClass("bui-grid-row-selected")){
        	user.find("#"+ev.record.employeecode).remove();
        }else{
        	if(user.find("#"+ev.record.employeecode).size()==0){
        		user.append("<div id='"+ev.record.employeecode+"' style='border:1px solid #F0F0F0;padding:0px 5px;margin:0px 1px;display:inline-block;border-radius:4px;-webkit-border-radius:4px;-moz-border-radius:4px;' ><span><input type='hidden' name='accepter' value='"+ev.record.sign+"' />"+ev.record.loginaccount+"<<i style='color:#CDCDCD;'>"+ev.record.displayalias+"</i>>;</span><span class='x-icon x-icon-mini x-icon-error accept-u-del' style='cursor:pointer;'>×</span></div>");
        	}
        }
     });
	
	grid.on('columnclick',function(ev){
        var sender = $(ev.domTarget);
        if(sender.hasClass('x-grid-checkbox')){  //如果不是点中勾选列，不能进行选中操作
        	var colEl = ev.column.get('el');
            var _item = grid.getItems();
            var user = $("#accept-user");
            if(colEl.hasClass('checked')){
            	BUI.each(_item,function(item){
            		user.find("#"+item.employeecode).remove();
                })
            }else{
            	BUI.each(_item,function(item){
            		if(user.find("#"+item.employeecode).size()==0){
            			user.append("<div id='"+item.employeecode+"' style='border:1px solid #F0F0F0;padding:0px 5px;margin:0px 1px;display:inline-block;border-radius:4px;-webkit-border-radius:4px;-moz-border-radius:4px;' ><span><input type='hidden' name='accepter' value='"+item.sign+"' />"+item.loginaccount+"<<i style='color:#CDCDCD;'>"+item.displayalias+"</i>>;</span><span class='x-icon x-icon-mini x-icon-error accept-u-del' style='cursor:pointer;'>×</span></div>");
            		}
                })
            }
        }
     });
	
	$(".accept-u-del").live("click",function(){
		$(this).parent().remove();
	});
	
	$("#searchForm").submit(function(){
  	  var obj = BUI.FormHelper.serializeToObject(this);
        obj.start = 0;
        store.load(obj);
  	  return false;
    }).submit();
})


</script>