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
              <label class="control-label">IP：</label>
                <input name="ip" class=" control-text"  type="text" value="${ip }"/>
            </div>
            
            <div class="control-group span7">
              <label class="control-label">浏览器标识码：</label>
                <input name="remark" class=" control-text"  type="text" value="${remark }"/>
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
    var store ;
    
    
    (function(){
    	//权限判断

    	var audit_brand = ${sessionScope.ERCM_USER_PSERSSION['MN00C7'].menustatus==1};//审核权限
  		var pay_setting = ${sessionScope.ERCM_USER_PSERSSION['MN00C8'].menustatus==1};//发放权限
  		var edit_brand = ${sessionScope.ERCM_USER_PSERSSION['MN00C9'].menustatus==1};//修改权限
  		
  		Date.prototype.format = function(format){ 
            var o = { 
            "M+" : this.getMonth()+1, //month 
            "d+" : this.getDate(), //day 
            "h+" : this.getHours(), //hour 
            "m+" : this.getMinutes(), //minute 
            "s+" : this.getSeconds(), //second 
            "q+" : Math.floor((this.getMonth()+3)/3), //quarter 
            "S" : this.getMilliseconds() //millisecond 
            } 
 
            if(/(y+)/.test(format)) { 
            format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
            } 
 
            for(var k in o) { 
            if(new RegExp("("+ k +")").test(format)) { 
            format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
            } 
            } 
            return format; 
        } 
  		
        function permissionValidate(){
        	var array= new Array();
        	return array;
        }
        
        
    	
        var Grid = BUI.Grid,
          Store = BUI.Data.Store,
          columns = [
            { title: 'ip地址',width:'10%', dataIndex: 'ip',renderer:function(value,obj){
            	var  html = '<a target="_blank" href="http://www.hyzonghe.net/ip.jsp?ip='+value+'">'+value+'</a>';
                return html;
            }},
            
            { title: '浏览器标识码',width:'15%', dataIndex: 'remark'},
            { title: '会员',width:'15%', dataIndex: 'loginaccount'},
            { title: '上级会员',width:'15%', dataIndex: 'parentemployeeaccount'},
            {title : '注册时间',width : '15%',sortable : true,dataIndex : 'createtime',renderer : BUI.Grid.Format.datetimeRenderer},
            { title: '操作',width:'12%',sortable: false,renderer:function(value,obj){
            	var  html = "";
            	/* html += '<button type="button" onclick="openNewWindow(\'show_list_ip\',\'${ctx}/regeditlog/emplist?ip='+ obj.ip
				+ '\',\'会员明细\');"  class="button  button-warning botton-margin settingApi"><span class="icon-qrcode icon-white"></span>查看明细</button>'; */
                return html;
            }}
          ];
      
        store = new Store({
            url : '${ctx}/regeditlog/data',
            autoLoad : false,
            pageSize: 20,
            sortInfo : {
                field : 'createtime',
                direction : 'desc'
              },
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
    
    
    </script>
<!-- script end -->
  </div>
	
	</div>
	
</body>
</html>