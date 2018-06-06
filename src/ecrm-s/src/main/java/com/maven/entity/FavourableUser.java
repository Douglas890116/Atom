package com.maven.entity;

import java.util.Date;

public class FavourableUser {
    private String lsh;

    private String favourableid;

    public FavourableUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FavourableUser(String lsh, String favourableid, String enterprisecode, String employeecode) {
		super();
		this.lsh = lsh;
		this.favourableid = favourableid;
		this.enterprisecode = enterprisecode;
		this.employeecode = employeecode;
	}

	private String enterprisecode;

    private String employeecode;

    //视图名称
    private String favourablename;//优惠名称
    private String loginaccount;//会员账号
    private Integer isonce;
    private Double lsbs;
    private Date starttime;
    private Date endtime;
    private Integer isdeault;
    private Integer status;

    public String getLsh() {
        return lsh;
    }

    public void setLsh(String lsh) {
        this.lsh = lsh;
    }

    public String getFavourableid() {
        return favourableid;
    }

    public void setFavourableid(String favourableid) {
        this.favourableid = favourableid;
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

	public String getFavourablename() {
		return favourablename;
	}

	public void setFavourablename(String favourablename) {
		this.favourablename = favourablename;
	}

	public String getLoginaccount() {
		return loginaccount;
	}

	public void setLoginaccount(String loginaccount) {
		this.loginaccount = loginaccount;
	}

	public Integer getIsonce() {
		return isonce;
	}

	public void setIsonce(Integer isonce) {
		this.isonce = isonce;
	}

	public Double getLsbs() {
		return lsbs;
	}

	public void setLsbs(Double lsbs) {
		this.lsbs = lsbs;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public Integer getIsdeault() {
		return isdeault;
	}

	public void setIsdeault(Integer isdeault) {
		this.isdeault = isdeault;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}