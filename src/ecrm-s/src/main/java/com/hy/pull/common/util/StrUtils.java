package com.hy.pull.common.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 字符格式化工具类
 * 创建日期 2016-10-06
 * @author temdy
 */
public class StrUtils {

	/**
	 * 把手机中间5位用*号来代替
	 * @author temdy
	 * @param phone 手机号码
	 * @return 返回处理好的字符串
	 */
	public static String hiddenPhone(String phone) {
		return StringUtils.replace(phone, phone.substring(3, 8), "*****");

	}

	/**
	 * 把一个字符串数组用一个标点符号连接成一个新的字符串
	 * @author temdy
	 * @param value  待处理的字符串数组
	 * @return 返回处理好的字符串
	 */
	public static String join(String[] value) {
		return join(value, ",");
	}

	/**
	 * 重载方法（把一个字符串数组用一个标点符号连接成一个新的字符串）
	 * @author temdy
	 * @param value 待处理的字符串数组
	 * @param sign 连接的标记
	 * @return 返回处理好的字符串
	 */
	public static String join(String[] value, String sign) {
		StringBuilder sb = new StringBuilder();
		if (value != null && value.length > 0) {
			for (String s : value) {
				sb.append(s + sign); //循环遍历数组中元素，添加到 StringBuilder 对象中
			}
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1); //调用 字符串的deleteCharAt()删除最后一个多余的逗号
			return sb.toString();
		} else {
			return "";
		}
	}

	/**
	 * 删除字符串最后一个字符的通用方法
	 * @author temdy
	 * @param value 待处理的字符串
	 * @return 返回处理后的字符串
	 */
	public static String delLastChar(String value) {
		StringBuilder sb = new StringBuilder(value);
		return sb.deleteCharAt(sb.length() - 1).toString();
	}
	

	/**
	 * 人民币金钱转大写格式方法
	 * @author temdy
	 * @param value 转换的字符串
	 * @return 返回大写格式的人民币字符串
	 */
	public static String rmbToBig(double value) {
		char[] hunit = { '拾', '佰', '仟' }; // 段内位置表示
		char[] vunit = { '万', '亿' }; // 段名表示
		char[] digit = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' }; // 数字表示
		long midVal = (long) (value * 100); // 转化成整形
		String valStr = String.valueOf(midVal); // 转化成字符串

		String head = valStr.substring(0, valStr.length() - 2); // 取整数部分
		String rail = valStr.substring(valStr.length() - 2); // 取小数部分

		String prefix = ""; // 整数部分转化的结果
		String suffix = ""; // 小数部分转化的结果
		// 处理小数点后面的数
		if (rail.equals("00")) { // 如果小数部分为0
			suffix = "整";
		} else {
			suffix = digit[rail.charAt(0) - '0'] + "角" + digit[rail.charAt(1) - '0'] + "分"; // 否则把角分转化出来
		}
		// 处理小数点前面的数
		char[] chDig = head.toCharArray(); // 把整数部分转化成字符数组
		char zero = '0'; // 标志'0'表示出现过0
		byte zeroSerNum = 0; // 连续出现0的次数
		for (int i = 0; i < chDig.length; i++) { // 循环处理每个数字
			int idx = (chDig.length - i - 1) % 4; // 取段内位置
			int vidx = (chDig.length - i - 1) / 4; // 取段位置
			if (chDig[i] == '0') { // 如果当前字符是0
				zeroSerNum++; // 连续0次数递增
				if (zero == '0') { // 标志
					zero = digit[0];
				} else if (idx == 0 && vidx > 0 && zeroSerNum < 4) {
					prefix += vunit[vidx - 1];
					zero = '0';
				}
				continue;
			}
			zeroSerNum = 0; // 连续0次数清零
			if (zero != '0') { // 如果标志不为0,则加上,例如万,亿什么的
				prefix += zero;
				zero = '0';
			}
			prefix += digit[chDig[i] - '0']; // 转化该数字表示
			if (idx > 0)
				prefix += hunit[idx - 1];
			if (idx == 0 && vidx > 0) {
				prefix += vunit[vidx - 1]; // 段结束位置应该加上段名如万,亿
			}
		}

		if (prefix.length() > 0)
			prefix += '圆'; // 如果整数部分存在,则有圆的字样
		return prefix + suffix; // 返回正确表示
	}

	/**
	 * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	 * @author temdy
	 * @param path 图片路径
	 * @return 返回Base64编码过的字节数组字符串
	 */
	public static String imageToBase64(String path) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		byte[] data = null;
		String base64 = null;
		// 读取图片字节数组
		try {
			InputStream in = new FileInputStream(path);
			data = new byte[in.available()];
			in.read(data);
			in.close();
			base64 = Base64.getEncoder().encodeToString(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return base64;
	}

	/**
	 * 对字节数组字符串进行Base64解码并生成图片
	 * @author temdy
	 * @param base64 图片Base64数据
	 * @param path 图片路径
	 * @return 是否解码成功{true,false}
	 */
	public static boolean base64ToImage(String base64, String path) {
		boolean flag = false;
		if (base64 == null){ // 图像数据为空
			return false;
		}		
		try {
			// Base64解码
			byte[] bytes = Base64.getDecoder().decode(base64);
			for (int i = 0; i < bytes.length; ++i) {
				if (bytes[i] < 0) {// 调整异常数据
					bytes[i] += 256;
				}
			}
			// 生成jpeg图片
			OutputStream out = new FileOutputStream(path);
			out.write(bytes);
			out.flush();
			out.close();
			flag = true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;		
	}
	
	/**
	 * 正则表达式删除HTML标签
	 * @author temdy
	 * @param htmlStr 带html标签的字符串
	 * @return 返回处理好的字符串
	 */
	public static String delHTMLTag(String htmlStr){ 
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式 
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式 
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式 
         
        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
        Matcher m_script=p_script.matcher(htmlStr); 
        htmlStr=m_script.replaceAll(""); //过滤script标签 
         
        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
        Matcher m_style=p_style.matcher(htmlStr); 
        htmlStr=m_style.replaceAll(""); //过滤style标签 
         
        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
        Matcher m_html=p_html.matcher(htmlStr); 
        htmlStr=m_html.replaceAll(""); //过滤html标签 

        return htmlStr.trim(); //返回文本字符串 
    }
}
