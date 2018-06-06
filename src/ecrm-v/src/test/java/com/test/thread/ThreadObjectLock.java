package com.test.thread;

import org.apache.commons.lang.math.RandomUtils;

/*
 * 对象锁，只要使用了同一个实例对象，都将执行等待队列， 对象如果被重新实例化，则开启另一个等待队列
 */
@SuppressWarnings("unused")
public class ThreadObjectLock extends Thread{

	private static int index = 0;
	
	private static User user = new User("队列1");
	@Override
	public void run() {
		changobject();
	}
	
	
	private void objectlock() {
		index++;
		synchronized (user) {
			System.out.println(index);
			try {
				ThreadObjectLock.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void changobject() {
		index++;
		if(index==50){
			user = new User("队列2");
		}
		synchronized (user) {
			System.out.println(user.getName()+"   "+user);
			try {
				ThreadObjectLock.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		
		for(int i = 1; i<100;i++){
			Thread thread = new Thread(new ThreadObjectLock());
			thread.start();
		}
	}
	

}
