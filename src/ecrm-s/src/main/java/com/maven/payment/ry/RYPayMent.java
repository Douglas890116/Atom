package com.maven.payment.ry;

import com.maven.entity.ThirdpartyPaymentType.Enum_ThirdpartyPaymentType;
import com.maven.payment.PayInterface;
import com.maven.payment.ry.RYAppConstants.RY_PayType;
import com.maven.util.RandomString;

public class RYPayMent<M extends RYMerchantConfig, O extends RYOrderConfig> implements PayInterface<M, O> {

	@Override
	public String save(RYMerchantConfig merchant, RYOrderConfig order) throws Exception {
		RYSave save = new RYSave();
		return save.getUrl(merchant, order);
	}

	@Override
	public String pay(M merchant, O order) throws Exception {
		// TODO 出款接口
		return null;
	}

	public static void main(String[] args) {
		try {
			PayInterface<RYMerchantConfig, RYOrderConfig> payment = new RYPayMent<RYMerchantConfig, RYOrderConfig>();
			RYMerchantConfig __merchant = new RYMerchantConfig();
			RYOrderConfig __order = new RYOrderConfig();
			String bankNo = "QQWALLET";
			String thirdPaymentType = Enum_ThirdpartyPaymentType.如意微信支付宝.value;
			
			__merchant.setMerId("990020073");
			__merchant.setMerKey("mtWksytQdzDPCpR6FiEiQK7ib4r6FeiH");
			__merchant.setPayUrl("http://pay.pushijiazhi.com/gateway/");
			__merchant.setNotifyUrl("http://api.hyzonghe.net/TPayment/RYPayCallback");
			__merchant.setReturnUrl("http://api.hyzonghe.net/TPayment/success");

			__order.setBankCode(bankNo);
			__order.setMerOrdAmt("10.00");
			__order.setMerOrdId(RandomString.UUID());
			// 根据参数设置支付类型
			// remark记录支付类型，方便回调时区分
			if (thirdPaymentType.equals(Enum_ThirdpartyPaymentType.如意网关支付.value)) {
				__order.setPayType(RY_PayType.网关支付.value);
				__order.setRemark(RY_PayType.网关支付.value);
			} else if (thirdPaymentType.equals(Enum_ThirdpartyPaymentType.如意微信支付宝.value)) {
					__order.setPayType(RY_PayType.QQ钱包扫码支付.value);
					__order.setRemark(RY_PayType.QQ钱包扫码支付.value);
			}else {
				__order.setPayType(RY_PayType.快捷支付.value);
				__order.setRemark(RY_PayType.快捷支付.value);
			}
			
			 System.out.println(payment.save(__merchant, __order));;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.getStackTrace();
		}
	}
}