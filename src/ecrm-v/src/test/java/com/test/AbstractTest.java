package com.test;

import com.maven.game.HttpUtils;

public abstract class AbstractTest {
	
	public static void main(String[] args) {
		try {
			HttpUtils.get("http://192.168.1.207:8080/ecrm/User/login?enterprisecode=EN0000&signature=d1776442e5c365f6b46bc4a94933cc52&params=HpBb60stkRDJNTrMqx9%2FojPvWZViJaPlhXgCrHfj9llrcmO6LPbRrsxKoajUsE4I");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
