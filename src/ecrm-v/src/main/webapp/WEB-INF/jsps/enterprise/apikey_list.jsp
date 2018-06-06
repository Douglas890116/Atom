<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<style type="text/css">
   .bui-stdmod-body{
        overflow-x : hidden;
        overflow-y : auto;
      }
 </style>
<body  style="padding: 10px;">
    <div class="demo-content">
   <form id="searchForm" class="form-horizontal" style="outline: none;" method="post" >
    <input name="end_hidden" type="hidden" />
    <div class="well">
    	<div style="position: relative;display: inline-block;">
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
<!-- 用户新增按钮是否有操作权限 -->
	<script type="text/javascript" src="${statics }/js/custom/page-quote.js"></script>
    <script type="text/javascript">
    
    (function(){
    	//权限判断
    	var add_brand = ${sessionScope.ERCM_USER_PSERSSION['MN001Z'].menustatus==1};
  		var game_setting = ${sessionScope.ERCM_USER_PSERSSION['MN002T'].menustatus==1};
  		var delete_brand = ${sessionScope.ERCM_USER_PSERSSION['MN008Y'].menustatus==1};
  		var customer_line = ${sessionScope.ERCM_USER_PSERSSION['MN009D'].menustatus==1};
  		
  		
        function permissionValidate(){
        	var array= new Array();
        	/* if(add_brand){
        		array.push({
                  btnCls : 'button button-primary',
                  text:'<span class="icon-file icon-white"></span>新增参数',
                  handler : function(){
                	 openNewWindow('create_brand','${ctx}/apigame/edit','新增参数');
                  }
              });
        	} */
        	return array;
        }
        
       
    	
        var Grid = BUI.Grid,
          Store = BUI.Data.Store,
          columns = [
            { title: '代码',width:'5%', dataIndex: 'enterprisecode'},
            { title: '企业名称',width:'6%', dataIndex: 'enterprisename'},
            { title: 'GAMETYPE',width:'6%', dataIndex: 'gametype'},
            { title: 'VALUES 请注意参数大小写，必须完全一致',width:'43%', dataIndex: 'vals'},
            { title: '操作',width:'40%',sortable: false,renderer:function(value,obj){
            	var  html = "";
            	html += '<button type="button" onclick="openNewWindow(\'edit_apikey\',\'${ctx}/apigame/edit?enterprisecode='
						+ obj.enterprisecode+'&gametype='+obj.gametype
						+ '\',\'编辑参数\');" class="button  button-success botton-margin"><span class="icon-edit icon-white"></span>编辑</button>';
				
				html += '<button type="button" onclick="test1(\''+ obj.enterprisecode+'\',\''+obj.gametype+'\', \'create\');" class="button  button-info botton-margin">测试创建用户</button>';
				html += '<button type="button" onclick="test1(\''+ obj.enterprisecode+'\',\''+obj.gametype+'\', \'up\');" class="button  button-info botton-margin">测试上分</button>';
				html += '<button type="button" onclick="test1(\''+ obj.enterprisecode+'\',\''+obj.gametype+'\', \'down\');" class="button  button-info botton-margin">测试下分</button>';
				html += '<button type="button" onclick="test1(\''+ obj.enterprisecode+'\',\''+obj.gametype+'\', \'balance\');" class="button  button-info botton-margin">测试余额查询</button>';
				html += '<button type="button" onclick="test1(\''+ obj.enterprisecode+'\',\''+obj.gametype+'\', \'order\');" class="button  button-info botton-margin">测试订单查询</button>';
				html += '<button type="button" onclick="test1(\''+ obj.enterprisecode+'\',\''+obj.gametype+'\', \'login\');" class="button  button-info botton-margin">测试登录</button>';
                return html;
            }}
          ];
      
        var store = new Store({
            url : '${ctx}/apigame/pagekey',
            autoLoad : false,
            pageSize: 15,
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
            tbar:{
             items:permissionValidate()
            },
            bbar : {
               	pagingBar:true,
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
        
    })();
    
    function test1(enterprisecode,gametype,stype) {
    	var url = "${ctx}/apigame/testAPI?enterprisecode="+enterprisecode+"&gametype="+gametype+"&stype="+stype;
		window.open(url);
    }
    
    
    </script>
<!-- script end -->
  </div>
	
	</div>
	<p>提示：请严格参照文档《综合平台游戏API配置指南.xlsx》说明的各游戏平台参数配置指引，注意参数及值的格式范例。</p>
</body>
</html>