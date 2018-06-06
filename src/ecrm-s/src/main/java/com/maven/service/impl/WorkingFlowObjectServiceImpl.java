package com.maven.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.WorkingFlowObjectDao;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeType;
import com.maven.entity.WorkingFlowObject;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.WorkingFlowObjectService;
import com.maven.util.AttrCheckout;

import edu.emory.mathcs.backport.java.util.Arrays;

@Service
public class WorkingFlowObjectServiceImpl extends BaseServiceImpl<WorkingFlowObject> 
	implements WorkingFlowObjectService{

	@Autowired
	private WorkingFlowObjectDao workingFlowObjectDao;
	
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	
	@Override
	public BaseDao<WorkingFlowObject> baseDao() {
		return workingFlowObjectDao;
	}

	@Override
	public Class<WorkingFlowObject> getClazz() {
		return WorkingFlowObject.class;
	}

	/**
	 * 添加工作流审核对象
	 */
	@Override
	public int addFlowObject(String flowcode,List<WorkingFlowObject> list) throws Exception {
		Map<String,Object> object = new HashMap<String, Object>();
		object.put("flowcode", flowcode);
		super.delete(object);
		return super.saveRecordBatch(AttrCheckout.checkout(list,false, new String[]{"employeecode","flowcode"}));
	}

	
	@Override
	public int deleteObjectByFlowcode(String flowcode) throws Exception {
		Map<String,Object> object = new HashMap<String, Object>();
		object.put("flowcode", flowcode);
		return super.delete(object);
	}
	
	/**
	 * 查用户是否在某个流程里面
	 * @param employeecode
	 * @param flowcode
	 * @return
	 * @throws Exception
	 */
	public int selectByEmployeecode(String employeecode,String flowcode) throws Exception {
		Map<String,Object> object = new HashMap<String, Object>();
		object.put("employeecode", employeecode);
		object.put("flowcode", flowcode);
		return super.selectAllCount(object);
	}

	/**
	 * 获取部门下已加入和未加入工作流节点的用户
	 */
	@Override
	public Map<String, List<EnterpriseEmployee>> takeGrantEmployee(Map<String, Object> object) throws Exception {
		object.put("employeetypecodes",
				Arrays.asList(new String[]{EnterpriseEmployeeType.Type.员工.value,EnterpriseEmployeeType.Type.企业号.value}));
		//获取企业所有员工和企业号
		List<EnterpriseEmployee> _all_available_users = enterpriseEmployeeService.findEmployee(
				AttrCheckout.checkout(object,true, new String[]{"employeetypecodes","enterprisecode"}));
		//获取工作流已添加对象
		List<WorkingFlowObject> _grant_user = this.takeFlowObject(String.valueOf(object.get("flowcode")));
		
		Map<String,List<EnterpriseEmployee>> _return_object = new HashMap<String, List<EnterpriseEmployee>>();
		Map<String,Integer> wom = new HashMap<String, Integer>();
		for (WorkingFlowObject w : _grant_user) {
			wom.put(w.getEmployeecode(), 1);
		}
		List<EnterpriseEmployee> list = new ArrayList<EnterpriseEmployee>();
		_return_object.put("Grant", list);
		_return_object.put("NoGrant", _all_available_users);
		Iterator<EnterpriseEmployee> ie = _all_available_users.iterator();
		while(ie.hasNext()){
			EnterpriseEmployee e= ie.next();
			if(wom.get(e.getEmployeecode())!=null){
				list.add(e);
				ie.remove();
			}
		}
		return _return_object;
	}
	
	/**
	 * 获取用户可审核的流程
	 */
	@Override
	public List<WorkingFlowObject> takeEmployeeWorkFlow(String employeeCode) throws Exception {
		return super.select(new WorkingFlowObject(employeeCode, null));
	}

	/**
	 * 获取工作节点审核对象
	 */
	private List<WorkingFlowObject> takeFlowObject(String flowcode) throws Exception {
		return super.select(AttrCheckout.checkout(new WorkingFlowObject(null, flowcode),false, new String[]{"flowcode"}));
	}

	

}
