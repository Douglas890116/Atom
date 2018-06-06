/*
 * @(#)SpringContextHolder.java
 */
package com.maven.utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * 以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候中取出ApplicaitonContext.
 */
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {
  private static Logger logger = LogManager.getLogger(SpringContextHolder.class.getName());
  private static ApplicationContext applicationContext = null;

  public SpringContextHolder() {
    super();
  }

  public void setApplicationContext(ApplicationContext applicationContext) {
    logger.debug("注入ApplicationContext到SpringContextHolder:" + applicationContext);

    if (SpringContextHolder.applicationContext != null) {
      logger.debug("SpringContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为:"
              + SpringContextHolder.applicationContext);
    }

    SpringContextHolder.applicationContext = applicationContext;
  }

  /**
   * 实现DisposableBean接口,在Context关闭时清理静态变量.
   */
  public void destroy() throws Exception {
    SpringContextHolder.clear();
  }

  /**
   * 取得存储在静态变量中的ApplicationContext.
   */
  public static ApplicationContext getApplicationContext() {
    assertContextInjected();
    return applicationContext;
  }

  /**
   * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
   */
  @SuppressWarnings("unchecked")
  public static <T> T getBean(String name) {
    assertContextInjected();
    return (T) applicationContext.getBean(name);
  }

  /**
   * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
   */
  public static <T> T getBean(Class<T> requiredType) {
    assertContextInjected();
    return applicationContext.getBean(requiredType);
  }

  /**
   * 清除SpringContextHolder中的ApplicationContext为Null.
   */
  public static void clear() {
    logger.debug("清除SpringContextHolder中的ApplicationContext:" + applicationContext);
    applicationContext = null;
  }

  /**
   * 检查ApplicationContext不为空.
   */
  private static void assertContextInjected() {
    if (applicationContext == null) {
      throw new IllegalStateException(
          "applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
    }
  }
}
