package com.test;

public class OpenURLTest {
	
	public static void main(String[] args) {
		try {
			java.net.URI uri = new java.net.URI("http://www.baidu.com");
	        java.awt.Desktop.getDesktop().browse(uri);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
