package com.test;

import com.maven.util.Encrypt;

@SuppressWarnings("unused")
public class GameApiTest {
	
	private static void regester(){
		try {
			String url = "http://192.168.1.207:8080/ecrm/User/register?enterprisecode=EN0000";
			String params = "brandcode=EB00001L&loginaccount=testuser4&loginpassword=111222333&fundpassword=88888&displayalias=秦始皇&domain=http://www.google.com";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("注册用户:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void login(){
		try {
			String url = "http://192.168.1.207:8080/ecrm/User/login?enterprisecode=EN0000";
			String params = "loginaccount=testuser&loginpassword=123456789&loginip=169.36.59.48&browserversion=firefox/45.0&opratesystem=Windows 10";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("用户登陆:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	
	private static void hyPhoneLogin(){
		try {
			String url = "http://192.168.1.207:8080/ecrm/Game/getLoginInfo?";
			String params = "user=bssnow&password=123456";
			String aesparams = Encrypt.AESEncrypt(params, "jnql6H9dX1PaLZGX");
			url += "signature="+Encrypt.MD5(params+"6mQegshJJKMH5j7P")+"&params="+aesparams;
			System.out.println("手机登陆:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void gWakeUp(){
		try {
			String url = "http://192.168.1.207:8080/ecrm/Game/GWakeUp?enterprisecode=EN0000";
			String params = "employeecode=E0000000&gametype=NHQGame&brandcode=EB00001L&application=iso";
			String aesparams = Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("唤起登陆:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void ptLogin(){
		try {
			String url = "http://192.168.1.207:8080/ecrm/Game/PtLogin?enterprisecode=EN0000";
			String params = "employeecode=E000IF1J";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("PT登陆:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void gamestatus(){
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

	private static void play(){
		try {
			String url = "http://192.168.1.207:8080/ecrm/Game/play?enterprisecode=EN0000";
			String params = "employeecode=E000IF1J&gameType=XCPGame&brandcode=EB00001L";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("进入游戏:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void tryPlay(){
		try {
			String url = "http://192.168.1.207:9090/ecrm/Game/tryPlay?enterprisecode=EN0000";
			String params = "gametype=AGGame";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("进入游戏:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private static void balances(){
		try {
			String url = "http://192.168.1.207:8080/ecrm/Game/balances?enterprisecode=EN0000";
			String params = "employeecode=E00000C3";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("所有游戏余额:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void balance(){
		try {
			String url = "http://192.168.1.207:8080/ecrm/Game/balance?enterprisecode=EN0000";
			String params = "employeecode=E00000C3&gameType=AGGame";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("单个游戏余额:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void gameOrders(){
		try {
			String url = "http://192.168.1.207:8080/ecrm/GRecords/Records?enterprisecode=EN0000";
			String params = "gametype=NHQGame&employeecode=E0000000&start=0&limit=10";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("游戏记录:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void takeEmployee(){
		try {
			String url = "http://192.168.1.207:8080/ecrm/User/takeEmployee?enterprisecode=EN0000";
			String params = "employeecode=E00000C3";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("通过编号获取用户信息:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void updateLoginPassword(){
		try {
			String url = "http://192.168.1.207:8080/ecrm/User/updatepwd?enterprisecode=EN0000";
			String params = "employeecode=E00000C3&oldloginpassword=111222333&newloginpassword=111222333";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("修改登陆密码:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void updateFunPassword(){
		try {
			String url = "http://192.168.1.207:8080/ecrm/User/updatefpwd?enterprisecode=EN0000";
			String params = "employeecode=E00000C3&oldfundpassword=88888&newfundpassword=88888";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("修改资金密码:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void addBankCard(){
		try {
			String url = "http://192.168.1.207:8080/ecrm/User/AddUBankCard?enterprisecode=EN0000";
			String params = "employeecode=E00000C3&paymentaccount=5646546546464646&accountname=王石&openningbank=龙山支行&bankcode=B004&fundpassword=88888";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("添加银行卡:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void editBankCard(){
		try {
			String url = "http://192.168.1.207:8080/ecrm/User/EditUBankCard?enterprisecode=EN0000";
			String params = "employeecode=E00000C3&informationcode=EEI0001I&fundpassword=88888&paymentaccount=2222222222222222222&accountname=王岐山&openningbank=萨拉支行&qq=123456546&skype=21faaf&email=aaaa@son.com";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("添加银行卡:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void delBankCard(){
		try {
			String url = "http://192.168.1.207:8080/ecrm/User/DeleteUBankCard?enterprisecode=EN0000";
			String params = "employeecode=E00000C3&informationcode=EEI0001I&fundpassword=888888";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("删除银行卡:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void sysMessage(){
		try {
			String url = "http://192.168.1.207:8080/ecrm/UserMessage/SysMessage?enterprisecode=EN0000";
			String params = "employeecode=E00000C3";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("系统消息:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void agentMessage(){
		try {
			String url = "http://192.168.1.207:8080/ecrm/UserMessage/AgentMessage?enterprisecode=EN0000";
			String params = "employeecode=E00000C3";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("用户消息:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void messageCount(){
		try {
			String url = "http://192.168.1.207:8080/ecrm/UserMessage/MessageCount?enterprisecode=EN0000";
			String params = "employeecode=E00000C3";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("未读消息:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void employeeAllBankCard(){
		try {
			String url = "http://192.168.1.207:8080/ecrm/User/UBankCards?enterprisecode=EN0000";
			String params = "employeecode=E00000C3";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("读取用户所有银行卡:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void employeeSingleBankCard(){
		try {
			String url = "http://192.168.1.207:8080/ecrm/User/UBankCards?enterprisecode=EN0000";
			String params = "employeecode=E00000C3&informationcode=EEI0001I";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("读取用户单张银行卡:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void eThirdpartys(){
		try {
			String url = "http://192.168.1.207:8080/ecrm/TPayment/EThirdpartys?enterprisecode=EN0000";
			String params = "enterprisecode=EN0000";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("读取企业第三方支付:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void enterpriseBankCard(){
		try {
			String url = "http://192.168.1.207:8080/ecrm/Funds/EBankCards?enterprisecode=EN0000";
			String params = "enterprisecode=EN0000";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("读取企业收款银行卡:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void saving(){
		try {
			String url = "http://192.168.1.207:8080/ecrm/Funds/Saving?enterprisecode=EN0000";
			String params = "brandcode=EB00001L&employeecode=E00000C3&orderamount=200&enterpriseinformationcode=EI00001K&employeepaymentbank=B006&employeepaymentaccount=555555555555555555&employeepaymentname=马云&traceip=195.125.12.62&ordercomment=test";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("用户存款:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void ESaving(){
		try {
			String url = "http://192.168.1.207:8080/ecrm/TPayment/ESaving?enterprisecode=EN0000";
			String params = "brandcode=EB00001L&employeecode=E00000C3&orderamount=200&enterprisethirdpartycode=EP000001&paymenttypebankcode=ABC&bankcode=B005&traceip=195.125.12.62";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("用户第三方即时存款:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void saveOrders(){
		try {
			String url = "http://192.168.1.207:8080/ecrm/Fetch/SaveOrder?enterprisecode=EN0000";
			String params = "brandcode=EB00001L&employeecode=E000IF1J&orderstatus=2&start=0&limit=10";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("用户存款记录:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void takeing(){
		try {
			String url = "http://192.168.1.207:8080/ecrm/Funds/Taking?enterprisecode=EN0000";
			String params = "brandcode=EB00001L&employeecode=E00000C3&orderamount=20&ordercomment=xfyladmin&informationcode=EEI0001I&traceip=195.36.69.78&fundpassword=88888";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("用户取款:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void takeOrders(){
		try {
			String url = "http://192.168.1.207:8080/ecrm/Fetch/TakeOrder?enterprisecode=EN0000";
			String params = "brandcode=EB00001L&employeecode=E000IF1I&orderstatus=2&start=0&limit=10";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("用户取款记录:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void takeAllDomainConfig(){
		try {
			String url = "http://192.168.1.207:8080/ecrm/Domain/TakeAllDomainConfig?enterprisecode=EN0000";
			String params = "enterprisecode=EN0000";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("获取所有域名配置信息:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void takeDomainConfig(){
		try {
			String url = "http://192.168.1.207:8080/ecrm/Domain/TakeDomainConfig?enterprisecode=EN0000";
			String params = "domain=www.baidu.com";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("获取单个域名配置信息:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void enterpriseInfo(){
		try {
			String url = "http://192.168.1.207:8080/ecrm/Domain/enterpriseInfo?";
			String params = "enterprisecode=EN0000";
			String aesparams= Encrypt.AESEncrypt(params, "i9Q30JYMf06NmBCJ");
			url += "signature="+Encrypt.MD5(params+"XjvTLI6p0mOQK051")+"&params="+aesparams;
			System.out.println("获取企业KEY:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private static void takeNotic(){
		try {
			String url = "http://192.168.1.207:8080/ecrm/Notic/Notic?enterprisecode=EN0000";
			String params = "enterprisecode=EN0000&brandcode=EB00001M&start=0&limit=3";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("获取公告:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void eRegisterSave(){
		try {
			String url = "http://192.168.1.207:8080/ecrm/Statistics/ERegisterSave?enterprisecode=EN0000";
			String params = "enterprisecode=EN0000";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("统计企业注册总数与充值总数:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void contact(){
		try {
			String url = "http://192.168.1.207:8080/ecrm/Domain/Contact?enterprisecode=EN0000";
			String params = "brandcode=EB00001L";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("品牌联系方式:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private static void eBGame(){
		try {
			String url = "http://192.168.1.207:8080/ecrm/EnterpriseBrand/EBrandGame?enterprisecode=EN0000";
			String params = "brandcode=EB00001L";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("企业游戏:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		gWakeUp();
	}
	
}
