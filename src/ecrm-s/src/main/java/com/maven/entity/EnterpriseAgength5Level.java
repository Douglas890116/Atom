package com.maven.entity;

import java.math.BigDecimal;

public class EnterpriseAgength5Level {
    private String employeelevelcode;

    private String employeelevelname;

    private String enterprisecode;

    private BigDecimal upperlevelRate;

    private BigDecimal upperlevelRate2;

    private Integer personcount;

    private Integer agengtcount;

    private BigDecimal orderamount;

    private Integer datastatus;

    private Integer ord;
    
    //视图
    private String sign;

    public String getEmployeelevelcode() {
        return employeelevelcode;
    }

    public void setEmployeelevelcode(String employeelevelcode) {
        this.employeelevelcode = employeelevelcode;
    }


    public String getEnterprisecode() {
        return enterprisecode;
    }

    public void setEnterprisecode(String enterprisecode) {
        this.enterprisecode = enterprisecode;
    }

    public BigDecimal getUpperlevelRate() {
        return upperlevelRate;
    }

    public void setUpperlevelRate(BigDecimal upperlevelRate) {
        this.upperlevelRate = upperlevelRate;
    }

    public BigDecimal getUpperlevelRate2() {
        return upperlevelRate2;
    }

    public void setUpperlevelRate2(BigDecimal upperlevelRate2) {
        this.upperlevelRate2 = upperlevelRate2;
    }

    public Integer getPersoncount() {
        return personcount;
    }

    public void setPersoncount(Integer personcount) {
        this.personcount = personcount;
    }

    public Integer getAgengtcount() {
        return agengtcount;
    }

    public void setAgengtcount(Integer agengtcount) {
        this.agengtcount = agengtcount;
    }

    public BigDecimal getOrderamount() {
        return orderamount;
    }

    public void setOrderamount(BigDecimal orderamount) {
        this.orderamount = orderamount;
    }

    public Integer getDatastatus() {
        return datastatus;
    }

    public void setDatastatus(Integer datastatus) {
        this.datastatus = datastatus;
    }

    public Integer getOrd() {
        return ord;
    }

    public void setOrd(Integer ord) {
        this.ord = ord;
    }

	public String getEmployeelevelname() {
		return employeelevelname;
	}

	public void setEmployeelevelname(String employeelevelname) {
		this.employeelevelname = employeelevelname;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}