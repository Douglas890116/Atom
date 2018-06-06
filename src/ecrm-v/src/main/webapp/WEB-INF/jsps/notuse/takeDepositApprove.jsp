<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<script type="text/javascript" src="${statics }/js/ajaxfileupload-min.js"></script>
<body>
  <div class="demo-content" style="margin: 27px;">
    <div class="row">
      <div>
        <h2>单据审批</h2>
        <hr>
        <form:form enctype="multipart/form-data" method="post" action="${ctx}/takeDepositRecord/approve">
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
                                                        审&nbsp;核&nbsp;说&nbsp;明：<textarea  name="auditdesc"  style="width:172px;height:68px;" ></textarea>
                  </div>
              </div>
              <br />
              <div class="control-group">
                  <div class="controls">
                                                        图&nbsp;片&nbsp;上&nbsp;传：<input type="file" id="fileId" name="file" style="width:152px;" dir="rtl" />
                                                <input type="button" value="上传" onclick="ajaxFileUpload('${ctx}/FileUpload','take');" style="background: none repeat scroll 0 0 transparent;"/>
                                                <input type="hidden"  name="imgname" /> 
                  </div>
              </div>
              <br />
              <div class="control-group">
                  <div class="controls" id="imgId"></div>
              </div>
              <hr>
              <div class="control-group">
                <button type="button" class="button button-primary" onclick="approveSubmit();"> 通过 </button>
                <button type="button" class="button button-primary" onclick="rejectSubmit();"> 不通过 </button>
                <a href="javascript:top.topManager.closePage();"><button class="button" type="button">返回列表</button></a>
            </div>
        </form:form>
      </div>
    </div>  
  </div>
</body>
</html>
