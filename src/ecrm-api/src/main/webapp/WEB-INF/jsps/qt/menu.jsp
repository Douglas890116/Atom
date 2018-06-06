<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ page import="com.maven.utils.AESUtil"%>
<c:set var="ctx" value="<%=request.getContextPath()%>" />
<c:set var="statics" value="${ctx}/static"></c:set>
<%@ page language="java" pageEncoding="UTF-8"%>

<%

%>

<div class="top">
    <div class="top_cen">
      <div class="logo"><img src="${statics }/qt/images/logo.png"></div>
      <div class="nav_l">
         <ul>
		    <li>
               <a href="${ctx}/qtgame/index?stype=<%=AESUtil.encrypt("热门游戏") %>" class="col">
               <c:if test="${stype == '热门游戏' }"><b>热门游戏</b></c:if>
               <c:if test="${stype != '热门游戏' }">热门游戏</c:if>
               </a>
            </li>
            <li>
               <a href="${ctx}/qtgame/index?stype=<%=AESUtil.encrypt("Amaya") %>" class="col">
               <c:if test="${stype == 'Amaya' }"><b>Amaya</b></c:if>
               <c:if test="${stype != 'Amaya' }">Amaya</c:if>
               </a>
            </li>
            <li>
               <a href="${ctx}/qtgame/index?stype=<%=AESUtil.encrypt("BG") %>" class="col">
               <c:if test="${stype == 'BG' }"><b>BG</b></c:if>
               <c:if test="${stype != 'BG' }">BG</c:if>
               </a>
            </li>
            <li>
               <a href="${ctx}/qtgame/index?stype=<%=AESUtil.encrypt("ELK") %>" class="col">
               <c:if test="${stype == 'ELK' }"><b>ELK</b></c:if>
               <c:if test="${stype != 'ELK' }">ELK</c:if>
               </a>
            </li>
            <li>
               <a href="${ctx}/qtgame/index?stype=<%=AESUtil.encrypt("HB") %>" class="col">
			   <c:if test="${stype == 'HB' }"><b>HB</b></c:if>
               <c:if test="${stype != 'HB' }">HB</c:if>	
			   </a>
            </li>
            <li>
               <a href="${ctx}/qtgame/index?stype=<%=AESUtil.encrypt("LB") %>" class="col">
               <c:if test="${stype == 'LB' }"><b>LB</b></c:if>
               <c:if test="${stype != 'LB' }">LB</c:if>
               </a>
            </li>
            <li>
               <a href="${ctx}/qtgame/index?stype=<%=AESUtil.encrypt("NG") %>" class="col">
               <c:if test="${stype == 'NG' }"><b>NG</b></c:if>
               <c:if test="${stype != 'NG' }">NG</c:if>
               </a>
            </li>
            <li>
               <a href="${ctx}/qtgame/index?stype=<%=AESUtil.encrypt("QS") %>" class="col">
			   <c:if test="${stype == 'QS' }"><b>QS</b></c:if>
               <c:if test="${stype != 'QS' }">QS</c:if>	
			   </a>
            </li>
            
         </ul> 
      </div>
       <div class="nav_r">
          <ul>
             <li class="l_china">
                <a href="#">CN</a>
             </li>
             <li class="l_english"><a href="#">EN</a></li>
             
          </ul>
       </div>
      
      
    </div>
</div>