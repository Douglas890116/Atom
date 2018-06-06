package com.maven.payment;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.maven.entity.TakeDepositRecord;
import com.maven.entity.ThirdpartyPaymentBank;
import com.maven.entity.ThirdpartyPaymentType.Enum_ThirdpartyPaymentType;
import com.maven.payment.dd.DDMerchantConfig;
import com.maven.payment.dd.DDOrderConfig;
import com.maven.payment.dd.DDPayMent;
import com.maven.payment.hr.df.HRDFAppConstants;
import com.maven.payment.hr.df.HRDFMerchantConfig;
import com.maven.payment.hr.df.HRDFOrderConfig;
import com.maven.payment.hr.df.HRDFPayMent;
import com.maven.payment.jeanpay.JEANPAYMerchantConfig;
import com.maven.payment.jeanpay.JEANPAYOrderConfig;
import com.maven.payment.jeanpay.JEANPAYPayMent;
import com.maven.payment.jh2.df.JH2DFMerchantConfig;
import com.maven.payment.jh2.df.JH2DFOrderConfig;
import com.maven.payment.jh2.df.JH2DFPayMent;
import com.maven.payment.lfdf.LfDFMerchantConfig;
import com.maven.payment.lfdf.LfDFOrderConfig;
import com.maven.payment.lfdf.LfDFPayMent;
import com.maven.payment.sdwx.SDWXMerchantConfig;
import com.maven.payment.sdwx.SDWXOrderConfig;
import com.maven.payment.sdwx.SDWXPayMent;
import com.maven.payment.th.THMerchantConfig;
import com.maven.payment.th.THOrderConfig;
import com.maven.payment.th.THPayMent;
import com.maven.payment.wft.df.WFTDFMerchantConfig;
import com.maven.payment.wft.df.WFTDFOrderConfig;
import com.maven.payment.wft.df.WFTDFPayMent;
import com.maven.payment.xf.XFMerchantConfig;
import com.maven.payment.xf.XFOrderConfig;
import com.maven.payment.xf.XFPayMent;
import com.maven.payment.xft.df.XFTDFMerchantConfig;
import com.maven.payment.xft.df.XFTDFOrderConfig;
import com.maven.payment.xft.df.XFTDFPayMent;
import com.maven.payment.xingfu.df.XingFDFMerchantConfig;
import com.maven.payment.xingfu.df.XingFDFOrderConfig;
import com.maven.payment.xingfu.df.XingFDFPayMent;
import com.maven.payment.ys.df.YSDFMerchantConfig;
import com.maven.payment.ys.df.YSDFOrderConfig;
import com.maven.payment.ys.df.YSDFPayMent;
import com.maven.payment.zft.df.ZFTDFMerchantConfig;
import com.maven.payment.zft.df.ZFTDFOrderConfig;
import com.maven.payment.zft.df.ZFTDFPayMent;
import com.maven.util.BeanToMapUtil;
import com.maven.util.RandomStringNum;

public class PayHelper {
	
	public static PayResult Pay(Map<String, Object> __agument, TakeDepositRecord __takeRecord,
			ThirdpartyPaymentBank __thirdpartyPaymentBank)throws Exception{
		String __thirdpartypaymenttypecode = String.valueOf(__agument.get("thirdpartypaymenttypecode"));
		
		if(StringUtils.isBlank(__thirdpartypaymenttypecode)){
			return new PayResult(false, "","未设置默认的快捷支付");
		}
		
		if(__thirdpartyPaymentBank==null||StringUtils.isBlank(__thirdpartyPaymentBank.getPaymenttypebankcode())){
			return new PayResult(false, "","支付银行编码错误");
		}
		
		if(__takeRecord==null){
			return new PayResult(false, "","支付订单为空");
		}
		
		if(StringUtils.isBlank(__takeRecord.getEmployeepaymentname())){
			return new PayResult(false, "","收款人姓名为空");
		}
		
		if(StringUtils.isBlank(__takeRecord.getEmployeepaymentaccount())){
			return new PayResult(false, "","收款人银行卡号为空");		
		}

		if(__takeRecord.getOrderamount()==null){
			return new PayResult(false, "","出款金额为空");
		}
		
		if(__thirdpartypaymenttypecode.equals(Enum_ThirdpartyPaymentType.通汇支付.value)){
			//进行支付
			String results = tonghuiPay(__agument, __takeRecord, __thirdpartyPaymentBank);
			//根据支付结果进行相应处理
			if(results.equals(PayInterface.PAY_SUCCESS)){
				return new PayResult(true, "通汇自动出款",results);
			}else{
				return new PayResult(false, "通汇自动出款",results);
			}
		}else if(__thirdpartypaymenttypecode.equals(Enum_ThirdpartyPaymentType.SD付款.value)){
			//进行支付
			String results = sdPay(__agument, __takeRecord, __thirdpartyPaymentBank);
			//根据支付结果进行相应处理
			if(results.equals(PayInterface.PAY_SUCCESS)){
				return new PayResult(true, "SD自动出款",results);
			}else{
				return new PayResult(false, "SD自动出款",results);
			}
		}else if(__thirdpartypaymenttypecode.equals(Enum_ThirdpartyPaymentType.极付出款.value)){
			//进行支付
			String results = jfPay(__agument, __takeRecord, __thirdpartyPaymentBank);
			//根据支付结果进行相应处理
			if(results.equals(PayInterface.PAY_SUCCESS)){
				return new PayResult(true, "JeanPay自动出款",results);
			}else{
				return new PayResult(false, "JeanPay自动出款",results);
			}
		}else if(__thirdpartypaymenttypecode.equals(Enum_ThirdpartyPaymentType.华仁代付.value)){
		    String results = HRDFPay(__agument, __takeRecord, __thirdpartyPaymentBank);
	          if(results.equals(PayInterface.PAY_SUCCESS)){
	                return new PayResult(true, "华仁代付自动出款",results);
	            }else{
	                return new PayResult(false, "华仁代付自动出款",results);
	            }
		}else if(__thirdpartypaymenttypecode.equals(Enum_ThirdpartyPaymentType.乐付出款.value)){
			//进行支付
			String results = lfPay(__agument, __takeRecord, __thirdpartyPaymentBank);
			//根据支付结果进行相应处理
			if(results.equals(PayInterface.PAY_SUCCESS)){
				return new PayResult(true, "乐付自动出款",results);
			}else{
				return new PayResult(false, "乐付自动出款",results);
			}
		}else if(__thirdpartypaymenttypecode.equals(Enum_ThirdpartyPaymentType.喜发代付.value)) {
			//进行支付
			String results = xfPay(__agument, __takeRecord, __thirdpartyPaymentBank);
			//根据支付结果进行相应处理
			if(results.equals(PayInterface.PAY_SUCCESS)){
				return new PayResult(true, "喜发代付自动出款",results);
			}else{
				return new PayResult(false, "喜发代付自动出款",results);
			}
		}else if(__thirdpartypaymenttypecode.equals(Enum_ThirdpartyPaymentType.点点代付.value)) {
			//进行支付
			String results = ddPay(__agument, __takeRecord, __thirdpartyPaymentBank);
			//根据支付结果进行相应处理
			if(results.equals(PayInterface.PAY_SUCCESS)){
				return new PayResult(true, "点点代付自动出款",results);
			}else{
				return new PayResult(false, "点点代付自动出款",results);
			}
		}else if(__thirdpartypaymenttypecode.equals(Enum_ThirdpartyPaymentType.支付通代付.value)) {
			//进行支付
			String results = ZFTDFPay(__agument, __takeRecord, __thirdpartyPaymentBank);
			//根据支付结果进行相应处理
			if(results.equals(PayInterface.PAY_SUCCESS)){
				return new PayResult(true, "支付通代付自动出款",results);
			}else{
				return new PayResult(false, "支付通代付自动出款",results);
			}
		}else if(__thirdpartypaymenttypecode.equals(Enum_ThirdpartyPaymentType.银盛代付.value)) {
			//进行支付
			String results = YSDFPay(__agument, __takeRecord, __thirdpartyPaymentBank);
			//根据支付结果进行相应处理
			if(results.equals(PayInterface.PAY_SUCCESS)){
				return new PayResult(true, "银盛代付自动出款",results);
			}else{
				return new PayResult(false, "银盛代付自动出款",results);
			}
		}else if(__thirdpartypaymenttypecode.equals(Enum_ThirdpartyPaymentType.星付代付.value)) {
			//进行支付
			String results = XFDFPay(__agument, __takeRecord, __thirdpartyPaymentBank);
			//根据支付结果进行相应处理
			if(results.equals(PayInterface.PAY_SUCCESS)){
				return new PayResult(true, "星付代付自动出款",results);
			}else{
				return new PayResult(false, "星付代付自动出款",results);
			}
		}else if(__thirdpartypaymenttypecode.equals(Enum_ThirdpartyPaymentType.旺付通代付.value)) {
			//进行支付
			String results = WFTDFPay(__agument, __takeRecord, __thirdpartyPaymentBank);
			//根据支付结果进行相应处理
			if(results.equals(PayInterface.PAY_SUCCESS)){
				return new PayResult(true, "旺付通代付自动出款", "请在旺付通后台确认出款结果.");
			}else{
				return new PayResult(false, "旺付通代付自动出款", results);
			}
		}else if(__thirdpartypaymenttypecode.equals(Enum_ThirdpartyPaymentType.信付通代付.value)) {
			//进行支付
			String results = XFTDFPay(__agument, __takeRecord, __thirdpartyPaymentBank);
			//根据支付结果进行相应处理
			if(results.equals(PayInterface.PAY_SUCCESS)){
				return new PayResult(true, "信付通代付自动出款", "请在信付通后台确认出款结果.");
			}else{
				return new PayResult(false, "信付通代付自动出款", results);
			}
		}else if(__thirdpartypaymenttypecode.equals(Enum_ThirdpartyPaymentType.神奇代付.value)) {
			//进行支付
			String results = JH2DFPay(__agument, __takeRecord, __thirdpartyPaymentBank);
			//根据支付结果进行相应处理
			if(results.equals(PayInterface.PAY_SUCCESS)){
				return new PayResult(true, "金塔聚合代付自动出款", "请在聚合后台确认出款结果.");
			}else{
				return new PayResult(false, "金塔聚合代付自动出款", results);
			}
		}
		return new PayResult(false, "","未发现默认出款的支付类型");
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			System.out.println(getRandPhoneno());
		}
	}
	
	private static String getRandPhoneno() {
		String aa = "13";
		return aa + RandomStringNum.createRandomString(9);
	}
	
	/**
	 * 乐付自动出款
	 * @param __agument
	 * @param __takeRecord
	 * @param __thirdpartyPaymentBank
	 * @return
	 * @throws Exception
	 */
	private static String lfPay(Map<String, Object> __agument, TakeDepositRecord __takeRecord, ThirdpartyPaymentBank __thirdpartyPaymentBank) throws Exception{
		
//		
		PayInterface<LfDFMerchantConfig,LfDFOrderConfig> __yyePay = new LfDFPayMent<LfDFMerchantConfig, LfDFOrderConfig>();
		LfDFMerchantConfig merchant = new LfDFMerchantConfig();
		merchant.setMerNo(__agument.get("MER_NO").toString());
		merchant.setMerKey(__agument.get("MEK_PRI_KEY").toString());
		merchant.setMerPubKey(__agument.get("MEK_PUB_KEY").toString());
		merchant.setPubKey(__agument.get("PUBLIC_KEY").toString());
		merchant.setNotiUrl("http://api.99scm.com/TPayment/LfPayCallback");
		merchant.setPayUrl(__agument.get("payUrl").toString());
		
		
		LfDFOrderConfig  __yeeorder =  new LfDFOrderConfig();
		__yeeorder.setOrdernumber(__takeRecord.getOrdernumber());
		__yeeorder.setPaymoney(__takeRecord.getOrderamount().abs().doubleValue());//因为订单金额是存的负数，所以再取负数变成正数
//		__yeeorder.setRequestTime("20161122102012");
		
		__yeeorder.setBankaccountname(__takeRecord.getEmployeepaymentname());
		__yeeorder.setBankaccountno(__takeRecord.getEmployeepaymentaccount());
		__yeeorder.setBankmobile_no(getRandPhoneno());//这里的手机号码可以不用真实的，但是格式要正确，而且每次要不一样
		__yeeorder.setBankprovince(__thirdpartyPaymentBank.getBankname());
		__yeeorder.setBankcity(__thirdpartyPaymentBank.getBankname());
		__yeeorder.setBanksitename(__thirdpartyPaymentBank.getBankname());
		__yeeorder.setBanksn(__thirdpartyPaymentBank.getPaymenttypebankcode());
		
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + " - PayHelper - 乐付出款接口开始发起出款：" + __takeRecord.getOrdernumber());
		
		//调用支付方法
		String results = __yyePay.pay(merchant,__yeeorder);
		return results;
	}
	
	/**
	 * JeanPay 极付自动出款
	 * @param __agument
	 * @param __takeRecord
	 * @param __thirdpartyPaymentBank
	 * @return
	 * @throws Exception
	 */
	private static String jfPay(Map<String, Object> __agument, TakeDepositRecord __takeRecord, ThirdpartyPaymentBank __thirdpartyPaymentBank) throws Exception{
		PayInterface<JEANPAYMerchantConfig,JEANPAYOrderConfig> __yyePay = new JEANPAYPayMent<JEANPAYMerchantConfig, JEANPAYOrderConfig>();
		//商户参数
		JEANPAYMerchantConfig __thmerchant = BeanToMapUtil.convertMap(__agument, JEANPAYMerchantConfig.class);
		__thmerchant.setMerKey(__agument.get("MER_KEY").toString());
		__thmerchant.setMerNo(__agument.get("MER_NO").toString());
		__thmerchant.setNotiUrl("http://api.99scm.com/TPayment/JfPayCallback");
		__thmerchant.setSignType("MD5");
		
		
		//出款订单参数对象
		JEANPAYOrderConfig __orderAguments = new JEANPAYOrderConfig();
		
		__orderAguments.setBankAccountCardno(__takeRecord.getEmployeepaymentaccount());
		__orderAguments.setBankAccountName(__takeRecord.getEmployeepaymentname());
		__orderAguments.setBankPoint(__thirdpartyPaymentBank.getBankname());
		__orderAguments.setOrderAmount(__takeRecord.getOrderamount().abs().doubleValue()+"");//因为订单金额是存的负数，所以再取负数变成正数
		__orderAguments.setOrderNo(__takeRecord.getOrdernumber());
		__orderAguments.setBankCode(__thirdpartyPaymentBank.getPaymenttypebankcode());
		__orderAguments.setBankName(__thirdpartyPaymentBank.getBankname());
		
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + " - PayHelper - 极付代付接口开始发起出款：" + __takeRecord.getOrdernumber());
		
		//调用支付方法
		String results = __yyePay.pay(__thmerchant,__orderAguments);
		return results;
	}
	
	
	/**
	 * SD自动出款
	 * @param __agument
	 * @param __takeRecord
	 * @param __thirdpartyPaymentBank
	 * @return
	 * @throws Exception
	 */
	private static String sdPay(Map<String, Object> __agument, TakeDepositRecord __takeRecord, ThirdpartyPaymentBank __thirdpartyPaymentBank) throws Exception{
		PayInterface<SDWXMerchantConfig, SDWXOrderConfig> pay = new SDWXPayMent<SDWXMerchantConfig, SDWXOrderConfig>();
		//商户参数
		SDWXMerchantConfig __thmerchant = BeanToMapUtil.convertMap(__agument, SDWXMerchantConfig.class);
		__thmerchant.setMerchantid(__agument.get("MER_NO").toString());
		
		//出款订单参数对象
		SDWXOrderConfig __orderAguments = new SDWXOrderConfig();
		//收款银行编码
		__orderAguments.setIntoBank1(__thirdpartyPaymentBank.getPaymenttypebankcode());
		//收款人姓名
		__orderAguments.setIntoName(__takeRecord.getEmployeepaymentname());
		//收款人银行卡号
		__orderAguments.setIntoAccount(__takeRecord.getEmployeepaymentaccount());
		//出款金额
		__orderAguments.setIntoAmount(__takeRecord.getOrderamount().abs().doubleValue());
		//订单号
		__orderAguments.setSerialNumber(__takeRecord.getOrdernumber());
		
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + " - PayHelper - SD代付接口开始发起出款：" + __takeRecord.getOrdernumber());
		
		//调用支付方法
		String results = pay.pay(__thmerchant,__orderAguments);
		return results;
	}
	
	/**
	 * 通汇自动出款
	 * @param __agument
	 * @param __takeRecord
	 * @param __thirdpartyPaymentBank
	 * @return
	 * @throws Exception
	 */
	private static String tonghuiPay(Map<String, Object> __agument, TakeDepositRecord __takeRecord, ThirdpartyPaymentBank __thirdpartyPaymentBank) throws Exception{
		PayInterface<THMerchantConfig, THOrderConfig> pay = new THPayMent<THMerchantConfig, THOrderConfig>();
		//商户参数
		THMerchantConfig __thmerchant = BeanToMapUtil.convertMap(__agument, THMerchantConfig.class);
		
		//出款订单参数对象
		THOrderConfig __orderAguments = new THOrderConfig();
		//收款银行编码
		__orderAguments.setBankCode(__thirdpartyPaymentBank.getPaymenttypebankcode());
		//收款人姓名
		__orderAguments.setBankAccount(__takeRecord.getEmployeepaymentname());
		//收款人银行卡号
		__orderAguments.setBankCardNo(__takeRecord.getEmployeepaymentaccount());
		//出款金额
		__orderAguments.setOrderAmount(__takeRecord.getOrderamount().abs().toString());
		
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + " - PayHelper - 汇通代付接口开始发起出款：" + __takeRecord.getOrdernumber());
		
		//调用支付方法
		String results = pay.pay(__thmerchant,__orderAguments);
		return results;
	}

	private static String HRDFPay(Map<String, Object> __agument, TakeDepositRecord __takeRecord, ThirdpartyPaymentBank __thirdpartyPaymentBank) throws Exception{
		PayInterface<HRDFMerchantConfig, HRDFOrderConfig> hrdf = new HRDFPayMent<HRDFMerchantConfig, HRDFOrderConfig>();
		// 商户参数
		HRDFMerchantConfig __thmerchant = BeanToMapUtil.convertMap(__agument, HRDFMerchantConfig.class);
		__thmerchant.setReturnUrl("http://api.hyzonghe.net/TPayment/success");
		// 出款订单参数
		HRDFOrderConfig __orderAguments = new HRDFOrderConfig();
		// 订单号
		__orderAguments.setV_oid(HRDFAppConstants.getOderNumber());
		// 代付总金额
		__orderAguments.setV_amount(__takeRecord.getOrderamount().abs().toString());
		// 收款人姓名
		__orderAguments.setV_payeename(__takeRecord.getEmployeepaymentname());
		// 收款人卡号
		__orderAguments.setV_payeecard(__takeRecord.getEmployeepaymentaccount());
		// 开户省
		__orderAguments.setV_accountprovince("accountprovince");
		// 开户市
		__orderAguments.setV_accountcity("accountcity");
		// 收款银行名称
		__orderAguments.setV_bankname(__thirdpartyPaymentBank.getBankname());
		// 收款银行编码
		__orderAguments.setV_bankno(__thirdpartyPaymentBank.getPaymenttypebankcode());
		
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + " - PayHelper - 华仁代付接口开始发起出款：" + __takeRecord.getOrdernumber());

		return hrdf.pay(__thmerchant, __orderAguments);
	}
	
	private static String xfPay(Map<String, Object> __agument, TakeDepositRecord __takeRecord, ThirdpartyPaymentBank __thirdpartyPaymentBank) throws Exception{
		
		PayInterface<XFMerchantConfig, XFOrderConfig> pay = new XFPayMent<XFMerchantConfig, XFOrderConfig>();
		// 商户参数
		XFMerchantConfig merchant = BeanToMapUtil.convertMap(__agument, XFMerchantConfig.class);
		
		// 出款订单参数
		XFOrderConfig order = new XFOrderConfig();
		// 代付订单号
		order.setOrderId(__takeRecord.getOrdernumber());
		// 代付金额
		order.setAmount(__takeRecord.getOrderamount().abs().doubleValue() + "");
		// 订单时间
		order.setTime((System.currentTimeMillis() / 1000) + "");
		// 银行编码
		order.setBank(__thirdpartyPaymentBank.getPaymenttypebankcode());
		// 收款人卡号
		order.setCardnum(__takeRecord.getEmployeepaymentaccount());
		// 收款人姓名
		order.setUsername(__takeRecord.getEmployeepaymentname());

		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + " - PayHelper - 代收付代付接口开始发起出款：" + __takeRecord.getOrdernumber());
		
		return pay.pay(merchant, order);
	}
	
	private static String ddPay(Map<String, Object> __agument, TakeDepositRecord __takeRecord, ThirdpartyPaymentBank __thirdpartyPaymentBank) throws Exception{

		PayInterface<DDMerchantConfig, DDOrderConfig> pay = new DDPayMent<DDMerchantConfig, DDOrderConfig>();
		// 商户参数
		DDMerchantConfig merchant = BeanToMapUtil.convertMap(__agument, DDMerchantConfig.class);

		// 出款订单参数
		DDOrderConfig order = new DDOrderConfig();
		// 代付订单号
		order.setOrderNo(__takeRecord.getOrdernumber());
		order.setAmount(__takeRecord.getOrderamount().abs().multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_DOWN).toString());
		order.setCustomerName(__takeRecord.getEmployeepaymentname());
		order.setCustomerCertType("01");
		order.setCustomerCertId(__takeRecord.getEmployeecode());
		order.setCustomerPhone(__takeRecord.getEmployeecode());
		order.setBankNo(__thirdpartyPaymentBank.getPaymenttypebankcode());
		order.setBankName(__thirdpartyPaymentBank.getPaymenttypebankcode());
		order.setBankCardNo(__takeRecord.getEmployeepaymentaccount());
		
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + " - PayHelper - 点点代付接口开始发起出款：" + __takeRecord.getOrdernumber());

		return pay.pay(merchant, order);
	}
	
	private static String ZFTDFPay(Map<String, Object> __agument, TakeDepositRecord __takeRecord, ThirdpartyPaymentBank __thirdpartyPaymentBank) throws Exception{

		PayInterface<ZFTDFMerchantConfig, ZFTDFOrderConfig> payment = new ZFTDFPayMent<ZFTDFMerchantConfig, ZFTDFOrderConfig>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyMMdd");
		// 商户参数
		ZFTDFMerchantConfig merchant = BeanToMapUtil.convertMap(__agument, ZFTDFMerchantConfig.class);

		// 出款订单参数
		ZFTDFOrderConfig order = new ZFTDFOrderConfig();
		// 代付订单号
		order.setOrderNo(__takeRecord.getOrdernumber());
		order.setOrderAmount(__takeRecord.getOrderamount().abs().setScale(2, BigDecimal.ROUND_DOWN).toString());
		order.setOrderDate(dateFormat.format(new Date()));
		order.setBankCard(__takeRecord.getEmployeepaymentaccount());
		order.setUserName(__takeRecord.getEmployeepaymentname());
		order.setBankName(__thirdpartyPaymentBank.getPaymenttypebankcode());
		order.setBranchName(__thirdpartyPaymentBank.getBankname());
		order.setProvince("province");
		order.setCity("city");

		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + " - PayHelper - 支付通代付接口开始发起出款：" + __takeRecord.getOrdernumber());
		
		return payment.pay(merchant, order);
	}
	/**
	 * 银盛代付
	 * @param __agument
	 * @param __takeRecord
	 * @param __thirdpartyPaymentBank
	 * @return
	 * @throws Exception
	 */
	private static String YSDFPay(Map<String, Object> __agument, TakeDepositRecord __takeRecord, ThirdpartyPaymentBank __thirdpartyPaymentBank) throws Exception{

		PayInterface<YSDFMerchantConfig, YSDFOrderConfig> payment = new YSDFPayMent<YSDFMerchantConfig, YSDFOrderConfig>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 商户参数
		YSDFMerchantConfig merchant = BeanToMapUtil.convertMap(__agument, YSDFMerchantConfig.class);

		// 出款订单参数
		YSDFOrderConfig order = new YSDFOrderConfig();
		// 代付订单号
		order.setOrderNo(__takeRecord.getOrdernumber());
		order.setOrderAmount(__takeRecord.getOrderamount().abs().setScale(2, BigDecimal.ROUND_DOWN).toString());
		order.setOrderTime(dateFormat.format(new Date()));
		order.setOrderSubject("Recharge");
		order.setBankName(__thirdpartyPaymentBank.getPaymenttypebankcode());
		order.setBankProvince("province");
		order.setBankCity("city");
		order.setBankAccountNo(__takeRecord.getEmployeepaymentaccount());
		order.setBankAccountName(__takeRecord.getEmployeepaymentname());
		order.setBankAccountType("personal");
		order.setBankCardType("debit");
		order.setBankTelephoneNo("159357258456");

		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + " - PayHelper - 银盛代付接口开始发起出款：" + __takeRecord.getOrdernumber());
		
		return payment.pay(merchant, order);
	}
	/**
	 * 星付代付
	 * @param __agument
	 * @param __takeRecord
	 * @param __thirdpartyPaymentBank
	 * @return
	 * @throws Exception
	 */
	private static String XFDFPay(Map<String, Object> __agument, TakeDepositRecord __takeRecord, ThirdpartyPaymentBank __thirdpartyPaymentBank) throws Exception{

		PayInterface<XingFDFMerchantConfig, XingFDFOrderConfig> payment = new XingFDFPayMent<XingFDFMerchantConfig, XingFDFOrderConfig>();
		// 商户参数
		XingFDFMerchantConfig merchant = BeanToMapUtil.convertMap(__agument, XingFDFMerchantConfig.class);

		// 出款订单参数
		XingFDFOrderConfig order = new XingFDFOrderConfig();
		// 代付订单号
		order.setOrderNo(__takeRecord.getOrdernumber());
		order.setOrderAmount(__takeRecord.getOrderamount().abs().setScale(2, BigDecimal.ROUND_DOWN).toString());
		order.setOrderDate(new SimpleDateFormat("yyyyMMdd").format(new Date()));
		
		order.setBankId(__thirdpartyPaymentBank.getPaymenttypebankcode());
		order.setBankCardNo(__takeRecord.getEmployeepaymentaccount());
		order.setBankCardName(__takeRecord.getEmployeepaymentname());
		order.setBankName(__thirdpartyPaymentBank.getBankname());
		
		order.setExtra("自动出款");
		order.setOrderSummary("DF");
		
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + " - PayHelper - 星付代付接口开始发起出款：" + __takeRecord.getOrdernumber());

		return payment.pay(merchant, order);
	}
	/**
	 * 旺付通代付
	 * @param __agument
	 * @param __takeRecord
	 * @param __thirdpartyPaymentBank
	 * @return
	 * @throws Exception
	 */
	private static String WFTDFPay(Map<String, Object> __agument, TakeDepositRecord __takeRecord, ThirdpartyPaymentBank __thirdpartyPaymentBank) throws Exception{

		PayInterface<WFTDFMerchantConfig, WFTDFOrderConfig> payment = new WFTDFPayMent<WFTDFMerchantConfig, WFTDFOrderConfig>();
		// 商户参数
		WFTDFMerchantConfig merchant = BeanToMapUtil.convertMap(__agument, WFTDFMerchantConfig.class);

		// 出款订单参数
		WFTDFOrderConfig order = new WFTDFOrderConfig();
		// 代付订单号
		order.setOrderId(__takeRecord.getOrdernumber());
		order.setOrderAmount(__takeRecord.getOrderamount().abs().setScale(2, BigDecimal.ROUND_DOWN).toString());
		order.setOrderTime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		
		order.setUserName(__takeRecord.getEmployeepaymentname());
		order.setCardNum(__takeRecord.getEmployeepaymentaccount());
		order.setBankCode(__thirdpartyPaymentBank.getPaymenttypebankcode());
		order.setBankProvince("BankProvince");
		order.setBankCity("BankCity");
		order.setBankBranch(__thirdpartyPaymentBank.getBankname());
		
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + " - PayHelper - 旺付通代付接口开始发起出款：" + __takeRecord.getOrdernumber());
		
		return payment.pay(merchant, order);
	}
	
	/**
	 * 信付通代付
	 * @param __agument
	 * @param __takeRecord
	 * @param __thirdpartyPaymentBank
	 * @return
	 * @throws Exception
	 */
	private static String XFTDFPay(Map<String, Object> __agument, TakeDepositRecord __takeRecord, ThirdpartyPaymentBank __thirdpartyPaymentBank) throws Exception{

		PayInterface<XFTDFMerchantConfig, XFTDFOrderConfig> payment = new XFTDFPayMent<XFTDFMerchantConfig, XFTDFOrderConfig>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyMMdd");
		// 商户参数
		XFTDFMerchantConfig merchant = BeanToMapUtil.convertMap(__agument, XFTDFMerchantConfig.class);

		// 出款订单参数
		XFTDFOrderConfig order = new XFTDFOrderConfig();
		// 代付订单号
		order.setOrderNo(__takeRecord.getOrdernumber());
		order.setOrderAmount(__takeRecord.getOrderamount().abs().setScale(2, BigDecimal.ROUND_DOWN).toString());
		order.setOrderDate(dateFormat.format(new Date()));
		order.setBankCard(__takeRecord.getEmployeepaymentaccount());
		order.setUserName(__takeRecord.getEmployeepaymentname());
		order.setBankName(__thirdpartyPaymentBank.getPaymenttypebankcode());
		order.setBranchName(__thirdpartyPaymentBank.getBankname());
		order.setProvince("province");
		order.setCity("city");

		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + " - PayHelper - 信付通代付接口开始发起出款：" + __takeRecord.getOrdernumber());
		
		return payment.pay(merchant, order);
	}
	/**
	 * 金塔聚合代付接口
	 * @param __agument
	 * @param __takeRecord
	 * @param __thirdpartyPaymentBank
	 * @return
	 * @throws Exception
	 */
	private static String JH2DFPay(Map<String, Object> __agument, TakeDepositRecord __takeRecord, ThirdpartyPaymentBank __thirdpartyPaymentBank) throws Exception{

		PayInterface<JH2DFMerchantConfig, JH2DFOrderConfig> payment = new JH2DFPayMent<JH2DFMerchantConfig, JH2DFOrderConfig>();
		// 商户参数
		JH2DFMerchantConfig merchant = BeanToMapUtil.convertMap(__agument, JH2DFMerchantConfig.class);

		// 出款订单参数
		JH2DFOrderConfig order = new JH2DFOrderConfig();
		// 代付订单号
		order.setOrderNo(__takeRecord.getOrdernumber());
		order.setOrderPrice(__takeRecord.getOrderamount().abs().setScale(2, BigDecimal.ROUND_DOWN).toString());
		order.setReceiverName(__takeRecord.getEmployeepaymentname());
		order.setReceiverAccountNo(__takeRecord.getEmployeepaymentaccount());
		order.setBankCode(__thirdpartyPaymentBank.getPaymenttypebankcode());
		order.setBankName(__thirdpartyPaymentBank.getBankname());
		order.setBankClearNo("ClearNo");
		order.setBankBranchNo("branchNo");
		order.setBankBranchName("branchName");
		order.setProvince("province");
		order.setCity("city");
		order.setPhoneNo("13588888888");
		order.setCertType("IDENTITY");
		order.setCertNo("123456789012345678");

		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + " - PayHelper - 金塔聚合代付接口开始发起出款：" + __takeRecord.getOrdernumber());
		
		return payment.pay(merchant, order);
	}
}