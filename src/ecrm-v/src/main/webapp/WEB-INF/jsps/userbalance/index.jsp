<%@page import="com.maven.config.SystemConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/layout/bui-header.jsp"%>
<c:set var="isEnglish" value="${sessionScope.LANGUAGE == 'en'}"/>
<body>
	<div class="demo-content" style="margin: 27px;">
		<!-- 表单页 ================================================== -->
		<div class="row">
			<div>
				<c:choose>
				  <c:when test="${sessionScope.LANGUAGE == 'en'}"><h2>${requestScope.MENUS.parentenglishname}<span class="divider"> / </span>${requestScope.MENUS.englishname}</h2></c:when>
				  <c:otherwise><h2>${requestScope.MENUS.parentmenuname}<span class="divider"> / </span>${requestScope.MENUS.menuname}</h2></c:otherwise>
				</c:choose>
				
				<hr/>
				<form id="searchForm" method="post" aria-pressed="false" aria-disabled="false" class="form-horizontal bui-form bui-form-field-container">
					<div class="control-group span7">
						<label class="control-label">${isEnglish?'Account':'用户账号'}：</label>
						<div class="controls">
							<input name="loginaccount" type="text" data-rules="{required:true}" data-tip="{text:'请输入用户账号'}"/>
							<input name="employeecode" type="hidden" data-rules="{required:true}"/>
						</div>
					</div>
					<div style="position: absolute; top: 94px; left: 280px">
						<button type="submit" class="button button-primary">
							<span class="icon-search icon-white"></span> ${isEnglish?'Search':'搜 索'}
						</button>
					</div>
					
				</form>
			</div>
		</div>
		<hr/>
		<div>
			<table border="1" style="width: 900px; border: 1px solid grey; font-size: 13px; text-align: center; vertical-align: middle;">
				<tbody>
				
				</tbody>
			</table>
		</div>
	</div>
</body>

<script type="text/javascript">
	$(function() {
		BUI.use('custom/commons');
		BUI.use('bui/overlay');
		BUI.use(['bui/mask']);
		var form = new BUI.Form.HForm({
			srcNode : '#searchForm',
		}).render();

		form.getField('loginaccount').set('remote', {
			url : '${ctx}/common/employeeIsExist',
			dataType : 'json',
			callback : function(data) {
				if (data.dont_exist) {
					return data.dont_exist;
				}
				if (data.exist) {
					$("input[name='employeecode']").val(data.exist);
					return '';
				}
			}
		});
	});

	$("#searchForm").submit(function(){
		var obj = BUI.FormHelper.serializeToObject(this);
		var loginaccount = obj.loginaccount;
		var employeecode = obj.employeecode;
		if (employeecode.trim().length == 0
			|| loginaccount.trim().length == 0) return;
		
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
					var transferForm = "<form id=\"transferForm\" method=\"post\"><table style=\"width: 300px; font-size: 13px; text-align: center; vertical-align: middle;\"><tr><td style=\"border: 5px solid transparent;\"><label>${isEnglish?"Transfer Out：":"转出："}</label><select name=\"from\"></select></td></tr><tr><td style=\"border: 5px solid transparent;\"><label>${isEnglish?"Transfer In：&nbsp;&nbsp;&nbsp;":"转入："}</label><select name=\"to\"></select></td></tr><tr><td style=\"border: 5px solid transparent;\"><label>${isEnglish?"Amount：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;":"金额："}</label><input name=\"amount\" type=\"text\"/></td></tr><tr><td style=\"border: 5px solid transparent; text-align: center;\"><button  type=\"submit\" class=\"button button-primary\"><span class=\"icon-ok-sign icon-white\"></span> ${isEnglish?" OK ":"确 认 转 账"} </button></td></tr></table></form>";
					// 总行数
					var rowCount = Math.floor(mapList.length/colCount);
					if((mapList.length%colCount) != 0) rowCount++;
					for (var i = 0; i < mapList.length; i++) {
						
						td += "<td><span style=\"font-weight: bold;\">" + mapList[i].gamename + "</span><span style=\"color: red\"><p>" + mapList[i].gamebalance + "</p></span></td>"
						if((i+1) == colCount){
							//在第一行加入额度转换的表单
							td += "<td width=\"300px\" rowspan=\"" + rowCount + "\" align=\"center\">" + transferForm + "</td>";
						}
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
					$("tbody").append("<tr><th colspan=\"" + (colCount + 1) +"\" style=\"background-color: grey; height: 30px; text-align: right;\"><a id=\"refresh\" href=\"#\"><span class=\"icon-repeat icon-white\"></span>　</a></th></tr>");
					$("tbody").append(tr);
					// 初始化相关元素
					initialEvent(mapList);
				} else {
					BUI.Message.Alert(data.msg,'error');
				}
			},
			error:function(){
				BUI.Message.Alert('${isEnglish?"Sysotem error, please contact the admin":"系统错误，请联系管理员"}!','error');
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
		
		$("input[name='amount']").attr("data-rules", "{required:true, number:true}");
		$("input[name='amount']").attr("placeholder", "请输入转账金额");
		
		// 刷新按钮，重新加载
		$("#refresh").click(function(){
			$("#searchForm").submit();
		});

		BUI.use(['bui/mask'], function(Mask) {
				// 进行转分
				$("#transferForm").submit(function() {
					var obj = BUI.FormHelper.serializeToObject(this);
					var employeecode = $("input[name='employeecode']").val();
					var from = obj.from;
					var to = obj.to;
					var amount = obj.amount;
					if (amount.trim().length == 0) {
						BUI.Message.Alert('请输入转账金额!', 'error');
						return false;
					}
					var reg = new RegExp("^([1-9][0-9]*)$");
					if (!reg.test(amount)) {
						BUI.Message.Alert('请输入正整数!', 'error');
						return false;
					}
					$.ajax({
						type : "post",
						url : "${ctx}/UserBalanceTransfer/balanceTransfer",
						data : {"employeecode" : employeecode, "from" : from, "to" : to, "amount" : amount},
						dataType : "json",
						beforeSend : function () {
							Mask.maskElement('body');
						},
						success : function(data) {
							if (data.status == 1) {
								BUI.Message.Alert(data.message, 'success');
								$("#searchForm").submit();
							} else {
								BUI.Message.Alert(data.message, 'error');
							}
						},
						error : function() {
							BUI.Message.Alert('${isEnglish?"Sysotem error, please contact the admin":"系统错误，请联系管理员"}!', 'error');
						},
						complete : function() {
							Mask.unmaskElement('body');
						}
					});
					// 阻止页面刷新
					return false;
				});
			});
	}
</script>
<!-- script end -->
</html>
