package com.maven.entity;

import java.util.Date;

public class EmployeeApiAccoutPasswordJob {
    private Integer lsh;

    private String employeecode;

    private String loginaccount;

    private String gametype;

    private String gameaccount;

    private String gamepassword;
    
    private String enterprisecode;

    private Date createtime;

    private Date updatetime;

    private Integer updatestatus;//0待处理/1已处理/2处理失败

    private String updatecomment;

    public Integer getLsh() {
        return lsh;
    }

    public void setLsh(Integer lsh) {
        this.lsh = lsh;
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
        return gamepassword;
    }

    public void setGamepassword(String gamepassword) {
        this.gamepassword = gamepassword;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getUpdatestatus() {
        return updatestatus;
    }

    public void setUpdatestatus(Integer updatestatus) {
        this.updatestatus = updatestatus;
    }

    public String getUpdatecomment() {
        return updatecomment;
    }

    public void setUpdatecomment(String updatecomment) {
        this.updatecomment = updatecomment;
    }

	public String getEnterprisecode() {
		return enterprisecode;
	}

	public void setEnterprisecode(String enterprisecode) {
		this.enterprisecode = enterprisecode;
	}
}