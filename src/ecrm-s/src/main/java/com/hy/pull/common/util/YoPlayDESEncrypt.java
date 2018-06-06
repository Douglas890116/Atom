package com.hy.pull.common.util;

import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import com.hy.pull.common.util.game.sa.DeEnCode;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class YoPlayDESEncrypt { 
 
 
    private static byte[] desEncrypt(String deskey, byte[] plainText) throws Exception { 
        SecureRandom sr = new SecureRandom(); 
        DESKeySpec dks = new DESKeySpec(deskey.getBytes()); 
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES"); 
        SecretKey key = keyFactory.generateSecret(dks); 
        Cipher cipher = Cipher.getInstance("DES"); 
        cipher.init(Cipher.ENCRYPT_MODE, key, sr); 
        byte data[] = plainText; 
        byte encryptedData[] = cipher.doFinal(data); 
        return encryptedData; 
    } 
 
    private static byte[] desDecrypt(String deskey, byte[] encryptText) throws Exception { 
        SecureRandom sr = new SecureRandom(); 
        DESKeySpec dks = new DESKeySpec(deskey.getBytes()); 
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES"); 
        SecretKey key = keyFactory.generateSecret(dks); 
        Cipher cipher = Cipher.getInstance("DES"); 
        cipher.init(Cipher.DECRYPT_MODE, key, sr); 
        byte encryptedData[] = encryptText; 
        byte decryptedData[] = cipher.doFinal(encryptedData); 
        return decryptedData; 
    } 
    //
    public static String string2MD5(String param,String agentpwd, String MD5_KEY ) throws Exception { 
    	//cid=aaa&loginname=bbb&password=ccccddddddeeeeee&action=lg&acctype=f&agent=ggg
    	String[] params = param.split("&");
    	StringBuffer str = new StringBuffer();
    	for (String string : params) {
    		str.append(string.split("=")[1]);
		}
//    	System.out.println("MD5加密原文："+str.toString().concat(agentpwd).concat(MD5_KEY));
        return DeEnCode.string2MD5(str.toString().concat(agentpwd).concat(MD5_KEY));
    } 
    //登录API专用
    public static String string2MD5Login(String param,String MD5_KEY ) throws Exception { 
    	//cid=aaa&loginname=bbb&password=ccccddddddeeeeee&action=lg&acctype=f&agent=ggg
//    	String[] params = param.split("&");
//    	StringBuffer str = new StringBuffer();
//    	for (String string : params) {
//    		str.append(string.split("=")[1]);
//		}
//    	System.out.println("MD5加密原文："+str.toString().concat(MD5_KEY));
        return DeEnCode.string2MD5(param.concat(MD5_KEY));
    } 
    
    public static String encrypt(String deskey, String input) throws Exception { 
        return base64Encode(desEncrypt(deskey, input.getBytes())).replaceAll("\\s*", ""); 
    } 
 
    public static String decrypt(String deskey, String input) throws Exception { 
        byte[] result = base64Decode(input); 
        return new String(desDecrypt(deskey, result)); 
    } 
 
    private static String base64Encode(byte[] s) { 
        if (s == null) 
            return null; 
        BASE64Encoder b = new sun.misc.BASE64Encoder(); 
        return b.encode(s); 
    } 
 
    private static byte[] base64Decode(String s) throws IOException { 
        if (s == null) { 
            return null; 
        } 
        BASE64Decoder decoder = new BASE64Decoder(); 
        byte[] b = decoder.decodeBuffer(s); 
        return b; 
    } 
 
    public static void main(String args[]) { 
        try { 
             
            String p=encrypt("abcdefgh", "cagent=XXXXXX/\\\\/loginname=ptest98/\\\\/method=ice"); 
            System.out.println("密文:"+p); 
 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
    }
    
 
}
