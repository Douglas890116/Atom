package com.maven.payment.leying;

import java.util.Date;

import com.maven.payment.PayInterface;
import com.maven.util.AttrCheckout;
import com.maven.util.DateUtils;
import com.maven.util.RandomString;

public class LEYINGPayMent<M extends LEYINGMerchantConfig,O extends LEYINGOrderConfig> implements PayInterface<M,O>{

	@Override
	public String save(M merchant, O order) throws Exception {
		AttrCheckout.checkout(merchant, false, LEYINGMerchantConfig.M_Save_Paramters.paramters());
		AttrCheckout.checkout(order, false, LEYINGOrderConfig.O_Save_Paramters.paramters());
		LEYINGSave save = new LEYINGSave(merchant.getVersion(), merchant.getPayType(), merchant.getReturnUrl(),
				merchant.getNoticeUrl(), merchant.getPartnerID(), merchant.getCharset(), merchant.getSignType(),
				merchant.getMd5key(), merchant.getSaveUrl());
		String skipUrl = save.getUrl(order.getSerialID(), order.getSubmitTime(), order.getOrderDetails(), order.getTotalAmount());
		return skipUrl;
	}

	@Override
	public String pay(M merchant, O order) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public static void main(String[] args) throws Exception {
		PayInterface<LEYINGMerchantConfig,LEYINGOrderConfig> __yyePay = new LEYINGPayMent<LEYINGMerchantConfig, LEYINGOrderConfig>();
		LEYINGMerchantConfig merchant = new LEYINGMerchantConfig();
		merchant.setPartnerID("10000438336");
		merchant.setSaveUrl("https://www.funpay.com/website/pay.htm");
		merchant.setReturnUrl("");
		merchant.setNoticeUrl("http://dw.com/callback");
		merchant.setMd5key("30820122300d06092a864886f70d01010105000382010f003082010a02820101009fc0dd7c03a5419033874a75130b4ab1eee7e503c453e7ab7114c8e287efb8970b531e98825acb3de8eeef53edcda7acc0b4e4a4f4e6802de98fd73e4ca45409d971494538afb04e1f5cb96f8c63b633863fd3ab4d5ce746b2f285db16a24d52b3f6bc20842b0d8c499a9acdf2a6752cdb27fb7ac9f2e457e1ea70ec88603f19f78baf946ff0f363bb8aedd4c62fb09911b0e285991e303f513d59e458f9ad5429ea54bd67c7794042e8df5d594c74126e9d615af2874d550c84daec4cb706c744f0333fba98a94f1238e3982dc89b202f1076a25b6f2b9986dba0a739136b214ba33373b7f4957a56b5e48a9c039babbaeb7659c4bfdb621c69559c3d3f701b0203010001");
		
		LEYINGOrderConfig  __yeeorder =  new LEYINGOrderConfig();
		__yeeorder.setSerialID(RandomString.UUID());
		__yeeorder.setSubmitTime(DateUtils.FormatDateToString(new Date(), "yyyyMMddHHmmss"));
		__yeeorder.setOrderDetails(new LEYINGOrder(RandomString.UUID(), "100").toString());
		__yeeorder.setTotalAmount("100");
		System.out.println(__yyePay.save(merchant, __yeeorder));
	}
	

}
