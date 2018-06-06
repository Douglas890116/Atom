package com.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.maven.config.SpringContextUtil;
import com.maven.entity.TakeDepositRecord;
import com.maven.entity.WorkingFlowObject;
import com.maven.service.TakeDepositRecoredService;

import junit.framework.TestCase;

public class TakeDepositRecoredServiceTest extends TestCase {

	public void testFindTakeDepositRecord() {
		try {
			TakeDepositRecoredService ss = SpringContextUtil.getBean("takeDepositRecoredServiceImpl");
			Map<String,Object> object = new HashMap<String, Object>();
			List<WorkingFlowObject> o = new ArrayList<WorkingFlowObject>();
			o.add(new WorkingFlowObject("E0000000","WF000001"));
			o.add(new WorkingFlowObject("E0000000","WF000002"));
			object.put("flowcodes", o);
			object.put("brandcode", "EB000001");
			List<TakeDepositRecord> list = ss.findTakeDepositRecord(object);
			for (TakeDepositRecord t : list) {
				System.out.println(t.getOrdernumber());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
