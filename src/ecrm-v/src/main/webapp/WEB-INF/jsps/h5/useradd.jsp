<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.maven.cache.SystemCache"%>
<%@include file="/WEB-INF/jsps/h5/layout/header.jsp" %>
<%@include file="/WEB-INF/jsps/h5/layout/sidebar.jsp" %>

<!-- content start -->
<div class="admin-content">
  <div class="admin-content-body">
    <div class="am-cf am-padding am-padding-bottom-0">
      <div class="am-fl am-cf">
        <strong class="am-text-primary am-text-lg">会员信息</strong> / <small>创建会员</small>
      </div>
    </div>

    <hr>

  <form action="" method="post" class="am-form" data-am-validator>
    <div class="am-tabs am-margin" data-am-tabs>
      <ul class="am-tabs-nav am-nav am-nav-tabs">
        <li class="am-active"><a href="#tab1">会员信息</a></li>
      </ul>

      <div class="am-tabs-bd">
      
        <div class="am-tab-panel am-fade am-in am-active" id="tab1">
        
          <div class="am-g am-margin-top">
            <div class="am-u-sm-4 am-u-md-2 am-text-right">品牌名称</div>
            <div class="am-u-sm-8 am-u-md-10">
              <select name="brandcode" required>
                <option value="">--请选择--</option>
              </select>
            </div>
          </div>
          
          <div class="am-g am-margin-top">
            <div class="am-u-sm-4 am-u-md-2 am-text-right">用户类型</div>
            <div class="am-u-sm-8 am-u-md-10">
              <select name="employeetypecode" required>
                  <option value="T003">会员</option>
              </select>
            </div>
          </div>

          <div class="am-g am-margin-top">
            <div class="am-u-sm-4 am-u-md-2 am-text-right">注册账号</div>
            <div class="am-u-sm-8 am-u-md-10">
              <input type="text" name="loginaccount" class="am-input" placeholder="请输入6-12位用户账号" minlength="6" maxlength="12" required/>
              <div class="am-alert-sm am-alert-danger" hidden></div>
            </div>
          </div>

          <div class="am-g am-margin-top">
            <div class="am-u-sm-4 am-u-md-2 am-text-right">登录密码</div>
            <div class="am-u-sm-8 am-u-md-10">
              <input type="password" id="loginpassword" name="loginpassword" class="am-input" placeholder="请输入6-12位密码" minlength="6" maxlength="12" required/>
              <div class="am-alert-sm am-alert-danger" hidden></div>
            </div>
          </div>

          <div class="am-g am-margin-top">
            <div class="am-u-sm-4 am-u-md-2 am-text-right">确认密码</div>
            <div class="am-u-sm-8 am-u-md-10">
              <input type="password" name="confirmLoginpassword" class="am-input" placeholder="请再次输入密码" data-equal-to="#loginpassword" minlength="6" maxlength="12" required/>
              <div class="am-alert-sm am-alert-danger" hidden></div>
            </div>
          </div>

          <hr/>

          <div class="am-g am-margin-top">
            <div class="am-u-sm-4 am-u-md-2 am-text-right">分红/占成</div>
            <div class="am-u-sm-8 am-u-md-10">
              <select id="modle" name="modle" required>
                <option value="">--请选择--</option>
                <option value="1">分红模式</option>
                <option value="2">占成模式</option>
              </select>
              <div class=" am-text-warning am-text-xs">注：设置后不能转换，并且分红、占成 设置之后只升不降</div>
            </div>
          </div>

          <div id="modle1" class="am-g am-margin-top" hidden>
            <div class="am-u-sm-4 am-u-md-2 am-text-right">分红百分比</div>
            <div class="am-u-sm-4 am-u-md-10 am-text-left">
              <input type="number" name="dividend" class="am-input" placeholder="最高${sessionScope.ERCM_USER_SESSION.dividend*100}" min="0" max="${sessionScope.ERCM_USER_SESSION.dividend*100}" value="0"/>
              <div class="am-alert-sm am-alert-danger" hidden></div>
            </div>
            <div class="am-u-sm-4 am-u-md-3 am-text-left">%</div>
          </div>

          <div id="modle2" class="am-g am-margin-top" hidden>
            <div class="am-u-sm-4 am-u-md-2 am-text-right">占成百分比</div>
            <div class="am-u-sm-4 am-u-md-10 am-text-left">
              <input type="number" name="share" class="am-input" placeholder="最高${sessionScope.ERCM_USER_SESSION.share*100}" min="0" max="${sessionScope.ERCM_USER_SESSION.dividend*100}" value="0"/>
              <div class="am-alert-sm am-alert-danger" hidden></div>
            </div >
            <div class="am-u-sm-4 am-u-md-3 am-text-left">%</div>
          </div>


          <div class="am-g am-margin-top">
            <div class="am-u-sm-4 am-u-md-2 am-text-left"><label>洗码百分比</label></div>
          </div>

          <c:forEach var="game" varStatus="status" items="${games}">
          <div class="am-g am-margin-top" style="clear: both;">
              <div class="am-u-sm-2 am-text-left am-text-danger">${game.gametype.replace("Game","")}</div>
              <c:forEach items="${SystemCache.getInstance().getGameCategory(game.gametype)}" var="category">
              <c:set var="key" scope="page" value="${category.gametype}_${category.categorytype}"/>
              <c:set var="maxbonus" scope="page" value="${bonus.get(key)==null?0:bonus.get(key)*100}"/>
              <div class="am-u-sm-2 am-text-left">${category.categoryname}</div>
              <div class="am-u-sm-2 am-text-left" style="float: left;"><input class="am-input am-fl" type="number" name="${key}" min="${category.minbonus*100}" max="${maxbonus}" value="0"/></div>
              </c:forEach>
          </div>
          </c:forEach>

        </div>


      </div>
    </div>

    <div class="am-margin">
      <button type="submit" class="am-btn am-btn-primary">提交</button>
    </div>

    <div class="am-margin">
      <button id="return" type="button" class="am-btn am-btn-success">返回列表</button>
    </div>

  </form>
  </div>
<!-- content end -->
<!-- 下面这个div结尾要保留，开始在sidebar里面 -->
</div>

<script type="text/javascript">

	window.onload = function loadBrand() {
		console.log('通过方法加载');
		$.ajax({
			url : "${ctx}/common/loadEnterpriseBrand",
			dataType : "json",
			success : function(data) {
				for (var i = 0; i < data.obj.length; i++) {
					$("select[name='brandcode']").append("<option value="+data.obj[i].brandcode+">" + data.obj[i].brandname + "</option>");
				}
			}
		});
	}
	
	var validator = false;
	
	// 验证用户是否存在
	$("form").validator({
		validate : function(validity) {
			if ($(validity.field).is("input[name='loginaccount']")) {
				$("input[name='loginaccount']").next("div").html("").hide();
				var loginaccount = $("input[name='loginaccount']").val();
				if ($.trim(loginaccount) == '') {
					validity.valid = false;
					validator = false;
					return validity;
				} else {
					validator = true;
				}
				var reg = new RegExp("^[0-9a-zA-z]{6,12}$");
				if (!reg.test(loginaccount)) {
					$("input[name='loginaccount']").next("div").html("必须由6-12位字母或数字组成").show();
					$("input[name='loginaccount']").removeClass("am-active am-field-valid");
					$("input[name='loginaccount']").addClass("am-field-error am-active");
					validity.valid = false;
					validator = false;
					return validity;
				} else {
					validator = true;
				}
				return $.ajax({
					url : '${ctx}/common/employeeIsExist',
					data : {'loginaccount' : loginaccount},
					dataType : 'json',
					success : function(data) {
						if (data.exist) {
							$("input[name='loginaccount']").next("div").html("用户名已存在").show();
							$("input[name='loginaccount']").removeClass("am-active am-field-valid");
							$("input[name='loginaccount']").addClass("am-field-error am-active");
							validity.valid = false;
							validator = false;
							return validity;
						} else {
							$("input[name='loginaccount']").removeClass("am-field-error am-active");
							$("input[name='loginaccount']").addClass("am-active am-field-valid");
							validator = true;
							return validity;
						}
					}
				});
			} else if ($(validity.field).is("input[name='loginpassword']")) {
				$("input[name='loginpassword']").next("div").html("").hide();
				var loginpassword = $("input[name='loginpassword']").val();
				if ($.trim(loginpassword) == '') {
					validity.valid = false;
					validator = false;
					return validity;
				} else {
					validator = true;
				}
				var reg2 = new RegExp("[a-z]+");// 验证必须包含小写字母
				var reg3 = new RegExp("[0-9]+");// 验证必须包含数字
				if (!reg2.test(loginpassword) || !reg3.test(loginpassword)) {
					validity.valid = false;
					validator = false;
					$("input[name='loginpassword']").next("div").html("由6-12位小写字母和数字组成").show();
				} else {
					validator = true;
					$("input[name='loginpassword']").next("div").html("").hide();
				}
				return validity;
			} else if ($(validity.field).is("input[name='confirmLoginpassword']")) {
				var confirmLoginpassword = $("input[name='confirmLoginpassword']").val();
				if (confirmLoginpassword != $("input[name='loginpassword']").val()) {
					validity.valid = false;
					validator = false;
				} else {
					validator = true;
				}
					return validity;
			} else if ($(validity.field).is("input[name='dividend']")) {
				var dividend = $("input[name='dividend']").val();
				var max = ${sessionScope.ERCM_USER_SESSION.dividend*100};
				console.log('最大值：' + dividend);
				if (dividend > max) {
					$("input[name='dividend']").next("div").html("输入值不能大于" + max).show();
					$("input[name='dividend']").removeClass("am-active am-field-valid");
					$("input[name='dividend']").addClass("am-field-error am-active");
					validity.valid = false;
					validator = false;
				} else {
					$("input[name='dividend']").next("div").html("").hide();
					$("input[name='dividend']").addClass("am-active am-field-valid");
					$("input[name='dividend']").removeClass("am-field-error am-active");
					validity.valid = true;
					validator = true;
				}
				return validity;
			} else if ($(validity.field).is("input[name='share']")) {
				var share = $("input[name='share']").val();
				var max = ${sessionScope.ERCM_USER_SESSION.share*100};
				console.log('最大值：' + share);
				if (share > max) {
					$("input[name='share']").next("div").html("输入值不能大于" + max).show();
					$("input[name='share']").removeClass("am-active am-field-valid");
					$("input[name='share']").addClass("am-field-error am-active");
					validity.valid = false;
					validator = false;
				} else {
					$("input[name='share']").next("div").html("").hide();
					$("input[name='share']").addClass("am-active am-field-valid");
					$("input[name='share']").removeClass("am-field-error am-active");
					validity.valid = true;
					validator = true;
				}
				return validity;
			}
		}
	});
	
	$("#modle").change(function(){
		$("#modle1").hide();
		$("#modle2").hide();
		
		$("input[name='dividend']").val("0");
		$("input[name='share']").val("0");
		
		$("input[name='dividend']").next("div").html("").hide();
		$("input[name='dividend']").addClass("am-active am-field-valid");
		$("input[name='dividend']").removeClass("am-field-error am-active");

		$("input[name='share']").next("div").html("").hide();
		$("input[name='share']").addClass("am-active am-field-valid");
		$("input[name='share']").removeClass("am-field-error am-active");
		
		var model = $(this).val();
		if (model != '') {
			$("#modle" + model).show();
		}
	});


	$("form").submit(function() {
		if ($.trim($("select[name='brandcode']").val()) == '') return false;
		if ($.trim($("select[name='employeetypecode']").val()) == '') return false;
		if ($.trim($("input[name='loginaccount']").val()) == '') return false;
		if ($.trim($("input[name='loginpassword']").val()) == '') return false;
		if ($.trim($("select[name='modle']").val()) == '') return false;
		if (!validator) return false;
		$.ajax({
			url : "${ctx}/EEmployee/saveEnterpriseEmployee",
			type : "POST",
			data : $('form').serialize(),
			dataType : "json",
			statusCode : {
				404 : function() {
					alert("请求未发送成功");
				}
			},
			success : function(data) {
				if (data.status == "1") {
					location.href = "${ctx}/h5/userList";
				} else {
					alert(data.message);
				}
			}
		});
	});
	$("#return").click(function(){
		location.href = "${ctx}/h5/userList";
	});
</script>

<%@include file="/WEB-INF/jsps/h5/layout/footer.jsp" %>