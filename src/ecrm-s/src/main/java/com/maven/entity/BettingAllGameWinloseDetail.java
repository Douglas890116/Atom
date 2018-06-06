package com.maven.entity;

import java.util.Date;

public class BettingAllGameWinloseDetail {
    // 游戏平台类型
    private String platformtype;
    // 游戏平台ID
    private String platformid;
    // 员工编码
    private String employeecode;
    // 下注时间
    private Date   bettime;
    // 下注金额
    private double betmoney;
    // 有效投注额
    private double validbet;
    // 输赢金额
    private double netmoney;
    // 企业编码
    private String enterprisecode;
    // 品牌编码
    private String brandcode;
    // 用户账号
    private String loginaccount;
    // 上级编码
    private String parentemployeecode;
    // 游戏类型
    private String gamebigtype;
    // 游戏平台
    private String gametype;
    // 批次号
    private String patchno;
    
    /**
     * 统计用：记录数
     */
    private int cnt;
    
    public String getPlatformtype() {
        return platformtype;
    }
    public void setPlatformtype(String platformtype) {
        this.platformtype = platformtype;
    }
    public String getPlatformid() {
        return platformid;
    }
    public void setPlatformid(String platformid) {
        this.platformid = platformid;
    }
    public String getEmployeecode() {
        return employeecode;
    }
    public void setEmployeecode(String employeecode) {
        this.employeecode = employeecode;
    }
    public Date getBettime() {
        return bettime;
    }
    public void setBettime(Date bettime) {
        this.bettime = bettime;
    }
    public double getBetmoney() {
        return betmoney;
    }
    public void setBetmoney(double betmoney) {
        this.betmoney = betmoney;
    }
    public double getValidbet() {
        return validbet;
    }
    public void setValidbet(double validbet) {
        this.validbet = validbet;
    }
    public double getNetmoney() {
        return netmoney;
    }
    public void setNetmoney(double netmoney) {
        this.netmoney = netmoney;
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
    public String getLoginaccount() {
        return loginaccount;
    }
    public void setLoginaccount(String loginaccount) {
        this.loginaccount = loginaccount;
    }
    public String getParentemployeecode() {
        return parentemployeecode;
    }
    public void setParentemployeecode(String parentemployeecode) {
        this.parentemployeecode = parentemployeecode;
    }
    public String getGamebigtype() {
        return gamebigtype;
    }
    public void setGamebigtype(String gamebigtype) {
        this.gamebigtype = gamebigtype;
    }
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public String getGametype() {
		return gametype;
	}
	public void setGametype(String gametype) {
		this.gametype = gametype;
	}
	public String getPatchno() {
		return patchno;
	}
	public void setPatchno(String patchno) {
		this.patchno = patchno;
	}
}