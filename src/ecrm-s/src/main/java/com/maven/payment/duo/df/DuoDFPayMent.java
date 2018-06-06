package com.maven.payment.duo.df;

import com.maven.payment.PayInterface;
/**
 * 多多代付接口
 * @author klay
 *
 * @param <M>
 * @param <O>
 */
public class DuoDFPayMent<M extends DuoDFMerchantConfig, O extends DuoDFOrderConfig> implements PayInterface<M, O> {

	@Override
	public String save(DuoDFMerchantConfig merchant, DuoDFOrderConfig order) throws Exception {
		return null;
	}

	@Override
	public String pay(DuoDFMerchantConfig merchant, DuoDFOrderConfig order) throws Exception {
		DuoDFPay pay = new DuoDFPay();
		return pay.getUrl(merchant, order);
	}
	
	public static void main(String[] args) {
		
	}
}