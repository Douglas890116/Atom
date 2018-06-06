package com.maven.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

public class OnlineSessionUtil {

	//	KEY=员工编号
	public static Map<String, HttpSession> mapSession = new HashMap<String, HttpSession>();
	public static String SESSION_USER_KEY = "SESSION_USER_KEY_132HJ";
	
}
