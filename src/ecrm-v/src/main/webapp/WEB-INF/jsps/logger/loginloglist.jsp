<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body  style="padding: 27px 27px;">
    <div class="demo-content">
   <form id="searchForm" class="form-horizontal" style="outline: none;" method="post" data-depends="{'#btn:click':['toggle'],'#btn3:click':['clearFields']}">
    <input name="end_hidden" type="hidden" />
    <input name="loginip"  type="hidden"  value="${loginIp}"/>
    <div class="well">
    	<div style="position: relative;display: inline-block;">
      	<div style="float: left;margin-right: 96px;">
      	       <div class="control-group span14">
              <label class="control-label">登录时间：</label>
              <div class="bui-form-group" data-rules="{dateRange : true}">
                <input name="startDate"  type="text" placeholder="起始时间"  class="calendar calendar-time"/>
                <span>&nbsp;-&nbsp;</span>
                <input  name="endDate" type="text"  placeholder="结束时间"  class="calendar calendar-time"/>
              </div>
              <div style="margin-right: auto;margin: -30px 425px;">
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
      	 	<div class="control-group span7">
              <label class="control-label">登录账号：</label>
                <input name="loginaccount"  type="text"  data-tip='{"text":"登录账号","iconCls":"icon icon-user"}' placeholder="登录账号 " class="control-text"/>
            </div>
        </div>
        <div style="position: absolute;right: -38px;top: 3px;">
               <button id="btnSearch" type="submit" class="button button-primary">搜索</button>
               <button class="button button-danger" type="button" onclick="top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">返回列表</button>
        </div>
    </div>
    </div>
    </form>
    <!-- 搜索页 ================================================== -->
    
    
    <div class="search-grid-container well">
      <div id="grid">
    </div>
<!-- 用户新增按钮是否有操作权限 -->
<input type="hidden" value="" id="add_isoperate_Id" />
    <script type="text/javascript">
        var Grid = BUI.Grid,
          Store = BUI.Data.Store,
          columns = [
            { title: '用户账号',width:'15%',dataIndex: 'loginaccount'},
            { title: '登陆IP',width:'15%',dataIndex: 'loginip',renderer:function(value,obj){
            	var  html = '<a target="_blank" href="http://www.hyzonghe.net/ip.jsp?ip='+value+'">'+value+'</a>';
                return html;
            }},
            { title: '浏览器版本',width:'15%',dataIndex: 'browserversion'},
            { title: '浏览器标识码',width:'15%',dataIndex: 'machinecode'},
            { title: '操作系统',width:'20%',dataIndex: 'opratesystem',renderer:function(value,obj){
            	return value+'<button type="button" class="button  button-success " style="visibility:hidden;"><span class="icon-edit icon-white"></span></button>';
            }},
            { title: '登陆时间',width:'20%', dataIndex: 'logintime',  showTip: true,sortable: true, renderer:BUI.Grid.Format.datetimeRenderer}
          ];
      
        var store = new Store({
            url : '${ctx}/LoginLog/data',
            autoLoad : false,
            params:{loginip:$("input[name='loginip']").val()},
            pageSize:15,
            sortInfo : {
                field : 'logintime',
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
            emptyDataTpl:'<div class="centered"><img alt="Crying" src="${statics }/img/dataisnull.png"/><font style="color:red;font-size:20px;"><h2>没有找到匹配的数据!</h2></font></div>',
            bbar : {
              pagingBar:true,
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
<!-- script end -->
  </div>
	
	</div>
</body>
</html>