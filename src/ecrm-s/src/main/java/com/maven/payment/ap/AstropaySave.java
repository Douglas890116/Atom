package com.maven.payment.ap;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import com.maven.payment.ap.AstropayAppConstants.Astro_CashOutType;
import com.maven.util.Encrypt;
import com.maven.util.HttpPostUtil;

public class AstropaySave {
	private static final float version = (float) 2.0;
	private static final int duplicate_window = 60;
	private static final String response_format = "json";
	private static final String delim_char = "|";
	private static final boolean delim_data = true;
	private static final String method = "CC";
	private static final String trans_id = "";
	
	/**
	 * 发起支付
	 * @param merchant
	 * @param order
	 * @return
	 */
	public String doPay(AstropayMerchantConfig merchant, AstropayOrderConfig order) {
		Map<String, String> params = new HashMap<String, String>();

		params.put(AstropayAppConstants.req_x_login, merchant.getMerLogin());
		params.put(AstropayAppConstants.req_x_tran_key, merchant.getMerTransKey());
		params.put(AstropayAppConstants.req_x_type, order.getOrderType());
		params.put(AstropayAppConstants.req_x_card_num, order.getUserCardNum());
		params.put(AstropayAppConstants.req_x_card_code, order.getUserCardCode());
		params.put(AstropayAppConstants.req_x_exp_date, order.getUserCardExpDate());
		params.put(AstropayAppConstants.req_x_amount, order.getOrderAmount());
		params.put(AstropayAppConstants.req_x_currency, order.getCurrency());
		params.put(AstropayAppConstants.req_x_unique_id, order.getUserNo());
		params.put(AstropayAppConstants.req_x_invoice_num, order.getOrderNo());
		params.put(AstropayAppConstants.req_x_version, version+"");
		params.put(AstropayAppConstants.req_x_duplicate_window, duplicate_window+"");
		params.put(AstropayAppConstants.req_x_response_format, response_format);
		params.put(AstropayAppConstants.req_x_delim_char, delim_char);
		params.put(AstropayAppConstants.req_x_delim_data, delim_data+"");
		params.put(AstropayAppConstants.req_x_method, method);
		params.put(AstropayAppConstants.req_x_trans_id, trans_id);
		
		String result = HttpPostUtil.doPostSubmitMap(merchant.getPayUrl(), params);
		
		System.out.println("支付请求结果：" + result);
		
		return result;
	}
	/**
	 * 查询支付结果
	 * @param merchant
	 * @param order
	 * @param InvoiceNo
	 * @return
	 */
	public String getPayStatus(AstropayMerchantConfig merchant, AstropayOrderConfig order, String InvoiceNo) {
		Map<String, String> params = new HashMap<String, String>();
		
		params.put(AstropayAppConstants.req_x_login, merchant.getMerLogin());
		params.put(AstropayAppConstants.req_x_trans_key, merchant.getMerTransKey());
		params.put(AstropayAppConstants.req_x_invoice_num, InvoiceNo);
		params.put(AstropayAppConstants.req_x_type, order.getOrderType());
		params.put(AstropayAppConstants.req_x_response_format, response_format);
		params.put(AstropayAppConstants.req_x_delim_char, delim_char);
		
		String result = HttpPostUtil.doPostSubmitMap(merchant.getPayUrl(), params);
		
		System.out.println("查询结果：" + result);
		
		return result;
	}
	/**
	 * 验签
	 * @param merLogin
	 * @param transactionId
	 * @param amount
	 * @param md5Hash
	 * @return
	 */
	public boolean checkSign(String merLogin, String transactionId, String amount, String md5Hash) {
		StringBuffer sb = new StringBuffer();
		sb.append(merLogin).append(transactionId).append(amount);
		String localSign = Encrypt.MD5(sb.toString());
		return localSign.equals(md5Hash);
	}
/*================================================== 华丽的分割线 ==================================================*/
	/**
	 * 发起出款
	 * @param merchant
	 * @param order
	 * @return
	 */
	public String doCashOut(AstropayMerchantConfig merchant, AstropayOrderConfig order) {
		String SHA1Sign = getSign(merchant, order);
		
		Map<String, String> params = new HashMap<String, String>();
		
		params.put(AstropayAppConstants.req_x_login, merchant.getMerLogin());
		params.put(AstropayAppConstants.req_x_trans_key, merchant.getMerTransKey());
		params.put(AstropayAppConstants.req_x_amount, order.getOrderAmount());
		params.put(AstropayAppConstants.req_x_currency, order.getCurrency());
		params.put(AstropayAppConstants.req_x_name, order.getUserName());
		params.put(AstropayAppConstants.req_x_document, order.getUserDocument());
		params.put(AstropayAppConstants.req_x_country, order.getUserCountry());
		params.put(AstropayAppConstants.req_x_control, SHA1Sign);
		
		if (Astro_CashOutType.FastCashout.value.equals(order.getOrderType())) {
			params.put(AstropayAppConstants.req_x_email, order.getUserEmail());
		} else if (Astro_CashOutType.MerchantCashout.value.equals(order.getOrderType())) {
			params.put(AstropayAppConstants.req_x_name, order.getUserName());
		} else if (Astro_CashOutType.MobileCashout.value.equals(order.getOrderType())) {
			params.put(AstropayAppConstants.req_x_mobile_number, order.getMobileNum());
		}
		
		String result = HttpPostUtil.doPostSubmitMap(merchant.getPayUrl(), params);
		
		System.out.println("出款请求结果：" + result);
		
		return result;
	}
	/**
	 * 获取出款的签名
	 * @param merchant
	 * @param order
	 * @return
	 */
	private String getSign(AstropayMerchantConfig merchant, AstropayOrderConfig order) {
		StringBuffer sb = new StringBuffer();
		sb.append(merchant.getMerSecretKey()).append(order.getOrderAmount()).append(order.getCurrency());
		// $control_string = sha1 ( $secret . number_format ($x_amount, 2, '.', '') . $x_currency . $x_email );
		// $control_string = sha1 ( $secret . number_format ($x_amount, 2, '.', '') . $x_currency . $x_name );
		// $control_string = sha1 ( $secret . number_format ($x_amount, 2, '.', '') . $x_currency . $x_mobile_number );
		if (Astro_CashOutType.FastCashout.value.equals(order.getOrderType())) {
			sb.append(order.getUserEmail());
		} else if (Astro_CashOutType.MerchantCashout.value.equals(order.getOrderType())) {
			sb.append(order.getUserName());
		} else if (Astro_CashOutType.MobileCashout.value.equals(order.getOrderType())) {
			sb.append(order.getMobileNum());
		}
		System.out.println("Astropay出款签名原文：" + sb.toString());
		
		String sign = SHA1(sb.toString());
		System.out.println("Astropay出款签名：" + sign);
		
		return sign;
	}
	/**
	 * 出款验签
	 * @param merchant
	 * @param order
	 * @param id_cashout
	 * @param x_currency
	 * @return
	 */
	public boolean checkSign(AstropayMerchantConfig merchant, AstropayOrderConfig order, String id_cashout, String amount, String x_currency, String SHA1Sign) {
		// $control_signature = sha1($secret.$id_cashout.$x_email.        number_format($x_amount, 2, '.', '').$x_currency)
		// $control_signature = sha1($secret.$id_cashout.                 number_format($x_amount, 2, '.', '').$x_currency)
		// $control_signature = sha1($secret.$id_cashout.$x_mobile_number.number_format($x_amount, 2, '.', '').$x_currency)
		StringBuffer sb = new StringBuffer();
		sb.append(merchant.getMerSecretKey()).append(id_cashout);
		if (Astro_CashOutType.FastCashout.value.equals(order.getOrderType())) {
			sb.append(order.getUserEmail());
		} else if (Astro_CashOutType.MobileCashout.value.equals(order.getOrderType())) {
			sb.append(order.getMobileNum());
		}
		sb.append(amount).append(x_currency);
		System.out.println("出款响应签名原文：" + sb.toString());
		
		String sign = SHA1(sb.toString());
		System.out.println("出款响应签名：" + sign);
		
		return sign.equals(SHA1Sign);
	}
	/**
	 * @param decript
	 *            要加密的字符串
	 * @return 加密的字符串 SHA1加密
	 */
	public final static String SHA1(String decript) {
		try {
			MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
			digest.update(decript.getBytes());
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
}