package com.maven.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class EnterprisePaymentMethodConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 企业编码
	 */
	private String enterpriseCode;
	/**
	 * 出款方式
	 */
	private String withdralway;
	/**
	 * 出款上线
	 */
	private BigDecimal upperlimit;
	
	public enum Enum_paymentMethod{
		系统自动出款("1","系统自动出款"),
		财务手动出款("2","财务手动出款");
		public String value;
		public String desc;
		private Enum_paymentMethod(String value,String desc){
			this.value = value;
			this.desc = desc;
		}
	}
	
	public String getEnterpriseCode() {
		return enterpriseCode;
	}
	public void setEnterpriseCode(String enterpriseCode) {
		this.enterpriseCode = enterpriseCode;
	}
	public String getWithdralway() {
		return withdralway;
	}
	public void setWithdralway(String withdralway) {
		this.withdralway = withdralway;
	}
	public BigDecimal getUpperlimit() {
		return upperlimit;
	}
	public void setUpperlimit(BigDecimal upperlimit) {
		this.upperlimit = upperlimit;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((enterpriseCode == null) ? 0 : enterpriseCode.hashCode());
		result = prime * result + ((upperlimit == null) ? 0 : upperlimit.hashCode());
		result = prime * result + ((withdralway == null) ? 0 : withdralway.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EnterprisePaymentMethodConfig other = (EnterprisePaymentMethodConfig) obj;
		if (enterpriseCode == null) {
			if (other.enterpriseCode != null)
				return false;
		} else if (!enterpriseCode.equals(other.enterpriseCode))
			return false;
		if (upperlimit == null) {
			if (other.upperlimit != null)
				return false;
		} else if (!upperlimit.equals(other.upperlimit))
			return false;
		if (withdralway == null) {
			if (other.withdralway != null)
				return false;
		} else if (!withdralway.equals(other.withdralway))
			return false;
		return true;
	}
}
