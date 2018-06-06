package com.maven.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.EmployeeApiAccout;
import com.maven.entity.EnterpriseBrandActivity;

@Service
public interface EmployeeApiAccoutService extends BaseServcie<EmployeeApiAccout>{
	
	/**
	 * 查询一批账号
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public List<EmployeeApiAccout> getEmployeeApiAccoutByObject(EmployeeApiAccout object) throws Exception ;
	
	/**
	 * 根据游戏账号和类型查找对象
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public EmployeeApiAccout getEmployeeApiAccoutByGameaccount(EmployeeApiAccout object) throws Exception ;
	
	/**
	 * 获取用户平台游戏账号和密码
	 * @see 必选参数  "employeecode","gametype"
	 * @return
	 */
	@DataSource("slave")
	public EmployeeApiAccout getEmployeeApiAccout(EmployeeApiAccout object) throws Exception;
	/**
	 * 获取用户所有的游戏平台账号
	 * @param object
	 * @return
	 */
	@DataSource("slave")
	public List<EmployeeApiAccout> getAllEmployeeApiAccout(String employeecode) throws Exception;
	
	/**
	 * 创建游戏平台账户
	 * @param object 必须参数 "apicode","employeecode","gameaccount","gamepassword"
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public int tc_createApiAccount(EmployeeApiAccout object) throws Exception;
	
	/**
	 * 修改
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public int updatePassword(EmployeeApiAccout object) throws Exception ;
	
	/**
	 * 关联游戏表查询用户游戏账号
	 * @param eaa
	 * @return
	 */
	@DataSource("slave")
	List<EmployeeApiAccout> selectUnionGName(EmployeeApiAccout eaa);
	
	/**
	 * 禁用选中的一条或者多条数据
	 * @param String[] array
	 */
	@DataSource("master")
	void tc_disableSelectEmployee(String[] array) throws Exception;
	/**
	 * 启用选中的一条或者多条数据
	 * @param String[] array
	 */
	@DataSource("master")
	void tc_activateSelectEmployee(String[] array) throws Exception;
	
}
