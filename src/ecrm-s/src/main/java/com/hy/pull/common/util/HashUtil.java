package com.hy.pull.common.util; 

import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

/**
 * hash算法工具类  
 * @date 2015年11月21日 下午12:02:17
 */
@SuppressWarnings("rawtypes")
public final class HashUtil {
	
	private static final long FNV_64_INIT = 0xcbf29ce484222325L;
	private static final long FNV_64_PRIME = 0x100000001b3L;
	
	/**
	 * 十六进制定义
	 */
	private final static byte[] hex = "0123456789ABCDEF".getBytes();
	
	/**
	 * 对一个文件进行md5计算
	 * @param f - 文件
	 * @return
	 */
	public static byte[] md5(File f) {
		
		if (null == f) {
			throw new IllegalArgumentException("file is null");
		}
		  
        FileInputStream in = null;  
	    try {  
	    	in =  new FileInputStream(f);
	        MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, f.length());  
	         
    		MessageDigest md = MessageDigest.getInstance("MD5"); 
    		byte[] rtv = null;
    		synchronized (md) {
    			md.reset(); 
    			md.update(byteBuffer);
    			rtv = md.digest();
    		}
    		return rtv; 
	        
	    } catch (Exception ex) {  
			throw new RuntimeException(ex);
	    } finally {  
	    	if (null != in) {
	    		try {
	    			in.close();
	    		} catch (Exception ex) { }
	    	}
	    }   
	}
	
	/**
	 * md5 hash算法
	 * @param msg
	 * @return - md5串(十六进制格式)
	 */
	public static String md5Hex(String msg) {
		
		byte[] r = null;
		try {
			r = md5(msg.getBytes("UTF-8"));
		} catch ( Exception ex) { }
		return encodeHex(r);
	}
	
	/**
	 * md5 hash算法:对一个文件计算
	 * @param f - 文件
	 * @return - md5值(十六进制表示)
	 */
	public static String md5Hex(File f) {

		byte[] r = null;
		try {
			r = md5(f);
		} catch ( Exception ex) { }
		return encodeHex(r);
	}
	
	/**
	 * md5 hash算法
	 * @param msg
	 * @return - md5串(base64编码)
	 */
	public static String md5Base64(String msg) {
		
		byte[] r = null;
		try {
			r = md5(msg.getBytes("UTF-8"));
		} catch ( Exception ex) { }
		return encodeBase64(r);
	}
	
	/**
	 * @description md5 hash算法
	 * @param data
	 * @return
	 */
	public static byte[] md5(byte[] data) {
		return md("MD5", data);
	}
	
	/**
	 * sha-1 hash算法
	 * @param msg
	 * @return - sha-1串(base64编码)
	 */
	public static String sha1Base64(String msg) {

		byte[] r = null;
		try {
			r = sha1(msg.getBytes("UTF-8"));
		} catch ( Exception ex) { }
		return encodeBase64(r);
	}
	
	/**
	 * sha-1 hash算法
	 * @param msg
	 * @return - sha-1串(十六进制格式)
	 */
	public static String sha1Hex(String msg) {

		byte[] r = null;
		try {
			r = sha1(msg.getBytes("UTF-8"));
		} catch ( Exception ex) { }
		return encodeHex(r);
	}
	
	/**
	 * sha-256 hash算法
	 * @param msg
	 * @return - sha-256串(十六进制格式)
	 */
	public static String sha256Hex(String msg) {

		byte[] r = null;
		try {
			r = sha256(msg.getBytes("UTF-8"));
		} catch ( Exception ex) { }
		return encodeHex(r);
	}
	
	/**
	 * sha-1 hash算法
	 * @param data
	 * @return
	 */
	public static byte[] sha1(byte[] data) {
		return md("SHA-1", data);
	}
	
	/**
	 * sha-256 hash算法
	 * @param data
	 * @return
	 */
	public static byte[] sha256(byte[] data) {
		return md("SHA-256", data);
	}
	 
	private static byte[] md(String alg, byte[] codes) {

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(alg);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(alg + " is not supported", e);
		}
		byte[] rtv = null;
		synchronized (md) {
			md.reset(); 
			md.update(codes);
			rtv = md.digest();
		}
		return rtv;
	}
	
	public static String encodeBase64(byte[] data) {
		
		byte[] rv = Base64.encodeBase64(data);
		
		return new String(rv);
	}
	
	public static byte[] decodeBase64(String data) {
		
		return Base64.decodeBase64(data);
	} 

	/**
	 * 从字节数组到十六进制字符串转换 
	 * @param b - 字节数组
	 * @return - 十六进制字符串
	 */
	public static String encodeHex(byte[] data) {

		try {
			byte[] buff = new byte[2 * data.length];
			for (int i = 0; i < data.length; i++) {
				buff[2 * i] = hex[(data[i] >> 4) & 0x0f];
				buff[2 * i + 1] = hex[data[i] & 0x0f];
			}
			return new String(buff);
		} catch (Exception ex) {
			throw new RuntimeException("十六进制编码异常：" + new String(data)); 
		}
	}
	
	/**
	 * 防止非法实例化
	 */
	private HashUtil() {}
}
