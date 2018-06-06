<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.maven.entity.EnterpriseEmployeeType.Type" %>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body style="padding: 10px;">
    <div class="demo-content" >
<!-- 搜索页 ================================================== style=" margin:0 auto;  width:950px;"-->
<form id="searchForm" class="form-horizontal" style="outline: none;" method="post">
    <div class="row well" style="margin-left: 0px;position: relative;">
    <div style="position: relative;display: inline-block;">
      <div style="float: left;margin-right: 96px;">
             <div class="control-group span12">
                <label class="control-label">推广链接：</label>
                <div class="bui-form-group">
                    <input type="text" name="domainlink" class="input-small control-text" style="width:167px;height:18px;">
                </div>
            </div>
          </div>
          	 <div style="position: absolute;right: -25px;top: 3px;">
             	 <button id="btnSearch" type="submit"  class="button button-primary"> <span class="icon-search icon-white"></span>搜 索 </button>
                 <input type="reset" style="display:none;" id="resetId" />
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

var Grid = BUI.Grid,
  Store = BUI.Data.Store,
  columns = [
	{ title: '企业名称',width: '20%',elCls:'center',  sortable: false,  dataIndex: 'enterprisename'},
	{ title: '品牌名称',width: '20%',elCls:'center',  sortable: false,  dataIndex: 'brandname'},
	{ title: '用户账号',width: '20%',elCls:'center',  sortable: false,  dataIndex: 'loginaccount'},
	{ title: '注册链接',width: '20%',elCls:'center',  sortable: false,  dataIndex: 'domainlink'},
	{ title: '用户数量',width: '20%',elCls:'center',  sortable: false,  dataIndex: 'num'}
  ];

var store = new Store({
    url : '${ctx}/report/queryUserDomainLink',
    autoLoad:false, 
    pageSize:15
  }),grid = new Grid.Grid({
    render:'#grid',
    autoRender:true,
    loadMask: true,//加载数据时显示屏蔽层
    width:'100%',
    columns : columns,
    store: store,
    plugins : [Grid.Plugins.CheckSelection],
    emptyDataTpl:'<div class="centered"><img alt="Crying" src="${statics }/img/dataisnull.png"/><font style="color:red;font-size:20px;"><h2>没有找到匹配的数据!</h2></font></div>',
    // 顶部工具栏
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