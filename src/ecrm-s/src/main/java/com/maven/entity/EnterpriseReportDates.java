package com.maven.entity;

import java.util.Date;

public class EnterpriseReportDates {
    private Integer lsh;

    private String enterprisecode;

    private Integer reportdate;

    private Integer memberRegeditCount;

    private Integer agentRegeditCount;

    private Integer loginCount;

    private Integer firstDepositCount;

    private Double firstDepositMoney;

    private Integer secondDepositCount;

    private Double secondDepositMoney;

    private Integer noSecondCount;

    private Integer noThreeCount;

    private Integer todayDepositCount;

    private Integer todayDepositCount1;

    private Double todayDepositMoney;

    private Integer todayTakeCount;

    private Integer todayTakeCount1;

    private Double todayTakeMoney;

    private Double todayBetmoney;

    private Double todayNetmoney;

    private Double todayVaildmoney;

    private Double todayProsmoney;

    private Double todayConsmoney;

    private Integer todayPreferentialCount;

    private Double todayPreferentialMoney;

    private Integer todayWashCount;

    private Double todayWashMoney;

    private Date createtime;
    
    //视图字段
    private String moneychangetypecode;
    private String moneychangeamount;
    private String moneychangecount;

    public EnterpriseReportDates() {
	}
    
    public EnterpriseReportDates(String enterprisecode, Integer reportdate, Integer memberRegeditCount,
			Integer agentRegeditCount, Integer loginCount, Integer firstDepositCount, Double firstDepositMoney,
			Integer secondDepositCount, Double secondDepositMoney, Integer noSecondCount, Integer noThreeCount,
			Integer todayDepositCount, Integer todayDepositCount1, Double todayDepositMoney, Integer todayTakeCount,
			Integer todayTakeCount1, Double todayTakeMoney, Double todayBetmoney, Double todayNetmoney,
			Double todayVaildmoney, Double todayProsmoney, Double todayConsmoney, Integer todayPreferentialCount,
			Double todayPreferentialMoney, Integer todayWashCount, Double todayWashMoney, Date createtime) {
		super();
		this.enterprisecode = enterprisecode;
		this.reportdate = reportdate;
		this.memberRegeditCount = memberRegeditCount;
		this.agentRegeditCount = agentRegeditCount;
		this.loginCount = loginCount;
		this.firstDepositCount = firstDepositCount;
		this.firstDepositMoney = firstDepositMoney;
		this.secondDepositCount = secondDepositCount;
		this.secondDepositMoney = secondDepositMoney;
		this.noSecondCount = noSecondCount;
		this.noThreeCount = noThreeCount;
		this.todayDepositCount = todayDepositCount;
		this.todayDepositCount1 = todayDepositCount1;
		this.todayDepositMoney = todayDepositMoney;
		this.todayTakeCount = todayTakeCount;
		this.todayTakeCount1 = todayTakeCount1;
		this.todayTakeMoney = todayTakeMoney;
		this.todayBetmoney = todayBetmoney;
		this.todayNetmoney = todayNetmoney;
		this.todayVaildmoney = todayVaildmoney;
		this.todayProsmoney = todayProsmoney;
		this.todayConsmoney = todayConsmoney;
		this.todayPreferentialCount = todayPreferentialCount;
		this.todayPreferentialMoney = todayPreferentialMoney;
		this.todayWashCount = todayWashCount;
		this.todayWashMoney = todayWashMoney;
		this.createtime = createtime;
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

    public Integer getReportdate() {
        return reportdate;
    }

    public void setReportdate(Integer reportdate) {
        this.reportdate = reportdate;
    }

    public Integer getMemberRegeditCount() {
        return memberRegeditCount;
    }

    public void setMemberRegeditCount(Integer memberRegeditCount) {
        this.memberRegeditCount = memberRegeditCount;
    }

    public Integer getAgentRegeditCount() {
        return agentRegeditCount;
    }

    public void setAgentRegeditCount(Integer agentRegeditCount) {
        this.agentRegeditCount = agentRegeditCount;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public Integer getFirstDepositCount() {
        return firstDepositCount;
    }

    public void setFirstDepositCount(Integer firstDepositCount) {
        this.firstDepositCount = firstDepositCount;
    }

    public Double getFirstDepositMoney() {
        return firstDepositMoney;
    }

    public void setFirstDepositMoney(Double firstDepositMoney) {
        this.firstDepositMoney = firstDepositMoney;
    }

    public Integer getSecondDepositCount() {
        return secondDepositCount;
    }

    public void setSecondDepositCount(Integer secondDepositCount) {
        this.secondDepositCount = secondDepositCount;
    }

    public Double getSecondDepositMoney() {
        return secondDepositMoney;
    }

    public void setSecondDepositMoney(Double secondDepositMoney) {
        this.secondDepositMoney = secondDepositMoney;
    }

    public Integer getNoSecondCount() {
        return noSecondCount;
    }

    public void setNoSecondCount(Integer noSecondCount) {
        this.noSecondCount = noSecondCount;
    }

    public Integer getNoThreeCount() {
        return noThreeCount;
    }

    public void setNoThreeCount(Integer noThreeCount) {
        this.noThreeCount = noThreeCount;
    }

    public Integer getTodayDepositCount() {
        return todayDepositCount;
    }

    public void setTodayDepositCount(Integer todayDepositCount) {
        this.todayDepositCount = todayDepositCount;
    }

    public Integer getTodayDepositCount1() {
        return todayDepositCount1;
    }

    public void setTodayDepositCount1(Integer todayDepositCount1) {
        this.todayDepositCount1 = todayDepositCount1;
    }

    public Double getTodayDepositMoney() {
        return todayDepositMoney;
    }

    public void setTodayDepositMoney(Double todayDepositMoney) {
        this.todayDepositMoney = todayDepositMoney;
    }

    public Integer getTodayTakeCount() {
        return todayTakeCount;
    }

    public void setTodayTakeCount(Integer todayTakeCount) {
        this.todayTakeCount = todayTakeCount;
    }

    public Integer getTodayTakeCount1() {
        return todayTakeCount1;
    }

    public void setTodayTakeCount1(Integer todayTakeCount1) {
        this.todayTakeCount1 = todayTakeCount1;
    }

    public Double getTodayTakeMoney() {
        return todayTakeMoney;
    }

    public void setTodayTakeMoney(Double todayTakeMoney) {
        this.todayTakeMoney = todayTakeMoney;
    }

    public Double getTodayBetmoney() {
        return todayBetmoney;
    }

    public void setTodayBetmoney(Double todayBetmoney) {
        this.todayBetmoney = todayBetmoney;
    }

    public Double getTodayNetmoney() {
        return todayNetmoney;
    }

    public void setTodayNetmoney(Double todayNetmoney) {
        this.todayNetmoney = todayNetmoney;
    }

    public Double getTodayVaildmoney() {
        return todayVaildmoney;
    }

    public void setTodayVaildmoney(Double todayVaildmoney) {
        this.todayVaildmoney = todayVaildmoney;
    }

    public Double getTodayProsmoney() {
        return todayProsmoney;
    }

    public void setTodayProsmoney(Double todayProsmoney) {
        this.todayProsmoney = todayProsmoney;
    }

    public Double getTodayConsmoney() {
        return todayConsmoney;
    }

    public void setTodayConsmoney(Double todayConsmoney) {
        this.todayConsmoney = todayConsmoney;
    }

    public Integer getTodayPreferentialCount() {
        return todayPreferentialCount;
    }

    public void setTodayPreferentialCount(Integer todayPreferentialCount) {
        this.todayPreferentialCount = todayPreferentialCount;
    }

    public Double getTodayPreferentialMoney() {
        return todayPreferentialMoney;
    }

    public void setTodayPreferentialMoney(Double todayPreferentialMoney) {
        this.todayPreferentialMoney = todayPreferentialMoney;
    }

    public Integer getTodayWashCount() {
        return todayWashCount;
    }

    public void setTodayWashCount(Integer todayWashCount) {
        this.todayWashCount = todayWashCount;
    }

    public Double getTodayWashMoney() {
        return todayWashMoney;
    }

    public void setTodayWashMoney(Double todayWashMoney) {
        this.todayWashMoney = todayWashMoney;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

	public String getMoneychangetypecode() {
		return moneychangetypecode;
	}

	public void setMoneychangetypecode(String moneychangetypecode) {
		this.moneychangetypecode = moneychangetypecode;
	}

	public String getMoneychangeamount() {
		return moneychangeamount;
	}

	public void setMoneychangeamount(String moneychangeamount) {
		this.moneychangeamount = moneychangeamount;
	}

	public String getMoneychangecount() {
		return moneychangecount;
	}

	public void setMoneychangecount(String moneychangecount) {
		this.moneychangecount = moneychangecount;
	}
}