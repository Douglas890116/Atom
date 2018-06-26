package com.hy.pull.common.util;

import java.io.File;    
import java.io.FileNotFoundException;    
import java.io.FileOutputStream;    
import java.io.IOException;    
import java.io.OutputStream;    
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;  
import org.apache.commons.logging.Log;  
import org.apache.commons.logging.LogFactory;  
import org.apache.commons.net.ftp.*;  
  
  
public class FtpUtilsNew {  
  
    private final static Log logger = LogFactory.getLog(FtpUtilsNew.class);  
  
    public static void main(String[] args) {
    	String ftpHost = "xml.agingames.com";  
        String ftpUserName = "S52.agenting";  
        String ftpPassword = "yXH$7Q3anb";  
        String agentgame = "S52_AGIN";
        int ftpPort = 21;  
        String localPath = "e:/agdownload/";//本地存储的目录
        
        // AGIN/HUNTER/XIN
        // 获取正常的数据（List只是纯数字名字）
        String max = "201707290000";  
        String ftpPath = "/AGIN/"+max.substring(0,8)+"/";
        
        List<String>  result = FtpUtilsNew.downloadFtpFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, localPath, max, agentgame);  
        if(result != null ) {
        	System.out.println("采集到"+result.size()+"个文件");
        }
        
        // 获取补单的数据（List只是纯数字名字）
        ftpPath = "/AGIN/"+max.substring(0,8)+"/lostAndfound/";
        List<String>  result2 = FtpUtilsNew.downloadFtpFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, localPath, max, agentgame);  
        if(result2 != null ) {
        	System.out.println("采集到"+result2.size()+"补单个文件");
        }
        
        
        
        //全部完成后最后更新本次的最大值（补单数据不更新最大值，因为每次都是采集最大值）
        if(result != null ) {
        	//更新本次的最大值
        	max = result.get(result.size() - 1) ;
        }
	}
    /** 
     * 获取FTPClient对象 
     * 
     * @param ftpHost 
     *            FTP主机服务器 
     * @param ftpPassword 
     *            FTP 登录密码 
     * @param ftpUserName 
     *            FTP登录用户名 
     * @param ftpPort 
     *            FTP端口 默认为21 
     * @return 
     */  
    public static FTPClient getFTPClient(String ftpHost, String ftpUserName,  
            String ftpPassword, int ftpPort) {  
        FTPClient ftpClient = new FTPClient();  
        try {  
            ftpClient = new FTPClient();  
            ftpClient.setDataTimeout(60000);       //设置传输超时时间为60秒 
			ftpClient.setConnectTimeout(60000);       //连接超时为60秒
            ftpClient.connect(ftpHost, ftpPort);// 连接FTP服务器  
            ftpClient.login(ftpUserName, ftpPassword);// 登陆FTP服务器  
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {  
                logger.info("未连接到FTP，用户名或密码错误。");  
                ftpClient.disconnect();  
            } else {  
                logger.info("FTP连接成功。");  
            }  
        } catch (SocketException e) {  
            e.printStackTrace();  
            logger.info("FTP的IP地址可能错误，请正确配置。");  
        } catch (IOException e) {  
            e.printStackTrace();  
            logger.info("FTP的端口错误,请正确配置。");  
        }  
        return ftpClient;  
    }  
  
    /**
	 * 获取服务器目录下的文件列表
	 * @param dir 目录
	 * @return 文件列表
	 */
	public static List<String> getFileList(String dir, FTPClient ftpClient ) {
		List<String> fileLists = new ArrayList<String>();
		//获得指定目录下所有文件名
		FTPFile[] ftpFiles = null;
		try {
//			System.out.println(dir);
			ftpFiles = ftpClient.listFiles(dir);
			if(ftpFiles !=null) {
//				System.out.println("ftpFiles="+ftpFiles.length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; ftpFiles != null && i < ftpFiles.length; i++) {
			FTPFile file = ftpFiles[i];
			if (file.isFile()) {
				String filename = file.getName();
				filename = filename.substring(0, 12);
//				System.out.println("ftpFiles=filename="+filename);
				fileLists.add(filename);
			}
		}
		return fileLists;
	}
	
    /* 
     * 从FTP服务器下载文件 
     *  
     * @param ftpHost FTP IP地址 
     *  
     * @param ftpUserName FTP 用户名 
     *  
     * @param ftpPassword FTP用户名密码 
     *  
     * @param ftpPort FTP端口 
     *  
     * @param ftpPath FTP服务器中文件所在路径 格式： ftptest/aa 
     *  
     * @param localPath 下载到本地的位置 格式：H:/download 
     *  
     * @param fileName 文件名称 
     */  
    public static List<String> downloadFtpFile(String ftpHost, String ftpUserName,  
            String ftpPassword, int ftpPort, String ftpPath, String localPath,  
            String maxtime , String agentgame) {  
  
    	List<String> listfileAll = new ArrayList<String>();
    	List<String> listfile = new ArrayList<String>();
        FTPClient ftpClient = null;  
  
        try {  
        	System.out.println(maxtime+"开始"+agentgame+"="+ftpPath);
            ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);  
            ftpClient.setControlEncoding("UTF-8"); // 中文支持  
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);  
            ftpClient.enterLocalPassiveMode();  
            boolean isExists = ftpClient.changeWorkingDirectory(ftpPath);
            
            if( isExists ) {
            	//先检查目录是否存在
                File localFile = new File(localPath + File.separatorChar + agentgame + ftpPath);  
                if( !localFile.exists()) {
                	localFile.mkdirs();
                }
                
                listfileAll = getFileList(ftpPath, ftpClient);
                listfile.addAll(listfileAll);
                
                int size1 = listfile.size();
                //System.out.println("size1="+size1);
                if(size1 > 0){
                	
                	//如果是补单文件，每次都读取整个文件夹数据下来
                	if( !ftpPath.contains("lostAndfound")) {
                		int index = listfile.indexOf(maxtime);
                		if(index == -1) {
                			for (int i = 0; i < size1; i++) {
                				if(Long.valueOf(listfile.get(i)) >= Long.valueOf(maxtime)) {
                					index = i;
									break;
								}
							}
                		}
                		
                		//如果没有还没找到,说明当前最大值之后没有最新的文件进来
                		if(index == -1) {
                			listfile = new ArrayList<String>();
                		} else {
                			listfile = listfile.subList(index, size1);//重新获取文件列表
                		}
                		
                		//查找手动补单的数据。手动补单的数据会放在补单时间的那个文件夹。
                		String currendate = maxtime.substring(0,8);
                		for (String string : listfileAll) {
							if( !currendate.equals(string.substring(0,8))) {//存在
								listfile.add(0, string);
							}
						}
                	}
					
                	//开始下载
                	OutputStream os = null;
                    for (String fileName : listfile) {
                    	fileName = fileName + ".xml";
                        localFile = new File(localPath + File.separatorChar + agentgame + ftpPath + fileName);
                        
                        //本地存在时先删除
                        if(localFile.exists()) {
                        	localFile.delete();
                        }
                        os = new FileOutputStream(localFile);
                        ftpClient.retrieveFile(fileName, os);
                        os.close();
//                        System.out.println(agentgame+""+ftpPath+fileName+"下载完成");
    				}
				}
                
            }
            
            ftpClient.logout();  
            logger.info("连接已关闭"); 
            return listfile;
            
        } catch (FileNotFoundException e) {  
            logger.error("没有找到" + ftpPath + "文件");  
            e.printStackTrace();  
        } catch (SocketException e) {  
            logger.error("连接FTP失败.");  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
            logger.error("文件读取错误。");  
            e.printStackTrace();  
        }  
        return listfile;
    }  
  
  
  
}  
