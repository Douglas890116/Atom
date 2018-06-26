package com.test.common.extend;

public abstract class First extends Base{

	public First(){
		index++;
		System.out.println("First index"+index);
	};

	public First(String s){
		index++;
		System.out.println("  into First class "+s);
		System.out.println("First index"+index);
	}
}
