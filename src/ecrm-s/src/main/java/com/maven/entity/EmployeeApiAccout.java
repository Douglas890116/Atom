package com.maven.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.maven.game.APIServiceUtil;

public class EmployeeApiAccout implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String apiaccountcode;
	
	/**
	 * 游戏编码 */
    private String gametype;
    
    /** 
     * 企业编码 */
    private String enterprisecode;
    
    /**
     * 品牌编码 */
    private String brandcode;

    /**
     * 员工编码 */
    private String employeecode;
    
    /** 
     * 上级编码 */
    private String parentemployeecode;
    
    /**
     * 登陆账号 */
    private String loginaccount;

    /**
     * 游戏账号 */
    private String gameaccount;
    
    /**
     * 游戏密码 */
    private String gamepassword;
    
    /***禁用启用状态
     * 
     * 禁用后：无法上下分和登录游戏，可查余额
     * 
     * **/
    private String status;
    
    /****子钱包余额
     * 
     * 暂时未启用该业务，保留该字段
     * 
     * ***/
    private BigDecimal balance ;
    
    
    
    
    
    /**
     * 是否存在余额 */
    private boolean exitmoney = true;
    
    /**创建时间**/
    private Date createtime;
    
    /* 关联字段：游戏名称 */
    private String gamename;
    
    /* 唯一标识字段 */
    private String sign;
    
    public EmployeeApiAccout(){}
    
    public enum Enum_status{
    	启用("启用","启用"),
    	禁用("禁用","禁用");
    	public String value;
    	public String desc;
    	private Enum_status(String _value,String _desc){
    		this.value = _value;
    		this.desc = _desc;
    	}
    }
    
    public EmployeeApiAccout(String employeecode){
    	this.employeecode = employeecode;
    }
    
    public EmployeeApiAccout(String gametype ,String employeecode){
    	this.gametype = gametype;
    	this.employeecode = employeecode;
    }

 

	public String getEnterprisecode() {
		return enterprisecode;
	}

	public void setEnterprisecode(String enterprisecode) {
		this.enterprisecode = enterprisecode;
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

	public String getBrandcode() {
		return brandcode;
	}

	public void setBrandcode(String brandcode) {
		this.brandcode = brandcode;
	}

	public String getGametype() {
		return gametype;
	}

	public void setGametype(String gametype) {
		this.gametype = gametype;
	}

	public String getGameaccount() {
        return gameaccount;
    }

    public void setGameaccount(String gameaccount) {
        this.gameaccount = gameaccount;
    }

	public String getGamepassword() {
		return this.gamepassword;
	}

	public void setGamepassword(String gamepassword) {
		this.gamepassword = gamepassword;
	}

	public String getParentemployeecode() {
		return parentemployeecode;
	}

	public void setParentemployeecode(String parentemployeecode) {
		this.parentemployeecode = parentemployeecode;
	}

	public boolean isExitmoney() {
		return exitmoney;
	}

	public void setExitmoney(boolean exitmoney) {
		this.exitmoney = exitmoney;
	}

	public String getGamename() {
		return gamename;
	}

	public void setGamename(String gamename) {
		this.gamename = gamename;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getApiaccountcode() {
		return apiaccountcode;
	}

	public void setApiaccountcode(String apiaccountcode) {
		this.apiaccountcode = apiaccountcode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}


}