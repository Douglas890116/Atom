<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<body style="margin: 5px 27px;">
	<div  style="min-width:700px;width: 700px;">
		<form id="searchForm2" class="form-horizontal" style="outline: none;" method="post">
			<div class="row well" style="margin-left: 0px; position: relative;">
				<div style="position: relative; display: inline-block;">
					<div style="float: left; margin-right: 96px;">
						<div class="control-group span7">
							<label class="control-label">登录账号：</label>
								<input name="loginaccount" type="text"
									data-tip='{"text":"登录账号","iconCls":"icon icon-user"}'
									class="control-text" />
						</div>
					</div>
					<div style="position: absolute; right: 15px; top: 3px;">
						<button id="btnSearch" type="submit" class="button button-primary">
							搜 索</button>
					</div>
				</div>
			</div>
			<div class="search-grid-container well">
			<div id="grid2"></div>
		</div>
		</form>
		<div  id="accredit-user">
		</div>
	</div>


</body>
</html>
<script type="text/javascript">

(function(){
	var Grid = BUI.Grid,
	  Store = BUI.Data.Store,
	  columns = [
		{ title: '用户账号 ',   width: 150,  sortable: false,dataIndex: 'loginaccount', renderer:function(value,obj){
	    	return  "<span style='color: #428BCA;font-size:16px;' title='用户账号' >"+value+"</span>";
	    	
	    }},
	    { title: '别名',   width: 150,  sortable: false,dataIndex: 'displayalias'},
	    { title: '账户类型',   width: 150,  sortable: false,dataIndex: 'employeetypename'},
		{ title: '最后登录时间',   width: 150,  sortable: false, dataIndex: 'lastlogintime',renderer:BUI.Grid.Format.datetimeRenderer},
	  ];

	var store2 = new Store({
	    url : '${ctx}/PrivateData/SearchUser',
	    autoLoad : false,
	    pageSize:10
	  }),
	  grid2 = new Grid.Grid({
	    render:'#grid2',
	    loadMask: true,
	    width:'100%',
	    columns : columns,
	    itemStatusFields : { //设置数据跟状态的对应关系
            selected : 'selected',
            disabled : 'disabled'
          },
	    store: store2,
	    plugins : [Grid.Plugins.CheckSelection],
	    emptyDataTpl:'<div class="centered"><img alt="Crying" src="${statics }/img/dataisnull.png"/><font style="color:red;font-size:20px;"><h2>没有找到匹配的数据!</h2></font></div>',
	    bbar : {
	      pagingBar:true
	    }
	  }),datepicker2 = new BUI.Calendar.DatePicker({
          trigger:'.calendar-time',
          showTime:true,
          autoRender:true
       });
	grid2.render();
	
	grid2.on('cellclick',function(ev){
        var sender = $(ev.domTarget);
        var user = $("#accredit-user");
        if($(ev.row).hasClass("bui-grid-row-selected")){
        	user.find("#"+ev.record.employeecode).remove();
        }else{
        	if(user.find("#"+ev.record.employeecode).size()==0){
        		user.prepend("<div id='"+ev.record.employeecode+"' style='border:1px solid #F0F0F0;padding:0px 5px;margin:0px 1px;display:inline-block;border-radius:4px;-webkit-border-radius:4px;-moz-border-radius:4px;' ><span><input type='hidden' name='employee' value='"+ev.record.sign+"' />"+ev.record.loginaccount+"<<i style='color:#CDCDCD;'>"+ev.record.displayalias+"</i>>;</span><span class='x-icon x-icon-mini x-icon-error accept-u-del' style='cursor:pointer;'>×</span></div>");
        	}
        }
     });
	
	grid2.on('columnclick',function(ev){
        var sender = $(ev.domTarget);
        if(sender.hasClass('x-grid-checkbox')){  //如果不是点中勾选列，不能进行选中操作
        	var colEl = ev.column.get('el');
            var _item = grid2.getItems();
            var user = $("#accredit-user");
            if(colEl.hasClass('checked')){
            	BUI.each(_item,function(item){
            		user.find("#"+item.employeecode).remove();
                })
            }else{
            	BUI.each(_item,function(item){
            		if(user.find("#"+item.employeecode).size()==0){
            			user.prepend("<div id='"+item.employeecode+"' style='border:1px solid #F0F0F0;padding:0px 5px;margin:0px 1px;display:inline-block;border-radius:4px;-webkit-border-radius:4px;-moz-border-radius:4px;' ><span><input type='hidden' name='employee' value='"+item.sign+"' />"+item.loginaccount+"<<i style='color:#CDCDCD;'>"+item.displayalias+"</i>>;</span><span class='x-icon x-icon-mini x-icon-error accept-u-del' style='cursor:pointer;'>×</span></div>");
            		}
                })
            }
        }
     });
	
	$(".accept-u-del").live("click",function(){
		$(this).parent().remove();
	});
	
	$("#searchForm2").submit(function(){
  	  var obj = BUI.FormHelper.serializeToObject(this);
        obj.start = 0;
        store2.load(obj);
  	  return false;
    }).submit();
})()


</script>