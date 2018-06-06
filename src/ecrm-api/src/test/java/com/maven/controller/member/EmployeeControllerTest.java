package com.maven.controller.member;

import com.maven.game.HttpUtils;

import junit.framework.TestCase;

public class EmployeeControllerTest extends TestCase {
	
	public void testLogin(){
		try {
			String content = HttpUtils.get("http://192.168.1.207:8080/ecrm-api/User/login?enterprisecode=EN0000&signature=afc09a96e9d589ead73f0fcc83b7ccda&params=%2Biid2LZt%2FSX%2B8138F%2BwrRAcHSli%2FC8oGUk4SKOFG%2BnIOOBbWwJoV0FuCQ3jIFkoNrZKYYJt70RyP%0D%0AB5s9lyuSZdlXhWk6KOTdbySioVnCO7FlXZXCZNG3TNiU8OfPI2IN%2BXXGG2sHHMAyFLdIHOhvyWOG%0D%0AoqbDeYntDYbPfi5asy4%3D");
			System.out.println("content:"+content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
