package com.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.maven.entity.Enterprise;
import com.maven.util.AttrCheckout;

import junit.framework.TestCase;

public class TestClone extends TestCase {
	
	public void testCloneMap(){
		try {
			List<Enterprise> list = new ArrayList<Enterprise>();
			Map<String,Enterprise> map = new HashMap<String, Enterprise>();
			Enterprise en = new Enterprise("EN00001", "好赢科技", "123");
			list.add(en);
			map.put("m", en);
			Enterprise ens = (Enterprise) AttrCheckout.checkout(en,true,new String[]{"enterprisename"},"parententerprisecode");
			System.out.println(ens);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	

}
