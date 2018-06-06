<%@page import="com.maven.entity.EnterpriseEmployeeType.Type"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body style="padding: 10px;">
    <div class="demo-content" >
<!-- 搜索页 ================================================== -->
<form id="searchForm" class="form-horizontal" style="outline: none;" method="post">
<div class="row well" style="margin-left: 0px;position: relative;">
    <div style="position: relative;display: inline-block;">
      <div style="float: left;margin-right: 126px;">
      		
          <div class="control-group span7">
              <label class="control-label">计划编号：</label>
                <input name="patchno" type="text" data-tip='{"text":"计划编号"}' class="control-text"/>
          </div>
          
          <div class="control-group span7">
              <label class="control-label">计划日期：</label>
                <input name="betday" type="text" data-tip='{"text":"计划日期"}' class="control-text"/>
          </div>
          <%-- 
          <div class="control-group span7">
              <label class="control-label">游戏平台：</label>
                <select id="gamePlatform" name="gamePlatform" >
                         <option value="" selected="selected">请选择</option>
                         <c:forEach var="game" varStatus="status" items="${listGame}">
	                    	<option value="${game.gametype }">${game.gamename }</option>
	                    </c:forEach>
                 </select>
          </div> --%>
          
            
      </div>  
      <div style="position: absolute;right: 15px;top: 3px;">
<!--        toggle-display -->
             <button id="btnSearch" type="submit"  class="button button-primary"> <span class="icon-search icon-white"></span>搜 索 </button>
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
   <script type="text/javascript" src="${statics }/js/custom/page-quote.js"></script>
   <script type="text/javascript">
 //默认设置以及回调
   function setDefaultWCardCallback(ecode){
     setDefaultWCard(ecode, function(){
       _store.load();
     });
   };
   
 //取消支付计划
   function setDefaultWCard(enterprisethirdpartycode,fc){
   	BUI.Message.Confirm('是否确定要取消该支付计划吗？',function(){
   		$.ajax({
   			url:getRootPath()+'/plan/cancelPlan',
   			type:"POST",
   			data:{'lsh':enterprisethirdpartycode},
   			dataType:"JSON",
   			success:function(data){
   				if(status.status = "success"){
   					BUI.Message.Alert("计划取消成功","success");
   					fc();
   				}else{
   					BUI.Message.Alert(data.message,"error");
   				}
   			}
   		});
   	},'question');
   }
 
        var Grid = BUI.Grid,
          Store = BUI.Data.Store,
          columns = [
            { title: '计划编号',width: '10%',sortable: false,dataIndex: 'patchno',renderer:function(value,obj){
            	return value;
            }},
            { title: '计划日期',width: '7%',sortable: false,dataIndex: 'betday'},
            { title: '总人数',width: '7%',sortable: false,dataIndex: 'totalCount', summary : true},
            { title: '总投注额',width: '10%',sortable: false,dataIndex: 'totalBetmoney', summary : true,renderer:function(value,data){
            	return parseFloat(value).toFixed(2);
            }},
            { title: '总输赢金额',width: '10%',sortable: false,dataIndex: 'totalNetmoney', summary : true,renderer:function(value,data){
            	return parseFloat(value).toFixed(2);
            }},
            { title: '总有效金额',width: '10%',sortable: false,dataIndex: 'totalValidbet', summary : true,renderer:function(value,data){
            	return parseFloat(value).toFixed(2);
            }},
            { title: '计划操作人',width: '10%',sortable: true,dataIndex: 'operater'},
            { title: '计划操作时间',width: '15%',sortable: true,dataIndex: 'operaterTime',renderer:BUI.Grid.Format.datetimeRenderer},
            { title: '计划状态',width: '8%',sortable: true,dataIndex: 'status',renderer:function(value,data){
            	if(value == 0) {
            		return "<font color='black'>已汇总<font>";
            	} else if(value == 1) {
            		return "<font color='red'>已取消计划<font>";
            	} else if(value == 2) {
            		return "<font color='black'>财务已核准<font>";
            	} else if(value == 3) {
            		return "<font color='green'>已支付<font>";
            	}
            }},
            { title: '操作',width: '15%', dataIndex: 'status',renderer:function(value,obj){
            	var temp = '';	
            	if(value == 0) {
            		temp += '<button type="button" class="button button-inverse" onclick=setDefaultWCardCallback('+obj.lsh+')><span class="icon-cog icon-white"></span>取消计划</button>';
            	} 
            	return temp;
            }}
            
          ];
        var store = new Store({
            url : '${ctx}/plan/data',
            autoLoad : false,
            pageSize:15,
            sortInfo : {
                    field : 'patchno',
                    direction : 'desc'
                  },
            remoteSort: true
          }),grid = new Grid.Grid({
            render:'#grid',
            autoRender:true,
            loadMask: true,//加载数据时显示屏蔽层
            columns : columns,
            width:'100%',
            store: store,
            plugins : [ Grid.Plugins.CheckSelection, Grid.Plugins.Summary  ],
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