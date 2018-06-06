package com.maven.logger;

import com.maven.entity.LogOperation;

public class ThreadOparetionLogger {
	
	private static ThreadLocal<LogOperation> operation = new ThreadLocal<LogOperation>();

	public static LogOperation getOperation() {
		return operation.get();
	}
	
	public static void setOperation(LogOperation log) {
		operation.set(log);
	}

}
