<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
      <link rel="stylesheet" href="${statics}/css/bs3/bui-min.css" />
      <link rel="stylesheet" href="${statics}/css/bs3/dpl-min.css" />
      <script src="${statics }/js/jquery-1.8.1.min.js"></script>
      <script src="${statics }/js/bui.js"></script> 
      <script src="${statics }/js/config.js"></script>
      <script type="text/javascript" src="${statics }/js/custom/submitJS.js"></script>
      <script type="text/javascript" src="${statics }/js/custom/commonJS.js"></script>
  </head>
<body>
  <div class="demo-content">
    <div class="row">
      <div class="span4">
        <div class="panel panel-head-borded panel-small">
          <div class="panel-header">权限组</div>
          <div id="treed">
            
          </div>
        </div>
        
      </div>
      <div class="span16" style="width:1510px;">
        <div class="panel panel-head-borded panel-small">
          <div class="panel-header">没有该权限组的所有用户</div>
          <div id="grid">
          
          </div>
        </div>
      </div>
    </div>
  </div>
    <input type="hidden" id="permissiongroupcode_Id" />
</body>
</html>
<script type="text/javascript">
        BUI.use(['bui/tree','bui/grid','bui/data'],function (Tree,Grid,Data) {
        //{text : '代理部',id : 'PG000000'},{text : '风控部',id : ''},{text : '财务部',id : ''},{text : '客服部',id : ''}
      var buff = [];
      //加载权限组
      $.ajax({
  		url:'${ctx}/common/findPermissionGroup',
  		type:"post",
  		dataType:"json",
  		async: false,
  		success:function(data){
  			buff.push("[");
  			for (var i = 0; i < data.rows.length; i++) {
  				buff.push("{");
  				buff.push("text :'"+data.rows[i].permissiongroup+"'");
  				buff.push(",");
  				buff.push("id :");
  				buff.push("'"+data.rows[i].permissiongroupcode+"'");
  				buff.push("}");
  				if((i+1)!=data.rows.length){
  					buff.push(",");
  				}
			}
  			buff.push("]");
  		}
  	});
      var temp = eval(buff.join(""));
      console.log("批量授权,权限加载为空===="+temp);
      var data = [{text : '现金网',id : 'xjw',expanded : true,children: temp}];
      //由于这个树，不显示根节点，所以可以不指定根节点
      var tree = new Tree.TreeList({
        render : '#treed',
        nodes : data,
        showLine : true,
        height:532
      });
      tree.render(); 
   
     var store = new Data.Store({
          url : '/ecrm/EmployeeMPG/queryNotPermissiongroupEmployee',
          autoLoad:true, //自动加载数据
          pageSize : 10
        }),
       
        columns = [{ title: '<input type="checkbox" name="selectAll" class="x-grid-checkbox" onclick="selectAll(this)"/><div style="position:absolute;top:8px;left:24px;">全选</div>',width: 50,sortable: false,
							renderer:function(value,obj){return '<input type="checkbox" name="selectName" value="'+obj.employeecode+'" class="x-grid-checkbox"/>'}},
           			{ title: '用户名',   width: 100,  sortable: false,  dataIndex: 'loginaccount'},
                   { title: '显示别名',   width: 100,  sortable: false,   dataIndex: 'displayalias'},
                   { title: '最后登录时间',   width: 150,  sortable: false,   dataIndex: 'lastlogintime',renderer:BUI.Grid.Format.dateRenderer}
                 ],
        
        grid = new Grid.Grid({
          render:'#grid',
          columns : columns,
          height:532,
          forceFit : true,
          loadMask: true,
          store: store,
          emptyDataTpl:'<div class="centered"><img alt="Crying" src="${statics }/img/dataisnull.png"/><font style="color:red;font-size:20px;"><h2>没有找到匹配的数据!</h2></font></div>',
          // 上面工具栏
          tbar:{
           items:[{
    	        btnCls : 'button button-primary',
    	        text:"批量授权",
    	        handler : function(){
    	        	var buffvalue=[];
    		        $("input[name='selectName']:checked").each(function(index){
    		        	index++;
    		        	buffvalue.push($(this).val());
    					if($("input[name='selectName']:checked").length != index){
    						buffvalue.push(",");
    						
    					}
    		        });
    		        //获取权限组编码
	    		    var permissiongroupcode = $("#permissiongroupcode_Id").val();
    		        if(isEmpty(permissiongroupcode)){
    		        	BUI.Message.Alert('请选择权限组之后再操作','warning');
    		        	return false;
    		        }
    		        if(isEmpty(buffvalue.join(""))){
    		        	BUI.Message.Alert("请先选择需要授权的用户！！！","warning");
    		        	return false;
    		        }
    		        BUI.Message.Confirm('确认需要批量授权吗？',function(){
        		        //将选择的权限赋予选择的用户
        		        $.ajax({
                    		url:'${ctx}/EmployeeMPG/saveBatchPerssionGroup',
                    		type:"post",
                    		dataType:"json",
                    		data:{employeecode:buffvalue.join(""),permissiongroupcode:permissiongroupcode},
                    		success:function(data){
                    			if(data.status > 0){
                    				BUI.Message.Alert('授权成功','success');
                    				store.load({permissiongroupcode : permissiongroupcode,start : 0});
                    			}else{
                    				BUI.Message.Alert('授权失败','error');
                    			}
                    		}
                    	});
    		        },'question');
    	        }
	  		}]
          },  
          // 顶部工具栏
            bbar : {
              //items 也可以在此配置
              // pagingBar:表明包含分页栏
              pagingBar:true
            }
        });
      grid.render();
 
       tree.on('selectedchange',function(ev){
        var node = ev.item;
      	//只有点击叶子节点才能加载Grid
        if(node.leaf){ 
         //将选择的权限组编码保存起来
          $("#permissiongroupcode_Id").val(node.id);
          //加载对应的数据，同时将分页回复到第一页
          store.load({permissiongroupcode : node.id,start : 0});
        }
      }); 
       //var firstLeaf = tree.findNode('xjw'); //获取第一个叶子节点
      //tree.setSelected(firstLeaf); 
    });
    </script>