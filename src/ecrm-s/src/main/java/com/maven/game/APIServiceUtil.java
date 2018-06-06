package com.maven.game;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.net.util.Base64;

import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.UserMoneyInAndOut;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
public class APIServiceUtil {

	//存储员工编码对应的最后一次上分记录的ganmetype
	public static Map<String, UserMoneyInAndOut> mapLastLoginGame = new HashMap<String, UserMoneyInAndOut>();
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private final static String DES = "DES";
    private final static String ENCODE = "GBK";
    private final static String defaultKey = "12345678";

    public static void main(String[] args) throws Exception {
        String data = "123456789";
        String key = "E000IF73";
        System.out.println(encrypt(data));
        //ltACiHjVjIkotGo4p6Jwkg==
        //2q8+rb7fpbwXJV6RCvTEZg==
        
        // System.err.println(encrypt(data, key));25f9e794323b453885f5181f1b624d0b(String), 2q8+rb7fpbwXJV6RCvTEZg==(String), E000IF73(String)
        // System.err.println(decrypt(encrypt(data, key), key));
//        System.out.println(encrypt(data,key)+"="+encrypt(data,key).length());
//        System.out.println(decrypt(encrypt(data,key) ,key));awong23
//        jmt1668
//        redmission
        System.out.println(decrypt("LaqLaVSIndGOoLBXbv9oKw==", "E000IY6U"));

    }

    /**
     * 使用 默认key 加密
     * 
     * @return String
     * @author lifq
     * @date 2015-3-17 下午02:46:43
     */
    @Deprecated
    public static String encrypt(String data) throws Exception {
        byte[] bt = encrypt(data.getBytes(ENCODE), defaultKey.getBytes(ENCODE));
        String strs = new BASE64Encoder().encode(bt);
        return strs;
    }

    /**
     * 使用 默认key 解密
     * 
     * @return String
     * @author lifq
     * @date 2015-3-17 下午02:49:52
     */
    @Deprecated
    public static String decrypt(String data) throws IOException, Exception {
        if (data == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] buf = decoder.decodeBuffer(data);
        byte[] bt = decrypt(buf, defaultKey.getBytes(ENCODE));
        return new String(bt, ENCODE);
    }
    
    /**
     * 使用 默认key 加密
     * 
     * @return String
     * @author lifq
     * @date 2015-3-17 下午02:46:43
     */
    public static String encrypt(String data,String key) throws Exception {
        byte[] bt = encrypt(data.getBytes(ENCODE), key.getBytes(ENCODE));
        String strs = new BASE64Encoder().encode(bt);
        return strs;
    }

    /**
     * 使用 默认key 解密
     * 
     * @return String
     * @author lifq
     * @date 2015-3-17 下午02:49:52
     */
    public static String decrypt(String data,String key) throws IOException, Exception {
        if (data == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] buf = decoder.decodeBuffer(data);
        byte[] bt = decrypt(buf, key.getBytes(ENCODE));
        return new String(bt, ENCODE);
    }
    

    /**
     * Description 根据键值进行加密
     * 
     * @param data
     * @param key
     *            加密键byte数组  key不能小于8位数
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, EnterpriseEmployee object) throws Exception {
       try {
    	   
    	   byte[] bt = encrypt(data.getBytes(ENCODE), object.getParentemployeecode().getBytes(ENCODE));
           String strs = new BASE64Encoder().encode(bt);
           return strs;
		} catch (Exception e) {
			System.out.println("#####################加密数据时异常：data="+data+" employeecode="+object.getEmployeecode()+" parentemployeecode="+object.getParentemployeecode()+" loginaccount="+object.getLoginaccount());
			e.printStackTrace();
		}
       
       return null;
    }

    /**
     * Description 根据键值进行解密
     * 
     * @param data
     * @param key
     *            加密键byte数组 key不能小于8位数
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String decrypt(String data, EnterpriseEmployee object) throws IOException,
            Exception {
        if (data == null) {
            return null;
        }
        
        try {
        	BASE64Decoder decoder = new BASE64Decoder();
            byte[] buf = decoder.decodeBuffer(data);
            byte[] bt = decrypt(buf, object.getParentemployeecode().getBytes(ENCODE));
            return new String(bt, ENCODE);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        return null;
    }

    /**
     * Description 根据键值进行加密
     * 
     * @param data
     * @param key
     *            加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }

    /**
     * Description 根据键值进行解密
     * 
     * @param data
     * @param key
     *            加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }
}
