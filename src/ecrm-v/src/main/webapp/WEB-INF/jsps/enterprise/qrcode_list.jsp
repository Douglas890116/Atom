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
              <label class="control-label">账户名称：</label>
                <input name="qraccountname"  type="text"   class="control-text" placeholder="账户名称" />
            </div>
            
            <div class="control-group span7">
              <label class="control-label">账号：</label>
                <input name="qraccountno"  type="text"   class="control-text" placeholder="账号" />
            </div>
            
            <div class="control-group span7">
				<label class="control-label" >二维码类型：</label>
				<select name="qrtype">
					<option value="">--请选择--</option>
					<option value="1">微信收款</option>
					<option value="2">支付宝收款</option>
				</select>
			</div>
			
			<div class="control-group span7">
				<label class="control-label" >状态：</label>
				<select name="status">
					<option value="">--请选择--</option>
					<option value="0">启用</option>
					<option value="1">禁用</option>
				</select>
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
    
    function reloadPage(){
    	top.topManager.reloadPage();
    }
    
    //快捷支付禁用
    function thirdpartyDisable(enterprisethirdpartycode){
    	$.post(getRootPath()+"/enterpriseqcode/enableDisable",{lsh: enterprisethirdpartycode, status:1},function(data){
    		if(data.status = "success"){
    			reloadPage();
    		}else{
    			BUI.Message.Alert("操作失败","error");
    		}
    	},'json');
    }
    //快捷支付启用
    function thirdpartyEnable(enterprisethirdpartycode){
    	$.post(getRootPath()+"/enterpriseqcode/enableDisable",{lsh: enterprisethirdpartycode, status:0},function(data){
    		if(data.status = "success"){
    			reloadPage();
    		}else{
    			BUI.Message.Alert("操作失败","error");
    		}
    	},'json');
    }
    
    (function(){
    	//权限判断
    	var add_brand = ${sessionScope.ERCM_USER_PSERSSION['MN00EF'].menustatus==1};
  		var edit_setting = ${sessionScope.ERCM_USER_PSERSSION['MN00EH'].menustatus==1};
  		var delete_brand = ${sessionScope.ERCM_USER_PSERSSION['MN00EG'].menustatus==1};
  		var on_off = ${sessionScope.ERCM_USER_PSERSSION['MN00EI'].menustatus==1};
  		
        function permissionValidate(){
        	var array= new Array();
        	if(add_brand){
        		array.push({
                  btnCls : 'button button-primary',
                  text:'<span class="icon-file icon-white"></span>创建二维码',
                  handler : function(){
                	 openNewWindow('create_brand','${ctx}/enterpriseqcode/add','创建二维码');
                  }
              });
        	}
        	if(delete_brand){
        		array.push({
    				btnCls : 'button button-danger',
    				text : '<span class=" icon-trash icon-white"></span>删除二维码',
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
    								url : "${ctx}/enterpriseqcode/delete",
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
			{ title: '编号',width:'5%', dataIndex: 'lsh',renderer:function(value,obj){
            	 return value;
            } },      	
            { title: '账户名称',width:'8%', dataIndex: 'qraccountname'},
            { title: '账号',width:'12%', dataIndex: 'qraccountno'},
            
            { title: '收款二维码',width:'10%',sortable: false,dataIndex: 'qrurl',renderer:function(value,obj){
            	 return "<img src='"+value+"' width='86' height='30'/>";
            } },
            { title: '二维码类型',width:'7%', sortable: true,  dataIndex: 'qrtype', renderer : function(value, obj) {
            	var str = "";
            	if(obj.qrtype == 1) {
            		str = "微信二维码";
            	} else if(obj.qrtype == 2) {
            		str = "支付宝二维码";
            	}
            	return str;
            }},
            { title: '状态',width:'7%', sortable: true,  dataIndex: 'status', renderer : function(value, obj) {
            	switch(value){
					case 0:
						return "<font style='color:green;'>启用</font>";
						break;
					case 1:
						return "<font style='color:red;'>禁用</font>";
						break;
					default:
						break;
				}
            }},
            { title: '操作',width:'50%',sortable: false,renderer:function(value,obj){
            	var  html = "";
            	if(edit_setting) {
            		html += '<button type="button" onclick="openNewWindow(\'enterpriseqcode_update\',\'${ctx}/enterpriseqcode/edit?lsh='
						+ obj.lsh
						+ '\',\'编辑二维码\');" class="button  button-success botton-margin"><span class="icon-edit icon-white"></span>编辑</button>';
            	}
            	if(on_off) {
            		if(0 == obj.status) {
            			html+= '<button type="button" class="button botton-margin" style="background-color:#DEDBDB;" onclick=thirdpartyDisable("'+obj.lsh+'")><i class="icon-remove-circle icon-white"></i>禁用</button>';
               		} else {
               			html+= '<button type="button" class="button button-success botton-margin" style="background-color:#DEDBDB;" onclick=thirdpartyEnable("'+obj.lsh+'")><i class="icon-ok-circle icon-white"></i><b style="color:green;">启用</b></button>';
               		}
            	}
                return html;
            }}
          ];
      
        var store = new Store({
            url : '${ctx}/enterpriseqcode/data',
            autoLoad : false,
            pageSize: 15,
            remoteSort: true,
            sortInfo : {
                field : 'lsh',
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