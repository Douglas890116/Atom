<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body style="padding: 10px;">
    <div class="demo-content">
<!-- 搜索页 ================================================== style=" margin:0 auto;  width:950px;"-->
 <form id="searchForm" class="form-horizontal" style="outline: none;" method="post">
    <div class="row well" style="margin-left: 0px;position: relative;">
    <div style="position: relative;display: inline-block;">
      <div style="float: left;margin-right: 96px;">
      		<div class="control-group span7">
              <label class="control-label">流程编号：</label>
                <input name="flowcode" type="text" data-tip='{"text":"流程编号"}' class="control-text"/>
            </div>
            <div class="control-group span7">
              <label class="control-label">流程名称：</label>
                <input name="flowname" type="text" data-tip='{"text":"流程名称"}' class="control-text"/>
            </div>
            <div class="control-group span7">
              <label class="control-label">状态：</label>
              	<select name="enable"  aria-pressed="false" aria-disabled="false"   class="bui-form-field-select bui-form-field">
					<option value="">请选择</option>
                    <option value="1">启用</option>
                    <option value="0">禁用</option>
	             </select>
            </div>
          </div>
      	 <div style="position: absolute;right: 15px;top: 3px;">
	         	 <button id="btnSearch" type="submit"  class="button button-primary"><span class="icon-search icon-white"></span> 搜 索 </button>
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
var add_flow    =    ${sessionScope.ERCM_USER_PSERSSION['MN001P'].menustatus == 1};
var update_flow =    ${sessionScope.ERCM_USER_PSERSSION['MN001Q'].menustatus == 1};
var delete_flow =    ${sessionScope.ERCM_USER_PSERSSION['MN001R'].menustatus == 1};
var audit_setting =  ${sessionScope.ERCM_USER_PSERSSION['MN003V'].menustatus == 1};

//权限验证
function permissionValidate(){
  var array= new Array();
  if(add_flow){
    array.push({
          btnCls : 'button button-primary',
          text:"<span class='icon-file icon-white'></span>新增取款流程",
          handler : function(){
        	  openNewWindow('create_takeflow','${ctx}/workingFlow/takeWorkingFlowConfigurationAdd','新增取款流程');
          }
      });
  }
  return array;
}
var Grid = BUI.Grid,
  Store = BUI.Data.Store,
  columns = [
             { title: '流程编号',   width: "10%",  sortable: true,dataIndex:'flowcode'},
             { title: '流程名称',   width: "10%",  sortable: true,dataIndex:'flowname'},
             { title: '流程标志',   width: "10%",  sortable: false,  dataIndex: 'flowtype'}, 
             { title: '流程启动条件',   width: "20%",  sortable: false, dataIndex:'flowthreshold',renderer : function(value,obj){
            	 return "取款大(等)于<b><font color='red'> "+value+ " </font></b>元";
             }},
             { title: '流程执行顺序',   width: "10%",  sortable: false, dataIndex: 'flowsort'},
             { title: '标准处理时间',   width: "10%",  sortable: false,dataIndex:'handletime',renderer : function(value,obj){
            	 return value+" 秒"
             }},
             { title: '流程创建时间',   width: "10%",  sortable: false,dataIndex:'createtime',renderer:BUI.Grid.Format.dateRenderer},
             { title: '流程状态',   width: "10%",  sortable: false,renderer:function(value,obj){
              	switch(obj.enable){
              		case 1:
              			return "启用";
              		case 0:
              			return "禁用";
              	}
              }},
             { title : '操作',   width: 280,  sortable: false,  renderer : function(value,obj){
               var temp = '';
               if(update_flow){
             	 temp  += '<button type="button" style="margin-right:9px;"  class="button  button-success " onclick="isUpdateOperate(this,\'编辑取款流程\',\'/workingFlow/takeWorkingFlowConfigurationUpdate?flowcode=\')" name="'+obj.flowcode+'" ><span class="icon-cog icon-white"></span>编辑  </button>';
               }
               if(audit_setting){
	               temp += '<button type="button" class="button button-info" style="margin-right:9px;"  onclick="openAddTakeApprove(this);" name="'+obj.flowcode+'" ><span class="icon-globe icon-white"></span>审批人设置</button>';
               }
               if(delete_flow){
             	  temp += '<button type="button" data-sign="'+obj.sign+'" data-url="/workingFlow/takeWorkingFlowConfigurationDelete"  class="button button-danger" style="margin-right:9px;"  onclick="deletewconfig(this)" ><span class=" icon-trash icon-white"></span>删除  </button>';
               }
               return temp;
             }}
           ];

var store = new Store({
    url : '${ctx}/workingFlow/queryTakeWorkingFlowConfiguration',
    autoLoad : false,
    pageSize:15
  }),
  grid = new Grid.Grid({
    render:'#grid',
    autoRender:true,
    loadMask: true,//加载数据时显示屏蔽层
    width:'100%',
    columns : columns,
    plugins : [Grid.Plugins.CheckSelection],
    store: store,
    emptyDataTpl:'<div class="centered"><img alt="Crying" src="${statics }/img/dataisnull.png"/><font style="color:red;font-size:20px;"><h2>没有找到匹配的数据!</h2></font></div>',
    // 底部工具栏
    tbar:{
     items:permissionValidate()
    },
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