package com.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.maven.config.SpringContextUtil;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.WorkingFlowObject;
import com.maven.service.WorkingFlowObjectService;

import junit.framework.TestCase;

public class WorkingFlowObjectServiceTest extends TestCase {

	public void testAddFlowObject() {
		try {
			WorkingFlowObjectService service = SpringContextUtil.getBean("workingFlowObjectServiceImpl");
			List<WorkingFlowObject> list = new ArrayList<WorkingFlowObject>();
			list.add(new WorkingFlowObject("E0000000","WF000001"));
			list.add(new WorkingFlowObject("E000000P","WF000001"));
			list.add(new WorkingFlowObject("E000000Q","WF000001"));
			list.add(new WorkingFlowObject("E000000S","WF000001"));
			list.add(new WorkingFlowObject("E000000V","WF000001"));
			service.addFlowObject("WF000001", list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	public void testtakeGrantEmployee() {
		try {
			WorkingFlowObjectService service = SpringContextUtil.getBean("workingFlowObjectServiceImpl");
			Map<String,Object> object = new HashMap<String, Object>();
			object.put("departmentcode", "BM000000");
			object.put("flowcode", "WF000001");
			Map<String,List<EnterpriseEmployee>> mm = service.takeGrantEmployee(object);
			List<EnterpriseEmployee> e1 = mm.get("Grant");
			for (EnterpriseEmployee e : e1) {
				System.out.println("已加入:"+e.getEmployeecode());
			}
			List<EnterpriseEmployee> e2 = mm.get("NoGrant");
			for (EnterpriseEmployee e : e2) {
				System.out.println("未加入:"+e.getEmployeecode());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	public void testtakeEmployeeWorkFlow(){
		try {
			WorkingFlowObjectService service = SpringContextUtil.getBean("workingFlowObjectServiceImpl");
			List<WorkingFlowObject>  o  = service.takeEmployeeWorkFlow("DSDS");
			System.out.println(o.size());
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
	}
	
	

}
