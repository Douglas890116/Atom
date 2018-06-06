<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body  style="padding: 10px;">
    <div class="demo-content">
   <form id="searchForm" class="form-horizontal" style="outline: none;" method="post" data-depends="{'#btn:click':['toggle'],'#btn3:click':['clearFields']}">
    <input name="end_hidden" type="hidden" />
    <div class="well">
    	<div style="position: relative;display: inline-block;">
      	<div style="float: left;margin-right: 96px;">
      	 	<div class="control-group span7">
              <label class="control-label">登录账号：</label>
                <input name="loginaccount"  type="text"  data-tip='{"text":"登录账号","iconCls":"icon icon-user"}' class="control-text"/>
            </div>
            <div class="control-group span7">
              <label class="control-label">登录IP：</label>
                <input name="loginip"  type="text"  data-tip='{"text":"登录IP","iconCls":"icon icon-user"}' class="control-text"/>
            </div>
        </div>
        <div style="position: absolute;right: 15px;top: 3px;">
             <button id="btnSearch" type="submit" class="button button-primary">搜索</button>
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
    
    function showDialog(ip,btn) {
    	
    	$.ajax({
			  type:"post",
			  url:'${ctx}/LoginLog/findIp?ip='+ip+"&c="+new Date(),
			  success:function(data){
				  eval(data);
			    	
				  BUI.use('bui/overlay',function(Overlay){
			            var dialog = new Overlay.Dialog({
			              title:'地区',
			              width:300,
			              height:150,
			              //配置文本
			              bodyContent: ip+" = " + lo+"，"+lc,
			              success:function () {
			                this.close();
			              }
			            });
			            dialog.show();
			       });
			    	
			  }
		  }); 
    	
    }
    
        var Grid = BUI.Grid,
          Store = BUI.Data.Store,
          columns = [
            { title: '登陆IP',width:150,dataIndex: 'loginip',renderer:function(value,obj){
            	return "<a href=javascript:showLoginLogDatail('"+value+"') style='font-size:16px;' title='查看下级信息'>"+value+"</a>";
            }},
            { title: '地区',width:150, dataIndex: 'loginip',renderer:function(value,obj){
            	var  html = '<a target="_blank" href="http://www.hyzonghe.net/ip.jsp?ip='+value+'">查看地区</a>';
                return html;
            }},
            
            { title: '用户账号',width:'100%',dataIndex: 'loginaccount'},
            { title: '最后登陆时间',width:135,dataIndex: 'logintime',renderer:BUI.Grid.Format.datetimeRenderer},
            { title: '详细信息',width:108,renderer:function(value,obj){
            	return "<button class='button button-primary botton-margin' onclick=showLoginLogDatail('"+obj.loginip+"')><span class='icon-th-large icon-white'></span>查看详情</button>";
            }},
          ];
      
        var store = new Store({
            url : '${ctx}/LoginLog/countIpLoginUser',
            autoLoad : false,
            pageSize:15
          }),grid = new Grid.Grid({
            render:'#grid',
            autoRender:true,
            width:'100%',
            loadMask: true,
            fixedLayout:true,
            columns : columns,
            store: store,
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