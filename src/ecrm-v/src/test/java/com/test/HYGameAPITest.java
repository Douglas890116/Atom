package com.test;

import com.maven.entity.GameApiInput;

import junit.framework.TestCase;

public class HYGameAPITest extends TestCase {

	public void testData() {
		try {
			GameApiInput Game = new GameApiInput();
			Game.setApiurl("http://www.77777f.com/service/");
			Game.setPlatformcode("5f448b277e804abda1d583e82b936555");
			Game.setApikey1("test0003");
			Game.setApikey2("test3333");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
