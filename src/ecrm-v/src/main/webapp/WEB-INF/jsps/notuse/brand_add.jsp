<%@page import="com.maven.config.SystemConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<body>
<div class="demo-content" style="margin: 27px;">
    <!-- 表单页 ================================================== --> 
    <div class="row">
      <div>
        <h2><a href="${ctx}/EOBrand/list">企业管理</a><span class="divider"> / </span>创建品牌</h2>
        <hr/>
        <form aria-pressed="false" aria-disabled="false" id="objForm" class="form-horizontal bui-form bui-form-field-container" method="post" >
            <div class="control-group">
              <label class="control-label">企业名称：</label>
              <div class="controls">
              	<select name="enterprisecode" aria-disabled="false" style="width:192px;height:30px;" class="bui-form-field-select bui-form-field" data-rules="{required:true}" disabled="disabled">
                  <option value="EN0000">好盈科技</option>
                </select>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">品牌名称：</label>
                <div class="controls">
                  <input name="brandname" class="input-small control-text" style="width:167px;height:18px;" type="text" data-rules="{required:true,maxlength:8}" data-tip="{text:'长度不超过8个字符'}"/>
                  <div class="common_mark"></div>  
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">品牌Logo：</label>
                <div class="controls">
                    <input type="file" id="fileId" name="file" style="width:144px;" dir="rtl" data-rules="{required:true}"/>
                    <input type="button" value="上传" onclick="ajaxFileUpload('${ctx}/FileUpload','logo');" style="background: none repeat scroll 0 0 transparent;" />
                    <input type="hidden"  name="logopath" data-rules="{required:true}"/>
                    <div class="common_mark"></div> 
                    <span id="imgId"></span>
                </div>
            </div>
            <hr/>
            <div class="control-group">
            <label class="control-label">账户类别：</label>
	                <div class="controls">
	                    <select name="employeetypecode" disabled="disabled" data-rules="{required:true}" style="width:192px;height:30px;">
	                        <option value="0001" >股东</option>
	                    </select>
	                </div>
            </div>
            
           <div class="control-group">
	                
	                 <label class="control-label">帐户昵称：</label>
	                  <div class="controls">
	                      <input class="input-normal control-text" name="displayalias" type="text" style="width:167px;height:18px;" data-rules="{required:true,maxlength:8}" data-tip="{text:'长度不超过8个字符'}"/>
	                      <div class="common_mark"></div>  
                      </div>
                       <label class="control-label">注册账号：</label>
	                <div class="controls">
	                  <input class="input-normal control-text" name="loginaccount" type="text" data-remote="${ctx}/EEmployee/employeeIsExistForBUI"  data-rules="{required:true,minlength:6,maxlength:12,regexp:/^[0-9a-zA-z]{6,12}$/}"  data-messages="{regexp:'账号只能是数字或者字母'}" data-tip="{text:'长度为6-12个字符'}" style="width:167px;height:18px;"/>
	                  <div class="common_mark"></div>  
                    </div>
	              </div>
	              
	              <div class="control-group">
	               
                     <label class="control-label">登陆密码：</label>
	                <div class="controls">
	                    <input class="input-normal control-text" name="loginpassword" id="passwordId" type="password" data-rules="{required:true,minlength:6,maxlength:18,regexp:/^[a-z0-9_-]{6,18}$/}" data-messages="{regexp:'密码只能是数字或者字母'}" data-tip="{text:'长度为6-18个字符'}"  style="width:167px;height:18px;" />
	                    <div class="common_mark"></div>  
                    </div>
                    <label class="control-label">确认密码：</label>
                  <div class="controls">
                      <input class="input-normal control-text" name="loginpassword" type="password" data-rules="{required:true,equalTo:'#passwordId'}" data-tip="{text:'与登陆密码一致'}"  style="width:167px;height:18px;" />
                      <div class="common_mark"></div>  
                    </div>
	              </div>
            <hr/>
              	  <div class="control-group">
	                <label class="control-label">接入游戏：</label>
	                <div class="bui-form-group" data-rules="{checkRange:1}" data-messages="{checkRange:'最少选择一个游戏'}">
	                 <div class="controls" >
	                  <c:forEach var="game" varStatus="status" items="${games}">
	                  	<label class="control-label checkbox span2" style="color: #3071A9;font-weight: bold;width:120px;text-align: left;">
			              <input type="checkbox" name="Game-${game.gametype}"  value="${game.gamecode}"> ${game.gamename} 
			            </label>
			           <c:if test="${status.count%5==0 }">
				           <c:out escapeXml="false" value="</div><div class='controls' style='clear: both;margin-left: 70px;'>"></c:out>
			           </c:if>
	                 </c:forEach>
	                 	</div>
	                </div>
              	</div>
              	 <div class="control-group">
	                <label class="control-label">接入API编码：</label>
	                <div class="controls">
	                  	<input class="input-normal control-text" name="platformcode" type="text" data-rules="{required:true}" data-tip="{text:'API平台编码'}"  style="width:167px;height:18px;" />
	                  	 <div class="common_mark"></div>  	
	                </div>
	                <label class="control-label">接入API地址：</label>
	                <div class="controls">
	                  	<input class="input-normal control-text" name="apiurl" type="text" data-rules="{required:true}" data-tip="{text:'API访问地址'}"  style="width:167px;height:18px;" />
	                  	 <div class="common_mark"></div>  	
	                </div>
	                
              	</div>
              	<div class="control-group">
	                <label class="control-label">AES &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  KEY：</label>
	                <div class="controls">
	                  	<input class="input-normal control-text" name="apikey1" type="text" data-rules="{required:true}" data-tip="{text:'AES KEY'}"  style="width:167px;height:18px;" />
	                  	 <div class="common_mark"></div>  	
	                </div>
	                <label class="control-label">MD5 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;KEY：</label>
	                <div class="controls">
	                  	<input class="input-normal control-text" name="apikey2" type="text" data-rules="{required:true}" data-tip="{text:'MD5 KEY'}"  style="width:167px;height:18px;" />
	                  	 <div class="common_mark"></div>  	
	                </div>
              	</div>
              	
	              	<hr/>
	              	<div class="control-group">
	                <label class="control-label">默认主域名：</label>
	                <div class="controls">
	                  	<input class="input-normal control-text" name="domainlink" type="text" data-rules="{required:true,regexp:/^(?=^.{3,255}$)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+$/}" data-messages="{regexp:'请输入正确的域名格式，例如:www.google.com'}" data-tip="{text:'例如:www.google.com'}"  style="width:350px;height:18px;" />
	                  	<div class="common_mark"></div>	
	                </div>
              	</div>
              	<hr/>
            <div class="row actions-bar">       
	          <div class="form-actions span13 offset3">
	            <button type="button" id="J_Form_Submit" class="button button-primary" >保存</button>
	            <button class="button button-danger" type="button" onclick="top.topManager.closePage();">返回列表</button>
	          </div>
	      </div>
        </form> 
      </div>
    </div>  
    <script type="text/javascript" src="${statics }/js/ajaxfileupload-min.js"></script>
    <script type="text/javascript">
    $(function () {
    	BUI.use('custom/commons');
    	BUI.use('bui/overlay');
    	BUI.use(['bui/mask']);
    	var form = new BUI.Form.HForm({
 		      srcNode : '#objForm',
 	    }).render(); 
   		bindClick("J_Form_Submit","${ctx}/EOBrand/save",form);
    }); 
    </script>
<!-- script end -->
  </div>
  <%@ include file="/WEB-INF/jsps/layout/pub-click-js.jsp"%>
</body>
</html>    