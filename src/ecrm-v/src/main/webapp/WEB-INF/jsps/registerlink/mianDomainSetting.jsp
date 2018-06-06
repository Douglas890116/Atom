<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body style="padding: 10px;">
<div class="demo-content" >
    <form id="searchForm" class="form-horizontal" style="outline: none;" method="post">
    <input name="enterprisecode" type="hidden" value="${param.enterprisecode }"/>
    <div class="row well" style="margin-left: 0px;position: relative;">
    <div style="position: relative;display: inline-block;">
        <div style="float: left;">
        		<div class="control-group span7">
                <label class="control-label">企业域名：</label>
                <div class="controls">
                  <input name="domainlink"  type="text" placeholder="企业域名" class="control-text"/>
                </div>
              </div>
         </div>
         <div class="control-group span6">
		  <label class="control-label">域名类型：</label>
		  <div class="controls">
		  	<select name="domaintype" class="span3">
		    	<option value="">--请选择--</option>
		    	<option value="1">会员站点</option>
		    	<option value="5">代理站点</option>
		    </select>
		  </div>
		 </div>
   		<div class="control-group span6">
           <label class="control-label">是否使用：</label>
           <div class="controls">
             <select name="copyright" class="span3">
             	<option value="">--请选择--</option>
             	<option value="2">是</option>
             	<option value="1">否</option>
             </select>
           </div>
         </div>
          <div style="position: absolute;right:-150px;top:3px;">
           	 <button id="btnSearch" type="submit"  class="button button-primary"><span class="icon-search icon-white"></span>搜索</button>
           	 <button class="button button-danger" type="button" onclick="top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">返回列表</button>
          </div>
     </div>
   </div> 
</form>

<div class="search-grid-container well">
  <div id="grid"></div>
</div>



<div class="control-group span15 hide" id="dvBindDomain">
<form id="objForm" class="form-horizontal" style="outline: none;" method="post">
<div class="control-group span10">
  <label class="control-label">企业域名：</label>
  <div class="controls">
  	<input name="brandcode" type="hidden" value="${brandcode}">
    <input name="domainlink"  type="text" placeholder="输入主域名" data-rules="{required:true,regexp:/^(?=^.{3,255}$)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+$/}" data-messages="{regexp:'请输入正确的域名格式，例如:www.google.com'}" data-remote="${ctx}/registerLink/validateDomain"  class="control-text span6"  />
  </div>
  </div>
  <div class="control-group span7">
  <label class="control-label">域名类型：</label>
  <div class="controls">
  <select name="domaintype" data-rules="{required:true}">
    	<option value="">--请选择--</option>
    	<option value="1">会员站点</option>
    	<option value="5">代理站点</option>
    </select>
    </div>
    </div>
</form>
</div>

</div>
</body>
</html>
<script type="text/javascript">
var deleteDomain = function(object){
		BUI.Message.Confirm('确认要删除吗？如果删除,其子域名也将被删除!',function(){
			$.ajax({
				type: "POST",
				url: "${ctx}/registerLink/deleteMainDomain",
				data:{"sign":$(object).attr("sign"),"link":$(object).attr("link")},
				dataType: "json",
			    success: function(data) {
				    if(data.status == "1"){
			    		BUI.Message.Alert(data.message,function(){
			    			store.load(null);
			    		},'success');
				    }else{
				    	BUI.Message.Alert(data.message,'error');
				    }
			    }
			});
		},'warning');
	}

var settingDefualtDoamin = function(sign,domaintype){
	BUI.Message.Confirm("是否确认更换默认域名",function(){
		$.ajax({
			type: "POST",
			url: "${ctx}/registerLink/settingDefualtDomain",
			data:{"sign":sign,"domaintype":domaintype},
			dataType: "json",
		    success: function(data) {
			    if(data.status == "1"){
		    		BUI.Message.Alert(data.message,function(){
		    			store.load(null);
		    		},'success');
			    }else{
			    	BUI.Message.Alert(data.message,'error');
			    }
		    }
		});
	},'question');
}

	var Grid = BUI.Grid,
	 Dialog = BUI.Overlay.Dialog,
	  Store = BUI.Data.Store,
	  columns = [
	     		{ title: '域名',   width: '40%',  sortable: false,dataIndex: 'domainlink',renderer:function(value,obj){
	     			if(obj.isdefualt==1){
	     				return '<span style="color: #428BCA;font-size:16px;">'+value+'</span>';
	     			}else{
	     				if(obj.copyright/1!=1){
	     					return '<span style="color:#BEBEBE;font-size:16px;text-decoration:line-through;">'+value+'</span>';
	     				}else{
			     			return '<span style="font-size:16px;color:rgb(38, 162, 20);">'+value+'</span>';	
	     				}
	     			}
	     		}},
	     		{ title: '域名类型',width: '20%',sortable: false,dataIndex: 'domaintype',renderer:function(value,obj){
	     	    	var content=value==1?"会员站点":"代理站点";
	     	    	if(obj.copyright/1!=1){
	     	    		return "<span style='color:#BEBEBE;text-decoration:line-through;'>"+content+"<span>";
	     	    	}else{
	     	    		return content;
	     	    	} 
	     	    }},
	     		{ title: '是否默认',   width: '20%',  sortable: false,dataIndex: 'isdefualt',renderer:function(value,obj){
	     			var content= value==1?"是":"否";
	     			if(obj.copyright/1!=1){
	     	    		return "<span style='color:#BEBEBE;text-decoration:line-through;'>"+content+"<span>";
	     	    	}else{
	     	    		return content;
	     	    	} 
	     		}},
	     	    { title: '绑定时间',width: '20%',sortable: false,dataIndex: 'createdate',renderer:function(value,obj){
	     	    	var create_date = BUI.Grid.Format.datetimeRenderer(value);
	     	    	if(obj.copyright/1!=1){
	     	    		return "<span style='color:#BEBEBE;text-decoration:line-through;'>"+create_date+"<span>";
	     	    	}else{
	     	    		return create_date;
	     	    	} 
	     	    }},
	     	    { title: '是否使用',   width: '20%',  sortable: false,dataIndex: 'copyright',renderer:function(value,obj){
	     			return value!=1?"<span style='color:#BEBEBE;text-decoration:line-through;'>是</span>":"否";	
	     		}},
	     	    { title : '操作',width: '20%',  sortable: false,  renderer : function(value,obj){
	     	    	var temp = "";
	     	    	if(obj.isdefualt==0){
	     	    		temp += '<button type="button" class="button button-blue1 botton-margin" onclick="settingDefualtDoamin(\''+obj.sign+'\',\''+obj.domaintype+'\')" ><span class="icon-star icon-white"></span>设为默认</button>';;
		     	    	temp += '<button type="button" class="button button-danger botton-margin" onclick="deleteDomain(this)" sign="'+obj.sign+'" link="'+obj.domainlink+'"><span class="icon-remove icon-white"></span>删除</button>';;
	     	    	}else{
	     	    		temp += '<button type="button" class="button button-blue1 botton-margin" style="visibility: hidden" ><span class="icon-star icon-white"></span>设为默认</button>';;
	     	    	}
	     	    	return temp;
	     	    }}
	     	  ];
	var form = new BUI.Form.HForm({
		  srcNode : '#objForm',
		}).render();
	var store = new Store({
	    url : '${ctx}/registerLink/takeMainDomain',
	    autoLoad:false,
	    pageSize:15
	  }),dialog = new Dialog({
		    title:'绑定企业域名',
		    width:500,
		    height:200,
		    buttons:[
             {
                 text:'立即绑定',
                 elCls : 'button button-primary',
                 handler : function(){
                	 var objct = this;
                	 if(form.isValid()){
               			$.ajax({
               				type: "POST",
               				url: "${ctx}/registerLink/addMainDomain",
               				data:$('#objForm').serialize(),
               				dataType: "json",
               			    success: function(data) {
               				    if(data.status == "1"){
               			    		BUI.Message.Alert(data.message,function(){
               			    			$("#searchForm input[type='text']:not(:hidden):enabled").val(""); 
               				        	store.load(null);
               			    		},'success');
               				    }else{
               				    	BUI.Message.Alert(data.message,'warning');
               				    }
               				 objct.close();
               			    }
               			});
               		}
                 }
               },{
                 text:'取消',
                 elCls : 'button',
                 handler : function(){
                   this.close();
                 }
               }
             ],
             contentId:"dvBindDomain"
		  }),grid = new Grid.Grid({
	    render:'#grid',
	    autoRender:true,
	    loadMask: true,
	    width:'100%',
	    columns : columns,
	    store: store,
	    emptyDataTpl:'<div class="centered"><img alt="Crying" src="${statics }/img/dataisnull.png"/><font style="color:red;font-size:20px;"><h2>没有找到匹配的数据!</h2></font></div>',
	    tbar:{
	        items:[{
		        btnCls : 'button button-primary',
		        text:'<span class="icon-file icon-white"></span>绑定企业域名',
		        handler : function(){
		        	dialog.show();
		     }}]
	       },
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