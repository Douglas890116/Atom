package com.maven.util;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class RandomStringNum {
	
	 private static int POKE = 10000;
	
	 private static char ch[] = { 
			 '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
			 '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
			 '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
			 '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
			 '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
			 '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
			 '0', '1', '2', '3', '4', '5' };//最后又重复两个0和1，因为需要凑足数组长度为64

	
	 
		  private static Random random = new Random();

		  //生成指定长度的随机字符串
		  public static synchronized String createRandomString(int length) {
		    if (length > 0) {
		      int index = 0;
		      char[] temp = new char[length];
		      int num = random.nextInt();
		      for (int i = 0; i < length % 5; i++) {
		        temp[index++] = ch[num & 63];//取后面六位，记得对应的二进制是以补码形式存在的。
		        num >>= 6;//63的二进制为:111111
		        // 为什么要右移6位？因为数组里面一共有64个有效字符。为什么要除5取余？因为一个int型要用4个字节表示，也就是32位。
		      }
		      for (int i = 0; i < length / 5; i++) {
		        num = random.nextInt();
		        for (int j = 0; j < 5; j++) {
		          temp[index++] = ch[num & 63];
		          num >>= 6;
		        }
		      }
		      return new String(temp, 0, length);
		    }
		    else if (length == 0) {
		      return "";
		    }
		    else {
		      throw new IllegalArgumentException();
		    }
		  }
		  
		 /**
		  * 产生在指定长度之间的任意字符串
		  * @param minlength
		  * @param maxlength
		  * @return
		  */
		 public static synchronized String createRandomString(int minlength,int maxlength){
			 int length = random.nextInt(maxlength-minlength)+minlength;
			 return createRandomString(length);
		 }

		
		  //根据指定个数，测试随机字符串函数的重复率
		  public static double rateOfRepeat(int number) {
		    int repeat = 0;
		    String[] str = new String[number];
		    for (int i = 0; i < number; i++) {//生成指定个数的字符串
		      str[i] = RandomStringNum.createRandomString(10);
		    }
		    for (int i = 0; i < number; i++) {//查找是否有相同的字符串
		      for (int j = i + 1; j < number - 1; j++) {
		        if (str[i].equals(str[j]))
		          repeat++;
		      }
		    }
		    return ((double) repeat) / number;
		  }
		  
		/**
		 * 生成32位的订单号
		 * @return
		 */
		public static String UUID(){
			return UUID.randomUUID().toString().trim().replaceAll("-", "").toUpperCase();
		}
		  
		/**
		 * 获取毫秒级+四位精确排序值
		 * @return
		 */
		public static synchronized long SORTTIME(){
			POKE++;
			if(POKE>=90000) POKE = 10000;
			return new Date().getTime()*100000+POKE;
		}
		
		public static void main(String[] args) {
			for(int i=0;i<1000;i++){
				System.out.println((SORTTIME()+"").length());
			}
			System.out.println(new Date().getTime());
				
		}
}
