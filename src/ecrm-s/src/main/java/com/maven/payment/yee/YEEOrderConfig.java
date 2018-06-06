package com.maven.payment.yee;

public class YEEOrderConfig {
	/**
	 * 商户订单号
	 */
	private String p2_Order;
	/**
	 * 支付金额
	 */
	private String p3_Amt;
	/**
	 * 商品名称
	 */
	private String p5_Pid="A";
	/**
	 * 商品种类
	 */
	private String p6_Pcat="B";
	/**
	 * 商品描述
	 */
	private String p7_Pdesc="ALL";
	/**
	 * 送货地址
	 */
	private String p9_SAF="0";
	/**
	 * 商户扩展信息
	 */
	private String pa_MP;
	/**
	 * 银行编码
	 */
	private String pd_FrpId;
	
	/**
	 * 签名数据
	 */
	private String hmac;

	/**
	 * 存款订单参数
	 * 
	 * @author Administrator
	 *
	 */
	public enum O_Save_Paramters {
		银行编码("pd_FrpId"), 订单号("p2_Order"), 订单金额("p3_Amt"), ;
		public String value;

		private O_Save_Paramters(String value) {
			this.value = value;
		}

		public static String[] paramters() {
			O_Save_Paramters[] s = O_Save_Paramters.values();
			String[] ss = new String[s.length];
			for (int i = 0; i < s.length; i++) {
				ss[i] = s[i].value;
			}
			return ss;
		}
	}

	public String getP2_Order() {
		return p2_Order;
	}

	public void setP2_Order(String p2_Order) {
		this.p2_Order = p2_Order;
	}

	public String getP3_Amt() {
		return p3_Amt;
	}

	public void setP3_Amt(String p3_Amt) {
		this.p3_Amt = p3_Amt;
	}

	public String getP5_Pid() {
		return p5_Pid;
	}

	public void setP5_Pid(String p5_Pid) {
		this.p5_Pid = p5_Pid;
	}

	public String getP6_Pcat() {
		return p6_Pcat;
	}

	public void setP6_Pcat(String p6_Pcat) {
		this.p6_Pcat = p6_Pcat;
	}

	public String getP7_Pdesc() {
		return p7_Pdesc;
	}

	public void setP7_Pdesc(String p7_Pdesc) {
		this.p7_Pdesc = p7_Pdesc;
	}

	public String getP9_SAF() {
		return p9_SAF;
	}

	public void setP9_SAF(String p9_SAF) {
		this.p9_SAF = p9_SAF;
	}

	public String getPa_MP() {
		return pa_MP;
	}

	public void setPa_MP(String pa_MP) {
		this.pa_MP = pa_MP;
	}

	public String getPd_FrpId() {
		return pd_FrpId;
	}

	public void setPd_FrpId(String pd_FrpId) {
		this.pd_FrpId = pd_FrpId;
	}
	
	public String getHmac() {
		return hmac;
	}

	public void setHmac(String hmac) {
		this.hmac = hmac;
	}
}
