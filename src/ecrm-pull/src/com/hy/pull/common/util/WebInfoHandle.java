package com.hy.pull.common.util;

import java.net.InetAddress;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;


public class WebInfoHandle {
	
	private final static String[] AGENT = { "Android", "iPhone", "iPod", "iPad", "Windows Phone", "MQQBrowser" };
	/**
	 * 如果是手机端访问则返回true
	 * @param request
	 * @return
	 */
	public static boolean checkAgentIsMobile(HttpServletRequest request) {
		String ua = request.getHeader("User-Agent");  
	    boolean flag = false;  
	    if (!ua.contains("Windows NT") || (ua.contains("Windows NT") && ua.contains("compatible; MSIE 9.0;"))) {
	        // 排除 苹果桌面系统  
	        if (!ua.contains("Windows NT") && !ua.contains("Macintosh")) {  
	            for (String item : AGENT) {  
	                if (ua.contains(item)) {  
	                    flag = true;  
	                    break;  
	                }  
	            }  
	        }  
	    }  
	    return flag;  
	}  

	
	/** 
     * 获取本机的IP 
     * @return Ip地址 
     */ 
     public static String getLocalHostIP() { 
          String ip; 
          try { 
               /**返回本地主机。*/ 
               InetAddress addr = InetAddress.getLocalHost(); 
               /**返回 IP 地址字符串（以文本表现形式）*/ 
               ip = addr.getHostAddress();  
          } catch(Exception ex) { 
              ip = ""; 
          } 
          return ip; 
     }

     
	/**
	 * 获取用户IP 
	 * @param request
	 * @return
	 */
	public static String getClientRealIP(HttpServletRequest request) {
		String ip = "";
		// 真实发出请求的客户端IP
		ip = request.getHeader("X-Real-IP");
		
		if (null == ip || "".equals(ip.trim()) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("x-forwarded-for");
			if (null != ip && !"".equals(ip.trim()) && !"unknown".equalsIgnoreCase(ip)) {
				// 多次反向代理后会有多个IP值，第一个为真实IP。
				int index = ip.indexOf(',');
				if (index != -1) {
					ip = ip.substring(0, index);
				}
			}
		}
		
		if (null == ip || "".equals(ip.trim()) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (null == ip || "".equals(ip.trim()) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (null == ip || "".equals(ip.trim()) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 获取浏览器版本信息
	 * @param request
	 * @return
	 */
	public static String getBrowser(HttpServletRequest request){
        String ua = request.getHeader("User-Agent").toLowerCase();
        String msieP = "msie ([\\d.]+)";  
        String firefoxP = "firefox\\/([\\d.]+)";  
        String chromeP = "chrome\\/([\\d.]+)";  
        String operaP = "opera.([\\d.]+)/)";  
        String safariP = "version\\/([\\d.]+).*safari";  
          
        Pattern pattern = Pattern.compile(msieP);  
        Matcher mat = pattern.matcher(ua);  
        if(mat.find()){  
          return  mat.group();  
        }  
        pattern = Pattern.compile(firefoxP);  
        mat=pattern.matcher(ua);  
        if(mat.find()){  
          return mat.group();  
        }  
        pattern = Pattern.compile(chromeP);  
        mat=pattern.matcher(ua);  
        if(mat.find()){  
            return mat.group();  
        }   
        pattern = Pattern.compile(operaP);  
        mat=pattern.matcher(ua);  
        if(mat.find()){  
            return mat.group();  
        }   
        pattern = Pattern.compile(safariP);  
        mat=pattern.matcher(ua);  
        if(mat.find()){  
            return mat.group();  
        }   
        return "";  
    } 
	
	/**
	 * 获取引用网址
	 * @param request
	 * @return
	 */
	public static String getReferer(HttpServletRequest request){
		return request.getHeader("Referer");
	}
	
	 /** 
     * 获取系统版本信息 
     * @param request 
     * @return 
     */  
    public static String getRequestSystem(HttpServletRequest request){  
      String systenInfo = null;  
      String header = request.getHeader("User-Agent");  
      if(header == null || header.equals("")){  
       return "";  
      }  
        //得到用户的操作系统  
        if (header.indexOf("NT 6.0") > 0){  
            systenInfo = "Windows Vista/Server 2008";  
        } else if (header.indexOf("NT 5.2") > 0){  
            systenInfo = "Windows Server 2003";  
        } else if (header.indexOf("NT 5.1") > 0){  
            systenInfo = "Windows XP";  
        } else if (header.indexOf("NT 6.0") > 0){  
            systenInfo = "Windows Vista";  
        } else if (header.indexOf("NT 6.1") > 0){  
            systenInfo = "Windows 7";  
        } else if (header.indexOf("NT 6.2") > 0){  
            systenInfo = "Windows Slate";  
        } else if (header.indexOf("NT 6.3") > 0){  
            systenInfo = "Windows 9";  
        } else if (header.indexOf("NT 10.0") > 0){
        	systenInfo = "Windows 10";  
        } else if (header.indexOf("NT 5") > 0){  
            systenInfo = "Windows 2000";  
        } else if (header.indexOf("NT 4") > 0){  
            systenInfo = "Windows NT4";  
        } else if (header.indexOf("Me") > 0){  
            systenInfo = "Windows Me";  
        } else if (header.indexOf("98") > 0){  
            systenInfo = "Windows 98";  
        } else if (header.indexOf("95") > 0){  
            systenInfo = "Windows 95";  
        } else if (header.indexOf("Mac") > 0){  
            systenInfo = "Mac";  
        } else if (header.indexOf("Unix") > 0){  
            systenInfo = "UNIX";  
        } else if (header.indexOf("Linux") > 0){  
            systenInfo = "Linux";  
        } else if (header.indexOf("SunOS") > 0){  
            systenInfo = "SunOS";  
        }  
        return systenInfo;  
     }  
    
    public void printHeaderInfo(HttpServletRequest request){
    	Enumeration<?> header = request.getHeaderNames();

    	while(header.hasMoreElements()){
    	    try{
    	      String str=(String)header.nextElement();
    	      System.out.print("Attribute key:"+str+"  --  ");
    	      String strRequest=request.getHeader(str);//取得相应参数的参数值
    	      System.out.println("  value："+strRequest+" ");//打印输出参数值
    	}catch(Exception e){
    	    System.out.println(e.getMessage());    
    	}
    	 
    	}
    }
    
}
