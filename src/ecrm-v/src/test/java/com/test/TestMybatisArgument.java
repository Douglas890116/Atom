package com.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.maven.config.SpringContextUtil;
import com.maven.dao.EnterpriseDao;
import com.maven.entity.Enterprise;

import junit.framework.TestCase;

public class TestMybatisArgument extends TestCase {
	
	public void testArgument(){
		try {
			EnterpriseDao impl = SpringContextUtil.getBean("enterpriseDaoImpl");
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("enterprisename", "好盈科技");
			//String  enterprisename = "好盈科技";
			Enterprise es = new Enterprise();
			es.setEnterprisename("好盈科技");
			es.setEnterprisecode("EN00000");
			List<Enterprise> list = impl.selectAll("selectAllByObject", es);
			for (Enterprise ee : list) {
				System.out.println(ee.getEnterprisename());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
