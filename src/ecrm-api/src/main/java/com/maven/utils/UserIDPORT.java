package com.maven.utils;


import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.maven.game.enums.GameEnum;
import com.maven.util.RandomString;

import sun.security.action.PutAllAction;



public class UserIDPORT {

	private static Log logger = LogFactory.getLog(UserIDPORT.class);
	
	/**
	 * 
	 * 4位数的流水号，左补0，0开始计数
	 * 
	 * @return
	 */
	private static int POKE = 0;
	public static String getAgentUserNo(){
		POKE++;
		if(POKE>9999) POKE = 0;
		return String.format("%04d", POKE);
	}
	/**
	 * 设置起始数
	 */
	public static void setStartPoke(int start) {
		POKE = start;
	}
	
	public static void main(String[] args) {
		// 快速测算高并发情况下重复的概率，即一秒内需要成功获取的单号数
		Map<String,String> map = new HashMap<String,String>();
        
		setStartPoke(0);
		for (int i = 0; i < 300; i++) {
			String no = getAgentUserNo()+"";
			map.put(no, no);
			System.out.println(i+"-"+no+"-"+no.length());
		}
		setStartPoke(300);
		for (int i = 0; i < 200; i++) {
			String no = getAgentUserNo()+"";
			map.put(no, no);
			System.out.println(i+"-"+no+"-"+no.length());
		}
		setStartPoke(0);
		for (int i = 0; i < 100; i++) {
			String no = getAgentUserNo()+"";
			map.put(no, no);
			System.out.println(i+"-"+no+"-"+no.length());
		}
		System.out.println("map="+map.size());
	}
	
	
}
