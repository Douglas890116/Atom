package com.test.common.lock;

public class Run1 implements Runnable{
	
	 private TestLock lock ; 
	 
	 public Run1(TestLock lock){
		 this.lock=lock;
	 }

	@Override
	public void run() {
		try {
			lock.executeSygn();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
