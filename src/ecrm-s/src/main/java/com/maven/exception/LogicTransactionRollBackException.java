package com.maven.exception;

/**
 * 逻辑事物异常,回滚
 * @author Administrator
 *
 */
public class LogicTransactionRollBackException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public LogicTransactionRollBackException(){
		super();
	}
	
	public LogicTransactionRollBackException(String message){
		super(message);
	}

}
