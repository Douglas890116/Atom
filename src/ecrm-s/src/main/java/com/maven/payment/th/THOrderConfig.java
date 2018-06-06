package com.maven.payment.th;

public class THOrderConfig {

	/**
	 * 银行编码
	 */
	private String bankCode;
	
	/**
	 * 订单号
	 */
	private String orderNo;
	
	/**
	 * 订单金额
	 */
	private String orderAmount;
	
	/**
	 * 付款人电话号码
	 */
	private String customerPhone;
	/**
	 * 收货地址 
	 */
	private String receiveAddr;
	/**
	 * 产品名称
	 */
	private String productName;
	/**
	 * 订单数量
	 */
	private String productNum;
	/**
	 * 付款人IP
	 */
	private String customerIp;
	/**
	 * 银行卡开户姓名
	 */
	private String bankAccount;
	/**
	 * 银行卡号
	 */
	private String bankCardNo;
	
	
	 /**
     * 存款订单参数
     * @author Administrator
     *
     */
    public enum O_Save_Paramters{
    	银行编码("bankCode"),
    	订单号("orderNo"),
    	订单金额("orderAmount"),
    	存款用户IP("customerIp"),
    	;
		public String value;
		private O_Save_Paramters(String value){
			this.value=value;
		}
		public static String[] paramters(){
			O_Save_Paramters[] s = O_Save_Paramters.values();
			String[] ss = new String[s.length];
			for (int i = 0 ;i<s.length;i++) {
				ss[i] = s[i].value;
			}
			return ss;
		}
	}
    
    /**
     * 支付订单参数
     * @author Administrator
     *
     */
    public enum O_Pay_Paramters{
    	银行编码("bankCode"),
    	银行卡开户名("bankAccount"),
    	银行卡卡号("bankCardNo"),
    	订单金额("orderAmount"),
    	;
		public String value;
		private O_Pay_Paramters(String value){
			this.value=value;
		}
		public static String[] paramters(){
			O_Pay_Paramters[] s = O_Pay_Paramters.values();
			String[] ss = new String[s.length];
			for (int i = 0 ;i<s.length;i++) {
				ss[i] = s[i].value;
			}
			return ss;
		}
	}

	/**
	 * 银行编码
	 * @return 银行编码
	 */
	public String getBankCode() {
		return bankCode;
	}

	/**
	 * 银行编码 
	 * @param bankCode 银行编码
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * 订单编号
	 * @return
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * 订单编号
	 * @param orderNo
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * 订单金额
	 * @return
	 */
	public String getOrderAmount() {
		return orderAmount;
	}

	/**
	 * 订单金额
	 * @param orderAmount
	 */
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	/**
	 * 付款人电话号码
	 * @return
	 */
	public String getCustomerPhone() {
		return customerPhone;
	}

	/**
	 * 付款人电话号码
	 * @param customerPhone
	 */
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	/**
	 *  收货地址
	 * @return
	 */
	public String getReceiveAddr() {
		return receiveAddr;
	}

	/**
	 *  收货地址
	 * @param receiveAddr
	 */
	public void setReceiveAddr(String receiveAddr) {
		this.receiveAddr = receiveAddr;
	}

	/**
	 * 产品名称
	 * @return
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * 产品名称
	 * @param productName
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * 订单数量
	 * @return
	 */
	public String getProductNum() {
		return productNum;
	}

	/**
	 * 订单数量
	 * @param productNum
	 */
	public void setProductNum(String productNum) {
		this.productNum = productNum;
	}

	/**
	 * 付款人IP
	 * @return
	 */
	public String getCustomerIp() {
		return customerIp;
	}

	/**
	 * 付款人IP
	 * @param customerIp
	 */
	public void setCustomerIp(String customerIp) {
		this.customerIp = customerIp;
	}

	/**
	 * 银行卡开户姓名
	 * @return
	 */
	public String getBankAccount() {
		return bankAccount;
	}

	/**
	 * 银行卡开户姓名
	 * @param bankAccount
	 */
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	/**
	 * 银行卡号
	 * @return
	 */
	public String getBankCardNo() {
		return bankCardNo;
	}

	/**
	 * 银行卡号
	 * @param bankCardNo
	 */
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	
	
}
