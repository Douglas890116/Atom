package com.test.local;

import com.maven.util.Encrypt;
import com.maven.util.HttpPostUtil;

public class Thread1 extends Thread{  
    private String name;  
    public Thread1(String name) {  
       this.name=name;  
    }  
    public void run() {  
        for (int i = 1; i <= 10; i++) {  
            
            try {  

            	String result = HttpPostUtil.doGetSubmit(playIBC());
				System.out.println("沙巴结果："+result);
				
//				Thread.sleep(30000l);
				
				result = HttpPostUtil.doGetSubmit(playNHQ());
				System.out.println("HY结果："+result);
				
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
         
    }
    
    
    public static String playIBC(){
		try {
			String url = "http://api.hyzonghe.net/Game/play?enterprisecode=EN003K";
			String params = "employeecode=E000IXE6&gametype=eIBCGame&brandcode=EB0000BD&playtype=TY";
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
	
    public static void main(String[] args) {
        
        for (int i = 0; i < 10; i++) {
        	Thread1 mTh2=new Thread1("B"+i);  
        	mTh2.start(); 
		}
	}
}  
