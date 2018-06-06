<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsps/h5/layout/header.jsp" %>
<%@include file="/WEB-INF/jsps/h5/layout/sidebar.jsp" %>

  <!-- content start -->
<div class="admin-content">
  <div class="admin-content-body">
    <div class="am-cf am-padding am-padding-bottom-0">
      <div class="am-fl am-cf">
        <strong class="am-text-primary am-text-lg">财务管理</strong> / <small>额度转换</small>
      </div>
    </div>

    <hr>

  <form id="searchForm" action="" method="post" class="am-form" data-am-validator>
    <div class="am-g">
      <div class="am-u-sm-12 am-u-md-3">
        <div class="am-input-group am-input-group-sm">
          <input type="text" name="loginaccount" placeholder="用户账号" value="${params.loginaccount}" class="am-form-field" required/>
          <input type="hidden" name="employeecode" required/>
          <span class="am-input-group-btn">
            <button class="am-btn am-btn-default" type="submit">搜索</button>
          </span>
        </div>
      <div id="errMsg" class="am-alert-sm am-alert-danger" hidden>&nbsp;输&nbsp;入&nbsp;账&nbsp;号&nbsp;不&nbsp;正&nbsp;确&nbsp;</div>
      </div>
    </div>
  </form>
  
  <hr/>
  
  <div class="am-tab-panel am-fade am-in am-active">
  <center>
    <table border="1" style="width: 400px; border: 1px solid grey; font-size: 13px; text-align: center; vertical-align: middle;">
      <tbody>
				
      </tbody>
    </table>
  </center>
  </div>
  
  </div>
  <!-- content end -->
<!-- 下面这个div结尾要保留，开始在sidebar里面 -->
</div>

<script type="text/javascript">

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

	$("#searchForm").submit(function(){
		var loginaccount = $.trim($("input[name='loginaccount']").val());
		var employeecode = $.trim($("input[name='employeecode']").val());
		if (loginaccount == '' || employeecode == '') return;
		
		$.ajax({
			type: "post",
			url : "${ctx}/UserBalanceTransfer/balanceAll",
			data: {"employeecode": employeecode},
			dataType:"json",
			success : function(data){
				if(data.status == 1){
					// 请求成功后，将原来的元素清空
					$("tbody").html("");
					var mapList = data.dataList;
					// 设置每行显示列数
					var colCount = mapList.length >= 3 ? 3 : mapList.length;
					var td = "";
					var tr = "";
					var transferForm = "<form id=\"transferForm\" method=\"post\"><table style=\"font-size: 13px; text-align: center; vertical-align: middle;\"><tr><td style=\"border: 5px solid transparent;\"><label>转出：</label><select name=\"from\" style=\"width: 150px; \"></select></td></tr><tr><td style=\"border: 5px solid transparent;\"><label>转入：</label><select name=\"to\" style=\"width: 150px; \"></select></td></tr><tr><td style=\"border: 5px solid transparent;\"><label>金额：</label><input name=\"amount\" type=\"number\" style=\"width: 150px; \" required/></td></tr><tr><td style=\"border: 5px solid transparent; text-align: center;\"><button id=\"transferBtn\" type=\"submit\" class=\"am-btn am-btn-primary am-btn-sm\"><i id=\"transferIcon\" class=\"am-icon am-icon-check\"></i> 确 认 转 账 </button></td></tr></table></form>";
					// 总行数
					var rowCount = Math.floor(mapList.length/colCount);
					if((mapList.length%colCount) != 0) rowCount++;
					for (var i = 0; i < mapList.length; i++) {
						
						td += "<td><span style=\"font-weight: bold;\">" + mapList[i].gamename + "</span><span style=\"color: red\"><p>" + mapList[i].gamebalance + "</p></span></td>"
						
						if((i+1)%colCount==0) {
							tr += "<tr>" + td + "</tr>";
							td = "";
						}
						if((i+1) == mapList.length && (mapList.length%colCount) != 0 && rowCount > 1) {
							for (var j = 0; j < (colCount-((i+1)%colCount)); j++) {
								td += "<td><span style=\"font-weight: bold;\"></span><span style=\"color: red\"><p></p></span></td>";	
							}
							tr += "<tr>" + td + "</tr>";
						}
					}
					$("tbody").append("<tr><th colspan=\"" + colCount +"\" style=\"background-color: grey; height: 30px; text-align: right;\"><a id=\"refresh\" href=\"#\" class=\"am-icon am-icon-refresh\" style=\"color : white;\">　</a></th></tr>");
					$("tbody").append(tr);
					$("tbody").append("<tr><td colspan=\"" + colCount + "\" align=\"center\">" + transferForm + "</td></tr>");
					
					// 初始化相关元素
					initialEvent(mapList);
				} else {
					alert(data.msg);
				}
			},
			error:function(){
				alert('系统错误，请联系管理员!');
			}
		});
		// 阻止页面刷新
		return false;
	});
	
	// 初始化新加载元素的事件
	function initialEvent(GameList) {
		var option = "";
		for (var i = 0; i < GameList.length; i++) {
			option += "<option value=\"" + GameList[i].gametype + "\">" + GameList[i].gamename + "</option>";
		}
		$("select[name='from']").append(option);
		$("select[name='to']").append(option);
		
		$("select[name='from'] option:first").attr("selected","selected");
		$("select[name='to'] option:eq(1)").attr("selected","selected");
		
		$("select[name='from']").change(function(){
			if ($("select[name='from'] option:first").attr("selected")=='selected' &&
					$("select[name='to'] option:first").attr("selected") == 'selected') {
				
				$("select[name='to'] option:eq(1)").attr("selected","selected");
				
			} else if ($("select[name='from'] option:first").attr("selected") != 'selected' &&
					$("select[name='to'] option:first").attr("selected") != 'selected') {
				
				$("select[name='to'] option:first").attr("selected","selected");
			}
		});
		$("select[name='to']").change(function(){
			if ($("select[name='to'] option:first").attr("selected") == 'selected' &&
					$("select[name='from'] option:first").attr("selected")=='selected') {
				
				$("select[name='from'] option:eq(1)").attr("selected","selected");
				
			} else if ($("select[name='to'] option:first").attr("selected") != 'selected' &&
					$("select[name='from'] option:first").attr("selected") != 'selected') {
				
				$("select[name='from'] option:first").attr("selected","selected");
			}
			
		});
		
		$("input[name='amount']").attr("required", true);
		$("input[name='amount']").attr("placeholder", "请输入转账金额");
		
		// 刷新按钮，重新加载
		$("#refresh").click(function(){
			$("#searchForm").submit();
		});

		// 进行转分
		$("#transferForm").submit(function() {
			var employeecode = $("input[name='employeecode']").val();
			var from = $("select[name='from']").val();
			var to = $("select[name='to']").val();
			var amount = $("input[name='amount']").val();
			if (amount.trim().length == 0) {
				alert('请输入转账金额!');
				return false;
			}
			var reg = new RegExp("^([1-9][0-9]*)$");
			if (!reg.test(amount)) {
				alert('请输入正整数!');
				return false;
			}
			
			$.ajax({
				type : "post",
				url : "${ctx}/UserBalanceTransfer/balanceTransfer",
				data : {"employeecode" : employeecode, "from" : from, "to" : to, "amount" : amount},
				dataType : "json",
				beforeSend : function() {
					$("#transferBtn").attr("disabled", true);
					$("#transferIcon").removeClass("am-icon-check");
					$("#transferIcon").addClass("am-icon-spinner am-icon-spin");
				},
				success : function(data) {
					if (data.status == 1) {
						alert(data.message);
						$("#searchForm").submit();
					} else {
						alert(data.message);
					}
				},
				error : function() {
					alert('系统错误，请联系管理员!');
				},
				complete : function() {
					$("#transferBtn").attr("disabled", false);
					$("#transferIcon").removeClass("am-icon-spinner am-icon-spin");
					$("#transferIcon").addClass("am-icon-check");
				}
			});
			// 阻止页面刷新
			return false;
		});
	}
</script>
<%@include file="/WEB-INF/jsps/h5/layout/footer.jsp" %>