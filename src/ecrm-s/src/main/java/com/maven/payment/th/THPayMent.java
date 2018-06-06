package com.maven.payment.th;

import com.maven.payment.PayInterface;
import com.maven.util.AttrCheckout;

/**
 * 通汇快捷支付
 * @author 
 *
 */
public class THPayMent<M extends THMerchantConfig,O extends THOrderConfig> implements PayInterface<M,O>{

	@Override
	public String save(THMerchantConfig merchant, THOrderConfig order) throws Exception {
		AttrCheckout.checkout(merchant, false, THMerchantConfig.M_Save_Paramters.paramters());
		AttrCheckout.checkout(order, false, THOrderConfig.O_Save_Paramters.paramters());
		THSave save = new THSave(merchant.getSaveUrl(), merchant.getMerchantCode(), merchant.getMerchantKey(), 
				merchant.getCallbackUrl(), merchant.getPageToUserUrl(), merchant.getReferer(), merchant.getReturnParams());
		String skipUrl = save.getUrl(order.getBankCode(), order.getOrderNo(), order.getOrderAmount(), order.getCustomerPhone(), 
							order.getReceiveAddr(), order.getProductName(), order.getProductNum(), order.getCustomerIp());
		return skipUrl;
	}

	@Override
	public String pay(THMerchantConfig merchant, THOrderConfig order) throws Exception {
		AttrCheckout.checkout(merchant, false, THMerchantConfig.M_Pay_Paramters.paramters());
		AttrCheckout.checkout(order, false, THOrderConfig.O_Pay_Paramters.paramters());
		THPay pay = new THPay(merchant.getMerchantCode(), merchant.getMerchantKey(), merchant.getPayUrl());
		String result = pay.remit(order.getBankAccount(), order.getBankCardNo(), order.getOrderAmount(), order.getBankCode());
		return result.equals("success")?PayInterface.PAY_SUCCESS:result;
	}
	

}
