package com.maven.utility;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ContextUrlManager {
  protected static HttpServletRequest getRequest(){
    HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    return request;
  }
  
  protected static String getContextPath(){
    HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    return request.getRequestURI();
  }
  
  public static String getBasePath() {
    HttpServletRequest request = null;
    StringBuffer fullurl = new StringBuffer();
    try {
      request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
      fullurl = request.getRequestURL();
      fullurl.delete(fullurl.indexOf(request.getRequestURI()), fullurl.length());
      fullurl.append(request.getContextPath());
    } catch (Exception e) {
    }
    return fullurl.toString();
  }
}
