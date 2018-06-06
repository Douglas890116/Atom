package com.maven.entity;

import java.util.Date;

public class BettingAllDay2 {
    private Integer id;

    private String enterprisecode;

    private String brandcode;

    private String employeecode;

    private String parentemployeecode;

    private String userName;

    private String gamePlatform;

    private String gameBigType;

    private String gameType;

    private Integer betDay;

    private Double betMoney;

    private Double netMoney;

    private Double validMoney;

    private Date addTime;

    private String patchno;
    
    //视图字段
    private int cnt;
    
    public BettingAllDay2() {
		super();
	}
    
    public BettingAllDay2(String enterprisecode, String brandcode, String employeecode, String parentemployeecode,
			String userName, String gamePlatform, String gameBigType, String gameType, Integer betDay, 
			Double betMoney,Double netMoney, Double validMoney, Date addTime, String patchno) {
			
		super();
		this.enterprisecode = enterprisecode;
		this.brandcode = brandcode;
		this.employeecode = employeecode;
		this.parentemployeecode = parentemployeecode;
		this.userName = userName;
		this.gamePlatform = gamePlatform;
		this.gameBigType = gameBigType;
		this.gameType = gameType;
		this.betDay = betDay;
		this.betMoney = betMoney;
		this.netMoney = netMoney;
		this.validMoney = validMoney;
		this.addTime = addTime;
		this.patchno = patchno;
	}

	

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGamePlatform() {
        return gamePlatform;
    }

    public void setGamePlatform(String gamePlatform) {
        this.gamePlatform = gamePlatform;
    }

    public String getGameBigType() {
        return gameBigType;
    }

    public void setGameBigType(String gameBigType) {
        this.gameBigType = gameBigType;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public Integer getBetDay() {
        return betDay;
    }

    public void setBetDay(Integer betDay) {
        this.betDay = betDay;
    }

    public Double getBetMoney() {
        return betMoney;
    }

    public void setBetMoney(Double betMoney) {
        this.betMoney = betMoney;
    }

    public Double getNetMoney() {
        return netMoney;
    }

    public void setNetMoney(Double netMoney) {
        this.netMoney = netMoney;
    }

    public Double getValidMoney() {
        return validMoney;
    }

    public void setValidMoney(Double validMoney) {
        this.validMoney = validMoney;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }


	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public String getPatchno() {
		return patchno;
	}

	public void setPatchno(String patchno) {
		this.patchno = patchno;
	}
}