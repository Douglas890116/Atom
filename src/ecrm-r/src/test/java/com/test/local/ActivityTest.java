package com.test.local;

import java.math.BigDecimal;

import com.maven.util.Encrypt;

import junit.framework.TestCase;

public class ActivityTest extends TestCase{

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
}
