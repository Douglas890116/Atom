package com.hy.pull.common.util.api;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

public class FileHelper {
	
	/**
	 * 创建多级目录
	 * @param dir
	 * @return int 
	 * 
	 * <p>
	 * -1 : 传入的路径不是个目录而是文件路径
	 * 0  : 创建成功
	 * 1  : 目录已经存在，没有重新创�?
	 * </p>
	 */
	public static int createDirs(String dir) {
		File file = new File(dir);
		//�?查传入的路径是否是目�?
		if(! file.isFile()) {
			if(file.exists()) {
				//已经存在该目�?
				return 1;
			} else {
				file.mkdirs();
				return 0;
			}
		} else {
			//不是目录
			return -1;
		}
	}
	
	/**
	 * <p>
	 * 根据文件名称或路径取得文件类型，并返回类型名（包�?.  �?.jpg�?
	 * </p>
	 * @param fileName
	 * @return
	 */
	public static String getFileType(String fileName){
		if(fileName!=null && !fileName.equals("")){
			fileName = fileName.toLowerCase();
			int a = fileName.lastIndexOf(".");
			fileName = fileName.substring(a);
			return fileName;
		}else return null;
	}
	
	/**
	 * 创建文件同时写入内容
	 * @param filePath
	 * @param content
	 */
	public static void writeFile(String filePath,String content) {
		boolean append = false;
		File file = new File(filePath);
		FileWriter fw;
		BufferedWriter bf;
		try {

			if (file.exists())
				append = true;
			fw = new FileWriter(filePath, append);// 同时创建新文件
			// 创建字符输出流对象
			bf = new BufferedWriter(fw);
			// 创建缓冲字符输出流对象
			bf.append(content).append("\r\n");
			bf.flush();
			bf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 读取文件内容
	 * @param filePath
	 * @return
	 */
	public static String readFile(String filePath) {
		try {
			String encoding = "UTF-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { //判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);//考虑到编码格�?
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				StringBuffer str = new StringBuffer();
				while ((lineTxt = bufferedReader.readLine()) != null) {
					str.append(lineTxt);
				}
				bufferedReader.close();
				read.close();
				return str.toString();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 读取文件内容
	 * @param filePath
	 * @return
	 */
	public static List<String> readFileList(String filePath) {
		try {
			String encoding = "UTF-8";
			File file = new File(filePath);
			List<String> __list = new ArrayList<String>();
			
			if (file.isFile() && file.exists()) { //判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);//考虑到编码格�?
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
//				StringBuffer str = new StringBuffer();
				while ((lineTxt = bufferedReader.readLine()) != null) {
//					str.append(lineTxt);
					__list.add(lineTxt);
				}
				bufferedReader.close();
				read.close();
//				return str.toString();
				return __list;
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return null;
	}
	
	private static final int BUFFER_SIZE = 16*1024;
	public static void copy(File src,File dst){
    	try{
    		InputStream in = null;
    		OutputStream out = null;
    		try{
    			in = new BufferedInputStream(new FileInputStream(src),BUFFER_SIZE);
    			out = new BufferedOutputStream(new FileOutputStream(dst),BUFFER_SIZE);
    			byte[] buffer = new byte[BUFFER_SIZE];
    			while(in.read(buffer)> 0){
    				out.write(buffer);
    			}
    		}finally{
    			if(null != in)
    				in.close();
    			if(null != out)
    				out.close();
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }

	
	
	/**
	 * 递归截取字符串。（对具有中文和英文的字符串，进行截取，获得其中的中文字符串）
	 * 
	 * @author xiaozd
	 */
	public static StringBuffer getChinesePart(StringBuffer stringBuffer) {
		stringBuffer = getStringBuffer(stringBuffer);
		if (stringBuffer.toString().getBytes().length == 2 * stringBuffer.length()) {
//			System.out.println("aaa");
		} else {
			stringBuffer = getChinesePart(stringBuffer);
		}
		return stringBuffer;
	}

	/**
	 * 字符串截取时，实际调用的递归方法
	 * 
	 * @param stringBuffer
	 * @return
	 */
	public static StringBuffer getStringBuffer(StringBuffer stringBuffer) {
		int start = 0;
		int end = 0;
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < stringBuffer.length(); i++) {
			// 通过字符的bytes长度来确定当前字符是否为中文，中文为2，英文，数字等为1
			String s = String.valueOf(stringBuffer.charAt(i));
			int len = s.getBytes().length;
			if (end == 0) {
				// 如果是英文
				if (len == 1) {
					end++;
				} else {
					// 如果是中文
					start++;
					end++;
				}
			} else {
				// 如果是英文
				if (len == 1) {
					end++;
				}
				if (len != 1 || i == stringBuffer.length() - 1) {
					// 如果是中文
					if (start != end) {
						sb = new StringBuffer(stringBuffer.delete(start, end));
						break;
					}
					start++;
					end++;
					if (i == stringBuffer.length() - 1) {
						sb = stringBuffer;
					}
				}
			}
		}
		return sb;
	}
    
	/**
	 * 读取文件内容
	 * 
	 * @param filePath
	 * @return
	 */
	public static void readFile2(String filePath) {
		try {
			String encoding = "UTF-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格�?
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				List<String> list = new ArrayList<String>();
				while ((lineTxt = bufferedReader.readLine()) != null) {
					String s = getChinesePart(new StringBuffer(lineTxt)).toString();//中文
					String s2 = lineTxt.replaceAll(s, "");
					System.out.println(s2+";"+s);
					list.add(s2+";"+s);
				}
				bufferedReader.close();
				read.close();
				
				// 写
				File f = new File("e://new.txt");
				if (f.exists()) {
				} else {
					f.createNewFile();//创建文件，如果指定路径是多层级的�?要使用该方法
				}
				OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f),"UTF-8");
				BufferedWriter writer=new BufferedWriter(write);
				
				for (String string : list) {
					writer.write(string);
					writer.newLine();
				}
				writer.close();
				write.close();
				
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		String fileName = "C:/Users/Administrator/Desktop/xxxxxx.txt";
		
		int count = 0;
	    Pattern p = Pattern.compile("HYLHQ33");
	    Matcher m = p.matcher(FileHelper.readFile(fileName));
	    while (m.find()) {
	        count++;
	    }
	    System.out.println(count);
	}
}
