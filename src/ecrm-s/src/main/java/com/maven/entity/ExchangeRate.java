package com.maven.entity;

import java.io.Serializable;
import java.util.Date;

public class ExchangeRate implements Serializable{
	
	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;
	/**
	 * 主键，自增
	 */
	private Integer id;
	/**
	 * 货币名称
	 */
	private String currencyname;
	/**
	 * 货币英文代码
	 */
	private String currencycode;
	/**
	 * 对人民币汇率
	 */
	private Double exchangerate;
	/**
	 * 操作时间
	 */
	private Date oprationtime;
	/**
	 * 操作者
	 */
	private String oprationperson;
	/**
	 * 加密
	 */
	 private String sign;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCurrencyname() {
		return currencyname;
	}
	public void setCurrencyname(String currencyname) {
		this.currencyname = currencyname;
	}
	public String getCurrencycode() {
		return currencycode;
	}
	public void setCurrencycode(String currencycode) {
		this.currencycode = currencycode;
	}
	public Double getExchangerate() {
		return exchangerate;
	}
	public void setExchangerate(Double exchangerate) {
		this.exchangerate = exchangerate;
	}
	public Date getOprationtime() {
		return oprationtime;
	}
	public void setOprationtime(Date oprationtime) {
		this.oprationtime = oprationtime;
	}
	public String getOprationperson() {
		return oprationperson;
	}
	public void setOprationperson(String oprationperson) {
		this.oprationperson = oprationperson;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}

}
