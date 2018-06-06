package com.maven.payment.sdwx;

/**
 * SD微信支付
 * @author Administrator
 *
 */
public class SDWXOrderConfig {
	
	private String cmd6006 = "6006";//个人计算机: 6006 手机:6009 手机无插件: 6010
	private String cmd6009 = "6009";//个人计算机: 6006 手机:6009 手机无插件: 6010
	private String cmd6010 = "6010";//个人计算机: 6006 手机:6009 手机无插件: 6010
	
	private String paytype;//支付方式。由该参数决定cmd取值及付款地址payurl的取值
	
	private String order;
	private String username;
	private String money;
	private String time ;//2016-06-06 11:08:31
	private String remark = "remark";
	
	/**
	 * 以下是出款使用
	 */
	private String intoAccount;//转入卡号
	private String intoName;//转入姓名
	private String intoBank1;//转入银行
	private String intoBank2;
	private double intoAmount;//转入金额
	private String serialNumber;//单号
	
	
	public SDWXOrderConfig() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SDWXOrderConfig(String intoAccount, String intoName, String intoBank1, String intoBank2, double intoAmount,
			String serialNumber) {
		super();
		this.intoAccount = intoAccount;
		this.intoName = intoName;
		this.intoBank1 = intoBank1;
		this.intoBank2 = intoBank2;
		this.intoAmount = intoAmount;
		this.serialNumber = serialNumber;
	}
	
	
	public SDWXOrderConfig(String order, String username, String money, String time, String paytype) {
		super();
		this.order = order;
		this.username = username;
		this.money = money;
		this.time = time;
		this.paytype = paytype;
	}
	
	public String getCmd6006() {
		return cmd6006;
	}
	public void setCmd6006(String cmd6006) {
		this.cmd6006 = cmd6006;
	}
	public String getCmd6009() {
		return cmd6009;
	}
	public void setCmd6009(String cmd6009) {
		this.cmd6009 = cmd6009;
	}
	public String getCmd6010() {
		return cmd6010;
	}
	public void setCmd6010(String cmd6010) {
		this.cmd6010 = cmd6010;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPaytype() {
		return paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	public String getIntoAccount() {
		return intoAccount;
	}

	public void setIntoAccount(String intoAccount) {
		this.intoAccount = intoAccount;
	}

	public String getIntoName() {
		return intoName;
	}

	public void setIntoName(String intoName) {
		this.intoName = intoName;
	}

	public String getIntoBank1() {
		return intoBank1;
	}

	public void setIntoBank1(String intoBank1) {
		this.intoBank1 = intoBank1;
	}

	public String getIntoBank2() {
		return intoBank2;
	}

	public void setIntoBank2(String intoBank2) {
		this.intoBank2 = intoBank2;
	}

	public double getIntoAmount() {
		return intoAmount;
	}

	public void setIntoAmount(double intoAmount) {
		this.intoAmount = intoAmount;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	
}
