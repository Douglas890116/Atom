<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<style type="text/css">
	span.badge{
		height: 18px;
		padding-top: 8px;
	}
</style>
<body style="padding: 10px;">
<div class="demo-content" >
    <form id="searchForm" class="form-horizontal" style="outline: none;" method="post">
    <input name="enterprisecode" type="hidden" value="${param.enterprisecode }"/>
    <div class="row well" style="margin-left: 0px;position: relative;">
    <div style="position: relative;display: inline-block;">
    <div style="float: left;margin-right: 96px;">
        <div class="control-group span7">
            <label class="control-label">域名：</label>
            <input name="domainlink"  type="text" placeholder="域名" class="control-text"/>
         </div>
         <div class="control-group span7">
             <label class="control-label">品牌：</label>
             <input name="brandname"  type="text" placeholder="品牌" class="control-text"/>
         </div>
         <div class="control-group span7">
             <label class="control-label">归属用户：</label>
             <input name="employeeName"  type="text" placeholder="归属用户" class="control-text"/>
         </div>
         <div class="control-group span6 ">
		  <label class="control-label">域名类型：</label>
		  	<select name="domaintype" class="span3">
		    	<option value="">--请选择--</option>
		    	<option value="1">会员站点(企业域名)</option>
		    	<option value="2">会员站点(代理域名)</option>
		    	<option value="4">代理站点(代理域名)</option>
		    	<option value="5">代理站点(企业域名)</option>
		    	<option value="3">注册链接(代理域名)</option>
		    </select>
		 </div>
         <div class="control-group span7 toggle-display">
            <label class="control-label">上级用户：</label>
            <input name="parentemployeeName"  type="text" placeholder="上级用户" class="control-text"/>
         </div>
   		<div class="control-group span6 toggle-display">
           <label class="control-label">是否使用：</label>
             <select name="copyright" class="span3">
             	<option value="">--请选择--</option>
             	<option value="2">是</option>
             	<option value="1">否</option>
             </select>
         </div>
          <div style="position: absolute;right:0px;top:3px;">
           	 <button id="btnSearch" type="submit"  class="button button-primary"><span class="icon-search icon-white"></span>搜索</button>
           	 <%-- <button class="button button-danger" type="button" onclick="top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">返回列表</button> --%>
          </div>
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

	var Grid = BUI.Grid,
	 Dialog = BUI.Overlay.Dialog,
	  Store = BUI.Data.Store,
	  columns = [
	     		{ title: '域名', width: '20%',  sortable: false,dataIndex: 'domainlink',renderer:function(value,obj){
	     			if(obj.isdefualt==1){
	     				return '<span style="color: #428BCA;font-size:16px;">'+value+'</span><br/>';
	     			}else{
	     				if(obj.copyright/1!=1){
	     					//return '<span style="color:#BEBEBE;font-size:16px;text-decoration:line-through;">'+value+'</span><br/>';
	     					return '<span style="font-size:16px;">'+value+'</span><br/>';
	     				}else{
			     			return '<span style="font-size:16px;color:rgb(38, 162, 20);">'+value+'</span><br/>';	
	     				}
	     			}
	     		}},
	     		{ title: '是否使用',   width: '8%',  sortable: false,dataIndex: 'copyright',renderer:function(value,obj){
	     			//return value!=1?"<span style='color:#BEBEBE;text-decoration:line-through;'>是</span>":"<span class='badge badge-success'>否</span>";	
	     			return value!=1?"<span class='badge badge-warning'>是</span>":"<span class='badge badge-success'>否</span>";	
	     		}},
	     	  	{ title: '所属用户',width: '10%',sortable: false,dataIndex: 'employeeName',renderer:function(value,obj){
	     	    	return value;
	     	    }},
	     		{ title: '所属企业',width: '10%',sortable: false,dataIndex: 'enterpriseName',renderer:function(value,obj){
	     	    	return value;
	     	    }},
	     	   	{ title: '所属品牌',width: '10%',sortable: false,dataIndex: 'brandname',renderer:function(value,obj){
	     	    	return value;
	     	    }},
	     	   /* 	{ title: '上级用户',width: '10%',sortable: false,dataIndex: 'parentemployeeName',renderer:function(value,obj){
	     	    	var content=value==null?'':value;
	     	    	return content;
	     	    }}, */
	     	   { title: '绑定时间',width: '20%',sortable: false,dataIndex: 'createdate',renderer:function(value,obj){
	     	    	var create_date = BUI.Grid.Format.datetimeRenderer(value);
	     	    	return create_date;
	     	    }},
	     		{ title: '域名类型',width: '14%',sortable: false,dataIndex: 'domaintype',renderer:function(value,obj){
	     	    	var content="";
	     	    	switch(value){
	     	    	case 1:
	     	    		content = "<span class='badge badge-info'>会员站点(企业域名)</span>";
	     	    		break;
	     	    	case 2:
	     	    		content = "<span class='badge badge-info'>会员站点(代理域名)</span>";
	     	    		break;
	     	    	case 3:
	     	    		content = "<span class='badge'>注册链接(代理域名)</span>";
	     	    		break;
	     	    	case 4:
	     	    		content = "<span class='badge'>代理站点(代理域名)</span>";
	     	    		break;
	     	    	case 5:
	     	    		content = "<span class='badge'>代理站点(企业域名)</span>";
	     	    		break;
	     	    	}
	     	    	return content;
	     	    }},
	     		{ title: '是否默认',   width: '8%',  sortable: false,dataIndex: 'isdefualt',renderer:function(value,obj){
	     			var content= value==1?"<span class='badge badge-warning'>是</span>":"<span class='badge badge-success'>否</span>";
	     			return content;
	     		}}
	     	    
	     	  ];
	var form = new BUI.Form.HForm({
		  srcNode : '#objForm',
		}).render();
	var store = new Store({
	    url : '${ctx}/registerLink/domainInfoData',
	    autoLoad:false,
	    pageSize:15
	  }),grid = new Grid.Grid({
	    render:'#grid',
	    autoRender:true,
	    loadMask: true,
	    width:'100%',
	    columns : columns,
	    store: store,
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
	
	  
</script>