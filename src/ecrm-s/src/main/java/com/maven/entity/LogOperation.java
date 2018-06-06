package com.maven.entity;

import java.util.Date;

public class LogOperation implements Cloneable {

	/** 日志编码 */
    private Integer logcode;

    /** 数据表名称 */
    private String tablename;

    /** 业务名称 */
    private String servicename;

    /** 访问URL */
    private String visiteurl;

    /** 操作类型 */
    private String oprationtype;

    /** 操作时间 */
    private Date oprationtime;
    
    /** 用户编码 */
    private String employeecode;
    
    /** 用户上级编码 */
    private String parentemployeecode;

    /** 操作人 */
    private String oprationperson;
    
    public LogOperation(){}
    
    public LogOperation(String servicename,String visiteurl,String employeecode,String parentemployeecode,String oprationpersion){
    	this.servicename = servicename;
    	this.visiteurl = visiteurl;
    	this.employeecode = employeecode;
    	this.parentemployeecode = parentemployeecode;
    	this.oprationperson = oprationpersion;
    }

    public Integer getLogcode() {
        return logcode;
    }

    public void setLogcode(Integer logcode) {
        this.logcode = logcode;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public String getVisiteurl() {
        return visiteurl;
    }

    public void setVisiteurl(String visiteurl) {
        this.visiteurl = visiteurl;
    }

    public String getOprationtype() {
        return oprationtype;
    }

    public void setOprationtype(String oprationtype) {
        this.oprationtype = oprationtype;
    }

    public Date getOprationtime() {
        return oprationtime;
    }

    public void setOprationtime(Date oprationtime) {
        this.oprationtime = oprationtime;
    }

    public String getEmployeecode() {
		return employeecode;
	}

	public void setEmployeecode(String employeecode) {
		this.employeecode = employeecode;
	}

	public String getParentemployeecode() {
		return parentemployeecode;
	}

	public void setParentemployeecode(String parentemployeecode) {
		this.parentemployeecode = parentemployeecode;
	}

	public String getOprationperson() {
        return oprationperson;
    }

    public void setOprationperson(String oprationperson) {
        this.oprationperson = oprationperson;
    }
    
    @Override
	public LogOperation clone() throws CloneNotSupportedException {
		return (LogOperation)super.clone();
	}
}