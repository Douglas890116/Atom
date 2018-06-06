<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
<!-- 活动参数设置 -->
    <div id="setting-panel">
        <form method="post" id="settingform">
        <input type="hidden" name="ecactivitycode" value="${ecactivitycode }">
        <input type="hidden" name="activitytemplatecode" value="${activitytemplatecode }">
        <c:choose>
        	<c:when test="${setting.size()>0 }">
				<c:forEach items="${setting }" var="sett">
	              <div class="control-group" style="margin-left: 20px;margin-bottom: 10px;">
	              	  <label class="control-label" style="margin-right: 12px;width: 200px;display: inline-block;">${sett.agementdesc }：</label>
					  <input name="${sett.agementname }" class="input-large control-text"  value='${auments[sett.activitysettingcode] }' style="width: 200px;" type="text" placeholder="${sett.agementdesc }"/>
					  <!-- 
					  <textarea rows="4" cols="80" name="${sett.agementname }" placeholder="${sett.agementdesc }">${auments[sett.activitysettingcode] }</textarea>
					   -->
					   
	              </div>
	        	</c:forEach>        	
        	</c:when>
        	<c:otherwise>
        		<div style="font-size: 36px;color:#EFEFEF;padding: 120px 100px;">无参数配置</div>
        	</c:otherwise>
        </c:choose>
        </form>
  </div>
