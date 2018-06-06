<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsps/h5/layout/header.jsp" %>
<%@include file="/WEB-INF/jsps/h5/layout/sidebar.jsp" %>

  <!-- content start -->
  <div class="admin-content">
    <div class="admin-content-body">
      <div class="am-cf am-padding">
        <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">首页</strong> / <small>团队总览</small></div>
      </div>
	<!-- 团队统计数据 start -->
      <ul class="am-avg-md-4 am-margin am-padding am-text-center admin-content-list ">
        <li>
        	<span class="am-icon-btn am-icon-sellsy"></span><br/>
        	<b>团队输赢</b><br/>
        	<font id="losewin" style="color:red; font-weight:bold; font-size:22px;">${losewin}</font>
        </li>
        <li>
        	<span class="am-icon-btn am-icon-bank"></span><br/>
        	<b>充值总额</b><br/>
        	<font id="savemoney" style="color:red; font-weight:bold; font-size:22px;">${savemoney}</font>
        </li>
        <li>
        	<span class="am-icon-btn am-icon-cubes"></span><br/>
        	<b>取款总额</b><br/>
        	<font id="takemoney" style="color:red; font-weight:bold; font-size:22px;">${takemoney}</font>
        </li>
        <li>
        	<span class="am-icon-btn am-icon-dollar"></span><br/>
        	<b>团队余额</b><br/>
        	<font id="balance" style="color:red; font-weight:bold; font-size:22px;">${balance}</font>
        </li>
        <li>
        	<span class="am-icon-btn am-icon-plus-square"></span><br/>
        	<b>冲正统计</b><br/>
        	<font id="integralPlus" style="color:red; font-weight:bold; font-size:22px;">${integralPlus}</font>
        </li>
        <li>
        	<span class="am-icon-btn am-icon-minus-square"></span><br/>
        	<b>冲负统计</b><br/>
        	<font id="integralMinus" style="color:red; font-weight:bold; font-size:22px;">${integralMinus}</font>
        </li>
        <li>
        	<span class="am-icon-btn am-icon-shopping-cart"></span><br/>
        	<b>优惠统计</b><br/>
        	<font id="discountAmount" style="color:red; font-weight:bold; font-size:22px;">${discountAmount}</font>
        </li>
        <li>
        	<span class="am-icon-btn am-icon-balance-scale"></span><br/>
        	<b>洗码统计</b><br/>
        	<font id="washCode" style="color:red; font-weight:bold; font-size:22px;">${washCode}</font>
        </li>
        <li>
        <!-- 占位，用于格式完整 -->
        </li>
      </ul>
	<!-- 团队统计数据 end -->
	
	<!-- 此处可以加些其他数据 start -->
	
	<!-- 此处可以加些其他数据 end -->


    </div>
  </div>
  <!-- content end -->
<!-- 下面这个div结尾要保留，开始在sidebar里面 -->
</div>

<%@include file="/WEB-INF/jsps/h5/layout/footer.jsp" %>