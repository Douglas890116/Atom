/*
 * 获取单条记录的josn对象
 * @param elementId 存储json字符串的隐藏表单ID
 * @param key 比较的Key
 * @param value 比较的值
 * @return obj json对象
 */
function getJsonObject(elementId,key,value) {
	var jsonStr = document.getElementById(elementId).value;//获取Json字符串值
	var json = JSON.parse(jsonStr);//把字符串转成json对象
	var result,obj;
	for (var i = 0; i < json.length; i++) {
		result = json[i];
	    if (result[key] == value) {
	    	obj = result;
	    	break;
	    };
	};
	return obj;
}

/*
 * 获取多选的值列表
 * @return 选择值id列表的参数字符串
 */
function getCheckedValues(){
	var values = document.getElementsByName("answer"); //返回answer的集合 
	var len = values.length;
	if(len > 0){
		var checkedValues = "";
		for ( var i = 0; i < len; i++) {
			if (values[i].checked) {
				checkedValues += ","+values[i].value;
			}
		}
		return checkedValues.substr(1);
	}else{
		return "";
	}			
}

/*
 * 获取多选的个数
 * @param elementName 元素名称
 * @return 选中的数量
 */
function getCheckCount(elementName){
	var values = document.getElementsByName(elementName); //返回elementName的集合 
	var len = values.length;
	var count = 0;
	for ( var i = 0; i < len; i++) {
		if (values[i].checked) {
			count++;
		}
	}
	return count;
}

/*
 * 获取第一个选中的值
 * @param elementName 元素名称
 * @return 选中值
 */
function getCheckVlaue(elementName){
	var values = document.getElementsByName(elementName); //返回elementName的集合 
	var len = values.length;
	var value;
	for ( var i = 0; i < len; i++) {
		if (values[i].checked) {
			value = values[i].value;
			break;
		}
	}
	return value;
}

/*
 * 删除全部选中项
 * @param url 提交的接口链接
 */
function delAll(url) {
	console.log(url);
	var ids = getCheckedValues();
	if (ids == "") {
		alert("请选择要删除的数据！");
	} else {
		if (confirm("您确认真的要删除选中项？此操作不可恢复！")) {	
			$.ajax({
				dataType : "json",
				type : "POST",
				url : url+"?ids=" + ids,
				success : function(data) {	
					alert(data.msg);
					window.location.reload();
				}
			});
		}
	}
}

/*
 * 更新操作(包括添加和更新数据)
 * @param formName 待更新表单名称
 * @param url 提交的接口链接
 */
function insertAndUpadte(formName,url) {
	var data = $("form[name='"+formName+"']").serialize();
	$.ajax({
		dataType : "json",
		type : "POST",
		url : url,
		data : data,
		success : function(item) {
			alert(item.msg);
			window.location.reload();
		}
	});
}

/*
 * 验证元素是空为空
 * @param data 待验证的结果
 * @return (true,false)
 */
function isNull(data){	
	if(data=='undefined' || data==null || data===''){
		return true;
	}else{
		return false;
	}
}

/*
 * 验证元素是空为空
 * @param obj 待验证的表单名称
 * @return 结果(true,false)
 */
function empty(obj){
	var $obj = $("#"+obj);	
	if($.isEmptyObject($obj.val())){
		alert($obj.attr("emsg"));
		$obj.focus();
		return false;
	}
	return true;
}

/*
 * 验证元素是否为数字类型
 * @param obj 待验证的表单名称
 * @return 结果(true,false)
 */
function numeric(obj){
	if (empty(obj)){
		var $obj = $("#"+obj);	
		if(!$.isNumeric($obj.val())){
			alert($obj.attr("nmsg"));
			$obj.focus();
			$obj.select();
			return false;
		}
		return true;
	}else{
		return false;
	}	
}

/*
 * 日期格式化的方法
 * @param format 待转的格式
 * @return 日期字符串
 */
Date.prototype.format = function(format) {

    /*
     * format="yyyy-MM-dd hh:mm:ss";
     */
    var o = {
        "M+" : this.getMonth() + 1,
        "d+" : this.getDate(),
        "h+" : this.getHours(),
        "m+" : this.getMinutes(),
        "s+" : this.getSeconds(),
        "q+" : Math.floor((this.getMonth() + 3) / 3),
        "S" : this.getMilliseconds()
    };
    if (/(y+)/.test(format)) {
            format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4- RegExp.$1.length));
        }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)){
            format = format.replace(RegExp.$1, RegExp.$1.length == 1? o[k]:("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
    
};

/*
 * json日期对象转字符串方法
 * @param objDate 待转的日期对象
 * @param format 待转的格式
 * @return 日期字符串
 */
function toDate(objDate, format) {
    var date = new Date();
    date.setTime(objDate.time);
    date.setHours(objDate.hours);
    date.setMinutes(objDate.minutes);
    date.setSeconds(objDate.seconds);
    return date.format(format);
}