package com.maven.entity;

import java.util.Date;

public class EnterpriseActivityPay {
	
    private Integer lsh;

    private String employeecode;

    private String enterprisecode;

    private String brandcode;

    private String parentemployeecode;

    private String loginaccount;

    private String acname;

    private Integer ecactivitycode;

    private Integer paytype;

    private Integer paystatus;

    private Double paymoneyaudit;

    private Double paymoneyreal;

    private Date createtime;

    private Date audittime;

    private String auditer;

    private Date paytyime;

    private String payer;

    private String auditremark;
    
    private String descs;//描述
    private String lsbs;//流水倍数
    
    private double depositmoney;
    
    public enum Enum_paystatus{
    	待审核(1,"待审核"),
		已审核(2,"已审核"),
		已支付(3,"已支付"),
    	已拒绝(4,"已拒绝");
		public Integer value;
		public String desc;
		
		private Enum_paystatus(Integer value,String desc){
			this.value=value;
			this.desc=desc;
		}
	}
    
    public enum Enum_paytype{
		自动发放(1,"自动核准"),
		人工发放(2,"人工核准");
		public Integer value;
		public String desc;
		
		private Enum_paytype(Integer value,String desc){
			this.value=value;
			this.desc=desc;
		}
	}

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

    public String getAcname() {
        return acname;
    }

    public void setAcname(String acname) {
        this.acname = acname;
    }

    public Integer getEcactivitycode() {
        return ecactivitycode;
    }

    public void setEcactivitycode(Integer ecactivitycode) {
        this.ecactivitycode = ecactivitycode;
    }

    public Integer getPaytype() {
        return paytype;
    }

    public void setPaytype(Integer paytype) {
        this.paytype = paytype;
    }

    public Integer getPaystatus() {
        return paystatus;
    }

    public void setPaystatus(Integer paystatus) {
        this.paystatus = paystatus;
    }

    public Double getPaymoneyaudit() {
        return paymoneyaudit;
    }

    public void setPaymoneyaudit(Double paymoneyaudit) {
        this.paymoneyaudit = paymoneyaudit;
    }

    public Double getPaymoneyreal() {
        return paymoneyreal;
    }

    public void setPaymoneyreal(Double paymoneyreal) {
        this.paymoneyreal = paymoneyreal;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
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

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getAuditremark() {
        return auditremark;
    }

    public void setAuditremark(String auditremark) {
        this.auditremark = auditremark;
    }

	public String getDescs() {
		return descs;
	}

	public void setDesc(String descs) {
		this.descs = descs;
	}

	public String getLsbs() {
		return lsbs;
	}

	public void setLsbs(String lsbs) {
		this.lsbs = lsbs;
	}

	public double getDepositmoney() {
		return depositmoney;
	}

	public void setDepositmoney(double depositmoney) {
		this.depositmoney = depositmoney;
	}
}