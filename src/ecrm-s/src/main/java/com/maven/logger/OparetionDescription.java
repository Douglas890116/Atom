package com.maven.logger;

public interface OparetionDescription {
	//企业	
	public final static String ENTERPRISE_EDIT = "编辑企业信息";
	public final static String ENTERPRISE_DELETE = "删除企业信息";
	public final static String ENTERPRISE_API = "企业API信息";
	//品牌
	public final static String ENTERPRISEOPERATINGBRAND_EDIT = "编辑品牌信息";
	public final static String ENTERPRISEOPERATINGBRAND_DELETE = "删除品牌信息";
	public final static String ENTERPRISEOPERATINGBRAND_GAMESWITCH = "品牌游戏开关";
	public final static String ENTERPRISEOPERATINGBRAND_DOMAINBIND = "品牌域名绑定";
	//用户信息
	public final static String ENTERPRISEEMPLOYEEINFO_DELETE = "删除用户信息";
	public final static String ENTERPRISEEMPLOYEEINFO_RESTLOGINPASSWORD = "重置登录密码";
	public final static String ENTERPRISEEMPLOYEEINFO_RESTCAPITALPASSWORD = "重置资金密码";
	public final static String ENTERPRISEEMPLOYEEINFO_COMPETENCE= "用户权限管理";
	public final static String ENTERPRISEEMPLOYEEINFO_BILLING = "结算配置";
	//用户银行卡
	public final static String EMPLOYEEBANKCARDINFO_DELETE = "用户银行卡删除";
	public final static String EMPLOYEEBANKCARDINFO_UPDATE = "用户银行卡修改";
	public final static String EMPLOYEEBANKCARDINFO_LOCKINGBANKCARD = "用户银行卡锁定";
	public final static String EMPLOYEEBANKCARDINFO_UNLOCKINGBANKCARD = "用户银行卡锁定";
	//存取款订单
	public final static String TAKE_UPDATE = "取款编辑";
	public final static String DEPOSIT_UPDATE = "存款编辑";
	public final static String DEPOSITTAKE_DELETE = "存取款删除";
	public final static String DEPOSIT_TAKE_APPROVE = "存取款审批";
	public final static String DEPOSIT_TAKE_REJECT = "存取款单拒绝";
	public final static String DEPOSIT_TAKE_OVERRULE = "存取款驳回";
	//公司银行卡
	public final static String COMPANY_BANK_CARD_DELETE = "公司银行卡删除";
	public final static String COMPANY_BANK_CARD_UPDATE = "公司银行卡编辑";
	//冲正负
	public final static String PLUS_LESS = "冲正负提交";
	//活动充值
	public final static String ACTIVITY_RECHARGE = "活动充值";
	//运营公告
	public final static String BRAND_NOTIC_DELETE = "运营公告删除";
	public final static String BRAND_NOTIC_UPDATE = "运营公告修改";
	//消息管理
	public final static String MESSAGE_MANAGER_DELETE = "消息管理删除";
	//密码修改
	public final static String LOGIN_PASSWORD_UPDATE = "登录密码修改";
	public final static String CAPITAL_PASSWORD_UPDATE = "资金密码修改";
	//存取款流程设置
	public final static String DEPOSIT_TAKE_WORKING_FLOW_CONFIGURATION_UPDATE = "存取款流程修改";
	public final static String DEPOSIT_TAKE_WORKING_FLOW_CONFIGURATION_DELETE = "存取款流程删除";
	public final static String DEPOSIT_TAKE_WORKING_FLOW_CONFIGURATION_SET_APPROVE = "存取款流程审批人设置";
	//游戏类型
	public final static String GAME_TYPE_UPDATE = "游戏类型修改";
	public final static String GAME_TYPE_DELETE = "游戏类型删除";
	//账变类型
	public final static String MONEY_CHANGE_TYPE_UPDATE="账变类型修改";
	public final static String MONEY_CHANGE_TYPE_DELETE="账变类型删除";
	//用户类型
	public final static String EMPLOYEE_TYPE_UPDATE = "用户类型修改";
	public final static String EMPLOYEE_TYPE_DELETE = "用户类型删除";
	//支付类型
	public final static String PAYMENT_TYPE_UPDATE = "支付类型修改";
	public final static String PAYMENT_TYPE_DELETE = "支付类型删除";
	//快捷支付类型
	public final static String THIRDPARTYPAYMENT_ENABLE_DISABLE = "支付方式启用与禁用";
	public final static String THIRDPARTYPAYMENT_DELETE = "支付方式删除";
	public final static String THIRDPARTYPAYMENT_UPDATE = "支付方式修改";
	//企业活动
	public final static String ENTERPRISEBRANDACTIVITY_ADD = "运营活动添加";
	//删除隐私数据授权
	public final static String PRIVATEDATAACCESS_DELETE = "删除隐私数据授权";
	
	//汇率管理
	public static final String EXCHANGE_ADD="汇率增加";
	public static final String EXCHANGE_UPDATE="汇率修改";
	public static final String EXCHANGE_DELETE="汇率删除";
}
