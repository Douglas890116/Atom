package com.maven.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TakeDepositRecord implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * 订单号
	 */
	private String ordernumber;
	/**
	 * 员工编码
	 */
	private String employeecode;
	/**
	 * 上级编码
	 */
	private String parentemployeecode;
	/**
	 * 企业编码
	 */
	private String enterprisecode;
	/**
	 * 品牌编码 == 游戏平台
	 */
	private String brandcode;
	/**
	 * 支付方式
	 */
	private String paymenttypecode;
	/**
	 * 订单日期
	 */
	private Date orderdate;
	/**
	 * 订单金额
	 */
	private BigDecimal orderamount;
	/**
	 * 汇率
	 */
	private Double exchangerate;
	/**
	 * 企业收付款账号姓名
	 */
	private String enterprisepaymentname;
	/**
	 * 企业收付款账号
	 */
	private String enterprisepaymentaccount;
	/**
	 * 企业收付款银行
	 */
	private String enterprisepaymentbank;
	/**
	 * 员工收付款姓名
	 */
	private String employeepaymentname;
	/**
	 * 员工收付款账号
	 */
	private String employeepaymentaccount;
	/**
	 * 员工收付款银行
	 */
	private String employeepaymentbank;
	/**
	 * 订单类型
	 */
	private Byte ordertype;
	/**
	 * 订单状态
	 */
	private Byte orderstatus;
	/**
	 * 订单创建人
	 */
	private String ordercreater;
	/**
	 * 跟踪IP
	 */
	private String traceip;
	/**
	 * 存款附言
	 */
	private String ordercomment;
	/**
	 * 流程编码
	 */
	private String flowcode;
	
	/**
	 * 流程编码
	 */
	private Integer delegatecode;
	/**
	 * 当前流程处理人
	 */
	private String handleemployee;
	/**
	 * 处理完成时间
	 */
	private Date handleovertime;
	
	/**
	 * 优惠组ID
	 */
	private String favourableid;
	
	//加密列
	private String sign;
	//申请单的登录账号
	private String loginaccount;
	//申请单的登录账号
	private String displayalias;
	private Byte creditrating;
	//存款总额
	private Double allDepositMoney;
	//取款总额
	private Double allTakeMoney;
	//取款(次数)
	private int quantity;
	//存款(次数)
	private int depositNumber;
	//上级用户名
	private String parentemployeeaccount;

	//存款总额
	private double savemoney;
	//存款次数
	private double takemoney;
	//取款总额
	private double savecount;
	//取款次数
	private double takecount;
	
	private double betmoney;//投注总额
	private double netmoney;//输赢总额
	private double ratex;//输赢比
	private String platformtype;//游戏平台代码
	
	private String favourablename;//优惠组名称
	
	/** 
	 * 订单处理时间报表函数所返回字段 
	 * **/
	//时间
	private String time;
	//数量
	private String mum;
	//开始时间
	private Date start_date;
	//结束时间
	private Date end_date;
	
	/**
	 * 取款稽核数据
	 */
	private Date ptime;
	private String pcontent;
	private String pmoney;
	private String ptype;
	
	
	public enum Enum_ordertype{
		存款(1,"存款"),
		取款(2,"取款");
		public int value;
		public String desc;
        private Enum_ordertype(int value,String desc) {
            this.value = value;
            this.desc = desc;
        }
        public static Enum_ordertype getOrdertype(int ordertype){
        	for (Enum_ordertype e : Enum_ordertype.values()) {
				if(e.value==ordertype) return e;
			}
        	return null;
        }
	}
	
	public enum Enum_orderstatus{
		审核中(1,"审核中"),
		审核通过(2,"审核通过"),
		驳回(3,"驳回"),
		拒绝(4,"拒绝"),
		待出款(5,"待出款");
		public int value;
		public String desc;
        private Enum_orderstatus(int value,String desc) {
            this.value = value;
            this.desc = desc;
        }
        
        public String getValueDesc(int value){
        	for (Enum_orderstatus o : Enum_orderstatus.values()) {
				if(o.value==value) return o.desc;
			}
        	return "";
        }
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getEnterprisecode() {
		return enterprisecode;
	}
	public void setEnterprisecode(String enterprisecode) {
		this.enterprisecode = enterprisecode;
	}
	public String getParentemployeeaccount() {
		return parentemployeeaccount;
	}
	public void setParentemployeeaccount(String parentemployeeaccount) {
		this.parentemployeeaccount = parentemployeeaccount;
	}
	public Double getAllTakeMoney() {
		return allTakeMoney;
	}
	public void setAllTakeMoney(Double allTakeMoney) {
		this.allTakeMoney = allTakeMoney;
	}
	public Double getAllDepositMoney() {
		return allDepositMoney;
	}
	public void setAllDepositMoney(Double allDepositMoney) {
		this.allDepositMoney = allDepositMoney;
	}
	public String getFlowcode() {
		return flowcode;
	}
	public void setFlowcode(String flowcode) {
		this.flowcode = flowcode;
	}
	public Byte getOrdertype() {
		return ordertype;
	}
	public void setOrdertype(Byte ordertype) {
		this.ordertype = ordertype;
	}
	public Byte getOrderstatus() {
		return orderstatus;
	}
	public void setOrderstatus(Byte orderstatus) {
		this.orderstatus = orderstatus;
	}
	public Double getExchangerate() {
		return exchangerate;
	}
	public void setExchangerate(Double exchangerate) {
		this.exchangerate = exchangerate;
	}
	
	public String getEmployeecode() {
		return employeecode;
	}
	public void setEmployeecode(String employeecode) {
		this.employeecode = employeecode;
	}
	public String getParentemployeecode() {
		return parentemployeecode;
	}
	public void setParentemployeecode(String parentemployeecode) {
		this.parentemployeecode = parentemployeecode;
	}
	public String getBrandcode() {
		return brandcode;
	}
	public void setBrandcode(String brandcode) {
		this.brandcode = brandcode;
	}
	public Date getOrderdate() {
		return orderdate;
	}
	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}
	public String getPaymenttypecode() {
		return paymenttypecode;
	}
	public void setPaymenttypecode(String paymenttypecode) {
		this.paymenttypecode = paymenttypecode;
	}
	public Integer getDelegatecode() {
		return delegatecode;
	}
	public void setDelegatecode(Integer delegatecode) {
		this.delegatecode = delegatecode;
	}
	public String getTraceip() {
		return traceip;
	}
	public void setTraceip(String traceip) {
		this.traceip = traceip;
	}
	public String getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(String ordernumber) {
		this.ordernumber = ordernumber;
	}
	public BigDecimal getOrderamount() {
		return orderamount;
	}
	public void setOrderamount(BigDecimal orderamount) {
		this.orderamount = orderamount;
	}
	public String getEnterprisepaymentaccount() {
		return enterprisepaymentaccount;
	}
	public String getEnterprisepaymentname() {
		return enterprisepaymentname;
	}
	public void setEnterprisepaymentname(String enterprisepaymentname) {
		this.enterprisepaymentname = enterprisepaymentname;
	}
	public String getEnterprisepaymentbank() {
		return enterprisepaymentbank;
	}
	public void setEnterprisepaymentbank(String enterprisepaymentbank) {
		this.enterprisepaymentbank = enterprisepaymentbank;
	}
	public String getEmployeepaymentname() {
		return employeepaymentname;
	}
	public void setEmployeepaymentname(String employeepaymentname) {
		this.employeepaymentname = employeepaymentname;
	}
	public String getEmployeepaymentbank() {
		return employeepaymentbank;
	}
	public void setEmployeepaymentbank(String employeepaymentbank) {
		this.employeepaymentbank = employeepaymentbank;
	}
	public void setEnterprisepaymentaccount(String enterprisepaymentaccount) {
		this.enterprisepaymentaccount = enterprisepaymentaccount;
	}
	public String getEmployeepaymentaccount() {
		return employeepaymentaccount;
	}
	public void setEmployeepaymentaccount(String employeepaymentaccount) {
		this.employeepaymentaccount = employeepaymentaccount;
	}
	
	public String getOrdercreater() {
		return ordercreater;
	}
	public void setOrdercreater(String ordercreater) {
		this.ordercreater = ordercreater;
	}
	public String getOrdercomment() {
		return ordercomment;
	}
	public void setOrdercomment(String ordercomment) {
		this.ordercomment = ordercomment;
	}
	public String getHandleemployee() {
		return handleemployee;
	}
	public void setHandleemployee(String handleemployee) {
		this.handleemployee = handleemployee;
	}
	public String getLoginaccount() {
		return loginaccount;
	}
	public void setLoginaccount(String loginaccount) {
		this.loginaccount = loginaccount;
	}
	public Byte getCreditrating() {
		return creditrating;
	}
	public void setCreditrating(Byte creditrating) {
		this.creditrating = creditrating;
	}
	public Date getHandleovertime() {
		return handleovertime;
	}
	public void setHandleovertime(Date handleovertime) {
		this.handleovertime = handleovertime;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getMum() {
		return mum;
	}
	public void setMum(String mum) {
		this.mum = mum;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	public int getDepositNumber() {
		return depositNumber;
	}
	public void setDepositNumber(int depositNumber) {
		this.depositNumber = depositNumber;
	}
	public double getSavemoney() {
		return savemoney;
	}
	public void setSavemoney(double savemoney) {
		this.savemoney = savemoney;
	}
	public double getTakemoney() {
		return takemoney;
	}
	public void setTakemoney(double takemoney) {
		this.takemoney = takemoney;
	}
	public double getSavecount() {
		return savecount;
	}
	public void setSavecount(double savecount) {
		this.savecount = savecount;
	}
	public double getTakecount() {
		return takecount;
	}
	public void setTakecount(double takecount) {
		this.takecount = takecount;
	}
	public double getBetmoney() {
		return betmoney;
	}
	public void setBetmoney(double betmoney) {
		this.betmoney = betmoney;
	}
	public double getNetmoney() {
		return netmoney;
	}
	public void setNetmoney(double netmoney) {
		this.netmoney = netmoney;
	}
	public double getRatex() {
		return ratex;
	}
	public void setRatex(double ratex) {
		this.ratex = ratex;
	}
	public String getPlatformtype() {
		return platformtype;
	}
	public void setPlatformtype(String platformtype) {
		this.platformtype = platformtype;
	}
	public String getFavourableid() {
		return favourableid;
	}
	public void setFavourableid(String favourableid) {
		this.favourableid = favourableid;
	}
	public String getFavourablename() {
		return favourablename;
	}
	public void setFavourablename(String favourablename) {
		this.favourablename = favourablename;
	}
	public Date getPtime() {
		return ptime;
	}
	public void setPtime(Date ptime) {
		this.ptime = ptime;
	}
	public String getPcontent() {
		return pcontent;
	}
	public void setPcontent(String pcontent) {
		this.pcontent = pcontent;
	}
	public String getPmoney() {
		return pmoney;
	}
	public void setPmoney(String pmoney) {
		this.pmoney = pmoney;
	}
	public String getPtype() {
		return ptype;
	}
	public void setPtype(String ptype) {
		this.ptype = ptype;
	}
	public String getDisplayalias() {
		return displayalias;
	}
	public void setDisplayalias(String displayalias) {
		this.displayalias = displayalias;
	}
	
}
