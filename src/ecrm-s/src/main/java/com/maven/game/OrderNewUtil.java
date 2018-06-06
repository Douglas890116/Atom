package com.maven.game;


import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.maven.game.enums.GameEnum;
import com.maven.util.RandomString;

import sun.security.action.PutAllAction;



public class OrderNewUtil {

	private static Log logger = LogFactory.getLog(OrderNewUtil.class);
	private static String machid = "0";//集群机器号1位（后期做多机负载时，该代码需要动态获取0-9）
	
	/**
	 * 获取上分操作订单号
	 * 
	 * 考虑到负载均衡架构，使用配置文件配置当前机器使用的端口号，加入到生成单号的最前面，
	 * 
	 * 即：业务类型代码固定值1+集群机器号1位+时间戳13位数+随机数4位=订单号19位纯数字
	 * 
	 * @return
	 */
	public static String getOrdernoUP() {
		long times = new Date().getTime();//13位数的时间戳
		int max = 9999;
		int min = 1000;
        Random random = new Random();
        long romnum = random.nextInt(max)%(max-min+1) + min;//生成指定区间的随机数
        
        return 1+""+machid+""+times+""+romnum;
	}
	
	/**
	 * 获取下分操作订单号
	 * 
	 * 考虑到负载均衡架构，使用配置文件配置当前机器使用的端口号，加入到生成单号的最前面，
	 * 
	 * 即：业务类型代码固定值2+集群机器号1位+时间戳13位数+随机数4位=订单号19位纯数字
	 * 
	 * @return
	 */
	public static String getOrdernoDOWN() {
		long times = new Date().getTime();//13位数的时间戳
		
		int max = 9999;
		int min = 1000;
        Random random = new Random();
        long romnum = random.nextInt(max)%(max-min+1) + min;//生成指定区间的随机数
        
        
        return 2+""+machid+""+times+""+romnum;
	}
	
	
	/**
	 * 
	 * 获取上下分操作的批次号（上下分操作为同一批次号）
	 * 
	 * 
	 * 业务代码固定值9+机器码1位++获取毫秒级+四位流水号=19位数
	 * @return
	 */
	private static int POKE = 1000;
	public static String getPatchno(){
		POKE++;
		if(POKE>=9999) POKE = 1000;
		return "9"+machid+""+(new Date().getTime()*10000 + POKE) + "";
	}
	
	/**
	 * 
	 * 获取取款单号
	 * 
	 * 
	 * 业务代码固定值8+机器码1位++获取毫秒级+四位流水号=19位数
	 * @return
	 */
	private static int POKE2 = 1000;
	public static String getOrdernoTake(){
		POKE2++;
		if(POKE2>=9999) POKE2 = 1000;
		return "8"+machid+""+(new Date().getTime()*10000 + POKE2) + "";
	}
	
	public static void main(String[] args) {
		// 快速测算高并发情况下重复的概率，即一秒内需要成功获取的单号数
//		Map<String,String> map = new HashMap<String,String>();
//        
//		for (int i = 0; i < 200000; i++) {
//			String no = getOrdernoTake()+"";
//			map.put(no, no);
//			
//			System.out.println(i+"-"+no+"-"+no.length());
//		}
//		System.out.println(map.size());
//		
//		System.out.println("147906604931110814".length());
		
		// 结论1：当随机数为五位数时，一万的并发会有29个重复单号
		// 结论2：当随机数为六位数时，一万的并发会有10个内的重复单号
		// 结论3：当随机数为九位数时，百万的并发不会有重复单号
		
		Calendar calendar = Calendar.getInstance();
		System.out.println(calendar.getTimeInMillis()+"="+calendar.getTime().toLocaleString());
		calendar.set(Calendar.YEAR, 2040);
		System.out.println(calendar.getTimeInMillis()+"="+calendar.getTime().toLocaleString());
	}
	
	
}
