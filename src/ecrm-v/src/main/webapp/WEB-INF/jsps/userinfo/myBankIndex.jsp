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
          <!-- <div class="control-group span7">
              <label class="control-label">登录账号：</label>
                <input name="loginaccount" type="text" data-tip='{"text":"登录账号"}' class="control-text"   placeholder="登录账号"/>
            </div> -->
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
            <div class="control-group span7">
              <label class="control-label">开户名：</label>
                <input name="accountname" type="text" data-tip='{"text":"开户名"}' class="control-text" placeholder="开户名"/>
            </div>
            <div class="control-group span7">
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
  var add_bank = ${sessionScope.ERCM_USER_PSERSSION['MN00AM'].menustatus == 1 };
  var update_bank = ${sessionScope.ERCM_USER_PSERSSION['MN00AN'].menustatus == 1 };
  var delete_bank = ${sessionScope.ERCM_USER_PSERSSION['MN00AP'].menustatus == 1 };
  //var employeetype =  '${sessionScope.ERCM_USER_SESSION.employeetypecode}'; 
  
(function(){
   //权限验证
     function permissionValidate(){
      var array= new Array();
      if (add_bank) {
	      array.push({
            btnCls : 'button button-primary',
            text:'<span class="icon-file icon-white"></span>添加银行卡',
            handler : function(){
              openNewWindow('create_userInfo','${ctx}/EmployeeInformation/userInfo/userInfoAdd','添加银行卡');
            }
       	  });    	  
      }
      return array;
     }
        var Grid = BUI.Grid,
          Store = BUI.Data.Store,
          columns = [
			{ title: '登录账号',width: '8%',sortable: false,dataIndex: 'loginaccount',renderer:function(value,obj){
      			return "<span style='font-size:16px;color:#08B108;'>"+value+"</span>";
      		}},
            { title: '银行名称',width: '8%',sortable: false,dataIndex: 'bankname'},
            { title: '银行账号',width: '20%',sortable: false,dataIndex: 'paymentaccount',renderer:function(value,obj){
      			return "<span style='font-size:16px;color:#0388FB;'>"+value+"</span>";
      		}},
            { title: '开户名',width: '6%',sortable: false,dataIndex: 'accountname'},
            { title: '开户支行',width: '10%',sortable: false,dataIndex: 'openningbank'},
            { title: '电话',width: '8%',sortable: false,dataIndex: 'phonenumber'},
            { title: 'QQ',width: '8%',sortable: false,dataIndex: 'qq'},
            { title: '邮箱',width: '10%',sortable: false,dataIndex: 'email'},
            { title: 'skype',width: '8%',sortable: false,dataIndex: 'skype'},
            { title: '备注信息',width: '10%',sortable: false,dataIndex: 'infomationcomment'},
            { title: '状态',width: '6%',sortable: false, renderer:function(value,obj){
            	return "2"==obj.status?"<span class='badge badge-info'>未锁定</span>":"<span class='badge'>已锁定</span>";
            }},
            { title : '操作',width: 165,sortable: false,renderer : function(value,obj){
              var temp = '';
              if("2"==obj.status){
            	  if (update_bank){
		              temp +='<button type="button" class="button  button-success" style="margin-right:9px;" onclick=bankInfoEditor("'+obj.informationcode+'")><span class="icon-cog icon-white"></span>编辑  </button>';
            	  }
            	  if (delete_bank){
		              temp += '<button type="button" class="button button-danger" style="margin-right:9px;" onclick="deleteRow(this)" name="/EmployeeInformation/deleteBankInfo" alt="'+obj.informationcode+'"><span class="icon-remove icon-white"></span>删除 </button>';
            	  }
              }
              return temp;
            }}
          ];

        var store = new Store({
            url : '${ctx}/EmployeeInformation/findMyBankInfo',
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