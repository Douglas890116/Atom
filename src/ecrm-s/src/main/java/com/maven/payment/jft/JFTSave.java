package com.maven.payment.jft;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import com.maven.payment.lfwx.LfwxPayAppConstants;
import com.maven.payment.xbei.XBAppConstants;
import com.maven.util.Encrypt;

/**
 * 优付支付
 * @author Administrator
 *
 */
public class JFTSave {
	
	 
	public JFTSave() {
		
	}
	
	/**
	 * 获取当前时间，精确到yyyyMMddHHmmss
	 * 
	 * xufc20130419 修改
	 * 
	 * @return
	 */
	private static String getCurrentTime() {
		String strTime = "-1";
		try {
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			strTime = dateFormat.format(date);

		} catch (Exception e) {
			strTime = "-1";
			e.fillInStackTrace();
		}
		return strTime;
	}
	
	/**
	 * 实现两位小数点输出(String),没有逗号分隔
	 * 
	 * @param dou
	 *            金额
	 * @return 格式化之后的结果
	 */
	public static String doubleFormat(String dou) {
		java.text.DecimalFormat df = new java.text.DecimalFormat(
				"###0.00;-###0.00");
		return df.format(Double.parseDouble(dou));
	}
	
	/**
	 * 获取支付请求URL
	 */
	public String getUrl(JFTMerchantConfig merchant, JFTOrderConfig order){
		
		order.setOrderAmount(doubleFormat(order.getOrderAmount()));//必须要转换为两个小数点的格式
		
		String sign = getRequestSign( merchant,  order);
		
		StringBuffer url = new StringBuffer();
		url.append(merchant.getPayUrl())
		.append("?").append(JFTAppConstants.p1_MerId).append("=").append(merchant.getMerNo())
		.append("&").append(JFTAppConstants.p1_OrdId).append("=").append(order.getOrderNo())
		.append("&").append(JFTAppConstants.p1_OrdAmt).append("=").append(order.getOrderAmount())
		.append("&").append(JFTAppConstants.p1_PayType).append("=").append(merchant.getPayType())
		.append("&").append(JFTAppConstants.p1_CurCode).append("=").append(merchant.getCurCode())
		.append("&").append(JFTAppConstants.p1_BankCode).append("=").append(order.getBankCode())
		.append("&").append(JFTAppConstants.p1_ProductInfo).append("=").append(merchant.getProductInfo())
		.append("&").append(JFTAppConstants.p1_Remark).append("=").append(merchant.getRemark())
		.append("&").append(JFTAppConstants.p1_ReturnURL).append("=").append(merchant.getReturnUrl())
		.append("&").append(JFTAppConstants.p1_NotifyURL).append("=").append(merchant.getNotiUrl())
		.append("&").append(JFTAppConstants.p1_SignType).append("=").append(merchant.getSignType())
		.append("&").append(JFTAppConstants.p1_SignInfo).append("=").append(sign);
		System.out.println(url);
		return url.toString();
	}
	
	/**
	 * 支付请求做MD5参数签名
	 * @return
	 */
	private static String getRequestSign(JFTMerchantConfig merchant, JFTOrderConfig order) {
//		  
//		  支付请求签名约定： 
//		MerId=xxx&OrdId=xxx&OrdAmt=xxx&PayType=PP&CurCode=RMB&BankCode=xxx&ProductInfo=xxx&Remark=xxx&ReturnURL=xxx&NotifyURL=xxx&SignType=MD5&MerKey=xxx 
//				其中  MerKey是商户注册时获取的，连接起来进行  MD5 加密。
			
		StringBuffer sValue = new StringBuffer();
		
		sValue.append(JFTAppConstants.p1_MerId).append("=").append(merchant.getMerNo()).append("&");
		sValue.append(JFTAppConstants.p1_OrdId).append("=").append(order.getOrderNo()).append("&");
		sValue.append(JFTAppConstants.p1_OrdAmt).append("=").append(order.getOrderAmount()).append("&");
		sValue.append(JFTAppConstants.p1_PayType).append("=").append(merchant.getPayType()).append("&");
		sValue.append(JFTAppConstants.p1_CurCode).append("=").append(merchant.getCurCode()).append("&");
		sValue.append(JFTAppConstants.p1_BankCode).append("=").append(order.getBankCode()).append("&");
		sValue.append(JFTAppConstants.p1_ProductInfo).append("=").append(merchant.getProductInfo()).append("&");
		sValue.append(JFTAppConstants.p1_Remark).append("=").append(merchant.getRemark()).append("&");
		sValue.append(JFTAppConstants.p1_ReturnURL).append("=").append(merchant.getReturnUrl()).append("&");
		sValue.append(JFTAppConstants.p1_NotifyURL).append("=").append(merchant.getNotiUrl()).append("&");
		sValue.append(JFTAppConstants.p1_SignType).append("=").append(merchant.getSignType()).append("&");
		sValue.append("MerKey").append("=").append(merchant.getMerKey()).append("");
		
		System.out.println("支付请求做MD5参数="+sValue);
		String sNewString = null;
		
		sNewString = Encrypt.MD5(sValue.toString());
		System.out.println("支付请求做MD5参数签名="+sNewString);
		
//		你们的签名规则范例：	
//		MerId=xxx&OrdId=xxx&OrdAmt=xxx&PayType=PP&CurCode=RMB&BankCode=xxx&ProductInfo=xxx&Remark=xxx&ReturnURL=xxx&NotifyURL=xxx&SignType=MD5&MerKey=xxx
		
//		我的实际签名原文：（请将我这里的所有真实参数改为xxx，以更好的与你的范例对比）
//		MerId=5900696&OrdId=35E88CCB2A074197AF5C8B6420F98373&OrdAmt=100.00&PayType=PP&CurCode=RMB&BankCode=BEA&ProductInfo=productInfo&Remark=remark&ReturnURL=http://api.hyzonghe.net/TPayment/YomPayCallback&NotifyURL=http://api.hyzonghe.net/TPayment/YomPayCallback&SignType=MD5&MerKey=DFPCC8pKPpXAjbWmMBX62BJjbck3Z3CS
		return (sNewString);
	}
	
	
	/**
	 * 结果通知回参做签名校验
	 * @param req
	 * @param MER_KEY
	 * @return
	 */
	public static boolean checkResponseSign(HttpServletRequest req, String MER_KEY) {
		
//		支付结果签名约定： 
//		$stream = MerId=xxx&OrdId=xxx&OrdAmt=xxx&OrdNo=xxx&ResultCode=xxx&Remark=xx&SignType=xxx 
//		$SignInfo=md5( md5($stream) . $MerKey); 
//		$stream = $stream&SignInfo=$SignInfo 
//		其中  MerKey是商户注册时获取的，连接起来进行  MD5 加密
		
		String p2_MerId = req.getParameter(JFTAppConstants.p2_MerId);
        String p2_OrdId = req.getParameter(JFTAppConstants.p2_OrdId);
        String p2_OrdAmt = req.getParameter(JFTAppConstants.p2_OrdAmt);
        
        p2_OrdAmt = (doubleFormat(p2_OrdAmt));//必须要转换为两个小数点的格式
        
        
        String p2_OrdNo = req.getParameter(JFTAppConstants.p2_OrdNo);
        String p2_ResultCode = req.getParameter(JFTAppConstants.p2_ResultCode);
        String p2_Remark = req.getParameter(JFTAppConstants.p2_Remark);
        String p2_SignType = req.getParameter(JFTAppConstants.p2_SignType);
        String p2_SignInfo = req.getParameter(JFTAppConstants.p2_SignInfo);
        
        StringBuffer stream = new StringBuffer();
		
        stream.append(JFTAppConstants.p2_MerId).append("=").append(p2_MerId).append("&");
        stream.append(JFTAppConstants.p2_OrdId).append("=").append(p2_OrdId).append("&");
        stream.append(JFTAppConstants.p2_OrdAmt).append("=").append(p2_OrdAmt).append("&");
        stream.append(JFTAppConstants.p2_OrdNo).append("=").append(p2_OrdNo).append("&");
        stream.append(JFTAppConstants.p2_ResultCode).append("=").append(p2_ResultCode).append("&");
        stream.append(JFTAppConstants.p2_Remark).append("=").append(p2_Remark).append("&");
        stream.append(JFTAppConstants.p2_SignType).append("=").append(p2_SignType).append("");
        
        System.out.println("支付响应做MD5参数="+stream);
        
        String SignInfo = Encrypt.MD5( Encrypt.MD5(stream.toString()) + "" + MER_KEY );
        System.out.println("支付响应做MD5参数签名="+SignInfo);
        
        String stream_new = stream.toString() + "&SignInfo="+SignInfo;
        
		return p2_SignInfo.equals(SignInfo) || p2_SignInfo.equals(stream_new);
	}
	
	/**
	 * 参数过滤
	 * @param text
	 * @return
	 */
	@SuppressWarnings("unused")
	private String formatString(String text){ 
		if(text == null) {
			return ""; 
		}
		return text;
	}
	 
	public static void main(String[] args) {
		System.out.println(Encrypt.MD5("MerId=5900696&OrdId=TEST1487313631935&OrdAmt=1.00&PayType=DTDP&CurCode=RMB&BankCode=CEBB&ProductInfo=Test Product&Remark=Test Remark&ReturnURL=http://test.8payment.com/gateway/returnUrl.php&NotifyURL=http://test.8payment.com/gateway/notifyUrl.php&SignType=MD5&&MerKey=DFPCC8pKPpXAjbWmMBX62BJjbck3Z3CS").equals("cc32bff337a2f97bc8ed33e73729d393"));
		System.out.println("cc32bff337a2f97bc8ed33e73729d393");
	}

}
