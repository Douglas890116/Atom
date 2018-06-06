package com.maven.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Model class of activity_raffle_record.
 * 
 * @author generated by ERMaster
 * @version $Id$
 */
public class ActivityRaffleRecord implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 主键. */
	private Integer rafflecontrolcode;

	/** 用户编码. */
	private String employeecode;

	/** 用户上级编码. */
	private String parentemployeecode;

	/** 用户账号. */
	private String loginaccount;

	/** 抽奖时间. */
	private Date reffletime;

	/** 中奖奖项. */
	private String reffleprizes;
	
	/** 用户唯一浏览器识别码. */
	private String fingerprintcode;
	/** 登录IP. */
	private String loginip;
	
	/**
	 * 活动编号
	 */
	private int ecactivitycode;//

	/**
	 * Constructor.
	 */
	public ActivityRaffleRecord(String employeecode,String parentemployeecode,String loginaccount,String reffleprizes, String fingerprintcode,String loginip, int ecactivitycode) {
		this.employeecode = employeecode;
		this.parentemployeecode = parentemployeecode;
		this.loginaccount = loginaccount;
		this.reffletime = new Date();
		this.reffleprizes = reffleprizes;
		this.fingerprintcode = fingerprintcode;
		this.loginip = loginip;
		this.ecactivitycode = ecactivitycode;
	}
	
	public ActivityRaffleRecord() {
	}

	/**
	 * Set the 主键.
	 * 
	 * @param rafflecontrolcode
	 *            主键
	 */
	public void setRafflecontrolcode(Integer rafflecontrolcode) {
		this.rafflecontrolcode = rafflecontrolcode;
	}

	/**
	 * Get the 主键.
	 * 
	 * @return 主键
	 */
	public Integer getRafflecontrolcode() {
		return this.rafflecontrolcode;
	}

	/**
	 * Set the 用户编码.
	 * 
	 * @param employeecode
	 *            用户编码
	 */
	public void setEmployeecode(String employeecode) {
		this.employeecode = employeecode;
	}

	/**
	 * Get the 用户编码.
	 * 
	 * @return 用户编码
	 */
	public String getEmployeecode() {
		return this.employeecode;
	}

	/**
	 * Set the 用户上级编码.
	 * 
	 * @param parentemployeecode
	 *            用户上级编码
	 */
	public void setParentemployeecode(String parentemployeecode) {
		this.parentemployeecode = parentemployeecode;
	}

	/**
	 * Get the 用户上级编码.
	 * 
	 * @return 用户上级编码
	 */
	public String getParentemployeecode() {
		return this.parentemployeecode;
	}

	/**
	 * Set the 用户账号.
	 * 
	 * @param loginaccount
	 *            用户账号
	 */
	public void setLoginaccount(String loginaccount) {
		this.loginaccount = loginaccount;
	}

	/**
	 * Get the 用户账号.
	 * 
	 * @return 用户账号
	 */
	public String getLoginaccount() {
		return this.loginaccount;
	}

	/**
	 * Set the 抽奖时间.
	 * 
	 * @param reffletime
	 *            抽奖时间
	 */
	public void setReffletime(Date reffletime) {
		this.reffletime = reffletime;
	}

	/**
	 * Get the 抽奖时间.
	 * 
	 * @return 抽奖时间
	 */
	public Date getReffletime() {
		return this.reffletime;
	}

	/**
	 * Set the 中奖奖项.
	 * 
	 * @param reffleprizes
	 *            中奖奖项
	 */
	public void setReffleprizes(String reffleprizes) {
		this.reffleprizes = reffleprizes;
	}

	/**
	 * Get the 中奖奖项.
	 * 
	 * @return 中奖奖项
	 */
	public String getReffleprizes() {
		return this.reffleprizes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rafflecontrolcode == null) ? 0 : rafflecontrolcode.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ActivityRaffleRecord other = (ActivityRaffleRecord) obj;
		if (rafflecontrolcode == null) {
			if (other.rafflecontrolcode != null) {
				return false;
			}
		} else if (!rafflecontrolcode.equals(other.rafflecontrolcode)) {
			return false;
		}
		return true;
	}
	
	/**
	 * API返回brand参数对象
	 * @author king
	 *
	 */
	public class ApiRaffleTimePrize{
		private String raffletime; //参数介绍
		private String raffleprizes; //参数名称
		public String getRaffletime() {
			return raffletime;
		}
		public void setRaffletime(String raffletime) {
			this.raffletime = raffletime;
		}
		public String getRaffleprizes() {
			return raffleprizes;
		}
		public void setRaffleprizes(String raffleprizes) {
			this.raffleprizes = raffleprizes;
		}
		
	}

	public String getFingerprintcode() {
		return fingerprintcode;
	}

	public void setFingerprintcode(String fingerprintcode) {
		this.fingerprintcode = fingerprintcode;
	}

	public String getLoginip() {
		return loginip;
	}

	public void setLoginip(String loginip) {
		this.loginip = loginip;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getEcactivitycode() {
		return ecactivitycode;
	}

	public void setEcactivitycode(int ecactivitycode) {
		this.ecactivitycode = ecactivitycode;
	}

}
