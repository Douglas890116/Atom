package com.maven.payment.yee;

import javax.servlet.http.HttpServletRequest;

import com.maven.payment.yee.utils.DigestUtil;
import com.maven.payment.yee.utils.YeeAppConstants;

public class YEESave {
	
	/**
	 * 业务类型
	 */
	private String p0_Cmd;
	/**
	 * 商户编号
	 */
	private String p1_MerId;
	/**
	 * 商户订单号
	 */
	private String p2_Order;
	/**
	 * 支付金额
	 */
	private String p3_Amt;
	/**
	 * 交易币种
	 */
	private String p4_Cur;
	/**
	 * 商品名称
	 */
	private String p5_Pid;
	/**
	 * 商品种类
	 */
	private String p6_Pcat;
	/**
	 * 商品描述
	 */
	private String p7_Pdesc;
	/**
	 * 商户接收支付成功数据的地址
	 */
	private String p8_Url;
	/**
	 * 送货地址
	 */
	private String p9_SAF;
	/**
	 * 商户扩展信息
	 */
	private String pa_MP;
	/**
	 * 支付通道编码
	 */
	private String pd_FrpId;
	/**
	 * 应答机制
	 */
	private String pr_NeedResponse;
	/**
	 * 签名数据
	 */
	private String hmac;
	
	 
	public YEESave(String p0_Cmd, String p1_MerId, String p4_Cur, String p8_Url ,String pr_NeedResponse) {
		this.p0_Cmd = formatString(p0_Cmd);
		this.p1_MerId = formatString(p1_MerId);
		this.p4_Cur = formatString(p4_Cur);
		this.p8_Url = formatString(p8_Url);
		this.pr_NeedResponse = formatString(pr_NeedResponse);
	}
	
	/**
	 * 获取URL
	 */
	public String getUrl(String postUrl,String keyValue,
			String t_p2_Order,String t_p3_Amt,String t_p5_Pid,String t_p6_Pcat,
			String t_p7_Pdesc,String t_p9_SAF,String t_pa_MP,String t_pd_FrpId){

		this.p2_Order = formatString(t_p2_Order);
		this.p3_Amt = formatString(t_p3_Amt);
		this.p5_Pid = formatString(t_p5_Pid);
		this.p6_Pcat = formatString(t_p6_Pcat);
		this.p7_Pdesc = formatString(t_p7_Pdesc);
		this.p9_SAF = formatString(t_p9_SAF);
		this.pa_MP = formatString(t_pa_MP);
		this.pd_FrpId = formatString(t_pd_FrpId);
		
		this.hmac= getReqMd5HmacForOnlinePayment(this.p0_Cmd, this.p1_MerId,
				 this.p2_Order,  this.p3_Amt,  this.p4_Cur, this.p5_Pid,  this.p6_Pcat,
				 this.p7_Pdesc, this.p8_Url,  this.p9_SAF, this.pa_MP, this.pd_FrpId,
				 this.pr_NeedResponse, keyValue);
		StringBuffer url = new StringBuffer();
		url.append(postUrl)
		.append("?").append(YeeAppConstants.P0_CMD).append("=").append(this.p0_Cmd)
		.append("&").append(YeeAppConstants.P1_MERID).append("=").append(this.p1_MerId)
		.append("&").append(YeeAppConstants.P2_ORDER).append("=").append(this.p2_Order)
		.append("&").append(YeeAppConstants.P3_AMT).append("=").append(this.p3_Amt)
		.append("&").append(YeeAppConstants.P4_CUR).append("=").append(this.p4_Cur)
		.append("&").append(YeeAppConstants.P5_PID).append("=").append(this.p5_Pid)
		.append("&").append(YeeAppConstants.P6_PCAT).append("=").append(this.p6_Pcat)
		.append("&").append(YeeAppConstants.P7_PDESC).append("=").append(this.p7_Pdesc)
		.append("&").append(YeeAppConstants.P8_URL).append("=").append(this.p8_Url)
		.append("&").append(YeeAppConstants.P9_SAF).append("=").append(this.p9_SAF)
		.append("&").append(YeeAppConstants.PA_MP).append("=").append(this.pa_MP)
		.append("&").append(YeeAppConstants.PD_FRPID).append("=").append(this.pd_FrpId)
		.append("&").append(YeeAppConstants.PR_NEEDRESPONSE).append("=").append(this.pr_NeedResponse)
		.append("&").append(YeeAppConstants.SIGN).append("=").append(this.hmac);
		return url.toString();
	}
	
	/**
	 * 生成hmac方法
	 * 业务类型
	 * @param p0_Cmd
	 * 商户编号
	 * @param p1_MerId
	 * 商户订单号
	 * @param p2_Order
	 * 支付金额
	 * @param p3_Amt
	 * 交易币种
	 * @param p4_Cur
	 * 商品名称
	 * @param p5_Pid
	 * 商品种类
	 * @param p6_Pcat
	 * 商品描述
	 * @param p7_Pdesc
	 * 商户接收支付成功数据的地址
	 * @param p8_Url
	 * 送货地址
	 * @param p9_SAF
	 * 商户扩展信息
	 * @param pa_MP
	 * 银行编码
	 * @param pd_FrpId
	 * 应答机制
	 * @param pr_NeedResponse
	 * 商户密钥
	 * @param keyValue
	 * @return
	 */
	private static String getReqMd5HmacForOnlinePayment(String p0_Cmd,String p1_MerId,
			String p2_Order, String p3_Amt, String p4_Cur,String p5_Pid, String p6_Pcat,
			String p7_Pdesc,String p8_Url, String p9_SAF,String pa_MP,String pd_FrpId,
			String pr_NeedResponse,String keyValue) {
		  StringBuffer sValue = new StringBuffer();
		// 业务类型
		sValue.append(p0_Cmd);
		// 商户编号
		sValue.append(p1_MerId);
		// 商户订单号
		sValue.append(p2_Order);
		// 支付金额
		sValue.append(p3_Amt);
		// 交易币种
		sValue.append(p4_Cur);
		// 商品名称
		sValue.append(p5_Pid);
		// 商品种类
		sValue.append(p6_Pcat);
		// 商品描述
		sValue.append(p7_Pdesc);
		// 商户接收支付成功数据的地址
		sValue.append(p8_Url);
		// 送货地址
		sValue.append(p9_SAF);
		// 商户扩展信息
		sValue.append(pa_MP);
		// 银行编码
		sValue.append(pd_FrpId);
		// 应答机制
		sValue.append(pr_NeedResponse);
		
		String sNewString = null;

		sNewString = DigestUtil.hmacSign(sValue.toString(), keyValue);
		return (sNewString);
	}
	
	/**
	 * 返回校验hmac方法
	 * 
	 * @param hmac
	 * 商户编号
	 * @param p1_MerId
	 * 业务类型
	 * @param r0_Cmd
	 * 支付结果
	 * @param r1_Code
	 * 易宝支付交易流水号
	 * @param r2_TrxId
	 * 支付金额
	 * @param r3_Amt
	 * 交易币种
	 * @param r4_Cur
	 * 商品名称
	 * @param r5_Pid
	 * 商户订单号
	 * @param r6_Order
	 * 易宝支付会员ID
	 * @param r7_Uid
	 * 商户扩展信息
	 * @param r8_MP
	 * 交易结果返回类型
	 * @param r9_BType
	 * 交易结果返回类型
	 * @param keyValue
	 * @return
	 */
	public static boolean verifyCallback(String hmac, String p1_MerId,
			String r0_Cmd, String r1_Code, String r2_TrxId, String r3_Amt,
			String r4_Cur, String r5_Pid, String r6_Order, String r7_Uid,
			String r8_MP, String r9_BType, String keyValue) {
		StringBuffer sValue = new StringBuffer();
		// 商户编号
		sValue.append(p1_MerId);
		// 业务类型
		sValue.append(r0_Cmd);
		// 支付结果
		sValue.append(r1_Code);
		// 易宝支付交易流水号
		sValue.append(r2_TrxId);
		// 支付金额
		sValue.append(r3_Amt);
		// 交易币种
		sValue.append(r4_Cur);
		// 商品名称
		sValue.append(r5_Pid);
		// 商户订单号
		sValue.append(r6_Order);
		// 易宝支付会员ID
		sValue.append(r7_Uid);
		// 商户扩展信息
		sValue.append(r8_MP);
		// 交易结果返回类型
		sValue.append(r9_BType);
		String sNewString = null;
		sNewString = DigestUtil.hmacSign(sValue.toString(), keyValue);

		if (hmac.equals(sNewString)) {
			return (true);
		}
		return (false);
	}
	
	public static boolean getParameterBeforeVerifyCallback(HttpServletRequest req, String keyValue) {
		String p1_MerId = req.getParameter("p1_MerId");
        String r0_Cmd = req.getParameter("r0_Cmd");
        String r1_Code = req.getParameter("r1_Code");
        String r2_TrxId = req.getParameter("r2_TrxId");
        String r3_Amt = req.getParameter("r3_Amt");
        String r4_Cur = req.getParameter("r4_Cur");
        String r5_Pid = req.getParameter("r5_Pid");
        String r6_Order = req.getParameter("r6_Order");
        String r7_Uid = req.getParameter("r7_Uid");
        String r8_MP = req.getParameter("r8_MP");
        String r9_BType = req.getParameter("r9_BType");
        String backsign = req.getParameter("hmac");
		return verifyCallback(backsign, p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid, r8_MP, r9_BType, keyValue);
	}
	
	/**
	 * 参数过滤
	 * @param text
	 * @return
	 */
	private String formatString(String text){ 
		if(text == null) {
			return ""; 
		}
		return text;
	}
	 
	public String getP0_Cmd() {
		return p0_Cmd;
	}

	public void setP0_Cmd(String p0_Cmd) {
		this.p0_Cmd = p0_Cmd;
	}

	public String getP1_MerId() {
		return p1_MerId;
	}

	public void setP1_MerId(String p1_MerId) {
		this.p1_MerId = p1_MerId;
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

	public String getP4_Cur() {
		return p4_Cur;
	}

	public void setP4_Cur(String p4_Cur) {
		this.p4_Cur = p4_Cur;
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

	public String getP8_Url() {
		return p8_Url;
	}

	public void setP8_Url(String p8_Url) {
		this.p8_Url = p8_Url;
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

	public String getPr_NeedResponse() {
		return pr_NeedResponse;
	}

	public void setPr_NeedResponse(String pr_NeedResponse) {
		this.pr_NeedResponse = pr_NeedResponse;
	}

	public String getHmac() {
		return hmac;
	}

	public void setHmac(String hmac) {
		this.hmac = hmac;
	}

}
