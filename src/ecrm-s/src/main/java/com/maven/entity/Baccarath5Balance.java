package com.maven.entity;

import java.util.Date;

public class Baccarath5Balance {
    private String employeecode;

    private String loginaccount;

    private String enterprisecode;

    private String brandcode;

    private String parentemployeecode;

    private Double balance;
    
    private Integer levelcode;//当前等级

    private Date updatetime;

    public Baccarath5Balance(String employeecode, Double balance) {
		this.employeecode = employeecode;
		this.balance = balance;
	}
    
    public Baccarath5Balance(String employeecode, String loginaccount, String enterprisecode, String brandcode,
			String parentemployeecode, Double balance, Date updatetime) {
		super();
		this.employeecode = employeecode;
		this.loginaccount = loginaccount;
		this.enterprisecode = enterprisecode;
		this.brandcode = brandcode;
		this.parentemployeecode = parentemployeecode;
		this.balance = balance;
		this.updatetime = updatetime;
	}

	public Baccarath5Balance() {
		// TODO Auto-generated constructor stub
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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

	public Integer getLevelcode() {
		return levelcode;
	}

	public void setLevelcode(Integer levelcode) {
		this.levelcode = levelcode;
	}
}