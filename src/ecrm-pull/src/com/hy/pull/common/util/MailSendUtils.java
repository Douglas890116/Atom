package com.hy.pull.common.util;

import java.util.Date;  
import java.util.Properties;  
  
import javax.mail.Address;  
import javax.mail.BodyPart;  
import javax.mail.Message;  
import javax.mail.MessagingException;  
import javax.mail.Multipart;  
import javax.mail.Session;  
import javax.mail.Transport;  
import javax.mail.internet.InternetAddress;  
import javax.mail.internet.MimeBodyPart;  
import javax.mail.internet.MimeMessage;  
import javax.mail.internet.MimeMultipart;  
  
public class MailSendUtils {  
    private MailSenderInfo mailInfo = new MailSenderInfo();  
    
    public static void main(String[] args) {
    	MailSendUtils sms = new MailSendUtils("this is title", "this is content", "278948465@qq.com");  
        System.out.println(sms.sendTextMail());  
	}
  
    public MailSendUtils(String subject, String message,String toAddress) {  
        mailInfo.setMailServerHost("mail.hy888.ph");  
        mailInfo.setMailServerPort("25");  
        mailInfo.setValidate(false);  
        mailInfo.setUserName("jason@hy888.ph");  
        mailInfo.setPassword("123456789");  
        mailInfo.setFromAddress("jason@hy888.ph");  
        mailInfo.setToAddress(new String[]{toAddress});  
        mailInfo.setSubject(subject);  
        mailInfo.setContent(message);  
    }  
  
    /** 
     * 以TEXT格式发送邮件 
     *  
     */  
    public boolean sendTextMail() {  
        // 判断是否需要身份认证  
        MyAuthenticator authenticator = null;  
        Properties pro = mailInfo.getProperties();  
        if (mailInfo.isValidate()) {  
            // 如果需要身份认证，则创建一个密码验证器  
            authenticator = new MyAuthenticator(mailInfo.getUserName(),  
                    mailInfo.getPassword());  
        }  
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session  
        Session sendMailSession = Session  
                .getDefaultInstance(pro, authenticator);  
        try {  
            // 根据session创建一个邮件消息  
            Message mailMessage = new MimeMessage(sendMailSession);  
            // 创建邮件发送者地址  
            Address from = new InternetAddress(mailInfo.getFromAddress());  
            // 设置邮件消息的发送者  
            mailMessage.setFrom(from);  
            // 创建邮件的接收者地址，并设置到邮件消息中  
            String[] ToAddresses = mailInfo.getToAddress();  
            Address[] addressList = new InternetAddress[ToAddresses.length];  
            for (int i = 0; i < ToAddresses.length; i++) {  
                addressList[i] = new InternetAddress(ToAddresses[i]);  
            }  
            // Message.RecipientType.TO属性表示接收者的类型为TO  
            mailMessage.setRecipients(Message.RecipientType.TO, addressList);  
            // 设置邮件消息的主题  
            mailMessage.setSubject(mailInfo.getSubject());  
            // 设置邮件消息发送的时间  
            mailMessage.setSentDate(new Date());  
            // 设置邮件消息的主要内容  
            String mailContent = mailInfo.getContent();  
            mailMessage.setText(mailContent);  
            // 发送邮件  
            Transport.send(mailMessage);  
            return true;  
        } catch (MessagingException ex) {  
            ex.printStackTrace();  
        }  
        return false;  
    }  
  
    /** 
     * 以HTML格式发送邮件 
     *  
     */  
    public boolean sendHtmlMail() {  
        // 判断是否需要身份认证  
        MyAuthenticator authenticator = null;  
        Properties pro = mailInfo.getProperties();  
        // 如果需要身份认证，则创建一个密码验证器  
        if (mailInfo.isValidate()) {  
            authenticator = new MyAuthenticator(mailInfo.getUserName(),  
                    mailInfo.getPassword());  
        }  
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session  
        Session sendMailSession = Session  
                .getDefaultInstance(pro, authenticator);  
        try {  
            // 根据session创建一个邮件消息  
            Message mailMessage = new MimeMessage(sendMailSession);  
            // 创建邮件发送者地址  
            Address from = new InternetAddress(mailInfo.getFromAddress());  
            // 设置邮件消息的发送者  
            mailMessage.setFrom(from);  
            // 创建邮件的接收者地址，并设置到邮件消息中  
            String[] ToAddresses = mailInfo.getToAddress();  
            Address[] addressList = new InternetAddress[ToAddresses.length];  
            for (int i = 0; i < ToAddresses.length; i++) {  
                addressList[i] = new InternetAddress(ToAddresses[i]);  
            }  
            // Message.RecipientType.TO属性表示接收者的类型为TO  
            mailMessage.setRecipients(Message.RecipientType.TO, addressList);  
            // 设置邮件消息的主题  
            mailMessage.setSubject(mailInfo.getSubject());  
            // 设置邮件消息发送的时间  
            mailMessage.setSentDate(new Date());  
            // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象  
            Multipart mainPart = new MimeMultipart();  
            // 创建一个包含HTML内容的MimeBodyPart  
            BodyPart html = new MimeBodyPart();  
            // 设置HTML内容  
            html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");  
            mainPart.addBodyPart(html);  
            // 将MiniMultipart对象设置为邮件内容  
            mailMessage.setContent(mainPart);  
            // 发送邮件  
            Transport.send(mailMessage);  
            return true;  
        } catch (MessagingException ex) {  
            ex.printStackTrace();  
        }  
        return false;  
    }  
}  
