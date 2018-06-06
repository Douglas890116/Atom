package com.maven.utility;
import javax.servlet.http.HttpServletRequest;

public class IpUtil{
  /**
   * 获取到客户端IP地址
   * 
   * @param request
   * @return
   */
  public static String getIpAddr(HttpServletRequest request)
  {
      String ip = null;
      
      ip = request.getHeader("Cdn-Src-Ip");
      if(ip != null && !"".equals(ip))
      {
          return ip;
      }
      
      ip = request.getHeader("x-forwarded-for");
      if(ip != null && ip.indexOf(',') > 0)
      {
          String[] tmp = ip.split("[,]");
          for(int i = 0; tmp != null && i < tmp.length; i++)
          {
              if(tmp[i] != null && tmp[i].length() > 0 && !"unknown".equalsIgnoreCase(tmp[i]))
              {
                  ip = tmp[i];
                  break;
              }
          }
      }
      if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
      {
          ip = request.getHeader("Proxy-Client-IP");
      }
      if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
      {
          ip = request.getHeader("WL-Proxy-Client-IP");
      }
      if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
      {
          ip = request.getRemoteAddr();
      }
      return ip;
  }
}
