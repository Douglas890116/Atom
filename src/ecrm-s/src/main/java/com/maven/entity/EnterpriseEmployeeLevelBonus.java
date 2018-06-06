package com.maven.entity;

import java.math.BigDecimal;

public class EnterpriseEmployeeLevelBonus {
    private String employeelevelcode;

    private String gametype;

    private String categorytype;

    

	private BigDecimal bonus;
	
	public EnterpriseEmployeeLevelBonus() {
		super();
	}

    public String getEmployeelevelcode() {
        return employeelevelcode;
    }

    public void setEmployeelevelcode(String employeelevelcode) {
        this.employeelevelcode = employeelevelcode;
    }

    public String getGametype() {
        return gametype;
    }

    public void setGametype(String gametype) {
        this.gametype = gametype;
    }

    public String getCategorytype() {
        return categorytype;
    }

    public void setCategorytype(String categorytype) {
        this.categorytype = categorytype;
    }

    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }
    public EnterpriseEmployeeLevelBonus(String employeelevelcode, String gametype, String categorytype,
			BigDecimal bonus) {
		super();
		this.employeelevelcode = employeelevelcode;
		this.gametype = gametype;
		this.categorytype = categorytype;
		this.bonus = bonus;
	}
}