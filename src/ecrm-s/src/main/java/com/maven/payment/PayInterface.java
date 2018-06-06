package com.maven.payment;

public interface PayInterface <M,O>{
	
	public static final String  PAY_SUCCESS = "success";

	/**
	 * 第三方支付存款接口
	 * @param merchant
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public String save(M merchant,O order)throws Exception;
	
	/**
	 * 第三方支付付款接口
	 * @param merchant
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public String pay(M merchant,O order)throws Exception;
	
}
