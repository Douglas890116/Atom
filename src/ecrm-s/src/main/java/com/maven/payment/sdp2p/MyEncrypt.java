package com.maven.payment.sdp2p;
import java.util.Date;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class MyEncrypt {

    public String Key1; 
    public String Key2;
   
    public String EncryptData(String Data) throws Exception
    {
    	  byte[] keyBytes = new BASE64Decoder().decodeBuffer(Key1);
          byte[] keyIV = new BASE64Decoder().decodeBuffer(Key2);
          IvParameterSpec zeroIv = new IvParameterSpec(keyIV);
          SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
          Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
          cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
          byte[] encryptedData = cipher.doFinal(Data.getBytes());
          String encode = new BASE64Encoder().encode(encryptedData); 
          return encode; 
    }
    
    
    public static String EncryptData(String Data, String Key1, String Key2)
    {
        String result="";
        MyEncrypt encrypt = new MyEncrypt();
        encrypt.Key1 = Key1;
        encrypt.Key2 = Key2;
        try {
        	result= encrypt.EncryptData(Data + getMd5Hash(getMac() + new Date().getTime()+""));
		} catch (Exception e) {
			e.printStackTrace();
		}
        return result;
    }
    static String getMd5Hash(String input)
    {
         new MD5Encode();
		return MD5Encode.encode(input);
    } 
    private static String getMac()
    {
        return new Date().getTime()+""; 
    }
    
}



