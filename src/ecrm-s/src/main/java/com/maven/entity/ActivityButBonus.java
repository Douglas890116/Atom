package com.maven.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Model class of activity_but_bonus.
 * 
 * @author generated by ERMaster
 * @version $Id$
 */
public class ActivityButBonus implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** lsh. */
	private Long lsh;

	/** createdate. */
	private Integer createdate;

	/** createtime. */
	private Date createtime;

	/** pay_type. */
	private String payType;

	/** enterprisebrandactivitycode. */
	private Integer enterprisebrandactivitycode;

	/** enterprisecode. */
	private String enterprisecode;

	/** brandcode. */
	private String brandcode;

	/** employeecode. */
	private String employeecode;

	/** loginaccount. */
	private String loginaccount;

	/** butmoney. */
	private Double butmoney;

	/** bonusrand. */
	private Double bonusrand;

	/** bonusmoney. */
	private Double bonusmoney;

	/** bonustime. */
	private Date bonustime;

	/** remark. */
	private String remark;
	
	/** status. */
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public enum Enum_Status{
		未领取("1","未领取"),
    	已领取("2","已领取")
    	;
    	
    	public String value;
    	public String desc;
    	private Enum_Status(String _value,String _desc){
    		this.value=_value;
    		this.desc=_desc;
    	}
    	
    }

	public enum Enum_PayType{
		日投注("1","日投注"),
    	周投注("2","周投注"),
    	月投注("3","月投注"),
    	季投注("4","季投注"),
    	年投注("5","年投注")
    	;
    	
    	public String value;
    	public String desc;
    	private Enum_PayType(String _value,String _desc){
    		this.value=_value;
    		this.desc=_desc;
    	}
    	
    }
	
	/**
	 * Constructor.
	 */
	public ActivityButBonus() {
	}

	/**
	 * Set the lsh.
	 * 
	 * @param lsh
	 *            lsh
	 */
	public void setLsh(Long lsh) {
		this.lsh = lsh;
	}

	/**
	 * Get the lsh.
	 * 
	 * @return lsh
	 */
	public Long getLsh() {
		return this.lsh;
	}

	/**
	 * Set the createdate.
	 * 
	 * @param createdate
	 *            createdate
	 */
	public void setCreatedate(Integer createdate) {
		this.createdate = createdate;
	}

	/**
	 * Get the createdate.
	 * 
	 * @return createdate
	 */
	public Integer getCreatedate() {
		return this.createdate;
	}

	/**
	 * Set the createtime.
	 * 
	 * @param createtime
	 *            createtime
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	/**
	 * Get the createtime.
	 * 
	 * @return createtime
	 */
	public Date getCreatetime() {
		return this.createtime;
	}

	/**
	 * Set the pay_type.
	 * 
	 * @param payType
	 *            pay_type
	 */
	public void setPayType(String payType) {
		this.payType = payType;
	}

	/**
	 * Get the pay_type.
	 * 
	 * @return pay_type
	 */
	public String getPayType() {
		return this.payType;
	}

	/**
	 * Set the enterprisebrandactivitycode.
	 * 
	 * @param enterprisebrandactivitycode
	 *            enterprisebrandactivitycode
	 */
	public void setEnterprisebrandactivitycode(Integer enterprisebrandactivitycode) {
		this.enterprisebrandactivitycode = enterprisebrandactivitycode;
	}

	/**
	 * Get the enterprisebrandactivitycode.
	 * 
	 * @return enterprisebrandactivitycode
	 */
	public Integer getEnterprisebrandactivitycode() {
		return this.enterprisebrandactivitycode;
	}

	/**
	 * Set the enterprisecode.
	 * 
	 * @param enterprisecode
	 *            enterprisecode
	 */
	public void setEnterprisecode(String enterprisecode) {
		this.enterprisecode = enterprisecode;
	}

	/**
	 * Get the enterprisecode.
	 * 
	 * @return enterprisecode
	 */
	public String getEnterprisecode() {
		return this.enterprisecode;
	}

	/**
	 * Set the brandcode.
	 * 
	 * @param brandcode
	 *            brandcode
	 */
	public void setBrandcode(String brandcode) {
		this.brandcode = brandcode;
	}

	/**
	 * Get the brandcode.
	 * 
	 * @return brandcode
	 */
	public String getBrandcode() {
		return this.brandcode;
	}

	/**
	 * Set the employeecode.
	 * 
	 * @param employeecode
	 *            employeecode
	 */
	public void setEmployeecode(String employeecode) {
		this.employeecode = employeecode;
	}

	/**
	 * Get the employeecode.
	 * 
	 * @return employeecode
	 */
	public String getEmployeecode() {
		return this.employeecode;
	}

	/**
	 * Set the loginaccount.
	 * 
	 * @param loginaccount
	 *            loginaccount
	 */
	public void setLoginaccount(String loginaccount) {
		this.loginaccount = loginaccount;
	}

	/**
	 * Get the loginaccount.
	 * 
	 * @return loginaccount
	 */
	public String getLoginaccount() {
		return this.loginaccount;
	}

	/**
	 * Set the butmoney.
	 * 
	 * @param butmoney
	 *            butmoney
	 */
	public void setButmoney(Double butmoney) {
		this.butmoney = butmoney;
	}

	/**
	 * Get the butmoney.
	 * 
	 * @return butmoney
	 */
	public Double getButmoney() {
		return this.butmoney;
	}

	/**
	 * Set the bonusrand.
	 * 
	 * @param bonusrand
	 *            bonusrand
	 */
	public void setBonusrand(Double bonusrand) {
		this.bonusrand = bonusrand;
	}

	/**
	 * Get the bonusrand.
	 * 
	 * @return bonusrand
	 */
	public Double getBonusrand() {
		return this.bonusrand;
	}

	/**
	 * Set the bonusmoney.
	 * 
	 * @param bonusmoney
	 *            bonusmoney
	 */
	public void setBonusmoney(Double bonusmoney) {
		this.bonusmoney = bonusmoney;
	}

	/**
	 * Get the bonusmoney.
	 * 
	 * @return bonusmoney
	 */
	public Double getBonusmoney() {
		return this.bonusmoney;
	}

	/**
	 * Set the bonustime.
	 * 
	 * @param bonustime
	 *            bonustime
	 */
	public void setBonustime(Date bonustime) {
		this.bonustime = bonustime;
	}

	/**
	 * Get the bonustime.
	 * 
	 * @return bonustime
	 */
	public Date getBonustime() {
		return this.bonustime;
	}

	/**
	 * Set the remark.
	 * 
	 * @param remark
	 *            remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * Get the remark.
	 * 
	 * @return remark
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lsh == null) ? 0 : lsh.hashCode());
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
		ActivityButBonus other = (ActivityButBonus) obj;
		if (lsh == null) {
			if (other.lsh != null) {
				return false;
			}
		} else if (!lsh.equals(other.lsh)) {
			return false;
		}
		return true;
	}

}
