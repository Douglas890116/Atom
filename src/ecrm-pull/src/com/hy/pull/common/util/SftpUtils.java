package com.hy.pull.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class SftpUtils {
	public ChannelSftp sftp;
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
	public SftpUtils(String ip, int port, String userName, String userPwd, String dir) {
		try {
//			JSch jsch = new JSch();
//			Session sshSession = jsch.getSession(LOGIN_NAME, FTP_IP, FTP_PORT);
//			sshSession.setPassword(PASSWORD);
//			sshSession.setConfig("StrictHostKeyChecking", "no");
//			sshSession.connect();
//			Channel channel = sshSession.openChannel("sftp");
//			channel.connect();
//			sftp = (ChannelSftp) channel;
			
			JSch jsch = new JSch();
			jsch.getSession(userName, ip, port);
			Session sshSession = jsch.getSession(userName, ip, port);
			sshSession.setPassword(userPwd);			
			sshSession.setConfig("StrictHostKeyChecking", "no");
			sshSession.connect();
			Channel channel = sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
			sftp.cd(dir);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 上传文件
	 * 
	 * @param directory 上传的目录
	 * @param uploadFile 要上传的文件
	 */
	public void upload(String directory, String uploadFile) {
		try {
			sftp.cd(directory);
			File file = new File(uploadFile);
			sftp.put(new FileInputStream(file), file.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param directory 下载目录
	 * @param downloadFile 下载的文件
	 * @param saveFile 存在本地的路径
	 */
	public void download(String directory, String downloadFile, String saveFile) {
		try {
			sftp.cd(directory);
			File file = new File(saveFile);
			sftp.get(downloadFile, new FileOutputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param directory 要删除文件所在目录
	 * @param deleteFile 要删除的文件
	 */
	public void delete(String directory, String deleteFile) {
		try {
			sftp.cd(directory);
			sftp.rm(deleteFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 列出目录下的文件
	 * 
	 * @param directory 要列出的目录
	 * @return
	 * @throws SftpException
	 */
	@SuppressWarnings("unchecked")
	public List<String> getFileList(String directory) throws SftpException {
		return sftp.ls(directory);
	}

	public static void main(String[] args) {
		
		String host = "linux主机ip";
		int port = 22;
		String username = "root";
		String password = "root";
		String directory = "/u01";
		String uploadFile = "F:/tempfiles/2_201083103325.png";
		String downloadFile = "123.png";
		String saveFile = "F:/tempfiles/2_201083103325_download.png";
		String deleteFile = "delete.txt";
		SftpUtils sf = new SftpUtils("xml.agingames.com",21,"P36.tcbet88yule","9&!UhbiFWo","/");
		//ChannelSftp sftp = sf.connect(host, port, username, password);
		//sf.upload(directory, uploadFile);
		// sf.download(directory, downloadFile, saveFile, sftp);
		// sf.delete(directory, deleteFile, sftp);
		try {
//			sf.sftp.cd(directory);
//			sf.sftp.mkdir("ss");
			System.out.println(sf.getFileList("/AGIN/20161109"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
