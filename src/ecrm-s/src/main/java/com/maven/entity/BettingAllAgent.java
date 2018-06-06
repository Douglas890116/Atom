package com.maven.entity;

import java.util.Date;

public class BettingAllAgent {
    private Integer lsh;

    private String enterprisecode;

    private String brandcode;

    private String employeecode;

    private String parentemployeecode;

    private String loginaccount;

    private String gametype;

    private String gamebigtype;

    private Integer betday;

    private Double betmoney;

    private Double netmoney;

    private Double validmoney;

    private Date createtime;

    private String patchno;

    private Double rate;

    private Double amount;

    private Integer status;
    
    public enum Enum_status{
    	已生成(0,"已生成"),
		已支付(1,"已支付");
		public Integer value;
		public String desc;
		
		private Enum_status(Integer value,String desc){
			this.value=value;
			this.desc=desc;
		}
	}
    
    public BettingAllAgent() {
		super();
	}
    public BettingAllAgent(String enterprisecode, String brandcode, String employeecode, String parentemployeecode,
			String loginaccount, String gametype, String gamebigtype, Integer betday, Double betmoney, Double netmoney,
			Double validmoney, Date createtime, String patchno, Double rate, Double amount, Integer status) {
		super();
		this.enterprisecode = enterprisecode;
		this.brandcode = brandcode;
		this.employeecode = employeecode;
		this.parentemployeecode = parentemployeecode;
		this.loginaccount = loginaccount;
		this.gametype = gametype;
		this.gamebigtype = gamebigtype;
		this.betday = betday;
		this.betmoney = betmoney;
		this.netmoney = netmoney;
		this.validmoney = validmoney;
		this.createtime = createtime;
		this.patchno = patchno;
		this.rate = rate;
		this.amount = amount;
		this.status = status;
	}

	public Integer getLsh() {
        return lsh;
    }

    public void setLsh(Integer lsh) {
        this.lsh = lsh;
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

    public String getLoginaccount() {
        return loginaccount;
    }

    public void setLoginaccount(String loginaccount) {
        this.loginaccount = loginaccount;
    }

    public String getGametype() {
        return gametype;
    }

    public void setGametype(String gametype) {
        this.gametype = gametype;
    }

    public String getGamebigtype() {
        return gamebigtype;
    }

    public void setGamebigtype(String gamebigtype) {
        this.gamebigtype = gamebigtype;
    }

    public Integer getBetday() {
        return betday;
    }

    public void setBetday(Integer betday) {
        this.betday = betday;
    }

    public Double getBetmoney() {
        return betmoney;
    }

    public void setBetmoney(Double betmoney) {
        this.betmoney = betmoney;
    }

    public Double getNetmoney() {
        return netmoney;
    }

    public void setNetmoney(Double netmoney) {
        this.netmoney = netmoney;
    }

    public Double getValidmoney() {
        return validmoney;
    }

    public void setValidmoney(Double validmoney) {
        this.validmoney = validmoney;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getPatchno() {
        return patchno;
    }

    public void setPatchno(String patchno) {
        this.patchno = patchno;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}