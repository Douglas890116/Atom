package com.maven.entity;

import java.math.BigDecimal;

public class IntegralSetting {
    private Integer lsh;

    private String enterprisecode;

    private String brandcode;

    private String gametype;

    private String gamebigtype;

    private BigDecimal m2iRate;

    private BigDecimal i2mRate;

	private Integer timeoutday;
    

	//
    private String keyname;
    
    public IntegralSetting() {
		super();
		
	}
    public IntegralSetting(String enterprisecode, String brandcode, String gametype, String gamebigtype,String keyname) {
			
		super();
		this.enterprisecode = enterprisecode;
		this.brandcode = brandcode;
		this.gametype = gametype;
		this.gamebigtype = gamebigtype;
		this.keyname = keyname;
		
		this.m2iRate = new BigDecimal(0);
		this.i2mRate = new BigDecimal(0);
		this.timeoutday = 1;
		
	}
    public IntegralSetting(String enterprisecode, String brandcode, String gametype, String gamebigtype,BigDecimal m2iRate, BigDecimal i2mRate, Integer timeoutday) {
		super();
		this.enterprisecode = enterprisecode;
		this.brandcode = brandcode;
		this.gametype = gametype;
		this.gamebigtype = gamebigtype;
		this.m2iRate = m2iRate;
		this.i2mRate = i2mRate;
		this.timeoutday = timeoutday;
	}
    

    public Integer getLsh() {
        return lsh;
    }

    public void setLsh(Integer lsh) {
        this.lsh = lsh;
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

    public String getGametype() {
        return gametype;
    }

    public void setGametype(String gametype) {
        this.gametype = gametype;
    }

    public String getGamebigtype() {
        return gamebigtype;
    }

    public void setGamebigtype(String gamebigtype) {
        this.gamebigtype = gamebigtype;
    }

    public BigDecimal getM2iRate() {
        return m2iRate;
    }

    public void setM2iRate(BigDecimal m2iRate) {
        this.m2iRate = m2iRate;
    }

    public BigDecimal getI2mRate() {
        return i2mRate;
    }

    public void setI2mRate(BigDecimal i2mRate) {
        this.i2mRate = i2mRate;
    }

    public Integer getTimeoutday() {
        return timeoutday;
    }

    public void setTimeoutday(Integer timeoutday) {
        this.timeoutday = timeoutday;
    }

	public String getKeyname() {
		return keyname;
	}

	public void setKeyname(String keyname) {
		this.keyname = keyname;
	}
}