package com.maven.payment.sdp2p;

/**
 * SD微信支付
 * @author Administrator
 *
 */
public class SDP2POrderConfig {
	
	private String userName = "";
	private String orderNo = "";
	private String money = "";
	private String bank = "";
	
	private String sPlayersId;//用户编号
	
	public SDP2POrderConfig() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getOrderNo() {
		return orderNo;
	}


	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}


	public String getMoney() {
		return money;
	}


	public void setMoney(String money) {
		this.money = money;
	}


	public String getBank() {
		return bank;
	}


	public void setBank(String bank) {
		this.bank = bank;
	}


	public String getsPlayersId() {
		return sPlayersId;
	}


	public void setsPlayersId(String sPlayersId) {
		this.sPlayersId = sPlayersId;
	}


	
}
