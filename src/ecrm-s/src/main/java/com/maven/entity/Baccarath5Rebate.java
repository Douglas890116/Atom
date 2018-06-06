package com.maven.entity;

import java.util.Date;
import java.util.UUID;

public class Baccarath5Rebate {
    private String lsh;

    private String patchno;

    private String ordernumber;

    private String loginaccount;

    private Double money;

    private Double orderamount;

    private Date orderdate;

    private String currenlevel;

    private String employeecode;

    private String parentemployeecode;

    private String enterprisecode;

    private String brandcode;

    private String employeecurrenlevel;

    private Double rebate;

    private Double rebatemoney;

    private Integer rebatestatus;

    private Date rebatetime;

    private String rebatedesc;

    private String remark;
    
    public enum Enum_rebatestatus{
    	待处理(1,"待处理"),
    	处理成功(2,"处理成功"),
		处理失败(3,"处理失败"),
		;
		public Integer value;
		public String desc;
		
		private Enum_rebatestatus(Integer value,String desc){
			this.value=value;
			this.desc=desc;
		}
	}
    
    public Baccarath5Rebate() {
		super();
	}
    public Baccarath5Rebate(String patchno, String ordernumber, String loginaccount, Double money, Double orderamount,
			String currenlevel, String employeecode, String parentemployeecode, String enterprisecode, String brandcode,
			String employeecurrenlevel, Double rebate, Double rebatemoney, Integer rebatestatus, String remark) {
		super();
		this.lsh = UUID.randomUUID().toString();
		this.orderdate = new Date();
		this.patchno = patchno;
		this.ordernumber = ordernumber;
		this.loginaccount = loginaccount;
		this.money = money;
		this.orderamount = orderamount;
		this.currenlevel = currenlevel;
		this.employeecode = employeecode;
		this.parentemployeecode = parentemployeecode;
		this.enterprisecode = enterprisecode;
		this.brandcode = brandcode;
		this.employeecurrenlevel = employeecurrenlevel;
		this.rebate = rebate;
		this.rebatemoney = rebatemoney;
		this.rebatestatus = rebatestatus;
		this.remark = remark;
	}

	public String getLsh() {
        return lsh;
    }

    public void setLsh(String lsh) {
        this.lsh = lsh;
    }

    public String getPatchno() {
        return patchno;
    }

    public void setPatchno(String patchno) {
        this.patchno = patchno;
    }

    public String getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
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

    public Double getOrderamount() {
        return orderamount;
    }

    public void setOrderamount(Double orderamount) {
        this.orderamount = orderamount;
    }

    public Date getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(Date orderdate) {
        this.orderdate = orderdate;
    }

    public String getCurrenlevel() {
        return currenlevel;
    }

    public void setCurrenlevel(String currenlevel) {
        this.currenlevel = currenlevel;
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

    public String getEmployeecurrenlevel() {
        return employeecurrenlevel;
    }

    public void setEmployeecurrenlevel(String employeecurrenlevel) {
        this.employeecurrenlevel = employeecurrenlevel;
    }

    public Double getRebate() {
        return rebate;
    }

    public void setRebate(Double rebate) {
        this.rebate = rebate;
    }

    public Double getRebatemoney() {
        return rebatemoney;
    }

    public void setRebatemoney(Double rebatemoney) {
        this.rebatemoney = rebatemoney;
    }

    public Integer getRebatestatus() {
        return rebatestatus;
    }

    public void setRebatestatus(Integer rebatestatus) {
        this.rebatestatus = rebatestatus;
    }

    public Date getRebatetime() {
        return rebatetime;
    }

    public void setRebatetime(Date rebatetime) {
        this.rebatetime = rebatetime;
    }

    public String getRebatedesc() {
        return rebatedesc;
    }

    public void setRebatedesc(String rebatedesc) {
        this.rebatedesc = rebatedesc;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}