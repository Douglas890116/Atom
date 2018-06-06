package com.maven.entity;

import java.io.Serializable;

public class WorkingFlowObject  implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
     * 员工编码
     */
    private String employeecode;

    /**
     * 工作流编码
     */
    private String flowcode;
    
    public WorkingFlowObject(){}
    
    public WorkingFlowObject(String employeecode,String flowcode){
    	this.employeecode = employeecode;
    	this.flowcode = flowcode;
    }

    public String getEmployeecode() {
        return employeecode;
    }

    public void setEmployeecode(String employeecode) {
        this.employeecode = employeecode;
    }

    public String getFlowcode() {
        return flowcode;
    }

    public void setFlowcode(String flowcode) {
        this.flowcode = flowcode;
    }
}