package com.test;

import com.maven.service.EnterpriseService;
import com.maven.service.impl.EnterpriseServiceImpl;

import junit.framework.TestCase;

public class TestEnterprise extends TestCase{
	
	EnterpriseService service = new EnterpriseServiceImpl();
	
	public void testSelectAll(){
		System.out.println("\u5171{totalCount}\u6761\u8bb0\u5f55");
	}
	

}
