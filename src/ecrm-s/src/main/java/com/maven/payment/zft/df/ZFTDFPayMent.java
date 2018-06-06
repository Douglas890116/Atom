package com.maven.payment.zft.df;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.maven.payment.PayInterface;
import com.maven.util.RandomString;

import net.sf.json.JSONObject;

public class ZFTDFPayMent<M extends ZFTDFMerchantConfig, O extends ZFTDFOrderConfig> implements PayInterface<M, O> {

	@Override
	public String save(ZFTDFMerchantConfig merchant, ZFTDFOrderConfig order) throws Exception {
		return null;
	}

	@Override
	public String pay(ZFTDFMerchantConfig merchant, ZFTDFOrderConfig order) throws Exception {
		ZFTDFPay pay = new ZFTDFPay();
		String result = pay.getUrl(merchant, order);
		if (StringUtils.isNotBlank(result)) {
			JSONObject json = JSONObject.fromObject(result);
			String respCode = json.getString(ZFTDFAppConstants.rep_respCode);
			String respMessage = json.getString(ZFTDFAppConstants.rep_respMessage);
			if ("S0001".equals(respCode)) {
				return PayInterface.PAY_SUCCESS;
			}
			System.out.println("支付通代付失败：CODE-" + respCode);
			System.out.println("支付通代付失败：MESG-" + respMessage);
			return respMessage;
		}
		return "FAIL";
	}

	public static void main(String[] args) {
		PayInterface<ZFTDFMerchantConfig, ZFTDFOrderConfig> payment = new ZFTDFPayMent<ZFTDFMerchantConfig, ZFTDFOrderConfig>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyMMdd");
		ZFTDFMerchantConfig merchant = new ZFTDFMerchantConfig();
		merchant.setMerId("101000000002504");
		merchant.setShaKey("5g13e78ce14g2df1198g35e63cc247c756f684c134c95664g23e7g9d21b0c3aa");
		merchant.setPayUrl("https://client.zftong.net/agentPay/v1/batch/");
		
		ZFTDFOrderConfig order = new ZFTDFOrderConfig();
		order.setOrderNo(RandomString.UUID().substring(2));
		order.setOrderAmount("1.0");
		order.setOrderDate(dateFormat.format(new Date()));
		order.setBankCard("156481234189456462168");
		order.setUserName("测试");
		order.setBankName("招商银行");
		order.setBranchName("BranchName");
		order.setProvince("Province");
		order.setCity("City");
		
		try {
			payment.pay(merchant, order);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
