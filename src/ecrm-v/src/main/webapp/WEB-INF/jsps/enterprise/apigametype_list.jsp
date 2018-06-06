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
              <label class="control-label">游戏种类：</label>
                <select name="biggametype">
                    <option value="">--请选择--</option>
                    <c:forEach var="game" varStatus="status" items="${listGame}">
                    	<option value="${game.gametype }">${game.gamename }</option>
                    </c:forEach>
                </select>
            </div>
            
            <div class="control-group span7">
              <label class="control-label">英文名称：</label>
                <input name="enname" class="input-small control-text"  type="text" />
            </div>
            
            <div class="control-group span7">
              <label class="control-label">业务分类：</label>
                <input name="stype" class="input-small control-text"  type="text" />
            </div>
            
            <div class="control-group span7">
              <label class="control-label">分类：</label>
                <input name="category" class="input-small control-text"  type="text" />
            </div>
            
            <div class="control-group span7">
              <label class="control-label">分类2：</label>
                <input name="category2" class="input-small control-text"  type="text" />
            </div>
            
            <div class="control-group span7">
              <label class="control-label">分类3：</label>
                <input name="category3" class="input-small control-text"  type="text" />
            </div>
            
            <div class="control-group span7">
              <label class="control-label">是否支持WEB：</label>
                <div class="controls">
                  	<select name="isweb" >
	                    <option value="">--请选择--</option>
	                    <option value="0">支持</option>
	                    <option value="1">不支持</option>
                	</select>
                </div>
            </div>
            
            <div class="control-group span7">
              <label class="control-label">是否支持H5：</label>
                <div class="controls">
                  	<select name="ish5" >
	                    <option value="">--请选择--</option>
	                    <option value="0">支持</option>
	                    <option value="1">不支持</option>
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
    var store ;
    
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
                  text:'<span class="icon-file icon-white"></span>新增游戏',
                  handler : function(){
                	 openNewWindow('create_brand','${ctx}/apigametype/edit','新增参数');
                  }
              });
        	}
        	return array;
        }
        
        
    	
        var Grid = BUI.Grid,
          Store = BUI.Data.Store,
          columns = [
            { title: '游戏平台',width:'5%', dataIndex: 'biggametype'},
            { title: '英文',width:'8%', dataIndex: 'enname'},
            { title: '中文简体',width:'8%', dataIndex: 'cnname'},
            { title: '中文繁体',width:'8%', dataIndex: 'trname'},
            { title: '日文',width:'6%', dataIndex: 'japname'},
            { title: '韩文',width:'6%', dataIndex: 'krname'},
            
            { title: '分类',width:'6%', dataIndex: 'category'},
            { title: '分类2',width:'6%', dataIndex: 'category2'},
            { title: '分类3',width:'6%', dataIndex: 'category3'},
            
            { title: '代码WEB',width:'5%', dataIndex: 'gamecodeweb'},
            { title: '代码H5',width:'5%', dataIndex: 'gamecodeh5'},
            
            { title: '游戏图片',width:'6%', dataIndex: 'imagename'},
            
            { title: '支持WEB',width:'5%', dataIndex: 'isweb', renderer:function(value,obj){
            	if(value == "0") return "<font color='green'>支持</font>";
            	else {
            		return "不支持";
            	}
            }},
            
            { title: '支持H5',width:'5%', dataIndex: 'ish5', renderer:function(value,obj){
            	if(value == "0") return "<font color='green'>支持</font>";
            	else {
            		return "不支持";
            	}
            }},
            { title: '业务分类',width:'6%', dataIndex: 'stype'},
            { title: '排序',width:'3%', dataIndex: 'ord'},
            
            { title: '操作',width:'16%',sortable: false,renderer:function(value,obj){
            	var  html = "";
            	html += '<button type="button" onclick="openNewWindow(\'edit_apikey\',\'${ctx}/apigametype/edit?lsh='+obj.lsh
						+ '\',\'编辑参数\');" class="button  button-success botton-margin"><span class="icon-edit icon-white"></span>编辑</button>';
				
				if(obj.isweb == "0") {
					html += '<button type="button" onclick="test1(\''+ obj.lsh+'\', 1);" class="button  button-info botton-margin">禁用WEB</button>';
				} else {
					html += '<button type="button" onclick="test1(\''+ obj.lsh+'\', 0);" class="button  button-info botton-margin">启用WEB</button>';
				}
				
				if(obj.ish5 == "0") {
					html += '<button type="button" onclick="test2(\''+ obj.lsh+'\', 1);" class="button  button-info botton-margin">禁用H5</button>';
				} else {
					html += '<button type="button" onclick="test2(\''+ obj.lsh+'\', 0);" class="button  button-info botton-margin">启用H5</button>';
				}
				
                return html;
            }}
          ];
      
        store = new Store({
            url : '${ctx}/apigametype/pagelist',
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
    
    function test1(lsh,stype) {//web
		BUI.Message.Confirm('是否确定操作？',function() {
			$.ajax({
				type : "POST",
				url : "${ctx}/apigametype/updateweb",
				data : {"isweb" : stype, "lsh" : lsh},
				dataType : "json",
				success : function(data) {
					if (data.status == 1) {
						BUI.Message.Alert(data.message,'success');
						store.load();
					} else {
						BUI.Message.Alert(data.message,'warning');
					}
				}
			});
		}, 'question');
    }
    function test2(lsh,stype) {//h5
    	BUI.Message.Confirm('是否确定操作？',function() {
			$.ajax({
				type : "POST",
				url : "${ctx}/apigametype/updateh5",
				data : {"ish5" : stype, "lsh" : lsh},
				dataType : "json",
				success : function(data) {
					if (data.status == 1) {
						BUI.Message.Alert(data.message,'success');
						store.load();
					} else {
						BUI.Message.Alert(data.message,'warning');
					}
				}
			});
		}, 'question');
    }
    
    </script>
<!-- script end -->
  </div>
	
	</div>
	
</body>
</html>