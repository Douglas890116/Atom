package com.maven.entity;

import java.io.Serializable;

public class BankCardsBlacklist implements Serializable {
	/** =-=-=-= */
	private static final long serialVersionUID = 1L;

	private Integer lsh;
	
	private String username;
	
	private String bankcard;
	
	private String phoneno;
	
	private String remark;

	public Integer getLsh() {
		return lsh;
	}

	public void setLsh(Integer lsh) {
		this.lsh = lsh;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getBankcard() {
		return bankcard;
	}

	public void setBankcard(String bankcard) {
		this.bankcard = bankcard;
	}

	public String getPhoneno() {
		return phoneno;
	}

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 判断信息是否存在
	 * @param info
	 * @return
	 */
	public boolean isExist(String info) {
		if (info == null) return false;
		if (info.equals(this.username)) return true;
		if (info.equals(this.bankcard)) return true;
		if (info.equals(this.phoneno)) return true;
		return false;
	}
}