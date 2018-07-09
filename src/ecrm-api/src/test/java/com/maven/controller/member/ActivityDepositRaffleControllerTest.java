package com.maven.controller.member;

import java.util.HashMap;
import java.util.Map;

import com.maven.util.Encrypt;

import junit.framework.TestCase;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ActivityDepositRaffleControllerTest extends TestCase {

	public void testDepositBonus() {
		
		try {
			String url = "http://api.hyzonghe.net/MemBerActivity/trigger";
		
			String params = "employeecode=E000IFQS&enterprisebrandactivitycode=9&loginip=127.0.0.1";
			String aesparams= Encrypt.AESEncodeEncrypt(params, "PC6XhDze1JjuSFa1");
			String signature = Encrypt.MD5(params+"J10VXueBlpXg2MPs");
				
			Map<String, String> paramsx = new HashMap<String, String>();
			paramsx.put("enterprisecode", "EN0039");
			paramsx.put("signature", signature);
			paramsx.put("params", aesparams);
		
			System.out.println("url:"+url);
			System.out.println(com.maven.util.HttpPostUtil.doPostSubmitMap(url, paramsx));
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}

	public void testRaffleData() {
		try {
			String url = "http://127.0.0.1:9090/ecrm-api/ActivityData/trigger";
		
			String params = "employeecode=E00000C3&enterprisebrandactivitycode=8";
			String aesparams= Encrypt.AESEncodeEncrypt(params, "VUhEndOteNSQDAAK");
			String signature = Encrypt.MD5(params+"Yb0caH3KBSv0j1sq");
			
			Map<String, String> paramsx = new HashMap<String, String>();
			paramsx.put("enterprisecode", "EN0000");
			paramsx.put("signature", signature);
			paramsx.put("params", aesparams);
		
			System.out.println("url:"+url);
			System.out.println(HttpPostUtil.doPostSubmitMap(url, paramsx));
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	
	public void testssss() {
		try {
			String url = "http://127.0.0.1:9090/ecrm-api/ActivityData/benefitRecord";
		
			String params = "employeecode=E00000C3&pageIndex=3&limit=20";
			String aesparams= Encrypt.AESEncodeEncrypt(params, "VUhEndOteNSQDAAK");
			String signature = Encrypt.MD5(params+"Yb0caH3KBSv0j1sq");
			
			Map<String, String> paramsx = new HashMap<String, String>();
			paramsx.put("enterprisecode", "EN0000");
			paramsx.put("signature", signature);
			paramsx.put("params", aesparams);
		
			System.out.println("url:"+url);
			JSONObject object = JSONObject.fromObject(HttpPostUtil.doPostSubmitMap(url, paramsx));
			JSONArray array = object.getJSONArray("info");
			System.out.println(array.size());
			System.out.println(array.get(0));
			System.out.println(object);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		ActivityDepositRaffleControllerTest test = new ActivityDepositRaffleControllerTest();
		test.testssss();
	}
}
