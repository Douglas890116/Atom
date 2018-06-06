package com.maven.payment;

public class PayResult {

	public PayResult(boolean result,String paytype,String description){
		this.result=result;
		this.paytype=paytype;
		this.description=description;
	}
	/**
	 * 支付结果
	 */
	private boolean result;
	
	/**
	 * 支付类型
	 */
	private String paytype;
	
	/**
	 * 支付描述
	 */
	private String description;

	public boolean getResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getPaytype() {
		return paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
