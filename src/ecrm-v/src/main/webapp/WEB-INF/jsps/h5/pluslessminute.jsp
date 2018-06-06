<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsps/h5/layout/header.jsp" %>
<%@include file="/WEB-INF/jsps/h5/layout/sidebar.jsp" %>

<!-- content start -->
<div class="admin-content">
  <div class="admin-content-body">
    <div class="am-cf am-padding am-padding-bottom-0">
      <div class="am-fl am-cf">
        <strong class="am-text-primary am-text-lg">财务管理</strong> / <small>冲正冲负</small>
      </div>
    </div>

    <hr>

  <form action="" method="post" class="am-form" data-am-validator>
    <div class="am-tabs am-margin" data-am-tabs>
      <ul class="am-tabs-nav am-nav am-nav-tabs">
        <li class="am-active"><a href="#tab1">冲正冲负</a></li>
      </ul>

      <div class="am-tabs-bd">
      
        <div class="am-tab-panel am-fade am-in am-active" id="tab1">
          <div class="am-g am-margin-top">
            <div class="am-u-sm-4 am-u-md-2 am-text-right">充值类型</div>
            <div class="am-u-sm-8 am-u-md-10">
              <select name="plusLess" required>
                    <option value="">--请选择--</option>
                    <option value="1">冲正</option>
                    <option value="2">冲负</option>
              </select>
            </div>
          </div>
          
          <div class="am-g am-margin-top">
            <div class="am-u-sm-4 am-u-md-2 am-text-right">类型描述</div>
            <div class="am-u-sm-8 am-u-md-10">
              <select name="moneyaddtype" required>
                  <option value="">--请选择--</option>
                  <c:forEach var="item" items="${moneyaddtypes}" varStatus="i">
                    <option value="${item.value}">${item.desc}</option>
                  </c:forEach>
              </select>
              <input type="hidden" id="moneyaddtypeName" name="moneyaddtypeName" required/>
            </div>
          </div>

          <div class="am-g am-margin-top">
            <div class="am-u-sm-4 am-u-md-2 am-text-right">用户账号</div>
            <div class="am-u-sm-8 am-u-md-10">
                 <input type="text" name="loginaccount" class="am-input" placeholder="请输入用户账号"required/>
                 <input type="hidden" name="employeecode" required/>
                 <div id="errMsg" class="am-alert-sm am-alert-danger" hidden>&nbsp;输&nbsp;入&nbsp;账&nbsp;号&nbsp;不&nbsp;正&nbsp;确&nbsp;</div>
            </div>
          </div>

          <div class="am-g am-margin-top">
            <div class="am-u-sm-4 am-u-md-2 am-text-right">充值金额</div>
            <div class="am-u-sm-8 am-u-md-10">
                 <input type="number" name="money" class="am-input" placeholder="请输入金额" required/>
            </div>
          </div>

          <div class="am-g am-margin-top">
            <div class="am-u-sm-4 am-u-md-2 am-text-right">流水倍数</div>
            <div class="am-u-sm-8 am-u-md-10">
                 <input type="number" name="lsbs" class="am-input" placeholder="0-50倍，默认为0"/>
            </div>
          </div>

          <div class="am-g am-margin-top">
            <div class="am-u-sm-4 am-u-md-2 am-text-right">备注信息</div>
            <div class="am-u-sm-8 am-u-md-10">
                 <textarea name="remark" rows="3" cols="20" placeholder="请输入备注信息"></textarea>
            </div>
          </div>

        </div>

      </div>
    </div>
    <div class="am-margin">
      <button type="submit" class="am-btn am-btn-primary">提交</button>
    </div>
  </form>
  </div>
<!-- content end -->
<!-- 下面这个div结尾要保留，开始在sidebar里面 -->
</div>

<script type="text/javascript">
	// 加载类型描述名称
	$("select[name='moneyaddtype']").change(function() {
		$("#moneyaddtypeName").val($(this).find("option:selected").text());
	});

	// 验证用户是否存在
	$("form").validator({
		validate : function(validity) {
			if ($(validity.field).is("input[name='loginaccount']")) {
				$("#errMsg").hide();
				var loginaccount = $("input[name='loginaccount']").val();
				return $.ajax({
					url  : '${ctx}/common/employeeIsExist',
					data : {
						'loginaccount' : loginaccount
					},
					dataType : 'json',
					success  : function(data) {
						if (data.dont_exist) {
							$("#errMsg").show();
							$("input[name='loginaccount']").removeClass("am-active am-field-valid");
							$("input[name='loginaccount']").addClass("am-field-error am-active");
							validity.valid = false;
							return validity;
						} else {
							$("input[name='employeecode']").val(data.exist);
							$("input[name='loginaccount']").removeClass("am-field-error am-active");
							$("input[name='loginaccount']").addClass("am-active am-field-valid");
							return validity;
						}
					}
				});
			}
		}
	});
	
	$("form").submit(function() {
		if ($.trim($("select[name='plusLess']").val()) == '') {console.log('plusLess为空!'); return false;}
		if ($.trim($("select[name='moneyaddtype']").val()) == '') {console.log('moneyaddtype为空!'); return false;}
		if ($.trim($("input[name='moneyaddtypeName']").val()) == '') {console.log('moneyaddtypeName为空!'); return false;}
		if ($.trim($("input[name='loginaccount']").val()) == '') {console.log('loginaccount为空!'); return false;}
		if ($.trim($("input[name='employeecode']").val()) == '') {console.log('employeecode为空!'); return false;}
		if ($.trim($("input[name='money']").val()) == '') {console.log('money为空!'); return false;}
		$.ajax({
			type : 'post',
			url  : '${ctx}/h5/savePlusLessMinute',
			data : $("form").serialize(),
			dataType : 'json',
			success  : function(data) {
				if (data.status == 1) {
					alert(data.message);
					location.reload();
				} else {
					console.log('操作失败: ' + data.message);
					alert(data.message);
				}
			}
		});
	});
</script>

<%@include file="/WEB-INF/jsps/h5/layout/footer.jsp" %>