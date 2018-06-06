<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body style="padding: 10px;">
    <div class="demo-content" >
<!-- 搜索页 ================================================== style=" margin:0 auto;  width:950px;"-->
<form id="searchForm" class="form-horizontal" style="outline: none;" method="post">
   <input name="end_hidden" type="hidden" />
    <div class="row well" style="margin-left: 0px;position: relative;">
      <div style="position: relative;display: inline-block;">
          <div style="float: left;margin-right: 96px;">
        		<div class="control-group span14">
					<div class="control-group span7">
		              <label class="control-label">用户账号：</label>
		                <input name="loginaccount"  type="text"  class="control-text"  placeholder="用户账号"/>
		            </div>
		            <div class="control-group span7">
		              <label class="control-label">上级账号：</label>
		                <input name="parentemployeeaccount"  type="text" class="control-text"  placeholder="上级账号"/>
		            </div>	
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
   $("input[name='startDate']").val(getDate("begintime"));
   $("input[name='endDate']").val(getDate("endtime"));	
   		var ranking = 0; 
        var Grid = BUI.Grid,
          Store = BUI.Data.Store,
          columns = [
                    { title: '用户名',  width: '15%', sortable: false,  dataIndex: 'loginaccount'},
         			//{ title: '用户编码',  width: 100, sortable: false,  dataIndex: 'employeecode'},
         			{ title: '上级用户名',  width: '15%', sortable: false,  dataIndex: 'parentemployeeaccount'},
         			//{ title: '上级编码',  width: 100, sortable: false,  dataIndex: 'parentemployeecode'},
         			{ title: '总存款',   width: '20%',  sortable: false,dataIndex:'accumulateddeposit', summary : true, renderer:function(value,obj){
                   		return "<span style='color:#5cb85c;font-size: 18px;'>"+parseFloat(value).toFixed(2)+"</span>";
                     }},
         			{ title: '总取款',width: '20%',sortable: false,dataIndex:'accumulatedwithdraw', summary : true, renderer:function(value,obj){
         				return "<span style='color:#FF0000;font-size: 18px;'>"+parseFloat(value).toFixed(2)+"</span>";
         			}},
         			{ title: '总输赢',width: '30%',sortable: false,dataIndex:'summoney', summary : true, renderer:function(value,obj){
         				if((value+"").indexOf("-")==0){
	         				return "<span style='color:#FF0000;font-size: 18px;'>"+parseFloat(value).toFixed(2)+"</span>";
         				}
         				return "<span style='color:#5cb85c;font-size: 18px;'>"+parseFloat(value).toFixed(2)+"</span>";
         			}}
          ];
        //var editing = new Grid.Plugins.RowEditing();
        /**
         * 自动发送的数据格式： 
         *  1. start: 开始记录的起始数，如第 20 条,从0开始
         *  2. limit : 单页多少条记录
         *  3. pageIndex : 第几页，同start参数重复，可以选择其中一个使用
         *
         * 返回的数据格式：
         *  {
         *     "rows" : [{},{}], //数据集合
         *     "results" : 100, //记录总数
         *     "hasError" : false, //是否存在错误
         *     "error" : "" // 仅在 hasError : true 时使用
         *   }
         * pencil,bin,plus
         */
        var store = new Store({
            url : '${ctx}/report/queryUserLoseWinAnalysis',
            autoLoad : false,
            pageSize:15
          }),grid = new Grid.Grid({
            render:'#grid',
            autoRender:true,
            loadMask: true,//加载数据时显示屏蔽层
            //forceFit:true,
            width:'100%',
            columns : columns,
            store: store,
            plugins : [ Grid.Plugins.CheckSelection , Grid.Plugins.Summary],	
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
             if(!sender.hasClass('x-grid-checkbox')){ //如果不是点中勾选列，不能进行选中操作
               return false;
             }
           });
 		
        $("#searchForm").submit(function(){
    	  var obj = BUI.FormHelper.serializeToObject(this);
          obj.start = 0;
          store.load(obj);
    	  return false;
      }).submit();
        
    </script>