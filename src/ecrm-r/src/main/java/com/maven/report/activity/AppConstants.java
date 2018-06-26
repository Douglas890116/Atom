package com.maven.report.activity;

public class AppConstants {

	
	
	public static final String START_TIME = "START_TIME";
	public static final String RAND_MONEY = "RAND_MONEY";
	public static final String IS_AUTO_PAY = "IS_AUTO_PAY";//是否自动付款（0=非自动 1=自动）非自动时，只生成统计数据报表，不会直接增加余额（注意：仅限测试时观察数据使用。测试情况下，务必设置为0）
	public static final String TOTAL_DEPOSIT_MONEY = "TOTAL_DEPOSIT_MONEY";//用户历史充值总额要求（无要求填写0）
	public static final String TODAY_DEPOSIT_MONEY = "TODAY_DEPOSIT_MONEY";//用户当日充值总额要求（无要求填写0）
	public static final String LSBS = "LSBS";//提款需流水倍数（无要求填写0）
	
}
