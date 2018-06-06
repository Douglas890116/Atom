<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body  style="padding: 10px;">
    <div class="demo-content">
   <form id="searchForm" class="form-horizontal" style="outline: none;" method="post" data-depends="{'#btn:click':['toggle'],'#btn3:click':['clearFields']}">
    <input name="end_hidden" type="hidden" />
    <div style="display: none;"><input name="ORDER_FEILD_createtime" value="DESC" type="hidden"/></div>
    <div class="well">
    	<div style="position: relative;display: inline-block;">
      	<div style="float: left;margin-right: 96px;">
      	      <div class="control-group span14">
              <label class="control-label">有效时间：</label>
              <div class="bui-form-group" data-rules="{dateRange : true}">
                <input name="begintime"  type="text" placeholder="起始区间" class="calendar calendar-time"/>
                <span>&nbsp;-&nbsp;
                </span><input  name="endtime" type="text" placeholder="结束区间" class="calendar calendar-time"/>
              </div>
              <div style="margin-right: auto;margin: -30px 425px;">
                 <select onchange="changeFormatDate(this,'begintime','endtime')"  style="width:85px;">
                                <option value="">请选择</option>
                                <option value="1" selected="selected">今天</option>
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
              <label class="control-label">品牌：</label>
                <input name="brandname"  type="text"  data-tip='{"text":"品牌"}' class="control-text" placeholder="品牌"/>
            </div>
      	 	<div class="control-group span7">
              <label class="control-label">公告标题：</label>
                <input name="title"  type="text"  data-tip='{"text":"公告标题"}' class="control-text" placeholder="公告标题"/>
            </div>
            <div class="control-group span7">
              <label class="control-label">公告内容：</label>
                <input name="content"  type="text"  data-tip='{"text":"公告内容"}' class="control-text" placeholder="公告内容"/>
            </div>
        </div>
        <div style="position: absolute;right: 15px;top: 3px;">
               <button id="btnSearch" type="submit" class="button button-primary"><span class="icon-search icon-white"></span>搜索</button>
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
<script type="text/javascript" src="${statics }/js/custom/page-quote.js"></script>
<script type="text/javascript">
   $("input[name='begintime']").val(getDate("begintime"));
   $("input[name='endtime']").val(getDate("endtime"));	
    function permissionValidate(){
      	var array= new Array();
   		array.push({
             btnCls : 'button button-primary',
             text:'<span class="icon-file icon-white"></span>创建公告',
             handler : function(){
            	 openNewWindow('noticadd','${ctx}/BrandNotic/add','创建公告');
             }
         });
   		return array;
    }
      	
        var Grid = BUI.Grid,
          Store = BUI.Data.Store,
          columns = [
			{ title: '企业',width:'10%',  dataIndex: 'enterprisename'},
			{ title: '品牌',width:'10%',  dataIndex: 'brandname'},
            { title: '公告标题',width:'15%', dataIndex: 'title'},
            { title: '公告内容',width:'60%',  dataIndex: 'content'},
            /* { title: '有效时间',width:150,  renderer:function(value,obj){
            	return BUI.Grid.Format.datetimeRenderer(obj.begintime) +"-<br/>-" + BUI.Grid.Format.datetimeRenderer(obj.endtime);
            }}, */
            { title: '起始有效时间',width:135,dataIndex: 'begintime', renderer:BUI.Grid.Format.datetimeRenderer},
            { title: '结束有效时间',width:135,dataIndex: 'endtime', renderer:BUI.Grid.Format.datetimeRenderer},
            { title: '创建时间',width:135,dataIndex: 'createtime', renderer:BUI.Grid.Format.datetimeRenderer},
            { title: '操作',width:170, renderer:function(value,obj){
            	return '<button type="button" class="button  button-success button-space" onclick="openNewWindow(\'noticedit\',\'${ctx}/BrandNotic/edit?noticcode='+obj.noticcode+'\',\'编辑公告\')" ><span class="icon-edit icon-white"></span>编辑</button>'
            	+'<button type="button" class="button button-danger button-space" onclick="PageFunc.Del(\'${ctx}/BrandNotic/delete\',\''+obj.noticcode+'\')" ><span class="icon-remove  icon-white"></span>删除</button>';
            }}
          ];
      
        var store = new Store({
            url : '${ctx}/BrandNotic/data',
            autoLoad:false,
            pageSize:15,
            sortInfo : {
                field : 'begintime',
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
            plugins : [ Grid.Plugins.CheckSelection ],
            emptyDataTpl : '<div class="centered"><img alt="Crying" src="${statics }/img/dataisnull.png"/><font style="color:red;font-size:20px;"><h2>没有找到匹配的数据!</h2></font></div>',
            tbar:{
                items:permissionValidate()
               },
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