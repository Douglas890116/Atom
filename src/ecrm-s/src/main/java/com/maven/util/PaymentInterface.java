package com.maven.util;

import java.util.List;

import com.maven.entity.EnterpriseThirdpartyPaymentAgument;
import com.maven.entity.TakeDepositRecord;
import com.maven.payment.PayInterface;
import com.maven.payment.th.THMerchantConfig;
import com.maven.payment.th.THOrderConfig;
import com.maven.payment.th.THPayMent;

/**
 * 出款支付接口类
 * @author Administrator
 *
 */
public class PaymentInterface {
	
	private PayInterface<THMerchantConfig,THOrderConfig> payObject = new THPayMent<THMerchantConfig, THOrderConfig>();
	
	/**
	 * 通汇支付方法
	 * @param list
	 * @param arrayObject
	 * @return
	 */
	public String tongHuiPaymentMethod(List<EnterpriseThirdpartyPaymentAgument> list,TakeDepositRecord takeDepositRecord){
		try{
			//出款接口验证对象
			THMerchantConfig payObjects = new THMerchantConfig();
			//支付链接
			payObjects.setPayUrl("http://pay.41.cn/remit");
			//支付接口编码
			payObjects.setMerchantCode(list.get(0).getAgumentvalue());
			//支付接口加密值
			payObjects.setMerchantKey(list.get(1).getAgumentvalue());
			//出款订单参数对象
			THOrderConfig orderObjects = new THOrderConfig();
			//收款银行编码
			orderObjects.setBankCode(takeDepositRecord.getEmployeepaymentbank());
			//收款人姓名
			orderObjects.setBankAccount(takeDepositRecord.getEmployeepaymentname());
			//收款人银行卡号
			orderObjects.setBankCardNo(takeDepositRecord.getEmployeepaymentaccount());
			//出款金额
			orderObjects.setOrderAmount(takeDepositRecord.getOrderamount().toString().replaceFirst("-", "").trim());
			//调用支付方法
			String results = payObject.pay(payObjects,orderObjects);
			return results;
		}catch(Exception e){
			return e.getMessage();
		}
	}
}
