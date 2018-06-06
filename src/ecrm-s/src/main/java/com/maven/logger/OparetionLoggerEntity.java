package com.maven.logger;

import java.util.List;

import com.maven.entity.LogOperation;
import com.maven.entity.LogOperationDetail;

public class OparetionLoggerEntity {
	
	private LogOperation logOperation;
	
	private List<LogOperationDetail> logOperationDetail;

	public OparetionLoggerEntity(LogOperation logOperation,List<LogOperationDetail> logOperationDetail){
		this.logOperation = logOperation;
		this.logOperationDetail = logOperationDetail;
	}
	
	public LogOperation getLogOperation() {
		return logOperation;
	}

	public void setLogOperation(LogOperation logOperation) {
		this.logOperation = logOperation;
	}

	public List<LogOperationDetail> getLogOperationDetail() {
		return logOperationDetail;
	}

	public void setLogOperationDetail(List<LogOperationDetail> logOperationDetail) {
		this.logOperationDetail = logOperationDetail;
	}

}
