<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsps/h5/layout/header.jsp" %>
<%@include file="/WEB-INF/jsps/h5/layout/sidebar.jsp" %>

<!-- content start -->
<div class="admin-content">
  <div class="admin-content-body">
    <div class="am-cf am-padding am-padding-bottom-0">
      <div class="am-fl am-cf">
        <strong class="am-text-primary am-text-lg">用户管理</strong> / <small>${loginaccount}</small>
      </div>
    </div>

    <hr>

    <div class="am-tabs am-margin" data-am-tabs>
    <input type="hidden" id="employeecode" value="${employeecode}"/>
      <ul class="am-tabs-nav am-nav am-nav-tabs">
        <c:if test="${sessionScope.ERCM_USER_SESSION.employeetypecode == 'T005'}">
        <li class=""><a href="#tab3">转分</a></li>
        </c:if>
      </ul>

      <div class="am-tabs-bd">

    <c:if test="${sessionScope.ERCM_USER_SESSION.employeetypecode == 'T005'}">
        <div class="am-tab-panel am-fade am-in am-active" id="tab3">
        <form id="pointTransfer" action="">
        <input type="hidden" name="sign" value="${sign}"/>

          <div class="am-g am-margin-top">
            <div class="am-u-sm-4 am-u-md-2 am-text-right">用户账户</div>
            <div class="am-u-sm-8 am-u-md-10">
                 <input type="text" name="loginaccout" value="${loginaccount}" class="am-input" disabled="disabled" readonly="readonly" />
            </div>
          </div>

          <div class="am-g am-margin-top">
            <div class="am-u-sm-4 am-u-md-2 am-text-right">转入转出</div>
            <div class="am-u-sm-8 am-u-md-10">
              <select name="opreate" data-am-selected="{btnSize: 'sm'}">
                <option value="IN">转入</option>
                <option value="OUT">转出</option>
              </select>
            </div>
          </div>

          <div class="am-g am-margin-top">
            <div class="am-u-sm-4 am-u-md-2 am-text-right">转移金额</div>
            <div class="am-u-sm-8 am-u-md-10">
              <input type="number" name="money" class="am-input" placeholder="请输入金额" required/>
            </div>
          </div>

          <div class="am-g am-margin-top">
          <center>
            <button id="pointTransferBtn" type="submit" class="am-btn am-btn-primary"><i id="pointTransferIcon" class="am-icon am-icon-check"></i> 确 认 转 移 </button>
          </center>
          </div>

        </form>
        </div>
    </c:if>

      </div>
    </div>

    <div class="am-margin">
      <button id="return" type="button" class="am-btn am-btn-success">返回列表</button>
    </div>
    
    
  </div>
</div>
<!-- content end -->
<!-- 下面这个div结尾要保留，开始在sidebar里面 -->
</div>

<script type="text/javascript">
$(function(){
	loadUserBalance();
});

function loadUserBalance() {
	$.ajax({
		type: "post",
		url : "${ctx}/UserBalanceTransfer/balanceAll",
		data: {"employeecode": $("#employeecode").val()},
		dataType:"json",
		success : function(data){
			if(data.status == 1){
				// 请求成功后，将原来的元素清空
				$("#userBanlance").html("");
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
				$("#userBanlance").append("<tr><th colspan=\"" + colCount +"\" style=\"background-color: grey; height: 30px; text-align: right;\"><a id=\"refresh\" href=\"#\" class=\"am-icon am-icon-refresh\" style=\"color : white;\">　</a></th></tr>");
				$("#userBanlance").append(tr);
				$("#userBanlance").append("<tr><td colspan=\"" + colCount + "\" align=\"center\">" + transferForm + "</td></tr>");
				
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
}
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
		loadUserBalance();
	});

	// 进行转分
	$("#transferForm").submit(function() {
		var employeecode = $("#employeecode").val();
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
					loadUserBalance();
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
$("#return").click(function(){
	location.href = "${ctx}/h5/userList";
});

$("#pointTransfer").submit(function(){
	console.log('准备好我要开始转分了！');
	var money = $("input[name='money']").val();
	if ($.trim(money) == '') return false;
	
	$.ajax({
		url  : "${ctx}/CreditAgent/shifintergral",
		type : "post",
		data : $("#pointTransfer").serialize(),
		dataType:"json",
		beforeSend : function() {
			$("#pointTransferBtn").attr("disabled", true);
			$("#pointTransferIcon").removeClass("am-icon-check");
			$("#pointTransferIcon").addClass("am-icon-spinner am-icon-spin");
		},
		success:function(data){
			if(1 == data.status){
				alert(data.message,'success');
			}else {
				alert(data.message,'error');
			}
		},
		error : function() {
			alert('系统错误，请联系管理员!');
		},
		complete : function() {
			$("#pointTransferBtn").attr("disabled", false);
			$("#pointTransferIcon").removeClass("am-icon-spinner am-icon-spin");
			$("#pointTransferIcon").addClass("am-icon-check");
		}
	});
	// 阻止页面跳转
	return false;
});
</script>

<%@include file="/WEB-INF/jsps/h5/layout/footer.jsp" %>