package com.maven.utility;

import java.text.MessageFormat;


public class BizException extends Exception{
  private static final long serialVersionUID = 300100L;

  public BizException(String s) {
      super(s);
  }

  public BizException(Exception t) {
      super(t);
  }

  public BizException(String pattern, Object... arguments) {
      super(MessageFormat.format(pattern, arguments));
  }

  public BizException(String s, Exception t) {
      super(s, t);
  }

  public BizException(Exception t, String pattern, Object... arguments) {
      super(MessageFormat.format(pattern, arguments), t);
  }

  public static BizException noPermission() {
      return new BizException("缺少必要的操作权限。");
  }
}
