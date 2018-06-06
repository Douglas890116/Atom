package com.maven.entity;

public class EnterpriseGame {
	/** 主键 */
    private Integer code;
    /** 企业编码 */
    private String enterprisecode;
    /** 游戏编码 */
    private String gamecode;
    
    
    
    
    
    
    //////
    private String enterprisename;
    private String gametype;
    private String gamename;
    private String sign;
    
    
    
    public EnterpriseGame(){}
    
    public EnterpriseGame(Integer code,String enterprisecode,String gamecode){
    	this.code = code;
    	this.enterprisecode = enterprisecode;
    	this.gamecode = gamecode;
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

    public String getGamecode() {
        return gamecode;
    }

    public void setGamecode(String gamecode) {
        this.gamecode = gamecode;
    }

	public String getEnterprisename() {
		return enterprisename;
	}

	public void setEnterprisename(String enterprisename) {
		this.enterprisename = enterprisename;
	}

	public String getGametype() {
		return gametype;
	}

	public void setGametype(String gametype) {
		this.gametype = gametype;
	}

	public String getGamename() {
		return gamename;
	}

	public void setGamename(String gamename) {
		this.gamename = gamename;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	
}