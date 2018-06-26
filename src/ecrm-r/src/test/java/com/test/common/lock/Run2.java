package com.test.common.lock;

public class Run2 implements Runnable{

	 private TestLock lock ; 
	 
	 public Run2(TestLock lock){
		 this.lock=lock;
	 }

	@Override
	public void run() {
		try {
			lock.executeanother();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
