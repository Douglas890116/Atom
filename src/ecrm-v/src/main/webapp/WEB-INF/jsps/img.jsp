<%@ page contentType="image/jpeg" import="java.awt.*, java.io.*, java.awt.image.*,java.util.*,javax.imageio.*,com.maven.util.VerifyCodeUtils" %> 

<% 
response.setHeader("Pragma", "No-cache");  
response.setHeader("Cache-Control", "no-cache");  
response.setDateHeader("Expires", 0);  
response.setContentType("image/jpeg");  
  
String verifyCode = VerifyCodeUtils.generateVerifyCode(4);  
session.setAttribute("rand", verifyCode.toLowerCase());  
int w = 160, h = 30;  
OutputStream os = response.getOutputStream();
VerifyCodeUtils.outputImage(w, h, os, verifyCode);
os.close();  
os.flush();

out.clear();
out = pageContext.pushBody();

%>