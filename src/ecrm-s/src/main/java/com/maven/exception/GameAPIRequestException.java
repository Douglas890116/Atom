package com.maven.exception;

public class GameAPIRequestException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public GameAPIRequestException(){
		super();
	}
	
	public GameAPIRequestException(String message){
		super(message);
	}
}
