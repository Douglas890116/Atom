package com.maven.util;

import java.net.URL;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailUtil {
	
	private static String SMTP_PORT = "465";//qq=465 163=25
	private static String SMTP_AUTH = "true";
	
	private static String sender = "3532822400@qq.com";// 发件人邮箱地址
	private static String password = "lnpujzfbatyydbha";// 发件人邮箱密码
	private static String smtpHost = "smtp.qq.com";// 邮件发送服务器（smtp）比如163就是smtp.163.com
	
	private static String to = "278948465@qq.com";//接收邮箱
	
	
	public static boolean send(String sendname, String title, String content) {
		
		try {
            //设置SSL连接、邮件环境
            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());  
            final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";  
            Properties props = System.getProperties();
            props.setProperty("mail.smtp.host", smtpHost);
            props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            props.setProperty("mail.smtp.port", SMTP_PORT);
            props.setProperty("mail.smtp.socketFactory.port", SMTP_PORT);
            props.setProperty("mail.smtp.auth", SMTP_AUTH);
//            props.put("mail.smtp.host", "smtp.qq.com");
//            props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
//            props.put("mail.smtp.socketFactory.fallback", "false");
//            props.put("mail.smtp.port", "465");
//            props.put("mail.smtp.socketFactory.port", "465");
//            props.put("mail.smtp.auth", "true");
            //建立邮件会话
            Session session = Session.getDefaultInstance(props, new Authenticator() {
                //身份认证
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(sender, password);
                }
            });
            //建立邮件对象
            MimeMessage message = new MimeMessage(session);
            //设置邮件的发件人、收件人、主题
                //附带发件人名字
            message.setFrom(new InternetAddress(sender, sendname));
//            message.setFrom(new InternetAddress(sender));
            message.setRecipients(Message.RecipientType.TO, to);
            message.setSubject(title+getcurrentime());
            message.setText(content);
//            //文本部分
//            MimeBodyPart textPart = new MimeBodyPart();
//            textPart.setContent(content, "text/html;charset=UTF-8");
//            //内嵌图片部分
//            MimeBodyPart imagePart = new MimeBodyPart();
//            imagePart.setDataHandler(new DataHandler(new FileDataSource("imagePath")));//图片路径
//            imagePart.setContentID("myimg");
//            //图文整合，关联关系
//            MimeMultipart mmp1 = new MimeMultipart();
//            mmp1.addBodyPart(textPart);
//            mmp1.addBodyPart(imagePart);
//            mmp1.setSubType("related");
//            MimeBodyPart textImagePart = new MimeBodyPart();
//            textImagePart.setContent(mmp1);
//            //附件部分
//            MimeBodyPart attachmentPart = new MimeBodyPart();
//            DataHandler dh = new DataHandler(new FileDataSource("filePath"));//文件路径
//            String fileName = dh.getName();
//            attachmentPart.setDataHandler(dh);
//            attachmentPart.setFileName(fileName);
//            //图文和附件整合，复杂关系
//            MimeMultipart mmp2 = new MimeMultipart();
//            mmp2.addBodyPart(textImagePart);
//            mmp2.addBodyPart(attachmentPart);
//            mmp2.setSubType("mixed");
//            //将以上内容添加到邮件的内容中并确认
//            message.setContent(mmp2);
            message.saveChanges();
            //发送邮件
            Transport.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
		return false;
	}

	public static void main(String[] args) {
		
		String title = "数据采集通知";
		String content = UUID.randomUUID().toString();
		
		send(title, "", content);
        
        System.out.println("发送完毕");
    }
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HH:mm");
	private static String getcurrentime(){
		String Time = sdf.format(new Date());
		return Time;
	}
	
}
