<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body style="padding: 10px;">
    <div class="demo-content" >
<!-- 搜索页 ================================================== style=" margin:0 auto;  width:950px;"-->
<form id="searchForm" class="form-horizontal" style="outline: none;" method="post">
    <div class="row well" style="margin-left: 0px;position: relative;">
    <div style="position: relative;display: inline-block;">
    	  <div class="control-group span7">
            <label class="control-label">站点域名：</label>
              <input name="domainlink"  type="text" placeholder="站点域名" class="control-text"/>
          </div>
          <div class="control-group span7">
                <label class="control-label">运营品牌：</label>
                  <input name="brandname"  type="text" placeholder="运营品牌" class="control-text"/>
            </div>
          <div style="position: absolute;right: -66px;top: 3px;">
           	 <button id="btnSearch" type="submit"  class="button button-primary"><span class="icon-search icon-white"></span>搜索 </button>
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
var store = null;
(function(){

	var start_use =     ${sessionScope.ERCM_USER_PSERSSION['MN0036'].menustatus==1};
	var disable_use =   ${sessionScope.ERCM_USER_PSERSSION['MN0037'].menustatus==1};
	var sigle_delete =  ${sessionScope.ERCM_USER_PSERSSION['MN003H'].menustatus==1};
	var batch_delete =  ${sessionScope.ERCM_USER_PSERSSION['MN003W'].menustatus==1};

	//权限验证
	function permissionValidate(){
		var array= new Array();
		array.push({
	        btnCls : 'button button-primary',
	        text:'<span class="icon-file icon-white"></span>代理站点注册',
	        handler : function(){
	        	openNewWindow('registeragent','${ctx}/registerLink/agentSiteAdd','代理站点注册');
	     }});
		if(batch_delete){
			array.push({
		        btnCls : 'button button-danger bin',
		        text:'<span class=" icon-trash icon-white"></span>批量删除',
		        handler : function(){
		        	deleteMutilterm(grid,"${ctx}/registerLink/deleteSelect","sign")
		     }});
		}
		return array;
	}

	var Grid = BUI.Grid,
	  Store = BUI.Data.Store,
	  columns = [
	     		{ title: '站点域名',   width: '15%',  sortable: false,dataIndex: 'domainlink',renderer:function(value,obj){
	     				return '<span style="color: #428BCA;font-size:16px;" title="域名">'+value+(obj.isdefualt == 1 ? "(默认)":"")+'</span>';
	     		}},
	     		{ title: '运营品牌',   width: '6%',  sortable: false,dataIndex: 'brandname'},
	     		{ title: '分红',   width: 60,  sortable: false, dataIndex: 'dividend', renderer:function(value,obj){
	     	    	return "<span style='color:#E90C04;font-size: 18px;'>"+value*100 +"%</span>"; 
	     	    }},
	     	    { title: '占成',   width: 60,  sortable: false, dataIndex: 'share', renderer:function(value,obj){
	     	    	return "<span style='color:#E90C04;font-size: 18px;'>"+value*100 +"%</span>"; 
	     	    }},
	     	    { title: '洗码',   width: '55%',  sortable: false, dataIndex: 'bonus', renderer:function(value,obj){
	     	    	return "<span style='color:#5cb85c;font-size: 14px;'>"+value.replace(/Game/g,"")  +"</span>"; 
	     	    }},
	     	    { title: '模板名称',   width: '15%',  sortable: false,dataIndex: 'templatename', renderer:function(value,obj){
	     	    	return value+"-"+obj.webtemplatecode;
	     	    }},
	     	    { title: '链接状态',width: 70,sortable: false,dataIndex: 'linkstatus',renderer:function(value,obj){
	     	    	switch(value){
	     	    		case "1":
	         	    		return "启用";
	         	    		break;
	     	    		case "2":
	     	    			return "禁用";
	     	    			break;
	     	    	}
	     	    }},
	     	    { title : '操作',width: 350,  sortable: false,  renderer : function(value,obj){
	     	    	var temp = '';
	     	    	temp += '<button type="button" class="button button-info botton-margin" onclick="editContact(\''+obj.sign+'\')"><span class="icon-headphones icon-white"></span>联系方式</button>';
	     	    	if(sigle_delete){
		     	    	temp += '<button type="button" class="button button-success botton-margin" onclick="editDomain(\''+obj.sign+'\')"><span class="icon-edit icon-white"></span>编辑</button>';
	     	    	}
	     	    	if(obj.linkstatus==2){
	     	    		if(start_use){
			     	    	temp += '<button type="button" class="button button-success botton-margin" onclick="settingStatus(\'是否要启用该域名？\',\''+obj.sign+'\','+obj.linkstatus+')"><span class="icon-lock icon-white"></span>启用</button>';
	     	    		}
	     	    	}else{
	     	    		if(disable_use){
			     	    	temp += '<button type="button" class="button button-warning botton-margin" onclick="settingStatus(\'是否要禁用该域名？\',\''+obj.sign+'\','+obj.linkstatus+')"><span class="icon-lock icon-white"></span>禁用</button>';
	     	    		}
	     	    	}
	     	    	if(sigle_delete){
		     	    	temp += '<button type="button" class="button button-danger botton-margin" onclick="deleteUserDBODomain(store,\''+obj.sign+'\')"><span class="icon-remove icon-white"></span>删除</button>';
	     	    	}
	     	    	return temp;
	     	    }}
	     	  ];

	store = new Store({
	    url : '${ctx}/registerLink/takeAgentDBODomain',
	    autoLoad : false,
	    pageSize:15
	  }),
	  grid = new Grid.Grid({
	    render:'#grid',
	    autoRender:true,
	    loadMask: true,
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
})()


function editDomain(signx){
	openNewWindow('agentSiteUpdate','${ctx}/registerLink/agentSiteUpdate?sign='+signx,'编辑参数比例');
}

function editContact(domaincode){
		$.get('${ctx}/agentcontact/setting',{"domaincode": domaincode},
    		  function(html){
    			$("#contact-panel").remove();
    			if(html){
    				var dialog = new BUI.Overlay.Dialog({
    		            title:'联系方式',
    		            width:420,
    		            height:320,
    		            mask:true,
    		            bodyContent:html,
    		            success:function () {
    		            	var _self = this;
    		            	$.ajax({
    		    				type: "POST",
    		    				url: "${ctx}/agentcontact/saveContact",
    		    				data:$('#contactForm').serialize(),
    		    				dataType: "json",
    		    				statusCode:{404:function(){
    		    					BUI.Message.Alert("请求未发送成功",'error');
    		    				}},
    		    			    success: function(data) {
    		    				    if(data.status == "1"){
    		    				    	_self.close();
    		    				    }else{
    		    				    	BUI.Message.Alert(data.message,'warning');
    		    				    }
    		    			    }
    		    			});
    		            }
    		          });
    		      dialog.show();
    			}
    		 });
	}
</script>