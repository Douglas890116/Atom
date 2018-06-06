package com.maven.payment.lf;


import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public class CoderUtil
{
  public static byte[] decryptBASE64(String key)
  {
    return Base64.getDecoder().decode(key);
  }

  public static String encryptBASE64(byte[] key)
    throws Exception
  {
    return Base64.getEncoder().encodeToString(key);
  }
}
