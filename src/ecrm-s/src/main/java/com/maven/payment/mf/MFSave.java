package com.maven.payment.mf;

import java.util.Map;

public class MFSave {
	/** 业务类型，固定值：Buy **/
	private static final String CMD = "Buy";
	/**
	 * 获取支付链接
	 * @param merchant
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public String getUrl(MFMerchantConfig merchant, MFOrderConfig order) throws Exception {
		String sign = getRequestSign(merchant, order);
		StringBuffer sb = new StringBuffer(merchant.getPayUrl());
		sb.append("?").append(MFAppConstants.p0_Cmd).append("=").append(CMD)
		  .append("&").append(MFAppConstants.p1_MerId).append("=").append(merchant.getMerId())
		  .append("&").append(MFAppConstants.p2_Order).append("=").append(order.getOrderId())
		  .append("&").append(MFAppConstants.p3_Amt).append("=").append(order.getAmount())
		  .append("&").append(MFAppConstants.p4_Cur).append("=").append(order.getCurrency())
		  .append("&").append(MFAppConstants.p5_Pid).append("=").append(order.getGoodsName())
		  .append("&").append(MFAppConstants.p6_Pcat).append("=").append(order.getGoodsCategory())
		  .append("&").append(MFAppConstants.p7_Pdesc).append("=").append(order.getGoodsDesc())
		  .append("&").append(MFAppConstants.p8_Url).append("=").append(merchant.getReturnUrl())
		  .append("&").append(MFAppConstants.pa_MP).append("=").append(merchant.getMerInfo())
		  .append("&").append(MFAppConstants.pd_FrpId).append("=").append(order.getBankNo())
		  .append("&").append(MFAppConstants.pr_NeedResponse).append("=").append(merchant.getNeedResponse())
		  .append("&").append(MFAppConstants.hmac).append("=").append(sign);
		System.out.println("秒付支付接口地址\n" + sb.toString());
		return sb.toString();
	}
	/**
	 * 参数签名
	 * @param merchant
	 * @param order
	 * @return
	 */
	private String getRequestSign(MFMerchantConfig merchant, MFOrderConfig order) {
//		签名字段的验证
//		目的：测试商户端的订单信息是否能被API网络网系统正确接收；
//		说明：商户端需要提供以下信息：p0_Cmd、p1_MerId、p2_Order、p3_Amt、p4_Cur、p5_Pid、p6_Pcat、p7_Pdesc、p8_Url、pa_MP、pd_FrpId、pr_NeedResponse（各项具体含义详见下文相关参数表），这些字符串按以下格式及顺序将请求字段组合成为待验证的一个无间隔字符串，待验证字符串的生成过程，也就是进行MD5加密的过程：
//		Md5签名格式（利用附件案例的MD5算法类）
//		Md5Sign=p0_Cmd+p1_MerId+p2_Order+p3_Amt+p4_Cur+p5_Pid+p6_Pcat+p7_Pdesc+p8_Url+pa_MP+pd_FrpId+pr_NeedResponse+商户密钥;
		StringBuffer sb = new StringBuffer();
		sb.append(CMD)
		  .append(merchant.getMerId())
		  .append(order.getOrderId())
		  .append(order.getAmount())
		  .append(order.getCurrency())
		  .append(order.getGoodsName())
		  .append(order.getGoodsCategory())
		  .append(order.getGoodsDesc())
		  .append(merchant.getReturnUrl())
		  .append(merchant.getMerInfo())
		  .append(order.getBankNo())
		  .append(merchant.getNeedResponse());
		System.out.println("秒付接口签名原文\n" + sb.toString());
		System.out.println("秒付签名Key：" + merchant.getMerKey());
		String sign = DigestUtil.hmacSign(sb.toString(), merchant.getMerKey());
		System.out.println("秒付签名密文：" + sign);
		return sign;
	}
	/**
	 * 校验回填签名
	 * @param params
	 * @param merKey
	 * @return
	 */
	public static boolean checkReponseSign(Map<String, Object> params, String merKey) {
//		{
//			r0_Cmd=Buy,
//			rb_BankId=alipay,
//			rp_PayDate=2017-05-22,
//			p1_MerId=52,
//			r3_Amt=10.000,
//			r9_BType=2,
//			r5_Pid=goodsName,
//			r4_Cur=CNY,
//			r6_Order=78FFE09DC3844E19A9C2F2539C32F411,
//			r1_Code=1,
//			hmac=a6dae3481c6f3c47d7b9adaec7634ed8,
//			r2_TrxId=O2017052216093252,
//			ru_Trxtime=2017-05-22,
//			r8_MP=API
//		}
//		52Buy1O201705221609325210.000CNYgoodsName78FFE09DC3844E19A9C2F2539C32F411nullAPI2
		StringBuffer sb = new StringBuffer();
		sb.append(params.get(MFAppConstants.p1_MerId));
		sb.append(params.get(MFAppConstants.r0_Cmd));
		sb.append(params.get(MFAppConstants.r1_Code));
		sb.append(params.get(MFAppConstants.r2_TrxId));
		sb.append(params.get(MFAppConstants.r3_Amt));
		sb.append(params.get(MFAppConstants.r4_Cur));
		sb.append(params.get(MFAppConstants.r5_Pid));
		sb.append(params.get(MFAppConstants.r6_Order));
		if (null != params.get(MFAppConstants.r7_Uid))
			sb.append(params.get(MFAppConstants.r7_Uid));
		sb.append(params.get(MFAppConstants.r8_MP));
		sb.append(params.get(MFAppConstants.r9_BType));
		System.out.println("秒付回调签名原文：" + sb.toString());
		System.out.println("秒付签名Key：" + merKey);
		// 本地加密签名
		String signLocal = DigestUtil.hmacSign(sb.toString(), merKey);
		System.out.println("秒付回调签名：" + signLocal);
		// 参数中的签名
		String signParam = params.get(MFAppConstants.hmac).toString();
		return signLocal.equals(signParam);
	}
	
	public static void main(String[] args) {
		String str = "52Buy1O201705221609325210.000CNYgoodsName78FFE09DC3844E19A9C2F2539C32F411API2";
		String merKey = "KrOKsdJVHl0yi04JPNoIHJ90K4B1aHVo";
		System.out.println(DigestUtil.hmacSign(str, merKey));
		System.out.println("a6dae3481c6f3c47d7b9adaec7634ed8");
	}
	/**
	 * 构造函数
	 */
	public MFSave(){}
}