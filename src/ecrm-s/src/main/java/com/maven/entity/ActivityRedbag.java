package com.maven.entity;

import java.util.Date;

public class ActivityRedbag {
    private String lsh;

    private String enterprisecode;

    private String employeecode;

    private String loginaccount;

    private Double money;

    private String logindate;

    private String loginip;
    
    /**
     * 浏览器唯一标识
     */
    private String fingerprintcode;
    
    private int redbagtype;

    private Date createtime;
    private int status;
    
    private Date audittime;
    private String auditer;
    private Date paytyime;
    private String auditremark;
    private String payer;
    private String lsbs;
    private String ectivityname;
    private String enterprisebrandactivitycode;
    
    public enum Enum_redbagtype{
    	签到红包(1,"签到红包"),//每天一次
    	注册红包(2,"注册红包"),//没人一次
    	完善资料领红包(3,"完善资料领红包");//没人一次
		public Integer value;
		public String desc;
		
		private Enum_redbagtype(Integer value,String desc){
			this.value=value;
			this.desc=desc;
		}
	}
    
    public enum Enum_status{
    	待审核(1,"待审核"),
    	审核通过(2,"审核通过"),
    	审核拒绝(3,"审核拒绝"),
    	已发放(4,"已发放");
		public Integer value;
		public String desc;
		
		private Enum_status(Integer value,String desc){
			this.value=value;
			this.desc=desc;
		}
	}
    

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

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getLogindate() {
        return logindate;
    }

    public void setLogindate(String logindate) {
        this.logindate = logindate;
    }

    public String getLoginip() {
        return loginip;
    }

    public void setLoginip(String loginip) {
        this.loginip = loginip;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getAudittime() {
		return audittime;
	}

	public void setAudittime(Date audittime) {
		this.audittime = audittime;
	}

	public String getAuditer() {
		return auditer;
	}

	public void setAuditer(String auditer) {
		this.auditer = auditer;
	}

	public Date getPaytyime() {
		return paytyime;
	}

	public void setPaytyime(Date paytyime) {
		this.paytyime = paytyime;
	}

	public String getAuditremark() {
		return auditremark;
	}

	public void setAuditremark(String auditremark) {
		this.auditremark = auditremark;
	}

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	public String getLsbs() {
		return lsbs;
	}

	public void setLsbs(String lsbs) {
		this.lsbs = lsbs;
	}

	public String getEctivityname() {
		return ectivityname;
	}

	public void setEctivityname(String ectivityname) {
		this.ectivityname = ectivityname;
	}

	public String getEnterprisebrandactivitycode() {
		return enterprisebrandactivitycode;
	}

	public void setEnterprisebrandactivitycode(String enterprisebrandactivitycode) {
		this.enterprisebrandactivitycode = enterprisebrandactivitycode;
	}

	public int getRedbagtype() {
		return redbagtype;
	}

	public void setRedbagtype(int redbagtype) {
		this.redbagtype = redbagtype;
	}

	public String getFingerprintcode() {
		return fingerprintcode;
	}

	public void setFingerprintcode(String fingerprintcode) {
		this.fingerprintcode = fingerprintcode;
	}
}