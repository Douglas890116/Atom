package com.maven.entity;

import java.io.Serializable;

public class ThirdpartyPaymentBank  implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String bankcode;

    private String thirdpartypaymenttypecode;

    private String paymenttypebankcode;

    private String enable;
    
    private String sign;
    
	private String bankname;
    
    private String thirdpartypaymenttypename;
    
    
    public enum Enum_enable{
		启用("1","启用"),
		禁用("2","禁用");
		public String value;
		public String desc;
		private Enum_enable(String value ,String desc) {
        	this.value = value;
        	this.desc = desc;
        }
	}
	
    
    public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public String getThirdpartypaymenttypename() {
		return thirdpartypaymenttypename;
	}

	public void setThirdpartypaymenttypename(String thirdpartypaymenttypename) {
		this.thirdpartypaymenttypename = thirdpartypaymenttypename;
	}
    
    public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    public String getThirdpartypaymenttypecode() {
        return thirdpartypaymenttypecode;
    }

    public void setThirdpartypaymenttypecode(String thirdpartypaymenttypecode) {
        this.thirdpartypaymenttypecode = thirdpartypaymenttypecode;
    }

    public String getPaymenttypebankcode() {
        return paymenttypebankcode;
    }

    public void setPaymenttypebankcode(String paymenttypebankcode) {
        this.paymenttypebankcode = paymenttypebankcode;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }
}