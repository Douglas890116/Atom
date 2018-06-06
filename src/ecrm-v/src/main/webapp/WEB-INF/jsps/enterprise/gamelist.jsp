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
            
            <div class="control-group span7">
              <label class="control-label">企业名称：</label>
                <select name="enterprisecode">
                    <option value="">--请选择--</option>
                    <c:forEach var="game" varStatus="status" items="${listEnterprise}">
                    	<option value="${game.enterprisecode }">${game.enterprisename }</option>
                    </c:forEach>
                </select>
            </div>
            <div class="control-group span7">
              <label class="control-label">游戏种类：</label>
                <select name="gamecode">
                    <option value="">--请选择--</option>
                    <c:forEach var="game" varStatus="status" items="${listGame}">
                    	<option value="${game.gamecode }">${game.gamename }</option>
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
   $(function(){
	   
        var Grid = BUI.Grid,
          Store = BUI.Data.Store,
          columns = [
			{ title: '游戏编号',width: '5%',  sortable: false,dataIndex:'code'},
			{ title: '企业编码',width: '6%',  sortable: false,dataIndex:'enterprisecode'},
			{ title: '企业名称',width: '6%',  sortable: false,dataIndex:'enterprisename'},
			{ title: '游戏类型',width: '6%',  sortable: false,dataIndex:'gametype'},
			{ title: '游戏名称',width: '6%',  sortable: false,dataIndex:'gamename'},
			{ title: '操作', width: '70%', sortable: false,  renderer:function(value,obj){
				var  html = "";
            	html += '<button type="button" onclick="batchDown(\'' + obj.sign + '\',this);" class="button  button-warning botton-margin"><span class="icon-edit icon-white"></span>批量下分</button>';
				html += '<button type="button" onclick="batchBalance(\''+ obj.sign + '\',this);" class="button  button-success botton-margin1"><span class="icon-edit icon-white"></span>查询余额</button>';	
				return html;	
			}}
          ];

        var store = new Store({
            url : '${ctx}/EmployeeAccout/gameListData',
            autoLoad : false, //自动加载数据
            pageSize:15,
            remoteSort: true,
            sortInfo : {
                field : 'enterprisecode',
                direction : 'asc'
              },
          }),grid = new Grid.Grid({
            render:'#grid',
            autoRender:true,
            loadMask: true,//加载数据时显示屏蔽层
            width:'100%',
            columns : columns,
            store: store,
            plugins : [Grid.Plugins.CheckSelection ],
            emptyDataTpl:'<div class="centered"><img alt="Crying" src="${statics }/img/dataisnull.png"/><font style="color:red;font-size:20px;"><h2>没有找到匹配的数据!</h2></font></div>',
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
   function batchBalance(sign,objx) {
	   
	   BUI.Message.Confirm('是否确定对该企业的该游戏所有用户进行批量余额查询业务？',function() {
		   $(".botton-margin1").text("查询中...");
		   $(".botton-margin1").attr("disabled", "disabled");
		   
			$.ajax({
				type : "POST",
				url : "${ctx}/EmployeeAccout/batchBalance",
				data : {
					"sign" : sign
				},
				dataType : "json",
				success : function(data) {
					if (data.status == 1) {
						BUI.Message.Alert(data.message,'success');
						$(objx).parent().html(data.data);
					} else {
						BUI.Message.Alert(data.message,'warning');
					}
					
					$(".botton-margin1").removeAttr("disabled"); 
					$(".botton-margin1").text("查询余额");
				}
			});
		}, 'question');
	   
   }
   function batchDown(sign,objx) {
	   //alert(sign);
	   
	   BUI.Message.Confirm('是否确定对该企业的该游戏所有用户进行下分操作？',function() {
		   $(".botton-margin").text("正在下分...");
		   $(".botton-margin").attr("disabled", "disabled");
		   
			$.ajax({
				type : "POST",
				url : "${ctx}/EmployeeAccout/batchDown",
				data : {
					"sign" : sign
				},
				dataType : "json",
				success : function(data) {
					if (data.status == 1) {
						BUI.Message.Alert(data.message,'success');
						$(objx).parent().html(data.data);
					} else {
						BUI.Message.Alert(data.message,'warning');
					}
					
					$(".botton-margin").removeAttr("disabled"); 
					$(".botton-margin").text("批量下分");
				}
			});
		}, 'question');
	   
   }
    </script>