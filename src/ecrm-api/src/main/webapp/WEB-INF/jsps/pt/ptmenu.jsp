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
               <a href="${ctx}/ptgame/ptnew?stype=" class="col">
               <c:if test="${stype == '最新游戏' }"><b>最新游戏</b></c:if>
               <c:if test="${stype != '最新游戏' }">最新游戏</c:if>
               </a>
            </li>
            
            <li>
               <a href="${ctx}/ptgame/ptnew?stype=<%=AESUtil.encrypt("热门游戏") %>" class="col">
               <c:if test="${stype == '热门游戏' }"><b>热门游戏</b></c:if>
               <c:if test="${stype != '热门游戏' }">热门游戏</c:if>
               </a>
            </li>
            
            <li>
               <a href="${ctx}/ptgame/ptnew?stype=<%=AESUtil.encrypt("澳门老虎机") %>" class="col">
               <c:if test="${stype == '澳门老虎机' }"><b>澳门老虎机</b></c:if>
               <c:if test="${stype != '澳门老虎机' }">澳门老虎机</c:if>
               </a>
            </li>
            
            <li>
               <a href="${ctx}/ptgame/ptnew?stype=<%=AESUtil.encrypt("漫威老虎机") %>" class="col">
               <c:if test="${stype == '漫威老虎机' }"><b>漫威老虎机</b></c:if>
               <c:if test="${stype != '漫威老虎机' }">漫威老虎机</c:if>
               </a>
            </li>
            
            <li>
               <a href="${ctx}/ptgame/ptnew?stype=<%=AESUtil.encrypt("累积大奖") %>" class="col">
               <c:if test="${stype == '累积大奖' }"><b>累积大奖</b></c:if>
               <c:if test="${stype != '累积大奖' }">累积大奖</c:if>
               </a>
            </li>
            
            <li>
               <a href="${ctx}/ptgame/ptnew?stype=<%=AESUtil.encrypt("吃角子老虎机") %>" class="col">
               <c:if test="${stype == '吃角子老虎机' }"><b>吃角子老虎机</b></c:if>
               <c:if test="${stype != '吃角子老虎机' }">吃角子老虎机</c:if>
               </a>
            </li>
            <%-- 
            <li>
               <a href="${ctx}/ptgame/ptnew?stype=<%=AESUtil.encrypt("街机游戏") %>" class="col">
               <c:if test="${stype == '街机游戏' }"><b>街机游戏</b></c:if>
               <c:if test="${stype != '街机游戏' }">街机游戏</c:if>
               </a>
            </li>
            
            <li>
               <a href="${ctx}/ptgame/ptnew?stype=<%=AESUtil.encrypt("乐透刮刮乐") %>" class="col">
               <c:if test="${stype == '乐透刮刮乐' }"><b>乐透刮刮乐</b></c:if>
               <c:if test="${stype != '乐透刮刮乐' }">乐透刮刮乐</c:if>
               </a>
            </li>
             --%>
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