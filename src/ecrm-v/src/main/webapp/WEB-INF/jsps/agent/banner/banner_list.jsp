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
			<div class="control-group">
				<label class="control-label" style="width: 70px; text-align: left;">品牌:</label>
				<div class="controls">
					<select style="width: 248px;" name="brandcode">
						<c:forEach items="${list}" var="brand">
							<option value="${brand.brandcode}">${brand.brandname}</option>
						</c:forEach>
					</select>
				</div>
			</div>
	       	<div class="control-group">
				<label class="control-label" style="width: 70px; text-align: left;">banner类型:</label>
				<div class="controls">
					<select style="width: 248px;" name="bannertype">
						<option value="">--- 请选择 ---</option>
						<option value="PC">ＰＣ端图片</option>
						<option value="H5">移动端图片</option>
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
    	var add_brand = ${sessionScope.ERCM_USER_PSERSSION['MN00CE'].menustatus==1};
  		var edit_brand = ${sessionScope.ERCM_USER_PSERSSION['MN00CF'].menustatus==1};
  		var delete_brand = ${sessionScope.ERCM_USER_PSERSSION['MN00CG'].menustatus==1};
  		
  		
        function permissionValidate(){
        	var array= new Array();
        	if(add_brand){
        		array.push({
                  btnCls : 'button button-primary',
                  text:'<span class="icon-file icon-white"></span>新增图片',
                  handler : function(){
                	 openNewWindow('create_banner','${ctx}/agent/banner/add','新增图片');
                  }
              });
        	}
        	if(delete_brand){
        		array.push({
    				btnCls : 'button button-danger',
    				text : '<span class=" icon-trash icon-white"></span>删除图片',
    				handler : function() {
    					var selectItem = grid.getSelection();
    					if (selectItem.length == 0) {
    						BUI.Message.Alert('请选择需要删除的数据', 'info');
    					} else {
    						BUI.Message.Confirm('是否确定删除？',function() {
    							var sign = new Array();
    							BUI.each(selectItem,function(item) {
    										sign.push(item.lsh);
    							});
    							$.ajax({
    								type : "POST",
    								url : "${ctx}/agent/banner/delete",
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
		{ title: '编号',width:'10%', dataIndex: 'lsh'},
            { title: '品牌名称',width:'15%', dataIndex: 'brandname'},
            { title: '图片',width:'25%',sortable: false,dataIndex: 'imgpath',renderer:function(value,obj){
           	 return "<img src='"+value+"' width='86' height='30'/>";
            }},
            { title: '图片类型',width:'10%', dataIndex: 'bannertype', renderer:function(value,obj){
            	switch(value) {
            	case 'PC': return 'PC端'
            	case 'H5': return 'H5端'
            	}
            }},
            /* { title: '图片链接',width:'40%', dataIndex: 'linkpath'},
            { title: '排序',width:'5%', dataIndex: 'ord'}, */
            { title: '操作',width:360,sortable: false,renderer:function(value,obj){
            	var  html = "";
            	html += '<button type="button" onclick="openNewWindow(\'edit_banner\',\'${ctx}/agent/banner/edit?lsh='
						+ obj.lsh
						+ '\',\'编辑Banner\');" class="button  button-success botton-margin"><span class="icon-edit icon-white"></span>编辑</button>';
				if(!edit_brand){
					html = "";
            	}
                return html;
            }}
          ];
      
        var store = new Store({
            url : '${ctx}/agent/banner/data',
            autoLoad : false,
            pageSize: 15,
            remoteSort: true,
            sortInfo : {
                field : 'lsh',
                direction : 'asc'
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
    
    </script>
<!-- script end -->
  </div>
	
	</div>
	
</body>
</html>