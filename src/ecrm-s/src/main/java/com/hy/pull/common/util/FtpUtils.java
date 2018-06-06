package com.hy.pull.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
 * FTP操作工具类
 * 创建日期 2016-10-12
 * @author temdy
 */
public class FtpUtils {
	public FTPClient ftpClient;
	private String max;

	/**
	 * 获取最大值
	 * @return 最大值
	 */
	public String getMax() {
		return max;
	}

	/**
	 * 设置最大值
	 * @param max 最大值
	 */
	public void setMax(String max) {
		this.max = max;
	}	

	/**
	 * @param ip ftp服务器
	 * @param port 端口号
	 * @param userName 用户名
	 * @param userPwd 密码
	 * @param dir 默认目录
	 */
	public FtpUtils(String ip, int port, String userName, String userPwd, String dir) {
		ftpClient = new FTPClient();
		try {
			//连接
			ftpClient.connect(ip, port);
			//登录
			ftpClient.login(userName, userPwd);
			if(dir == null){
				dir = "/";
			}			
			//跳转到指定目录
			ftpClient.changeWorkingDirectory(dir);			
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭连接
	 */
	public void closeServer() {
		if (ftpClient.isConnected()) {
			try {
				ftpClient.logout();
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取服务器目录下的文件列表
	 * @param dir 目录
	 * @return 文件列表
	 */
	public List<String> getFileList(String dir) {
		List<String> fileLists = new ArrayList<String>();
		// 获得指定目录下所有文件名
		FTPFile[] ftpFiles = null;
		try {
			ftpFiles = ftpClient.listFiles(dir);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; ftpFiles != null && i < ftpFiles.length; i++) {
			FTPFile file = ftpFiles[i];
			if (file.isFile()) {
				fileLists.add(file.getName());
			}
		}
		return fileLists;
	}

	/**
	 * 读取文件内容
	 * @param filePath 文件路径
	 * @return 文件内容
	 */
	public String readFile(String filePath) {
		InputStream ins = null;
		StringBuilder builder = null;
		try {
			//从服务器上读取指定的文件
			ins = ftpClient.retrieveFileStream(filePath);
			BufferedReader reader = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
			String line;
			builder = new StringBuilder(150);
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			reader.close();
			if (ins != null) {
				ins.close();
			}
			// 主动调用一次getReply()把接下来的226消费掉. 这样做是可以解决这个返回null问题
			ftpClient.getReply();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return builder.toString();
	}

	/**
	 * 删除服务器上的文件
	 * @param fileName 文件名称
	 */
	public void deleteFile(String fileName) {
		try {
			ftpClient.deleteFile(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}