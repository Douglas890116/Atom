package com.maven.utils;

import java.net.URLEncoder;

import org.apache.commons.codec.binary.Base64;

public class Base64Helper {

	/**
	 * 编码
	 * 
	 * @param byteArray
	 * @return
	 */
	public static String encode(byte[] byteArray) {
		return new String(new Base64().encode(byteArray));
	}

	/**
	 * 解码
	 * 
	 * @param base64EncodedString
	 * @return
	 */
	public static byte[] decode(String base64EncodedString) {
		return new Base64().decode(base64EncodedString);
	}
	
	public static void main(String[] args) {
//		H5登录加密源：u=hqyy303092|p=112233|i=0|l=cn|url=http://www.dw6868.com
//		H5登录完整登录链接：http://phone.gew33.com/html5Login.html?h5params=dT1ocXl5MzAzMDkyfHA9MTEyMjMzfGk9MHxsPWNufHVybD1odHRwOi8vd3d3LmR3Njg2OC5jb20%3D
		String __param = "u=hqyy303092|p=112233|i=0|l=cn|url=http://www.dw6868.com";
		try {
			System.out.println("1=dT1ocXl5MzAzMDkyfHA9MTEyMjMzfGk9MHxsPWNufHVybD1odHRwOi8vd3d3LmR3Njg2OC5jb20%3D");
//			System.out.println("2="+encode(__param.getBytes("UTF-8")));
			System.out.println("3=dT1ocXl5MzAzMDkyfHA9MTEyMjMzfGk9MHxsPWNufHVybD1odHRwOi8vd3d3LmR3Njg2OC5jb20=");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
