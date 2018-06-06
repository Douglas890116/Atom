package com.maven.entity;

import java.util.Date;

public class BettingQp {
    private String turnNumber;

    private String serverId;

    private String kindId;

    private String roomName;

    private String startTime;

    private String endTime;

    private String score;//--输赢

    private String turnScore;//底注
    
    private String revenue;//-抽水

    private String account;

    private Date createtime;

    private String platformflag;

    private String enterprisecode;

    private String brandcode;

    private String employeecode;

    private String parentemployeecode;

    private String loginaccount;

    private String gamebigtype;

    private Integer status;

    private Date bettime;

    private Double netmoney;

    private Double betmoney;

    public String getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(String turnNumber) {
        this.turnNumber = turnNumber;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getKindId() {
        return kindId;
    }

    public void setKindId(String kindId) {
        this.kindId = kindId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTurnScore() {
        return turnScore;
    }

    public void setTurnScore(String turnScore) {
        this.turnScore = turnScore;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public Double getNetmoney() {
        return netmoney;
    }

    public void setNetmoney(Double netmoney) {
        this.netmoney = netmoney;
    }

    public Double getBetmoney() {
        return betmoney;
    }

    public void setBetmoney(Double betmoney) {
        this.betmoney = betmoney;
    }

	public String getRevenue() {
		return revenue;
	}

	public void setRevenue(String revenue) {
		this.revenue = revenue;
	}
}