package com.hy.pull.common.util.api;

import com.maven.util.JSONUnit;

public enum Enum_MSG {

	/** --------返回接口错误-------- */
	成功("0","成功"),
	失败("-1","失败"),
	账号已存在("100","账号已存在"),
	
	系统错误("1000","系统错误，请检查系统日志"),
	接口调用错误("1001","接口调用错误"),
	参数错误("1002","参数错误"),
	逻辑事务异常("1003","逻辑事务异常"),
	不支持的接口("1004","不支持的接口"),
	
	/** --------PT 详细错误-------- */
	用户长度不能超过30位("10000","用户长度不能超过30位"),
	用户密码必须是8位数的数字("10001","用户密码必须是8位数的数字"),
	缺少必须的参数("10002","缺少必须的参数，请检查"),
	
	
	;
	
	private String code;
	public String desc;
	
    private Enum_MSG(String _code ,String _desc) {
    	this.code = _code;
    	this.desc = _desc;
    }
    public String message(String _code ,String _desc){
    	return "{\"code\":\""+_code +"\",\"info\":\""+ _desc +"\"}";
    }
    public String message(Object object){
    	if(object==null){
    		return "{\"code\":\""+this.code +"\",\"info\":\""+ this.desc +"\"}";
    	}else if(object instanceof String){
    		return "{\"code\":\""+this.code +"\",\"info\":\""+ object.toString() +"\"}";
    	}else{
    		return "{\"code\":\""+this.code +"\",\"info\":"+ JSONUnit.getJSONString(object)+"}";
    	}
    }
    
}
