<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<c:set var="ctx" value="<%=request.getContextPath()%>" />
<c:set var="statics" value="${ctx}/static"></c:set>
<%@ page import="com.maven.utils.AESUtil"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<div class="top">
    <div class="top_cen">
      <div class="logo"><img src="${statics }/pt/images/logo.png"></div>
      <div class="nav_l">
         <ul>
		    <li>
               <a href="${ctx}/mggame/index?stype=<%=AESUtil.encrypt("最新游戏") %>" class="col">
               <c:if test="${stype == '最新游戏' }"><b>最新游戏</b></c:if>
               <c:if test="${stype != '最新游戏' }">最新游戏</c:if>	
               </a>
            </li>
            <li>
               <a href="${ctx}/mggame/index?stype=<%=AESUtil.encrypt("老虎机") %>" class="col">
               <c:if test="${stype == '老虎机' }"><b>老虎机</b></c:if>
               <c:if test="${stype != '老虎机' }">老虎机</c:if>	
               </a>
            </li>
            <li>
               <a href="${ctx}/mggame/index?stype=<%=AESUtil.encrypt("桌面游戏") %>" class="col">
               <c:if test="${stype == '桌面游戏' }"><b>桌面游戏</b></c:if>
               <c:if test="${stype != '桌面游戏' }">桌面游戏</c:if>	
               </a>
            </li>
            <li>
               <a href="${ctx}/mggame/index?stype=<%=AESUtil.encrypt("视频扑克") %>" class="col">
               <c:if test="${stype == '视频扑克' }"><b>视频扑克</b></c:if>
               <c:if test="${stype != '视频扑克' }">视频扑克</c:if>	
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