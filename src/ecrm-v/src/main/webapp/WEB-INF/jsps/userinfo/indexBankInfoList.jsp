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
              <label class="control-label">${isEnglish?'Bank Name':'银行名称：'}</label>
                <select name="bankcode">
                    <option value="">请选择</option>
                </select>
            </div>
            <div class="control-group span7">
              <label class="control-label">${isEnglish?'Account':'会员账号：'}</label>
                <input name="loginaccount" type="text" data-tip='{"text":"会员账号"}' class="control-text" placeholder="会员账号"/>
            </div>
            <div class="control-group span7">
              <label class="control-label">${isEnglish?'Card Num':'银行账号：'}</label>
                <input name="paymentaccount" type="text" data-tip='{"text":"银行账号"}' class="control-text" placeholder="银行账号"/>
            </div>
            <div class="control-group span7">
              <label class="control-label">${isEnglish?'Bank Account':'开户名：'}</label>
                <input name="accountname" type="text" data-tip='{"text":"开户名"}' class="control-text" placeholder="开户名"/>
            </div>
            <div class="control-group span7">
              <label class="control-label">${isEnglish?'Bank Branch':'开户支行：'}</label>
                <input name="openningbank" type="text" data-tip='{"text":"开户支行"}' class="control-text" placeholder="开户支行"/>
            </div>
        </div>  
         <div style="position: absolute;right: 15px;top: 3px;">
           <button id="btnSearch" type="submit"  class="button button-primary"> <span class="icon-search icon-white"></span>${isEnglish?'Search':'搜索'}</button>
        </div>
        </div>
    </div> 
</form>
<ul style="border:5px; height:20px; line-height:20px; color:red; font-weight:bold;" class="breadcrumb">
	<li>
	<span style="font-size: 12px">
	若用户信息展示为*号，说明您没有查看用户游戏密码的权限，请向管理人员申请，[配置管理]-[隐私数据授权]
	</span>
	</li>
</ul>
<div class="search-grid-container well">
    <div id="grid"></div>
</div>
</div>

</body>
</html>
   <script type="text/javascript">
   loadAllBank();
  var update_bank = ${sessionScope.ERCM_USER_PSERSSION['MN00DP'].menustatus == 1 };
  var delete_bank = ${sessionScope.ERCM_USER_PSERSSION['MN00DQ'].menustatus == 1 };
  var lock_bank   = ${sessionScope.ERCM_USER_PSERSSION['MN00EB'].menustatus == 1 };
  var unlock_bank = ${sessionScope.ERCM_USER_PSERSSION['MN00EC'].menustatus == 1 };
  var add_bank = ${sessionScope.ERCM_USER_PSERSSION['MN00AM'].menustatus == 1 && sessionScope.PRIVATE_DATA_PSERSSION != null};
  
(function(){
   //权限验证
     function permissionValidate(){
      var array= new Array();
      
      if (add_bank) {
	      array.push({
            btnCls : 'button button-primary',
            text:'<span class="icon-file icon-white"></span>${isEnglish ? "Add Member Bank" : "添加银行卡"}',
            handler : function(){
              openNewWindow('create_userInfo','${ctx}/EmployeeInformation/userInfo/addUserInfo','添加银行卡');
            }
       	  });    	  
      }
      
      return array;
     }
        var Grid = BUI.Grid,
          Store = BUI.Data.Store,
          columns = [
			{ title: '${isEnglish?"Account":"登录账号"}',width: '5%',sortable: false,dataIndex: 'loginaccount',renderer:function(value,obj){
      			return "<span style='font-size:16px;color:#08B108;'>"+value+"</span>";
      		}},
            { title: '${isEnglish?"Bank Name":"银行名称"}',width: '7%',sortable: false,dataIndex: 'bankname'},
            { title: '${isEnglish?"Card Num":"银行账号"}',width: '15%',sortable: false,dataIndex: 'paymentaccount',renderer:function(value,obj){
      			return "<span style='font-size:16px;color:#0388FB;'>"+value+"</span>";
      		}},
            { title: '${isEnglish?"Bank Account":"开户名"}',width: '8%',sortable: false,dataIndex: 'accountname'},
            { title: '${isEnglish?"Bank Branch":"开户支行"}',width: '8%',sortable: false,dataIndex: 'openningbank'},
            { title: '${isEnglish?"Phone No":"电话"}',width: '7%',sortable: false,dataIndex: 'phonenumber'},
            { title: 'QQ',width: '8%',sortable: false,dataIndex: 'qq'},
            { title: '${isEnglish? "Email":"邮箱"}',width: '10%',sortable: false,dataIndex: 'email'},
            { title: 'Skype',width: '8%',sortable: false,dataIndex: 'skype'},
            { title: '${isEnglish? "Remarks":"备注信息"}',width: '10%',sortable: false,dataIndex: 'infomationcomment'},
            { title: '${isEnglish? "Status":"状态"}',width: '5%',sortable: false, renderer:function(value,obj){
            	return "2"==obj.status?"<span class='badge badge-info'>${isEnglish?'Unlocked':'未锁定'}</span>":"<span class='badge'>${isEnglish?'Locked':'已锁定'}</span>";
            }},
            { title : '${isEnglish?"Operation":"操作"}',width: '20%',sortable: false,renderer : function(value,obj){
              var temp = '';
           	  if (update_bank)
	              temp +='<button type="button" class="button  button-success" style="margin-right:9px;" onclick=bankInfoEditorByAdmin("'+obj.informationcode+'")><span class="icon-edit icon-white"></span>${isEnglish?"Edit":"编辑"}</button>';
			  if(lock_bank && obj.status == '2')
	              temp +='<button type="button" class="button  button-warning" style="margin-right:9px;" onclick=changeBankStatus("'+obj.informationcode+'","1")><span class="icon-lock icon-white"></span>${isEnglish?"Lock":"锁定"}</button>';
	          if(unlock_bank &&obj.status == '1')
	           	  temp +='<button type="button" class="button  button-warning" style="margin-right:9px;" onclick=changeBankStatus("'+obj.informationcode+'","2")><span class="icon-repeat icon-white"></span>${isEnglish?"Unlock":"解锁"}</button>';
           	  if (delete_bank)
	              temp += '<button type="button" class="button button-danger" style="margin-right:9px;" onclick="deleteRow(this)" name="/EmployeeInformation/deleteBankInfo" alt="'+obj.informationcode+'"><span class="icon-remove icon-white"></span>${isEnglish?"Delete":"删除"}</button>';
              return temp;
            }}
          ];

        var store = new Store({
            url : '${ctx}/EmployeeInformation/findBankInfoList',
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
            emptyDataTpl:'<div class="centered"><img alt="Crying" src="${statics }/img/dataisnull.png"/><font style="color:red;font-size:20px;"><h2>${isEnglish?"No Data !":"没有找到匹配的数据!"}</h2></font></div>',
            tbar:{ items:permissionValidate() },
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
	
	function changeBankStatus(informationcode, status) {
		$.ajax({
			url  : "${ctx}/EmployeeInformation/updateStatus",
			data : {'informationcode' : informationcode, 'status' : status},
			dataType : 'json',
			success : function(data){
				if(data.status ==1) {
					$("#searchForm").submit();
				} else {
					BUI.Message.Alert(data.message,'error');
				}
			},
			error : function(){
				BUI.Message.Alert('系统错误，请联系管理员','error');
			}
		});
	}
	
	function bankInfoEditorByAdmin(informationcode) {
		openNewWindow('bankInfo_update_page', getRootPath()+ "/EmployeeInformation/updateBankInfo?from=admin&informationcode="+ informationcode, '${isEnglish?"Edit Bank Info":"编辑银行卡信息"}');
	}
</script>