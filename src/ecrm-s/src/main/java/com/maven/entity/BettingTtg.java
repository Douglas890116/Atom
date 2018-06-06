package com.maven.entity;

import java.util.Date;

public class BettingTtg {
    private Long transactionid;

    private String requestid;

    private String partnerid;

    private String playerid;

    private String amount;
    private String netMoney;//输赢金额

    private String handid;

    private String transactiontype;

    private String transactionsubtype;

    private String currency;

    private String game;

    private String transactiondate;

    private Date createtime;

    private String platformflag;

    private String enterprisecode;

    private String brandcode;

    private String employeecode;

    private String parentemployeecode;

    private String loginaccount;

    private String gamebigtype;

    private Integer status;
    
    private Date bettime;//投注时间 从transactiondate转换，格式20141115 22:11:30
    private Date resolvetime;//结算时间从transactiondate转换，格式20141115 22:11:30。结算时间是下注后的结算时间，因为投注与结算是分开的两条记录
    
    public Long getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(Long transactionid) {
        this.transactionid = transactionid;
    }

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPlayerid() {
        return playerid;
    }

    public void setPlayerid(String playerid) {
        this.playerid = playerid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getHandid() {
        return handid;
    }

    public void setHandid(String handid) {
        this.handid = handid;
    }

    public String getTransactiontype() {
        return transactiontype;
    }

    public void setTransactiontype(String transactiontype) {
        this.transactiontype = transactiontype;
    }

    public String getTransactionsubtype() {
        return transactionsubtype;
    }

    public void setTransactionsubtype(String transactionsubtype) {
        this.transactionsubtype = transactionsubtype;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getTransactiondate() {
        return transactiondate;
    }

    public void setTransactiondate(String transactiondate) {
        this.transactiondate = transactiondate;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getPlatformflag() {
        return platformflag;
    }

    public void setPlatformflag(String platformflag) {
        this.platformflag = platformflag;
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

    public String getGamebigtype() {
        return gamebigtype;
    }

    public void setGamebigtype(String gamebigtype) {
        this.gamebigtype = gamebigtype;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public Date getBettime() {
		return bettime;
	}

	public void setBettime(Date bettime) {
		this.bettime = bettime;
	}

	public String getNetMoney() {
		return netMoney;
	}

	public void setNetMoney(String netMoney) {
		this.netMoney = netMoney;
	}

	public Date getResolvetime() {
		return resolvetime;
	}

	public void setResolvetime(Date resolvetime) {
		this.resolvetime = resolvetime;
	}

}