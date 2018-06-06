package com.maven.entity;

import java.io.Serializable;
import java.util.Date;

public class BettingHqNhq implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4011517983364618850L;

	private String bettingId;
	private String bettingNo;

    private String enterprisecode;

    private String brandcode;
    
	/*** 上级编码 */
    private String parentemployeecode;
    
    private String employeecode;

    private String loginaccount;

    private Double bettingCredits;

    private Double preCreditsPoint;

    private String gameResult;

    private String gameRoomName;

    private String agentAccount;

    private String gamblingCode;

    private Double afterPayoutCredits;

    private String userAccount;

    private String gameType;

    private Date bettingDate;

    private String dealerName;

    private String gameName;

    private String setGameNo;

    private Byte isPayout;

    private String parentUserId;

    private Double winningCredits;

    private String bettingPoint;

    private String tableName;

    private String trackIp;

    private Date createTime;

    private Double washCodeCredits;

    private Date updateTime;

    private Date lastTime;

    
    public String getBettingNo() {
        return bettingNo;
    }

    public void setBettingNo(String bettingNo) {
        this.bettingNo = bettingNo;
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

	/**
	 * Get the parentemployeecode.
	 * 
	 * @return parentemployeecode
	 */
	public String getParentemployeecode() {
		return parentemployeecode;
	}

	/**
	 * Set the parentemployeecode.
	 * 
	 * @param parentemployeecode
	 *            parentemployeecode
	 */
	public void setParentemployeecode(String parentemployeecode) {
		this.parentemployeecode = parentemployeecode;
	}
	
    public Double getBettingCredits() {
        return bettingCredits;
    }

    public void setBettingCredits(Double bettingCredits) {
        this.bettingCredits = bettingCredits;
    }

    public Double getPreCreditsPoint() {
        return preCreditsPoint;
    }

    public void setPreCreditsPoint(Double preCreditsPoint) {
        this.preCreditsPoint = preCreditsPoint;
    }

    public String getGameResult() {
        return gameResult;
    }

    public void setGameResult(String gameResult) {
        this.gameResult = gameResult;
    }

    public String getGameRoomName() {
        return gameRoomName;
    }

    public void setGameRoomName(String gameRoomName) {
        this.gameRoomName = gameRoomName;
    }

    public String getAgentAccount() {
        return agentAccount;
    }

    public void setAgentAccount(String agentAccount) {
        this.agentAccount = agentAccount;
    }

    public String getGamblingCode() {
        return gamblingCode;
    }

    public void setGamblingCode(String gamblingCode) {
        this.gamblingCode = gamblingCode;
    }

    public Double getAfterPayoutCredits() {
        return afterPayoutCredits;
    }

    public void setAfterPayoutCredits(Double afterPayoutCredits) {
        this.afterPayoutCredits = afterPayoutCredits;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public Date getBettingDate() {
        return bettingDate;
    }

    public void setBettingDate(Date bettingDate) {
        this.bettingDate = bettingDate;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getSetGameNo() {
        return setGameNo;
    }

    public void setSetGameNo(String setGameNo) {
        this.setGameNo = setGameNo;
    }

    public Byte getIsPayout() {
        return isPayout;
    }

    public void setIsPayout(Byte isPayout) {
        this.isPayout = isPayout;
    }

    public String getParentUserId() {
        return parentUserId;
    }

    public void setParentUserId(String parentUserId) {
        this.parentUserId = parentUserId;
    }

    public Double getWinningCredits() {
        return winningCredits;
    }

    public void setWinningCredits(Double winningCredits) {
        this.winningCredits = winningCredits;
    }

    public String getBettingPoint() {
        return bettingPoint;
    }

    public void setBettingPoint(String bettingPoint) {
        this.bettingPoint = bettingPoint;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTrackIp() {
        return trackIp;
    }

    public void setTrackIp(String trackIp) {
        this.trackIp = trackIp;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Double getWashCodeCredits() {
        return washCodeCredits;
    }

    public void setWashCodeCredits(Double washCodeCredits) {
        this.washCodeCredits = washCodeCredits;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
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

	public String getBettingId() {
		return bettingId;
	}

	public void setBettingId(String bettingId) {
		this.bettingId = bettingId;
	}
}