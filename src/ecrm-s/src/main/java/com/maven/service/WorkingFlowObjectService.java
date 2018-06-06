package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.WorkingFlowObject;

@Service
public interface WorkingFlowObjectService{
	
	/**
	 * 添加工作流的操作权限对象
	 * @see 集合对象必须参数  "employeecode","employeecode"
	 * @return
	 */
	@DataSource("master")
	public int addFlowObject(String flowcode,List<WorkingFlowObject> list)  throws Exception ;
	
	/**
	 * 通过工作流编码删除审核用户
	 * @param flowcode
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public int deleteObjectByFlowcode(String flowcode) throws Exception;
	
	/**
	 * 查用户是否在某个流程里面
	 * @param employeecode
	 * @param flowcode
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public int selectByEmployeecode(String employeecode,String flowcode) throws Exception ;
	
	
	/**
	 * 获取部门下已加入和未加入工作流节点的用户
	 * @see 必须参数  "departmentcode","flowcode" 
	 * @return  map.get("Grant") 已加入工作流节点  map.get("NoGrant") 未加入工作流节点
	 * @throws Exception
	 */
	@DataSource("slave")
	public Map<String,List<EnterpriseEmployee>> takeGrantEmployee(Map<String, Object> object) throws Exception;
	
	/**
	 * 获取员工的工作流标志
	 * @param employeeCode 员工编码
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public List<WorkingFlowObject> takeEmployeeWorkFlow(String employeeCode) throws Exception;
	
}
