package com.maven.entity;

import java.math.BigDecimal;
import java.util.Date;

public class EmployeeMoneyChange {
	/** 帐变编码 */
    private String moneychangecode;
    /** 帐变订单编号 */
    private String ordernumber;
    /** 员工编码 */
    private String employeecode;
    /** 企业编码 */
    private String enterprisecode;
    /** 用户上级编码 */
    private String parentemployeecode;
    /** 员工名称*/
    private String employeename;
    /** 登录账号*/
    private String loginaccount;
	/** 帐变类型编码 */
    private String moneychangetypecode;
    /** 账变类型名称*/
    private String moneychangetypename;
    /** 帐变时间 */
    private Date moneychangedate;
    
    private String currencycode;
    /** 帐变金额 */
    private BigDecimal moneychangeamount;
    /** 帐变前余额 */
    private BigDecimal settlementamount;
    /** 备注 */
    private String moneyinoutcomment;
    /**  冲正冲负类型 */
    private String moneyaddtype;
    /** 时间排序 */
    private Long timesort;
    

	/*  业务字段：帐变后余额  */
    private BigDecimal afteramount;
    
    
    
    public EmployeeMoneyChange(){}
    
    
    public EmployeeMoneyChange(String moneychangecode,String ordernumber,String employeecode,String parentemployeecode,String moneychangetypecode,Date moneychangedate, 
    		BigDecimal moneychangeamount, BigDecimal settlementamount,String moneyinoutcomment,long sorttime, String enterprisecode){
    	this.moneychangecode = moneychangecode;
    	this.ordernumber = ordernumber;
    	this.employeecode = employeecode;
    	this.parentemployeecode = parentemployeecode;
    	this.moneychangetypecode = moneychangetypecode;
    	this.moneychangedate = moneychangedate;
    	this.moneychangeamount = moneychangeamount;
    	this.settlementamount = settlementamount;
    	this.moneyinoutcomment = moneyinoutcomment;
    	this.timesort = sorttime;
    	this.enterprisecode = enterprisecode;
    }
    public EmployeeMoneyChange(String moneychangecode,String ordernumber,String employeecode,String parentemployeecode,String moneychangetypecode,Date moneychangedate, 
    		BigDecimal moneychangeamount, BigDecimal settlementamount,String moneyinoutcomment,String moneyaddtype,long sorttime,String enterprisecode){
    	this.moneychangecode = moneychangecode;
    	this.ordernumber = ordernumber;
    	this.employeecode = employeecode;
    	this.parentemployeecode = parentemployeecode;
    	this.moneychangetypecode = moneychangetypecode;
    	this.moneychangedate = moneychangedate;
    	this.moneychangeamount = moneychangeamount;
    	this.settlementamount = settlementamount;
    	this.moneyinoutcomment = moneyinoutcomment;
    	this.moneyaddtype = moneyaddtype;
    	this.timesort = sorttime;
    	this.enterprisecode = enterprisecode;
    }
    
    public String getMoneyaddtype() {
		return moneyaddtype;
	}


	public void setMoneyaddtype(String moneyaddtype) {
		this.moneyaddtype = moneyaddtype;
	}
	
    public String getLoginaccount() {
		return loginaccount;
	}


	public void setLoginaccount(String loginaccount) {
		this.loginaccount = loginaccount;
	}
    
    public String getOrdernumber() {
		return ordernumber;
	}


	public void setOrdernumber(String ordernumber) {
		this.ordernumber = ordernumber;
	}


	public String getEmployeename() {
		return employeename;
	}

	public void setEmployeename(String employeename) {
		this.employeename = employeename;
	}

	public String getMoneychangetypename() {
		return moneychangetypename;
	}

	public void setMoneychangetypename(String moneychangetypename) {
		this.moneychangetypename = moneychangetypename;
	}
    
    public String getMoneychangecode() {
        return moneychangecode;
    }

    public void setMoneychangecode(String moneychangecode) {
        this.moneychangecode = moneychangecode;
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


	public String getMoneychangetypecode() {
        return moneychangetypecode;
    }

    public void setMoneychangetypecode(String moneychangetypecode) {
        this.moneychangetypecode = moneychangetypecode;
    }

    public Date getMoneychangedate() {
        return moneychangedate;
    }

    public void setMoneychangedate(Date moneychangedate) {
        this.moneychangedate = moneychangedate;
    }

    public String getCurrencycode() {
        return currencycode;
    }

    public void setCurrencycode(String currencycode) {
        this.currencycode = currencycode;
    }

    public BigDecimal getMoneychangeamount() {
        return moneychangeamount;
    }

    public void setMoneychangeamount(BigDecimal moneychangeamount) {
        this.moneychangeamount = moneychangeamount;
    }

    public BigDecimal getSettlementamount() {
        return settlementamount;
    }

    public void setSettlementamount(BigDecimal settlementamount) {
        this.settlementamount = settlementamount;
    }

    public BigDecimal getAfteramount() {
		return afteramount;
	}


	public void setAfteramount(BigDecimal afteramount) {
		this.afteramount = afteramount;
	}


	public String getMoneyinoutcomment() {
        return moneyinoutcomment;
    }

    public void setMoneyinoutcomment(String moneyinoutcomment) {
        this.moneyinoutcomment = moneyinoutcomment;
    }


	public Long getTimesort() {
		return timesort;
	}


	public void setTimesort(Long timesort) {
		this.timesort = timesort;
	}


	public String getEnterprisecode() {
		return enterprisecode;
	}


	public void setEnterprisecode(String enterprisecode) {
		this.enterprisecode = enterprisecode;
	}
}