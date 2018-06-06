package com.maven.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 上下分记录对象
 *
 */
public class UserMoneyInAndOut implements Serializable{

	/**
	 * @fieldName:serialVersionUID
	 * @fieldType:long
	 * @Description:TODO
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 类型编码
	 */
	private String moneyinoutcode;
	
	/**
	 * 员工编码
	 */
	private String employeecode;
	
	/**
	 * 用户上级编码
	 */
	private String parentemployeecode;
	
	/** 企业编码 */
	private String enterprisecode;
	
	/** 品牌编码. */
	private String brandcode;
	
	/**
	 * 订单号，19位的纯数字。传递给第三方接口平台后，此单号再传递给最终游戏平台，以此单号为存取款（上下分）操作的唯一识别码
	 */
	private String orderno;
	
	/**
	 * 上下分流程唯一批次号，19位的纯数字。
	 */
	private String patchno;
	
	/**
	 * 游戏类型
	 */
	private String gametype;
	/**
	 * 上下分时间
	 */
	private Timestamp moneyinoutdate;
	
	/**
	 *上下分金额
	 */
	private BigDecimal moneyinoutamount;
	
	/**
	 * 备注
	 */
	private String moneyinoutcomment;
	
	/**
	 * 上下分前余额 
	 */
	private BigDecimal beforebalance; 
	
	/**
	 * 转分后分数
	 */
	private BigDecimal afterbalance;
	
	/**
	 * 是否成功
	 */
	private Byte updatecapital;
	
	/**
	 * 上下分类型（1：上分，2：下分）
	 */
	private Byte opreatetype;
	
	/**
	 * 是否返还金额
	 */
	private Byte backmoney;
	
	/** 
	 * 排序字段 
	 */
	private long timesort;
	
	/**
	 * 上分后是否已下分标志
	 * 
	 * 默认为1，表示上了分缺下分过或者下分成功过。
	 * 2为已下分
	 */
	private String isdown = "1";
	
	
	/* 关联属性：员工名称 */
	private String loginaccount;
	
	/* 关联属性：员工名称 */
	private String employeename;
	
	public UserMoneyInAndOut(){}
	
	public UserMoneyInAndOut(
			Enum_opreatetype opratetype,
			String employeeCode,String 
			parentemployeecode,String gametype,
			BigDecimal moneyInOutAmount,
			String moneyInOutComment,
			BigDecimal beforebalance,
			BigDecimal afterbalance,
			Enum_updatecapital update,
			long timesort,
			String orderno,
			String patchno,
			
			String enterprisecode,
			String brandcode){
		
		this.opreatetype = opratetype.code.byteValue();
		this.employeecode = employeeCode;
		this.parentemployeecode = parentemployeecode;
		this.gametype = gametype;
		this.moneyinoutamount = moneyInOutAmount;
		this.moneyinoutcomment = moneyInOutComment;
		this.beforebalance = beforebalance;
		this.afterbalance = afterbalance;
		this.updatecapital = update.value.byteValue();
		this.timesort = timesort;
		
		this.orderno = orderno;
		this.patchno = patchno;
		
		this.enterprisecode = enterprisecode;
		this.brandcode = brandcode;
	}
	
	public UserMoneyInAndOut(String employeecode, String enterprisecode, String gametype) {
		super();
		this.employeecode = employeecode;
		this.enterprisecode = enterprisecode;
		this.gametype = gametype;
	}

	public UserMoneyInAndOut(
			String moneyinoutcode,
			String moneyinoutcomment,
			BigDecimal afterbalance,
			Enum_updatecapital updatecapital,
			
			String orderno,
			String patchno,
			String employeeCode){
		
		this.moneyinoutcode = moneyinoutcode;
		this.moneyinoutcomment = moneyinoutcomment;
		this.afterbalance = afterbalance;
		this.updatecapital = updatecapital.value.byteValue();
		
		this.orderno = orderno;
		this.patchno = patchno;
		this.employeecode = employeeCode;
	}
	
	public enum Enum_opreatetype{
		上分(1),
		下分(2);
		public Integer code;
		
		private Enum_opreatetype(Integer code){
			this.code = code;
		}
	}
	
	public enum Enum_backmoney{
		否(1),
		是(2);
		public Integer code;
		
		private Enum_backmoney(Integer code){
			this.code = code;
		}
	}
	
	public enum Enum_updatecapital{
		请求中(0,"请求中"),
		是(1,"成功"),
		否(2,"失败");
		public Integer value;
		public String desc;
		
		private Enum_updatecapital(Integer value,String desc){
			this.value=value;
			this.desc=desc;
		}
	}
	
	public String getEmployeename() {
		return employeename;
	}

	public String getLoginaccount() {
		return loginaccount;
	}

	public void setLoginaccount(String loginaccount) {
		this.loginaccount = loginaccount;
	}
	
	public void setEmployeename(String employeename) {
		this.employeename = employeename;
	}
	
	public String getMoneyinoutcode() {
		return moneyinoutcode;
	}

	public void setMoneyinoutcode(String moneyinoutcode) {
		this.moneyinoutcode = moneyinoutcode;
	}

	public String getGametype() {
		return gametype;
	}

	public void setGametype(String gametype) {
		this.gametype = gametype;
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

	public Timestamp getMoneyinoutdate() {
		return moneyinoutdate;
	}

	public void setMoneyinoutdate(Timestamp moneyinoutdate) {
		this.moneyinoutdate = moneyinoutdate;
	}

	public BigDecimal getMoneyinoutamount() {
		return moneyinoutamount;
	}

	public void setMoneyinoutamount(BigDecimal moneyinoutamount) {
		this.moneyinoutamount = moneyinoutamount;
	}

	public String getMoneyinoutcomment() {
		return moneyinoutcomment;
	}

	public void setMoneyinoutcomment(String moneyinoutcomment) {
		this.moneyinoutcomment = moneyinoutcomment;
	}

	public BigDecimal getBeforebalance() {
		return beforebalance;
	}
	public void setBeforebalance(BigDecimal beforebalance) {
		this.beforebalance = beforebalance;
	}

	public BigDecimal getAfterbalance() {
		return afterbalance;
	}

	public void setAfterbalance(BigDecimal afterbalance) {
		this.afterbalance = afterbalance;
	}

	public Byte getUpdatecapital() {
		return updatecapital;
	}

	public Byte getBackmoney() {
		return backmoney;
	}

	public void setBackmoney(Byte backmoney) {
		this.backmoney = backmoney;
	}

	public void setUpdatecapital(Byte updatecapital) {
		this.updatecapital = updatecapital;
	}

	public Byte getOpreatetype() {
		return opreatetype;
	}

	public void setOpreatetype(Byte opreatetype) {
		this.opreatetype = opreatetype;
	}

	public long getTimesort() {
		return timesort;
	}

	public void setTimesort(long timesort) {
		this.timesort = timesort;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getPatchno() {
		return patchno;
	}

	public void setPatchno(String patchno) {
		this.patchno = patchno;
	}

	public String getEnterprisecode() {
		return enterprisecode;
	}

	public void setEnterprisecode(String enterprisecode) {
		this.enterprisecode = enterprisecode;
	}

	public String getBrandcode() {
		return brandcode;
	}

	public void setBrandcode(String brandcode) {
		this.brandcode = brandcode;
	}

	public String getIsdown() {
		return isdown;
	}

	public void setIsdown(String isdown) {
		this.isdown = isdown;
	}
	
}
