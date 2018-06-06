package com.maven.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * PNG与TAG是一样的数据结构
 * @author Administrator
 *
 */
public class BettingHqPng implements Serializable{
	private static final long serialVersionUID = -5557051226855482714L;
	//注单流水号
	private String billNo;
	//企业编码
	private String enterpriseCode;
	/** employeecode. */
	private String employeecode;
	/*** 上级编码 */
    private String parentemployeecode;
	//品牌编码
	private String brandCode;
	//玩家账号
	private String playerName;
	//代理商编号
	private String agentCode;
	//游戏局号
	private String gameCode;
	//玩家输赢额度
	private Double netAmount;
	//投注时间
	private Date betTime;
	//游戏类型
	private String gameType;
	//投注金额
	private Double betAmount;
	//有效投注额度
	private Double validBetAmount;
	//结算状态
	private int flag;
	//游戏玩法
	private String playType;
	//货币类型
	private String currency;
	//桌子编号
	private String tableCode;
	//玩家 IP
	private String loginIP;
	//注单重新派彩时间
	private Date recalcuTime;
	//平台编号(通常為空)
	private String platformId;
	//平台類型
	private String platformType;
	//轮盘游戏
	private String remark;
	//
	private String round;
	//
	private String stringex;
	//数据导出时间
	private Date createTime;
	//
	private String result;
	//
	private String beforeCredit;
	//设备类型
	private String deviceType;
	//
	private Date lastTime;
	//员工编码
	private String employeeCode;
	//登录账号
	private String loginaccount;
	/* 查询条件:开始条数*/
	private Integer start;
	/* 查询条件:查询条数 */
	private Integer limit;
	
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getEnterpriseCode() {
		return enterpriseCode;
	}
	public void setEnterpriseCode(String enterpriseCode) {
		this.enterpriseCode = enterpriseCode;
	}
	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
	/**
	 * Set the employeecode.
	 * 
	 * @param employeecode
	 *            employeecode
	 */
	public void setEmployeecode(String employeecode) {
		this.employeecode = employeecode;
	}

	/**
	 * Get the employeecode.
	 * 
	 * @return employeecode
	 */
	public String getEmployeecode() {
		return this.employeecode;
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
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	public String getGameCode() {
		return gameCode;
	}
	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}
	public Double getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(Double netAmount) {
		this.netAmount = netAmount;
	}
	public Date getBetTime() {
		return betTime;
	}
	public void setBetTime(Date betTime) {
		this.betTime = betTime;
	}
	public String getGameType() {
		return gameType;
	}
	public void setGameType(String gameType) {
		this.gameType = gameType;
	}
	public Double getBetAmount() {
		return betAmount;
	}
	public void setBetAmount(Double betAmount) {
		this.betAmount = betAmount;
	}
	public Double getValidBetAmount() {
		return validBetAmount;
	}
	public void setValidBetAmount(Double validBetAmount) {
		this.validBetAmount = validBetAmount;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getPlayType() {
		return playType;
	}
	public void setPlayType(String playType) {
		this.playType = playType;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getTableCode() {
		return tableCode;
	}
	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}
	public String getLoginIP() {
		return loginIP;
	}
	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}
	public Date getRecalcuTime() {
		return recalcuTime;
	}
	public void setRecalcuTime(Date recalcuTime) {
		this.recalcuTime = recalcuTime;
	}
	public String getPlatformId() {
		return platformId;
	}
	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}
	public String getPlatformType() {
		return platformType;
	}
	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRound() {
		return round;
	}
	public void setRound(String round) {
		this.round = round;
	}
	public String getStringex() {
		return stringex;
	}
	public void setStringex(String stringex) {
		this.stringex = stringex;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getBeforeCredit() {
		return beforeCredit;
	}
	public void setBeforeCredit(String beforeCredit) {
		this.beforeCredit = beforeCredit;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public Date getLastTime() {
		return lastTime;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	public String getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	public String getLoginaccount() {
		return loginaccount;
	}
	public void setLoginaccount(String loginaccount) {
		this.loginaccount = loginaccount;
	}
	
}
