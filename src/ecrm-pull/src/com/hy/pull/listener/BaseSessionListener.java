package com.hy.pull.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * session监听类
 * 创建日期 2016-10-06 
 * @author temdy
 */
public class BaseSessionListener implements HttpSessionListener {

    /**
     * 默认构造函数. 
     */
    public BaseSessionListener() {
        
    }

	/**
     * session创建的监听方法
     * @param event 会话事件
     */
    public void sessionCreated(HttpSessionEvent event) {
    	/**
    	 * 处理相应的逻辑
    	 */
    }

	/**
     * session失效的监听方法
     * @param event 会话事件
     */
    public void sessionDestroyed(HttpSessionEvent event) {
    	/**
    	 * 处理相应的逻辑
    	 */
    }

}
