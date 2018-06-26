package com.test.local;

import com.maven.util.Encrypt;

import junit.framework.TestCase;

public class AgentSiteControllerTest extends TestCase {
	
	public void testAgentDomain() {
		try {
			String url = "http://192.168.1.207:8080/ecrm-api/Agent/AgentDomain?domain=003f.m.redhat.com";
			System.out.println("代理站点模板信息:"+url);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	public void testRegister() {
		try {
			String url = "http://192.168.1.207:8080/ecrm-api/Agent/Register?enterprisecode=EN0000";
			String params = "loginaccount=yijdaili7&loginpassword=111222333&domain=002y.redhat.com";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("注册代理:"+url);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	public void testFoundAgentSite() {
		try {
			String url = "http://192.168.1.207:8080/ecrm-api/Agent/FoundAgentSite?enterprisecode=EN0000";
			String params = "employeecode=E00000BN&agentsitedomain=002y.redhat.com&domainlink=0040.redhat.com&dividend=20&phone=13595489563&share=30&qq=51565645646&skype=sdfa.slj&vchat=dsj1265&NHQGame_SX=0&TAGGame_SX=0&TAGGame_DZ=0&OGGame_SX=0&PTGame_SX=0&PTGame_DZ=0";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("注册代理站点:"+url);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	public void testFoundUserSite() {
		try {
			String url = "http://192.168.1.207:8080/ecrm-api/Agent/FoundUserSite?enterprisecode=EN0000";
			String params = "brandcode=EB00001L&employeecode=E00000BN&domainlink=0042.redhat1.com&NHQGame_SX=0&TAGGame_SX=0&TAGGame_DZ=0&OGGame_SX=0&PTGame_SX=0&PTGame_DZ=0";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("注册会员站点:"+url);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	public void testAgentInfo() {
		try {
			String url = "http://192.168.1.207:8080/ecrm-api/Agent/AgentInfo?enterprisecode=EN0000";
			String params = "employeecode=E00000BN";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("代理信息:"+url);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	public void testAgentSiteInfo() {
		try {
			String url = "http://192.168.1.207:8080/ecrm-api/Agent/AgentSiteInfo?enterprisecode=EN0000";
			String params = "employeecode=E00000BN";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("代理站点信息:"+url);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	public void testMemberSiteInfo() {
		try {
			String url = "http://192.168.1.207:8080/ecrm-api/Agent/MemberSiteInfo?enterprisecode=EN0000";
			String params = "employeecode=E00000BN";
			String aesparams= Encrypt.AESEncrypt(params, "VUhEndOteNSQDAAK");
			url += "&signature="+Encrypt.MD5(params+"Yb0caH3KBSv0j1sq")+"&params="+aesparams;
			System.out.println("会员站点信息:"+url);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
