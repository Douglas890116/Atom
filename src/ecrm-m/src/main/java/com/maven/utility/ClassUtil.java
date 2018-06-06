package com.maven.utility;

public class ClassUtil {

  public static String getCurrentMethodName(Throwable throwable) {
    if (throwable == null)
      return null;
    return throwable.getStackTrace()[0].getMethodName();
  }

  public static String getCurrentClassName(Throwable throwable) {
    if (throwable == null)
      return null;
    return throwable.getStackTrace()[0].getClassName();
  }

  public static String getMapId(Class<?> poClass, Throwable throwable) {
    return poClass.getSimpleName() + "." + getCurrentMethodName(throwable);
  }
}
