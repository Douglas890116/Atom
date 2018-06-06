package com.maven.payment.lfdf;

/**
 * 
 * @author Administrator
 *
 */
public class LfDFOrderConfig {
	
	/*******银行类型******/
	private String bankCode = "";

	/*******金额  （单位元）******/
	private double paymoney;
	
	/*******订单号******/
	private String ordernumber;
	
	/*******请求时间 YYMMDDHHmmss******/
	private String requestTime;
	
	private String banksn = "";//银行编码
	private String banksitename = "";//开户网点，如果非空，最多80个汉字
	private String bankaccountname = "";//开户名
	private String bankaccountno = "";//银行账号
	private String bustype = "11";//00:对公 11:对私（不填默认对私,暂时只对私）
	private String bankmobile_no = "";//银行帐号预留手机号码
	private String bankprovince = "";//银行卡省份
	private String bankcity = "";//银行卡城市
	
	public LfDFOrderConfig() {
		super();
		// TODO Auto-generated constructor stub
	}


	public double getPaymoney() {
		return paymoney;
	}

	public void setPaymoney(double paymoney) {
		this.paymoney = paymoney;
	}


	public String getOrdernumber() {
		return ordernumber;
	}

	public void setOrdernumber(String ordernumber) {
		this.ordernumber = ordernumber;
	}


	public String getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}




	public String getBanksn() {
		return banksn;
	}


	public void setBanksn(String banksn) {
		this.banksn = banksn;
	}


	public String getBanksitename() {
		return banksitename;
	}


	public void setBanksitename(String banksitename) {
		this.banksitename = banksitename;
	}


	public String getBankaccountname() {
		return bankaccountname;
	}


	public void setBankaccountname(String bankaccountname) {
		this.bankaccountname = bankaccountname;
	}


	public String getBankaccountno() {
		return bankaccountno;
	}


	public void setBankaccountno(String bankaccountno) {
		this.bankaccountno = bankaccountno;
	}


	public String getBustype() {
		return bustype;
	}


	public void setBustype(String bustype) {
		this.bustype = bustype;
	}


	public String getBankmobile_no() {
		return bankmobile_no;
	}


	public void setBankmobile_no(String bankmobile_no) {
		this.bankmobile_no = bankmobile_no;
	}


	public String getBankprovince() {
		return bankprovince;
	}


	public void setBankprovince(String bankprovince) {
		this.bankprovince = bankprovince;
	}


	public String getBankcity() {
		return bankcity;
	}


	public void setBankcity(String bankcity) {
		this.bankcity = bankcity;
	}
	

	
}
