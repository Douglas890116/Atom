package com.maven.payment.rf.df;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.maven.payment.PayInterface;
import com.maven.payment.rf.df.RFDFAppConstants.Enum_DirectPaidout;
import com.maven.util.RandomString;

public class RFDFPayMent<M extends RFDFMerchantConfig, O extends RFDFOrderConfig> implements PayInterface<M, O> {

	@Override
	public String save(RFDFMerchantConfig merchant, RFDFOrderConfig order) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String pay(RFDFMerchantConfig merchant, RFDFOrderConfig order) throws Exception {
		RFDFPay pay = new RFDFPay();
		pay.getUrl(merchant, order);
		return null;
	}

	public static void main(String[] args) {
		PayInterface<RFDFMerchantConfig, RFDFOrderConfig> payment = new RFDFPayMent<RFDFMerchantConfig, RFDFOrderConfig>();
		
		RFDFMerchantConfig merchant = new RFDFMerchantConfig();
		merchant.setPartyId("gateway_QRFT001607");
		merchant.setAccountId("gateway_QRFT001607001");
		merchant.setPassword("cbWSFOAQMf");
		merchant.setMd5Key("1d218b497080792c485053cda7e74c41af897a4c2e71183faf00d6847b3c87ea786f861d7b1b2dddbe6f251c8831b5c1dcb06ec5d62e9d8423902c66d612a1933a333c4c4a1d3215314532a0e4da5d67");
		merchant.setDirectPaidout(Enum_DirectPaidout.直接下发.value);
		merchant.setNote("Note");
		merchant.setPayUrl("https://portal.rfupayadv.com/Main/api_paidout/submitPaidout");
		
		RFDFOrderConfig order = new RFDFOrderConfig();
		order.setOrderNo("TEST"+RandomString.UUID().substring(6));
		order.setOrderAmount("1.0");
		order.setBankName("招商銀行");
		order.setUserName("陳大文");
		order.setCardNum("6214837830927483");
		order.setBankProvice("广东");
		order.setBankCity("深圳");
		order.setBankBranch("深圳罗湖支行");
		order.setRemark("TEST");
		
		try {
			payment.pay(merchant, order);
			
			System.out.println("请求时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
