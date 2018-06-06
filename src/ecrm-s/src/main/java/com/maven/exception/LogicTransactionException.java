package com.maven.exception;
/**
 * 逻辑事物异常，不回滚
 * @author Administrator
 *
 */
public class LogicTransactionException extends Exception{

	private static final long serialVersionUID = 1L;


	public LogicTransactionException(){}
	
	public LogicTransactionException(String message){
		super(message);
	}
	
}
