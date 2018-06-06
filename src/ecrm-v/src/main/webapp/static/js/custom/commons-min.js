$(function() {
	// 查询条件布局
	if ($(".toggle-display").size() > 0) {
		$("#btnSearch")
				.after(
						"<a href='javascript:void(0)' id='searchdetail' style='vertical-align:bottom;text-decoration:underline;color:red;'>更多条件</a>");
		$("#searchdetail")
				.toggle(
						function() {
							$(".toggle-display").show().find(
									"input[type='text']:enabled").focus()
									.blur();
							$(this).text("收起条件");
						},
						function() {
							$(".toggle-display")
									.hide()
									.find(
											"input[type='text']:enabled,select option:first-child")
									.val("").attr("selected", "selected");
							$(this).text("更多条件");
						});
	}
});

/**
 * js获取项目根路径，如： http://localhost:8083/uimcardprj
 */
function getRootPath() {
	// 获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
	var curWwwPath = window.document.location.href;
	// 获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	// 获取主机地址，如： http://localhost:8083
	var localhostPaht = curWwwPath.substring(0, pos);
	// 获取带"/"的项目名，如：/uimcardprj
	var projectName = window.top.itempath;
	return (localhostPaht + projectName);
}

/**
 * 获得浏览器信息
 */
function getBrowserVersion() {
	var agent = navigator.userAgent.toLowerCase();
	var regStr_ie = /msie [\d.]+/gi;
	var regStr_ie11 = /(trident.*rv:)([\w.]+)/gi;
	var regStr_ff = /firefox\/[\d.]+/gi;
	var regStr_chrome = /chrome\/[\d.]+/gi;
	var regStr_saf = /safari\/[\d.]+/gi;
	// IE 6 7 8 9 10
	if (agent.indexOf("msie") > 0) {
		return agent.match(regStr_ie);
	}
	// IE 11
	if (agent.indexOf("trident") > 0) {
		var ie11 = agent.match(regStr_ie11).toString();
		return "IE:" + ie11.substr(ie11.indexOf(":") + 1);
	}
	// firefox
	if (agent.indexOf("firefox") > 0) {
		return agent.match(regStr_ff);
	}
	// Chrome
	if (agent.indexOf("chrome") > 0) {
		return agent.match(regStr_chrome);
	}
	// Safari
	if (agent.indexOf("safari") > 0 && agent.indexOf("chrome") < 0) {
		return agent.match(regStr_saf);
	}
}

/**
 * 得到服务器保存图片的路径
 */
function getImgPath(imgname) {
	return getRootPath() + "/upload/" + imgname;
}

/** 账户总览 */
function openAccount(employeecode, loginaccount) {
	openNewWindow('settlement', getRootPath()
			+ '/EEmployee/account?employeecode=' + employeecode, '['
			+ loginaccount + ']账户总览');
}

/**
 * 在框架内打开新窗口
 * 
 * @param id
 * @param url
 * @param title
 */
function openNewWindow(id, url, title) {
	if (top.topManager) {
		top.topManager.openPage({
			id : id,
			href : url,
			title : title,
		});
	}
}
/** 打开授权页面 */
function permission(params) {
	openNewWindow('userpermission', getRootPath() + '/EmployeeMPG/permission?' + params, '权限管理');
}
/** 打开授权页面 */
function agentpermission(params) {
	openNewWindow('userpermission', getRootPath() + '/EmployeeMPG/agentpermission?' + params, '权限管理');
}
/** 结算配置 */
function settlement(employeecode) {
	openNewWindow('settlement', getRootPath()
			+ '/GCBonus/setting?employeecode=' + employeecode, '结算配置');
}
/**
 * 将Map对象封装成URL参数
 * 
 * @param params
 * @returns
 */
function getParams(params) {
	if (params) {
		var str = "?";
		$.each(params, function(i, param) {
			str = str + i + "=" + param + "&";
		});
		return str.substring(0, str.length - 1);
	}
	return "";
}


function getDate(dates) {
	var dd = new Date();
	dd.setDate(dd.getDate() + dates);
	var y = dd.getFullYear();
	var m = dd.getMonth() + 1;
	var d = dd.getDate();

	return y + "-" + m + "-" + d;
}

function getMonday() {
	var d = new Date();
	var year = d.getFullYear();
	var month = d.getMonth() + 1;
	var date = d.getDate();

	// 周
	var day = d.getDay();
	var monday = day != 0 ? day - 1 : 6; // 本周一与当前日期相差的天数

	return monday;
}

function getMonth(type, months) {
	var d = new Date();
	var year = d.getFullYear();
	var month = d.getMonth() + 1;

	if (months != 0) {
		// 如果本月为12月，年份加1，月份为1，否则月份加1。
		if (month == 12 && months > 0) {
			year++;
			month = 1;
		} else if (month == 1 && months < 0) {
			year--;
			month = 12;
		} else {
			month = month + months;
		}
	}
	var date = d.getDate();
	var firstday = year + "-" + month + "-" + 1;
	var lastday = "";
	if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
			|| month == 10 || month == 12) {
		lastday = year + "-" + month + "-" + 31;
	} else if (month == 2) {
		// 判断是否为闰年（能被4整除且不能被100整除 或 能被100整除且能被400整除）
		if ((year % 4 == 0 && year0 != 0) || (year0 == 0 && year0 == 0)) {
			lastday = year + "-" + month + "-" + 29;
		} else {
			lastday = year + "-" + month + "-" + 28;
		}
	} else {
		lastday = year + "-" + month + "-" + 30;
	}
	var day = "";
	if (type == "s") {
		day = firstday;
	} else {
		day = lastday;
	}
	return day;
}

function srightBuiTip(id, title, mtop_px){
	BUI.use('bui/tooltip',function (Tooltip) {
		var t2 = new Tooltip.Tip({
		       trigger : id,
		       alignType : 'right',
		       offset : 10,
		       title : title,
		       elCls : 'tips tips-success',
		       titleTpl : '<span class="x-icon x-icon-small x-icon-success" style="margin-top:'+(mtop_px==undefined?30:mtop_px)+'px;"><i class="icon icon-white icon-question"></i></span>\
		       <div class="tips-content" style="margin-top:'+(mtop_px==undefined?30:mtop_px)+'px;">{title}</div>'
		   });
		t2.render();
    });
}

function getParamFormUrl(name){
	if (window == null || window.location == null){
		return '';
	}
	var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i'); 
	var r = window.location.search.substr(1).match(reg);
	if (r != null) { return unescape(r[2])==undefined?'':unescape(r[2]); }
	return '';
}

