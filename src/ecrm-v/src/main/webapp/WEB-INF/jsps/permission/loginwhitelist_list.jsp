<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body style="padding: 10px;">
    <div class="demo-content">
   <form id="searchForm" class="form-horizontal" style="outline: none;" method="post" data-depends="{'#btn:click':['toggle'],'#btn3:click':['clearFields']}">
    
    <div class="row well" style="margin-left: 0px;position: relative;">
    <div style="position: relative;display: inline-block;">
      <div style="float: left;margin-right: 96px;">
      		<div class="control-group span7">
              <label class="control-label">IP地址：</label>
              <div class="controls">
                <input name="ip"  type="text"  class="control-text" value="${ipaddress }"/>
              </div>
            </div>
<!--           toggle-display -->
          	 <div style="position: absolute;right: 15px;top: 3px;">
             	 <button id="btnSearch" type="submit"  class="button button-primary"> 搜 索 </button>
            </div>
        </div>
    </div> 
    </div>
    </form>
    <!-- 搜索页 ================================================== -->
    
    
    <div class="search-grid-container well">
      <div id="grid">
    </div>
<!-- 用户新增按钮是否有操作权限 -->
<input type="hidden" value="" id="add_isoperate_Id" />
    <script type="text/javascript">
    
	    function permissionValidate(){
	      	var array= new Array();
	   		array.push({
	             btnCls : 'button button-primary',
	             text:'<span class="icon-file icon-white"></span>添加IP到白名单',
	             handler : function(){
	            	 openNewWindow('updateadd','${ctx}/LoginWhiteList/add','添加IP到白名单');
	             }
	         });
	   		array.push({
	   			btnCls : 'button button-danger bin',
		        text:'<span class=" icon-trash icon-white"></span>批量删除',
		        handler : function(){
		        	//公用的删除js,需要传入不同url路径
	    	        deleteMutilterm(grid,"${ctx}/LoginWhiteList/deleteSelectIp","sign");//最后一个参数是主键/字段名
		     }});
	   		return array;
	    }
	    
	    
        var Grid = BUI.Grid,
          Store = BUI.Data.Store,
          columns = [
            { title: '企业编码',width:'15%',dataIndex: 'enterprisecode'},
            { title: '白名单IP',width:'15%',dataIndex: 'ip'},
            { title: '备注',width:'45%',dataIndex: 'remark'},
            { title: '操作',width:'25%',dataIndex: 'lsh',renderer:function(value,obj){
            	var  action = "";
              	action += '<button type="button" onclick="updateWhiteList('+obj.lsh+')" class="button  button-primary button-space see-detail" ><span class="icon-th-large icon-white"></span>编辑</button>'
              	/* if(obj.sendemployeecode != '${sessionScope.ERCM_USER_SESSION.employeecode }'){
              		action += '<button type="button" class="button  button-success button-space" onclick="openNewWindow(\'replymessage\',\'${ctx}/Message/ReplyMessage?sendemployeecode='+obj.sign+'\',\'消息回复\')" ><span class="icon-comment icon-white"></span>回复</button>'
              	} */
              	action +='<button type="button" class="button button-danger button-space"  onclick=delMessage(this,"'+obj.sign+'")><span class="icon-remove icon-white"></span> 删 除 </button></li>';
              	return action;
            }}
          ];
      
        var store = new Store({
            url : '${ctx}/LoginWhiteList/data',
            autoLoad : false,
            params:{},
            pageSize:15,
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
            emptyDataTpl:'<div class="centered"><img alt="Crying" src="${statics }/img/dataisnull.png"/><font style="color:red;font-size:20px;"><h2>没有找到匹配的数据!</h2></font></div>',
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

        $("#searchForm").submit(function(){
      	  var obj = BUI.FormHelper.serializeToObject(this);
            obj.start = 0;
            store.load(obj);
      	  return false;
        }).submit();
        
        function delMessage(obj,val){
			BUI.Message.Confirm('是否确定删除？',function() {
				var array = new Array();
					array.push(val);
				$.ajax({
					type : "POST",
					url : "${ctx}/LoginWhiteList/deleteIp",
					data : {"array" : array,"sign" : val},
					dataType : "json",
					success : function(data) {
						if (data.status == 1) {
							BUI.Message.Alert(data.message,'success');
							$(obj).parent().parent().parent().parent().remove();
						} else {
							BUI.Message.Alert(data.message,'warning');
						}
					}
				});
			}, 'question');
		}
        
        function updateWhiteList(lsh){
			top.topManager.openPage({
				id : 'update_whitelist',
				href : '${ctx}/LoginWhiteList/edit?lsh='+lsh,
				title : '修改白名单'
			});
		}
    </script>
<!-- script end -->
  </div>
	
	</div>
</body>
</html>