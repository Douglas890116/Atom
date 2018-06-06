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
    <input name="createDate" type="hidden" value="${createDate}"/>
    <div class="row well" style="margin-left: 0px;position: relative;">
    <div style="position: relative;display: inline-block;">
      <div style="float: left;margin-right: 96px;">

      		<div class="control-group span5">
                  <c:choose>
                    <c:when test="${isEnglish}"><label class="control-label">User Type：</label></c:when>
                    <c:otherwise><label class="control-label">员工类型：</label></c:otherwise>
                  </c:choose>
	                <select name="employeetypecode" id="employeetypeId" aria-pressed="false" aria-disabled="false"   class="bui-form-field-select bui-form-field" style="width: 117px;">
						<option value="">请选择</option>
	              	</select>
	         </div>
             <div class="control-group span6">
                  <c:choose>
                    <c:when test="${isEnglish}"><label class="control-label">Account：</label></c:when>
                    <c:otherwise><label class="control-label">登录账户：</label></c:otherwise>
                  </c:choose>
                <input name="loginaccount"  type="text"  data-tip='{"text":"登录账户"}'  class="control-text" placeholder="登录账户"/>
            </div>
      		<div class="control-group span6">
                  <c:choose>
                    <c:when test="${isEnglish}"><label class="control-label">Brand：</label></c:when>
                    <c:otherwise><label class="control-label">品牌名称：</label></c:otherwise>
                  </c:choose>
                <input name="brandname"  type="text" data-tip='{"text":"品牌名称"}'  class="control-text" placeholder="品牌名称"/>
            </div>
             <div class="control-group span7">
                <c:choose>
                  <c:when test="${isEnglish}"><label class="control-label">Parent Account：</label></c:when>
                  <c:otherwise><label class="control-label">上级账户：</label></c:otherwise>
                </c:choose>
                <input name="parentemployeeaccount"  type="text" data-tip='{"text":"上级账户"}' class="control-text span3" placeholder="上级账户"/>
            </div>
           <div class="control-group span8 toggle-display">
                <c:choose>
                  <c:when test="${isEnglish}"><label class="control-label">Member Balance：</label></c:when>
                  <c:otherwise><label class="control-label">账户余额：</label></c:otherwise>
                </c:choose>
                <input name="balance_begin"  type="text" data-tip='{"text":"余额下限"}'  class="control-text span2" placeholder="余额下限"/>
                -
                <input name="balance_end"  type="text" data-tip='{"text":"余额上限"}'  class="control-text span2" placeholder="余额上限"/>
            </div>
            <div class="control-group span9 toggle-display">
                <c:choose>
                  <c:when test="${isEnglish}"><label class="control-label">User Dividends：</label></c:when>
                  <c:otherwise><label class="control-label">用户分红：</label></c:otherwise>
                </c:choose>
                <input name="dividend_begin"  type="text" data-tip='{"text":"分红下限"}' class="control-text span2" placeholder="分红下限"/>
                -
                <input name="dividend_end"  type="text" data-tip='{"text":"分红上限"}' class="control-text span2" placeholder="分红上限"/>
            </div>
            <div class="control-group span9 toggle-display">
                <c:choose>
                  <c:when test="${isEnglish}"><label class="control-label">User Commission：</label></c:when>
                  <c:otherwise><label class="control-label">用户占成：</label></c:otherwise>
                </c:choose>
                <input name="share_begin"  type="text" data-tip='{"text":"占成下限"}'  class="control-text span2" placeholder="占成下限"/>
                -
                <input name="share_end"  type="text" data-tip='{"text":"占成上限"}'  class="control-text span2" placeholder="占成上限"/>
            </div>
            <div class="control-group span6 toggle-display">
                <c:choose>
                  <c:when test="${isEnglish}"><label class="control-label">User Status：</label></c:when>
                  <c:otherwise><label class="control-label">员工状态：</label></c:otherwise>
                </c:choose>
	                <select name="employeestatus" aria-pressed="false" aria-disabled="false"   class="bui-form-field-select bui-form-field" style="width: 117px;">
						<option value="">${isEnglish?'=== All ===':'请选择'}</option>
	                    <option value="1">${isEnglish?'Enabled':'启用'}</option>
	                    <!-- <option value="2">${isEnglish?'Game Locked':'锁定游戏'}</option> -->
	                    <option value="3">${isEnglish?'Disabled':'禁用'}</option>
	              	</select>
	         </div>
             <div class="control-group span14 toggle-display">
               <c:choose>
                 <c:when test="${isEnglish}"><label class="control-label">最后登录时间：</label></c:when>
                 <c:otherwise><label class="control-label">最后登录时间：</label></c:otherwise>
               </c:choose>
              <div class="bui-form-group" data-rules="{dateRange : true}">
                <input name="lastStartDate"  type="text"  class="calendar calendar-time" placeholder="起始时间" />
                <span>&nbsp;-&nbsp;</span>
               	<input  name="lastEndDate" type="text" class="calendar calendar-time" placeholder="结束时间"/>
              </div>
              <div style="margin-right: auto;margin: -30px 450px;">
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
             <div class="control-group span14 toggle-display">
               <c:choose>
                 <c:when test="${isEnglish}"><label class="control-label">Registration Time：</label></c:when>
                 <c:otherwise><label class="control-label">注册时间：</label></c:otherwise>
               </c:choose>
                <div class="bui-form-group" data-rules="{dateRange : true}">
                  <input name="startDate"  type="text"   class="calendar calendar-time" placeholder="起始时间"/>
                  <span>&nbsp;-&nbsp;</span>
                  <input  name="endDate" type="text"    class="calendar calendar-time" placeholder="结束时间"/>
                </div>
                <div style="margin-right: auto;margin: -30px 470px;">
                   <select onchange="changeFormatDate(this,'startDate','endDate')"  id="selectDateId"  style="width:85px;">
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
               <c:choose>
                 <c:when test="${isEnglish}"><button id="btnSearch" type="submit"  class="button button-primary"><span class="icon-search icon-white"></span> Search </button></c:when>
                 <c:otherwise><button id="btnSearch" type="submit"  class="button button-primary"><span class="icon-search icon-white"></span> 搜 索 </button></c:otherwise>
               </c:choose>
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

<div id="money_in_out" style="display: none;">
      <form id="money_in_out_form" class="form-horizontal">
         	<div class="control-group">
               <c:choose>
                 <c:when test="${isEnglish}"><label class="control-label" style="width:100px;">Account：</label></c:when>
                 <c:otherwise><label class="control-label" style="width:100px;">用户账号：</label></c:otherwise>
               </c:choose>
               <input class="input-normal control-text" type="text" name="loginaccout" disabled="disabled" readonly="readonly">
             </div>
             <c:choose>
               <c:when test="${isEnglish}">
                  <div class="control-group">
                  <label class="control-label" style="width:100px;">Transfer：</label>
                  <select name="opreate">
                  	<option value="IN">Transfer In</option>
                  	<option value="OUT">Transfer Out</option>
                  </select>
                </div>
               </c:when>
               <c:otherwise>
                  <div class="control-group">
                  <label class="control-label" style="width:100px;">转入转出：</label>
                  <select name="opreate">
                  	<option value="IN">转入</option>
                  	<option value="OUT">转出</option>
                  </select>
                </div>
               </c:otherwise>
             </c:choose>

             
             <div class="control-group">
               <c:choose>
                 <c:when test="${isEnglish}"><label class="control-label" style="width:100px;">Amount：</label></c:when>
                 <c:otherwise><label class="control-label" style="width:100px;">转移金额：</label></c:otherwise>
               </c:choose>
               <input class="input-normal control-text" name="money" type="text"  data-rules="{required:true,number:true}" >
             </div>
      </form>
</div>

    <!-- 此节点内部的内容会在弹出框内显示,默认隐藏此节点-->
    <div id="content3" class="hidden" style="display: none;">
      <form id="userPwd1" class="form-horizontal">
        <div class="row">
          <div class="control-group span15">
          
          	<input class="input control-text" type="hidden" id="employeecode1" >
          	
          	<div class="controls" style="">
          		<p style="text-align:center">请输入新的密码：<br /><br /></p>
              	<p style="text-align:center"><input class="input control-text" type="text" id="newpassword1" data-rules="{required : true}"/><br/></p>
              	<p><font color="red">注：密码由【6~12位】的【小写字母】和【数字】组成</font></p><br/>
              	<p style="text-align:center">
              	<input type="button" class="button button-primary  botton-margin" onclick="getRandomPassword(this,1)" value="随机密码"></input>
              	<input type="button" class='button button-success botton-margin'  onclick="saveNewpassword(this,1)" value="确定修改"></input>
              	</p><p></p>
   			</div>
   			
          </div>
        </div>
      </form>
    </div>
    <!-- 此节点内部的内容会在弹出框内显示,默认隐藏此节点-->
    <div id="content4" class="hidden" style="display: none;">
      <form id="userPwd2" class="form-horizontal">
        <div class="row">
          <div class="control-group span15">
          
          	<input class="input control-text" type="hidden" id="employeecode2" >
          	
          	<div class="controls" style="">
          		<p style="text-align:center">请输入新的密码：<br /><br /></p>
              	<p style="text-align:center"><input class="input control-text" type="text" id="newpassword2" data-rules="{required : true}"/><br/></p>
              	<p><font color="red">注：密码由【6~12位】的【小写字母】和【数字】组成</font></p><br/>
              	<p style="text-align:center">
              	<input type="button" class="button button-primary  botton-margin" onclick="getRandomPassword(this,2)" value="随机密码"></input>
              	<input type="button" class='button button-success botton-margin'  onclick="saveNewpassword(this,2)" value="确定修改"></input>
              	</p><p></p>
   			</div>
   			
          </div>
        </div>
      </form>
    </div>
</body>
</html>
<script type="text/javascript">
var batch_delete        = ${sessionScope.ERCM_USER_PSERSSION['MN000Z'].menustatus==1};
var batch_disable       = ${sessionScope.ERCM_USER_PSERSSION['MN00A9'].menustatus==1};
var batch_activate      = ${sessionScope.ERCM_USER_PSERSSION['MN00AA'].menustatus==1};
var reset_loginpassword = ${sessionScope.ERCM_USER_PSERSSION['MN0011'].menustatus==1};
var reset_funpassword   = ${sessionScope.ERCM_USER_PSERSSION['MN0039'].menustatus==1};
var setting_config      = ${sessionScope.ERCM_USER_PSERSSION['MN003A'].menustatus==1};
var batch_add           = ${sessionScope.ERCM_USER_PSERSSION['MN0067'].menustatus==1};
var shift_money         = ${sessionScope.ERCM_USER_PSERSSION['MN00BD'].menustatus==1};

var user_game           = ${sessionScope.ERCM_USER_PSERSSION['MN00DS'].menustatus==1};
var all_view            = ${sessionScope.ERCM_USER_PSERSSION['MN00DT'].menustatus==1};
var employeetype        = '${sessionScope.ERCM_USER_SESSION.employeetypecode}';
var employeecode        = '${sessionScope.ERCM_USER_SESSION.employeecode}';
var is_English          = ${sessionScope.LANGUAGE=='en'}

function showUserData(employeecode,loginaccount) {
	openNewWindow('showshowuserdatadetail','${ctx}/EEmployee/allinfo?employeecode='+employeecode+'&loginaccount='+loginaccount,'会员详细信息');
}


(function(){
	//加载员工类型	
	loadEmployeeType();
	
	//权限验证
	function permissionValidate(){
		var array= new Array();
		if(batch_add){
	    	array.push({
	            btnCls : 'button button-primary',
	            text:'<span class="icon-file icon-white"></span> ${isEnglish?"Create Member":"创建会员"}',
	            handler : function(){
   	        	//公用的删除js,需要传入不同url路径
   	        	openNewWindow("CreateNewUser","${ctx}/EEmployee/userJsp/userAdd","${isEnglish?'Create Member':'创建会员'}");
           		//BUI.Message.Alert("对不起,您没有批量删除权限!","warning");
	         }});
		}
		array.push({
	        btnCls : 'button button-inverse',
	        text:'<span class="icon-asterisk icon-white"></span>批量结算配置',
	        handler : function(){
		        batchSetBonus(grid);
	     }});
		// 只有员工类型与企业类型的用户具有权限
		if(batch_delete){
			array.push({
		        btnCls : 'button button-danger bin',
		        text:'<span class=" icon-trash icon-white"></span> ${isEnglish?"Btach Delete":"批量删除"}',
		        handler : function(){
	    	        //公用的删除js,需要传入不同url路径
	    	        deleteMutilterm(grid,"${ctx}/EEmployee/deleteSelectEmployee","employeecode");
		     }});
		}
		if(batch_disable){
			array.push({
		        btnCls : 'button button-warning bin',
		        text:'<span class=" icon-remove icon-white"></span> ${isEnglish?"Batch Disable":"批量禁用"}',
		        handler : function(){
	    	        //公用的删除js,需要传入不同url路径
	    	        disableMutilterm(grid,store,"${ctx}/EEmployee/disableSelectEmployee","employeecode");
		     }});
		}
		if(batch_activate){
			array.push({
		        btnCls : 'button button-success bin',
		        text:'<span class=" icon-ok icon-white"></span> ${isEnglish?"Batch Enable":"批量启用"}',
		        handler : function(){
	    	        //公用的删除js,需要传入不同url路径
	    	       	activateMutilterm(grid,store,"${ctx}/EEmployee/activateSelectEmployee","employeecode");
		     }});
		}
		
		return array;
	}
	
	var Grid = BUI.Grid,
	  Store = BUI.Data.Store,
	  columns = [
		{title:'${isEnglish?"Brand":"品牌"}',width:'6%',elCls:'center',dataIndex:'brandname',sortable:false},
		{title: '${isEnglish?"Account":"账号"}', width: '8%',elCls:'center',  sortable: false, renderer:function(value,obj){
	    	//return  "<span style='color: #428BCA;font-size:16px;' title='账号 ' >"+obj.loginaccount.split("_")[1]+"</span>";
	    	return "<a href=javascript:showUserData('"+obj.employeecode.split('_')[1]+"','"+obj.loginaccount.split("_")[1]+"') style='font-size:16px;' title='查看汇总信息'>"+obj.loginaccount.split("_")[1]+"</a>";
	    	
	    }},
	    {title:'${isEnglish?"Member Name":"姓名"}',width: '8%',elCls:'center',sortable: false,dataIndex:'displayalias'},
	    { title: '${isEnglish?"Balance":"账户余额"}',   width: '8%', elCls:'center', sortable: false, dataIndex: 'balance',renderer:function(value,obj){
	    	return "<span style='color:#5cb85c;font-size:16px;'>"+($.isNumeric(value)?value.toFixed(2):value)+"</span>";
	    }},
	    {title: '${isEnglish?"Parent Account":"上级账号"}',width: '8%', elCls:'center', sortable: false,  dataIndex: 'parentemployeeaccount'},
	    /* 
	    { title: '分红',   width: '7%', elCls:'center', sortable: false, dataIndex: 'dividend',renderer:function(value,obj){
	    	return "<span style='color:#5cb85c;font-size: 16px;'>"+(value/1*100)+"%</span>";
	    }},
	    { title: '占成',   width: '7%', elCls:'center', sortable: false, dataIndex: 'share',renderer:function(value,obj){
	    	return "<span style='color:#5cb85c;font-size: 16px;'>"+(value/1*100)+"%</span>";
	    }},
	     */
	     
	     {title: '${isEnglish?"Phone Num":"电话"}',width: '8%', elCls:'center', sortable: false,  dataIndex: 'phoneno'},
	     {title: '${isEnglish?"QQ Num":"QQ号码"}',width: '8%', elCls:'center', sortable: false,  dataIndex: 'qq'},
	     {title: '${isEnglish?"Email":"邮箱地址"}',width: '8%', elCls:'center', sortable: false,  dataIndex: 'email'},
	    { title: '${isEnglish?"Status":"状态"}',   width: '6%',elCls:'center',dataIndex: 'employeestatus', sortable: false,renderer:function(value,obj){
	    	switch(value){
			case 1:
				return "${isEnglish?'Enabled':'启用'}";
			case 2:
				return "${isEnglish?'Game Locked':'锁定游戏'}";
			case 3:
				return "<span style='color:red;'>${isEnglish?'Disabled':'禁用'}<span>";
			}
		}},
		{ title: '${isEnglish?"Last Login Time":"最后登录时间"}',   width: '11%', elCls:'center', sortable: true, dataIndex: 'lastlogintime',renderer:BUI.Grid.Format.datetimeRenderer},
	    { title: '${isEnglish?"Registration Time":"注册时间"}',   width: '11%', elCls:'center', sortable: false,   dataIndex: 'logindatetime',renderer:BUI.Grid.Format.datetimeRenderer},
	    { title : '${isEnglish?"Operation":"操作"}',width: 400, sortable: false,  renderer : function(value,obj){
	    	var temp = '';
	    	if(shift_money&&obj.parentemployeecode==employeecode){
	    		if (is_English) {
					temp += "<button class='button button-info botton-margin'  onclick='show_in_out_panel(\""+obj.employeecode+"\",\""+obj.loginaccount.split("_")[1]+"\")'><i class='icon-random icon-white'></i> Transfer</button>";
	    		} else {
					temp += "<button class='button button-info botton-margin'  onclick='show_in_out_panel(\""+obj.employeecode+"\",\""+obj.loginaccount.split("_")[1]+"\")'><i class='icon-random icon-white'></i> 转分</button>";
	    		}
	    	}
	    	if (user_game) {
	    		if (is_English) {
		    		temp += "<button class='button button-inverse botton-margin'  onclick='getGameAccount(\""+obj.employeecode+"\")'><i class='icon-align-justify icon-white'></i>Game</button>";
	    		} else {
		    		temp += "<button class='button button-inverse botton-margin'  onclick='getGameAccount(\""+obj.employeecode+"\")'><i class='icon-align-justify icon-white'></i>游戏</button>";
	    		}
	    	}
	    	if(setting_config){
	    		if (is_English) {
		    		temp += "<button class='button button-success botton-margin'  onclick=\"openNewWindow('usersettlement','${ctx}/GCBonus/agentSetting?employeecode="+obj.employeecode+"','结算配置')\"><span class='icon-cog icon-white'></span> Settlement</button>";
	    		} else {
		    		temp += "<button class='button button-success botton-margin'  onclick=\"openNewWindow('usersettlement','${ctx}/GCBonus/agentSetting?employeecode="+obj.employeecode+"','结算配置')\"><span class='icon-cog icon-white'></span> 结算</button>";
	    		}
	    	}
   			if(reset_funpassword && reset_loginpassword ){
		    	temp += "<button class='button button-info   botton-margin'  onclick='updatePassword(this)' value='"+obj.employeecode+"' title='密码重置'><i class=' icon-info-sign icon-white'></i>密</button>";
   			}
	    	if(all_view) {
	    		if (is_English) {
		    		temp+="<button class='button button-warning botton-margin' onclick=openAccount('"+obj.employeecode.split('_')[1]+"','"+obj.loginaccount.split('_')[1]+"')><i class='icon-user icon-white'></i> Overview</button>";
	    		} else {
		    		temp+="<button class='button button-warning botton-margin' onclick=openAccount('"+obj.employeecode.split('_')[1]+"','"+obj.loginaccount.split('_')[1]+"')><i class='icon-user icon-white'></i> 总览</button>";
	    		}
	    	}
	    	return temp;
	    }}
	  ];
	
	var store = new Store({
	    url : '${ctx}/EEmployee/findEmployee',
	    autoLoad:false,
	    pageSize:15,
	    remoteSort: true,
	    sortInfo : {
	       field : 'lastlogintime',
	       direction : 'desc'
	    }
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
	
	grid.on('cellclick',function(ev){
        var sender = $(ev.domTarget);
        if(!sender.hasClass('x-grid-checkbox')){ //如果不是点中勾选列，不能进行选中操作
          return false;
        }
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

var dialogGlobal11 = null;
var dialogGlobal22 = null;
var dialogGlobal33 = null;

function updatePassword(obj){
	
	$("#employeecode1").val("");
	$("#newpassword1").val("");
	
	$("#employeecode2").val("");
	$("#newpassword2").val("");
	
	if(dialogGlobal11 == null) {
		BUI.use('bui/overlay',function(Overlay){
			dialogGlobal11 = new Overlay.Dialog({
				width:240,
				height:140,
				title:"密码重置",
				elCls : 'custom-dialog',
				bodyContent:"<button class='button button-inverse botton-margin'  onclick='showLoginPassword(this)' value='"+$(obj).val()+"'>登录密码重置</button>"
				+"<button class='button button-danger botton-margin'  onclick='showCapitalPassword(this)' value='"+$(obj).val()+"'>资金密码重置</button>",
				buttons : [{
					text:'取消',
					elCls : 'button',
					handler : function(){
						this.close();
					}
				}]
			});
		});
	}
	dialogGlobal11.show();
}
function showLoginPassword(obj){
	
	//$("#newpasswordtype").val("loginpassword");//登录密码
	$("#employeecode1").val(obj.value);
	
	
	if(dialogGlobal22 == null) {
		BUI.use('bui/overlay',function(Overlay){
			dialogGlobal22 = new Overlay.Dialog({
				width:340,
				height:260,
				title:"登录密码重置",
				elCls : 'custom-dialog',
				contentId:'content3',
				buttons : [{
					text:'取消',
					elCls : 'button',
					handler : function(){
						this.close();
					}
				}]
			});
			//dialogGlobal22.show();
		});
	}
	dialogGlobal22.show();
}

function showCapitalPassword(obj){
	
	//$("#newpasswordtype").val("capitalpassword");//资金密码
	$("#employeecode2").val(obj.value);
	
	if(dialogGlobal33 == null) {
		BUI.use('bui/overlay',function(Overlay){
			dialogGlobal33 = new Overlay.Dialog({
				width:340,
				height:260,
				title:"资金密码重置",
				elCls : 'custom-dialog',
				contentId:'content4',
				buttons : [{
					text:'取消',
					elCls : 'button',
					handler : function(){
						this.close();
					}
				}]
			});
			//dialogGlobal33.show();
		});
	}
	
	dialogGlobal33.show();
}
//确定修改密码
function saveNewpassword(obj,stype) {
	var value = $("#newpassword"+stype).val();
	var reg1 = new RegExp("^[a-z0-9]+$");// 验证只包含小写字母与数字
	var reg2 = new RegExp("[a-z]+");// 验证必须包含小写字母
	var reg3 = new RegExp("[0-9]+");// 验证必须包含数字
	if(value == null || value == "") {
		BUI.Message.Alert('请输入密码 !','error');
		$("#newpassword"+stype)[0].focus();
		return ;
	}
	if(value.length < 6 || value.length > 12) {
		BUI.Message.Alert('密码长度必须在6~12位之间 !','error');
		return ;
	}
 	if(!reg1.test(value)) {
		BUI.Message.Alert('密码不能包含特殊字符和大写字母 !','error');
		return ;
	}
 	if(!reg2.test(value) || !reg3.test(value)) {
		BUI.Message.Alert('密码必须包含小写字母符和数字 !','error');
		return ;
 	}
	if(stype == 2 ) {
		resetCapitalPassword($("#employeecode"+stype).val(), $("#newpassword"+stype).val());
	} else {
		resetLoginPassword($("#employeecode"+stype).val(), $("#newpassword"+stype).val());
	}
}

//获取随机密码
function getRandomPassword(obj,stype){
	$.ajax({
		url:"${ctx}/common/getRandomPassword",
		type:"post",
		data:{"employeecode": $("#employeecode"+stype).val()},
		dataType:"json",
		success:function(data){
			
			if("success" == data.status){
				$("#newpassword"+stype).attr("value", data.resetPassword);
				
			} else if("failure" == data.status){
				BUI.Message.Alert('获取随机密码失败!','error');
			}
			
		}
	});
}

//登录密码重置
function resetLoginPassword(employeecode, newpassword){
	//alert("调试：employeecode"+employeecode+ ",newpassword="+newpassword);
	$.ajax({
		url:"${ctx}/common/resetLoginPassword",
		type:"post",
		data:{"employeecode":employeecode, "newpassword": newpassword },
		dataType:"json",
		success:function(data){
			if("success" == data.status){
				//$(obj).parent().parent().remove();
				//$("div.bui-ext-mask").remove();
				//BUI.Message.Alert('初始密码为：<span style="font-weight:bolder;">'+data.resetPassword+'</span>','success');
				alert("初始密码为："+data.resetPassword);
				$("#searchForm").submit();
			}else if("failure" == data.status){
				//BUI.Message.Alert('密码重置失败!','error');
				alert("密码重置失败!");
			} else {
				alert("密码重置失败，status="+data.status);
			}
		}
	});
}
//资金密码重置
function resetCapitalPassword(employeecode, newpassword){
	$.ajax({
		url:"${ctx}/common/resetCapitalPassword",
		type:"post",
		data:{"employeecode":employeecode, "newpassword": newpassword },
		dataType:"json",
		success:function(data){
			if("success" == data.status){
				//$(obj).parent().parent().remove();
				//$("div.bui-ext-mask").remove();
				//BUI.Message.Alert('初始密码为：<span style="font-weight:bolder;">'+data.resetPassword+'</span>','success');
				alert("初始密码为："+data.resetPassword);
				$("#searchForm").submit();
			}else if("failure" == data.status){
				//BUI.Message.Alert('密码重置失败!','error');
				alert("密码重置失败!");
			}
		}
	});
}
</script>