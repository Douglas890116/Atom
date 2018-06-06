<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.maven.cache.SystemCache"%>
<%@ include file="/WEB-INF/jsps/layout/base-header.jsp"%>
  <div class="demo-content" style="margin-left: 18px;">
    <div class="row">
      <div>
            <form:form modelAttribute="enterpriseEmployee" method="post" id="objForm-bonus" class="form-horizontal" style="width:558px;">
                  <div class="control-group" >
                    <label class="control-label">洗&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码: </label>
                    <div style="clear: both;">
                      <c:forEach var="game" varStatus="status" items="${games}">
                        <div class="control-group" >
                          <label class="control-label" style="color:red;width: 50px;"><b>${game.gametype.replace("Game","") }:</b></label>
                          <div class="controls">
                            <c:forEach items="${SystemCache.getInstance().getGameCategory(game.gametype)}" var="category">
                            <c:set var="key" scope="page" value="${category.gametype}_${category.categorytype}"/>
                            <c:set var="maxbonus" scope="page" value="${bonus.get(key)==null?0:bonus.get(key)*100}"/>
                             <div style="float: left;">
                             ${category.categoryname}：<input  class="input-normal control-text" type="text" name="${key}"  value=""  style="width:30px;height:18px;" data-rules="{number:true,min:${category.minbonus*100 },max:${maxbonus}}" /> %&nbsp;&nbsp;&nbsp;&nbsp;
                             </div>
                         </c:forEach>
                          </div>
                        </div>
                    </c:forEach>
                      <div style="clear: both;"></div>
                    </div>
                  </div>
                  <!-- <hr/>
                   <button type="button" id="J_Form_Submit" class="button button-primary" onclick="save()"> 提 交 </button> -->
        </form:form>
      </div>
    </div>  
  </div>

