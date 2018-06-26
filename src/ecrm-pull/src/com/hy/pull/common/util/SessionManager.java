package com.hy.pull.common.util;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

/**
 * session管理器
 * 创建日期 2016-10-06 
 * @author temdy
 */
public class SessionManager {

	// 获取初始化对象
	private static SessionManager instance;

	// session存放容器
	private HashMap<String, HttpSession> sessions;

	/**
	 * 获取单例对象
	 * 
	 * @author temdy
	 * @return 单例对象
	 */
	public static SessionManager getInstance() {
		if (instance == null) {
			instance = new SessionManager();
		}
		return instance;
	}

	/**
	 * 构造函数
	 */
	private SessionManager() {
		sessions = new HashMap<String, HttpSession>();
	}

	/**
	 * 增加session
	 * 
	 * @author temdy
	 * @param session 会话
	 */
	public synchronized void putSession(HttpSession session) {
		if (session != null) {
			session.setMaxInactiveInterval(86400);
			sessions.put(session.getId(), session);
		}
	}

	/**
	 * 移除一个session
	 * 
	 * @author temdy
	 * @param session 会话
	 */
	public synchronized void removeSession(HttpSession session) {
		if (session != null) {
			sessions.remove(session.getId());
		}
	}

	/**
	 * 通过sessionid获取session对象
	 * 
	 * @author temdy
	 * @param session_id 会话ID sessionid
	 * @return session对象
	 */
	public synchronized HttpSession getSession(String session_id) {
		HttpSession ret = null;
		if (session_id != null) {
			HttpSession session = sessions.get(session_id);
			if (session != null) {
				ret = session;
			}
		}
		return ret;
	}
}
