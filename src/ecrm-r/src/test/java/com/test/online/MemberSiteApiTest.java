package com.test.online;

import com.maven.payment.yee.utils.DigestUtil;
import com.maven.util.Encrypt;

import junit.framework.TestCase;

public class MemberSiteApiTest  extends TestCase {
	
	public static final  String URL = "http://api.hyzonghe.net/";
	
	public static final String AES_KEY = "VUhEndOteNSQDAAK";
	
	public static final String MD5_KEY = "Yb0caH3KBSv0j1sq";
	
	public static final String ENTERPRISECODE = "EN0000";
	
	public static final String BRANDCODE = "EB0000AV";
	
	public static final String EMPLOYEECODE="E00000C3";
	
	
	public  void regesterAgent(){
		try {
			String url = URL+"/Agent/Register?enterprisecode="+ENTERPRISECODE;
			String params = "loginaccount=yijdaili6&loginpassword=111222333&domain=002y.redhat.com";
			String aesparams= Encrypt.AESEncrypt(params, AES_KEY);
			url += "&signature="+Encrypt.MD5(params+MD5_KEY)+"&params="+aesparams;
			System.out.println("注册代理:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void regesterMember(){
		try {
			String url = URL+"/User/register?enterprisecode="+ENTERPRISECODE;
			String params = "brandcode="+BRANDCODE+"&loginaccount=testuser4&loginpassword=111222333&fundpassword=88888&displayalias=秦始皇&domain=http://www.google.com";
			String aesparams= Encrypt.AESEncrypt(params, AES_KEY);
			url += "&signature="+Encrypt.MD5(params+MD5_KEY)+"&params="+aesparams;
			System.out.println("注册会员:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void login(){
		try {
			String url = URL+"/User/login?enterprisecode="+ENTERPRISECODE;
			String params = "loginaccount=testuser&loginpassword=123456789&loginip=169.36.59.48&browserversion=firefox/45.0&opratesystem=Windows 10";
			String aesparams= Encrypt.AESEncrypt(params, AES_KEY);
			url += "&signature="+Encrypt.MD5(params+MD5_KEY)+"&params="+aesparams;
			System.out.println("用户登陆:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	
	public  void getHYAccout(){
		try {
			String url = URL+"/Game/getLoginInfo?";
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
			String url = URL+"/Game/PtLogin?enterprisecode="+ENTERPRISECODE;
			String params = "employeecode=E000IF1J";
			String aesparams= Encrypt.AESEncrypt(params, AES_KEY);
			url += "&signature="+Encrypt.MD5(params+MD5_KEY)+"&params="+aesparams;
			System.out.println("PT登陆:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void isRunGame(){
		try {
			String url = "http://192.168.1.207:9090/ecrm/Game/gamestatus?enterprisecode="+ENTERPRISECODE;
			String params = "gameType=PTGame&brandcode=EB00001L";
			String aesparams= Encrypt.AESEncrypt(params, AES_KEY);
			url += "&signature="+Encrypt.MD5(params+MD5_KEY)+"&params="+aesparams;
			System.out.println("游戏状态:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public  void play(){
		try {
			String url = URL+"/Game/play?enterprisecode="+ENTERPRISECODE;
			String params = "employeecode=E000IF6Z&gametype=ZJGame&brandcode=EB000027";
			String aesparams= Encrypt.AESEncrypt(params, "FSmrFkJOrPzFRdO5");
			url += "&signature="+Encrypt.MD5(params+"aST5TdfmVokT35iJ")+"&params="+aesparams;
			System.out.println("进入游戏:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void phonePlay(){
		try {
			String url = URL+"/Game/GWakeUp?enterprisecode="+ENTERPRISECODE;
			String params = "employeecode=E0000000&gametype=AVGame&brandcode="+BRANDCODE+"&application=h5";
			String aesparams = Encrypt.AESEncrypt(params, AES_KEY);
			url += "&signature="+Encrypt.MD5(params+MD5_KEY)+"&params="+aesparams;
			System.out.println("唤起登陆:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void tryPlay(){
		try {
			String url = URL+"/Game/tryPlay?enterprisecode=EN003B";
			String params = "gametype=ZJGame";
			String aesparams= Encrypt.AESEncrypt(params, "FSmrFkJOrPzFRdO5");
			url += "&signature="+Encrypt.MD5(params+"aST5TdfmVokT35iJ")+"&params="+aesparams;
			System.out.println("进入游戏:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public  void balances(){
		try {
			String url = URL+"/Game/balances?enterprisecode="+ENTERPRISECODE;
			String params = "employeecode=E00000C3";
			String aesparams= Encrypt.AESEncrypt(params, AES_KEY);
			url += "&signature="+Encrypt.MD5(params+MD5_KEY)+"&params="+aesparams;
			System.out.println("所有游戏余额:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void balance(){
		try {
			String url = URL+"/Game/balance?enterprisecode="+ENTERPRISECODE;
			String params = "employeecode=E00000C3&gameType=AGGame";
			String aesparams= Encrypt.AESEncrypt(params, AES_KEY);
			url += "&signature="+Encrypt.MD5(params+MD5_KEY)+"&params="+aesparams;
			System.out.println("单个游戏余额:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void gameOrders(){
		try {
			String url = URL+"/GRecords/Records?enterprisecode="+ENTERPRISECODE;
			String params = "gametype=NHQGame&employeecode=E0000000&start=0&limit=10";
			String aesparams= Encrypt.AESEncrypt(params, AES_KEY);
			url += "&signature="+Encrypt.MD5(params+MD5_KEY)+"&params="+aesparams;
			System.out.println("游戏记录:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void takeEmployee(){
		try {
			String url = URL+"/User/takeEmployee?enterprisecode="+ENTERPRISECODE;
			String params = "employeecode=E00000C3";
			String aesparams= Encrypt.AESEncrypt(params, AES_KEY);
			url += "&signature="+Encrypt.MD5(params+MD5_KEY)+"&params="+aesparams;
			System.out.println("通过编号获取用户信息:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void updateLoginPassword(){
		try {
			String url = URL+"/User/updatepwd?enterprisecode="+ENTERPRISECODE;
			String params = "employeecode=E00000C3&oldloginpassword=111222333&newloginpassword=111222333";
			String aesparams= Encrypt.AESEncrypt(params, AES_KEY);
			url += "&signature="+Encrypt.MD5(params+MD5_KEY)+"&params="+aesparams;
			System.out.println("修改登陆密码:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void updateFunPassword(){
		try {
			String url = URL+"/User/updatefpwd?enterprisecode="+ENTERPRISECODE;
			String params = "employeecode=E00000C3&oldfundpassword=88888&newfundpassword=88888";
			String aesparams= Encrypt.AESEncrypt(params, AES_KEY);
			url += "&signature="+Encrypt.MD5(params+MD5_KEY)+"&params="+aesparams;
			System.out.println("修改资金密码:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void addBankCard(){
		try {
			String url = URL+"/User/AddUBankCard?enterprisecode="+ENTERPRISECODE;
			String params = "employeecode=E00000C3&paymentaccount=5646546546464646&accountname=王石&openningbank=龙山支行&bankcode=B004&fundpassword=88888";
			String aesparams= Encrypt.AESEncrypt(params, AES_KEY);
			url += "&signature="+Encrypt.MD5(params+MD5_KEY)+"&params="+aesparams;
			System.out.println("添加银行卡:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void editBankCard(){
		try {
			String url = URL+"/User/EditUBankCard?enterprisecode="+ENTERPRISECODE;
			String params = "employeecode=E00000C3&informationcode=EEI0001I&fundpassword=88888&paymentaccount=2222222222222222222&accountname=王岐山&openningbank=萨拉支行&qq=123456546&skype=21faaf&email=aaaa@son.com";
			String aesparams= Encrypt.AESEncrypt(params, AES_KEY);
			url += "&signature="+Encrypt.MD5(params+MD5_KEY)+"&params="+aesparams;
			System.out.println("添加银行卡:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void delBankCard(){
		try {
			String url = URL+"/User/DeleteUBankCard?enterprisecode="+ENTERPRISECODE;
			String params = "employeecode=E00000C3&informationcode=EEI0001I&fundpassword=888888";
			String aesparams= Encrypt.AESEncrypt(params, AES_KEY);
			url += "&signature="+Encrypt.MD5(params+MD5_KEY)+"&params="+aesparams;
			System.out.println("删除银行卡:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void sysMessage(){
		try {
			String url = URL+"/UserMessage/SysMessage?enterprisecode="+ENTERPRISECODE;
			String params = "employeecode=E00000C3";
			String aesparams= Encrypt.AESEncrypt(params, AES_KEY);
			url += "&signature="+Encrypt.MD5(params+MD5_KEY)+"&params="+aesparams;
			System.out.println("系统消息:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void agentMessage(){
		try {
			String url = URL+"/UserMessage/AgentMessage?enterprisecode="+ENTERPRISECODE;
			String params = "employeecode=E00000C3";
			String aesparams= Encrypt.AESEncrypt(params, AES_KEY);
			url += "&signature="+Encrypt.MD5(params+MD5_KEY)+"&params="+aesparams;
			System.out.println("用户消息:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void messageCount(){
		try {
			String url = URL+"/UserMessage/MessageCount?enterprisecode="+ENTERPRISECODE;
			String params = "employeecode=E00000C3";
			String aesparams= Encrypt.AESEncrypt(params, AES_KEY);
			url += "&signature="+Encrypt.MD5(params+MD5_KEY)+"&params="+aesparams;
			System.out.println("未读消息:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void employeeAllBankCard(){
		try {
			String url = URL+"/User/UBankCards?enterprisecode="+ENTERPRISECODE;
			String params = "employeecode=E00000C3";
			String aesparams= Encrypt.AESEncrypt(params, AES_KEY);
			url += "&signature="+Encrypt.MD5(params+MD5_KEY)+"&params="+aesparams;
			System.out.println("读取用户所有银行卡:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void employeeSingleBankCard(){
		try {
			String url = URL+"/User/UBankCards?enterprisecode="+ENTERPRISECODE;
			String params = "employeecode=E00000C3&informationcode=EEI0001I";
			String aesparams= Encrypt.AESEncrypt(params, AES_KEY);
			url += "&signature="+Encrypt.MD5(params+MD5_KEY)+"&params="+aesparams;
			System.out.println("读取用户单张银行卡:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void eThirdpartys(){
		try {
			String url = URL+"/TPayment/EThirdpartys?enterprisecode="+ENTERPRISECODE;
			String params = "enterprisecode="+ENTERPRISECODE;
			String aesparams= Encrypt.AESEncrypt(params, AES_KEY);
			url += "&signature="+Encrypt.MD5(params+MD5_KEY)+"&params="+aesparams;
			System.out.println("读取企业第三方支付:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void enterpriseBankCard(){
		try {
			String url = URL+"/Funds/EBankCards?enterprisecode="+ENTERPRISECODE;
			String params = "enterprisecode="+ENTERPRISECODE;
			String aesparams= Encrypt.AESEncrypt(params, AES_KEY);
			url += "&signature="+Encrypt.MD5(params+MD5_KEY)+"&params="+aesparams;
			System.out.println("读取企业收款银行卡:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void saving(){
		try {
			String url = URL+"/Funds/Saving?enterprisecode="+ENTERPRISECODE;
			String params = "brandcode="+BRANDCODE+"&employeecode=E00000C3&orderamount=200&enterpriseinformationcode=EI00001K&employeepaymentbank=B006&employeepaymentaccount=555555555555555555&employeepaymentname=马云&traceip=195.125.12.62&ordercomment=test";
			String aesparams= Encrypt.AESEncrypt(params, AES_KEY);
			url += "&signature="+Encrypt.MD5(params+MD5_KEY)+"&params="+aesparams;
			System.out.println("用户存款:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void ESaving(){
		try {
			String url = URL+"/TPayment/ESaving?enterprisecode="+ENTERPRISECODE;
			String params = "brandcode="+BRANDCODE+"&employeecode=E00000C3&orderamount=200&enterprisethirdpartycode=EP00000C&traceip=192.168.1.21";
			String aesparams= Encrypt.AESEncrypt(params, AES_KEY);
			url += "&signature="+Encrypt.MD5(params+MD5_KEY)+"&params="+aesparams;
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
	
	public  void saveOrders(){
		try {
			String url = URL+"/Fetch/SaveOrder?enterprisecode="+ENTERPRISECODE;
			String params = "brandcode="+BRANDCODE+"&employeecode=E000IF1J&orderstatus=2&start=0&limit=10";
			String aesparams= Encrypt.AESEncrypt(params, AES_KEY);
			url += "&signature="+Encrypt.MD5(params+MD5_KEY)+"&params="+aesparams;
			System.out.println("用户存款记录:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void takeing(){
		try {
			String url = URL+"/Funds/Taking?enterprisecode="+ENTERPRISECODE;
			String params = "brandcode="+BRANDCODE+"&employeecode=E00000C3&orderamount=20&ordercomment=xfyladmin&informationcode=EEI0001I&traceip=195.36.69.78&fundpassword=88888";
			String aesparams= Encrypt.AESEncrypt(params, AES_KEY);
			url += "&signature="+Encrypt.MD5(params+MD5_KEY)+"&params="+aesparams;
			System.out.println("用户取款:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void takeOrders(){
		try {
			String url = URL+"/Fetch/TakeOrder?enterprisecode="+ENTERPRISECODE;
			String params = "brandcode="+BRANDCODE+"&employeecode=E000IF1I&orderstatus=2&start=0&limit=10";
			String aesparams= Encrypt.AESEncrypt(params, AES_KEY);
			url += "&signature="+Encrypt.MD5(params+MD5_KEY)+"&params="+aesparams;
			System.out.println("用户取款记录:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void takeAllDomainConfig(){
		try {
			String url = URL+"/Domain/TakeAllDomainConfig?enterprisecode="+ENTERPRISECODE;
			String params = "enterprisecode="+ENTERPRISECODE;
			String aesparams= Encrypt.AESEncrypt(params, AES_KEY);
			url += "&signature="+Encrypt.MD5(params+MD5_KEY)+"&params="+aesparams;
			System.out.println("获取所有域名配置信息:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void takeDomainConfig(){
		try {
			String url = URL+"/Domain/TakeDomainConfig?enterprisecode="+ENTERPRISECODE;
			String params = "domain=www.baidu.com";
			String aesparams= Encrypt.AESEncrypt(params, AES_KEY);
			url += "&signature="+Encrypt.MD5(params+MD5_KEY)+"&params="+aesparams;
			System.out.println("获取单个域名配置信息:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void enterpriseInfo(){
		try {
			String url = URL+"/Domain/enterpriseInfo?";
			String params = "enterprisecode="+ENTERPRISECODE;
			String aesparams= Encrypt.AESEncrypt(params, "i9Q30JYMf06NmBCJ");
			url += "signature="+Encrypt.MD5(params+"XjvTLI6p0mOQK051")+"&params="+aesparams;
			System.out.println("获取企业KEY:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public  void takeNotic(){
		try {
			String url = URL+"/Notic/Notic?enterprisecode="+ENTERPRISECODE;
			String params = "enterprisecode=EN0000&brandcode="+BRANDCODE+"&start=0&limit=3";
			String aesparams= Encrypt.AESEncrypt(params, AES_KEY);
			url += "&signature="+Encrypt.MD5(params+MD5_KEY)+"&params="+aesparams;
			System.out.println("获取公告:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void eRegisterSave(){
		try {
			String url = URL+"/Statistics/ERegisterSave?enterprisecode="+ENTERPRISECODE;
			String params = "enterprisecode="+ENTERPRISECODE;
			String aesparams= Encrypt.AESEncrypt(params, AES_KEY);
			url += "&signature="+Encrypt.MD5(params+MD5_KEY)+"&params="+aesparams;
			System.out.println("统计企业注册总数与充值总数:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void contact(){
		try {
			String url = URL+"/Domain/Contact?enterprisecode="+ENTERPRISECODE;
			String params = "brandcode=EB00001L";
			String aesparams= Encrypt.AESEncrypt(params, AES_KEY);
			url += "&signature="+Encrypt.MD5(params+MD5_KEY)+"&params="+aesparams;
			System.out.println("品牌联系方式:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public  void eBGame(){
		try {
			String url = URL+"/EnterpriseBrand/EBrandGame?enterprisecode=EN0030";
			String params = "brandcode="+BRANDCODE;
			String aesparams= Encrypt.AESEncrypt(params, "1IMBHOq1sl3SxJIm");
			url += "&signature="+Encrypt.MD5(params+"6qe2lNoQC0c8kJ24")+"&params="+aesparams;
			System.out.println("企业游戏:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
