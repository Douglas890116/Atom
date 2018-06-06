<%@page import="com.maven.config.SystemConstant"%>
<%@page import="com.maven.cache.SystemCache"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body style="padding: 10px;">
<div class="demo-content" >
<!-- 搜索页 ================================================== -->
  <form id="searchForm" class="form-horizontal" style="outline: none;" method="post">
    <div class="row well" style="margin-left: 0px;position: relative;">
    <div style="position: relative;display: inline-block;">
      <div style="float: left;margin-right: 96px;">
            <div class="control-group span7" >
              <label class="control-label">订单号：</label>
              <div class="controls">
                <input name="ordernumber"  type="text"   class="control-text"/>
              </div>
            </div>
            <div class="control-group span6" >
              <label class="control-label">账号：</label>
              <div class="controls">
                <input name="loginaccount"  type="text"  class="control-text span3"/>
              </div>
            </div>
            <div class="control-group span6" >
              <label class="control-label">付款人：</label>
              <div class="controls">
                <input name="employeepaymentname"  type="text"  class="control-text span3"/>
              </div>
            </div>
             <div class="control-group span7 toggle-display" >
              <label class="control-label">付款账号：</label>
              <div class="controls">
                <input name="employeepaymentaccount"  type="text"  class="control-text "/>
              </div>
            </div>
            <div class="control-group span6 toggle-display" >
              <label class="control-label">收款人：</label>
              <div class="controls">
                <input name="enterprisepaymentname"  type="text"  class="control-text span3"/>
              </div>
            </div>
             <div class="control-group span7 toggle-display" >
              <label class="control-label">收款账号：</label>
              <div class="controls">
                <input name="enterprisepaymentaccount"  type="text"  class="control-text "/>
              </div>
            </div>
             <div class="control-group span10 toggle-display">
              <label class="control-label">订单时间：</label>
              <div class="controls bui-form-group" data-rules="{dateRange : true}">
                <input name="orderdate_begin" type="text" class="calendar calendar-time " value="" style="width: 125px;"/>
                -
                <input name="orderdate_end" type="text"   class="calendar calendar-time " value="" style="width: 125px;"/>
              </div>
           </div> 
             <div class="control-group span6 toggle-display" >
              <label class="control-label">交易IP：</label>
              <div class="controls">
                <input name="traceip"  type="text"  class="control-text span3"/>
              </div>
            </div>
            <div class="control-group span7 toggle-display" >
              <label class="control-label">订单金额：</label>
              <div class="controls">
                <input name="orderamount_begin"  type="text"  class="control-text span2"/>
                -
                <input name="orderamount_end"  type="text"  class="control-text span2"/>
              </div>
            </div>
            
         </div>
      	 <div style="position: absolute;right: 15px;top: 3px;">
         	 <button id="btnSearch" type="submit"  class="button button-primary"> 搜 索 </button>
        </div>
        </div>
    </div> 
  </form>
  <div class="search-grid-container well">
    <div id="grid"></div>
  </div>
</div>
<!-- 存款申请单审批 -->
<div style="display:none;background-color: white;width: 588px;height: 500;z-index: 100;position: absolute;top: 130px;right:678px;border:1px solid #000" id="approveDivId">
    <div class="demo-content" style="margin: 27px;">
    <div class="row">
      <div>
        <h2>存款申请单审批</h2><br>
        <div>
          <span id="bankNameId"></span>&nbsp;&nbsp;&nbsp;
          <span id="accountNameId"></span>&nbsp;&nbsp;&nbsp;
          <span id="bankAccountId"></span>
       </div>
        <hr>
        <form:form enctype="multipart/form-data" method="post" action="">
              <input type="hidden" name="orderNumber" />
              <div class="control-group">
                  <div class="controls">
                                                          委托人编码：<input class="input-normal control-text" type="text" disabled="disabled" value="${sessionScope.ERCM_USER_SESSION.employeecode}" style="width:167px;height:18px;"/>&nbsp;&nbsp;<span style="color: red">*</span>
                  </div>
              </div>
              <br />
              <div class="control-group">
                  <div class="controls">
                                                          委托人名称：<input class="input-normal control-text" type="text" disabled="disabled" value="${sessionScope.ERCM_USER_SESSION.loginaccount}" style="width:167px;height:18px;"/>&nbsp;&nbsp;<span style="color: red">*</span>
                  </div>
              </div>
              <br />
              <div class="control-group">
                  <div class="controls">
                                                        审&nbsp;核&nbsp;说&nbsp;明：<textarea  name="auditdesc"  style="width:172px;height:68px;" ></textarea>&nbsp;&nbsp;<span style="color: red">*</span>
                  </div>
              </div>
              <br />
              <div class="control-group">
                  <div class="controls">
                                                        图&nbsp;片&nbsp;上&nbsp;传：<input type="file" id="fileId" name="file" style="width:152px;" dir="rtl" />
                                                <input type="button" value="上传" onclick="ajaxFileUpload('${ctx}/FileUpload','deposit');" style="background: none repeat scroll 0 0 transparent;"/>
                                                <input type="hidden"  name="imgname" /> 
                  </div>
              </div>
              <br />
              <div class="control-group">
              <a href=""></a>
                  <div class="controls" id="imgId"></div>
              </div>
              <hr>
              <div class="control-group">
                <button type="button" class="button button-primary" onclick="approveSubmit();">通过</button>
                <button type="button" class="button button-primary" onclick="refusal();">拒绝</button>
                <button type="button" class="button button-primary" onclick="rejectSubmit();">驳回</button>
                <br>
                <span style="color:red;">通过：订单有效;&nbsp;&nbsp;拒绝：订单无效;&nbsp;&nbsp;驳回：修改之后重新提交审批</span>
            </div>
        </form:form>
      </div>
    </div>  
  </div>
</div>
</body>
</html>
<script type="text/javascript">
	(function(){
		var order_audit = ${sessionScope.ERCM_USER_PSERSSION['MN003K'].menustatus == 1 };
		
		 function permissionValidate(){
        	var array= new Array();
       		array.push({
                 btnCls : 'button button-primary',
                 text:'<span class="icon-file icon-white"></span>手工补单',
                 handler : function(){
               	 openNewWindow('create_brand','${ctx}/takeDepositRecord/DepositAdd','存款补单');
                 }
             });
       		return array;
		 }
		
	    var Grid = BUI.Grid,
	      Store = BUI.Data.Store,
	      columns = [
			{ title: '品牌',width: 80,  dataIndex: 'brandcode'},
			{ title: '订单号',   width: '25%', dataIndex:'ordernumber'},			
			{ title: '账号',  width: '10%',  dataIndex: 'loginaccount',renderer:function(value,obj){
				return  "<span style='color: #428BCA;font-size:16px;' title='账户' >"+value+"</span>";
			}},
			{ title: '订单金额', width: 100,  renderer:function(value,obj){
	        	return "<span style='color:#5cb85c;font-size: 18px;'>"+parseFloat(obj.orderamount).toFixed(2)+"</span>";
	        }},
			{ title: '付款人',  width: 90,  dataIndex: 'employeepaymentname'},
	        { title: '付款账号', width: '18%',  dataIndex: 'employeepaymentaccount'},
	        { title: '订单时间',width: '14%',dataIndex: 'orderdate',renderer:BUI.Grid.Format.datetimeRenderer},
	        { title: '企业收款人',  width: 80,  dataIndex: 'enterprisepaymentname'},
	        { title: '企业收款账号',  width: '18%',  dataIndex: 'enterprisepaymentaccount'},
	        { title: '交易IP',   width: 100,    dataIndex: 'traceip'},
	        { title: '备注',  width: '6%',  dataIndex: 'ordercomment'},
	        { title: '当前处理人',   width: 80,    dataIndex: 'handleemployee'},
	        { title : '操作',		width: 80,   renderer : function(value,obj){
	        	var html="";
	        	if(order_audit&&!obj.handleemployee){
	        		html+= '<button onclick="openApproveDialog(this)" value="'+obj.ordernumber+'" name="存款审批" class="button button-info" title="审批"></button>';
	        	}
	          	return html;
	        }}
	      ];

	var store = new Store({
	        url : '${ctx}/takeDepositRecord/findDepositApproveRecord',
	        autoLoad:true, //自动加载数据
	        pageSize:15
	      }),
	      grid = new Grid.Grid({
	        render:'#grid',
	        loadMask: true,//加载数据时显示屏蔽层
	        width:'100%',
	        columns : columns,
	        store: store,
	        plugins : [ Grid.Plugins.CheckSelection ],
	        emptyDataTpl:'<div class="centered"><img alt="Crying" src="${statics }/img/dataisnull.png"/><font style="color:red;font-size:20px;"><h2>没有找到匹配的数据!</h2></font></div>',
	        tbar : {
				items : permissionValidate()
			},
	        bbar : {
	          pagingBar:true
	        }
	      });
		 grid.on('cellclick',function(ev){
	         var sender = $(ev.domTarget);
	         if(!sender.hasClass('x-grid-checkbox')){ //如果不是点中勾选列，不能进行选中操作
	           return false;
	         }
	       });

	    grid.render();
	    //创建表单，表单中的日历，不需要单独初始化
	    var form = new BUI.Form.HForm({
	      srcNode : '#searchForm',
	    }).render();

	    form.on('beforesubmit',function(ev) {
	      //序列化成对象
	      var obj = form.serializeToObject();
	      obj.start = 0; //返回第一页
	      store.load(obj);
	      return false;
	    });
		
	})()

	
</script>