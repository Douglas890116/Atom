package com.maven.entity;

import java.util.Date;

public class Baccarath5Exchange {
    private String lsh;

    private String employeecode;

    private String loginaccount;

    private String enterprisecode;

    private String brandcode;

    private String parentemployeecode;

    private Double exchangeIn;

    private Double exchangeOut;

    private Double exchangeRate;

    private Date exchangeTime;

    private String exchangeStatus;

    private String remark;

    
    public enum Enum_exchangeStatus{
    	处理中("0","处理中"),
    	成功("1","成功"),
    	失败("2","失败");
		public String value;
		public String desc;
		
		private Enum_exchangeStatus(String value,String desc){
			this.value=value;
			this.desc=desc;
		}
	}
    
    
    public String getLsh() {
        return lsh;
    }

    public void setLsh(String lsh) {
        this.lsh = lsh;
    }

    public String getEmployeecode() {
        return employeecode;
    }

    public void setEmployeecode(String employeecode) {
        this.employeecode = employeecode;
    }

    public String getLoginaccount() {
        return loginaccount;
    }

    public void setLoginaccount(String loginaccount) {
        this.loginaccount = loginaccount;
    }

    public String getEnterprisecode() {
        return enterprisecode;
    }

    public void setEnterprisecode(String enterprisecode) {
        this.enterprisecode = enterprisecode;
    }

    public String getBrandcode() {
        return brandcode;
    }

    public void setBrandcode(String brandcode) {
        this.brandcode = brandcode;
    }

    public String getParentemployeecode() {
        return parentemployeecode;
    }

    public void setParentemployeecode(String parentemployeecode) {
        this.parentemployeecode = parentemployeecode;
    }

    public Double getExchangeIn() {
        return exchangeIn;
    }

    public void setExchangeIn(Double exchangeIn) {
        this.exchangeIn = exchangeIn;
    }

    public Double getExchangeOut() {
        return exchangeOut;
    }

    public void setExchangeOut(Double exchangeOut) {
        this.exchangeOut = exchangeOut;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Date getExchangeTime() {
        return exchangeTime;
    }

    public void setExchangeTime(Date exchangeTime) {
        this.exchangeTime = exchangeTime;
    }

    public String getExchangeStatus() {
        return exchangeStatus;
    }

    public void setExchangeStatus(String exchangeStatus) {
        this.exchangeStatus = exchangeStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}