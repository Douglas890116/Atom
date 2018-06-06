package com.maven.payment.jpay;

/**
 * 
 * @author Administrator
 *
 */
public class JOrderConfig {
	
	/*******支付类型 22 支付宝  30微信******/
	private String pay_type;

	/*******金额  （传递时需要为：单位 分）******/
	private double pay_amt;
	
	
	/*******商品名称******/
	private String goods_name = "A";
	
	/*******备注******/
	private String remark = "B";
	
	/*******订单号******/
	private String agent_bill_id;
	
	public JOrderConfig() {
		super();
		// TODO Auto-generated constructor stub
	}
	public JOrderConfig(String pay_type, double pay_amt, String agent_bill_id) {
		super();
		this.pay_type = pay_type;
		this.pay_amt = pay_amt;
		this.agent_bill_id = agent_bill_id;
	}
	public JOrderConfig(String pay_type, double pay_amt, String goods_name, String remark, String agent_bill_id) {
		super();
		this.pay_type = pay_type;
		this.pay_amt = pay_amt;
		this.goods_name = goods_name;
		this.remark = remark;
		this.agent_bill_id = agent_bill_id;
	}
	
	
	public String getPay_type() {
		return pay_type;
	}

	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}

	public double getPay_amt() {
		return pay_amt;
	}

	public void setPay_amt(double pay_amt) {
		this.pay_amt = pay_amt;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAgent_bill_id() {
		return agent_bill_id;
	}

	public void setAgent_bill_id(String agent_bill_id) {
		this.agent_bill_id = agent_bill_id;
	}

	
}
