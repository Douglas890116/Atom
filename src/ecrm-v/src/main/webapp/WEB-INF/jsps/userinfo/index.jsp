<%@page import="com.maven.entity.EnterpriseEmployeeType.Type"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body style="padding: 10px;">
    <div class="demo-content" >
<!-- 搜索页 ================================================== -->
<form id="searchForm" class="form-horizontal" style="outline: none;" method="post">
<div class="row well" style="margin-left: 0px;position: relative;">
    <div style="position: relative;display: inline-block;">
      <div style="float: left;margin-right: 126px;">
          <div class="control-group span7">
              <label class="control-label">会员账号：</label>
                <input name="loginaccount" type="text" data-tip='{"text":"登录账号"}' class="control-text"   placeholder="登录账号"/>
            </div>
            <div class="control-group span7">
              <label class="control-label">银行名称：</label>
                <select name="bankcode">
                    <option value="">请选择</option>
                </select>
            </div>
            <div class="control-group span7">
              <label class="control-label">银行账号：</label>
                <input name="paymentaccount" type="text" data-tip='{"text":"银行账号"}' class="control-text" placeholder="银行账号"/>
            </div>
            <div class="control-group span7 toggle-display">
              <label class="control-label">开户名：</label>
                <input name="accountname" type="text" data-tip='{"text":"开户名"}' class="control-text" placeholder="开户名"/>
            </div>
            <div class="control-group span7 toggle-display">
              <label class="control-label">开户支行：</label>
                <input name="openningbank" type="text" data-tip='{"text":"开户支行"}' class="control-text" placeholder="开户支行"/>
            </div>
        </div>  
         <div style="position: absolute;right: 15px;top: 3px;">
           <button id="btnSearch" type="submit"  class="button button-primary"> <span class="icon-search icon-white"></span>搜 索 </button>
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
   loadAllBank();
  var add_bank  =    ${sessionScope.ERCM_USER_PSERSSION['MN0012'].menustatus == 1 };
  var update_bank  = ${sessionScope.ERCM_USER_PSERSSION['MN0014'].menustatus == 1 };
  var unlock_bank  = ${sessionScope.ERCM_USER_PSERSSION['MN003I'].menustatus == 1 };
  var lock_bank  = ${sessionScope.ERCM_USER_PSERSSION['MN007U'].menustatus == 1 };
  var batch_delete = ${sessionScope.ERCM_USER_PSERSSION['MN006F'].menustatus == 1 };
  var one_delete = ${sessionScope.ERCM_USER_PSERSSION['MN006G'].menustatus == 1 };
  var employeetype =  '${sessionScope.ERCM_USER_SESSION.employeetypecode}'; 
  
(function(){
   //权限验证
     function permissionValidate(){
      var array= new Array();
      /* if(add_bank){
        array.push({
              btnCls : 'button button-primary',
              text:'<span class="icon-file icon-white"></span>添加银行卡',
              handler : function(){
                openNewWindow('create_userInfo','${ctx}/EmployeeInformation/userInfo/userInfoAdd','添加银行卡');
              }
          });
      } */
      if(batch_delete){
    	  array.push({
  	        btnCls : 'button button-danger bin',
  	        text:'<span class=" icon-trash icon-white"></span>批量删除',
  	        handler : function(){
  	        	deleteMutilterm(grid,"${ctx}/EmployeeInformation/deleteSelectBankInfo","informationcode");
  	     }});
      }
      return array;
     }
        var Grid = BUI.Grid,
          Store = BUI.Data.Store,
          columns = [
			{ title: '会员账号',width: '10%',sortable: false,dataIndex: 'loginaccount',renderer:function(value,obj){
      			return "<span style='font-size:16px;color:#08B108;'>"+value+"</span>";
      		}},
            { title: '银行名称',width: '10%',sortable: false,dataIndex: 'bankname'},
            { title: '银行账号',width: '20%',sortable: false,dataIndex: 'paymentaccount',renderer:function(value,obj){
      			return "<span style='font-size:16px;color:#0388FB;'>"+value+"</span>";
      		}},
            { title: '开户名',width: '10%',sortable: false,dataIndex: 'accountname'},
            { title: '开户支行',width: '10%',sortable: false,dataIndex: 'openningbank'},
            { title: '电话',width: '10%',sortable: false,dataIndex: 'phonenumber'},
            { title: 'QQ',width: '10%',sortable: false,dataIndex: 'qq'},
            { title: 'skype',width: '10%',sortable: false,dataIndex: 'skype'},
            { title: 'Email',width: '10%',sortable: false,dataIndex: 'email'},
            { title: '备注信息',width: '10%',sortable: false,dataIndex: 'infomationcomment'},
            { title : '操作',width: 80,sortable: false,renderer : function(value,obj){
              var temp = '';
              if(unlock_bank && "1"==obj.status && (employeetype == '<%=Type.企业号.value%>' || employeetype == '<%=Type.员工.value%>')){
                  temp += '<button type="button" class="button button-info" onclick=unlockingBank("'+obj.informationcode+'") style="margin-right:9px;background-color:#79BFD4;border-color:#46b8da"><span class="icon-unlock icon-white"></span>解锁</button>';
              }
              if(lock_bank && "2"==obj.status && (employeetype == '<%=Type.企业号.value%>' || employeetype == '<%=Type.员工.value%>')){
                  temp += '<button type="button" class="button button-info" onclick=lockingBank("'+obj.informationcode+'") style="margin-right:9px;background-color:#B8B9C1;border-color:#A0B1A0"><span class="icon-lock icon-white"></span>锁定</button>';
              }
              if(update_bank && "2"==obj.status && employeetype == '<%=Type.代理.value%>'){
                    temp +='<button type="button" class="button  button-success" style="margin-right:9px;" onclick=bankInfoEditor("'+obj.informationcode+'")><span class="icon-cog icon-white"></span>编辑  </button>';
              }
              if(one_delete && employeetype == '<%=Type.代理.value%>'){
            	  temp += '<button type="button" class="button button-danger" style="margin-right:9px;" onclick="deleteRow(this)" name="/EmployeeInformation/deleteBankInfo" alt="'+obj.informationcode+'"><span class="icon-remove icon-white"></span>删除 </button>';
              }
                return temp;
            }}
          ];

        var store = new Store({
            url : '${ctx}/EmployeeInformation/findBankInfo',
            autoLoad : false,
            pageSize:15
          }),grid = new Grid.Grid({
            render:'#grid',
            autoRender:true,
            loadMask: true,//加载数据时显示屏蔽层
            columns : columns,
            width:'100%',
            store: store,
            plugins : [ Grid.Plugins.CheckSelection ],
            emptyDataTpl:'<div class="centered"><img alt="Crying" src="${statics }/img/dataisnull.png"/><font style="color:red;font-size:20px;"><h2>没有找到匹配的数据!</h2></font></div>',
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
	})()
    </script>