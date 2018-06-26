package com.hy.pull.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 * ftp 上传下载工具类 采用Apache FTPClient类
 * Created by Cloud on 2016/6/28.
 */
public class FtpUtil {
	// FTPClient工具
	private static FTPClient ftpClient;
	// 登录信息
	private String serverIP;
	private int port;
	private String userName;
	private String password;
	// FTP相关参数配置
	private int fileTye = FTPClient.BINARY_FILE_TYPE; // 传输方式:二进制
	private int bufferSize = 1024 * 10; // 缓存大小
	private String charset = "UTF-8"; // 字符编码

	/**
	 * 构造函数，初始化登录信息
	 *
	 * @param serverIP
	 *            ftp服务器IP地址
	 * @param port
	 *            ftp服务器端口号
	 * @param userName
	 *            登录用户名
	 * @param password
	 *            登录密码
	 */
	public FtpUtil(String serverIP, int port, String userName, String password) {
		this.serverIP = serverIP;
		this.port = port;
		this.userName = userName;
		this.password = password;
	}

	/**
	 * 打开ftp连接
	 *
	 * @return
	 */
	public boolean open() throws IOException {
		if (null != ftpClient && ftpClient.isConnected())
			return true;
		ftpClient = new FTPClient();
		// 连接FTP服务器
		ftpClient.connect(this.serverIP, this.port);
		ftpClient.login(this.userName, this.password);
		// 检测是否连接成功
		int reply = ftpClient.getReplyCode();
		if (FTPReply.isPositiveCompletion(reply)) {
			// 连接成功，设置基础配置
			ftpClient.enterLocalPassiveMode(); // 开通传输端口
			ftpClient.setFileType(this.fileTye); // 设置采用二进制传输
			ftpClient.setBufferSize(this.bufferSize); // 设置传输缓存大小
			ftpClient.setControlEncoding(this.charset); // 设置传输字符编码
			return true;
		} else {
			// 连接失败，关闭FTPClient
			this.close();
			return false;
		}
	}

	/**
	 * 关闭FTP连接
	 */
	public void close() throws IOException {
		if (null != ftpClient && ftpClient.isConnected())
			ftpClient.disconnect();
	}

	/**
	 * 对路径中的字符进行编码格式化
	 *
	 * @param string
	 * @return
	 */
	public String UnicodeFormat(String string) throws UnsupportedEncodingException {
		return new String(string.getBytes("UTF-8"), "ISO-8859-1");
	}

	/**
	 * 改变目录
	 *
	 * @param ftpPath
	 *            目录地址
	 * @return
	 * @throws IOException
	 */
	public boolean changeDir(String ftpPath) throws IOException {
		if (null == ftpClient || !ftpClient.isConnected())
			return false;
		ftpPath = pathFormat(ftpPath);
		ftpPath = UnicodeFormat(ftpPath);
		if (ftpPath.indexOf('/') < 0) {
			return ftpClient.changeWorkingDirectory(ftpPath);
		} else {
			String[] paths = ftpPath.split("/");
			boolean result = false;
			for (String path : paths) {
				result = ftpClient.changeWorkingDirectory(UnicodeFormat(path));
			}
			return result;
		}
	}

	/**
	 * 获取指定文件夹下的的文件列表
	 *
	 * @param folderPath
	 *            文件夹位置
	 * @return 返回文件列表
	 */
	public List<String> getFileList(String folderPath) throws IOException {
		changeDir(folderPath);
		FTPFile[] ftpFiles = ftpClient.listFiles();
		List<String> files = new ArrayList<String>();
		if (ftpFiles != null && ftpFiles.length > 0) {
			for (FTPFile ftpFile : ftpFiles) {
				if (ftpFile.isFile())
					files.add(ftpFile.getName());
			}
		}
		return files;
	}

	/**
	 * 获取指定文件夹下的的文件夹列表
	 *
	 * @param folderPath
	 *            文件夹位置
	 * @return 返回文件列表
	 */
	public List<String> getFolderList(String folderPath) throws IOException {
		changeDir(folderPath);
		FTPFile[] ftpFiles = ftpClient.listFiles();
		List<String> folders = new ArrayList<String>();
		if (ftpFiles != null && ftpFiles.length > 0) {
			for (FTPFile ftpFile : ftpFiles) {
				if (ftpFile.isDirectory())
					folders.add(ftpFile.getName());
			}
		}
		return folders;
	}
	
	/**
	 * 获取指定文件夹下的所有文件及文件夹列表
	 *
	 * @param folderPath
	 *            文件夹位置
	 * @return 返回文件列表
	 */
	public List<String> getAllList(String folderPath) throws IOException {
		changeDir(folderPath);
		FTPFile[] ftpFiles = ftpClient.listFiles();
		List<String> all = new ArrayList<String>();
		if (ftpFiles != null && ftpFiles.length > 0) {
			for (FTPFile ftpFile : ftpFiles) {
				all.add(ftpFile.getName());
			}
		}
		return all;
	}
	
	/**
	 * 创建目录
	 *
	 * @param ftpPath
	 *            需要创建目录的路径
	 * @return
	 */
	public boolean mkDir(String ftpPath) throws IOException {
		if (!ftpClient.isConnected())
			return false;
		// 格式化路径
		ftpPath = pathFormat(ftpPath);
		ftpPath = UnicodeFormat(ftpPath);
		if (ftpPath.indexOf('/') < 0) {
			// 只有一层目录
			ftpClient.makeDirectory(ftpPath);
			ftpClient.changeWorkingDirectory(ftpPath);
		} else {
			// 多层目录
			String[] paths = ftpPath.split("/");
			for (String path : paths) {
				ftpClient.makeDirectory(path);
				ftpClient.changeWorkingDirectory(UnicodeFormat(path));
			}
		}
		return true;
	}

	/**
	 * 上传整个目录
	 *
	 * @param file
	 *            需要上传到目录
	 */
	public void upload(File file) throws IOException {
		if (file.isDirectory()) {
			ftpClient.makeDirectory(UnicodeFormat(file.getName()));
			ftpClient.changeWorkingDirectory(UnicodeFormat(file.getName()));
			String[] files = file.list();
			for (String str : files) {
				File _file = new File(file.getPath() + "/" + str);
				if (_file.isDirectory()) {
					upload(_file);
					ftpClient.changeToParentDirectory();
				} else {
					File file_ = new File(file.getPath() + "/" + str);
					FileInputStream input = new FileInputStream(file_);
					ftpClient.storeFile(UnicodeFormat(file_.getName()), input);
					input.close();
				}
			}
		} else {
			File file_ = new File(UnicodeFormat(file.getName()));
			FileInputStream input = new FileInputStream(file_);
			ftpClient.storeFile(UnicodeFormat(file_.getName()), input);
			input.close();
		}
	}

	/**
	 * 將本地文件上上传到ftp服务器指定位置
	 *
	 * @param localFile
	 *            本地文件位置
	 * @param ftpFileName
	 *            上传到ftp服务器后的文件名
	 * @param ftpDir
	 *            需要上传到ftp服务器的目录
	 * @return
	 */
	public boolean put(String localFile, String ftpFileName, String ftpDir) throws IOException {
		if (null == ftpClient || !ftpClient.isConnected())
			return false;
		File srcFile = new File(localFile);
		FileInputStream fis = new FileInputStream(srcFile);
		mkDir(ftpDir);
		boolean isSuccess = ftpClient.storeFile(UnicodeFormat(ftpFileName), fis);
		IOUtils.closeQuietly(fis);
		return isSuccess;
	}

	/**
	 * 从ftp服务器上下载文件到本地
	 *
	 * @param ftpFile
	 *            服务器上的文件位置
	 * @param localFile
	 *            下载到本地的文件位置
	 * @return
	 */
	public boolean get(String ftpFile, String localFile) throws IOException {
		if (null == ftpClient || !ftpClient.isConnected())
			return false;
		ftpFile = pathFormat(ftpFile);
		String ftpDir = ftpFile.substring(0, ftpFile.lastIndexOf("/"));
		String ftpFileName = ftpFile.substring(ftpFile.lastIndexOf("/") + 1);
		this.changeDir(ftpDir);
		return ftpClient.retrieveFile(UnicodeFormat(ftpFileName), new FileOutputStream(localFile));
	}

	/**
	 * 删除FTP上的文件
	 *
	 * @param ftpFile
	 * @return
	 */
	public boolean deleteFile(String ftpFile) throws IOException {
		if (!ftpClient.isConnected())
			return false;
		ftpClient.doCommandAsStrings("DELE", UnicodeFormat(ftpFile));
		return true;
	}

	/**
	 * 删除ftp上的目录
	 *
	 * @param dir
	 * @return
	 * @throws IOException
	 */
	public boolean deleteDir(String dir) throws IOException {
		if (!ftpClient.isConnected())
			return false;
		ftpClient.doCommandAsStrings("RMDA", UnicodeFormat(dir));
		return true;
	}

	/**
	 * 删除整个目录
	 *
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public boolean removeAll(String path) throws IOException {
		if (!ftpClient.isConnected())
			return false;
		FTPFile[] files = ftpClient.listFiles(path);
		boolean result = true;
		for (FTPFile file : files) {
			if (file.isDirectory()) {
				result = removeAll(path + "/" + file.getName()) && result;
				result = ftpClient.removeDirectory(path) && result;
			} else {
				result = ftpClient.deleteFile(path + "/" + file.getName()) && result;
			}
		}
		return result;
	}

	/**
	 * 格式化路径 将反斜杠(\)替换成斜杠(/)
	 *
	 * @param pathStr
	 *            原始路径
	 * @return 格式化后的路径
	 */
	public static String pathFormat(String pathStr) {
		// 字符串为空则直接返回空路径
		if (null == pathStr)
			return "";
		char[] cs = pathStr.toCharArray();
		StringBuffer sb = new StringBuffer(256);
		for (char c : cs) {
			if (c == '\\')
				sb.append('/');
			else
				sb.append(c);
		}
		return sb.toString();
	}
}