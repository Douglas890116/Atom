package com.maven.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class BankUtils {
	
	/**********银行类型************/
	public static Map<String, String> __BankType = new HashMap<String, String>(){{
		this.put("B001", "上海银行");
		this.put("B002", "北京银行");
		this.put("B003", "交通银行");
		this.put("B004", "中国银行");
		this.put("B005", "中国农业银行");
		this.put("B006", "中国工商银行");
		this.put("B007", "兴业银行");
		this.put("B008", "中国光大银行");
		this.put("B009", "广发银行");
		this.put("B010", "平安银行");
		this.put("B011", "华夏银行");
		this.put("B012", "浦发银行");
		this.put("B013", "中信银行");
		this.put("B015", "中国建设银行");
		this.put("B016", "招商银行");
		this.put("B018", "中国民生银行");
		this.put("B019", "微信支付");
		this.put("B020", "支付宝");
		this.put("B021", "中国邮政储蓄银行");
		this.put("B022", "北京农商银行");
		this.put("B023", "上海农商银行");
		this.put("B024", "南京银行");
		this.put("B025", "东亚银行");
		this.put("B026", "宁波银行");
		this.put("B027", "深圳发展银行");
		this.put("B028", "财付通");
		this.put("B029", "微信WAP");
		this.put("B030", "渤海银行");
		this.put("B031", "浙江稠州商业银行");
		this.put("B032", "H5收银台");
		this.put("B033", "PC收银台");
		this.put("B034", "QQ钱包");
	}};
	
	/**
	 * 获取银行名称
	 * @param employeepaymentbank
	 * @return
	 */
	public static String getBankName(String employeepaymentbank) {
		if(StringUtils.isBlank(employeepaymentbank)) return "";
		if(employeepaymentbank.equals("B001"))return "上海银行";
		if(employeepaymentbank.equals("B002"))return "北京银行";
		if(employeepaymentbank.equals("B003"))return "交通银行";
		if(employeepaymentbank.equals("B004"))return "中国银行";
		if(employeepaymentbank.equals("B005"))return "中国农业银行";
		if(employeepaymentbank.equals("B006"))return "中国工商银行";
		if(employeepaymentbank.equals("B007"))return "兴业银行";
		if(employeepaymentbank.equals("B008"))return "中国光大银行";
		if(employeepaymentbank.equals("B009"))return "广发银行";
		if(employeepaymentbank.equals("B010"))return "平安银行";
		if(employeepaymentbank.equals("B011"))return "华夏银行";
		if(employeepaymentbank.equals("B012"))return "浦发银行";
		if(employeepaymentbank.equals("B013"))return "中信银行";
		if(employeepaymentbank.equals("B015"))return "中国建设银行";
		if(employeepaymentbank.equals("B016"))return "招商银行";
		if(employeepaymentbank.equals("B018"))return "中国民生银行";
		if(employeepaymentbank.equals("B019"))return "微信";
		if(employeepaymentbank.equals("B020"))return "支付宝";
		if(employeepaymentbank.equals("B021"))return "中国邮政储蓄银行";
		if(employeepaymentbank.equals("B022"))return "北京农商银行";
		if(employeepaymentbank.equals("B023"))return "上海农商银行";
		if(employeepaymentbank.equals("B024"))return "南京银行";
		if(employeepaymentbank.equals("B025"))return "东亚银行";
		if(employeepaymentbank.equals("B026"))return "宁波银行";
		if(employeepaymentbank.equals("B027"))return "深圳发展银行";
		if(employeepaymentbank.equals("B028"))return "财付通";
		if(employeepaymentbank.equals("B029"))return "微信WAP";
		if(employeepaymentbank.equals("B030"))return "渤海银行";
		if(employeepaymentbank.equals("B031"))return "浙江稠州商业银行";
		if(employeepaymentbank.equals("B032"))return "H5收银台";
		if(employeepaymentbank.equals("B033"))return "PC收银台";
		if(employeepaymentbank.equals("B034"))return "QQ钱包";
		return "";
	}
	
	
}