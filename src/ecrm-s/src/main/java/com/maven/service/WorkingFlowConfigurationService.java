package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.WorkingFlowConfiguration;

@Service
public interface WorkingFlowConfigurationService extends BaseServcie<WorkingFlowConfiguration>{
	
	/**
	 * 查询当前企业存款工作流
	 * @see 参数："enterpeisecode"
	 * @see 可选参数："flowcode","departmentcode","brandcode","employeecode","enable","flowsort","flowthreshold","createtime_begin","createtime_end","start","limit"
	 * @return
	 */
	@DataSource("slave")
	public List<WorkingFlowConfiguration> queryRechargeWorkingFlow(Map<String,Object> object) throws Exception;
	
	/**
	 * 查询存款工作流总记录数
	 * @see 参数："enterpeisecode"
	 * @see 可选参数："flowcode","departmentcode","brandcode","employeecode","enable","flowsort","flowthreshold","createtime_begin","createtime_end"
	 * @return
	 */
	@DataSource("slave")
	public int queryRechargeWorkingFlowCount(Map<String,Object> object) throws Exception;
	/**
	 * 添加存款工作流 
	 * @see 参数："enterpeisecode","flowtype","departmentcode","enable","flowsort","flowthreshold"
	 * @see 可选参数："brandcode","employeecode"
	 * @return
	 */
	@DataSource("master")
	public int addRechargeWorkingFlow(WorkingFlowConfiguration workflow) throws Exception;
	/**
	 * 编辑存款工作流
	 * @see 参数："flowcode"
	 * @see 可选参数："departmentcode","brandcode","employeecode","enable","flowsort","flowthreshold"
	 * @return
	 */
	@DataSource("master")
	public int editRechargeWorkingFlow(WorkingFlowConfiguration workflow) throws Exception;

	/**
	 * 查询当前企业取款工作流
	 * @see 参数："enterpeisecode"
	 * @see 可选参数："flowcode","departmentcode","brandcode","employeecode","enable","flowsort","flowthreshold","createtime_begin","createtime_end","start","limit"
	 * @return
	 */
	@DataSource("slave")
	public List<WorkingFlowConfiguration> queryWithdrawlWorkingFlow(Map<String,Object> object)throws Exception;
	
	/**
	 * 查询当前企业取款工作流总记录数
	 * @see 参数："enterpeisecode"
	 * @see 可选参数："flowcode","departmentcode","brandcode","employeecode","enable","flowsort","flowthreshold","createtime_begin","createtime_end"
	 * @return
	 */
	@DataSource("slave")
	public int queryWithdrawlWorkingFlowCount(Map<String,Object> object)throws Exception;
	/**
	 * 添加取款工作流
	 * @see  参数："enterpeisecode","departmentcode","enable","flowsort","flowthreshold"
	 * @see 可选参数："brandcode","employeecode"
	 * @return
	 */
	@DataSource("master")
	public int addWithdrawlWorkingFlow(WorkingFlowConfiguration workflow)throws Exception;
	/**
	 * 编辑取款工作流
	 * @see 参数："flowcode"
	 * @see 可选参数："departmentcode","brandcode","employeecode","enable","flowsort","flowthreshold"
	 * @return
	 */
	@DataSource("master")
	public int editWithdrawlWorkingFlow(WorkingFlowConfiguration workflow)throws Exception;
	
	/**
	 * 删除工作流
	 * @see 参数: {"flowcode"}
	 * @return
	 */
	@DataSource("master")
	public int delWorkingFlow(String flowcode,String enterprisecode)throws Exception;
	
	/**
	 * 查用户是否在存款或取款流程里面
	 * @param employeecode
	 * @param enterprisecode
	 * @param flowtype
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public int selectByEmployeecodeCount(String employeecode,String enterprisecode,WorkingFlowConfiguration.Enum_flowtype flowtype) throws Exception;
	

}
