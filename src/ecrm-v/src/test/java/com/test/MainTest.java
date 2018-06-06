package com.test;

public class MainTest {
	
	 /** 
     * @param args 
     */  
    public static void main(String[] args) {  
        EventSourceObject object = new EventSourceObject();  
        //注册监听器  
        object.addCusListener(new CusEventListener());  
        //触发事件  
        object.setName("eric");  
    }  

}
