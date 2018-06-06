package com.maven.entity;

public class EnterpriseActivityCustomizationSetting {
	
	/**
	 * 主键
	 */
    private Integer eacscode;

    /**
     * 企业定制活动编码
     */
    private Integer ecactivitycode;

    /**
     * 活动参数编码
     */
    private Integer activitysettingcode;

    /**
     * 参数值
     */
    private String agementvalue;
    
    public EnterpriseActivityCustomizationSetting(){}
    
    public EnterpriseActivityCustomizationSetting(Integer ecactivitycode){
    	this.ecactivitycode = ecactivitycode;
    }
    
    public EnterpriseActivityCustomizationSetting(Integer ecactivitycode,Integer activitysettingcode,String agementvalue){
    	this.ecactivitycode = ecactivitycode;
    	this.activitysettingcode = activitysettingcode;
    	this.agementvalue = agementvalue;
    }

    public Integer getEacscode() {
        return eacscode;
    }

    public void setEacscode(Integer eacscode) {
        this.eacscode = eacscode;
    }

    public Integer getEcactivitycode() {
        return ecactivitycode;
    }

    public void setEcactivitycode(Integer ecactivitycode) {
        this.ecactivitycode = ecactivitycode;
    }

    public Integer getActivitysettingcode() {
        return activitysettingcode;
    }

    public void setActivitysettingcode(Integer activitysettingcode) {
        this.activitysettingcode = activitysettingcode;
    }

    public String getAgementvalue() {
        return agementvalue;
    }

    public void setAgementvalue(String agementvalue) {
        this.agementvalue = agementvalue;
    }
}