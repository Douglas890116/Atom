package com.maven.game;


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



public class OrderNewUtil2 {

	private static Log logger = LogFactory.getLog(OrderNewUtil2.class);
	private static String machid = "0";//集群机器号1位（后期做多机负载时，该代码需要动态获取0-9）
	
	/**
	 * 
	 * 获取16位数单号
	 * 
	 * 获取毫秒级+三位流水号=16位数
	 * @return
	 */
	private static int POKE = 100;
	public static String getPatchno(){
		POKE++;
		if(POKE>=999) POKE = 100;
		return (new Date().getTime()*1000 + POKE) + "";
	}
	
	public static void main(String[] args) {
		// 快速测算高并发情况下重复的概率，即一秒内需要成功获取的单号数
		Map<String,String> map = new HashMap<String,String>();
        
		for (int i = 0; i < 100; i++) {
			String no = getPatchno()+"";
			map.put(no, no);
			
			System.out.println(i+"-"+no+"-"+no.length());
		}
		System.out.println("map="+map.size());//输出map的大小，如果大小不与循环次数相等，则意味着存在重复的单号
	}
	
	
}
