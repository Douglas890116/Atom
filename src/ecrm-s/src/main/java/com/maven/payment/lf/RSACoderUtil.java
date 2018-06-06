package com.maven.payment.lf;


import java.net.URLEncoder;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;

public class RSACoderUtil extends CoderUtil
{
  public static final String KEY_ALGORTHM = "RSA";
  public static final String SIGNATURE_ALGORITHM = "SHA1withRSA";
  public static final int KEY_SIZE = 1024;
  public static final String PUBLIC_KEY = "RSAPublicKey";
  public static final String PRIVATE_KEY = "RSAPrivateKey";
  private static final int MAX_ENCRYPT_BLOCK = 117;
  private static final int MAX_DECRYPT_BLOCK = 128;

  public static String getParamsWithDecodeByPublicKey(String paramsString, String charset, String fcsPublicKey)
    throws Exception
  {
    byte[] paramByteArr = encryptByPublicKey(paramsString.getBytes(charset), fcsPublicKey);
    return URLEncoder.encode(encryptBASE64(paramByteArr), charset);
  }

  public static String getParamsWithDecodeByPrivateKey(String paramsString, String charset, String privateKey)
    throws Exception
  {
    byte[] shaParams = DigestCoder.encodeWithSHA(paramsString.getBytes(charset));

    String signParams = sign(shaParams, privateKey);

    return URLEncoder.encode(signParams, charset);
  }

  public static byte[] encryptByPrivateKey(byte[] data, String key)
    throws Exception
  {
    byte[] keyBytes = decryptBASE64(key);

    PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

    Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
    cipher.init(1, privateKey);

    byte[] dataReturn = null;
    for (int i = 0; i < data.length; i += 117) {
      byte[] doFinal = cipher.doFinal(subarray(data, i, i + 117));
      dataReturn = addAll(dataReturn, doFinal);
    }

    return dataReturn;
  }

  public static byte[] decryptByPrivateKey(byte[] data, String key)
    throws Exception
  {
    byte[] keyBytes = decryptBASE64(key);

    PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

    Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
    cipher.init(2, privateKey);

    byte[] dataReturn = null;
    for (int i = 0; i < data.length; i += 128) {
      byte[] doFinal = cipher.doFinal(subarray(data, i, i + 128));
      dataReturn = addAll(dataReturn, doFinal);
    }
    return dataReturn;
  }

  public static byte[] encryptByPublicKey(byte[] data, String key)
    throws Exception
  {
    byte[] keyBytes = decryptBASE64(key);

    X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

    Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
    cipher.init(1, publicKey);

    byte[] dataReturn = null;
    for (int i = 0; i < data.length; i += 117) {
      byte[] doFinal = cipher.doFinal(subarray(data, i, i + 117));
      dataReturn = addAll(dataReturn, doFinal);
    }

    return dataReturn;
  }

  public static byte[] decryptByPublicKey(byte[] data, String key)
    throws Exception
  {
    byte[] keyBytes = decryptBASE64(key);
    X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

    Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
    cipher.init(2, publicKey);

    byte[] dataReturn = null;
    for (int i = 0; i < data.length; i += 128) {
      byte[] doFinal = cipher.doFinal(subarray(data, i, i + 128));
      dataReturn = addAll(dataReturn, doFinal);
    }

    return dataReturn;
  }

  public static String sign(byte[] data, String privateKey)
    throws Exception
  {
    byte[] keyBytes = decryptBASE64(privateKey);

    PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);

    KeyFactory keyFactory = KeyFactory.getInstance("RSA");

    PrivateKey privateKey2 = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

    Signature signature = Signature.getInstance("SHA1withRSA");
    signature.initSign(privateKey2);
    signature.update(data);

    return encryptBASE64(signature.sign());
  }

  public static boolean verify(byte[] data, String publicKey, String sign)
    throws Exception
  {
    byte[] keyBytes = decryptBASE64(publicKey);

    X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);

    KeyFactory keyFactory = KeyFactory.getInstance("RSA");

    PublicKey publicKey2 = keyFactory.generatePublic(x509EncodedKeySpec);

    Signature signature = Signature.getInstance("SHA1withRSA");
    signature.initVerify(publicKey2);
    signature.update(data);

    return signature.verify(decryptBASE64(sign));
  }

  public static byte[] subarray(byte[] array, int startIndexInclusive, int endIndexExclusive)
  {
    if (array == null) {
      return null;
    }
    if (startIndexInclusive < 0) {
      startIndexInclusive = 0;
    }
    if (endIndexExclusive > array.length) {
      endIndexExclusive = array.length;
    }
    int newSize = endIndexExclusive - startIndexInclusive;
    if (newSize <= 0) {
      return new byte[0];
    }

    byte[] subarray = new byte[newSize];
    System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
    return subarray;
  }

  public static Map<String, Object> initKey() throws Exception {
    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
    keyPairGenerator.initialize(1024);
    KeyPair keyPair = keyPairGenerator.generateKeyPair();
    RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
    RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
    Map keyMap = new HashMap();
    keyMap.put("RSAPublicKey", publicKey);
    keyMap.put("RSAPrivateKey", privateKey);
    return keyMap;
  }

  public static byte[] addAll(byte[] array1, byte[] array2)
  {
    if (array1 == null)
      return clone(array2);
    if (array2 == null) {
      return clone(array1);
    }
    byte[] joinedArray = new byte[array1.length + array2.length];
    System.arraycopy(array1, 0, joinedArray, 0, array1.length);
    System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
    return joinedArray;
  }

  public static byte[] clone(byte[] array)
  {
    if (array == null) {
      return null;
    }
    return (byte[])array.clone();
  }
}
