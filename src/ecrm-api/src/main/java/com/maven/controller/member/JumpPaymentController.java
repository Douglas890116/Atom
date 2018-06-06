package com.maven.controller.member;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hy.pull.common.util.game.qp.QPUtil;
import com.maven.constant.Constant;
import com.maven.constant.Enum_MSG;
import com.maven.controller.BaseController;
import com.maven.entity.Enterprise;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeCapitalAccount;
import com.maven.entity.EnterpriseEmployeeInformation;
import com.maven.entity.EnterpriseEmployeeInformation.Enum_status;
import com.maven.entity.EnterpriseEmployeeLevel;
import com.maven.entity.EnterpriseEmployeeType.Type;
import com.maven.entity.EnterpriseInformation;
import com.maven.entity.EnterpriseThirdpartyPayment;
import com.maven.entity.EnterpriseInformation.Enum_enable;
import com.maven.entity.PaymentType.Enum_PayType;
import com.maven.entity.TakeDepositRecord;
import com.maven.entity.TakeDepositRecord.Enum_orderstatus;
import com.maven.entity.TakeDepositRecord.Enum_ordertype;
import com.maven.entity.ThirdpartyPaymentType.Enum_ThirdpartyPaymentType;
import com.maven.exception.ArgumentValidationException;
import com.maven.exception.GameAPIRequestException;
import com.maven.exception.LogicTransactionException;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.game.APIServiceNew;
import com.maven.game.OrderNewUtil;
import com.maven.interceptor.RepeatRequestIntercept;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.payment.PayInterface;
import com.maven.payment.lfwx.LfwxMerchantConfig;
import com.maven.payment.lfwx.LfwxOrderConfig;
import com.maven.payment.lfwx.LfwxPayMent;
import com.maven.payment.xbei.XBAppConstants;
import com.maven.service.BankService;
import com.maven.service.BusinessEmployeeLovelService;
import com.maven.service.EnterpriseEmployeeCapitalAccountService;
import com.maven.service.EnterpriseEmployeeInformationService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseInformationService;
import com.maven.service.TakeDepositRecoredService;
import com.maven.util.AttrCheckout;
import com.maven.util.BeanToMapUtil;
import com.maven.util.Encrypt;
import com.maven.util.RandomString;
import com.maven.utils.AESUtil2Net;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/jump")
public class JumpPaymentController extends BaseController{

	private static LoggerManager log = LoggerManager.getLogger(JumpPaymentController.class.getName(), OutputManager.LOG_USER_FUNDS);
	
	
	/**
	 * 乐付微信存款
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/lfwxPay",produces="text/html;charset=UTF-8")
	public String lfwxPay(HttpServletRequest request,Model model){
		try {
			Map<String,Object> object = super.getRequestParamters(request);
			System.out.println(object);
			String apiKey = "rGKpfj0RsSzaSnl0";//固定秘钥
			String iv = "6bG0cht1zu3J2qp9";//固定偏移量
			/*
			String data = request.getParameter("content").trim().replaceAll("\\r\n", "");
			data = data.replaceAll(" ", "");
//			data = AESUtil2Net.decryptAES(data, apiKey, iv);
			data = QPUtil.Decrypt(data, apiKey);
			System.out.println("解密后data="+data);
			JSONObject jsonObject = JSONObject.fromObject(data);
			
			String ordernumber = jsonObject.getString("out_trade_no").toString();
			String orderamount = jsonObject.getString("amount_str").toString();
			String traceip = jsonObject.getString("tran_ip").toString();
			String returnurl = jsonObject.getString("return_url").toString();
			*/
			
			String ordernumber = request.getParameter("out_trade_no").toString();
			String orderamount = request.getParameter("amount_str").toString();
			String traceip = request.getParameter("tran_ip").toString();
			String returnurl = request.getParameter("return_url").toString();

			PayInterface<LfwxMerchantConfig,LfwxOrderConfig> __yyePay = new LfwxPayMent<LfwxMerchantConfig, LfwxOrderConfig>();
			LfwxMerchantConfig merchant = new LfwxMerchantConfig();
			merchant.setMerNo("100322");//商户号
			merchant.setMerKey("MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAP0GDDcK55QCA9rEl0KEexJQQtbMDqoZPfSLa4MUTQ6n+ap6xzxZNczVQhSMVZg0tZIGfKayCogLz0nsl7XFJpd+DpjNewDnC42bcMZj637GyORsMAyIKt8wqt7HkHXMz3s8bZeYsMve6hf7m4YxlxUmFkDUp5ChwLSOCunzh5EbAgMBAAECgYBZp6jwYAbKpSQkgoBua28IgDQO1GNl1mfjnPtNiQX86XxH8hVixuGPYQl3Knqx4gtsYKwOjQu6RSUANrtTBzayxvRZL5MJo05rTGlvJ0w9Exs2N2cOgXK9iTN/+b858Ck9f2bOKD+Hg5qVGgjSKRk+/JPlcSIdcrqbMdHoCybgMQJBAP+TDR7bCBrLDo1FlE4Fous2kcPZXYG2zYJUdcMSK8WNUmjtkM43oqDF04vmRMWl5PFdiA8QuVIIw0ujM6AyV00CQQD9cei5zQCv8Qzs6YTvHz7tRePFZ+0hB5TvCYQDPtD4HteduLeJ4D0Hhw6Q73AvvEWjezUcjTjBJ7oXKCCKmOYHAkEA6E0QQviR0FC7RFt3JsfmwudR7PN5E7tF5u3AMHQmxyTiQC+XTGmzb3EBDQtbfU+B3oXGcvMfj1oZsXmBJl47jQJBAMQhDlQajNma1MHRxIm3yF6dozH0xtC0qVCCMKLCTbx1Qa5Qb9hGq3PT1DXc1RhbvhhRFDzQHId9UsjwyJ34zKkCQCqWUFqVqgFFexy87242ajCHaz7MwZO0shLfc4hF0dAbN+uEHTadgIStgh3zI3XHeQCd/zF8gOQtEtcInqnV7qs=");
			merchant.setMerPubKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQD9Bgw3CueUAgPaxJdChHsSUELWzA6qGT30i2uDFE0Op/mqesc8WTXM1UIUjFWYNLWSBnymsgqIC89J7Je1xSaXfg6YzXsA5wuNm3DGY+t+xsjkbDAMiCrfMKrex5B1zM97PG2XmLDL3uoX+5uGMZcVJhZA1KeQocC0jgrp84eRGwIDAQAB");
			merchant.setPubKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDMcRww57ecNBtZ1Wu1clzkNgNl97MGqUQ8ECVLys5WX30bfeRBpaJCX2x9Ia8EqI99QTDJjrwvnpOIFNo3qkNaL2L73PYo++bFUDVLpyUGK6R5eT7Z2gt8DY904Vni5ZRFeS7kn9a+dmHUcX9t2rXCpfrks5YQ6B91hcH4vX6eLwIDAQAB");
			merchant.setPayUrl("http://service.lepayle.com/api/gateway");
			merchant.setReturn_url(returnurl);
			
			LfwxOrderConfig  __yeeorder =  new LfwxOrderConfig();
			__yeeorder.setOrdernumber(ordernumber);
			__yeeorder.setPaymoney(Double.valueOf(orderamount));
			__yeeorder.setTransip(traceip);
			String url = __yyePay.save(merchant, __yeeorder);
			
//			return Enum_MSG.成功.message(url);//
			model.addAttribute("url", url);
			return "/payment/jump";
		}catch(ArgumentValidationException e){
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(e.getMessage());
		}catch(LogicTransactionRollBackException e){
			return Enum_MSG.系统错误.message(e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(e.getMessage());
		}
	}
	
	
	/**  
     * 使用java正则表达式去掉多余的.与0  
     * @param s  
     * @return   
     */    
    public static String subZeroAndDot(String s){    
        if(s.indexOf(".") > 0){    
            s = s.replaceAll("0+?$", "");//去掉多余的0    
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉    
        }    
        return s;
    }
	/**
	 * 判断是否为整数。如果为0，也返回true
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){  
	    Pattern pattern = Pattern.compile("[0-9]*");  
	    return pattern.matcher(str).matches();
	}  
	public static void main(String[] args) {
		BigDecimal ordermount = new BigDecimal(String.valueOf("39400.01"));
//		System.out.println(ordermount.intValue());
//		//判断是否为整数
//		if( !isNumeric(String.valueOf("39400.00"))) {
//			System.out.println(Enum_MSG.逻辑事物异常.message("xxxxxxxxxxxxxxxx"));;
//		}
//		//判断是否为10的倍数整数
//		System.out.println(ordermount.intValue());
//		if( ordermount.intValue() % 10 != 0 ) {//个位数非0时返回
//			System.out.println(Enum_MSG.逻辑事物异常.message(Enum_MSG.只能按整数取款.desc));
//		}
//		System.out.println("完成");
		
		String result = Enum_MSG.成功.message(ordermount.setScale(2, RoundingMode.HALF_UP).toString());
		
		JSONObject jsonObject = JSONObject.fromObject(result);
		double balance = 0;
		if(jsonObject.getString("code").equals("1")) {
			balance = jsonObject.getDouble("info");
		} else {
			
		}
		System.out.println(jsonObject);
		System.out.println(balance);
		
	}
	
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}
}
