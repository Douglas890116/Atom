package com.maven.payment.lfwx;

import java.io.FileInputStream;
import java.io.PrintStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class test {

	private static String ALGORITHM = "DSA";

	  private static final char[] bcdLookup = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	  
	  
	public static void main(String[] args) {
		String pubkey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQD9Bgw3CueUAgPaxJdChHsSUELWzA6qGT30i2uDFE0Op/mqesc8WTXM1UIUjFWYNLWSBnymsgqIC89J7Je1xSaXfg6YzXsA5wuNm3DGY+t+xsjkbDAMiCrfMKrex5B1zM97PG2XmLDL3uoX+5uGMZcVJhZA1KeQocC0jgrp84eRGwIDAQAB";
		String prikey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAP0GDDcK55QCA9rEl0KEexJQQtbMDqoZPfSLa4MUTQ6n+ap6xzxZNczVQhSMVZg0tZIGfKayCogLz0nsl7XFJpd+DpjNewDnC42bcMZj637GyORsMAyIKt8wqt7HkHXMz3s8bZeYsMve6hf7m4YxlxUmFkDUp5ChwLSOCunzh5EbAgMBAAECgYBZp6jwYAbKpSQkgoBua28IgDQO1GNl1mfjnPtNiQX86XxH8hVixuGPYQl3Knqx4gtsYKwOjQu6RSUANrtTBzayxvRZL5MJo05rTGlvJ0w9Exs2N2cOgXK9iTN/+b858Ck9f2bOKD+Hg5qVGgjSKRk+/JPlcSIdcrqbMdHoCybgMQJBAP+TDR7bCBrLDo1FlE4Fous2kcPZXYG2zYJUdcMSK8WNUmjtkM43oqDF04vmRMWl5PFdiA8QuVIIw0ujM6AyV00CQQD9cei5zQCv8Qzs6YTvHz7tRePFZ+0hB5TvCYQDPtD4HteduLeJ4D0Hhw6Q73AvvEWjezUcjTjBJ7oXKCCKmOYHAkEA6E0QQviR0FC7RFt3JsfmwudR7PN5E7tF5u3AMHQmxyTiQC+XTGmzb3EBDQtbfU+B3oXGcvMfj1oZsXmBJl47jQJBAMQhDlQajNma1MHRxIm3yF6dozH0xtC0qVCCMKLCTbx1Qa5Qb9hGq3PT1DXc1RhbvhhRFDzQHId9UsjwyJ34zKkCQCqWUFqVqgFFexy87242ajCHaz7MwZO0shLfc4hF0dAbN+uEHTadgIStgh3zI3XHeQCd/zF8gOQtEtcInqnV7qs=";
		//sign(privateKeyFile, data);
		String data = "{\"wx_pay_sm_url\":\"weixin://wxpay/bizpayurl?pr=PYrH5dY\",\"wx_pay_hx_url\":null,\"base64QRCode\":\"data:image/png;base64,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\"}";
		String signData = "Wj7vYIUkxjQ3bnSPqN3Whj8nLiNHayLmrcuX+GtmjiVoP+wSit0RHrSkAFHVp+gSYpJQSpXNj3R6g66wxZ8X+uEGndknowwLLCJUBFrHXswEuaT6IQeIU24hLbWD99khs9ywQOcgpUQjfspU4/OVnPLC23nMO/dqZeAySyuU1+g=";
		verifySign("C:/Users/Administrator/Desktop/乐付最新商户文档/rsa_public_key_lepay.key", signData, data, "UTF-8");
		
	}  
	
	
	public static boolean verifySign(String publicKeyFile, String signData, String data, String lang) {
	    try {
	      PublicKey publicKey = LoadPublicKey(publicKeyFile);

	      byte[] message = data.getBytes();
	      byte[] signature = (byte[])null;
	      if ((lang == null) || (lang.equals("")) || (lang.toUpperCase().equals("JAVA")))
	        signature = hexStrToBytes(signData);
	      else {
	        signature = changeDSANet2java(hexStrToBytes(signData));
	      }

	      Signature verifier = Signature.getInstance(ALGORITHM);
	      verifier.initVerify(publicKey);
	      verifier.update(message);

	      return verifier.verify(signature);
	    }
	    catch (Exception e)
	    {
	    	e.printStackTrace();
	      System.err.println("验证签名错误: " + e.toString());
	    }return false;
	  }

	  public static String sign(String privateKeyFile, String data)
	  {
	    try
	    {
	      PrivateKey privateKey = LoadPrivateKey(privateKeyFile);

	      Signature signer = Signature.getInstance(ALGORITHM);
	      signer.initSign(privateKey, new SecureRandom());

	      byte[] message = data.getBytes();

	      signer.update(message);
	      byte[] signature = signer.sign();

	      return bytesToHexStr(signature);
	    } catch (Exception e) {
	      System.err.println("签名数据错误: " + e.toString());
	    }return "";
	  }

	  private static final String bytesToHexStr(byte[] bcd)
	  {
	    StringBuffer s = new StringBuffer(bcd.length * 2);
	    for (int i = 0; i < bcd.length; i++) {
	      s.append(bcdLookup[(bcd[i] >>> 4 & 0xF)]);
	      s.append(bcdLookup[(bcd[i] & 0xF)]);
	    }
	    return s.toString();
	  }

	  private static final byte[] hexStrToBytes(String s)
	  {
	    byte[] bytes = new byte[s.length() / 2];
	    for (int i = 0; i < bytes.length; i++) {
	      bytes[i] = 
	        ((byte)Integer.parseInt(s.substring(2 * i, 2 * i + 2), 
	        16));
	    }
	    return bytes;
	  }

	  private static PublicKey LoadPublicKey(String filename)
	  {
	    PublicKey key = null;
	    try {
	      FileInputStream fis = new FileInputStream(filename);
	      byte[] b = new byte[fis.available()];
	      fis.read(b);
	      fis.close();

	      X509EncodedKeySpec spec = new X509EncodedKeySpec(b);

	      KeyFactory factory = KeyFactory.getInstance(ALGORITHM);
	      key = factory.generatePublic(spec);
	    }
	    catch (Exception e) {
	    	e.printStackTrace();
	      System.err.println("LoadPublicKey: " + e.toString());
	    }

	    return key;
	  }

	  private static PrivateKey LoadPrivateKey(String filename) {
	    PrivateKey key = null;
	    try
	    {
	      FileInputStream fis = new FileInputStream(filename);
	      byte[] b = new byte[fis.available()];
	      fis.read(b);
	      fis.close();

	      PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(b);

	      KeyFactory factory = KeyFactory.getInstance("DSA");
	      key = factory.generatePrivate(spec);
	    }
	    catch (Exception e) {
	      System.err.println("LoadPrivateKey: " + e.toString());
	    }

	    return key;
	  }

	  private static byte[] changeDSANet2java(byte[] sign) {
	    byte[] tx_new = new byte[46];
	    tx_new[0] = 48;
	    tx_new[1] = 44;
	    tx_new[2] = 2;
	    tx_new[3] = 20;
	    for (int x = 0; x < 20; x++) {
	      tx_new[(x + 4)] = sign[x];
	    }
	    tx_new[24] = 2;
	    tx_new[25] = 20;
	    for (int x = 20; x < 40; x++) {
	      tx_new[(x + 6)] = sign[x];
	    }
	    return tx_new;
	  }
}

