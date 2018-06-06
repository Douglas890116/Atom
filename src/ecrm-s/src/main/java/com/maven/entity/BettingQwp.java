package com.maven.entity;

import java.util.Date;

public class BettingQwp {
	private long turnNumber;
	private int serverId;
	private int kindId;
	private String roomName;
	private Date startTime;
	private Date endTime;
	private Date recordTime;
	private String cardData;
	private String account;
	private double score;
	private double turnScore;
	private double revenue;
	private String enterprisecode;
	private String brandcode;
	private String employeecode;
	private String parentemployeecode;
	private String loginaccount;
	private String gamebigtype;
	private int status;
	
	public long getTurnNumber() {
		return turnNumber;
	}
	public void setTurnNumber(long turnNumber) {
		this.turnNumber = turnNumber;
	}
	public int getServerId() {
		return serverId;
	}
	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
	public int getKindId() {
		return kindId;
	}
	public void setKindId(int kindId) {
		this.kindId = kindId;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}
	public String getCardData() {
		return cardData;
	}
	public void setCardData(String cardData) {
		this.cardData = cardData;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public double getTurnScore() {
		return turnScore;
	}
	public void setTurnScore(double turnScore) {
		this.turnScore = turnScore;
	}
	public double getRevenue() {
		return revenue;
	}
	public void setRevenue(double revenue) {
		this.revenue = revenue;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}