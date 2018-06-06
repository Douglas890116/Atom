package com.test.local;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.hy.pull.common.util.api.AVGameAPI;
import com.maven.cache.SystemCache;
import com.maven.util.Encrypt;
import com.maven.util.RandomString;

import junit.framework.TestCase;

public class ActivityTest extends TestCase{

	public static void main(String[] args) {
		AVGameAPI gameAPI = new AVGameAPI();
		
		Map<String, Object> entity = new HashMap<String, Object>();
		String userID = RandomString.createRandomString(15);
		System.out.println(userID);
		
		entity.put("userID", "8WjJ1CZXnSSkTPf");
		entity.put("operatorID", "haoying");
		entity.put("operatorPassword", "e2db61f0210aa179c909ea3ccbc7cee4");
		entity.put("3rdparty", "NT0005");
		
		entity.put("amount", "0.1");
		
//		2e4f9fd926f5fdc3ed1559ea168f0cc1
//		http://gspotslots.bbtech.asia/UIS/connect?token=2e4f9fd926f5fdc3ed1559ea168f0cc1&amp;config=tokyo_ch&amp;gameconfig=mysisters&amp;room=10002
//		http://gspotslots.bbtech.asia/UIS/connect?token=691eb71a20371d4f8f05fec8298c704e&amp;config=tokyo_ch&amp;gameconfig=slotgames_lines25_yakinbyouto_slots&amp;room=10002
//		http://gspotslots.bbtech.asia/UIS/connect?token=691eb71a20371d4f8f05fec8298c704e&config=tokyo_ch&gameconfig=miracle_jq_ayu&room=10002
		
		
		entity.put("transactionID", RandomString.createRandomString(20));
//		System.out.println(gameAPI.createAccount(entity));
//		System.out.println(gameAPI.deposit(entity));
//		System.out.println(gameAPI.withdraw(entity));
//		System.out.println(gameAPI.getBalance(entity));
//		System.out.println(gameAPI.getGameListRNG(entity));
//		System.out.println(gameAPI.getSeesion(entity));
//		System.out.println(gameAPI.login(entity));
		SystemCache.getInstance().getActivityRunFlag("dddddddddd");
		System.out.println("x");
		
	}
	public void testSignInRaffle(){
		try {
			new BigDecimal(0);
			String url = "http://192.168.1.207:8080/ecrm-api/MemBerActivity/trigger?enterprisecode=EN0000";
			String params = "employeecode=E000IF5F&enterprisebrandactivitycode=2&loginip=195.2.214.3";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("活动分发:"+url);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	public void testUrl(){
		String url_for_get = "https://www.yompay.com/Payapi/?INPUT_CHARSET=UTF-8&RETURN_URL=&NOTIFY_URL=http://api.hyzonghe.net/TPayment/YomPayCallback&BANK_CODE=ABC&MER_NO=10982&ORDER_NO=B0E93C49C7B44487810D66DF3FBBB9A5&ORDER_AMOUNT=100&PRODUCT_NAME=A&PRODUCT_NUM=1&REFERER=http://api.hyzonghe.net&CUSTOMER_IP=api.hyzonghe.net&CUSTOMER_PHONE=13631223000&RECEIVE_ADDRESS=B&RETURN_PARAMS=1&SIGN=92d2f2bdd6a19dda7373f6798cf7d956";
		String url_for_post = url_for_get.substring(0,url_for_get.indexOf("?"));
		String url_params = url_for_get.substring(url_for_get.indexOf("?")+1);
		String[] url_params_array = url_params.split("&");
		System.out.println("url_for_post:"+url_for_post);
		System.out.println("url_params:"+url_params);
		for (String s : url_params_array) {
			String[] param = s.split("=");
    		if(param.length==2){
    			System.out.println("name:"+param[0]+"  value:"+param[1]);
    		}else{
    			System.out.println("name:"+param[0]+"  value:");
    		}
		}
	}
}
