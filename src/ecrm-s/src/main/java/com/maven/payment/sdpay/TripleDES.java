package com.maven.payment.sdpay;
import java.security.NoSuchAlgorithmException;  
import java.security.Security;  
import java.util.Random;  
  



import javax.crypto.Cipher;  
import javax.crypto.KeyGenerator;  
import javax.crypto.SecretKey;  
import javax.crypto.SecretKeyFactory;  
import javax.crypto.spec.DESedeKeySpec;  
import javax.crypto.spec.IvParameterSpec;  

import sun.misc.BASE64Decoder;
  
@SuppressWarnings("restriction")  
public class TripleDES {  
    static {  
        Security.addProvider(new com.sun.crypto.provider.SunJCE());  
    }  
  
    private static final String MCRYPT_TRIPLEDES = "DESede";  
    private static final String TRANSFORMATION = "DESede/CBC/PKCS5Padding";  
  
    public static byte[] decrypt(byte[] data, byte[] key, byte[] iv) throws Exception {  
        DESedeKeySpec spec = new DESedeKeySpec(key);  
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(MCRYPT_TRIPLEDES);  
        SecretKey sec = keyFactory.generateSecret(spec);  
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);  
        IvParameterSpec IvParameters = new IvParameterSpec(iv);  
        cipher.init(Cipher.DECRYPT_MODE, sec, IvParameters);  
        return cipher.doFinal(data);  
    }  
  
    public static byte[] encrypt(byte[] data, String Key1, String Key2) throws Exception {  

  	  byte[] key = new BASE64Decoder().decodeBuffer(Key1);
        byte[] iv = new BASE64Decoder().decodeBuffer(Key2);
        DESedeKeySpec spec = new DESedeKeySpec(key);  
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");  
        SecretKey sec = keyFactory.generateSecret(spec);  
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);  
        IvParameterSpec IvParameters = new IvParameterSpec(iv);  
        cipher.init(Cipher.ENCRYPT_MODE, sec, IvParameters);  
        return cipher.doFinal(data);  
    }  
  
    public static byte[] generateSecretKey() throws NoSuchAlgorithmException {  
        KeyGenerator keygen = KeyGenerator.getInstance(MCRYPT_TRIPLEDES);  
        return keygen.generateKey().getEncoded();  
    }  
  
    public static byte[] randomIVBytes() {  
        Random ran = new Random();  
        byte[] bytes = new byte[8];  
        for (int i = 0; i < bytes.length; ++i) {  
            bytes[i] = (byte) ran.nextInt(Byte.MAX_VALUE + 1);  
        }  
        return bytes;  
    }  
  
    public static void main(String args[]) throws Exception {  
        String plainText = "<TransferInformation>                           "
				+"<Id>0</Id>                                  "
				+"<IntoAccount>11</IntoAccount>               "
				+"<IntoName>11</IntoName>                     "
				+"<IntoBank1>CMB</IntoBank1>                  "
				+"<IntoBank2></IntoBank2>                     "
				+"<IntoAmount>0.1</IntoAmount>                "
				+"<TransferNote></TransferNote>               "
				+"<RecordsState>0</RecordsState>              "
				+"<SerialNumber>"+"al21011554484984984184"+"</SerialNumber>"
				+"<BusinessmanId>0</BusinessmanId>            "
				+"<SendORNOT>0</SendORNOT>                    "
				+"<beforeMoney>0</beforeMoney>                "
				+"<afterMoney>0</afterMoney>                  "
				+"<BankCardAlias></BankCardAlias>             "
				+"<AccountSerialNumber>0</AccountSerialNumber>"
				+"<GroupId>0</GroupId>                        "
				+"<TransferredBank></TransferredBank>         "
				+"<IntoProvince></IntoProvince>               "
				+"<IntoCity></IntoCity>                       "
				+"<BusinessmanName></BusinessmanName>         "
				+"<BankCode></BankCode>                       "
				+"</TransferInformation>                     ";
        final byte[] secretBytes = TripleDES.generateSecretKey();  

		String key1 = "SC4a7f6tIBU=";
		String key2 = "fOUqnZtouPc=";
        final byte[] ivbytes = TripleDES.randomIVBytes();  
        System.out.println("plain text: " + plainText);  
        byte[] encrypt = TripleDES.encrypt(plainText.getBytes(), key1, key2);  
        System.out.println("cipher text: " + encrypt);  
        System.out.println("decrypt text: " + new String(TripleDES.decrypt(encrypt, secretBytes, ivbytes), "UTF-8"));  
    }  
  
}  