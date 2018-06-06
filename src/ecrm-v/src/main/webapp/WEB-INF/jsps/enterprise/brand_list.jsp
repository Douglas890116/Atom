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
              <label class="control-label">品牌名称：</label>
                <input name="brandname"  type="text"   class="control-text" placeholder="品牌名称" />
            </div>
            <div class="control-group span14">
				<label class="control-label">成立时间：</label>
				<div class="bui-form-group" data-rules="{dateRange : true}">
					<input name="brandfoundedtime_begin" type="text" class="calendar calendar-time" placeholder="起始时间" /> 
					<span>&nbsp;-&nbsp;</span>
					<input name="brandfoundedtime_end" type="text"  class="calendar calendar-time"  placeholder="结束时间"/>
				</div>
                <div style="margin-right: auto;margin: -30px 425px;">
                  <select onchange="changeFormatDate(this,'brandfoundedtime_begin','brandfoundedtime_end')" id="selectDateId" style="width:85px;">
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
        	if(add_brand){
        		array.push({
                  btnCls : 'button button-primary',
                  text:'<span class="icon-file icon-white"></span>创建品牌',
                  handler : function(){
                	 openNewWindow('create_brand','${ctx}/EOBrand/add','创建品牌');
                  }
              });
        	}
        	if(delete_brand){
        		array.push({
    				btnCls : 'button button-danger',
    				text : '<span class=" icon-trash icon-white"></span>删除品牌',
    				handler : function() {
    					var selectItem = grid.getSelection();
    					if (selectItem.length == 0) {
    						BUI.Message.Alert('请选择需要删除的数据', 'info');
    					} else {
    						BUI.Message.Confirm('是否确定删除？',function() {
    							var sign = new Array();
    							BUI.each(selectItem,function(item) {
    										sign.push(item.brandcode);
    							});
    							$.ajax({
    								type : "POST",
    								url : "${ctx}/EOBrand/Delete",
    								data : {"sign" : sign},
    								dataType : "json",
    								success : function(data) {
    									if (data.status == 1) {
    										BUI.Message.Alert(data.message,'success');
    										grid.removeItems(selectItem);
    									} else {
    										BUI.Message.Alert(data.message,'warning');
    									}
    								}
    							});
    						}, 'question');
    					}
    				}
    			});
        	}
        	return array;
        }
    	
        var Grid = BUI.Grid,
          Store = BUI.Data.Store,
          columns = [
			{ title: '品牌编码',width:'8%', dataIndex: 'brandcode',renderer:function(value,obj){
            	 return obj.brandcode.split("_")[1];
            } },      	
            { title: '品牌名称',width:'8%', dataIndex: 'brandname'},
            { title: '品牌LOGO',width:'10%',sortable: false,dataIndex: 'logopath',renderer:function(value,obj){
            	 return "<img src='"+value+"' width='86' height='30'/>";
            } },
            { title: '品牌成立时间',width:'12%', sortable: true,  dataIndex: 'brandfoundedtime',  showTip: true,renderer:BUI.Grid.Format.datetimeRenderer },
            { title: 'Web模板',width:'10%', sortable: true,  dataIndex: 'templatename', renderer : function(value, obj) {
            	return obj.webtemplate?obj.webtemplate.templatename:"";
            }},
            { title: '模板编码',width:'8%', dataIndex: 'webtemplatecode'},
            { title: '默认打码倍数',width:'10%', sortable: true,  dataIndex: 'defualtchip', renderer : function(value, obj) {
            	return value+"倍";
            }},
            { title: '操作',width:'50%',sortable: false,renderer:function(value,obj){
            	var  html = "";
            	html += '<button type="button" onclick="openNewWindow(\'Setting_MianDomain\',\'${ctx}/EOBrand/Edit?brandcode='
						+ obj.brandcode
						+ '\',\'编辑品牌\');" class="button  button-success botton-margin"><span class="icon-edit icon-white"></span>编辑</button>';
				html += '<button class="button button-warning botton-margin" onclick="LogicFunc.defualtship('+obj.defualtchip+',\''+obj.brandcode+'\')" ><span class="icon-off icon-white"></span>默认打码</button>';
				if(game_setting){
            		html += '<button class="button button-info botton-margin" onclick="loadGame(this)" data-code="'+obj.brandcode+'" data-encode="'+obj.enterprisecode+'" ><span class="icon-off icon-white"></span>游戏开关</button>';
            	}
				if(customer_line){
					html+='<button class="button button-primary" style="background-color:#8C894B;" onclick=jumpBrandContact("'+obj.brandcode.split("_")[1]+'","'+obj.brandname+'")><i class="icon-headphones icon-white"></i>客服专线</button>';
				}
                return html;
            }}
          ];
      
        var store = new Store({
            url : '${ctx}/EOBrand/data',
            autoLoad : false,
            pageSize: 15,
            remoteSort: true,
            sortInfo : {
                field : 'brandfoundedtime',
                direction : 'desc'
              }
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
    
    function jumpBrandContact(brandcode,brandname){
    	top.topManager.openPage({
			id : 'MN0094',
			href : '${ctx}/brandContact/index?brandcode='+brandcode+"&brandname="+brandname,
			title : '客服专线'
		});
    }
    </script>
<!-- script end -->
  </div>
	
	</div>
	<!--     公司出款方式隐藏域 -->
     <div id="setdefualtchip" style="display: none;">
      <form id="defualtchipform" class="form-horizontal">
          <div class="control-group span10">
              <label class="control-label">流水倍数：</label>
              <input name="brandcode" type="hidden"  />
              <input name="defualtchip" type="text"   data-rules="{required:true,number:true}"  class="control-text" placeholder="流水倍数" />
            </div>
      </form>
    </div>
</body>
</html>