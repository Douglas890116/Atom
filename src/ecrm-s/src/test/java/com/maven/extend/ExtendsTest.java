package com.maven.extend;

public class ExtendsTest {

	public static Employee getEmployee(){
		return new Employee();
	}
	
	public static Manager getManager(){
		Manager manager = (Manager) getEmployee();
		return manager;
	}
	
}
