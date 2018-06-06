<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<html>
<style type="text/css">
body,ol,ul,li,p,h1,h2,h3{ padding:0; margin:0; list-style:none; }
.a_c_left{  float:left; }
.a_c_right{float:right; }
.a_c_left,.a_c_right{width:43%;border:1px #ddd solid; border-radius:3px}
.a_c_left h1{ font-size:12px; color:#666; font-weight:normal; height:30px; line-height:30px; padding-left:10px; border-bottom:1px #ddd solid;}
.a_c_left h1 span{ color:red}
.a_c_left ul{ height:230px; overflow:hidden; overflow-y:scroll; border:1px #ddd solid; width:97%; margin:0 auto; margin-top:15px; margin-bottom:15px; padding-top:5px;}
.a_c_left ul li{ height:24px; padding-left:10px; line-height:24px; color:#666; font-size:12px; cursor:pointer;}
.a_c_left ul li:hover{ color:red; background:#E2E2E2}
.a_c_left ul li.hover{ color:red; background:#E2E2E2}
.left_muen  h1{ font-size:12px; color:#666; font-weight:normal; height:30px; line-height:30px; padding-left:10px; border-bottom:1px #ddd solid;}
.left_muen ul{ padding-top:6px}
.left_muen ul li{ font-size:12px; line-height:24px; line-height:24px; padding-left:15px;}
.left_muen ul li{ color:#333; text-decoration:none; display:block;cursor: pointer;}
.left_muen ul li:hover{ color:red; }
.left_muen ul li.hover{ background:#E2E2E2; color:#39F }
.a_c_right h1{ font-size:12px; color:#666; font-weight:normal; height:30px; line-height:30px; padding-left:10px; border-bottom:1px #ddd solid;}
.a_c_right h1 span{ color:red}
.a_c_right ul{ height:230px; overflow:hidden; overflow-y:scroll; border:1px #ddd solid; width:97%; margin:0 auto; margin-top:15px; margin-bottom:15px; padding-top:5px;}
.a_c_right ul li{ height:24px; padding-left:10px; line-height:24px; color:#666; font-size:12px; cursor:pointer;}
.a_c_right ul li:hover{ color:red; background:#E2E2E2}
.a_c_right ul li.hover{ color:red; background:#E2E2E2}
.left_muen{ float:left; width:8.5%; min-height:550px; margin-right:12px;border:1px #ddd solid; border-radius:3px}
.buttons{ float:left; margin-left:-35px;margin-top:58px;height:24px;position: absolute;left: 50%;}
.buttons button{ width:68px; margin:0 auto; cursor:pointer;line-height:24px;  margin-bottom:10px; font-size:12px}
.conter_font{ padding:10px 0px 10px 0px}
.conter_font h1{ font-size:14px; color:#06F; padding-bottom:10px;}
.conter_font h2{ font-size:16px; color:#666; padding-bottom:10px;}
.conter_font p{ font-size:12px; line-height:20px;}
.conter_font ol{ padding:12px;}
.conter_font ol li{ font-size:12px; line-height:24px; text-indent:3em;}
.admin_conter{ margin-left: 20px;margin-right: 20px;position: absolute;width: 800px;}
</style>
<body>
<div class="admin_conter">
 <div class="conter_font">
<h1><b>快速编辑流程审批人员</b></h1>
<b>您可以一次添加或删除多个审批人员。 进行审批授权时请注意：</b>
<ol>
	<li>．<b>说明</b> - 左侧列表框表示未授权用户，右侧列表框表示已授权用户。</li>
	<li>．<b>删除</b> - 点击选中右侧已授权用户，通过选中移除按钮将用户移入左侧列表框，点击保存按钮即可。</li>
	<li>．<b>添加</b> - 点击选中左侧未授权用户，通过选中加入按钮将用户移入右侧列表框，点击保存按钮即可。</li>
</ol>
<p><b>步骤 1:</b> 选择需要编辑的用户组移入到相应的列表框内。</p>
<p><b>步骤 2:</b> 点击保存按钮。</p>
</div>
	<div class="a_c_left">
      <h1>共<span id="countGrant">0</span>个组，选择中了<span id="countSelect">0</span>个组</h1>
      <ul id="addElement"></ul>
    </div>
    <div class="buttons">
      <p><button onclick="addElement(1);" class="button button-small button-primary">选中加入</button></p>
      <p><button onclick="addElement(2);" class="button button-small button-primary">全部加入</button></p>
      <p><button onclick="addElement(3);" class="button button-small button-danger">选中移除</button></p>
      <p><button onclick="addElement(4);" class="button button-small button-danger">全部移除</button></p>
      <p><button onclick="saveElement();" id="saveId" class="button button-success">保存</button></p>
      <p><button class="button button-small" onclick="top.topManager.openPage({id:'${requestScope.MENUS.parentmenucode}',isClose : true});">返回列表</button></p>
    </div>
    <div class="a_c_right">
      <h1>已授权共<span id="countNoGrant">0</span>用户组</h1>
      <ul id="removeElement"></ul>
    </div>
</div>
<input name="flowcode" value="${flowcode}" type="hidden"/>
</body>
</html>
<script type="text/javascript">
//初始化将保存按钮禁用
$("#saveId").attr({"disabled":"disabled"});
//加载员工数据
loadData();
</script>