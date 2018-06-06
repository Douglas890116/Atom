package com.maven.entity;

public class PaymentType {
	/**
	 * 支付编码
	 */
    private String paymenttypecode;

    /**
     * 支付方式
     */
    private String paymenttype;
    
    public enum Enum_PayType{
    	第三方即时支付("PM01","第三方即时支付"),
    	银行卡转账("PM02","银行卡转账"),
    	;
    	
    	public String value;
    	public String desc;
    	private Enum_PayType(String _value,String _desc){
    		this.value=_value;
    		this.desc=_desc;
    	}
    	
    }

    public String getPaymenttypecode() {
        return paymenttypecode;
    }

    public void setPaymenttypecode(String paymenttypecode) {
        this.paymenttypecode = paymenttypecode;
    }

    public String getPaymenttype() {
        return paymenttype;
    }

    public void setPaymenttype(String paymenttype) {
        this.paymenttype = paymenttype;
    }
}