<%@page import="com.maven.config.SystemConstant"%>
<%@page import="com.maven.cache.SystemCache"%>
<%@page import="com.maven.entity.EnterpriseEmployee"%>
<%@page import="com.maven.constant.Constant"%>
<%@page import="org.springframework.web.context.request.SessionScope"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.maven.entity.EnterpriseEmployeeType.Type" %>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<c:set var="user" value="${sessionScope.ERCM_USER_SESSION.employeecode}"></c:set>
<style>
body,dd,div,dl,dt,h1,h2,h3,h4,h5,h6,li,ol,p,ul{
	margin: 0;
	padding: 0;
	border-color: #F81D21; list-style:none;
}
.btn{ width:80px; height:28px; line-height:28px; text-align:center; border-radius:4px; color:#fff; background-color:#449bc9; margin:5px 10px; cursor:pointer; list-style-type:none; outline:none; border:0;}
.btn:hover{ background-color:#0f8fce}
div#OperatingButton{
    background:rgba(0,0,0,0.5);
    width:100px;
    height:150px;
    display:none; 
    padding:10px 0; 
    border-radius:6px
}
</style>
<body style="padding: 10px;">
    <div class="demo-content" >
<!-- 搜索页 ================================================== style=" margin:0 auto;  width:950px;"-->
<form id="searchForm" class="form-horizontal" style="outline: none;" method="post">
    <input name="parentemployeecode" type="hidden" />
    <input name="end_hidden" type="hidden" />
    <input name="last_hidden" type="hidden" />
    <input name="createDate" type="hidden" value="${createDate}"/>
    <div class="row well" style="margin-left: 0px;position: relative;margin-bottom: 0px;">
    <div style="position: relative;display: inline-block;">
      <div style="float: left;margin-right: 96px;">
      		 
      		 <div class="control-group span7">
              <label class="control-label">会员账号：</label>
                <input name="loginaccount"  type="text"  data-tip='{"text":"登录账户"}'  class="control-text" placeholder="登录账户"/>
            </div>
            
             <div class="control-group span7">
              <label class="control-label">游戏账号：</label>
                <input name="gameaccount"  type="text" data-tip='{"text":"游戏账号"}' class="control-text" placeholder="游戏账号"/>
            </div>
            
            <div class="control-group span6">
	              <label class="control-label">游戏平台：</label>
	                <select name="gametype" aria-pressed="false" aria-disabled="false"   class="bui-form-field-select bui-form-field" style="width: 117px;">
						<option value="">请选择</option>
	                    <c:forEach var="game" varStatus="status" items="${sessionScope.ERCM_ENTERPRISE_GAMES}">
	                    	<option value="${game.gametype }">${game.gamename }</option>
	                    </c:forEach>
	              	</select>
	         </div>
	         
             <div class="control-group span6">
	              <label class="control-label">账号状态：</label>
	                <select name="status" aria-pressed="false" aria-disabled="false"   class="bui-form-field-select bui-form-field" style="width: 117px;">
						<option value="">请选择</option>
	                    <option value="启用">启用</option>
	                    <option value="禁用">禁用</option>
	              	</select>
	         </div>
            
           </div>
          	 <div style="position: absolute;right: 0px;top: 3px;">
             	 <button id="btnSearch" type="submit"  class="button button-primary"><span class="icon-search icon-white"></span> 搜 索 </button>
            </div>
        </div>
    </div> 
    </div>
     </form>
     
<ul style="border:5px; height:20px; line-height:20px; color:red; font-weight:bold;" class="breadcrumb">
	<li>
	<span style="font-size: 12px">
	若密码显示为*号，说明您没有查看用户游戏密码的权限，请向管理人员申请，[配置管理]-[隐私数据授权]
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

(function(){

var batch_export = 	${sessionScope.ERCM_USER_PSERSSION['MN00D5'].menustatus==1};

var employeetype =  '${sessionScope.ERCM_USER_SESSION.employeetypecode}';

//权限验证
function permissionValidate(){
	var array= new Array();
	if(batch_export){
		array.push({
	        btnCls : 'button button-warning bin',
	        text:'<span class=" icon-remove icon-white"></span>禁用游戏账号',
	        handler : function(){
	        	disableMutilterm(grid,store,"${ctx}/apiaccount/disableSelectEmployee","apiaccountcode");
	     }});
	}
	if(batch_export){
		array.push({
	        btnCls : 'button button-success bin',
	        text:'<span class=" icon-ok icon-white"></span>启用游戏账号',
	        handler : function(){
	        	activateMutilterm(grid,store,"${ctx}/apiaccount/activateSelectEmployee","apiaccountcode");
	     }});
	}
	return array;
}



var Grid = BUI.Grid,
  Store = BUI.Data.Store,
  columns = [
     {title:'会员账号',width:'10%',dataIndex:'loginaccount',sortable:false},
     {title:'游戏平台',width:'10%',dataIndex:'gametype',sortable:false},
     {title:'游戏账号',width:'10%',dataIndex:'gameaccount',sortable:false},
     {title:'游戏密码',width:'10%',dataIndex:'gamepassword',sortable:false},
     {title: '创建时间', width: '15%',dataIndex: 'createtime',elCls:'center',renderer:BUI.Grid.Format.datetimeRenderer},
     {title: '状态',width: '10%',sortable: false, dataIndex: 'status', renderer:function(value,obj){
    	if(value == "启用") {
    		return "启用";
    	} else {
    		return "<span style='color:red'>禁用</span>";
    	}
    	
	}}
  ];

	var store  = new Store({
      url : '${ctx}/apiaccount/pagelist',
      autoLoad:false,
      pageSize:15,
      remoteSort: true,
      sortInfo : {
          field : 'createtime',
          direction : 'desc'
        }
  	}),
  	grid = new Grid.Grid({
      render:'#grid',
      loadMask: true,
      autoRender:true,
      width:'100%',
      columns : columns,
      store: store,
      plugins : [ Grid.Plugins.CheckSelection ],
      emptyDataTpl:'<div class="centered"><img alt="Crying" src="${statics }/img/dataisnull.png"/><font style="color:red;font-size:20px;"><h2>没有找到匹配的数据!</h2></font></div>',
      tbar:{
       items:permissionValidate()
      },
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
	
})();

</script>