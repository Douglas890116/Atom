package com.maven.entity;

public class EnterpriseInformationQrcode {
    private Integer lsh;

    private String enterprisecode;

    private String brandcode;

    private Integer qrtype;

    private String qrurl;

    private String qraccountno;

    private String qraccountname;

    private Integer status;

    private String sign;
    
    public enum Enum_status{
    	正常(0,"启用"),
    	禁用(1,"禁用");
    	public Integer value;
    	public String desc;
    	private Enum_status(Integer _value,String _desc){
    		this.value = _value;
    		this.desc = _desc;
    	}
    }
    public enum Enum_qrtype{
    	微信收款(1,"微信收款"),
    	二维码收款(2,"二维码收款");
    	public Integer value;
    	public String desc;
    	private Enum_qrtype(Integer _value,String _desc){
    		this.value = _value;
    		this.desc = _desc;
    	}
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

    public Integer getQrtype() {
        return qrtype;
    }

    public void setQrtype(Integer qrtype) {
        this.qrtype = qrtype;
    }

    public String getQrurl() {
        return qrurl;
    }

    public void setQrurl(String qrurl) {
        this.qrurl = qrurl;
    }

    public String getQraccountno() {
        return qraccountno;
    }

    public void setQraccountno(String qraccountno) {
        this.qraccountno = qraccountno;
    }

    public String getQraccountname() {
        return qraccountname;
    }

    public void setQraccountname(String qraccountname) {
        this.qraccountname = qraccountname;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}