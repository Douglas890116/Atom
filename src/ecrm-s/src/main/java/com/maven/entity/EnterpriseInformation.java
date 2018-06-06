package com.maven.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Model class of enterprise_information.
 * 
 * @author generated by ERMaster
 * @version $Id$
 */
public class EnterpriseInformation implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 企业信息编码. */
	private String enterpriseinformationcode;
	
	/** 企业编码 */
	private String enterprisecode;

	/** 品牌编码 */
	private String brandcode;

	/** 银行代码. */
	private String bankcode;

	/** 账号或地址. */
	private String openningaccount;

	/** 开户名. */
	private String accountname;

	/** 支行信息. */
	private String openningbank;

	/** 企业联系人. */
	private String enterprisecontact;

	/** 企业联系人电话. */
	private String enterprisephone;

	/** 企业联系人QQ. */
	private String enterpriseqq;

	/** 企业联系人邮箱. */
	private String enterpriseemail;
	
	/** 当前余额 */
	private BigDecimal currentbalance;
	
	/** 是否启用 */
	private Byte enable;
	
	/* 唯一编码  */
	private String sign;
	/* 视图关联属性  银行名称*/
	private String bankname;

	public enum Enum_enable{
		启用("1","启用"),
		禁用("0","禁用");
		public String value;
		public String desc;
		private Enum_enable(String value,String desc){
			this.value=value;
			this.desc=desc;
		}
	}

	/**
	 * Constructor.
	 */
	public EnterpriseInformation() {
	}

	/**
	 * Set the 企业信息编码.
	 * 
	 * @param enterpriseinformationcode
	 *            企业信息编码
	 */
	public void setEnterpriseinformationcode(String enterpriseinformationcode) {
		this.enterpriseinformationcode = enterpriseinformationcode;
	}

	/**
	 * Get the 企业信息编码.
	 * 
	 * @return 企业信息编码
	 */
	public String getEnterpriseinformationcode() {
		return this.enterpriseinformationcode;
	}


	public String getEnterprisecode() {
		return enterprisecode;
	}

	public void setEnterprisecode(String enterprisecode) {
		this.enterprisecode = enterprisecode;
	}

	/**
	 * Set the 银行代码.
	 * 
	 * @param bankcode
	 *            银行代码
	 */
	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}

	/**
	 * Get the 银行代码.
	 * 
	 * @return 银行代码
	 */
	public String getBankcode() {
		return this.bankcode;
	}

	/**
	 * Set the 账号或地址.
	 * 
	 * @param openningaccount
	 *            账号或地址
	 */
	public void setOpenningaccount(String openningaccount) {
		this.openningaccount = openningaccount;
	}

	/**
	 * Get the 账号或地址.
	 * 
	 * @return 账号或地址
	 */
	public String getOpenningaccount() {
		return this.openningaccount;
	}

	/**
	 * Set the 开户名.
	 * 
	 * @param accountname
	 *            开户名
	 */
	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	/**
	 * Get the 开户名.
	 * 
	 * @return 开户名
	 */
	public String getAccountname() {
		return this.accountname;
	}

	/**
	 * Set the 开户行.
	 * 
	 * @param openningbank
	 *            开户行
	 */
	public void setOpenningbank(String openningbank) {
		this.openningbank = openningbank;
	}

	/**
	 * Get the 开户行.
	 * 
	 * @return 开户行
	 */
	public String getOpenningbank() {
		return this.openningbank;
	}

	/**
	 * Set the 企业联系人.
	 * 
	 * @param enterprisecontact
	 *            企业联系人
	 */
	public void setEnterprisecontact(String enterprisecontact) {
		this.enterprisecontact = enterprisecontact;
	}

	/**
	 * Get the 企业联系人.
	 * 
	 * @return 企业联系人
	 */
	public String getEnterprisecontact() {
		return this.enterprisecontact;
	}

	/**
	 * Set the 企业联系人电话.
	 * 
	 * @param enterprisephone
	 *            企业联系人电话
	 */
	public void setEnterprisephone(String enterprisephone) {
		this.enterprisephone = enterprisephone;
	}

	/**
	 * Get the 企业联系人电话.
	 * 
	 * @return 企业联系人电话
	 */
	public String getEnterprisephone() {
		return this.enterprisephone;
	}

	/**
	 * Set the 企业联系人QQ.
	 * 
	 * @param enterpriseqq
	 *            企业联系人QQ
	 */
	public void setEnterpriseqq(String enterpriseqq) {
		this.enterpriseqq = enterpriseqq;
	}

	/**
	 * Get the 企业联系人QQ.
	 * 
	 * @return 企业联系人QQ
	 */
	public String getEnterpriseqq() {
		return this.enterpriseqq;
	}

	/**
	 * Set the 企业联系人邮箱.
	 * 
	 * @param enterpriseemail
	 *            企业联系人邮箱
	 */
	public void setEnterpriseemail(String enterpriseemail) {
		this.enterpriseemail = enterpriseemail;
	}

	/**
	 * Get the 企业联系人邮箱.
	 * 
	 * @return 企业联系人邮箱
	 */
	public String getEnterpriseemail() {
		return this.enterpriseemail;
	}

	public BigDecimal getCurrentbalance() {
		return currentbalance;
	}

	public void setCurrentbalance(BigDecimal currentbalance) {
		this.currentbalance = currentbalance;
	}

	public Byte getEnable() {
		return enable;
	}

	public void setEnable(Byte enable) {
		this.enable = enable;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((enterpriseinformationcode == null) ? 0 : enterpriseinformationcode.hashCode());
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
		EnterpriseInformation other = (EnterpriseInformation) obj;
		if (enterpriseinformationcode == null) {
			if (other.enterpriseinformationcode != null) {
				return false;
			}
		} else if (!enterpriseinformationcode.equals(other.enterpriseinformationcode)) {
			return false;
		}
		return true;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public String getBrandcode() {
		return brandcode;
	}

	public void setBrandcode(String brandcode) {
		this.brandcode = brandcode;
	}

}