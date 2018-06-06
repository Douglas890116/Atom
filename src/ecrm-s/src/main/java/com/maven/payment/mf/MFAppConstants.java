package com.maven.payment.mf;

public class MFAppConstants {
	/*==============================请求参数列表==============================*/
	public static final String p0_Cmd = "p0_Cmd";
	
	public static final String p1_MerId = "p1_MerId";
	
	public static final String p2_Order = "p2_Order";
	
	public static final String p3_Amt = "p3_Amt";
	
	public static final String p4_Cur = "p4_Cur";
	
	public static final String p5_Pid = "p5_Pid";
	
	public static final String p6_Pcat = "p6_Pcat";
	
	public static final String p7_Pdesc = "p7_Pdesc";
	
	public static final String p8_Url = "p8_Url";
	
	public static final String pa_MP = "pa_MP";
	
	public static final String pd_FrpId = "pd_FrpId";
	
	public static final String pr_NeedResponse = "pr_NeedResponse";
	/*==============================回调参数列表==============================*/
	public static final String r0_Cmd = "r0_Cmd";
	
	public static final String r1_Code = "r1_Code";
	
	public static final String r2_TrxId = "r2_TrxId";
	
	public static final String r3_Amt = "r3_Amt";
	
	public static final String r4_Cur = "r4_Cur";
	
	public static final String r5_Pid = "r5_Pid";
	
	public static final String r6_Order = "r6_Order";
	
	public static final String r7_Uid = "r7_Uid";
	
	public static final String r8_MP = "r8_MP";
	
	public static final String r9_BType = "r9_BType";
	/*==============================一下参数不参与签名==============================*/
	public static final String rb_BankId = "rb_BankId";
	
	public static final String ro_BankOrderId = "ro_BankOrderId";
	
	public static final String rp_PayDAte = "rp_PayDAte";
	
	public static final String ru_Trxtime = "ru_Trxtime";
	/*==============================签名参数==============================*/
	public static final String hmac = "hmac";
}