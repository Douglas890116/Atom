package com.test.thread;

import com.test.extend.Description;

@Description("测试对象方法锁的应用级别(对象,容器)")
public class ThreadMethodLock extends Thread{
	
	private static int index = 0;
	
	
	
	@Override
	public void run() {
		printName();
	}


	public static synchronized void printName(){
		index++;
		System.out.println(index);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		for (int i=0;i<100;i++) {
			Thread thread = new Thread(new ThreadMethodLock());
			thread.start();
		}
	}

}
