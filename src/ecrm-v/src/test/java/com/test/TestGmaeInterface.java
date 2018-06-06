package com.test;

import com.maven.game.HYEncrypt;
import com.maven.game.HttpUtils;

import junit.framework.TestCase;

public class TestGmaeInterface extends TestCase {
	
	public static void main(String[] args) {
		
		//平台商
		String platform = "5f448b277e804abda1d583e82b936555";
		//游戏类型
		String type = "AGGame";
		//key
		String deskey = "test0003";
		String md5key = "test3333";
		//url
//		String url = "http://127.0.0.1:8080/service/web";
		String url = "http://www.77777f.com/service/web";
		//方法
		String method = "createUser";//createUser,balance,deposit，withdraw,play
		//用户类型
		String usertype = "agent"; //agent,user
		
		String username = "aassaaaa";
		String password = "aaaaaass";
		String usercode = "E0000000";
		String parentcode = "E0000000";
		
		
		//测试案例
		String param = "username="+username+"!password="+password+"!method="+method+"!usercode="+usercode+"!parentcode="+parentcode+"!usertype="+usertype;  //createUser
//		String param = "username="+username+"!password="+password+"!method="+method;  //balance
//		String param = "username="+username+"!password="+password+"!money=222222!ordernum="+Encrypt.UUID()+"!method="+method;//deposit,withdraw
//		String param = "username="+username+"!password="+password+"!method="+method; //play
		
		
		System.out.println(param);
		String params = HYEncrypt.encrypt(param, deskey);
		String key = HYEncrypt.string2MD5(param + md5key);
		String sendurl = url+"?params=" +params +"&key=" + key +"&platform="+platform+"&type="+type;
		if(method.equals("info")){
			sendurl = sendurl.replace("web", "info");
		}
		System.out.println(sendurl);
		try {
			String res = HttpUtils.get(sendurl);
			System.out.println(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		method=createUser!username=byaaabbb!password=b5yaej!usercode=byaaabbb!parentcode=byaaabbb!usertype=agent
//		INFO :[2015-10-10 19:00:40]com.tans.core.game.api.HQ_ALL_GameAPI.createPlayer(HQ_ALL_GameAPI.java:42) : http://www.77777f.com/service/web?params=O2bkdBJoQDWQmkj3NfKpw3kKLbuyzTyHagInNywLw9wdqd5P2DBOGDgZau9ChgIIMfPMP+KY5PwaOzmg7LAJSx8tTVxNeW+H1eVwtVeJGCVtFnYjpW7uCV3n14FXozfze7ccbK2gdcu5PXhPwWeLEQ==&key=96969d50d23f464d36ca1bad7020d0d2&platform=
//		INFO :[2015-10-10 19:00:41]com.tans.core.game.api.HQ_ALL_GameAPI.createPlayer(HQ_ALL_GameAPI.java:44) : 
		
	}
}
