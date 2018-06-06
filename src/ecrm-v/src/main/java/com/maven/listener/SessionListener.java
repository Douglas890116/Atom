package com.maven.listener;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.maven.constant.Constant;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployee.Enum_onlinestatus;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.utility.SpringContextHolder;


public class SessionListener implements HttpSessionListener,HttpSessionAttributeListener{
	
	/** 在线人数 */
	private static int ONLINE_USER = 0;
	
	/** Session 替换标识 */
	private static String SESSION_REPLACE = "SESSIONREPLACE";
	
	/** SessionMap<Username,HttpSession>映射 */
	private static Map<String, HttpSession> sessionMap = new ConcurrentHashMap<String, HttpSession>();
	
	/**
	 * 添加session属性
	 */
    @Override
	public void attributeAdded(HttpSessionBindingEvent _arg) {
    	if(_arg.getName().equals(Constant.USER_SESSION)){
    		try {
    			EnterpriseEmployee user_session =  (EnterpriseEmployee)_arg.getValue();
    			sessionReplace(_arg, user_session);
    			if(_arg.getSession().getAttribute(SESSION_REPLACE)==null)
    				userStatus(user_session,EnterpriseEmployee.Enum_onlinestatus.在线);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
	}

	

    /**
     * 移除session属性
     */
    @Override
	public void attributeRemoved(HttpSessionBindingEvent _arg) {
		
	}

    /**
     * 替换session属性
     */
	@Override
	public void attributeReplaced(HttpSessionBindingEvent _arg) {
	
	}
    
	/**
	 * 创建session
	 */
	@Override
	public void sessionCreated(HttpSessionEvent _arg) {
		ONLINE_USER ++;
	}

	/**
	 * 销毁session
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent _arg) {
		EnterpriseEmployee user_session = (EnterpriseEmployee)_arg.getSession().getAttribute(Constant.USER_SESSION);
		if(user_session!=null){
			try {
				sessionMap.remove(user_session.getLoginaccount());
				if(_arg.getSession().getAttribute(SESSION_REPLACE)==null)
					userStatus(user_session,EnterpriseEmployee.Enum_onlinestatus.离线);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ONLINE_USER--;
	}
	
	/**
	 * 在线用户数
	 * @return
	 */
	public static int online(){
		return ONLINE_USER;
	}
	
	private void userStatus(EnterpriseEmployee user_session,EnterpriseEmployee.Enum_onlinestatus onlinestatus) throws Exception {
		EnterpriseEmployeeService service = SpringContextHolder.getBean("enterpriseEmployeeServiceImpl");
		EnterpriseEmployee status = new EnterpriseEmployee();
		status.setEmployeecode(user_session.getEmployeecode());
		status.setOnlinestatus(Byte.valueOf(onlinestatus.value));
		if(onlinestatus.value.equals(Enum_onlinestatus.在线.value)){
			status.setLastlogintime(new Date());
		}
		service.updateOnlineStatus(status);
	}

    /**
     * 用户重复登陆 Session 替换
     * @param _arg
     * @param user_session
     */
	private void sessionReplace(HttpSessionBindingEvent _arg, EnterpriseEmployee user_session) {
		//特殊处理如果是sa则不验证同会话登录
		if( user_session.getLoginaccount().equals("sa") ) {
			return ;
		}
		
		HttpSession oldSession = sessionMap.get(user_session.getLoginaccount());
		if(oldSession!=null){
			_arg.getSession().setAttribute(SESSION_REPLACE, SESSION_REPLACE);
			oldSession.setAttribute(SESSION_REPLACE, SESSION_REPLACE);
			oldSession.invalidate();
		}
		sessionMap.put(user_session.getLoginaccount(), _arg.getSession());
		
	}

}
