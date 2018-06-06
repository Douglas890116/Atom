package com.maven.entity;

import java.util.Date;

public class PrivateDataAccess {
	/** 主键 */
    private Integer id;
    /** 企业编码 */
    private String enterprisecode;
    /** 员工编码 */
    private String employeecode;
    
    /* 关联属性:登陆账号 */
    private String loginaccount;
    /* 关联属性：别名 */
    private String displayalias; 
    /* 关联属性:用户类型 */
    private String employeetypename;
    /* 关联属性:在线状态 */
    private Byte onlinestatus;
    /* 关联属性:用户状态 */
    private Byte employeestatus;
    /* 关联属性:最后一次登录时间 */
    private Date lastlogintime;
    /* 关联属性:注册时间 */
    private Date logindatetime;
    /* 加密字段*/
    private String sign;
    
    
    
    /**
     * 无参数构造函数
     */
    public PrivateDataAccess(){}
    
    /**
     * 构造函数
     * @param enterprisecode
     * @param employeecode
     */
    public PrivateDataAccess(String enterprisecode,String employeecode){
    	this.enterprisecode = enterprisecode;
    	this.employeecode = employeecode;
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

	public String getDisplayalias() {
		return displayalias;
	}

	public void setDisplayalias(String displayalias) {
		this.displayalias = displayalias;
	}

	public String getEmployeetypename() {
		return employeetypename;
	}

	public void setEmployeetypename(String employeetypename) {
		this.employeetypename = employeetypename;
	}

	public Byte getOnlinestatus() {
		return onlinestatus;
	}

	public void setOnlinestatus(Byte onlinestatus) {
		this.onlinestatus = onlinestatus;
	}

	public Byte getEmployeestatus() {
		return employeestatus;
	}

	public void setEmployeestatus(Byte employeestatus) {
		this.employeestatus = employeestatus;
	}

	public Date getLastlogintime() {
		return lastlogintime;
	}

	public void setLastlogintime(Date lastlogintime) {
		this.lastlogintime = lastlogintime;
	}

	public Date getLogindatetime() {
		return logindatetime;
	}

	public void setLogindatetime(Date logindatetime) {
		this.logindatetime = logindatetime;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

}