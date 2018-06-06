package com.hy.pull.common.util;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PBKDF2Encryption {

	public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";  
    
     public static String hash_pbkdf2( String password, String salt, int iterations, int len) throws Exception {
    	PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), iterations, len * 4);
    	SecretKeyFactory kFactory = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
    	SecretKey key = kFactory.generateSecret(pbeKeySpec);
    	byte[] res = key.getEncoded();
//    	return toHex1(res);
    	return toHex1(res);
    	}
    	private static String toHex1(byte[] array){
    	            BigInteger bi = new BigInteger(1, array);
    	            String hex = bi.toString(16);
    	            int paddingLength = (array.length * 2) - hex.length();
    	            if(paddingLength > 0)
    	                return String.format("%0" + paddingLength + "d", 0) + hex;
    	            else
    	                return hex;
    	    }
    	
    public static void main(String[] args) {
        try {  
        	
        	String salt = PBKDF2Encryption.hash_pbkdf2("280285", "1234567890", 1000, 32);
        	System.out.println(salt);
        	String str = salt.substring(0, 16);
        	System.out.println(str);;
        	
        	
        	
        } catch (NoSuchAlgorithmException e) {  
            System.out.println("NoSuchAlgorithmException");  
        } catch (Exception e) {  
            System.out.println("InvalidKeySpecException");  
        }  
	}
}
