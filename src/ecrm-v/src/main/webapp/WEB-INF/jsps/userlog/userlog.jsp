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
      		
            <div class="control-group span14 ">
                <label class="control-label">时间：</label>
                <div class="bui-form-group" data-rules="{dateRange : true}">
                  <input name="createtime_begin"  type="text" class="calendar calendar-time" value="${param.createtime_begin }"  placeholder="起始时间" />
                  <span>&nbsp;-&nbsp;</span>
                  <input  name="createtime_end" type="text" class="calendar calendar-time" value="${param.createtime_end }"   placeholder="结束时间" />
                </div>
                <div style="margin-right: auto;margin: -30px 425px;">
                  <select onchange="changeFormatDate(this,'createtime_begin','createtime_end')"  id="quiklychoicetime"   style="width:85px;">
                                <option value="">请选择</option>
                                <option value="1">今天</option>
                                <option value="2">昨天</option>
                                <option value="3">三天</option>
                                <option value="4">本周</option>
                                <option value="5">上周</option>
                                <option value="6">半月</option>
                                <option value="7">本月</option>
                                <option value="8">上月</option>
                                <option value="9">半年</option>
                                <option value="10">本年</option>
                              </select>
                </div>
            </div>
            
            <div class="control-group span7">
              <label class="control-label">业务类别：</label>
                <select name="operatype">
                    <option value="">--请选择--</option>
                    <c:forEach var="type" items="${operatypes}">
					    <option value="${type.key}" >${type.value}</option>
					</c:forEach>
                </select>
            </div>
            
            <div class="control-group span7">
              <label class="control-label">会员账号：</label>
                <input name="loginaccount" class="input-small control-text"  type="text" />
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
    	
    	var begintime = $("input[name='createtime_begin']");
 	   var endtime = $("input[name='createtime_end']");
 	   if(!begintime.val()&&!endtime.val()){
 		   begintime.val(getDate("begintime"));
 		   endtime.val(getDate("endtime"));
 		   $("#quiklychoicetime option:eq(1)").attr("selected","selected");
 	   }
 	   
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
            { title: '用户账号',width:'6%', dataIndex: 'loginaccount'},
            { title: '业务类别',width:'10%', dataIndex: 'operatype'},
            { title: '内容描述',width:'50%', dataIndex: 'content'},
            { title: '操作人',width:'6%', dataIndex: 'operaer'},
            { title: '操作时间',width:'10%', dataIndex: 'createtime',renderer:BUI.Grid.Format.datetimeRenderer},
            { title: '备注',width:'18%', dataIndex: 'remark'}
          ];
      
        store = new Store({
            url : '${ctx}/userlog/userlogdata',
            autoLoad : false,
            pageSize: 15,
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