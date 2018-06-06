package com.hy.pull.common.util.game.qp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.hy.pull.common.util.api.Enum_MSG;

import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;

/**
 * AES加密解密 AES-128-ECB加密
 * 
 * @author temdy
 */
public class QPUtil {

	
	private static byte[] encrypt(String content, String password) {     
        try {                
                KeyGenerator kgen = KeyGenerator.getInstance("AES"); //KeyGenerator提供（对称）密钥生成器的功能。使用getInstance 类方法构造密钥生成器。  
           kgen.init(128, new SecureRandom(password.getBytes()));//使用用户提供的随机源初始化此密钥生成器，使其具有确定的密钥大小。  
           SecretKey secretKey = kgen.generateKey();     
                byte[] enCodeFormat = secretKey.getEncoded();     
                SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");//使用SecretKeySpec类来根据一个字节数组构造一个 SecretKey,，而无须通过一个（基于 provider 的）SecretKeyFactory.  
                Cipher cipher = Cipher.getInstance("AES");// 创建密码器   //为创建 Cipher 对象，应用程序调用 Cipher 的 getInstance 方法并将所请求转换 的名称传递给它。还可以指定提供者的名称（可选）。   
           byte[] byteContent = content.getBytes("utf-8");     
                cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化     
           byte[] result = cipher.doFinal(byteContent); //按单部分操作加密或解密数据，或者结束一个多部分操作。数据将被加密或解密（具体取决于此 Cipher 的初始化方式）。     
           System.out.println("加密后："+result);  
                return result; // 加密     
        } catch (NoSuchAlgorithmException e) {     
                e.printStackTrace();     
        } catch (NoSuchPaddingException e) {     
                e.printStackTrace();     
        } catch (InvalidKeyException e) {     
                e.printStackTrace();     
        } catch (UnsupportedEncodingException e) {     
                e.printStackTrace();     
        } catch (IllegalBlockSizeException e) {     
                e.printStackTrace();     
        } catch (BadPaddingException e) {     
                e.printStackTrace();     
        }     
        return null;     
    }    
	// 加密
    public static String Encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
 
        return new Base64().encodeToString(encrypted).trim().replaceAll("\\r\n", "");//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }
 
    // 解密
    public static String Decrypt(String sSrc, String sKey) throws Exception {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = new Base64().decode(sSrc);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original,"utf-8");
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }


	public static void main(String[] args) {
		// String filePath = "e:/ssl/www.zhzw.gov.cn.cer";
		//
		// String idEncrypt = AESUtil.encrypt(filePath); //加密数据
		// System.out.println(idEncrypt);
		// String idDecrypt = AESUtil.decrypt(idEncrypt);//解密数据
		// System.out.println(idDecrypt);
		
		try {
			JSONObject params = new JSONObject();
			params.put("Account", "54897rihd8732yhdsd");
			params.put("LoginPass", "temdydsdxsdsad");
			System.out.println(params.toString());
			String data = Encrypt(params.toString(), "ASDFGHJQWE123456");
//			System.out.println(data.trim());
			System.out.println(Decrypt("0bBSeBYPZ2ZHFVW/xyfYrlmtdNsfseuV4KUtvNNwQ+2EDIYsY6fFzzzsfSdTPXUy5Pw2MGXR7rwk9MasCe5fxw+0LB2bX40e5DCIi0ZlPRBYnHelznPodP9twpjMrGCCCZjzr6Ou8e5hENJ9Ddw04OLCJF3qPNIlIunZykFP6OV7lop7Zrp6uxwN351Aqhr9q+ASQm/y9cEZIyDcGi8uZQ==", "rGKpfj0RsSzaSnl0"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
