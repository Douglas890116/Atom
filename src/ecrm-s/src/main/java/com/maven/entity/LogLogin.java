package com.maven.entity;

import java.util.Date;
import java.util.List;

public class LogLogin {
	/** 主键	 */
    private Integer code;
    
    /** 企业编码 */
    private String enterprisecode;
    
    /** 品牌编码 */
    private String brandcode;

    /**  用户编码     */
    private String employeecode;
    
    /** 用户上级编码 */
    private String parentemployeecode;

    /** 登陆账号 */
    private String loginaccount;

    /** 登陆时间 */
    private Date logintime;

    /** 登陆IP */
    private String loginip;

    /** 机器码  */
    private String machinecode;//浏览器编码

    /** 操作系统 */
    private String opratesystem;

    /** 浏览器版本 */
    private String browserversion;

    /** 来源网址 */
    private String refererurl;
    
    /* 查询条件:开始条数*/
	private Integer start;
	/* 查询条件:查询条数 */
	private Integer limit;
	/* 用户团队集合 */
	private List<EmployeeRelation> employees;
	
	public LogLogin(){}
	
	public LogLogin(String enterprisecode,String brandcode,String employeecode){
		this.enterprisecode = enterprisecode;
		this.brandcode = brandcode;
		this.employeecode = employeecode;
	}
	
	
	public LogLogin(String enterprisecode,String brandcode,String employeecode,String parentemployeecode,String loginaccount,Date logintime,String loginip){
		this.enterprisecode = enterprisecode;
		this.brandcode = brandcode;
		this.employeecode = employeecode;
		this.parentemployeecode = parentemployeecode;
		this.loginaccount = loginaccount;
		this.logintime = logintime;
		this.loginip = loginip;
	}
	
	public LogLogin(String enterprisecode,String brandcode,String employeecode,String parentemployeecode,String loginaccount,Date logintime,String loginip
			,String machinecode,String opratesystem,String browserversion,String refererurl){
		this.enterprisecode = enterprisecode;
		this.brandcode = brandcode;
		this.employeecode = employeecode;
		this.parentemployeecode = parentemployeecode;
		this.loginaccount = loginaccount;
		this.logintime = logintime;
		this.loginip = loginip;
		this.machinecode = machinecode;
		this.opratesystem = opratesystem;
		this.browserversion = browserversion;
		this.refererurl = refererurl;
	}
	
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
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

    public Date getLogintime() {
        return logintime;
    }

    public void setLogintime(Date logintime) {
        this.logintime = logintime;
    }

    public String getLoginip() {
        return loginip;
    }

    public void setLoginip(String loginip) {
        this.loginip = loginip;
    }

    public String getMachinecode() {
        return machinecode;
    }

    public void setMachinecode(String machinecode) {
        this.machinecode = machinecode;
    }

    public String getBrowserversion() {
        return browserversion;
    }

    public void setBrowserversion(String browserversion) {
        this.browserversion = browserversion;
    }

    public String getOpratesystem() {
		return opratesystem;
	}

	public void setOpratesystem(String opratesystem) {
		this.opratesystem = opratesystem;
	}

	public String getRefererurl() {
        return refererurl;
    }

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

	public List<EmployeeRelation> getEmployees() {
		return employees;
	}

	public void setEmployees(List<EmployeeRelation> employees) {
		this.employees = employees;
	}

	public void setRefererurl(String refererurl) {
        this.refererurl = refererurl;
    }
}