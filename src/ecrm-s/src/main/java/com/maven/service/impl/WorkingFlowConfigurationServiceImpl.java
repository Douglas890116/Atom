package com.maven.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.cache.SystemCache;
import com.maven.constant.Constant;
import com.maven.dao.WorkingFlowConfigurationDao;
import com.maven.entity.WorkingFlowConfiguration;
import com.maven.service.WorkingFlowConfigurationService;
import com.maven.service.WorkingFlowObjectService;
import com.maven.util.AttrCheckout;

@Service
public class WorkingFlowConfigurationServiceImpl extends BaseServiceImpl<WorkingFlowConfiguration> 
	implements WorkingFlowConfigurationService{

	@Autowired
	private WorkingFlowConfigurationDao workingFlowConfigurationDao;

	@Autowired
	private WorkingFlowObjectService workingFlowObjectService;
	
	
	@Override
	public BaseDao<WorkingFlowConfiguration> baseDao() {
		return workingFlowConfigurationDao;
	}

	@Override
	public Class<WorkingFlowConfiguration> getClazz() {
		return WorkingFlowConfiguration.class;
	}

	@Override
	public List<WorkingFlowConfiguration> queryRechargeWorkingFlow(Map<String, Object> object) throws Exception {
		object.put("flowtype", WorkingFlowConfiguration.Enum_flowtype.存款工作流.value);
		object.put("status", Constant.Enum_DataStatus.正常.value);
		return super.selectAll(AttrCheckout.checkout(object,false,
				new String[]{"enterprisecode","flowtype","status"}));
	}
	
	@Override
	public int queryRechargeWorkingFlowCount(Map<String, Object> object) throws Exception {
		object.put("flowtype", WorkingFlowConfiguration.Enum_flowtype.存款工作流.value);
		object.put("status", Constant.Enum_DataStatus.正常.value);
		return super.selectAllCount(AttrCheckout.checkout(object,false,
				new String[]{"enterprisecode","flowtype","status"}));
	}

	@Override
	public int addRechargeWorkingFlow(WorkingFlowConfiguration workflow) throws Exception {
		AttrCheckout.checkout(workflow,  false,
				new String[]{"enterprisecode","handletime","enable","flowsort","flowthreshold"});
		workflow.setFlowtype((short)WorkingFlowConfiguration.Enum_flowtype.存款工作流.value);
		this.updateSort(workflow);
		super.add(workflow);
		SystemCache.getInstance().getWorkflow().reload(workflow.getEnterprisecode());
		return 1;
	}

	@Override
	public int editRechargeWorkingFlow(WorkingFlowConfiguration workflow) throws Exception {
		AttrCheckout.checkout(workflow, false,new String[]{"flowcode","enterprisecode"});
		workflow.setFlowtype((short)WorkingFlowConfiguration.Enum_flowtype.存款工作流.value);
		this.updateSort(workflow);
		super.update(workflow);
		SystemCache.getInstance().getWorkflow().reload(workflow.getEnterprisecode());
		return 1;
	}


	@Override
	public List<WorkingFlowConfiguration> queryWithdrawlWorkingFlow(Map<String, Object> object) throws Exception {
		object.put("flowtype", WorkingFlowConfiguration.Enum_flowtype.取款工作流.value);
		object.put("status", Constant.Enum_DataStatus.正常.value);
		return super.selectAll(AttrCheckout.checkout(object, false,
				new String[]{"enterprisecode","flowtype","status"}));
	}

	@Override
	public int queryWithdrawlWorkingFlowCount(Map<String, Object> object) throws Exception {
		object.put("flowtype", WorkingFlowConfiguration.Enum_flowtype.取款工作流.value);
		object.put("status", Constant.Enum_DataStatus.正常.value);
		return super.selectAllCount(AttrCheckout.checkout(object, false,
				new String[]{"enterprisecode","flowtype","status"}));
	}
	@Override
	public int addWithdrawlWorkingFlow(WorkingFlowConfiguration workflow) throws Exception {
		AttrCheckout.checkout(workflow, false,
				new String[]{"enterprisecode","handletime","enable","flowsort","flowthreshold"});
		workflow.setFlowtype((short)WorkingFlowConfiguration.Enum_flowtype.取款工作流.value);
		this.updateSort(workflow);
		super.add(workflow);
		SystemCache.getInstance().getWorkflow().reload(workflow.getEnterprisecode());
		return 1;
	}

	@Override
	public int editWithdrawlWorkingFlow(WorkingFlowConfiguration workflow) throws Exception {
		AttrCheckout.checkout(workflow, false,new String[]{"flowcode","enterprisecode"});
		workflow.setFlowtype((short)WorkingFlowConfiguration.Enum_flowtype.取款工作流.value);
		this.updateSort(workflow);
		super.update(workflow);
		SystemCache.getInstance().getWorkflow().reload(workflow.getEnterprisecode());
		return 1;
	}

	@Override
	public int delWorkingFlow(String flowcode,String enterprisecode) throws Exception{
		Map<String,Object> object = new HashMap<String, Object>();
		object.put("flowcode", flowcode);
		object.put("datastatus", Constant.Enum_DataStatus.删除.value);
		super.update(AttrCheckout.checkout(object,false, new String[]{"flowcode","datastatus"}));
		workingFlowObjectService.deleteObjectByFlowcode(flowcode);
		SystemCache.getInstance().getWorkflow().reload(enterprisecode);
		return 1;
	}
	
	/**
	 * 查用户是否在存款或取款流程里面
	 */
	@Override
	public int selectByEmployeecodeCount(String employeecode,String enterprisecode,WorkingFlowConfiguration.Enum_flowtype flowtype) throws Exception{
		Map<String,Object> object = new HashMap<String, Object>();
		object.put("enterprisecode", enterprisecode);
		object.put("flowtype", flowtype.value);
		object.put("status", Constant.Enum_DataStatus.正常.value);
		
		int count = 0;
		List<WorkingFlowConfiguration> list = super.selectAll(object);
		for (WorkingFlowConfiguration workingFlowConfiguration : list) {
			count += workingFlowObjectService.selectByEmployeecode(employeecode, workingFlowConfiguration.getFlowcode());
		}
		
		return count;
	}

	private int updateSort(Object object) throws Exception{
		AttrCheckout.checkout(object, false, new String[]{"enterprisecode","flowsort","flowtype"});
		workingFlowConfigurationDao.updateSort(object);
		return 1;
	}

	

}
