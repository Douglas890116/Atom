package com.maven.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maven.base.dao.DataSource;
import com.maven.base.service.BaseServcie;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeInformation;
/**
 * 用户银行信息Service
 * @author Ethan
 *
 */
@Service
public interface EnterpriseEmployeeInformationService extends BaseServcie<EnterpriseEmployeeInformation>{
	
	/**
	 * 分页查询
	 * @param obj
	 * @return List<EnterpriseEmployeeInformation>
	 */
	@DataSource("slave")
	public List<EnterpriseEmployeeInformation> findEmployeeInfo(Map<String, Object> obj)throws Exception;
	/**
	 * 查询统计
	 * @param obj
	 * @return int
	 */
	@DataSource("slave")
	public int findEmployeeInfoCount(Map<String, Object> obj) throws Exception;
	
	/**
	 * 保存用户银行信息
	 * @param enterpriseEmployeeInformation
	 */
	@DataSource("master")
	int tc_saveEnterpriseEmployeeInformation(EnterpriseEmployeeInformation enterpriseEmployeeInformation)throws Exception;
	/**
	 * 银行信息单条记录删除
	 * @param request
	 */
	@DataSource("master")
	void tc_deleteBankInfo(String code)throws Exception;
	
	/**
	 * 银行信息单条记录删除
	 * @param request
	 */
	@DataSource("master")
	public int tc_deleteEmployeeBankCard(String employeecode ,String informationcode)throws Exception;
	/**
	 * 删除选中的一条或者多条数据
	 * @param request
	 */
	@DataSource("master")
	void tc_deleteSelectBankInfo(String[] array)throws Exception;
	/**
	 * 查询单个实体对象
	 * @param informationcode
	 * @return EnterpriseEmployeeInformation
	 */
	@DataSource("slave")
	EnterpriseEmployeeInformation findOneBankInfo(String informationcode);
	/**
	 * 根据银行卡查询单个实体对象
	 * @param paymentaccount
	 * @return EnterpriseEmployeeInformation
	 */
	@DataSource("slave")
	EnterpriseEmployeeInformation findEnterpriseEmployeeInformation(String enterprisecode,String paymentaccount);
	
	/**
	 * 锁定与解锁银行卡
	 * @param eei
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	public int tc_lockEmployeeInformation(EnterpriseEmployeeInformation eei)throws Exception;
	/**
	 * 用户修改自己银行卡信息
	 * @param enterpriseEmployeeInformation
	 */
	@DataSource("master")
	int tc_updateEnterpriseEmployeeInformation(EnterpriseEmployeeInformation enterpriseEmployeeInformation)throws Exception;
	/**
	 * 管理员修改用户的银行卡信息
	 * @param enterpriseEmployeeInformation
	 * @return
	 * @throws Exception
	 */
	@DataSource("master")
	int tc_updateEnterpriseEmployeeInformationByAdmin(EnterpriseEmployeeInformation enterpriseEmployeeInformation)throws Exception;
	/**
	 * 根据员工编码删除所有的银行信息
	 * @param employeecode
	 * @throws RuntimeException
	 */
	@DataSource("master")
	void tc_deleteEmployeeBankInfo(String employeecode)throws Exception;
	/**
	 * 批量删除不同用户的银行信息
	 * @param array
	 */
	@DataSource("master")
	void tc_deleteSelectEmployeeBankInfo(String[] array)throws Exception;
	
	/**
	 * 查询当前登录用户的信息
	 * @param loginEmployee
	 * @return
	 */
	@DataSource("slave")
	EnterpriseEmployeeInformation queryAccountData(EnterpriseEmployee loginEmployee)throws Exception;
	
	/**
	 * 根据开户名称与银行卡查询是否存在
	 * @param paramter
	 * @return
	 * @throws Exception
	 */
	@DataSource("slave")
	public EnterpriseEmployeeInformation queryBankCardIsNoExist(Map<String, Object> paramter)throws Exception;
	
	/**
	 * 逻辑删除选中的一条或者多条数据
	 * @param array
	 * @throws Exception
	 */
	@DataSource("master")
	public void tc_logicDeleteSelectEmployeeBankInfo(String[] array)throws Exception;
}
