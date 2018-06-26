package com.hy.pull.common.util.api;

/**
 * 参数验证异常
 * @author Administrator
 *
 */
public class ArgumentValidationException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public ArgumentValidationException(){
		super();
	}
	
	public ArgumentValidationException(String message){
		super(message);
	}
	

}
