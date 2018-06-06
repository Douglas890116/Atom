package com.test.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestLock{
	
	private Lock lock = new ReentrantLock();
	
	public void executeLock() throws Exception{
		try {
			lock.lock();
			throw new Exception();
		} finally {
			System.out.println("go into finlly");
			lock.unlock();
		}
	}
	
	public void executeanother() throws Exception{
		for (int i = 0; i < 50; i++) {
            System.out.println(Thread.currentThread().getName() + " synchronized loop " + i); 
        }
	}
	
	
	public void executeSygn() throws Exception{
		synchronized (this) {
		 for (int i = 0; i < 50; i++) {
                System.out.println(Thread.currentThread().getName() + " synchronized loop " + i); 
         }
		}
	}
	
	public static void main(String[] args) {
		   TestLock lock = new TestLock();
		   Run1 run1 = new Run1(lock);
		   Run2 run2 = new Run2(lock);
	       Thread ta = new Thread(run1, "A");
	       Thread tb = new Thread(run2, "B");
	       ta.start();
	       tb.start(); 
		
		
	}
	

}
