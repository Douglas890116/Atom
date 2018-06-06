package com.test;

public class ThreadTest extends Thread {
	
	public static ThreadLocal<Object> localValiable = new ThreadLocal<Object>();

	@Override
	public void run() {
		System.out.println(Thread.currentThread());
		System.out.println(localValiable.get());
	}
	
	public static void main(String[] args) {
		ThreadTest.localValiable.set("this is threadlocal");
		for (int i = 0; i < 15; i++) {
			Thread thread = new Thread(new ThreadTest());
			thread.start();
		}
		
	}

}
