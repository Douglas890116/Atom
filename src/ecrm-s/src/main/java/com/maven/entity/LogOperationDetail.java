package com.maven.entity;

public class LogOperationDetail {
	
	/** 详细日志编码 */
    private Integer logdetailcode;
    
    /** 日志编码 */
    private Integer logcode;

    /** 操作字段 */
    private String fieldname;

    /** 操作值 */
    private String fieldvalue;

    /** 操作条件 */
    private String conditions;
    
    public LogOperationDetail(){}
    
    public LogOperationDetail(String fieldname,String fieldvalue,String condition){
    	this.fieldname = fieldname;
    	this.fieldvalue = fieldvalue;
    	this.conditions = condition;
    }

    public Integer getLogdetailcode() {
        return logdetailcode;
    }

    public void setLogdetailcode(Integer logdetailcode) {
        this.logdetailcode = logdetailcode;
    }

    public Integer getLogcode() {
        return logcode;
    }

    public void setLogcode(Integer logcode) {
        this.logcode = logcode;
    }

    public String getFieldname() {
		return fieldname;
	}

	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}

	public String getFieldvalue() {
		return fieldvalue;
	}

	public void setFieldvalue(String fieldvalue) {
		this.fieldvalue = fieldvalue;
	}

	public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }
}