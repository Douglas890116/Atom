<%@page import="com.maven.constant.Constant"%>
<%@page import="com.maven.entity.EnterpriseEmployee"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.maven.entity.EnterpriseEmployeeType.Type" %>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<c:set var="isEnglish" value="${sessionScope.LANGUAGE == 'en'}"/>
<body style="padding: 10px;">
    <div class="demo-content" >
<!-- 搜索页 ================================================== style=" margin:0 auto;  width:950px;"-->
<form id="searchForm" class="form-horizontal" style="outline: none;" method="post">
    <input name="end_hidden" type="hidden" />
    <input name="last_hidden" type="hidden" />
    <input type="hidden" name="parentemployeecode" />
    <div class="row well" style="margin-left: 0px;position: relative;margin-bottom: 0px;">
    <div style="position: relative;display: inline-block;">
      <div style="float: left;margin-right: 96px;">
      		<div class="control-group span5">
	              <label class="control-label">${isEnglish?'User Type':'员工类型'}：</label>
	                <select name="employeetypecode" id="employeetypeId" aria-pressed="false" aria-disabled="false"   class="bui-form-field-select bui-form-field" style="width: 117px;">
						<option value="">请选择</option>
	              	</select>
	         </div>
      		<div class="control-group span6">
              <label class="control-label">${isEnglish?'Brand':'品牌名称'}：</label>
                <input name="brandname"  type="text" data-tip='{"text":"品牌名称"}'  class="control-text" placeholder="品牌名称" />
            </div>
          <div class="control-group span6">
              <label class="control-label">${isEnglish?'Account':'登录账户'}：</label>
                <input name="loginaccount"  type="text"  data-tip='{"text":"登录账户"}'  class="control-text" placeholder="登录账户" />
            </div>
             <div class="control-group span6">
              <label class="control-label">${isEnglish?'Parent Account':'上级账户'}：</label>
                <input name="parentemployeeaccount"  type="text" data-tip='{"text":"开户支行"}' class="control-text span3" placeholder="开户支行"/>
            </div>
           <div class="control-group span7 toggle-display">
              <label class="control-label">${isEnglish?'Balance':'账户余额'}：</label>
                <input name="balance_begin"  type="text" data-tip='{"text":"余额下限"}'  class="control-text span2" placeholder="余额下限"/>
                -
                <input name="balance_end"  type="text" data-tip='{"text":"余额上限"}'  class="control-text span2" placeholder="余额上限"/>
            </div>
            <div class="control-group span8 toggle-display">
              <label class="control-label">${isEnglish?'User Dividends':'用户分红'}：</label>
                <input name="dividend_begin"  type="text" data-tip='{"text":"分红下限"}' class="control-text span2" placeholder="分红下限"/>
                -
                <input name="dividend_end"  type="text" data-tip='{"text":"分红上限"}' class="control-text span2" placeholder="分红上限"/>
            </div>
            <div class="control-group span8 toggle-display">
              <label class="control-label">${isEnglish?'User Commission':'用户占成'}：</label>
                <input name="share_begin"  type="text" data-tip='{"text":"占成下限"}'  class="control-text span2" placeholder="占成下限"/>
                -
                <input name="share_end"  type="text" data-tip='{"text":"占成上限"}'  class="control-text span2" placeholder="占成上限"/>
            </div>
            <div class="control-group span6 toggle-display">
	              <label class="control-label">${isEnglish?'User Status':'员工状态'}：</label>
	                <select name="employeestatus" aria-pressed="false" aria-disabled="false"   class="bui-form-field-select bui-form-field" style="width: 117px;">
						<option value="">${isEnglish?'=== All ===':'请选择'}</option>
	                    <option value="1">${isEnglish?'Enabled':'启用'}</option>
	                    <!-- <option value="2">${isEnglish?'Game Locked':'锁定游戏'}</option> -->
	                    <option value="3">${isEnglish?'Disabled':'禁用'}</option>
	              	</select>
	       </div>
            <div class="control-group span15 toggle-display">
              <label class="control-label">${isEnglish?'Last Login Time':'最后登录时间'}：</label>
              <div class="bui-form-group" data-rules="{dateRange : true}">
                <input name="lastStartDate"  type="text"  class="calendar calendar-time" placeholder="起始时间" />
                <span>&nbsp;-&nbsp;</span>
               	<input  name="lastEndDate" type="text" class="calendar calendar-time" placeholder="结束时间"/>
              </div>
              <div style="margin-right: auto;margin: -30px 460px;">
                   <select onchange="changeFormatDate(this,'lastStartDate','lastEndDate')" id="selectDateId" style="width:85px;">
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
             <div class="control-group span15 toggle-display">
                <label class="control-label">${isEnglish?'Registration Time':'注册时间'}：</label>
                <div class="bui-form-group" data-rules="{dateRange : true}">
                  <input name="startDate"  type="text"   class="calendar calendar-time" placeholder="起始时间"/>
                  <span>&nbsp;-&nbsp;</span>
                  <input  name="endDate" type="text"    class="calendar calendar-time" placeholder="结束时间"/>
                </div>
                <div style="margin-right: auto;margin: -30px 470px;">
                    <select onchange="changeFormatDate(this,'startDate','endDate')" style="width:85px;">
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
          	 <div style="position: absolute;right: 10px;top: 3px;">
             	 <button id="btnSearch" type="submit"  class="button button-primary"> <span class="icon-search icon-white"></span>${isEnglish?' Search ':' 搜 索 '}</button>
                 <input type="reset" style="display:none;" id="resetId" />
            </div>
        </div>
    </div>
</form>
<ul style="border: none;height: 20px;line-height: 20px;color:#428bca;font-weight: bold;visibility: hidden;" id="visiteScent" class="breadcrumb">
        <li>
			访问轨迹：
        </li>
        <li>
        	<span style="padding: 0 5px;border: 1px solid #E8E9EE;cursor:pointer;" onclick = "clearScent(this)">清除</span>
        </li>
    </ul>
<div class="search-grid-container well">
  <div id="grid"></div>
</div>
</div>

</body>


<div id="money_in_out" style="display: none;">
      <form id="money_in_out_form" class="form-horizontal">
         	<div class="control-group">
               <label class="control-label" style="width:100px;">${isEnglish?'Account':'用户账号'}：</label>
               <input class="input-normal control-text" type="text" name="loginaccout" disabled="disabled" readonly="readonly">
             </div>
         	<div class="control-group">
               <label class="control-label" style="width:100px;">${isEnglish?'Transfer':'转入转出'}：</label>
               <select name="opreate">
               	<option value="IN">${isEnglish?'Transfer In':'转入'}</option>
               	<option value="OUT">${isEnglish?'Transfer Out':'转出'}</option>
               </select>
             </div>
             
             <div class="control-group">
               <label class="control-label" style="width:100px;">${isEnglish?'Amount':'转移金额'}：</label>
               <input class="input-normal control-text" name="money" type="text"  data-rules="{required:true,number:true}" >
             </div>
      </form>
</div>
</html>
<script type="text/javascript">
//加载员工类型数据
loadEmployeeType();

var batch_delete =         ${sessionScope.ERCM_USER_PSERSSION['MN003C'].menustatus==1};
var batch_disable =        ${sessionScope.ERCM_USER_PSERSSION['MN00AB'].menustatus==1};
var batch_activate =       ${sessionScope.ERCM_USER_PSERSSION['MN00AC'].menustatus==1};
var reset_loginpassword =  ${sessionScope.ERCM_USER_PSERSSION['MN003D'].menustatus==1};
var reset_funpassword =    ${sessionScope.ERCM_USER_PSERSSION['MN003E'].menustatus==1};
var permission_setting =   ${sessionScope.ERCM_USER_PSERSSION['MN003F'].menustatus==1};
var setting_config =       ${sessionScope.ERCM_USER_PSERSSION['MN003G'].menustatus==1};
var shift_money =       ${sessionScope.ERCM_USER_PSERSSION['MN00BD'].menustatus==1};
var add =       ${sessionScope.ERCM_USER_PSERSSION['MN0068'].menustatus==1};
var employeetype =  '${sessionScope.ERCM_USER_SESSION.employeetypecode}';
var employeecode =  '${sessionScope.ERCM_USER_SESSION.employeecode}';

(function(){

//权限验证
function permissionValidate(){
	var array= new Array();
	if(add){
  		array.push({
          btnCls : 'button button-primary',
          text:'<span class="icon-file icon-white"></span> ${isEnglish?"Create Agent":"创建代理"}',
          handler : function(){
  	        	//公用的删除js,需要传入不同url路径
  	        	openNewWindow("CreateNewAgent","${ctx}/employeeAgent/userJsp/agentEmployeeAdd","${isEnglish?'Create Agent':'创建代理'}");
          		//BUI.Message.Alert("对不起,您没有批量删除权限!","warning");
       }});
	}
	//只有员工类型与企业类型的用户具有权限
	if(batch_delete){
		array.push({
	        btnCls : 'button button-danger bin',
	        text:'<span class=" icon-trash icon-white"></span> ${isEnglish?"Batch Delete":"批量删除"}',
	        handler : function(){
	        	deleteMutilterm(grid,"${ctx}/EEmployee/deleteSelectEmployee","employeecode");
	     }});
	}
	if(batch_disable){
		array.push({
	        btnCls : 'button button-warning bin',
	        text:'<span class=" icon-remove icon-white"></span> ${isEnglish?"Batch Disable":"批量禁用"}',
	        handler : function(){
	        	disableMutilterm(grid,store,"${ctx}/EEmployee/disableSelectEmployee","employeecode");
	     }});
	}
	if(batch_activate){
		array.push({
	        btnCls : 'button button-success bin',
	        text:'<span class=" icon-ok icon-white"></span> ${isEnglish?"Batch Enable":"批量启用"}',
	        handler : function(){
	        	activateMutilterm(grid,store,"${ctx}/EEmployee/activateSelectEmployee","employeecode");
	     }});
	}
	/* array.push({
        btnCls : 'button button-success',
        text:'<i class="icon-barcode icon-white"></i>批量结算配置',
        handler : function(){
	        batchSetDividendShareBonus();
     }}); */
	return array;
}

var Grid = BUI.Grid,
  Store = BUI.Data.Store,
  columns = [
	{title:'${isEnglish?"Brand":"品牌"}',width:'5%',dataIndex:'brandname',sortable:false},
	{ title: '${isEnglish?"Account":"账号"} ',   width: '8%', elCls:'center', sortable: false, renderer:function(value,obj){
    	return  "<a href=javascript:showDownData('"+obj.employeecode.split('_')[1]+"','"+obj.loginaccount+"') title='${isEnglish?"View subordinate agents":"查看下级代理"}' style='font-size:16px;'>"+obj.loginaccount+"</a>";
    	
    }},
    { title: '${isEnglish?"Nick Name":"昵称"}',   width: '7%',  sortable: false, elCls:'center',dataIndex: 'displayalias'},
    { title: '${isEnglish?"User Type":"类型"}',width: '7%',sortable: false,elCls:'center',dataIndex: 'employeetypename'},
    { title: '${isEnglish?"Dividends":"分红"}',   width: '6%', elCls:'center', sortable: false, dataIndex: 'dividend',renderer:function(value,obj){
    	return "<span style='color:#5cb85c;font-size: 16px;'>"+(value/1*100).toFixed(2)+"%</span>";
    }},
    { title: '${isEnglish?"Commission":"占成"}',   width: '7%', elCls:'center', sortable: false, dataIndex: 'share',renderer:function(value,obj){
    	return "<span style='color:#5cb85c;font-size: 16px;'>"+(value/1*100).toFixed(2)+"%</span>";
    }},
    { title: '${isEnglish?"Num of Agents":"代理数"}',   width: '10%', elCls:'center', sortable: false, dataIndex: 'agentCount',renderer:function(value,obj){
    	return "<span style='color:red;font-size: 16px;'>"+value+"</span>";
    }},
    { title: '${isEnglish?"Num of Members":"会员数"}',   width: '10%', elCls:'center', sortable: false, dataIndex: 'memberCount',renderer:function(value,obj){
    	return "<span style='color:red;font-size: 16px;'>"+value+"</span>";
    }},
    { title: '${isEnglish?"Balance":"账户余额"}',   width: '6%',elCls:'center',  sortable: false, dataIndex: 'balance',renderer:function(value,obj){
    	return "<span style='color:red;font-size:16px;'>"+($.isNumeric(value)?value.toFixed(2):value)+"</span>";
    }},
    { title: '${isEnglish?"Parent Account":"上级账号"}',width: '9%',elCls:'center',  sortable: false,  dataIndex: 'parentemployeeaccount'},
    { title: '${isEnglish?"Status":"状态"}',   width: '6%',elCls:'center',dataIndex: 'employeestatus', sortable: false,renderer:function(value,obj){
    	switch(value){
    		case 1:
    			return "${isEnglish?'Enabled':'启用'}";
    		case 2:
    			return "${isEnglish?'Locked':'锁定'}";
    		case 3:
    			return "<span style='color:red;'>${isEnglish?'Disabled':'禁用'}</span>";
		}
	}},
	{ title: '${isEnglish?"Last Login Time":"最后登录时间"}',width: '11%',elCls:'center',sortable: true, dataIndex: 'lastlogintime',renderer:BUI.Grid.Format.datetimeRenderer},
    { title: '${isEnglish?"Registration Time":"注册日期"}',width: '11%',elCls:'center',sortable: false,dataIndex: 'logindatetime',renderer:BUI.Grid.Format.datetimeRenderer},
    { title : '${isEnglish?"Operation":"操作"}',width: 400,sortable: false, renderer : function(value,obj){
    	var temp = '';
    	if(shift_money&&obj.parentemployeecode==employeecode){
	    	temp += "<button class='button button-info botton-margin'  onclick='show_in_out_panel(\""+obj.employeecode+"\",\""+obj.loginaccount+"\")'><span class='icon-random icon-white'></span> ${isEnglish?'Transfer':'转分'}</button>";
    	}
    	if(permission_setting&&obj.parentemployeecode=='<%=((EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION)).getEmployeecode() %>'){
    		temp += '<button class="button button-primary botton-margin" onclick="agentpermission(\'employeecode='+obj.employeecode+'&employeename='+obj.loginaccount+'\')"><span class="icon-globe icon-white"></span> ${isEnglish?"Authority":"权限"}</button>';
    	}
    	if(setting_config){
	    	temp += "<button class='button button-success botton-margin'  onclick=\"openNewWindow('agentsettlement','${ctx}/GCBonus/agentSetting?employeecode="+obj.employeecode+"','结算配置')\"  ><span class='icon-cog icon-white'/>${isEnglish?'Settlement':'结算'}</button>";
    	}
    	temp+="<button class='button button-warning' onclick=openAllDownMember('"+obj.employeecode.split('_')[1]+"','"+obj.loginaccount+"')><i class='icon-user icon-white'></i>${isEnglish?'Overview':'查看'}</button>";
    	
    	
    	return temp;
    }}
  ];
  
var store = new Store({
    url : '${ctx}/employeeAgent/findEmployee',
    autoLoad:false, 
    pageSize:15,
    sortInfo : {
        field : 'lastlogintime',
        direction : 'desc'
      },
  }),
  grid = new Grid.Grid({
    render:'#grid',
    autoRender:true,
    loadMask: true,//加载数据时显示屏蔽层
    width:'100%',
    columns : columns,
    store: store,
    plugins : [ Grid.Plugins.CheckSelection ],
    emptyDataTpl:'<div class="centered"><img alt="Crying" src="${statics }/img/dataisnull.png"/><font style="color:red;font-size:20px;"><h2>${isEnglish?"No Data !":"没有找到匹配的数据!"}</h2></font></div>',
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
   
	if (getParamFormUrl("search") != ''){
		var la = $("input[name='loginaccount']");
	  	la.val(getParamFormUrl("search"));
	}
	$("#searchForm").submit(function(){
  	  var obj = BUI.FormHelper.serializeToObject(this);
        obj.start = 0;
        store.load(obj);
  	  return false;
    }).submit();
    
})()

function show_in_out_panel(employeecode,loginaccout){
	$("#money_in_out_form input[name='loginaccout']").val(loginaccout);
	BUI.use('bui/overlay',function(Overlay){
        var dialog = new BUI.Overlay.Dialog({
          title:'${isEnglish?"Transfer In/Out":"资金-转入转出"}',
          width:360,
          height:210,
          zIndex:'100',
          childContainer : '.bui-stdmod-body',
	      closeAction : 'destroy',
          contentId:'money_in_out',
          success:function () {
        	  var panel = this;
        	  if(!$.isNumeric($("#money_in_out_form input[name='money']").val())){
        		  BUI.Message.Alert("${isEnglish?'The amount must be a number.':'输入金额必须为数字'}","error");
        		  return ;
        	  }
        	  $.ajax({
	        		url:getRootPath()+"/CreditAgent/shifintergral",
	        		type:"post",
	        		data:$("#money_in_out_form").serialize()+"&sign="+employeecode,
	        		dataType:"json",
	        		success:function(data){
	        			if(1 == data.status){
	        				BUI.Message.Alert(data.message,'success');
	        				panel.close();
	        				$("#btnSearch").trigger("click");
	        			}else {
	        				BUI.Message.Alert(data.message,'error');
	        			}
	        		}
	        	});
        	
          }
	 	})
        dialog.show();
    });	
}
</script>