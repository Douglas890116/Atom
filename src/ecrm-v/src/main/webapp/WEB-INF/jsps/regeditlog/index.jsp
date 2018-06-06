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
              <label class="control-label">浏览器标识：</label>
                <input name="remark" class=" control-text"  type="text" />
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

        function permissionValidate(){
        	var array= new Array();
        	
        	array.push({
                btnCls : 'button button-primary',
                text:'<span class="icon-file icon-white"></span>查看本机浏览器指纹',
                handler : function(){
                  open('http://www.urlconcepts.com/');
                }
            });
        	
        	return array;
        }
        
        
        
        var Grid = BUI.Grid,
          Store = BUI.Data.Store,
          columns = [
            { title: '浏览器标识',width:'20%', dataIndex: 'remark'},
            { title: '注册的会员数量（人）',width:'15%', dataIndex: 'count'},
            { title: '操作',width:'12%',sortable: false,renderer:function(value,obj){
            	var  html = "";
            	html += '<button type="button" onclick="openNewWindow(\'show_list_ip\',\'${ctx}/regeditlog/emplist?remark='+ obj.remark
				+ '\',\'会员明细\');"  class="button  button-warning botton-margin "><span class="icon-white"></span>查看明细</button>';
                return html;
            }}
          ];
      
        store = new Store({
            url : '${ctx}/regeditlog/pagelist',
            autoLoad : false,
            pageSize: 15,
            sortInfo : {
                field : 'count',
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