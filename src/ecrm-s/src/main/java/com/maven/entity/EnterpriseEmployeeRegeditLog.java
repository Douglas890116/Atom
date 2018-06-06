package com.maven.entity;

import java.util.Date;

public class EnterpriseEmployeeRegeditLog {
    private String lsh;

    private String enterprisecode;

    private String employeecode;

    private String loginaccount;

    private String parentemployeeaccount;

    private Date createtime;

    private String ip;

    private String remark;
    
    /***工具字段，用于查询统计**/
    private int count;

    public String getLsh() {
        return lsh;
    }

    public void setLsh(String lsh) {
        this.lsh = lsh;
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

    public String getParentemployeeaccount() {
        return parentemployeeaccount;
    }

    public void setParentemployeeaccount(String parentemployeeaccount) {
        this.parentemployeeaccount = parentemployeeaccount;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}