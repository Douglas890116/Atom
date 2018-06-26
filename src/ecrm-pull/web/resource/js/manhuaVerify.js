//验证元素是空为空
function empty(obj){
	var $obj = $("#"+obj);	
	if($.isEmptyObject($obj.val())){
		alert($obj.attr("emsg"));
		$obj.focus();
		return false;
	}
	return true;
}

//验证元素是否为数字类型
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