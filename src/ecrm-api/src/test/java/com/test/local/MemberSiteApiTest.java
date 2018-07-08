package com.test.local;

import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.payment.mf.DigestUtil;
import com.maven.util.Encrypt;
import com.maven.util.HttpPostUtil;

import junit.framework.TestCase;

public class MemberSiteApiTest  extends TestCase {
	
	public static void main(String[] args) {
		MemberSiteApiTest apiTest = new MemberSiteApiTest();
//		apiTest.upIntegral();
//		apiTest.phonePlay();
//		apiTest.Redbag();
//		apiTest.createGameaccount();
//		apiTest.getVerifycode();
//		apiTest.regesterMember();
//		apiTest.balance();
//		apiTest.balances();
//		apiTest.takeEmployeeAccount();
//		apiTest.updateLoginPassword();
//		apiTest.login();
//		apiTest.enterpriseInfo();
//		apiTest.RecordsAll();
//		apiTest.updateSysMessage();
//		apiTest.MessageCount();
//		apiTest.updateInfo();
//		apiTest.allMoney();
//		apiTest.gameOrders();
		apiTest.takeEmployee();
//		apiTest.banner();
//		apiTest.bannerh5();
//		apiTest.BrandGameAll();
//		apiTest.play();
//		
		
		/*
		try {
			for (int i = 0; i < 500; i++) {
				
				String result = HttpPostUtil.doGetSubmit(playIDN());
				System.out.println(i+"=IDN结果："+result);
				
				result = HttpPostUtil.doGetSubmit(playGG());
				System.out.println(i+"=GG结果："+result);
				
				result = HttpPostUtil.doGetSubmit(playNHQ());
				System.out.println(i+"=HY结果："+result);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
//		System.out.println(playGG());;
		
//		apiTest.takeEmployee();
//		apiTest.balancesAll();
//		apiTest.upIntegralGame();
//		apiTest.playIDN2();
//		apiTest.playNHQ2();
//		apiTest.playGG2();
		
//		apiTest.findAccountChange();
//		apiTest.banner();
		
//		apiTest.saveOrders();
//		apiTest.takeOrders();
//		apiTest.takeEmployee();
//		apiTest.Redbag();
//		apiTest.test();
	}
	
	public  void saveOrders(){
		try {
			String url = "http://api.hyzonghe.net/Fetch/SaveOrder?enterprisecode=EN0030";
			String params = "brandcode=EB0000AV&employeecode=E000IFIK&orderstatus=2&start=0&limit=10&orderdate_begin=2017-05-30&orderdate_end=2017-05-30";
			String aesparams= Encrypt.AESEncrypt(params, "1IMBHOq1sl3SxJIm");	
			url += "&signature="+Encrypt.MD5(params+"6qe2lNoQC0c8kJ24")+"&params="+aesparams;
			System.out.println("用户存款记录:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public  void takeOrders(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/Fetch/TakeOrder?enterprisecode=EN0030";
			String params = "brandcode=EB0000AV&employeecode=E000IFIK&orderstatus=2&start=0&limit=10";
			String aesparams= Encrypt.AESEncrypt(params, "1IMBHOq1sl3SxJIm");
			url += "&signature="+Encrypt.MD5(params+"6qe2lNoQC0c8kJ24")+"&params="+aesparams;
			System.out.println("用户取款记录:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public  void test(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/User/test?enterprisecode=EN0000";
			String params = "employeecode=E00000C3&enterprisebrandactivitycode=22&loginip=192.168.1.1";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("签到领红包："+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public  void Redbag(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/MemBerActivity/trigger?enterprisecode=EN0000";
			String params = "employeecode=E00000C3&enterprisebrandactivitycode=22&loginip=192.168.1.1";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("签到领红包："+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public  void Redbag2(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/MemBerActivity/trigger?enterprisecode=EN0000";
			String params = "employeecode=E00000C3&enterprisebrandactivitycode=23&loginip=192.168.1.1";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("签到领红包："+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public  void findBrandActivity(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/BrandActivity/trigger?enterprisecode=EN0000";
			String params = "enterprisebrandcode=E000IF5F&way=List";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("获取企业品牌所有活动："+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public  void takeEmployeeLoginaccount(){
		try {
			String url = "http://api.hyzonghe.net/User/takeEmployeeLoginaccount?enterprisecode=EN003K";
			String params = "loginaccount=egg2230";	
			String aesparams= Encrypt.AESEncrypt(params, "GKQP3pbZqR07D1Fu");
			url += "&signature="+Encrypt.MD5(params+"p7BGzPOFM60lvdPg")+"&params="+aesparams;	
			System.out.println("所有游戏余额:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void balancesAll(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/Game/balancesAll?enterprisecode=EN003K";
			String params = "employeecode=E000IXE6&gametype=NHQGame&brandcode=EB0000BD&playtype=TY";
			String aesparams= Encrypt.AESEncrypt(params, "GKQP3pbZqR07D1Fu");
			url += "&signature="+Encrypt.MD5(params+"p7BGzPOFM60lvdPg")+"&params="+aesparams;
			System.out.println("所有游戏余额:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public  void upIntegralGame(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/Game/upIntegralGame?enterprisecode=EN003K";
			String params = "employeecode=E000IXE6&gametype=NHQGame&brandcode=EB0000BD&money=1";
			String aesparams= Encrypt.AESEncrypt(params, "GKQP3pbZqR07D1Fu");
			url += "&signature="+Encrypt.MD5(params+"p7BGzPOFM60lvdPg")+"&params="+aesparams;
			System.out.println("所有游戏余额:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String playIDN2(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/Game/play?enterprisecode=EN003K";
			String params = "employeecode=E000IXE6&gametype=IDNGame&brandcode=EB0000BD&playtype=DZ";
			String aesparams= Encrypt.AESEncrypt(params, "GKQP3pbZqR07D1Fu");
			url += "&signature="+Encrypt.MD5(params+"p7BGzPOFM60lvdPg")+"&params="+aesparams;	
			System.out.println("所有游戏余额:"+url);
			return url;
		} catch (Exception e) {
			e.printStackTrace();	
		}
		return "";
	}
	public String playNHQ2(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/Game/play?enterprisecode=EN003K";
			String params = "employeecode=E000IXE6&gametype=NHQGame&brandcode=EB0000BD&playtype=TY";
			String aesparams= Encrypt.AESEncrypt(params, "GKQP3pbZqR07D1Fu");
			url += "&signature="+Encrypt.MD5(params+"p7BGzPOFM60lvdPg")+"&params="+aesparams;
			System.out.println("所有游戏余额:"+url);
			return url;
		} catch (Exception e) {
			e.printStackTrace();	
		}
		return "";
	}
	public String playGG2(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/Game/play?enterprisecode=EN003K";
			String params = "employeecode=E000IXE6&gametype=GGGame&brandcode=EB0000BD&playtype=TY";
			String aesparams= Encrypt.AESEncrypt(params, "GKQP3pbZqR07D1Fu");
			url += "&signature="+Encrypt.MD5(params+"p7BGzPOFM60lvdPg")+"&params="+aesparams;	
			System.out.println("所有游戏余额:"+url);
			return url;
		} catch (Exception e) {
			e.printStackTrace();	
		}
		return "";
	}
	
	
	
	public  void balances(){
		try {
			String url = "http://api.hyzonghe.net/Game/balances?enterprisecode=EN003X";
			String params = "employeecode=E000IXC8";	
			String aesparams= Encrypt.AESEncrypt(params, "6Zc81eZlDT7Y1Yap");
			url += "&signature="+Encrypt.MD5(params+"1TpE5GlkMV6CABxZ")+"&params="+aesparams;
			System.out.println("所有游戏余额:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public  void takeEmployee(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/User/takeEmployee?enterprisecode=EN0000";
			String params = "employeecode=E00000C3&gametype=NHQGame&brandcode=EB00001L";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("通过编号获取用户信息:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static String playIDN(){
		try {
			String url = "http://api.hyzonghe.net/Game/play?enterprisecode=EN003K";
			String params = "employeecode=E000IXE6&gametype=IDNGame&brandcode=EB0000BD&playtype=DZ";
			String aesparams= Encrypt.AESEncrypt(params, "GKQP3pbZqR07D1Fu");
			url += "&signature="+Encrypt.MD5(params+"p7BGzPOFM60lvdPg")+"&params="+aesparams;	
			return url;
		} catch (Exception e) {
			e.printStackTrace();	
		}
		return "";
	}
	public static String playNHQ(){
		try {
			String url = "http://api.hyzonghe.net/Game/play?enterprisecode=EN003K";
			String params = "employeecode=E000IXE6&gametype=NHQGame&brandcode=EB0000BD&playtype=TY";
			String aesparams= Encrypt.AESEncrypt(params, "GKQP3pbZqR07D1Fu");
			url += "&signature="+Encrypt.MD5(params+"p7BGzPOFM60lvdPg")+"&params="+aesparams;	
			return url;
		} catch (Exception e) {
			e.printStackTrace();	
		}
		return "";
	}
	public static String playGG(){
		try {
			String url = "http://api.hyzonghe.net/Game/play?enterprisecode=EN003K";
			String params = "employeecode=E000IXE6&gametype=GGGame&brandcode=EB0000BD&playtype=TY";
			String aesparams= Encrypt.AESEncrypt(params, "GKQP3pbZqR07D1Fu");
			url += "&signature="+Encrypt.MD5(params+"p7BGzPOFM60lvdPg")+"&params="+aesparams;	
			return url;
		} catch (Exception e) {
			e.printStackTrace();	
		}
		return "";
	}
	public  void play(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/Game/play?enterprisecode=EN0000";
			String params = "employeecode=E00000C3&gametype=MGGame&brandcode=EB00001L";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;	
			System.out.println("进入游戏:"+url);
		} catch (Exception e) {
			e.printStackTrace();	
		}
	}
	
	
	public  void BrandGameAll(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/GRecords/BrandGameAll?enterprisecode=EN0000";
			String params = "brandcode=EB00001M";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("获取首页Banner:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public  void bannerh5(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/EnterpriseBrand/bannerh5?enterprisecode=EN0000";
			String params = "brandcode=EB00001M";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("获取首页Banner:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public  void banner(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/EnterpriseBrand/banner?enterprisecode=EN0000";
			String params = "brandcode=EB00001M&bannertype=H5";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("获取首页Banner:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public  void RecordsAll(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/GRecords/RecordsAll?enterprisecode=EN0000";
			String params = "employeecode=E000IF73&start=0&limit=2";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("会员存取款统计数据:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public  void findEmployeeLovel(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/User/findEmployeeLovel?enterprisecode=EN0000";
			String params = "enterprisecode=EN0000";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("会员存取款统计数据:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public  void allMoney(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/Game/allMoney?enterprisecode=EN0000";
			String params = "employeecode=E000IF73";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("会员存取款统计数据:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public  void updateInfo(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/User/updateInfo?enterprisecode=EN0000";
			String params = "employeecode=E000IF73&qq=2412456467&email=gfggdfkjfnkj@qq.com&phoneno=413241545&verifycode";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("更新会员信息："+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public  void findUserFavourable(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/User/findUserFavourable?enterprisecode=EN0000";
			String params = "employeecode=E000IF73";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("获取优惠组："+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void findAccountChange(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/User/findAccountChange?enterprisecode=EN0000";
			String params = "employeecode=E00000C3&start=0&limit=100&startDate=2017-01-23&endDate=2017-05-26";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("获取账变记录："+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public  void getVerifycode(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/User/getVerifycode?enterprisecode=EN0000";
			String params = "brandcode=EB00001M&phoneno=13631223451";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("获取验证码:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public  void createGameaccount(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/Game/createGameaccount?enterprisecode=EN0000";
			String params = "employeecode=E000IF73&gametype=QPGame&brandcode=EB00001M";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("注册会员:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public  void regesterAgent(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api-api/Agent/Register?enterprisecode=EN0000";
			String params = "loginaccount=yijdaili6&loginpassword=111222333&domain=002y.redhat.com";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("注册代理:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void upIntegral(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/Game/upIntegral?enterprisecode=EN0000";
			String params = "employeecode=E000IF73&gametype=PTGame&brandcode=EB00001M&application=h5";
			String aesparams = Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("唤起登陆:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public  void phonePlay(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/Game/GWakeUp?enterprisecode=EN0000";
			String params = "employeecode=E000IF73&gametype=PTGame&brandcode=EB00001M&application=h5";
			String aesparams = Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("手动上分："+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public  void takeEmployeeAccountQp(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/User/takeEmployeeAccountQp?enterprisecode=EN0000";
			String params = "employeecode=E000IF73";
			String aesparams = Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("唤起登陆:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public  void takeEmployeeAccount(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/User/takeEmployeeAccount?enterprisecode=EN0000";
			String params = "employeecode=E000IF73";
			String aesparams = Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("唤起登陆:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public  void takeEmployeeAccountOne(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/User/makeEmployeeAccountTest?enterprisecode=EN0000";
			String params = "employeecode=E000IF73&gametype=QPGame";
			String aesparams = Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("唤起登陆:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public  void regesterMember(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/User/register?enterprisecode=EN0000";
			String params = "brandcode=EB00001L&loginaccount=testuser9125&loginpassword=111222333&fundpassword=88888&displayalias=秦始皇&domain=http://www.google.com&verifycode=7827&phoneno=13631221234";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("注册会员:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void login(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/User/login?enterprisecode=EN0000";
			String params = "loginaccount=tonny9&loginpassword=123456&loginip=169.36.59.48&browserversion=firefox/45.0&opratesystem=Windows 10";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("用户登陆:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public  void tryPlay(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/Game/tryPlay?enterprisecode=EN003B";
			String params = "gametype=PTGame&playtype=SX";
			String aesparams= Encrypt.AESEncrypt(params, "FSmrFkJOrPzFRdO5");
			url += "&signature="+Encrypt.MD5(params+"aST5TdfmVokT35iJ")+"&params="+aesparams;
			System.out.println("进入游戏:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	public  void getHYAccout(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/Game/getLoginInfo?";
			String params = "user=bssnow&password=123456";
			String aesparams = Encrypt.AESEncrypt(params, "jnql6H9dX1PaLZGX");
			url += "signature="+Encrypt.MD5(params+"6mQegshJJKMH5j7P")+"&params="+aesparams;
			System.out.println("手机登陆:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void getPTAccout(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/Game/PtLogin?enterprisecode=EN0000";
			String params = "employeecode=E000IF1J";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("PT登陆:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void isRunGame(){
		try {
			String url = "http://192.168.1.207:9090/ecrm/Game/gamestatus?enterprisecode=EN0000";
			String params = "gameType=PTGame&brandcode=EB00001L";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("游戏状态:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	
	public  void balance(){
		try {
			String url = "http://127.0.0.1:8081/ecrm-api/Game/balance?enterprisecode=EN0000";
			String params = "employeecode=E000IF73&gameType=TTGGame";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("单个游戏余额:"+url);
			
			/*
			String url = "http://api.hyzonghe.net/Game/balance?enterprisecode=EN0032";
			String params = "employeecode=E000IFB4&gameType=OGGame";
			String aesparams= Encrypt.AESEncrypt(params, "VGARrDk97Ez1r5JU");
			url += "&signature="+Encrypt.MD5(params+"gw7z3DbEp00ocwXu")+"&params="+aesparams;
			System.out.println("单个游戏余额:"+url);
			System.out.println(HttpPostUtil.doGetSubmit(url));
			*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void gameOrders(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/GRecords/Records?enterprisecode=EN0000";
			String params = "gametype=EBETGame&employeecode=E000IV9Y&start=0&limit=1";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("游戏记录:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public  void updateLoginPassword(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/User/updatepwd?enterprisecode=EN0000";
			String params = "employeecode=E00000C3&oldloginpassword=1234567890123&newloginpassword=123456789012";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("修改登陆密码:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void updateFunPassword(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/User/updatefpwd?enterprisecode=EN0000";
			String params = "employeecode=E00000C3&oldfundpassword=88888&newfundpassword=88888";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("修改资金密码:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void addBankCard(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/User/AddUBankCard?enterprisecode=EN0000";
			String params = "employeecode=E00000C3&paymentaccount=564654654646412646&accountname=王石订单&openningbank=龙山支订单行&bankcode=B004&fundpassword=123456789";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("添加银行卡:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void editBankCard(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api-api/User/EditUBankCard?enterprisecode=EN0000";
			String params = "employeecode=E00000C3&informationcode=EEI0001I&fundpassword=123123&paymentaccount=1111111111111112&accountname=王岐山2&openningbank=萨拉支行&qq=123456546&skype=21faaf&email=aaaa@son.com";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("编辑银行卡:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void delBankCard(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/User/DeleteUBankCard?enterprisecode=EN0000";
			String params = "employeecode=E00000C3&informationcode=EEI0001I&fundpassword=888888";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("删除银行卡:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public  void MessageCount(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/UserMessage/MessageCount?enterprisecode=EN0000";
			String params = "employeecode=E00000C0";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("系统消息:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public  void sysMessage(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/UserMessage/SysMessage?enterprisecode=EN0000";
			String params = "employeecode=E00000C0&start=0&limit=20&field=readstatus&direcation=asc";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("系统消息:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public  void updateSysMessage(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/UserMessage/updateSysMessage?enterprisecode=EN0000";
			String params = "employeecode=E00000C0&messagecode=39";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("系统消息:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void agentMessage(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/UserMessage/AgentMessage?enterprisecode=EN0000";
			String params = "employeecode=E00000C3";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("用户消息:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void messageCount(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/UserMessage/MessageCount?enterprisecode=EN0000";
			String params = "employeecode=E00000C3";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("未读消息:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void employeeAllBankCard(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/User/UBankCards?enterprisecode=EN0000";
			String params = "employeecode=E00000C3";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("读取用户所有银行卡:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void employeeSingleBankCard(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/User/UBankCards?enterprisecode=EN0000";
			String params = "employeecode=E00000C3&informationcode=EEI0001I";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("读取用户单张银行卡:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void eThirdpartys222(){
		try {
			String url = "http://api.hyzonghe.net/TPayment/EThirdpartys?enterprisecode=EN003C";
			String params = "enterprisecode=EN003C";
			String aesparams= Encrypt.AESEncrypt(params, "il2mdYWlHaLh1Ji3");
			url += "&signature="+Encrypt.MD5(params+"kL0fJX9yyywQ2xgQ")+"&params="+aesparams;
			System.out.println("读取企业第三方支付:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public  void eThirdpartys(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/TPayment/EThirdpartys?enterprisecode=EN0000";
			String params = "enterprisecode=EN0000";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("读取企业第三方支付:"+url);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void enterpriseBankCard(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/Funds/EBankCards?enterprisecode=EN0000";
			String params = "enterprisecode=EN0000";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("读取企业收款银行卡:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void saving(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/Funds/Saving?enterprisecode=EN0000";
			String params = "brandcode=EB00001L&employeecode=E00000C3&orderamount=200&enterpriseinformationcode=EI00001K&employeepaymentbank=B006&employeepaymentaccount=555555555555555555&employeepaymentname=马云&traceip=195.125.12.62&ordercomment=test";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("用户存款:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void ESaving(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/TPayment/ESaving?enterprisecode=EN0000";
			String params = "brandcode=EB00001L&employeecode=E00000C3&orderamount=200&enterprisethirdpartycode=EP00000J&traceip=192.168.1.21&paymenttypebankcode=ABC";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("用户第三方即时存款:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testyeesign(){
		try {
			StringBuffer sValue = new StringBuffer();
			// 商户编号
			sValue.append("10001126856");
			// 业务类型
			sValue.append("Buy");
			// 支付结果
			sValue.append("1");
			// 易宝支付交易流水号
			sValue.append("2131458787aaa4478q5");
			// 支付金额
			sValue.append("200");
			// 交易币种
			sValue.append("RMB");
			// 商品名称
			sValue.append("A");
			// 商户订单号
			sValue.append("04A85CD7DDEC45359552DAAD8319AA0C");
			// 易宝支付会员ID
			sValue.append("12545778");
			// 商户扩展信息
			sValue.append("");
			// 交易结果返回类型
			sValue.append("1");
			String sNewString = null;
			sNewString = DigestUtil.hmacSign(sValue.toString(), "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl");
			System.out.println(sNewString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public  void takeing(){
		try {
			String url = "http://127.0.0.1:8081/ecrm-api/Funds/Taking?enterprisecode=EN0000";
			String params = "brandcode=EB00001L&employeecode=E00000C3&orderamount=20&ordercomment=xfyladmin&informationcode=EEI0001I&traceip=195.36.69.78&fundpassword=123456";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("用户取款:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public  void takeAllDomainConfig(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/Domain/TakeAllDomainConfig?enterprisecode=EN0000";
			String params = "enterprisecode=EN0000";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("获取所有域名配置信息:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void takeDomainConfig(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api-api/Domain/TakeDomainConfig?domain=www.baidu.com";
			System.out.println("获取单个域名配置信息:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void enterpriseInfo(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/Domain/enterpriseInfo?";
			String params = "enterprisecode=EN0000";
			String aesparams= Encrypt.AESEncrypt(params, "i9Q30JYMf06NmBCJ");
			url += "signature="+Encrypt.MD5(params+"XjvTLI6p0mOQK051")+"&params="+aesparams;
			System.out.println("获取企业KEY:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public  void takeNotic(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/Notic/Notic?enterprisecode=EN0000";
			String params = "enterprisecode=EN0000&brandcode=EB00001M&start=0&limit=3";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("获取公告:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void eRegisterSave(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/Statistics/ERegisterSave?enterprisecode=EN0000";
			String params = "enterprisecode=EN0000";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("统计企业注册总数与充值总数:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void contact(){
		try {
			String url = "http://192.168.1.187:8081/ecrm-api/Domain/Contact?enterprisecode=EN0000";
			String params = "brandcode=EB00001L";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("品牌联系方式:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public  void eBGame(){
		try {
			String url = "http://api.hyzonghe.net//EnterpriseBrand/EBrandGame?enterprisecode=EN0030";
			String params = "brandcode=EB0000AS";
			String aesparams= Encrypt.AESEncrypt(params, "1IMBHOq1sl3SxJIm");
			url += "&signature="+Encrypt.MD5(params+"6qe2lNoQC0c8kJ24")+"&params="+aesparams;
			System.out.println("企业游戏:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
