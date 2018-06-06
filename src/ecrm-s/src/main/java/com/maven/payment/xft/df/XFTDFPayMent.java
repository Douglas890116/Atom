package com.maven.payment.xft.df;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.maven.payment.PayInterface;
import com.maven.payment.zft.df.ZFTDFAppConstants;
import com.maven.util.RandomString;

import net.sf.json.JSONObject;

public class XFTDFPayMent<M extends XFTDFMerchantConfig, O extends XFTDFOrderConfig> implements PayInterface<M, O> {

	@Override
	public String save(XFTDFMerchantConfig merchant, XFTDFOrderConfig order) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String pay(XFTDFMerchantConfig merchant, XFTDFOrderConfig order) throws Exception {
		XFTDFUtil pay = new XFTDFUtil();
		String result = pay.getUrl(merchant, order);
		if (StringUtils.isNotBlank(result)) {
			JSONObject json = JSONObject.fromObject(result);
			String respCode = json.getString(ZFTDFAppConstants.rep_respCode);
			String respMessage = json.getString(ZFTDFAppConstants.rep_respMessage);
			if ("S0001".equals(respCode)) {
				return PayInterface.PAY_SUCCESS;
			}
			System.out.println("信付通代付失败：CODE-" + respCode);
			System.out.println("信付通代付失败：MESG-" + respMessage);
			return respMessage;
		}
		return "FAIL";
	}

	public static void main(String[] args) {
		PayInterface<XFTDFMerchantConfig, XFTDFOrderConfig> payment = new XFTDFPayMent<XFTDFMerchantConfig, XFTDFOrderConfig>();
		
		XFTDFMerchantConfig merchant = new XFTDFMerchantConfig();
		merchant.setMerId("100000000002363");
		merchant.setShaKey("6cf103f1755a4ef65210441a576a6336a9d3bead453eaaffg0438d3b47fg33ae");
		merchant.setPayUrl("https://client.xfuoo.com/agentPay/v1/batch/");
		
		XFTDFOrderConfig order = new XFTDFOrderConfig();
		order.setOrderNo("TEST"+RandomString.UUID().substring(6));
		order.setOrderAmount("10.0");
		order.setOrderDate(new SimpleDateFormat("yyyMMdd").format(new Date()));
		
		order.setBankCard("6226090221368562");
		order.setUserName("扎克");
		order.setBankName("招商银行");
		order.setBranchName("招商银行");
		order.setProvince("province");
		order.setCity("city");
		
		try {
			String result = payment.pay(merchant, order);
			System.out.println("代付结果：" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
